package frc.robot.commands;

import frc.robot.subsystems.BallChucker9000;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class BallChucker9000RotateRight extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final BallChucker9000 BallChucker9000Subsystem;

  /**
   * Creates a new BallChucker9000RotateRight.
   *
   * @param subsystem The subsystem used by this command.
   */
  public BallChucker9000RotateRight(BallChucker9000 subsystem) {
    BallChucker9000Subsystem = subsystem;
    addRequirements(BallChucker9000Subsystem);
  }

  @Override
  public void initialize() {
    BallChucker9000Subsystem.rotatorMotorControl(0.1);
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    BallChucker9000Subsystem.rotatorMotorControl(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
