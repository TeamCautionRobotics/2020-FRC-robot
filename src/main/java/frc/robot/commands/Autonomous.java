package frc.robot.commands;

import frc.robot.subsystems.BallChuckerIndexer;
import frc.robot.subsystems.BallTransfer;
import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Autonomous extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private final DriveBase driveBase;
  private final BallChuckerIndexer indexer;
  private final BallTransfer elevator;

  private boolean rotatorLocked;
  private boolean flywheelLocked;

  private Timer shootBallTimer;
  private boolean timerRunning;

  private boolean ballsShot = false;

  private boolean autoDone = false;

  public Autonomous(DriveBase driveBase, BallChuckerIndexer indexer, BallTransfer elevator, boolean rotatorLocked, boolean flywheelLocked) {

    this.driveBase = driveBase;
    this.indexer = indexer;
    this.elevator = elevator;

    this.rotatorLocked = rotatorLocked;
    this.flywheelLocked = flywheelLocked;

    addRequirements(driveBase, indexer, elevator);
  }

  @Override
  public void initialize() {

    // reset encoders
    driveBase.resetEncoders();

    // drive forward 3 feet
    while (driveBase.getDistance() < 36) {
      driveBase.drive(0.75);
    } 

    // stop
    driveBase.drive(0);
    shootBallTimer.reset();

  }

  @Override
  public void execute() {

    if (rotatorLocked && flywheelLocked && !ballsShot) {  // locked and balls have not been shot, go time
      if (!timerRunning) {
        timerRunning = true;
        shootBallTimer.start();
      }

      driveBase.drive(0);
      indexer.setPower(0.7);
      elevator.moveBalls(0.7);

      if (shootBallTimer.get() > 5) {  // shoot for 5s
        indexer.setPower(0);
        elevator.moveBalls(0);
        ballsShot = true;
      }

    } else if (rotatorLocked && !flywheelLocked && !ballsShot) {  // flywheel has slowed, wait for it to spin up again
      
      driveBase.drive(0);
      indexer.setPower(0);
      elevator.moveBalls(0);

    } else if (!rotatorLocked && !ballsShot) {  // no lock and balls haven't been shot, rotate in place until we get lock
      driveBase.drive(-0.2, 0.2);
    } else if (ballsShot){  // balls have been shot. stop everything
      indexer.setPower(0);
      elevator.moveBalls(0);
      driveBase.drive(0);
      autoDone = true;
    }

  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return autoDone;
  }
}
