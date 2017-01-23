package models;

import java.sql.*;

//added by ue 22.01.2017
public class Report {
	
	public long report_id ;
	public long post_id ;
	public int user_id ;
	public String report_type ;
	public String report_sub_type ;
	public String report_message ;
	public String status_id	;	
	public Timestamp create_ts ;
	public Timestamp update_ts ;
	
}
