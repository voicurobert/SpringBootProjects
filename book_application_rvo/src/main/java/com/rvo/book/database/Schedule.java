package com.rvo.book.database;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "schedule" )
public class Schedule implements IAsHashmap{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "schedule_id" )
	private Integer scheduleId;
	
	@Column( name = "monday" )
	private int monday;
	
	@Column( name = "tuesday" )
	private int tuesday;
	
	@Column( name = "wednesday" )
	private int wednesday;
	
	@Column( name = "thursday" )
	private int thursday;
	
	@Column( name = "friday" )
	private int friday;
	
	@Column( name = "saturday" )
	private int saturday;
	
	@Column( name = "sunday" )
	private int sunday;
	
	@Column( name = "monday_working_hours" )
	private String mondayWorkingHours;
	
	@Column( name = "tuesday_working_hours" )
	private String tuesdayWorkingHours;
	
	@Column( name = "wednesday_working_hours" )
	private String wednesdayWorkingHours;
	
	@Column( name = "thursday_working_hours" )
	private String thursdayWorkingHours;
	
	@Column( name = "friday_working_hours" )
	private String fridayWorkingHours;
	
	@Column( name = "saturday_working_hours" )
	private String saturdayWorkingHours;
	
	@Column( name = "sunday_working_hours" )
	private String sundayWorkingHours;
	
	public Integer getScheduleId(){
		return scheduleId;
	}

	public void setFriday( int friday ){
		this.friday = friday;
	}
	
	public int getFriday(){
		return friday;
	}
	
	public void setMonday( int monday ){
		this.monday = monday;
	}
	
	public int getMonday(){
		return monday;
	}
	
	public void setSaturday( int saturday ){
		this.saturday = saturday;
	}
	
	public int getSaturday(){
		return saturday;
	}
	
	public void setSunday( int sunday ){
		this.sunday = sunday;
	}
	
	public int getSunday(){
		return sunday;
	}
	
	public void setThursday( int thursday ){
		this.thursday = thursday;
	}
	
	public int getThursday(){
		return thursday;
	}
	
	public void setTuesday( int tuesday ){
		this.tuesday = tuesday;
	}
	
	public void setWednesday( int wednesday ){
		this.wednesday = wednesday;
	}
	
	public int getTuesday(){
		return tuesday;
	}
	
	public int getWednesday(){
		return wednesday;
	}
	
	public String getMondayWorkingHours(){
		return mondayWorkingHours;
	}
	
	public void setMondayWorkingHours( String mondayWorkingHours ){
		this.mondayWorkingHours = mondayWorkingHours;
	}
	
	public String getTuesdayWorkingHours(){
		return tuesdayWorkingHours;
	}
	
	public void setTuesdayWorkingHours( String tuesdayWorkingHours ){
		this.tuesdayWorkingHours = tuesdayWorkingHours;
	}
	
	public String getWednesdayWorkingHours(){
		return wednesdayWorkingHours;
	}
	
	public void setWednesdayWorkingHours( String wednesdayWorkingHours ){
		this.wednesdayWorkingHours = wednesdayWorkingHours;
	}
	
	public String getThursdayWorkingHours(){
		return thursdayWorkingHours;
	}
	
	public void setThursdayWorkingHours( String thursdayWorkingHours ){
		this.thursdayWorkingHours = thursdayWorkingHours;
	}
	
	public String getFridayWorkingHours(){
		return fridayWorkingHours;
	}
	
	public void setFridayWorkingHours( String fridayWorkingHours ){
		this.fridayWorkingHours = fridayWorkingHours;
	}
	
	public String getSaturdayWorkingHours(){
		return saturdayWorkingHours;
	}
	
	public void setSaturdayWorkingHours( String saturdayWorkingHours ){
		this.saturdayWorkingHours = saturdayWorkingHours;
	}
	
	public String getSundayWorkingHours(){
		return sundayWorkingHours;
	}
	
	public void setSundayWorkingHours( String sundayWorkingHours ){
		this.sundayWorkingHours = sundayWorkingHours;
	}
	

}
