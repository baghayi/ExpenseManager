package org.adeveloper.expensemanager.db;

import java.io.Closeable;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpensemanagerDatasource implements Closeable
{
	private SQLiteOpenHelper helper;
	private SQLiteDatabase database;
	
	public ExpensemanagerDatasource(SQLiteOpenHelper helper)
	{
		this.helper = helper;
	}
	
	public SQLiteDatabase open()
	{
		database = helper.getWritableDatabase();
		return database;
	}
	

	@Override
	public void close()
	{
		database.close();
		helper.close();
	}
	
	
}
