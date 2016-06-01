package utils;

import java.sql.Timestamp;
import java.util.Date;

public class Timef {
	
	public static Timestamp getDateTime(){
		Date date = new Date();
		return new Timestamp(date.getTime());
	}
}
