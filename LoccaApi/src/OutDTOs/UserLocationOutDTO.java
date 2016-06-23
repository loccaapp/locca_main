package OutDTOs;

import java.util.ArrayList;

import models.*;

public class UserLocationOutDTO extends BaseOutDTO{
	
	public ArrayList<UserLocation> userLocation;

	public UserLocationOutDTO(boolean isSuccess, int returnCode, int reasonCode, String message, ArrayList<UserLocation> userLocation) {
		super(isSuccess, returnCode, reasonCode, message);
		// TODO Auto-generated constructor stub
		
		setUserLocation(userLocation);
	}
	
	public ArrayList<UserLocation> getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(ArrayList<UserLocation> userLocation) {
		this.userLocation = userLocation;
	}

	public UserLocationOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}	
}
