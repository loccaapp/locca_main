package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.LikedPostInDTO;
import OutDTOs.*;
import helper.OperationResult;
import managers.PostManager;
import models.*;

public class GetLikedPostsByUserId implements RequestHandler<LikedPostInDTO, PostOutDTO>  
{

    @Override
    public PostOutDTO handleRequest(LikedPostInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
                
		int pageingCount = 10;		
		int start = input.page_number * pageingCount;
		
        OperationResult result = 
        		new PostManager().getLikedPostsByUserId(input.user_id,         	
        												start, 
        												pageingCount);
                		
        ArrayList<Post> posts;
        
        if(result.isSuccess==true) 
        { 
        	posts = (ArrayList<Post>)result.object;        
        }
        else
        {        	
        	posts = null;
        }     
        
        return new PostOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message,
            					  posts);
    }
	
}
