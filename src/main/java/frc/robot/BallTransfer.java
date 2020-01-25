package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class BallTransfer {

    private final VictorSP beltMotor;

    public BallTransfer(int beltMotorPort) {
        beltMotor = new VictorSP(beltMotorPort);
    }

    /**
     * 
     * @param power positive to move ball in, negative to move ball out
     */
    public void moveBalls(double power) {
        beltMotor.set(power);
    }
}