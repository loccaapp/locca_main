package PersonalFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.GetLastLocsByUserIdInDTO;
import Items.PostItem;
import OutDTOs.GetLastLocsByUserIdOutDTO;
import helper.OperationResult;
import managers.PostManager;
import models.Post;

public class GetLastLocsByUserId implements RequestHandler<GetLastLocsByUserIdInDTO, GetLastLocsByUserIdOutDTO>{

	@Override
	public GetLastLocsByUserIdOutDTO handleRequest(GetLastLocsByUserIdInDTO input, Context context) {
		int pageingCount = 10;		
		int start = input.page_number * pageingCount;
		OperationResult result = new PostManager().getLastLocsByUserId(input.user_id, start, pageingCount);
		GetLastLocsByUserIdOutDTO outDto = new GetLastLocsByUserIdOutDTO(result);
		
		if(!result.isSuccess){	
			return outDto;
		}
		
		ArrayList<Post> inPosts = (ArrayList<Post>) result.object;
		
		ArrayList<PostItem> outPosts = new ArrayList<>();
		for (Post post : inPosts) {
			PostItem loc = new PostItem();
			loc.dislike_count = post.dislike_count;
			loc.district_name = post.location.district_name;
			loc.like_count = post.like_count;
			loc.location_id = post.location_id;
			loc.location_name = post.location.location_name;
			loc.post_id = post.post_id;
			loc.post_text = post.post_text;
			loc.user_id = post.user_id;
			loc.username = post.user.username;
			outPosts.add(loc);
		}
		
		outDto.userLocs = outPosts;
		return outDto;
	}

}
