package org.comp479.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentimentEvaluator {
	//Hashmap to hold the term and sentiment value pairs
	Map <String, Integer> sentimentValues;
	
	public SentimentEvaluator() throws IOException{
		//Hashmap to hold the term and sentiment value pairs
		sentimentValues = new HashMap<String, Integer>();
		
		//Buffered Reader which will read the sentiment values file
		BufferedReader bufferedReader = new BufferedReader(new FileReader("Sentiments/AFINN-111.txt"));
		
		//For every line in the sentiment values file, store the term as the key and the sentiment value as the value in the hashmap
		String nextLine = bufferedReader.readLine();
		while (nextLine != null){
			sentimentValues.put(nextLine.substring(0, nextLine.indexOf("\t")), Integer.parseInt(nextLine.substring(nextLine.indexOf("\t") + 1)));			nextLine = bufferedReader.readLine();
		}
		
		//Close the reader
		bufferedReader.close();		
	}

	public int getSentimentScore(String inputString){
		//Start at a score of zero
		int totalScore = 0;
		
		//Tokenize the string input
		List<String> tokenList = Tokenizer.tokenizeDoc(inputString);
		
		//For each token, check if the term exists within the hashmap. if it does add its value to the current total
		for (String token : tokenList){
			if (sentimentValues.containsKey(token)){
				//System.out.println("\"" + token + "\" is worth " + sentimentValues.get(token) + " points");
				totalScore += sentimentValues.get(token);
				//System.out.println("New Total: " + totalScore + "\n");
			}else{
				//System.out.println("\"" + token + "\" is not in the collection.");
			}
			
		}
		
		//Return the sentiment score of the input string
		return totalScore;
	}

}
