package duke.parser;

import duke.commands.Command;
import duke.commands.DeadlineCommand;
import duke.commands.DeleteCommand;
import duke.commands.EndCommand;
import duke.commands.EventCommand;
import duke.commands.HelpCommand;
import duke.commands.ListCommand;
import duke.commands.MarkCommand;
import duke.commands.TodoCommand;

import duke.data.exception.DukeException;


public class Parser {

    public static Command parseCommand(String input) throws DukeException {

        String[] commandInput = input.split(" ");
        switch (commandInput[0]) {
            case "bye":
                return new EndCommand();
            case "list":
                return new ListCommand();
            case "delete":
                return new DeleteCommand();
            case "mark":
                return new MarkCommand(true);
            case "unmark":
                return new MarkCommand(false);
            case "todo":
                if (commandInput.length < 2) {
                    System.out.println("     ☹ OOPS!!! The description of a todo cannot be empty.");
                    return new HelpCommand();
                } else {
                    return new TodoCommand();
                }
            case "deadline":
                return new DeadlineCommand();
            case "event":
                return new EventCommand();
            case "help":
                return new HelpCommand();
            default:
                throw new DukeException("     ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}