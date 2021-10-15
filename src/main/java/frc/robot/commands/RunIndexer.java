package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallChuckerIndexer;


public class RunIndexer extends CommandBase {

    private final BallChuckerIndexer ballChucker;
    private final DoubleSupplier powerSupplier;

    public RunIndexer(BallChuckerIndexer ballChuckerObj, DoubleSupplier powerSupplier) {
        this.ballChucker = ballChuckerObj;
        this.powerSupplier = powerSupplier;
    }

    @Override
    public void execute() {
        ballChucker.setPower(powerSupplier.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        ballChucker.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
