package org.adeveloper.expensechecker.EventListener;

import java.text.NumberFormat;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class RealtimeUserBalance implements OnKeyListener 
{
	private EditText balanceField;
	private TextView realtimeView;

	public RealtimeUserBalance(EditText balanceField, TextView realtimeView)
	{
		this.balanceField = balanceField;
		this.realtimeView = realtimeView;
	}

	@Override
	public boolean onKey(View arg0, int arg1, KeyEvent arg2)
	{
		NumberFormat format = NumberFormat.getNumberInstance();
		
		String formattedNumber = "";
		try {
			double number = Double.parseDouble((String) balanceField.getText().toString());
			formattedNumber = format.format(number);
			
		} catch (NumberFormatException e) {
			formattedNumber = "0";
		}
		realtimeView.setText(formattedNumber);
		return false;
	}

	

}
