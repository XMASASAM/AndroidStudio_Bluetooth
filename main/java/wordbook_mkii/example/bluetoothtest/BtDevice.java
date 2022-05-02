package wordbook_mkii.example.bluetoothtest;

import android.bluetooth.BluetoothDevice;

import java.io.Serializable;

public class BtDevice implements Serializable {
    BluetoothDevice device;
    public  BtDevice(BluetoothDevice device){
        this.device = device;
    }

    public  BluetoothDevice get(){
        return device;
    }
}
