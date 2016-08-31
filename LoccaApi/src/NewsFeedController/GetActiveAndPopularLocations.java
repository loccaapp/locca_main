package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.LocationManager;
import models.*;

public class GetActiveAndPopularLocations implements RequestHandler<BaseInDTO, LocationOutDTO>  {

    @Override
    public LocationOutDTO handleRequest(BaseInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = 
        		new LocationManager().getActiveAndPopularLocations(input.user_id);
        
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
