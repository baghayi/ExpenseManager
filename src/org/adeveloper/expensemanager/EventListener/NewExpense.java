package org.adeveloper.expensemanager.EventListener;

import org.adeveloper.expensemanager.MainActivity;
import org.adeveloper.expensemanager.db.Database;
import org.adeveloper.expensemanager.db.DatabaseConnectionFactory;
import org.adeveloper.expensemanager.lib.Balance;
import org.adeveloper.expensemanager.lib.Expense;
import org.adeveloper.expensemanager.util.Notification;

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
		
		boolean result = false;
		try
		{
			database.beginTransaction();
			Expense expenseObject = new Expense(database);
			result = expenseObject.add(getCaption(), getPrice(), new Balance(database));
			
			if(result){
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
		Notification.successFailureToastMessage(context, result);
	}
	
	// TODO feels like code smell!
	private SQLiteDatabase getDatabaseConnection(){
		return new DatabaseConnectionFactory(context)
				.create(Database.ExpenseManager);
	}

}
