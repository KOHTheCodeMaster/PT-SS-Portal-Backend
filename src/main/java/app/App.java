package app;

import app.user.dto.SalesStaffDTO;
import app.user.dto.UserDTO;
import app.user.exceptions.SalesStaffException;
import app.user.exceptions.UserException;
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

        testLogin();
        testSalesStaffService();

    }

    private void testSalesStaffService() {

        logger.info("Testing Sales Staff");

        testSalesStaffFetchBySalesId(2);        //  Valid Entry
        testSalesStaffFetchBySalesId(999);      //  Invalid Entry

        testInsertIntoSalesStaff();             //  Insert New Sales Entry
        testDeleteFromSalesStaff(241);    //  Delete Entry with salesId 241

    }

    private void testLogin() {

        logger.info("Testing Login User");

//        loginUser(1);    //  Login Successful
//        loginUser(2);    //  Invalid Password
//        loginUser(3);    //  User Not Found
    }

    private void testDeleteFromSalesStaff(int salesId) {

        try {
            salesStaffService.deleteSalesById(salesId);
        } catch (SalesStaffException e) {
            logger.error("Failed to delete Sales due to Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void testInsertIntoSalesStaff() {

        SalesStaffDTO salesStaffDTO;
        try {

            logger.info("Inserting New Entry");
            salesStaffDTO = salesStaffService.fetchSalesStaffById(10);
            logger.info(salesStaffService.insertIntoSalesStaff(salesStaffDTO) + " Inserted!");
            logger.info("Insertion Complete.");

        } catch (SalesStaffException e) {
            logger.error("Sales Staff Failed Insertion due to - Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void testSalesStaffFetchBySalesId(int salesId) {

        try {

            SalesStaffDTO salesStaffDTO = salesStaffService.fetchSalesStaffById(salesId);
            logger.info("Existing Id: " + salesStaffDTO.getSalesId());

        } catch (SalesStaffException e) {
            logger.info("Sales Staff Failed with Exception - " + e.getMessage());
//            e.printStackTrace();
        }

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
