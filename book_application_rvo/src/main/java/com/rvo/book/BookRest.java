package com.rvo.book;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;
import com.rvo.book.database.Bookings;
import com.rvo.book.database.BookingsRepository;
import com.rvo.book.database.Customers;
import com.rvo.book.database.CustomersRepository;
import com.rvo.book.database.DatabaseManager;
import com.rvo.book.database.Employee;
import com.rvo.book.database.FirmCategoriesRepository;
import com.rvo.book.database.FirmOwners;
import com.rvo.book.database.FirmsOwnersRepository;
import com.rvo.book.database.Products;
import com.rvo.book.database.EmployeeRepository;
import com.rvo.book.database.FirmCategories;
import com.rvo.book.database.ProductsRepository;
import com.rvo.book.database.Schedule;
import com.rvo.book.database.ScheduleRepository;

@RestController
public class BookRest{
	
	@Autowired
	public BookingsRepository bookingsRepository;
	
	@Autowired
	private CustomersRepository customersRepository;
	
	@Autowired
	private FirmCategoriesRepository firmCategoriesRepository;
	
	@Autowired
	private FirmsOwnersRepository firmsOwnersRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private List< HashMap< Object, Object > > insertOperationResponse = null; 
	private DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
	private DateFormat sqlFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	private DateFormat comparatorFormat = new SimpleDateFormat( "yyyyMMdd" );
	
	private void initialiseDatabaseManagerRepository(){
		databaseManager.setBookingsRepository( bookingsRepository );
		databaseManager.setCustomersRepository( customersRepository );
		databaseManager.setFirmCategoriesRepository( firmCategoriesRepository );
		databaseManager.setOwnersRepository( employeeRepository );
		databaseManager.setProductsRepository( productsRepository );
		databaseManager.setScheduleRepository( scheduleRepository );
		databaseManager.setFirmsOwnersRepository( firmsOwnersRepository );
		insertOperationResponse = new ArrayList<>();
		dateFormat.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
		sqlFormat.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
	}
	
	@RequestMapping( "/insertFirmOwner" )
	public List< HashMap< Object, Object > > insertFirmOwner( 
			@RequestParam String password, 
			@RequestParam String email, 
			@RequestParam String firmName,
			@RequestParam String firmAddress,
			@RequestParam String phoneNumber, 
			@RequestParam String firebaseToken,
			@RequestParam int active ){
		
		initialiseDatabaseManagerRepository();
		FirmOwners insertedFirmOwner = null;
		HashMap< Object, Object > response = new HashMap<>();
		insertedFirmOwner = databaseManager.insertFirmOwner( password, email, firmName, firmAddress, phoneNumber, firebaseToken, active );
		if( insertedFirmOwner != null ){
			response = insertedFirmOwner.asHashMap();
			response.put( "inserted?", true );
		}else{
			response.put( "inserted?", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/updateFirm" )
	public List< HashMap< Object, Object > > updateFirm( 
			@RequestParam Integer firmOwnerId, 
			@RequestParam String firmName, 
			@RequestParam String firmAddress, 
			@RequestParam String email, 
			@RequestParam String phoneNumber ){
		initialiseDatabaseManagerRepository();
		List< HashMap< Object, Object > > firmOwnerAsHashMap = new ArrayList<>();
		FirmOwners firm = firmsOwnersRepository.findById( firmOwnerId ).get();
		firm.setFirmName( firmName );
		firm.setEmail( email );
		firm.setFirmAddress( firmAddress );
		firm.setPhoneNumber( phoneNumber );
		HashMap< Object, Object > response = new HashMap<>();
		if( firmsOwnersRepository.save( firm ) != null ){
			response = firm.asHashMap();
			response.put( "updated?", true );
		}else{
			response.put( "updated?", false );
		}
		firmOwnerAsHashMap.add( response );
		return firmOwnerAsHashMap;
	}
	
	@RequestMapping( "/updateFirmWithFirebaseToken" )
	public List< HashMap< Object, Object > > updateFirmWithFirebaseToken( @RequestParam Integer firmOwnerId, @RequestParam String firebaseToken ){
		initialiseDatabaseManagerRepository();
		List< HashMap< Object, Object > > firmOwnerAsHashMap = new ArrayList<>();
		FirmOwners firm = firmsOwnersRepository.findById( firmOwnerId ).get();
		firm.setFirebaseToken( firebaseToken );
		HashMap< Object, Object > response = new HashMap<>();
		if( firmsOwnersRepository.save( firm ) != null ){
			response = firm.asHashMap();
			response.put( "updated?", true );
		}else{
			response.put( "updated?", false );
		}
		firmOwnerAsHashMap.add( response );
		return firmOwnerAsHashMap;
	}
	
	@RequestMapping( "/getFirms" )
	public List< HashMap< Object, Object > > getFirms(){
		initialiseDatabaseManagerRepository();
		List< FirmOwners > firms = (List< FirmOwners >) firmsOwnersRepository.findAll();
		List< HashMap< Object, Object > > firmsAsHashMap = new ArrayList<>();
		for( FirmOwners firm : firms ){
			firmsAsHashMap.add( firm.asHashMap() );
		}
		return firmsAsHashMap;
	}
	
	@RequestMapping( "/getActiveFirms" )
	public List< HashMap< Object, Object > > getActiveFirms(){
		initialiseDatabaseManagerRepository();
		List< FirmOwners > firms = (List< FirmOwners >) firmsOwnersRepository.findAll();
		List< HashMap< Object, Object > > firmsAsHashMap = new ArrayList<>();
		for( FirmOwners firm : firms ){
			if( firm.isActive() ){
				firmsAsHashMap.add( firm.asHashMap() );
			}
		}
		return firmsAsHashMap;
	}
	
	@RequestMapping( "/getFirmOwnerFromEmail" )
	public List< HashMap< Object, Object > >  getFirmOwnerFromEmail( @RequestParam String email ){
		initialiseDatabaseManagerRepository();
		FirmOwners owner = firmsOwnersRepository.findByEmail( email );
		HashMap< Object, Object > response = new HashMap<>();
		if( owner != null ){
			response = owner.asHashMap();
			insertOperationResponse.add( response );
			return insertOperationResponse;
		}
		response.put( "ERROR", "ERROR" );
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/firmOwnerWithEmailExists" )
	public List< HashMap< Object, Object > > firmOwnerWithEmailExists( @RequestParam String email ){
		initialiseDatabaseManagerRepository();
		FirmOwners owner = firmsOwnersRepository.findByEmail( email );
		HashMap< Object, Object > response = new HashMap<>();
		if( owner != null ){
			response.put( "exists", true );
		}else{
			response.put( "exists", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/firmOwnerWithEmailAndPasswordExists" )
	public List< HashMap< Object, Object > > firmOwnerWithEmailAndPasswordExists( @RequestParam String email, @RequestParam String password ){
		initialiseDatabaseManagerRepository();
		FirmOwners owner = firmsOwnersRepository.findByEmailAndPassword( email, password );
		HashMap< Object, Object > response = new HashMap<>();
		if( owner != null ){
			response.put( "exists", true );
		}else{
			response.put( "exists", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/insertCategory" )
	public List< HashMap< Object, Object > > insertCategory( @RequestParam String categoryName, @RequestParam int firmOwnerId ) {
		initialiseDatabaseManagerRepository();
		FirmCategories insertedFirmCategory = null;
		HashMap< Object, Object > response = new HashMap<>();
		insertedFirmCategory = databaseManager.insertFirmCategory( categoryName, firmOwnerId );
		if( insertedFirmCategory != null ){
			response = insertedFirmCategory.asHashMap();
			response.put( "inserted?", true );
		}else{
			response.put( "inserted?", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/updateCategory" )
	public List< HashMap< Object, Object > > updateCategory( @RequestParam Integer categoryId, @RequestParam String categoryName ){
		initialiseDatabaseManagerRepository();
		List< HashMap< Object, Object > > categoriesAsHashMap = new ArrayList<>();
		FirmCategories category = firmCategoriesRepository.findById( categoryId ).get();
		category.setCategoryName( categoryName );
		HashMap< Object, Object > response = new HashMap<>();
		if( firmCategoriesRepository.save( category ) != null ){
			response = category.asHashMap();
			response.put( "updated?", true );
		}else{
			response.put( "updated?" , false );
		}
		categoriesAsHashMap.add( response );
		return categoriesAsHashMap;
	}
	
	@RequestMapping( "/getFirmCategoriesFromFirmOwnerId" )
	public List< HashMap< Object, Object > > getCategoriesFromFirmOwnerId( @RequestParam int firmOwnerId ){
		initialiseDatabaseManagerRepository();
		List< FirmCategories > categories = firmCategoriesRepository.findAllByFirmOwnerId( firmOwnerId );
		List< HashMap< Object, Object > > categoriesAsHashMap = new ArrayList<>();
		if( categories == null || categories.isEmpty() ){
			return categoriesAsHashMap;
		}
		for( FirmCategories firmCategory : categories ){
			categoriesAsHashMap.add( firmCategory.asHashMap() );
		}
		return categoriesAsHashMap;
	}
	
	@RequestMapping( "/getFirmCategoryIdFromName" )
	public List< HashMap< Object, Object > > getFirmCategoryIdFromName( @RequestParam String categoryName ) {
		initialiseDatabaseManagerRepository();
		FirmCategories insertedFirmCategory = null;
		HashMap< Object, Object > response = new HashMap<>();
		try{
			insertedFirmCategory = firmCategoriesRepository.findByCategoryName( categoryName );
			if( insertedFirmCategory != null ){
				response.put( "id", insertedFirmCategory.getCategoryId() );
			}else{
				response.put( "id", -1 );
			}
			insertOperationResponse.add( response );
		}catch( JDBCConnectionException ex ){
			response.put( "insert", ex.getLocalizedMessage() );
			insertOperationResponse.add( response );
			return insertOperationResponse;
		}
		return insertOperationResponse;
	}
	
	@RequestMapping( "/insertProduct" )
	public List< HashMap< Object, Object > > insertProduct( 
			@RequestParam String productName, 
			@RequestParam Integer price, 
			@RequestParam String duration, 
			@RequestParam int 
			firmCategoryId ) {
		initialiseDatabaseManagerRepository();
		Products product = null;
		HashMap< Object, Object > response = new HashMap<>();
		product = databaseManager.insertProduct( productName, price, duration, firmCategoryId );
		if( product != null ){
			response = product.asHashMap();
			response.put( "inserted?", true );
		}else{
			response.put( "inserted?", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/updateProduct" )
	public List< HashMap< Object, Object > > updateProduct( 
			@RequestParam Integer productId, 
			@RequestParam String productName, 
			@RequestParam Integer price, 
			@RequestParam String duration ){
		initialiseDatabaseManagerRepository();
		List< HashMap< Object, Object > > productsAsHashMap = new ArrayList<>();
		Products product = productsRepository.findById( productId ).get();
		product.setProductName( productName );
		product.setPrice( price );
		product.setDuration( duration );
		if( productsRepository.save( product ) != null ){
			productsAsHashMap.add( product.asHashMap() );
		}
		return productsAsHashMap;
	}
	
	@RequestMapping( "/getProductsFromCategoryId" )
	public List< HashMap< Object, Object > > getProductsFromCategoryId( @RequestParam int firmCategoryId ){
		List< Products > products = productsRepository.findAllByCategoryId( firmCategoryId );
		List< HashMap< Object, Object > > productsHasMap = new ArrayList<>();
		if( products == null || products.isEmpty() ){
			return productsHasMap;
		}
		for( Products firmCategory : products ){
			productsHasMap.add( firmCategory.asHashMap() );
		}
		return productsHasMap;
	}
	
	@RequestMapping( "/getProductIdFromName" )
	public List< HashMap< Object, Object > > getProductIdFromName( @RequestParam String productName ) {
		initialiseDatabaseManagerRepository();
		Products product = null;
		HashMap< Object, Object > response = new HashMap<>();
		try{
			product = productsRepository.findByName( productName );
			if( product != null ){
				response.put( "id", product.getProductId() );
			}else{
				response.put( "id", -1 );
			}
			insertOperationResponse.add( response );
		}catch( JDBCConnectionException ex ){
			response.put( "insert", ex.getLocalizedMessage() );
			insertOperationResponse.add( response );
			return insertOperationResponse;
		}
		return insertOperationResponse;
	}
	
	@RequestMapping( "/insertEmployee" )
	public List< HashMap< Object, Object > > insertEmployee( 
			@RequestParam String employeeName, 
			@RequestParam String firmCategoryIds, 
			@RequestParam int firmOwnerId ) {
		initialiseDatabaseManagerRepository();
		Employee employee = null;
		HashMap< Object, Object > response = new HashMap<>();
		employee = databaseManager.insertEmployee( employeeName, firmOwnerId, firmCategoryIds );
		if( employee != null ){
			response = employee.asHashMap();
			response.put( "inserted?", true );
		}else{
			response.put( "inserted?", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/updateEmployee" )
	public List< HashMap< Object, Object > > updateEmployee( 
			@RequestParam Integer employeeId, 
			@RequestParam String employeeName, 
			@RequestParam String categoryIds ){
		initialiseDatabaseManagerRepository();
		List< HashMap< Object, Object > > employeesAsHashMap = new ArrayList<>();
		Employee employee = employeeRepository.findById( employeeId ).get();
		employee.setEmployeeName( employeeName );
		employee.setCategoryIds( categoryIds );
		if( employeeRepository.save( employee ) != null ){
			employeesAsHashMap.add( employee.asHashMap() );
		}
		return employeesAsHashMap;
	}
	
	@RequestMapping( "/getEmployeesFromFirmOwnerId" )
	public List< HashMap< Object, Object > > getEmployeesFromFirmOwnerId( @RequestParam int firmOwnerId ){
		initialiseDatabaseManagerRepository();
		List< Employee > employees = employeeRepository.findAllByFirmOwnerId( firmOwnerId );
		List< HashMap< Object, Object > > employeesAsHashMap = new ArrayList<>();
		if( employees == null || employees.isEmpty() ){
			return employeesAsHashMap;
		}
		for( Employee employee : employees ){
			employeesAsHashMap.add( employee.asHashMap() );
		}
		return employeesAsHashMap;
	}
	
	@RequestMapping( "/insertSchedule" )
	public List< HashMap< Object, Object > > insertSchedule( 
			@RequestParam int monday,
			@RequestParam int tuesday,
			@RequestParam int wednesday,
			@RequestParam int thursday, 
			@RequestParam int friday, 
			@RequestParam int saturday, 
			@RequestParam int sunday,
			@RequestParam String mondayWorkingHours, 
			@RequestParam String tuesdayWorkingHours, 
			@RequestParam String wednesdayWorkingHours, 
			@RequestParam String thursdayWorkingHours, 
			@RequestParam String fridayWorkingHours, 
			@RequestParam String saturdayWorkingHours,
			@RequestParam String sundayWorkingHours ){
		initialiseDatabaseManagerRepository();
		Schedule schedule = databaseManager.insertSchedule( 
				monday, 
				tuesday, 
				wednesday, 
				thursday, 
				friday, 
				saturday, 
				sunday, 
				mondayWorkingHours,
				tuesdayWorkingHours, 
				wednesdayWorkingHours, 
				thursdayWorkingHours,
				fridayWorkingHours, 
				saturdayWorkingHours, 
				sundayWorkingHours );
		HashMap< Object, Object > response = new HashMap<>();
		if( schedule != null ){
			response = schedule.asHashMap();
			response.put( "inserted?", true );
		}else{
			response.put( "inserted?", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/updateSchedule" )
	public List< HashMap< Object, Object > > updateSchedule( 
			@RequestParam int scheduleId, 
			@RequestParam int monday, 
			@RequestParam int tuesday, 
			@RequestParam int wednesday, 
			@RequestParam int thursday, 
			@RequestParam int friday, 
			@RequestParam int saturday, 
			@RequestParam int sunday,
			@RequestParam String mondayWorkingHours,
			@RequestParam String tuesdayWorkingHours, 
			@RequestParam String wednesdayWorkingHours, 
			@RequestParam String thursdayWorkingHours, 
			@RequestParam String fridayWorkingHours, 
			@RequestParam String saturdayWorkingHours, 
			@RequestParam String sundayWorkingHours ){
		initialiseDatabaseManagerRepository();
		Schedule schedule = scheduleRepository.findById( scheduleId ).get();
		schedule.setMonday( monday );
		schedule.setTuesday( tuesday );
		schedule.setWednesday( wednesday );
		schedule.setThursday( thursday );
		schedule.setFriday( friday );
		schedule.setSaturday( saturday );
		schedule.setSunday( sunday );
		schedule.setMondayWorkingHours( mondayWorkingHours );
		schedule.setTuesdayWorkingHours( tuesdayWorkingHours );
		schedule.setWednesdayWorkingHours( wednesdayWorkingHours );
		schedule.setThursdayWorkingHours( thursdayWorkingHours );
		schedule.setFridayWorkingHours( fridayWorkingHours );
		schedule.setSaturdayWorkingHours( saturdayWorkingHours );
		schedule.setSaturdayWorkingHours( saturdayWorkingHours );
		Schedule updatedSchedule = scheduleRepository.save( schedule );
		HashMap< Object, Object > response = new HashMap<>();
		if( updatedSchedule != null ){
			response.put( "updated?", true );
		}else{
			response.put( "updated?", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/getScheduleFromFirmOwnerId" )
	public List< HashMap< Object, Object > > getScheduleFromFirmOwnerId( @RequestParam int firmOwnerId ){
		initialiseDatabaseManagerRepository();
		FirmOwners firmOwner = firmsOwnersRepository.findById( firmOwnerId ).get();
		List< HashMap< Object, Object > > schedulesList = new ArrayList<>();
		if(  firmOwner.getScheduleId() == null ){
			return schedulesList;
		}
		Schedule schedule = scheduleRepository.findById( firmOwner.getScheduleId() ).get();
		if( schedule == null){
			return schedulesList;
		}
		schedulesList.add( schedule.asHashMap() );
		return schedulesList;
	}
	
	@RequestMapping( "/getScheduleFromEmployeeId" )
	public List< HashMap< Object, Object > > getScheduleFromEmployeeId( @RequestParam int employeeId ){
		initialiseDatabaseManagerRepository();
		Employee employee = employeeRepository.findById( employeeId ).get();
		List< HashMap< Object, Object > > schedulesList = new ArrayList<>();
		if( employee.getScheduleId() == null ){
			return schedulesList;
		}
		Schedule schedule = scheduleRepository.findById( employee.getScheduleId() ).get();
		if( schedule == null){
			return schedulesList;
		}
		schedulesList.add( schedule.asHashMap() );
		return schedulesList;
	}
	
	@RequestMapping( "/updateFirmOwnerWithScheduleId" )
	public List< HashMap< Object, Object > > updateFirmOwnerWithScheduleId( @RequestParam int firmOwnerId, @RequestParam int scheduleId ){
		initialiseDatabaseManagerRepository();
		FirmOwners firmOwner = databaseManager.updateFirmOwnerWithScheduleId( firmOwnerId, scheduleId );
		HashMap< Object, Object > response = new HashMap<>();
		if( firmOwner != null ){
			response.put( "updated?", true );
		}else{
			response.put( "updated?", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/updateEmployeeWithScheduleId" )
	public List< HashMap< Object, Object > > updateEmployeeWithScheduleId( @RequestParam int employeeId, @RequestParam int scheduleId ){
		initialiseDatabaseManagerRepository();
		Employee employee = databaseManager.updateEmployeeWithScheduleId( employeeId, scheduleId );
		HashMap< Object, Object > response = new HashMap<>();
		if( employee != null ){
			response.put( "updated?", true );
		}else{
			response.put( "updated?", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/insertCustomer" )
	public List< HashMap< Object, Object > > insertCustomer( 
			@RequestParam String name, 
			@RequestParam String email, 
			@RequestParam String password, 
			@RequestParam String phoneNumber,
			@RequestParam String firebaseToken ) {
		
		initialiseDatabaseManagerRepository();
		Customers insertedCustomer = null;
		HashMap< Object, Object > response = new HashMap<>();
		try{
			insertedCustomer = databaseManager.insertCustomer( name, password, email, phoneNumber, firebaseToken );
			if( insertedCustomer != null ){
				response = insertedCustomer.asHashMap();
				response.put( "inserted?", true );
			}else{
				response.put( "inserted?", false );
			}
		}catch( MySQLDataException e ){
			Log.log( e.getMessage() );
			response.put( "error", e.getMessage() );
			insertOperationResponse.add( response );
			return insertOperationResponse;
		}catch( JDBCConnectionException ex ){
			response.put( "error", ex.getLocalizedMessage() );
			insertOperationResponse.add( response );
			return insertOperationResponse;
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/getCustomerFromId" )
	public List< HashMap< Object, Object > > getCustomerFromId( @RequestParam Integer customerId ){
		initialiseDatabaseManagerRepository();
		List< HashMap< Object, Object > > customerHashMap = new ArrayList<>();
		Customers customer = customersRepository.findById( customerId ).get();
		HashMap< Object, Object > response = new HashMap<>();
		if( customersRepository.save( customer ) != null ){
			response = customer.asHashMap();
			response.put( "updated?", true );
		}else{
			response.put( "updated?", false );
		}
		customerHashMap.add( response );
		return customerHashMap;
	}
	
	@RequestMapping( "/updateCustomerWithFirebaseToken" )
	public List< HashMap< Object, Object > > updateCustomerWithFirebaseToken( @RequestParam Integer customerId, @RequestParam String firebaseToken ){
		initialiseDatabaseManagerRepository();
		List< HashMap< Object, Object > > customerHashMap = new ArrayList<>();
		Customers customer = customersRepository.findById( customerId ).get();
		customer.setFirebaseToken( firebaseToken );
		HashMap< Object, Object > response = new HashMap<>();
		if( customersRepository.save( customer ) != null ){
			response = customer.asHashMap();
			response.put( "updated?", true );
		}else{
			response.put( "updated?", false );
		}
		customerHashMap.add( response );
		return customerHashMap;
	}
	
	@RequestMapping( "/customerWithEmailAndPasswordExists" )
	public List< HashMap< Object, Object > > customerWithEmailAndPasswordExists( @RequestParam String email, @RequestParam String password ){
		initialiseDatabaseManagerRepository();
		Customers customer = customersRepository.findByEmailAndPassword( email, password );
		HashMap< Object, Object > response = new HashMap<>();
		if( customer != null ){
			response = customer.asHashMap();
			response.put( "exists", true );
		}else{
			response.put( "exists", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/customerWithEmailExists" )
	public List< HashMap< Object, Object > > customerWithEmailExists( @RequestParam String email ){
		initialiseDatabaseManagerRepository();
		Customers customer = customersRepository.findByEmail( email );
		HashMap< Object, Object > response = new HashMap<>();
		if( customer != null ){
			response.put( "exists", true );
		}else{
			response.put( "exists", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/insertBooking" )
	public List< HashMap< Object, Object > > insertBooking( 
			@RequestParam Integer customerId, 
			@RequestParam Integer employeeId, 
			@RequestParam Integer productId, 
			@RequestParam String dateString,
			@RequestParam Integer active ){
		initialiseDatabaseManagerRepository();
		Date dateFromParam = null;
		try{
			dateFromParam = dateFormat.parse( dateString.replaceAll("Z$", "+0000") );
		}catch( ParseException e ){
			e.printStackTrace();
		}
		HashMap< Object, Object > response = new HashMap<>();
		if( dateFromParam == null ){
			response.put( "inserted?", false );
			insertOperationResponse.add( response );
			return insertOperationResponse;
		}
		Timestamp sqlTimestamp = Timestamp.valueOf( sqlFormat.format( dateFromParam ) );
		Bookings book = databaseManager.insertBooking( sqlTimestamp, customerId, employeeId, productId, active );
		if( book != null ){
			response = book.asHashMap();
			response.put( "inserted?", true );
		}else{
			response.put( "inserted?", false );
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
	@RequestMapping( "/getBookingsFromEmployeeId" )
	public List< HashMap< Object, Object > > getBookingsFromEmployeeId( @RequestParam Integer employeeId ){
		initialiseDatabaseManagerRepository();
		List< Bookings > bookings = bookingsRepository.findAllByEmployeeId( employeeId );
		List< HashMap< Object, Object > > bookingList = new ArrayList<>();
		if( bookings.isEmpty() ){
			return bookingList;
		}
		for( Bookings book : bookings ){
			bookingList.add( book.asHashMap() );
		}
		return bookingList;
	}
	
	@RequestMapping( "/getPendingBookingsForFirmOwnerId" )
	public List< HashMap< Object, Object > > getPendingBookingsForFirmOwnerId( @RequestParam Integer firmOwnerId ){
		initialiseDatabaseManagerRepository();
		List< HashMap< Object, Object > > bookingList = new ArrayList<>();
		Iterable< Bookings > bookings = bookingsRepository.findAll();
		for( Bookings booking : bookings ){
			if( booking.getActive().equals( -1 ) && 
					databaseManager.getFirmOwnerFromProductId( booking.getProductId() ).getFirmOwnerId().equals( firmOwnerId ) ){
				bookingList.add( booking.asHashMap() );
			}
		}
		return bookingList;
	}
	
	@RequestMapping( "/getBookingsFromEmployeeIdForDate" )
	public List< HashMap< Object, Object > > getBookingsFromEmployeeIdForDate( @RequestParam Integer employeeId, @RequestParam String dateString ){
		initialiseDatabaseManagerRepository();
		List< Bookings > bookings = bookingsRepository.findAllByEmployeeId( employeeId );
		List< HashMap< Object, Object > > bookingList = new ArrayList<>();
		if( bookings.isEmpty() ){
			return bookingList;
		}
		Date dateFromParam = null;
		try{
			dateFromParam = dateFormat.parse( dateString.replaceAll( "Z$", "+0000" ) );
		}catch( ParseException e ){
			e.printStackTrace();
		}
		Timestamp sqlTimestamp = Timestamp.valueOf( sqlFormat.format( dateFromParam ) );
		for( Bookings book : bookings ){
			if( comparatorFormat.format( book.date() ).equals( comparatorFormat.format( sqlTimestamp ) ) ){
				bookingList.add( book.asHashMap() );
			}
		}
		return bookingList;
	}
	
	@RequestMapping( "/getBookingsFromCustomerIdForDate" )
	public List< HashMap< Object, Object > > getBookingsFromCustomerIdForDate( @RequestParam Integer customerId, @RequestParam String dateString ){
		initialiseDatabaseManagerRepository();
		List< Bookings > bookings = bookingsRepository.findAllByCustomerId( customerId );
		List< HashMap< Object, Object > > bookingList = new ArrayList<>();
		if( bookings.isEmpty() ){
			return bookingList;
		}
		Date dateFromParam = null;
		try{
			dateFromParam = dateFormat.parse( dateString.replaceAll( "Z$", "+0000" ) );
		}catch( ParseException e ){
			e.printStackTrace();
		}
		Timestamp sqlTimestamp = Timestamp.valueOf( sqlFormat.format( dateFromParam ) );
		for( Bookings book : bookings ){
			if( comparatorFormat.format( book.date() ).equals( comparatorFormat.format( sqlTimestamp ) ) ){
				bookingList.add( book.asHashMap() );
			}
		}
		return bookingList;
	}
	
	@RequestMapping( "/setBookingActivationStatus" )
	public List< HashMap< Object, Object > > setBookingActivationStatus( @RequestParam Integer bookingId, @RequestParam Integer active ){
		initialiseDatabaseManagerRepository();
		Bookings booking = bookingsRepository.findById( bookingId ).get();
		HashMap< Object, Object > response = new HashMap<>();
		if( booking == null ){
			response.put( "updated?", false );
		}else{
			booking.setActive( active );
			Bookings savedBooking = bookingsRepository.save( booking );
			if( savedBooking.getActive().equals( active ) ){
				response = booking.asHashMap();
				response.put( "updated?", true );
			}else{
				response.put( "updated?", false );
			}
		}
		insertOperationResponse.add( response );
		return insertOperationResponse;
	}
	
}
