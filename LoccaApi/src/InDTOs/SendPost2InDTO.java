package InDTOs;

import java.sql.Timestamp;

import models.Location;
import models.User;

public class SendPost2InDTO extends BaseInDTO {
	
	public long post_id;
	public int user_id;
	public int location_id;
	public char post_type;
	public String post_text;
	public String post_image_id;
	public String post_video_id;
	public String status_id;
	public double longitude;
	public double latitude;
	
}
