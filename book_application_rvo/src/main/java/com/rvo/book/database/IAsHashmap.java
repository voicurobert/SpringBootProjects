package com.rvo.book.database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public interface IAsHashmap{
	
	public default HashMap< Object, Object > asHashMap(){
		HashMap< Object, Object > response = new HashMap<>();
		Method[] methods = this.getClass().getMethods();
		for( Method method : methods ){
			String methodName = method.getName();
			if( methodName.matches( "getClass" ) ){
				continue;
			}
			if( methodName.matches( "get(.*)" ) ){
				
				String key = methodName.split( "get" )[ 1 ];
				String first = String.valueOf( key.charAt( 0 ) );
				String newFirst = first.toLowerCase();
				String newKey = key.replace( first+"(.*)", newFirst );
				try{
					Object returnedObject = method.invoke( this );
					response.put( newKey, returnedObject );
				}catch( IllegalAccessException e ){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch( IllegalArgumentException e ){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch( InvocationTargetException e ){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return response;
	}
}
	