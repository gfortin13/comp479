package org.comp479.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.comp479.model.Index;

public class DictBlockWriter {
	
	public static void write(Index index, Integer dictId){
		//System.out.println("Writing dictionary to disk...");
		//create dir and file
		File file = new File("dict/block/block"+Integer.toString(dictId)+".dict");
		file.getParentFile().mkdirs();
		Writer writer;
		try {
			writer = new FileWriter(file);
			
			StringBuffer blockString = new StringBuffer();
			
			//for each term write print term and posting list on new line (format: word[1, 2, 3, 4])
			for(String term : index.sortEntries().keySet()){
				blockString.append(term+index.get(term).toString()+"\n");
					
            }
			//System.out.println(blockString.toString());
			
			writer.write(blockString.toString());
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
