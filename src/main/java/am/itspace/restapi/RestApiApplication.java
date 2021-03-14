package am.itspace.restapi;

import am.itspace.restapi.service.PDFReportService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PDFReportService pdfReportService;

    @Override
    public void run(String... args) throws Exception {
        pdfReportService.reportUsersLastLogin();
    }
}
