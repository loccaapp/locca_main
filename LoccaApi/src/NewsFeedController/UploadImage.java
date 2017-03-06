package NewsFeedController;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.UserManager;
import models.*;
//import S3Manager.*;

//added by ue 01.06.2016
public class UploadImage implements RequestHandler<InPhotoDTO, OutPhotoDTO> {

    @Override
    public OutPhotoDTO handleRequest(InPhotoDTO input, Context context) {
        context.getLogger().log("Input: " + input);
        /*
        //OperationResult result = new UserManager().insertUser(input);
        
        S3Manager.Uploader loader = new Uploader();        
        
        String resultVal = loader.uploadPicture(input.photoArray);   
        
        OutPhotoDTO outpic =  new OutPhotoDTO();
        
        outpic.photoArray = resultVal;
        
        return outpic;
        
        /*
        User user = new User();    
        if(result.isSuccess == true)
        {
        	user.user_id = (int)result.object;        
        }
        else
        {        	
        	user.user_id = -1;
        }        
                                 
        return new UserOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message,
            					  user);   
        */
    	return new OutPhotoDTO();
    }
	
}
