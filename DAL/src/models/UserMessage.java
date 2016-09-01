package models;

import java.sql.*;

//added by ue 02.06.2016
public class UserMessage {

	public long message_group_id;
	public long message_id;
	public String message_type;
	public int from_user_id;
	public int to_user_id;
	public String message;
	public String is_read;
	public String is_spam;
	public String status_id;
	public Timestamp created_ts;
	public Timestamp update_ts;
	public String from_username;
	public String from_name_first;
	public String from_name_last;
	public String to_username;
	public String to_name_first;
	public String to_name_last;
	
	public UserMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserMessage(long message_group_id, long message_id, String message_type, int from_user_id, int to_user_id,
			String message, String is_read, String is_spam, String status_id, Timestamp created_ts, Timestamp update_ts,
			String from_username, String from_name_first, String from_name_last, String to_username,
			String to_name_first, String to_name_last) {
		super();
		this.message_group_id = message_group_id;
		this.message_id = message_id;
		this.message_type = message_type;
		this.from_user_id = from_user_id;
		this.to_user_id = to_user_id;
		this.message = message;
		this.is_read = is_read;
		this.is_spam = is_spam;
		this.status_id = status_id;
		this.created_ts = created_ts;
		this.update_ts = update_ts;
		this.from_username = from_username;
		this.from_name_first = from_name_first;
		this.from_name_last = from_name_last;
		this.to_username = to_username;
		this.to_name_first = to_name_first;
		this.to_name_last = to_name_last;
	}
		
}
