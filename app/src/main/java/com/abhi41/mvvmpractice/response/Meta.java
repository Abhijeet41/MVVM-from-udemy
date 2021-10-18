package com.abhi41.mvvmpractice.response;

import com.google.gson.annotations.SerializedName;

public class Meta{

	@SerializedName("pagination")
	private Pagination pagination;

	public Pagination getPagination(){
		return pagination;
	}
}