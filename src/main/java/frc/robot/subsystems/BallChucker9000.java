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
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;

public class BallChucker9000 extends SubsystemBase {

    // PID
    private final PIDController rotatorPID;
    private final PIDController flywheelPID;

    // ESC declarations
    private final SpeedController rotatorMotor;
    private final SpeedController rightFlywheelMotor;
    private final SpeedController leftFlywheelMotor;
    private final SpeedController indexerMotor;
    
    // Encoder declarations
    private final Encoder rotatorEncoder;
    private final Encoder flywheelEncoder;

    // Piston declarations
    // private final Solenoid indexerPiston;

    // Limit switches
    private final DigitalInput rotatorAtZeroSwitch;


    // Class initializer
    public BallChucker9000(SpeedController rotatorController, SpeedController leftFlywheelController, SpeedController rightFlywheelController, 
                            SpeedController indexerController, int rotatorEncoderChannelA, int rotatorEncoderChannelB, int flywheelEncoderChannelA,
                            int flywheelEncoderChannelB, int rotatorAtZeroSwitchPort) {

        // PID
        rotatorPID = new PIDController(0.5, 0, 0.5);
        flywheelPID = new PIDController(0.5, 0, 0.5);

        // ESCs
        leftFlywheelMotor = leftFlywheelController;
        rightFlywheelMotor = rightFlywheelController;
        rotatorMotor = rotatorController;
        indexerMotor = indexerController;

        leftFlywheelMotor.setInverted(true);
        rightFlywheelMotor.setInverted(true);
        rotatorMotor.setInverted(true);
        indexerMotor.setInverted(true);

        // Encoders
        rotatorEncoder = new Encoder(rotatorEncoderChannelA, rotatorEncoderChannelB);
        flywheelEncoder = new Encoder(flywheelEncoderChannelA, flywheelEncoderChannelB);
        rotatorEncoder.setDistancePerPulse(15.0 * 360.0 / (50.0 * 124.0 * 1024.0));
        flywheelEncoder.setDistancePerPulse(1.0/1024.0);

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
        rightFlywheelMotor.set(power);
        leftFlywheelMotor.set(power);
    }

    public void flywheelPIDControl(double velocity) {
        flywheelMotorControl(flywheelPID.calculate(getFlywheelSpeed(), velocity));
    }

    public void indexerMotorControl(double power) {
        indexerMotor.set(power);
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