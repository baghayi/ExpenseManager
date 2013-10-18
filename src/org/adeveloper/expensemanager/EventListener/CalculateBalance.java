package org.adeveloper.expensemanager.EventListener;

import org.adeveloper.expensemanager.db.Database;
import org.adeveloper.expensemanager.db.DatabaseConnectionFactory;
import org.adeveloper.expensemanager.db.ExpensemanagerDatasource;
import org.adeveloper.expensemanager.lib.Balance;
import org.adeveloper.expensemanager.util.Notification;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public final class CalculateBalance implements OnClickListener
{
	
	public final static String BALANCE_DECREASE = "add to balance :D";
	public final static String BALANCE_INCREASE = "subtract from balance :|";
	
	private Context context;
	private String balanceType;
	private EditText balanceField;
	private TextView realtimeBalance;

	public CalculateBalance(Context context, String balanceType, EditText balanceField, TextView realtimeBalance)
	{
		this.context = context;
		this.balanceType = balanceType;
		this.balanceField = balanceField;
		this.realtimeBalance = realtimeBalance;
	}
	
	
	private double getPrice(){
		String price = balanceField.getText().toString();
		
		if(price.length() <= 0){
			return 0;
		}
		
		return Double.parseDouble(price);
	}
	
	
	@Override
	public void onClick(View v)
	{
		if(getPrice() == 0){
			return;
		}
		
		// TODO Ask user for confirmation.
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
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
				ExpensemanagerDatasource datasource = CalculateBalance.this.getDatabaseConnection();
				Balance balance = new Balance(datasource.open());
				
				boolean result;
				
				if(balanceType == BALANCE_INCREASE){
					result = balance.add(getPrice());
				}else if (balanceType == BALANCE_DECREASE){
					result = balance.subtract(getPrice());
				}else {
					result = false;
				}
				
				datasource.close();
				
				Notification.successFailureToastMessage(context, result);
				
				if(result){
					CalculateBalance.this.resetBalanceField();
				}
			}
		});
		
		AlertDialog alert = alertBuilder.create();
		alert.show();
	}
	
	// TODO this method feels like a code smell!
	private ExpensemanagerDatasource getDatabaseConnection()
	{
		return new DatabaseConnectionFactory(context)
		.create(Database.ExpenseManager);
	}
	
	private void resetBalanceField()
	{
		balanceField.setText("0");
		
		if(realtimeBalance != null){
			realtimeBalance.setText("0");
		}
	}

}
