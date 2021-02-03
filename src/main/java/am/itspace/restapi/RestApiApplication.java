package am.itspace.restapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RestApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
