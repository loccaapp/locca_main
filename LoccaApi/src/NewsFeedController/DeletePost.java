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
public class DeletePost implements RequestHandler<PostInDTO, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(PostInDTO input, Context context) {
        
        OperationResult result = new PostManager().deletePost(input.post_id,input.user_id);
        
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

