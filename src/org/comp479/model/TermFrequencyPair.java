package org.comp479.model;

public class TermFrequencyPair {
	private Integer docId;
	private Integer termFrequency;
	
	public TermFrequencyPair(int docId, int termFrequency) {
		super();
		this.docId = docId;
		this.termFrequency = termFrequency;
	}
	
	public Integer getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public Integer getTermFrequency() {
		return termFrequency;
	}

	public void setTermFrequency(int termFrequency) {
		this.termFrequency = termFrequency;
	}
	
	public void increaseFrequency(){
		this.termFrequency++;
	}
	
	public String toString(){
		return docId.toString() + ":" + termFrequency.toString();
	}
}
