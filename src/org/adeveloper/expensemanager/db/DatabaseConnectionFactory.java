package org.adeveloper.expensemanager.db;

import java.security.InvalidParameterException;

import android.content.Context;

public final class DatabaseConnectionFactory
{
	
	private Context context;
	
	public DatabaseConnectionFactory(Context context)
	{
		this.context = context;
	}
	
	
	public ExpensemanagerDatasource create(Database databaseName) throws InvalidParameterException
	{
		switch (databaseName){
			case ExpenseManager:
				return new ExpensemanagerDatasource(
							new ExpensemanagerHelper(context));
			default: 
				throw new InvalidParameterException("Requested Database Does Not Exist!");
		}
		
	}

}
