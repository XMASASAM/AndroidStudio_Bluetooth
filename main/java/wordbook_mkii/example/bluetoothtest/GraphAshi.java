package wordbook_mkii.example.bluetoothtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class GraphAshi extends View {

    Sole current=null;
    Paint paint;
    float scale = (float) 0.1;
    public GraphAshi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(current==null)return;
        canvas.drawCircle(getWidth()/2 - 100,getHeight()/2 - 250,current.getC(50) + 2,paint);
        canvas.drawCircle(getWidth()/2 + 116,getHeight()/2 - 180,current.getB(50) +2,paint);
        canvas.drawCircle(getWidth()/2,getHeight()/2 + 300,current.getA(50) + 2,paint);
    }

    public  void setCurrentSole(Sole sole){
        current = sole;
        invalidate();
    }


}
