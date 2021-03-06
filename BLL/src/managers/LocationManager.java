package managers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.lang.*;

import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.*;

public class LocationManager extends BaseManager {

	public LocationManager(){
		super();
	}
	
	//added by ue 01.06.2016
	public OperationResult getLocations(String search_text){
		
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("select * from tp_location where "
					+ " location_name like '%"+search_text+"%' "
					+ " or district_name like '%"+search_text+"%' "
				    + " or location_tags like '%"+search_text+"%' order by location_name asc ");								
			
			ArrayList<Location> locations = new ArrayList<Location>();
			while(dbResultSet.next()){ 
				
				Location location = new Location(); 
				location.location_id = dbResultSet.getInt("location_id");
				location.country_id = dbResultSet.getInt("country_id");
				location.city_id = dbResultSet.getInt("city_id");
				location.district_name = dbResultSet.getString("district_name");
				location.location_name = dbResultSet.getString("location_name");
				location.location_brand_name = dbResultSet.getString("location_brand_name");
				location.location_type = dbResultSet.getString("location_type");	
				location.location_sub_type = dbResultSet.getString("location_sub_type");
				location.longitude = dbResultSet.getDouble("longitude");
				location.latitude = dbResultSet.getDouble("latitude");
				location.radius = dbResultSet.getDouble("radius");
				location.status_id = dbResultSet.getString("status_id");
				location.location_tags = dbResultSet.getString("location_tags");	
				//location.start_ts = dbResultSet.getTimestamp("start_ts");
				//location.end_ts = dbResultSet.getTimestamp("end_ts");
				//location.create_ts = dbResultSet.getTimestamp("create_ts");
				//location.update_ts = dbResultSet.getTimestamp("update_ts");
				
				locations.add(location);
			}
			
			if(locations.size() > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getLocations", search_text, "Success for search_text");
				result.object = locations;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getLocations", search_text , "Failure for search_text");	
				result.object = " ";
			}	
						
		} catch (SQLException e) {
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getLocations", search_text , e.getMessage());
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
	
	//added by ue 07.06.2016
	public OperationResult getLocation(int location_id){
		
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("select * from tp_location where location_id = "+location_id+" ");
			
			ArrayList<Location> locations = new ArrayList<Location>();
			Location location = new Location();
			while(dbResultSet.next()){ 
				
				location.location_id = dbResultSet.getInt("location_id");
				location.country_id = dbResultSet.getInt("country_id");
				location.city_id = dbResultSet.getInt("city_id");
				location.district_name = dbResultSet.getString("district_name");
				location.location_name = dbResultSet.getString("location_name");
				location.location_brand_name = dbResultSet.getString("location_brand_name");
				location.location_type = dbResultSet.getString("location_type");	
				location.location_sub_type = dbResultSet.getString("location_sub_type");
				location.longitude = dbResultSet.getDouble("longitude");
				location.latitude = dbResultSet.getDouble("latitude");
				location.radius = dbResultSet.getDouble("radius");
				location.status_id = dbResultSet.getString("status_id");
				location.location_tags = dbResultSet.getString("location_tags");	
				location.start_ts = dbResultSet.getTimestamp("start_ts");
				location.end_ts = dbResultSet.getTimestamp("end_ts");
				location.create_ts = dbResultSet.getTimestamp("create_ts");
				location.update_ts = dbResultSet.getTimestamp("update_ts");		
				
				locations.add(location);
			}
			
			if(locations.size() > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getLocation", Integer.toString(location_id), "Success for location_id");
				result.object = locations;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getLocation", Integer.toString(location_id) , "Failure for location_id");	
				result.object = locations;
			}			
			
		} catch (SQLException e) {			
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getLocation", Integer.toString(location_id) , e.getMessage());
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
	
	//added by ue 07.06.2016
	public OperationResult checkLocation(Double latitude, Double longitude, Double radius){
		
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("SELECT * FROM "
							     + "( "
							     + "SELECT t1.*, r,"
								 + "      units * DEGREES( ACOS(  "
					             + "      COS(RADIANS(latpoint))  "
					             + "    * COS(RADIANS(latitude))  "
					             + "    * COS(RADIANS(longpoint) - RADIANS(longitude))  "
					             + "    + SIN(RADIANS(latpoint))  "
					             + "    * SIN(RADIANS(latitude)))) AS distance "
								 + "	   FROM tp_location t1 "
								 + "	   JOIN ( "
								 + "	        SELECT " + latitude  + " AS latpoint,   "
								 + "	               " + longitude + " AS longpoint,  "
								 + "	               " + radius + " AS r,  "
								 + "	               111.045 AS units "
								 + "	        ) AS t2 ON (1=1) "
								 + "	) t3 "
								 + "	WHERE radius > distance  "
								 + "	order by distance asc ");
			
			ArrayList<Location> locations = new ArrayList<Location>();
			
			String abc = " ";
			while(dbResultSet.next()){ 
								
				Location location = new Location();
				
				location.location_id = dbResultSet.getInt("location_id");
				location.country_id = dbResultSet.getInt("country_id");
				location.city_id = dbResultSet.getInt("city_id");
				location.district_name = dbResultSet.getString("district_name");
				location.location_name = dbResultSet.getString("location_name");
				location.location_brand_name = dbResultSet.getString("location_brand_name");
				location.location_type = dbResultSet.getString("location_type");	
				location.location_sub_type = dbResultSet.getString("location_sub_type");
				location.longitude = dbResultSet.getDouble("longitude");
				location.latitude = dbResultSet.getDouble("latitude");
				location.radius = dbResultSet.getDouble("radius");
				location.status_id = dbResultSet.getString("status_id");
				location.location_tags = dbResultSet.getString("location_tags");	
				//location.start_ts = dbResultSet.getTimestamp("start_ts");
				//location.end_ts = dbResultSet.getTimestamp("end_ts");
				//location.create_ts = dbResultSet.getTimestamp("create_ts");
				//location.update_ts = dbResultSet.getTimestamp("update_ts");		
				//location.distance = dbResultSet.getDouble("distance"); 
				
				abc = abc + " "  + location.location_name;
				
				locations.add(location);
								
			}
			
			if(locations.size() > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("CheckLocation"
						, Double.toString(latitude) + Double.toString(longitude)
						, "Success for location" + abc);
				result.object = locations;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("CheckLocation"
						, Double.toString(latitude) + Double.toString(longitude)
						, "Failure for location");	
				result.object = locations;
			}						
			
		} catch (SQLException e) {			
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("CheckLocation"
					, Double.toString(latitude) + Double.toString(longitude)
					, e.getMessage());
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
	
	//added by ue 07.06.2016
	public OperationResult checkAndInsertLocation(int user_id, Double latitude, Double longitude, Double radius){
		
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("SELECT * FROM "
							     + "( "
							     + "SELECT t1.*, r,"
								 + "      units * DEGREES( ACOS(  "
					             + "      COS(RADIANS(latpoint))  "
					             + "    * COS(RADIANS(latitude))  "
					             + "    * COS(RADIANS(longpoint) - RADIANS(longitude))  "
					             + "    + SIN(RADIANS(latpoint))  "
					             + "    * SIN(RADIANS(latitude)))) AS distance "
								 + "	   FROM tp_location t1 "
								 + "	   JOIN ( "
								 + "	        SELECT " + latitude  + " AS latpoint,   "
								 + "	               " + longitude + " AS longpoint,  "
								 + "	               " + radius + " AS r,  "
								 + "	               111.045 AS units "
								 + "	        ) AS t2 ON (1=1) "
								 + "	) t3 "
								 + "	WHERE radius > distance  "
								 + "	order by distance asc ");
			
			ArrayList<Location> locations = new ArrayList<Location>();
			
			String abc = " ";
			while(dbResultSet.next()){ 
								
				Location location = new Location();
				
				location.location_id = dbResultSet.getInt("location_id");
				location.country_id = dbResultSet.getInt("country_id");
				location.city_id = dbResultSet.getInt("city_id");
				location.district_name = dbResultSet.getString("district_name");
				location.location_name = dbResultSet.getString("location_name");
				location.location_brand_name = dbResultSet.getString("location_brand_name");
				location.location_type = dbResultSet.getString("location_type");	
				location.location_sub_type = dbResultSet.getString("location_sub_type");
				location.longitude = dbResultSet.getDouble("longitude");
				location.latitude = dbResultSet.getDouble("latitude");
				location.radius = dbResultSet.getDouble("radius");
				location.status_id = dbResultSet.getString("status_id");
				location.location_tags = dbResultSet.getString("location_tags");	
				//location.start_ts = dbResultSet.getTimestamp("start_ts");
				//location.end_ts = dbResultSet.getTimestamp("end_ts");
				//location.create_ts = dbResultSet.getTimestamp("create_ts");
				//location.update_ts = dbResultSet.getTimestamp("update_ts");		
				//location.distance = dbResultSet.getDouble("distance"); 
				
				locations.add(location);
				
				UserLocation userLocation = new UserLocation(user_id, 
															location.location_id, 
															longitude, 
															latitude, 
															null );

				OperationResult or = insertUserLocation(userLocation); 
								
			}
			
			if(locations.size() > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("checkAndInsertLocation"
						, Double.toString(latitude) + Double.toString(longitude)
						, "Success for location" + abc);
				result.object = locations;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("checkAndInsertLocation"
						, " user_id:" + Integer.toString(user_id) 
						+ " latitude:" + Double.toString(latitude) 
						+ " longitude:" + Double.toString(longitude)
						, "Failure for location");	
				result.object = locations;
			}						
			
		} catch (SQLException e) {			
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("checkAndInsertLocation"
					, Double.toString(latitude) + Double.toString(longitude)
					, e.getMessage());
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
	
	//added by ue 28.06.2016
	public OperationResult getNearestLocations(Double latitude, Double longitude){
		
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("SELECT * FROM "
							     + "( "
							     + "SELECT t1.*, "
								 + "      units * DEGREES( ACOS(  "
					             + "      COS(RADIANS(latpoint))  "
					             + "    * COS(RADIANS(latitude))  "
					             + "    * COS(RADIANS(longpoint) - RADIANS(longitude))  "
					             + "    + SIN(RADIANS(latpoint))  "
					             + "    * SIN(RADIANS(latitude)))) AS distance "
								 + "	   FROM tp_location t1 "
								 + "	   JOIN ( "
								 + "	        SELECT " + latitude  + " AS latpoint,   "
								 + "	               " + longitude + " AS longpoint,  "
								 + "	               111.045 AS units "
								 + "	        ) AS t2 ON (1=1) "
								 + "	) t3 "
								 + "	order by distance asc ");
			
			ArrayList<Location> locations = new ArrayList<Location>();
			
			while(dbResultSet.next()){ 
								
				Location location = new Location();
				
				location.location_id = dbResultSet.getInt("location_id");
				location.country_id = dbResultSet.getInt("country_id");
				location.city_id = dbResultSet.getInt("city_id");
				location.district_name = dbResultSet.getString("district_name");
				location.location_name = dbResultSet.getString("location_name");
				location.location_brand_name = dbResultSet.getString("location_brand_name");
				location.location_type = dbResultSet.getString("location_type");	
				location.location_sub_type = dbResultSet.getString("location_sub_type");
				location.longitude = dbResultSet.getDouble("longitude");
				location.latitude = dbResultSet.getDouble("latitude");
				location.radius = dbResultSet.getDouble("radius");
				location.status_id = dbResultSet.getString("status_id");
				location.location_tags = dbResultSet.getString("location_tags");	
				location.start_ts = dbResultSet.getTimestamp("start_ts");
				location.end_ts = dbResultSet.getTimestamp("end_ts");
				location.create_ts = dbResultSet.getTimestamp("create_ts");
				location.update_ts = dbResultSet.getTimestamp("update_ts");		
				location.distance = dbResultSet.getDouble("distance"); 
				
				locations.add(location);
			}
			
			if(locations.size() > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getNearestLocations"
						, Double.toString(latitude) + Double.toString(longitude)
						, "Success for location" );
				result.object = locations;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getNearestLocations"
						, Double.toString(latitude) + Double.toString(longitude)
						, "Failure for location");	
				result.object = locations;
			}						
			
		} catch (SQLException e) {			
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getNearestLocations"
					, Double.toString(latitude) + Double.toString(longitude)
					, e.getMessage());
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
	
	//added by ue 17.06.2016
	public OperationResult insertUserLocation(UserLocation userLocation){
				
		OperationResult result = new OperationResult();
		
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			int retVal = dbStatement.executeUpdate("INSERT INTO tp_user_location " 
					+ "(user_id,location_id,longitude,latitude,create_ts ) " 
					+ "VALUES "
					+ " ( "+userLocation.user_id + ","
						   +userLocation.location_id + ","
					       +userLocation.longitude + ","
						   +userLocation.latitude+","
					       +" now() )");
			
			if (retVal>0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("insertUserLocation"
						, Integer.toString(userLocation.user_id) 
						, "Success for user_id" );
				result.object = retVal;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("insertUserLocation"
						,  Integer.toString(userLocation.user_id) 
						, "Failure for location");	
				result.object = retVal;
			}
			
			
		} catch (SQLException e) {
			
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("insertUserLocation"
					, Integer.toString(userLocation.user_id) 
					, e.getMessage());
			result.object = " ";
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
	
	//added by ue 23.06.2016
	public OperationResult checkUserLocation(int user_id, int location_id){
			
			OperationResult result = new OperationResult();
			try {			
				dbStatement = (Statement) dbConnection.createStatement();
				
				dbResultSet = dbStatement.executeQuery("select * from tp_user_location "
						+ " where location_id = "+ location_id + " "
						+ " and user_id = " + user_id + " "
						+ " and create_ts > DATE_SUB(NOW(), INTERVAL 90 HOUR) "
						+ " order by create_ts desc ");
				
				ArrayList<UserLocation> userLocations = new ArrayList<UserLocation>();
				UserLocation userLocation = new UserLocation();
				while(dbResultSet.next()){ 
					
					userLocation.user_id = dbResultSet.getInt("user_id");
					userLocation.location_id = dbResultSet.getInt("location_id");
					userLocation.longitude = dbResultSet.getDouble("longitude");
					userLocation.latitude = dbResultSet.getDouble("latitude");
					//userLocation.create_ts = dbResultSet.getTimestamp("create_ts");	
					
					userLocations.add(userLocation);
				}
				
				if(userLocations.size() > 0)
				{
					result.isSuccess = true;
					result.returnCode = OperationCode.ReturnCode.Info.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Info_default;
					result.setMessage("CheckUserLocation", Integer.toString(user_id), "Success for user_id");
					result.object = userLocations;
				}
				else
				{
					result.isSuccess = false;
					result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
					result.setMessage("CheckUserLocation", Integer.toString(user_id) , "Not Found for user_id");	
					result.object = userLocations;
				}							
				
			} catch (SQLException e) {			
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
				result.returnCode = OperationCode.ReasonCode.Error_Login;
				result.setMessage("CheckUserLocation", Integer.toString(user_id) , e.getMessage());
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

	//added by ue 23.06.2016
	public OperationResult getUserLocation(int user_id){
			
			OperationResult result = new OperationResult();
			try {			
				dbStatement = (Statement) dbConnection.createStatement();
				
				dbResultSet = dbStatement.executeQuery("select * from "
						+ " tp_user_location t1, "
						+ " tp_location t2 "
						+ " where t1.location_id = t2.location_id "
						+ " and t1.user_id = " + user_id + " "
						+ " and t1.create_ts > DATE_SUB(NOW(), INTERVAL 90 DAY) "
						+ " order by t1.create_ts desc "
						+ " limit 1 "); 
				
				ArrayList<UserLocation> userLocations = new ArrayList<UserLocation>();
				
				while(dbResultSet.next()){ 
					
					UserLocation userLocation = new UserLocation();
					
					userLocation.user_id = dbResultSet.getInt("user_id");
					userLocation.location_id = dbResultSet.getInt("location_id");
					userLocation.location_name = dbResultSet.getString("location_name");
					userLocation.district_name = dbResultSet.getString("district_name");								
					userLocation.longitude = dbResultSet.getDouble("longitude");
					userLocation.latitude = dbResultSet.getDouble("latitude");
					userLocation.create_ts = dbResultSet.getTimestamp("create_ts");	
					
					userLocations.add(userLocation);
				}
				
				if(userLocations.size() > 0)
				{
					result.isSuccess = true;
					result.returnCode = OperationCode.ReturnCode.Info.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Info_default;
					result.setMessage("GetUserLocation", Integer.toString(user_id), "Success for user_id");
					result.object = userLocations;
				}
				else
				{
					result.isSuccess = false;
					result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
					result.setMessage("GetUserLocation", Integer.toString(user_id) , "Not Found for user_id");	
					result.object = userLocations;
				}							
				
			} catch (SQLException e) {			
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
				result.returnCode = OperationCode.ReasonCode.Error_Login;
				result.setMessage("GetUserLocation", Integer.toString(user_id) , e.getMessage());
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
	
	//added by ue 08.08.2016
	public OperationResult getNearestAndPopularLocations(int user_id, Double latitude, Double longitude){
		
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("SELECT * FROM "
							     + "( "
							     + "SELECT t1.*, "
								 + "      units * DEGREES( ACOS(  "
					             + "      COS(RADIANS(latpoint))  "
					             + "    * COS(RADIANS(latitude))  "
					             + "    * COS(RADIANS(longpoint) - RADIANS(longitude))  "
					             + "    + SIN(RADIANS(latpoint))  "
					             + "    * SIN(RADIANS(latitude)))) AS distance "
								 + "	   FROM tp_location t1 "
								 + "	   JOIN ( "
								 + "	        SELECT " + latitude  + " AS latpoint,   "
								 + "	               " + longitude + " AS longpoint,  "
								 + "	               111.045 AS units "
								 + "	        ) AS t2 ON (1=1) "
								 + "	) t3 "
								 + "	order by distance asc "
								 + "    limit 5 ");
			
			ArrayList<Location> locations = new ArrayList<Location>();
			
			//sadece en yakin lokasyon Inside olarak isaretlenir.
			boolean insideControl = true;
			
			while(dbResultSet.next()){ 
								
				Location location = new Location();
				
				if(dbResultSet.getDouble("radius") > dbResultSet.getDouble("distance") && insideControl)
				{
					insideControl = false;
					Location locationIn = new Location();
					locationIn.location_id = dbResultSet.getInt("location_id");
					locationIn.country_id = dbResultSet.getInt("country_id");
					locationIn.city_id = dbResultSet.getInt("city_id");
					locationIn.district_name = dbResultSet.getString("district_name");
					locationIn.location_name = dbResultSet.getString("location_name");
					locationIn.location_brand_name = dbResultSet.getString("location_brand_name");
					locationIn.location_type = dbResultSet.getString("location_type");	
					locationIn.location_sub_type = "I"; //Inside location flag
					locationIn.longitude = dbResultSet.getDouble("longitude");
					locationIn.latitude = dbResultSet.getDouble("latitude");
					locationIn.radius = dbResultSet.getDouble("radius");
					locationIn.status_id = dbResultSet.getString("status_id");
					locationIn.location_tags = dbResultSet.getString("location_tags");	
					locationIn.start_ts = dbResultSet.getTimestamp("start_ts");
					locationIn.end_ts = dbResultSet.getTimestamp("end_ts");
					locationIn.create_ts = dbResultSet.getTimestamp("create_ts"); 
					locationIn.update_ts = dbResultSet.getTimestamp("update_ts");		
					locationIn.distance = dbResultSet.getDouble("distance");	
					locations.add(locationIn); 
					
					UserLocation userLocation = new UserLocation(user_id, 
							locationIn.location_id, 
							longitude, 
							latitude, 
							null ); 

					OperationResult or = insertUserLocation(userLocation); 
				}
				
				location.location_id = dbResultSet.getInt("location_id");
				location.country_id = dbResultSet.getInt("country_id");
				location.city_id = dbResultSet.getInt("city_id");
				location.district_name = dbResultSet.getString("district_name");
				location.location_name = dbResultSet.getString("location_name");
				location.location_brand_name = dbResultSet.getString("location_brand_name");
				location.location_type = dbResultSet.getString("location_type");	
				location.location_sub_type = "N"; //nearest location flag
				location.longitude = dbResultSet.getDouble("longitude");
				location.latitude = dbResultSet.getDouble("latitude");
				location.radius = dbResultSet.getDouble("radius");
				location.status_id = dbResultSet.getString("status_id");
				location.location_tags = dbResultSet.getString("location_tags");	
				location.start_ts = dbResultSet.getTimestamp("start_ts");
				location.end_ts = dbResultSet.getTimestamp("end_ts");
				location.create_ts = dbResultSet.getTimestamp("create_ts");
				location.update_ts = dbResultSet.getTimestamp("update_ts");		
				location.distance = dbResultSet.getDouble("distance"); 
				
				locations.add(location);
								
			}
			
			dbResultSet = dbStatement.executeQuery("select distinct t3.* from tp_location t3, "
									 + " ( select t2.location_id, count(*) cnt from " 
									 + " tp_user_location t1, tp_location t2 "
									 + " where t1.location_id = t2.location_id " 
									 + " and t1.create_ts > DATE_SUB(NOW(), INTERVAL 360 DAY) " 
									 + " group by t1.location_id ) t4 "
									 + " where t3.location_id = t4.location_id " 
									 + " order by cnt desc "
									 + " limit 5 ");
			
			while(dbResultSet.next()){ 
				
				Location location = new Location();
				
				location.location_id = dbResultSet.getInt("location_id");
				location.country_id = dbResultSet.getInt("country_id");
				location.city_id = dbResultSet.getInt("city_id");
				location.district_name = dbResultSet.getString("district_name");
				location.location_name = dbResultSet.getString("location_name");
				location.location_brand_name = dbResultSet.getString("location_brand_name");
				location.location_type = dbResultSet.getString("location_type");	
				location.location_sub_type = "P"; //popular location flag
				location.longitude = dbResultSet.getDouble("longitude");
				location.latitude = dbResultSet.getDouble("latitude");
				location.radius = dbResultSet.getDouble("radius");
				location.status_id = dbResultSet.getString("status_id");
				location.location_tags = dbResultSet.getString("location_tags");	
				location.start_ts = dbResultSet.getTimestamp("start_ts");
				location.end_ts = dbResultSet.getTimestamp("end_ts");
				location.create_ts = dbResultSet.getTimestamp("create_ts");
				location.update_ts = dbResultSet.getTimestamp("update_ts"); 
				
				locations.add(location);
			}
			
			dbResultSet = dbStatement.executeQuery(" Select distinct t2.* from " 
					 + " tp_user_location t1, tp_location t2 "
					 + " where t1.location_id = t2.location_id " 
					 + " and t1.create_ts > DATE_SUB(NOW(), INTERVAL 360 DAY) "
					 + " and t1.user_id = " + user_id 
					 + " order by t1.create_ts desc "
					 + " limit 5");

			while(dbResultSet.next()){ 
			
			Location location = new Location();
			
			location.location_id = dbResultSet.getInt("location_id");
			location.country_id = dbResultSet.getInt("country_id");
			location.city_id = dbResultSet.getInt("city_id");
			location.district_name = dbResultSet.getString("district_name");
			location.location_name = dbResultSet.getString("location_name");
			location.location_brand_name = dbResultSet.getString("location_brand_name");
			location.location_type = dbResultSet.getString("location_type");	
			location.location_sub_type = "A"; //active location flag
			location.longitude = dbResultSet.getDouble("longitude");
			location.latitude = dbResultSet.getDouble("latitude");
			location.radius = dbResultSet.getDouble("radius");
			location.status_id = dbResultSet.getString("status_id");
			location.location_tags = dbResultSet.getString("location_tags");	
			location.start_ts = dbResultSet.getTimestamp("start_ts");
			location.end_ts = dbResultSet.getTimestamp("end_ts");
			location.create_ts = dbResultSet.getTimestamp("create_ts");
			location.update_ts = dbResultSet.getTimestamp("update_ts"); 
			
			locations.add(location);

			}
			
			if(locations.size() > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getNearestAndPopularLocations"
						, Double.toString(latitude) + Double.toString(longitude)
						, "Success for location" );
				result.object = locations;				
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getNearestAndPopularLocations"
						, Double.toString(latitude) + Double.toString(longitude)
						, "Failure for location");	
				
				LogManager logger =  new LogManager();	
				logger.createServerLog(dbStatement, "I" , user_id, "getNearestAndPopularLocations", result.message);
				
				result.object = locations;			
			}						
			
		} catch (SQLException e) {			
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getNearestAndPopularLocations"
					, Double.toString(latitude) + Double.toString(longitude)
					, e.getMessage());
			result.object = " ";
			
			LogManager logger =  new LogManager();	
			logger.createServerError(dbStatement, "E" , "1", user_id, "getNearestAndPopularLocations", result.message);	
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
	public OperationResult getActiveAndPopularLocations(int user_id){
		
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();	
			
			ArrayList<Location> locations = new ArrayList<Location>();			
			
			dbResultSet = dbStatement.executeQuery("select distinct t3.* from tp_location t3, "
									 + " ( select t2.location_id, count(*) cnt from " 
									 + " tp_user_location t1, tp_location t2 "
									 + " where t1.location_id = t2.location_id " 
									 + " and t1.create_ts > DATE_SUB(NOW(), INTERVAL 360 DAY) " 
									 + " group by t1.location_id ) t4 "
									 + " where t3.location_id = t4.location_id " 
									 + " order by cnt desc "
									 + " limit 5 ");
			
			while(dbResultSet.next()){ 
				
				Location location = new Location();
				
				location.location_id = dbResultSet.getInt("location_id");
				location.country_id = dbResultSet.getInt("country_id");
				location.city_id = dbResultSet.getInt("city_id");
				location.district_name = dbResultSet.getString("district_name");
				location.location_name = dbResultSet.getString("location_name");
				location.location_brand_name = dbResultSet.getString("location_brand_name");
				location.location_type = dbResultSet.getString("location_type");	
				location.location_sub_type = "P"; //popular location flag
				location.longitude = dbResultSet.getDouble("longitude");
				location.latitude = dbResultSet.getDouble("latitude");
				location.radius = dbResultSet.getDouble("radius");
				location.status_id = dbResultSet.getString("status_id");
				location.location_tags = dbResultSet.getString("location_tags");	
				location.start_ts = dbResultSet.getTimestamp("start_ts");
				location.end_ts = dbResultSet.getTimestamp("end_ts");
				location.create_ts = dbResultSet.getTimestamp("create_ts");
				location.update_ts = dbResultSet.getTimestamp("update_ts"); 
				
				locations.add(location);
			}
			
			dbResultSet = dbStatement.executeQuery(" Select distinct t2.* from " 
					 + " tp_user_location t1, tp_location t2 "
					 + " where t1.location_id = t2.location_id " 
					 + " and t1.create_ts > DATE_SUB(NOW(), INTERVAL 360 DAY) "
					 + " and t1.user_id = " + user_id 
					 + " order by t1.create_ts desc "
					 + " limit 5");

			while(dbResultSet.next()){ 
			
			Location location = new Location();
			
			location.location_id = dbResultSet.getInt("location_id");
			location.country_id = dbResultSet.getInt("country_id");
			location.city_id = dbResultSet.getInt("city_id");
			location.district_name = dbResultSet.getString("district_name");
			location.location_name = dbResultSet.getString("location_name");
			location.location_brand_name = dbResultSet.getString("location_brand_name");
			location.location_type = dbResultSet.getString("location_type");	
			location.location_sub_type = "A"; //active location flag
			location.longitude = dbResultSet.getDouble("longitude");
			location.latitude = dbResultSet.getDouble("latitude");
			location.radius = dbResultSet.getDouble("radius");
			location.status_id = dbResultSet.getString("status_id");
			location.location_tags = dbResultSet.getString("location_tags");	
			location.start_ts = dbResultSet.getTimestamp("start_ts");
			location.end_ts = dbResultSet.getTimestamp("end_ts");
			location.create_ts = dbResultSet.getTimestamp("create_ts");
			location.update_ts = dbResultSet.getTimestamp("update_ts"); 
			
			locations.add(location);

			}
			
			if(locations.size() > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getActiveAndPopularLocations"
						, Integer.toString(user_id) 
						, "Success for location" );
				result.object = locations;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getActiveAndPopularLocations"
						, Integer.toString(user_id) 
						, "Failure for location");	
				result.object = locations;
			}						
			
		} catch (SQLException e) {
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getActiveAndPopularLocations"
					, Integer.toString(user_id) 
					, e.getMessage());
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
	
	//added by ue 08.08.2016
	public OperationResult getActiveLocations(int user_id){
		
		OperationResult result = new OperationResult();
		try {			
			dbStatement = (Statement) dbConnection.createStatement();	
			
			ArrayList<Location> locations = new ArrayList<Location>();			
			
			dbResultSet = dbStatement.executeQuery(" Select distinct t2.* from " 
					 + " tp_user_location t1, tp_location t2 "
					 + " where t1.location_id = t2.location_id " 
					 + " and t1.create_ts > DATE_SUB(NOW(), INTERVAL 120 DAY) "
					 + " and t1.user_id = " + user_id 
					 + " order by t1.create_ts desc "
					 + " limit 5");

			while(dbResultSet.next()){ 
				
				Location location = new Location();
				
				location.location_id = dbResultSet.getInt("location_id");
				location.country_id = dbResultSet.getInt("country_id");
				location.city_id = dbResultSet.getInt("city_id");
				location.district_name = dbResultSet.getString("district_name");
				location.location_name = dbResultSet.getString("location_name");
				location.location_brand_name = dbResultSet.getString("location_brand_name");
				location.location_type = dbResultSet.getString("location_type");	
				location.location_sub_type = "A"; //active location flag
				location.longitude = dbResultSet.getDouble("longitude");
				location.latitude = dbResultSet.getDouble("latitude");
				location.radius = dbResultSet.getDouble("radius");
				location.status_id = dbResultSet.getString("status_id");
				location.location_tags = dbResultSet.getString("location_tags");	
				location.start_ts = dbResultSet.getTimestamp("start_ts");
				location.end_ts = dbResultSet.getTimestamp("end_ts");
				location.create_ts = dbResultSet.getTimestamp("create_ts");
				location.update_ts = dbResultSet.getTimestamp("update_ts"); 
				
				locations.add(location);
			}
			
			if(locations.size() > 0)
			{
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("getActiveLocations"
						, Integer.toString(user_id) 
						, "Success for getting active locations" );
				result.object = locations;
			}
			else
			{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("getActiveLocations"
						, Integer.toString(user_id) 
						, "Failure for getting active locations");	
				result.object = locations;
			}						
			
		} catch (SQLException e) {
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getActiveLocations"
					, Integer.toString(user_id) 
					, e.getMessage());
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
