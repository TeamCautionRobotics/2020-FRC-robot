package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;

public class Climb {

    private final VictorSP winchMotor;
    private final VictorSP armMotor;

    private final Solenoid winchLock;

    private DigitalInput armLimitSwitch;

    private boolean currentWinchLockState;

    public Climb(int winchMotorPort, int armMotorPort, int winchLockPort, int armLimitSwitchPort) {
        winchMotor = new VictorSP(winchLock);
        winchLock = new Solenoid(winchLockPort);
        armMotor = new VictorSP(armMotorPort);
        armLimitSwitchPort = new DigitalInput(armLimitSwitchPort);
        currentWinchLockState = false;
    }

    public void liftRobot(double power) {
        winchMotor.set(power);
    }

    public void activateWinchLock(boolean lock) {
        winchLock.set(lock);
    }

    public void moveArms(double power) {
        armMotor.set(power);
    }

    public boolean getArmLimitSwitchValue() {
        return !armLimitSwitch.get();
    }

    public void toggleWinchLock() {
        activateWinchLock(!currentWinchLockState);
    }
}