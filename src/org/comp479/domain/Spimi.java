package org.comp479.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.comp479.model.CollectionInfo;
import org.comp479.model.Document;
import org.comp479.model.Index;
import org.comp479.util.DictBlockWriter;
import org.comp479.util.Normalizer;
import org.comp479.util.Tokenizer;

public class Spimi {

	public static void execute() throws Exception{
		//List<File_Reader> files = new ArrayList<File_Reader>();
    	List<Document> documents = new ArrayList<Document>();
    	
    	//creating final dict and dir
		File lengthFile = new File("docInfo/doclengths.sgm");
		lengthFile.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(lengthFile);
        OutputStreamWriter lengthFileWriter = new OutputStreamWriter(fos);
    	
    	//first empty index
    	Index index = new Index();

    	Integer docCount = 1;
    	Integer dictCount = 0;
    	
    	documents = DocGathering.gather();
    	//first pass to collect the ~19000 docs (ignores bodyless docs)
    	/*for (int i=0; i <= 21; i++){
    		File file = new File("reuters21578/reut2-" + String.format("%03d" ,i) + ".sgm"); //for ex foo.txt
    		
    		// for each loop pass get all documents per single file
    		//documents.addAll(Document.collectDocuments(File_Reader.read(file)));
    	}*/
    	
    	CollectionInfo colInfo = new CollectionInfo(documents.size(), 0);
    	Integer totalTokens = 0;
	  	
    	//SPIMI algorithm
    	for (Document doc : documents) {
    		//doc.writeContent();
    		//tokenize each document body
    	    doc.setTokens(Tokenizer.tokenizeDoc(doc.getContent()));
    	    //normalize tokens of each doc
    	    doc.setTokens(Normalizer.normalize(doc));
    	    
    	    totalTokens += doc.getTokens().size();
    	    
    	    //generate inverted index for each term
    	    for(String term : doc.getTokens()){
    	    	index.add(term, doc.getId());
    	    }
    	    //limit single index to 1000 docs
    	    if(docCount++ % 1000 == 0){
    	    	//Write block to disk
    	    	DictBlockWriter.write(index, dictCount++);
    	    	//System.out.println(index.sortEntries().toString());
    	    	
    	    	//Clear current index
    	    	index = new Index();
    	    }
    	    lengthFileWriter.write(doc.getId().toString() + ":" + Integer.toString(doc.getTokens().size()) + "\n");
    	}
    	lengthFileWriter.close();
    	
    	colInfo.setAvgDocLength(totalTokens/colInfo.getDocCount());
    	colInfo.writeInfo();
    	//after all passes if last index has elements write block to disk
    	if(!index.isEmpty()){
    		//Write block to disk
	    	DictBlockWriter.write(index, dictCount++);
	    	//System.out.println(index.sortEntries().toString());
	    	
	    	//Clear current index
	    	index = new Index();
    	}

	}
}
