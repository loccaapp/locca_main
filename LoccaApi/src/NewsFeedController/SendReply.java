package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.*;
import utils.Timef;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.*;

public class SendReply implements RequestHandler<ReplyInDTO, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(ReplyInDTO input, Context context) {
        context.getLogger().log("Input: " + input); 		
		
        Reply reply = new Reply();
        reply.reply_id = input.reply_id;
        reply.post_id = input.post_id;
        reply.user_id = input.user_id;
        reply.message = input.message;
        reply.status_id = input.status_id;
        
		OperationResult result = new ReplyManager().sendReply(reply);
        
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
