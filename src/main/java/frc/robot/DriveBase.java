package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class DriveBase {

    private final SpeedControllerGroup driveLeft;
    private final SpeedControllerGroup driveRight;

    private final Solenoid leftShifter;
    private final Solenoid rightShifter;

    private final Encoder leftEncoder;
    private final Encoder rightEncoder;

    private boolean usingLeftEncoder = false;

    private double heading;
    public double courseHeading;

    /**
     * 
     * @param driveLeft           positive moves forward. the front of the robot is
     *                            opposite the intake.
     * @param driveRight
     * @param leftShifterChannel
     * @param rightShifterChannel
     * @param leftA
     * @param leftB
     * @param rightA
     * @param rightB
     */
    public DriveBase(SpeedControllerGroup driveLeft, SpeedControllerGroup driveRight, int leftShifterChannel,
            int rightShifterChannel, int leftA, int leftB, int rightA, int rightB) {
        this.driveLeft = driveLeft;
        this.driveRight = driveRight;
        this.driveRight.setInverted(true);

        leftShifter = new Solenoid(leftShifterChannel);
        rightShifter = new Solenoid(rightShifterChannel);

        leftEncoder = new Encoder(leftA, leftB, false, EncodingType.k4X);
        rightEncoder = new Encoder(rightA, rightB, true, EncodingType.k4X);

        leftEncoder.setDistancePerPulse((4 * Math.PI) / 1024.0);
        rightEncoder.setDistancePerPulse((4 * Math.PI) / 1024.0);
    }

    public void drive(double leftPower, double rightPower) {
        driveLeft.set(leftPower);
        driveRight.set(rightPower);
    }

    public void drive(double power) {
        drive(power, power);
    }

    public void useHighGear(boolean highGear) {
        leftShifter.set(highGear);
        rightShifter.set(highGear);
    }

    public boolean shifterEngaged() {
        return leftShifter.get();
    }

    public void toggleHighGear() {
        useHighGear(!shifterEngaged());
    }

    public void resetGyro() {
        // TODO: NOPE
    }

    public double getGyroAngle() {
        return 0;
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public boolean usingLeftEncoder() {
        return usingLeftEncoder;
    }

    public void setUsingLeftEncoder(boolean usingLeftEncoder) {
        this.usingLeftEncoder = usingLeftEncoder;
    }

    public double getDistance() {
        if (usingLeftEncoder) {
            return getLeftDistance();
        } else {
            // default to right encoder
            return getRightDistance();
        }
    }

    public double getSpeed() {
        if (usingLeftEncoder) {
            return getLeftSpeed();
        } else {
            // default to right encoder
            return getRightSpeed();
        }
    }

    public double getRightDistance() {
        return rightEncoder.getDistance();
    }

    public double getRightSpeed() {
        return rightEncoder.getRate();
    }

    public double getLeftDistance() {
        return leftEncoder.getDistance();
    }

    public double getLeftSpeed() {
        return leftEncoder.getRate();
    }
}