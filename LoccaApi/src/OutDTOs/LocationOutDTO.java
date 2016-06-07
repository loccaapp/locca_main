package OutDTOs;

import java.util.ArrayList;
import models.Location;

public class LocationOutDTO extends BaseOutDTO{
	
	public ArrayList<Location> locations;

	public LocationOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LocationOutDTO(boolean isSuccess, int returnCode, int reasonCode, String message, ArrayList<Location> locations) {
		super(isSuccess, returnCode, reasonCode, message);
		// TODO Auto-generated constructor stub
		setLocations(locations);		
	}
	
	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}
	
}
