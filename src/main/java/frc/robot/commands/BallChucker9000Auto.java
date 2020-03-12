/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.BallChucker9000;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class BallChucker9000Auto extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final BallChucker9000 BallChucker9000Subsystem;

  private final PIDController rotatorPID;
  private final PIDController flywheelPID;

  private final NetworkTable limelightData = NetworkTableInstance.getDefault().getTable("limelight");

  private NetworkTableEntry tx = limelightData.getEntry("tx"); // X Offset from crosshair
  private NetworkTableEntry ta = limelightData.getEntry("ta"); // Target's size
  private NetworkTableEntry tv = limelightData.getEntry("tv"); // Target available

  private NetworkTableEntry ledMode = limelightData.getEntry("ledMode"); // Limelight's LED mode

  private double targetXOffset;
  private double targetSize;
  private boolean targetAvailable;

  /**
   * Creates command to make the BallChucker9000 automatically target
   *
   * @param subsystem The subsystem used by this command.
   */
  public BallChucker9000Auto(BallChucker9000 subsystem) {
    BallChucker9000Subsystem = subsystem;
    addRequirements(BallChucker9000Subsystem);

    // PID Controllers
    rotatorPID = new PIDController(0, 0, 0);
    flywheelPID = new PIDController(0, 0, 0);
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

        // BASIC NON-PID ROTATOR CONTROL

        if (0.5 > targetXOffset && targetXOffset > -0.5) { // If we're within 0.5* of target, stop
          BallChucker9000Subsystem.rotatorMotorControl(0.0);
        } else if (targetXOffset > 0.0) { // If the target is to the right, move right
          BallChucker9000Subsystem.rotatorMotorControl(0.1);
        } else if (targetXOffset < 0.0) { // If the target is to the left, move left
          BallChucker9000Subsystem.rotatorMotorControl(-0.1);
        } else { // We should never get here, but if we do, stop the motor as a failsafe
          BallChucker9000Subsystem.rotatorMotorControl(0.0);
        }

        // Use the targetSize value to calculate the flywheel's speed with a PID controller

      } else { // If no target is detected (Search state)

        // Do nothing for now

      }

  }

  @Override
  public void end(boolean interrupted) {
    // Reset the shooter to a resting postion (+/- 90*) ?

    ledMode.setNumber(1); // Turn the LEDs off

  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
