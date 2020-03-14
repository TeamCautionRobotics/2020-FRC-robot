/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.ToggleReaper;
import frc.robot.commands.RunReaper;
import frc.robot.commands.TankDrive;
import frc.robot.commands.ToggleShifter;
import frc.robot.misc2020.EnhancedJoystick;
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

  private static final VictorSP intakeMotor = new VictorSP(Constants.REAPER_MOTOR_PORT);
  
  EnhancedJoystick leftJoystick = new EnhancedJoystick(Constants.LEFT_JOYSTICK_PORT);
  EnhancedJoystick rightJoystick = new EnhancedJoystick(Constants.RIGHT_JOYSTICK_PORT);
  XboxController manipulator = new XboxController(Constants.MANIPULATOR_PORT);
  
  public static final DriveBase driveBase = new DriveBase(new WPI_TalonSRX(Constants.LEFT_DRIVE_MOTOR_0_DEVICE_NUMBER),
  new WPI_VictorSPX(Constants.LEFT_DRIVE_MOTOR_1_DEVICE_NUMBER),
  new WPI_VictorSPX(Constants.LEFT_DRIVE_MOTOR_2_DEVICE_NUMBER),
  new WPI_TalonSRX(Constants.RIGHT_DRIVE_MOTOR_0_DEVICE_NUMBER),
  new WPI_VictorSPX(Constants.RIGHT_DRIVE_MOTOR_1_DEVICE_NUMBER),
  new WPI_VictorSPX(Constants.RIGHT_DRIVE_MOTOR_2_DEVICE_NUMBER), Constants.LEFT_SHIFTER_PORT,
  Constants.RIGHT_SHIFTER_PORT, Constants.LEFT_DRIVE_ENCODER_PORT_A, Constants.LEFT_DRIVE_ENCODER_PORT_B,
  Constants.RIGHT_DRIVE_ENCODER_PORT_A, Constants.RIGHT_DRIVE_ENCODER_PORT_B);

  public static final Reaper reaper = new Reaper(intakeMotor, Constants.REAPER_PORT_PISTON_PORT, Constants.REAPER_STARBOARD_PISTON_PORT);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    driveBase.setDefaultCommand(new TankDrive(driveBase, () -> leftJoystick.getY(), () -> rightJoystick.getY()));
    reaper.setDefaultCommand(new RunReaper(reaper, () -> manipulator.getY(Hand.kRight)));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(leftJoystick, 4).toggleWhenPressed(new ToggleShifter(driveBase));

    new JoystickButton(manipulator, Button.kA.value)
        .toggleWhenPressed(new ToggleReaper(reaper));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new InstantCommand();
  }
}