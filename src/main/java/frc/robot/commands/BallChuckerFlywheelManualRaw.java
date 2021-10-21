package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallChuckerFlywheel;


public class BallChuckerFlywheelManualRaw extends CommandBase {

    private final BallChuckerFlywheel ballChucker;
    private boolean usePid;
    private DoubleSupplier powerTarget;
    public double desiredRps = 0.0; 

    public BallChuckerFlywheelManualRaw(BallChuckerFlywheel ballChucker, boolean usePid, DoubleSupplier powerTarget) {
        this.ballChucker = ballChucker;
        this.usePid = usePid;
        this.powerTarget = powerTarget;

        addRequirements(ballChucker);
    }

    @Override
    public void initialize() {

        if (usePid) {
            ballChucker.enablePid(true);
            SmartDashboard.putNumber("flywheel desired speed (rps)", desiredRps);
        } else {
            ballChucker.enablePid(false);
        }


    }

    @Override
    public void execute() {

        if (usePid) {

            ballChucker.setSpeed(desiredRps);

            desiredRps = SmartDashboard.getNumber("flywheel desired speed (rps)", 0);

        } else {
            ballChucker.setSpeed(powerTarget.getAsDouble());
        }

    }

    @Override
    public void end(boolean interrupted) {
        ballChucker.setSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
