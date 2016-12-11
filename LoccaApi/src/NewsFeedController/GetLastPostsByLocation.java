package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.*;
import Items.PostItem;
import OutDTOs.*;
import helper.OperationResult;
import managers.PostManager;
import models.Post;

public class GetLastPostsByLocation implements RequestHandler<UserPostInDTO, PostOutDTO>{

	@Override
	public PostOutDTO handleRequest(UserPostInDTO input, Context context) {

		OperationResult result = new PostManager()
				.getLastPostsByLocation(input.location_id, input.page_number, 10);
		
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
		
		/*
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
        
        return new GetLastPostsByLocationOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message,
            					  postItems);	
        */
       
	}
}