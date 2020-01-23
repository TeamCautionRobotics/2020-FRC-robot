package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveBase {

    private final VictorSP driveLeft;
    private final VictorSP driveRight;
    private Solenoid shifter;

    private final Encoder leftEncoder;
    private final Encoder rightEncoder;

    private final ADXRS450_Gyro gyro;

    private boolean usingLeftEncoder = false;

    private double heading;
    public double courseHeading;

    public DriveBase(int left, int right, int leftA, int leftB, int rightA, int rightB, int shifterChannel) {
        driveLeft = new VictorSP(left);
        driveRight = new VictorSP(right);

        leftEncoder = new Encoder(leftA, leftB, false, EncodingType.k4X);
        rightEncoder = new Encoder(rightA, rightB, true, EncodingType.k4X);

        leftEncoder.setDistancePerPulse((4 * Math.PI) / 1024.0);
        rightEncoder.setDistancePerPulse((4 * Math.PI) / 1024.0);

        shifter = new Solenoid(shifterChannel);

        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        heading = gyro.getAngle();
        courseHeading = heading;
    }

    public void drive(double leftPower, double rightPower) {
        driveLeft.set(leftPower);
        driveRight.set(-rightPower);
    }

    public void drive(double power) {
        drive(power, power);
    }

    public void useHighGear(boolean highGear) {
        shifter.set(highGear);
    }

    public void resetGyro() {
        gyro.reset();
    }

    public double getGyroAngle() {
        return gyro.getAngle();
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