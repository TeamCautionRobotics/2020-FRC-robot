package frc.misc2020;

public class Gamepad extends EnhancedJoystick {

    public Gamepad(int port) {
        this(port, 0.2);
    }

    public Gamepad(int port, double deadband) {
        super(port, deadband);
    }

    public double getAxis(Axis axis) {
        return getRawAxis(axis.ordinal());
    }

    public boolean getButton(Button button) {
        return getRawButton(button.ordinal() + 1);
    }

    public boolean getAxisAsButton(Axis axis) {
        return getAxis(axis) > 0.5;
    }

    public enum Axis {
        LEFT_X, LEFT_Y, LEFT_TRIGGER, RIGHT_TRIGGER, RIGHT_X, RIGHT_Y
    }

    public enum Button {
        A, B, X, Y, LEFT_BUMPER, RIGHT_BUMPER, BACK, START,
        // Joystick click
        LEFT_JOYSTICK, RIGHT_JOYSTICK
    }
}