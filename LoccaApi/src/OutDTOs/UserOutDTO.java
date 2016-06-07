package OutDTOs;

import models.User;

//added by ue 07.06.2016
public class UserOutDTO extends BaseOutDTO{	
	
	public User user;

	public UserOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserOutDTO(boolean isSuccess, int returnCode, int reasonCode, String message, User user) {
		super(isSuccess, returnCode, reasonCode, message);
		// TODO Auto-generated constructor stub
		setUser(user);		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
