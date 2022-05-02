package wordbook_mkii.example.bluetoothtest;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BtBroadcastReceiver extends BroadcastReceiver {
    BluetoothListViewAdapter btlv;

    public BtBroadcastReceiver(BluetoothListViewAdapter btlv) {
        this.btlv = btlv;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
            BluetoothDevice device = intent.getParcelableExtra(
                    BluetoothDevice.EXTRA_DEVICE);


            btlv.add(device, 0);
        }
    }

}
