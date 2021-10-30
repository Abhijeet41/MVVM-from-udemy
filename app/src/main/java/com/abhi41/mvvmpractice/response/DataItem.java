package com.abhi41.mvvmpractice.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.abhi41.mvvmpractice.utils.Common;
import com.google.gson.annotations.SerializedName;


public class DataItem implements Parcelable {

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

	protected DataItem(Parcel in) {
		gender = in.readString();
		name = in.readString();
		id = in.readInt();
		email = in.readString();
		status = in.readString();
	}

	public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
		@Override
		public DataItem createFromParcel(Parcel in) {
			return new DataItem(in);
		}

		@Override
		public DataItem[] newArray(int size) {
			return new DataItem[size];
		}
	};

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
		Common.loadImage(imageView,imageUrl, Common.getProgressCircularDrawable(imageView.getContext()));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(gender);
		parcel.writeString(name);
		parcel.writeString(email);
		parcel.writeString(status);
		parcel.writeInt(id);

	}
}