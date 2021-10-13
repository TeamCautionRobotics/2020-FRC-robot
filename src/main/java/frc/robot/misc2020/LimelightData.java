package frc.robot.misc2020;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightData {

    private void setVar(String var, int data) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry(var).setNumber(data);
    }

    public double getTv() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
      }
    
    public double getTx() {
      return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }
  
    public double getTy() {
      return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    }
  
    public double getTa() {
      return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }
  
    public double getTs() {
      return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }
  
    public void setLedMode(int mode) {
      setVar("ledMode", mode);
    }
  
    public void setPipeline(int pipeline) {
      setVar("pipeline", pipeline);
    }

}