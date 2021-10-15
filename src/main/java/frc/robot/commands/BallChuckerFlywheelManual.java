package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallChuckerFlywheel;


public class BallChuckerFlywheelManual extends CommandBase {

    private final BallChuckerFlywheel ballChucker;
    public double desiredRpm = 0.0;

    public BallChuckerFlywheelManual(BallChuckerFlywheel ballChucker) {
        this.ballChucker = ballChucker;
    }

    @Override
    public void initialize() {
        ballChucker.enablePid(true);
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("manual flywheel rpm target", desiredRpm);
        ballChucker.setSpeed(desiredRpm);
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
