package rvo.lipe.facebook_request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import rvo.lipe.Log;



public class FacebookRequestCreator{

	private static final FacebookRequestCreator instance = new FacebookRequestCreator();
	private JsonParser jsonParser = JsonParserFactory.getJsonParser();
	private static final String GRAPH_URL = "https://graph.facebook.com";
	private static final String GRAPH_VERSION = "v2.11";
	private static final String FIELDS = "fields";
	private static final String QUESTION_MARK_SEP = "?";
	private static final String BACKSLASH_SEP = "/";
	private static final String EQUALS_SEP = "=";
	private static final String AND_SEP = "&";
	private static final String LIMIT = ".limit(10000)";
	
	private FacebookRequestCreator(){

	}

	public static FacebookRequestCreator getInstance(){
		return instance;
	}

	private String createBasicRequest( String facebookId ){
		return GRAPH_URL + BACKSLASH_SEP + GRAPH_VERSION + BACKSLASH_SEP + facebookId + QUESTION_MARK_SEP + FIELDS + EQUALS_SEP;
	}

	public String computeGenderFacebookRequest( String facebookId, String accessToken ){
		return createBasicRequest( facebookId ) + "gender" + AND_SEP + "access_token=" + accessToken;
	}

	public List< String > getFacebookDataStringNodes(){
		List< String > nodes = new ArrayList< String >();
		nodes.add( "education" );
		nodes.add( "favorite_teams" );
		nodes.add( "hometown" );
		nodes.add( "inspirational_people" );
		nodes.add( "interested_in" );
		nodes.add( "tagged" );
		nodes.add( "music" );
		nodes.add( "television" );
		nodes.add( "political" );
		nodes.add( "relationship_status" );
		nodes.add( "sports" );
		nodes.add( "movies" );
		nodes.add( "games" );
		nodes.add( "books" );
		nodes.add( "likes" );
		nodes.add( "favorite_athletes" );
		nodes.add( "languages" );
		nodes.add( "quotes" );
		nodes.add( "tagged_places" );
		nodes.add( "picture" );
		nodes.add( "groups" );
		return nodes;
	}
	
	public String computeFacebookDataRequest( String facebookId, String accessToken ){
		StringBuilder facebookDataRequest = new StringBuilder();
		facebookDataRequest.append( createBasicRequest( facebookId ) );
		List< String > nodes = this.getFacebookDataStringNodes();
		for( String node : nodes ){
			if( node.equals( "tagged" ) || node.equals( "music" ) || node.equals( "television" ) ||
				node.equals( "movies" ) || node.equals( "games" ) || node.equals( "likes" ) || node.equals( "tagged_places" ) ){
				if( nodes.indexOf( node ) == nodes.size() - 1 ){
					facebookDataRequest.append( node ).append( LIMIT );
				}else{
					facebookDataRequest.append( node ).append( LIMIT ).append( "," );
				}
			}else{
				if( nodes.indexOf( node ) == nodes.size() - 1 ){
					facebookDataRequest.append( node );
				}else{
					facebookDataRequest.append( node ).append( "," );
				}
			}
		}
		facebookDataRequest.append( AND_SEP ).append( "access_token=" ).append( accessToken );
		Log.log( facebookDataRequest.toString() );
		return facebookDataRequest.toString();
	}

	public String executeUrl( String stringUrl ){
		System.out.println( stringUrl );
		try{
			InputStream inputStream = getInputStreamFromUrl( stringUrl );
			BufferedReader in = new BufferedReader( new InputStreamReader( inputStream ) );
			StringBuffer string = new StringBuffer();
			String line;
			while( ( line = in.readLine() ) != null ){
				string.append( line );
			}
			in.close();
			return string.toString();
		}catch( MalformedURLException e ){
			e.printStackTrace();
		}catch( IOException e ){
			e.printStackTrace();
		}
		return "";
	}

	public Map< String, Object > getResponseAsJsonMapping( String response ){
		return jsonParser.parseMap( executeUrl( response ) );
	}

	private InputStream getInputStreamFromUrl( String stringUrl ){
		try{
			URL url = new URL( stringUrl );
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			return connection.getInputStream();
		}catch( MalformedURLException e ){
			e.printStackTrace();
		}catch( IOException e ){
			e.printStackTrace();
		}
		return null;
	}

}
