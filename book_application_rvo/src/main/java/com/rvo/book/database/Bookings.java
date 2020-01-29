package com.rvo.book.database;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "bookings" )
public class Bookings implements IAsHashmap{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "booking_id")
	private Integer bookingId;
	
	@Column( name = "product_id" )
	private Integer productId;
	
	@Column( name = "employee_id" )
	private Integer employeeId;
	
	@Column( name = "customer_id" )
	private Integer customerId;
	
	@Column( name = "date" )
	private Timestamp date;
	
	@Column( name = "active" )
	private Integer active;
	
	public Integer getBookingId(){
		return bookingId;
	}
	
	public Integer getEmployeeId(){
		return employeeId;
	}
	
	public void setEmployeeId( Integer employeeId ){
		this.employeeId = employeeId;
	}
	
	public Integer getProductId(){
		return productId;
	}
	
	public void setProductId( Integer productId ){
		this.productId = productId;
	}
	
	public Integer getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId( Integer customerId ){
		this.customerId = customerId;
	}
	
	public String getDate(){
		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss"  );
		return dateFormat.format( date );
	}
	
	public Date date(){
		return date;
	}
	
	public void setDate( Timestamp date ){
		this.date = date;
	}
	
	public void setActive( Integer active ){
		this.active = active;
	}
	
	public Integer getActive(){
		return active;
	}
	
	@Override
	public String toString(){
		DatabaseManager instance = DatabaseManager.getInstance();
		return "Booking ID: [" + this.bookingId + "] is set on: " + date.toString() + 
				" for customer " + instance.getCustomerFromCustomerId( this.customerId ).toString() +
				" with owner: " + instance.getEmployeeFromEmployeeId( this.employeeId ).get().getEmployeeName() +
				" for product: " + instance.getProductFromProductId( productId ).get().getProductName();
	}
	
	public boolean onSameDateAs( Timestamp ts ){
		if( date.getTime() == ts.getTime() ){
			return true;
		}
		return false;
	}
	
	public boolean after( Date when ){
		return date.getTime() >= when.getTime();
	}
}
