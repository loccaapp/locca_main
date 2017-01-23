package managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import helper.OperationCode;
import helper.OperationResult;
import models.Location;
import models.Log;
import models.Message;
import models.Reply;
import models.Report;
import models.Post;

public class ReportManager extends BaseManager {

	public ReportManager(){
		super();
	}
	
	//added by ue 12.01.2017 send Report
	public OperationResult sendReport(Report report){
		
		OperationResult result = new OperationResult();
		int effectedRows = 0;
		String sql = " ";
		
		try {
			
			dbStatement = (Statement)dbConnection.createStatement();
			
			sql = "INSERT INTO tp_report " 
					+ " (report_id, post_id, user_id, report_type, " 
					+ " report_sub_type, report_message, status_id, " 
					+ " create_ts, update_ts) " 
					+ " VALUES (NULL,?,?,?,?,?,?,now(),now()) " ;		
			
			PreparedStatement pst = dbConnection.prepareStatement(sql);
            pst.setLong(1, report.post_id);
            pst.setInt(2, report.user_id);
            pst.setString(3, "S"); //spam
            pst.setString(4, report.report_sub_type); //not considered yet
            pst.setString(5, report.report_message); //not considered yet
            pst.setString(6, "A"); //spam or not degerlendirmesi sonrasi P yapilir.
            
            effectedRows = pst.executeUpdate();
			
			if(effectedRows > 0){
				result.isSuccess = true;
				result.returnCode = OperationCode.ReturnCode.Info.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Info_default;
				result.setMessage("sendReport", " " , "Report was sent!");
				result.object = effectedRows;
			}			
			else{
				result.isSuccess = false;
				result.returnCode = OperationCode.ReturnCode.Warning.ordinal();
				result.reasonCode = OperationCode.ReasonCode.Warning_NotFound;
				result.setMessage("sendReport", "sql: " + sql , 
						"user_id :" + report.user_id + " Report was not sent! for post_id:" + report.post_id);

				LogManager logger =  new LogManager();	
				logger.createServerLog(dbStatement, "I" , report.user_id, "sendReport", result.message);				
			}
		
		} catch (SQLException e) {
			result.isSuccess= false;
			result.returnCode = OperationCode.ReturnCode.Error.ordinal();
			result.reasonCode = OperationCode.ReasonCode.Error_Sql;
			result.setMessage("sendReport","post_id: " + report.post_id + "sql: " + sql , e.getMessage());
			
			LogManager logger =  new LogManager();	
			logger.createServerError(dbStatement, "E" , "1", report.user_id, "sendReport", result.message);
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