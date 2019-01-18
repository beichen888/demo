package weixin.popular.bean;

import java.io.Serializable;
import java.util.Calendar;

public class Token extends BaseResult implements Serializable {

	private String access_token;
	private int expires_in;
	private long createdDate;

	public Token(){
		this.createdDate = Calendar.getInstance().getTimeInMillis()/1000;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expiresIn) {
		expires_in = expiresIn;
	}

	public boolean isValid(){
		long now = Calendar.getInstance().getTimeInMillis()/1000;
		return access_token!=null && (now-createdDate) < expires_in;
	}

}
