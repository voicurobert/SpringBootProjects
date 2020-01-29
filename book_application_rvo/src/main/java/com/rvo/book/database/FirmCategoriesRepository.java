package com.rvo.book.database;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FirmCategoriesRepository extends CrudRepository< FirmCategories, Integer >{
	
	@Query( "select u from FirmCategories u where u.firmOwnerId = ?1" )
	List< FirmCategories > findAllByFirmOwnerId( int firmOwnerId );
	
	@Query( "select u from FirmCategories u where u.categoryName = ?1" )
	FirmCategories findByCategoryName( String name );
}
