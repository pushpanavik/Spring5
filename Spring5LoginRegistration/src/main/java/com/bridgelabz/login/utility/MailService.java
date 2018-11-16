package com.bridgelabz.login.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.bridgelabz.login.model.User;

import reactor.core.publisher.TopicProcessor;

@Component
public class MailService {

	
	static String from="aadira392@gmail.com";
	static String password="aadi123456";
	static Properties props=new Properties();
		
	public MailService(TopicProcessor<User> userRegister) {
		userRegister.subscribe(user -> {
			System.out.println("sfsdfsdfsdf "+Thread.currentThread().getId());
			try {
				send(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		

	}
	
	public void send(User user) throws Exception {
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
        	
            MimeMessage mimeMessageHelper = new MimeMessage(session);
            mimeMessageHelper.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            mimeMessageHelper.setSubject("Link for activation");
		
            
            String token=GenerateToken.generateUserToken(user.getFirstname(), user.getLastname(), user.getUserId(), user.getEmail());
            mimeMessageHelper.setText("Please click the link below to verify yourself \n\n "+
									"http://localhost:8080/activateuser/"+token);
            
		Transport.send(mimeMessageHelper);
		System.out.println("Email send successfully");
        } catch (Exception e) {
         System.out.println(e);
      }
	}

}



