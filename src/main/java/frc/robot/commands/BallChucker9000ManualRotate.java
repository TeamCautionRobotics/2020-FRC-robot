package frc.robot.commands;

import frc.robot.subsystems.BallChucker9000;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class BallChucker9000ManualRotate extends InstantCommand {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final BallChucker9000 BallChucker9000Subsystem;
  private boolean rotateLeft;
  private double rotatorPosition;

  /**
   * Creates a new BallChucker9000ManualRotate.
   *
   * @param subsystem The subsystem used by this command.
   */
  public BallChucker9000ManualRotate(BallChucker9000 subsystem, boolean rotateLeft) {
    BallChucker9000Subsystem = subsystem;
    addRequirements(BallChucker9000Subsystem);

    this.rotateLeft = rotateLeft;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {

    // Prevent rotating if we're at rotational limits or the switch is pressed
    rotatorPosition = BallChucker9000Subsystem.getRotatorDistance();
    if (rotatorPosition <= -120.0 || rotatorPosition >= 120.0 || !BallChucker9000Subsystem.getRotatorAtZeroSwitch()) {
      BallChucker9000Subsystem.rotatorMotorControl(0);
    } else {
      if (this.rotateLeft) {
        BallChucker9000Subsystem.rotatorMotorControl(-0.25);
      } else {
        BallChucker9000Subsystem.rotatorMotorControl(0.25);
      }
     }

  }

  @Override
  public void end(boolean interrupted) {
    BallChucker9000Subsystem.rotatorMotorControl(0.0);
  }
}
