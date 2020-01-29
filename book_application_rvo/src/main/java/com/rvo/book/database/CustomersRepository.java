package com.rvo.book.database;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository< Customers, Integer >{
	
	@Query( "select u from Customers u where u.email = ?1" )
	Customers findByEmail( String email );
	
	@Query( "select u from Customers u where u.email = ?1 and u.password = ?2" )
	Customers findByEmailAndPassword( String email, String password );
}
