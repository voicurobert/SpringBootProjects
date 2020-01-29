package com.rvo.book.database;

import java.sql.Timestamp;
import java.util.Optional;
import org.hibernate.exception.JDBCConnectionException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;



public class DatabaseManager{
	
	private static DatabaseManager instance = null;
	private BookingsRepository bookingsRepository = null;
	private CustomersRepository customersRepository = null;
	private FirmCategoriesRepository firmCategoriesRepository = null;
	private FirmsOwnersRepository firmsOwnersRepository = null;
	private EmployeeRepository employeeRepository = null;
	private ProductsRepository productsRepository = null;
	private ScheduleRepository scheduleRepository = null;
	
	private DatabaseManager(){
		
	}
	
	public static DatabaseManager getInstance(){
		if( instance == null ){
			instance = new DatabaseManager();
		}
		return instance;
	}
	
	public FirmOwners insertFirmOwner( 
			String password, 
			String email, 
			String firmName, 
			String firmAddress, 
			String phoneNumber, 
			String firebaseToken,
			int active ){	
		
		FirmOwners newFirmOwner = firmsOwnersRepository.save( new FirmOwners( password, email, firmName, firmAddress, phoneNumber, active ) );
		return newFirmOwner;
	}
	
	public FirmOwners getFirmOwnerFromId( int firmOwnerId ){
		return firmsOwnersRepository.findById( firmOwnerId ).get();
	}
	
	public Employee insertEmployee( String name, Integer firmOwnerId, String firmCategoryIds ){
		Employee employee = new Employee();
		employee.setEmployeeName( name );
		employee.setFirmOwnerId( firmOwnerId );
		employee.setCategoryIds( firmCategoryIds );
		return employeeRepository.save( employee );
	}
	
	public Optional< Employee > getEmployeeFromEmployeeId( Integer employeeId ){
		return employeeRepository.findById( employeeId );
	}
	
	public FirmCategories insertFirmCategory( String categoryName, Integer firmOwnerId ){
		FirmCategories firmCategory = new FirmCategories();
		firmCategory.setCategoryName( categoryName );
		firmCategory.setFirmOwnerId( firmOwnerId );
		return firmCategoriesRepository.save( firmCategory );
	}
	
	public Optional<FirmCategories> getFirmCategoryFromFirmCategoryId( Integer firmCategoryId ){
		return firmCategoriesRepository.findById( firmCategoryId );
	}
	
	public Products insertProduct( String name, Integer price, String duration, Integer firmCategoryId ){
		Products product = new Products();
		product.setProductName( name );
		product.setPrice( price );
		product.setDuration( duration );
		product.setFirmCategoryId( firmCategoryId );
		return productsRepository.save( product );
	}
	
	public Optional<Products> getProductFromProductId( Integer productId ){
		return productsRepository.findById( productId );
	}
	
	public Optional<Customers> getCustomerFromCustomerId( Integer customerId ){
		return customersRepository.findById( customerId );
	}
	
	public FirmOwners getFirmOwnerFromProductId( Integer productId ){
		Products product = getProductFromProductId( productId ).get();
		if( product != null ){
			FirmCategories category = getFirmCategoryFromFirmCategoryId( product.getFirmCategoryId() ).get();
			if( category != null ){
				return getFirmOwnerFromId( category.getFirmOwnerId() );
			}
		}
		return null;
	}
	
	public Bookings insertBooking( Timestamp date, Integer customerId, Integer employeeId, Integer productId, Integer active ){
		Bookings booking = new Bookings();
		booking.setDate( date );
		booking.setCustomerId( customerId );
		booking.setEmployeeId( employeeId );
		booking.setProductId( productId );
		booking.setActive( active );
		return bookingsRepository.save( booking );
	}
	
	public Optional<Bookings> getBookingFromBookingId( Integer bookingId ){
		return bookingsRepository.findById( bookingId );
	}
	
	public Schedule insertSchedule( 
			int monday, 
			int tuesday, 
			int wednesday, 
			int thursday, 
			int friday, 
			int saturday, 
			int sunday,
			String mondayWorkingHours, 
			String tuesdayWorkingHours, 
			String wednesdayWorkingHours, 
			String thursdayWorkingHours, 
			String fridayWorkingHours, 
			String saturdayWorkingHours, 
			String sundayWorkingHours ){
		
		Schedule schedule = new Schedule();
		schedule.setMonday( monday );
		schedule.setMondayWorkingHours( mondayWorkingHours );
		schedule.setTuesday( tuesday );
		schedule.setTuesdayWorkingHours( tuesdayWorkingHours );
		schedule.setWednesday( wednesday );
		schedule.setWednesdayWorkingHours( wednesdayWorkingHours );
		schedule.setThursday( thursday );
		schedule.setThursdayWorkingHours( thursdayWorkingHours );
		schedule.setFriday( friday );
		schedule.setFridayWorkingHours( fridayWorkingHours );
		schedule.setSaturday( saturday );
		schedule.setSaturdayWorkingHours( saturdayWorkingHours );
		schedule.setSunday( sunday );
		schedule.setSundayWorkingHours( sundayWorkingHours );
		return scheduleRepository.save( schedule );
	}
	
	public FirmOwners updateFirmOwnerWithScheduleId( int firmOwnerId, int scheduleId ){
		FirmOwners firmOwner = firmsOwnersRepository.findById( firmOwnerId ).get();
		firmOwner.setScheduleId( scheduleId );
		return firmsOwnersRepository.save( firmOwner );
	}
	
	public Employee updateEmployeeWithScheduleId( int employeeId, int scheduleId ){
		Employee employee = employeeRepository.findById( employeeId ).get();
		employee.setScheduleId( scheduleId );
		return employeeRepository.save( employee );
	}
	
	public Customers insertCustomer( 
			String name, 
			String password, 
			String email, 
			String phoneNumber,
			String firebaseToken) throws MySQLDataException, JDBCConnectionException{
		
		Customers customer = new Customers();
		customer.setEmail( email );
		customer.setName( name );
		customer.setPassword( password );
		customer.setPhoneNumber( phoneNumber );
		customer.setFirebaseToken( firebaseToken );
		return customersRepository.save( customer );
	}
	
	public Optional<Schedule> getScheduleFromScheduleId( int scheduleId ){
		return scheduleRepository.findById( scheduleId );
	}
	
	public void setBookingsRepository( BookingsRepository bookingsRepository ){
		this.bookingsRepository = bookingsRepository;
	}
	
	public void setCustomersRepository( CustomersRepository customersRepository ){
		this.customersRepository = customersRepository;
	}
	
	public void setFirmCategoriesRepository( FirmCategoriesRepository firmCategoriesRepository ){
		this.firmCategoriesRepository = firmCategoriesRepository;
	}
	
	
	public void setOwnersRepository( EmployeeRepository employeeRepository ){
		this.employeeRepository = employeeRepository;
	}
	
	public void setProductsRepository( ProductsRepository productsRepository ){
		this.productsRepository = productsRepository;
	}
	
	public void setScheduleRepository( ScheduleRepository scheduleRepository ){
		this.scheduleRepository = scheduleRepository;
	}
	
	public void setFirmsOwnersRepository( FirmsOwnersRepository firmsOwnersRepository ){
		this.firmsOwnersRepository = firmsOwnersRepository;
	}
	
}
