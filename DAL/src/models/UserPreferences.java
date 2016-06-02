package models;

import java.sql.*;

//added by ue 02.06.2016
public class UserPreferences {

	public int user_id	; 
	public String os_type ;
	public String os_version ;
	public String os_id ;
	public String device_id ;
	public String user_interface_id ;
	public int language_id ;
	public int primary_location_id ;
	public Timestamp create_ts ;
	public Timestamp update_ts ;
	
}
