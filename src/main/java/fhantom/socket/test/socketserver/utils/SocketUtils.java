package fhantom.socket.test.socketserver.utils;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nimesha Buddhika on 8/12/2019 6:08 PM
 */
@Component
public class SocketUtils {

    private static ServerSocket serverSocket;

    private static final List<Socket> socketList = new ArrayList<>();

    @PostConstruct
    public void init() throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(2000);
                    System.out.println("Socket started : " + 2000);
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        PrintWriter pWriter = new PrintWriter(clientSocket.getOutputStream());

                        Thread listener = new Thread(new ClientHandler(clientSocket, pWriter));
                        listener.start();
                    }
                } catch (Exception e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        for (String userId : UserList.users.keySet()) {
                            SocketDto socketDto = UserList.users.get(userId);
                            try {
                                socketDto.getSocket().getInputStream().read();
                            } catch (Exception e) {
                                System.out.println("ERROR writing data to socket !!!");
                                UserList.users.remove(userId);
                            }
                        }
                        Thread.sleep(1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }
}
