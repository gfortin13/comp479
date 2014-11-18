package org.comp479.model;

public class SingleIndex {
	private String term;
	private PostingList pl;
	
	public SingleIndex(String blockLine){
		this.term = blockStringToTerm(blockLine);
		this.pl = PostingList.blockStringToPL(blockLine);
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public PostingList getPl() {
		return pl;
	}

	public void setPl(PostingList pl) {
		this.pl = pl;
	}
	
	public static String blockStringToTerm(String s){
        int beginIndex = s.indexOf('[');
        return s.substring(0, beginIndex);
    }
}
