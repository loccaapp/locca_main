package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.*;
import utils.Timef;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.*;

public class SendMessage implements RequestHandler<Message, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(Message input, Context context) {
        context.getLogger().log("Input: " + input); 		
		
		OperationResult result = new MessageManager().sendMessage(input);
        
        int retVal = 0;        
        if(result.isSuccess == true)
        {
        	retVal = (int)result.object;        
        }
        else
        {        	
        	retVal = 0;
        }        
                                 
        return new BaseOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message );    
        
    }
}
