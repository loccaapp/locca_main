package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.UserManager;
import models.User;

public class LoginUser implements RequestHandler<BaseInDTO, UserOutDTO> {

    @Override
    public UserOutDTO handleRequest(BaseInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new UserManager().loginUser(input.username,
        													 input.user_pwd,
        													 input.email_address);        													                                            
        
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
