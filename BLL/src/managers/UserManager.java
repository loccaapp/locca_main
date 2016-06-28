package managers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.*;

public class UserManager extends BaseManager {

	public UserManager() {
		super();
	}
			
	public OperationResult getUsers(){
		
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery("SELECT * FROM tp_user");
			ArrayList<User> users = new ArrayList<User>();
			while(dbResultSet.next()){
				User user = new User();
				user.user_id = dbResultSet.getInt("user_id");
				user.username = dbResultSet.getString("username");
				user.motto = dbResultSet.getString("motto");
				users.add(user);
			}
			
			OperationResult result = new OperationResult();
			result.isSuccess = true;
			result.message = "Success";
			result.object = users;
			return result;
			
		} catch (SQLException e) {
			OperationResult result = new OperationResult();
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}		
	}
	
	//added by ue 01.06.2016
	public OperationResult getUser(int user_id){
		//comment1
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("select user_id, username, user_pwd, user_type, user_sub_type, name_first, name_last, email_address, picture_id, education, gender, motto, birthdate, phone_country_code, phone_operator_code, phone_num, status_id, account_try_count, last_activity_ts, create_ts, update_ts "
					+ " from tp_user where user_id = " + user_id + " ");		
			
			User user = new User();
			while(dbResultSet.next()){
				user.user_id = dbResultSet.getInt("user_id");
				user.username = dbResultSet.getString("username");
				user.user_pwd = dbResultSet.getString("user_pwd");
				user.user_type = dbResultSet.getString("user_type");
				user.user_sub_type = dbResultSet.getString("user_sub_type");
				user.name_first = dbResultSet.getString("name_first");
				user.name_last = dbResultSet.getString("name_last");	
				user.email_address = dbResultSet.getString("email_address");
				user.picture_id = dbResultSet.getString("picture_id");
				user.education = dbResultSet.getString("education");
				user.gender = dbResultSet.getString("gender");
				user.motto = dbResultSet.getString("motto");	
				user.birthdate = dbResultSet.getDate("birthdate");
				user.phone_country_code = dbResultSet.getInt("phone_country_code");
			}
			
			if(user.user_id > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getUser", Integer.toString(user_id) , "Success for user_id");
				result.object = user;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getUser", Integer.toString(user_id) , "Failure for user_id");	
				result.object = user;
			}
			return result;
			
		} catch (SQLException e) {
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getUser", Integer.toString(user_id) , e.getMessage());
			result.object = " ";
			return result;
		}		
	}
	
	//added by ue 01.06.2016
	public OperationResult getUserByUserName(String username){
		//comment1
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("select user_id, username, user_pwd, user_type, user_sub_type, name_first, name_last, email_address, picture_id, education, gender, motto, birthdate, phone_country_code, phone_operator_code, phone_num, status_id, account_try_count, last_activity_ts, create_ts, update_ts "
					+ " from tp_user where username = " + username + " ");		
			
			User user = new User();
			while(dbResultSet.next()){
				user.user_id = dbResultSet.getInt("user_id");
				user.username = dbResultSet.getString("username");
				user.user_pwd = dbResultSet.getString("user_pwd");
				user.user_type = dbResultSet.getString("user_type");
				user.user_sub_type = dbResultSet.getString("user_sub_type");
				user.name_first = dbResultSet.getString("name_first");
				user.name_last = dbResultSet.getString("name_last");	
				user.email_address = dbResultSet.getString("email_address");
				user.picture_id = dbResultSet.getString("picture_id");
				user.education = dbResultSet.getString("education");
				user.gender = dbResultSet.getString("gender");
				user.motto = dbResultSet.getString("motto");	
				user.birthdate = dbResultSet.getDate("birthdate");
				user.phone_country_code = dbResultSet.getInt("phone_country_code");
			}
			
			if(user.user_id > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getUser", username , "Success for username");
				result.object = user;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getUser", username , "Failure for username");	
				result.object = user;
			}
			return result;
			
		} catch (SQLException e) {
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getUser", username , e.getMessage());
			result.object = " ";
			return result;
		}		
	}
	
	//added by ue 01.06.2016
	public OperationResult loginUser(String username, String user_pwd , String email_address){
		
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("select * from "
					+ " tp_user where (username = '" + username + "' or "
								   + " email_address = '" + email_address + "' ) and "
								   + " user_pwd = '" + user_pwd + "' ");		
			
			User user = new User();
			while(dbResultSet.next()){
				
				user.user_id = dbResultSet.getInt("user_id");
				user.username = dbResultSet.getString("username");
				user.user_pwd = dbResultSet.getString("user_pwd");
				user.user_type = dbResultSet.getString("user_type");
				user.user_sub_type = dbResultSet.getString("user_sub_type");
				user.name_first = dbResultSet.getString("name_first");
				user.name_last = dbResultSet.getString("name_last");	
				user.email_address = dbResultSet.getString("email_address");
				user.picture_id = dbResultSet.getString("picture_id");
				user.education = dbResultSet.getString("education");
				user.gender = dbResultSet.getString("gender");
				user.motto = dbResultSet.getString("motto");	
				user.birthdate = dbResultSet.getDate("birthdate");
				user.phone_country_code = dbResultSet.getInt("phone_country_code");
				user.last_activity_ts = dbResultSet.getTimestamp("last_activity_ts");
				
			}		

			if(user.user_id > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("loginUser", username , "Success for user_name");
				result.object = user;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("loginUser", username , "Failure for user_name");	
				result.object = user;
			}
			return result;
			
		} catch (SQLException e) {
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("loginUser", username , e.getMessage());
			result.object = " ";
			return result;
		}		
	}
	
	//added by ue 17.06.2016
	public OperationResult insertUser(User user){
				
		OperationResult result = new OperationResult();
		String sqlStatement = " ";
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			sqlStatement = "INSERT INTO tp_user " 
					+ " (user_id, username, user_pwd, user_type, user_sub_type, name_first, "
					+ " name_last, email_address, picture_id, education, gender, motto, birthdate, "
					+ " phone_country_code, phone_operator_code, phone_num, status_id, "
					+ " account_try_count, last_activity_ts, create_ts, update_ts) "
					+ " VALUES "
					+ " (  NULL,"
						   +"'"+user.username  + "',"
						   +"'"+user.user_pwd  + "',"
						   +"'"+user.user_type + "',"
						   +"'"+user.user_sub_type + "',"
						   +"'"+user.name_first + "',"
						   +"'"+user.name_last + "',"
						   +"'"+user.email_address + "',"
						   +"'"+user.picture_id + "',"
						   +"'"+user.education + "',"
						   +"'"+user.gender + "',"
						   +"'"+user.motto + "',"
						   +"'"+user.birthdate + "',"
						   +user.phone_country_code + ","
						   +user.phone_operator_code + ","
						   +user.phone_num + ","
					       +"'"+user.status_id + "',"
					       +user.account_try_count + ","
					       +" now() ,"
					       +" now() ,"
					       +" now() )";
			int retVal = dbStatement.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
			int user_id = -1;
			if (retVal>0)
			{
				ResultSet rs = dbStatement.getGeneratedKeys();
				if (rs.next()){
				    user_id =rs.getInt(1);
				}
				
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("insertUser"
						, user.username
						, "Success for user_id" + Integer.toString(user_id) );
				result.object = user_id;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("insertUser"
						,  user.username
						, "Failure for location" + Integer.toString(user_id) );	
				result.object = user_id;
			}
			
			return result;
			
		} catch (SQLException e) {
			
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("insertUser"
					, user.username
					, e.getMessage() + "***:" + sqlStatement);
			result.object = " ";
			return result;
		}

	}	
	
	//added by ue 29.06.2016
	public OperationResult insertUserLog(Log log){
				
		OperationResult result = new OperationResult();
		String sqlStatement = " ";
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			sqlStatement = "INSERT INTO tp_log " 
					+ " (log_id, log_type, user_id, screen_code, log_message, create_ts ) "
					+ " VALUES "
					+ " (  NULL,"
						   +"'" + log.log_type + "',"
						   +log.user_id + ","
						   +"'" + log.screen_code + "',"
						   +"'" + log.log_message + "',"
						   +"now() )";
			int retVal = dbStatement.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
			int log_id = -1;
			if (retVal>0)
			{
				ResultSet rs = dbStatement.getGeneratedKeys();
				if (rs.next()){
					log_id =rs.getInt(1);
				}
				
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("insertUserLog"
						, Integer.toString(log.user_id) 
						, "Success for user_id" + Integer.toString(log_id) );
				result.object = log_id;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("insertUserLog"
						,  Integer.toString(log.user_id) 
						, "Failure for location" + Integer.toString(log_id) );	
				result.object = log_id;
			}
			
			return result;
			
		} catch (SQLException e) {
			
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("insertUserLog"
					, Integer.toString(log.user_id) 
					, e.getMessage() + "***:" + sqlStatement);
			result.object = " ";
			return result;
		}

	}	

	
}
