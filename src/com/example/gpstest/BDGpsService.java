package com.example.gpstest;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.util.BDGpsServiceListener;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BDGpsService extends Service {

	private static final int minTime = 60000;
	private LocationClient locationClient;
	private BDLocationListener locationListener;
	private LocationClientOption lco;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("BDGpsService", "********BDGpsService onCreate*******");
		lco = new LocationClientOption();
		lco.setLocationMode(LocationMode.Hight_Accuracy);
		lco.setScanSpan(minTime);
		lco.setCoorType("bd09ll");
		lco.setOpenGps(true);
		lco.setIsNeedAddress(true);
		locationListener = new BDGpsServiceListener(getApplicationContext());
		locationClient = new LocationClient(getApplicationContext());
		locationClient.setLocOption(lco);
		locationClient.registerLocationListener(locationListener);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("BDGpsService", "********BDGpsService onStartCommand*******");
		if (locationClient != null && !locationClient.isStarted()){
			locationClient.start();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("BDGpsService", "********BDGpsService onDestroy*******");
		if (locationClient != null && locationClient.isStarted()){
			locationClient.stop();
		}
		locationClient.unRegisterLocationListener(locationListener);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
