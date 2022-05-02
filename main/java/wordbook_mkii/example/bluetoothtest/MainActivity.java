package wordbook_mkii.example.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.Inflater;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter mBluetoothAdapter;
    BluetoothTask mReceiveTask;
    List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  LinearLayout layout = new LinearLayout(this);
      //  layout.addView(getLayoutInflater().inflate(R.layout.activity_main,null));

      //  setContentView(layout);
        setContentView(R.layout.activity_main);
       // layout.addView(new GraphTest(this));
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        TextView text1 = findViewById(R.id.text1);
        TextView text2 = findViewById(R.id.text2);

        if (mBluetoothAdapter == null) {
            text1.setText("Nothing");
            // エラー: Bluetooth なし.
            Log.v("BletoothDevice", "nothing");
            return;
        } else {
            ListView listView = findViewById(R.id.ListView);
            listView.setAdapter(new BluetoothListViewAdapter(this, mBluetoothAdapter));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    makeDialog mD = new makeDialog(MainActivity.this, getLayoutInflater(), savedInstanceState) {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            MyApp myapp = (MyApp) MainActivity.this.getApplication();
                            myapp.device = (BluetoothDevice) ((BluetoothListViewAdapter)listView.getAdapter()).getItem(position);
                            View v = makeView(R.layout.bluetooth_dialog);
                            builder = onCleate(v);
                            builder.setView(v);
                            BluetoothDevice bt = (BluetoothDevice) v.getTag();
                            TextView t1 = v.findViewById(R.id.text1);
                            TextView t2 = v.findViewById(R.id.text2);
                            t1.setText(((TextView) view.findViewById(R.id.text1)).getText());
                            t2.setText(myapp.device.getAddress());

                            builder.setPositiveButton("接続", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mBluetoothAdapter.cancelDiscovery();
                                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                 //   intent.putExtra("bluetoothDevice", new BtDevice((BluetoothDevice) view.getTag(position)));

                                  //  myapp.device = (BluetoothDevice) ((BluetoothListViewAdapter)listView.getAdapter()).getItem(position);
                                    MainActivity.this.startActivity(intent);

                                }
                            });

                            builder.setNegativeButton("戻る", null);
                            builder.setTitle("BluetoothDevice");

                            return builder.create();
                        }
                    };
                    mD.show();
                }
            });
        }
        WeakReference<TextView> aaa1 = new WeakReference<>(findViewById(R.id.text1));
        WeakReference<TextView> aaa2 = new WeakReference<>(findViewById(R.id.text2));
        AbejaAPI abejaAPI = new AbejaAPI(aaa1,aaa2);
       // abejaAPI.doInBackground("");
    }
}


