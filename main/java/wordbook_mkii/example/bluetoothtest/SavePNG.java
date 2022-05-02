package wordbook_mkii.example.bluetoothtest;

import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SavePNG {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public File saveAsPngImage(Bitmap bitmap) {
        File file = null;
        try {
            File extStrageDir =
                    Environment.getExternalStorageDirectory();
            file = new File(
                    extStrageDir.getAbsolutePath()
                            + "/" + Environment.DIRECTORY_DCIM,
                    getFileName());
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

      /*      Toast.makeText(
                    context,
                    "Image saved",
                    Toast.LENGTH_SHORT).show();*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return file;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected String getFileName() {
        Calendar c = Calendar.getInstance();
        String s = c.get(Calendar.YEAR)
                + "_" + (c.get(Calendar.MONTH) + 1)
                + "_" + c.get(Calendar.DAY_OF_MONTH)
                + "_" + c.get(Calendar.HOUR_OF_DAY)
                + "_" + c.get(Calendar.MINUTE)
                + "_" + c.get(Calendar.SECOND)
                + "_" + c.get(Calendar.MILLISECOND)
                + ".png";
        return s;
    }

}