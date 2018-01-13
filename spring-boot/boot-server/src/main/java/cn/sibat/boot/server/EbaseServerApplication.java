package cn.sibat.boot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EbaseServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EbaseServerApplication.class, args);
    }
}
