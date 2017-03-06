package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.UserPostInDTO;
import OutDTOs.*;
import helper.OperationCode;
import helper.OperationResult;

import java.util.Properties;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.*;

public class SendEmail2 implements RequestHandler<UserPostInDTO, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(UserPostInDTO input, Context context) {

    	OperationResult result = new OperationResult();
    	
        String FROM = "uygarer@gmail.com";   // Replace with your "From" address. This address must be verified.
        String TO = "uygarer@yandex.com";  // Replace with a "To" address. If your account is still in the 
                                                           // sandbox, this address must be verified.               
        
        String BODY = "This email was sent through the Amazon SES SMTP interface by using Java.";
        String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
        
        // Supply your SMTP credentials below. Note that your SMTP credentials are different from your AWS credentials.
        String SMTP_USERNAME = "uygarer@gmail.com";  // Replace with your SMTP username.
        String SMTP_PASSWORD = "uyg287er288";  // Replace with your SMTP password.
        
        // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
        // String HOST = "email-smtp.us-west-2.amazonaws.com";    
        String HOST = "smtp.gmail.com"; 
        
        TO = input.email_address;
        
        // The port you will connect to on the Amazon SES SMTP endpoint. We are choosing port 25 because we will use
        // STARTTLS to encrypt the connection.
        //int PORT = 25;
        int PORT = 465;

        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtps");
    	props.put("mail.smtp.port", PORT); 
    	
    	// Set properties indicating that we want to use STARTTLS to encrypt the connection.
    	// The SMTP session will begin on an unencrypted connection, and then the client
        // will issue a STARTTLS command to upgrade to an encrypted connection.
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.starttls.required", "true");

        // Send the message.
        try
        {
    	
        	// Create a Session object to represent a mail session with the specified properties. 
        	Session session = Session.getDefaultInstance(props);

        	// Create a message with the specified information. 
        	MimeMessage msg = new MimeMessage(session);
                        
            msg.setFrom(new InternetAddress(FROM));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
            msg.setSubject(SUBJECT);
            msg.setContent(BODY,"text/plain");
                
            // Create a transport.        
            Transport transport = session.getTransport();	
            
            System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
            
    		result.isSuccess = true;
    		result.returnCode = OperationCode.ReturnCode.Info.ordinal();
    		result.reasonCode = OperationCode.ReasonCode.Info_default;
        }
        catch (Exception ex) {
        	
            System.out.println("The email was not sent.");
            System.out.println("TO:" + TO);
            System.out.println("FROM:" + FROM);
            System.out.println("Error message: " + ex.getMessage());
            
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("SendEmail", String.valueOf(input.user_id), 
					"email1:" + FROM + "email2:" + TO + " " 
				  + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            //transport.close();        	
        }
    	    	
        return new BaseOutDTO(result.isSuccess,
				  result.returnCode,
				  result.reasonCode, 
				  result.message );        
    }
}


/*
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class AmazonSESSample {

    static final String FROM = "SENDER@EXAMPLE.COM";   // Replace with your "From" address. This address must be verified.
    static final String TO = "RECIPIENT@EXAMPLE.COM";  // Replace with a "To" address. If your account is still in the 
                                                       // sandbox, this address must be verified.
    
    static final String BODY = "This email was sent through the Amazon SES SMTP interface by using Java.";
    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
    
    // Supply your SMTP credentials below. Note that your SMTP credentials are different from your AWS credentials.
    static final String SMTP_USERNAME = "YOUR_SMTP_USERNAME";  // Replace with your SMTP username.
    static final String SMTP_PASSWORD = "YOUR_SMTP_PASSWORD";  // Replace with your SMTP password.
    
    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    static final String HOST = "email-smtp.us-west-2.amazonaws.com";    
    
    // The port you will connect to on the Amazon SES SMTP endpoint. We are choosing port 25 because we will use
    // STARTTLS to encrypt the connection.
    static final int PORT = 25;

    public static void main(String[] args) throws Exception {

        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtps");
    	props.put("mail.smtp.port", PORT); 
    	
    	// Set properties indicating that we want to use STARTTLS to encrypt the connection.
    	// The SMTP session will begin on an unencrypted connection, and then the client
        // will issue a STARTTLS command to upgrade to an encrypted connection.
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.starttls.required", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/plain");
            
        // Create a transport.        
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
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
*/