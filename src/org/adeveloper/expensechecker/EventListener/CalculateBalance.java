package org.adeveloper.expensechecker.EventListener;

import org.adeveloper.expensechecker.Balance;
import org.adeveloper.expensechecker.util.DatabaseConnectionFactory;
import org.adeveloper.expensechecker.util.Notification;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public final class CalculateBalance implements OnClickListener
{
	
	public final static String BALANCE_DECREASE = "add to balance :D";
	public final static String BALANCE_INCREASE = "subtract from balance :|";
	
	private Context context;
	private String balanceType;
	private EditText balanceField;
	

	public CalculateBalance(Context context, String balanceType, EditText balanceField)
	{
		this.context = context;
		this.balanceType = balanceType;
		this.balanceField = balanceField;
	}
	
	
	@Override
	public void onClick(View v)
	{
		SQLiteDatabase database = getDatabaseConnection();
		Balance balance = new Balance(database);
		
		double newBalance =  Double.parseDouble(balanceField.getText().toString());
		boolean result;
		
		if(balanceType == BALANCE_INCREASE){
			 result = balance.add(newBalance);
		}else if (balanceType == BALANCE_DECREASE){
			result = balance.subtract(newBalance);
		}else {
			result = false;
		}
		
		database.close();
		
		Notification.successFailureToastMessage(context, result);
		
	}
	
	// TODO this method feels like a code smell!
	private SQLiteDatabase getDatabaseConnection()
	{
		return new DatabaseConnectionFactory(context)
		.create(DatabaseConnectionFactory.DATABASE_EXPENSECHEKER);
	}

}
