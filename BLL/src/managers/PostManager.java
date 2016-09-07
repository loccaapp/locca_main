package managers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.Location;
import models.Post;

public class PostManager extends BaseManager {

	public PostManager(){
		super();
	}
	
	public OperationResult insert(Post post){
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			int i = dbStatement.executeUpdate("INSERT INTO tp_post "
					+ "(user_id, location_id, post_type, post_text, like_count, dislike_count,is_replied, to_fb, to_twitter, to_instagram, status_id, create_ts, update_ts)"
             +" VALUES("+post.user_id+", "+ post.location_id+",'"+post.post_type+"', '"+ post.post_text + "', 0, 0, 'N', 'N', 'N', 'N', 'A',now(),now())");
			
		} catch (SQLException e) {
			OperationResult result = new OperationResult();
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		OperationResult result = new OperationResult();
		result.isSuccess = true;
		result.message = "Your post sent";		
		return result;	
	}
	
	public OperationResult getLikeAndDislikeCount(int post_id){
		OperationResult result = new OperationResult();
		try {
			dbStatement = (Statement)dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery("SELECT user_id, like_count, dislike_count FROM tp_post"+
					" WHERE post_id ="+ post_id+"");
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		Post post = new Post();		
		try {
			dbResultSet.next();
			post.user_id = dbResultSet.getInt("user_id");
			post.like_count = dbResultSet.getInt("like_count");
			post.dislike_count = dbResultSet.getInt("dislike_count");
			result.isSuccess = true;
			result.object = post;
			return result;
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
	}
	
	public OperationResult setLikeCount(int post_id, int count){
		OperationResult result = new OperationResult();
		try {
			dbStatement = (Statement)dbConnection.createStatement();
			dbStatement.executeUpdate("UPDATE tp_post SET like_count = "+count+" WHERE post_id ="+post_id+"");
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		result.isSuccess = true;
		result.message = "Post updated";
		return result;
	}

	public OperationResult setDislikeCount(int post_id, int count){
		OperationResult result = new OperationResult();
		try {
			dbStatement = (Statement)dbConnection.createStatement();
			dbStatement.executeUpdate("UPDATE tp_post SET dislike_count = "+count+" WHERE post_id ="+post_id+"");
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		result.isSuccess = true;
		result.message = "Post updated";
		return result;
	}

	public OperationResult getBestPostsByLocation(int location_id, int start, int count){
		
		OperationResult result = new OperationResult();
		LogManager logger =  new LogManager();
		
		try {
			
			String query = "SELECT * "
					+ " FROM tp_post, tp_location, tp_user "
					+ " WHERE "
					+ " tp_post.location_id = tp_location.location_id and "
					+ " tp_post.user_id = tp_user.user_id and "
					+ " tp_post.location_id = "+location_id +"  "
					+ " ORDER BY tp_post.like_count DESC "
					+ " LIMIT "+start*count+", "+count+" ";
			
			dbStatement = (Statement) dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(query);						
			
			ArrayList<Post> userPosts = new ArrayList<Post>();
			while(dbResultSet.next()){
				Post post = new Post();
				post.post_id = dbResultSet.getInt("post_id");
				post.user_id = dbResultSet.getInt("user_id");
				post.post_text = dbResultSet.getString("post_text");
				post.create_ts = dbResultSet.getTimestamp("create_ts");
				post.update_ts = dbResultSet.getTimestamp("update_ts");
				//post.post_type = dbResultSet.getString("post_type").charAt(0);
				post.like_count = dbResultSet.getInt("like_count");
				post.dislike_count = dbResultSet.getInt("dislike_count");
				post.location.district_name = dbResultSet.getString("district_name");
				post.location.location_name = dbResultSet.getString("location_name"); 
				post.user.username = dbResultSet.getString("username");
				userPosts.add(post);
			}			
			
			if(userPosts.size() > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getBestPostsByLocation", String.valueOf(location_id), "Success best post list for this location");
				result.object = userPosts;
			}else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getBestPostsByLocation", String.valueOf(location_id), "There aren't any posts in this location");
			}	
						
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("getBestPostsByLocation", String.valueOf(location_id), e.getMessage());	
			
			logger.createServerError(dbStatement, "E" , "1", location_id, " ", result.message);
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	
	public OperationResult getLastPostsByLocation(int location_id, int start, int count){
		
		OperationResult result = new OperationResult();
		LogManager logger =  new LogManager();
		
		try {
			
			String query = "SELECT * "
					+ " FROM tp_post, tp_location, tp_user "
					+ " WHERE "
					+ " tp_post.location_id = tp_location.location_id and "
					+ " tp_post.user_id = tp_user.user_id and "
					+ " tp_post.location_id = "+location_id +"  "
					+ " ORDER BY tp_post.update_ts DESC  "
					+ " LIMIT "+start*count+", "+count+"";
			
			dbStatement = (Statement) dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(query);						
			
			ArrayList<Post> userPosts = new ArrayList<Post>();
			while(dbResultSet.next()){
				Post post = new Post();
				post.post_id = dbResultSet.getInt("post_id");
				post.user_id = dbResultSet.getInt("user_id");
				post.post_text = dbResultSet.getString("post_text");
				post.create_ts = dbResultSet.getTimestamp("create_ts");
				post.update_ts = dbResultSet.getTimestamp("update_ts");
				//post.post_type = dbResultSet.getString("post_type").charAt(0);
				post.like_count = dbResultSet.getInt("like_count");
				post.dislike_count = dbResultSet.getInt("dislike_count");
				post.location.location_id = dbResultSet.getInt("location_id");
				post.location.district_name = dbResultSet.getString("district_name");
				post.location.location_name = dbResultSet.getString("location_name");
				post.user.username = dbResultSet.getString("username");
				userPosts.add(post);
			}
			
			if(userPosts.size() > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getLastPostsByLocation", String.valueOf(location_id), "Success last posts list for this location");
				result.object = userPosts;
			}else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getLastPostsByLocation", String.valueOf(location_id), "There aren't any posts in this location");
			}	
						
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("getLastPostsByLocation", String.valueOf(location_id), e.getMessage());	
			
			logger.createServerError(dbStatement, "E" , "1", location_id, " ", result.message);
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	
	//reorganized by ue 03.09.2016
	public OperationResult getLastLocsByUserId(int user_id, int start, int count){
		
		OperationResult result = new OperationResult();
		LogManager logger =  new LogManager();
		
		try {
			
			String query = "SELECT tp_post.post_id,tp_post.user_id,tp_post.location_id,"
					+ " tp_post.post_type,tp_post.post_text,tp_post.post_image_id,"
					+ " tp_post.post_video_id,tp_post.is_replied,tp_post.to_fb,tp_post.to_twitter,"
					+ " tp_post.to_instagram,tp_post.status_id,tp_post.like_count,"
					+ " tp_post.dislike_count,tp_post.longitude,tp_post.latitude,tp_post.create_ts,"
					+ " tp_post.update_ts,"
					+ " tp_location.location_id,tp_location.country_id,tp_location.city_id,"
					+ " tp_location.district_name,tp_location.location_name,"
					+ " tp_location.location_brand_name,tp_location.location_type,"
					+ " tp_location.status_id,tp_location.start_ts,tp_location.end_ts,"
					+ " tp_user.user_id,tp_user.username "
					+"FROM tp_post, tp_location, tp_user "
					+"WHERE tp_post.user_id = tp_user.user_id "
					+"and tp_post.location_id = tp_location.location_id "
					+"and tp_post.user_id = " + user_id
					+" ORDER BY tp_post.create_ts DESC "
					+"LIMIT "+start*count+", "+count+"";			
			
			dbStatement = (Statement)dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(query);
			
			List<Post> userPosts = new ArrayList<Post>();
			while(dbResultSet.next()){
				Post post = new Post();
				post.post_id = dbResultSet.getInt("post_id");
				post.user_id = dbResultSet.getInt("user_id");
				post.post_image_id = dbResultSet.getString("post_image_id");
				post.post_video_id = dbResultSet.getString("post_video_id");
				post.is_replied = dbResultSet.getString("is_replied");
				post.latitude = dbResultSet.getDouble("latitude");
				post.longitude = dbResultSet.getDouble("longitude");
				post.post_text = dbResultSet.getString("post_text");
				post.create_ts = dbResultSet.getTimestamp("create_ts");
				post.update_ts = dbResultSet.getTimestamp("update_ts");
				post.like_count = dbResultSet.getInt("like_count");
				post.dislike_count = dbResultSet.getInt("dislike_count");
				post.location.location_id = dbResultSet.getInt("location_id");
				post.location.district_name = dbResultSet.getString("district_name");
				post.location.location_name = dbResultSet.getString("location_name");
				post.user.username = dbResultSet.getString("username");
				userPosts.add(post);
			}
			
			if(userPosts.size() > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getLastLocsByUserId", String.valueOf(user_id), "Success for user_id");
				result.object = userPosts;
			}else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getLastLocsByUserId", String.valueOf(user_id), "There aren't any recorded posts");
			}	
			
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("getLastLocsByUserId", String.valueOf(user_id), e.getMessage());	
			
			logger.createServerError(dbStatement, "E" , "1", user_id, " ", result.message);
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	
	//reorganized by ue 03.09.2016
	public OperationResult getBestLocsByUserId(int user_id, int start, int count){
		
		OperationResult result = new OperationResult();
		LogManager logger =  new LogManager();
		
		try {
			
			String query = "SELECT tp_post.post_id,tp_post.user_id,tp_post.location_id,"
					+ " tp_post.post_type,tp_post.post_text,tp_post.post_image_id,"
					+ " tp_post.post_video_id,tp_post.is_replied,tp_post.to_fb,"
					+ " tp_post.to_twitter,tp_post.to_instagram,tp_post.status_id,"
					+ " tp_post.like_count,tp_post.dislike_count,tp_post.longitude,"
					+ " tp_post.latitude,tp_post.create_ts,tp_post.update_ts,"
					+ " tp_location.location_id,tp_location.country_id,tp_location.city_id,"
					+ " tp_location.district_name,tp_location.location_name,"
					+ " tp_location.location_brand_name,tp_location.location_type,"
					+ " tp_location.status_id,tp_location.start_ts,tp_location.end_ts,"
					+ " tp_user.user_id,tp_user.username "
					+"FROM tp_post, tp_location, tp_user "
					+"WHERE tp_post.user_id = tp_user.user_id "
					+"and tp_post.location_id = tp_location.location_id "
					+"and tp_post.user_id = " + user_id
					+" ORDER BY tp_post.like_count DESC "
					+"LIMIT "+start*count+", "+count+"";
			
			dbStatement = (Statement)dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(query);
			
			List<Post> userPosts = new ArrayList<Post>();
			
			while(dbResultSet.next()){
				Post post = new Post();
				post.post_id = dbResultSet.getInt("post_id");
				post.user_id = dbResultSet.getInt("user_id");
				post.post_image_id = dbResultSet.getString("post_image_id");
				post.post_video_id = dbResultSet.getString("post_video_id");
				post.is_replied = dbResultSet.getString("is_replied");
				post.latitude = dbResultSet.getDouble("latitude");
				post.longitude = dbResultSet.getDouble("longitude");
				post.post_text = dbResultSet.getString("post_text");
				post.create_ts = dbResultSet.getTimestamp("create_ts");
				post.update_ts = dbResultSet.getTimestamp("update_ts");
				post.like_count = dbResultSet.getInt("like_count");
				post.dislike_count = dbResultSet.getInt("dislike_count");
				post.location.location_id = dbResultSet.getInt("location_id");
				post.location.district_name = dbResultSet.getString("district_name");
				post.location.location_name = dbResultSet.getString("location_name");
				post.user.username = dbResultSet.getString("username");
				userPosts.add(post);
			}
			
			if(userPosts.size() > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getBestLocsByUserId", String.valueOf(user_id), "Success for user_id");
				result.object = userPosts;
			}else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getBestLocsByUserId", String.valueOf(user_id), "There aren't any recorded posts");
			}
			
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("getBestLocsByUserId", String.valueOf(user_id), e.getMessage());
			
			logger.createServerError(dbStatement, "E" , "1", user_id, " ", result.message);
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;

	}

	//added by ue 08.08.2016
	//lokasyonun icindeki post'lari search eder
	public OperationResult searchPostInLocation(int location_id, String search_text, int start, int count){
		
		OperationResult result = new OperationResult();
		LogManager logger =  new LogManager();
		
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("select * from tp_post, tp_location, tp_user "
					+ "where "
					+ "tp_post.location_id = tp_location.location_id and "
					+ "tp_post.user_id = tp_user.user_id and "
					+ "tp_post.location_id = " + location_id + " and "
					+ "tp_post.post_text like '%" + search_text.trim() + "%' "
					+ "ORDER BY tp_post.create_ts DESC "
					+ "LIMIT "+start*count+","+count+"");
			
			ArrayList<Post> posts = new ArrayList<Post>();
			while(dbResultSet.next()){
				Post post = new Post();
				post.post_id = dbResultSet.getInt("post_id");
				post.user_id = dbResultSet.getInt("user_id");
				post.post_text = dbResultSet.getString("post_text");
				post.like_count = dbResultSet.getInt("like_count");
				post.dislike_count = dbResultSet.getInt("dislike_count");
				post.location.district_name = dbResultSet.getString("district_name"); 
				post.location.location_name = dbResultSet.getString("location_name"); 
				post.user.username = dbResultSet.getString("username");
				posts.add(post);
			}			
			
			if(posts.size() > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("searchPostInLocation", Integer.toString(location_id), "Success for location_id");
				result.object = posts;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("searchPostInLocation", Integer.toString(location_id) , "Failure for location_id");					
			}			
			
		} catch (SQLException e) {			
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("searchPostInLocation", Integer.toString(location_id) + search_text , e.getMessage());
			
			logger.createServerError(dbStatement, "E" , "1", 0, " ", result.message);
		}		
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	
	//added by ue 13.08.2016
	//bir user'in begendigi loc'lari last'a gore desc siralanacak fonksiyon gerekiyor.
	public OperationResult getLikedPostsByUserId(int user_id, int start, int count){
		
		OperationResult result = new OperationResult();
		LogManager logger =  new LogManager();
		
		try {
			
			String query = "SELECT t1.post_id,t1.user_id,t1.location_id,t1.post_type,t1.post_text,"
					+ "t1.post_image_id,t1.post_video_id,t1.is_replied,t1.to_fb,t1.to_twitter,"
					+ "t1.to_instagram,t1.status_id,t1.like_count,t1.dislike_count,t1.longitude,t1.latitude,"
					+ "t1.create_ts,t1.update_ts,"
					+ "t3.location_id,t3.country_id,t3.city_id,t3.district_name,t3.location_name,"
					+ "t3.location_brand_name,t3.location_type,t3.status_id,t3.start_ts,t3.end_ts,"
					+ "t4.username "
					+" FROM tp_post t1, tp_like t2, tp_location t3, tp_user t4 "
					+" WHERE t1.user_id = t2.user_id "
					+" and t1.post_id = t2.post_id "
					+" and t1.location_id = t3.location_id "
					+" and t1.user_id = t4.user_id "
					+" and t2.effecter_user_id = " + user_id
					+" ORDER BY t1.create_ts DESC "
					+" LIMIT "+start*count+", "+count+"";
			
			dbStatement = (Statement)dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(query);
			
			List<Post> userPosts = new ArrayList<Post>();
			while(dbResultSet.next()){
				Post post = new Post();
				post.post_id = dbResultSet.getInt("post_id");
				post.user_id = dbResultSet.getInt("user_id");
				post.post_image_id = dbResultSet.getString("post_image_id");
				post.post_video_id = dbResultSet.getString("post_video_id");
				post.is_replied = dbResultSet.getString("is_replied");
				post.latitude = dbResultSet.getDouble("latitude");
				post.longitude = dbResultSet.getDouble("longitude");
				post.post_text = dbResultSet.getString("post_text");
				post.create_ts = dbResultSet.getTimestamp("create_ts");
				post.update_ts = dbResultSet.getTimestamp("update_ts");
				post.like_count = dbResultSet.getInt("like_count");
				post.dislike_count = dbResultSet.getInt("dislike_count");
				post.location.location_id = dbResultSet.getInt("location_id");
				post.location.district_name = dbResultSet.getString("district_name");
				post.location.location_name = dbResultSet.getString("location_name");
				post.user.username = dbResultSet.getString("username");
				userPosts.add(post);
			}
			
			if(userPosts.size() > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getLikedPostsByUserId", String.valueOf(user_id), "Success for user_id");
				result.object = userPosts;
			}else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getLikedPostsByUserId", String.valueOf(user_id), "There aren't any recorded posts");
			}
			
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("getLikedPostsByUserId", String.valueOf(user_id), e.getMessage());
			
			logger.createServerError(dbStatement, "E" , "1", user_id, " ", result.message);
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

	//added by ue 13.08.2016
	//bir user'in begendigi loc'lari last'a gore desc siralanacak fonksiyon gerekiyor.
	public OperationResult getPopularPostsByTimeInterval(int start, int count, int time_interval){
		
		OperationResult result = new OperationResult();
		LogManager logger =  new LogManager();		
		String time_interval_value = " ";		
		
		try {	
			
			if(time_interval==4)
			{
				time_interval_value = "INTERVAL 120 DAY";
			}
			else if(time_interval==3)
			{
				time_interval_value = "INTERVAL 30 DAY";			
			}
			else if(time_interval==2)
			{
				time_interval_value = "INTERVAL 7 DAY";			
			}
			else if(time_interval==1)
			{
				time_interval_value = "INTERVAL 24 HOUR";			
			}
			else 
			{
				time_interval_value = "INTERVAL 24 HOUR";			
			}
			
			String query = "SELECT t1.post_id,t1.user_id,t1.location_id,t1.post_type,t1.post_text,"
					+ "t1.post_image_id,t1.post_video_id,t1.is_replied,t1.to_fb,t1.to_twitter,"
					+ "t1.to_instagram,t1.status_id,t1.like_count,t1.dislike_count,t1.longitude,t1.latitude,"
					+ "t1.create_ts,t1.update_ts,"
					+ "t3.location_id,t3.country_id,t3.city_id,t3.district_name,t3.location_name,"
					+ "t3.location_brand_name,t3.location_type,t3.status_id,t3.start_ts,t3.end_ts,"
					+ "t4.username "
					+" FROM tp_post t1, tp_location t3, tp_user t4 "
					+" WHERE t1.location_id = t3.location_id "
					+" and t1.user_id = t4.user_id "
					+" and t1.create_ts > DATE_SUB(NOW(), " +time_interval_value+ ") "
					+" ORDER BY t1.like_count DESC, t1.create_ts DESC "
					+" LIMIT "+start*count+", "+count+"";
			
			dbStatement = (Statement)dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(query);
			
			List<Post> userPosts = new ArrayList<Post>();
			while(dbResultSet.next()){
				Post post = new Post();
				post.post_id = dbResultSet.getInt("post_id");
				post.user_id = dbResultSet.getInt("user_id");
				post.post_image_id = dbResultSet.getString("post_image_id");
				post.post_video_id = dbResultSet.getString("post_video_id");
				post.is_replied = dbResultSet.getString("is_replied");
				post.latitude = dbResultSet.getDouble("latitude");
				post.longitude = dbResultSet.getDouble("longitude");
				post.post_text = dbResultSet.getString("post_text");
				post.create_ts = dbResultSet.getTimestamp("create_ts");
				post.update_ts = dbResultSet.getTimestamp("update_ts");
				post.like_count = dbResultSet.getInt("like_count");
				post.dislike_count = dbResultSet.getInt("dislike_count");
				post.location.location_id = dbResultSet.getInt("location_id");
				post.location.district_name = dbResultSet.getString("district_name");
				post.location.location_name = dbResultSet.getString("location_name");
				post.user.username = dbResultSet.getString("username");
				userPosts.add(post);
			}
			
			if(userPosts.size() > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getLikedPostsByUserId", " " , "Success for user_id");
				result.object = userPosts;
			}else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getLikedPostsByUserId", " " , "There aren't any recorded posts");
			}
			
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("getLikedPostsByUserId", " ", e.getMessage());
			
			logger.createServerError(dbStatement, "E" , "1", 0, " ", result.message);
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	
	//added by ue 13.08.2016
	//like ve dislike operasyonlari tekrar duzenlendi.
	public OperationResult setLikeOrDislikeCount(long post_id, String operation_type){
		
		//operation_type >> L : for like operation
		//operation_type >> D : for dislike operation
		
		OperationResult result = new OperationResult();
		LogManager logger =  new LogManager();		
		int effectedRows = 0;
		
		try {
			
			dbStatement = (Statement)dbConnection.createStatement();
			
			String aa = "uygar";
			if(operation_type.trim().contains("L"))
			{
				effectedRows = dbStatement.executeUpdate("UPDATE tp_post "
						   + " SET like_count = like_count + 1 "
						   + " WHERE post_id ="+post_id+"");	
			}
			else if(operation_type.trim().contains("D"))
			{
				effectedRows = dbStatement.executeUpdate("UPDATE tp_post "
						   + " SET dislike_count = dislike_count + 1 "
						   + " WHERE post_id ="+post_id+"");
			}
			
			//effectedRows = dbStatement.getUpdateCount();			
			//dbConnection.commit();		
			
			if(effectedRows > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("setLikeOrDislikeCount", String.valueOf(effectedRows) , "Post was updated!");
				result.object = effectedRows;
			}			
			else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("setLikeOrDislikeCount", String.valueOf(effectedRows) , " Post ID:" +post_id+ "Post was not updated!");
			}
		
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("setLikeOrDislikeCount", String.valueOf(post_id) , e.getMessage());
			
			logger.createServerError(dbStatement, "E" , "1", 0, " ", result.message);	
		}
		
		return result;
	}	
	
	//Get Offers by Location
	public OperationResult getOffersByLocation(int location_id, int start, int count){
		
		OperationResult result = new OperationResult();
		LogManager logger =  new LogManager();
		
		try {
			
			String query = "SELECT t1.post_id,t1.user_id,t1.location_id,t1.post_type,t1.post_text,"
					+ "t1.post_image_id,t1.post_video_id,t1.is_replied,t1.to_fb,t1.to_twitter,"
					+ "t1.to_instagram,t1.status_id,t1.like_count,t1.dislike_count,t1.longitude,t1.latitude,"
					+ "t1.create_ts,t1.update_ts,"
					+ "t3.location_id,t3.country_id,t3.city_id,t3.district_name,t3.location_name,"
					+ "t3.location_brand_name,t3.location_type,t3.status_id,t3.start_ts,t3.end_ts,"
					+ "t4.username "
					+" FROM tp_post t1, tp_location t3, tp_user t4 "
					+" WHERE t1.location_id = t3.location_id "
					+" and t1.user_id = t4.user_id "
					+" and t1.location_id = " + location_id
					+" and t1.post_type = 'O' "
					+" and t1.create_ts > DATE_SUB(NOW(), INTERVAL 360 DAY) "
					+" ORDER BY t1.create_ts DESC "
					+" LIMIT "+start*count+", "+count+"";
			
			dbStatement = (Statement)dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(query);
			
			List<Post> userPosts = new ArrayList<Post>();
			
			while(dbResultSet.next()){
				
				Post post = new Post();
				post.post_id = dbResultSet.getInt("post_id");
				post.user_id = dbResultSet.getInt("user_id");
				post.post_image_id = dbResultSet.getString("post_image_id");
				post.post_video_id = dbResultSet.getString("post_video_id");
				post.is_replied = dbResultSet.getString("is_replied");
				post.latitude = dbResultSet.getDouble("latitude");
				post.longitude = dbResultSet.getDouble("longitude");
				post.post_text = dbResultSet.getString("post_text");
				post.create_ts = dbResultSet.getTimestamp("create_ts");
				post.update_ts = dbResultSet.getTimestamp("update_ts");
				post.like_count = dbResultSet.getInt("like_count");
				post.dislike_count = dbResultSet.getInt("dislike_count");
				post.location.location_id = dbResultSet.getInt("location_id");
				post.location.district_name = dbResultSet.getString("district_name");
				post.location.location_name = dbResultSet.getString("location_name");
				post.user.username = dbResultSet.getString("username");
				userPosts.add(post);
			}
			
			if(userPosts.size() > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getOffersByLocation", " " , "Success for user_id");
				result.object = userPosts;
			}else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getOffersByLocation", query , "There aren't any recorded posts");
			}
			
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("getOffersByLocation", Integer.toString(location_id) , e.getMessage());
			
			logger.createServerError(dbStatement, "E" , "1", 0, " ", result.message);		
		}
		
		try {
			dbConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
		
	//goruntulenen post'larin istatisigi ile ilgili veriler yeni yaratilacak tp_statistics tablosunda tutulacak.
	
	//reply post 
	
}
