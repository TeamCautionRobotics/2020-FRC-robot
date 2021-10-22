package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoDriveThreeFeet extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private final DriveBase driveBase;
  private boolean autoDone = false;

  public AutoDriveThreeFeet(DriveBase driveBase) {

    this.driveBase = driveBase;

    addRequirements(driveBase);
  }

  @Override
  public void initialize() {
    autoDone = false;

    // reset encoders
    driveBase.resetEncoders();

    // drive forward 3 feet
    while (driveBase.getDistance() < 36) {
      driveBase.drive(0.75);
    } 

    // stop
    driveBase.drive(0);
    autoDone = true;
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return autoDone;
  }
}
