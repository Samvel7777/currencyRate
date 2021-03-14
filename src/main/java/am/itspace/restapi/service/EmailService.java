package am.itspace.restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("${spring.mail.password}")
    private String mailPassword;
    @Value("${spring.mail.host}")
    private String mailSmtpHost;
    @Value("${spring.mail.port}")
    private String mailSmtpPort;

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;

    @Async
    public void send(String subject, String messageText, String email) {
        try {

            Session session = Session.getInstance(getMailProperties(mailUsername, mailPassword));
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setSubject(subject, "UTF-8");
            message.setText(messageText, "UTF-8");

            message.setFrom(new InternetAddress(mailUsername));
            InternetAddress toAddress = new InternetAddress(email);
            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(subject);
            message.setText(messageText);
            Transport transport = session.getTransport("smtp");
            transport.connect(mailSmtpHost, mailUsername, mailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ignored) {
        }
    }

    @Async
    public void sendReportMail(String subject, String messageText, String email, String file) {
        try {

            Session session = Session.getInstance(getMailProperties(mailUsername, mailPassword));
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setSubject(subject, "UTF-8");
            message.setText(messageText, "UTF-8");

            message.setFrom(new InternetAddress(mailUsername));
            InternetAddress toAddress = new InternetAddress(email);
            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(subject);
            message.setText(messageText);
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageText);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            String fileName = "report.pdf";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport transport = session.getTransport("smtp");
            transport.connect(mailSmtpHost, mailUsername, mailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
        }
    }

    private Properties getMailProperties(String email, String password) {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", mailSmtpHost);
        props.put("mail.smtp.user", email);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", mailSmtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.quitwait", "false");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.debug", "true");
        return props;
    }

}
