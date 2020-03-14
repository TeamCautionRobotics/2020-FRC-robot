package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;

public class Reaper extends SubsystemBase {

    // ESC declarations
    private final VictorSP reaperMotor;

    // Piston declarations
    private final Solenoid portDeployPiston;
    private final Solenoid starboardDeployPiston;

    // class initializer
    public Reaper(VictorSP intakeMotor, int portDeployPistonPort, int starboardDeployPistonPort) {
        this.reaperMotor = intakeMotor;
        portDeployPiston = new Solenoid(portDeployPistonPort);
        starboardDeployPiston = new Solenoid(starboardDeployPistonPort);
    }

    // Setters

    /**
     * 
     * @param power positave intakes ball? negative outakes the balls? duhhhh
     */
    public void reaperMotorControl(double power) {
        reaperMotor.set(power);
    }

    // Piston
    public void deployReaper(boolean deploy) {
        portDeployPiston.set(deploy);
        starboardDeployPiston.set(deploy);
    }

    // Toggles piston
    public void toggleReaperDeployer() {
        deployReaper(!getDeployPiston());
    }

    // Method to return piston state
    public boolean getDeployPiston() {
        return portDeployPiston.get();
    }
}