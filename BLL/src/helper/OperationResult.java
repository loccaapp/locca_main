package helper;

public class OperationResult {
	
	public boolean isSuccess;
	public int returnCode;
	public int reasonCode;
	public String message;
	public Object object;
	
	public OperationResult()
	{
		
	}

	public OperationResult(boolean isSuccess, int returnCode, int reasonCode, String message, Object object) {
		super();
		this.isSuccess = isSuccess;
		this.returnCode = returnCode;
		this.reasonCode = reasonCode;
		this.message = message;
		this.object = object;
	}
	
	public void setMessage(String functionCode, String id, String errorMessage) {
		this.message = "FunctionCode:" + functionCode + " Id:" + id + " Message:" + errorMessage;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	 
}
