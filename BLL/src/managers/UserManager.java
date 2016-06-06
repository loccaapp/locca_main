package managers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.User;

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
			
			OperationResult result = new OperationResult();
			result.isSuccess = true;
			result.message = "Success";
			result.object = user;
			return result;
			
		} catch (SQLException e) {
			OperationResult result = new OperationResult();
			result.isSuccess = false;
			result.message = "User: " + user_id + " Error1 : " 
					+ e.getStackTrace() + "=="
					+ e.getMessage()    + "--" 
					+ e.getErrorCode()  + "**" 
					+ e.getSQLState()   + "++" 
					+ e.getStackTrace();
			return result;
		}		
	}
	
	//added by ue 01.06.2016
	public OperationResult loginUser(String username, String user_pwd , String email_address){
		
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("select user_id from "
					+ " tp_user where (username = '" + username + "' or "
								   + " email_address = '" + email_address + "' ) and "
								   + " user_pwd = '" + user_pwd + "' ");		
			
			int user_id = 0;
			while(dbResultSet.next()){
				user_id = dbResultSet.getInt("user_id");
			}		

			if(user_id > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("loginUser", username , "Success for user_name");
				result.object = user_id;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("loginUser", username , "Failure for user_name");	
				result.object = user_id;
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
	
	
}
