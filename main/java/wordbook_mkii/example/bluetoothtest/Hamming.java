package wordbook_mkii.example.bluetoothtest;

public class Hamming {

    int msize;
    double[] k;
    public Hamming(int size){
        msize=size;
        k = new double[size];

        for(int i=0;i<size;i++){
            k[i] = 0.54 - 0.46*Math.cos(2*i*Math.PI / (size -1));
        }

    }

    public double getK(int index){

        return k[index];
    }

}
