/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.misc2020.EnhancedJoystick;
import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TankDrive extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final DriveBase driveBase;
    private final EnhancedJoystick leftJoystick;
    private final EnhancedJoystick rightJoystick;

    public TankDrive(DriveBase driveBase, EnhancedJoystick leftJoystick, EnhancedJoystick rightJoystick) {
        this.driveBase = driveBase;
        this.leftJoystick = leftJoystick;
        this.rightJoystick = rightJoystick;
        addRequirements(driveBase);
    }

    @Override
    public void execute() {
        driveBase.drive(leftJoystick.getY(), rightJoystick.getY());
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.drive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
