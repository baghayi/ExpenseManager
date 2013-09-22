package org.adeveloper.expensemanager.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public final class DatabaseConnectionFactory
{
	// A Constant to be used as database name.
	public final static String DATABASE_EXPENSECHEKER = "expensechecker";
	
	private Context context;
	
	public DatabaseConnectionFactory(Context context)
	{
		this.context = context;
	}
	
	
	public SQLiteDatabase create(String databaseName)
	{
		return context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
	}

}
