package OutDTOs;

import java.util.ArrayList;

import models.*;

public class UserNotificationOutDTO extends BaseOutDTO{
	
	public ArrayList<Notification> notifications;

	public UserNotificationOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserNotificationOutDTO(boolean isSuccess, 
								int returnCode, int reasonCode, String message, 
								ArrayList<Notification> not) {
		super(isSuccess, returnCode, reasonCode, message);
		// TODO Auto-generated constructor stub
		
		setNotification(not);
	}

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}

	public void setNotification(ArrayList<Notification> notifications) {
		this.notifications = notifications;
	}
}
