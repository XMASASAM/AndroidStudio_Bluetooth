package wordbook_mkii.example.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.UUID;

public class SerialPortProfileConnectThread extends BluetoothConnectThread {
//00001101-0000-1000-8000-00805F9B34FB
    // "00001101-0000-1000-8000-00805f9b34fb" = SPP (シリアルポートプロファイル) の UUID.
    public static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public SerialPortProfileConnectThread(BluetoothDevice device) {
        super(device,SPP_UUID);
       // super();
       // TAG = "SerialPortProfileConnectThread";
    }
}
