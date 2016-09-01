package models;

import java.sql.*;

//added by ue 02.06.2016
public class UserLocation {

	public int user_id ;
	public int location_id ;
	public String location_name ;
	public String district_name ;
	public double longitude	;
	public double latitude ;
	public Timestamp create_ts ;
	
	public UserLocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserLocation(int user_id, int location_id, double longitude, double latitude, Timestamp create_ts) {
		super();
		this.user_id = user_id;
		this.location_id = location_id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.create_ts = create_ts;
	}
		
}
