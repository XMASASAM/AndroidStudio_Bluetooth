package wordbook_mkii.example.bluetoothtest;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

class MakeFile {
    public  MakeFile(){
    }

    public  void save(String path,String text) throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            //ファイルの読み書きの処理
            FileOutputStream fileOutputStream = new FileOutputStream(path, true);
            fileOutputStream.write(text.getBytes());
        }
    }

}
