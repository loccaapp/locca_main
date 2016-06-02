package models;

import java.sql.*;

public class User {

	public int user_id;
	public String username;
	public String user_pwd;
	public String user_type;
	public String user_sub_type;
	public String name_first;
	public String name_last;
	public String email_address;
	public String picture_id;
	public String education;
	public String gender;
	public String motto;
	public Date birthdate;
	public int phone_country_code;
	public int phone_operator_code;
	public int phone_num;
	public String status_id;
	public int account_try_count;
	public Timestamp last_activity_ts;
	public Timestamp create_ts;
	public Timestamp update_ts;
	
}
