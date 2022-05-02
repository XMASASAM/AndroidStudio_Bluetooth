package wordbook_mkii.example.bluetoothtest;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CVS_Reader {
 //   List<ListData> objects = new ArrayList<ListData>();
    static String TAG = "cvsread";
    public DNN reader(Context context,String name) {
        AssetManager assetManager = context.getResources().getAssets();
        DNN dnn=null;
        Log.v(TAG,"kitayo:"+name);
        try {
            // CSVファイルの読み込み
            InputStream inputStream = assetManager.open(name);
            Log.v(TAG,"kitayo");

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line;
            int num_weight;
            line = bufferReader.readLine();
            //String[] RowData = line.split(" ");
            num_weight = Integer.parseInt(line)-1;
            int[] num_newron = new int[num_weight+1];
            line = bufferReader.readLine();
            String[] RowData = line.split(" ");
            int max_num=0;
            Log.v(TAG,"weight:"+String.valueOf(num_weight));
            for(int i=0;i<num_weight+1;i++){
                num_newron[i]=Integer.parseInt(RowData[i]);
                max_num = Math.max(num_newron[i],max_num);
            }
            Log.v(TAG,"weight:"+String.valueOf(num_weight));
            Log.v(TAG,String.valueOf(num_weight));
            int i,j,k;
            double weight[][][] = new double[num_weight][max_num+1][max_num];
           // Log.v(TAG,weight.toString());

            for( i=0;i<num_weight;i++)
                for(j=0;j<num_newron[i]+1;j++){
                    line = bufferReader.readLine();
                    RowData = line.split(" ");
                    for(k=0;k<num_newron[i+1];k++){
                        Log.v(TAG,"i:"+String.valueOf(i) +"2:"+String.valueOf(j) + "3:"+String.valueOf(k) );
                        weight[i][j][k] = Double.parseDouble(RowData[k]);
                   //     Log.v(TAG,String.valueOf(weight[i][j][k]));
                        //fprintf( fp, "%lf ",weight[i][j][k]);
                    }
                   // fprintf(fp , "\n");
                }
            Log.v(TAG,"今からセット！");
            dnn = new DNN(num_weight,num_newron,weight);
            bufferReader.close();
                 Log.v(TAG,"かんりょう！！");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dnn;
    }
}
