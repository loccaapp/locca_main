package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.LocationInDTO;
import Items.UserItem;
import OutDTOs.*;
import helper.OperationResult;
import managers.LocationManager;
import models.*;

public class GetLocations implements RequestHandler<LocationInDTO, GetLocationOutDTO>  {

    @Override
    public GetLocationOutDTO handleRequest(LocationInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new LocationManager().getLocations(input.search_text);
        ArrayList<Location> locations = (ArrayList<Location>)result.object;        
        GetLocationOutDTO resp_dto = new GetLocationOutDTO();
    	
        if(!result.isSuccess){
        	resp_dto.isSuccess = result.isSuccess;
        	resp_dto.message = result.message;
        	return resp_dto;
        }
        if(locations.size() == 0 || locations == null){
        	resp_dto.isSuccess = true;
        	resp_dto.message = "There is no location for that keyword";
        	return resp_dto;
        }
        else
        {        
	        resp_dto.isSuccess = result.isSuccess;
	        resp_dto.message = result.message;
	        resp_dto.locations = locations;        
	        return resp_dto;
        }
    }
	
}
