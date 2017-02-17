package managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import helper.Notifications;
import helper.OperationCode;
import helper.OperationResult;
import models.*;
import utils.Timef;

public class MessageManager extends BaseManager {

	public MessageManager(){
		super();
	}

	//added by ue 29.08.2016 send Message
	public OperationResult getConnectedUsers(UserConnection userconnection){
		
		OperationResult result = new OperationResult();
		
		try {									
			dbStatement = (Statement)dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("select message_group_id, from_user_id, "
					+ " to_user_id, tp_user_message.status_id, username, name_first, name_last, picture_id "
					+ " from tp_user_message, tp_user " 
					+ " where tp_user.user_id = tp_user_message.to_user_id "
					+ " and from_user_id = " + userconnection.from_user_id + " "
					+ " order by tp_user_message.update_ts desc ");	
			
			ArrayList<UserConnection> userConList = new ArrayList<UserConnection>();
			
			while(dbResultSet.next()){
				
				UserConnection userCon = new UserConnection();
				
				userCon.message_group_id = dbResultSet.getLong("message_group_id");
				userCon.from_user_id = dbResultSet.getInt("from_user_id");
				userCon.to_user_id = dbResultSet.getInt("to_user_id");
				userCon.status_id = dbResultSet.getString("status_id");
				userCon.username = dbResultSet.getString("username");
				userCon.name_first = dbResultSet.getString("name_first");
				userCon.name_last = dbResultSet.getString("name_last");
				userCon.picture_id = dbResultSet.getString("picture_id");
				
				userConList.add(userCon);
			}
			
			if(userConList.size() > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getConnectedUsers", Integer.toString(userconnection.from_user_id), 
						"Success for from_user_id");
				result.object = userConList;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getConnectedUsers", Integer.toString(userconnection.from_user_id) , 
						"Not Found for from_user_id");	
				result.object = userConList;
			}		
			
		}catch (SQLException e) {
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getConnectedUsers", Integer.toString(userconnection.from_user_id) , 
					e.getMessage());
			result.object = " ";
		}
		
		return result;
	}
		
	//added by ue 29.08.2016 send Message
	public OperationResult sendMessage(Message message){
		
		OperationResult result = new OperationResult();
		int effectedRows = 0;
		String sql = " ";
		String trace1 = " ";
		
		try {									
			dbStatement = (Statement)dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("select message_group_id, from_user_id, "
					+ " to_user_id, status_id "
					+ " from tp_user_message "
					+ " where from_user_id = " + message.from_user_id + " "
					+ " and to_user_id = " + message.to_user_id + " ");	
			
			trace1 += "select atti. ";
			long message_group_id = 0;
			while(dbResultSet.next()){
				message_group_id = dbResultSet.getInt("message_group_id");
				trace1 += "group_id aldi. ";
			}

			if(message_group_id == 0)
			{
				trace1 += "group_id alamadi. ";
				
				sql = "INSERT INTO tp_user_message "
						+ " (message_group_id, from_user_id, to_user_id, " 
						+ " status_id, created_ts, update_ts) "
						+ " VALUES "
						+ " (NULL, ?, ?, ?, now(), now());";
					
				PreparedStatement pst2 = dbConnection.prepareStatement(sql);
	            pst2.setInt(1, message.to_user_id);
	            pst2.setInt(2, message.from_user_id);
	            pst2.setString(3, message.status_id);
		            
		        effectedRows = pst2.executeUpdate();				
				
				sql = "INSERT INTO tp_user_message "
					+ " (message_group_id, from_user_id, to_user_id, " 
					+ " status_id, created_ts, update_ts) "
					+ " VALUES "
					+ " (NULL, ?, ?, ?, now(), now());";
				
				PreparedStatement pst = dbConnection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	            pst.setInt(1, message.from_user_id);
	            pst.setInt(2, message.to_user_id);
	            pst.setString(3, message.status_id);
	            
	            effectedRows = pst.executeUpdate();
	            
	            trace1 += "insert etti ";
	            
	            ResultSet genKeys = pst.getGeneratedKeys();
	            if ( genKeys.next() ) {
	            	trace1 += "group_id yaratti ve aldi ";
	            	message_group_id = (long)genKeys.getObject( 1 );  // ResultSet should have exactly one column, the primary key of INSERT table.
	            }	          
			}			
			
			sql = "INSERT INTO tp_message "
				+ " (message_group_id, message_id, message_type, " 
				+ " from_user_id, to_user_id, message, is_read, "
				+ " is_spam, status_id, created_ts, update_ts) "
				+ " VALUES "
				+ " (?, NULL, ?, ?, ?, ?, ?, ?, ?, now(), now());";
			
			PreparedStatement pst = dbConnection.prepareStatement(sql);
			pst.setLong(1,message_group_id);
			//pst.setLong(2, message.message_id);
            pst.setString(2, message.message_type);
            pst.setInt(3, message.from_user_id);
            pst.setInt(4, message.to_user_id);
            pst.setString(5, message.message);
            pst.setString(6, message.is_read);
            pst.setString(7, message.is_spam);
            pst.setString(8, message.status_id);
            
            effectedRows = pst.executeUpdate();
            
            trace1 += "message insert etti galiba? ";
			
			if(effectedRows > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("sendMessage", "message_id :" + message.message_id + " message_group_id:" + message_group_id + " trace1:"+trace1, 
						"Message was inserted!");
				result.object = effectedRows;
				
				message.message_group_id = message_group_id;
				OperationResult op_res = UpdateUserMessageTable(message);
				
				NotificationManager nm = new NotificationManager();							
				nm.sendNotification(dbConnection,
									0,
									message_group_id,
									message.to_user_id,
									message.from_user_id,
								    Notifications.NtfType.Info,
								    Notifications.NtfSubType.Message);
				
			}			
			else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("sendMessage", "sql :" + sql + " trace1:"+trace1, 
						"Message_id :" + message.message_id + "Message was not inserted!");
				
				LogManager logger = new LogManager();	
				logger.createServerLog(dbStatement, "I" , message.from_user_id, "sendMessage", result.message);	
			}
		
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.Info.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("sendMessage", "sql :" + sql + " trace1:"+trace1, e.getMessage());
			
			LogManager logger =  new LogManager();	
			logger.createServerError(dbStatement, "E" , "I", message.from_user_id, "sendMessage", result.message);	
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
								+ " and ((t1.from_user_id = " + message.from_user_id
								+ " and t1.to_user_id = " + message.to_user_id + ")"
								+ " or (t1.from_user_id = " + message.to_user_id 
								+ " and t1.to_user_id = " + message.from_user_id + "))"
								+ " order by t1.created_ts asc "); 
				
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
					userMessage.created_ts = dbResultSet.getTimestamp("created_ts");
					userMessage.update_ts = dbResultSet.getTimestamp("update_ts");
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

	
	public OperationResult UpdateUserMessageTable(Message message){
		
		OperationResult result = new OperationResult();	
		int effectedRows = 0;
		
		try {
			
			dbStatement = (Statement)dbConnection.createStatement();
			
			/*
			effectedRows = dbStatement.executeUpdate("UPDATE tp_user_message "
					   + " SET update_ts = now() "
					   + " WHERE message_group_id = " + message.message_group_id );	
			*/
			
			effectedRows = dbStatement.executeUpdate("UPDATE tp_user_message "
					   + " SET update_ts = now() "
						+ " WHERE ((from_user_id = " + message.from_user_id
						+ " and to_user_id = " + message.to_user_id + ")"
						+ " or (from_user_id = " + message.to_user_id 
						+ " and to_user_id = " + message.from_user_id + "))" );
			
			if(effectedRows > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("UpdateUserMessageTable", String.valueOf(effectedRows) , " User Message table was updated!");
				result.object = effectedRows;
				
				LogManager logger =  new LogManager();	
				logger.createServerLog(dbStatement, "I" , message.from_user_id, "UpdateUserMessageTable2", result.message);	
				
				//dbConnection.commit();
			}			
			else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("UpdateUserMessageTable", String.valueOf(effectedRows) , " User Message group_id:" +message.message_group_id+ " was not updated!");
				
				LogManager logger =  new LogManager();	
				logger.createServerLog(dbStatement, "I" , message.from_user_id, "UpdateUserMessageTable", result.message);	
			}
		
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("UpdateUserMessageTable", String.valueOf(message.from_user_id) , e.getMessage());
			
			LogManager logger =  new LogManager();	
			logger.createServerError(dbStatement, "E" , "1", message.from_user_id, "UpdateUserMessageTable", result.message);	
		}
		
		/* yukarida cagiriliyor diye kapadim kontrol edilecek 
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		
		return result;
	}	

}


