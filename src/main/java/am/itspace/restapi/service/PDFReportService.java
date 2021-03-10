package am.itspace.restapi.service;

import am.itspace.restapi.model.LastLogin;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class PDFReportService {

    String basePath = "E:\\Новая папка\\report.pdf";

    public void report(List<LastLogin> lastLogin) throws FileNotFoundException, DocumentException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(basePath));
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk(String.valueOf(lastLogin), font);
        document.close();
    }

}


