package es.uco.ism.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Mail {
	
	public Mail(String to, String subject, String body) {
		
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	
	private String FROM = "hokuroincorporated@gmail.com";;
	private String FROMNAME = "Hokuro";
	private String to;
	private String CONFIGSET = "ConfigSet";
	private String HOST = "smtp.gmail.com";
	private int PORT = 587;
	private String subject;
	private String body;
 
	public void sendEmail() throws UnsupportedEncodingException, MessagingException, IOException{
 
		Properties propertiesBD = new Properties();
		FileReader fileReaderBD = new FileReader("/src/main/webapp/WEB-INF/server.properties");
		propertiesBD.load(fileReaderBD);
		
		// Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT);
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setContent(body,"text/html");
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, propertiesBD.getProperty("MAIL"), propertiesBD.getProperty("PWD"));
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
		
	}
 
}