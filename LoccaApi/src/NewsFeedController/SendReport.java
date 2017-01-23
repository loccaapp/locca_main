package NewsFeedController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.*;
import utils.Timef;
import InDTOs.*;
import OutDTOs.*;
import helper.OperationResult;
import managers.*;

public class SendReport implements RequestHandler<ReportInDTO, BaseOutDTO> {

    @Override
    public BaseOutDTO handleRequest(ReportInDTO input, Context context) {
        context.getLogger().log("Input: " + input); 		
		
        Report report = new Report();
        report.post_id = input.post_id;
        report.user_id = input.user_id;
        report.report_type = input.report_type;
        report.report_sub_type = input.report_sub_type;
        report.report_message = input.report_message;
        report.status_id = input.status_id;
        
		OperationResult result = new ReportManager().sendReport(report);
        
        int retVal = 0;        
        if(result.isSuccess == true)
        {
        	retVal = (int)result.object;        
        }
                                 
        return new BaseOutDTO(result.isSuccess,
        						  result.returnCode,
            					  result.reasonCode, 
            					  result.message );    
        
    }
}
