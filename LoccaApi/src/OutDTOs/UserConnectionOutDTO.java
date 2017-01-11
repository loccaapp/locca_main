package OutDTOs;

import java.util.ArrayList;
import models.*;

public class UserConnectionOutDTO  extends BaseOutDTO{

	public ArrayList<UserConnection> userCons;

	public UserConnectionOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserConnectionOutDTO(boolean isSuccess, int returnCode, int reasonCode, String message, ArrayList<UserConnection> userCons) {
		super(isSuccess, returnCode, reasonCode, message);
		// TODO Auto-generated constructor stub
		setUserCons(userCons);		
	}

	public ArrayList<UserConnection> getUserCons() {
		return userCons;
	}

	public void setUserCons(ArrayList<UserConnection> userCons) {
		this.userCons = userCons;
	}

}
