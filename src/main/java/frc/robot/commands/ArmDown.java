package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ArmDown extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private final ClimbSubsystem subsystem;

  public ArmDown(ClimbSubsystem subsystem) {

    this.subsystem = subsystem;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

    subsystem.enablePid(true);

  }

  @Override
  public void execute() {
    subsystem.moveArmPID(-50);
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.moveArmPID(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
