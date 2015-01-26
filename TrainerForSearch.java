package mp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.corpus.ObjectHandler;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Compilable;
import com.aliasi.util.Files;



public class TrainerForSearch {
	public static void searchTrainer(String input) throws IOException, ClassNotFoundException, SQLException {
		String currentDirectory = "C:\\Users\\AravindKumarReddy\\Documents\\NetBeansProjects\\MovieProphecy\\src\\java\\mp";
		File f = new File (currentDirectory + "\\classifier.txt");
		String text; 
		
		File s = new File (currentDirectory + "\\Tweets2.txt");
		FileReader fr = new FileReader(s);
		BufferedReader br = new BufferedReader(fr);
		
		int count=0;
		ArrayList<ArrayList<String>> finalArr = new ArrayList<ArrayList<String>>();
		
		while((text=br.readLine())!=null){
			String[] arr = text.split("\t");
			
			ArrayList<String> result = new ArrayList<String>();
			
			for(int i=0;i<arr.length;i++){
				if(!arr[i].equalsIgnoreCase(""))
					result.add(arr[i]);
			}
			
			finalArr.add(result);
		}
		
		if (!(f.exists() && !f.isDirectory())) {
			try {
				Trainer.trainModel();
			}catch (ClassNotFoundException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SentimentClassifier sc = new SentimentClassifier();
		for(int i=0;i<finalArr.size();i++){
			sc.classify(finalArr.get(i), input);
		}

	}
	
	public static void trainModel() throws IOException, ClassNotFoundException {
		File trainDir;
		String[] categories;
		LMClassifier classifier;
		String currentDirectory = "C:\\Users\\AravindKumarReddy\\Documents\\NetBeansProjects\\MovieProphecy\\src\\java\\mp";
		trainDir = new File(currentDirectory + "\\txt_sentoken");
		categories = trainDir.list();
		int nGram = 7; //the nGram level, any value between 7 and 12 works
		classifier = DynamicLMClassifier.createNGramProcess(categories,nGram);
		
		for (int i = 0; i < categories.length; i++) {
			String category = categories[i];
			Classification classification = new Classification(category);
			File file = new File(trainDir, categories[i]);
			File[] trainFiles = file.listFiles();
			for (int j = 0; j < trainFiles.length; j++) {
				File trainFile = trainFiles[j];
				String review = Files.readFromFile(trainFile, "ISO-8859-1");
				Classified classified = new Classified(review, classification);
				((ObjectHandler) classifier).handle(classified);
			}
			System.out.println("Current Folder: " + (i+1));
		}
		AbstractExternalizable.compileTo((Compilable) classifier, new File(currentDirectory + "\\classifier.txt"));
			
	}
}
