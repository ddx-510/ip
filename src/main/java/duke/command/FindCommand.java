package duke.command;

import duke.helptool.Storage;
import duke.helptool.TaskList;
import duke.helptool.Ui;

/**
 * The type Find command.
 */
public class FindCommand extends Command{
    /**
     * The Find detail.
     */
    String findDetail;

    /**
     * Instantiates a new Find command.
     *
     * @param findDetail the find detail
     */
    public FindCommand(String findDetail){
        this.findDetail = findDetail;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matchList = new TaskList();
        for (int i = 0; i < tasks.getSize(); i++) {
            if (tasks.getTask(i).getDescription().contains(findDetail)){
                matchList.addTask(tasks.getTask(i));
            }
        }
        ui.showMatchedTask(matchList);
    }
}
