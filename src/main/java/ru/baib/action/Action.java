package ru.baib.action;

import ru.baib.input.Input;
import ru.baib.model.ApplicationState;

import java.net.Socket;

public interface Action {

    String name();
    boolean execute(Input input, ApplicationState applicationState);
}
