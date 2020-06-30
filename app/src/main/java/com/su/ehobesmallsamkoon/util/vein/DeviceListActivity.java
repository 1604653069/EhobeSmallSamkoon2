/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.su.ehobesmallsamkoon.util.vein;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.xgzx.veinmanager.VeinApi;

import java.util.Set;

public class DeviceListActivity extends Activity {
	// Debugging
	private Set<BluetoothDevice> pairedDevices;
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = true;
	private boolean discoveryFlag = false; // �Ƿ����
	public static final String ACTION_NOT_FOUND_DEVICE = "ACTION_NOT_FOUND_DEVICE";
	public static final String ACTION_SELECTED_DEVICE = "ACTION_SELECTED_DEVICE";//
	public static String sDevMac = "00:BA:55:56:C4:99";//"AB:90:78:56:C5:74";
	public static String sDevName = "SPP-CA";// ����

	// Return Intent extra
	public static String DEVICE = "device_address";

	// Member fields
	private BluetoothAdapter mBtAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter discoveryFilter = new IntentFilter();
		discoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		discoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		discoveryFilter.addAction(BluetoothDevice.ACTION_FOUND);
		discoveryFilter.addAction(ACTION_NOT_FOUND_DEVICE);
		this.registerReceiver(discoveryReceiver, discoveryFilter);

		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		doDiscovery();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(discoveryReceiver);
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}
	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	private void doDiscovery() {
		pairedDevices = mBtAdapter.getBondedDevices();
		VeinApi.PrintfDebug("MAC:" + sDevMac + ",NAME:" + sDevName);
		for(BluetoothDevice bt : pairedDevices){
			VeinApi.PrintfDebug("BTDEVICE:" + bt.getName() + "-" + bt.getAddress());
			if (bt.getAddress().equals(sDevMac) || bt.getName().equals(sDevName)) {
				setBluetoothAddress(bt.getAddress());
				return;
			}
		}
		if (D)
			VeinApi.PrintfDebug("doDiscovery()");
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
		discoveryFlag = true;
		mBtAdapter.startDiscovery();
	}

	private void setBluetoothAddress(String address) {
		Intent intent = new Intent();
		intent.putExtra(DEVICE, address);
		VeinApi.PrintfDebug("address = " + address + "，转去连接蓝牙");
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private final BroadcastReceiver discoveryReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			VeinApi.PrintfDebug(action);
			if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {// ��ʼ���������豸

			} else if (BluetoothDevice.ACTION_FOUND.equals(action)) {// �ҵ������豸
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				String address = device.getAddress();
				String name;
				if (device.getName() != null) {
					name = device.getName();
				} else {
					name = "null";
				}
				VeinApi.PrintfDebug(name + "_" + device.getAddress());
				if (address.equals(sDevMac) || name.equals(sDevName)) {
					discoveryFlag = false;
					mBtAdapter.cancelDiscovery();// ȡ������
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setBluetoothAddress(address);
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {// �������
				if (discoveryFlag) {
					VeinApi.PrintfDebug("搜索完毕，没有找到蓝牙设备");
					// doDiscovery();
				}
			} else if (ACTION_NOT_FOUND_DEVICE.equals(action)) {
				VeinApi.PrintfDebug("没有找到蓝牙设备");
				doDiscovery();
			}
		}
	};
}
