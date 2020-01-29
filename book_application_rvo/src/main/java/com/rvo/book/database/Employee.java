package com.rvo.book.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "employee" )
public class Employee implements IAsHashmap{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "employee_id" )
	private Integer employeeId;
	
	@Column( name = "name" )
	private String employeeName;
	
	@Column( name = "firm_owner_id" )
	private Integer firmOwnerId;
	
	@Column( name = "category_ids" )
	private String categoryIds;
	
	@Column( name = "schedule_id" )
	private Integer scheduleId;
	
	public Employee(){
		
	}
	
	public Employee( String employeeName, Integer firmOwnerId, String categoryIds ){
		this.employeeName = employeeName;
		this.firmOwnerId = firmOwnerId;
		this.categoryIds = categoryIds;
	}
	
	public Integer getEmployeeId(){
		return employeeId;
	}
	
	public String getEmployeeName(){
		return employeeName;
	}
	
	public void setEmployeeName( String employeeName ){
		this.employeeName = employeeName;
	}
	
	public Integer getFirmOwnerId(){
		return firmOwnerId;
	}
	
	public void setFirmOwnerId( Integer firmOwnerId ){
		this.firmOwnerId = firmOwnerId;
	}
	
	public String getCategoryIds(){
		return categoryIds;
	}
	
	public void setCategoryIds( String categoryIds ){
		this.categoryIds = categoryIds;
	}
	
	public Integer getScheduleId(){
		return scheduleId;
	}
	
	public void setScheduleId( Integer scheduleId ){
		this.scheduleId = scheduleId;
	}
	
	@Override
	public String toString(){
		return " [" + this.employeeId + "] " + this.employeeName + DatabaseManager.getInstance().getFirmOwnerFromId( firmOwnerId ).toString();
	}
}
