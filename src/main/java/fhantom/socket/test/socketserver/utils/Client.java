package fhantom.socket.test.socketserver.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Nimesha Buddhika on 8/12/2019 6:40 PM
 */
public class Client {
    private static PrintWriter writer;
    private static Socket socket;
    private static BufferedReader reader;

    public static void main(String[] args) {

        try {
            //socket = new Socket("localhost", 2000);

            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 2000), 0);

            String message = "";
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            JSONObject payload = new JSONObject();
            payload.put("message", "reg");
            payload.put("user", "1");

            writer.println(payload.toString());
            writer.flush();

            while ((message = reader.readLine()) != null) {

                System.out.println(message);

                //cWriter.println(message);
                //cWriter.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedReader getReader() throws IOException {
        if (reader == null)
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return reader;
    }

    private PrintWriter getWriter() throws IOException {
        if (writer == null)
            writer = new PrintWriter(socket.getOutputStream());
        return writer;
    }
}
