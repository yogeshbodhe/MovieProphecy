package mp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class SearchTweets {

	public static String search(String input) throws IOException, ClassNotFoundException, TwitterException, SQLException {
		// TODO Auto-generated method stub

		//Twitter twitter = TwitterFactory.getSingleton();
		
		String consumerKey = "SrKp8ZOnn9zREI5PQVoMf5vET";
		String consumerSecret = "bmH35YJZrXNjNVqgwRBcaVqmHCYltBfiNMnpJz5S8FDF7CbyTW";
		
                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret);
                
		String currentDirectory = "C:\\Users\\AravindKumarReddy\\Documents\\NetBeansProjects\\MovieProphecy\\src\\java\\mp";
		
		FileInputStream fis = new FileInputStream(currentDirectory + "\\accessToken.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		FileOutputStream fos = new FileOutputStream(currentDirectory + "\\Tweets2.txt");
		PrintWriter pw = new PrintWriter(fos);
		
		AccessToken accessToken = (AccessToken)ois.readObject();
		ois.close();
		
                cb.setOAuthAccessToken(accessToken.toString());
                TwitterFactory tf = new TwitterFactory(cb.build());
                Twitter twitter = tf.getInstance();
		//twitter.setOAuthConsumer(consumerKey, consumerSecret);
		twitter.setOAuthAccessToken(accessToken);
		
		Query query = new Query();
		query.setQuery(input);
		query.setSince("2012-08-01");
		QueryResult result;
		int counter=0;
		try{
			do{
				result = twitter.search(query);
				counter++;
                                List<Status> tweets = result.getTweets();
				
				for(Status tweet:tweets){
					if(!tweet.isRetweet() && !tweet.isRetweeted() && tweet.getLang().equalsIgnoreCase("en"))
						pw.println("@" + tweet.getUser().getScreenName() + "\t" + tweet.getCreatedAt() + "\t" + tweet.getUser().getLocation() + "\t" + tweet.getText());
				}
				
			}while(counter<=60 && (query=result.nextQuery())!=null);
                        pw.close();
                        TrainerForSearch tfs = new TrainerForSearch();
                        tfs.searchTrainer(input);
		}catch(TwitterException te){
			if(te.getErrorCode()==88){
				//System.out.println("Rate limit exceeded");
				//System.out.println(te.getMessage());
				//System.out.println(te.getRateLimitStatus());
                            //TrainerForSearch tfs = new TrainerForSearch();
                            //tfs.searchTrainer(input);
			}
		}
		
                return "0";
	}

}
