package com.ecoland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public MailService(JavaMailSender javaMailSender) {
	this.mailSender = javaMailSender;
    }

    /**
     * This method will send compose and send the message
     */
    public void sendMail(String to, String subject, String body) {
	var mailMessage = new SimpleMailMessage();
	mailMessage.setTo(to);
	mailMessage.setSubject(subject);
	mailMessage.setText(body);
	mailSender.send(mailMessage);
    }

}
