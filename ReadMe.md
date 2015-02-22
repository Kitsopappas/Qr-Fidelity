# Qr Fidelity

## Description
Is an android app that scans a specific qr code and connects to a wifi that is associated with the qr

## Code Example

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

## Code Example for the qr scanner

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ZBAR_SCANNER_REQUEST:
		case ZBAR_QR_SCANNER_REQUEST:
			if (resultCode == RESULT_OK) {
				String x = data.getStringExtra(ZBarConstants.SCAN_RESULT);
				networkSSID = getS.getSSID(x);

...}

## Text for the Qr sample
###########<ssid>networkSSID</ssid> 
###########<pass>password</pass>


## Images

#### Screen Shot
![alt tag](https://raw.githubusercontent.com/Kitsopappas/Qr-Fidelity/master/images/img1.jpg)


#### Qr example

![alt tag](https://raw.githubusercontent.com/Kitsopappas/Qr-Fidelity/master/images/qr_sample.png)
