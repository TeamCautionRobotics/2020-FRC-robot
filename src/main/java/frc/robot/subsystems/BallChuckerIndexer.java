package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BallChuckerIndexer extends SubsystemBase {

  private final SpeedController indexerMotor;

  /**
   * Creates a new BallChuckerIndexer
   */
  public BallChuckerIndexer(SpeedController indexerMotorObj) {

    this.indexerMotor = indexerMotorObj;

  }

  public void setPower(double power) {
    indexerMotor.set(power);
  }

  public void stop() {
    indexerMotor.set(0);
    indexerMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
