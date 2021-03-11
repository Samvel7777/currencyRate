package am.itspace.restapi.service;

import am.itspace.restapi.model.LastLogin;
import am.itspace.restapi.model.User;
import am.itspace.restapi.repository.LastLoginRepository;
import am.itspace.restapi.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PDFReportService {

    @Value("${pdf.report.path}")
    private String basePath;

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final LastLoginRepository lastLoginRepository;

    @Scheduled(fixedRate = 1000)
    private void reportUsersLastLogin() {
        List<User> all = userRepository.findAll();
        for (User user : all) {
            List<LastLogin> byUserId = lastLoginRepository.findByUserId(user.getId());
            try {
                report(byUserId, user);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    public void report(List<LastLogin> lastLogin, User user) throws FileNotFoundException, DocumentException {

        try {
            File filePath = new File(basePath);
            File file = File.createTempFile(user.getEmail() + "_" + System.currentTimeMillis(), ".pdf", filePath);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk(String.valueOf(lastLogin), font);

            PdfPTable table = new PdfPTable(9);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.setTotalWidth(550);
            table.setLockedWidth(true);

            table.addCell(new PdfPCell(new Phrase("User Id", font)));
            table.addCell(new PdfPCell(new Phrase("Id", font)));
            table.addCell(new PdfPCell(new Phrase("Ip", font)));
            table.addCell(new PdfPCell(new Phrase("Country Code", font)));
            table.addCell(new PdfPCell(new Phrase("Country Name", font)));
            table.addCell(new PdfPCell(new Phrase("Region Code", font)));
            table.addCell(new PdfPCell(new Phrase("Region Name", font)));
            table.addCell(new PdfPCell(new Phrase("City", font)));
            table.addCell(new PdfPCell(new Phrase("Login Date", font)));

            for (LastLogin lastLogins : lastLogin) {
                table.addCell(new PdfPCell(new Phrase(lastLogins.getUserId())));
                table.addCell(new PdfPCell(new Phrase(lastLogins.getId())));
                table.addCell(new PdfPCell(new Phrase(lastLogins.getIp())));
                table.addCell(new PdfPCell(new Phrase(lastLogins.getCountryCode())));
                table.addCell(new PdfPCell(new Phrase(lastLogins.getCountryName())));
                table.addCell(new PdfPCell(new Phrase(lastLogins.getRegionCode())));
                table.addCell(new PdfPCell(new Phrase(lastLogins.getRegionName())));
                table.addCell(new PdfPCell(new Phrase(lastLogins.getCity())));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(lastLogins.getLoginDate()))));
            }

            document.add(table);
            document.close();
            emailService.sendReportMail(user.getEmail(),"Dear " + user.getEmail(),", Your last logins - >", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


