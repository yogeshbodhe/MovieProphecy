package mp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class OAuth {

	public static void main(String[] args) throws TwitterException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String consumerKey, consumerSecret;
		AccessToken accessToken = null;
		
		consumerKey = "SrKp8ZOnn9zREI5PQVoMf5vET";
		consumerSecret = "bmH35YJZrXNjNVqgwRBcaVqmHCYltBfiNMnpJz5S8FDF7CbyTW";
		
		String currentDirectory = "C:\\Users\\AravindKumarReddy\\Documents\\NetBeansProjects\\MovieProphecy\\src\\java\\mp\\";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		FileOutputStream fos = new FileOutputStream(currentDirectory + "accessTokenDetails.txt");
		FileOutputStream fos2 = new FileOutputStream(currentDirectory + "accessToken.txt");
		PrintWriter pw = new PrintWriter(fos);
		ObjectOutputStream oos = new ObjectOutputStream(fos2);
		
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		RequestToken requestToken = twitter.getOAuthRequestToken();
		
		while(accessToken==null){
			System.out.println("Open the URL and authenticate with your credentials:\n" + requestToken.getAuthenticationURL());
			System.out.println("Enter PIN:");
			String pin = br.readLine();
			accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			pw.println("ID: " + twitter.verifyCredentials().getId());
			pw.println("Token: " + accessToken.getToken());
			pw.print("Secret: " + accessToken.getTokenSecret());
			oos.writeObject(accessToken);
		}
		
		pw.close();
		oos.close();
		
	}

}
