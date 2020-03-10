package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;

public class Climb {

    private final SpeedController winchMotor;
    private final SpeedController armMotor;

    private final Solenoid winchLockPiston;

    private final Encoder armEncoder;
    private final DigitalInput armLimitSwitch;

    public PIDController armPidController;

    public Climb(SpeedController winchMotor, SpeedController armMotor, int armLockPistonPort, int armEncoderPortA,
            int armEncoderPortB, int armLimitSwitchPort, double P, double I, double D) {
        this.winchMotor = winchMotor;
        this.armMotor = armMotor;
        winchLockPiston = new Solenoid(armLockPistonPort);

        armEncoder = new Encoder(armEncoderPortA, armEncoderPortB);
        armEncoder.setDistancePerPulse(360.0 / (100.0 * 1024.0));
        armLimitSwitch = new DigitalInput(armLimitSwitchPort);

        armPidController = new PIDController(P, I, D);
    }

    public void runWinch(double power) {
        if (!lockLocked()) {
            winchMotor.set(power);
        } else {
            winchMotor.set(0);
        }
    }

    public void moveArm(double power) {
        armMotor.set(power);
    }

    /**
     * 
     * @param velocitySetpoint in degrees per second. Up is positive
     */
    public void moveArmPID(double velocitySetpoint) {
        armPidController.setSetpoint(velocitySetpoint);
        moveArm(armPidController.calculate(getArmSpeed()));
    }

    public void resetArmPID() {
        armPidController.reset();
    }

    public void lock(boolean on) {
        winchLockPiston.set(!on);
    }

    public void toggleLock() {
        lock(!lockLocked());
    }

    public boolean lockLocked() {
        return !winchLockPiston.get();
    }

    public void resetArmEncoder() {
        armEncoder.reset();
    }

    /**
     * 
     * @return angle of the arm in degrees. 0 degrees is down position.
     */
    public double getArmAngle() {
        return armEncoder.getDistance();
    }

    /**
     * 
     * @return angular velocity of arm, where up is the positive direction
     */
    public double getArmSpeed() {
        return armEncoder.getRate();
    }

    public boolean getArmLimitSwitchValue() {
        return !armLimitSwitch.get();
    }
}