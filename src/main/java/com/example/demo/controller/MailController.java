package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.User;

import jakarta.mail.MessagingException;
import jakarta.mail.Quota.Resource;
import jakarta.mail.internet.MimeMessage;

@RestController

public class MailController {

	@Autowired
	private JavaMailSender javaMailSender;
//	
//	@PostMapping("/sendemail")
//	public String sendEmail(@RequestBody User user)
//	{
//		SimpleMailMessage s = new SimpleMailMessage();
//		s.setTo(user.getTo());
//		s.setSubject(user.getSubject());
//		s.setText(user.getText());
//		
//		javaMailSender.send(s);
//		
//		return "sent successfully";
//	}
	
	@PostMapping("/email")
	public String sendEmailWithFile(@ModelAttribute User user) throws IllegalStateException, MessagingException, IOException
	{
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper mhelper = new MimeMessageHelper(mime, true);
		
		mhelper.setTo(user.getTo());
		mhelper.setSubject(user.getSubject());
		mhelper.setText(user.getText());
		
		 String fileName = Objects.requireNonNull(user.getAttachment().getOriginalFilename());
         ByteArrayResource fileResource = new ByteArrayResource(user.getAttachment().getBytes());
         
		//mhelper.addAttachment(user.getAttachment().getOriginalFilename(), covertIntoFile(user.getAttachment(), user.getAttachment().getOriginalFilename()));
		mhelper.addAttachment(fileName, fileResource);
		javaMailSender.send(mime);
		
		return "sent successfully";
	}
	
//	private static File covertIntoFile(MultipartFile multipartFile, String filename) throws IllegalStateException, IOException
//	{
//		String path = "D:\\Akshay\\path";
//		File covert = new File(System.getProperty(path));
//		multipartFile.transferTo(covert);
//		return covert;
//	}
}
