package wordbook_mkii.example.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothConnectThread extends Thread {
    protected String TAG = "BluetoothConnectThread";
    InputStream inputStream;
    OutputStream outputStrem;
    protected BluetoothSocket mmSocket;

    public BluetoothSocket getSocket() {
        return mmSocket;
    }

    public BluetoothConnectThread(BluetoothDevice device, UUID uuid) {
        BluetoothSocket tmp = null;
        BluetoothAdapter ad = BluetoothAdapter.getDefaultAdapter();
        device = ad.getRemoteDevice(device.getAddress());
        try {
            tmp = device.createRfcommSocketToServiceRecord(uuid);
           Log.i("BluetoothConnectThread",device.getName());
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "Bluetooth NOOOOOT connecting.");
            // NOP.
        }
        mmSocket = tmp;

    }

    public void run() {


        if (mmSocket == null) {
            return;
        }
        while (true) {
            if (Thread.interrupted()) {
                break;
            }
            try {
                mmSocket.connect();
                inputStream = mmSocket.getInputStream();
                outputStrem = mmSocket.getOutputStream();
                while (true) {
                    if (Thread.interrupted()) {
                        break;
                    }
                    String command = "GET:TEMP";
                    outputStrem.write(command.getBytes());
                    Thread.sleep(3000);

                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                try {
                    mmSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return;
            }
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "Bluetooth connecting.");
        if(mmSocket != null){
            try {
                mmSocket.close();
            } catch (IOException e) {}
            mmSocket = null;
        }
    }
}

/*
public class BTClientThread extends Thread {

    InputStream inputStream;
    OutputStream outputStrem;
    BluetoothSocket bluetoothSocket;

    public void run() {

        byte[] incomingBuff = new byte[64];

        BluetoothDevice bluetoothDevice = null;
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice device : devices){
            if(device.getName().equals(Constants.BT_DEVICE)) {
                bluetoothDevice = device;
                break;
            }
        }

        if(bluetoothDevice == null){
            Log.d(TAG, "No device found.");
            return;
        }

        try {

            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(
                    Constants.BT_UUID);

            while(true) {

                if(Thread.interrupted()){
                    break;
                }

                try {
                    bluetoothSocket.connect();

                    handler.obtainMessage(
                            Constants.MESSAGE_BT,
                            "CONNECTED " + bluetoothDevice.getName())
                            .sendToTarget();

                    inputStream = bluetoothSocket.getInputStream();
                    outputStrem = bluetoothSocket.getOutputStream();

                    while (true) {

                        if (Thread.interrupted()) {
                            break;
                        }

                        // Send Command
                        String command = "GET:TEMP";
                        outputStrem.write(command.getBytes());
                        // Read Response
                        int incomingBytes = inputStream.read(incomingBuff);
                        byte[] buff = new byte[incomingBytes];
                        System.arraycopy(incomingBuff, 0, buff, 0, incomingBytes);
                        String s = new String(buff, StandardCharsets.UTF_8);

                        // Show Result to UI
                        handler.obtainMessage(
                                Constants.MESSAGE_TEMP,
                                s)
                                .sendToTarget();

                        // Update again in a few seconds
                        Thread.sleep(3000);
                    }

                } catch (IOException e) {
                    // connect will throw IOException immediately
                    // when it's disconnected.
                    Log.d(TAG, e.getMessage());
                }

                handler.obtainMessage(
                        Constants.MESSAGE_BT,
                        "DISCONNECTED")
                        .sendToTarget();

                // Re-try after 3 sec
                Thread.sleep(3 * 1000);
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if(bluetoothSocket != null){
            try {
                bluetoothSocket.close();
            } catch (IOException e) {}
            bluetoothSocket = null;
        }

        handler.obtainMessage(
                Constants.MESSAGE_BT,
                "DISCONNECTED - Exit BTClientThread")
                .sendToTarget();
    }
}*/