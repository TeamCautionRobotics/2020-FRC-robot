/*
    This class includes:
    -rainbowDJEncoder

*/

package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class RainbowDJ {

    // Encoder declarations
    private final Encoder rainbowDJEncoder;

    //Color Sensor
    private final ColorSensorV3 colorSensorV3;

   // rainbowDJSpinner

    // Class initializer
    public RainbowDJ(int rainbowDJEncoderChannelA, int rainbowDJEncoderChannelB, I2C.Port colorSensorPort) {
    
        // Encoders
        rainbowDJEncoder = new Encoder(rainbowDJEncoderChannelA, rainbowDJEncoderChannelB);
        // Color Sensor
        colorSensorV3 = new ColorSensorV3(colorSensorPort);

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