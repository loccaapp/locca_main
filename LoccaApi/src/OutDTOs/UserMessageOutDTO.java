package OutDTOs;

import java.util.ArrayList;

import models.*;

public class UserMessageOutDTO extends BaseOutDTO{
	
	public ArrayList<UserMessage> userMessage;

	public UserMessageOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserMessageOutDTO(boolean isSuccess, int returnCode, int reasonCode, String message, ArrayList<UserMessage> userMessage) {
		super(isSuccess, returnCode, reasonCode, message);
		// TODO Auto-generated constructor stub
		
		setUserMessage(userMessage);
	}

	public ArrayList<UserMessage> getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(ArrayList<UserMessage> userMessage) {
		this.userMessage = userMessage;
	}

}
