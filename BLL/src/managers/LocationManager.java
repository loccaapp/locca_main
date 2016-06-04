package managers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import helper.OperationResult;
import models.*;

public class LocationManager  extends BaseManager {

	public LocationManager(){
		super();
	}
	
	//added by ue 01.06.2016
	public OperationResult getLocations(String search_text){
		
		try {			
			dbStatement = (Statement) dbConnection.createStatement();
			
			dbResultSet = dbStatement.executeQuery("select * from tp_location where location_name like '%"+search_text+"%' ");					
			
			
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
				
				locations.add(location);
			}
			
			OperationResult result = new OperationResult();
			result.isSuccess = true;
			result.message = "Success";
			result.object = locations;
			return result;
			
		} catch (SQLException e) {
			OperationResult result = new OperationResult();
			result.isSuccess = false;
			result.message = "Search text: " + search_text + " Error2 : " 
					+ e.getStackTrace() + "=="
					+ e.getMessage()    + "--" 
					+ e.getErrorCode()  + "**" 
					+ e.getSQLState()   + "++" 
					+ e.getStackTrace();
			return result;
		}		
	}
	
}
