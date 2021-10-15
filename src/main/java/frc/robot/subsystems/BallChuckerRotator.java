package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class BallChuckerRotator extends SubsystemBase {

    private final SpeedController rotatorMotor;
    private final Encoder rotatorEncoder;
    private final DigitalInput rotatorSwitch;

    private PIDController rotatorPid;

    private boolean pidActive = false;
    private double pidSetpoint = 90.0;
    private double pidResult;

    // TODO: put us (editable) on smartdash
    // remove & hardcode when initial setup done
    public double resetMovePwr = 0.1;
    public double pidP = 0.7;
    public double pidI = 0.0;
    public double pidD = 0.25;
    public double rotatorMovementLimitLow = 10.0;
    public double rotatorMovementLimitHigh = 170.0;

    public BallChuckerRotator(SpeedController rotatorMotorObj, Encoder rotatorEncoderObj, DigitalInput rotatorSwitchObj) {

        this.rotatorMotor = rotatorMotorObj;
        this.rotatorEncoder = rotatorEncoderObj;
        this.rotatorSwitch = rotatorSwitchObj;

        PIDController rotatorPid = new PIDController(pidP, pidI, pidD);
        // no integrator for 1st impl
        rotatorPid.setIntegratorRange(0, 0);
        // set tolerance to 3 deg error
        rotatorPid.setTolerance(3.0);

        // TODO: set proper distanceperpulse - should be 1 revolution = 360 degrees
        // JUST NEED GEAR RATIO 
        rotatorEncoder.setDistancePerPulse(360.0 / 1024.0);

        // home up before use
        this.fullReset();

    }

    public void fullReset() {
        // Slowly turn rotator until we hit the switch

        rotatorMotor.set(resetMovePwr);
        if (this.getRotatorSwitch()) {
            rotatorMotor.set(0.0);
        }
        // Then reset
        rotatorEncoder.reset();
        rotatorPid.reset();

        // send rotator to center
        pidSetpoint = 90.0;
        pidActive = true;

    }

    // Directly sets rotator power
    public void setRotatorMotor(double power) {
        if (!pidActive) {

            double powerCorrected;

            // prevent moving past limit
            if (this.getEncoderDistance() < rotatorMovementLimitLow) {
                powerCorrected = MathUtil.clamp(power, 0.0, 9999.0);
            } else if (this.getEncoderDistance() > rotatorMovementLimitHigh) {
                powerCorrected = MathUtil.clamp(power, -9999.0, 0.0); 
            } else {
                powerCorrected = power;
            }

            rotatorMotor.set(powerCorrected);
        }
    }

    public void stop() {
        rotatorMotor.set(0);
        rotatorMotor.stopMotor();
    }

    public void enablePid(boolean enable) {
        // reset to prevent wackiness from a pid that hasn't been getting
        // where it wants for a while
        if (!pidActive && enable) {
            rotatorPid.reset();
        }
        pidActive = enable;
    }

    // Sets rotator pid postion
    // clamped to min and max spec'd pos
    public void setRotatorPosition(double degrees) {
        pidSetpoint = MathUtil.clamp(degrees, rotatorMovementLimitLow, rotatorMovementLimitHigh);
    }

    // true if pid has found and reached a target
    public boolean getPidAtSetpoint() {
        return rotatorPid.atSetpoint();
    }

    public double getEncoderRate() {
        return rotatorEncoder.getRate();
    }

    // TODO: put me on smartdash!
    public double getEncoderDistance() {
        return rotatorEncoder.getDistance();
    }

    public boolean getRotatorSwitch() {
        // ROTATOR SWITCH IS INVERTED ON BOT!
        return !rotatorSwitch.get();
    }

    @Override
    public void periodic() {

        // -------------------------------------------------------------------------
        // SAFETY WHILE ENCODER IS NOT PROPERLY CALIBRATED!!!!!!
        // DO NOT REMOVE THE LINE BELOW UNTIL IT IS VERIFIED TO BE GOOD!!!!!!!!!!!
        pidActive = false;
        // DAMAGE MAY RESULT!
        // -------------------------------------------------------------------------

        // always calculate the pid result - wackiness may ensue if this does not happen
        pidResult = rotatorPid.calculate(this.getEncoderDistance(), pidSetpoint);

        // apply pid power to motor when pid is active
        if (pidActive) {

            // clamp the pid output range to -1.0/+1.0
            pidResult = MathUtil.clamp(pidResult, -1.0, 1.0);

            // set the motor with the clamped pid result
            rotatorMotor.set(pidResult);
        }
    }
} 