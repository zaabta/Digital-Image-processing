
package my_project;

import cezeri.factory.FactoryMatrix;
import cezeri.factory.FactoryNormalization;
import cezeri.matrix.CMatrix;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        CMatrix aslan = CMatrix.getInstance()
                .imread("/Users/mac/Downloads/final sinyal soruları/aslan.jpg")
                .imresize(640, 480)
                //.imshow()
                //.rgb2gray()
                //.imshow()
                ;
        CMatrix kaplan = CMatrix.getInstance()
                .imread("/Users/mac/Downloads/final sinyal soruları/kaplan1.jpg")
                .imresize(640, 480)
                //.imshow()
                //.rgb2gray()
                //.imshow()
                ;
        CMatrix leopar = CMatrix.getInstance()
                .imread("/Users/mac/Downloads/final sinyal soruları/leopar.jpg")
                .imresize(640, 480)
                //.imshow()
                //.rgb2gray()
                //.imshow()
                ;
        
        //merge two Image aslan & leopar
        
        //CMatrix ret = aslan.merge(leopar, ",").imshowRefresh();
        //ret = overlay(ret, kaplan).imshow();
        CMatrix ret = aslan;
        int i = 0;
        CMatrix[] images = {kaplan, leopar, aslan};
        double changeValue = 0.0001;
        while(true){
            ret = overlay(ret, images[i], changeValue).imshowRefresh();
            TimeUnit.SECONDS.sleep(1);
            changeValue += 0.0009;
            if(changeValue > 0.005){
                changeValue = 0.0001; 
                i = i + 1;
            }   
            if(i > images.length-1) i = 0;
        }
        
        
         
                 
            
        
       
        
    }
    
     public static CMatrix overlay(CMatrix Image1, CMatrix Image2, double change_value){
     // converting merged image with overlooking The fist Channel (alpha Channel)*
        double[][][] d_ret = Image1.toDoubleArray3D();
        double[][][] d2 = Image2.toDoubleArray3D();
        
        for (int i = 1; i < d_ret.length; i++) {
            double[][] d_1 = d_ret[i];
            double[][] d_2 = d2[i];
            
            d_ret[i] = sumPixels(d_1, d_2, 0, 640, change_value);
        }
        //return Image
        return CMatrix.getInstance().setArray(d_ret);
    }
    
    public static double[][] sumPixels(double[][] image1, double[][] image2, int start, int end, double change_value) {
        double[][] returnImage = new double[image1.length][image1[0].length];
        for (int i = 0; i < returnImage.length; i++) {
            
            double alpha_right = 1.0;
            double alpha_left = 0.000001;
            int index = -1;
            
            
            for (int j = 0; j < returnImage[0].length; j++) {
               
                    double sag = image1[i][j] * alpha_right;
                    double sol = image2[i][j] * alpha_left;
                    
                    returnImage[i][j] = sag + sol;

                    alpha_right -= change_value;
                    alpha_left += change_value;

                    if (alpha_right < 0)  alpha_right = 0.000001;
                    if (alpha_left > 1) alpha_left = 1.0;
                    
 
            }
        }

        return returnImage;
    }
}
