package models;

import java.sql.*;

public class Location {
	
	public int location_id;
	public int country_id;
	public int city_id;
	public String district_name;
	public String location_name;
	public String location_brand_name;
	public String location_type;
	public String location_sub_type;
	public double longitude;
	public double latitude;
	public double radius;
	public String status_id;
	public String location_tags;
	public Timestamp start_ts;
	public Timestamp end_ts;
	public Timestamp create_ts;
	public Timestamp update_ts;
	
	public double distance;
	
}
