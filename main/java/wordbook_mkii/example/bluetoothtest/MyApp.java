package wordbook_mkii.example.bluetoothtest;

import android.app.Application;
import android.bluetooth.BluetoothDevice;

public class MyApp extends Application {
    public BluetoothDevice device;
    OverService overService = null;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setOverService(OverService a) {
        overService = a;
    }

    public OverService getOverService(){
        return overService;
    }

}
