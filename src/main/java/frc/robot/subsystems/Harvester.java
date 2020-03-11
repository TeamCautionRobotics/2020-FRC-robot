package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;

public class Harvester extends SubsystemBase {

    // ESC declarations
    private final VictorSP intakeMotor;

    // Piston declarations
    private final Solenoid deployPiston;

    // class initializer
    public Harvester(int intakeMotorPort, int deployPistonPort) {
        intakeMotor = new VictorSP(intakeMotorPort);
        deployPiston = new Solenoid(deployPistonPort);
    }

    // Setters

    /**
     * 
     * @param power positave intakes ball? negative outakes the balls? duhhhh
     */
    public void intakeMotorControl(double power) {
        intakeMotor.set(power);
    }

    // Piston
    public void deployIntake(boolean deployed) {
        deployPiston.set(deployed);
    }

    // Toggles piston
    public void toggleIntakeDeployer() {
        deployPiston.set(!getDeployPiston());
    }

    // Method to return piston state
    public boolean getDeployPiston() {
        return deployPiston.get();
    }
}