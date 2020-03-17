package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class DriveBase extends DifferentialDrive implements Subsystem {

  private final SpeedControllerGroup leftDrive;
  private final SpeedControllerGroup rightDrive;
  private final Solenoid leftShifter;
  private final Solenoid rightShifter;

  private final Encoder leftEncoder;
  private final Encoder rightEncoder;

  private final ADXRS450_Gyro gyro;

  private boolean usingLeftEncoder = false;

  private double heading;
  public double courseHeading;

  public DriveBase(SpeedControllerGroup leftDrive, SpeedControllerGroup rightDrive, int leftShifterPort,
      int rightShifterPort, int leftA, int leftB, int rightA, int rightB) {
    super(leftDrive, rightDrive);
    this.leftDrive = leftDrive;
    this.rightDrive = rightDrive;

    leftShifter = new Solenoid(leftShifterPort);
    rightShifter = new Solenoid(rightShifterPort);

    leftEncoder = new Encoder(leftA, leftB, false, EncodingType.k4X);
    rightEncoder = new Encoder(rightA, rightB, true, EncodingType.k4X);

    leftEncoder.setDistancePerPulse((4 * Math.PI) / 1024.0);
    rightEncoder.setDistancePerPulse((4 * Math.PI) / 1024.0);

    gyro = new ADXRS450_Gyro();
    gyro.calibrate();
    heading = gyro.getAngle();
    courseHeading = heading;
  }

  /**
   * The front of the robot is the climb side. The back of the robot is the intake
   * side.
   * 
   * @param power positive moves the left side of the robot forward
   */
  public void setLeftPower(double power) {
    leftDrive.set(power);
  }

  /**
   * The front of the robot is the climb side. The back of the robot is the intake
   * side.
   * 
   * @param power positive moves the right side of the robot forward
   */
  public void setRightPower(double power) {
    rightDrive.set(power);
  }

  /**
   * @param boolean highGear is positive when the robot is in high gear
   */
  public void setHighGear(boolean highGear) {
    leftShifter.set(highGear);
    rightShifter.set(highGear);
  }

  /**
   * @return boolean shifterState is positive when the robot is in high gear
   */
  public boolean getShifterState() {
    return leftShifter.get();
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