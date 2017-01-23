package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.*;
import utils.Timef;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.*;

public class SendPost2 implements RequestHandler<SendPost2InDTO, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(SendPost2InDTO input, Context context) {
        context.getLogger().log("Input: " + input); 		
		
        Post post = new Post();        
    	post.post_id		 = input.post_id;			
	    post.user_id         = input.user_id;
	    post.location_id     = input.location_id;
	    post.post_type       = input.post_type;
	    post.post_text       = input.post_text;
	    post.post_image_id   = input.post_image_id;
	    post.post_video_id   = input.post_video_id;
	    post.status_id       = input.status_id;
	    post.longitude       = input.longitude;
	    post.latitude        = input.latitude;
               
		OperationResult result = new PostManager().sendPost(post);
        
        int retVal = 0;        
        if(result.isSuccess == true)
        {
        	retVal = (int)result.object;        
        }     
                                 
        return new BaseOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message );    
        
    }
}
