package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.*;
import models.*;

public class GetConnectedUsers implements RequestHandler<UserConnectionInDTO, UserConnectionOutDTO>  {

    @Override 
    public UserConnectionOutDTO handleRequest(UserConnectionInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        UserConnection userCon = new UserConnection();
        
        userCon.from_user_id = input.from_user_id;
        userCon.to_user_id = input.to_user_id;
        
        OperationResult result = new MessageManager().getConnectedUsers(userCon);	
        
        ArrayList<UserConnection> userConList;
        
        if(result.isSuccess==true) 
        {
        	userConList = (ArrayList<UserConnection>)result.object;        
        }
        else
        {        	
        	userConList = null;
        } 
        
        return new UserConnectionOutDTO(result.isSuccess,
	        						  result.returnCode,
	            					  result.reasonCode, 
	            					  " " , // result.message,
	            					  userConList);
    }
	
}
