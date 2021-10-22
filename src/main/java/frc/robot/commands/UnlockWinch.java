package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class UnlockWinch extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private final ClimbSubsystem subsystem;
  private boolean finished = false;

  public UnlockWinch(ClimbSubsystem subsystem) {
    this.subsystem = subsystem;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    
    subsystem.lock(false);
    finished = true;

  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return finished;
  }
}
