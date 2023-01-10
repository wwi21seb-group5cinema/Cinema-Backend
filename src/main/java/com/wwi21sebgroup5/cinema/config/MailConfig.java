package com.wwi21sebgroup5.cinema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    private final String authLiteral = "spring.mail.properties.mail.smtp.auth";
    private final String startTlsLiteral = "spring.mail.properties.mail.smtp.starttls.enable";
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean startTls;
/*
    @Bean
    public JavaMailSender javaMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // basic config
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // specific java-mail config
        final Properties javaMailProps = new Properties();
        javaMailProps.put(authLiteral, auth);
        javaMailProps.put(startTlsLiteral, startTls);
        mailSender.setJavaMailProperties(javaMailProps);

        return mailSender;
    }*/

}
