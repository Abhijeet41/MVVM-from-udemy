package com.abhi41.mvvmpractice.response;

import com.google.gson.annotations.SerializedName;

public class Links{

	@SerializedName("next")
	private String next;

	@SerializedName("current")
	private String current;

	@SerializedName("previous")
	private Object previous;

	public String getNext(){
		return next;
	}

	public String getCurrent(){
		return current;
	}

	public Object getPrevious(){
		return previous;
	}
}