package org.adeveloper.expensemanager;

import org.adeveloper.expensemanager.db.ExpensemanagerDatasource;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class BackupActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup);
	}

	
	public void backup(View view)
	{
		@SuppressWarnings("resource")
		ExpensemanagerDatasource datasource = new ExpensemanagerDatasource();
		
		if(datasource.backup()){
			// success
			Log.i("Debug", "Success");
		}else {
			// failure
			Log.i("Debug", "Failure");
		}
	}

}
