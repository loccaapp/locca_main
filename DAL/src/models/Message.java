package models;

import java.sql.*;

//added by ue 02.06.2016
public class Message {
	
	public long message_group_id ;
	public long message_id ;
	public String message_type ;
	public int from_user_id ;
	public int to_user_id ;
	public String message ;
	public String is_read ;
	public String is_spam ;
	public String status_id ;
	public Timestamp created_ts ;
	public Timestamp update_ts ;
	
}
