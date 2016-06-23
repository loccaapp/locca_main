package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.LocationManager;
import models.*;

//added by ue 01.06.2016
public class InsertUserLocation implements RequestHandler<UserLocation, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(UserLocation input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new LocationManager().insertUserLocation(input);
        
        int retVal;        
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
