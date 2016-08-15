package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.LikeManager;
import models.*;

//added by ue 01.06.2016
public class LikeOrDislikePost implements RequestHandler<LikeOrDislikeInDTO, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(LikeOrDislikeInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        Like like = new Like(); 
        like.post_id = input.post_id;
        like.effecter_user_id = input.effecter_user_id;
        like.like_dislike_ind = input.like_dislike_ind;
        like.user_id = input.user_id; 
        
        OperationResult result = new LikeManager().likeOrDislikePost(like);
        
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
