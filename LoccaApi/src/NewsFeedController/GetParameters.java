package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.*;
import models.*;

public class GetParameters implements RequestHandler<ParameterInDTO, ParameterOutDTO>  {

    @Override 
    public ParameterOutDTO handleRequest(ParameterInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
                
        Parameter prm = new Parameter();
        prm.prm_group_text = input.prm_group_text;        
        
        OperationResult result = new ParameterManager().getParameter(prm,input.process_type);
        
        ArrayList<Parameter> parameters;
        
        if(result.isSuccess==true) 
        {
        	parameters = (ArrayList<Parameter>)result.object;        
        }
        else
        {        	
        	parameters = null;
        } 
        
        return new ParameterOutDTO(result.isSuccess,
		    							  result.returnCode,
		            					  result.reasonCode, 
		            					  result.message,
		            					  parameters);
    }	
}
