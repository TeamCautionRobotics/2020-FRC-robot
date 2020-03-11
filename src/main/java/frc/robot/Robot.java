/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import frc.misc2020.ButtonToggleRunner;
import frc.misc2020.EnhancedJoystick;
import frc.misc2020.Gamepad;
import frc.misc2020.Gamepad.Axis;
import frc.misc2020.Gamepad.Button;

public class Robot extends TimedRobot {
  EnhancedJoystick leftJoystick;
  EnhancedJoystick rightJoystick;
  Gamepad manipulator;

  DriveBase driveBase;
  Harvester harvester;
  BallTransfer ballTransfer;
  BallChucker9000 ballChucker9000;
  Climb climb;

  Timer timer;

  ButtonToggleRunner shifterToggleRunner;
  ButtonToggleRunner intakeDeployerToggleRunner;
  ButtonToggleRunner winchLockToggleRunner;

  @Override
  public void robotInit() {
    leftJoystick = new EnhancedJoystick(0);
    rightJoystick = new EnhancedJoystick(1);
    manipulator = new Gamepad(2);

    // motors are 0 indexed here
    WPI_VictorSPX leftDriveMotor1 = new WPI_VictorSPX(11);
    leftDriveMotor1.setInverted(true);
    WPI_VictorSPX rightDriveMotor1 = new WPI_VictorSPX(21);
    rightDriveMotor1.setInverted(true);

    SpeedControllerGroup leftDriveGroup = new SpeedControllerGroup(new WPI_TalonSRX(10), leftDriveMotor1,
        new WPI_VictorSPX(12));
    SpeedControllerGroup rightDriveGroup = new SpeedControllerGroup(new WPI_TalonSRX(20), rightDriveMotor1,
        new WPI_VictorSPX(22));

    VictorSP harvesterMotor = new VictorSP(5);
    harvesterMotor.setInverted(true);
    VictorSP ballTransfterMotor = new VictorSP(3);
    ballTransfterMotor.setInverted(true);

    VictorSP leftFlywheelMotor = new VictorSP(0);
    leftFlywheelMotor.setInverted(true);
    VictorSP rightFlywheelMotor = new VictorSP(1);
    rightFlywheelMotor.setInverted(true);
    WPI_VictorSPX rotatorMotor = new WPI_VictorSPX(4);
    rotatorMotor.setInverted(true);
    WPI_VictorSPX indexerMotor = new WPI_VictorSPX(31);
    indexerMotor.setInverted(true);

    WPI_VictorSPX winchMotor = new WPI_VictorSPX(30);
    WPI_VictorSPX armMotor = new WPI_VictorSPX(32);
    armMotor.setInverted(true);

    driveBase = new DriveBase(leftDriveGroup, rightDriveGroup, 4, 3, 0, 1, 2, 3);
    harvester = new Harvester(harvesterMotor, 2, 0);
    ballTransfer = new BallTransfer(ballTransfterMotor);
    ballChucker9000 = new BallChucker9000(leftFlywheelMotor, rightFlywheelMotor, rotatorMotor, indexerMotor, 5, 6, 7, 8,
        9);
    climb = new Climb(winchMotor, armMotor, 1, 10);

    timer = new Timer();

    shifterToggleRunner = new ButtonToggleRunner(() -> leftJoystick.getRawButton(3), driveBase::toggleHighGear);
    intakeDeployerToggleRunner = new ButtonToggleRunner(() -> manipulator.getButton(Button.A),
        harvester::toggleDeployer);
    winchLockToggleRunner = new ButtonToggleRunner(() -> manipulator.getButton(Button.Y), climb::toggleLock);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    timer.reset();
    timer.start();
  }

  @Override
  public void autonomousPeriodic() {
    if (timer.get() < 1) {
      driveBase.drive(0.5, 0.5);
    } else {
      driveBase.drive(0, 0);
    }
  }

  @Override
  public void teleopPeriodic() {
    /*
     * make a toggle with the sensor as the input. count 4 balls then ignore the
     * fifth Regarding the first 4, nudge them
     */

    driveBase.drive(-leftJoystick.getY(), -rightJoystick.getY());

    if (leftJoystick.getTrigger()) {
      harvester.intakeMotorControl(0.5);
      ballTransfer.moveBalls(0.5);
    } else if (leftJoystick.getRawButton(2)) {
      harvester.intakeMotorControl(-0.5);
      ballTransfer.moveBalls(-0.5);
    } else {
      harvester.intakeMotorControl(manipulator.getAxis(Axis.LEFT_Y));
      ballTransfer.moveBalls(manipulator.getAxis(Axis.RIGHT_Y));
    }

    // Check the limit switch every loop
    ballChucker9000.update();

    // This needs some PID!
    if (rightJoystick.getTrigger()) {
      ballChucker9000.moveFlywheel(1);
    } else {
      ballChucker9000.moveFlywheel(0);
    }

    // Replace with limelight stuff at some point
    if (manipulator.getButton(Button.RIGHT_BUMPER)) {
      ballChucker9000.moveRotator(ballChucker9000.getRotatorEncoder() >= 270 ? 0 : 0.1);
    } else if (manipulator.getButton(Button.LEFT_BUMPER)) {
      ballChucker9000.moveRotator(ballChucker9000.getRotatorEncoder() <= 0 ? 0 : -0.1);
    } else {
      ballChucker9000.moveRotator(0);
    }

    // Indexer motor
    if (rightJoystick.getRawButton(3)) {
      ballChucker9000.moveIndexer(0.75);
    } else if (rightJoystick.getRawButton(2)) {
      ballChucker9000.moveIndexer(-0.75);
    } else {
      ballChucker9000.moveIndexer(0);
    }

    if (manipulator.getButton(Button.X)) {
      climb.runWinch(0.5);
    } else if (manipulator.getButton(Button.B)) {
      climb.runWinch(-0.5);
    } else {
      climb.runWinch(0);
    }

    if (manipulator.getAxis(Axis.RIGHT_TRIGGER) >= 0.1) {
      climb.moveArm(0.7 * manipulator.getAxis(Axis.RIGHT_TRIGGER));
    } else {
      climb.moveArm(-manipulator.getAxis(Axis.LEFT_TRIGGER));
    }

    shifterToggleRunner.update();
    intakeDeployerToggleRunner.update();
    winchLockToggleRunner.update();
  }

  ButtonToggleRunner testWinchLockToggleRunner = new ButtonToggleRunner(() -> rightJoystick.getTrigger(),
      climb::toggleLock);

  @Override
  public void testPeriodic() {
    driveBase.arcadeDrive(leftJoystick.getY(), leftJoystick.getX());
    climb.moveArm(rightJoystick.getY());

    if (rightJoystick.getRawButton(3)) {
      climb.runWinch(0.5);
    } else if (rightJoystick.getRawButton(2)) {
      climb.runWinch(-0.5);
    } else {
      climb.runWinch(0);
    }

    testWinchLockToggleRunner.update();
  }
}
