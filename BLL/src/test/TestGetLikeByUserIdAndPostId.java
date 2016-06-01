package test;

import helper.OperationResult;
import managers.LikeManager;

public class TestGetLikeByUserIdAndPostId {
	
	public static void main(String[] args) {

		OperationResult result = new LikeManager().getLikeByUserIdAndPostId(1000000, 1);
		System.out.println(result.message);
	}
}
