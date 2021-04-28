package app;

import app.user.service.SalesStaffService;
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
    private final SalesStaffService salesStaffService;
    private final Environment environment;

    @Autowired
    public App(UserService userService, SalesStaffService salesStaffService, Environment environment) {
        this.userService = userService;
        this.salesStaffService = salesStaffService;
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {

    }

}
