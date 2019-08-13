package fhantom.socket.test.socketserver.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Nimesha Buddhika on 8/12/2019 6:20 PM
 */

public class ClientHandler implements Runnable {

    BufferedReader reader;
    Socket clientSocket;
    PrintWriter printWriter;
    private String usereId = "";

    public ClientHandler(Socket socket, PrintWriter writer) {
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
        try {
            if ((message = reader.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(message);

                if (jsonObject.getString("message").equals("reg")) {
                    usereId = jsonObject.getString("user");
                    UserList.users.put(usereId, new SocketDto(clientSocket, printWriter));
                    //break;
                }

                //cWriter.println(message);
                //cWriter.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            UserList.users.remove(usereId);
        }
    }
}
