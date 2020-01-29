package com.rvo.book.database;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductsRepository extends CrudRepository< Products, Integer >{
	
	@Query( "select u from Products u where u.productName = ?1" )
	Products findByName( String productName );
	
	@Query( "select u from Products u where u.firmCategoryId = ?1" )
	List< Products > findAllByCategoryId( int firmCategoryId );
}
