package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BallChucker9000Piston extends SubsystemBase {

    private final Solenoid piston;

    public BallChucker9000Piston(Solenoid pistonObj) {

        this.piston = pistonObj;

    }

    public void setPiston(boolean state) {
        piston.set(state);
    }

    public boolean getState() {
        return piston.get();
    }

}