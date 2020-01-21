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
    public RainbowDJ(int rainbowDJEncoderChannelA, int rainbowDJEncoderChannelB) {
    
        // Encoders
        rainbowDJEncoder = new Encoder(rainbowDJEncoderChannelA, rainbowDJEncoderChannelB);

    }

    // Setters

    // Getters

    public double getRainbowDJEncoderDistance() {
        return rainbowDJEncoder.getDistance();
    }

    public double getRainbowDJEncoderRate() {
        return rainbowDJEncoder.getRate();
    }

}