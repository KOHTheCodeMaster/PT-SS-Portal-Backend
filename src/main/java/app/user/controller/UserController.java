package app.user.controller;

import app.user.dto.UserDTO;
import app.user.exceptions.UserException;
import app.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
//        demoLoginUser();
    }

    /**
     * Authenticate User by matching the credentials provided by user in POST Request Body
     * with the original credentials of the user stored in User Table in DB
     *
     * @param userDTO user credentials which needs to be authenticated
     * @return If successful then true, Otherwise false
     * @throws UserException If either of the userDTO, emailId, password is null
     */
    @PostMapping(value = "/login")
    public boolean loginUser(@RequestBody UserDTO userDTO) throws UserException {
        LOGGER.info("Login request for User {} with password {}", userDTO.getEmailId(), userDTO.getPassword());
        return userService.loginUser(userDTO);
    }

    /**
     * Retrieve UserDTO by providing emailId in the url
     *
     * @param emailId id corresponding to the user record of the User Table
     * @return UserDTO for the given emailId
     * @throws UserException If either of the userDTO, emailId, password is null
     */
    @GetMapping(value = "/user/{emailId}")
    public ResponseEntity<UserDTO> getUserByEmailId(@PathVariable String emailId) throws UserException {

//        LOGGER.info("Requesting user for id: {}", emailId);

        UserDTO userDTO = userService.getUserByEmailId(emailId);
        userDTO.setPassword("********");   //  Explicitly Override password when requesting for userDTO

        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

    private void demoLoginUser() {

        LOGGER.info("Demo - Login User");

        loginDemo(1);    //  Login Successful
        loginDemo(2);    //  Invalid Password
        loginDemo(3);    //  User Not Found

    }

    private void loginDemo(int temp) {

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
            LOGGER.info("Login " + (b ? "Successful" : "Failure - Invalid Password"));

        } catch (UserException e) {
            LOGGER.error("Login Failed with Exception - " + e.getMessage());
//            e.printStackTrace();
        }

    }


}
