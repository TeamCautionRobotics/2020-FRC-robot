package frc.misc2020;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Use this class to have a button on the SmartDashboard that will run a
 * function.
 * 
 * @author schuyler
 *
 */
public class FunctionRunnerSendable extends SendableBase implements Sendable {

    private boolean needsToRun;
    private final Runnable function;

    /**
     * The name will be the name on the SmartDashboard. The {@code function} is
     * called when the button is clicked.
     * 
     * @param name
     * @param function Returns true when complete.
     */
    public FunctionRunnerSendable(String name, Runnable function) {
        setName(name);
        this.function = function;
    }

    public void update() {
        if (needsToRun) {
            function.run();
            needsToRun = false;
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Button");
        builder.addStringProperty(".name", this::getName, null);
        builder.addBooleanProperty("pressed", () -> needsToRun, (value) -> needsToRun = value);
    }
}