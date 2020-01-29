package com.rvo.book.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "products" )
public class Products implements IAsHashmap{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "product_id" )
	private Integer productId;
	
	@Column( name = "name" )
	private String productName;
	
	@Column( name = "firm_category_id" )
	private Integer firmCategoryId;
	
	@Column( name = "price" )
	private Integer price;
	
	@Column( name = "duration" )
	private String duration;
	
	public Integer getProductId(){
		return productId;
	}
	
	public Integer getFirmCategoryId(){
		return firmCategoryId;
	}
	
	public void setFirmCategoryId( Integer firmCategoryId ){
		this.firmCategoryId = firmCategoryId;
	}
	
	public String getProductName(){
		return productName;
	}
	
	public void setProductName( String productName ){
		this.productName = productName;
	}
	
	public Integer getPrice(){
		return price;
	}
	
	public void setPrice( Integer price ){
		this.price = price;
	}
	
	public String getDuration(){
		return duration;
	}
	
	public void setDuration( String duration ){
		this.duration = duration;
	}
	
	@Override
	public String toString(){
		DatabaseManager instance = DatabaseManager.getInstance();
		return " [" + this.productId + "] " + this.productName + " with price " + this.price + " and duration " + this.duration +
				" firm category:" + instance.getFirmCategoryFromFirmCategoryId( firmCategoryId );
	}
	
}
