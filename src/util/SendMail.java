package util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {

    private String fromEmail = "gvhoang30112004@gmail.com";
    private String password = "cdbpubumgnhvbtpc";

    public void SendMailVoucher(String toEmail, String subject, String body, List<File> files) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(fromEmail, "Galaxy Cinema"));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            if (files == null) {
                msg.setContent(body, "text/html; charset=utf-8");
            }
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            if (files != null) {
                //Attachments
                Multipart multipart = new MimeMultipart();

                //Content
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(body, "text/html; charset=utf-8");
                multipart.addBodyPart(bodyPart);
                for (File file : files) {
                    DataSource dataSource = new FileDataSource(file);
                    MimeBodyPart mimeBodyPart = new MimeBodyPart();
                    mimeBodyPart.setDataHandler(new DataHandler(dataSource));
                    mimeBodyPart.setFileName(file.getName());
                    multipart.addBodyPart(mimeBodyPart);
                }
                msg.setContent(multipart);
            }
            Transport.send(msg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("Error");
        }
    }
    public void sendMailResetPassword(String toEmail, String subject, String body){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(fromEmail, "Galaxy Cinema"));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(body, "text/html; charset=utf-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("Error");
        }
    }
}
