package org.adeveloper.expensemanager.lib;

import org.adeveloper.expensemanager.db.ExpensemanagerHelper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Expense
{
	private SQLiteDatabase database;
	
	public Expense(SQLiteDatabase database)
	{
		this.database = database;
	}
	
	/**
	 * Adds a new row to expense table as expense with caption and price.
	 * 
	 * @param caption
	 * @param price
	 * @param balanceId
	 * @return
	 */
	public boolean add(String caption, double price, Balance balanceObject)
	{
		if(balanceObject.subtract(price)){
			return false;
		}
		
		ContentValues values = new ContentValues();
		values.put("Caption", caption);
		values.put("BalanceId", balanceObject.getLastInsertId());
		values.put("UpdateTime", System.currentTimeMillis());
		long insertId = database.insert(ExpensemanagerHelper.TABLE_NAME_EXPENSE, null, values);
		
		if(insertId == -1){
			return false;
		}else {
			return true;
		}
	}
}
