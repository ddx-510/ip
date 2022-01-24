package duke.command;

import duke.helptool.Storage;
import duke.helptool.TaskList;
import duke.helptool.Ui;

/**
 * The type Exit command.
 */
public class ExitCommand extends Command{

    /**
     * Instantiates a new Exit command.
     */
    public ExitCommand(){

    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.bye();
        ui.closeReading();
    }
}
