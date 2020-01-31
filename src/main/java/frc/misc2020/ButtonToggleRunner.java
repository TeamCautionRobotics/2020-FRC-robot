package frc.misc2020;

import java.util.function.BooleanSupplier;

/**
 * ButtonPressRunner
 */
public class ButtonToggleRunner {
    private final BooleanSupplier getButton;
    private final Runnable runnable;
    private boolean lastButtonState;

    /**
     * Calls runnable when the button value, accessed by getButton
     * goes from false to true.
     * 
     * @param getButton returns the state of the button controlling the runnable
     * @param runnable called when the button is pressed
     */
    public ButtonToggleRunner(BooleanSupplier getButton, Runnable runnable) {
        this.getButton = getButton;
        this.runnable = runnable;
        lastButtonState = this.getButton.getAsBoolean();
    }

    /**
     * Call this method to trigger the button to be checked.
     * This probably belongs in robotPeriodic() or teleopPeriodic()
     */
    public void update() {
        if (!lastButtonState && getButton.getAsBoolean()) {
            runnable.run();
        }
        lastButtonState = getButton.getAsBoolean();
    }
}