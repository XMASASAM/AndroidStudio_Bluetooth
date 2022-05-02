package wordbook_mkii.example.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BluetoothListViewAdapter extends BaseAdapter {
    List<BluetoothDevice> list = new ArrayList<>();
    Set<BluetoothDevice> set = new HashSet<>();
    List<Integer> pair = new ArrayList<>();
    AppCompatActivity nowact;

    BluetoothListViewAdapter(AppCompatActivity nowact, BluetoothAdapter mbt) {
        this.nowact = nowact;
        Set<BluetoothDevice> set2 = mbt.getBondedDevices();
        for (BluetoothDevice i : set2) {
            add(i, 1);
        }
        BtBroadcastReceiver btBroadcastReceiver = new BtBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        nowact.registerReceiver(btBroadcastReceiver, intentFilter);

        mbt.startDiscovery();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(BluetoothDevice bt, int p) {
        if (set.contains(bt)) return;
        list.add(bt);
        pair.add(p);
        set.add(bt);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = nowact.getLayoutInflater();
            view = inflater.inflate(R.layout.bluetoothlayout, null);
        }
        view.setTag(list.get(position));
        TextView t1 = view.findViewById(R.id.text1);
        TextView t2 = view.findViewById(R.id.text2);
        TextView t3 = view.findViewById(R.id.text3);

        BluetoothDevice i = list.get(position);
        String name = i.getName();
        String pa = "";
        if (i.getName() == null) name = "no_name";
        if (pair.get(position) == 1) pa = "ペア済み";
        t1.setText(name);
        t2.setText(i.getAddress());
        t3.setText(pa);
        return view;
    }
}
