package NewsFeedController;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.LocationManager;
import models.*;

public class CheckLocation implements RequestHandler<CheckLocationInDTO, LocationOutDTO>  {

    @Override
    public LocationOutDTO handleRequest(CheckLocationInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
                
        OperationResult result = new LocationManager().CheckLocation(input.latitude
        															,input.longitude
        															,input.radius);
        
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
