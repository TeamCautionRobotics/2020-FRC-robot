/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.ToggleReaper;
import frc.robot.commands.ClimbControl;
import frc.robot.commands.ArmUp;
import frc.robot.commands.Autonomous;
import frc.robot.commands.BallChuckerFlywheelAuto;
import frc.robot.commands.BallChuckerRotatorAuto;
import frc.robot.commands.BallChuckerRotatorManual;
import frc.robot.commands.BallChuckerRotatorPark;
import frc.robot.commands.ElevateBalls;
import frc.robot.commands.BallChuckerFlywheelManual;
import frc.robot.commands.LockWinch;
import frc.robot.commands.RunIndexer;
import frc.robot.commands.RunReaper;
import frc.robot.commands.RunWinchDown;
import frc.robot.commands.RunWinchUp;
import frc.robot.commands.TankDrive;
import frc.robot.commands.ToggleShifter;
import frc.robot.commands.UnlockWinch;
import frc.robot.misc2020.EnhancedJoystick;
import frc.robot.misc2020.LimelightData;
import frc.robot.subsystems.BallChuckerFlywheel;
import frc.robot.subsystems.BallChuckerIndexer;
import frc.robot.subsystems.BallChuckerRotator;
import frc.robot.subsystems.BallTransfer;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Reaper;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  EnhancedJoystick leftJoystick;
  EnhancedJoystick rightJoystick;
  XboxController manipulator;

  public final DriveBase driveBase;
  public final Reaper reaper;
  public final ClimbSubsystem climb;
  public final BallChuckerFlywheel ballChuckerFlywheel;
  public final BallChuckerRotator ballChuckerRotator;
  public final BallChuckerIndexer ballChuckerIndexer;
  public final BallTransfer ballTransfer;

  public final LimelightData limelightData;

  public boolean rotatorLocked;
  public boolean flywheelLocked;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Initialization things
    leftJoystick = new EnhancedJoystick(Constants.LEFT_JOYSTICK_PORT);
    rightJoystick = new EnhancedJoystick(Constants.RIGHT_JOYSTICK_PORT);
    manipulator = new XboxController(Constants.MANIPULATOR_PORT);

    final VictorSP reaperMotor;
    reaperMotor = new VictorSP(Constants.REAPER_MOTOR_PORT);
    reaperMotor.setInverted(true);

    limelightData = new LimelightData();

    reaper = new Reaper(
        reaperMotor,
        Constants.REAPER_PORT_PISTON_PORT, 
        Constants.REAPER_STARBOARD_PISTON_PORT);

    driveBase = new DriveBase(
        new WPI_TalonSRX(Constants.LEFT_DRIVE_MOTOR_0_DEVICE_NUMBER),
        new WPI_VictorSPX(Constants.LEFT_DRIVE_MOTOR_1_DEVICE_NUMBER),
        new WPI_VictorSPX(Constants.LEFT_DRIVE_MOTOR_2_DEVICE_NUMBER),
        new WPI_TalonSRX(Constants.RIGHT_DRIVE_MOTOR_0_DEVICE_NUMBER),
        new WPI_VictorSPX(Constants.RIGHT_DRIVE_MOTOR_1_DEVICE_NUMBER),
        new WPI_VictorSPX(Constants.RIGHT_DRIVE_MOTOR_2_DEVICE_NUMBER), 
        new Solenoid(Constants.LEFT_SHIFTER_PORT), 
        new Solenoid(Constants.RIGHT_SHIFTER_PORT),
        new Encoder(Constants.LEFT_DRIVE_ENCODER_PORT_A, Constants.LEFT_DRIVE_ENCODER_PORT_B, false, EncodingType.k4X),
        new Encoder(Constants.RIGHT_DRIVE_ENCODER_PORT_A, Constants.RIGHT_DRIVE_ENCODER_PORT_B, true, EncodingType.k4X));

    climb = new ClimbSubsystem(
        new WPI_VictorSPX(Constants.WINCH_MOTOR_DEVICE_NUMBER), 
        new WPI_VictorSPX(Constants.ARM_MOTOR_DEVICE_NUMBER), 
        new Solenoid(Constants.WINCH_LOCK_PISTON_PORT), 
        new Encoder(Constants.ARM_ENCODER_PORT_A, Constants.ARM_ENCODER_PORT_B), 
        new DigitalInput(Constants.ARM_LIMIT_SWITCH_PORT));

    ballChuckerFlywheel = new BallChuckerFlywheel(
        new VictorSP(Constants.LEFT_FLYWHEEL_MOTOR_PORT),
        new VictorSP(Constants.RIGHT_FLYWHEEL_MOTOR_PORT),
        new Encoder(Constants.FLYWHEEL_ENCODER_PORT_A, Constants.FLYWHEEL_ENCODER_PORT_B));

    ballChuckerRotator = new BallChuckerRotator(
        new VictorSP(Constants.ROTATOR_MOTOR_PORT),
        new Encoder(Constants.ROTATOR_ENCODER_PORT_A, Constants.ROTATOR_ENCODER_PORT_B),
        new DigitalInput(Constants.ROTATOR_LIMIT_SWITCH_PORT));

    ballChuckerIndexer = new BallChuckerIndexer(
        new WPI_VictorSPX(Constants.INDEXER_MOTOR_DEVICE_ID));

    ballTransfer = new BallTransfer(
        new VictorSP(Constants.BALL_TRANSFER_MOTOR_PORT));
    
    driveBase.setDefaultCommand(new TankDrive(driveBase, () -> -leftJoystick.getY(), () -> -rightJoystick.getY()));
    ballChuckerRotator.setDefaultCommand(new BallChuckerRotatorAuto(ballChuckerRotator, limelightData, rotatorLocked));
    climb.setDefaultCommand(new ClimbControl(climb, () -> -manipulator.getY(Hand.kRight), () -> manipulator.getRawAxis(2), () -> manipulator.getRawAxis(3)));

    // Configure the button bindings
    configureButtonBindings();


    climb.lock(false);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {


    // JOYSTICK BINDS:
    // shifter
    new JoystickButton(leftJoystick, 4).toggleWhenPressed(new ToggleShifter(driveBase));
    // indexer
    new JoystickButton(rightJoystick, 1).whileHeld(new RunIndexer(ballChuckerIndexer, () -> 0.75));
    // auto flywheel
    new JoystickButton(rightJoystick, 3).whileHeld(new BallChuckerFlywheelAuto(ballChuckerFlywheel, limelightData, flywheelLocked));
    // Failsafes
    new JoystickButton(leftJoystick, 11).toggleWhenPressed(new BallChuckerRotatorManual(ballChuckerRotator, () -> manipulator.getX(Hand.kLeft)));
    new JoystickButton(leftJoystick, 11).toggleWhenPressed(new BallChuckerFlywheelManual(ballChuckerFlywheel, false, () -> -rightJoystick.getRawAxis(2)));

    // MANIPULATOR BINDS:
    //intake piston 
    new JoystickButton(manipulator, Button.kA.value).toggleWhenPressed(new ToggleReaper(reaper));
    // intake run
    new JoystickButton(manipulator, Button.kX.value).whileHeld(new RunReaper(reaper, () -> 0.7));
    // elevator
    new JoystickButton(manipulator, Button.kY.value).whileHeld(new ElevateBalls(ballTransfer, () -> -1));
    // winch lock/unlock
    new JoystickButton(manipulator, Button.kBumperLeft.value).whenPressed(new LockWinch(climb));
    new JoystickButton(manipulator, Button.kBumperRight.value).whenPressed(new UnlockWinch(climb));
    // Rotator park
    new JoystickButton(manipulator, Button.kStart.value).toggleWhenPressed(new BallChuckerRotatorPark(ballChuckerRotator));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new Autonomous(driveBase, ballChuckerIndexer, ballTransfer, rotatorLocked, flywheelLocked);
  }
}
