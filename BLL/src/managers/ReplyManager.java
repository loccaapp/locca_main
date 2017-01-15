package managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.Location;
import models.Log;
import models.Message;
import models.Reply;
import models.Post;

public class ReplyManager extends BaseManager {

	public ReplyManager(){
		super();
	}
	
	//added by ue 12.01.2017 send Reply
	public OperationResult sendReply(Reply reply){
		
		OperationResult result = new OperationResult();
		int effectedRows = 0;
		String sql = " ";
		
		try {
			
			dbStatement = (Statement)dbConnection.createStatement();
			
			sql = "INSERT INTO awsTest.tp_reply "
					+ " (reply_id, post_id, user_id, message, "
					+ " status_id, create_ts, update_ts) "
					+ " VALUES (NULL,?,?,?,?,now(),now())" ;			
			
			PreparedStatement pst = dbConnection.prepareStatement(sql);
            pst.setLong(1, reply.post_id);
            pst.setInt(2, reply.user_id);
            pst.setString(3, reply.message.replace(';', ' '));
            pst.setString(4, "A");
            
            effectedRows = pst.executeUpdate();
			
			if(effectedRows > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("sendReply", " " , "Reply was sent!");
				result.object = effectedRows;
			}			
			else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("sendReply", "sql :" + sql , 
						"user_id :" + reply.user_id + "Reply was not sent!");

				LogManager logger =  new LogManager();	
				logger.createServerLog(dbStatement, "I" , reply.user_id, "sendReply", result.message);				
			}
		
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("sendReply", "sql :" + sql , e.getMessage());
			
			LogManager logger =  new LogManager();	
			logger.createServerError(dbStatement, "E" , "1", reply.user_id, "sendReply", result.message);
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}			

	//added by ue 13.01.2017 get Replies
	public OperationResult getReplies(Reply rep){
			
			OperationResult result = new OperationResult();
			
			try {		
				
				dbStatement = (Statement) dbConnection.createStatement();
				
				dbResultSet = dbStatement.executeQuery("select "
						+ " t1.reply_id, t1.post_id, t1.user_id, t1.message, "
						+ " t1.create_ts, t1.update_ts, t1.status_id, t2.username, t2.picture_id " 
						+ " from tp_reply t1, tp_user t2 "
						+ " where t1.user_id = t2.user_id "
						+ " and t1.post_id = " + rep.post_id 
						+ " order by t1.create_ts desc "); 
				
				ArrayList<Reply> userReplies = new ArrayList<Reply>();
				
				while(dbResultSet.next()){ 
					
					Reply reply = new Reply();
					
					reply.reply_id = dbResultSet.getLong("reply_id");
					reply.post_id = dbResultSet.getLong("post_id");
					reply.user_id = dbResultSet.getInt("user_id");
					reply.message = dbResultSet.getString("message");
					reply.status_id = dbResultSet.getString("status_id");
					reply.create_ts = dbResultSet.getTimestamp("create_ts");
					reply.update_ts = dbResultSet.getTimestamp("update_ts");
					reply.user.username = dbResultSet.getString("username");
					reply.user.picture_id = dbResultSet.getString("picture_id");
					
					userReplies.add(reply);
				}
				
				if(userReplies.size() > 0)
				{
					result.isSuccess = true;
					result.returnCode = OperationCode.ReturnCode.Info.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Info_default;
					result.setMessage("getReplies", Long.toString(rep.post_id ), 
							"Success for post_id");
					result.object = userReplies;
				}
				else
				{
					result.isSuccess = false;
					result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
					result.setMessage("getReplies", Long.toString(rep.post_id ) , 
							"Not Found for post_id");	
					result.object = userReplies;
				}							
				
			} catch (SQLException e) {
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
				result.returnCode = OperationCode.ReasonCode.Error_Login;
				result.setMessage("getReplies", Long.toString(rep.post_id ) , 
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

	//added by ue 15.01.2017 delete Reply
	public OperationResult deleteReply(Reply reply){
		
		OperationResult result = new OperationResult();	
		int effectedRows = 0;
		
		try {
			
			dbStatement = (Statement)dbConnection.createStatement();
			
			effectedRows = dbStatement.executeUpdate("UPDATE tp_reply "
					   + " SET status_id = 'D' "
					   + " WHERE reply_id = " + reply.reply_id 
					   + " and user_id = " + reply.user_id );	

			//effectedRows = dbStatement.getUpdateCount();			
			//dbConnection.commit();		
			
			if(effectedRows > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("deleteReply", String.valueOf(effectedRows) , " Reply was deleted!");
				result.object = effectedRows;
			}			
			else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("deleteReply", String.valueOf(effectedRows) , " Reply ID:" +reply.reply_id+ " Reply was not deleted!");
				
				LogManager logger =  new LogManager();	
				logger.createServerLog(dbStatement, "I" , reply.user_id, "deleteReply", result.message);	
			}
		
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("deleteReply", String.valueOf(reply.reply_id) , e.getMessage());
			
			LogManager logger =  new LogManager();	
			logger.createServerError(dbStatement, "E" , "1", reply.user_id, "deleteReply", result.message);	
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
