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
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.ToggleShifterCommand;
import frc.robot.misc2020.EnhancedJoystick;
import frc.robot.misc2020.Gamepad;
import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

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
  Gamepad manipulator;

  DriveBase driveBase;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    leftJoystick = new EnhancedJoystick(Constants.LEFT_JOYSTICK_PORT);
    rightJoystick = new EnhancedJoystick(Constants.RIGHT_JOYSTICK_PORT);
    manipulator = new Gamepad(Constants.MANIPULATOR_PORT);

    WPI_TalonSRX leftDrive0 = new WPI_TalonSRX(Constants.LEFT_DRIVE_MOTOR_0_DEVICE_NUMBER);
    WPI_VictorSPX leftDrive1 = new WPI_VictorSPX(Constants.LEFT_DRIVE_MOTOR_1_DEVICE_NUMBER);
    WPI_VictorSPX leftDrive2 = new WPI_VictorSPX(Constants.LEFT_DRIVE_MOTOR_2_DEVICE_NUMBER);

    WPI_TalonSRX rightDrive0 = new WPI_TalonSRX(Constants.RIGHT_DRIVE_MOTOR_0_DEVICE_NUMBER);
    WPI_VictorSPX rightDrive1 = new WPI_VictorSPX(Constants.RIGHT_DRIVE_MOTOR_1_DEVICE_NUMBER);
    WPI_VictorSPX rightDrive2 = new WPI_VictorSPX(Constants.RIGHT_DRIVE_MOTOR_2_DEVICE_NUMBER);

    leftDrive0.setInverted(true);
    leftDrive2.setInverted(true);
    rightDrive1.setInverted(true);

    driveBase = new DriveBase(new SpeedControllerGroup(leftDrive0, leftDrive1, leftDrive2),
        new SpeedControllerGroup(rightDrive0, rightDrive1, rightDrive2), Constants.LEFT_SHIFTER_PORT,
        Constants.RIGHT_SHIFTER_PORT, Constants.LEFT_DRIVE_ENCODER_PORT_A, Constants.LEFT_DRIVE_ENCODER_PORT_B,
        Constants.RIGHT_DRIVE_ENCODER_PORT_A, Constants.RIGHT_DRIVE_ENCODER_PORT_B);

    configureButtonBindings();

    driveBase.setDefaultCommand(new ArcadeDriveCommand(driveBase, () -> leftJoystick.getX(), () -> -rightJoystick.getY()));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(leftJoystick, 4).toggleWhenActive(new ToggleShifterCommand(driveBase));
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