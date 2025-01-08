package ie.atu.transferapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TransferAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferAppApplication.class, args);
    }

}
