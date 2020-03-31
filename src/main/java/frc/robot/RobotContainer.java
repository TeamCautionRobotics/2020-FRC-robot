/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.BallChucker9000AutoRotate;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.BallChucker9000;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final BallChucker9000 BallChucker9000Subsystem;
  private final BallChucker9000AutoRotate BallChucker9000AutoRotateCommand;

  private final WPI_VictorSPX rotatorMotor;
  private final VictorSP leftFlywheelMotor;
  private final VictorSP rightFlywheelMotor;
  private final WPI_VictorSPX indexerMotor;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    rotatorMotor = new WPI_VictorSPX(4);
    leftFlywheelMotor = new VictorSP(0);
    rightFlywheelMotor = new VictorSP(1);
    indexerMotor = new WPI_VictorSPX(31);

    rotatorMotor.setInverted(true);
    leftFlywheelMotor.setInverted(true);
    rightFlywheelMotor.setInverted(true);
    indexerMotor.setInverted(true);

    BallChucker9000Subsystem = new BallChucker9000(new WPI_VictorSPX(4), new VictorSP(0), new VictorSP(1),
                               new WPI_VictorSPX(31), 0, 1, 7, 8, 2);

    BallChucker9000AutoRotateCommand = new BallChucker9000AutoRotate(BallChucker9000Subsystem);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return BallChucker9000AutoRotateCommand;
  }
}
