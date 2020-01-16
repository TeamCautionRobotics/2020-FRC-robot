/*
    This class includes:

     Rotator:
      Motor
      Encoder
      Limit Switch
     
      Flywheel:
       Motor
       Encoder

      Indexer Wheel

      Indexer Piston

      - - - - - - - - - - - - - - - - - - - - - - -

      Professionally blasting your balls since 2020
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
    private final DigitalInput rotatorSwitch;


    // Class initializer
    public BallChucker9000(int flywheelMotorPort, int rotatorMotorPort, int indexerMotorPort, int rotatorEncoderChannelA, 
                            int rotatorEncoderChannelB, int flywheelEncoderChannelA, int flywheelEncoderChannelB, 
                            int indexerPistonPort, int rotatorSwitchPort) {
    
        // ESCs
        flywheelMotor = new VictorSP(flywheelMotorPort);
        rotatorMotor = new VictorSP(rotatorMotorPort);
        indexerMotor = new VictorSP(indexerMotorPort);

        // Encoders
        rotatorEncoder = new Encoder(rotatorEncoderChannelA, rotatorEncoderChannelB);
        flywheelEncoder = new Encoder(flywheelEncoderChannelA, flywheelEncoderChannelB);
        rotatorEncoder.setDistancePerPulse(1/1024);
        flywheelEncoder.setDistancePerPulse(1/1024);

        // Piston
        indexerPiston = new Solenoid(indexerPistonPort);

        //Limit switches
        rotatorSwitch = new DigitalInput(rotatorSwitchPort);

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
    public void indexerPistonControl(boolean state) {
        indexerPiston.set(state);
    }


    // Getters

    // Encoder
    public double getRotatorEncoder() {
        return rotatorEncoder.get();
    }

    public double getFlywheelEncoder() {
        return flywheelEncoder.get();
    }

    // Limit switch 
    public boolean getRotatorSwitch() {
        return rotatorSwitch.get();
    }

}