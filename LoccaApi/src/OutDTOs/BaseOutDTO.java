package OutDTOs;

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
	
}
