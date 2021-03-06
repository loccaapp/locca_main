package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import helper.OperationResult;
import OutDTOs.*;
import managers.*;
import models.*;

//added by ue 01.06.2016
public class UpdateUserEmailAndPhone implements RequestHandler<User, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(User input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new UserManager().updateUserEmailAndPhone(input);
        
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
