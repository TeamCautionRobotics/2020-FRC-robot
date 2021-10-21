package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmAnalogControl extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private final ClimbSubsystem subsystem;
  private DoubleSupplier controlPower;

  public ArmAnalogControl(ClimbSubsystem subsystem, DoubleSupplier controlPower) {

    this.subsystem = subsystem;
    this.controlPower = controlPower;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

    subsystem.enablePid(false);

  }

  @Override
  public void execute() {
    SmartDashboard.putNumber("arm power", controlPower.getAsDouble());
    subsystem.moveArm(controlPower.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.moveArm(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
