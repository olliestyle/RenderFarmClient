package ru.baib.action;

import ru.baib.input.Input;
import ru.baib.model.ApplicationState;
import ru.baib.model.ClientMessage;
import ru.baib.model.ServerResponse;
import ru.baib.model.User;

import java.io.*;
import java.net.Socket;

public class LoginAction implements Action {

    private Socket socket;

    public LoginAction(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String name() {
        return "--- Log in ---";
    }

    @Override
    public boolean execute(Input input, ApplicationState applicationState) {
        String username = input.askStr("Enter username: ");
        String password = input.askStr("Enter password: ");
        User user = new User(username, password);
        applicationState.setLogRegUser(user);
        ClientMessage clientMessage = new ClientMessage(this.getClass().getName(), applicationState);
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
            clientMessage.getApplicationState().setCurrentUser(resp.getUser());
            System.out.println(resp.getStatus());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
