package fhantom.socket.test.socketserver.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Nimesha Buddhika on 8/12/2019 6:26 PM
 */
@Component
public class UserList {

    private static final Map<String, SocketDto> users = new HashMap<>();

    public void add(String userId, SocketDto socketDto) {
        users.put(userId, socketDto);
    }

    public SocketDto get(String userId) {
        return users.get(userId);
    }

    public void remove(String userId) {
        users.remove(userId);
    }

    public Set<String> keySet() {
        return users.keySet();
    }
}
