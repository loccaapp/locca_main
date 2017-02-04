package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.PostManager;
import models.*;

public class GetPopularPostsByTimeInterval implements RequestHandler<UserPostInDTO, PostOutDTO>  
{

    @Override
    public PostOutDTO handleRequest(UserPostInDTO input, Context context) {
        context.getLogger().log("Input: " + input);        
		
        OperationResult result = 
        		new PostManager().getPopularPostsByTimeInterval(input.user_id,
        														input.page_number, 
        														input.paging_count,
        														input.time_interval );
        
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
