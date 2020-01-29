package rvo.lipe.database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User{
	@Id
	private long facebookId;
	private String gender;
	private String accessToken;
	
	public long getFacebookId() {
		return facebookId;
	}

	public void setFacebookId( long facebookId ) {
		this.facebookId = facebookId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender( String gender ) {
		this.gender = gender;
	}
	
	public void setAccessToken( String accessToken ){
		this.accessToken = accessToken;
	}
	
	public String getAccessToken(){
		return accessToken;
	}
}
