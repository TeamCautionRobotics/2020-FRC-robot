package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;

public class Harvester {

    // ESC declarations
    private final SpeedController intakeMotor;

    // Piston declarations
    private final Solenoid portDeployerPiston;
    private final Solenoid starboardDeployerPiston;

    /**
     * 
     * @param intakeMotor
     * @param portDeployPistonPort        This is the left piston, relative to the
     *                                    front being the climb side of the robot
     * @param starboardDeployerPistonPort This is the right piston.
     */
    public Harvester(SpeedController intakeMotor, int portDeployPistonPort, int starboardDeployerPistonPort) {
        this.intakeMotor = intakeMotor;
        portDeployerPiston = new Solenoid(portDeployPistonPort);
        starboardDeployerPiston = new Solenoid(starboardDeployerPistonPort);
    }

    // Setters

    /**
     * 
     * @param power positive intakes ball? negative outakes the balls? duhhhh
     */
    public void intakeMotorControl(double power) {
        intakeMotor.set(-power);
    }

    // Pistons
    public void deployIntake(boolean deployed) {
        portDeployerPiston.set(deployed);
        starboardDeployerPiston.set(deployed);
    }

    public void toggleDeployer() {
        deployIntake(!portDeployerPiston.get());
    }
}