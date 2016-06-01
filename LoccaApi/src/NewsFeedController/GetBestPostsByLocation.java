package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.GetBestPostsByLocationInDTO;
import Items.PostItem;
import OutDTOs.GetBestPostsByLocationOutDTO;
import helper.OperationResult;
import managers.PostManager;
import models.Post;

public class GetBestPostsByLocation implements RequestHandler<GetBestPostsByLocationInDTO, GetBestPostsByLocationOutDTO>{

	@Override
	public GetBestPostsByLocationOutDTO handleRequest(GetBestPostsByLocationInDTO input, Context context) {

		GetBestPostsByLocationOutDTO dto = new GetBestPostsByLocationOutDTO();
		int pageingCount = 10;		
		int start = input.page_number * pageingCount;
		OperationResult result = new PostManager()
				.getBestPostsByLocation(input.location_id, start, pageingCount);
		
		if(!result.isSuccess){
			dto.isSuccess = false;
			dto.message = result.message;
			return dto;
		}
				
		ArrayList<Post> posts = (ArrayList<Post>)result.object;
		
		if(posts == null || posts.size() == 0){
			dto.isSuccess = false;
			dto.message = "There aren't any records";
			return dto;
		}
		
		ArrayList<PostItem> postItems = new ArrayList<PostItem>();
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
		
		dto.isSuccess = true;
		dto.posts = postItems;
		return dto;
	}

}
