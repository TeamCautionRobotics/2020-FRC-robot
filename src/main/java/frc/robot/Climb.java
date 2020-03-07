package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;

public class Climb {

    private final SpeedController winchMotor;
    private final SpeedController armMotor;

    private final Solenoid armLockPiston;

    private DigitalInput armLimitSwitch;

    public Climb(SpeedController winchMotor, SpeedController armMotor, int armLockPistonPort, int armLimitSwitchPort) {
        this.winchMotor = winchMotor;
        this.armMotor = armMotor;
        armLockPiston = new Solenoid(armLockPistonPort);

        armLimitSwitch = new DigitalInput(armLimitSwitchPort);
    }

    public void runWinch(double power) {
        winchMotor.set(power);
    }

    public void moveArms(double power) {
        armMotor.set(-power);
    }

    public void lock(boolean on) {
        armLockPiston.set(on);
    }

    public boolean getArmLimitSwitchValue() {
        return !armLimitSwitch.get();
    }
}