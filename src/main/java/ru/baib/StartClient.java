package ru.baib;

import ru.baib.action.*;
import ru.baib.input.ConsoleInput;
import ru.baib.input.Input;
import ru.baib.model.ApplicationState;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class StartClient {
    public static void main(String[] args) {
        ApplicationState applicationState = new ApplicationState();
        Input input = new ConsoleInput();
        List<Action> actions = new ArrayList<>();
        try {
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
            actions.add(new CreateAction(socket));
            actions.add(new ShowAllAction(socket));
            actions.add(new LoginAction(socket));
            actions.add(new RegisterAction(socket));
            actions.add(new ExitAction());
            new StartClient().init(input, actions, applicationState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(Input input, List<Action> actions, ApplicationState applicationState) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ", actions.size());
            Action action = actions.get(select);
            run = action.execute(input, applicationState);
        }
    }

    private void showMenu(List<Action> actions) {
        System.out.println("Menu.");
        int index = 0;
        for (Action action: actions) {
            System.out.println(index + " " + action.name());
            index++;
        }
    }
}
