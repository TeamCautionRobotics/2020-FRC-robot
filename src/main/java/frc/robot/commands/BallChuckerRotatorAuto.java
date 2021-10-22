package frc.robot.commands;

import frc.robot.subsystems.BallChuckerRotator;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.misc2020.LimelightData;

public class BallChuckerRotatorAuto extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private BallChuckerRotator ballChucker;
  private LimelightData limelight;

  private double tV;  // target valid
  private double tX;  // target x offset

  public boolean locked;

  private boolean searchDir = false;
  private double currentAngle;
  private double targetAngle;
  private double offsetAngle = 4;

  private Timer seekTimer;
  private boolean seekTimerRunning = false;

  private Timer targetLostDelay;
  private boolean targetLostDelayExpired = false;
  private boolean targetLostTimerRunning = false;

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

    seekTimer = new Timer();
    targetLostDelay = new Timer();

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

      ballChucker.setPidP(0.04);
      ballChucker.setPidI(0.031);
      seekTimerRunning = false;
      seekTimer.stop();
      targetLostDelayExpired = false;

      // rotator 0deg is shooter pointing left
      // rotator 180deg is shooter pointing right
      // rotator 90deg is shooter pointing straight
      
      // limelight fov is -27deg/27deg from left to right
      // current angle + target offset = target angle

      currentAngle = ballChucker.getEncoderDistance();
      targetAngle = currentAngle + tX + offsetAngle;
      ballChucker.setRotatorPosition(targetAngle);

      SmartDashboard.putNumber("tX", tX);
      // Are we locked?
      if (-6.0 < tX && tX < -3.0) {
        locked = true;
      } else {
        locked = false;
      }

      SmartDashboard.putBoolean("locked", locked);

    } else {  // no target mode

      if (targetLostDelayExpired) {

        if (!seekTimerRunning) {
          seekTimer.reset();
          seekTimer.start();
          seekTimerRunning = true;
        }

        ballChucker.setPidP(0.01);
        ballChucker.setPidI(0.010);

        // search between 15 and 165 deg

        if (searchDir) {
          ballChucker.setRotatorPosition(110);
        } else {
          ballChucker.setRotatorPosition(10);
        }


        if (seekTimer.get() > 1.3) {
          searchDir = !searchDir;
          seekTimer.reset();
        }
      } else {

        if (!targetLostTimerRunning) {
          targetLostTimerRunning = true;
          targetLostDelay.reset();
          targetLostDelay.start();
        }

        if (targetLostDelay.get() > 1) {
          targetLostDelayExpired = true;
          targetLostDelay.stop();
          targetLostDelay.reset();
          targetLostTimerRunning = false;
        }
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
