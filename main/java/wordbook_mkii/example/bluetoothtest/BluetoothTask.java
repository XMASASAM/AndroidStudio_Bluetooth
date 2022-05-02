package wordbook_mkii.example.bluetoothtest;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BluetoothTask extends Thread {
    public static final String TAG = "tuushinn";

    protected InputStream mInputStream;
    protected OutputStream mOutputStream;
    protected HttpRequestAsync httpRequestAsync;
    protected BluetoothSocket mSocket;

    protected volatile boolean mIsCancel;
    public Sole currentSole;
    String currentData="";
    MainActivity2 act2;
    public BluetoothTask(BluetoothSocket socket,MainActivity2 act21) {
        act2 = act21;
        httpRequestAsync = new HttpRequestAsync(act2);
        mIsCancel = false;
        mSocket = null;
        mInputStream = null;
        if (socket == null) {
            Log.e(TAG, "parameter socket is null.");
            return;
        }

        try {
            mInputStream = socket.getInputStream();
            mOutputStream = socket.getOutputStream();
            mSocket = socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    boolean flag2 = false;
  //  List<String> sss = new ArrayList<>();
    String buffer_string;
    int current_buffer=0;
    public void run() {
        byte[] buffer = new byte[102400];
        byte[] buffer_buffer = new byte[102400];
        int readSize;
        if(mOutputStream==null) {
            Log.v("tuushinn", "mOutputStream=null");
        }

        Log.v(TAG, "start read task.II");
        while (true) {
            if (mIsCancel) {
                break;
            }
           // connectBluetooth();
            try {
                mOutputStream.write(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
      //      Log.v(TAG,"SizeXXX:");
            try {
                readSize = mInputStream.read(buffer);

       //         Log.v(TAG,"Size:" + readSize);
                String data =new String(buffer, 0, readSize);

                    if( data!=null&&data.length()>0) {
                        Log.v(TAG,"Data:" +data );
                        if(flag2){
                            if(data.equals("2")) {
                                Log.v(TAG,"2dayo2");
                                byte[] bbb = new byte[current_buffer];
                                for(int uuu=0;uuu<current_buffer;uuu++){
                                    bbb[uuu] = buffer_buffer[uuu];
                                }
                                Log.v(TAG,"2dayo3");
                                current_buffer=0;
                                httpRequestAsync.setByte(bbb);
                                httpRequestAsync.execute();
                                buffer_string="";
                                //Log.v(TAG,"Datakimasita:" +data );
                                flag2=false;
                                Log.v(TAG,"2dayo4");
                            }else{
                                buffer_string += data;
                                for(int uu=0;uu<readSize;uu++){
                                    buffer_buffer[uu+current_buffer] = buffer[uu];
                                }
                                current_buffer+=readSize;
                            }
                    /*
                    */
                        }
                     //   currentData += data;
                        if(data.equals("1")&&!flag2){
                            act2.setTextVqwq("得点計算中");
                            Log.v(TAG,"Datakimasita:" +data );
                            //Thread.sleep(1000);
                            flag2=true;
                         /*   readSize = mInputStream.read(buffer);
                            httpRequestAsync.setByte(buffer);
                            httpRequestAsync.execute();
                            Log.v(TAG,"Datakimasita:" +data );*/
                        }
                        if(!flag2) {

                            Sole sub = makeSole(data);
                            currentSole = null;
                            if (sub != null) {
                                currentSole = sub;
                                act2.setNewSole(sub);

                            }

                            sub = null;
                        }
                    }
                    data = null;
               // mOutputStream.write(1);
                StringBuilder sb = new StringBuilder();
                Thread.sleep(10);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                Log.e(TAG, "InterruptedException!!");
                // NOP.
                break;
            }
      //      Log.v(TAG,"SizeXXX55555:");

        }
        Log.v(TAG, "exit read task.");

        finish();
    }
    public boolean finishflag = false;
    void connectBluetooth(){
        //while (mInputStream == null) {

            try {
       //         mInputStream = mSocket.getInputStream();
                mInputStream = mSocket.getInputStream();
                mOutputStream = mSocket.getOutputStream();
                mOutputStream.write(1);
             //   Log.v(TAG, "start read task.");
            } catch (IOException e) {
                e.printStackTrace();
            }

       // }
    }

    public void cancel() {
        mIsCancel = true;
    }

    public void finish() {
        finishflag = true;
        if (mSocket == null) {
            return;
        }

        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String subData="";
    int flag=0;
    public Sole makeSole(String data){
        Sole s=null;
        for (int i=0;i<data.length();i++){
            char ch = data.charAt(i);
            if(ch=='['){
                flag=1;
                subData="";
            }

            if(flag==1){
                subData+=ch;
            }

            if(flag==1&&ch==']'){
                flag=0;
                s= new Sole(subData);
            }

        }

        return s;
    }

}

class HttpRequestAsync extends AsyncTask<Void, Void, String> {

    MainActivity2 main_ = null;
    String path;
    byte[] imageDate;
    HttpRequestAsync(MainActivity2 mainActivity){
        main_ = mainActivity;
    }
    public  void setByte(byte[] data){
        imageDate = data;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // doInBackground前処理
    }

    @Override
    protected String doInBackground(Void... params) {
        URL url = null;
        StringBuilder sb = new StringBuilder();
        HttpURLConnection conn = null;
        try {
            url = new URL("https://dcon22-akashi-nct-intel.api.abeja.io/deployments/2716898040578/services/ser-d62b0dc806e64fe4");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
           // main_.setText("転送の準備");
            // ヘッダ設定
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "image/png");
            conn.setRequestProperty("Authorization","Basic dXNlci0yNTc2ODU5MTMyMDY0OjNmZmI0ZThlYzM4N2FhNTY3NDE0MTUyZDViMDJhMTJiNzY3OTI0ZmQ=");

            conn.connect();
            // データを投げる
            //main_.setText("画像データ送信");
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());

            out.write(imageDate);

            out.flush();
           // main_.setText("結果受信中");
            // データを受け取る
            InputStream is = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line = "";

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(conn!=null)
                conn.disconnect();
        }
        return sb.toString();

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // doInBackground後処理
        main_.setText(result);
    }

}