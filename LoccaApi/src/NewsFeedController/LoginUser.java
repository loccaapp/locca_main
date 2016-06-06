package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.UserManager;

public class LoginUser implements RequestHandler<BaseInDTO, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(BaseInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new UserManager().loginUser(input.username,
        													 input.user_pwd,
        													 input.email_address);        													                                    
        
        return new BaseOutDTO(result.isSuccess, 
        					  result.returnCode,
        					  result.reasonCode, 
        					  result.message);
    }
}
