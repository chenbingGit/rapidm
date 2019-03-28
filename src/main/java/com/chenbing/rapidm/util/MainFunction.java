package com.chenbing.rapidm.util;

import java.util.ArrayList;

public class MainFunction {
    public static void main(String[] args) {
        count1();
    }


     private static void count(){
//         三毒平爆
        double[] data1 = {12147,14312,13147,3747,1878,2098,2098};
//         乱洒平爆
        double[] data2 = {5948,14312,13147,1878,2098,2098};

        double gl = 1;

        for (int i = 0; i < data1.length; i++) {
            for (int j = 0; j < 2 ; j++) {
                boolean isHx = i == 0;

            }
        }
        System.out.print("\n" );
    }

    private static void count1(){
        //         三毒平爆
        double[] data1 = {12147,14312,13147,3747,1878,2098,2098};
        ArrayList<double[]> info = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l <2 ; l++) {
                        for (int m = 0; m <2 ; m++) {
                            for (int n = 0; n < 2; n++) {
                                for (int o = 0; o < 2; o++) {
                                    getDps(data1, new int[]{i, j, k, l, m, n, o},info);
                                }
                            }
                        }
                    }
                }
            }
        }

        info.sort((doubles, t1) -> (int) ((doubles[1] - t1[1]) * 100));


        double lv = 1;
        for (double[] anInfo : info) {
            System.out.println("有\t"+lv+"\t的概率造成\t"+anInfo[1]+"\t以上伤害");
            lv -= anInfo[0];

        }


    }
    private static void getDps(double[] dps, int [] hx, ArrayList<double[]> info){
        double count =0;
        double gl = 1;
        double hxl = 0.29;
        for (int i = 0; i < dps.length ; i++) {
            if (hx[i] == 0) {
                gl *= hxl;
                count +=  dps[i] * 1.7739;
            }else {
                gl *= (1-hxl);
                count+= dps[i];
            }
        }
        info.add(new double[]{gl,count});
        System.out.println( gl + "\t"+count);
    }
}
