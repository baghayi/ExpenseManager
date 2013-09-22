package org.adeveloper.expensechecker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Expense
{
	private SQLiteDatabase database;
	private final static String TABLE_EXPENSE = "expense"; 
	
	public Expense(SQLiteDatabase database)
	{
		this.database = database;
		createTable();
	}
	
	/**
	 * Creates expense table in order to submit new expenses in.
	 */
	private void createTable()
	{
		String query = "CREATE TABLE IF NOT EXISTS "+TABLE_EXPENSE+" ("
				+ "Id INTEGER PRIMARY KEY,"
				+ "Caption varchar(255),"
				+ "Price double(20),"
				+ "UpdateTime int(20)"
				+ ");";
		database.execSQL(query);
	}
	
	/**
	 * Adds a new row to expense table as expense with caption and price.
	 * 
	 * @param caption
	 * @param price
	 * @return
	 */
	public boolean add(String caption, double price)
	{
		ContentValues values = new ContentValues();
		values.put("Caption", caption);
		values.put("Price", price);
		values.put("UpdateTime", System.currentTimeMillis());
		long insertId = database.insert(TABLE_EXPENSE, null, values);
		
		if(insertId == -1){
			return false;
		}else {
			return true;
		}
	}
}
