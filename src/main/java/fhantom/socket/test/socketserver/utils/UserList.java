package fhantom.socket.test.socketserver.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nimesha Buddhika on 8/12/2019 6:26 PM
 */
@Component
public class UserList {
    public static final Map<String, SocketDto> users = new HashMap<>();
}
    /*public void add(String userId, PrintWriter printWriter) {
        users.put(userId, printWriter);
    }

    public PrintWriter get(String userId) {
        return users.get(userId);
    }

    public void remove(String userId) {
        users.remove(userId);
    }
}*/
