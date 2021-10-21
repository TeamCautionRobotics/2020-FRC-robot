package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimbControl extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private final ClimbSubsystem subsystem;
  private DoubleSupplier controlPower;
  private DoubleSupplier winchDown;
  private DoubleSupplier winchUp;

  public ClimbControl(ClimbSubsystem subsystem, DoubleSupplier controlPower, DoubleSupplier winchDown, DoubleSupplier winchUp) {

    this.subsystem = subsystem;
    this.controlPower = controlPower;

    this.winchDown = winchDown;
    this.winchUp = winchUp;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

    subsystem.enablePid(false);

  }

  @Override
  public void execute() {
    subsystem.moveArm(controlPower.getAsDouble());

    if (winchUp.getAsDouble() >= 0.1) {
      subsystem.runWinch(winchUp.getAsDouble());
    } else {
      subsystem.runWinch(-winchDown.getAsDouble());
    }

  }

  @Override
  public void end(boolean interrupted) {
    subsystem.moveArm(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
