package fhantom.socket.test.socketserver.controller;

import fhantom.socket.test.socketserver.utils.SocketDto;
import fhantom.socket.test.socketserver.utils.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Nimesha Buddhika on 8/12/2019 5:57 PM
 */
@Controller
public class BaseController {


    private final UserList userList;

    @Autowired
    public BaseController(UserList userList) {
        this.userList = userList;
    }

    @GetMapping("/{user}/{message}")
    public ResponseEntity<Object> test(@PathVariable("user") String user, @PathVariable("message") String message) {
        SocketDto socketDto = userList.get(user);
        if (socketDto != null) {
            socketDto.getPrintWriter().println(message);
            socketDto.getPrintWriter().flush();
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
