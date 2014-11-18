package org.comp479.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class File2String {
	public static String read(File file){
		String content = null;
		try {
	       FileReader reader = new FileReader(file);
	       char[] chars = new char[(int) file.length()];
	       reader.read(chars);
	       content = new String(chars);
	       reader.close();
	   } catch (IOException e) {
	       e.printStackTrace();
	   }
		
		return content;
	}
}
