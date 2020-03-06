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

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class BallChucker9000 {

    // ESC declarations
    private final SpeedController leftFlywheelMotor;
    private final SpeedController rightFlywheelMotor;
    private final SpeedController rotatorMotor;
    private final SpeedController indexerMotor;

    // Encoder declarations
    private final Encoder rotatorEncoder;
    private final Encoder flywheelEncoder;

    // Limit switches
    private final DigitalInput rotatorAtZeroSwitch;
    private final DigitalInput rotatorAtMaxSwitch;

    // Class initializer
    public BallChucker9000(SpeedController leftFlywheelMotor, SpeedController rightFlywheelMotor,
            SpeedController rotatorMotor, SpeedController indexerMotor, int rotatorEncoderChannelA,
            int rotatorEncoderChannelB, int flywheelEncoderChannelA, int flywheelEncoderChannelB,
            int rotatorAtZeroSwitchPort, int rotatorAtMaxSwitchPort) {

        // ESCs
        this.leftFlywheelMotor = leftFlywheelMotor;
        this.rightFlywheelMotor = rightFlywheelMotor;
        this.rotatorMotor = rotatorMotor;
        this.indexerMotor = indexerMotor;

        // Encoders
        rotatorEncoder = new Encoder(rotatorEncoderChannelA, rotatorEncoderChannelB);
        flywheelEncoder = new Encoder(flywheelEncoderChannelA, flywheelEncoderChannelB);
        rotatorEncoder.setDistancePerPulse(1.0 / 1024.0);
        flywheelEncoder.setDistancePerPulse(1.0 / 1024.0);

        // Limit switches
        rotatorAtZeroSwitch = new DigitalInput(rotatorAtZeroSwitchPort);
        rotatorAtMaxSwitch = new DigitalInput(rotatorAtMaxSwitchPort);
    }

    // Reset the encoder to zero when called
    public void update() {
        if (getRotatorAtZeroSwitch()) {
            rotatorEncoder.reset();
        }
    }

    // Setters

    // ESC
    public void rotatorMotorControl(double power) {
        rotatorMotor.set(power);
    }

    public void flywheelMotorControl(double power) {
        leftFlywheelMotor.set(power);
        rightFlywheelMotor.set(-power);
    }

    public void indexerMotorControl(double power) {
        indexerMotor.set(power);
    }

    // Getters
    public double getFlywheelEncoder() {
        return flywheelEncoder.getRate();
    }

    // Limit switches
    public boolean getRotatorAtZeroSwitch() {
        return !rotatorAtZeroSwitch.get();
    }

    public boolean getRotatorAtMaxSwitch() {
        return !rotatorAtMaxSwitch.get();
    }
}