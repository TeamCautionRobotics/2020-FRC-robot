package frc.robot.autonomous;

public interface Command {

    /**
     * Executes the command, must be called repeatedly
     * 
     * @return false if still running, true if the command is complete.
     */
    boolean run();

    void reset();

}
