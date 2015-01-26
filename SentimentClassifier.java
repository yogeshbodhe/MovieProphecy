package mp;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;


public class SentimentClassifier {
	String[] categories;
	LMClassifier classifier;
	
	public SentimentClassifier() {
		try {
			String currentDirectory = "C:\\Users\\AravindKumarReddy\\Documents\\NetBeansProjects\\MovieProphecy\\src\\java\\mp";
			classifier = (LMClassifier) AbstractExternalizable.readObject(new File(currentDirectory + "\\classifier.txt"));
			categories = classifier.categories();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void classify(ArrayList<String> textData, String input) throws ClassNotFoundException, SQLException {
		ConditionalClassification classification = null;
		if(textData.size()!=0){
			classification = classifier.classify(textData.get(textData.size()-1));
			//System.out.println(classification.bestCategory()  + "\t" + text);
			String polarity = classification.bestCategory();
			String tweetTextLC = new String();
			tweetTextLC = textData.get(textData.size()-1);//.toLowerCase();
			if(input!=null){
                            textData.add(input);
                        }
                        else{
                        String keywords[] = {"thehungergamesmockingjay", "theimitationgame", "vhsviral", "foxcatcher", "dumbanddumberto", "intersteller", "bighero6", "thetheoryofeverything", "dumbanddumberto", "nightcrawler", "abcsofdeath2", "beforeigotosleep", "godthefather", "johnwick", "citizenfour", "forcemajeure", "thebestofme", "thebookoflife", "birdman", "listenupphilip", "campxray", "thetaleofprincesskaguya", "themazerunner", "guardiansofthegalaxy", "draculauntold", "annabelle", "transformersageofextinction", "teenagemutantninjaturtles", "xmendaysoffuturepast", "thejudge", "gonegirl", input};
			boolean flag=false;
			for(int i=0;i<keywords.length;i++){
				if(tweetTextLC.contains(keywords[i])){
					textData.add(keywords[i]);
					flag=true;
					break;
				}
			}
			if(!flag){
				textData.add("Movie name not present");
			}
                        }
			textData.add(polarity);
			if(textData.size()>=5){
				//System.out.println(textData);
				WriteToDB.writeData(textData);
			}
			//return classification.bestCategory();
		}
	}
	
}
