package am.itspace.restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;

    @Async
    public void send(String to, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
    }

    @Async
    public void sendReportMail(String to, String subject, String message, String file) {

        MimeMessage messages = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(messages, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message);

            FileSystemResource pdfFile = new FileSystemResource(file);
            helper.addAttachment("Invoice", pdfFile);

            javaMailSender.send(messages);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
