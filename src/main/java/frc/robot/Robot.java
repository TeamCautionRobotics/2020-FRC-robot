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

  @Override
  public void robotInit() {
    leftJoystick = new EnhancedJoystick(0);
    rightJoystick = new EnhancedJoystick(1);
    manipulator = new Gamepad(2);

    driveBase = new DriveBase(new SpeedControllerGroup(new WPI_TalonSRX(10), new WPI_VictorSPX(11), new WPI_VictorSPX(12)),
        new SpeedControllerGroup(new WPI_TalonSRX(20), new WPI_VictorSPX(21), new WPI_VictorSPX(22)), 0, 1, 0, 1, 2, 3);
    harvester = new Harvester(new VictorSP(3), 2, 3);
    ballTransfer = new BallTransfer(new VictorSP(5));
    ballChucker9000 = new BallChucker9000(new VictorSP(0), new VictorSP(1), new VictorSP(4), new WPI_VictorSPX(31), 5, 6, 7, 8, 9);
    climb = new Climb(new WPI_VictorSPX(30), new VictorSP(2), 4, 10);

    timer = new Timer();
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
    }

    else {
      driveBase.drive(0, 0);
    }
  }

  @Override
  public void teleopPeriodic() {

    driveBase.drive(leftJoystick.getY(), rightJoystick.getY());

    harvester.intakeMotorControl(manipulator.getAxis(Axis.LEFT_TRIGGER));
    harvester.delpoyIntake(manipulator.getButton(Button.A));

    ballTransfer.moveBalls(manipulator.getAxis(Axis.RIGHT_TRIGGER));

    // Check the limit switch every loop
    ballChucker9000.update();

    // This needs some PID!
    if (rightJoystick.getTrigger()) {
      ballChucker9000.flywheelMotorControl(1);
    } else {
      ballChucker9000.flywheelMotorControl(0);
    }

    // Replace with limelight stuff at some point
    if (rightJoystick.getRawButton(2)) {
      ballChucker9000.rotatorMotorControl(1);
    } else if (rightJoystick.getRawButton(3)) {
      ballChucker9000.rotatorMotorControl(-1);
    } else {
      ballChucker9000.rotatorMotorControl(0);
    }

    // Indexer motor
    if (leftJoystick.getTrigger()) {
      ballChucker9000.indexerMotorControl(0.75);
    } else {
      ballChucker9000.indexerMotorControl(0);
    }

    if (manipulator.getButton(Button.X)) {
      climb.runWinch(1);
    } else {
      climb.runWinch(0);
    }

    climb.moveArms(manipulator.getAxis(Axis.RIGHT_Y));
  }

  @Override
  public void testPeriodic() {
  }
}
