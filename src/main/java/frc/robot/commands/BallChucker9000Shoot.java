package frc.robot.commands;

import frc.robot.subsystems.BallChucker9000;
import edu.wpi.first.wpilibj2.command.InstantCommand;


public class BallChucker9000Shoot extends InstantCommand {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final BallChucker9000 BallChucker9000Subsystem;
  private boolean forward;

  /**
   * Creates a new BallChucker9000Index.
   *
   * @param subsystem The subsystem used by this command.
   */
  public BallChucker9000Shoot(BallChucker9000 subsystem, boolean forward) {
    BallChucker9000Subsystem = subsystem;
    
    this.forward = forward;
  }

  @Override
  public void initialize() {
    if (this.forward) {
      BallChucker9000Subsystem.indexerMotorControl(0.75);
    } else {
      BallChucker9000Subsystem.indexerMotorControl(-0.75);
    }
  }

  @Override
  public void end(boolean interrupted) {
    BallChucker9000Subsystem.indexerMotorControl(0);
  }
}
