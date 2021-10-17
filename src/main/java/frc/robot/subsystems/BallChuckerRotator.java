package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.misc2020.LimelightData;

public class BallChuckerRotator extends SubsystemBase {

    private final SpeedController rotatorMotor;
    private final Encoder rotatorEncoder;
    private final DigitalInput rotatorSwitch;

    private PIDController rotatorPid;

    private boolean pidActive = false;
    private double pidSetpoint;
    private double pidResult;

    // remove & hardcode when initial setup done
    public double resetMovePwr = -0.1;
    public double pidP = 0.04;
    public double pidI = 0.031;
    public double pidD = 0.0012;
    public double rotatorMovementLimitLow = 0.0;
    public double rotatorMovementLimitHigh = 120.0;
    public boolean forceDisablePid = false;

    private boolean homingSwitchHit = false;

    public BallChuckerRotator(SpeedController rotatorMotorObj, Encoder rotatorEncoderObj, DigitalInput rotatorSwitchObj) {

        this.rotatorMotor = rotatorMotorObj;
        this.rotatorEncoder = rotatorEncoderObj;
        this.rotatorSwitch = rotatorSwitchObj;

        this.rotatorMotor.setInverted(true);

        rotatorPid = new PIDController(pidP, pidI, pidD);
        rotatorPid.setIntegratorRange(-0.001, 0.001);

        // set tolerance to 3 deg error
        rotatorPid.setTolerance(2.0);
 
        rotatorEncoder.setDistancePerPulse(360.0 / ((124.0 / 15.0) * (50.0 * 1024.0)));
        rotatorEncoder.setReverseDirection(true);

        // home up before use
        this.fullReset();

    }

    public void fullReset() {
        // Slowly turn rotator until we hit the switch

        rotatorMotor.set(resetMovePwr);

        if (this.getRotatorSwitch()) {
            rotatorMotor.set(0.0);
            homingSwitchHit = true;

            // Then reset
            rotatorEncoder.reset();
            rotatorPid.reset();

            // send rotator to center
            pidSetpoint = 45.0;
            pidActive = true;

        }
    }

    // Directly sets rotator power
    public void setRotatorMotor(double power) {
        if (!pidActive) {

            double powerCorrected;

            // prevent moving past limit
            if (this.getEncoderDistance() < rotatorMovementLimitLow) {
                powerCorrected = MathUtil.clamp(power, -1.0, 0.0);
            } else if (this.getEncoderDistance() > rotatorMovementLimitHigh) {
                powerCorrected = MathUtil.clamp(power, 0.0, 1.0); 
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
        // stop motor when pid disabled
        if (!enable) {
            rotatorMotor.set(0);
        }
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

    public double getEncoderDistance() {
        return rotatorEncoder.getDistance();
    }

    public boolean getRotatorSwitch() {
        // ROTATOR SWITCH IS INVERTED ON BOT!
        return !rotatorSwitch.get();
    }

    @Override
    public void periodic() {

        // homing routine:
        if (!homingSwitchHit) {

            fullReset();

        } else {

            // -------------------------------------------------------------------------
            // SAFETY WHILE ENCODER IS NOT PROPERLY CALIBRATED!!!!!!
            // DO NOT REMOVE THE LINE BELOW UNTIL IT IS VERIFIED TO BE GOOD!!!!!!!!!!!
            if (forceDisablePid) {
                pidActive = false;
            }
            // DAMAGE MAY RESULT!
            // -------------------------------------------------------------------------

            // always calculate the pid result - wackiness may ensue if this does not happen
            pidResult = rotatorPid.calculate(this.getEncoderDistance(), pidSetpoint);

            // apply pid power to motor when pid is active
            if (pidActive) {

                // clamp the pid output range to -1.0/+1.0
                pidResult = MathUtil.clamp(pidResult, -0.5, 0.5);

                // set the motor with the clamped pid result
                rotatorMotor.set(pidResult);
            }

        }

        SmartDashboard.putNumber("Rotator Reset Movement Power", resetMovePwr);
        SmartDashboard.putNumber("Rotator P", pidP);
        SmartDashboard.putNumber("Rotator I", pidI);
        SmartDashboard.putNumber("Rotator D", pidD);
        SmartDashboard.putNumber("Rotator Limit Low", rotatorMovementLimitLow);
        SmartDashboard.putNumber("Rotator Limit High", rotatorMovementLimitHigh);
        SmartDashboard.putNumber("Rotator getDistance()", getEncoderDistance());
        SmartDashboard.putBoolean("Force Disable PID", forceDisablePid);

        SmartDashboard.putNumber("pid rotator setpoint", pidSetpoint);
        SmartDashboard.putNumber("pid rotator power", pidResult);

    }
} 