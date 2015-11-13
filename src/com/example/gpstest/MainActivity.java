package com.example.gpstest;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String LOCSTART = "START_LOCATING";
	private Button startBtn;
	private Button endBtn;
	private Button endApp;
	private TextView content;
	private LocationReceiver lr;
	private AlarmManager alarmManager;
	private PendingIntent pi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("MainActivity", "********MainActivity onCreate*******");
		startBtn = (Button) findViewById(R.id.startServBtn);
		endBtn = (Button) findViewById(R.id.endServBtn);
		endApp = (Button) findViewById(R.id.endApp);
		content = (TextView) findViewById(R.id.content);
		alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		
		
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.i("MainActivity", "********MainActivity startBtn onClick*******");
				Intent intent = new Intent(LOCSTART);
				pi = PendingIntent.getService(getApplicationContext(), 0, intent, 
						PendingIntent.FLAG_UPDATE_CURRENT);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10000, pi);
				Toast.makeText(getApplicationContext(), "GPS≤‚ ‘ø™ º", Toast.LENGTH_SHORT).show();
			}
		});
		endBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("MainActivity", "********MainActivity endBtn onClick*******");
				alarmManager.cancel(pi);
				Intent intent = new Intent(LOCSTART);
				stopService(intent);
				Toast.makeText(getApplicationContext(), "GPS≤‚ ‘Ω· ¯", Toast.LENGTH_SHORT).show();
			}
		});
		endApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		lr = new LocationReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("NEW LOCATION SENT");
		registerReceiver(lr, intentFilter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("MainActivity", "********MainActivity onDestroy*******");
		unregisterReceiver(lr);
	}

	class LocationReceiver extends BroadcastReceiver {

		String locationMsg = "";
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			locationMsg = intent.getStringExtra("newLoca");
			content.setText(locationMsg);
		}
	}
}
