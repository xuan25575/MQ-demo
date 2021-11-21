package cn.hy.deleyed;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/tree/v3.8.0
 */
@SpringBootApplication
public class DelayedApp {
    public static void main(String[] args) {
        SpringApplication.run(DelayedApp.class,args);
    }
}
