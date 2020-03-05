/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
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

    // Limelight
    NetworkTableEntry limeTargetDetected;
    NetworkTableEntry limeHorizontalOffset;
    NetworkTableEntry limeDistance;
    NetworkTableEntry limeLED;
  
    boolean targetDetected = false;
    double targetHorizontalOffset;
    double targetDistance;

  @Override
  public void robotInit() {
    NetworkTableInstance NTInstance = NetworkTableInstance.getDefault();
    NetworkTable limelightTable = NTInstance.getTable("limelight");

    limeTargetDetected = limelightTable.getEntry("tv");
    limeHorizontalOffset = limelightTable.getEntry("tx");
    limeDistance = limelightTable.getEntry("ta");
    limeLED = limelightTable.getEntry("ledMode");

    leftJoystick = new EnhancedJoystick(0);
    rightJoystick = new EnhancedJoystick(1);
    manipulator = new Gamepad(2);

    driveBase = new DriveBase(0, 1, 0, 1, 2, 3, 0);
    harvester = new Harvester(2, 1);
    ballTransfer = new BallTransfer(3);
    ballChucker9000 = new BallChucker9000(4, 5, 6, 4, 5, 6, 7, 2, 8);
    climb = new Climb(7, 8, 9);

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

    // Indexer piston
    if (leftJoystick.getRawButton(1)) {
      ballChucker9000.indexerPistonOut(true);
    } else {
      ballChucker9000.indexerPistonOut(false);
    }

    driveBase.drive(leftJoystick.getY(), rightJoystick.getY());

    harvester.intakeMotorControl(manipulator.getAxis(Axis.LEFT_Y));
    harvester.delpoyIntake(manipulator.getButton(Button.A));

    ballTransfer.moveBalls(manipulator.getAxis(Axis.RIGHT_TRIGGER));

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

  void autoTarget() {

    // Check if we have a target. Default to false.
    targetDetected = limeTargetDetected.getBoolean(false);

    // Put values on SmartDashboard to see if we're reading correctly
    // (Will be removed once we know it's working right.)
    SmartDashboard.putBoolean("Limelight Target Detected", targetDetected);
    SmartDashboard.putNumber("Limelight Horizontal Offset", targetHorizontalOffset);
    SmartDashboard.putNumber("Limelight Target Distance", targetDistance);

    if (targetDetected) { // "Lock" state

      // Read offset and distance (target size) values
      targetHorizontalOffset = limeHorizontalOffset.getDouble(0.0);
      targetDistance = limeDistance.getDouble(0.0);

    } else { // "Search" state

    }

  }
}
