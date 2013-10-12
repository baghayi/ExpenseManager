package org.adeveloper.expensemanager.db;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class ExpensemanagerDatasource implements Closeable
{
	private SQLiteOpenHelper helper;
	private SQLiteDatabase database;
	
	/**
	 * Only used when taking backup.
	 * 
	 * Which in this case database file MUST not be touched.
	 */
	public ExpensemanagerDatasource(){}
	
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
	
	@SuppressLint("SimpleDateFormat")
	public boolean backup()
	{
		File sd = Environment.getExternalStorageDirectory();
		sd = new File(sd.toString()+"/Expensemanager");
		
		if (sd.canWrite()) {
		    Locale locale = new Locale("en_US");
		    Locale.setDefault(locale);
		    
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		    String backupFilename = formatter.format(new Date());
		    File backupFile = new File(sd, backupFilename);
		    
		    File originalDatabase = new File(Environment.getDataDirectory(), 
		    		"//data//org.adeveloper.expensemanager//databases//" + ExpensemanagerHelper.DATABASE_NAME);
		    
		    if (originalDatabase.exists()) {
		    	FileChannel source = null;
		    	FileChannel destination = null;
		    	
		    	FileInputStream fileInputStream = null;
		    	FileOutputStream fileOutputStream = null;
		    	
		        try
				{
					fileInputStream = new FileInputStream(originalDatabase);
					source = fileInputStream.getChannel();
					
					fileOutputStream = new FileOutputStream(backupFile);
					destination = fileOutputStream.getChannel();
					
					destination.transferFrom(source, 0, source.size());
					
					return true;
				} catch (FileNotFoundException e){
					return false;
				} catch (IOException e){
					return false;
				}finally {
					try
					{
						source.close();
						destination.close();
						
						fileInputStream.close();
						fileOutputStream.close();
					} catch (IOException e){
						return false;
					}
				}
		    }
		}
		
		return false;
	}
	
	
}
