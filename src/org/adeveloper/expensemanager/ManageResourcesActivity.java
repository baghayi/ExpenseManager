package org.adeveloper.expensemanager;

import org.adeveloper.expensemanager.EventListener.CalculateBalance;
import org.adeveloper.expensemanager.EventListener.RealtimeUserBalance;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ManageResourcesActivity extends Activity {
	
	public String balanceTypeValue = CalculateBalance.BALANCE_INCREASE;
	
	private TextView realtimeBalance;
	private static final String KEY_REALTIME_BALANCE = "realtimebalance";
	
	public String balanceTypeSign = "+";
	public static final String KEY_BALANCE_TYPE_SIGN = "balancetypesign";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_manage_resources);
		
		manageBalance(savedInstanceState);
		
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putString(KEY_REALTIME_BALANCE, realtimeBalance.getText().toString());
		outState.putString(KEY_BALANCE_TYPE_SIGN, balanceTypeSign);
	}

	
	private void manageBalance(Bundle savedInstanceState)
	{
		realtimeBalance = (TextView) findViewById(R.id.realtime_balance);
		
		if(savedInstanceState != null)
		{
			realtimeBalance.setText(savedInstanceState.getString(KEY_REALTIME_BALANCE));
			
			setBalanceType(savedInstanceState.getString(KEY_BALANCE_TYPE_SIGN));
		}
		
		EditText price = (EditText) findViewById(R.id.price_field);
		price.setOnKeyListener(new RealtimeUserBalance(price, realtimeBalance));
		
		Button button = (Button) findViewById(R.id.balance_button);
		button.setOnClickListener(new CalculateBalance(this, balanceTypeValue, price, realtimeBalance));
	}
	
	private void setBalanceType(String sign)
	{
		if(sign.contains("+"))
		{
			setBalanceTypeIncrease();
		}else if(sign.contains("-"))
		{
			setBalanceTypeDecrease();
		}
	}
	
	public void flipBalanceType(View view)
	{
		ImageButton balanceType = (ImageButton) findViewById(R.id.balance_type_image);
		
		if(balanceType.getTag().toString().contains("+")){
			setBalanceTypeDecrease();
		}else if(balanceType.getTag().toString().contains("-")){
			setBalanceTypeIncrease();
		}
		
		// Bind event again. (will take balaneTypeValue property's newly set value).
		manageBalance(null);
	}
	
	private void setBalanceTypeIncrease()
	{
		balanceTypeSign = "+";

		TextView balanceText = (TextView) findViewById(R.id.balance_text);
		ImageButton balanceType = (ImageButton) findViewById(R.id.balance_type_image);
		
		balanceType.setTag(balanceTypeSign);
		balanceTypeValue = CalculateBalance.BALANCE_INCREASE;
		balanceType.setImageDrawable(getResources().getDrawable(R.drawable.plus_sign));
		balanceText.setText("افزایش موجودی:");
	}
	
	private void setBalanceTypeDecrease()
	{
		balanceTypeSign = "-";
		
		TextView balanceText = (TextView) findViewById(R.id.balance_text);
		ImageButton balanceType = (ImageButton) findViewById(R.id.balance_type_image);
		
		balanceType.setTag(balanceTypeSign);
		balanceTypeValue = CalculateBalance.BALANCE_DECREASE;
		balanceType.setImageDrawable(getResources().getDrawable(R.drawable.minus_sign));
		balanceText.setText("کاهش موجودی:");
	}
	
}
