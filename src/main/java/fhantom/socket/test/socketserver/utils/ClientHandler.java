package fhantom.socket.test.socketserver.utils;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Nimesha Buddhika on 8/12/2019 6:20 PM
 */
@Component
public class ClientHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private BufferedReader reader;
    private Socket clientSocket;
    private PrintWriter printWriter;

    private final UserList userList;


    public ClientHandler(UserList userList) {
        this.userList = userList;
    }

    public void setConfigs(Socket socket, PrintWriter writer) {
        printWriter = writer;
        try {
            clientSocket = socket;
            InputStreamReader inputReader = new InputStreamReader(clientSocket.getInputStream());
            reader = new BufferedReader(inputReader);
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        String message = "";
        String userId = "";
        try {
            if ((message = reader.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(message);
                if (jsonObject.getString("type").equals("register")) {
                    userId = jsonObject.getString("user");
                    logger.info("User {} is connected to socket server", userId);
                    userList.add(userId, new SocketDto(clientSocket, printWriter));
                }
            }
        } catch (Exception ex) {
            userList.remove(userId);
            logger.error("User {} disconnected unexpectedly | Error : {}", userId, ex.getMessage());
        }
    }
}
