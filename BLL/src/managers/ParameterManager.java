package managers;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.*;

public class ParameterManager extends BaseManager {

	public ParameterManager(){
		super();
	}
	
	//added by ue 23.06.2016 get Message
	public OperationResult getParameter(Parameter prm, String process_type){
			
			OperationResult result = new OperationResult();
			
			try {		
				
				dbStatement = (Statement) dbConnection.createStatement();
				
				String sql = "select prm_id, prm_group_text, prm_type, prm_value_text, "
						+ " prm_value_num, prm_desc, create_ts, update_ts "
						+ " from tp_parameter "						
						+ " where prm_group_text = '" + prm.prm_group_text.trim() + "' "
						+ " order by prm_desc desc " ;
				
				dbResultSet = dbStatement.executeQuery(sql); 
				
				ArrayList<Parameter> parameters = new ArrayList<Parameter>();
				
				while(dbResultSet.next()){
					
					Parameter parameter = new Parameter();
					
					parameter.prm_id = dbResultSet.getInt("prm_id");
					parameter.prm_group_text = dbResultSet.getString("prm_group_text");
					parameter.prm_type = dbResultSet.getString("prm_type");
					parameter.prm_value_text = dbResultSet.getString("prm_value_text"); 
					parameter.prm_value_num = dbResultSet.getInt("prm_value_num");
					parameter.prm_desc = dbResultSet.getString("prm_desc");
								
					parameters.add(parameter);
				}
				
				if(parameters.size() > 0)
				{
					result.isSuccess = true;
					result.returnCode = OperationCode.ReturnCode.Info.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Info_default;
					result.setMessage("getParameter", prm.prm_group_text, 
									  "Success for from_user_id");
					
					LogManager logger =  new LogManager();	
					logger.createServerLog(dbStatement, "I" , 0 , "getParameter", 
										   result.message );
					
					result.object = parameters;
				}
				else
				{
					result.isSuccess = false;
					result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
					result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
					result.setMessage("getParameter", prm.prm_group_text , 
									  "Not Found for from_user_id");
					
					LogManager logger =  new LogManager();	
					logger.createServerLog(dbStatement, "E" , 0 , "getParameter", 
										   result.message );
					result.object = parameters;
				}							
				
			} catch (SQLException e) {
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Error.ordinal();	
				result.returnCode = OperationCode.ReasonCode.Error_Login;
				result.setMessage("getParameter", prm.prm_group_text , 
								   e.getMessage());
				result.object = " ";
			}		
			
			try {
				dbConnection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return result;
		}	

	
}
