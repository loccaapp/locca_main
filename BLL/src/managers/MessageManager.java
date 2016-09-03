package managers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.*;
import utils.Timef;

public class MessageManager extends BaseManager {

	public MessageManager(){
		super();
	}
	
	//added by ue 29.08.2016 send Message
	public OperationResult sendMessage(Message message){
		
		OperationResult result = new OperationResult();
		int effectedRows = 0;
		String sql = " ";
		
		try {
			
			dbStatement = (Statement)dbConnection.createStatement();
			
			sql = "INSERT INTO tp_message "
				+ " (message_group_id, message_id, message_type, " 
				+ " from_user_id, to_user_id, message, is_read, "
				+ " is_spam, status_id, created_ts, update_ts) "
				+ " VALUES "
				+ " (NULL, ?, ?, ?, ?, ?, ?, ?, ?, now(), now());";
			
			PreparedStatement pst = dbConnection.prepareStatement(sql);
            pst.setLong(1, message.message_id);
            pst.setString(2, message.message_type);
            pst.setInt(3, message.from_user_id);
            pst.setInt(4, message.to_user_id);
            pst.setString(5, message.message);
            pst.setString(6, message.is_read);
            pst.setString(7, message.is_spam);
            pst.setString(8, message.status_id);
            
            effectedRows = pst.executeUpdate();
			
			if(effectedRows > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("sendMessage", "message_id :" + message.message_id , 
						"Message was inserted!");
				result.object = effectedRows;
			}			
			else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("sendMessage", "sql :" + sql , 
						"Message_id :" + message.message_id + "Message was not inserted!");
			}
		
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.Info.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("sendMessage", "sql :" + sql , e.getMessage());
		}
		            
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}	
	
	//added by ue 23.06.2016 get Message
	public OperationResult getMessages(Message message){
			
			OperationResult result = new OperationResult();
			
			try {		
				
				dbStatement = (Statement) dbConnection.createStatement();
				
				dbResultSet = dbStatement.executeQuery("Select "
								+ " message_group_id, message_id, message_type, "
								+ " from_user_id, to_user_id, message, is_read, " 
								+ " is_spam, t1.status_id, t1.created_ts, t1.update_ts, "
								+ " t2.username as from_username, "
								+ " t2.name_first as from_name_first, " 
								+ " t2.name_last as from_name_last, "
								+ " t3.username as to_username, "
								+ " t3.name_first as to_name_first, " 
								+ " t3.name_last as to_name_last "
								+ " from  tp_message t1, tp_user t2, tp_user t3 "
								+ " where t1.from_user_id = t2.user_id "
								+ " and t1.to_user_id = t3.user_id "
								+ " and t1.from_user_id = " + message.from_user_id
								+ " and t1.to_user_id = " + message.to_user_id + " "
								+ " order by t1.created_ts desc "); 
				
				ArrayList<UserMessage> userMessages = new ArrayList<UserMessage>();
				
				while(dbResultSet.next()){ 
					
					UserMessage userMessage = new UserMessage();
					
					userMessage.message_group_id = dbResultSet.getLong("message_group_id");
					userMessage.message_id = dbResultSet.getLong("message_id");
					userMessage.message_type = dbResultSet.getString("message_type");
					userMessage.from_user_id = dbResultSet.getInt("from_user_id");
					userMessage.to_user_id = dbResultSet.getInt("to_user_id");
					userMessage.message = dbResultSet.getString("message");
					userMessage.is_read = dbResultSet.getString("is_read");
					userMessage.is_spam = dbResultSet.getString("is_spam");
					userMessage.status_id = dbResultSet.getString("status_id");
					//userManager.created_ts = dbResultSet.getTimestamp("created_ts");
					//userManager.update_ts = dbResultSet.getTimestamp("update_ts");
					userMessage.from_username = dbResultSet.getString("from_username");
					userMessage.from_name_first = dbResultSet.getString("from_name_first");
					userMessage.from_name_last = dbResultSet.getString("from_name_last");
					userMessage.to_username = dbResultSet.getString("to_username");
					userMessage.to_name_first = dbResultSet.getString("to_name_first");
					userMessage.to_name_last = dbResultSet.getString("to_name_last");					
					
					userMessages.add(userMessage);
				}
				
				if(userMessages.size() > 0)
				{
					result.isSuccess = true;
					result.returnCode = OperationCode.ReturnCode.Info.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Info_default;
					result.setMessage("getMessages", Integer.toString(message.from_user_id), 
							"Success for from_user_id");
					result.object = userMessages;
				}
				else
				{
					result.isSuccess = false;
					result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
					result.setMessage("getMessages", Integer.toString(message.from_user_id) , 
							"Not Found for from_user_id");	
					result.object = userMessages;
				}							
				
			} catch (SQLException e) {
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
				result.returnCode = OperationCode.ReasonCode.Error_Login;
				result.setMessage("getMessages", Integer.toString(message.from_user_id) , 
						e.getMessage());
				result.object = " ";
			}		
			
			try {
				dbConnection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return result;
		}
	
}

