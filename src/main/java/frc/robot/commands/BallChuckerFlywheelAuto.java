package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  // Angles in deg, distance in inches
  private double a1 = 10.6;  // Angle between ground and camera lens
  private double h1 = 34.75;  // Distance between camera lens and ground
  private double h2 = 98.25-13.5;  // Distance between center of target and ground

  public double distance;

  private double desiredRps;

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
    ballChucker.setSpeed(0);

  }

  @Override
  public void execute() {

    // update limelight vars
    tV = limelight.getTv();
    tY = limelight.getTy();

    if (tV == 1) {  // if we have a target

      // Distance in inch
      distance = (h2 - h1) / Math.tan(Math.PI*(a1 + tY) / 180.0);
      SmartDashboard.putNumber("Limelight Calculated Distance:", distance);

      desiredRps = Math.pow(0.000003516428755144 * distance, 3) - Math.pow(0.0066848566503 * distance, 2) + (0.339064673194 * distance) + 45.678336060798;

      SmartDashboard.putNumber("flywheel desired rps", desiredRps);
      SmartDashboard.putNumber("flywheel actual rps", ballChucker.getSpeed());

      ballChucker.setSpeed(desiredRps);

      if ((desiredRps - 5) < ballChucker.getSpeed() && ballChucker.getSpeed() < (desiredRps + 5)) {
        ballChucker.setLocked(true);
      } else {
        ballChucker.setLocked(false);
      }

    } else {  // no target, set half speed
      ballChucker.setSpeed(0);
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
