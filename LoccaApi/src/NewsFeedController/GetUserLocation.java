package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.LocationManager;
import models.*;

public class GetUserLocation implements RequestHandler<UserInDTO, UserLocationOutDTO>  {

    @Override
    public UserLocationOutDTO handleRequest(UserInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new LocationManager().GetUserLocation(input.user_id);
        
        ArrayList<UserLocation> userLocation;
        
        if(result.isSuccess==true) 
        {
        	userLocation = (ArrayList<UserLocation>)result.object;        
        }
        else
        {        	
        	userLocation = null;
        }     
        
        return new UserLocationOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message,
            					  userLocation);
    }
	
}
