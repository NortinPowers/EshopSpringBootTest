package by.tms.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EshopSpringBootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopSpringBootTestApplication.class, args);
    }
}