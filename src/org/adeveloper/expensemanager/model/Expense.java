package org.adeveloper.expensemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Expense implements Parcelable
{
	
	private long Id;
	private String Caption;
	private double price;
	private long UpdateTime;
	
	public static final Parcelable.Creator<Expense> CREATOR = 
			new Parcelable.Creator<Expense>() {

				@Override
				public Expense createFromParcel(Parcel source)
				{
					return new Expense(source);
				}

				@Override
				public Expense[] newArray(int size)
				{
					return new Expense[size];
				}
		
			};
	
	public Expense(){}
	
	public Expense(Parcel in)
	{
		setId(in.readLong());
		setCaption(in.readString());
		setPrice(in.readDouble());
		setUpdateTime(in.readLong());
	}
	
	public long getId()
	{
		return Id;
	}
	
	public void setId(long id)
	{
		Id = id;
	}
	
	public String getCaption()
	{
		return Caption;
	}
	
	public void setCaption(String caption)
	{
		Caption = caption;
	}
	
	public long getUpdateTime()
	{
		return UpdateTime;
	}
	
	public void setUpdateTime(long updateTime)
	{
		UpdateTime = updateTime;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeLong(getId());
		dest.writeString(getCaption());
		dest.writeDouble(getPrice());
		dest.writeLong(getUpdateTime());
	}
	
	
}
