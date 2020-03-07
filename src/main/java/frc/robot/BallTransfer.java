package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class BallTransfer {

    private final SpeedController beltMotor;

    public BallTransfer(SpeedController beltMotor) {
        this.beltMotor = beltMotor;
    }

    /**
     * 
     * @param power positive to move ball towards Ball Chucker 9000, negative to
     *              move ball towards Harvester
     */
    public void moveBalls(double power) {
        beltMotor.set(-power);
    }
}