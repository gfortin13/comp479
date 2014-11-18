package org.comp479.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Document {
	
	Integer id;
	String content;
	List<String> tokens;
	
	public Document(Integer id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	// OLD CODE FOR REUTERS, DO NOT USE, YOU MAY REFER TO CODE FOR IDEAS
	/*
	//one reuters file contains multiple documents, returns a list of separated documents from a file
	public static List<Document> collectDocuments(String fileContent){
		//Pattern split in 7 groups, group 4 is doc content
		Pattern pattern = Pattern.compile("(?i)(<reuters.*?>)(.+?)(<body>)(.+?)(</body>)(.+?)(</reuters>)", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(fileContent);

		List<Document> docs = new ArrayList<Document>();
		
		while (matcher.find()) {
			//from group 1 of parsed doc, find the doc id
			Pattern idPattern = Pattern.compile("(?i)(newid=\")(.+)(\")", Pattern.DOTALL);
			Matcher idMatcher = idPattern.matcher(matcher.group(1));
			
			// get doc id from second group in pattern string
			Integer docId = null;
			if (idMatcher.find()) {
				docId = Integer.parseInt(idMatcher.group(2));
				//System.out.println(docId);
			}
			
			//create new doc from parsed id and body content
			docs.add(new Document(docId, matcher.group(4)));	
		}
		//System.out.println(docs.get(30).getId() + docs.get(30).getContent());
		
		return docs;
	}
	
	public void writeContent(){
		//create dir and file
		File file = new File("doc-bodies/doc"+id+".sgm");
		file.getParentFile().mkdirs();
		Writer writer;
		try {
			writer = new FileWriter(file);
			
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}


}
