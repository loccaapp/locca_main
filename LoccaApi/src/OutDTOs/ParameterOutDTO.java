package OutDTOs;

import java.util.ArrayList;
import models.*;

public class ParameterOutDTO extends BaseOutDTO {

	public ArrayList<Parameter> parameters;
	
	public ParameterOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParameterOutDTO(boolean isSuccess, int returnCode, int reasonCode, String message, 
					   ArrayList<Parameter> parameters) {
		super(isSuccess, returnCode, reasonCode, message);
		// TODO Auto-generated constructor stub
		setParameters(parameters);		
	}
	
	public ArrayList<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<Parameter> parameters) {
		this.parameters = parameters;
	}
}
