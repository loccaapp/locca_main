package OutDTOs;

import java.util.ArrayList;

import models.*;

//added by ue 08.08.2016
public class PostOutDTO extends BaseOutDTO{	
	
	
	public ArrayList<Post> posts;

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
	
	public PostOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PostOutDTO(boolean isSuccess, 
			int returnCode, 
			int reasonCode, 
			String message, 
			ArrayList<Post> posts) 
	{
		super(isSuccess, 
			returnCode, 
			reasonCode, 
			message);
		// TODO Auto-generated constructor stub
		setPosts(posts);		
	}	
}