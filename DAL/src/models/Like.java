package models;

import java.sql.Timestamp;

public class Like {
	public int post_id;
	public int effecter_user_id;
	public int user_id;
	public char like_dislike_ind;
	public Timestamp create_ts;
	public Timestamp update_ts;
}
