package frc.misc2020;

import edu.wpi.first.wpilibj.Joystick;

public class EnhancedJoystick extends Joystick {

    protected double deadbandSize;

    public EnhancedJoystick(int port) {
        this(port, 0.1);
    }

    public EnhancedJoystick(int port, double deadband) {
        super(port);
        setDeadband(deadband);
    }

    /**
     * Get the value of the axis with a deadband applied
     * 
     * @param axis The axis to read, starting at 0.
     * @return The value of the axis with the deadband applied.
     */
    public double getRawAxis(int axis) {
        return deadband(super.getRawAxis(axis));
    }

    /**
     * Set the size of the deadband.
     * 
     * @param deadband Size of the deadband. Must be non-negative.
     */
    public void setDeadband(double deadband) {
        if (deadband < 0) {
            deadband = 0;
        }
        deadbandSize = deadband;
    }

    public double getDeadband() {
        return deadbandSize;
    }

    protected double deadband(double value) {
        if (value > deadbandSize) {
            return (value - deadbandSize) / (1 - deadbandSize);
        }
        if (value < -deadbandSize) {
            return (value + deadbandSize) / (1 - deadbandSize);
        }
        return 0;
    }
}