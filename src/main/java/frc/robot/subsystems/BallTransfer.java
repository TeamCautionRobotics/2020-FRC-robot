package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BallTransfer extends SubsystemBase {

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