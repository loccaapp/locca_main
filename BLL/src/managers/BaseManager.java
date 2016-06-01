package managers;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class BaseManager {
	protected Connection dbConnection;
	protected Statement dbStatement;
	protected ResultSet dbResultSet;
	
	public BaseManager(){
		if(this.dbConnection == null){
			try {
				this.dbConnection = (Connection) DriverManager.getConnection(
						"jdbc:mysql://awstest.ch9co98ltjp7.us-west-2.rds.amazonaws.com/awsTest", "root", "testroot");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
