package OutDTOs;

import java.util.ArrayList;

import Items.PostItem;
import helper.OperationResult;

public class GetBestLocsByUserIdOutDTO extends BaseOutDTO{

	public ArrayList<PostItem> userLocs;

	public GetBestLocsByUserIdOutDTO(OperationResult os) {
		super(os);
	}
	
	
}
