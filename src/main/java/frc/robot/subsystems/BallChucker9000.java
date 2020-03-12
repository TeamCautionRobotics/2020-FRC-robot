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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.controller.PIDController;

public class BallChucker9000 extends SubsystemBase {

    // PID
    private final PIDController rotatorPID;
    private final PIDController flywheelPID;

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
    public BallChucker9000(VictorSP rotatorController, VictorSP flywheelController, VictorSP indexerController,
                            int rotatorEncoderChannelA, int rotatorEncoderChannelB, int flywheelEncoderChannelA,
                            int flywheelEncoderChannelB, int indexerPistonPort, int rotatorAtZeroSwitchPort) {

        // public BallChucker9000(int flywheelMotorPort, int rotatorMotorPort, int indexerMotorPort, int rotatorEncoderChannelA, 
        // int rotatorEncoderChannelB, int flywheelEncoderChannelA, int flywheelEncoderChannelB, 
        // int indexerPistonPort, int rotatorAtZeroSwitchPort) {

        // PID
        rotatorPID = new PIDController(0.5, 0, 0.5);
        flywheelPID = new PIDController(0.5, 0, 0.5);

        // ESCs
        flywheelMotor = rotatorController;
        rotatorMotor = flywheelController;
        indexerMotor = indexerController;

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
    }

    // Setters

    // ESC
    public void rotatorMotorControl(double power) {
        if (getRotatorAtZeroSwitch()) { // Only set power if the limit switch isn't pressed
            rotatorMotor.set(power);
        } else {
            rotatorMotor.set(0.0);
        }
    }

    public void rotatorPIDControl(double setPoint) {
        rotatorMotorControl(rotatorPID.calculate(getRotatorDistance(), setPoint));
    }

    public void flywheelMotorControl(double power) {
        flywheelMotor.set(power);
    }

    public void flywheelPIDControl(double velocity) {
        flywheelMotorControl(flywheelPID.calculate(getFlywheelSpeed(), velocity));
    }

    public void indexerMotorControl(double power) {
        indexerMotor.set(power);
    }

    // Pistons
    public void indexerPistonOut(boolean state) {
        indexerPiston.set(state);
    }


    // Getters
    public double getFlywheelSpeed() {
        return flywheelEncoder.getRate();
    }

    public double getRotatorDistance() {
        return rotatorEncoder.getDistance();
    }

    // Limit switch 
    public boolean getRotatorAtZeroSwitch() {
        return !rotatorAtZeroSwitch.get();
    }

}