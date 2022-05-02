package wordbook_mkii.example.bluetoothtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class GraphLine extends View {
    PathPaint[] currentPathPaint=null;
  //  Button button;
  //  String title = "";
    int number_of_Path=0;

    public GraphLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        currentPathPaint = new PathPaint[3];
    }


    @Override
    protected void onDraw(Canvas canvas) {
     /*   if(button==null){
            button = findViewById(R.id.power);
            if(button!=null)button.setText(title);
        }*/

        canvas.drawARGB(200,255,255,255);
        if(currentPathPaint==null)return;
        for(int i=0;i<number_of_Path;i++)
        canvas.drawPath(currentPathPaint[i].path, currentPathPaint[i].paint);

        number_of_Path=0;
    }

 /*   public  void setTitle(String title){
     //   if(button==null)button = findViewById(R.id.power);
       // button.setText(title);
      //  this.title = title;
    }*/

    public  void DrawPathPaint(PathPaint paint){
        number_of_Path=1;
        currentPathPaint[0] = paint;
        invalidate();
    }

    public  void DrawPathPaint(PathPaint paint1,PathPaint paint2){
        number_of_Path=2;
        currentPathPaint[0] = paint1;
        currentPathPaint[1] = paint2;
        invalidate();
    }
    public  void DrawPathPaint(PathPaint paint1,PathPaint paint2,PathPaint paint3){
        number_of_Path=3;
        currentPathPaint[0] = paint1;
        currentPathPaint[1] = paint2;
        currentPathPaint[2] = paint3;
        invalidate();
    }

}
