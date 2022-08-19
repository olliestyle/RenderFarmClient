package ru.baib.action;

import ru.baib.input.Input;
import ru.baib.model.ApplicationState;

public class ExitAction implements Action {
    @Override
    public String name() {
        return "--- Exit ---";
    }

    @Override
    public boolean execute(Input input, ApplicationState applicationState) {
        return false;
    }
}
