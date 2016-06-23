package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.LocationInDTO;
import InDTOs.UserLocationInDTO;
import OutDTOs.*;
import helper.OperationResult;
import managers.LocationManager;
import models.*;

public class CheckUserLocation implements RequestHandler<UserLocationInDTO, UserLocationOutDTO>  {

    @Override
    public UserLocationOutDTO handleRequest(UserLocationInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new LocationManager().CheckUserLocation(input.user_id, input.location_id);
        
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
