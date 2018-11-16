//package com.bridgelabz.login.utility;
//
//import org.springframework.messaging.Message;
//
//import org.springframework.jms.annotation.JmsListener;
//
//import com.bridgelabz.login.model.EmailDto;
//
//public class MessageReciever {
//	private final String EMAIL_RESPONSE_QUEUE = "mail.queue";
//
//	@JmsListener(destination = EMAIL_RESPONSE_QUEUE)
//	public void receiverMessage(final Message<EmailDto> message) {
//		EmailDto emailDto = message.getPayload();
//
//		System.out.println("Mail-Id : " + emailDto.getMailto());
//		System.out.println("Subject : " + emailDto.getSubject());
//		System.out.println("URL     : " + emailDto.getUrl());
//
//		try {
//
//			MailService.sendMail(emailDto.getMailto(),emailDto.getSubject(),emailDto.getUrl());
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
