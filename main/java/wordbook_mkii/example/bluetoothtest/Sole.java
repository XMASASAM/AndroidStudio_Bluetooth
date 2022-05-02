package wordbook_mkii.example.bluetoothtest;

import android.util.Log;

public class Sole {

    double a=0;
    double  b=0;
    double  c=0;
    double time=0;
    static double maxvalue_A=0;
    static double maxvalue_B=0;
    static double maxvalue_C=0;
    public  Sole(int a,int b,int c,long time){
        this.a = a;
        this.b =b;
        this.c =c;
       // this.maxvalue = 0;
    }

    public Sole(String str){
        if(str.charAt(0)!='['||str.charAt(str.length()-1)!=']'){
            time=-1;
            return;
        }

        str = str.substring(1,str.length()-1);
       // Log.v("ssssss","Data:" +str );
        String sub[] = str.split(",");

       // a= Integer.parseInt(sub[0]);
      //  b= Integer.parseInt(sub[1]);
      //  c= Integer.parseInt(sub[2]);
      //  time= Integer.parseInt(sub[3]);
        if(sub[0].matches("[+-]?\\d*(\\.\\d+)?")) {
            a = Double.parseDouble(sub[0]);
            b = Double.parseDouble(sub[1]);
            c = Double.parseDouble(sub[2]);
            time = Double.parseDouble(sub[3]);

        }else{
            a=0;
            b=0;
            c=0;time=-1;
        }
        maxvalue_A = Math.max(maxvalue_A, a);
        maxvalue_B = Math.max(maxvalue_B, a);
        maxvalue_C = Math.max(maxvalue_C, a);
     //   double temp = a;
       // a=c;
    //    c=temp;


        // time = Double.parseDouble(sub[3]);
    }

    public  String toString(){
        return "A:"+String.valueOf(a)+" B:"+String.valueOf(b)+
                " C:"+String.valueOf(c)+" Time:"+String.valueOf(time);
    }

    public  String toStringCSV(){
        return String.valueOf(a)+","+String.valueOf(b)+","+
                String.valueOf(c)+","+String.valueOf(time);
    }

    public  int getA(int mx){
        return (int)(mx*a/maxvalue_A);
    }
    public  int getB(int mx){
        return (int)(mx*b/maxvalue_B);
    }
    public  int getC(int mx){
        return (int)(mx*c/maxvalue_C);
    }
}
