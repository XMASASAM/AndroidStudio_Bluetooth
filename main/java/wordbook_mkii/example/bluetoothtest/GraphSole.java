package wordbook_mkii.example.bluetoothtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GraphSole extends View {
    public int sX,sY;


    PathPaint pA,pB,pC;
    int scaleX = 50;
    int scaleY = 1;
    public GraphSole(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.sX=scaleX;
        this.sY = scaleY;

        pA = new PathPaint(Color.RED);
        pB = new PathPaint(Color.GRAY);
        pC = new PathPaint(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(pA==null&&pre==null)return;
        canvas.drawPath(pA.path, pA.paint);
        canvas.drawPath(pB.path, pB.paint);
        canvas.drawPath(pC.path, pC.paint);
    }

    Sole pre=null;


    public  void setSole(Sole sole){
        if(pre==null){
            pre=sole;
        }
        int wd = getWidth() +1;
        int x1 = (int) (pre.time*sX % wd);
        int y1 = (int)(getHeight() - pre.getA(getHeight()));

        int x2 = (int) (sole.time*sX % wd);
        int y2 = (int)(getHeight() - sole.getA(getHeight()));


        int x3 = (int) (pre.time*sX % wd);
        int y3 = (int)(getHeight() - pre.getB(getHeight()));

        int x4 = (int) (sole.time*sX % wd);
        int y4 = (int)(getHeight() - sole.getB(getHeight()));

        int x5 = (int) (pre.time*sX % wd);
        int y5 = (int)(getHeight() - pre.getC(getHeight()));

        int x6 = (int) (sole.time*sX % wd);
        int y6 = (int)(getHeight() - sole.getC(getHeight()));

        pA.setLine(x1,y1,x2,y2);
        pB.setLine(x3,y3,x4,y4);
        pC.setLine(x5,y5,x6,y6);

        float xAi1 = (int)(pre.time*sX) % (wd);
        float xAi2 = (int)(sole.time*sX) % (wd);
        if(xAi1>xAi2){
            pA.path.reset();
            pB.path.reset();
            pC.path.reset();
        }
        pre = sole;

        invalidate();

    }

}

