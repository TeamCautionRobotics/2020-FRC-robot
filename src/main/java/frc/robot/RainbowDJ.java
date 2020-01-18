/*
    This class includes:
    -rainbowDJEncoder

*/

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;

public class RainbowDJ {

    // Encoder declarations
    private final Encoder rainbowDJEncoder;

   // rainbowDJSpinner

    // Class initializer
    public BallChucker9000(int rainbowDJEncoder) {
    
        // Encoders
        rainbowDJEncoder = new Encoder(rainbowDJEncoderChannelA, rainbowDJEncoderChannelB);

    }

    // Setters

    // Getters

    // Encoder
    public double getRotatorEncoder() {
        return rotatorEncoder.get();
    }

    public double getFlywheelEncoder() {
        return flywheelEncoder.get();
    }

    // Limit switch 
    public boolean getRotatorSwitch() {
        return rotatorSwitch.get();
    }

}