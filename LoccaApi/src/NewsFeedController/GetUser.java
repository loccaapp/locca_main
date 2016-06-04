package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.BaseInDTO;
import OutDTOs.GetUserOutDTO;
import OutDTOs.GetUsersOutDTO;
import helper.OperationResult;
import managers.UserManager;
import models.User;

//added by ue 01.06.2016
public class GetUser implements RequestHandler<BaseInDTO, GetUserOutDTO> {

    @Override
    public GetUserOutDTO handleRequest(BaseInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        
        OperationResult result = new UserManager().getUser(input.user_id);
                              
        
        
        GetUserOutDTO dto = new GetUserOutDTO();
        dto.isSuccess = result.isSuccess;
        dto.message = result.message;
        dto.user = (User)result.object;
        
        
        
        return dto;               
    }
}
