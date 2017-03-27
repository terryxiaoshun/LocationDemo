package com.example.administrator.locationdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
/**
 * 
 * @description 
 * @charset UTF-8
 * @author xiong_it
 * @date 2015-7-20上午10:34:58
 * @version
 */
public class MainActivity extends Activity {
	private TextView text;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.text);

		// 注册广播
		IntentFilter filter = new IntentFilter();
		filter.addAction(Common.LOCATION_ACTION);
		this.registerReceiver(new LocationBroadcastReceiver(), filter);

		// 启动服务
		Intent intent = new Intent(this, LocationService.class);
//		intent.setClass(this, LocationService.class);
		startService(intent);

		// 等待提示
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在定位...");
		dialog.setCancelable(true);
		dialog.show();
	}

	private class LocationBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String locationInfo = intent.getStringExtra(Common.LOCATION);
			text.setText(locationInfo);
			dialog.dismiss();
			MainActivity.this.unregisterReceiver(this);// 不需要时注销
		}
	}

}
