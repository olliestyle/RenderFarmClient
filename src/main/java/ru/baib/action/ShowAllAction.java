package ru.baib.action;

import ru.baib.input.Input;
import ru.baib.model.ApplicationState;
import ru.baib.model.ClientMessage;
import ru.baib.model.ServerResponse;
import ru.baib.model.Task;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ShowAllAction implements Action {

    private Socket socket;

    public ShowAllAction(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String name() {
        return "--- Show all tasks ---";
    }

    @Override
    public boolean execute(Input input, ApplicationState applicationState) {
        ClientMessage clientMessage = new ClientMessage(this.getClass().getName(), applicationState);
        List<Task> list = sendMessage(clientMessage);
        for (Task task : list) {
            System.out.println(task);
        }
        return true;
    }

    private List<Task> sendMessage(ClientMessage clientMessage) {
        List<Task> toReturn = new ArrayList<>();
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            OutputStream os = socket.getOutputStream();
            oos.writeObject(clientMessage);
            os.write(bos.toByteArray());
            os.flush();
            ServerResponse resp = (ServerResponse) new ObjectInputStream(socket.getInputStream()).readObject();
            toReturn.addAll(resp.getTaskList());
            System.out.println(resp.getStatus());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}
