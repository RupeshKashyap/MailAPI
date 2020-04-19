package com.example.MailAPI.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.MailAPI.model.User;

@Service
public class MailService {
	
	private JavaMailSender javaMailSender;
	
	/**
	 * 
	 * @param javaMailSender
	 */
	
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	/**
	 * This function is used to send mail without attachment.
	 * @param user
	 * @throws MailException
	 */
	
	public void sendEmail(User user) throws MailException {

		/*
		 * This JavaMailSender Interface is used to send Mail in Spring Boot. This
		 * JavaMailSender extends the MailSender Interface which contains send()
		 * function. SimpleMailMessage Object is required because send() function uses
		 * object of SimpleMailMessage as a Parameter
		 */

		SimpleMailMessage  mail = new SimpleMailMessage();
		mail.setTo(user.getEmailAddress());
		mail.setSubject("Testing Mail API");
		mail.setText("Hurray ! You have done that...");

		/*
		 * This send() contains an Object of SimpleMailMessage as an Parameter
		 */
		javaMailSender.send(mail);
	}
	
	/**
	 * This fucntion is used to send mail that contains a attachment.
	 * 
	 * @param user
	 * @throws MailException
	 * @throws MessagingException
	 */
	public void sendEmailWithAttachment(User user) throws MailException, MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setTo(user.getEmailAddress());
		helper.setSubject("Testing Mail API with Attachment");
		helper.setText("Please find the attached document below.");

		ClassPathResource classPathResource = new ClassPathResource("pp.jpg");
		helper.addAttachment(classPathResource.getFilename(), classPathResource);

		javaMailSender.send(mimeMessage);
	}


}
