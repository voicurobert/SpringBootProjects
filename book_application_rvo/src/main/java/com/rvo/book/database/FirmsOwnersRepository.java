package com.rvo.book.database;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FirmsOwnersRepository extends CrudRepository< FirmOwners, Integer >{
	
	@Query( "select u from FirmOwners u where u.email = ?1 and u.password = ?2" )
	FirmOwners findByEmailAndPassword( String username, String password );
	
	@Query( "select u from FirmOwners u where u.email = ?1" )
	FirmOwners findByEmail( String email );
}
