package managers;

import java.sql.SQLException;

import com.mysql.jdbc.Statement;

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
			dbConnection.close();
			return result;
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
	}
	
	public OperationResult likeThePost(Like like){
		OperationResult result = new OperationResult();
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			dbStatement.executeUpdate("INSERT INTO tp_like "
					 +"(post_id, effecter_user_id, user_id, like_dislike_ind, create_ts, update_ts)"
					+" VALUES ("+like.post_id+", "+like.effecter_user_id+","+like.user_id+",'L', '"+Timef.getDateTime()+"', '"+Timef.getDateTime()+"')");
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		result.isSuccess = true;
		result.message = "You liked this post";
		return result;
	}
	
	public OperationResult dislikeThePost(Like like){
		OperationResult result = new OperationResult();
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			dbStatement.executeUpdate("INSERT INTO tp_like "
					 +"(post_id, effecter_user_id, user_id, like_dislike_ind, create_ts, update_ts)"
					+" VALUES ("+like.post_id+", "+like.effecter_user_id+","+like.user_id+",'D', '"+Timef.getDateTime()+"', '"+Timef.getDateTime()+"')");
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		result.isSuccess = true;
		result.message = "You liked this post";
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
		return result;
	}
}
