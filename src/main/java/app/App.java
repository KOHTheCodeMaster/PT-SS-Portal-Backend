package app;

import app.user.dto.UserDTO;
import app.user.exceptions.UserException;
import app.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class App implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final Environment environment;

    @Autowired
    public App(UserService userService, Environment environment) {
        this.userService = userService;
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {

        loginUser(1);    //  Login Successful
        loginUser(2);    //  Invalid Password
        loginUser(3);    //  User Not Found

    }

    private void loginUser(int temp) {

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
            logger.info("Login Failed with Exception - " + e.getMessage());
//            e.printStackTrace();
        }

    }


}
