// Created by Aly Abdelfattah-Elmakhzangui

import java.util.Properties;
 
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class SendMailTLS {
 
	public void sendEmail(final String username, final String password, String toEmail, String fileAttachment, 
			String subject, String emailContent) {
		
		// Connection Details
		// SMTP = Simple Mail Transfer Protocol
		// TLS = Transport Layer Security
		// This does not constitute real security - it's the same thing all e-mails have
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		if (username.contains("@gmail")) {
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
		}
		
		else if (username.contains("@cardiff")) {
			props.put("mail.smtp.host", "outlook.office365.com");
			props.put("mail.smtp.port", "587");
		}
		
		else if (username.contains("@aol")) {
			props.put("mail.smtp.host", "smtp.aol.com");
			props.put("mail.smtp.port", "587");
		}
		
		else {
			try {
				throw new Exception("Please enter a valid e-mail address");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				return;
			}
		}
		
		Session mailSession = Session.getDefaultInstance(props, null);
		mailSession.setDebug(true);
		
		// Initiate authentication checks
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
                        @Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
			
			// Main body of text is created, including subject
			// Also set recipient (can send to more than one recipient by adding address on at end
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);

			// create the message part   
            MimeBodyPart messageBodyPart = new MimeBodyPart();  
            
            //fill message
            messageBodyPart.setText(emailContent);
            Multipart multipart = new MimeMultipart();  
            multipart.addBodyPart(messageBodyPart);  
            
            // Part two is attachment  
            if (!"".equals(fileAttachment)) {
                for (int i = 0; i < fileAttachment.length(); i++) {
                    if (fileAttachment.charAt(i) == ',') {
                        String file = fileAttachment.substring(0, i);
                        fileAttachment = fileAttachment.substring(i + 1);
                        addAttachment(multipart, file);
                    }
                }
            }
            
            // Put parts in message  
            message.setContent(multipart);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
        
        private static void addAttachment(Multipart multipart, String filename) throws MessagingException {
            MimeBodyPart messageBodyPart = new MimeBodyPart();  
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
        }
	
	public static void main(String[] args) {
                
            SendMailTLS sendThis = new SendMailTLS();
		sendThis.sendEmail("chickentika99@gmail.com", "password97", "simon.titcomb@ntlworld.com", "",
				"Pyramids", "They are awesome, yah?");
		
		//sendEmail("Abdelfattah-ElmakhzanguiAS@cardiff.ac.uk", "********", "chickentika99@gmail.com", "",
					//"Pyramids", "They are awesome, yah?");
		
		/*sendEmail("stuff", "password97", "chickentika99@gmail.com", "",
					"Pyramids", "They are awesome, yah?");*/
	}
}
