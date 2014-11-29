package org.comp479.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.comp479.model.Document;
import org.comp479.util.File2String;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class DocGathering {
	static Integer currentDocId = 0;
	static StringBuffer docIdString = new StringBuffer();

	//FIXME: return List<Document> and uncomment return statement when ready to use
	//public static void main(String[] args) {
	public static List<Document> gather() {
		File file = new File("docInfo/docIds.sgm");
		file.getParentFile().mkdirs();
		Writer writer;
		
		List<Document> docs = new ArrayList<Document>();
		
		listFilesForFolder(new File("collection"), docs);
		
		try {
			writer = new FileWriter(file);
			
			writer.write(docIdString.toString());
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return docs;
	}

	public static void listFilesForFolder(final File folder, List<Document> docs) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry, docs);
	        } else {
	        	String htmlContent = File2String.read(fileEntry);
	        	//NOTE: this is a Document from the external Jsoup Lib not the model class Document
	        	org.jsoup.nodes.Document docJsoup = Jsoup.parse(htmlContent);
	        	
	        	Elements h1Tags = docJsoup.select("h1");
	        	Elements pTags = docJsoup.select("p");
	        	
	        	//System.out.println(h1Tags.text() + " " + pTags.text());
	        	
	        	docIdString.append(++currentDocId + ":" + fileEntry.getPath().replace("collection\\", "") +"\n");
	        	//System.out.print(currentDocId + ":" + fileEntry.getPath().replace("collection\\", "") +"\n");
	        	
	        	docs.add(new Document(currentDocId, h1Tags.text() + " " + pTags.text()));
				
	        }
	        
	    }
	    
	}


}
