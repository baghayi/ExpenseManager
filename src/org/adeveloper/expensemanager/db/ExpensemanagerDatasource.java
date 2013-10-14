package org.adeveloper.expensemanager.db;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.adeveloper.expensemanager.util.FileUtils;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class ExpensemanagerDatasource implements Closeable
{
	private SQLiteOpenHelper helper;
	private SQLiteDatabase database;
	
	private static final String DATABASE_FILE_PATH = 
			"//data//org.adeveloper.expensemanager//databases//" + ExpensemanagerHelper.DATABASE_NAME;
	private static final String BACKUP_PATH = 
			Environment.getExternalStorageDirectory().toString() + "/Expensemanager";
	
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
		File sd = new File(BACKUP_PATH);
		if(!sd.exists() && !sd.mkdirs()){
			return false;
		}
		
	    if (sd.canWrite()) {
		    Locale locale = new Locale("en_US");
		    Locale.setDefault(locale);
		    
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		    String backupFilename = formatter.format(new Date());
		    File backupFile = new File(sd, backupFilename);
		    
		    File originalDatabase = new File(Environment.getDataDirectory(), 
		    		DATABASE_FILE_PATH);
		    
		    if (originalDatabase.exists()) {
		    	
	        	try
				{
					FileUtils.copyFile(new FileInputStream(originalDatabase), 
							new FileOutputStream(backupFile));
					return true;
				} catch (FileNotFoundException e){
					return false;
				} catch (IOException e){
					return false;
				}
		    }
		}
		
		return false;
	}
	
	
	public boolean restore(String backupfileName) {
		
		String dbPath = BACKUP_PATH + "//" + backupfileName;
		
	    File newDb = new File(dbPath);
	    File oldDb = new File(Environment.getDataDirectory().toString() + DATABASE_FILE_PATH);
	    if (newDb.exists()) {
	        try
			{
				FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
			} catch (FileNotFoundException e)
			{
				return false;
			} catch (IOException e)
			{
				return false;
			}
	        
	        // Access the copied database so SQLiteHelper will cache it and mark
	        // it as created.
	        open();
	        close();
	        return true;
	    }
	    return false;
	}
	
}
