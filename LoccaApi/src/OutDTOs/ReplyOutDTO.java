package OutDTOs;

import java.util.ArrayList;
import models.*;

public class ReplyOutDTO extends BaseOutDTO {

	public ArrayList<Reply> replies;
	
	public ReplyOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReplyOutDTO(boolean isSuccess, int returnCode, int reasonCode, String message, 
					   ArrayList<Reply> replies) {
		super(isSuccess, returnCode, reasonCode, message);
		// TODO Auto-generated constructor stub
		setReplies(replies);		
	}
	
	public ArrayList<Reply> getReplies() {
		return replies;
	}

	public void setReplies(ArrayList<Reply> replies) {
		this.replies = replies;
	}
}
