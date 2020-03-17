/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.DriveBase;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDriveCommand extends CommandBase {
    private final DriveBase driveBase;
    private final DoubleSupplier leftPowerSupplier;
    private final DoubleSupplier rightPowerSupplier;
    private final BooleanSupplier squareInputs;

    public ArcadeDriveCommand(DriveBase driveBase, DoubleSupplier leftPowerSupplier, DoubleSupplier rightPowerSupplier,
            BooleanSupplier squareInputs) {
        this.driveBase = driveBase;
        this.leftPowerSupplier = leftPowerSupplier;
        this.rightPowerSupplier = rightPowerSupplier;
        this.squareInputs = squareInputs;
        addRequirements(driveBase);
    }

    public ArcadeDriveCommand(DriveBase driveBase, DoubleSupplier leftPowerSupplier,
            DoubleSupplier rightPowerSupplier) {
        this(driveBase, leftPowerSupplier, rightPowerSupplier, () -> false);
    }

    @Override
    public void execute() {
        driveBase.arcadeDrive(leftPowerSupplier.getAsDouble(), rightPowerSupplier.getAsDouble(), squareInputs.getAsBoolean());
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
