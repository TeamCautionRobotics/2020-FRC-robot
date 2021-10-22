package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {

  private final SpeedController winchMotor;
  private final SpeedController armMotor;

  private final Solenoid winchLockPiston;

  private final Encoder armEncoder;
  private final DigitalInput armLimitSwitch;

  private final PIDController armPidController;

  private boolean usingPid = true;
  private double pidOutput;


  /**
   * Creates a new ArmSubsystem.
   */
  public ClimbSubsystem(SpeedController winchMotor, SpeedController armMotor, Solenoid winchLockPiston,
                        Encoder armEncoder, DigitalInput armLimitSwitch) {

    this.winchMotor = winchMotor;
    this.armMotor = armMotor;
    this.winchLockPiston = winchLockPiston;
    this.armEncoder = armEncoder;
    this.armLimitSwitch = armLimitSwitch;

    armMotor.setInverted(true);
    this.winchMotor.setInverted(true);

    armEncoder.setDistancePerPulse(360.0 / (100.0 * 1024.0));

    armPidController = new PIDController(0.1, 0.01, 0.1);

    // start at 0
    armPidController.setSetpoint(0);

  }

  public void runWinch(double power) {
    if (!lockLocked()) {
        winchMotor.set(power);
    } else {
        winchMotor.set(0);
    }

}

public void moveArm(double power) {
    armMotor.set(0.7 * power);
}

/**
 * 
 * @param velocitySetpoint in degrees per second. Up is positive
 */
public void moveArmPID(double velocitySetpoint) {
    usingPid = true;
    armPidController.setSetpoint(velocitySetpoint);
}

public void resetArmPID() {
    armPidController.reset();
}

public void enablePid(boolean enabled) {
  if (!usingPid && enabled) {
    resetArmPID();
  }
  usingPid = enabled;
}

public void lock(boolean on) {
    winchLockPiston.set(!on);
}

public void toggleLock() {
    lock(!lockLocked());
}

public boolean lockLocked() {
    return !winchLockPiston.get();
}

public void resetArmEncoder() {
    armEncoder.reset();
}

/**
 * 
 * @return angle of the arm in degrees. 0 degrees is down position.
 */
public double getArmAngle() {
    return armEncoder.getDistance();
}

/**
 * 
 * @return angular velocity of arm, where up is the positive direction
 */
public double getArmSpeed() {
    return armEncoder.getRate();
}

public boolean getArmLimitSwitchValue() {
    return !armLimitSwitch.get();
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    pidOutput = armPidController.calculate(getArmSpeed());

    if (usingPid) {

      moveArm(pidOutput);
      
    }

  }
}
