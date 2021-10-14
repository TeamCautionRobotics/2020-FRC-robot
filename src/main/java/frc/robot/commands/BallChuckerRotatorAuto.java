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

  private boolean searchDir = false;
  private double currentAngle;
  private double targetAngle;

  /**
   * Creates a new BallChuckerRotatorAuto command
   * This uses the Limelight to automatically aim the turret
   *
   * @param subsystem The subsystem used by this command.
   * @param limelightObj Pass an instance of LimelightData.
   */
  public BallChuckerRotatorAuto(BallChuckerRotator subsystem, LimelightData limelightObj) {
    ballChucker = subsystem;
    this.limelight = limelightObj;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

    // force lights on
    limelight.setLedMode(3);
    // enable the pid
    ballChucker.enablePid(true);

    // center up
    ballChucker.setRotatorPosition(90.0);
    while (!ballChucker.getPidAtSetpoint()) {
      ballChucker.setRotatorPosition(90.0);
    }

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

      // set position for pid
      // if pid is in range, turn it off
      // if we're too far out, re-enable, reset and set target position

      // if (ballChucker.getPidAtSetpoint()) {
      //   ballChucker.enablePid(false);
      //   ballChucker.stop();
      // } else {
      //   ballChucker.enablePid(true);
      //   ballChucker.setRotatorPosition(targetAngle);
      // }

      ballChucker.setRotatorPosition(targetAngle);


    } else {  // no target mode

      // TODO: SATURDAY - modify PID for a slower response here

      // search between 15 and 165 deg
      if (searchDir) {
        ballChucker.setRotatorPosition(165);
      } else {
        ballChucker.setRotatorPosition(15);
      }

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
