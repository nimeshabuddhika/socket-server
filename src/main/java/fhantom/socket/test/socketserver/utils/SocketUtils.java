package fhantom.socket.test.socketserver.utils;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private static String jsonResponse;
    private static ServerSocket serverSocket;

    private final UserList userList;

    static {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "response");
        jsonResponse = jsonObject.toString();
    }

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
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    private void checkDisconnectUsers() {
        try {
            logger.info("Disconnect user scheduler running | User count : {}", userList.keySet().size());
            for (String userId : userList.keySet()) {
                SocketDto socketDto = userList.get(userId);
                try {
                    if (!ping(socketDto).equals("OK"))
                        close(userId, socketDto);
                } catch (Exception e) {
                    close(userId, socketDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void close(String userId, SocketDto socketDto) throws IOException {
        logger.error("User {} is disconnected", userId);
        userList.remove(userId);
        socketDto.getPrintWriter().close();
        socketDto.getBufferedReader().close();
        socketDto.getSocket().close();
    }

    private String ping(SocketDto socketDto) throws IOException {
        socketDto.getPrintWriter().println(jsonResponse);
        socketDto.getPrintWriter().flush();
        if (socketDto.getBufferedReader() == null)
             socketDto.setBufferedReader(new BufferedReader(new InputStreamReader(socketDto.getSocket().getInputStream())));
        return socketDto.getBufferedReader().readLine();
    }
}
