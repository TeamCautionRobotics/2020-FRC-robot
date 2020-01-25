package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;

public class Harvester {

    // ESC declarations
    private final VictorSP intakeMotor; 

    // Piston declarations
    private final Solenoid deployPistion;


    // class initializer
    public Harvester(int intakeMotorPort, int deployPistonPort) {
        intakeMotor = new VictorSP(intakeMotorPort);
        deployPistion = new Solenoid(deployPistonPort);
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
    public void delpoyIntake(boolean deployed) {
        deployPistion.set(deployed);
    }
}