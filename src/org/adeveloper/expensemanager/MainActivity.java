package org.adeveloper.expensemanager;

import java.text.NumberFormat;

import org.adeveloper.expensemanager.EventListener.NewExpense;
import org.adeveloper.expensemanager.db.Database;
import org.adeveloper.expensemanager.db.DatabaseConnectionFactory;
import org.adeveloper.expensemanager.db.ExpensemanagerDatasource;
import org.adeveloper.expensemanager.lib.Balance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		submitNewExpense();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateCurrentAvailableMoney();
	}
	
	
	public void updateCurrentAvailableMoney(){
		ExpensemanagerDatasource datasource = getDatabaseConnection();
		Balance balance = new Balance(datasource.open());
		
		TextView currentBalanceView = (TextView) findViewById(R.id.current_balance);
		double currentBalance = (double) balance.getCurrentBalance();
		currentBalanceView.setText(NumberFormat.getInstance().format(currentBalance));
		
		TextView currency = (TextView) findViewById(R.id.currency);
		currency.setText("ریال");
		datasource.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.manage_resource){
			startManageResourceActivity();
		}else if (item.getItemId() == R.id.expenses_list_menu){
			startExpensesListActivity();
		}else if (item.getItemId() == R.id.backup_menu){
			startBackupActivity();
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void startManageResourceActivity() {
		Intent intent = new Intent(this, ManageResourcesActivity.class);
		startActivity(intent);
	}
	
	private void startExpensesListActivity() 
	{
		Intent intent = new Intent(this, ExpensesListActivity.class);
		startActivity(intent);
	}
	
	private void startBackupActivity()
	{
		Intent intent = new Intent(this, BackupActivity.class);
		startActivity(intent);
	}
	
	
	private void submitNewExpense()
	{
		final EditText caption = (EditText) findViewById(R.id.stuff_bought_field);
		final EditText price = (EditText) findViewById(R.id.stuff_price_field);
		Button button = (Button) findViewById(R.id.submit_bought_stuff_button);
		button.setOnClickListener(new NewExpense(this, caption, price));
	}
	
	// TODO this method feels like a code smell!
	private ExpensemanagerDatasource getDatabaseConnection()
	{
		return new DatabaseConnectionFactory(this)
		.create(Database.ExpenseManager);
	}

}
