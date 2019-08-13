package fhantom.socket.test.socketserver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Nimesha Buddhika on 8/12/2019 6:08 PM
 */
@Component
public class SocketUtils {
    private static final Logger logger = LoggerFactory.getLogger(SocketUtils.class);
    private static final int SERVER_PORT = 2000;
    private static ServerSocket serverSocket;

    private final UserList userList;

    @Autowired
    public SocketUtils(UserList userList) {
        this.userList = userList;
    }


    @PostConstruct
    public void init() throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (serverSocket == null) {
                        serverSocket = new ServerSocket(SERVER_PORT);
                        logger.info("Socket started on port : {}", SERVER_PORT);
                        while (true) {
                            Socket clientSocket = serverSocket.accept();
                            PrintWriter pWriter = new PrintWriter(clientSocket.getOutputStream());

                            ClientHandler clientHandler = new ClientHandler(userList);
                            clientHandler.setConfigs(clientSocket, pWriter);
                            Thread listener = new Thread(clientHandler);
                            listener.start();
                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();


        new Thread(() -> checkDisconnectUsers()).start();
    }

    private void checkDisconnectUsers() {
        try {
            while (true) {
                logger.info("Disconnect user scheduler running | User count : {}", userList.keySet().size());
                for (String userId : userList.keySet()) {
                    SocketDto socketDto = userList.get(userId);
                    try {
                        socketDto.getSocket().getInputStream().read();
                    } catch (Exception e) {
                        logger.error("User {} is disconnected", userId);
                        userList.remove(userId);
                    }
                }
                Thread.sleep(1000 * 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
