package adhamenaya;

import android.content.Context;
import android.widget.Toast;

public class Util {

	public static void toast(String msg, Context cx) {
		Toast.makeText(cx, msg, Toast.LENGTH_SHORT).show();
	} // end toast
}
