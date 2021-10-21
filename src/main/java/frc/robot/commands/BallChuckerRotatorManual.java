package frc.robot.commands;

import frc.robot.subsystems.BallChuckerRotator;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.misc2020.LimelightData;

public class BallChuckerRotatorManual extends CommandBase {

  BallChuckerRotator ballChucker;
  DoubleSupplier inputPower;

  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  public BallChuckerRotatorManual(BallChuckerRotator subsystem, DoubleSupplier inputPower) {
    ballChucker = subsystem;
    this.inputPower = inputPower;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

    ballChucker.enablePid(false);

  }

  @Override
  public void execute() {

    ballChucker.setRotatorMotor(inputPower.getAsDouble());

  }

  @Override
  public void end(boolean interrupted) {

    ballChucker.setRotatorMotor(0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
