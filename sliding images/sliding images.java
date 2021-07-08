/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_project;

import cezeri.factory.FactoryMatrix;
import cezeri.factory.FactoryNormalization;
import cezeri.matrix.CMatrix;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author mac
 */
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
        //overlay(merge(aslan, leopar).imshow(), kaplan).imshowRefresh();
        CMatrix ret = merge(merge(aslan, kaplan)
                //.imshow()
                , leopar)
                .imshow()
                ;

        int start = ret.getColumnNumber()-1;
        int end = ret.getColumnNumber() - 393;
        while(true){
            split(ret, start, end).imshowRefresh();
            TimeUnit.MILLISECONDS.sleep(1);
            start = start - 1;
            end = end - 1;
            if(start < 0) start = ret.getColumnNumber()-1;
            if(end < 0) end = ret.getColumnNumber()-1;
            //System.out.println(start);
        }


    }

    public static CMatrix merge(CMatrix cm1, CMatrix cm2){
        CMatrix ret = cm1.imresize(cm1.getColumnNumber() + cm2.getColumnNumber(), cm1.getRowNumber());
        double[][][] ret_3d = ret.toDoubleArray3D();
        double[][][] leftImage = cm1.toDoubleArray3D();
        double[][][] rightImage = cm2.toDoubleArray3D();

        for(int ch = 1; ch < ret_3d.length; ch++){
            double[][] retChannel = ret_3d[ch];
            double[][] leftImageChannel = leftImage[ch];
            double[][] rightImageChannel = rightImage[ch];
            for(int i = 0; i < retChannel.length; i++){
            int index = -1;
            for(int j = 0; j < retChannel[i].length; j++){
                if (j < cm1.getColumnNumber()) retChannel[i][j] = leftImageChannel[i][j];  // left image
                else retChannel[i][j] = rightImageChannel[i][++index];  // right image

                }

            }
            ret_3d[ch] = retChannel;
        }


        return ret.setArray(ret_3d);
    }



    private static CMatrix split(CMatrix cm, int start, int end) {
        double[][] cm3D = cm.toDoubleArray2D();
        double[][] ret = new double[480][639];

        for(int i = ret.length-1; i >=0 ; i--){
            int c = start;
            for(int j = ret[i].length-1; j >=0 ; j--){
                ret[i][j] = cm.getValue(i, c);
                c = c - 1;
                if(c == 0) c = 1920-1;
            }

        }


         return CMatrix.getInstance().setArray(ret);
    }

}
