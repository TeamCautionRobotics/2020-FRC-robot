package frc.robot.commands;

import frc.robot.subsystems.BallChuckerRotator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.misc2020.LimelightData;

public class BallChuckerRotatorAuto extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private BallChuckerRotator ballChucker;
  private LimelightData limelight;

  private double tV;  // target valid
  private double tX;  // target x offset

  private boolean locked = false;

  private boolean searchDir = false;
  private double currentAngle;
  private double targetAngle;

  /**
   * Creates a new BallChuckerRotatorAuto command
   * This uses the Limelight to automatically aim the turret
   *
   * @param subsystem The subsystem used by this command.
   * @param limelightObj Pass an instance of LimelightData.
   * @param lockedObj Pass a boolean to track if we're locked on target
   */
  public BallChuckerRotatorAuto(BallChuckerRotator subsystem, LimelightData limelightObj, boolean lockedObj) {
    ballChucker = subsystem;
    this.limelight = limelightObj;

    this.locked = lockedObj;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

    // force lights on
    limelight.setLedMode(3);
    // enable the pid
    ballChucker.enablePid(true);

  }

  @Override
  public void execute() {

    // refresh limelight vars
    tV = limelight.getTv();  // target available (0/1)
    tX = limelight.getTx();  // x offset

    // main targeting
    if (tV == 1) {  // target mode

      // rotator 0deg is shooter pointing left
      // rotator 180deg is shooter pointing right
      // rotator 90deg is shooter pointing straight
      
      // limelight fov is -27deg/27deg from left to right
      // current angle + target offset = target angle

      currentAngle = ballChucker.getEncoderDistance();
      targetAngle = currentAngle + tX;
      ballChucker.setRotatorPosition(targetAngle);

      // Are we locked?
      if (-0.5 < tX && tX < 0.5) {
        locked = true;
      } else {
        locked = false;
      }

    } else {  // no target mode

      // TODO: SATURDAY - modify PID for a slower response here

      // // search between 15 and 165 deg
      // if (searchDir) {
      //   ballChucker.setRotatorPosition(110);
      // } else {
      //   ballChucker.setRotatorPosition(10);
      // }

      // reverse the search dir if we're at the setpoint
      if (ballChucker.getPidAtSetpoint()) {
        searchDir = !searchDir;
      }
    }
  }

  @Override
  public void end(boolean interrupted) {

    // disable pid when command killed
    ballChucker.enablePid(false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
