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

        //把w平均分成3n份，没份大小为x，如果x大于女生容量，说明最小的女生容量可以装满，答案是女生容量 * 3n
        //如果x小于女生容量，则说明可以把w分完，则答案是 w
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
        int max = 0;
        for(int i=0; i<n; i++){
            beach[i] = sc.nextInt();
            max = Math.max(max, beach[i]);
        }
        //k当最大时，一定是m个人全坐到原有最多人的椅子上
        int maxK = max + m;
        int minK = 0;

        //k最小时，同时要满足是所有椅子中坐的人最多的，则首先将尽力使得一开始除了最大人数的椅子上都坐满最大人数，
        //如果这样m个人数不够用，则k最小就为一开始最大的人数
        //如果人数够用，则此时所有椅子上都坐了max个人，还剩一部分人，把剩下的这些人平均分配到所有椅子上，
        //当出现不够分时，则一部分椅子上人数是k，一部分是k-1
        //
        int totalSub = 0;
        for(int i=0;i<n;i++){
        	totalSub += max - beach[i];
        }
        if(m <= totalSub){
        	System.out.println(max + " " +maxK);
        }else{
        	m -= totalSub;
        	minK = max + m/n;
        	if(m%n==0){
        		System.out.println(minK + " " +maxK);
        	}else{
        		minK += 1;
        		System.out.println(minK + " " +maxK);
        	}

        }

    }

}


