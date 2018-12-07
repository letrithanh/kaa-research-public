package me.letrithanh.mongodb.simpleforecast.global;

public enum GlobalVariables {
	
	LENGTH_OF_COLLECTION("LENGTH_OF_COLLECTION");
	
	private String var;
	
	private GlobalVariables (String var) {
		this.var = var;
	}
	
	public String getVar() {
		return this.var;
	}
}
