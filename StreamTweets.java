package mp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;


public class StreamTweets {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		
		String currentDirectory = "C:\\Users\\AravindKumarReddy\\Documents\\NetBeansProjects\\MovieProphecy\\src\\java\\mp";
		
		FileInputStream fis = new FileInputStream(currentDirectory + "\\accessToken.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		FileOutputStream fos = new FileOutputStream(currentDirectory + "\\Tweets.txt", true);
		PrintWriter pw = new PrintWriter(fos, true);
		
		AccessToken accessToken = (AccessToken)ois.readObject();
		ois.close();
		
		String consumerKey = "SrKp8ZOnn9zREI5PQVoMf5vET";
		String consumerSecret = "bmH35YJZrXNjNVqgwRBcaVqmHCYltBfiNMnpJz5S8FDF7CbyTW";
		
		twitterStream.setOAuthConsumer(consumerKey, consumerSecret);
		twitterStream.setOAuthAccessToken(accessToken);
		
		//twitterStream.setOAuthConsumer(accessToken.getToken(), accessToken.getTokenSecret());;
		
		StatusListener listener = new StatusListener() {
			
			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				ex.printStackTrace();
			}
			
			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStatus(Status status) {
				// TODO Auto-generated method stub
				if(!status.isRetweet() && !status.isRetweeted()){
					pw.println("@" + status.getUser().getScreenName() + "\t" + status.getCreatedAt()+ "\t" + status.getUser().getLocation() + "\t" + status.getText());
				}
			}
			
			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		FilterQuery filter = new FilterQuery();
		String keywords[] = {"thehungergamesmockingjay", "theimitationgame", "vhsviral", "foxcatcher", "dumbanddumberto", "intersteller", "bighero6", "thetheoryofeverything", "nightcrawler", "abcsofdeath2", "beforeigotosleep", "godthefather", "johnwick", "citizenfour", "forcemajeure", "thebestofme", "thebookoflife", "birdman", "listenupphilip", "campxray", "thetaleofprincesskaguya", "themazerunner", "guardiansofthegalaxy", "draculauntold", "annabelle", "transformersageofextinction", "teenagemutantninjaturtles", "xmendaysoffuturepast", "thejudge", "gonegirl"};
		filter.track(keywords);
		String languages[] = {"en"};
		filter.language(languages);
		
		twitterStream.addListener(listener);
		twitterStream.sample();
		twitterStream.filter(filter);
	}

}
