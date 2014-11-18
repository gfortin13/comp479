package org.comp479.domain;

public class IndexBuilder {

	public static void main(String[] args) {
    	//execute spimi and block merger algorithms
    	try {
    		System.out.println("Indexing...");
    		Spimi.execute();
    		System.out.println("Merging...");
			BlockMerger.execute();
			System.out.println("Done!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
