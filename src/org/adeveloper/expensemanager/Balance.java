package org.adeveloper.expensemanager;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

public class Balance {
	
	SQLiteDatabase database = null;
	
	public Balance(SQLiteDatabase database){
		this.database = database;
		createBalanceTable();
	}
	
	private void createBalanceTable(){
		String query = "CREATE TABLE IF NOT EXISTS balance ("
				+ "Id INTEGER PRIMARY KEY,"
				+ "NewBalance int(20),"
				+ "TotalBalance int(20),"
				+ "UpdateTime int(20)"
				+ ");";
		database.execSQL(query);
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
		
		long result = database.insert("balance", null, values);
		
		if(result == -1){
			return false;
		}else{
			return true;
		}
	}
	
	
	public double getCurrentBalance() {
		String query = "SELECT TotalBalance FROM balance ORDER BY Id DESC LIMIT 1;";
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
