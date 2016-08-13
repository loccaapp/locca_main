package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.CheckLocationInDTO;
import OutDTOs.*;
import helper.OperationResult;
import managers.LocationManager;
import models.*;

public class GetNearestAndPopularLocations implements RequestHandler<CheckLocationInDTO, LocationOutDTO>  {

    @Override
    public LocationOutDTO handleRequest(CheckLocationInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
                
        OperationResult result = new LocationManager().getNearestAndPopularLocations(input.latitude
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
