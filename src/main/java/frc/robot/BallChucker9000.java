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
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class BallChucker9000 {

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

    // Reset the encoder to zero when called
    // The offset value may be a nonzero value, see line 45
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
        flywheelMotor.set(power);
    }

    public void indexerMotorControl(double power) {
        indexerMotor.set(power);
    }

    // Pistons
    public void indexerPistonOut(boolean state) {
        indexerPiston.set(state);
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