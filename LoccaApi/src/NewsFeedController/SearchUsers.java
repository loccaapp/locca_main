
package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.LocationManager;
import managers.UserManager;
import models.*;

public class SearchUsers implements RequestHandler<SearchInDTO, SearchUsersOutDTO>  {

    @Override
    public SearchUsersOutDTO handleRequest(SearchInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new UserManager().searchUsers(input.searchText);
        
        ArrayList<User> users;
        
        if(result.isSuccess==true)
        {
        	users = (ArrayList<User>)result.object;        
        }
        else
        {        	
        	users = null;
        }     
        
        return new SearchUsersOutDTO(result.isSuccess,
	        						  result.returnCode,
	            					  result.reasonCode, 
	            					  result.message,
	            					  users);
    }
	
}
