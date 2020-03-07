/*
    This class includes:
    -rainbowDJEncoder
    -rainbowDJColorSensor
    -rainbotDJMotor
*/

package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class RainbowDJ {

    // ESCs
    private final SpeedController rainbowDJMotor;

    // Encoder declarations
    private final Encoder rainbowDJEncoder;

    // Color Sensor
    private final ColorSensorV3 rainbowDJColorSensor;

    // Class initializer
    public RainbowDJ(SpeedController rainbowDJMotorPort, int rainbowDJEncoderChannelA, int rainbowDJEncoderChannelB,
            I2C.Port colorSensorPort) {

        // ESCs
        this.rainbowDJMotor = rainbowDJMotorPort;

        // Encoders
        rainbowDJEncoder = new Encoder(rainbowDJEncoderChannelA, rainbowDJEncoderChannelB);

        // Color Sensor
        rainbowDJColorSensor = new ColorSensorV3(colorSensorPort);
    }

    // Setters
    // Positive power moves control panel clockwise
    public void moveRainbowDJMotor(double power) {
        rainbowDJMotor.set(power);
    }

    // Getters
    public double getDistance() {
        return rainbowDJEncoder.getDistance();
    }

    public double getSpeed() {
        return rainbowDJEncoder.getRate();
    }
}