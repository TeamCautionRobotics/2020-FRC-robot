/*
     Rotator:
      Motor - Positive for clockwise, negative for counterclockwise
      Encoder with offset
      Limit Switch - False for closed, true for open
     
     Flywheel:
      Motor - Positive will eject the ball
      Encoder
     
      Indexer
       Motor - Positive will push balls toward the flywheel
       Piston - True for extended, false for retracted

      - - - - - - - - - - - - - - - - - - - - - - -

      Professionally chucking balls since 2020
*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallChucker9000 extends SubsystemBase {

    // Limelight
    private final NetworkTable limelightData = NetworkTableInstance.getDefault().getTable("limelight");

    private NetworkTableEntry tx = limelightData.getEntry("tx"); // X Offset from crosshair
    private NetworkTableEntry ta = limelightData.getEntry("ta"); // Target's size
    private NetworkTableEntry tv = limelightData.getEntry("tv"); // targets available

    private boolean autoAim = true;
    private double targetXOffset;
    private double targetSize;
    private boolean targetAvailable;

    // ESC declarations
    private final VictorSP rotatorMotor;
    private final VictorSP flywheelMotor;
    private final VictorSP indexerMotor;
    
    // Encoder declarations
    private final Encoder rotatorEncoder;
    private final Encoder flywheelEncoder;

    // Piston declarations
    private final Solenoid indexerPiston;

    // Limit switches
    private final DigitalInput rotatorAtZeroSwitch;


    // Class initializer
    public BallChucker9000(int flywheelMotorPort, int rotatorMotorPort, int indexerMotorPort, int rotatorEncoderChannelA, 
                            int rotatorEncoderChannelB, int flywheelEncoderChannelA, int flywheelEncoderChannelB, 
                            int indexerPistonPort, int rotatorAtZeroSwitchPort) {

        // ESCs
        flywheelMotor = new VictorSP(flywheelMotorPort);
        rotatorMotor = new VictorSP(rotatorMotorPort);
        indexerMotor = new VictorSP(indexerMotorPort);

        // Encoders
        rotatorEncoder = new Encoder(rotatorEncoderChannelA, rotatorEncoderChannelB);
        flywheelEncoder = new Encoder(flywheelEncoderChannelA, flywheelEncoderChannelB);
        rotatorEncoder.setDistancePerPulse(1.0/1024.0);
        flywheelEncoder.setDistancePerPulse(1.0/1024.0);

        // Piston
        indexerPiston = new Solenoid(indexerPistonPort);

        //Limit switches
        rotatorAtZeroSwitch = new DigitalInput(rotatorAtZeroSwitchPort);

    }

    // Reset the encoder to zero, read Limelight data
    @Override
    public void periodic() {
        if (getRotatorAtZeroSwitch()) {
            rotatorEncoder.reset();
        }

        targetXOffset = tx.getDouble(0.0);
        targetSize = ta.getDouble(0.0);
        targetAvailable = tv.getBoolean(false);

        if (autoAim) { // Automatically aim if enabled
            if (targetAvailable) { // If we see a target (Lock state)
                // Negative targetXOffset rotates left, positive rotates right


            } else { // If we don't see a target (Search state)
                // Rotate back and forth until we find a target


            }
        }

        SmartDashboard.putNumber("Limelight X Offset", targetXOffset);
        SmartDashboard.putNumber("Limelight Target Area", targetSize);
        SmartDashboard.putBoolean("Limelight Target Available", targetAvailable);
    }

    // Setters

    // ESC
    public void rotatorMotorControl(double power) {
        rotatorMotor.set(power);
    }

    public void flywheelMotorControl(double power) {
        flywheelMotor.set(power);
    }

    public void indexerMotorControl(double power) {
        indexerMotor.set(power);
    }

    // Pistons
    public void indexerPistonOut(boolean state) {
        indexerPiston.set(state);
    }

    public void enableAutomaticAim(boolean state) {
        autoAim = state;
    }


    // Getters
    public double getFlywheelEncoder() {
        return flywheelEncoder.getRate();
    }

    // Limit switch 
    public boolean getRotatorAtZeroSwitch() {
        return !rotatorAtZeroSwitch.get();
    }

}