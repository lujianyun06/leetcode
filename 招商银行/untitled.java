package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Main m = new Main();
        m.test3();
    }

    public void test3(){
        Scanner cin = new Scanner(System.in);
        int n,w;
        n = cin.nextInt();
        w = cin.nextInt();
        double minMan=Integer.MAX_VALUE;
        double minWoman = Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            int tmp = cin.nextInt();
            minMan = Math.min(tmp, minMan);
        }

        for(int i=0;i<n;i++){
            int tmp = cin.nextInt();
            minWoman = Math.min(tmp, minWoman);
        }

        if(minMan >= minWoman * 2)
            minMan = minWoman * 2;
        else
            minWoman = minMan / 2;

        double womanLimit = (double) w/(3 * n);

        if(womanLimit < minWoman){
            System.out.printf("%.6f", womanLimit * 3 * n);
        }else{
            System.out.printf("%.6f", (double)minWoman * 3 * n);
        }
    }
}


import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] beach = new int[n];
        for(int i=0; i<n; ++i){
            beach[i] = sc.nextInt();
        }
        Arrays.sort(beach);

        int tempSum = 0;
        for(int i=0; i<n-1; ++i){
            tempSum += (beach[n-1]-beach[i]);
        }

        int maxK = beach[n-1]+m;
        int minK = beach[n-1];

        if(m>tempSum){
            m -= tempSum;
            if(m%n==0){
                minK += (m/n);
            }
            else{
                minK += (m/n)+1;
            }
        }
        System.out.println(minK + " " + maxK);
    }
}


