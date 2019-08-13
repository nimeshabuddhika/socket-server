package fhantom.socket.test.socketserver.controller;

import fhantom.socket.test.socketserver.utils.UserList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.PrintWriter;

/**
 * @author Nimesha Buddhika on 8/12/2019 5:57 PM
 */
@Controller
public class BaseController {

    @GetMapping("/{user}/{message}")
    public ResponseEntity<Object> test(@PathVariable("user") String user, @PathVariable("message") String message) {
        PrintWriter printWriter = UserList.users.get(user).getPrintWriter();
        if (printWriter != null) {
            printWriter.println(message);
            printWriter.flush();
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
