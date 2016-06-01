package test;

import java.util.ArrayList;

import helper.OperationResult;
import managers.UserManager;
import models.User;
public class Test {

	public static void main(String[] args) {
		UserManager usman = new UserManager();
		OperationResult result = usman.getUsers();
		System.out.println(result.isSuccess);
		if(result.isSuccess){
			ArrayList<User> users = (ArrayList<User>)result.object;
			for(int i = 0 ; i < users.size(); i++){
				System.out.println(users.get(i).username);
			}
		}
	}

}
