package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunWinchDown extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private final ClimbSubsystem subsystem;

  public RunWinchDown(ClimbSubsystem subsystem) {

    this.subsystem = subsystem;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    subsystem.runWinch(-1.0);
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.runWinch(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
