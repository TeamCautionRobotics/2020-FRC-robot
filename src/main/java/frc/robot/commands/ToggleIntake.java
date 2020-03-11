package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Harvester;

public class ToggleIntake extends InstantCommand {
    public ToggleIntake(Harvester harvester) {
        super(() -> harvester.toggleIntakeDeployer(), harvester);
    }
}