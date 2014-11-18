package org.comp479.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.comp479.model.PostingList;
import org.comp479.model.SingleIndex;
import org.comp479.model.TermFrequencyPair;

public class BlockMerger {
	public static void execute() throws Exception{
		
		//creating final dict and dir
		File dictFile = new File("dict/final/final.dict");
		dictFile.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(dictFile);
        OutputStreamWriter finalDictWriter = new OutputStreamWriter(fos);
		
		//list of each dict block names
		File dir = new File("dict/block");
		String[] files = dir.list();
		
		//creating buffered reader for each temp dict block
		ArrayList<BufferedReader> dictReaders = new ArrayList<BufferedReader>();
		for (String file : files) {
			//System.out.println(file);
			dictReaders.add(new BufferedReader(new FileReader(new File(dir + "/" + file))));
        }
		
		//hold one index entry in a simple object rather than hashtable, is replaced each time buffered read advances
		ArrayList<SingleIndex> currentBlockEntries = new ArrayList<SingleIndex>(dictReaders.size());
		//get first index line for each dict block
		for (int i = 0; i < dictReaders.size(); i++) {
			currentBlockEntries.add(i, new SingleIndex(dictReaders.get(i).readLine()));
        }
		
		//loop over each dict block removing them when they reach EOF
		while(dictReaders.size() > 0){
			//position of the dict block(s) with smallest word
			ArrayList<Integer> smallestBlockEntryIndexes = new ArrayList<Integer>();

			//instantiate first block with smallest word
			String smallestWord = currentBlockEntries.get(0).getTerm();
			smallestBlockEntryIndexes.add(0);
            
            //find smallest word
            for (int i = 1; i < currentBlockEntries.size(); i++) {
                String currentWord = currentBlockEntries.get(i).getTerm();
                //if smaller word is found clear current array
                if (currentWord.compareTo(smallestWord) < 0) {
                	smallestBlockEntryIndexes.clear();
                	smallestBlockEntryIndexes.add(i);
                    smallestWord = currentWord;
                //if same word add to array (to merge posting lists further)
                } else if (currentWord.compareTo(smallestWord) == 0) {
                	smallestBlockEntryIndexes.add(i);
                }
            }

            //creating new posting list to merge and writing current smallest term
            PostingList mergedPostingList = new PostingList();
            finalDictWriter.write(currentBlockEntries.get(smallestBlockEntryIndexes.get(0)).getTerm());

            //loop backwards to remove biggest indexes first for remove() repacking
            for (int i = smallestBlockEntryIndexes.size()-1; i >= 0; i--) {
                int currentSmallestIndex = smallestBlockEntryIndexes.get(i);
                
                //System.out.println(i+":"+currentSmallestIndex);
                //merge posting lists (and sort)
                mergedPostingList.addAll(currentBlockEntries.get(currentSmallestIndex).getPl());
                //Collections.sort(mergedPostingList.toArray(), new CustomComparator());
                Collections.sort(mergedPostingList, new Comparator<TermFrequencyPair>(){
                    public int compare(TermFrequencyPair o1, TermFrequencyPair o2) {
                        return o1.getDocId().compareTo(o2.getDocId());
                    }
                });

                //read next line from smallest block index
                String newCurrentBlockLine = dictReaders.get(currentSmallestIndex).readLine();
                //remove current block/reader/smallest index if reach EOF
                if (newCurrentBlockLine == null) {
                	//System.out.println("Removed="+i+":"+currentSmallestIndex);
                	currentBlockEntries.remove(currentSmallestIndex);
                	dictReaders.get(currentSmallestIndex).close();
                	dictReaders.remove(currentSmallestIndex);
                	smallestBlockEntryIndexes.remove(i);
                //replace appropriate block entry with new line
                } else {
                    SingleIndex newCurrentBlockEntry = new SingleIndex(newCurrentBlockLine);
                    currentBlockEntries.set(currentSmallestIndex, newCurrentBlockEntry);
                }
            }
            //append posting list to term on same dict line
            finalDictWriter.write(mergedPostingList.toString() + "\n");
        }
		finalDictWriter.close();
		
		//add deleting temp dict blocks..?
	}
}
