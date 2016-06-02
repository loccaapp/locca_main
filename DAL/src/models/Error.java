package models;

import java.sql.*;

//added by ue 02.06.2016
public class Error {
	
	public long error_id ;
	public String error_type ;
	public int error_severity ;
	public int user_id ;
	public String screen_code ;
	public String error_message ;
	public Timestamp create_ts ;
	
}
