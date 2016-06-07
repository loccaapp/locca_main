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
public class GetUser implements RequestHandler<BaseInDTO, UserOutDTO> {

    @Override
    public UserOutDTO handleRequest(BaseInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new UserManager().getUser(input.user_id);
        
        User user;        
        if(result.isSuccess == true)
        {
        	user = (User)result.object;        
        }
        else
        {        	
        	user = null;
        }        
                                 
        return new UserOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message,
            					  user);          
    }
}
