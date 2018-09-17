package com.example.spring.utility;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.example.spring.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Util {
	private static final String KEY="piyush19";
	
	static String from="aadira392@gmail.com";
	static String password="aadi123456";
	static Properties props=new Properties();
	
	private static final Pattern emailValidation = Pattern.compile("[a-z0-9+_.-]+@{1}[a-z](.+){1}[a-z]",Pattern.CASE_INSENSITIVE);
	
	public static String generateToken(String username,String emailId,int id)
	{
		long currentTime=System.currentTimeMillis();
		Date currentDate=new Date(currentTime);
		Date expireDate=new Date(currentTime+ 24*60*60*1000);
		
		String getToken=Jwts.builder()
				.setId(Integer.toString(id))
				.setIssuer(username)
				.setSubject(emailId)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS256,KEY)
				.compact().toString();
		
		return getToken;
	}
	

	public static int getId(String token)
	 {
		 int id=0;
		 System.out.println("token is:" +token);
		 Claims claim = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
		 id=Integer.parseInt(claim.getId());
		 return id;
	 }

	
	public static String sendMail(String mailTo,String msg,String subject)
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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSubject(subject);
            message.setText("\tWe receive your request for registration. \n \n "+
                    "\tPlease click the Link to complete the registration\n"
                    + msg);
            
            
            //send message  
            Transport.send(message);
            System.out.println("message sent successfully");
          
        } catch (Exception e) {
            System.out.println(e);
        }
    
		return subject;
	}
	
	public static String getUrl(HttpServletRequest request,String path) throws MalformedURLException
	{
		URL url=new URL(request.getRequestURL().toString());
		String redirectUrl=url.getProtocol() + "//" +url.getHost() + ":" +url.getPort() +
			 request.getContextPath();
		
		return redirectUrl;
	}
	
	public static String addLink(String link)
	{
		return "<html>\n" + "<body>\n" 
				+ "<a href='"+ link +"'>Click here to activate your account!!!</a>\n" +
				 "</body>\n" +
				"</html>";
	}
	
	public static String validateUserData(User user)
	{
		if(user.getUsername()==null ||user.getUsername().trim().length()<6){
			return "firstname is too short";
		}			
				else if(user.getEmail()==null || !(isEmailValid(user.getEmail()))){
					return "not a valid email address";
				}
					else if(Integer.toString(user.getPhoneNumber()).length()!=10) {
						return "enter 10 digit phone number";
					}	
					else if((user.getPhoneNumber())!=10) {
						return "enter 10 digit phone number";
					}
							else if(user.getPassword()==null || user.getPassword().length()<5){
								return "password is too weak";
							}else
							{
								return "success";
							}
	}
	
	private static boolean isEmailValid(String email) {
		
		Matcher matches=emailValidation.matcher(email);
		return matches.find();
	}
}
