package duke.ui;

import java.util.Scanner;

public class Ui {
    private String userInput;
    private Scanner scanner;

    public Ui(Scanner scanner) {
        this.scanner = scanner;

    }

    public void greet() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello I'm Duke\n What can I do for you?");
    }

    public String receiveInput() {
        String input = this.scanner.nextLine();
        this.userInput = input;
        return input;
    }

    public String userString() {
        return this.userInput;
    }

}