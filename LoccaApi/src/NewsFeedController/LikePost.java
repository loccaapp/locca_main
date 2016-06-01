package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.LikePostInDTO;
import OutDTOs.LikePostOutDTO;
import helper.OperationResult;
import managers.LikeManager;

public class LikePost implements RequestHandler<LikePostInDTO, LikePostOutDTO> {

    @Override
    public LikePostOutDTO handleRequest(LikePostInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
		
        OperationResult result = new LikeManager().transLikePost(input.user_id, input.post_id);
        LikePostOutDTO dto = new LikePostOutDTO();
        dto.isSuccess = result.isSuccess;
        dto.message = result.message;
        
        return dto;               
    }
}
