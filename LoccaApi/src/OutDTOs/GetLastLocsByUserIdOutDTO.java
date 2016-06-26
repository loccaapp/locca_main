package OutDTOs;

import java.util.ArrayList;

import Items.PostItem;
import helper.OperationResult;

public class GetLastLocsByUserIdOutDTO extends BaseOutDTO{

	public ArrayList<PostItem> userLocs;
	
	public GetLastLocsByUserIdOutDTO(){
		
	}
	
	public GetLastLocsByUserIdOutDTO(OperationResult os){
		super(os);
	}
}
