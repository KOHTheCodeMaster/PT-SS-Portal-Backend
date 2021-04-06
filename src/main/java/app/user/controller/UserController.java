package app.user.controller;

import app.user.dto.UserDTO;
import app.user.exceptions.UserException;
import app.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    Logger logger;
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean loginUser(@RequestBody UserDTO userDTO) throws UserException {
        logger.info("Login request for User {} with password {}", userDTO.getEmailId(), userDTO.getPassword());
        return userService.loginUser(userDTO);
    }

}
