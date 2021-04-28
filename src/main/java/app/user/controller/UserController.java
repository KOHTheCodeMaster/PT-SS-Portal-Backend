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

//        demoLoginUser();

    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean demoAttemptLoginUser(@RequestBody UserDTO userDTO) throws UserException {
        logger.info("Login request for User {} with password {}", userDTO.getEmailId(), userDTO.getPassword());
        return userService.loginUser(userDTO);
    }


    private void demoLoginUser() {

        logger.info("Demo - Login User");

        demoAttemptLoginUser(1);    //  Login Successful
        demoAttemptLoginUser(2);    //  Invalid Password
        demoAttemptLoginUser(3);    //  User Not Found

    }

    private void demoAttemptLoginUser(int temp) {

        UserDTO userDTO = new UserDTO();

        switch (temp) {
            case 1:
                userDTO.setEmailId("rudys@sermanisteel.co.id");
                userDTO.setPassword("123");
                break;
            case 2:
                userDTO.setEmailId("rudys@sermanisteel.co.id");
                userDTO.setPassword("gibberish");
                break;
            case 3:
                userDTO.setEmailId("unknown user");
                userDTO.setPassword("123");
                break;
            default:
                return;
        }


        try {

            boolean b = userService.loginUser(userDTO);
            logger.info("Login " + (b ? "Successful" : "Failure - Invalid Password"));

        } catch (UserException e) {
            logger.error("Login Failed with Exception - " + e.getMessage());
//            e.printStackTrace();
        }

    }


}
