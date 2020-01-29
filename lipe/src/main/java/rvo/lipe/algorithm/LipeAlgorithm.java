package rvo.lipe.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import rvo.lipe.Log;
import rvo.lipe.database.User;
import rvo.lipe.facebook_request.FacebookRequestCreator;

/**
 * - Get female/male users from database as a candidate list. - Starting from
 * the facebook user, get facebook activities (pages he liked, movies that he
 * watched, etc). - For each candidate, get the same activities as current user
 * and compare them. Create a percentage from that comparison. - Sort the list
 * and return the list.
 * 
 * @author Robert
 *
 */
public class LipeAlgorithm{
	private List< User > allUsers;
	private List< User > candidates;
	private double score = 0d;
	private int dataSize = 0;
	private double dataCount = 0;
	private double pointsForScore = 0d;

	public LipeAlgorithm( List< User > allUsers ){
		this.allUsers = allUsers;
	}

	public String startAlgorithmForFacebookIdAndAccessToken( String facebookId, String accessToken ){
		score = 0;
		dataSize = 0;
		dataCount = 0;
		User facebookUser = getFacebookUserFromFacebookId( facebookId );
		findCandidatesForFacebookUser( facebookUser );
		FacebookRequestCreator facebookCreator = FacebookRequestCreator.getInstance();
		String facebookDataRequest = facebookCreator.computeFacebookDataRequest( facebookId, accessToken );
		Map< String, Object > facebookUserMapping = facebookCreator.getResponseAsJsonMapping( facebookDataRequest );
		for( User user: candidates ){
			String candidateFacebookRequest = facebookCreator.computeFacebookDataRequest( String.valueOf( user.getFacebookId() ), accessToken );
			Map< String, Object > candidateMapping = facebookCreator.getResponseAsJsonMapping( candidateFacebookRequest );
			compareMappingBetweenUserAndCandidate( facebookUserMapping, candidateMapping );
		}
		return "";
	}

	private User getFacebookUserFromFacebookId( String facebookId ){
		for( User user: allUsers ){
			if( String.valueOf( user.getFacebookId() ).equals( facebookId ) ){
				return user;
			}
		}
		return null;
	}

	private void findCandidatesForFacebookUser( User facebookUser ){
		for( User user: allUsers ){
			if( user.getFacebookId() != facebookUser.getFacebookId()&& !user.getGender().equals( facebookUser.getGender() ) ){
				candidates.add( user );
			}
		}
	}

	public void testMappings( String facebookId, String accessToken ){
		FacebookRequestCreator facebookCreator = FacebookRequestCreator.getInstance();
		String facebookDataRequest = facebookCreator.computeFacebookDataRequest( facebookId, accessToken );
		Map< String, Object > facebookUserMapping = facebookCreator.getResponseAsJsonMapping( facebookDataRequest );
		String testFacebookId = "1518693268170276";
		String testAccessToken = "EAAaZCF5ax16YBAHHh2ieddIVZBAyzKxLZAdyx1zNClYagtgYo1swcl7l9ENzAfy5ucj7fv8ZAeYnytULimG9lZBn2wRdod6z8Cp1qoytAs4T9qfcyTgC9drJdmgiU6CAZC4bB2SRLiwGdQwc5myQk9F7TPvGNfbZBGURUTYbkCv5zw2M0gpoTpLHoSj9UVD90e8HSnwiY5GogZDZD";
		String testFbRequest = facebookCreator.computeFacebookDataRequest( testFacebookId, testAccessToken );
		Map< String, Object > candidateUserMapping = facebookCreator.getResponseAsJsonMapping( testFbRequest );
		compareMappingBetweenUserAndCandidate( facebookUserMapping, candidateUserMapping );
	}

	@Autowired( required = false )
	@SuppressWarnings( "unchecked" )
	private void compareMappingBetweenUserAndCandidate( Map< String, Object > userMapping, Map< String, Object > candidateMapping ){
		Set< String > keys = userMapping.keySet();
		dataSize = keys.size();
		pointsForScore = dataSize / 100d;
		for( String key: keys ){
			Log.log( key );
			Object userObject = userMapping.get( key );
			Object candidateObject = candidateMapping.get( key );
			if( candidateObject == null ){
				continue;
			}
			switch( key ){
				case "education":
					compareEducationDataBetweenUserAndCandidate( (List< Map< String, Object > >) userObject,
							(List< Map< String, Object > >) candidateObject );
					break;
				case "favorite_teams":
					compareMultipleDataBetweenUserAndCandidate( (List< Map< String, Object > >) userObject,
							(List< Map< String, Object > >) candidateObject );
					break;
				case "inspirational_people":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "interested_in":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "tagged":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "music":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "television":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "political":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "relationship_status":
					// compareMultipleDataBetweenUserAndCandidate(
					// (ArrayList< Map< String, Object > >) ( (Map< String,
					// Object
					// >) userObject ).get( "data" ),
					// (ArrayList< Map< String, Object > >) ( (Map< String,
					// Object
					// >) candidateObject ).get( "data" ) );
					break;
				case "sports":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "movies":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "games":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "books":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "likes":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "favorite_athletes":
					compareMultipleDataBetweenUserAndCandidate( (ArrayList< Map< String, Object > >) userObject,
							(ArrayList< Map< String, Object > >) candidateObject );
					break;
				case "languages":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "quotes":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "tagged_places":
					// need redefined
					compareTaggedPlacesBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				case "groups":
					compareMultipleDataBetweenUserAndCandidate(
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) userObject ).get( "data" ),
							(ArrayList< Map< String, Object > >) ( (Map< String, Object >) candidateObject )
									.get( "data" ) );
					break;
				default:
					break;
			}
		}
		Log.log( dataCount );
		Log.log( dataSize );
		score = ( dataCount / dataSize ) * 100;
		String scoreString = String.valueOf( score ).substring( 0, 4 );
		Log.log( scoreString.concat( "%" ) );
	}

	private void compareEducationDataBetweenUserAndCandidate( List< Map< String, Object > > userObject, List< Map< String, Object > > candidateObject ){
		double pointPerElement = pointsForScore / userObject.size();
		for( int i = 0; i < candidateObject.size(); i++ ){
			if( i > userObject.size() ){
				break;
			}
			Map< String, Object > userElement = userObject.get( i );
			Map< String, Object > candidateElement = candidateObject.get( i );
			String userEducation = (String) userElement.get( "type" );
			String candidateEducation = (String) candidateElement.get( "type" );
			if( userEducation.equals( candidateEducation ) ){
				dataCount += pointPerElement;
			}
		}
	}

	private void compareMultipleDataBetweenUserAndCandidate( List< Map< String, Object > > userObject, List< Map< String, Object > > candidateObject ){
		double pointPerElement = pointsForScore / userObject.size();
		for( int i = 0; i < candidateObject.size(); i++ ){
			Map< String, Object > candidateElement = candidateObject.get( i );
			String candidateString = (String) candidateElement.get( "name" );
			for( int j = 0; j < userObject.size(); j++ ){
				Map< String, Object > userElement = userObject.get( j );
				String userString = (String) userElement.get( "name" );
				if( userString.matches( Pattern.quote( candidateString ) ) ){
					dataCount += pointPerElement;
				}
			}
		}
	}

	@SuppressWarnings( "unchecked" )
	private void compareTaggedPlacesBetweenUserAndCandidate( List< Map< String, Object > > userObject, List< Map< String, Object > > candidateObject ){
		double pointPerElement = pointsForScore / userObject.size();
		for( int i = 0; i < candidateObject.size(); i++ ){
			Map< String, Object > candidateElement = (Map< String, Object >) candidateObject.get( i ).get( "place" );
			String candidateString = (String) candidateElement.get( "name" );
			for( int j = 0; j < userObject.size(); j++ ){
				Map< String, Object > userElement = (Map< String, Object >) userObject.get( j ).get( "place" );
				String userString = (String) userElement.get( "name" );
				if( userString.matches( Pattern.quote( candidateString ) ) ){
					dataCount += pointPerElement;
				}
			}
		}
	}
}
