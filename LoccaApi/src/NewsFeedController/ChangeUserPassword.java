package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import helper.OperationResult;
import OutDTOs.*;
import InDTOs.*;
import managers.*;
import models.*;

//added by ue 01.06.2016
public class ChangeUserPassword implements RequestHandler<ChangePwdInDTO, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(ChangePwdInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new UserManager().changeUserPassword(input.user_id,
        															  input.existing_user_pwd,
        															  input.new_user_pwd);
        
        int retVal = 0;        
        if(result.isSuccess == true)
        {
        	retVal = (int)result.object;        
        }
        else
        {        	
        	retVal = 0;
        }        
                                 
        return new BaseOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message );          
    }
}
