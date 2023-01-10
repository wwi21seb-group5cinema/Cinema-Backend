package com.wwi21sebgroup5.cinema.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(String recipient, String subject, String msgBody) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try {
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            mailMessage.setText(msgBody);
            mailMessage.setSubject(subject);

            javaMailSender.send(mailMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendMail(String recipient, String subject, String msgBody, String attachment) {
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setText(msgBody);
            mimeMessageHelper.setSubject(subject);

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(attachment));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
