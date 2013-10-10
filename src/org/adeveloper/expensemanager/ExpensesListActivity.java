package org.adeveloper.expensemanager;

import java.util.ArrayList;
import java.util.List;

import org.adeveloper.expensemanager.db.Database;
import org.adeveloper.expensemanager.db.DatabaseConnectionFactory;
import org.adeveloper.expensemanager.db.ExpensemanagerDatasource;
import org.adeveloper.expensemanager.model.Expense;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExpensesListActivity extends ListActivity
{
	private List<ExpenseItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expenses_list);
		
		dataList = getExpensesList();
		ArrayAdapter<ExpenseItem> adapter = new ArrayAdapter<ExpenseItem>(this, 
				R.layout.expense_list_item,
				dataList);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(this, ExpenseDetailActivity.class);
		intent.putExtra(".model.Expense", dataList.get(position).getExpense());
		
		startActivity(intent);
	}
	
	
	private List<ExpensesListActivity.ExpenseItem> getExpensesList()
	{
		ExpensemanagerDatasource datasource = getDatabaseConnection();
		
		org.adeveloper.expensemanager.lib.Expense expenseObject =
				new org.adeveloper.expensemanager.lib.Expense(datasource.open());
		
		List<ExpensesListActivity.ExpenseItem> list = new ArrayList<ExpensesListActivity.ExpenseItem>();
		
		for (Expense expense : expenseObject.getAllExpenses())
		{
			ExpenseItem expenseItem = new ExpenseItem(expense);
			list.add(expenseItem);
		}
		
		datasource.close();
		
		return list;
	}
	
	private ExpensemanagerDatasource getDatabaseConnection()
	{
		return new DatabaseConnectionFactory(this).create(Database.ExpenseManager);
	}
	
	
	private class ExpenseItem 
	{
		private Expense expense;
		
		public ExpenseItem(Expense expense)
		{
			this.expense = expense;
		}
		
		private Expense getExpense()
		{
			return this.expense;
		}
		
		@Override
		public String toString()
		{
			return " - " + this.getExpense().getCaption();
		}
	}
}
