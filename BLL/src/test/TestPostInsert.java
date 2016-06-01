package test;

import helper.OperationResult;
import managers.PostManager;
import models.Post;

public class TestPostInsert {
	public static void main(String[] args) {
		Post post = new Post();
		post.user_id = 1000000;
		post.location_id = 1;
		post.post_type = 'P';
		post.post_text = "Hello From Lambda";
		OperationResult result = new PostManager().insert(post);
		System.out.println(result.message);
	}
}
