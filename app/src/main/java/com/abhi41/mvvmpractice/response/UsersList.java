package com.abhi41.mvvmpractice.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UsersList{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("meta")
	private Meta meta;

	public List<DataItem> getData(){
		return data;
	}

	public Meta getMeta(){
		return meta;
	}

	public UsersList(List<DataItem> data) {
		this.data = data;
	}
}