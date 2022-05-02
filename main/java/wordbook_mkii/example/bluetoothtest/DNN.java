package wordbook_mkii.example.bluetoothtest;

import java.util.ArrayList;
import java.util.Random;

public class DNN {
    final static int MAX_WIDTH=20;
    final static int MAX_NEWRON=101;
    double[][] neuron = new double[MAX_WIDTH][MAX_NEWRON];
    double[][][] weight = new double[MAX_WIDTH][MAX_NEWRON][MAX_NEWRON];
    double[][][] weight_buffer = new double[MAX_WIDTH][MAX_NEWRON][MAX_NEWRON];
    public double[] output = new double[MAX_NEWRON];
    int[] num_newron = new int[MAX_WIDTH];
    int width,num_input,num_output;
    public DNN(int... number_of){
        int i,j,k;

        i=0;
        for (int jj:number_of) {
            num_newron[i] = jj;
            i++;
        }
        width = i;
        Random rand = new Random();
        for(i=0;i<width-1;i++)
            for(j=0;j<num_newron[i];j++)
                for(k=0;k<num_newron[i+1];k++){
                    weight[i][j][k] = rand.nextInt(100)/100.0;
                }
        num_input = num_newron[0];
        num_output = num_newron[width-1];

        div = new My_div[num_output];
        dot = new My_dot[num_output];
        for(i=0;i<num_output;i++) {
            div[i] = new My_div();
            dot[i] = new My_dot();
        }
    }

    public DNN (int num_weight,int[] num_newron,double[][][] weight){
        width = num_weight+1;
        int i,j,k;

        for(i=0;i<num_weight+1;i++){
            this.num_newron[i]=num_newron[i];
        }

        for(i=0;i<width-1;i++)
            for(j=0;j<num_newron[i]+1;j++)
                for(k=0;k<num_newron[i+1];k++){
                    this.weight[i][j][k] = weight[i][j][k];
                }
        num_input = num_newron[0];
        num_output = num_newron[width-1];

        div = new My_div[num_output];
        dot = new My_dot[num_output];
        for(i=0;i<num_output;i++) {
            div[i] = new My_div();
            dot[i] = new My_dot();
        }
    }

    public void forward(double[] input_data){
        int i,j,k;
        for(i=0;i<num_newron[0];i++)
            neuron[0][i]=input_data[i];
        double sum;
        for(i=0;i<width-1;i++){
            for(j=0;j<num_newron[i+1];j++){

                sum=0;
                neuron[i][num_newron[i]]=1.0;

                for(k=0;k<num_newron[i]+1;k++)
                    sum+= neuron[i][k] * weight[i][k][j];

                if(i+1<width-1) neuron[i+1][j] = Math.max(sum,0.0001);
                else neuron[i+1][j] = sum;
            }
        }
    }

    double[] back_weight = new double[MAX_NEWRON];
    double[] back_weight_buffer = new double[MAX_NEWRON];
    void back_forward() {
        int i, j, k, ii;
        for (i = 0; i < num_output; i++) {
            back_weight[i] = output[i];
        }

        for (i = width - 2; i >= 0; i--) {
            for (ii = 0; ii < num_newron[i] + 1; ii++) back_weight_buffer[ii] = 0;
            for (j = 0; j < num_newron[i + 1]; j++) {
                for (k = 0; k < num_newron[i] + 1; k++) {
                    back_weight_buffer[k] += back_weight[j] * weight[i][k][j];
                    weight_buffer[i][k][j] += back_weight[j] * neuron[i][k];
                }
            }
            for (j = 0; j < num_newron[i]; j++) {
                if (neuron[i][j] > 0) back_weight[j] = back_weight_buffer[j];
                else back_weight[j] = 0;
            }

        }

    }
    My_div[] div;
    My_dot[] dot;
    void softMax(){
        int i,j,k;
        double sum;

        sum=0;
        for(i=0;i<num_output;i++){
            sum+= neuron[width-1][i];
        }

        for(i=0;i<num_output;i++){
            div[i].set(neuron[width-1][i],sum);
            output[i] = div[i].equals;
        }
    }

    double get_loss(double[] corect_data){
        int i,j,k;
        double sum;

        sum=0;
      //  for(i=0;i<num_output;i++){
      //      sum+=newron[width-1][i];
    //    }
      //  My_div[] div = new My_div[num_output];
     //   My_dot[] dot = new My_dot[num_output];
        for(i=0;i<num_output;i++){
         //   div[i].set(newron[width-1][i],sum);
            double sub2 = corect_data[i]-div[i].equals;
            dot[i].set(sub2,sub2);
        }

        sum=0;
        for(i=0;i<num_output;i++){
            sum+=dot[i].equals;
            double sub = -2*dot[i].get_A(1);
            sub = div[i].get_A(sub) + div[i].get_B(sub);
            output[i] = sub;
            //    printf("i:%d soft:%f\n",i,dot[i].equals);
        }

        return sum;
    }

    double loss_time=1;
    void optimaize2(){
        int i,j,k;
        for(i=0;i<width-1;i++)
            for(j=0;j<num_newron[i]+1;j++)
                for(k=0;k<num_newron[i+1];k++){
                    weight[i][j][k] -= weight_buffer[i][j][k]*0.01*loss_time;
                    weight_buffer[i][j][k]=0;
                }
    }

    void learning(ArrayList<TraningData> input_set){
        int i,j,k;
        double loss_ave=1;
        //     flag = true;
        // for(int i=0;i<20;i++){
        while(loss_ave>0.0001){
            double loss_old = loss_ave;
            loss_ave=0;

            j=0;
            for(TraningData input_data:input_set){
                //   double error = get_loss(corect_data[j]);
                forward(input_data.input_data);
                softMax();
                double error = get_loss(input_data.correct_data);
                back_forward();
                loss_ave+=error;
                //soft_max();
                j++;
            }
            //   loss_ave = get_error()/4;
            //    optimaize();
            loss_ave/=j;
            optimaize2();
            // forward();
            //  back_forward(get_loss());
            if(Math.abs(loss_ave - loss_old)<0.0000001){
                loss_time=-loss_time;
                if(loss_time>0)loss_time+=0.0001;
                else loss_time-=0.0001;
            }
          else loss_time=1;

        }
    }

}


class My_dot{

    public double a,b;
    public double equals;
    public My_dot(){
    }
    public void set(double s1,double s2){
        a=s1;
        b=s2;
        equals = a*b;
    }
    public double get_A(double k){
        return k*b;
    }

    public double get_B(double k){
        return k*a;
    }
}

class My_div{
    public double a,b;
    public double equals;
    public My_div(){
    }
    public void set(double s1,double s2){
        a=s1;
        b=s2;
        equals = a/b;
    }
    public double get_A(double k){
        return k/b;
    }

    public double get_B(double k){
        return -k*a/(b*b);
    }
}

class TraningData{
    public  double[] input_data;
    public  double[] correct_data;
    public  TraningData(double[] input,double[] correct){
        input_data = input;
        correct_data = correct;
    }
}