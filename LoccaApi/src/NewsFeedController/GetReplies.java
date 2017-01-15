package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.MessageManager;
import managers.ReplyManager;
import models.*;

public class GetReplies implements RequestHandler<ReplyInDTO, ReplyOutDTO>  {

    @Override 
    public ReplyOutDTO handleRequest(ReplyInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        Reply reply = new Reply();
        reply.post_id = input.post_id;
        
        OperationResult result = new ReplyManager().getReplies(reply);
        
        ArrayList<Reply> replies;
        
        if(result.isSuccess==true) 
        {
        	replies = (ArrayList<Reply>)result.object;        
        }
        else
        {        	
        	replies = null;
        } 
        
        return new ReplyOutDTO(result.isSuccess,
	        						  result.returnCode,
	            					  result.reasonCode, 
	            					  result.message,
	            					  replies);
    }
	
}
