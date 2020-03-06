package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;

public class Harvester {

    // ESC declarations
    private final SpeedController intakeMotor;

    // Piston declarations
    private final Solenoid leftDeployerPiston;
    private final Solenoid rightDeployerPiston;

    // class initializer
    public Harvester(SpeedController intakeMotor, int leftDeployPistonPort, int rightDeployerPistonPort) {
        this.intakeMotor = intakeMotor;
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