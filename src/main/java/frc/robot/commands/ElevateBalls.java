package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallTransfer;


public class ElevateBalls extends CommandBase {

    private final BallTransfer ballTransfer;
    private final DoubleSupplier powerSupplier;

    public ElevateBalls(BallTransfer ballTransfer, DoubleSupplier powerSupplier) {

        this.ballTransfer = ballTransfer;
        this.powerSupplier = powerSupplier;
    }

    @Override
    public void execute() {
        ballTransfer.moveBalls(powerSupplier.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        ballTransfer.moveBalls(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
