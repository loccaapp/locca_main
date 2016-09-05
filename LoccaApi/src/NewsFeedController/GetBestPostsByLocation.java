package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.GetBestPostsByLocationInDTO;
import Items.PostItem;
import OutDTOs.GetBestPostsByLocationOutDTO;
import OutDTOs.PostOutDTO;
import helper.OperationResult;
import managers.PostManager;
import models.Post;

public class GetBestPostsByLocation implements RequestHandler<GetBestPostsByLocationInDTO, GetBestPostsByLocationOutDTO>{

	@Override
	public GetBestPostsByLocationOutDTO handleRequest(GetBestPostsByLocationInDTO input, Context context) {

		OperationResult result = new PostManager()
				.getBestPostsByLocation(input.location_id, input.page_number, 10);
				
        ArrayList<Post> posts;
        ArrayList<PostItem> postItems = null;
        
        if(result.isSuccess==true) 
        { 
        	posts = (ArrayList<Post>)result.object;     
        	
    		postItems = new ArrayList<PostItem>();
    		for (Post post : posts) {
    			PostItem item = new PostItem();
    			item.user_id = post.user_id;
    			item.post_id = post.post_id;
    			item.district_name = post.location.district_name;
    			item.location_name = post.location.location_name;
    			item.post_text = post.post_text;
    			item.like_count = post.like_count;
    			item.dislike_count = post.dislike_count;
    			item.username = post.user.username;
    			postItems.add(item);
    		}
        }
        else
        {        	
        	postItems = null;
        }     
        
        return new GetBestPostsByLocationOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message,
            					  postItems);
        
	}

}
