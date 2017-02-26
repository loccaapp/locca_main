package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.*;
import utils.Timef;
import InDTOs.UserInDTO;
import InDTOs.UserPostInDTO;
import OutDTOs.*;
import helper.OperationCode;
import helper.OperationResult;
import managers.*;

/*
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
*/

import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.regions.*;

import java.io.IOException;
/*
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.*;
*/

public class SendEmail implements RequestHandler<UserPostInDTO, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(UserPostInDTO input, Context context) {
        context.getLogger().log("Input: " + input); 	
        
        String FROM = "uygarer@gmail.com";  // Replace with your "From" address. This address must be verified.
        String TO = "uygarer@gmail.com"; // Replace with a "To" address. If your account is still in the
                                                          // sandbox, this address must be verified.
        
        TO = input.email_address;
        
        OperationResult result = new OperationResult();
        
        //ozgurmeral68@gmail.com
        //ozgur_meral@yahoo.com 
        final String BODY = "This email was sent through Amazon SES by using the AWS SDK for Java.";
        final String SUBJECT = "Amazon SES test (AWS SDK for Java)";
		
        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{TO});
        
        // Create the subject and body of the message.
        Content subject = new Content().withData(SUBJECT);
        Content textBody = new Content().withData(BODY); 
        Body body = new Body().withText(textBody);
        
        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);
        
        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);
        
        try
        {        
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
        
            // Instantiate an Amazon SES client, which will make the service call. The service call requires your AWS credentials. 
            // Because we're not providing an argument when instantiating the client, the SDK will attempt to find your AWS credentials 
            // using the default credential provider chain. The first place the chain looks for the credentials is in environment variables 
            // AWS_ACCESS_KEY_ID and AWS_SECRET_KEY. 
            // For more information, see http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient();
               
            // Choose the AWS region of the Amazon SES endpoint you want to connect to. Note that your sandbox 
            // status, sending limits, and Amazon SES identity-related settings are specific to a given AWS 
            // region, so be sure to select an AWS region in which you set up Amazon SES. Here, we are using 
            // the US West (Oregon) region. Examples of other regions that Amazon SES supports are US_EAST_1 
            // and EU_WEST_1. For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html 
            Region REGION = Region.getRegion(Regions.US_WEST_2);
            client.setRegion(REGION);
       
            // Send the email.
            client.sendEmail(request);  
            System.out.println("Email sent!");
            
    		result.isSuccess = true;
    		result.returnCode = OperationCode.ReturnCode.Info.ordinal();
    		result.reasonCode = OperationCode.ReasonCode.Info_default;
        }
        catch (Exception ex) 
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("SendEmail", String.valueOf(input.user_id), ex.getMessage());
			
        }

        return new BaseOutDTO(result.isSuccess,
				  result.returnCode,
				  result.reasonCode, 
				  result.message );  
        
    }
}


/*


public class AmazonSESSample {
 
    static final String FROM = "SENDER@EXAMPLE.COM";  // Replace with your "From" address. This address must be verified.
    static final String TO = "RECIPIENT@EXAMPLE.COM"; // Replace with a "To" address. If your account is still in the
                                                      // sandbox, this address must be verified.
    static final String BODY = "This email was sent through Amazon SES by using the AWS SDK for Java.";
    static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";
  

    public static void main(String[] args) throws IOException {    	
                
        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{TO});
        
        // Create the subject and body of the message.
        Content subject = new Content().withData(SUBJECT);
        Content textBody = new Content().withData(BODY); 
        Body body = new Body().withText(textBody);
        
        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);
        
        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);
        
        try
        {        
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
        
            // Instantiate an Amazon SES client, which will make the service call. The service call requires your AWS credentials. 
            // Because we're not providing an argument when instantiating the client, the SDK will attempt to find your AWS credentials 
            // using the default credential provider chain. The first place the chain looks for the credentials is in environment variables 
            // AWS_ACCESS_KEY_ID and AWS_SECRET_KEY. 
            // For more information, see http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient();
               
            // Choose the AWS region of the Amazon SES endpoint you want to connect to. Note that your sandbox 
            // status, sending limits, and Amazon SES identity-related settings are specific to a given AWS 
            // region, so be sure to select an AWS region in which you set up Amazon SES. Here, we are using 
            // the US West (Oregon) region. Examples of other regions that Amazon SES supports are US_EAST_1 
            // and EU_WEST_1. For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html 
            Region REGION = Region.getRegion(Regions.US_WEST_2);
            client.setRegion(REGION);
       
            // Send the email.
            client.sendEmail(request);  
            System.out.println("Email sent!");
        }
        catch (Exception ex) 
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
    }
}

*/