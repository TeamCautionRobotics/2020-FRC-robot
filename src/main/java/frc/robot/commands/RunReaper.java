package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Reaper;

public class RunReaper extends CommandBase {
    private final Reaper reaper;
    private final DoubleSupplier powerSupplier;

    public RunReaper(Reaper reaper, DoubleSupplier powerSupplier) {
        this.reaper = reaper;
        this.powerSupplier = powerSupplier;
    }

    @Override
    public void execute() {
        reaper.reaperMotorControl(powerSupplier.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        reaper.reaperMotorControl(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
