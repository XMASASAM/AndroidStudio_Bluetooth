package wordbook_mkii.example.bluetoothtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;


public class GraphTest extends View {
    Paint paint;
    Path path;

    float StrokeWidth1 = 20f;
    float StrokeWidth2 = 40f;
    float dp;

    public GraphTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        path = new Path();

        // スクリーンサイズからdipのようなものを作る
        DisplayMetrics metrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);

        dp = getResources().getDisplayMetrics().density;
        Log.d("debug","fdp="+dp);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // 背景
        canvas.drawColor(Color.argb(255, 0, 0, 125));

        // Canvas 中心点
        float xc = getWidth() / 2;
        float yc = getHeight() / 2;

        // 円
        paint.setColor(Color.argb(255, 125, 125, 255));
        paint.setStrokeWidth(StrokeWidth1);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
        canvas.drawCircle(xc - 15*dp, yc - 55*dp, xc / 2, paint);

        // 矩形
        paint.setColor(Color.argb(255, 255, 0, 255));
        paint.setStyle(Paint.Style.STROKE);
        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        canvas.drawRect(xc - 30*dp, yc - 50*dp,
                xc + 120*dp, yc + 100*dp, paint);

        // 線
        paint.setStrokeWidth(StrokeWidth1);
        paint.setColor(Color.argb(255, 0, 255, 0));
        // (x1,y1,x2,y2,paint) 始点の座標(x1,y1), 終点の座標(x2,y2)
        canvas.drawLine(xc + 20*dp, yc - 30*dp, xc - 70*dp, yc + 70*dp, paint);

        // 三角形を書く
        float tx1 = 230*dp;
        float ty1 = 370*dp;
        float tx2 = 100*dp;
        float ty2 = 500*dp;
        float tx3 = 350*dp;
        float ty3 = 500*dp;

        paint.setStrokeWidth(10);
        paint.setColor(Color.WHITE);
        path.moveTo(tx1, ty1);
        path.lineTo(tx2, ty2);
        path.lineTo(tx3, ty3);
        path.lineTo(tx1, ty1);
        canvas.drawPath(path, paint);

        // 円
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(StrokeWidth2);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        // (x,y,r,paint) x座標, y座標, r半径
        canvas.drawCircle(220*dp, 130*dp, 40*dp, paint);

    }

}
