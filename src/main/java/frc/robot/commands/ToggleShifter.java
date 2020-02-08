/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ToggleShifter extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final DriveBase driveBase;

    public ToggleShifter(DriveBase driveBase) {
        this.driveBase = driveBase;
        addRequirements(driveBase);
    }

    @Override
    public void execute() {
        driveBase.switchGears();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
