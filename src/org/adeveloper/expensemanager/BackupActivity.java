package org.adeveloper.expensemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.adeveloper.expensemanager.db.ExpensemanagerDatasource;
import org.adeveloper.expensemanager.db.ExpensemanagerHelper;
import org.adeveloper.expensemanager.util.Notification;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BackupActivity extends ListActivity
{
	private List<String> backupFilesList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup);
		
		showList();
	}
	
	
	private void showList()
	{
		backupFilesList = getBackupFilesList();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, backupFilesList);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		final int itemPosition = position;
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setMessage("آیا از انجام عمل مورد نظر اطمینان داری؟");
		alertBuilder.setCancelable(false);
		alertBuilder.setNegativeButton("نه نمی دونم!", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		});
		
		alertBuilder.setPositiveButton("آره داداش", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				restore(backupFilesList.get(itemPosition));
			}
		});
		
		AlertDialog alert = alertBuilder.create();
		alert.show();
		
		super.onListItemClick(l, v, position, id);
	}

	
	public void backup(View view)
	{
		@SuppressWarnings("resource")
		ExpensemanagerDatasource datasource = new ExpensemanagerDatasource();
		
		Notification.successFailureToastMessage(this, datasource.backup());
		
		showList();
	}
	
	private void restore(String filename)
	{
		@SuppressWarnings("resource")
		ExpensemanagerDatasource datasource = new ExpensemanagerDatasource(new ExpensemanagerHelper(this));
		
		Notification.successFailureToastMessage(this, datasource.restore(filename));
	}
	
	private List<String> getBackupFilesList()
	{
		List<String> filesList = new ArrayList<String>();
		
		File folder = new File(Environment.getExternalStorageDirectory().toString()+"/Expensemanager");
		if(!folder.exists()){
			return filesList;
		}
		
		for	(File file : folder.listFiles())
		{
			filesList.add(file.getName());
		}
		
		
		return filesList;
	}
}
