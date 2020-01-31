package frc.autonomous;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MissionSendable extends SendableBase implements Sendable {
    private Supplier<Mission> missionSupplier;

    private boolean running = false;
    private boolean initialized = false;
    private boolean finished = true;
    private Mission selectedMission;

    public MissionSendable(String name, Supplier<Mission> selectedMissionSupplier) {
        setName(name);
        missionSupplier = selectedMissionSupplier;
    }

    public boolean run() {
        if (running) {
            if (!initialized) {
                selectedMission = missionSupplier.get();
                if (selectedMission != null) {
                    selectedMission.reset();
                    System.out.format("Teleop mission '%s' Started%n", selectedMission.getName());
                    initialized = true;
                    finished = false;
                    return running;
                } else {
                    running = false;
                }
            }

            finished = selectedMission.run();

            if (finished) {
                running = false;
                initialized = false;
                System.out.format("Teleop mission '%s' Completed Successfully%n",
                        selectedMission.getName());
            }
        } else if (!finished) {
            // Not running and not finished: were were cancelled
            System.out.format("Teleop mission '%s' Cancelled%n", selectedMission.getName());
            initialized = false;
            finished = true;
        }
        return running;
    }


    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Command");
        builder.addStringProperty(".name", this::getName, null);
        builder.addBooleanProperty("running", () -> this.running, (value) -> running = value);
    }
}
