/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.DriveBase;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SetHighGearCommand extends InstantCommand {
    public SetHighGearCommand(DriveBase driveBase, boolean highGear) {
        super(() -> driveBase.setHighGear(highGear), driveBase);
    }

    public SetHighGearCommand(DriveBase driveBase) {
        this(driveBase, true);
    }
}
