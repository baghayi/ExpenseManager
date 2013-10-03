package org.adeveloper.expensemanager.lib;


import org.adeveloper.expensemanager.db.ExpensemanagerHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

public class Balance {
	
	SQLiteDatabase database = null;
	
	private long lastInsertId;
	
	public Balance(SQLiteDatabase database){
		this.database = database;
	}
	
	
	public boolean add(double amount)
	{
		double totalBalance = getCurrentBalance() + amount;
		return calculateBalance(amount, totalBalance);
	}
	
	
	public boolean subtract(double amount)
	{
		double totalBalance = getCurrentBalance() - amount;
		return calculateBalance(amount, totalBalance);
	}
	
	private boolean calculateBalance(double amount, double totalBalance)
	{
		ContentValues values = new ContentValues();
		values.put("NewBalance", amount);
		values.put("TotalBalance", totalBalance);
		values.put("UpdateTime", System.currentTimeMillis());
		
		long result = database.insert(ExpensemanagerHelper.TABLE_NAME_BALANCE, null, values);
		lastInsertId = result;
		
		if(result == -1){
			return false;
		}else{
			return true;
		}
	}
	
	public long getLastInsertId()
	{
		return lastInsertId;
	}
	
	
	public double getCurrentBalance() {
		String query = "SELECT TotalBalance FROM " + ExpensemanagerHelper.TABLE_NAME_BALANCE + " ORDER BY Id DESC LIMIT 1;";
		Cursor result = database.rawQuery(query, null);
		result.moveToFirst();
		int columnIndex = result.getColumnIndex("TotalBalance");
		
		try{
			return result.getInt(columnIndex);
		}catch(CursorIndexOutOfBoundsException e){
			return 0;
		}
	}

}
