package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.UserManager;
import models.*;

//added by ue 01.06.2016
public class S3Sample1 implements RequestHandler<User, UserOutDTO> {

    @Override
    public UserOutDTO handleRequest(User input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new UserManager().insertUser(input);
        
        User user = new User();    
        if(result.isSuccess == true)
        {
        	user.user_id = (int)result.object;        
        }
        else
        {        	
        	user.user_id = -1;
        }        
                                 
        return new UserOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message,
            					  user);         
    }
}
