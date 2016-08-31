package managers;

import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.Like;
import models.Post;
import utils.Timef;

public class LikeManager extends BaseManager{

	public LikeManager(){
		super();
	}
	
	public OperationResult getLikeByUserIdAndPostId(int user_id, int post_id){
		OperationResult result = new OperationResult();
		try {
			dbStatement = (Statement)dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery("SELECT * FROM tp_like"
					+ " WHERE effecter_user_id = "+user_id+" and post_id = "+post_id+"");

			if(!dbResultSet.isBeforeFirst()){
				result.isSuccess = true;
				result.message = "There aren't any record";
				result.object = null;
			}else{
				dbResultSet.next();
				Like like = new Like();			
				like.user_id = dbResultSet.getInt("user_id");
				like.post_id = dbResultSet.getInt("post_id");
				result.isSuccess = true;
				result.message = "There are some records";
				result.object = like;
			}
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	
	public OperationResult likeThePost(Like like){
		
		OperationResult result = new OperationResult();
		
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			dbStatement.executeUpdate("INSERT INTO tp_like "
					 +"(post_id, effecter_user_id, user_id, like_dislike_ind, create_ts, update_ts)"
					+" VALUES ("+like.post_id+", "+like.effecter_user_id+","+like.user_id+",'L', '"+Timef.getDateTime()+"', '"+Timef.getDateTime()+"')");
			
			result.isSuccess = true;
			result.message = "You liked this post";
		
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
		
	}
	
	public OperationResult dislikeThePost(Like like){
		
		OperationResult result = new OperationResult();
		
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			dbStatement.executeUpdate("INSERT INTO tp_like "
					 +"(post_id, effecter_user_id, user_id, like_dislike_ind, create_ts, update_ts)"
					+" VALUES ("+like.post_id+", "+like.effecter_user_id+","+like.user_id+",'D', '"+Timef.getDateTime()+"', '"+Timef.getDateTime()+"')");
		
			result.isSuccess = true;
			result.message = "You liked this post";
			
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

	public OperationResult transLikePost(int user_id, int post_id){
		// Bu post'a daha önceden like veya dislike atýlmýþsa
		OperationResult result = getLikeByUserIdAndPostId(user_id, post_id);
		
		if(result.object != null){			
			result.isSuccess = false;
			result.message = "You already voted it";
			return result;
		}
		//Atilmamissa like/dislike atilan postu al
		PostManager postManager = new PostManager();
		result = postManager.getLikeAndDislikeCount(post_id);
		if(!result.isSuccess){
			
			return result;
		}
		Post post = (Post)result.object;
		//Like sayisini arttir Transaction'i baslat
		try {
			dbConnection.setAutoCommit(false);
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = "Transaction error" + e.getMessage();
			return result;
		}
		result = postManager.setLikeCount(post_id, post.like_count + 1);
		if(!result.isSuccess){		
			try {
				dbConnection.rollback();
			} catch (SQLException e) {
				result.isSuccess = false;
				result.message = e.getMessage();
				return result;
			}
			return result;
		}
		//Like tablosuna like gir
		Like like = new Like();
		like.user_id = post.user_id;
		like.effecter_user_id = user_id;
		like.post_id = post_id;
		like.create_ts = Timef.getDateTime();
		like.update_ts = Timef.getDateTime();
		result = likeThePost(like);
		if(!result.isSuccess){
			try {
				dbConnection.rollback();
			} catch (SQLException e) {
				result.isSuccess = false;
				result.message = e.getMessage();
				return result;
			}
			return result;
		}
		try {
			dbConnection.commit();
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			try {
				dbConnection.rollback();
				return result;
			} catch (SQLException e1) {
				result.isSuccess = false;
				result.message = e1.getMessage();
				return result;
			}
		}
		
		result.isSuccess = true;
		result.message = "You liked perfectly";
		try {
			dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public OperationResult transDislikePost(int user_id, int post_id){
		// Bu post'a daha önceden like veya dislike atýlmýþsa
		OperationResult result = getLikeByUserIdAndPostId(user_id, post_id);
		if(result.object != null){			
			result.isSuccess = false;
			result.message = "You already voted it";
			return result;
		}
		//Atýlmamýþsa like/dislike atýlan postu al
		PostManager postManager = new PostManager();
		result = postManager.getLikeAndDislikeCount(post_id);
		if(!result.isSuccess){
			
			return result;
		}
		Post post = (Post)result.object;
		//Like sayýsýný arttýr Transaction'ý baþlat
		try {
			dbConnection.setAutoCommit(false);
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = "Transaction error" + e.getMessage();
			return result;
		}
		result = postManager.setDislikeCount(post_id, post.dislike_count + 1);
		if(!result.isSuccess){		
			try {
				dbConnection.rollback();
			} catch (SQLException e) {
				result.isSuccess = false;
				result.message = e.getMessage();
				return result;
			}
			return result;
		}
		//Like tablosuna like gir
		Like like = new Like();
		like.user_id = post.user_id;
		like.effecter_user_id = user_id;
		like.post_id = post_id;
		like.create_ts = Timef.getDateTime();
		like.update_ts = Timef.getDateTime();
		result = dislikeThePost(like);
		if(!result.isSuccess){		
			try {
				dbConnection.rollback();
			} catch (SQLException e) {
				result.isSuccess = false;
				result.message = e.getMessage();
				return result;
			}
			return result;
		}
		try {
			dbConnection.commit();
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			try {
				dbConnection.rollback();
				return result;
			} catch (SQLException e1) {
				result.isSuccess = false;
				result.message = e1.getMessage();
				return result;
			}
		}
		
		result.isSuccess = true;
		result.message = "You disliked perfectly";
		
		try {
			dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
	//added by ue 13.08.2016 sadece bu fonksiyon kullanilabilir.
	public OperationResult likeOrDislikePost(Like like){
		
		//operation_type >> L : for like operation
		//operation_type >> D : for dislike operation
		
		OperationResult result = new OperationResult();
		LogManager logger =  new LogManager();
		
		int effectedRows = 0;
		String log_msg = " "; 
		
		try {
														
			dbStatement = (Statement) dbConnection.createStatement();
			dbConnection.setAutoCommit(false);
			
			String sql = "INSERT INTO tp_like "
			 		+" (post_id, effecter_user_id, user_id, like_dislike_ind, create_ts, update_ts)"
			 		+" VALUES ("+like.post_id+", "+like.effecter_user_id+","+like.user_id+",'"+like.like_dislike_ind+"', now(), now() )";			
			
			effectedRows = dbStatement.executeUpdate(sql);
			
			if(effectedRows > 0){
				
				PostManager postManager = new PostManager();
				OperationResult result2 = new OperationResult();
				
				result2 = postManager.setLikeOrDislikeCount(like.post_id, like.like_dislike_ind);				
				
				if(result2.isSuccess == true)
				{
					result.isSuccess = true;
					result.returnCode = OperationCode.ReturnCode.Info.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Info_default;
					result.setMessage("likeOrDislikePost", log_msg + "--" + String.valueOf(effectedRows) , "Like was inserted into tp_like!");
					result.object = effectedRows;
					log_msg = logger.createServerLog(dbStatement,"I",like.user_id,
							"likeOrDislikePost1",result2.message );
					dbConnection.commit();
				}
				else
				{
					dbConnection.rollback();
					result.isSuccess = false;
					result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
					result.setMessage("postManager.setLikeOrDislikeCount", String.valueOf(effectedRows) , result2.message);
					
					log_msg = logger.createServerLog(dbStatement,"I",like.user_id,
							"likeOrDislikePost2",result2.message );
					dbConnection.commit();
				}
				
			}else{
				dbConnection.rollback();
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("likeOrDislikePost", String.valueOf(effectedRows) , "Like was not inserted into tp_like!");
				log_msg = logger.createServerLog(dbStatement,"I",like.user_id,
						"likeOrDislikePost3",String.valueOf(effectedRows) );
			}
		
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.Info.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("likeOrDislikePost", String.valueOf(effectedRows) , e.getMessage());			
			log_msg = logger.createServerLog(dbStatement,"I",like.user_id,
					"likeOrDislikePost4",e.getMessage() );
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
