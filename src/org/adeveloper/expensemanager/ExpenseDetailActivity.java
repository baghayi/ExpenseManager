package org.adeveloper.expensemanager;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;

import org.adeveloper.expensemanager.model.Expense;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ExpenseDetailActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense_detail);
		
		Bundle bundle = getIntent().getExtras();
		Expense expense = bundle.getParcelable(".model.Expense");
		
		TextView captionField = (TextView) findViewById(R.id.expense_detail_caption);
		captionField.setText(expense.getCaption());
		
		TextView priceField = (TextView) findViewById(R.id.expense_detail_price);
		
		NumberFormat numberformatter = NumberFormat.getInstance();
		priceField.setText(numberformatter.format(expense.getPrice()));
		
		DateFormat dateformatter = DateFormat.getDateInstance(DateFormat.FULL);
		String formattedDate = dateformatter.format(new Date(expense.getUpdateTime()));
		TextView updatetimeField = (TextView) findViewById(R.id.expense_detail_updatetime);
		updatetimeField.setText(formattedDate);
	}

}
