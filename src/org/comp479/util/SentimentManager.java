package org.comp479.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentimentManager {
	//Hashmap to hold the term and sentiment value pairs
	Map <String, Integer> sentimentValues;
	
	public SentimentManager() throws IOException{
		//Hashmap to hold the term and sentiment value pairs
		sentimentValues = new HashMap<String, Integer>();
		
		//Buffered Reader which will read the sentiment values file
		BufferedReader bufferedReader = new BufferedReader(new FileReader("Sentiments/AFINN-111.txt"));
		
		//For every line in the sentiment values file, store the term as the key and the sentiment value as the value in the hashmap
		String nextLine = bufferedReader.readLine();
		while (nextLine != null){
			sentimentValues.put(nextLine.substring(0, nextLine.indexOf("\t")), Integer.parseInt(nextLine.substring(nextLine.indexOf("\t") + 1)));
			nextLine = bufferedReader.readLine();
		}
		
		//Close the reader
		bufferedReader.close();		
	}

	public double getSentimentScore(List<String> tokenList){
		//Start at a score of zero
		int totalScore = 0;
		
		//For each token, check if the term exists within the hashmap. if it does add its value to the current total
		for (String token : tokenList){
			if (sentimentValues.containsKey(token)){
				totalScore += sentimentValues.get(token);	
			}else{	
			}
			
		}
		
		//Return the sentiment score of the input string
		return (totalScore/Math.sqrt(tokenList.size()));
	}

	
public static void writeSentimentScores(HashMap <Integer, Double> docSentimentMap) throws IOException{
		
		ArrayList<String> fileLines = new ArrayList<String>();
		
		//Transfer file contents to memory
		File oldFile = new File("docInfo/docIds.sgm");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(oldFile));
		
		String nextLine = bufferedReader.readLine();
		while (nextLine != null){
			fileLines.add(nextLine);
			nextLine = bufferedReader.readLine();
		}
		bufferedReader.close();

		//For each line, find the sentiment value within the map and append it to the end
		File newFile = new File("docInfo/docIdsTemp.sgm");
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
		
		for (String currentLine : fileLines)
		{
			int docId = Integer.parseInt(currentLine.substring(0, currentLine.indexOf(":")));
			bufferedWriter.write(currentLine + ":" + docSentimentMap.get(docId) + "\n");
		}
		
		bufferedWriter.close();
			
		//Delete the old file and rename the new file to that of the old
		oldFile.delete();
		newFile.renameTo(oldFile);
		
	}
}
