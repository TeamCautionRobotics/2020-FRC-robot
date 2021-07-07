package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;

public class Climb {

    private final SpeedController winchMotor;
    private final SpeedController armMotor;

    private final Solenoid winchLockPiston;

    private DigitalInput armLimitSwitch;

    public Climb(SpeedController winchMotor, SpeedController armMotor, int armLockPistonPort, int armLimitSwitchPort) {
        this.winchMotor = winchMotor;
        this.armMotor = armMotor;
        winchLockPiston = new Solenoid(armLockPistonPort);

        armLimitSwitch = new DigitalInput(armLimitSwitchPort);
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

    public void lock(boolean on) {
        winchLockPiston.set(!on);
    }

    public void toggleLock() {
        lock(!lockLocked());
    }

    public boolean lockLocked() {
        return !winchLockPiston.get();
    }

    public boolean getArmLimitSwitchValue() {
        return !armLimitSwitch.get();
    }
}