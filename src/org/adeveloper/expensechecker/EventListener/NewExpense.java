package org.adeveloper.expensechecker.EventListener;

import org.adeveloper.expensechecker.Balance;
import org.adeveloper.expensechecker.Expense;
import org.adeveloper.expensechecker.MainActivity;
import org.adeveloper.expensechecker.util.DatabaseConnectionFactory;
import org.adeveloper.expensechecker.util.Notification;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class NewExpense implements OnClickListener
{
	private Context context;
	private EditText caption;
	private EditText price;

	public NewExpense(Context context, EditText caption, EditText price)
	{
		this.context = context;
		this.caption = caption;
		this.price = price;
	}
	
	private String getCaption()
	{
		return caption.getText().toString();
	}
	
	private double getPrice()
	{
		String priceString = price.getText().toString();

		if(priceString.isEmpty()){
			return 0;
		}
		return Double.parseDouble(priceString);
	}

	@Override
	public void onClick(View arg0)
	{
		// if both fields not filled with values at the same time, there is no need to continue.
		if(getCaption().isEmpty() && getPrice() == 0){
			return;
		}
		
		SQLiteDatabase database = getDatabaseConnection();
		
		
		Expense expenseObject = new Expense(database);
		Balance balance = new Balance(database);
		
		boolean resultState = false;
		try
		{
			database.beginTransaction();
			boolean expenseTableResult = expenseObject.add(getCaption(), getPrice());
			boolean balanceTableResult = balance.subtract(getPrice());
			
			if(expenseTableResult && balanceTableResult){
				resultState = true;
				database.setTransactionSuccessful();
				caption.setText("");
				price.setText("");
			}
			
		} 
		catch (Exception e){}
		finally {
			database.endTransaction();
			database.close();
		}

		((MainActivity) context).updateCurrentAvailableMoney();
		Notification.successFailureToastMessage(context, resultState);
	}
	
	// TODO feels like code smell!
	private SQLiteDatabase getDatabaseConnection(){
		return new DatabaseConnectionFactory(context)
				.create(DatabaseConnectionFactory.DATABASE_EXPENSECHEKER);
	}

}
