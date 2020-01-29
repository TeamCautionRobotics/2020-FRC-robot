package frc.robot.autonomous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mission {

    protected int step = 0;

    protected ArrayList<Command> commands = new ArrayList<Command>();

    private String name;
    public final boolean enableControls;

    public Mission(String name, boolean enableControls, List<Command> commands) {
        this.name = name;
        this.enableControls = enableControls;
        this.commands.addAll(commands);
    }

    public Mission(String name, boolean enableControls, Command... commands) {
        this(name, enableControls, Arrays.asList(commands));
    }

    public Mission(String name, List<Command> commands) {
        this(name, false, commands);
    }

    public Mission(String name, Command... commands) {
        this(name, false, commands);
    }


    public String getName() {
        return name;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void reset() {
        step = 0;
        for (Command command : commands) {
            command.reset();
        }
    }

    /**
     * Executes the mission, must be called repeatedly
     * 
     * @return false if still running, true if the mission is complete.
     */
    public boolean run() {
        if (step < commands.size()) {
            if (commands.get(step).run()) {
                step++;
                // Step 1 is first for display purposes:
                System.out.format("Command %d/%d complete.%n", step, commands.size());
            }
            return false;
        } else {
            return true;
        }
    }

    public void add(Command command) {
        commands.add(command);
    }
}
