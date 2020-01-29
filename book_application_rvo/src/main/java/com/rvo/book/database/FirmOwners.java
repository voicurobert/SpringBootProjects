package com.rvo.book.database;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "firm_owners" )
public class FirmOwners implements IAsHashmap {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "owner_id" )
	private Integer firmOwnerId;
	
	@Column( name = "password" )
	private String password;
	
	@Column( name = "email" )
	private String email;
	
	@Column( name = "firm_name" )
	private String firmName;
	
	@Column( name = "firm_address" )
	private String firmAddress;
	
	@Column( name = "phone_number" )
	private String phoneNumber;
	
	@Column( name = "active" )
	private Integer active;
	
	@Column( name = "schedule_id" )
	private Integer scheduleId;
	
	@Column( name = "firebase_token" )
	private String firebaseToken;
	
	public FirmOwners(){
		
	}
	
	public FirmOwners( String password, String email, String firmName, String firmAddress, String phoneNumber, Integer active ){
		this.password = password;
		this.email = email;
		this.firmName = firmName;
		this.firmAddress = firmAddress;
		this.phoneNumber = phoneNumber;
		this.active = active;
	}
	
	public Integer getFirmOwnerId(){
		return firmOwnerId;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail( String email ){
		this.email = email;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword( String password ){
		this.password = password;
	}
	
	public String getFirmName(){
		return firmName;
	}
	
	public void setFirmName( String firmName ){
		this.firmName = firmName;
	}
	
	public String getFirmAddress(){
		return firmAddress;
	}
	
	public void setFirmAddress( String firmAddress ){
		this.firmAddress = firmAddress;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public void setPhoneNumber( String phoneNumber ){
		this.phoneNumber = phoneNumber;
	}

	public int getActive(){
		return active;
	}
	
	public void setActive( int active ){
		this.active = active;
	}
	
	public void setScheduleId( Integer scheduleId ){
		this.scheduleId = scheduleId;
	}
	
	public Integer getScheduleId(){
		return scheduleId;
	}
	
	public boolean isActive(){
		return active.equals( 1 );
	}
	
	public String getFirebaseToken(){
		return firebaseToken;
	}
	
	public void setFirebaseToken( String firebaseToken ){
		this.firebaseToken = firebaseToken;
	}
	
	
	@Override
	public String toString(){
		return " [" + this.firmOwnerId + "] name: " + " firm name: " + this.firmName;
	}
	
}
