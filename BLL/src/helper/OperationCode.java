package helper;

public class OperationCode {
	
	public enum ReturnCode {
	    Info,
		Warning,
	    Error,
	    Severe;
	}
	
	public static class ReasonCode {
	    
		public static int Info_default = 1000;
		
		public static int Warning_default = 2000;
		public static int Warning_NotFound = 2001;
		public static int Warning_Sql = 2002;
		public static int Warning_CaseControl = 2003;
		
		public static int Error_default = 3000;
		public static int Error_Sql = 3001;
		public static int Error_Login = 3002;
		public static int Error_LocationGet = 3003;
		public static int Error_Post = 3004;
		
		public static int Severe_default = 4000;
	}
	
}
