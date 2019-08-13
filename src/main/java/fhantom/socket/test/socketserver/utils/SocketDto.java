package fhantom.socket.test.socketserver.utils;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Nimesha Buddhika on 8/13/2019 10:32 AM
 */
public class SocketDto {
    private Socket socket;
    private PrintWriter printWriter;

    public SocketDto(Socket socket, PrintWriter printWriter) {
        this.socket = socket;
        this.printWriter = printWriter;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
}
