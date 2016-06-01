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
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery("select * from tp_user where user_id = " + user_id + " ");		
			
			User user = new User();
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
			
			OperationResult result = new OperationResult();
			result.isSuccess = true;
			result.message = "Success";
			result.object = user;
			return result;
			
		} catch (SQLException e) {
			OperationResult result = new OperationResult();
			result.isSuccess = false;
			result.message = "error1 : " + e.getMessage();
			return result;
		}		
	}
	
	
}
