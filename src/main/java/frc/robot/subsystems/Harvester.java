package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;

public class Harvester extends SubsystemBase {

    private final boolean deployerState = false;
    // ESC declarations
    private final VictorSP intakeMotor; 

    // Piston declarations
    private final Solenoid deployPiston;

    // Class initializer
    public Harvester(int intakeMotorPort, int deployPistonPort) {
        intakeMotor = new VictorSP(intakeMotorPort);
        deployPiston = new Solenoid(deployPistonPort);
    }

    // Setters for Piston
    public void deployIntake(boolean deployed) {
        deployPiston.set(deployed);
        deployerState = deployed;
      }
    
      public boolean getDeployerState() {
        return deployerState;
      }
    
      public void changeDeployerState() {
        deployIntake(!deployerState);
      }

    /**
     * 
     * @param power positave intakes ball? negative outakes the balls? duhhhh
     */
    public void intakeMotorControl(double power) {
        intakeMotor.set(power);
    }

}