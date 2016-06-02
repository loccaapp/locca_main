package models;

import java.sql.*;

//added by ue 02.06.2016
public class Notification {
	
	public long notification_id ;
	public int user_id ;
	public String type_id ;
	public String message ;
	public Timestamp created_ts ;
	public Timestamp update_ts ;
	
}
