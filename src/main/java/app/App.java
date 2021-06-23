package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import stdlib.utils.MyTimer;

@SpringBootApplication
public class App {

    public static void main(String[] args) {

        MyTimer myTimer = new MyTimer();
        myTimer.startTimer();
        System.out.println("\nBegin.\n");

        SpringApplication.run(App.class, args);

        System.out.println("\nEnd.\n");
        myTimer.stopTimer(true);
    }

}
