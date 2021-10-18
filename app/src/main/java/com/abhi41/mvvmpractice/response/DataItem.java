package com.abhi41.mvvmpractice.response;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.abhi41.mvvmpractice.Utils;
import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("gender")
	private String gender;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public String getGender(){
		return gender;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getStatus(){
		return status;
	}

	@Override
	public String toString() {
		return "DataItem{" +
				"gender='" + gender + '\'' +
				", name='" + name + '\'' +
				", id=" + id +
				", email='" + email + '\'' +
				", status='" + status + '\'' +
				'}';
	}

	public DataItem(String gender, String name, int id, String email, String status) {
		this.gender = gender;
		this.name = name;
		this.id = id;
		this.email = email;
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DataItem dataItem = (DataItem) o;
		return getId() == dataItem.getId() && getGender().equals(dataItem.getGender()) && getName().equals(dataItem.getName()) && getEmail().equals(dataItem.getEmail()) && getStatus().equals(dataItem.getStatus());
	}

	public static DiffUtil.ItemCallback<DataItem> itemCallback = new DiffUtil.ItemCallback<DataItem>() {
		@Override
		public boolean areItemsTheSame(@NonNull DataItem oldItem, @NonNull DataItem newItem) {
			return oldItem.getId() == newItem.getId();
		}

		@Override
		public boolean areContentsTheSame(@NonNull DataItem oldItem, @NonNull DataItem newItem) {
			return oldItem.equals(newItem);
		}
	};

	@BindingAdapter("android:userImage")
	public static void loadImage(ImageView imageView, String imageUrl) {
		Utils.loadImage(imageView,imageUrl,Utils.getProgressCircularDrawable(imageView.getContext()));
	}

}