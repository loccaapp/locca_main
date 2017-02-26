package managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.*;

public class NotificationManager extends BaseManager {

	public NotificationManager(){
		super();
	}
	
	//added by ue 12.01.2017 send Report
	public OperationResult sendNotification(Connection con, 
											   long post_id,
											   long message_group_id,
											   int user_id,
											   int effecter_user_id,
											   String ntf_type,
											   int sub_type_code){
		
		OperationResult result = new OperationResult();
		int effectedRows = 0;
		String sql = " ";
		
		try {			
			
			sql = "INSERT INTO awsTest.tp_notification "
				+ "(notification_id, post_id , message_group_id, user_id, effecter_user_id, ntf_type, "
				+ " sub_type_code, mark_as_read, message, created_ts, update_ts) "
				+ "VALUES (NULL,?,?,?,?,?,?,?,?,now(),now()) ";
			
			PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, user_id);
            pst.setLong(2, post_id);
            pst.setLong(3, message_group_id);
            pst.setInt(4, effecter_user_id);
            pst.setString(5, ntf_type);
            pst.setInt(6, sub_type_code); 
            pst.setString(7, "N"); 
            pst.setString(8, "Heyyy you have some notifications!!!" );
            //not considered yet
            
            effectedRows = pst.executeUpdate();
			
			if(effectedRows > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("sendNotification", " " , "Notification was sent!");
				result.object = effectedRows;
			}			
			else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("sendNotification", "sql: " + sql , 
						"user_id :" + user_id + " Notification was not sent!");
				
				//LogManager logger =  new LogManager();	
				//logger.createServerLog(dbStatement, "I" , notification.user_id, "sendNotification", result.message);				
			}
		
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("sendNotification","post_id: " + user_id + "sql: " + sql , e.getMessage());
			
			//LogManager logger =  new LogManager();	
			//logger.createServerError(dbStatement, "E" , "1", notification.user_id, "sendNotification", result.message);
		}		
		
		/*
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		return result;
	}			

	//added by ue 23.06.2016 get Message
	public OperationResult getNotifications(Notification not,int start, int count){
			
			OperationResult result = new OperationResult();
			
			try {		
				
				dbStatement = (Statement) dbConnection.createStatement();
				
				String sql = "select notification_id, "
						+ "replace(message_text,'@usr',t3.username) as message, "
						+ "t1.user_id, t1.message_group_id, t1.post_id, t1.effecter_user_id, "
						+ "t1.created_ts, t1.mark_as_read "
						+ "from tp_notification t1, tp_msg_templates t2, tp_user t3 "
						+ "where t1.ntf_type = t2.msg_type "
						+ "and t1.sub_type_code = t2.msg_sub_type "
						+ "and t1.effecter_user_id = t3.user_id "
						+ "and t1.user_id = " + not.user_id + " "
						+ "order by t1.created_ts desc " 
						+ "LIMIT " + start * count + ", " + count + " ";
				
				dbResultSet = 
						dbStatement.executeQuery(sql); 
				
				ArrayList<Notification> notifications = new ArrayList<Notification>();
				
				while(dbResultSet.next()){
					
					Notification notification = new Notification();
					
					notification.notification_id = dbResultSet.getLong("notification_id");
					notification.post_id = dbResultSet.getLong("post_id");
					notification.message_group_id = dbResultSet.getLong("message_group_id");
					notification.message = dbResultSet.getString("message"); 
					notification.user_id = dbResultSet.getInt("user_id");
					notification.effecter_user_id = dbResultSet.getInt("effecter_user_id");
					notification.mark_as_read = dbResultSet.getString("mark_as_read");	
					notification.created_ts = dbResultSet.getTimestamp("created_ts");
													
					notifications.add(notification);
				}
				
				if(notifications.size() > 0)
				{
					result.isSuccess = true;
					result.returnCode = OperationCode.ReturnCode.Info.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Info_default;
					result.setMessage("getNotifications", Integer.toString(not.user_id), 
									  "Success for from_user_id");
					
					LogManager logger =  new LogManager();	
					logger.createServerLog(dbStatement, "I" , not.user_id, "getNotifications", 
										   result.message );
					
					result.object = notifications;
				}
				else
				{
					result.isSuccess = false;
					result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
					result.setMessage("getNotifications", Integer.toString(not.user_id) , 
									  "Not Found for from_user_id");	
					result.object = notifications;
				}							
				
			} catch (SQLException e) {
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
				result.returnCode = OperationCode.ReasonCode.Error_Login;
				result.setMessage("getNotifications", Integer.toString(not.user_id) , 
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