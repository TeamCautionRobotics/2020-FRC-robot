package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class BallTransfer {

    private final VictorSP beltMotor;

    public BallTransfer(int beltMotorPort) {
        beltMotor = new VictorSP(beltMotorPort);
    }

    /**
     * 
     * @param power positive to move ball towards Ball Chucker 9000, negative to move ball towards Harvester
     */
    public void moveBalls(double power) {
        beltMotor.set(power);
    }
}