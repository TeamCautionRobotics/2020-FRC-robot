/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.DriveBase;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TankDrive extends CommandBase {
    private final DriveBase driveBase;
    private final DoubleSupplier leftPowerSupplier;
    private final DoubleSupplier rightPowerSupplier;

    public TankDrive(DriveBase driveBase, DoubleSupplier leftPowerSupplier, DoubleSupplier rightPowerSupplier) {
        this.driveBase = driveBase;
        this.leftPowerSupplier = leftPowerSupplier;
        this.rightPowerSupplier = rightPowerSupplier;
        addRequirements(driveBase);
    }

    @Override
    public void execute() {
        driveBase.drive(leftPowerSupplier.getAsDouble(), rightPowerSupplier.getAsDouble());
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
