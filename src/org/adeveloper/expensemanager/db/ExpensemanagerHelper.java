package org.adeveloper.expensemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpensemanagerHelper extends SQLiteOpenHelper
{
	private final static int VERSION_NUMBER = 1;
	private final static String DATABASE_NAME = "expensemanager"; 
	
	public final static String TABLE_NAME_BALANCE = "balance";
	private final String TABLE_STRUCTURE_BALANCE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BALANCE + " ("
			+ "Id INTEGER PRIMARY KEY,"
			+ "NewBalance int(20),"
			+ "TotalBalance int(20),"
			+ "UpdateTime int(20)"
			+ ");";
	
	public final static String TABLE_NAME_EXPENSE = "expense";
	private final String TABLE_STRUCTURE_EXPENSE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_EXPENSE +" ("
			+ "Id INTEGER PRIMARY KEY,"
			+ "Caption varchar(255),"
			+ "BalanceId int(20),"
			+ "UpdateTime int(20)"
			+ ");";
	
	public ExpensemanagerHelper(Context context)
	{
		super(context, DATABASE_NAME, null, VERSION_NUMBER);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0)
	{
		arg0.execSQL(TABLE_STRUCTURE_BALANCE);
		arg0.execSQL(TABLE_STRUCTURE_EXPENSE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
	{
	}

}
