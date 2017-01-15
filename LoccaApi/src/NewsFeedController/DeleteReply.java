package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.*;
import models.*;

//added by ue 01.06.2016
public class DeleteReply implements RequestHandler<ReplyInDTO, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(ReplyInDTO input, Context context) {
        
    	Reply reply = new Reply();    	
    	reply.reply_id = input.reply_id;
    	reply.user_id = input.user_id;
    	
        OperationResult result = new ReplyManager().deleteReply(reply);
        
        int retVal;        
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

