package ru.baib.action;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.baib.input.Input;
import ru.baib.model.*;

import java.io.*;
import java.net.Socket;

public class RegisterAction implements Action {

    private Socket socket;
    private static final Logger LOG = LogManager.getLogger(RegisterAction.class.getName());

    public RegisterAction(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String name() {
        return "--- Register ---";
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
            System.out.println(resp.getStatus());
        } catch (IOException | ClassNotFoundException e) {
            LOG.error(e);
        }
    }

}
