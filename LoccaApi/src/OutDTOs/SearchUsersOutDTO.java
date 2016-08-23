package OutDTOs;

import java.util.ArrayList;

//import Items.UserItem;
import models.*;

public class SearchUsersOutDTO extends BaseOutDTO{
	
	public ArrayList<User> users;

	public SearchUsersOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchUsersOutDTO(boolean isSuccess, int returnCode, int reasonCode, String message,
			ArrayList<User> users) {
		super(isSuccess, returnCode, reasonCode, message);
		// TODO Auto-generated constructor stub
		setUsers(users);	
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
}
