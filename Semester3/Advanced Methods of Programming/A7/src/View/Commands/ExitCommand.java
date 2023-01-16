package View.Commands;

import View.Commands.Command;

public class ExitCommand extends Command {
    @Override
    public void execute() {
        System.exit(0);
    }

    public ExitCommand(String key, String description) {
        super(key, description);
    }

}
