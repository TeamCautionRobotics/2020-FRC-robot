package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBase extends SubsystemBase {

  private final VictorSP driveLeft;
  private final VictorSP driveRight;
  private final Solenoid shifter;

  private final Encoder leftEncoder;
  private final Encoder rightEncoder;

  private final ADXRS450_Gyro gyro;

  private boolean shifterState = false;
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

  /**
   * The front of the robot is the climb side. The back of the robot is the intake
   * side.
   * 
   * @param power positive moves the left side of the robot forward
   */
  public void setLeftPower(double power) {
    driveLeft.set(power);
  }

  /**
   * The front of the robot is the climb side. The back of the robot is the intake
   * side.
   * 
   * @param power positive moves the right side of the robot forward
   */
  public void setRightPower(double power) {
    driveRight.set(power);
  }

  /**
   * The front of the robot is the climb side.
   * The back of the robot is the intake side.
   * @param leftPower positive moves the left side of the robot forward
   * @param leftPower positive moves the right side of the robot forward
   */
  public void drive(double leftPower, double rightPower) {
    setLeftPower(leftPower);
    setRightPower(rightPower);
  }

    /**
   * The front of the robot is the climb side.
   * The back of the robot is the intake side.
   * @param power positive moves the robot forward
   */
  public void drive(double power) {
    drive(power, power);
  }

  /**
   * @param boolean highGear is positive when the robot is in high gear
   */
  public void useHighGear(boolean highGear) {
    shifter.set(highGear);
    shifterState = highGear;
  }

  /**
   * @return boolean shifterState is positive when the robot is in high gear
   */
  public boolean getShifterState() {
    return shifterState;
  }

  public void toggleShifterState() {
    useHighGear(!shifterState);
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