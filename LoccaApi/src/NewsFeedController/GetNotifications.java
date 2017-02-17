package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.*;
import models.*;

public class GetNotifications implements RequestHandler<UserPostInDTO, UserNotificationOutDTO>  {

    @Override 
    public UserNotificationOutDTO handleRequest(UserPostInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
                
        Notification not = new Notification();
        not.user_id = input.user_id;            
        
        OperationResult result = new NotificationManager().getNotifications(not,
															        		input.page_number,
															        		15);
        
        ArrayList<Notification> notification;
        
        if(result.isSuccess==true) 
        {
        	notification = (ArrayList<Notification>)result.object;        
        }
        else
        {        	
        	notification = null;
        } 
        
        return new UserNotificationOutDTO(result.isSuccess,
		    							  result.returnCode,
		            					  result.reasonCode, 
		            					  result.message,
		            					  notification);
    }	
}
