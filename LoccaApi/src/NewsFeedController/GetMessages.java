package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.MessageManager;
import models.*;

public class GetMessages implements RequestHandler<Message, UserMessageOutDTO>  {

    @Override 
    public UserMessageOutDTO handleRequest(Message input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new MessageManager().getMessages(input);        		
        
        ArrayList<UserMessage> userMessage;
        
        if(result.isSuccess==true) 
        {
        	userMessage = (ArrayList<UserMessage>)result.object;        
        }
        else
        {        	
        	userMessage = null;
        } 
        
        return new UserMessageOutDTO(result.isSuccess,
	        						  result.returnCode,
	            					  result.reasonCode, 
	            					  result.message,
	            					  userMessage);
    }
	
}
