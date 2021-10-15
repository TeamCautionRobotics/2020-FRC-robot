package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BallTransfer extends SubsystemBase {

    private final SpeedController beltMotor;

    public BallTransfer(SpeedController beltMotor) {
        this.beltMotor = beltMotor;
    }

    /**
     * 
     * @param power positive to move ball in, negative to move ball out
     */
    public void moveBalls(double power) {
        beltMotor.set(power);
    }
}