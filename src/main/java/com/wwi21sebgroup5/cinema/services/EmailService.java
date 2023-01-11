package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Booking;
import com.wwi21sebgroup5.cinema.entities.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final static String CONFIRM_BOOKING = "confirmBooking.html";
    private final static String CONFIRM_REGISTRATION = "confirmRegistration.html";
    private final static String CONFIRM_TOKEN = "confirmToken.html";

    private final static String BOOKING_SUBJECT = "Vielen Dank für deine Buchung bei Cineverse!";

    private final static String REGISTRATION_SUBJECT = "Willkommen bei Cineverse!";

    private final static String TOKEN_SUBJECT = "Deine E-Mail wurde bestätigt!";

    @Value("classpath:/static/cinemaGroupFiveLogo.ico")
    private Resource logoResource;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String sender;

    private void sendMail(String recipient, String subject, String msgBody) {
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setText(msgBody, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.addInline("logo.ico", logoResource);

            // Sending the mail
            javaMailSender.send(mimeMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendRegistrationConfirmation(User user, String tokenUrl) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("confirmationToken", tokenUrl);
        context.setVariable("frontendUrl", frontendUrl);
        String msgBody = templateEngine.process(CONFIRM_REGISTRATION, context);

        sendMail(user.getEmail(), REGISTRATION_SUBJECT, msgBody);
    }

    public void sendTokenConfirmation(User user) {
        Context context = new Context();
        context.setVariable("user", user);
        String msgBody = templateEngine.process(CONFIRM_TOKEN, context);

        sendMail(user.getEmail(), TOKEN_SUBJECT, msgBody);
    }

    public void sendBookingConfirmation(User user, Booking booking) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("booking", booking);
        String msgBody = templateEngine.process(CONFIRM_BOOKING, context);

        sendMail(user.getEmail(), BOOKING_SUBJECT, msgBody);
    }

}
