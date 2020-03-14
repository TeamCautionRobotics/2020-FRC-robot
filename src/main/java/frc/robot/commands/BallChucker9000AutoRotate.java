package frc.robot.commands;

import frc.robot.subsystems.BallChucker9000;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class BallChucker9000AutoRotate extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final BallChucker9000 BallChucker9000Subsystem;

  private final NetworkTable limelightData = NetworkTableInstance.getDefault().getTable("limelight");

  private NetworkTableEntry tx = limelightData.getEntry("tx"); // X Offset from crosshair
  private NetworkTableEntry ta = limelightData.getEntry("ta"); // Target's size
  private NetworkTableEntry tv = limelightData.getEntry("tv"); // Target available

  private NetworkTableEntry ledMode = limelightData.getEntry("ledMode"); // Limelight's LED mode

  private double targetXOffset;
  private double targetSize;
  private boolean targetAvailable;

  private double rotatorPosition;
  private double rotatorDestination;

  /**
   * Creates command to make the BallChucker9000 automatically target
   *
   * @param subsystem The subsystem used by this command.
   */
  public BallChucker9000AutoRotate(BallChucker9000 subsystem) {
    BallChucker9000Subsystem = subsystem;
    addRequirements(BallChucker9000Subsystem);
  }

  @Override
  public void initialize() {

      ledMode.setNumber(3); // Turn the LEDs on

  }

  @Override
  public void execute() {

      // Read data
      targetXOffset = tx.getDouble(0.0);
      targetSize = ta.getDouble(0.0);
      targetAvailable = tv.getBoolean(false);

      // Show data (can be removed)
      SmartDashboard.putNumber("Limelight X Offset", targetXOffset);
      SmartDashboard.putNumber("Limelight Target Area", targetSize);
      SmartDashboard.putBoolean("Limelight Target Available", targetAvailable);

      if (targetAvailable) { // If a target is detected (Lock state)
        
        // get the encoder
        rotatorPosition = BallChucker9000Subsystem.getRotatorDistance();

        // Set the destnation to the current position to bypass the 
        // search check the first time it runs
        rotatorDestination = Math.round(rotatorPosition);

        // Move the rotator to the detected position including offset
        BallChucker9000Subsystem.rotatorPIDControl(targetXOffset + rotatorPosition);

      } else { // If no target is detected (Search state)

        // get the encoder and round it
        rotatorPosition = Math.round(BallChucker9000Subsystem.getRotatorDistance());
        
        if (rotatorDestination == rotatorPosition) { // If we've reached our intended destination, check how we move

          if (rotatorPosition >= 180) {

            // Rotator is at max right rotation, go to max left rotation
            BallChucker9000Subsystem.rotatorPIDControl(0);
            rotatorDestination = 0;

          } else if (rotatorPosition >= 90) {

            // Rotator is above or equal to center, go to max right rotation
            BallChucker9000Subsystem.rotatorPIDControl(180);
            rotatorDestination = 180;

          } else if (rotatorPosition <= 0) {

            // Rotator is at max left rotation, go to max right rotation
            BallChucker9000Subsystem.rotatorPIDControl(180);
            rotatorDestination = 180;

          } else if (rotatorPosition < 90) {

            // Rotator is below center, go to max left rotation
            BallChucker9000Subsystem.rotatorPIDControl(0);
            rotatorDestination = 0;

          } else {

            // Uh oh! Hopefully we didn't break anything!
            // (go to center)
            BallChucker9000Subsystem.rotatorPIDControl(90);
            rotatorDestination = 0;

          } 

        }
        
      }

  }

  @Override
  public void end(boolean interrupted) {
    BallChucker9000Subsystem.rotatorPIDControl(0); // Reset to resting position
    ledMode.setNumber(1); // Turn the LEDs off
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
