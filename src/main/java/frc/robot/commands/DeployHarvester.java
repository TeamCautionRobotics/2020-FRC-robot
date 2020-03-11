package frc.robot.commands;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Harvester;

/**
 * A simple command that deploys intake arms
 */
public class DeployHarvester extends InstantCommand {

  public DeployHarvester(Harvester harvester) {
    super(harvester::changeDeployerState, harvester);
  }
}