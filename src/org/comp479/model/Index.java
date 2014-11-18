package org.comp479.model;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class Index {
	
	Hashtable<String, PostingList> index = new Hashtable<>();
	
	public void add(String term, Integer DocId){
		//new terms
		if(!index.containsKey(term)){
			PostingList pl = new PostingList();
			TermFrequencyPair tfPair = new TermFrequencyPair(DocId, 1);
			pl.add(tfPair);
			
			index.put(term, pl);
		}
		//add docId to existing terms posting list
		else{
			if(!index.get(term).getLast().getDocId().equals(DocId)){
				TermFrequencyPair tfPair = new TermFrequencyPair(DocId, 1);
				index.get(term).add(tfPair);
				//System.out.println(index.get(term).getLast().getDocId().toString()+":"+DocId.toString());
			}
			else{
				index.get(term).getLast().increaseFrequency();
				System.out.println(index.get(term).getLast().toString());
			}
		}
	}
	
	//sort current hashtable as a treemap
	public Map<String, PostingList> sortEntries() {
        Map<String, PostingList> entriesSorted = new TreeMap<String, PostingList>();
        entriesSorted.putAll(index);
        return entriesSorted;
    }
	
	public PostingList get(String s){
		return index.get(s);
	}
	
	public boolean contains(String s){
		return index.contains(s);
	}
	
	public boolean containsKey(String s){
		return index.containsKey(s);
	}
	
	public void put(String s, PostingList pl){
		index.put(s, pl);
	}

	public Set<String> keySet() {
		return index.keySet();
	}
	
	public boolean isEmpty(){
		return index.isEmpty();
	}

}
