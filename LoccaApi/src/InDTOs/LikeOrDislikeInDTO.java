package InDTOs;

import models.Like;

public class LikeOrDislikeInDTO extends BaseInDTO {
	
	public long post_id;
	public int effecter_user_id;
	public int user_id;
	public String like_dislike_ind;

}
