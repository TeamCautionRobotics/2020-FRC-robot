package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Climb {

    private final VictorSP winchMotor;
    private final VictorSP armMotor;

    private final Solenoid armLockPiston;

    private DigitalInput armLimitSwitch;

    public Climb(int winchMotorPort, int armMotorPort, int armLockPistonPort, int armLimitSwitchPort) {
        winchMotor = new VictorSP(winchMotorPort);
        armMotor = new VictorSP(armMotorPort);
        armLockPiston = new Solenoid(armLockPistonPort);

        armLimitSwitch = new DigitalInput(armLimitSwitchPort);
    }

    public void runWinch(double power) {
        winchMotor.set(power);
    }

    public void moveArms(double power) {
        armMotor.set(power);
    }

    public void lock(boolean on) {
        armLockPiston.set(on);
    }

    public boolean getArmLimitSwitchValue() {
        return !armLimitSwitch.get();
    }
}