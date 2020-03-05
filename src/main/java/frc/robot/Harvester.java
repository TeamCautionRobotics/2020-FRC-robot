package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;

public class Harvester {

    // ESC declarations
    private final VictorSP intakeMotor;

    // Piston declarations
    private final Solenoid leftDeployerPiston;
    private final Solenoid rightDeployerPiston;

    // class initializer
    public Harvester(int intakeMotorPort, int leftDeployPistonPort, int rightDeployerPistonPort) {
        intakeMotor = new VictorSP(intakeMotorPort);
        leftDeployerPiston = new Solenoid(leftDeployPistonPort);
        rightDeployerPiston = new Solenoid(rightDeployerPistonPort);
    }

    // Setters

    /**
     * 
     * @param power positive intakes ball? negative outakes the balls? duhhhh
     */
    public void intakeMotorControl(double power) {
        intakeMotor.set(power);
    }

    // Piston
    public void delpoyIntake(boolean deployed) {
        leftDeployerPiston.set(deployed);
        rightDeployerPiston.set(deployed);
    }
}