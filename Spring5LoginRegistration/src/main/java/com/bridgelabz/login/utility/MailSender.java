package com.bridgelabz.login.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.bridgelabz.login.model.User;



@Component
public class MailSender {
	
		static String from="aadira392@gmail.com";
		static String password="aadi123456";
		static Properties props=new Properties();
		
		public static String sendMail(User user,String token)
		{
			props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.socketFactory.port", "465");
	        props.put("mail.smtp.socketFactory.class",
	                "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.port", "465");
	        props.put("mail.smtp.starttls.enable", "true");

	        //get Session provides the scope of the message 
	        Session session = Session.getInstance(props,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(from, password);
	                    }
	                });
	       
	        try {
	        	
	            MimeMessage message = new MimeMessage(session);
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
	            message.setSubject("reset link");
	          
	            message.setText("Please click the link below to verify yourself \n\n "+
						"http://localhost:3000/resetpassword/"+token);
	            
	            
	            //send message  
	            Transport.send(message);
	            System.out.println("message sent successfully");
	          
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    
			return null;
		}
		
	}