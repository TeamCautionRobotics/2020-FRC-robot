package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallChuckerFlywheel;


public class BallChuckerFlywheelManual extends CommandBase {

    private final BallChuckerFlywheel ballChucker;
    private boolean usePid;
    private DoubleSupplier powerTarget;
    public double desiredRps = 0.0; 
    private double correctedPower;

    public BallChuckerFlywheelManual(BallChuckerFlywheel ballChucker, boolean usePid, DoubleSupplier powerTarget) {
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

            if (powerTarget.getAsDouble() > 0) {
                correctedPower = (powerTarget.getAsDouble() / 2.0) + 0.5;
            } else {
                correctedPower = (1.0 + powerTarget.getAsDouble()) / 2.0;
            }


            ballChucker.setPower(correctedPower);
            SmartDashboard.putNumber("power target flywheel", correctedPower);
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
