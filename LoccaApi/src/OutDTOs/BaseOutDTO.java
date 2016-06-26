package OutDTOs;

import helper.OperationResult;

public class BaseOutDTO {
	
	public boolean isSuccess;
	public int returnCode;
	public int reasonCode;
	public String message;
	
	public BaseOutDTO()
	{
		
	}
	
	public BaseOutDTO(boolean isSuccess, int returnCode, int reasonCode, String message) {
		super();
		this.isSuccess = isSuccess;
		this.returnCode = returnCode;
		this.reasonCode = reasonCode;
		this.message = message;
	}
	
	public BaseOutDTO(OperationResult os){
		isSuccess = os.isSuccess;
		reasonCode = os.reasonCode;
		returnCode = os.returnCode;
		message = os.message;
	}
	
}
