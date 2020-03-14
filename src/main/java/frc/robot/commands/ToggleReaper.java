package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Reaper;

public class ToggleReaper extends InstantCommand {
    public ToggleReaper(Reaper reaper) {
        super(() -> reaper.toggleReaperDeployer(), reaper);
    }
}