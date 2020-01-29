package com.rvo.book.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "firm_categories" )
public class FirmCategories implements IAsHashmap{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "category_id" )
	private Integer categoryId;
	
	@Column( name = "category_name" )
	private String categoryName;
	
	@Column( name = "firm_owner_id" )
	private Integer firmOwnerId;
	
	public Integer getCategoryId(){
		return categoryId;
	}
	
	public String getCategoryName(){
		return categoryName;
	}
	
	public void setCategoryName( String categoryName ){
		this.categoryName = categoryName;
	}
	
	public Integer getFirmOwnerId(){
		return firmOwnerId;
	}
	
	public void setFirmOwnerId( Integer firmOwnerId ){
		this.firmOwnerId = firmOwnerId;
	}
	
	
	@Override
	public String toString(){
		DatabaseManager instance = DatabaseManager.getInstance();
		return " [" + this.categoryName + "] from firm: " + instance.getFirmOwnerFromId( firmOwnerId ).toString();
	}
}
