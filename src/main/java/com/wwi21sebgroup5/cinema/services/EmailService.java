package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Booking;
import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Ticket;
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

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class EmailService {

    private final static String CONFIRM_BOOKING = "/email/confirmBooking.html";
    private final static String CONFIRM_REGISTRATION = "/email/confirmRegistration.html";
    private final static String CONFIRM_TOKEN = "/email/confirmToken.html";
    private final static String CHANGE_PASSWORD = "/email/sendPasswordChange.html";
    private final static String RESET_PASSWORD = "/email/sendPasswordReset.html";

    private final static String BOOKING_SUBJECT = "Vielen Dank für deine Buchung bei Cineverse!";
    private final static String REGISTRATION_SUBJECT = "Willkommen bei Cineverse!";
    private final static String TOKEN_SUBJECT = "Deine E-Mail wurde bestätigt!";
    private final static String CHANGE_PASSWORD_SUBJECT = "Dein Passwort wurde geändert!";
    private final static String RESET_PASSWORD_SUBJECT = "Dein Passwort wurde zurückgesetzt!!";

    @Value("classpath:/static/cinemaGroupFiveLogo.ico")
    private Resource logoResource;

    @Autowired
    private QrCodeService qrCodeService;
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
        String msgBody = templateEngine.process(CONFIRM_REGISTRATION, context);

        sendMail(user.getEmail(), REGISTRATION_SUBJECT, msgBody);
    }

    public void sendTokenConfirmation(User user) {
        Context context = new Context();
        context.setVariable("user", user);
        String msgBody = templateEngine.process(CONFIRM_TOKEN, context);

        sendMail(user.getEmail(), TOKEN_SUBJECT, msgBody);
    }

    public void sendBookingConfirmation(List<Ticket> ticketList, Booking booking) {
        Context context = new Context();
        Event event = ticketList.get(0).getEvent();
        context.setVariable("booking", booking);
        context.setVariable("event", event);
        context.setVariable("tickets", ticketList);
        context.setVariable("movie", event.getMovie());

        // backdrop
        if (!event.getMovie().getExternalImage()) {
            context.setVariable("backdrop", Base64.getEncoder().encodeToString(
                    event.getMovie().getImage().getImageData()
            ));
        }

        // qr codes
        List<String> base64images = new ArrayList<>();
        ticketList.forEach(ticket -> base64images.add(
                imgToBase64String(qrCodeService.generateQRCodeImage(ticket.getId().toString()))));

        context.setVariable("codes", base64images);
        String msgBody = templateEngine.process(CONFIRM_BOOKING, context);

        sendMail(booking.getUser().getEmail(), BOOKING_SUBJECT, msgBody);
    }

    private String imgToBase64String(final RenderedImage img) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(img, "png", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    public void sendPasswordReset(User user, String password) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("password", password);
        String msgBody = templateEngine.process(RESET_PASSWORD, context);

        sendMail(user.getEmail(), RESET_PASSWORD_SUBJECT, msgBody);
    }

    public void sendPasswordChangeConfirmation(User user) {
        Context context = new Context();
        context.setVariable("user", user);
        String msgBody = templateEngine.process(CHANGE_PASSWORD, context);

        sendMail(user.getEmail(), CHANGE_PASSWORD_SUBJECT, msgBody);
    }
}
