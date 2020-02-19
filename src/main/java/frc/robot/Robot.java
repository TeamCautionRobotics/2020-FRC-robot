/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
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

  Timer timer;

  @Override
  public void robotInit() {
    leftJoystick = new EnhancedJoystick(0);
    rightJoystick = new EnhancedJoystick(1);
    manipulator = new Gamepad(2);

    driveBase = new DriveBase(0, 1, 0, 1, 2, 3, 0);
    harvester = new Harvester(2, 1);
    ballTransfer = new BallTransfer(3);

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

    harvester.intakeMotorControl(manipulator.getAxis(Axis.LEFT_Y));
    harvester.delpoyIntake(manipulator.getButton(Button.A));

    ballTransfer.moveBalls(manipulator.getAxis(Axis.RIGHT_TRIGGER));
  }

  @Override
  public void testPeriodic() {
  }
}
