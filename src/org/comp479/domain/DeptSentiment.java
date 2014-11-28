package org.comp479.domain;

import java.util.Hashtable;

public class DeptSentiment {
	public static void main(String[] args) {
		Hashtable<String, Integer> deptSentiments = new Hashtable<String, Integer>();
		
		deptSentiments.put("bcee", 0);
		deptSentiments.put("computer-science-software-engineering", 0);
		deptSentiments.put("electrical-computer", 0);
		deptSentiments.put("eng-society", 0);
		deptSentiments.put("info-systems-eng", 0);
		deptSentiments.put("mechanical-industrial", 0);
		
		//TODO: WRITE THE CODE FOR THIS PSEUDOCODE
		/*
		 * foreach(line of "docInfo/docIds.sgm){ <-- use bufferedReader
		 * 		String[] s = split(line, ":");
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
