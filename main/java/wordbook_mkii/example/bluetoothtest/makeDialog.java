package wordbook_mkii.example.bluetoothtest;

import android.app.AlertDialog;
import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.DialogFragment;

public class makeDialog extends DialogFragment {
    AppCompatActivity act;
    LayoutInflater loi;
    protected AlertDialog currentDialog;
    Bundle bund;
    AlertDialog.Builder builder;

    public makeDialog(AppCompatActivity a, LayoutInflater b, Bundle saveInstance) {
        act = a;
        loi = b;
        bund = saveInstance;
    }



    public View makeView(int id)
    {
        return loi.inflate(id,null);
    }

    protected AlertDialog.Builder onCleate(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        builder.setView(v);

        return builder;
    }

    AlertDialog create()
    {
        return (AlertDialog) onCreateDialog(bund);
    }

    public  void show(){
        currentDialog = this.create();
        currentDialog.show();
    }
}