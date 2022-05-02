package wordbook_mkii.example.bluetoothtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class OverService extends Service {
    private static MyApp mApp;
    public WindowManager wm;
    //MyApp mApp;
    @RequiresApi(api = Build.VERSION_CODES.O)
    static void init(MyApp app, MainActivity2 act){
        Log.v("tuushinn","bbbbbbbb1");
        if(act!=null){
            Intent intent = new Intent(act, OverService.class);
            Log.v("tuushinn","bbbbbbbb2");
            app.startForegroundService(intent);
            Log.v("tuushinn","bbbbbbbb4");

        }
        Log.v("tuushinn","ccccccc");
        OverView.init(app);
        mApp = app;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // startForegroundService() -----
        Log.v("tuushinn", "aaaaaaaa");
        Context context = mApp;//getApplicationContext();
        Log.v("tuushinn","aaaaaaaa");

        String channelId = "default";
        Log.v("tuushinn","aaaaaaaa");

        String title = context.getString(R.string.app_name);
        Log.v("tuushinn","aaaaaaaa");

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        Log.v("tuushinn","aaaaaaaa");

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.v("tuushinn","aaaaaaaa");

        // Notification　Channel 設定
        NotificationChannel channel = new NotificationChannel(
                channelId, title, NotificationManager.IMPORTANCE_DEFAULT);
        Log.v("tuushinn","aaaaaaaa");

        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
            Log.v("tuushinn","aaaaaaaa11111111");

            Notification notification = new Notification.Builder(context, channelId)
                    .setContentTitle(title)
                    // android標準アイコンから
                    .setSmallIcon(R.drawable.ic_baseline_directions_walk_24)
                    .setContentText("APPLICATION_OVERLAY")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .build();
            Log.v("tuushinn","aaaaaaaa1222222");
            // startForeground
            startForeground(1, notification);
            Log.v("tuushinn","aaaaaaaa3333333");
        }
        wm = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Log.v("tuushinn","aaaaaaaa");
          MyApp app = (MyApp) getApplication();
         app.setOverService(this);
        Log.v("tuushinn","aaaaaaaa11");
        // overService = OverService.this;
        //Update();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public WindowManager getWm() {
        return wm;
    }
/*
    public  void Update(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loop();
                Update();
            }
        },100);
    }

    public void loop(){

    }*/

}
