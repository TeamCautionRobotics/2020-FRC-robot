package frc.robot.commands;

import frc.robot.subsystems.BallChuckerRotator;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class BallChuckerRotatorPark extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private BallChuckerRotator ballChucker;

  public BallChuckerRotatorPark(BallChuckerRotator subsystem) {
    ballChucker = subsystem;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

    // enable the pid
    ballChucker.enablePid(true);

  }

  @Override
  public void execute() {

    ballChucker.setRotatorPosition(17.0);

  }

  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
