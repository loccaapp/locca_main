package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.LocationManager;
import models.*;

public class GetNearestAndPopularLocations implements RequestHandler<GetNearestAndPopularLocationsInDTO, LocationOutDTO>  {

    @Override
    public LocationOutDTO handleRequest(GetNearestAndPopularLocationsInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = 
        		new LocationManager().getNearestAndPopularLocations(input.user_id
        														   ,input.latitude
        														   ,input.longitude);
        
        ArrayList<Location> locations;
        
        if(result.isSuccess==true)
        {
        	locations = (ArrayList<Location>)result.object;        
        }
        else
        {        	
            locations = null;
        }     
        
        return new LocationOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message,
            					  locations);
    }
	
}
