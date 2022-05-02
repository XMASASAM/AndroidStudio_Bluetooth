package wordbook_mkii.example.bluetoothtest;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public  class OverView{





  //  WindowManager wm;
    static MyApp mApp;
    FrameLayout mLayout;
  //  static OverService overService;

    public  static  void init(MyApp app){
        mApp = app;
    }

    @SuppressLint("ClickableViewAccessibility")
    public  OverView(int LayoutID){
      //  this.overService = mApp.overService;

        OverService os = mApp.getOverService();

        if(os==null){
            Log.e("tuushinn","OverService is null");
            return;
        }
        if(os!=null){
            Log.v("tuushinn","iveabgibhaibhtpihj");
        }



        mLayout = new FrameLayout(os);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(os);
        inflater.inflate(LayoutID, mLayout);

        os.wm.addView(mLayout, lp);
        //button = (Button) mLayout.findViewById(R.id.power);
        View sub = mLayout.findViewById(R.id.RelativeLayout);
        sub.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    int x = (int) event.getRawX();
                    int y = (int) event.getRawY();

                    lp.x = x - (os.getWm().getDefaultDisplay().getWidth() >> 1);
                    lp.y = y - (os.getWm().getDefaultDisplay().getHeight() >> 1);
                    os.getWm().updateViewLayout(mLayout, lp);

                }

                return true;
            }
        });
    }
    public  FrameLayout getLayout(){
        return mLayout;
    }

}