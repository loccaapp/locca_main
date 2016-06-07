package managers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.lang.*;

import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.*;

public class LocationManager  extends BaseManager {

	public LocationManager(){
		super();
	}
	
	//added by ue 01.06.2016
	public OperationResult getLocations(String search_text){
		
		OperationResult result = new OperationResult();
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
			
			return result;
			
		} catch (SQLException e) {
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getLocations", search_text , e.getMessage());
			result.object = " ";
			return result;
		}		
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
			
			return result;
			
		} catch (SQLException e) {			
			result.isSuccess = false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
			result.returnCode = OperationCode.ReasonCode.Error_Login;
			result.setMessage("getLocation", Integer.toString(location_id) , e.getMessage());
			result.object = " ";
			return result;
		}		
	}
	
}
