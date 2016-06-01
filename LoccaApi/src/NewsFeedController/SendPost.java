package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.Post;
import utils.Timef;
import InDTOs.SendPostInDTO;
import OutDTOs.SendPostOutDTO;
import helper.OperationResult;
import managers.PostManager;

public class SendPost implements RequestHandler<SendPostInDTO, SendPostOutDTO> {

    @Override
    public SendPostOutDTO handleRequest(SendPostInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        Post post = new Post();
        post.user_id = input.user_id;
        post.location_id = input.location_id;
        post.post_type = input.post_type;
        post.post_text = input.post_text;
        post.create_ts = Timef.getDateTime();
        post.update_ts = Timef.getDateTime();
        
        OperationResult result = new PostManager().insert(post);
    	SendPostOutDTO response = new SendPostOutDTO();
    	
        if(!result.isSuccess){
        	response.isSuccess = false;
        	response.message = result.message;
        	return response;
        }
        
        response.isSuccess = true;
        response.message = "Your post sent";
		return response;
        
    }
}
