package com.codecool.idontspeakjava.queststore.views;

import de.vandermeer.asciitable.AsciiTable;

import java.util.List;
import java.util.Scanner;

public abstract class UserView {

    static final String CLEAR_CONSOLE = "\033\143";

    private Scanner scanner;

    UserView() {
        scanner = new Scanner(System.in);
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void continuePrompt() {
        System.out.print(Colors.PURPLE_BOLD);
        printTable(new String[][]{{"Hit enter to continue"}});
        getUserInput();
    }

    void printTable(List<String> rows) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        for (int i = 0; i < rows.size(); i++) {
            table.addRow(String.valueOf(i + 1), rows.get(i));
            table.addRule();
        }
        System.out.println(table.render());

    }

    void printTable(String[][] rows) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        for (String[] row : rows) {
            table.addRow((Object[]) row);
            table.addRule();
        }
        System.out.println(table.render());
    }

    public void clearScreen() {
        System.out.print(CLEAR_CONSOLE);
    }
}