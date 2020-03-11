import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A simple command that harvests balls (intake wheels)
 */
public class RunHarvester extends CommandBase {
  // The subsystem the command runs on
  private final Harvester harvester;
  private final DoubleSupplier powerSupplier;

  public RunHarvester(Harvester subsystem, DoubleSupplier powerSupplier) {
    harvester = subsystem;
    this.powerSupplier = powerSupplier;
    addRequirements(harvester);
  }
  public void execute() {
    harvester.intakeMotorControl(powerSupplier.getAsDouble());
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}