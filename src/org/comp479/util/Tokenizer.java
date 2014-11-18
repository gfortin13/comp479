package org.comp479.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Tokenizer {
    
    public static List<String> tokenizeDoc(String doc){
    	List<String> tokens = new ArrayList<String>();
    	
    	// included delimiter: end line, /, space
    	StringTokenizer st = new StringTokenizer(doc, "\n/ ");
        while (st.hasMoreTokens()) {
            tokens.add(st.nextToken());
        }
    	return tokens;
    }

}

