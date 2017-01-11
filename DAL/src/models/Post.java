package models;

import java.sql.*;

public class Post {

	public long post_id;
	public int user_id;
	public int location_id;
	public char post_type;
	public String post_text;
	public String post_image_id;
	public String post_video_id;
	public String is_replied;
	public String to_fb ;
	public String to_twitter ;
	public String to_instagram ;
	public String status_id;
	public int like_count;
	public int dislike_count;
	public double longitude;
	public double latitude;
	public Timestamp create_ts;
	public Timestamp update_ts;
	public String like_dislike_ind;
	public Location location;
	public User user;
	
	public Post(){
		location = new Location();
		user = new User();
	}
}
