package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import InDTOs.DislikePostInDTO;
import OutDTOs.DislikePostOutDTO;
import helper.OperationResult;
import managers.LikeManager;

public class DislikePost implements RequestHandler<DislikePostInDTO, DislikePostOutDTO> {

    @Override
    public DislikePostOutDTO handleRequest(DislikePostInDTO input, Context context) {
        context.getLogger().log("Input: " + input);
		
        OperationResult result = new LikeManager().transDislikePost(input.user_id, input.post_id);
        DislikePostOutDTO dto = new DislikePostOutDTO();
        dto.isSuccess = result.isSuccess;
        dto.message = result.message;
        
        return dto;               
    }

}
