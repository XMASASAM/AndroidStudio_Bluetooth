package wordbook_mkii.example.bluetoothtest;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;

class PathPaint {
    public Paint paint;
    public Path path;

    public  PathPaint(int color){
        paint = new Paint();
        path = new Path();

        paint.setColor(color);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    public  void setLine(float x1,float y1,float x2,float y2){
        path.moveTo( x1,y1);
        path.lineTo(x2,y2);
    }
    public  void setLine(double x1,double y1,double x2,double y2) {
        setLine((float) x1,(float) y1,(float) x2,(float) y2);
    }

        public  void setArray(PointF[] p){
        for(int i=0;i<p.length-1;i++){
            setLine(p[i].x,p[i].y,p[i+1].x,p[i+1].y);
        }
    }

    public  void setArray(ArrayList<PointF> p){
        setArray((PointF[]) p.toArray());

    }

    public void setPoint(float x,float y){
        path.lineTo(x,y);
    }

    public  void moveTo(float x,float y){
        path.moveTo(x,y);
    }

    public  void reset(){
        path.reset();
    }
}