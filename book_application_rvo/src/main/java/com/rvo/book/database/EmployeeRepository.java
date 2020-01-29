package com.rvo.book.database;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository< Employee, Integer >{

	@Query( "select u from Employee u where u.firmOwnerId = ?1" )
	Employee findByFirmOwnerId( int firmOwnerId );
	
	@Query( "select u from Employee u where u.employeeName = ?1 and u.firmOwnerId = ?2" )
	Employee findByEmployeeNameAndFirmOwnerId( String employeeName, int firmOwnerId );
	
	@Query( "select u from Employee u where u.employeeName = ?1" )
	FirmCategories findByName( String employeeName );
	
	@Query( "select u from Employee u where u.firmOwnerId = ?1" )
	List< Employee > findAllByFirmOwnerId( int firmOwnerId );
}
