package org.comp479.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class CollectionInfo {
	Integer docCount;
	double avgDocLength;
	
	public CollectionInfo(Integer docCount, double avg) {
		super();
		this.docCount = docCount;
		this.avgDocLength = avg;
	}
	
	public Integer getDocCount() {
		return docCount;
	}
	
	public void setDocCount(Integer docCount) {
		this.docCount = docCount;
	}
	
	public double getAvgDocLength() {
		return avgDocLength;
	}
	
	public void setAvgDocLength(double avgDocLength) {
		this.avgDocLength = avgDocLength;
	}
	
	public void writeInfo(){
		//create dir and file
		File file = new File("docInfo/collectioninfo.sgm");
		file.getParentFile().mkdirs();
		Writer writer;
		try {
			writer = new FileWriter(file);
			
			writer.write(docCount.toString()+":"+Double.toString(avgDocLength));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static CollectionInfo readInfo() {
		CollectionInfo colInfo = null;
		try {
			File file = new File("docInfo/collectioninfo.sgm");
	       FileReader reader = new FileReader(file);
	       char[] chars = new char[(int) file.length()];
	       reader.read(chars);
	       String content = new String(chars);
	       
	       String[] splitInfo = content.split(":");
	       Integer docCount = Integer.parseInt(splitInfo[0]);
	       double avg = Double.parseDouble(splitInfo[1]);
	       
	       colInfo = new CollectionInfo(docCount, avg);
	       
	       reader.close();
	   } catch (IOException e) {
	       e.printStackTrace();
	   }
		
		return colInfo;
	}
}
