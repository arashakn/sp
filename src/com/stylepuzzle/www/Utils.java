
package com.stylepuzzle.www;




import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.google.gson.Gson;

/**
 * Class containing some static utility methods.
 */
public class Utils {
	public static final int PASSWORD_MIN_LENGHT = 2;
	public static boolean haveNetworkConnection(Context mContext) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}


	public static boolean hasFroyo() {
		// Can use static final constants like FROYO, declared in later versions
		// of the OS since they are inlined at compile time. This is guaranteed behavior.
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

//	public static boolean hasJellyBean() {
//		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
//	}

	public static <T> T  jsonToObject(String result,Class<T> type) {
		T t1 = null;
		if (result != null && result.length() > 0) {
			try {
				Gson gson = new Gson();
				t1 = gson.fromJson(result,type);
			} catch (IllegalStateException ex) {
				// just eat the exception
			} catch(Exception e){

			}
		}
		return t1;
	}

	public static boolean isValidEmailAddressObsolete(String email) {
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(".+@.+\\.[a-z]+");
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isValidPassword(String pass){
		if(pass==null)
			return false;
		else{
			if(pass.length()>=PASSWORD_MIN_LENGHT)
				return true;
			else
				return false;
		}

	}
	
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}

	/** Transform Calendar to ISO 8601 string. */
	public static String fromCalendar(final Calendar calendar) {
		Date date = calendar.getTime();
		String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
		.format(date);
		return formatted.substring(0, 22) + ":" + formatted.substring(22);
	}

	/** Get current date and time formatted as ISO 8601 string. */
	public static String now() {
		return fromCalendar(Calendar.getInstance());
	}

	/** Transform ISO 8601 string to Calendar. */
	public static Calendar toCalendar(final String iso8601string) {
		Calendar calendar = Calendar.getInstance();
		String s = iso8601string.replace("Z", "+00:00");
		try {
			s = s.substring(0, 22) + s.substring(23);  // to get rid of the ":"
			Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
			calendar.setTime(date);
		} 
		
		catch (Exception e) {
		}
		return calendar;
	}

	public static String addKeyToJson(String key, String value){
		String finalResult= "{\""+key+"\":"+value+"}";
		return finalResult;
		
	}
	
}
