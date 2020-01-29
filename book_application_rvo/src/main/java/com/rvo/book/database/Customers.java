package com.rvo.book.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "customers" )
public class Customers implements IAsHashmap{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "customer_id" )
	private Integer customerId;
	
	@Column( name = "name" )
	private String name;
	
	@Column( name = "password" )
	private String password;
	
	@Column( name = "phone_number" )
	private String phoneNumber;

	@Column( name = "email" )
	private String email;
	
	@Column( name = "firebase_token" )
	private String firebaseToken;
	
	public Integer getCustomerId(){
		return customerId;
	}
	
	
	public String getName(){
		return name;
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public void setPhoneNumber( String phoneNumber ){
		this.phoneNumber = phoneNumber;
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
	
	public String getFirebaseToken(){
		return firebaseToken;
	}
	
	public void setFirebaseToken( String firebaseToken ){
		this.firebaseToken = firebaseToken;
	}
	
	@Override
	public String toString(){
		return " [" + this.customerId + "] " + this.name + " #" + this.phoneNumber + "# " + " email: " + this.email;
	}
}
