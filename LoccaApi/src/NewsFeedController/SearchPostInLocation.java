package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.LocationInDTO;
import InDTOs.SearchPostInDTO;
import OutDTOs.*;
import helper.OperationResult;
import managers.LocationManager;
import managers.PostManager;
import models.*;

public class SearchPostInLocation implements RequestHandler<SearchPostInDTO, 
															PostOutDTO>  
{

    @Override
    public PostOutDTO handleRequest(SearchPostInDTO input, Context context) {
        context.getLogger().log("Input: " + input);        
        
        OperationResult result = 
        		new PostManager().searchPostInLocation(input.location_id, 
								        				input.search_text, 
								        				input.page_number, 
								        				input.paging_count);
                		
        ArrayList<Post> posts;
        
        if(result.isSuccess==true)
        {
        	posts = (ArrayList<Post>)result.object;        
        }
        else
        {        	
        	posts = null;
        }     
        
        return new PostOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message,
            					  posts);
    }
	
}
