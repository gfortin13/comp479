package org.comp479.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.comp479.model.CollectionInfo;
import org.comp479.model.Document;
import org.comp479.model.Index;
import org.comp479.model.PostingList;
import org.comp479.model.SingleIndex;
import org.comp479.model.TermFrequencyPair;
import org.comp479.util.File2String;
import org.comp479.util.Normalizer;
import org.comp479.util.Tokenizer;

public class SearchQuery {
	public static void main (String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter a search query (ex: George Bush):");
		String queryStr = keyboard.nextLine();
		
		//System.out.println(queryStr);
		
		CollectionInfo colInfo = CollectionInfo.readInfo();
		
		//treat query as a document
		Document queryDoc = new Document(0, queryStr);
		
		//tokenize query doc
		queryDoc.setTokens(Tokenizer.tokenizeDoc(queryDoc.getContent()));
	    //normalize query(doc) tokens
		queryDoc.setTokens(Normalizer.normalize(queryDoc));
		
		//sort tokens and return to simple string array
		Collections.sort(queryDoc.getTokens());
		List<String> tokens = queryDoc.getTokens();
		
		//for (String token : queryDoc.getTokens()){
			//System.out.println(token);
		//}
		
		//buffered dictionary reader
		File dictFile = new File("dict/final/final.dict");
		BufferedReader dictReader = new BufferedReader(new FileReader(dictFile));
		
		//buffered doc length reader
		File lengthFile = new File("docInfo/doclengths.sgm");
		BufferedReader lengthReader = new BufferedReader(new FileReader(lengthFile));
		
		//index for all found terms
		Index matchedTermIndex = new Index();
		
		//current querry token index to match
		int queryIndex = 0;
		
		//loop over each line from dict and set current line string
		String dictLine;
		while ((dictLine = dictReader.readLine()) != null && queryIndex < tokens.size()) {
			//retrieve term from term/posting list line
			String dictTerm = SingleIndex.blockStringToTerm(dictLine);
			
			//System.out.println(dictTerm+":"+tokens.get(queryIndex));
			//if dict term matches query token add to matched index and move to next query term (both sorted)
			if(dictTerm.equals(tokens.get(queryIndex))){
				PostingList dictPl = PostingList.blockStringToPL(dictLine);
				matchedTermIndex.put(dictTerm, dictPl);
				queryIndex++;
			}
		}
		
		HashMap<Integer, Double> docScores = new HashMap<>();
		Double k = 1.8;
		Double b = 0.6;
		
		Set<String> keys = matchedTermIndex.keySet();
        for(String key: keys){
            PostingList currentPl = matchedTermIndex.get(key);
            Integer df = currentPl.size();
            for(TermFrequencyPair pair : currentPl){
            	Integer docId = pair.getDocId();
            	Integer tf = pair.getTermFrequency();
            	Integer N = colInfo.getDocCount();
            	double avgL = colInfo.getAvgDocLength();
            	
            	//get word length from file
            	String lengthLine;
            	Integer fileDocId = 0;
            	Integer dL = 0;
        		while (!fileDocId.equals(docId) && (lengthLine = lengthReader.readLine()) != null){
        			String[] lengthLineSplit = lengthLine.split(":");
        			fileDocId = Integer.parseInt(lengthLineSplit[0]);
        			dL = Integer.parseInt(lengthLineSplit[1]);
        		}
            	Double score = (Math.log(N/df))*((k+1.0)*tf)/((k*(1.0-b)+b*(dL/avgL))+tf);
        		if(docScores.containsKey(docId)){
        			Double oldScore = docScores.get(docId);
        			docScores.replace(docId, oldScore + score);
        		}
        		else{
        			docScores.put(docId, score);
        		}
            }
        }
        Map sortedScores = sortByValue(docScores);
        Set<Integer> scoreKeys = sortedScores.keySet();
        
        Integer topDisplayed = 5;
        if (sortedScores.size() < topDisplayed){
        	topDisplayed = sortedScores.size();
        }
        
        System.out.println("Displaying top " + topDisplayed + " documents:");
        Iterator<Integer> it = scoreKeys.iterator();
        for(int i = 0; i < topDisplayed; i++){
        	Integer currentDocId = it.next();
        	//File docBodyFile = new File("doc-bodies/doc"+currentDocId+".sgm");
        	//String docBody = File2String.read(docBodyFile);
        	System.out.println("Doc id " + currentDocId + " body:\n");// + docBody);
        }
        
        /*for(Integer key: scoreKeys){
        	System.out.println("Doc id: " + key + " score: " + docScores.get(key));
        }*/
        
	}
	
	public static Map sortByValue(Map unsortedMap) {
		Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}
 
}
 
class ValueComparator implements Comparator {
 
	Map map;
 
	public ValueComparator(Map map) {
		this.map = map;
	}
 
	public int compare(Object keyA, Object keyB) {
		Comparable valueA = (Comparable) map.get(keyA);
		Comparable valueB = (Comparable) map.get(keyB);
		return valueB.compareTo(valueA);
	}
}

