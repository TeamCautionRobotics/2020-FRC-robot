package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.misc2020.LimelightData;
import frc.robot.subsystems.BallChuckerFlywheel;

public class BallChuckerFlywheelAuto extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private final BallChuckerFlywheel ballChucker;
  private LimelightData limelight;

  private double tV;  // target valid
  private double tY;  // target area

  // TODO: SATURDAY - set us!
  // Distance measurement calculation vars
  // Angles in deg, distance in feet
  private double a1 = 0.0;  // Angle between ground and camera lens
  private double h1 = 0.0;  // Distance between camera lens and ground

  private double h2 = 8.2021;  // Distance between center of target and ground

  // TODO: put me on smartdashboard
  public double distance;

  private double desiredRpm;

  private boolean locked = false;

  /**
   * Creates a new BallChuckerFlywheelAuto
   *
   * @param subsystem The subsystem used by this command.
   * @param limelightObj Pass an instance of LimelightData. 
   */
  public BallChuckerFlywheelAuto(BallChuckerFlywheel subsystem, LimelightData limelightObj, boolean lockedObj) {

    this.ballChucker = subsystem;
    this.limelight = limelightObj;
    this.locked = lockedObj;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

    // enable pid
    ballChucker.enablePid(true);
    ballChucker.setSpeed(5500.0);

  }

  @Override
  public void execute() {

    // update limelight vars
    tV = limelight.getTv();
    tY = limelight.getTy();

    if (tV == 1) {  // if we have a target

      // Distance in feet
      distance = (h2 - h1) / Math.tan(a1 + tY);

      // TODO: SATURDAY - find the equation for this
      desiredRpm = 0.0;

      ballChucker.setSpeed(desiredRpm);

      if (ballChucker.getPidAtSetpoint()) {
        locked = true;
      } else {
        locked = false;
      }

    } else {  // no target, set half speed
      ballChucker.setSpeed(5500.0);
    }

  }

  @Override
  public void end(boolean interrupted) {
    ballChucker.stop();
    ballChucker.enablePid(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
