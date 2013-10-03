package org.adeveloper.expensemanager.db;

import java.security.InvalidParameterException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public final class DatabaseConnectionFactory
{
	
	private Context context;
	
	public DatabaseConnectionFactory(Context context)
	{
		this.context = context;
	}
	
	
	public SQLiteDatabase create(Database databaseName) throws InvalidParameterException
	{
		switch (databaseName){
			case ExpenseManager:
				return new ExpensemanagerHelper(context).getWritableDatabase();
			default: 
				throw new InvalidParameterException("Requested Database Does Not Exist!");
		}
		
	}

}
