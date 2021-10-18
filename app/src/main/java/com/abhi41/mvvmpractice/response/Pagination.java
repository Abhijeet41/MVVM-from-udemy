package com.abhi41.mvvmpractice.response;

import com.google.gson.annotations.SerializedName;

public class Pagination{

	@SerializedName("total")
	private int total;

	@SerializedName("pages")
	private int pages;

	@SerializedName("limit")
	private int limit;

	@SerializedName("links")
	private Links links;

	@SerializedName("page")
	private int page;

	public int getTotal(){
		return total;
	}

	public int getPages(){
		return pages;
	}

	public int getLimit(){
		return limit;
	}

	public Links getLinks(){
		return links;
	}

	public int getPage(){
		return page;
	}
}