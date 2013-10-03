package org.adeveloper.expensemanager.db;

import java.io.Closeable;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpensemanagerDatasource implements Closeable
{
	private SQLiteOpenHelper helper;
	
	public ExpensemanagerDatasource(SQLiteOpenHelper helper)
	{
		this.helper = helper;
	}
	
	public SQLiteDatabase open()
	{
		return helper.getWritableDatabase();
	}
	

	@Override
	public void close()
	{
		helper.close();
	}
	
	
}
