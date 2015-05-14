package com.qrlab.qrfidelity;

import net.sourceforge.zbar.Symbol;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.qrlab.qrfidelity.logic.QrFidelityReaderF;
import com.qrlab.qrfidelity.logic.ZBarConstants;
import com.qrlab.qrfidelity.logic.ZBarScannerActivity;

public class MainActivity extends Activity {
	static final String key = "XristosPappasRedone";
	private String networkSSID;
	private String pass;
	private static final int ZBAR_SCANNER_REQUEST = 0;
	private static final int ZBAR_QR_SCANNER_REQUEST = 1;
	QrFidelityReaderF getS;
	String url = "http://83.212.112.221/qrfi/products.php?cmd=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getS = new QrFidelityReaderF();

		final ImageButton connect = (ImageButton) findViewById(R.id.connect);

		// listeners
		connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				launchQRScanner(v);
			}

		});

	}

	private void connectTo(String SSID, String password) {
		WifiConfiguration wifiConfig = new WifiConfiguration();
		wifiConfig.SSID = String.format("\"%s\"", SSID);
		wifiConfig.preSharedKey = String.format("\"%s\"", password);

		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		// remember id
		int netId = wifiManager.addNetwork(wifiConfig);
		wifiManager.disconnect();
		wifiManager.enableNetwork(netId, true);
		wifiManager.reconnect();

		// WifiManager wifiManager = (WifiManager)
		// getSystemService(Context.WIFI_SERVICE);

	}

	public void launchScanner(View v) {
		if (isCameraAvailable()) {
			Intent intent = new Intent(this, ZBarScannerActivity.class);
			startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
		} else {
			Toast.makeText(this, "Rear Facing Camera Unavailable",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void launchQRScanner(View v) {
		if (isCameraAvailable()) {
			Intent intent = new Intent(this, ZBarScannerActivity.class);
			intent.putExtra(ZBarConstants.SCAN_MODES,
					new int[] { Symbol.QRCODE });
			startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
		} else {
			Toast.makeText(this, "Rear Facing Camera Unavailable",
					Toast.LENGTH_SHORT).show();
		}
	}

	public boolean isCameraAvailable() {
		PackageManager pm = getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ZBAR_SCANNER_REQUEST:
		case ZBAR_QR_SCANNER_REQUEST:
			if (resultCode == RESULT_OK) {
				String x = data.getStringExtra(ZBarConstants.SCAN_RESULT);
				networkSSID = getS.getStuff(x,QrFidelityReaderF.SSID);
				pass = getS.getStuff(x,QrFidelityReaderF.PASS);
				Log.i("ssid", networkSSID);
				Log.i("pass", pass);

				WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				if (wifiManager.isWifiEnabled()) {
					connectTo(networkSSID, pass);

				} else {
					wifiManager.setWifiEnabled(true);
					connectTo(networkSSID, pass);
				}
				
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url+getS.getStuff(x,QrFidelityReaderF.USER)));
				startActivity(i);

			} else if (resultCode == RESULT_CANCELED && data != null) {
				String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
				if (!TextUtils.isEmpty(error)) {
					Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}

}
