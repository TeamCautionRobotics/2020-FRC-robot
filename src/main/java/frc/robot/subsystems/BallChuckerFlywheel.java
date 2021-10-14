package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class BallChuckerFlywheel extends SubsystemBase {

    private final SpeedControllerGroup flywheelMotors;
    private final Encoder flywheelEncoder;

    private PIDController flywheelPid;

    private boolean pidActive = false;
    private double pidSetpoint = 500.0 / 60.0;  // default to 500 rpm
    private double pidResult;

    // TODO: put us (editable) on smartdashboard
    public double pidP = 0.5;
    public double pidI = 0.8;
    public double pidD = 0.0;

    public BallChuckerFlywheel(SpeedControllerGroup flywheelMotorsObj, Encoder flywheelEncoderObj) {

        this.flywheelMotors = flywheelMotorsObj;
        this.flywheelEncoder = flywheelEncoderObj;

        flywheelPid = new PIDController(pidP, pidI, pidD);
        flywheelPid.setTolerance(100.0/60.0);  /// 100 rpm error

        flywheelEncoder.setDistancePerPulse(1.0/1024.0);

    }

    // raw power setter
    public void setPower(double power) {
        if (!pidActive) {
            flywheelMotors.set(power);
        }
    }
    
    public void enablePid(boolean enable) {
 
        // reset to prevent wackiness from a pid that hasn't been getting
        // where it wants for a while
        if (!pidActive && enable) {
            flywheelPid.reset();
        }
        pidActive = enable;
    }

    public void setSpeed(double speed) {

        // max flywheel speed is 11000 rpm
        speed = MathUtil.clamp(speed, 0.0, 11000.0);

        // rpm to rps
        pidSetpoint = speed / 60.0;
    }

    public void stop() {
        pidActive = false;
        flywheelMotors.set(0);
        flywheelMotors.stopMotor();
    }

    public double getSpeed() {
        return flywheelEncoder.getRate();
    }

    public boolean getPidAtSetpoint() {
        return flywheelPid.atSetpoint();
    }

    @Override
    public void periodic() {

        // always calculate the pid result - wackiness may ensue if this does not happen
        pidResult = flywheelPid.calculate(this.getSpeed(), pidSetpoint);

        if (pidActive) {

            /// clamp the pid output range to -1.0/+1.0
            pidResult = MathUtil.clamp(pidResult, -1.0, 1.0);

            // set the motor with the clamped pid result
            flywheelMotors.set(pidResult);

        }
    }
}