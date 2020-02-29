package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;

public class Climb {

    private final VictorSP winchMotor;
    private final VictorSP armMotor;

    private DigitalInput armLimitSwitch;

    public Climb(int winchMotorPort, int armMotorPort, int armLimitSwitchPort) {
        winchMotor = new VictorSP(winchMotorPort);
        armMotor = new VictorSP(armMotorPort);
        armLimitSwitch = new DigitalInput(armLimitSwitchPort);
    }

    public void runWinch(double power) {
        winchMotor.set(power);
    }

    public void moveArms(double power) {
        armMotor.set(power);
    }

    public boolean getArmLimitSwitchValue() {
        return !armLimitSwitch.get();
    }
}