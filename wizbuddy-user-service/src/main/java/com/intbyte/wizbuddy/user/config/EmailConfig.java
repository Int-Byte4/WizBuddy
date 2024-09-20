package com.intbyte.wizbuddy.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.smtp.host}")
    private String host;

    @Value("${spring.mail.smtp.port}")
    private int port;

    @Value("${spring.mail.smtp.username}")
    private String username;

    @Value("${spring.mail.smtp.password}")
    private String password;

    @Value("${spring.mail.smtp.personal}")
    private String personal;

    @Value("${spring.mail.smtp.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.smtp.properties.mail.smtp.ssl.enable}")
    private boolean sslEnable;


    @Bean
    public JavaMailSender mailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.ssl.enable", sslEnable);
        props.put("mail.smtp.ssl.trust", host);

        return mailSender;
    }
}