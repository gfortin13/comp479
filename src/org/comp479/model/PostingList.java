package org.comp479.model;

import java.util.LinkedList;
import java.util.StringTokenizer;

public class PostingList extends LinkedList<TermFrequencyPair> {
	
	//parse block string back to posting list
	public static PostingList blockStringToPL(String s){
		PostingList pl = new PostingList();

		//posting list content located between [] (ex: [1, 2, 3, 4]
        int beginIndex = s.indexOf('[');
        int endIndex = s.indexOf(']');

        String postingsListArray = s.substring(beginIndex + 1, endIndex);
        postingsListArray = postingsListArray.replace(",", "");

        //split numbers with tokenizer then add to posting list
        StringTokenizer st = new StringTokenizer(postingsListArray);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            String[] tokenPair = token.split(":");
            int docId = Integer.parseInt(tokenPair[0]);
            int tf = Integer.parseInt(tokenPair[1]);
            TermFrequencyPair tfPair = new TermFrequencyPair(docId, tf);
            pl.add(tfPair);
        }

        return pl;
    }

	//not used anymore
	public static String toString(PostingList pl){
		String s = "[";
		String delim = "";
		for(TermFrequencyPair tfPair : pl){
			s += delim + tfPair.toString();
			delim = ", ";
		}
		s += "]";
		
		return s;
	}
	
	@Override
	public String toString(){
		String s = "[";
		String delim = "";
		for(TermFrequencyPair tfPair : this){
			s += delim + tfPair.toString();
			delim = ", ";
		}
		s += "]";
		
		return s;
	}
	
	
}
