/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.commands.TankDrive;
import frc.robot.commands.ToggleShifter;
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

  public static final DriveBase driveBase = new DriveBase(Constants.LEFT_DRIVE_PORT, Constants.RIGHT_DRIVE_PORT,
      Constants.LEFT_DRIVE_ENCODER_PORT_A, Constants.LEFT_DRIVE_ENCODER_PORT_B, Constants.RIGHT_DRIVE_ENCODER_PORT_A,
      Constants.RIGHT_DRIVE_ENCODER_PORT_B, Constants.SHIFTER_PORT);

  EnhancedJoystick leftJoystick = new EnhancedJoystick(Constants.LEFT_JOYSTICK_PORT);
  EnhancedJoystick rightJoystick = new EnhancedJoystick(Constants.RIGHT_JOYSTICK_PORT);
  Gamepad manipulator = new Gamepad(Constants.MANIPULATOR_PORT);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    driveBase.setDefaultCommand(new TankDrive(driveBase, () -> leftJoystick.getY(), () -> rightJoystick.getY()));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(leftJoystick, Constants.LEFT_JOYSTICK_SHIFTER_BUTTON)
        .toggleWhenPressed(new ToggleShifter(driveBase));
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
