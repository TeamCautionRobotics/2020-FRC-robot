package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;

public class Harvester {

    // ESC declarations
    private final VictorSP intakeMotor; 

    // Piston declarations
    private final Solenoid deployerPiston;


    // class initializer
    public Harvester(int intakeMotorPort, int deployPistonPort) {
        intakeMotor = new VictorSP(intakeMotorPort);
        deployerPiston = new Solenoid(deployPistonPort);
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
        deployerPiston.set(deployed);
    }
}