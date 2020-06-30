package com.su.ehobesmallsamkoon.util.vein;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.xgzx.veinmanager.VeinApi;

public class VeinInit extends Activity {
	private BluetoothAdapter mBluetoothAdapter = null;
	public static BTSocket mBtSocket = null;
	public static String BTCONNECT = "BTCONNECT";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (!mBluetoothAdapter.isEnabled()) {
			mBluetoothAdapter.enable();// 打开蓝牙
			try {
				Thread.sleep(1000);// 休眠1秒
			} catch (Exception e) {
				Log.e("sleep", e.getMessage());
			}
		}
		mBtSocket = new BTSocket();
		Intent btIntent = new Intent(this, DeviceListActivity.class);
		startActivityForResult(btIntent, 0);
	}

	private boolean connectBTDeviec(String adress) {
		mBtSocket = new BTSocket();
		if (mBtSocket != null) {
			if (adress != null && BluetoothAdapter.checkBluetoothAddress(adress)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (mBtSocket.connect(mBluetoothAdapter.getRemoteDevice(adress)) == true) {
					//蓝牙模块连接后进入透传模式需要延时？
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				} else {
					Toast.makeText(this, "蓝牙连接错误!", Toast.LENGTH_SHORT).show();
					VeinApi.PrintfDebug("蓝牙连接错误!");
					return false;
				}
			}
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			if (resultCode == Activity.RESULT_OK) {
				String address = data.getExtras().getString(DeviceListActivity.DEVICE);
				VeinApi.PrintfDebug("address = " + address);
				if (!BluetoothAdapter.checkBluetoothAddress(address)) {
					VeinApi.PrintfDebug("MAC地址无效");
					finish();
				} else {
					Toast.makeText(this,"正在连接蓝牙...", Toast.LENGTH_SHORT).show();
					VeinApi.PrintfDebug("正在连接终端--------" + address);
					boolean bDevConnect = connectBTDeviec(address);
					if (bDevConnect) {
						Intent intent = new Intent();
						intent.putExtra(BTCONNECT, 1);
						setResult(Activity.RESULT_OK, intent);
						finish();
					} else {
						Intent intent = new Intent();
						intent.putExtra(BTCONNECT, 0);
						setResult(Activity.RESULT_OK, intent);
						finish();
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				finish();
			}
			break;
		case 1:
			if (resultCode == Activity.RESULT_OK) {
				VeinApi.PrintfDebug("VeinInit onActivityResult case 111111111");
				// Intent btIntent = new Intent(this, DeviceListActivity.class);
				// startActivityForResult(btIntent, 0);
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		// finish();
		super.onResume();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}