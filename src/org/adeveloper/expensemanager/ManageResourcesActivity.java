package org.adeveloper.expensemanager;

import org.adeveloper.expensemanager.EventListener.CalculateBalance;
import org.adeveloper.expensemanager.EventListener.RealtimeUserBalance;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ManageResourcesActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_manage_resources);
		
		addBalance();
		subtractBalance();
	}
	
	private void addBalance()
	{
		final TextView realtimeNewBalance = (TextView) findViewById(R.id.realtime_new_balance);
		final EditText newBalanceField = (EditText) findViewById(R.id.add_new_balance_field);
		newBalanceField.setOnKeyListener(new RealtimeUserBalance(newBalanceField, realtimeNewBalance));
		
		Button addNewBalancButton = (Button) findViewById(R.id.add_new_balance_button);
		addNewBalancButton.setOnClickListener(new CalculateBalance(this, CalculateBalance.BALANCE_INCREASE, newBalanceField));
		
	}
	
	private void subtractBalance()
	{
		final TextView realtimeValue = (TextView) findViewById(R.id.realtime_subtraction_balance);
		final EditText userBalance = (EditText) findViewById(R.id.subtract_current_balance_field);
		userBalance.setOnKeyListener(new RealtimeUserBalance(userBalance, realtimeValue));
		
		Button button = (Button) findViewById(R.id.subtract_current_balance_button);
		button.setOnClickListener(new CalculateBalance(this, CalculateBalance.BALANCE_DECREASE, userBalance));
	}
}
