package rvo.lipe;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rvo.lipe.facebook_request.FacebookRequestCreator;
import rvo.lipe.algorithm.LipeAlgorithm;
import rvo.lipe.database.User;
import rvo.lipe.database.UserRepository;

@RestController
public class LipeRest{
	@Autowired
	private UserRepository userRepository;
	// http get example:
	// https://graph.facebook.com/v2.9/1736110509740203?fields=gender&access_token=EAAaZCF5ax16YBAA6zssdsqvpljBtO0GGHK84zfptbn75DQKRXFZARoZCtFOVAfzQWKFc6xqfZBWADAZABAl0SS0zft6tUVyaBatovtIDSGX3eUBWVhvMuRaGTfmbDnfE6VleyB1Q2EvI0NzcFpvYQroGNNMZCfBC9fLXgffn8jmPOePnzi2Yqkeo5VNF6TJotWZAlzdhWodLQZDZD
	
	
	
	@RequestMapping( "/insertUser" )
	public void insertUser( @RequestParam String facebookId, @RequestParam String accessToken ){
		User user = new User();
		user.setFacebookId( Long.valueOf( facebookId ) );
		String gender = getGenderFromFacebookId( facebookId, accessToken );
		user.setGender( gender );
		user.setAccessToken( accessToken );
		userRepository.save( user );
	}
	
	@RequestMapping( "/updateAccessTokenForFacebookId" )
	public void updateAccessTokenForFacebookId( @RequestParam String accessToken, @RequestParam String facebookId ){
		List< User > users = new ArrayList<>();
		userRepository.findAll().forEach( users :: add );
		for( User user : users ){
			if( user.getFacebookId() == Long.valueOf( facebookId ) && !user.getAccessToken().equals( accessToken ) ){
				user.setAccessToken( accessToken );
				userRepository.save( user );
			}
		}
	}
	
	private String getGenderFromFacebookId( String facebookId, String accessToken ){
		String request = FacebookRequestCreator.getInstance().computeGenderFacebookRequest( facebookId, accessToken );
		String response = FacebookRequestCreator.getInstance().executeUrl( request );
		JsonParser jsonParser = JsonParserFactory.getJsonParser();
		Map< String, Object > mapping = jsonParser.parseMap( response );
		String gender = String.valueOf( mapping.get( "gender" ) );
		if( gender.equals( "male" ) ){
			return "M";
		}else if( gender.equals( "female" ) ){
			return "F";
		}
		return null;
	}
	
	@RequestMapping( "/matchingFacebookCandidates" )
	public void findMatchingFacebookCandidatesFromFacebookId( String facebookId, String accessToken ){
		List< User > users = new ArrayList<>();
		userRepository.findAll().forEach( users :: add );
		LipeAlgorithm lipeAlgorithm = new LipeAlgorithm( users );
		lipeAlgorithm.startAlgorithmForFacebookIdAndAccessToken( facebookId, accessToken );
	}
	
	@RequestMapping( "/facebookData" )
	public String facebookData( @RequestParam String facebookId, @RequestParam String accessToken ){
		String request = FacebookRequestCreator.getInstance().computeFacebookDataRequest( String.valueOf( facebookId ), accessToken );
		return FacebookRequestCreator.getInstance().executeUrl( request );
		
	}
	
	@RequestMapping( "/testMapping" )
	public String testMapping( @RequestParam String facebookId, @RequestParam String accessToken ){
		LipeAlgorithm lipeAlgorithm = new LipeAlgorithm( new ArrayList<>() );
		lipeAlgorithm.testMappings( facebookId, accessToken );
		return "";
	}
	
	@RequestMapping( "/test" )
	public String test( ){
		return userRepository.findAll().toString();
	}
}
