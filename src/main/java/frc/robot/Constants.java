/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class AutoConstants{
        /*
        driveBase = new DriveBase(0, 1, 0, 1, 2, 3, 0);
        */
    }
    public static final class BallChucker9000Constants{
        public static final int flywheelMotorPort = 4;
        public static final int rotatorMotorPort = 5;
        public static final int indexerMotorPort = 6; 
        public static final int rotatorEncoderChannelA = 4; 
        public static final int rotatorEncoderChannelB = 5; 
        public static final int flywheelEncoderChannelA = 6; 
        public static final int flywheelEncoderChannelB = 7; 
        public static final int indexerPistonPort = 2;
        public static final int rotatorAtZeroSwitchPort = 8;
    }
    public static final class BallTransferConstants{
        public static final int beltMotorPort = 3;
    }
    public static final class ClimbConstants{
        /*
        Winch port 7
        Arm port 8
        Piston port 3
        Limit switch port 9
        */
    }
    public static final class DriveConstants{
        public static final int left = 0;
        public static final int right = 1; 
        public static final int leftA = 0; 
        public static final int leftB = 1; 
        public static final int rightA = 2; 
        public static final int rightB = 3;
        public static final int shifterChannel = 0;
    }
    public static final class HarvesterConstants{
        public static final int intakeMotorPort = 2;
        public static final int deployPistonPort = 1;
    }
}
