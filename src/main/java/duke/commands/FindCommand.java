package duke.commands;

import duke.data.TaskList;
import duke.storage.Storage;
import duke.task.Task;
import duke.ui.Ui;

/**
 * Command to find whether a command with the task name exists.
 */
public class FindCommand extends Command {

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        StringBuilder sb = new StringBuilder();
        String wordToFind = ui.userString().split(" ")[1];
        sb.append("     Here are the matching tasks in your list:");
        int counter = 1;

        for (Task task : taskList.getAllTasks()) {
            if (task.getTaskName().contains(wordToFind)) {
                sb.append(String.format("%d. %s\n", counter, task));
                ++counter;
            }
        }
        return sb.toString();
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
