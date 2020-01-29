package com.rvo.book.database;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookingsRepository extends CrudRepository< Bookings, Integer >{
	
	@Query( "select u from Bookings u where u.employeeId = ?1" )
	List< Bookings > findAllByEmployeeId( Integer employeeId );
	
	@Query( "select u from Bookings u where u.customerId = ?1" )
	List< Bookings > findAllByCustomerId( Integer customerId );
}
