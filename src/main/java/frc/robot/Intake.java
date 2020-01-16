package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake {

    //ESC declarations
    private final VictorSP intakeMotor; 

    //Piston declarations
    private final Solenoid expanderPiston;


    //class initializer
    public Intake(int intakeMotorPort, int expanderPistonPort) {
        
        intakeMotor = new VictorSP(intakeMotorPort);
        expanderPiston = new Solenoid(expanderPistonPort);

    }

    //Setters

    //ESC
    public void intakeMotorControl(double power) {
        intakeMotor.set(power);
    }

    //Piston
    public void expanderPistonControl(boolean state) {
        expanderPiston.set(state);

    }
}