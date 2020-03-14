/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
	// Ports
	public static final int LEFT_JOYSTICK_PORT = 0;
	public static final int RIGHT_JOYSTICK_PORT = 1;
	public static final int MANIPULATOR_PORT = 2;

	public static final int LEFT_DRIVE_MOTOR_0_DEVICE_NUMBER = 10;
	public static final int LEFT_DRIVE_MOTOR_1_DEVICE_NUMBER = 11;
	public static final int LEFT_DRIVE_MOTOR_2_DEVICE_NUMBER = 12;

	public static final int RIGHT_DRIVE_MOTOR_0_DEVICE_NUMBER = 20;
	public static final int RIGHT_DRIVE_MOTOR_1_DEVICE_NUMBER = 21;
	public static final int RIGHT_DRIVE_MOTOR_2_DEVICE_NUMBER = 22;

	public static final int LEFT_DRIVE_ENCODER_PORT_A = 0;
	public static final int LEFT_DRIVE_ENCODER_PORT_B = 1;
	public static final int RIGHT_DRIVE_ENCODER_PORT_A = 2;
	public static final int RIGHT_DRIVE_ENCODER_PORT_B = 3;

	public static final int LEFT_SHIFTER_PORT = 4;
	public static final int RIGHT_SHIFTER_PORT = 3;

	public static final int INTAKE_MOTOR_PORT = 5;
	public static final int DEPLOY_PISTON_PORT = 2;
}
