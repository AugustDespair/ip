import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {

    private ArrayList<Task> taskList;
    private File file;
    private Scanner inputScanner;

    public static final String PATH = "./data/";

    public Duke() {
        this.taskList = new ArrayList<>();
        this.inputScanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Hello I'm Duke\n What can I do for you?");
        setup();

        load();

        while (inputScanner.hasNextLine()) {
            System.out.println();
            String userInput = inputScanner.nextLine();

            try {
                if (userInput.equals("bye")) return;
                userInputHandler(userInput);
                save();
            } catch(DukeException dukeEx) {
                System.out.println(dukeEx.getMessage());
            }
        }
    }

    private void setup() {
        File directory = new File(PATH);

        try {

            if (!directory.exists()) {
                directory.mkdir();
            }

            file = new File(PATH, "duke.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage() +
                    ((directory.exists()) ? "Yes" : "NO")
                    +
                    ((this.file.exists()) ? "File works ? " : "FML LOL"));
        }


    }

    private void load() {
        try {
            Scanner fileScanner = new Scanner(this.file);

            while(fileScanner.hasNext()) {
                String[] taskString = fileScanner.nextLine().split(",");
                String taskType = taskString[0];

                switch(taskType) {
                    case "T":
                        Task todo = new ToDo(taskString[2], taskString[1].equals("Y"));
                        this.taskList.add(todo);
                        break;
                    case "E":
                        Task event = new Event(taskString[2], taskString[3], taskString[1].equals("Y"));
                        this.taskList.add(event);
                        break;
                    case "D":
                        Task deadline = new Deadline(taskString[2], taskString[3], taskString[1].equals("Y"));
                        this.taskList.add(deadline);
                        break;
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void save() {
        try {
            FileWriter fileWriter = new FileWriter(this.file);

            for (Task task : this.taskList) {
                fileWriter.write(task.savedString() + "\n");
            }

            fileWriter.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void userInputHandler(String userInput) throws DukeException{
        String[] splitSS = userInput.split(" ");
        switch (splitSS[0]) {
            case "bye":
                terminate();
                return;
            case "list":
                list();
                break;
            case "delete":
                delete(userInput);
                break;
            case "mark":
                changeMarkStatus(userInput, true);
                break;
            case "unmark":
                changeMarkStatus(userInput, false);
                break;
            case "todo":
                try {
                    todo(userInput);
                    break;
                } catch (DukeException exception) {
                    System.out.println(exception.getMessage());
                    break;
                }
            case "deadline":
                deadline(userInput);
                break;
            case "event":
                event(userInput);
                break;
            default:
                throw new DukeException("     ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }


    }

    private void terminate() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    private void list() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); ++i) {
            System.out.printf(" %d. %s\n", i + 1, taskList.get(i));
        }
    }

    private void changeMarkStatus(String input, boolean mark) {
        int indexOfTask = Integer.parseInt(input.split((" "))[1]) - 1;

        if (mark) {
            this.taskList.get(indexOfTask).completed();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            this.taskList.get(indexOfTask).uncompleted();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.printf("\t%s\n", this.taskList.get(indexOfTask));
    }

    private void todo(String input) throws DukeException {

        String[] splitInput = input.split(" ");

        if (splitInput.length < 2) {
            throw new DukeException("     ☹ OOPS!!! The description of a todo cannot be empty.");
        }

        System.out.println("Got it. I've added this task");
        StringBuilder todo = new StringBuilder();

        for (int i = 1; i < splitInput.length; ++i) {
            todo.append(' ');
            todo.append(splitInput[i]);
        }

        ToDo todoTask = new ToDo(todo.toString());
        taskList.add(todoTask);
        System.out.printf("\t %s\n", todoTask);
        System.out.printf("Now you have %d tasks in the list.\n", this.taskList.size());
    }

    private void deadline(String input) {
        String[] splitInput = input.split(" ");
        System.out.println("Got it. I've added this task");
        StringBuilder deadline = new StringBuilder();

        for (int i = 1; i < splitInput.length; ++i) {
            if (splitInput[i].equals("/by")) break;
            deadline.append(" " + splitInput[i]);
        }

        String date = input.split("/by")[1].trim();
        Deadline deadlineTask = new Deadline(deadline.toString(), date);
        taskList.add(deadlineTask);
        System.out.printf("\t %s\n", deadlineTask);
        System.out.printf("Now you have %d tasks in the list.\n", this.taskList.size());
    }

    private void event(String input) {
        String[] splitInput = input.split(" ");
        System.out.println("Got it. I've added this task");
        StringBuilder event = new StringBuilder();

        for (int i = 1; i < splitInput.length; ++i) {
            if (splitInput[i].equals("/at")) break;
            event.append(" " + splitInput[i]);
        }

        Event eventTask = new Event(event.toString(), input.split("/at")[1].trim());
        taskList.add(eventTask);
        System.out.printf("\t %s\n", eventTask);
        System.out.printf("Now you have %d tasks in the list.\n", this.taskList.size());
    }

    private void delete(String input) {
        int indexToDelete = Integer.parseInt(input.split(" ")[1]) - 1;

        System.out.println("Noted. I've removed this task:");
        System.out.printf("\t%s\n", this.taskList.get(indexToDelete));
        this.taskList.remove(indexToDelete);
        System.out.printf("Now you have %d tasks in the list.\n", this.taskList.size());
    }
}
