package managers;

import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class LogManager {
	
	public String createServerLog(Statement st, 
			String log_type, 
			int user_id, 
			String screen_code, 
			String log_message)
	{ 
		int effectedRows = 0;
		String return_msg = " ";
		log_message = log_message.replace("'", " ");
		
		String sql = "INSERT INTO tp_log "
		+ " (log_type, user_id, screen_code, log_message, create_ts ) "
		+ " VALUES "
		+ " ('" +log_type+ "',"+user_id+",'"+screen_code+"','"+log_message+"',now())";
		
		try {
			effectedRows = st.executeUpdate(sql);			
			return_msg = "True! InsertedLogCnt : " + String.valueOf(effectedRows);
		} catch (SQLException e) {
			return_msg = sql + " ** " + e.getMessage();
		}
		
		return return_msg;
	}	
	
}
