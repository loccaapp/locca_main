package models;

import java.sql.Timestamp;

public class Post {

	public int post_id;
	public int user_id;
	public int location_id;
	public char post_type;
	public String post_text;
	public int post_image_id;
	public int like_count;
	public int dislike_count;
	public Timestamp create_ts;
	public Timestamp update_ts;
	public Location location;
	public User user;
	
	public Post(){
		location = new Location();
		user = new User();
	}
}
