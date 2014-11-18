package org.comp479.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.comp479.model.Document;
import org.tartarus.snowball.*;

public class Normalizer {
	static SnowballStemmer stemmer = null;
	
	// undesired special characters for tokens
	private static final Pattern UNDESIRABLES = Pattern.compile("[\\[\\]\"(){}'*_,.;:!?<>%$&#\\+\\-\\=1234567890\t]");
	
	//list of stop words to remove
	public static List<String> stopWordList = Arrays.asList(
		"without", "see", "unless", "due", "also", "must", "might", "like", "]", "[", "}", "{", "<", ">", "?", "\\", "/", ")", "(", "will", "may", "can", "much", "every", "the", "in", "other", "this", "the", 
		"many", "any", "an", "or", "for", "in", "an", "an ", "is", "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren’t", "as", "at", "be", "because", "been", 
		"before", "being", "below", "between", "both", "but", "by", "can’t", "cannot", "could",
		"couldn’t", "did", "didn’t", "do", "does", "doesn’t", "doing", "don’t", "down", "during", "each", "few", "for", "from", "further", "had", "hadn’t", "has", "hasn’t", "have", "haven’t", "having",
		"he", "he’d", "he’ll", "he’s", "her", "here", "here’s", "hers", "herself", "him", "himself", "his", "how", "how’s", "i ", " i", "i’d", "i’ll", "i’m", "i’ve", "if", "in", "into", "is",
		"isn’t", "it", "it’s", "its", "itself", "let’s", "me", "more", "most", "mustn’t", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "ought", "our", "ours", "ourselves",
		"out", "over", "own", "same", "shan’t", "she", "she’d", "she’ll", "she’s", "should", "shouldn’t", "so", "some", "such", "than",
		"that", "that’s", "their", "theirs", "them", "themselves", "then", "there", "there’s", "these", "they", "they’d", "they’ll", "they’re", "they’ve",
		"this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn’t", "we", "we’d", "we’ll", "we’re", "we’ve",
		"were", "weren’t", "what", "what’s", "when", "when’s", "where", "where’s", "which", "while", "who", "who’s", "whom",
		"why", "why’s", "with", "won’t", "would", "wouldn’t", "you", "you’d", "you’ll", "you’re", "you’ve", "your", "yours", "yourself", "yourselves"
	);
	//transfer simple array to ArrayList to utilize contains function (out dated)
	/*new ArrayList<String>(){{
		for (String stopWord : stopWords){
			add(stopWord);
		}
	}};*/
	
	//Normalize one string at a time
	public static String normalize(String token){
		String term = token;
		//stemmer cannot be initialize in class block, initialize once instead of loading a stemmer for each token
		initializeStemmer();
		
		term = caseFold(term);
		term = removePunctuation(term);
		
		// Remove numbers or stop words or empty strings(used to be a token with special characters only)
  	  	if(term.isEmpty() || isNumeric(term) || isStopWord(term)){
			term = "";
		}
		else{
			term = stem(term);
		}
		
		return term;
	}
	
	//run all simple normalization on the doc tokens
	public static List<String> normalize(Document doc){
		List<String> tokens = doc.getTokens();
		
		//stemmer cannot be initialize in class block, initialize once instead of loading a stemmer for each token
		initializeStemmer();
		
		for (Integer i = 0; i < tokens.size(); i++) {
    	   tokens.set(i, caseFold(tokens.get(i)));
    	   tokens.set(i, removePunctuation(tokens.get(i)));
    	   
    	   
    	   // Remove numbers or stop words or empty strings(used to be a token with special characters only)
    	   if( tokens.get(i).isEmpty() || isNumeric(tokens.get(i)) || isStopWord(tokens.get(i))){
    		   tokens.remove(tokens.get(i));
    		   i--;
    	   }
    	   else{
    		   tokens.set(i, stem(tokens.get(i)));
    	   }
    	}

		return tokens;
	}
	
	public static void initializeStemmer(){
		Class<?> stemClass = null;
		try {
			stemClass = Class.forName("org.tartarus.snowball.ext.englishStemmer");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stemmer = (SnowballStemmer) stemClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String caseFold(String s){
		return s.toLowerCase();
	}
	
	public static String removePunctuation(String s){
		return UNDESIRABLES.matcher(s).replaceAll("");
	}
	
	public static String stem(String s){
		if(stemmer == null){
			initializeStemmer();
		}
		
		String your_stemmed_word = null;
		stemmer.setCurrent(s);
		stemmer.stem();
		your_stemmed_word = stemmer.getCurrent();
		
		return your_stemmed_word;
	}
	
	public static Boolean isNumeric(String s){
		//match a number with optional '-' and decimal.
		return s.matches("-?\\d+(\\.\\d+)?");
	}
	
	public static Boolean isStopWord(String s){
		return stopWordList.contains(s);
	}
	
	// out dated simple array form of stop words 
	/*public static String[] stopWords = {
		"without", "see", "unless", "due", "also", "must", "might", "like", "]", "[", "}", "{", "<", ">", "?", "\\", "/", ")", "(", "will", "may", "can", "much", "every", "the", "in", "other", "this", "the", 
		"many", "any", "an", "or", "for", "in", "an", "an ", "is", "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren’t", "as", "at", "be", "because", "been", 
		"before", "being", "below", "between", "both", "but", "by", "can’t", "cannot", "could",
		"couldn’t", "did", "didn’t", "do", "does", "doesn’t", "doing", "don’t", "down", "during", "each", "few", "for", "from", "further", "had", "hadn’t", "has", "hasn’t", "have", "haven’t", "having",
		"he", "he’d", "he’ll", "he’s", "her", "here", "here’s", "hers", "herself", "him", "himself", "his", "how", "how’s", "i ", " i", "i’d", "i’ll", "i’m", "i’ve", "if", "in", "into", "is",
		"isn’t", "it", "it’s", "its", "itself", "let’s", "me", "more", "most", "mustn’t", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "ought", "our", "ours", "ourselves",
		"out", "over", "own", "same", "shan’t", "she", "she’d", "she’ll", "she’s", "should", "shouldn’t", "so", "some", "such", "than",
		"that", "that’s", "their", "theirs", "them", "themselves", "then", "there", "there’s", "these", "they", "they’d", "they’ll", "they’re", "they’ve",
		"this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn’t", "we", "we’d", "we’ll", "we’re", "we’ve",
		"were", "weren’t", "what", "what’s", "when", "when’s", "where", "where’s", "which", "while", "who", "who’s", "whom",
		"why", "why’s", "with", "won’t", "would", "wouldn’t", "you", "you’d", "you’ll", "you’re", "you’ve", "your", "yours", "yourself", "yourselves"
	};*/
}
