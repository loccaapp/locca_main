package models;

import java.sql.*;

//added by ue 02.06.2016
public class Notification {
	
	public long notification_id ;
	public long post_id ;
	public long message_group_id;
	public int user_id ;
	public int effecter_user_id ;
	public String ntf_type ;
	public int sub_type_code ;
	public String mark_as_read ;
	public String message ;
	public Timestamp created_ts ;
	public Timestamp update_ts ;
	
	/*
	public Notification()
	{
		super();
	}
	
	public Notification(int user_id, String type_id, String mark_as_read, String message) {
		super();
		this.user_id = user_id;
		this.ntf_type = type_id;
		this.mark_as_read = mark_as_read;
		this.message = message;
	}	
	*/
}
