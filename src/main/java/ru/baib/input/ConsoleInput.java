package ru.baib.input;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class ConsoleInput implements Input {

    private Scanner scanner = new Scanner(System.in);
    private static final Logger LOG = LogManager.getLogger(ConsoleInput.class.getName());

    @Override
    public String askStr(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    @Override
    public int askInt(String question) {
        return Integer.parseInt(askStr(question));
    }

    @Override
    public int askInt(String question, int max) {
        boolean invalid = true;
        int select = -1;
        do {
            try {
                select = askInt(question);
                if (select >= 0 && select < max) {
                    invalid = false;
                } else {
                    System.out.println("Please select key from menu");
                }
            } catch (NumberFormatException e) {
                LOG.error(e);
            }
        } while (invalid);
        return select;
    }
}
