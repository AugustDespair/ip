package duke.data;

import java.util.Scanner;

import duke.commands.Command;
import duke.data.TaskList;
import duke.data.exception.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.ui.Ui;

public class Duke {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    public Duke() {
        ;
        this.ui = new Ui(new Scanner(System.in));
        this.storage = new Storage();
        this.taskList = this.storage.load();
    }

    public void run() {
        this.ui.greet();
        boolean terminate = false;

        do {
            try {
                String userInput = this.ui.receiveInput();
                Command c = Parser.parseCommand(userInput);
                c.execute(taskList, ui, storage);
                terminate = c.isEnd();
            } catch (DukeException de) {
                System.out.println(de.getMessage());
            } catch (IndexOutOfBoundsException ioobe) {
                System.out.println("Index input is greater than todolist length");
            }
        } while (!terminate);
    }


}
