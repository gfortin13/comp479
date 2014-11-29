package org.comp479.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class DeptSentiment {
	public static void main(String[] args) throws Exception {
		File file = new File("docInfo/docIds.sgm");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		Hashtable<String, Double> deptSentiments = new Hashtable<String, Double>();
		
		deptSentiments.put("bcee", 0.0);
		deptSentiments.put("computer-science-software-engineering", 0.0);
		deptSentiments.put("electrical-computer", 0.0);
		deptSentiments.put("eng-society", 0.0);
		deptSentiments.put("info-systems-eng", 0.0);
		deptSentiments.put("mechanical-industrial", 0.0);
		deptSentiments.put("comp-sci", 0.0);
		
		String line;
		while((line = reader.readLine()) != null){
			String[] segment = line.split(":");
			Set<String> keySet = deptSentiments.keySet();
		    Iterator<String> keys = keySet.iterator();
			  
		    // display search result
		    while (keys.hasNext()) {
		    	String key = keys.next();
		    	if(segment[1].contains(key)){
		    		Double sentValue = 0.0;
		    		if(!segment[2].equals("NaN")){
		    			sentValue =  Double.parseDouble(segment[2]);
		    		}
		    		deptSentiments.replace(key, deptSentiments.get(key) + sentValue);
		    	}
		    }
		}
		
		System.out.println(deptSentiments.toString());
		reader.close();
		
		//TODO: WRITE THE CODE FOR THIS PSEUDOCODE
		/*
		 * foreach(line of "docInfo/docIds.sgm){ <-- use bufferedReader
		 * 		String[] s = split(line, ":"); **line looks like: docId:URL:sentiment so they split in 0:docId, 1:URL, 2:Sentiment
		 * 		foreach(key from deptSentiments){
		 * 			if s[1].contains(key){
		 * 				deptSentiment.replace(key, deptSentiment.get(key)+s[2].toInt) 
		 * 			}
		 * 		}
		 * }
		 * 
		 * Then find a way to answer and output all the questions based on the sentiments you have for each department
		 * 
		 */
	}
}
