package wordbook_mkii.example.bluetoothtest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jtransforms.fft.DoubleFFT_1D;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {
    private BluetoothTask mReceiveTask;
    MyApp mApp;
    TextView textV11;
    TextView textV1;
    TextView textV2;
    TextView textV3;
    TextView textVH;
    GraphSole graphSole;
    GraphAshi graphAshi;
    RecodingButton recoding;
    MinWaveform waveformA=null;
    MinWaveform waveformB=null;
    MinWaveform waveformC=null;
    //EditText editText;
    //DNN deeplearning;
    DNN ashi1;
    DNN ashi2;
    DNN ashi3;
    Handler handler;
    Bundle bundle;
    hamteiclass hantei11;
    hamteiclass hantei22;
    hamteiclass hantei33;
   // SerialPortProfileConnectThread connectTread;
    static private final int flame_size = 16;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
        setContentView(R.layout.activity_main2);
     //   Intent intent = getIntent();
        MyApp app = (MyApp)getApplication();
        mApp = app;
        handler = new Handler(Looper.getMainLooper());
        BluetoothDevice device = app.device;
        //SerialPortProfileConnectThread connectTread = new SerialPortProfileConnectThread(device);
    //    ConnectBluetooth(device);
        /*(
                (BtDevice)getIntent().getSerializableExtra("bluetoothDevice")
        ).get();*/
   //     if(device==null){
   //         Log.v("BluetoothDevice","notinh");
    //        return;
    //    }

        SerialPortProfileConnectThread connectTread = new SerialPortProfileConnectThread(device);
        Log.i("BluetoothConnectThread",device.getName());
        connectTread.start();
        Log.v("tuusnihn",connectTread.toString());

// 接続完了を待つ.
// 下記は sleep() を使用した暫定処理です.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        mReceiveTask = new BluetoothTask(connectTread.getSocket(),this);
        mReceiveTask.start();
        Log.v("tuusnihn",mReceiveTask.toString());

        textV1 = findViewById(R.id.text1);
        textV11 = findViewById(R.id.setV);
        textV2 = findViewById(R.id.text2);
        textV3 = findViewById(R.id.text3);
        textVH = findViewById(R.id.hosuu);

        graphSole = findViewById(R.id.GraphSole);
        graphAshi = findViewById(R.id.GraphAshi);
        recoding = new RecodingButton(findViewById(R.id.button),savedInstanceState);
       // editText = findViewById(R.id.EditText);
        OverService.init(mApp,this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                OverService.init(mApp,MainActivity2.this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      //  displayWave = new MinWaveform2(frame_size, Color.RED);
                    }
                }, 100);

               // StringBuilder a = new StringBuilder();
                /* 3,000ms後に行いたい処理 */
           //     waveformA = new MinWaveform("a",flame_size, (int) (flame_size*0.3),Color.RED);
           //     waveformB = new MinWaveform("b",flame_size, (int) (flame_size*0.3),R.color.green);
           //     waveformC = new MinWaveform("c",flame_size, (int) (flame_size*0.3),R.color.purple_500);
            }
        }, 100);

        Log.v("tuusnihn",connectTread.toString());
        loop();
        //deeplearning = new DNN((frame_size*3)>>1,100,100,80,60,30,10,5,3);
        CVS_Reader cvsReader = new CVS_Reader();

        ashi1 = cvsReader.reader(this,"weight_1.csv");
        ashi2 = cvsReader.reader(this,"weight_2.csv");
        ashi3 = cvsReader.reader(this,"weight_3.csv");
        hantei11 = new hamteiclass(0);
        hantei22 = new hamteiclass(1);
        hantei33 = new hamteiclass(2);
// 接続完了を待つ.

// 下記は sleep() を使用した暫定処理です.

    //    mReceiveTask = new BluetoothTask(connectTread.getSocket(),this);
  //      mReceiveTask.start();

    //    if (mReceiveTask != null) {
   //         mReceiveTask.cancel();
  //      }

// キャンセル完了を待つ処理など.

     //   if (mReceiveTask != null) {
      //      mReceiveTask.finish();
     //       mReceiveTask = null;
     //   }


   /*     new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!atattchFlag){
                    ConnectBluetooth(app.device);

                }
            }
        },20000);*/
    }
/*
    OverService overService = new OverService(){
        @Override
        public void loop(){
            if(!atattchFlag){
                mReceiveTask.start();
            }
        }
    };*/

    void Connect(){
       // connectTread = new SerialPortProfileConnectThread(mApp.device);
      //  connectTread.start();
     //   mReceiveTask = new BluetoothTask(connectTread.getSocket(),this);
        mReceiveTask.start();
    }
    void ConnectBluetooth(BluetoothDevice device){
    //    BluetoothDevice device = app.device; (
   //             (BtDevice)getIntent().getSerializableExtra("bluetoothDevice")
   //     ).get();
        if(device==null){
            Log.v("BluetoothDevice","notinh");
            return;
        }

        SerialPortProfileConnectThread connectTread = new SerialPortProfileConnectThread(device);
        Log.i("BluetoothConnectThread",device.getName());
        connectTread.start();
       // connectTread.start();
   //     try {
    //        Thread.sleep(20000);
   //     } catch (InterruptedException e) {
   //         e.printStackTrace();
  //      }

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                if(connectTread.getSocket()==null){
                    ConnectBluetooth(device);
                    Log.v("tuushinn","not bluetooth device");
                }else{
                    mReceiveTask = new BluetoothTask(connectTread.getSocket(),MainActivity2.this);
                    Log.v("tuushinn","not bluetooth device1");
                    mReceiveTask.start();
                    Log.v("tuushinn","not bluetooth device2");
                   // OverService.init((MyApp) getApplication(),MainActivity2. this);
                    OverView.init(mApp);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /* 3,000ms後に行いたい処理 */
                 //           waveformA = new MinWaveform("a",flame_size, (int) (flame_size*0.3),Color.RED);
                  //          waveformB = new MinWaveform("b",flame_size, (int) (flame_size*0.3),R.color.green);
                 //           waveformC = new MinWaveform("c",flame_size, (int) (flame_size*0.3),R.color.purple_500);
                        }
                    }, 1000);


                }
            }
        },20000);
    }
    boolean loopFlag=true;
    void loop(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mReceiveTask.finishflag&&loopFlag){
                    loopFlag=false;
                    SerialPortProfileConnectThread connectTread =
                            new SerialPortProfileConnectThread(mApp.device);
                    connectTread.start();
                    //while (connectTread.getState()== Thread.State.WAITING);
// 接続完了を待つ.
// 下記は sleep() を使用した暫定処理です.
                    try {
                        Thread.sleep(2000);
                        mReceiveTask = new BluetoothTask(connectTread.getSocket(), MainActivity2.this);
                        mReceiveTask.start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                    loopFlag=true;
                }
                loop();
            }
            },10);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mReceiveTask != null) {
            mReceiveTask.cancel();

        }

// キャンセル完了を待つ処理など.

        if (mReceiveTask != null) {
            mReceiveTask.finish();
            mReceiveTask = null;
        }
    }
    boolean atattchFlag=false;
    public  void setNewSole(Sole sole){
        atattchFlag = true;

        try {
            final Handler mainHandler = new Handler(Looper.getMainLooper());
            /* 処理 */
            mainHandler.post(() -> {
                Update(sole);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Update(Sole sole){
       // textV1.setText(sole.toString());
   //     if(String.valueOf(editText.getText())!=null)
     //       graphSole.sX = Integer.parseInt(String.valueOf(editText.getText()));
        int nokori = Math.max(120 - (int)sole.time,0);
        setTextVqwq("計算開始まであと："+String.valueOf(nokori)+"秒");
        graphSole.sX = 100;
        graphSole.setSole(sole);
        graphAshi.setCurrentSole(sole);
        if(recoding.getOnOff()){
            recoding.setDataln(sole.toStringCSV());
            Log.v("recoding",sole.toStringCSV());
        }
        if(waveformA!=null)waveformA.Update(sole.time,sole.a);
        if(waveformB!=null)waveformB.Update(sole.time,sole.b);
        if(waveformC!=null)waveformC.Update(sole.time,sole.c);
        Hantei(sole);
        //sole=null;
    }

    final static int frame_size=30;
    final static int cut_size=10;
    List<Sole> soleBuffer = new ArrayList<>();
  //  fft = new DoubleFFT_1D(size);
  //  hamming = new Hamming(size);
  DoubleFFT_1D fft = new DoubleFFT_1D(frame_size);
   // DoubleFFT_1D fft2 = new DoubleFFT_1D(frame_size>>1);
    double[] test_fdata = new double[frame_size];
    Hamming hamming = new Hamming(frame_size);
    int current_sole_buffer=0;
    double[] fdata1_h = new double[frame_size];
    double[] fdata2_h = new double[frame_size];
    double[] fdata3_h = new double[frame_size];
    double[] input_data_h = new double[(frame_size*3)];
    MinWaveform2 displayWave = null;
    double max_sole1=0;
    double max_sole2=0;
    double max_sole3=0;
    double[] input_data = new double[12];


    class hamteiclass{
        int flag=0;
        boolean flag2=false;
        boolean flag3=false;
        public boolean change = false;
        List<Sole> totest = new ArrayList<>();
        public boolean hosuu=false;
        public  hamteiclass(int flag){
            this.flag = flag;
        }

        public  void set(Sole sole){
            hosuu = false;
            Log.v("deep","dddddd222");
            if((sole.a / max_sole1 >= 0.3 && flag==0) ||
                    (sole.b / max_sole2 >= 0.3 && flag==1) ||
                    (sole.c / max_sole3 >= 0.3 && flag==2) ) {
                Log.v("deep","dddddd");
                if(flag2)hosuu = true;
                else hosuu = false;
                //hosuu = true;
                totest.add(sole);
                flag2 = true;
                flag3 = false;
            }else {
                flag3 = true;

            }
            Log.v("deep","dddddd333");
            if(flag3){
                change=false;
                if(flag2){
                    flag2 = false;
                    int distance_time_max12 = 0;
                    double distance_value_max12=0;
                    int distance_time_max13 = 0;
                    double distance_value_max13=0;
                    int distance_time_max23 = 0;
                    double distance_value_max23=0;
                    double distance12;
                    double distance13;
                    double distance23;
                    double sum_12=0;
                    double sum_13=0;
                    double sum_23=0;
                    if(totest.size()>=10){
                        for(int j=0;j<totest.size();j++){
                            distance12 = totest.get(j).a - totest.get(j).b;
                            distance13 = totest.get(j).a - totest.get(j).c;
                            distance23 = totest.get(j).b - totest.get(j).c;
                            sum_12 += totest.get(j).a;// - dd2[j];
                            sum_13 += totest.get(j).b;// - dd3[j];
                            sum_23 += totest.get(j).c;// - dd3[j];
                            if(Math.abs(distance_value_max12) < Math.abs(distance12)){
                                distance_value_max12 = distance12;
                                distance_time_max12 = j;
                            }
                            if(Math.abs(distance_value_max13) < Math.abs(distance13)){
                                distance_value_max13 = distance13;
                                distance_time_max13 = j;
                            }
                            if(Math.abs(distance_value_max23) < Math.abs(distance23)){
                                distance_value_max23 = distance23;
                                distance_time_max23 = j;
                            }

                            double ave12 = sum_12 / totest.size();
                            double ave13 = sum_13 / totest.size();
                            double ave23 = sum_23 / totest.size();

                            double sum_distributed1 = 0;
                            double sum_distributed2 = 0;
                            double sum_distributed3 = 0;
                            for(j=0;j<totest.size();j++){
                                sum_distributed1+=(totest.get(j).a - ave12)*(totest.get(j).a - ave12);
                                sum_distributed2+=(totest.get(j).b - ave12)*(totest.get(j).b - ave12);
                                sum_distributed3+=(totest.get(j).c - ave12)*(totest.get(j).c - ave12);
                               // sum_distributed2+=(dd2[j] - ave13)*(dd2[j] - ave13);
                               // sum_distributed3+=(dd3[j] - ave23)*(dd3[j] - ave23);
                            }


                            double dis_22 = sum_distributed2 / totest.size();
                            double dis_33 = sum_distributed3 / totest.size();

                            double max_time1 = distance_time_max12/(double)(totest.size());
                            double max_value1 = distance_value_max12;
                            double ave_1 = ave12;
                            double dis_1 = sum_distributed1 / totest.size();

                            double max_time2 = distance_time_max13/(double)(totest.size());
                            double max_value2 = distance_value_max13;
                            double ave_2 = ave13;
                            double dis_2 = sum_distributed2 / totest.size();

                            double max_time3 = distance_time_max23/(double)(totest.size());
                            double max_value3 = distance_value_max23;
                            double ave_3 = ave23;
                            double dis_3 = sum_distributed3 / totest.size();

                            input_data[0] = max_time1;
                            input_data[1] = max_value1;
                            input_data[2] = ave_1;
                            input_data[3] = dis_1;
                            input_data[4] = max_time2;
                            input_data[5] = max_value2;
                            input_data[6] = ave_2;
                            input_data[7] = dis_2;
                            input_data[8] = max_time3;
                            input_data[9] = max_value3;
                            input_data[10] = ave_3;
                            input_data[11] = dis_3;
                            if(flag==0) {
                                ashi1.forward(input_data);
                                ashi1.softMax();
                            }else if(flag==1){
                                ashi2.forward(input_data);
                                ashi2.softMax();
                            }else if(flag==2){
                                ashi3.forward(input_data);
                                ashi3.softMax();
                            }
                            change = true;
                        }


                    }

                    totest.clear();
                }
                flag2 = false;
            }
        }
    }

    int hosuuu=0;
    int notcount=0;
    void Hantei(Sole sole){
        max_sole1=Math.max(max_sole1,sole.a);
        max_sole2=Math.max(max_sole2,sole.b);
        max_sole3=Math.max(max_sole3,sole.c);
        hantei11.set(sole);
        hantei22.set(sole);
        hantei33.set(sole);
        Log.v("deep","kiteruyo");
        if(hantei11.change){
            textV1.setText(getString(R.string.data1)+String.valueOf(ashi1.output[0]));
            textV2.setText(getString(R.string.data2)+String.valueOf(ashi1.output[1]));
            textV3.setText(getString(R.string.data3)+String.valueOf(ashi1.output[2]));
        }else if(hantei22.change){
            textV1.setText(getString(R.string.data1)+String.valueOf(ashi2.output[0]));
            textV2.setText(getString(R.string.data2)+String.valueOf(ashi2.output[1]));
            textV3.setText(getString(R.string.data3)+String.valueOf(ashi2.output[2]));
        }else if(hantei33.change){
            textV1.setText(getString(R.string.data1)+String.valueOf(ashi3.output[0]));
            textV2.setText(getString(R.string.data2)+String.valueOf(ashi3.output[1]));
            textV3.setText(getString(R.string.data3)+String.valueOf(ashi3.output[2]));
        }
        notcount = Math.max(--notcount,0);
        if(hantei11.hosuu&&notcount<=0){
            hosuuu++;
            notcount=60;
        }
        if(hantei22.hosuu&&notcount<=0){
            hosuuu++;
            notcount=60;
        }
        if(hantei33.hosuu&&notcount<=0){
            hosuuu++;
            notcount=60;
        }
        textVH.setText(MainActivity2.this.getString(R.string.walk) + ":" + String.valueOf(hosuuu));
   /*     soleBuffer.add(sole);
        if(soleBuffer.size()<frame_size)return;
        current_sole_buffer=0;
        int i,j,k;

        for (i=0;i<frame_size;i++){
            fdata1_h[i] = soleBuffer.get(i).a * hamming.getK(i);
            fdata2_h[i] = soleBuffer.get(i).b * hamming.getK(i);
            fdata3_h[i] = soleBuffer.get(i).c * hamming.getK(i);
        }
        fft.realForward(fdata1_h);
        fft.realForward(fdata2_h);
        fft.realForward(fdata3_h);

        int step = frame_size;
        for(i=0;i<step;i++) {
            input_data_h[i] = fdata1_h[i];
          //  test_fdata[i] = fdata1_h[i];
            input_data_h[i+step] = fdata2_h[i];
            input_data_h[i+(step<<1)] = fdata3_h[i];
        }
        for(i=0;i<frame_size;i++){
            test_fdata[i] = fdata1_h[i]; ;//* hamming.getK(i);
        }
        fft.realInverse(test_fdata,true);

     //   deeplearning.forward(input_data_h);
     //   deeplearning.softMax();
     //   textV1.setText(String.valueOf((float)deeplearning.output[0]));
        if(displayWave!=null&&displayWave.getWidth()!=0){
            displayWave.Update(test_fdata);
            //Thread.sleep(1000);
        }else{
           // displayWave = new MinWaveform2(frame_size, Color.RED);
        }

        for(i=0;i<cut_size;i++)
            soleBuffer.remove(0);*/
    }

    class RecodingButton{
        Button button;
        boolean onoff=false;
        String textdata="";
        MakeFile makeFile;
        makeDialog dialog;
        SaveConfirmationDialog savedialog;
        String path="";
        public RecodingButton(Button button,Bundle bund) {
            this.button = button;
            savedialog =
                    new SaveConfirmationDialog(MainActivity2.this,MainActivity2.this.getLayoutInflater(),bund);
//            path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()+"/";//Environment.getExternalStorageDirectory().getPath()+"/";//"/"+getString( R.string.product_name);
            path = getExternalFilesDir(Environment.MEDIA_SHARED).toString()+"/";//Environment.getExternalStorageDirectory().getPath()+"/";//"/"+getString( R.string.product_name);
            makeFile = new MakeFile();

            this.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonOnOff();
                }
            });
        }


        void buttonOnOff(){
            if(onoff)savedialog.show();
            else buttonOn();
        }

        void buttonOn(){

            onoff=true;
            textdata="";
            button.setText(getString(R.string.recodingnow));
        }

        void buttonOff(){
            onoff=false;
            textdata="";
            button.setText(getString(R.string.recoding));
        }

        void setDataln(String text){
            textdata+=text + "\n";

        }

        boolean getOnOff(){
            return onoff;
        }

        class SaveConfirmationDialog extends  makeDialog{

            public SaveConfirmationDialog(AppCompatActivity a, LayoutInflater b, Bundle saveInstance) {
                super(a, b, saveInstance);
            }
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                View v = makeView(R.layout.csv_data_sava_dialog);
                builder = onCleate(v);
                builder.setView(v);

                builder.setTitle("記録の保存");
                TextView textView = v.findViewById(R.id.text);
                EditText editText = v.findViewById(R.id.EditText);

                textView.setText("保存場所："+path);
                editText.setText(getCalenderTime());
                v.findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            makeFile.save(path +editText.getText()+".csv",textdata);
                            Log.v("recoding","setData:"+textdata);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        buttonOff();
                        currentDialog.dismiss();
                    }
                });

                v.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonOff();
                        currentDialog.dismiss();
                    }
                });

                v.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentDialog.dismiss();
                    }
                });

                return builder.create();
            }

            String getCalenderTime(){
                Calendar cal = Calendar.getInstance();       //カレンダーを取得
                int iYear = cal.get(Calendar.YEAR);         //年を取得
                int iMonth = cal.get(Calendar.MONTH);       //月を取得
                int iDate = cal.get(Calendar.DATE);         //日を取得
                int iHour = cal.get(Calendar.HOUR);         //時を取得
                int iMinute = cal.get(Calendar.MINUTE);    //分を取得
                int iSecond = cal.get(Calendar.SECOND);    //分を取得
                String strDay = iYear + "年" + iMonth + "月" + iDate + "日";     //日付を表示形式で設定
                String strTime = iHour + "時" + iMinute + "分" + iSecond + "秒"; //時刻を表示形式で設
                return strDay + strTime;
            }

        }

    }
    public  void setTextVqwq(String text){
        handler.post(() -> {
            textV11.setText(text);
        });
    }
    String souhou;
    String advice;
    public  void setText(String text){
        try {
            /* 処理 */
            handler.post(() -> {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(text);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    String text22 = jsonArray.getJSONObject(0).getString("probability");
                    String text23 = jsonArray.getJSONObject(1).getString("probability");
                    String text24 = jsonArray.getJSONObject(2).getString("probability");
                    textV1.setText(getString(R.string.data1)+text22);
                    textV2.setText(getString(R.string.data2)+text23);
                    textV3.setText(getString(R.string.data3)+text24);
                    setTextVqwq("走行中");
                    double toku1 = Double.parseDouble(text22);
                    double toku2 = Double.parseDouble(text23);
                    double toku3 = Double.parseDouble(text24);
                    double max_toku = Math.max(toku1,toku2);
                    max_toku = Math.max(toku3,max_toku);
                    int gggg=0;
                   // String souhou;
                  //  String advice;
                    Random random = new Random();
                    int rand = random.nextInt(10);
                    if(toku1>=toku2&& toku1>=toku3){
                        gggg=0;
                        souhou = MainActivity2.this.getString(R.string.data1);
                        if(rand%3 == 0)
                        advice = "ヒール着地が多くなっています。足裏全体を使って体を水平移動させるイメージで走りましょう！";
                        else if(rand%3==1)
                            advice = "身体の軸を真っ直ぐ保つことを意識して見ましょう。";
                        else
                            advice = "少し休憩を挟みましょう";
                    }else if(toku2>=toku1 && toku2>=toku3){
                        gggg=1;
                        souhou = MainActivity2.this.getString(R.string.data2);
                        if(rand%2 == 0)
                        advice = "良い着地です！その感覚を忘れずに！";
                        else if(rand%2 ==1)
                            advice = "その調子！";
                    }else if(toku3>=toku1 && toku3>=toku2){
                        gggg=2;
                        souhou = MainActivity2.this.getString(R.string.data3);
                        if(rand%2==0)
                        advice="もっと身体を起こして、真っ直ぐ正面を向いて走ってみましょう";
                        else if(rand%2==1)
                            advice="足の裏を地面に付けて走りましょう";
                    }

                    makeDialog mD = new makeDialog(MainActivity2.this, getLayoutInflater(), bundle) {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            MyApp myapp = (MyApp) MainActivity2.this.getApplication();
                        //    myapp.device = (BluetoothDevice) ((BluetoothListViewAdapter)listView.getAdapter()).getItem(position);
                            View v = makeView(R.layout.bluetooth_dialog);
                            builder = onCleate(v);
                            builder.setView(v);
                         //   BluetoothDevice bt = (BluetoothDevice) v.getTag();
                            TextView t1 = v.findViewById(R.id.text1);
                            TextView t2 = v.findViewById(R.id.text2);

                            t1.setText(souhou+":");
                            t2.setText(advice);

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });

                          //  builder.setNegativeButton("戻る", null);
                            builder.setTitle("アドバイス");

                            return builder.create();
                        }
                    };
                    mD.show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


class MinWaveform{
    ArrayList<PointF> mdata;
    int current=0;
    int msize=0;
    //OverView ov;
    GraphLine gl;
   // GraphLine gl2;

    PathPaint pp;
    PathPaint pp2;
    PathPaint pp3;
    ArrayList<Double> fdata;
    DoubleFFT_1D fft;
    Hamming hamming;

    int over_buffer_size=0;
    int over_current_size=0;

    public  MinWaveform(String name,int size,int over,int color){
        mdata = new ArrayList<>(size);
        msize = size;
        over_buffer_size = over;
        OverView ov = new OverView(R.layout.cut_waveform);

        gl = ov.getLayout().findViewById(R.id.GraphLine);
        ((Button)ov.getLayout().findViewById(R.id.power)).setText(name);
     //   gl.setTitle("元の波形");
       // ov = new OverView(R.layout.cut_waveform);
       // gl2 = ov.getLayout().findViewById(R.id.GraphLine);
      //  ((Button)ov.getLayout().findViewById(R.id.button)).setText("フーリエ逆変換&周波数スペクトル");
     //   gl2.setTitle("フーリエ逆変換&周波数スペクトル");
        pp = new PathPaint(color);
        pp2 = new PathPaint(Color.BLACK);
        pp3 = new PathPaint(Color.BLUE);
        fdata = new ArrayList<>();
        fft = new DoubleFFT_1D(size);
        hamming = new Hamming(size);
    }


    public  void Update(double time,double data){
      //  mdata.set(current,new PointF(time,data));
      //  fdata[current] = Math.log( data + 1E-7);
      //  current++;
      //  current%=msize;
        fdata.add((double) data);

        if(fdata.size()>msize){
            fdata.remove(0);
            over_current_size++;
        }

        //if(current==0){
       // if(fdata.size()>=msize){
        if(over_current_size>=over_buffer_size){
            over_current_size=0;
       /*     pp.setArray(mdata);
            gl.DrawPathPaint(pp);
            mdata.clear();
            pp.reset();*/
           // Double[] fdata2 = fdata.toArray(new Double[msize]);//fdata.clone();
            double[] fdata1 = new double[msize];
            double[] fdata2;// = new double[msize];
            for (int i=0;i<msize;i++){
                fdata1[i] = fdata.get(i) * hamming.getK(i);
//                fdata1[i] =  hamming.getK(i);
            }
          //  fdata1 = ((Double[]) fdata.clone());
          // fdata2 = fdata1.clone();
            fft.realForward(fdata1);

            double[] fdata3 = fdata1.clone();

            fft.realInverse(fdata1,true);

            double maxf = 1E-7;
            double maxf2 = 1E-7;
            double maxf3 = 1E-7;

            for(int i=0;i<msize;i++){
                maxf =  Math.max(maxf,fdata1[i]);
                maxf2 =  Math.max(maxf2,fdata.get(i));
                fdata3[i] = Math.abs(fdata3[i]);
                maxf3 = Math.max(maxf3,fdata3[i]);
            }

            double scaleX = gl.getWidth() / (double)msize;
            double scaleY = gl.getHeight() / maxf;
            double scaleY2 = gl.getHeight() / maxf2;
            double scaleY3 = gl.getHeight()*0.5 / maxf3;
            double heightD2 = gl.getHeight()*0.5;
            pp.reset();
            pp2.reset();
            pp3.reset();

        //    pp.moveTo(0,gl.getHeight());
        //    pp2.moveTo(0,gl2.getHeight());
            for(int i=0;i<msize;i++){
                if(i==0){
                    pp.moveTo((float) (i * scaleX), (float) (gl.getHeight() - fdata.get(i) * scaleY2));
                    pp2.moveTo((float) (i * scaleX), (float) (gl.getHeight() - fdata1[i] * scaleY));
                 //   pp3.moveTo(i*scaleX,heightD2,i*scaleX,heightD2 + fdata3[i]*scaleY3);
                }else {
                    pp.setPoint((float) (i * scaleX), (float) (gl.getHeight() - fdata.get(i) * scaleY2));
                    pp2.setPoint((float) (i * scaleX), (float) (gl.getHeight() - fdata1[i] * scaleY));
                    pp3.setLine(i*scaleX,heightD2,i*scaleX,heightD2 - fdata3[i]*scaleY3);
                }
            }
            gl.DrawPathPaint(pp,pp3,pp2);
           // gl2.DrawPathPaint(pp,pp3,pp2);
          //  gl2.DrawPathPaint(pp2);
        }
    }




}


class MinWaveform2{
    PathPaint pp;
    PathPaint pp2;
    PathPaint pp3;
    //double[] fdata;
    GraphLine gl;
    int msize;
    public  MinWaveform2(int size,int color){
        //fdata = data;
        msize = size;
        OverView ov = new OverView(R.layout.cut_waveform);

        gl = ov.getLayout().findViewById(R.id.GraphLine);
       // ((Button)ov.getLayout().findViewById(R.id.power)).setText(name);
        //   gl.setTitle("元の波形");
        // ov = new OverView(R.layout.cut_waveform);
        // gl2 = ov.getLayout().findViewById(R.id.GraphLine);
        //  ((Button)ov.getLayout().findViewById(R.id.button)).setText("フーリエ逆変換&周波数スペクトル");
        //   gl2.setTitle("フーリエ逆変換&周波数スペクトル");
        pp = new PathPaint(color);
        pp2 = new PathPaint(Color.BLACK);
        pp3 = new PathPaint(Color.BLUE);
    }
    public  int getWidth(){
        return gl.getWidth();
    }
    public  void Update(double[] fdata){
        //  mdata.set(current,new PointF(time,data));
        //  fdata[current] = Math.log( data + 1E-7);
        //  current++;
        //  current%=msize;

            double maxf = 1E-7;
            double maxf2 = 1E-7;
            double maxf3 = 1E-7;

            for(int i=0;i<msize;i++){
                maxf =  Math.max(maxf,fdata[i]);

            }//

            double scaleX = gl.getWidth() / (double)msize;
            double scaleY = gl.getHeight() / maxf;
            double scaleY2 = gl.getHeight() / maxf2;
            double scaleY3 = gl.getHeight()*0.5 / maxf3;
            double heightD2 = gl.getHeight()*0.5;
            pp.reset();
            pp2.reset();
            pp3.reset();

            //    pp.moveTo(0,gl.getHeight());
            //    pp2.moveTo(0,gl2.getHeight());
            for(int i=0;i<msize;i++){
                if(i==0){
                    pp.moveTo((float) (i * scaleX), (float) (gl.getHeight() - fdata[i] * scaleY));
                //    pp2.moveTo((float) (i * scaleX), (float) (gl.getHeight() - fdata1[i] * scaleY));
                    //   pp3.moveTo(i*scaleX,heightD2,i*scaleX,heightD2 + fdata3[i]*scaleY3);
                }else {
                    pp.setPoint((float) (i * scaleX), (float) (gl.getHeight() - fdata[i] * scaleY));
                //    pp2.setPoint((float) (i * scaleX), (float) (gl.getHeight() - fdata1[i] * scaleY));
             //       pp3.setLine(i*scaleX,heightD2,i*scaleX,heightD2 - fdata3[i]*scaleY3);
                }
            }
            gl.DrawPathPaint(pp);
            // gl2.DrawPathPaint(pp,pp3,pp2);
            //  gl2.DrawPathPaint(pp2);
        }
    }





