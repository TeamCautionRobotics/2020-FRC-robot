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
    public BallChucker9000(SpeedController rotatorMotor, SpeedController leftFlywheelMotor, SpeedController rightFlywheelMotor, 
                            SpeedController indexerMotor, int rotatorEncoderChannelA, int rotatorEncoderChannelB, int flywheelEncoderChannelA,
                            int flywheelEncoderChannelB, int rotatorAtZeroSwitchPort) {

        // PID
        rotatorPID = new PIDController(0.5, 0, 0.5);
        flywheelPID = new PIDController(0.5, 0, 0.5);

        // ESCs
        this.leftFlywheelMotor = leftFlywheelMotor;
        this.rightFlywheelMotor = rightFlywheelMotor;
        this.rotatorMotor = rotatorMotor;
        this.indexerMotor = indexerMotor;

        this.leftFlywheelMotor.setInverted(true);
        this.rightFlywheelMotor.setInverted(true);
        this.rotatorMotor.setInverted(true);
        this.indexerMotor.setInverted(true);

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
            this.rotatorMotor.set(power);
        } else {
            this.rotatorMotor.set(0.0);
        }
    }

    public void rotatorPIDControl(double setPoint) {
        rotatorMotorControl(rotatorPID.calculate(getRotatorAngle(), setPoint));
    }

    public void flywheelMotorControl(double power) {
        this.rightFlywheelMotor.set(0.1);
        this.leftFlywheelMotor.set(0.1);
    }

    public void flywheelPIDControl(double velocity) {
        flywheelMotorControl(flywheelPID.calculate(getFlywheelSpeed(), velocity));
    }

    public void indexerMotorControl(double power) {
        this.indexerMotor.set(power);
    }


    // Getters
    public double getFlywheelSpeed() {
        return flywheelEncoder.getRate();
    }

    public double getRotatorAngle() {
        return rotatorEncoder.getDistance() + 120;
    }

    // Limit switch 
    public boolean getRotatorAtZeroSwitch() {
        return !rotatorAtZeroSwitch.get();
    }

}