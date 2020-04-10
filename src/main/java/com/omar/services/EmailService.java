package com.omar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String to, String subject, String text) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("oosama@codecdev.com");
		msg.setTo(to);

		msg.setSubject(subject);
		msg.setText(text);

		javaMailSender.send(msg);

	}
}
