package models;

import java.sql.*;

//added by ue 02.06.2016
public class Reply {
	
	public long reply_id ;
	public long post_id;
	public int user_id ;
	public String message ;
	public String status_id	;
	public Timestamp create_ts ;
	public Timestamp update_ts ;
	public User user;
	
	public Reply(){
		user = new User();
	}
	
}
