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

public class Robot extends TimedRobot {
  EnhancedJoystick leftJoystick;
  EnhancedJoystick rightJoystick;

  DriveBase driveBase;

  Timer timer;

  @Override
  public void robotInit() {
    leftJoystick = new EnhancedJoystick(0);
    rightJoystick = new EnhancedJoystick(1);

    driveBase = new DriveBase(0, 1, 0, 1, 2, 3, 0);
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
  }

  @Override
  public void testPeriodic() {
  }
}
