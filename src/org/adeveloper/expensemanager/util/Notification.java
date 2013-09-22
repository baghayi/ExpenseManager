package org.adeveloper.expensemanager.util;

import org.adeveloper.expensemanager.R;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Notification
{
	/**
	 * Bases on the result parameter, it will show proper text.
	 * 
	 * @param context
	 * @param result
	 */
	public static void successFailureToastMessage(Context context, boolean result)
	{
		Toast message = null;
		if(result){
			message = Toast.makeText(context, R.string.lang_mission_succeed, Toast.LENGTH_SHORT);
		}else{
			message = Toast.makeText(context, R.string.lang_mission_failed, Toast.LENGTH_SHORT);
		}
		
		message.setGravity(Gravity.CENTER, 0, 0);
		message.show();
	}
}
