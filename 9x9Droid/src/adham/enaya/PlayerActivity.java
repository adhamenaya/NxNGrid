package adham.enaya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class PlayerActivity extends Activity {
	private LinearLayout lytBlack;
	private LinearLayout lytWhite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.palyer);

		lytBlack = (LinearLayout) findViewById(R.id.lytBlack);
		lytWhite = (LinearLayout) findViewById(R.id.lytWhite);

		final Intent i = new Intent(PlayerActivity.this, BoardActivity.class);
		lytBlack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				i.putExtra("id", 0);
				finish();
				startActivity(i);
			}
		});
		lytWhite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				i.putExtra("id", 1);
				finish();
				startActivity(i);
			}
		});
	}
}