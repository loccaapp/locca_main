package OutDTOs;

import java.util.ArrayList;

import Items.PostItem;
import models.Post;

public class GetBestPostsByLocationOutDTO extends BaseOutDTO{

	public ArrayList<PostItem> posts;

	public ArrayList<PostItem> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<PostItem> posts) {
		this.posts = posts;
	}

	public GetBestPostsByLocationOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetBestPostsByLocationOutDTO(boolean isSuccess, 
									int returnCode, 
									int reasonCode, 
									String message, 
									ArrayList<PostItem> posts) {
		super(isSuccess, returnCode, reasonCode, message);
		// TODO Auto-generated constructor stub
		
		setPosts(posts);	
	}
}
