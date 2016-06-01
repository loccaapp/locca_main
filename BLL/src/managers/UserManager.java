package managers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

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
		
		int i = 0 ;
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			i++;
			
			dbResultSet = dbStatement.executeQuery("select user_id, username, user_pwd, user_type, user_sub_type, name_first, name_last, email_address, picture_id, education, gender, motto, birthdate, phone_country_code, phone_operator_code, phone_num, status_id, account_try_count, last_activity_ts, create_ts, update_ts "
					+ " from tp_user where user_id = " + user_id + " ");		
			
			i++;
			
			User user = new User();
			while(dbResultSet.next()){
			i++;
			user.user_id = dbResultSet.getInt("user_id");
			i++;
			user.username = dbResultSet.getString("username");
			i++;
			user.user_pwd = dbResultSet.getString("user_pwd");
			i++;
			user.user_type = dbResultSet.getString("user_type");
			i++;
			user.user_sub_type = dbResultSet.getString("user_sub_type");
			i++;
			user.name_first = dbResultSet.getString("name_first");
			i++;
			user.name_last = dbResultSet.getString("name_last");	
			i++;
			user.email_address = dbResultSet.getString("email_address");
			i++;
			user.picture_id = dbResultSet.getString("picture_id");
			i++;
			user.education = dbResultSet.getString("education");
			i++;
			user.gender = dbResultSet.getString("gender");
			i++;
			user.motto = dbResultSet.getString("motto");	
			i++;
			user.birthdate = dbResultSet.getDate("birthdate");
			i++;
			user.phone_country_code = dbResultSet.getInt("phone_country_code");
			}
			
			i++;
			OperationResult result = new OperationResult();
			i++;
			result.isSuccess = true;
			i++;
			result.message = "Success";
			i++;
			result.object = user;
			i++;
			return result;
			
		} catch (SQLException e) {
			OperationResult result = new OperationResult();
			result.isSuccess = false;
			result.message = "iii: " + i + "User: " + user_id + " Error1 : " 
					+ e.getStackTrace() + "=="
					+ e.getMessage() + "--" 
					+ e.getErrorCode()+ "**" 
					+ e.getSQLState() + "++" 
					+ e.getStackTrace();
			return result;
		}		
	}
	
	
}
