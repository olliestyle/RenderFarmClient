package ru.baib.action;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.baib.input.Input;
import ru.baib.model.ApplicationState;
import ru.baib.model.ClientMessage;
import ru.baib.model.ServerResponse;
import ru.baib.model.Task;

import java.io.*;
import java.net.Socket;

public class CreateAction implements Action {

    private Socket socket;
    private static final Logger LOG = LogManager.getLogger(CreateAction.class.getName());

    public CreateAction(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String name() {
        return "--- Create new task ---";
    }

    @Override
    public boolean execute(Input input, ApplicationState applicationState) {
        String taskName = input.askStr("Enter task name: ");
        ClientMessage clientMessage = new ClientMessage(this.getClass().getName(), applicationState, new Task(taskName));
        sendMessage(clientMessage);
        return true;
    }

    private void sendMessage(ClientMessage clientMessage) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            OutputStream os = socket.getOutputStream();
            oos.writeObject(clientMessage);
            os.write(bos.toByteArray());
            os.flush();
            ServerResponse resp = (ServerResponse) new ObjectInputStream(socket.getInputStream()).readObject();
            System.out.println(resp.getStatus());
        } catch (IOException | ClassNotFoundException e) {
            LOG.error(e);
        }
    }
}
