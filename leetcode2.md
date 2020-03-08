

************************* 做一些实际的笔试题*************************
bishi


b1.牛牛找工作

给定一些数对(di,pi)，以及一些数字ai，对于每一个ai，找到满足ai>=di的最大pi并按照ai的顺序输出其对应的pi，一对(di,pi)可以对应多个ai

输入描述:
每个输入包含一个测试用例。
每个测试用例的第一行包含两个正整数，分别表示工作的数量N(N<=100000)和小伙伴的数量M(M<=100000)。
接下来的N行每行包含两个正整数，分别表示该项工作的难度Di(Di<=1000000000)和报酬Pi(Pi<=1000000000)。
接下来的一行包含M个正整数，分别表示M个小伙伴的能力值Ai(Ai<=1000000000)。
保证不存在两项工作的报酬相同。
输出描述:
对于每个小伙伴，在单独的一行输出一个正整数表示他能得到的最高报酬。一个工作可以被多个人选择。
示例1
输入
复制
3 3 
1 100 
10 1000 
1000000000 1001 
9 10 1000000000
输出
复制
100 
1000 
1001

先用map保存di-pi，然后保存ai，若map中已有ai(即有di==ai)则不动，否则保存ai-0，并且用一个数组mm保存di和ai，用一个数组nn保存ai
对ai从小到大排序，然后遍历mm，保存目前为止最大的pi为max，取map.get(mmi)和max的大值作为map中mmi的value
这样遍历到一个ai时，即使map中值为0，由于max的存在，也能取到比ai小的中di对应的最大的pi
之后再遍历nn，以其为key从map中取出value输出，由于nn保存了ai输入的顺序，所以可以保证输出顺序和输入顺序对应

```java

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        int m,n;
        n = in.nextInt();
        m = in.nextInt();
        HashMap<Integer, Integer> map = new HashMap<>();

        int[] a = new int[m+n];

        //保存能力和薪资
        for(int i=0;i<n;i++){
            int di= in.nextInt();
            int pi = in.nextInt();
            map.put(di, pi);
            a[i]=di;

        }

        //b依次保存人的能力
        //把人的能力也放入map中，如果有则保留，如果没有则薪资暂为0
        int[] b = new int[m];
        for(int i=0;i<m;i++){
            int ai = in.nextInt();
            a[i+n]=ai;
            b[i]=ai;
            if(!map.containsKey(ai))
                map.put(ai, 0);
        }

        //将a排序，能力低的在先
        Arrays.sort(a);

        int max=0;
        //遍历a，更新map，对于一个k-v来说，v始终是k值小等于它的k的所有v中最大的那个
        //若ai>aj,ai对应的薪资不是0而aj对应的薪资是0，则aj对应的薪资会被更新为至少为ai的薪资
        for(int i=0;i<a.length;i++){
            max = Math.max(max, map.get(a[i]));
            map.put(a[i],max);

        }

        //逐个输出每个人对应的薪资
        for(int i:b)
            System.out.println(map.get(i));


    }
}
```

b2.被3整除
题目描述
小Q得到一个神奇的数列: 1, 12, 123,...12345678910,1234567891011...。

并且小Q对于能否被3整除这个性质很感兴趣。

小Q现在希望你能帮他计算一下从数列的第l个到第r个(包含端点)有多少个数可以被3整除。

输入描述:
输入包括两个整数l和r(1 <= l <= r <= 1e9), 表示要求解的区间两端。
输出描述:
输出一个整数, 表示区间内能被3整除的数字个数。
示例1
输入

2 5
输出

3
说明
12, 123, 1234, 12345...
其中12, 123, 12345能被3整除。


可以被3整除的数的各个位上的数字之和是3的整数倍


AC的答案：
   
1代表第i个数不是3的倍数，0代表是3的倍数


    i = 1 ----> 1   1
    i = 2 ----> 0   12
    i = 3 ----> 0   123
    i = 4 ----> 1   1234
    i = 5 ----> 0   12345
    i = 6 ----> 0   123456
    i = 7 ----> 1   1234567
………………
也就是说[1,x]中共有f(x)=(x+2)/3 个数不是3的倍数

即[1,2]中是 1，12  其中有1个1，即1---》1
[1,3]中是1，12，123   f(3)=(3+2)/3=1 个1 ，即 1---》1
[1,4]中是1，12，123，1234，f(4)=(4+2)/3=2 个1 即 1---》1， 4--》1234
因此，边界为l到r之间的数字共有 f(r)-f(l-1)个数不是3的倍数，而l到r之间共有r-l+1个数
因此 l到r之间的3的倍数的个数为 r-l+1-f(r)+f(l-1)

==============
证明：

记插入数字i以后形成的新数字为a[i]，数字a[i]除以3的余数记作last[i]
容易发现，a[i] = a[i-1]* 10^k + i。
则
last[i] = a[i]%3 = (a[i-1] * 10^k + i)%3 
= (a[i-1] * (10^k -1) + (a[i-1] + i))%3
=(a[i-1] + i)%3
=(a[i-1]%3 + i%3)%3
=(last[i-1] + i%3)%3

则last满足递推关系：
last[i] = (last[i-1] + i%3)%3
数学归纳法：
当k = 0的时候：
last[0] = 0、last[1] = 1、last[2] = 0成立

假设规律last[3k+1] = 1、last[3k] = 0、last[3k+2] = 0成立；
则对于任意k+1而言
last[3(k+1)] = last[(3k+2) + 1] = (0 + 3(k+1)%3)%3 = 0
last[3(k+1)+1] = (0 + (3(k+1)+1)%3)%3 = 1
last[3(k+1)+2] = (1 + (3(k+1)+2)%3)%3 = (1+2)%3 = 0
可见对任意k，上述规律恒成立。

综上，last[i] = i%3==1
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int l, r;
        Scanner in = new Scanner(System.in);
        l = in.nextInt();
        r = in.nextInt();
        System.out.println(r - l + 1 - f(r) + f(l - 1));
    }

    static int f(int a) {
        return (a + 2) / 3;
    }
}
```


//只通过了70%的答案:  关于位数的那个想法很值得借鉴，虽然没AC

用sum[i]表示数列的第i个数各个位上的和，则 sum[i] = sum[i-1] + 数字i各个位的数字之和
因为sum[i]只和sum[i-1]有关，所以直接优化成一个数即可

```java
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        int l, r;
        Scanner in = new Scanner(System.in);
        l = in.nextInt();
        r = in.nextInt();
        //由于l和r最大为10e9，所以不要用数组存，数组存不了这么大
        int ans = 0;
        if (l > r) {
            System.out.println(ans);
            return;
        }
        int sum = 0;
        //上一个数字各各位之和
        int pre = 0;
        //上一个数字是否能被3整除
        boolean aliquot = false;
        for (int i = 1; i <= r; i++) {
            //如果位数没变化，则当前数的各位之和等于上一个数字的各位之和+1
            if (String.valueOf(i).length() == String.valueOf(i - 1).length()) {
                pre = pre + 1;
            } else {
                //如果位数变化了，则计算当前数的各位之和，因为位数变化了，一定是从若干个9变成了一个1后面全是0，数位之和一定是1
                pre = 1;
            }
            sum = sum + pre;
            //如果上个数字能整除，且当前数各位和能整除3
            if (aliquot && pre % 3 == 0) {
                if(i>=l)ans++;
                aliquot = true;
            //否则才算整体    
            } else if (sum % 3 == 0) {
                if(i>=l)ans++;
                aliquot = true;
            }else {
                aliquot = false;
            }
        }
        System.out.println(ans);
    }
}
``` 

b3.安置路灯
题目描述
小Q正在给一条长度为n的道路设计路灯安置方案。

为了让问题更简单,小Q把道路视为n个方格,需要照亮的地方用'.'表示, 不需要照亮的障碍物格子用'X'表示。

小Q现在要在道路上设置一些路灯, 对于安置在pos位置的路灯, 这盏路灯可以照亮pos - 1, pos, pos + 1这三个位置。注意，不论是X还是.都可以放置路灯

小Q希望能安置尽量少的路灯照亮所有'.'区域, 希望你能帮他计算一下最少需要多少盏路灯。

输入描述:
输入的第一行包含一个正整数t(1 <= t <= 1000), 表示测试用例数
接下来每两行一个测试数据, 第一行一个正整数n(1 <= n <= 1000),表示道路的长度。
第二行一个字符串s表示道路的构造,只包含'.'和'X'。
输出描述:
对于每个测试用例, 输出一个正整数表示最少需要多少盏路灯。
示例1
输入

2
3
.X.
11
...XX....XX
输出

1
3

照亮i及之前区域，最少用dp[i]个灯
如果nums[i]='X',dp[i]=dp[i-1]
如果nums[i]='.' dp=1+Math.min(dp[i-1], dp[i-2], dp[i-3])
且i=0,i=1,i=2的时候都要单独提出来，上面的循环必须从i=3开始

# Scanner无法读入char：只能用in.next(),读入String，然后再用charAt(index)去转成字符

```java

import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while((t--)>0){
            int n = in.nextInt();
            if(n<=0) continue;
            String s=in.next();
            char[] road = new char[n];
            for(int i=0;i<n;i++){
                road[i] = s.charAt(i);
            }
            int[] dp = new int[n];
            if(n>=1){
                if(road[0]=='.') dp[0]=1;
                else dp[0]=0;
            }
            if(n>=2){
                if(road[1]=='X') dp[1]=dp[0];
                else{
                    dp[1]=1; //不管第一个需不需照，这个都可以是1
                }
            }
            if(n>=3){
                if(road[2]=='X') dp[2]=dp[1];
                else{
                    dp[2]=1; //不管前两个需不需照，这个都可以是1
                }
            }

            for(int i=3;i<n;i++){
                if(road[i]=='X') dp[i]=dp[i-1];
                else{
                    dp[i] = Math.min(dp[i-3], dp[i-2]);
                    dp[i] = Math.min(dp[i-1], dp[i]);
                    dp[i] += 1;
                }
            }
            System.out.println(dp[n-1]);
        }
    }
}
```

b4.迷路的牛牛

题目描述
牛牛去犇犇老师家补课，出门的时候面向北方，但是现在他迷路了。虽然他手里有一张地图，但是他需要知道自己面向哪个方向，请你帮帮他。
输入描述:
每个输入包含一个测试用例。
每个测试用例的第一行包含一个正整数，表示转方向的次数N(N<=1000)。
接下来的一行包含一个长度为N的字符串，由L和R组成，L表示向左转，R表示向右转。
输出描述:
输出牛牛最后面向的方向，N表示北，S表示南，E表示东，W表示西。
示例1
输入
3
LRR
输出
E

显然方向中 一对L和R就可以抵消，所以LRR就相当于一个R，就是东了(E)

给每个方向代号,顺时针增加：N:0, E：1，S：2，W：3
左转相当于减1，右转相当于加1。 得到最终转向累加数lastDir
为了方便，全部转换成正数。左转一次相当于右转3次，如果lastDir非负，则不用变，如果为负，则取绝对值后* 3
例如，lastDir=-2，即从正北左转2次，相当于从正北右转6次，lastDir=abs(lastDir)* 3
再取 lastDir%4 即为最终方向

```java

public class Main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        char dir = new char[]{'N', 'E', 'S', 'W'};
        int lastDir = 0;
        String s = in.next();
        for(int i=0;i<t;i++){
            char c = s.charAt(i);
            if(c=='L'){
                lastDir--;
            }else{
                lastDir++;
            }
        }
        //全部转换成正数，左转一次相当于右转3次
        lastDir = lastDir<0?Math.abs(lastDir)*3+lastDir;
        //直接
        return dir[lastDir%4];
    }
}
```

b5.数对
题目描述
牛牛以前在老师那里得到了一个正整数数对(x, y), 牛牛忘记他们具体是多少了。

但是牛牛记得老师告诉过他x和y均不大于n, 并且x除以y的余数大于等于k。
牛牛希望你能帮他计算一共有多少个可能的数对。

输入描述:
输入包括两个正整数n,k(1 <= n <= 10^5, 0 <= k <= n - 1)。
输出描述:
对于每个测试用例, 输出一个正整数表示可能的数对数量。
示例1
输入
复制
5 2
输出
复制
7
说明
满足条件的数对有(2,3),(2,4),(2,5),(3,4),(3,5),(4,5),(5,3)

余数只能小于除数

如果直接用上面的思路写代码，复杂度过高


x可以在 [1, n] 上取，但是y只能在 [k, n]上取，因为k以下都不存在大于等于k的余数。
所以遍历y，对于每一个y，统计符合的x的个数，加到count里。

先假设x可以从 [0, n]中取值，那么这段区间至少可以分成(n/y)个完整的、长度为y的区间。
x = 【0，1……y-2，y-1】【y，y+1，……，2y-2，2y-1】……【……】……【……，n】
在每个小区间a上，第i个数a[i]%y的余数是i。这样每一小段上大于等于k的x有y-k个（显然当k=0时，y个数都满足题意）。
【0，1，……，k，k+1，……，y-1】

需要注意的是，上面x要从0开始算区间长度，所以一定会余下一个区间，比如对于n=5，y=3
划分后为[0,1,2],[3,4,5] 而5/3=1，则最后的区间没有算进去
若n=5，y=5
划分后为[0,1,2,3,4],[5] 5/5=1，最后一个区间还是没算进去。因此，这种方法，至少剩了一个数，至多会把一个整区间（数量为y）都剩下来

前面区间有n/y个，每个区间中除以y余数大等于k的数字共有y-k个，因此对于任意一个y，前面区间中满足条件的数一共有 n/y* (y-k)个

但是无论如何，最后这个区间last的第i个数last[i]%y一定是i。则最后一个数（n）的余数就是n%y。
如此一来，此区间内从 [k,n%y] 包含共计 n%y-k+1 个数。不过如果算出小于0的数，则不需要减回去，直接当没有就可以了。
所以最后一个区间里包含了 max(n%y-k+1, 0) 个满足条件的x。

对k=0时单独处理，当k=0时，不论x和y取什么值，都能满足条件，共有n* n个数字

```java
import java.util.Scanner;
public class Main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        //这个必须用long，因为如果n*n大于int最大值，就会溢出
        long n = in.nextLong();
        long k = in.nextLong();
        if(k==0){
            System.out.println(n*n);
        }else{
            long ans = 0L;
            for(long y=k+1;y<=n;y++){
                long res =  n/y* (y-k) + Math.max(n%y-k+1, 0);
                ans+=res;
            }
            System.out.println(ans);
        }
    }
}
```


b6.矩形重叠
题目描述
平面内有n个矩形, 第i个矩形的左下角坐标为(x1[i], y1[i]), 右上角坐标为(x2[i], y2[i])。

如果两个或者多个矩形有公共区域则认为它们是相互重叠的(不考虑边界和角落)。

请你计算出平面内重叠矩形数量最多的地方,有多少个矩形相互重叠。

输入描述:
输入包括五行。
第一行包括一个整数n(2 <= n <= 50), 表示矩形的个数。
第二行包括n个整数x1[i](-10^9 <= x1[i] <= 10^9),表示左下角的横坐标。
第三行包括n个整数y1[i](-10^9 <= y1[i] <= 10^9),表示左下角的纵坐标。
第四行包括n个整数x2[i](-10^9 <= x2[i] <= 10^9),表示右上角的横坐标。
第五行包括n个整数y2[i](-10^9 <= y2[i] <= 10^9),表示右上角的纵坐标。
输出描述:
输出一个正整数, 表示最多的地方有多少个矩形相互重叠,如果矩形都不互相重叠,输出1。

注意的是，是最多的地方有几个矩形重叠，而不是最多有几个矩形重叠！
比如像奥迪的标志，最多有3个矩形重叠，中间的和两边的重叠，但一个地方最多只有2个矩形重叠！

矩形很少，最多才50个

示例1
输入

2
0 90
0 90
100 200
100 200
输出

2

这里列举的不光是所有现有矩形的左下角，而是考虑了所有左下角横和左下角纵的组合
因为无论何种情况，重叠区域也是四条边组成。
而且是取自于这n个矩形中的某四条边，而且重叠区域矩形的左下角一定是某个矩形的下边和某个矩形的左边相交而成的。这两个矩形可能相同可能不同
因此遍历所有矩形之间下边和左边能形成的交点，并看该交点在几个矩形中，因为该交点是重叠区域矩形的左下角点，对于一个矩形，
若 矩形左边界<=该点横坐标< 矩形右边界 且 矩形下边界<=该点纵坐标< 矩形上边界 则该矩形和该重叠区域有交集
对于边界条件的解释：
    如果交点在该矩形的下边或左边上，该交点算作在该矩形中
    若该交点在该矩形的上边或右边上，则不算在该矩形中，这是当然的，因为该交点是重叠矩形的左下角。

```java

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] x1 = new int[n];
        int[] y1 = new int[n];
        int[] x2 = new int[n];
        int[] y2 = new int[n];

        for (int i = 0; i < n; i++)
            x1[i] = in.nextInt();
        for (int i = 0; i < n; i++)
            y1[i] = in.nextInt();
        for (int i = 0; i < n; i++)
            x2[i] = in.nextInt();
        for (int i = 0; i < n; i++)
            y2[i] = in.nextInt();
        int ans = 0;
        int cnt = 0;

        for (int x : x1)
            for (int y : y1) {
                //对于某两个矩形的下边和左边构成的一个点，当这个点是重叠区域的左下角点时：
                //若该点和某个矩形满足如下条件时，说明该重叠区域和该矩形有交集
                for (int i = 0; i < n; i++) {
                    if (x >= x1[i] && x < x2[i] && y >= y1[i] && y < y2[i])
                        cnt++;
                }
                if (cnt > ans)
                    ans = cnt;
                cnt = 0;
            }
        System.out.println(ans);
    }
}
```


b7.牛牛的闹钟
题目描述
牛牛总是睡过头，所以他定了很多闹钟，只有在闹钟响的时候他才会醒过来并且决定起不起床。从他起床算起他需要X分钟到达教室，上课时间为当天的A时B分，请问他最晚可以什么时间起床
输入描述:
每个输入包含一个测试用例。
每个测试用例的第一行包含一个正整数，表示闹钟的数量N(N<=100)。
接下来的N行每行包含两个整数，表示这个闹钟响起的时间为Hi(0<=A<24)时Mi(0<=B<60)分。
接下来的一行包含一个整数，表示从起床算起他需要X(0<=X<=100)分钟到达教室。
接下来的一行包含两个整数，表示上课时间为A(0<=A<24)时B(0<=B<60)分。
数据保证至少有一个闹钟可以让牛牛及时到达教室。
输出描述:
输出两个整数表示牛牛最晚起床时间。
示例1
输入
3 
5 0 
6 0 
7 0 
59 
6 59

输出
6 0


给出一组时间(H:M), 一个分钟数x，及另一个时间AB（A：B） 求出满足在AB的x分钟前的离AB最近的时间

用A和B，及x计算出最晚的起床时间T，再从闹钟中挑一个时间最晚且早于T的时间即为答案
//把时间单独摘出来做一个类，便于操作（h,m）
T的计算方法：//计算当前时间的x分钟之前的时间
            Time getPreTime(int x){
            int h = hour;
            int m = min - x;
            if(m<0){
                m = 60+m;
                h = h-1;
            }
            if(h<0){
                h = 24 + h;
            }
            return new Time(h, m);
        }
从闹钟中挑：
   int sub = (T.h* 60+T.m) - (clocks[i].h * 60 + clocks[i].m)
   挑出满足sub>=0的情况下，最小的sub对应的clocks即为答案


```java

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    //把时间单独摘出来做一个类，便于操作
    static class Time{
        int hour;
        int min;
        public Time(int h, int m){
            hour = h;
            min = m;
        }

        public Time(){
        }

        //计算当前时间的x分钟之前的时间
        Time getPreTime(int x){
            int h = hour;
            int m = min - x;
            if(m<0){
                m = 60+m;
                h = h-1;
            }
            if(h<0){
                h = 24 + h;
            }
            return new Time(h, m);
        }

        //判断当前时间与at之间的差值
        int getInterval(Time at){
            return ((this.hour*60+this.min)-(at.hour*60+at.min));
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Time[] clocks = new Time[n];
        for(int i=0;i<n;i++){
            clocks[i] = new Time();
            clocks[i].hour = in.nextInt();
            clocks[i].min = in.nextInt();
        }

        int x = in.nextInt();
        int h = in.nextInt();
        int m = in.nextInt();

        Time ab = new Time(h, m);
        Time t = ab.getPreTime(x);
        HashMap<Integer, Time> map = new HashMap<>();

        //最小差值，且该值必须大等于0
        int minInterval = Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            int tmp = t.getInterval(clocks[i]);
            if(tmp>=0){
                minInterval = Math.min(minInterval, tmp);
                map.put(tmp, clocks[i]);
            }
        }
        Time ans = map.get(minInterval);
        System.out.println(ans.hour + " " + ans.min);

    }
}
```

b8.牛牛的背包问题
题目描述
牛牛准备参加学校组织的春游, 出发前牛牛准备往背包里装入一些零食, 牛牛的背包容量为w。
牛牛家里一共有n袋零食, 第i袋零食体积为v[i]。
牛牛想知道在总体积不超过背包容量的情况下,他一共有多少种零食放法(总体积为0也算一种放法)。
输入描述:
输入包括两行
第一行为两个正整数n和w(1 <= n <= 30, 1 <= w <= 2 * 10^9),表示零食的数量和背包的容量。
第二行n个正整数v[i](0 <= v[i] <= 10^9),表示每袋零食的体积。
输出描述:
输出一个正整数, 表示牛牛一共有多少种零食放法。
示例1

输入

3 10
1 2 4

输出

8
说明
三种零食总体积小于10,于是每种零食有放入和不放入两种情况，一共有2*2*2 = 8种情况。

共有n种零食，每种体积为v[i], 要放入体积为w的袋子，要求放入的总体积不超过袋子体积，有多少种不同的放法（不放算一种）

01背包问题：对于放和不放有两种选择，


这一类背包问题有两种方法，一种是当物品个数n较少，但是背包大小m比较大时采用指数级的枚举搜索（递归搜索），复杂度为O(2^n)
另一种是当背包比较小的时候采用动态规划O(nm)  让能达到的值都存一下

这道题明显是属于第一种。如果用简单的trackback递归，AC率 80%，时间复杂度太大 ，2^30的复杂度是不可以接受的，因此我们可以采用中途相遇法。把我们能接受的前15个物品先进行第一次枚举搜索，然后再对剩下的物品进行第二次枚举搜索。把第二次枚举搜索出来的结果(至多2^15=32768个答案)存入数组并排序，枚举第一次搜出来的结果，计算出还剩下多少背包体积还能装，在第二次的结果中进行二分搜索，并把两次搜索的结果进行相乘(乘法原理)。再把所有的结果进行相加(加法原理)，就是答案了。
当然也可以把物品分成n/2和n-n/2两部分。
总复杂度是O(2^(n/2)*n)


```java

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long w = in.nextLong();

        long[] v = new long[n];
        for (int i = 0; i < n; i++) {
            v[i] = in.nextLong();
        }


        ArrayList<Long> part1 = new ArrayList<>();
        ArrayList<Long> part2 = new ArrayList<>();


        //分成两部分，前一半和后一半
        trackBack(0, n/2+1,0, w, v, part1);
        trackBack(n/2+1, v.length, 0, w, v, part2);

        //把后一半排序
        Collections.sort(part2);
        int count = 0;
        for(long l: part1){
            long remain = w-l;
            //二分查找后一半中第一个大于remain的数的索引，则其前面的所有数都可以和l组合
            int index = binarySearch(part2, remain);
            if(part2.get(index)>remain){
                count += index;
            }else{
                //整个part2都可以和l组合
                count += part2.size();
            }
        }

        System.out.println(count);

    }

    static int search(ArrayList<Long> part, long target){
        int index = 0;
        for (int i=0;i<part.size();i++){
            if(target<part.get(i)){
                index = i;
                break;
            }
        }
        return Math.min(index, part.size()-1);
    }

    //二分查找找到第一个比target值大的数的索引
    static int binarySearch(ArrayList<Long> part, long target){
        int low = 0;
        int high = part.size()-1;
        int mid = (low+high)/2;
        while(low<high){
            mid = (low+high)/2;
            if(part.get(mid)==target){
                break;
            }else if(part.get(mid)>target){
                high = mid;
            }else{
                low = mid+1;
            }
        }

        if(part.get(mid)==target){
            while(mid<=(part.size()-1) && part.get(mid)==target ){
                mid++;
            }
            return Math.min(mid,high);
        }else if(part.get(mid)<target){
            while(mid<=(part.size()-1) && part.get(mid)<target){
                mid++;
            }
            return Math.min(mid,high);
        }else{
            while( mid>=0 && part.get(mid)>target){
                mid--;
            }
            return Math.max(mid+1, 0);
        }


    }


    //递归行解法
    public static void trackBack(int curIndex, int endIndex, long curW, long w, long[] v, ArrayList<Long> part) {
        if(endIndex>v.length || curIndex>endIndex){
            return;
        }

        if (curIndex == endIndex) {
            part.add(curW);
            return;
        }


        //可以放
        trackBack(curIndex + 1, endIndex,curW + v[curIndex], w, v, part);

        //也可以不放
        trackBack(curIndex + 1, endIndex,curW, w, v,part);

    }

}
```


b9.俄罗斯方块
题目描述
小易有一个古老的游戏机，上面有着经典的游戏俄罗斯方块。因为它比较古老，所以规则和一般的俄罗斯方块不同。
荧幕上一共有 n 列，每次都会有一个 1 x 1 的方块随机落下，在同一列中，后落下的方块会叠在先前的方块之上，当一整行方块都被占满时，这一行会被消去，并得到1分。
有一天，小易又开了一局游戏，当玩到第 m 个方块落下时他觉得太无聊就关掉了，小易希望你告诉他这局游戏他获得的分数。
输入描述:
第一行两个数 n, m
第二行 m 个数，c1, c2, ... , cm ， ci 表示第 i 个方块落在第几列
其中 1 <= n, m <= 1000, 1 <= ci <= n
输出描述:
小易这局游戏获得的分数
示例1
输入
复制
3 9
1 1 2 2 2 3 1 2 3
输出
复制
2


特殊的俄罗斯方块：荧幕上一共有 n 列，每次都会有一个 1 x 1 的方块随机落下，在同一列中，后落下的方块会叠在先前的方块之上，当一整行方块都被占满时，这一行会被消去，并得到1分
已知共有m个方块落下，且知道这些方块分别落在哪一列，求最终所得分数

总得分s
记录一个数字c, 记录当前有方块的列的个数
并用一个数组blocks保存当前i列上的方块数
每当有方块落到新的一列时，c+1，而当方块落到已有方块的一列时，c不变
当c=n时，s++，且将blocks中的所有数都-1，且当有列上方块数为0时，c--

最终返回s

```java

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int s = 0;
        int c = 0;
        int[] blocks = new int[n];
        int[] cols = new int[m];

        //先把输入保存下来，再做处理
        for(int i=0;i<m;i++){
            cols[i] = in.nextInt()-1;
        }

        for(int col: cols){
            if(blocks[col]==0){
                c++;
            }
            blocks[col]++;

            if(c==n){
                for(int i=0;i<n;i++){
                    blocks[i]--;
                    if(blocks[i]==0) c--;
                }
                s++;
            }
        }
        System.out.println(s);
    }

}
```


b10.瞌睡
题目描述
小易觉得高数课太无聊了，决定睡觉。不过他对课上的一些内容挺感兴趣，所以希望你在老师讲到有趣的部分的时候叫醒他一下。你知道了小易对一堂课每分钟知识点的感兴趣程度，并以分数量化，以及他在这堂课上每分钟是否会睡着，你可以叫醒他一次，这会使得他在接下来的k分钟内保持清醒。你需要选择一种方案最大化小易这堂课听到的知识点分值。
输入描述:
第一行 n, k (1 <= n, k <= 105) ，表示这堂课持续多少分钟，以及叫醒小易一次使他能够保持清醒的时间。
第二行 n 个数，a1, a2, ... , an(1 <= ai <= 104) 表示小易对每分钟知识点的感兴趣评分。
第三行 n 个数，t1, t2, ... , tn 表示每分钟小易是否清醒, 1表示清醒。
输出描述:
小易这堂课听到的知识点的最大兴趣值。
示例1
输入
复制
6 3
1 3 5 2 5 4
1 1 0 1 0 0
输出
复制
16


给2个长度为n的数组a,t，a里面是值，t里面只有0和1。再给一个值k。可以做一次处理，让t中某个位置开始连续k个位置都是1(原来是1则保持该位置仍为1)，
然后求所有在t中值为1的索引在a中的值的和的最大值。

遍历所有的可取值的位置，算连着3个的值，并且把其中本来就为1的那个值去掉，取其中最大值
然后再算一遍本来就为1的值的和，即为答案, 这样做提示算法复杂度过大，AC率为90%

关键在于怎么把n^2的复杂度降下来，可以考虑用窗口，保持窗口大小为k, 窗口在变，设置一个值表示当前窗口中t为0的数之和，每次只删一个值，只加一个值（加和删的都是t为0的）然后一直记录窗口中t为0的位置的和的最大值，然后再算一遍本来就为1的值的和，即为答案。这样用窗口可以在tO(n)得到最大值

10 5
6481 6127 4477 5436 7356 3137 1076 7182 8147 835
1     0    1    0    1    1    0    0    0    1

```java

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();

        int[] a = new int[n];
        int[] t = new int[n];

        //先把输入保存下来，再做处理
        for(int i=0;i<n;i++){
            a[i] = in.nextInt();
        }

        for(int i=0;i<n;i++){
            t[i] = in.nextInt();
        }

        int maxNewWakeScore = 0;
        int tailIndex = 0;
        int frontIndex = 0;

        int score = 0;
        for(;tailIndex<k;tailIndex++){
            if(t[tailIndex]==0){
                score += a[tailIndex];
            }
        }
        maxNewWakeScore = Math.max(score, maxNewWakeScore);
        //让尾指针回到该在的地方
        tailIndex--;

        //窗口往后走，先加新尾，再去旧头
        for(; frontIndex<n;){
            tailIndex++;
            if(tailIndex<n && t[tailIndex]==0){
                score += a[tailIndex];
            }

            if(t[frontIndex]==0){
                score -= a[frontIndex];
            }
            frontIndex++;
            maxNewWakeScore = Math.max(maxNewWakeScore, score);
        }


        int wakeScore = 0;
        for(int i=0;i<n;i++){
            if(t[i]==1)
                wakeScore += a[i];
        }


        System.out.println(wakeScore+maxNewWakeScore);
    }

}
```

b11.丰收
题目描述
又到了丰收的季节，恰逢小易去牛牛的果园里游玩。
牛牛常说他对整个果园的每个地方都了如指掌，小易不太相信，所以他想考考牛牛。
在果园里有N堆苹果，每堆苹果的数量为ai，小易希望知道从左往右数第x个苹果是属于哪一堆的。
牛牛觉得这个问题太简单，所以希望你来替他回答。
输入描述:
第一行一个数n(1 <= n <= 10^5)。
第二行n个数ai(1 <= ai <= 1000)，表示从左往右数第i堆有多少苹果
第三行一个数m(1 <= m <= 10^5)，表示有m次询问。
第四行m个数qi，表示小易希望知道第qi个苹果属于哪一堆。
输出描述:
m行，第i行输出第qi个苹果属于哪一堆。
示例1
输入
复制
5
2 7 3 4 9
3
1 25 11
输出
复制
1
5
3

有n个列表从左往右依次排开，每个列表i中元素数量为ai，有一个数字q，给出从左往右数第q个元素属于第几个列表

把n个列表中的元素个数，从左往右依次加到一个数组中：
如：2，9，12，16，25
将q依次与该元素中的数比较，当q<=num[i], 则其就在第（i+1）（因为堆数从1开始，要+1）堆中


对于数q[i]，方法是使用二分查找，依次找num中第一个大等于q[i]的数，在c++中有函数 lower_bound，在Java中要自己实现

对q[i]排序然后依次顺着在num中找的方法不可行，貌似是因为排序复杂度过大，所以在排序和二分查找做选择的时候，要选择二分查找的办法

```java

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];
        int[] num = new int[n];
        for(int i=0;i<n;i++){
            a[i]=in.nextInt();
            if(i==0){
                num[i]=a[i];
            }else{
                num[i]=num[i-1]+a[i];
            }
        }

        int m= in.nextInt();
        int[] q = new int[m];
        int[] ans = new int[m];
        for(int i=0;i<m;i++){
            q[i] = in.nextInt();
            ans[i] = binaryFoundLowerBound(num, q[i])+1;
        }

        for(int i=0;i<m;i++){
            System.out.println(ans[i]);
        }

    }

    //二分查找到数组中第一个大等于target的值，并返回其索引
    static int binaryFoundLowerBound(int[] num, int target){
        int low = 0;
        int high = num.length-1;
        int ans = num.length-1;
        while (low<high){
            int mid = (low+high)/2;
            //如果值等于target，或者 值大于target，且（前一个数小于target 或 前面没数）
            if(num[mid]==target || (num[mid]>target && (((mid-1)>=0 && num[mid-1]<target) || mid==0))){
                return mid;
            }
            else if(num[mid]>target){
                high=mid;
            }
            else if(num[mid]<target){
                low=mid+1;
            }
        }
        //如果都比target小，则返回到这里
        return ans;
    }
}
```

b12.整理房间
题目描述
又到了周末，小易的房间乱得一团糟。
他希望将地上的杂物稍微整理下，使每团杂物看起来都紧凑一些，没有那么乱。
地上一共有n团杂物，每团杂物都包含4个物品。第i物品的坐标用(ai,bi)表示，小易每次都可以将它绕着(xi,yi)逆时针旋转90°
∘
 ，这将消耗他的一次移动次数。如果一团杂物的4个点构成了一个面积不为0的正方形，我们说它是紧凑的。
因为小易很懒，所以他希望你帮助他计算一下每团杂物最少需要多少步移动能使它变得紧凑。
输入描述:
第一行一个数n(1 <= n <= 100)，表示杂物的团数。
接下来4n行，每4行表示一团杂物，每行4个数ai, bi，xi, yi, (-10^4 <= xi, yi, ai, bi <= 10^4)，表示第i个物品旋转的它本身的坐标和中心点坐标。
输出描述:
n行，每行1个数，表示最少移动次数。
示例1
输入
复制
4
1 1 0 0
-1 1 0 0
-1 1 0 0
1 -1 0 0
1 1 0 0
-2 1 0 0
-1 1 0 0
1 -1 0 0
1 1 0 0
-1 1 0 0
-1 1 0 0
-1 1 0 0
2 2 0 1
-1 0 0 -2
3 0 0 -2
-1 1 -2 0
输出
复制
1
-1
3
3
说明
对于第一团杂物，我们可以旋转第二个或者第三个物品1次。

有n个点组，每个点组包含4个点的横纵坐标，对于一个组中的某个点(ai,bi)，都有一个它对应的中心坐标(xi,yi)，每次可以让该点围绕它的中心点逆时针旋转90度，
旋转一次将耗费一次移动次数，当一个点组中的四个点能构成一个面积不为0的正方形，则说该点组是紧凑的，计算每个点组需要几步移动才能使它变得紧凑，如果不能则返回-1


## 笛卡尔坐标系内点旋转公式：
(a,b)为旋转中心，（x,y）为旋转初始点，(x',y')为旋转目标点

θ 是逆时针的旋转角
x' = xcosθ  - ysinθ  + a(1-cosθ ) + bsinθ 
y' = xsinθ  + ycosθ  + a(-sinθ ) + b(1-cosθ) 

当θ = 90°时
x' =  -y + a + b
y' = x -a + b

对每一组点，能构成的排列组合形式有 4* 4* 4* 4共256种可能性，可以直接枚举。

## 如何判断四个点能否构成正方形
    判断方法如下：
    若有3个点的x相等或3个点的y相等，直接为false
    选定1个点p1, 则其余3个点分别为：p2,p3,p4
    始终以p1作为要判断的角的顶点，则有如下几种可能：
        p1与p2对角，p1p3 = p1p4=p2p3=p2p4，且p3p1p4为直角
        p1与p3对角，p1p2 = p1p4=p3p2=p3p4，且p2p1p4为直角
        p1与p4对角，p1p2 = p1p3=p4p2=p4p3，且p2p1p3为直角

1.距离：
两个点(x,y), (x',y')之间的距离为sqrt((x-x')^2+(y-y')^2)
仅做相等判断的话，则可不用根号，直接用根号内的公司判断即可

2.角度
判断三点连续构成的角是否为直角，第一个点参数为顶点： （如果两条直线的斜率都存在。则,它们的斜率之积=-1，更一般起见，就用下面的公式）
bool IsRightAngle(int x1,int y1,int x2,int y2,int x3,int y3){
    if((x2-x1)* (x3-x1)+(y2-y1)* (y3-y1)==0)
        return true;
    return false;
}

```java

import java.util.Scanner;

public class Main {
    static class Point{
        int x;
        int y;

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        //返回该点与点p之间距离的的平方
        int squareOfDistance(Point p){
            return (p.x-x)*(p.x-x) + (p.y-y)*(p.y-y);
        }
    }

    static class PointGroup{
        Point pivot; //轴点
        Point main;  //主点

        PointGroup(Point m, Point p){
            main = m;
            pivot = p;
        }

    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        //每4个点是一组
        PointGroup[][] points = new PointGroup[n][4];
        for(int i=0;i<n;i++){
            for(int j=0;j<4;j++){
                Point m = new Point(in.nextInt(), in.nextInt());
                Point p = new Point(in.nextInt(),in.nextInt());
                points[i][j] = new PointGroup(m, p);
            }
        }

        int[] ans = new int[n];
        for(int i=0;i<n;i++){
            Point p1,p2,p3,p4;

            int minStep = Integer.MAX_VALUE;
            for(int i1=0;i1<4;i1++){
                p1 = rotate(points[i][0].main, points[i][0].pivot, i1);
                for(int i2=0;i2<4;i2++){
                    p2 = rotate(points[i][1].main, points[i][1].pivot, i2);
                    for(int i3=0;i3<4;i3++){
                        p3 = rotate(points[i][2].main, points[i][2].pivot, i3);
                        for(int i4=0;i4<4;i4++){
                            p4 = rotate(points[i][3].main, points[i][3].pivot, i4);
                            if(isSquare(p1,p2,p3,p4)){
                                minStep = Math.min(minStep, i1+i2+i3+i4);
                            }
                         }
                    }
                }
            }
            if(minStep==Integer.MAX_VALUE) ans[i]=-1;
            else ans[i]=minStep;
        }

        for(int i=0;i<n;i++){
            System.out.println(ans[i]);
        }

    }

    //将p绕pivot旋转count个90度后的点返回
    public static Point rotate(Point p, Point pivot, int count){
        Point ans = new Point(p.x, p.y);
        Point pre = new Point(p.x, p.y);
        for(int i=0;i<count;i++){
            ans.x = -pre.y+pivot.x+pivot.y;
            ans.y = pre.x - pivot.x + pivot.y;
            pre.x = ans.x;
            pre.y = ans.y;
        }
        return ans;
    }

    public static boolean isRightAngle(Point p1, Point p2, Point p3){
        if((p2.x-p1.x)* (p3.x-p1.x)+(p2.y-p1.y)* (p3.y-p1.y)==0)
            return true;
        return false;
    }

    //判断四个点能否构成正方形
    public static boolean isSquare(Point p1, Point p2, Point p3, Point p4){
        //若有三个点x或y相等，直接返回false
        if((p1.x==p2.x && p1.x==p3.x) || (p1.x==p2.x && p1.x==p4.x) || (p1.x==p3.x && p4.x==p3.x)|| (p2.x==p3.x && p4.x==p3.x)
            || (p1.y==p2.y && p1.y==p3.y) || (p1.y==p2.y && p1.y==p4.y) || (p1.y==p3.y && p4.y==p3.y)|| (p2.y==p3.y && p4.y==p3.y)){
            return false;
        }
        //若始终以p1作为要判断的角的顶点，则有如下几种可能：
        /*
        p1与p2对角，p1p3 = p1p4=p2p3=p2p4，且p3p1p4为直角
        p1与p3对角，p1p2 = p1p4=p3p2=p3p4，且p2p1p4为直角
        p1与p4对角，p1p2 = p1p3=p4p2=p4p3，且p2p1p3为直角
         */
        if(p1.squareOfDistance(p3)==p1.squareOfDistance(p4) && p2.squareOfDistance(p3)==p2.squareOfDistance(p4) && p1.squareOfDistance(p4) == p2.squareOfDistance(p3)){
            if(isRightAngle(p1,p3,p4)) return true;
            else return false;
        }

        if(p1.squareOfDistance(p2)==p1.squareOfDistance(p4) && p3.squareOfDistance(p2)==p3.squareOfDistance(p4) && p1.squareOfDistance(p2) == p3.squareOfDistance(p2)){
            if(isRightAngle(p1,p2,p4)) return true;
            else return false;
        }

        if(p1.squareOfDistance(p2)==p1.squareOfDistance(p3) && p4.squareOfDistance(p2)==p4.squareOfDistance(p3) && p1.squareOfDistance(p2) == p4.squareOfDistance(p3)){
            if(isRightAngle(p1,p2,p3)) return true;
            else return false;
        }
        return false;
    }
}

```


b13.表达式求值

今天上课，老师教了小易怎么计算加法和乘法，乘法的优先级大于加法，但是如果一个运算加了括号，那么它的优先级是最高的。例如：
1+2* 3=7
1* (2+3)=5
1* 2* 3=6
(1+2)* 3=9
现在小易希望你帮他计算给定3个数a，b，c，（abc的顺序不变）在它们中间添加"+"， "* "， "("， ")"符号，能够获得的最大值。
输入描述:
一行三个数a，b，c (1 <= a, b, c <= 10)
输出描述:
能够获得的最大值
示例1
输入
复制
1 2 3
输出
复制
9


有以下几种可能的算法：

先算ab，在算c ： (a+b)+c  (a+b)* c  (a* b)+c  (a* b) * c
先算bc，再算a    a+ (b+c) a* (b+c)  a+ (b* c) a* (b* c)

共有下面几种办法：
a+b+c, (a+b)* c  a+(b* c)  (a* b) +c  a* (b+c)  a* b* c

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        int b = in.nextInt();
        int c = in.nextInt();
        int max = 0;
        max = Math.max(a+b+c, max);
        max = Math.max((a+b)*c, max);
        max = Math.max(a+b*c, max);
        max = Math.max(a*(b+c), max);
        max = Math.max(a*b*c, max);
        System.out.println(max);
    }
}
```

b14.塔
小易有一些立方体，每个立方体的边长为1，他用这些立方体搭了一些塔。
现在小易定义：这些塔的不稳定值为它们之中最高的塔与最低的塔的高度差。
小易想让这些塔尽量稳定，所以他进行了如下操作：每次从某座塔上取下一块立方体，并把它放到另一座塔上。
注意，小易不会把立方体放到它原本的那座塔上，因为他认为这样毫无意义。
现在小易想要知道，他进行了不超过k次操作之后，不稳定值最小是多少。
输入描述:
第一行两个数n,k (1 <= n <= 100, 0 <= k <= 1000)表示塔的数量以及最多操作的次数。
第二行n个数，ai(1 <= ai <= 10^4)表示第i座塔的初始高度。
输出描述:
第一行两个数s, m，表示最小的不稳定值和操作次数(m <= k)
接下来m行，每行两个数x,y表示从第x座塔上取下一块立方体放到第y座塔上。
示例1
输入
复制
3 2
5 8 5
输出
复制
0 2
2 1
2 3

数组中有一些数，不稳定值为其中最大数与最小数之差，每次可以把某个数-1，再让每个数+1，这视为一个操作，不超过k次操作后，不稳定值最小是多少

一定是每次把最大的-1，最小的+1，每次都遍历找最大最小的，并记录交换的次数。如要求每次的索引，则可把索引记录到一个数组中。
最关键的一步在于，如果最大的和最小的之间差1，那就不用再移动了，因为再移动也是左右横跳。因此，当最大的和最小的差小等于1时，就可以不操作了


```java

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] a = new int[n];
        for(int i=0;i<n;i++){
            a[i] = in.nextInt();
        }

        int s=0,m=0;
        ArrayList<int[]> record = new ArrayList<>();
        for(int i=0;i<k;i++){
            int minIndex = 0;
            int maxIndex = 0;
            for(int j=0;j<n;j++){
                minIndex = a[j]<a[minIndex]?j:minIndex;
                maxIndex = a[j]>a[maxIndex]?j:maxIndex;
            }
            //如果出现塔高度总和为奇数，即永远无法平衡，且给的移动次数过多，就会导致左右横跳。所以如果最高和最低的差1时，就已经平衡了
            if(a[maxIndex]-a[minIndex]<=1){
                break;
            }else {
                a[maxIndex]--;
                a[minIndex]++;
                int[] tmp = new int[2];
                tmp[0] = maxIndex+1;
                tmp[1] = minIndex+1;
                record.add(tmp);
                m++;
            }
        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i=0;i<n;i++){
            max = Math.max(max, a[i]);
            min = Math.min(min, a[i]);
        }
        s = max-min;
        System.out.println(s+" "+m);
        for(int i=0;i<m;i++){
            System.out.println(record.get(i)[0]+" "+record.get(i)[1]);
        }

    }
}
```

b15.小易的字典
题目描述
小易在学校中学习了关于字符串的理论, 于是他基于此完成了一个字典的项目。

小易的这个字典很奇特, 字典内的每个单词都包含n个'a'和m个'z', 并且所有单词按照字典序排列。

小易现在希望你能帮他找出第k个单词是什么。

输入描述:
输入包括一行三个整数n, m, k(1 <= n, m <= 100, 1 <= k <= 10^9), 以空格分割。

输出描述:
输出第k个字典中的字符串，如果无解，输出-1。
示例1
输入
2 2 6

输出
zzaa

说明
字典中的字符串依次为aazz azaz azza zaaz zaza zzaa

即n个a和m个z组成的字符串。找出第k个单词是什么

这个题能想到组合数就好办多了，C(a,b):从a个不同的物品中取出b个，不考虑顺序的取法
n个a和m个b，相当于一共有m+n个空位，从中取出n个来放置a，则取法一共有C(m+n,n)种，因此，组合的个数一共有C(m+n,n)


思路：
1.假设第一个字符为a，则剩下n-1个'a'和m个'z'组成的子序列只能构成count(n-1+m,n-1)个单词，且是字典中前count(n-1+m,n-1)个单词。
2.比较k和count(n-1+m,n-1)，若k小，说明k是前count(n-1+m,n-1)个单词，则第一个字符必为'a'。子问题化为在子序列(n-1个'a'和m个'z')找到第k个单词
3.若k大，则说明第一个字符必为'z',单词是以'z'开头的单词中的第k-count(n-1+m,n-1)个。子问题化为在子序列(n个'a'和m-1个'z')找到第k-count(n+m-1,m-1)个单词。

eg:n=2,m=2,k=5
假设第一个字符为a,则剩下1个a,2个z只能构成3个单词，且是字典中前3个单词(aamm,amam,amma)
k>3，则第一个字符必为z。原问题化为在n=2,m=1,k=2，即在剩下2个a，1个z中找到第2个单词


```java
//

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        long k = in.nextInt();
        StringBuilder builder = new StringBuilder();
        while(n>0 && m>0){
            long count = 1;
            for (int i = 0; i < n - 1; i++) {//求组合数c(m+n-1, n-1)
                count *= n - 1 + m - i;
                count /= (i + 1);
                if (count > k)break;//防止越界。count>k就可以退出计算了
            }

            if(count>=k){
                n--;
                builder.append('a');
                //问题缩减为 n-1个a和m个z 中找第k大
            }else{
                m--;
                builder.append('z');
                k -= count; //问题缩减为 n-1个a和m个z 中找第k-count大
            }
        }

        //循环结束后，剩余子序列只存在"aa..aaa" 或 "zz..zzz"1种情况,即n和m有一个用完了，有一个没用完,没用完的全加在后面即可
        //到这一步，范围一定缩小到一个序列了，k=1即是找到剩下序列的第一个序列，即，如果不是，说明就不在这个范围内
        if (k != 1) {//
            System.out.println(-1);;
            return;
        }

        if(n>0){
            for(int i=0;i<n;i++){
                builder.append('a');
            }
        }else if(m>0){
            for(int i=0;i<m;i++){
                builder.append('z');
            }
        }
        System.out.println(builder.toString());
    }

}
```

b16.获得最多的奖金
题目描述
小明在越南旅游，参加了当地的娱乐活动。小明运气很好，拿到了大奖， 到了最后的拿奖金环节。小明发现桌子上放着一列红包，每个红包上写着奖金数额。
现在主持人给要求小明在这一列红包之间“切”2刀，将这一列红包“切”成3组，并且第一组的奖金之和等于最后一组奖金和（允许任意一组的红包集合是空）。最终第一组红包的奖金之和就是小明能拿到的总奖金。小明想知道最多能拿到的奖金是多少，你能帮他算算吗。

举例解释：桌子上放了红包  1, 2, 3, 4, 7, 10。小明在“4,7”之间、“7,10” 之间各切一刀，将红包分成3组 [1, 2, 3, 4]   [7]   [10]，其中第一组奖金之和=第三组奖金之和=10，所以小明可以拿到10越南盾。
输入描述:
第一行包含一个正整数n，(1<=n<= 200 000)，表示有多少个红包。

第二行包含n个正整数d[i]，表示每个红包包含的奖金数额。其中1<= d[i] <= 1000 000 000
输出描述:
小明可以拿到的总奖金
示例1
输入
5
1 3 1 1 4
输出
5
说明
[1,3,1]  [ ]   [1,4] ，其中第一组奖金和是5，等于第三组奖金和。所以小明可以拿到5越南盾
示例2
输入
5
1 3 2 1 4
输出
4
说明
[1,3]   [2,1]  [4]，小明可以拿到4越南盾
示例3
输入
3
4 1 2
输出
0
说明
[ ]  [4, 1, 2] [ ] ，小明没办法，为了保证第一组第三组相等，只能都分成空的。所以小明只能拿到0越南盾。

一个数组，要求把数组“切”成三组，并且必须保证第一个数组之和等于最后一个数组之和（允许任意一组是空），最终第一组数组之和就是能得到的奖金，求最多能拿到的奖金

如 1 3 1 1 4， sum1表示第一组中的和，sum3表示第三组中的和

注意的是，第三组不能每次都从后往前重新开始，会超时

i为第一组的后索引，j为第三组的前索引
    若sum3大于sum1，j不用动，i++ sum1+=d[i]；
    若sum3==sum1，加入结果集，i++ sum1+=d[i]；
    若sum3< sum1，i不动，j++，sum3+=d[j]

注意的是，为了不把两端的情况漏掉，i从-1开始，j从n开始（因为每次添加d[i]或d[j]之前，i和j都要+1，所以不存在越界）

从空开始，sum1=0，sum3=0，满足，加入答案集
第一组加入1，sum1=1，sum3=0， sum3+=4
sum1< sum3, sum1+=3
sum1==sum3==4  4加入答案集。sum1+=1
sum1(5)>sum3(4) sum3+=1
sum1(5)==sum3(5) 5加入答案集，sum1+=1
i=3,j=3跳出循环


1<=n<= 200 000

1<= d[i] <= 1000 000 000

```java

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long[] d = new long[n];
        for(int i=0;i<n;i++){
            d[i] = in.nextLong();
        }

        long sum1 = 0;
        long sum3 = 0;

        int i=-1;  //第一组索引从-1开始，第三组索引从n开始，是为了不把两段的漏了
        int j = n; //为第三组当前最左边的索引
        //sum3也不用每次都从末尾开始，若sum3大于sum1，j不用动，i加；若sum3==sum1，加入结果集，i加，若sum3<sum1，i不动，j加
        long ans = 0;
        //sum1能加到倒数第二个数
        while (i<j){
            if(sum3==sum1){
                i++;
                ans = Math.max(ans, sum1);
                sum1 += d[i];
            }else if(sum3>sum1){
                i++;
                sum1 += d[i];
            }else{
                j--;
                sum3 += d[j];
            }
        }
        System.out.println(ans);

    }

}
```

b17.将满二叉树转换为求和树
题目描述
给出满二叉树，编写算法将其转化为求和树

什么是求和树：二叉树的求和树， 是一颗同样结构的二叉树，其树中的每个节点将包含原始树中的左子树和右子树的和。

二叉树：
                  10
               /      \
             -2        6
           /   \      /  \ 
          8    -4    7    5

求和树：
                 20(4-2+12+6)
               /      \
           4(8-4)      12(7+5)
            /   \      /  \ 
          0      0    0    0

二叉树给出前序和中序输入，求和树要求中序输出；
所有处理数据不会大于int；

输入描述:
2行整数，第1行表示二叉树的前序遍历，第2行表示二叉树的中序遍历，以空格分割
输出描述:
1行整数，表示求和树的中序遍历，以空格分割
示例1
输入
复制
10 -2 8 -4 6 7 5 
8 -2 -4 10 7 6 5
输出
复制
0 4 0 20 0 12 0

求一个满二叉树的求和树，二叉树的求和树， 是一颗同样结构的二叉树，其树中的每个节点是原始树中的左子树和右子树的和。

读入一行：Scanner.nextLine，然后可以用split分割

》方法一
先通过中序和前序构建原树，然后根据原树的结构构建与其同构的新树。可知原树中的节点和新树中的节点可以一一对应
新树节点值 = 原树中对应节点的 左子树所有节点值 + 右子树所有节点值 （以递归形式赋值）。

》方法二：
    因为是满二叉树，其实结果跟前序遍历数组无关，只和中序遍历数组有关，并且中序数组一定是奇数个，结果索引为偶数的一定为0，索引为奇数的值是中序遍历数组其他值之和(不包括自己)，使用二分法找到根节点，然后计算子树之和，不用还原二叉树。

```java

import java.util.Scanner;
import java.util.Stack;

public class Main {
    static class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;

        TreeNode(int v){
            val = v;
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String preStr = in.nextLine();
        String[] pres = preStr.split(" ");
        String inStr = in.nextLine();
        String[] ins = inStr.split(" ");

        int[] inOrder = new int[ins.length];
        int[] preOrder = new int[ins.length];
        int len = ins.length;
        for(int i=0;i<len;i++){
            inOrder[i] = Integer.valueOf(ins[i]);
            preOrder[i] = Integer.valueOf(pres[i]);
        }
        TreeNode ori = buildTree(inOrder,0,len-1,preOrder,0,len-1);
        TreeNode newRoot = buildSameStructTree(ori);
        getChildrenSum(newRoot, ori);

        
        int[] ans = new int[len];
        int i=0;

        //中序遍历
        Stack<TreeNode> stk = new Stack<>();
        TreeNode p = newRoot;
        while (p!=null || !stk.isEmpty()){
            if(p!=null){
                stk.push(p);
                p = p.left;
            }else{
                p = stk.pop();
                ans[i++] = p.val;
                p = p.right;
            }

        }
        for (int j=0;j<i;j++){
            if(j==i-1){
                System.out.print(ans[j]);
            }else
                System.out.print(ans[j]+ " ");
        }

    }

    static int getChildrenSum(TreeNode node, TreeNode oriNode){
        if(node==null) return 0;
        //
        node.val = getChildrenSum(node.left, oriNode.left)+getChildrenSum(node.right, oriNode.right);
        return oriNode.val + node.val;
    }

    //构建和ori同构的树
    static TreeNode buildSameStructTree(TreeNode ori){
        if(ori==null) return null;
        TreeNode root = new TreeNode(0);
        root.left = buildSameStructTree(ori.left);
        root.right = buildSameStructTree(ori.right);
        return root;
    }

    //根据前序和中序构建二叉树
    static TreeNode  buildTree(int[] inOrder, int inStart, int inEnd, int[] preOrder, int preStart, int preEnd){
        if(inStart>inEnd || preStart>preEnd || inEnd>=inOrder.length || preEnd >= preOrder.length)
            return null;
        int rootVal = preOrder[preStart];
        int inRootIndex = 0;
        for(int i=inStart;i<=inEnd;i++){
            if(inOrder[i]==rootVal){
                inRootIndex = i;
                break;
            }
        }
        int leftCount = inRootIndex-inStart;
        int rightCount = inEnd - inRootIndex;
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTree(inOrder, inStart, inRootIndex-1, preOrder, preStart+1, preStart+leftCount);
        root.right = buildTree(inOrder, inRootIndex+1, inEnd, preOrder, preStart+leftCount+1, preEnd);
        return root;

    }

}
```

b18.搭积木
题目描述
小明有一袋子长方形的积木，如果一个积木A的长和宽都不大于另外一个积木B的长和宽，则积木A可以搭在积木B的上面。好奇的小明特别想知道这一袋子积木最多可以搭多少层，你能帮他想想办法吗？
定义每一个长方形的长L和宽W都为正整数，并且1 <= W <= L <= INT_MAX, 袋子里面长方形的个数为N, 并且 1 <= N <= 1000000.
假如袋子里共有5个积木分别为 (2, 2), (2, 4), (3, 3), (2, 5), (4, 5), 则不难判断这些积木最多可以搭成4层, 因为(2, 2) < (2, 4) < (2, 5) < (4, 5)。
输入描述:
第一行为积木的总个数 N

之后一共有N行，分别对应于每一个积木的宽W和长L
输出描述:
输出总共可以搭的层数
示例1
输入
5
2 2
2 4
3 3
2 5
4 5
输出
4

有一袋子长方形的积木(二维数组，第一维有多少个，第二维是长和宽)，如果一个积木A的长和宽都不大于另外一个积木B的长和宽，则积木A可以搭在积木B的上面。
求这一袋子积木最多可以搭多少层，你能帮他想想办法吗？

把积木先按长排序，再按宽排序（宽的优先级高）

(2, 2), (3, 3)，(2, 4), (2, 5), (4, 5)

(2, 2), (2, 4), (2, 5)，(3, 3), (4, 5)

在数组中求长的最长递增子序列，但这里不能按照普通的那种两层循环来求（即dp[i]是以第i个元素结尾（从第0个开始算）的最长子序列
这种思想），这样做会超时。

用另一种思路：dp中存储堆叠积木的长度，dp是一个上升(不减)的数组

int[] dp = new int[n]
int count //能达到的最大层数

for(int i=0;i< n;i++){
    if(count==0 || bricks[i].l>=dp[count-1]){  //第一个，或者遍历到的长度不小于当前最大长度，直接加入数组
        dp[count++] = bricks[i].l;
    }else{
        //设bricks[i]是当前积木
        //dp中的积木的宽一定都小于当前积木的宽（因为宽是排过序的），而当前积木的长又小于dp中最长的积木
        //将第一个比bricks[i]的长大的位置找出来将其替换为bricks[i]，目的不是为了替换当前的积木塔中的积木，而是为了是为了另开一个积木塔
        // 而”共用"前面比它小的部分，在后续如果比它宽更大的就连在它的后面即可
        index = upper_bound(0, count, dp[count])

    }
}

再举个例子(2, 2), (2, 4)，(2, 5), (20, 2), (30, 2), (40,2)
按宽度排序即为上面的顺序，然后按照上述的算法：
(2, 2)  count:1
(2, 2), (2, 4) count:2
(2, 2), (2, 4)，(2, 5) count:3

(2, 2), (20, 2)，(2, 5), count:3  //(2,4已经用不到了，相当于重开了个塔：(2, 2), (20, 2) )，但count始终保存当前能达到的最大层数，当前积木不一定在最高的塔中

(2, 2), (20, 2)，(30, 2) count:3

(2, 2), (20, 2)，(30, 2) （40，2）count:4

最高的塔层数为4


//如果把brick单开一个类，会超时，所以当能用原生类型的时候，尽量不要新开类，创建对象所需的时间成本有时候也较大

```java

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[][] bricks = new int[n][2];
        for(int i=0;i<n;i++){
            bricks[i][0] = in.nextInt();
            bricks[i][1] = in.nextInt();
        }

        Arrays.sort(bricks, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]!=o2[0]){
                    return o1[0]-o2[0]; //宽的优先级高
                }else{
                    return o1[1]-o2[1];
                }
            }
        });

        int[] dp = new int[n];
        dp[0] = 1;
        int count = 0;
        for(int i=0;i<n;i++){
            //这里不用像普通的求最长子序列一样又遍历一遍0到i
            if(count == 0 || bricks[i][1] >= dp[count - 1]){
                dp[count] = bricks[i][1];
                count++;
            }else{
                //设bricks[i]是当前积木
                //dp中的积木的宽一定都小于当前积木的宽（因为宽是排过序的），而当前积木的长又小于dp中最长的积木
                //将第一个比bricks[i]的长大的位置找出来将其替换为bricks[i]，目的不是为了替换当前的积木塔，而是为了是为了另开一个积木塔
                // 而共用前面比它小的部分，在后续如果比它宽更大的就连在它的后面即可
                int index = upperBound(dp, 0, count, bricks[i][1]);
                dp[index] = bricks[i][1];
            }
        }

        System.out.println(count);
    }

    /**
     * upper_bound函数要求在按照非递减顺序排好序的数组中找到第一个大于给定值key的那个数索引，
     * 其基本实现原理是二分查找
     */
    public static int upperBound(int []nums ,int l,int r, int target){
        while(l<r){
            int m = (l+r)/2;
            if(nums[m]<=target) l = m+1;
            else    r = m;
        }
        return l;
    }

}


```

b19.魔法深渊

题目描述
前几个月放映的头号玩家简直火得不能再火了，作为一个探索终极AI的研究人员，月神自然去看了此神剧。
由于太过兴奋，晚上月神做了一个奇怪的梦，月神梦见自己掉入了一个被施放了魔法的深渊，月神想要爬上此深渊。

已知深渊有N层台阶构成（1 <= N <= 1000)，并且每次月神仅可往上爬2的整数次幂个台阶(1、2、4、....)，请你编程告诉月神，月神有多少种方法爬出深渊
输入描述:
输入共有M行，(1<=M<=1000)

第一行输入一个数M表示有多少组测试数据，

接着有M行，每一行都输入一个N表示深渊的台阶数
输出描述:
输出可能的爬出深渊的方式
示例1
输入
复制
4
1
2
3
4
输出
复制
1
2
3
6
备注:
为了防止溢出，可将输出对10^9 + 3取模

共有N个台阶，每次可网上爬2的整数次幂，问共有多少种方法爬上去
n最大为1000，也就是说最大只有2的10次方，为了防止溢出，可将输出对10^9 + 3取模

dp[i] 为爬上i层有多少种爬法
对于层数为i时，
    当i为2的幂次时，dp[i] = 1 + Σdp[i-xx]  (xx为所有小于i的2的幂次数)
    当i不为2的幂次时，dp[i] = Σdp[i-xx]  (xx为所有小于i的2的幂次数)

```java

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    static final int UPPER = (int) (Math.pow(10, 9) + 3);

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        //n最大是1000，也就是说最大只有2的10次方
        HashSet<Integer> powerSet = new HashSet<>();
        ArrayList<Integer> powerList = new ArrayList<>();

        powerSet.add(1);
        powerList.add(1);
        for (int i = 1, x = 1; i <= 10; i++) {
            x *= 2;
            powerSet.add(x);
            powerList.add(x);
        }

        int[] n = new int[m];
        int max = 0;

        for (int i = 0; i < m; i++) {
            n[i] = in.nextInt();
            max = Math.max(max, n[i]);
        }
            /*
            对于2的幂次的阶梯，每次都能爬一次就到达

             */

        //爬i层有多少种爬法
        int[] dp = new int[max + 1];
        for (int i = 1; i <= max; i++) {
            //若为2的幂次，首先初始化就为1，因为可以直接到达
            if (powerSet.contains(i))
                dp[i] = 1;

            for (int j = 0; j < powerList.size(); j++) {
                if (i <= powerList.get(j))
                    break;
                dp[i] += (dp[i - powerList.get(j)] % UPPER);
                dp[i] = dp[i] % UPPER;
            }
            dp[i] = dp[i] % UPPER;
        }
        for (int i = 0; i < m; i++) {
            System.out.println(dp[n[i]]);
        }
    }
}

```

b20.善变的同伴
题目描述
又到了吃午饭的时间，你和你的同伴刚刚研发出了最新的GSS-483型自动打饭机器人，现在你们正在对机器人进行功能测试。
为了简化问题，我们假设午饭一共有N个菜，对于第i个菜，你和你的同伴对其定义了一个好吃程度（或难吃程度，如果是负数的话……）A[i]，
由于一些技（经）术（费）限制，机器人一次只能接受一个指令：两个数L, R——表示机器人将会去打第L~R一共R-L+1个菜。
本着不浪费的原则，你们决定机器人打上来的菜，含着泪也要都吃完，于是你们希望机器人打的菜的好吃程度之和最大
然而，你善变的同伴希望对机器人进行多次测试（实际上可能是为了多吃到好吃的菜），他想知道机器人打M次菜能达到的最大的好吃程度之和
当然，打过一次的菜是不能再打的，而且你也可以对机器人输入-1, -1，表示一个菜也不打
输入描述:
第一行：N, M

第二行：A[1], A[2], ..., A[N]
输出描述:
一个数字S，表示M次打菜的最大好吃程度之和
示例1
输入
7 2
1 2 3 -2 3 -10 3

输出
10
说明
[1 2 3 -2 3] -10 [3]

示例2
输入
7 4
1 2 3 -2 3 -10 3
输出
12
说明
[1 2 3] -2 [3] -10 [3]

第四次给机器人-1, -1的指令
备注:
N <= 10^5 = 100000

|A[i]| <= 10^4 = 10000

10%数据M = 1

50%数据M <= 2

80%数据M <= 100

100%数据M <= 10^4 = 10000


给出一个数组，其中有正有负，给出一个数m（选取操作次数），表示最多可以在其中选取的连续子数组的个数，当然也可以一个都不取，上限为m，下限为0，求取出的数能达到的最大和是多少

先对数组进行处理，遇到连着正的或者负的就累加在一起

    /*
    举几组例子方便理解：
    输入：7 7 【1 2 3 -2 3 -10 3】
    输出ss:[6 -2 3 -10 3]
    输入：10 1 【-1 2 4 -3 5 -7 11 24 -6 -9】
    输出ss:[-1 6 -3 5 -7 35]
    输入：6 1 【1 -2 -3 -4 -5 -6】
    输出ss：[1]
    输入：7 1 【1 -1 -2 -3 -4 2 3】
    输出ss：[1 -10 5]
    这样做的目的可以有效减少接下来二维dp的运算量
    否则本题直接用dp会因为运算量过大只通过80%的测试案例
    */


************最大m子段和***********
给定由n个整数（可能为负）组成的序列a1、a2、a3...,an,
以及一个正整数m，要求确定序列的m个不相交子段，使这m个子段的总和最大

定义二维数组dp， dp[i][j]，表示前 j 项所构成 i 子段的最大和，且必须包含着第j项，即以第j项结尾

求dp[ i ][ j ]，有两种情况

1、dp[ i ][ j ] = dp[ i ] [ j-1 ] + a[ j ] ，即把第j项融合到第 j-1 项的子段中，子段数没变；而且是融合进去总段数没变，且每个段中的元素必须是连续的，所以第j项只能融合进dp[i][j-1]

2、dp[ i ][ j ] = dp[ i-1 ] [ t ] + a[ j ]，（i-1<= t < j ） a[j]是单独的一个子段，与前面的i-1个字段（a[j]前的最后一个子段不一定是dp[i-1][j-1],因为段内部是连续的，但是段之间不一定是连续的）

for(int i=1;i<=m;i++){
    for(int j=i;j<=n;j){
        for(int t=i-1;t<=j-1;t++){
            dp[i][j] = max(dp[i][j-1], dp[i-1][t]) + a[j-1]
        }
    }
}

优化：
dp[i][j] = max(dp[i][j-1], dp[i-1][t]) + a[j-1]  （a[j-1]是第j个数）,此时的dp[i-1][t]遍历 dp[i-1][i-1]...dp[i-1][j-1]

对于dp[i][j+1] = max(dp[i][j], dp[i-1][t]) + a[j]  （a[j]是第j+1个数），此时的dp[i-1][t] 遍历 dp[i-1][i-1]...dp[i-1][j]，
只比上一步的dp[i-1][t]多了一个dp[i-1][j]。

因此，设一个max保存dp[i-1][t]的最大值, 每当我们计算到dp[i][j]时， max=(max, dp[i-1][j-1])
     计算到dp[i][j+1]时，只需要比较一下当前max和dp[i-1][j]，取其更大值即可，并把该更大值再存入max供下一个计算使用，这样就不用每次都遍历一遍t了，省去了一个复杂度

改进为：
for(int i=1;i<=m;i++){
    int max = 0;  //对同一个i，max要跟着j的更新而更新
    for(int j=i;j<=n;j){
        max = Math.max(max, dp[i-1][j-1]);
        dp[i][j] = Math.max(dp[i][j-1], max)+a[j-1];
    }
}
遍历所有的dp[m][?],最大值即为答案，要求是让必须分成m段，所以要取第一个维度为m

***********证明结束***********

因为该题是比上面更复杂一步，可以取小于m个段，所以要遍历所有的dp[i][j]，找出其中的最大值
由于对数组已经预处理过了（连续的正和负合并）预处理后为ss，就直接使用ss代替上面算法中的a即可





下面代码，总提示数组越界，只能AC 80%，但没发现哪里有问题，输入0个数也做了处理

```java

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[] array = new int[n];

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }

        if(n==0){
            System.out.println(0);
            return;
        }

        //合并连续的同符号数字
        boolean preIsPos = array[0] >= 0;
        list.add(array[0]);
        for (int i = 1; i < n; i++) {
            if (array[i] >= 0) {
                if (preIsPos) {
                    list.set(list.size() - 1, array[i] + list.get(list.size() - 1));
                } else {
                    list.add(array[i]);
                }
                preIsPos = true;
            } else {
                if (preIsPos) {
                    list.add(array[i]);
                } else {
                    list.set(list.size() - 1, array[i] + list.get(list.size() - 1));
                }
                preIsPos = false;
            }
        }
        //把合并的结果放入数组中
        int len = list.size();
        int[] ss = new int[len];
        int notNav = 0;
        for (int i = 0; i < len; i++) {
            ss[i] = list.get(i);
            if(ss[i]>=0) notNav++;
        }


        int ans = 0;
        //如果可操作数大等于所有非负数的个数，则直接返回所有非负数之和
        if (m >= len) {
            for (int i = 0; i < len; i++) {
                if (list.get(i) > 0) ans += ss[i];
            }

        } else {
            //dp[i][j] 前j各元素（包括第j个）组成i段的最大和
            int[][] dp = new int[m + 1][len + 1];
            for (int i = 1; i < m + 1; i++) { //最少分1段，最多m段
                int max = 0;
                for (int j = i; j <= ss.length; j++) {
                    //对于dp[i][j] = dp[i-1][t] + a[j]，0<t<j
                    //这里用max来表示dp[i-1][t]的最大值

                    //max是dp[i][k] k从i到j-1的 中的最大值
                    max = Math.max(max, dp[i - 1][j - 1]);
                    dp[i][j] = Math.max(dp[i][j - 1], max) + ss[j - 1];
                    ans = Math.max(ans, dp[i][j]);
                }
            }
        }
        System.out.println(ans);
    }
}
```


b21.字符串归一化
题目描述

通过键盘输入一串小写字母(a~z)组成的字符串。
请编写一个字符串归一化程序，统计字符串中相同字符出现的次数，并按字典序输出字符及其出现次数。
例如字符串"babcc"归一化后为"a1b2c2"



输入描述:
每个测试用例每行为一个字符串，以'\n'结尾，例如cccddecca
输出描述:
输出压缩后的字符串ac5d2e
示例1
输入
复制
dabcab
输出
复制
a2b2c1d1


统计字符串（只有小写字母）中相同字符出现的次数，并按字典序输出字符及其出现次数。例如字符串"babcc"归一化后为"a1b2c2"。
很简单，26大小的数组存储每个字母的次数，然后顺序输出字母和次数即可

```java

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int[] counter = new int[26];
        for(int i=0;i<s.length();i++){
            counter[s.charAt(i)-'a']++;
        }

        StringBuilder builder = new StringBuilder();
        for(int i=0;i<26;i++){
            if(counter[i]>0){
                char c = (char) (i+'a');
                builder.append(c);
                if(counter[i]>1)
                    builder.append(counter[i]);
            }
        }
        System.out.println(builder.toString());
    }
}

```


b22.字符串排序
题目描述


月神拿到一个新的数据集，其中每个样本都是一个字符串（长度小于100），样本的的后六位是纯数字，月神需要将所有样本的后六位数字提出来，转换成数字，并排序输出。
月神要实现这样一个很简单的功能确没有时间，作为好朋友的你，一定能解决月神的烦恼，对吧。
输入描述:
每个测试用例的第一行是一个正整数M（1<=M<=100)，表示数据集的样本数目

接下来输入M行，每行是数据集的一个样本，每个样本均是字符串，且后六位是数字字符。
输出描述:
对每个数据集，输出所有样本的后六位构成的数字排序后的结果（每行输出一个样本的结果）
示例1
输入
复制
4
abc123455
boyxx213456
cba312456
cdwxa654321
输出
复制
123455
213456
312456
654321

一个数据集，其中每个样本都是一个字符串（长度小于100），字符串后六位是纯数字，将所有样本的后六位数字提取出来，对其进行排序并逐行输出
提取每个样本的后六位很简单，得到字符串后能知道长度len，直接substring(len-6,len)即可
不要全部提取出来然后排序，最好是每提取一个就排序，可以用构建二叉排序树的方式构建一个二叉排序树，每提取一个数字，将其插入到树中，最后对树进行中序遍历即可，要注意的是，树节点要有一个cnt记录有多少个当前值，因为数字有可能重复，所以输出的时候按照出现的次数输出节点值

```java

import java.util.Scanner;
import java.util.Stack;

public class Main {
    static class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        int cnt;

        TreeNode(int val){
            this.val = val;
            cnt = 1;
        }
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        TreeNode root = null;
        int n = Integer.valueOf(in.nextLine());
        for(int i=0;i<n;i++){
            String s = in.nextLine();
            int len = s.length();
            String ans = s.substring(len-6,len);
            int val = Integer.valueOf(ans);
            root = insertNode(root, val);
        }
        //中序遍历二叉排序树，即顺序输出
        Stack<TreeNode> stk = new Stack<>();
        TreeNode p = root;
        while (p!=null || !stk.isEmpty()){
            if(p!=null){
                stk.push(p);
                p = p.left;
            }else{
                p = stk.pop();
                //如果有重复的值
                for(int i=0;i<p.cnt;i++)
                    System.out.println(p.val);
                p = p.right;
            }
        }
    }

    //将数插入二叉排序树中
    static TreeNode insertNode(TreeNode root, int val){
        if(root==null){
            TreeNode node = new TreeNode(val);
            return node;
        }
        int rootVal = root.val;
        if(rootVal==val){ //如果有重复的值
            root.cnt++;
        }else if(rootVal>val){
            root.left = insertNode(root.left, val);
        }else{
            root.right = insertNode(root.right, val);
        }
        return root;
    }
}

```


b23.回文字符串
题目描述
最大回文子串是被研究得比较多的一个经典问题。最近月神想到了一个变种，对于一个字符串，如果不要求子串连续，那么一个字符串的最大回文子串的最大长度是多少呢。
输入描述:
每个测试用例输入一行字符串（由数字0-9，字母a-z、A-Z构成），字条串长度大于0且不大于1000.
输出描述:
输出该字符串的最长回文子串的长度。（不要求输出最长回文串，并且子串不要求连续）
示例1
输入
复制
adbca
输出
复制
3
说明
因为在本题中，不要求回文子串连续，故最长回文子串为aba(或ada、aca)
备注:
因为不要求子串连续，所以字符串abc的子串有a、b、c、ab、ac、bc、abc7个


dp[i][j]表示 s[i...j]中最长的回文子串长度

dp[i][i] = 1
若 s[i]==s[j], dp[i][j] = dp[i+1][j-1] + 2  (s[j]和s[i]也加入这个最长回文)
若 s[i]!=s[j], dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1])  (s[j]加入回文，或者s[i]加入回文)

这里的问题是不能对i和j都从头到尾遍历，因为dp[i][j]会依赖于dp[i+1][x]，而i是小等于j的，所以外层循环是j从0到n-1，然后内层i的循环从j-1到0

连续的回文的问题：可以用中心扩散的方法

```java

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int len = s.length();
        int[][] dp = new int[len][len];

        for(int r = 0;r<len;r++){
            dp[r][r] = 1;
            for(int l=r-1;l>=0;l--){
                if(s.charAt(l)==s.charAt(r)){
                    dp[l][r] = dp[l+1][r-1]+2;
                }else{
                    dp[l][r] = Math.max(dp[l][r-1], dp[l+1][r]);
                }
            }
        }
        System.out.println(dp[0][len-1]);

    }
}
```


b24.latex爱好者

题目描述
latex自然是广大研究人员最喜欢使用的科研论文排版工具之一。
月神想在iPhone 上查阅写好的paper，但是无奈iPhone 上没有月神喜欢使用的阅读软件，于是月神也希望像tex老爷爷Donald Knuth那样自己动手do it yourself一个。
在DIY这个阅读软件的过程中，月神碰到一个问题，已知iPhone屏幕的高为H，宽为W，若字体大小为S(假设为方形），则一行可放W / S(取整数部分）个文字，一屏最多可放H / S （取整数部分）行文字。
已知一篇paper有N个段落，每个段落的文字数目由a1, a2, a3,...., an表示，月神希望排版的页数不多于P页（一屏显示一页），那么月神最多可使用多大的字体呢？

1 <= W, H, ai <= 1000
1 <= P <= 1000000
输入描述:
每个测试用例的输入包含两行。

第一行输入N,P,H,W

第二行输入N个数a1,a2,a3,...,an表示每个段落的文字个数。
输出描述:
对于每个测试用例，输出最大允许的字符大小S
示例1
输入
复制
1 10 4 3 10 2 10 4 3 10 10
输出
复制
3 2


已知iPhone屏幕的高为H，宽为W，若字体大小为S(假设为方形），则一行可放W / S(取整数部分）个文字，一屏最多可放H / S （取整数部分）行文字。
已知一篇paper有N个段落，每个段落的文字数目由a1, a2, a3,...., an表示，月神希望排版的页数不多于P页（一屏显示一页），那么最大可使用多大的字体呢？

注意这里的段落，因为有段落的存在，所以不能直接用所有的字符数的和，因为段落可能会造成结尾句的空白字符，这也是要算进去的。

如果不考虑分段的话：
设一页能显示的文字是 (w/s) * (h/s) = m 个  ， 最终的页数为x，则  x <= p ,   m * x >= (Σai)
得   (Σai)/m <=x<= P  因此求能满足P大等于(Σai)/m 的最大的s，移项得：
((Σai)/p) <= (w/s) * (h/s)，因此就是求最大的s使得不等式成立
这样能得出s的大概范围： s = (int) Math.sqrt(w*h*p/sum), 

但是由于分段的存在，真实字体可能比该值大，也可能比该值小，因此就要以该值为中心计算真正能满足条件的s

假设当前字号s已确定，如何计算该文章需要的总行数？
totalLines = Σ(Math.ceil(a[i]/(w/s))) 为该片文章总共所需的行数
而总页数最大为p，每一页至多能有h/s行，所以行数的上限值为 maxLines = p * （h/s）
如果字号s有效，则必须满足 totalLines <= maxLines

如果s满足，则再验证s+1是否满足，若满足再验证s+2是否满足。。。
如果s不满足，则验证s-1是否满足，不满足再验证s-2是否满足。。。

这样，不满足时上一个满足的值就是答案。

例如，从27开始试，27不满足，试26，26满足，设置一个boolean类型，再试27，27不满足，但上一个（26）满足，则答案是26

```java

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n,p,h,w;
        n = in.nextInt();
        p = in.nextInt();
        h = in.nextInt();
        w = in.nextInt();


        int[] a = new int[n];
        int sum = 0;
        for(int i=0;i<n;i++){
            a[i] = in.nextInt();
            sum += a[i];
        }

        
        //这个数是不看分段的结果，不看分段的话，就变成只有一大段，，
        // 字体可能会变大，因为相当于文字总数变少了（段尾空格没了），页数限制相同的情况下，每页可能容纳更大更少的字而不超过页数，因此这个s应该比实际值更大
        //但是计算除法和开方的过程可能会使得s变得过小，所以需要以此时的s为中心值，去它的左右探索能满足条件的最大值
        int s = (int) Math.sqrt(w*h*p/sum);

        boolean preValid = false;
        for(;;){
            boolean isSValid = isValid(p,h,w,sum,s, a);
            if(isSValid){ //如果s可行，则继续试s+1是否行，并标注s可行
                s++;
                preValid = true;
            }else { //如果s不行，则如果上一个行,返回上一个，如果不行，继续看s-1是否行
                s--;
                if(preValid) {
                    break;
                }
            }
        }
        System.out.println(s);



    }

    static boolean isValid(int p, int h, int w, int sum, int s, int[] a){
        int maxLine = p* ((int)h/s);
        int totalLine = 0;

        for(int i=0;i<a.length;i++){
            int tmp = w/s;
            //先转成double，防止有的直接除成0
            double t =(double) a[i]/tmp;
            totalLine += Math.ceil(t);
        }
        return maxLine>=totalLine;
    }
}
```

b25.游戏海报
题目描述
小明有26种游戏海报，用小写字母"a"到"z"表示。小明会把游戏海报装订成册（可能有重复的海报），册子可以用一个字符串来表示，每个字符就表示对应的海报，例如abcdea。小明现在想做一些“特别版”，然后卖掉。特别版就是会从所有海报（26种）中随机选一张，加入到册子的任意一个位置。
那现在小明手里已经有一种海报册子，再插入一张新的海报后，他一共可以组成多少不同的海报册子呢？
输入描述:
海报册子的字符串表示，1 <= 字符串长度<= 20
输出描述:
一个整数，表示可以组成的不同的海报册子种类数
示例1
输入
复制
a
输出
复制
51
说明
我们可以组成 'ab','ac',...,'az','ba','ca',...,'za' 还有 'aa', 一共 51 种不同的海报册子。

有一个只包含26个小写字母的字符串，再从26个字母中随机拿出一个，插入字符串的任意一个位置，组成新的字符串，问新的字符串一共有多少种可能性
用set保存所有可能性，然后把26个字母依次添加到字符串中的所有可用位置即可。要注意，StringBuilder.insert无法插入到末尾，插到末尾用append

```java

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        StringBuilder builder = new StringBuilder(s);
        HashSet<String> ansSet = new HashSet<>();
        for(int i=0;i<26;i++){
            char c = (char) (i+'a');
            //插入每一个位置，但这里不包括插入到末尾
            for(int j=0;j<s.length();j++){
                builder.insert(j, c);
                ansSet.add(builder.toString());
                builder.deleteCharAt(j);
            }
            //插入到末尾
            builder.append(c);
            ansSet.add(builder.toString());
            builder.delete(builder.length()-1, builder.length());
        }

        System.out.println(ansSet.size());
    }

}
```

b26.合并数组

题目描述
请实现一个函数，功能为合并两个升序数组为一个升序数组
点击页面左下角“例2”，了解如何实现输入输出
输入描述:
输入有多个测试用例，每个测试用例有1-2行，每行都是以英文逗号分隔从小到大排列的数字
输出描述:
输出一行以英文逗号分隔从小到大排列的数组
示例1
输入
复制
1,5,7,9
2,3,4,6,8,10
输出
复制
1,2,3,4,5,6,7,8,9,10

备注:
不允许使用原生的 sort、concat 等函数

合并两个或一个升序数组为一个升序数组：
很简单的双指针法合并数组。p1，p2分别在两个数组的头部，把当前p1，p2中指向的小的数字加入结果数组，然后指针后移,直到一个指针到头了，然后把另一数组全部加入结果数组即可
因为同一个数组的数字都在同一行，所以用nextLine，然后用split分割每个数字
难点在于如何判别输入的是两个还是一个数组？关键在于用hasNextLine判断是否还有下一行。如下：如果只有一行，则直接输出s1

        Scanner in = new Scanner(System.in);
        String s1 = in.nextLine();

        if(!in.hasNextLine()){
            System.out.println(s1);
            return;
        }
        String s2 = in.nextLine();

直接用上述代码在控制台中手动模拟不行，因为hasNextLine还是会有读下一行的操作（读到了再判断是不是空），可以用文件来测试，在牛客上可以通过

```java

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String s1 = in.nextLine();

        if(!in.hasNextLine()){
            System.out.println(s1);
            return;
        }
        String s2 = in.nextLine();
        String[] strs1 = s1.split(",");
        String[] strs2 = s2.split(",");

        int[] nums1 = new int[0];
        nums1 = new int[strs1.length];
        for (int i = 0; i < nums1.length && strs1[i].length() != 0; i++) {
            nums1[i] = Integer.parseInt(strs1[i]);
        }


        int[] nums2 = new int[0];
        nums2 = new int[strs2.length];
        for (int i = 0; i < nums2.length && strs2[i].length() != 0; i++) {
            nums2[i] = Integer.parseInt(strs2[i]);
        }

        int len1 = nums1.length;
        int len2 = nums2.length;


        int p1 = 0, p2 = 0;
        int i = 0;
        int[] ans = new int[nums1.length + nums2.length];
        while (p1 != len1 && p2 != len2) {
            if (nums1[p1] < nums2[p2]) {
                ans[i++] = nums1[p1++];
            } else {
                ans[i++] = nums2[p2++];
            }
        }

        if (p1 == len1) {
            while (p2 != len2) {
                ans[i++] = nums2[p2++];
            }
        } else {
            while (p1 != len1) {
                ans[i++] = nums1[p1++];
            }
        }

        for(int j=0;j<ans.length;j++){
            if(j==0) System.out.print(ans[j]);
            else System.out.print(","+ans[j]);
        }
    }
}
```

b27.字符串包含

题目描述
我们定义字符串包含关系：字符串A=abc，字符串B=ab，字符串C=ac，则说A包含B，A和C没有包含关系。
输入描述:
两个字符串，判断这个两个字符串是否具有包含关系，测试数据有多组，请用循环读入。
输出描述:
如果包含输出1，否则输出0.

示例1
输入

abc ab
输出
1

用字符串的contains即可判断，要注意的是是否有包含关系，包括A包含B，也包括B包括A，两者都要考虑。
多行数据，用Scanner的hasNextLine来判断是否有下一行数据

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
//        File f = new File("test.txt");
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()){
            String[] strs = in.nextLine().split(" ");
            boolean contains = strs[0].contains(strs[1]) || strs[1].contains(strs[0]);
            System.out.println(contains?1:0);
        }
    }
}
```


b28.最少数量货物装箱问题
题目描述
有重量分别为3，5，7公斤的三种货物，和一个载重量为X公斤的箱子（不考虑体积等其它因素，只计算重量）
需要向箱子内装满X公斤的货物，要求使用的货物个数尽可能少（三种货物数量无限）

输入描述:
输入箱子载重量X(1 <= X <= 10000)，一个整数。
输出描述:
如果无法装满，输出 -1。
如果可以装满，输出使用货物的总个数。
示例1
输入
复制
4
输出
复制
-1
说明
无法装满
示例2
输入
复制
8
输出
复制
2
说明
使用1个5公斤，1个3公斤货物

使用动态规划。设dp[i]能装满载重为i的箱子的最少货物个数

//下面为了书写简洁起见没有考虑i-3,i-5,i-7的范围，其实代码中是要考虑进去的
dp[i]=-1;
if(dp[i-3]!=-1 || dp[i-5]!=-1 || dp[i-7]!=-1){
    dp[i] = Integer.MAX_VALUE;
    if(dp[i-3]!=-1){
        dp[i] = Math.min(dp[i-3],dp[i])+1;
    }
    if(dp[i-5]!=-1){
        dp[i] = Math.min(dp[i-5],dp[i])+1;
    }
    if(dp[i-7]!=-1){
        dp[i] = Math.min(dp[i-7],dp[i])+1;
    }
}

```java

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        File f = new File("test.txt");
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        if (x < 3) {
            System.out.println(-1);
            return;
        }
        int[] dp = new int[x+1];
        Arrays.fill(dp, -1);
        dp[0]=0; //为了方便计算，让dp[0]为0

        for (int i = 3; i <= x; i++) {
            dp[i] = -1;
            if ( (i-3>=0 && dp[i - 3] != -1) || (i-5>=0 && dp[i - 5] != -1) || (i-7>=0 && dp[i - 7] != -1)) {
                dp[i] = Integer.MAX_VALUE;
                if (i-3>=0 && dp[i - 3] != -1) {
                    dp[i] = Math.min(dp[i - 3], dp[i]) + 1;
                }
                if (i-5>=0 && dp[i - 5] != -1) {
                    dp[i] = Math.min(dp[i - 5], dp[i]) + 1;
                }
                if (i-7>=0 && dp[i - 7] != -1) {
                    dp[i] = Math.min(dp[i - 7], dp[i]) + 1;
                }
            }
        }
        System.out.println(dp[x]);
    }
}
```

b29.回文子串
题目描述
给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
("回文串”是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。)
具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被计为是不同的子串。
可用C++,Java,C#实现相关代码逻辑
输入描述:
输入一个字符串S 例如“aabcb”(1 <= |S| <= 50), |S|表示字符串S的长度。
输出描述:
符合条件的字符串有"a","a","aa","b","c","b","bcb"

所以答案:7
示例1
输入
复制
aabcb
输出
复制
7

用中心扩散法求回文, 对于每一个字母，要兼顾以它为中心的奇数长度回文串和以它为左中心的偶数长度回文串

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int len = s.length();
        List<String> ans = new ArrayList<>();

        for(int i=0;i<len;i++){
            //奇数长度回文串
            getPlalindrome(s, i, i, ans);
            //偶数长度回文串
            getPlalindrome(s,i,i+1,ans);
        }
        System.out.println(ans.size());
    }

    static void getPlalindrome(String str, int left, int right, List<String> ans){
        while (left>=0 && right < str.length() && str.charAt(left)==str.charAt(right)){
            ans.add(str.substring(left, right+1));
            left--;
            right++;
        }
    }
}
```


b30 字符串压缩
题目描述
对字符串进行RLE压缩，将相邻的相同字符，用计数值和字符值来代替。例如：aaabccccccddeee，则可用3a1b6c2d3e来代替。

输入描述:
输入为a-z,A-Z的字符串，且字符串不为空，如aaabccccccddeee
输出描述:
压缩后的字符串，如3a1b6c2d3e
示例1
输入
复制
aaabccccccdd
输出
复制
3a1b6c2d

遍历字符串，如果当前字符等于pre，则计数+1，如果不等，则将当前计数和pre放入答案集中，然后更新计数为1，pre为当前字符

```java

import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String s = in.next();
        if(s.length()==0){
            System.out.println("");
        }
        StringBuilder ans = new StringBuilder();
        char pre = s.charAt(0);
        int cnt = 1;
        for(int i=1;i<s.length();i++){
            char c = s.charAt(i);
            if(c==pre){
                cnt++;
            }else{
                ans.append(cnt);
                ans.append(pre);
                cnt = 1;
                pre = c;
            }
        }

        //将最后的字符算入答案
        ans.append(cnt);
        ans.append(pre);
        System.out.println(ans.toString());
    }
}

```


b31.解析加减法运算
题目描述
解析加减法运算
如：
输入字符串："1+2+3" 输出："6"
输入字符串："1+2-3" 输出："0"
输入字符串："-1+2+3" 输出："4"
输入字符串："1" 输出："1"
输入字符串："-1" 输出："-1"

已知条件：输入的运算都是整数运算，且只有加减运算
要求：输出为String类型，不能使用内建的eval()函数

输入描述:
输入字符串："1+2+3"
输出描述:
输出："6"
示例1
输入
复制
1+2+3
输出
复制
6

关键之处在于要能把大于1位的数准确识别出来
设置基础值ans=0，判断第一个字符是不是符号，用preOp表示上一个符号，preOpPos表示上一个符号的位置
则，当遇到一个符号时(其位置为i)，前一个数字就是substring(preOpPos+1,i)，其符号就为preOp，把它加到ans上。
当遍历完时，再把最后一个数也加上即可

```java
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String s = in.next();
        char preOp;
        int preOpPos = 0;
        if(s.charAt(0)=='+' || s.charAt(0)=='-'){
            preOp = s.charAt(0);
        }else{
            preOp = '+';
            preOpPos = -1; //为了方便后面计算
        }

        int ans = 0;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(i==0 && (c=='+'|| c=='-')){
                continue;
            }
            if(c=='+'|| c=='-'){
                int n = Integer.parseInt(s.substring(preOpPos+1, i));
                ans += preOp=='-'?n*-1:n;
                preOp = c;
                preOpPos = i;
            }
        }
        //只剩最后一个数字
        int n = Integer.parseInt(s.substring(preOpPos+1, s.length()));
        ans += preOp=='-'?n*-1:n;
        System.out.println(ans);

    }

}
```


b32.求连续子数组的最大和
题目描述
一个非空整数数组，选择其中的两个位置，使得两个位置之间的数和最大。
如果最大的和为正数，则输出这个数；如果最大的和为负数或0，则输出0
输入描述:
3,-5,7,-2,8
输出描述:
13
示例1
输入
复制
-6,-9,-10
输出
复制
0

当遍历到一个数时，若前面加的和加上它自己，比它自己还小的话，则这个和重新从它开始，否则就把它加到前面那个和上继续
但要注意一点，每遍历一个数，就看看此时的和是不是最大的，保存整个遍历过程中最大的和作为答案

```java

import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String s = in.next();
        String[] strs = s.split(",");
        int len = strs.length;

        int maxSum = 0;
        int sum = 0;
        for(int i=0;i<len;i++){
            int tmp = Integer.parseInt(strs[i]);
            if(sum+tmp<tmp){
                sum = tmp;
            }else {
                sum += tmp;
            }
            maxSum = Math.max(maxSum, sum);
        }
        System.out.println(maxSum);

    }
}

```


b33.字符串长度最大乘积
题目描述
已知一个字符串数组words，要求寻找其中两个没有重复字符的字符串，使得这两个字符串的长度乘积最大，输出这个最大的乘积。如：
words=["abcd","wxyh","defgh"], 其中不包含重复字符的两个字符串是"abcd"和"wxyh"，则输出16
words=["a","aa","aaa","aaaa"], 找不到满足要求的两个字符串，则输出0
输入描述:
Input:

["a","ab","abc","cd","bcd","abcd"]
输出描述:
Output:

4
示例1
输入
复制
["a","ab","abc","cd","bcd","abcd"]
输出
复制
4
备注:
Input中，不包含相同字符的有三对：
"ab"和"cd"
"a"和"cd"
"a"和"bcd"
所以字符串长度乘积的最大值是4

检测直接用暴力破解即可，对比两个字符串中是否有重复字符，可以先用hashset把str1中的每个字母保存，这里有个窍门，当set中的元素个数为26时，就说明所有字母都包含了，直接返回true即可

剩下要注意的点在于ArrayList转数组
不能用如下方法强转
String[] strs = (String[]) list.toArray();  //会报错，类型转换错误
必须用如下方法：
String[] strs = list.toArray(new String[list.size()]);

还有一点要注意，split方法可能会返回一个长度为1的字符串数组，这个唯一元素长度为0，即""。如果对该元素处理往往出错，要注意规避

```java

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        //输入格式：["a","ab","abc","cd","bcd","abcd"]
        String line = in.nextLine();
        if(line==null || line.length()==0) {
            System.out.println(0);
            return;
        }
        String[] ss = line.substring(1, line.length()-1).split(",");
        //下面代码防的是若只输入 []，这样ss中会有一个元素，但这个元素长度为0，若用它往下进行会越界
        if(ss.length==0 || (ss.length==1&&ss[0].length()==0)){
            System.out.println(0);
            return;
        }
        ArrayList<String> list = new ArrayList<>();
        for(String s : ss){
            list.add(s.substring(1,s.length()-1));
        }

        String[] strs = list.toArray(new String[list.size()]);
        int len = strs.length;
        int ans = 0;
        for(int i=0;i<len;i++){
            for (int j=i+1;j<len;j++){
                if(!isOverlap(strs[i], strs[j])){
                    ans = Math.max(ans, strs[i].length()*strs[j].length());
                }
            }
        }
        System.out.println(ans);
    }

    static boolean isOverlap(String sa, String sb){
        HashSet<Integer> set = new HashSet<>();
        int flag = 0;
        for(int i=0;i<sa.length();i++){
            //如果所有的字母都加进去了，这个比较就不用进行了
            if(set.size()==26) return true;
            set.add(sa.charAt(i)-'a');
        }
        for(int i=0;i<sb.length();i++){
            if(set.contains(sb.charAt(i)-'a')) return true;
        }
        return false;
    }

}



```


b34.今年的第几天
题目描述

输入年、月、日，计算该天是本年的第几天。 

输入： 

包括三个整数年(1<=Y<=3000)、月(1<=M<=12)、日(1<=D<=31)。 

输出： 

输入可能有多组测试数据，对于每一组测试数据， 

输出一个整数，代表Input中的年、月、日对应本年的第几天。


输入描述:
输入：1990 9 20
输出描述:
输入：263
示例1
输入
复制
2000 5 1 
输出
复制
122
备注:
注意闰年的判定方式

闰年：
1.是4的倍数且不是100的倍数
2.是400的倍数
剩下的只需要把该月之前的月份天数全加上，再加上当前是本月的多少号即可

```java
import java.util.Scanner;

public class Main{
    static int[] monthsDay = {0,31,28,31,30,31,30,31,31,30,31,30,31};
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int year = in.nextInt();
        int month = in.nextInt();
        int day = in.nextInt();

        //闰年
        boolean leap = false;

        if(year%400==0) leap = true;
        else if(year%4==0 && year%100!=0) leap = true;

        int ans = 0;
        for(int i=1;i<month;i++){
            if(i==2)
                ans += leap?monthsDay[i]+1:monthsDay[i];
            else
                ans += monthsDay[i];
        }
        ans += day;
        System.out.println(ans);

    }
}
```

字节2019笔试题2：
3个同样的字母放在一起，去掉一个；AABB型的，去掉一个B
第一次遍历，发现连续有3个一样的字符，把第一个替换为#（发现#则继续往后看，#视为没有即可）
第一次遍历，发现连续有AABB，把第一个B替换为# （发现#则继续往后看，#视为没有即可）
再次遍历，遇到不为#的就把它加到答案字符串中
```java

    public String handleStr(String s){
        if (s==null) return null;
        StringBuilder builder = new StringBuilder(s);
        int len = builder.length();
        for(int i=0;i<len;i++){
            while (i<len && builder.charAt(i)=='#'){
                i++;
            }
            int j = i+1;
            while (j<len && builder.charAt(j)=='#'){
                j++;
            }
            int k = j+1;
            while (k<len && builder.charAt(k)=='#'){
                k++;
            }
            if(i<len&&j<len&&k<len){
                if(builder.charAt(i)==builder.charAt(j) && builder.charAt(i)==builder.charAt(k)){
                    builder.replace(i,i+1,"#");
                }
            }else {
                break;
            }
        }

        for(int i=0;i<len;i++){
            while (i<len && builder.charAt(i)=='#'){
                i++;
            }
            int j = i+1;
            while (j<len && builder.charAt(j)=='#'){
                j++;
            }
            int k = j+1;
            while (k<len && builder.charAt(k)=='#'){
                k++;
            }
            int l = k+1;
            while (l<len && builder.charAt(l)=='#'){
                l++;
            }

            if(i<len&&j<len&&k<len && l<len){
                if(builder.charAt(i)==builder.charAt(j) && builder.charAt(k)==builder.charAt(l)){
                    builder.replace(k,k+1,"#");
                }
            }else {
                break;
            }
        }
        StringBuilder ans = new StringBuilder();
        for(int i=0;i<len;i++){
            if(builder.charAt(i)!='#'){
                ans.append(builder.charAt(i));
            }
        }
        return ans.toString();

    }
```

1.田忌赛马问题：bb1
  两个小组A、B，每个小组有n个同学。已知每位同学的速度。两个小组进行赛跑获取积分，每次派出一名同学，胜者+1，败者-1，平局+0。问A组最多积多少分。输入n代表n名同学，在输入n个数代表A组每人速度，又n个数代表B组每人速度。


输入样例：
3
92 83 71
95 87 74
2
20 20
20 20
2
20 19
22 18
0
输出样例：
1
0
0


step1：先让A的最大的和B的最大的比，如果A更大，则两个最大的同时出队，再比剩下的最大的。
如果B更大，则让A最小的和B最大的比，A最小的和B最大的出队，然后回到step1继续，直到两个队列为空

```java

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        LinkedList<Integer> q1 = new LinkedList<>();

        for(int i=0;i<n;i++){
            int t = in.nextInt();
            q1.offerFirst(t);
        }
        Collections.sort(q1);

        LinkedList<Integer> q2 = new LinkedList<>();
        for(int i=0;i<n;i++){
            int t = in.nextInt();
            q2.offerFirst(t);
        }
        Collections.sort(q2);

        int ans = 0;
        for(int i=0;i<n;i++){
            if(q1.peekLast()>q2.peekLast()){
                q1.pollLast();
                q2.pollLast();
                ans++;
            }else {
                //只要A最快的不大于B最快的，最好就要用A最慢的把B最快的消耗掉
                int t1 = q1.pollFirst();
                int t2 = q2.pollLast();
                if(t1>t2)
                    ans++;
                else if(t1<t2)
                    ans--;
            }
        }
        System.out.println(ans);
    }
}
```

2.取扑克牌问题
  n张卡牌堆成一堆，每张卡牌上都有一个整数代表该牌得分。两人A,B交替从牌堆顶拿牌。第一次可以拿1-2张牌，后面每次拿牌，最多拿上个人拿牌数的两倍，最少拿1张，直到牌全部拿完。假设两个人都会采取最优策略让自己得分最大化，求先手拿牌人的得分。



3.坐圆桌问题
  n个人围着圆桌吃饭，要求每相邻两个人的身高差距不能超过m，有多少种安排方法。输入n和m,n=1,...,10,m=1,...,1000000。后面n行输入代表每个人的身高。


1. 字节跳动大闯关：bb2
题目描述：

Bytedance Efficiency Engineering团队在8月20日搬入了学清嘉创大厦。为庆祝团队的乔迁之喜，字节君决定邀请整个EE团队，举办一个大型团建游戏-字节跳动大闯关。可是遇到了一个问题：

EE团队共有n个人，大家都比较害羞，不善于与陌生人交流。这n个人每个人都向字节君提供了自己认识人的名字，不包括自己。如果A的名单里有B，或B的名单里有A，则代表A与B相互认识。同时如果A认识B，B认识C，则代表A与C也会很快认识，毕竟通过B的介绍，两个人就可以很快相互认识的了。

为了大闯关游戏可以更好地团队协作、气氛更活跃，并使得团队中的人可以尽快的相互了解、认识和交流，字节君决定根据这个名单将团队分为m组，每组人数可以不同，但组内的任何一个人都与组内的其他所有人直接或间接的认识和交流。如何确定一个方案，使得团队可以分成m组，并且这个m尽可能地小呢？

BFS构建集合即可,但要注意的是，由于可能A认得B，B不认得A，会导致B走不到A处，会出错，
在构建的时候就如果A认得B或者B认得A，让map[A][B]和map[B][A]都是true即可

```java

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[][] map = new int[n][n];
        for(int i=0;i<n;i++){
            int tmp = 0;
            while ((tmp=in.nextInt())!=0){
                map[i][tmp-1] = 1;
                map[tmp-1][i] = 1;
            }
        }
        boolean[] flag = new boolean[n];
        int m = 0;
        for(int i=0;i<n;i++){
            if(!flag[i]){
                flag[i] = true;
                bfs(i, n,map, flag);
                m++;
            }
        }
        System.out.println(m);
    }
    static void bfs(int start, int n, int[][] map, boolean[] flag){
        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()){
            int e = queue.poll();
            for(int i=0;i<n;i++){
                if(map[e][i]!=0 && !flag[i]){
                    queue.offer(i);
                    flag[i] = true;
                }
            }
        }
    }
}

```

2.合法的表达式

合法标识符：0-9组成的字符串，可以包含多个前导0

合法表达式：
1、若X为合法标识符，则X是合法的表达式
2、若X为合法的表达式，则(X)为合法的表达式
3、若X和Y均为合法的表达式，则X+Y，X-Y均为合法的表达式

如：1，100，1+2，(10), 1-(3-2)
不合法的表达式： (, 1+-2

给定一个整数n，得到结果数，结果对1000000007取模

这个题不用一个一个试
设f[i]为i个字符组成的合法表达式个数

f[1]=10
f[2]=100
f[3]= 1000+f[1]+ 1 * 2* f[1] * f[1] 解释： 1000为3位全是数字，f[1]为最两边是括号，中间是合法表达式的情况，
（3-2）* 2 * f[1] * f[1]：除了最边上两个位置不能是±，其他位置都能是±，
每个位置上有加减两种情况，共有x-2个位置，然后符号两边的合法表达式个数要乘积

f[4] = 10000 + f[2] + 2 * f[1] * f[2] + 2 * f[2] * f[1]...
f[5] = 100000 + f[3] + 2* f[1] * f[3] +  但这个是可能有重复的。

```java

```


3.双生词：bb3
双生词是满足两个条件的字符串：s和s'
1.s与s'长度相同
2.将s首尾相接绕成环，再选一个位置切开，顺时针或逆时针能得到s'

给出测试组数t，表示共有多少组数据
对于每组数据，第一行表示共有多少个字符串，接下来n行，每行一个字符串

对于每组数据，如果存在双生词，输出Yeah，否则输出Sad

每个字符串长度在1-32之间， n<100000

每个字符串能得到的双生词有 2len 个，len是字符串长度
对于一个组
把第一个词能构成的双生词全部构造出，放在以这个词为key的map中，然后遍历剩下的词，只要有一个能满足，剩下的就不用再构造了，返回Yeah
如果第一个试了不行，再把第二个构造出，以此类推


```java

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while (t-- > 0) {
            int n = in.nextInt();
            String[] strs = new String[n];
            for (int i = 0; i < n; i++) {
                strs[i] = in.next();
            }
            if(check(n, strs)){
                System.out.println("Yeah");
            }else
                System.out.println("Sad");
        }


    }

    static boolean check(int n, String[] strs) {
        HashMap<String, HashSet<String>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String s = strs[i];
            buildDoubleString(s, map);
            HashSet<String> set = map.get(s);
            for(int j=i+1;j<n;j++){
                if(set.contains(strs[j])) return true;
            }
        }
        return false;
    }

    static void buildDoubleString(String s, HashMap<String, HashSet<String>> map){
        HashSet<String> set = new HashSet<>();
        map.put(s, set);
        StringBuilder builder = new StringBuilder(s);
        int len = s.length();
        for(int i=0;i<len;i++){
            char c = builder.charAt(0);
            builder.delete(0, 1);
            builder.append(c);
            set.add(builder.toString());
            set.add(builder.reverse().toString());
            //翻转回来
            builder.reverse();
        }

    }
```

4.空气质量，核心是找不连续最长不减子序列
先在一个周期内找到最长的不减序列，然后看其中出现过最多的一个数是多少，



头条笔试2：bb4
给一棵树对应的完全二叉树为参照，空白节点处用#字符表示，使用层序遍历表示二叉树，节点之间采用空格分割。
如 
4 2 7 # 3 6 9
输出
4 7 2 9 6 3 #

使用重构二叉树(节点值用#存，可以处理中间的空节点的情况)输出镜像的形式，只能过80%，感觉应该是空间复杂度的问题，其实只要逆序输出每一行即可，并且不用额外的空间复杂度（只用一个数组的）：
```java
import java.util.LinkedList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line;
        if (in.hasNextLine()){
            line = in.nextLine();
        }else {
            return;
        }

        String[] tokens = line.split(" ");
        if(tokens.length<=0 || tokens[0].length()<=0){
            return;
        }
        int cur = 1;
        int cnt = 0;
        for(int i=0;i<tokens.length;){
            for(int j=(int) Math.min(tokens.length-1,i+(1<<(cur-1))-1);j>=i;j--){
                if(cnt==tokens.length-1){
                    System.out.print(tokens[j]);
                    break;
                }
                System.out.print(tokens[j] + " ");
                cnt++;
            }
            cur++;
            i = (1<<(cur-1))-1;
        }
    }
}
```

题3：b35
36进制由0-9，a-z，共36个字符表示，最小为'0'
'0'~'9'对应十进制的0~9，'a'~'z'对应十进制的10~35
例如：'1b' 换算成10进制等于 1 * 36^1 + 11 * 36^0 = 36 + 11 = 47
要求按照加法规则计算出任意两个36进制正整数的和
如：按照加法规则，计算'1b' + '2x' = '48'
要求：不允许把36进制数字整体转为10进制数字，计算出10进制数字的相加结果再转回为36进制

说的是不允许整体转，那就一位一位转着加

//注意下面代码没有判空，真遇到了判一下空检查一下合法性之类的

```java

    public String add(String a, String b) {
        HashMap<Character, Integer> map = new HashMap<>();
        for(int i=0;i<=9;i++){
            map.put((char) (i+'0'), i);
        }

        for(int i=0;i<36;i++){
            map.put((char) (i+'a'), i+10);
        }

        int c = 0;
        StringBuilder ans = new StringBuilder();
        StringBuilder aa = new StringBuilder(a).reverse();
        StringBuilder bb = new StringBuilder(b).reverse();

        for(int i=0;i<aa.length() || i<bb.length();i++){
            int ia = (i<aa.length())?map.get(aa.charAt(i)):0;
            int ib = (i<bb.length())?map.get(bb.charAt(i)):0;
            int cc = (ia+ib+c)/36;
            int cur = (ia+ib+c)%36;
            ans.append(getChar(cur));
            c = cc;
        }
        if(c>0)
            ans.append('1');
        return ans.reverse().toString();
    }

    private Character getChar(int i){
        if(i<=9) return (char)('0'+i);
        else{
            return (char)((i-10)+'a');
        }
    }
```

4. 剪绳子：b36

题目描述
有N根绳子，第i根绳子长度为Li，现在需要M根等长的绳子，你可以对n根绳子进行任意裁剪（不能拼接），请你帮忙计算出这m根绳子最长的长度是多少。

输入描述：
第一行包含2个正整数N、M，表示N根原始的绳子，和最终需要M根绳子数
第二行包含N个整数，第i个整数Li表示第i根绳子的长度
其中
1 <= N、M <= 100000,
0 < Li < 10 0000 0000
输出描述
对每一个测试用例，输出一个数字，表示裁剪后最长的长度，保留两位小数。

  这道题其实就是问裁剪出 M 段等长绳子，最大裁剪长度是多少。像这样的 "找到一个数，它要满足一定条件" 的题目，第一反应就是 贪心+二分，比如 “公路建设加油站，使最小距离最大化” 这道题以及现在这道题。这道题就是找到一个长度值 X，使得 X 满足能够从已有的 N 根绳子中裁剪出来 M 段。

二分查找：low：0，high：绳子总长度
然后迭代mid，如果mid能满足条件，则可能有更大的值满足条件，low=mid
如果mid不满足条件，则大于mid的一定都不满足，往小处找，high=mid
# 因为low和high都是double，所以不能让mid±1来赋值

(没看出来贪心体现在哪。。)

```java

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

//1 2 3 4 5 6 7 8 # 10 11 # 13 14 15
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Double> list = new ArrayList<>();

        int n = in.nextInt();
        int m = in.nextInt();
        double totalLen = 0;
        for(int i=0;i<n;i++){
            double d = in.nextDouble();
            list.add(d);
            totalLen += d;
        }

        double low = 0;
        double high = totalLen;
        while (high-low>0.0000001){
            double mid = (low+high)/2;
            if(check(list, mid, m)){
                low = mid;
            }else{
                high = mid;
            }
        }
        DecimalFormat format = new DecimalFormat("#.00");
        String s = format.format(low);
        //直接用%.2f 可能会导致进位,用DecimalFormat来进行截位，但用这个好像还是没法做到准确截位
        //最保险的还是转成str，获取小数点位置后直接用substring
        System.out.print(s);
    }

    //如果能剪成m段以上target长的绳子，说明有可能答案比target更长，否则答案一定比target更短
    static boolean check(ArrayList<Double> list, double target, double m){
        int cnt=0;
        for(double d: list){
            cnt += d/target;
        }
        return cnt>=m;
    }
}

```

建加油站：b37
有一条高速公路，想要建设 m 个加油站，一共有 n 个可以选择建设加油站的地点（n >= m），在这 n 个可选位置中选择 m 个建加油站有很多种方式，每一种方式中，两个相邻加油站之间的距离都有一个最小值，每种情况的最小距离可能不一样，问所有情况中，这个最小距离的最大值是多少？
2 <= n, m <= 100000
1 <= a[i] < a[i + 1] <= 10^9 (a[i] 是加油站位置)

原题不是这样的，但是很多人并没有理解题什么意思（什么是最小距离最大值），所以我就翻译了一下，变成上边这道题。
  输入第一行是 n 和 m，接下来下 n 个数是加油站的位置（保证是递增的，也就是 a[i + 1] > a[i]）。比如下边例子：

Input 1:        Input 1:
5 3          5 4
1 4 5 6 9        1 4 5 6 9

Output 1:       Output 2:
4 (1 5 9)       2 (1 4 6 9)

  网上很多答案说用 DP，dp[i][j] 表示前 i 个位置中选择 j 个最小距离最大值是多少，但是这道题的 n 和 m 最大都是十万，数组开不了这么大的，vs中开这么大数组直接编译就错了。所以不是 DP，而是二分+贪心。

把每个相邻距离都存入数组arr中，如对于a[i]= 1 4 5 6 9 来说，存入的是3 1 1 3
取low=0，high=Σarr[i] 那么要做的就是每次看一个 mid 能不能满足在若干次合并内有m-1个数都 >= mid（为什么可以这样，是因为最左边和最右边的加油站一定会选，因为选其他任何两个边界，其中的最小距离都只能小于选两个顶头，每合并出一个数大于等于mid，说明可以放一个加油站和上一个加油站之间距离大等于mid）。
共有n个可选地，所以有n-1个间隙(距离)，因为最左和最右一定会选，所以只需要把剩下的距离合并成每个距离大等于mid，且距离的个数大等于m-1个即可(如果等了，说明距离还能再大，但首先是说明了能保证距离mid满足)


```java
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();
        long sum = 0;
        ArrayList<Long> list = new ArrayList<>();
        long pre = in.nextLong();
        for(int i=1;i<n;i++){
            long cur = in.nextLong();
            list.add(cur-pre);
            sum += cur-pre;
            pre = cur;
        }

        long low = 0;
        long high = sum;
        long ans = 0;
        while (high>low){
            long mid = (low+high)>>1;
            if(check(list, mid, m)){
                ans = mid;
                low = mid+1;
            }else{
                high = mid-1;
            }
        }
        //这里不能直接用low，因为mid满足时，mid+1如果不满足，且low和high就差1，此时会跳出循环，low是mid+1，但它是不满足的。
        System.out.println(ans);
    }

    
    static boolean check(ArrayList<Long> list, long mid, long m){
        int sum = 0;
        int cnt = 0;
        for(int i=0;i<list.size();i++){
            sum+=list.get(i);
            if(sum>=mid){
                //当前段已经大于mid，则着手看下一段
                sum=0;
                cnt++;
                //如果满足条件的段已经超过或等于m-1个，则说明此数字可成
                if(cnt>=m-1) return true;
            }
        }
        return false;

    }
}
```

b38：
一串数字，用五个星号分隔成六段，每段的数值大小不可以大于600，打印出所有可能的结果
使用trackback，这一题再次提个醒，不是所有的trackback都要用到循环的，要视具体情况而定。

直接使用递归，每次要判断当前数字是不是满足条件(包括前导0的判断和数字值的判断)，如果满足再把当前数字加入list往下传，不满足就不传。递归结束后要remove本次的数字，方便下次数字添加

```java
    public void func(String s){
        if(s==null || s.length()<6) return;
        trackback(s, 0, new ArrayList<>());
    }

    private void print(ArrayList<Integer> list){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<list.size();i++){
            builder.append(list.get(i)).append('*');
        }
        builder.deleteCharAt(builder.length()-1);
        System.out.println(builder.toString());
    }


    public void trackback(String str, int start, ArrayList<Integer> list) {
        int len = str.length();
        if(start<len && list.size()>=6){
            return;
        }

        if(start==len && list.size()==6){
            print(list);
            return;
        }

        int i = start;
        if (i<len && isValid(str.substring(i, i + 1))) {
            list.add(Integer.valueOf(str.substring(i, i + 1)));
            trackback(str, i + 1, list);
            list.remove(list.size() - 1);
        }
        if (i+1<len && isValid(str.substring(i, i + 2))) {
            list.add(Integer.valueOf(str.substring(i, i + 2)));
            trackback(str, i + 2, list);
            list.remove(list.size() - 1);
        }
        if (i+2<len && isValid(str.substring(i, i + 3))) {
            list.add(Integer.valueOf(str.substring(i, i + 3)));
            trackback(str, i + 3, list);
            list.remove(list.size() - 1);
        }
    }

    public boolean isValid(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            //一旦出现前导0，直接返回
            if (res == 0 && s.charAt(i) == '0') return false;
            res = res * 10 + s.charAt(i) - '0';
        }
        return res <= 600;
    }
```

62进制数的加法：b39

把0-9，a-z，A-Z 字符串组成62进制数，实现两个62进制数的加法

```java
    public static String add(String a1, String a2){
        if(a1==null || a2==null) return "";
        int len1 = a1.length();
        int len2 = a2.length();

        StringBuilder builder1 = new StringBuilder(a1).reverse();
        StringBuilder builder2 = new StringBuilder(a2).reverse();
        a1 = builder1.toString();
        a2 = builder2.toString();

        int c = 0;
        StringBuilder builder = new StringBuilder();
        int i=0;
        for(;i<len1 && i<len2; i++){
            char c1 = a1.charAt(i);
            char c2 = a2.charAt(i);
            int i1=getNumber(c1);
            int i2=getNumber(c2);
            int res = i1+i2+c;
            if(res>=62){
                c=1;
            }else
                c=0;
            char cc = int2Char(res%62);
            builder.insert(0,cc);
        }
        if(i<len1){
            for(;i<len1;i++){
                char c1 = a1.charAt(i);
                int i1 = getNumber(c1);
                int res = i1+c;
                if(res>=62){
                    c = 1;
                }else
                    c=0;
                char cc = int2Char(res%62);
                builder.insert(0,cc);
            }
        }else if(i<len2){
            for(;i<len2;i++){
                char c2 = a2.charAt(i);
                int i2 = getNumber(c2);
                int res = i2+c;
                if(res>=62){
                    c = 1;
                }else
                    c=0;
                char cc = int2Char(res%62);
                builder.insert(0,cc);
            }
        }
        if(c==1)
            builder.insert(0, '1');

        return builder.toString();

    }

    public static int getNumber(char c){
        if(c>='0' && c<='9'){
            return c-'0';
        }else if(c>='a' && c<='z'){
            return c-'a'+10;
        }else {
            return c-'A'+ 36;
        }
    }

    public static char int2Char(int i){
        if(i>=0 && i<=9){
            return (char)(i+'0');
        }else if(i>=10 && i<=35){
            return (char)((i-10)+'a');
        }else{
            return (char)((i-36)+'A');
        }
    }
```

将整数字符串转成整数值：c-p248
给定一个字符串str，如果str符合日常书写的整数形式，并且属于32位整数的范围，返回str所代表的整数值，否则返回0

"123"->123, "023"->0  A13>0  0>0  2147483647>2147483647
2147483648>0 因为溢出了，-123>-123

首先看有没有负号，也不能有正号，正号不符合书写习惯
然后从第一个元素开始，如果长度大于10，直接返回0，因为最大的就是10位
如果第一个元素是0，返回0
如果一遇到不是数字的，直接返回
把遇到的字符都加到StringBuilder中，最后转成long，然后和int的上下限比较，比较通过则返回，
否则返回0

```java
    public int getInt(String s){
        if(s==null || s.length()==0) return 0;
        boolean isNa = false;
        char[] ss = s.toCharArray();
        if(ss[0]=='-'){
            isNa = true;
        }
        int i=isNa?1:0;
        if(ss[i]=='0') return 0;

        StringBuilder builder = new StringBuilder();
        for(;i<ss.length;i++){
            if(ss[i]<'0' || ss[i]>'9') return 0;
            builder.append(ss[i]);
        }
        if(builder.length()>10) return 0;

        long l = Long.valueOf(builder.toString());
        l = isNa?-l:l;
        if(l < Integer.MIN_VALUE) return 0;
        else if(l > Integer.MAX_VALUE) return 0;

        return (int)l;

    }
```


























~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ cxy 程序员代码面试指南 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

如何仅用递归函数和栈操作逆序一个栈：c-p8

需要做两个递归函数，第一个函数递归获得当前栈的栈底元素（栈底元素出栈，上面的都不动）
第二个逆序一个栈，只需要做3件事：
1. 调用第一个函数，得到当前栈的栈底元素
2. 递归该函数
3. 将刚才的元素压入栈

```java
//获得栈底元素,并且上面的不动
public int getLastElement(Stack<Integer> stack){
    int top = stack.pop();
    if(stack.isEmpty()){
        return top;
    }else{
        int last = getLastElement(stack);
        stack.push(top);
        return last;
    }
}
//逆序一个栈：
/*
1. 获得栈底元素，上面的不变
2. 递归该函数
3. 将刚才的元素压入栈
*/
public void reverse(Stack<Integer> stack){
    //首先要判断栈不为空才行
    if(stack.isEmpty()) return;
    int ele = getLastElement(stack);
    reverse(stack);
    stack.push(ele);
}
```

猫狗队列：
要能想到用额外的数据结构包装现有的类，在不破坏现有类的基础上增加功能


用一个栈实现另一个栈的排序：c-p13

一个栈中的元素为整型，现想将栈从顶到底按从大到小的顺序排列，只可以额外申请一个栈，只允许申请额外的变量，不允许申请额外的数据结构

将要排序的栈记为stack，辅助栈记为help，在stack上执行pop操作，弹出的元素记为cur
    如果cur小于或等于help中的栈顶元素，，或者help为空，直接将cur压入help
    如果cur大于help栈顶元素，则将help的元素逐一弹出，逐一压入stack，直到cur小于或等于help栈顶元素，或者help为空，再将cur压入help中。
    一直执行该操作，直到stack中全部元素压入help中，最后再将help中所有元素逐一压入stack，完成排序

    如果要求的是栈从顶到底从小到大，则把上述操作的条件中大于和小于互换即可(等于号不换)

例，stack：2，1，2，3 ,4 左顶右底

    stack            help
    1,2,3,4           2
    2,3,4            1,2
    1,3,4            2,2
    3,4              1,2,2
    2,2,1,4          3
    4                1,2,2,3
    3,2,2,1          4
                     1,2,2,3,4
    4,3,2,2,1
                     
```java
public void sort(Stack<Integer> stack){
    Stack<Integer> help = new Stack<>();
    while(!stack.isEmpty()){
        int cur = stack.pop();
        while(!help.isEmpty() && cur>help.peek()){
            stack.push(help.pop());
        }
        help.push(cur);
    }

    while(!help.isEmpty()){
        stack.push(help.pop());
    }
}



```

用栈解决汉诺塔问题：c-p14
汉诺塔的要求是，任何时刻一个盘子上面都不能有比它大的盘子
最左边的塔不能直接跳到最右边，必须经过中间杆子，打印最优移动过程和最优总步数
例如对于
对于每个杆子，按照上--下的顺序, 有的中间步骤就省略了，只保留关键步骤

 左           中          右
1,2,3        
 2,3          1
  3           2           1
 3                      1,2
             1,3          2
 1           3            2
            1,2,3
 2            3           1
 1,2                      3
             2           1,3
 1           2            3
                         1,2,3
可以看成汉诺塔要做的就是想尽一切办法让最下面的盘往右挪

其实对于每一层的移动，步骤都是类似的。

假设要移动第1层，把它移到中，再移到右
.....移动第二层2，则1在右，把2移到中，再把1移到中，再把1移到左，再把2移到右，再把1移到中，再把1移到右
移动3，则1到2在右。移3到中，移1到中，移1到左，移2到中，移1到中，移1到右，移2到左，移1到中，移1到左，移3到右，移1到中，移1到右，移2到中，移1到中，移1到左，移2到右，移1到中，移1到右
假设当前左边要移动第n层，那么1到n-1层一定都在右，需要做的是，先把n移到中，借它为跳板，把1到n-1移回左，然后把n移到右，然后再把1到n-1移回右
因此，把1到n-1挪到右用了x步，则把1到n挪到右用如下：
把n挪到中，1步；把1到n-1经过中间挪回左边，x步；把n挪到右，1步； 把1到n-1经过中间挪回右，x步。加上本来的1到n-1挪到右的x步，因此，
若1到n-1挪到右用x步，则1到n挪到右用3x+2步。显然如果要打印过程则可以用递归,如果只是要求总步数，直接用动态规划即可

```java

    public void test() {
        step = 0;
        moveHanno(3);
        System.out.println("总步数="+step);

    }

    int step = 0;
    public void fromLeft2Mid(int i){
        if(i<=0) return;
        System.out.println(i+" fromLeft2Mid");
        step++;
    }

    public void fromMid2Right(int i){
        if(i<=0) return;
        System.out.println(i+" fromMid2Right");
        step++;
    }

    public void fromRight2Mid(int i){
        if(i<=0) return;
        System.out.println(i+" fromLRight2Mid");
        step++;

    }

    public void fromMid2Left(int i){
        if(i<=0) return;
        System.out.println(i+" fromMid2Left");
        step++;

    }

    //从右往左挪
    public void moveBackHanoi(int n){
        //当将要把i移到左边时，1到i-1都已经在左边了
        for(int i=1;i<=n;i++){
            fromRight2Mid(i);
            moveHanoi(i-1);
            fromMid2Left(i);
            moveBackHanoi(i-1);
        }
    }

    public void moveHanoi(int n){
        //当将要把i移到右边时，1到i-1都已经在右边了
        for(int i=1;i<=n;i++){
            fromLeft2Mid(i);
            moveBackHanoi(i-1);
            fromMid2Right(i);
            moveHanoi(i-1);
        }


    }
```

使用栈模拟，左中右三个地点都是栈，一个动作能发生的先决条件是不违反小压大原则
还有一个原则是相邻不可逆原则，即：如果上一步的动作是L->M，，那么这一步必不可能是M->L,否则就不可能是最小步数
因此非递归的核心结论：
1.游戏的第一个动作一定是L->M
2.在走出最小步数过程中的任何时刻，四个动作中只有一个动作不违反小压大原则和相邻不可逆原则，其他三个动作一定会违反
p18有证明

为了统一操作，先将3个栈都压入MAX值，这样不影响过程和结果，而且不用单独处理栈空的情况

非递归方法：
```java

    public enum Action{
        NO, L2M,M2L,M2R,R2M;
    }


    public int hanoi2(int n){
        Stack<Integer> lS = new Stack<>();
        Stack<Integer> mS = new Stack<>();
        Stack<Integer> rS = new Stack<>();

        lS.push(Integer.MAX_VALUE);
        mS.push(Integer.MAX_VALUE);
        rS.push(Integer.MAX_VALUE);
        for(int i=n;i>=1;i--){
            lS.push(i);
        }

        //记录上一个操作
        Action[] record = {Action.NO};
        int step = 0;
        while(rS.size()!=n+1){
            //l2m必须是第一个，其他的顺序可以随意
            step += fStack2tStack(record, Action.M2L, Action.L2M, lS, mS, "left", "mid");
            step += fStack2tStack(record, Action.L2M, Action.M2L, mS, lS, "mid", "left");
            step += fStack2tStack(record, Action.M2R, Action.R2M, rS, mS, "right", "mid");
            step += fStack2tStack(record, Action.R2M, Action.M2R, mS, rS, "mid", "right");
        }
        return step;
    }

    //preNoAct为上一个不可能的操作，即当前操作的逆操作
    public int fStack2tStack(Action[] record, Action preNoAct, Action nowAct, Stack<Integer> fStack, Stack<Integer> tStack, String from, String to){
        if(record[0]!=preNoAct && fStack.peek()<tStack.peek()){
            tStack.push(fStack.pop());
            System.out.println("from " + from + " to "+to);
            record[0] = nowAct;
            return 1; //表示该步操作成功
        }
        return 0; //当前操作不满足

    }



```


构造数组的MaxTree c-p22
一个数组的MaxTree定义如下：
》数组必须没有重复元素
》MaxTree是一棵二叉树，数组的每一个值对应一个二叉树节点
》包括MaxTree在内的每一棵子树上，值最大的节点都是树的根

以下面原则来建立这棵树：
1.每一个数的父节点是它左边第一个比它大的数和它右边第一个比它大的数中较小的那一个。
2.如果一个数左边没有比它大的数，右边也没有，也就是说这个数是整个数组的最大值，那么这个数是MaxTree的根节点。

，通过这个方法，所有的数最多只有两个孩子，也就是说，这棵树可以用二叉树表示，而不用多叉树。
要证明的话，只需证明任何一个数在单独一侧，害死数量都不可能超过1即可。

假设a在单独一侧有两个孩子，不妨设在右侧，假设这两个孩子一个是k1，另一个是k2

即：...a...k1...k2...

因为a是k1和k2的父，所以a>k1,a>k2,且k1与k2不相等，所以k1和k2可以分出大小，假设k1较小，k2较大
那么k1可能会以k2位父节点，且绝对不会以a为父节点。再假设k2是较小的，k1是较大的，同理a也轮不到当k2的父节点。总之，k1和k2肯定有一个不是a的孩子，所以，任何一个数的单独一侧，其孩子数量都不可能超过1个，最多只会有1个，因此，任何数最多有2个孩子，而不会有更多。

如何尽快找到每个数左右两边第一个比它大的数呢？利用栈

找每个数左边第一个比它大的数，从左到右遍历每一个数，栈中保持递减序列，新来的数不停地利用pop出栈顶，直到栈顶比新数大或没有数。

以[3,1,2]为例，首先3入栈，接下来1比3小，无须pop出3，1入栈，且确定1往左第一个比它大的数是3，然后2比1大，1出栈，2比3小，2入栈，确定了2往左第一个比它大的数是3。
同样的方法可求得每个数往右第一个比它大的数

可以把生成的树节点以值为key存入hashmap，方便后续得到，

```java

    class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;

        TreeNode(int i){
            val = i;
        }
    }

    public TreeNode getMaxTree(int[] arr){
        if(arr==null || arr.length==0) return null;
        Stack<Integer> leftStk = new Stack<>();
        Stack<Integer> rightStk = new Stack<>();

        int len = arr.length;
        HashMap<Integer, TreeNode> map = new HashMap<>();
        for(int i=0;i<arr.length;i++){
            map.put(arr[i], new TreeNode(arr[i]));
        }

        int[] left = new int[len];
        int[] right = new int[len];

        //对于边界值来说，将比它大的数设为MAX，在后续过程中，如果它两边都是MAX，则他就是根节点；如果不都是MAX，则其中的MAX不会起到作用。
        for(int i=0;i<len;i++){
            int cur = arr[i];
            while(!leftStk.isEmpty() && leftStk.peek()<cur){
                leftStk.pop();
            }
            left[i] = leftStk.isEmpty()?Integer.MAX_VALUE:leftStk.peek();
            leftStk.push(cur);
        }

        for(int i=len-1;i>=0;i--){
            int cur = arr[i];
            while(!rightStk.isEmpty() && rightStk.peek()<cur){
                rightStk.pop();
            }
            right[i] = rightStk.isEmpty()?Integer.MAX_VALUE:rightStk.peek();
            rightStk.push(cur);
        }

        TreeNode root = null;
        for(int i=0;i<len;i++){
            int cur = arr[i];
            //如果它两边都是MAX，则他就是根节点
            if(left[i]==right[i] && right[i]==Integer.MAX_VALUE){
                root = map.get(cur);
            }else if(left[i]>right[i]){
                //当前数的右边的第一个大值为其父，则它就是它父的左孩子
                TreeNode parent = map.get(right[i]);
                parent.left = map.get(cur);
            }else{
                TreeNode parent = map.get(left[i]);
                parent.right = map.get(cur);
            }
        }
        return root;

    }
```

求最大子矩阵的大小：c-p26
01矩阵中，求全是1的矩阵中最大的面积

按行遍历，每遍历到一个1的位置时，往它上面看以它为底的这列最多能有多高，然后再向它的两边扩展，看每一个相邻的1所在列的高度能不能达到它的高度，每扩展一个1，计算当前矩形能达到的最大面积，取其中最大的，就是包含该位置的1的矩形能达到的最大面积，遍历所有位置，取其中的最大值作为答案.

preHeight[] 表示上一行的元素对应列的最大高度

以当前元素为中心，先向左扩展，计算出以当前列为右边界的矩形的最大面积，计算出左边的最小高度，
再向右扩展，计算出以当前列为左边界的矩形的最大面积，计算出右边的最小高度，
再计算出仅有一列的矩形的面积（最高瘦的矩形）
再计算出宽度最大，高度为左右中的小值的矩形面积（最矮胖的矩形）
这四个值中的最大值即为当前元素所在所有矩形中最大的。

遍历所有元素并计算其对应的最大矩形，取其中最大的那个作为答案

```java
 public int maxRecSize(int[][] map){
        if(map==null || map.length==0 || map[0].length==0) return 0;
        int row = map.length;
        int col = map[0].length;

        int ans = 0;
        int[] preHeight = new int[col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                int cur = map[i][j];
                if(cur==0){
                    preHeight[j]=0;
                    continue;
                }else{
                    preHeight[j]+=1;
                }
                //以该位置往两边辐射
                int lp = j-1;
                int rp = j+1;

                int area = preHeight[j]*1;  //以本元素为底的宽度为1的矩形大小
                int minHeight = preHeight[j];
                //计算出左边矩形的最大面积，并记录最小高度和最长宽度
                int minLHeight = preHeight[j];
                int lArea = area;
                int lWidth = 1;
                while((lp>=0 && map[i][lp]==1)){
                    //左边的，高度数组已经更新为本行的了。
                    lWidth++;
                    minLHeight = Math.min(preHeight[lp], minLHeight);
                    lArea = Math.max(lArea, minLHeight*lWidth);
                    lp--;

                }
                int minRHeight = preHeight[j];
                int rArea = area;
                int rWidth = 1;
                while(rp<col && map[i][rp]==1){
                    //右边的，高度数组还是上一行的，所以要加上本行的
                    rWidth++;
                    minRHeight = Math.min(preHeight[rp]+1, minRHeight);
                    rArea = Math.max(rArea, minRHeight*rWidth);
                    rp++;
                }
                //再综合左右情况，较出最小的高度
                minHeight = Math.min(minLHeight, minRHeight);
                //到这里，rp和lp都是不合法的位置了（边界）
                int width = rp-lp-1;
                int tmp = minHeight * width;
                //当前左边能达到的最大矩形和右边能达到的最大矩形和最矮矩形和最高矩形比出谁最大
                area = Math.max(tmp, lArea);
                area = Math.max(area, rArea);

                ans = Math.max(ans, area);
            }
        }
        return ans;
    }
```

最大值减去最小值小于或等于num的子数组数量：c-p31

给定数组arr和整数num，共返回有多少子数组满足如下情况：
max(arr[i..j])-min(arr[i..j]) <= num
max(arr[i..j])表示子数组arr[i..j]中的最大值，
min(arr[i..j])表示子数组arr[i..j]中的最小值。

实现O(N)的算法

普通解法是求出每个子数组，再在每个子数组中得出最大值和最小值看是否满足条件，复杂度是O(N^3)

借助于”生成窗口最大值数组“中的双端队列的思想，生成两个双端队列qmax和qmin，一个存储大值的下标，一个存储小值的下标
其原则与原来的基本一致：
遍历到num[i]时：
若num[i]>=qmax头端的索引对应的值，则qmax全出队，num[i]入队
否则从qmax尾端比较，从尾端出队直到尾端的值大于等于num[i]，再压入num[i]

若num[i]<=qmin头端的索引对应的值，则qmin全出队，num[i]入队
否则从qmin尾端比较，从尾端出队直到尾端的值小于等于num[i]，再压入num[i]

qmax维护了窗口子数组arr[i..j]的最大值的更新结构，qmin维护了窗口子数组arr[i..j]的最小值的更新结构。当子数组arr[i..j]向右扩一个位置变成arr[i,j+1]时，qmax和qmin结构可以在O(1)的时间内更新，当子数组arr[i..j]向左缩一个位置变成arr[i+1,j]时。qmax和qmin结构仍可以在O(1)的时间内更新，并在O(1)的时间内获得最大最小值。

结论：
1.如果子数组arr[i..j]满足条件，那么arr[i..j]中的每一个子数组都满足条件
2.如果子数组arr[i..j]不满足条件，那么所有包含arr[i..j]的子数组都不满足条件。

步骤：
1.用两个变量i和j表示子数组的范围。res为结果
2.令j不断向右移，即j++，表示arr[i,j]向右扩大，并不断更新qmax和qmin
一旦出现arr[i..j]不满足条件，j停止向右扩。此时arr[i..i], ... arr[i,j-2], arr[i, j-1]都满足条件，即所有以arr[i]作为第一个元素的子数组，满足条件的数量为j-i个，res+= j-i
3.进行完步骤2，令i向右移动一个位置，并对qmax和qmin更新，然后重复步骤2，也就是求所有以arr[i+1]作为第一个元素的子数组中，满足条件的数量有多少个。
4.根据步骤2和步骤3，依次求出以arr[0], arr[1] ... arr[n-1]作为第一个元素的子数组中满足条件的数量分别有多少个，加起来就是结果

有两个点要注意：第一层循环中条件不能加j< len，因为最后一个子数组的时候arr[len-1, len-1]需要j为len
在更新最大最小值的过程中，如果当前j在队列中，则要先将其出队，然后再入队，否则直接入队会造成队列中存在重复值（虽然对结果没有影响）。因为此时若j不满足条件，则j对应的值会被更新进qmax，然后跳出循环；i更新后，又会进入j的循环，j对应的值又会被更新进qmax，所以如果已经在的话，先出队，然后再入队，保证个数正确


```java
    public int getNum(int[] arr, int num){
        if(arr==null || arr.length==0) return 0;
        LinkedList<Integer> qmax = new LinkedList<>();
        LinkedList<Integer> qmin = new LinkedList<>();
        int i=0,j=0;
        int res = 0;
        int len = arr.length;
//        qmax.offerLast(0);
//        qmin.offerLast(0);

        while(i<len){ //这里不能判断j<len，因为最后一个子数组的时候arr[len-1, len-1]需要j为len
            while(j<len){

                //更新最大值
                if(!qmax.isEmpty()) {
                    if (arr[qmax.getFirst()] < arr[j] || qmax.getFirst()==j) {
                        while (!qmax.isEmpty())
                            qmax.poll();
                    } else {
                        while (arr[qmax.getLast()] < arr[j] || qmax.getLast()==j)
                            qmax.pollLast();
                    }
                }
                qmax.offerLast(j);

                //更新最小值
                if(!qmin.isEmpty()) {
                    if (arr[qmin.getFirst()] > arr[j] || qmin.getFirst()==j) {
                        while (!qmin.isEmpty())
                            qmin.poll();
                    } else {
                        while (arr[qmin.getLast()] > arr[j] || qmin.getLast()==j)
                            qmin.pollLast();
                    }
                }
                qmin.offerLast(j);

                if((arr[qmax.getFirst()]-arr[qmin.getFirst()])>num){
                    break;
                }
                j++;
            }

            //到这里不满足了
            res += j-i;
            i++;
            while(!qmax.isEmpty() && qmax.getFirst()<i){
                qmax.pollFirst();
            }
            while(!qmin.isEmpty() && qmin.getFirst()<i){
                qmin.pollFirst();
            }

        }

        return res;
    }

```

打印2个有序链表的公共部分：c-p34

注意不是两个链表有公共节点，而是打印出它俩值相等的节点

如果head1小于head2，head1往下移动
如果head1大于head2，head2往下移动
如果二者相等，打印值，然后head1和head2都往下移动
head1和head2任意一个移动到null，则停止过程




在单链表和双链表中删除倒数第k个节点，c-p35

删除单链表的：双指针法：p1到达第k个节点后，p2设为第1个结点，然后二者一起往后走，当p1到末尾节点时，p2就是倒数第k个节点，并且过程中要一直维持p2的前驱节点（为了方便，最好弄个假头节点）

删除双链表的：
双指针法也可，不用设置前驱了，因为有pre域
单指针法：一个指针一直到达末尾，然后往前走k-1次，就是倒数第k个节点


删除链表的中间节点和a/b处的节点：c-p38

删除中间节点：三个指针p1，p2和pre，p1一次走2步，p2一次走1步，pre始终是p2的前驱
当p1走到末尾时，p2刚好在中间，执行删除即可。注意最好是有dummy节点，方便操作

删除a/b处的节点：
例如，有链表1->2->3->4->5 假设a/b的值为r
如果r为0，则不删除任何节点
如果r在区间(0, 1/5]上，删除节点1
如果r在区间(1/5, 2/5]上，删除节点2
如果r在区间(2/5, 3/5]上，删除节点3
...
如果r在区间(4/5, 1]上，删除节点5
如果r大于1，不删除任何节点

先遍历链表得出长度l，让a/b * l 向上取整得到要删除的节点编号，再顺着去删除即可


```java
    public ListNode removeNode(ListNode head, int a, int b){
        if(head==null || a==0 || b==0 || a>b) return head;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode p = head;
        int len = 0;
        while(p!=null){
            len++;
            p = p.next;
        }

        int target = (int) Math.ceil(a*len / b);
        ListNode pre = dummy;
        p = head;

        for(int i=1;i<target;i++){
            pre = p;
            p = p.next;
        }
        pre.next = p.next;
        p = null;
        return dummy.next;

    }

```

翻转单向和双向链表：c-p40：
要求空间复杂度是O(1)
翻转单向链表，见j24-p142，
    链表长为2时直接调换头尾即可
    长大等于3时，要维持3个指针p1,p2,p3
    循环 p2.next=p1, p1=p2,p2=p3,p3=p3.next，结束条件是p3!=null
    最后再让p2.next=p1 ，很重要的一点是最后要让head的next为null。(head是起始链表的第一个节点)

翻转双向链表：
    双向链表的难点在于要维持两个域(pre和next)的正确
    和单向链表相似，但要改部分：
    循环：p2.next=p1,p1.pre=p2, p1=p2,p2=p3,p3=p3.next，结束条件是p3!=null
    最后再让p2.pre=null, p2.next=p1, p1.pre=p2, head.next=null 
    (head是起始链表的第一个节点）

```java

    class DListNode{
        int val;
        DListNode next;
        DListNode pre;

        DListNode(int x) {
            val = x;
        }
    }


    public DListNode reverse(DListNode head){
        if(head==null || head.next==null) return head;
        if(head.next.next == null){
            DListNode tmp = head.next;
            head.next = null;
            head.pre = tmp;
            tmp.next = head;
            tmp.pre = null;
            return tmp;
        }

        DListNode p1,p2,p3;
        p1=head;
        p2=head.next;
        p3=head.next.next;
        while (p3!=null){
            p2.next = p1;
            p1.pre = p2;

            p1=p2;
            p2=p3;
            p3=p3.next;
        }
        p2.next=p1;
        p2.pre=null;
        p1.pre=p2;
        head.next = null;
        return p2;
    }
```


反转部分单向链表：c-p42
给定一个单向链表，及两个整数from和to，把from到to进行反转
很简单，将第from-1个节点和to+1个节点记录下来，然后把from到to的链表反转，然后把from-1和to+1接上去即可。
为了方便操作，先引入dummy

```java
    public ListNode reversePart(ListNode head, int from, int to){
        ListNode dummy = new ListNode(-1);
        if(head==null || to<=from) return head;
        dummy.next = head;

        ListNode fromPre=null, nextTo=null;
        ListNode p = head;
        int len = 0;
        while (p!=null){
            len++;
            p = p.next;
        }

        if(from > len || to>len) return head;

        p = dummy;
        for (int i=0;i<=to;i++){
            if(i==from-1){
                fromPre = p;
            }
            p = p.next;
        }
        nextTo = p;

        ListNode newTail = fromPre.next;
        ListNode newHead = reverse(newTail);

        newTail.next = nextTo;
        fromPre.next = newHead;
        return dummy.next;

    }


    ListNode reverse(ListNode head){
        if(head==null || head.next==null) {
            return head;
        }
        if(head.next.next==null){
            ListNode tmp = head.next;
            tmp.next = head;
            head.next = null;
        }

        ListNode p1 = head;
        ListNode p2 = head.next;
        ListNode p3 = head.next.next;

        while (p3!=null){
            p2.next = p1;
            p1=p2;
            p2=p3;
            p3=p3.next;
        }
        p2.next = p1;
        head.next = null;
        return p2;
    }
```

环形单链表的约瑟夫问题：c-p43
41个人绕成一个圈，从第一个人开始报数，报到m的就踢出环，从下一个人再开始报1，直到剩下一个人
用环形单链表来描述这个过程。

建一个环，然后过程很简单，不断更新计数，当计数为3时，删除当前节点，并把它的前后节点连起来
最终直到只有一个节点，它的next是自己。

可以引入dummy，但dummy不在环内，不参与循环过程
利用循环，整个算法的复杂度是O(n * m) //n为节点个数，m为报数的值
（每想删掉一个节点，都需要遍历m次，一共需要删除的节点数是n-1个，所以复杂度是O(n * m)）

```java
    public int josephus(int num, int m){
        ListNode dummy = new ListNode(0);
        ListNode n = dummy;
        for(int i=1;i<=num;i++){
            ListNode node = new ListNode(i);

            if(i==num){
                n.next = node;
                node.next = dummy.next;
            }else {
                n.next = node;
                n = node;
            }
        }

        n = dummy.next;
        ListNode pre = dummy;
        int i=1;
        while(n.next!=n){ //只剩下一个节点
            if(i==m){
                pre.next = n.next;
                n = n.next;
                i = 1;
                continue;
            }
            pre = n;
            n = n.next;
            i++;
        }
        return n.val;
    }
```

如果链表节点数n，想在时间复杂度为O(N)内完成解答，要如何实现？
原问题花费时间多，是因为我们一开始不知道哪个节点会最后活下来，所以需要不断删除。
例如1->2->3->4->5->1,从头结点开始编号，
如果环形链表节点数为n，作如下定义：从环形链表头结点开始编号，头结点编号1，下一个编号2.。。最后一个节点编号n
最后只剩下一个节点，这个幸存节点在只由自己组成的环中编号为1，记为Num(1)=1;
在由2个节点组成额环中，这个幸存节点的编号是多少呢？假设是Num(2)
。。。
在由i个节点组成额环中，这个幸存节点的编号是多少呢？假设是Num(i)
。。。
在由n个节点组成额环中，这个幸存节点的编号是多少呢？假设是Num(n)

我们已经知道Num(1)=1,如果再确定Num(i-1)和Num(i)的关系，就可以递归求出了

1.假设现在圈中共有i个节点，从头结点开始报数，报1的是编号1的节点，报2的是编号2的节点，假设报A的是编号B的节点，则A和B关系如下：
    A    B
    1    1
    2    2
    i    i
    i+1  1
    i+2  2
    ...
    2i   i
    2i+1 1
    ...
举个例子，环形链表中有3个节点，报1的是编号1，报2的是编号2，报3的是编号3，报4的是编号1，
报5的是编号2，报6的是编号3，报7的是编号1，。。

A与B的关系可写成 B=(A—1)%i+1 。该表达式不唯一，总之只要找到报的数（A）和当前环中的编号节点（B）的关系即可。

2.如果编号为2的节点被删除，环的节点数自然从i变成i-1.那么原来在大小为i的环中，每个节点的编号会发生什么呢？


    环大小为i的每个节点编号    删掉编号s的节点后，环大小为i-1的每个节点的编号
       ...                      ...
       s-2                      i-2
       s-1                      i-1
       s                        ----(无编号，因为被删掉了)
       s+1                       1
       s+2                       2

假设环大小为i的每个节点编号即为old，环大小为i-1的每个节点编号记为new，则old与new的关系表达式为：
old=(new+s-1)%i+1 (这个也不唯一，能满足即可) 

3.因为每次都是报数到m的节点被杀，根据步骤1的表达式B=（A-1）%i+1, A=m。被杀的节点编号为(m-1)%i+1, 即s=(m-1)%i+1，代入到步骤2中的表达式中，即为old=(new+(m-1)%i+1)%i+1,经过化简为：
old=(new+m-1)%i+1。至此，我们终于得到了Num(i-1)-new 和 Num(i)-old 的关系，且这个关系只和m与i的值有关。

整个解法总结如下：
1.遍历链表，求链表的节点个数记为n，时间复杂度为O(N),
2.根据n和m的值，还有上面分析的Num(i-1)和Num(i)的关系，递归求生存节点的编号，递归为N层，所以时间复杂度为O(N)
3.最后根据生存节点的编号，遍历链表找到该节点，时间复杂度为O(N)
4.整个过程结束，总的时间复杂度是O(N)

核心代码：getLive：递归，根据下一次环中幸存者的编号计算出它在此次环中的编号
public int getLive(int len, int m){
    if(i==1) return 1;
    return (getLive(len-1, m)+m-1)%len+1;
}


```java
public ListNode josephuKill2(Node head, int m){
    if(head==null || head.next==head || m<1){
        return head;
    }
    //注意这里的head不是假头结点，而是环中的头结点
    ListNode cur = head.next;
    int tmp = 1;
    while(cur!=head){
        tmp++;
        cur = cur.next;
    }
    tmp = getLive(tmp, m);
    while(--tmp!=0){
        head = head.next;
    }
    head.next = head;
    return head;
}

public int getLive(int len, int m){
    if(i==1) return 1;
    return (getLive(len-1, m)+m-1)%len+1;
}
```


回文链表：c-p48.见：234

方法一：先把链表值全压入栈，然后逐个出栈与原链表逐一比对
方法二：把原链表分成两部分，前一半不变，后一半翻转，然后两个指针分别从头部和中间(后一半的头部)逐一比较



将单向链表按某值划分成左边小，中间相等，右边大的形式：c-p52：
很简单，设置3个指针假头，遍历原链表，第一个后面接比该值小的，第二个后面接和它等的，第三个后面接比它大的，最后再把3个链表从第二个节点开始（去除假头）连起来即可,这样能保证顺序也不变，sO(1), tO(n)

需要注意的是，在往3个链上接节点的时候，要把当前节点的next置空，避免链一直那么长，所以要提前把next保存起来以便遍历
```java

    public ListNode listPartition(ListNode head, int pivot){
        if(head==null) return null;
        ListNode dummy1 = new ListNode(-1);
        ListNode dummy2 = new ListNode(-2);
        ListNode dummy3 = new ListNode(-3);

        ListNode p1=dummy1,p2=dummy2,p3=dummy3, p=head;

        while(p!=null){
            //提前把next保存，然后每把一个节点接到相应的链上，就要把它的next置空。
            ListNode tmp = p.next;
            p.next = null;
            if(p.val<pivot){
                p1.next=p;
                p1 = p1.next;
            }else if(p.val==pivot){
                p2.next = p;
                p2 = p2.next;
            }else{
                p3.next = p;
                p3 = p3.next;
            }
            p = tmp;
        }

        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        ListNode n = dummy1.next;
        while(n!=null){
            node.next = n;
            n = n.next;
            node = node.next;
        }
        n = dummy2.next;
        while(n!=null){
            node.next = n;
            n = n.next;
            node = node.next;
        }
        n = dummy3.next;
        while(n!=null){
            node.next = n;
            n = n.next;
            node = node.next;
        }
        return dummy.next;

    }
```

复制含有随机指针节点的链表：c-p56
：138, j35-p187
sO(n)很简单，把原始节点存入list1，把新的节点也存入一个list2，按照原始节点random域指向节点在list1中的索引，得到其对应的新节点的random域应该指向list2中的哪个节点

sO(1):复制的节点在原节点后面接上，即oriNode.next = newNode; 先把整个链表复制一遍，这样链表就变成：
o1->n1->o2->n2->o3->n3, 然后遍历奇数号的节点（即原节点）
让其next(即它对应的新节点)的next指向它的random的next(即它的random的新节点)
即： cur.next.random = cur.random.next;
然后再分离奇数号和偶数号的节点，形成两个链表

```java
    public ListNode copyListWithRand(ListNode head){
        if(head==null) return null;
        ListNode n = head;
        while(n!=null){
            ListNode tmp = n.next;
            n.next = new ListNode(n.val);
            n.next.next = tmp;
            n = tmp;
            return tmp;
        }

        n = head;
        while(n!=null){
            if(n.random!=null)
                n.next.random = n.random.next;
            n = n.next.next;
        }

        n = head;
        ListNode newHead = head.next;
        ListNode nn = newHead;
        while(n!=null){
            n.next = n.next.next;
            if(nn.next!=null)
                nn.next = nn.next.next;

            n = n.next;
            nn = nn.next;
        }
        return newHead;
    }
```

两个链表相加生成链表，节点的值在0-9之间。如9->3->7 和 6->3相加 新链表是 1->0->0->0：c-p59
用sO(n)很简单，把两个链表分别压入栈中，弹栈相加，记录进位，和的个位新做一个链表，然后把结果链表翻转即可

用sO(1)
先把两个链表翻转，然后逐节点加和，记录进位，和的个位新做一个链表，然后把结果链表翻转即可
注意，一定要记得把dummy从原链表中断开，否则也就被翻转掉了


```java

    public ListNode addList(ListNode head1, ListNode head2){
        ListNode iHead1 = reverse(head1);
        ListNode iHead2 = reverse(head2);
        ListNode dummy = new ListNode(0);
        ListNode n = dummy;
        ListNode p1=iHead1, p2=iHead2;
        int c = 0;
        while(p1!=null || p2!=null){
            int i1 = p1==null?0:p1.val;
            int i2 = p2==null?0:p2.val;

            int cur = (i1+i2+c)%10;
            c = (i1+i2+c)/10;
            n.next = new ListNode(cur);
            n = n.next;
            if(p1!=null)p1=p1.next;
            if(p2!=null)p2=p2.next;
        }

        if(c>0){
            n.next = new ListNode(c);
        }
        ListNode head = dummy.next;
        dummy.next = null; 
        //这一步很重要，一定要记得把dummy从原链表中断开，否则也就被翻转掉了
        reverse(iHead1);
        reverse(iHead2);

        return reverse(head);
    }


    ListNode reverse(ListNode head){
        if(head==null || head.next==null) {
            return head;
        }
        if(head.next.next==null){
            ListNode tmp = head.next;
            tmp.next = head;
            head.next = null;
            return tmp;
        }

        ListNode p1 = head;
        ListNode p2 = head.next;
        ListNode p3 = head.next.next;

        while (p3!=null){
            p2.next = p1;
            p1=p2;
            p2=p3;
            p3=p3.next;
        }
        p2.next = p1;
        head.next = null;
        return p2;
    }
```

两个单链表相交的一系列问题： c-p62
两个单链表，可能有环也可能无环，给定两个头结点head1和head2，两个链表可能相交也可能不相交，实现一个函数，如果相交，返回相交的第一个节点，如果不相交，返回null
要求：如果链表1长度为N，链表2长度为M，时间复杂度要求O(M+N), 额外空间复杂度请达到O(1)

如何判断一个链表有环是关键，有的单链表的环不是全环，而是部分有环，如 1->2->3->2..
如何判断这种环是难点

用龟兔赛跑算法，p1每次跑1步，p2每次跑2步，如果p2为了null，则说明没环，一旦p2和p1相等了，说明有环，p1返回链表头，p1，p2都一步一步往前走，最先重合的点是环入口

链表2也用上述办法，但过程中多加一步判断当前节点是否是链1的环入口，如果有节点是环入口，说明链1和链2的环是重合的。但二者的首个重合点不一定是环入口，而且二者的入环点可能不同，此时要算出环的长度，再算出链1链2分别环外的长度，让两个节点分别从距环相同距离的节点开始往前走，如果走到环上了还不相同，说明入环点不一样，返回任意一个入环点都可

如果一个有环一个没环，一定不相交

如果两个都没环，则p1先走l1再走l2，p2先走l2再走l2，第一次相遇的点就是第一个相交的节点。如果二者都走过了两个链表长，始终不相等，则说明不相交

```java


    public ListNode getCrossNode(ListNode head1, ListNode head2){
        if(head1==null || head2==null) return null;
        ListNode entrance1 = getCycleEntrance(head1);
        ListNode entrance2 = getCycleEntrance(head2);
        if((entrance1==null && entrance2!=null) || (entrance2==null && entrance1!=null)){
            return null;
        }else if(entrance1==null && entrance2==null){
            ListNode p1 = head1;
            ListNode p2 = head2;
            int len1 = 1;
            int len2 = 1;
            while(p1!=null){
                p1=p1.next;
                len1++;
            }
            while(p2!=null){
                p2=p2.next;
                len2++;
            }
            p1=head1;
            p2=head2;
            int step=1;
            while(p1!=p2 || step>=len1+len2){
                p1=p1.next;
                if(p1==null){
                    p1=head2;
                }
                p2=p2.next;
                if(p2==null){
                    p2=head1;
                }
                step++;
            }
            return p1==p2?p1:null;
        }else{
            //如果两个环没有交点
            if(!isCycleCross(entrance1, entrance2)) return null;

            //就算entrance1==entrance2，也不能确保他俩就算第一个交点，或许交点在环外
            int outlen1 = getOutLength(head1, entrance1);
            int outlen2 = getOutLength(head2, entrance2);
            ListNode p1=head1, p2=head2;
            if(outlen1>outlen2){
                while(outlen1!=outlen2){
                    outlen1--;
                    p1=p1.next;
                }
            }else if(outlen1<outlen2){
                while(outlen1!=outlen2){
                    outlen2--;
                    p2=p2.next;
                }
            }
            //环长+环外最小长，如果


            //此时p1,p2距离环相等,如果走到环了二者还不等，说明两条链进入环的点不一样，则无所谓第一个交点，返回第一个入环点即可.
            // 否则若在返回它们俩第一次相等时的位置即可
            while(p1!=p2 && p1!=entrance1){
                p1=p1.next;
                p2=p2.next;
            }
            return p1;


        }
    }

    //获得环外的长度
    int getOutLength(ListNode head, ListNode entrance){
        if(head==entrance) return 0;
        int len = 0;
        ListNode n = head;
        while(n!=entrance){
            n = n.next;
            len++;
        }
        return len;

    }

    //传入两个环，看是否有交点
    public boolean isCycleCross(ListNode head1, ListNode head2){
        if(head1==head2) return true;
        ListNode n = head1;
        do{
            n=n.next;
        }while(n!=head2 && n!=head1);

        if(n==head2) return true;
        else return false;


    }

    //传入环中任意一个节点
    public int getCycleLength(ListNode head){
        ListNode n = head;
        int len = 0;
        do{
            n = n.next;
            len++;

        }while(n!=head);
        return len;
    }


    //如果返回null，说明没环，否则返回环的入口
    public ListNode getCycleEntrance(ListNode head){
        ListNode p1 = head;
        ListNode p2 = head;

        do{
            p1 = p1.next;
            p2 = p2.next;
            if(p2!=null){
                p2=p2.next;
            }

        }while (!(p2==null || p1==p2));
        if(p2==null) return null;

        p1=head;
        while(p1!=p2){
            p1=p1.next;
            p2=p2.next;
        }
        return p1;
    }

```

将单链表的每k个节点之间逆序：c-p68

给一个单链表，实现调整单链表的函数，使得每K个节点之间逆序，如果最后不够K个节点一组，则不调整最后几个节点，例如：1-2-3-4-5-6-7-8   K=3   调整为 3-2-1-6-5-4-7-8   7、8不调整，因为不够1组

用2个节点，h来表示当前要翻转的子链的头结点，n顺着往下遍历，
用一个计数器，每经过k个点，每当计数器为K-1时，就翻转h到n的节点，然后k恢复为0，h调为翻转前n的next，n也调为翻转前n的next

```java

    ListNode reverseKGroup(ListNode head, int k) {
        ListNode n = head;
        ListNode h = head;
        ListNode newHead = null;
        ListNode preTail = null;
        int c = 0;
        while (n != null) {
            ListNode tmp = n.next;
            if (c == k - 1) {
                n.next = null;
                reverse(h);
                if (h == head) {
                    newHead = n;
                }
                if (preTail != null) {
                    preTail.next = n;
                }
                preTail = h;
                h.next = tmp;
                h = tmp;
                n = tmp;
                c = 0;
            } else {
                n = tmp;
                c++;
            }
        }
        return newHead == null ? head : newHead;
    }

    ListNode reverse(ListNode head){
        if(head==null || head.next==null) {
            return head;
        }
        if(head.next.next==null){
            ListNode tmp = head.next;
            tmp.next = head;
            head.next = null;
            return tmp;
        }

        ListNode p1 = head;
        ListNode p2 = head.next;
        ListNode p3 = head.next.next;

        while (p3!=null){
            p2.next = p1;
            p1=p2;
            p2=p3;
            p3=p3.next;
        }
        p2.next = p1;
        head.next = null;
        return p2;
    }

```

删除无序单链表中值重复出现的节点：c-p71
例如 1-2-3-3-4-4-2-1-1  删除后为 1-2-3-4
如果空间复杂度为O(N),时间复杂度是O(N)的话很简单，每遍历一个节点，尝试把其值放入hashset中，如果出现过该值则删除该节点

如果空间复杂度为O(1),时间复杂度是O(N^2)，则设定一个节点p等于当前节点，n为p.next, n往后遍历，只要出现等于这个值的节点，都删掉（所以还要保留pre），然后p继续往下走一个，n继续为p.next，继续。
最后，head一定还是head，因为是从第二个节点才开始删的

```java
    public ListNode removeDuplicates(ListNode head){
        ListNode p = head;
        ListNode n = head.next;
        ListNode pre = head;
        while(p!=null){
            n = p.next;
            pre = p;
            while(n!=null){
                if(n.val==p.val){
                    pre.next = n.next;
                    n = n.next;
                }else{
                    pre = n;
                    n = n.next;
                }
            }
            p = p.next;
        }
        return head;
    }
```

单链表中删除指定值的节点：c-p73
很简单，保存一个pre，一个cur，遇到cur等于指定值，直接删了就行，tO(n)+sO(1)
```java
    public ListNode removeNode(ListNode head, int num){
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = head;
        ListNode pre = dummy;
        while(cur!=null){
            if(cur.val==num){
                pre.next = cur.next;
                cur = cur.next;
            }else{
                pre = cur;
                cur = cur.next;
            }
        }
        return dummy.next;
    }
```

将二叉搜索树转成有序的双向链表 c-p74
二叉搜索树的中序遍历就是顺序，中序遍历即可，维持一个前一个遍历过的节点用来保持连接
但要注意的是，如果引入假头节点，在最后返回的时候要断开假头结点和链表之间的联系（主要是head.pre）

```java
    public ListNode convertTree2List(TreeNode root){
        ListNode dummy = new ListNode(0);
        TreeNode p = root;
        ListNode pre = dummy;
        Stack<TreeNode> stk = new Stack<>();
        while(p!=null || !stk.isEmpty()){
            if(p!=null){
                stk.push(p);
                p=p.left;
            }else{
                p = stk.pop();
                ListNode t = new ListNode(p.val);
                pre.next = t;
                t.pre = pre;
                pre = t;
                p = p.right;
            }
        }
        ListNode head = dummy.next;
        head.pre = null;
        return head;
    }
```

单链表的选择排序：c-p79
要求sO(1),选择排序就是从未排序的部分找出最小值，然后放在排好序部分的尾部。每次都遍历未排序序列，挑出其中的最小值单独接在已排序序列的最后，并且把它从未排序链表中删除。而且删除的过程要保证链表在结构上不断开


#（注意区别插入排序和选择排序）：
插入排序是直接从未排序序列中取首元素，然后逐个与已排序的比，插入。
选择排序是从未排序的序列中选出最小的，直接插入到已排序序列的最后

```java
    public ListNode selectionSort(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode inode = dummy.next;
        ListNode newDummy = new ListNode(0);
        ListNode n = newDummy;
        while (inode != null) {

            ListNode min = inode;
            ListNode minPre = dummy;

            ListNode jnode = inode.next;
            ListNode jpre = inode;
            while (jnode != null) {
                if (jnode.val < min.val) {
                    min = jnode;
                    minPre = jpre;
                }
                jpre = jnode;
                jnode = jnode.next;

            }

            minPre.next = min.next;
            n.next = min;
            n = n.next;
            n.next = null;

            inode = dummy.next;

        }
        return newDummy.next;
    }
```

只给定一个节点，以O(1)方式删除该节点的方法存在的问题：c-p82： 237的问题
1.这样的方式无法删除最后一个节点（继无法让倒数第二个节点的next变成null）
2.本质上部署删除node节点，工厂上一个节点可能代表很复杂的结构，节点值的复制很复杂，或者根本就禁止改变节点的值


向有序的环形单链表中插入新节点：c-p82

一个环形链表从头结点head开始不降序，最后节点指回头结点，给一个num，将其生成新节点插入到链表中

无限循环遍历链表，遍历到第一个比它大的节点处，插在前面。
有区别的两点：
如果要插在最后一个节点之前，要判断的是如果两边都比他小或等，且后面是head时插入到head前
如果要插在第一个节点前，则要判断的是如果两边都比他大或等，且后面是head时插入到head前

一定要考虑等的情况，不然可能陷入死循环

最后返回的时候，可以考虑遍历一遍

```java
    public ListNode insertCycle(ListNode head, int num){
        ListNode n = new ListNode(num);
        if(head==null){
            n.next = n;
            return n;
        }

        ListNode cur = head.next;
        ListNode pre = head;
        while(true){
            if(cur.val>=num && num<=pre.val){
                pre.next = n;
                n.next = cur;
                return head.val>n.val?n:head;
            }
            if(pre.val<=num && cur.val<=num && cur==head){
                pre.next = n;
                n.next = cur;
                return head;
            }
            if(pre.val>num && cur.val>num && cur==head){
                pre.next = n;
                n.next = cur;
                break;
            }

            pre = cur;
            cur = cur.next;
        }
        return head;
    }
```

合并两个有序的单链表：c-p84
很简单，两个指针p1，p2分别指向两个链表的头，哪个小就接在新链表后面，且指针往后挪一位，直到一个链表空了，把剩下的链表都接在新链表后面即可

```java
    public ListNode merge(ListNode head1, ListNode head2){
        ListNode p1 = head1;
        ListNode p2 = head2;
        ListNode dummy = new ListNode(0);
        ListNode n = dummy;
        while(p1!=null && p2!=null){
            if(p1.val<p2.val){
                n.next = p1;
                p1 = p1.next;
            }else{
                n.next = p2;
                p2 = p2.next;
            }
            n = n.next;
        }
        n.next = p1==null?p2:p1;
        return dummy.next;
    }
```


给定一个单链表头部节点head，链表长度N，如果N为奇数，那么前N/2算左半区，后N/2为右半区；如果N为奇数，那么前N/2算左半区，后N/2+1为右半区；左半区是L1->L2->... 右半区是R1->R2->...请将单链表调整为 L1->R1->L2->R2...的形式：c-p86

轮流把两个半区的节点接到新链表上。p1到左半区头，p2为右半区头，设立一个flag表示该接左还是右的节点了，接上后，p1/p2往后移一位， flag取反， 新链表的尾节点=其next... 循环直到p1,p2都是null

```java
    public void relocate(ListNode head){
        ListNode n = head;
        ListNode p1= head;
        int len = 0;
        while(n!=null){
            n=n.next;
            len++;
        }
        ListNode p2 = head;
        for(int i=0;i<len/2;i++){
            p2=p2.next;
        }

        ListNode dummy = new ListNode(0);
        n = dummy;
        boolean flag = true;
        while(p1!=null && p2!=null){
            if(flag){
                n.next = p1;
                p1 = p1.next;
            }else{
                n.next = p2;
                p2 = p2.next;
            }
            flag = !flag;
            n = n.next;
        }
    }
```

使用递归方法和非递归方法完成二叉树的前序，中序，后序遍历
递归方法很简单，不用多说
主要再写一遍非递归：
```java
//前序
public List preTraversal(TreeNode root){
    ArrayList<Integer> list = new ArrayList<>();
    Stack<TreeNode> stk = new Stack<>();
    TreeNode p = root;
    stk.push(p)
    while(!stk.isEmpty()){
        p = stk.pop();
        list.add(p.val);
        if(p.right!=null )stk.push(p.right);
        if(p.left!=null )st.push(p.left);
    }
    return list;
}


//中序
public List midTraversal(TreeNode root){
    ArrayList<Integer> list = new ArrayList<>();
    Stack<TreeNode> stk = new Stack<>();
    TreeNode p = root;
    while(!stk.isEmpty() || p!=null){
        if(p!=null){
            stk.push(p);
            p=p.left;
        }else{
            p = stk.pop();
            list.add(p.val);
            p = p.right;
        }
    }
    return list;
}


//后序
    public List postTraversal(TreeNode root){
        ArrayList<Integer> list = new ArrayList<>();
        Stack<TreeNode> stk = new Stack<>();
        TreeNode p = root;
        TreeNode pre = null;
        while(!stk.isEmpty() || p!=null){
            while(p!=null){
                stk.push(p);
                p = p.left;
            }
            p = stk.peek();
            if(p.right==pre || p.right==null){
                p = stk.pop();
                list.add(p.val);
                pre = p;
                p = null;
            }else{
                p = p.right;
            }
        }
        return list;
    }
```

打印二叉树的边界节点：c-p96
按照两种标准打印：
标准1：
1.头结点为边界节点
2.叶节点为边界节点
3.如果节点在其所在的层中是最左或者最右的，那么也是边界节点
标准2：
1.头结点为边界节点
2.叶节点为边界节点
3.树左边界延伸下去的路径为边界节点
4.树右边界延伸下去的路径为边界节点

标准1：
这里要求的顺序是逆时针的顺序不重复打印.关键是利用一个二维数组levelBound，把每一层的左边界和右边界都保存起来
1.先利用获取高度的方法，填充levelBound。
1.从上到下打印所有层中最左边的节点
2.打印既不是左边界，也不是右边界的叶子节点
3.倒序打印不是左边界的所有右边界


标准2：
1.从头结点开始往下寻找，找到第一个既有左孩子，又有右孩子的节点，即为h，进入步骤2，这个过程中找过的节点都打印。
2.h的左子树先进入步骤3的打印过程；h的右子树再进入步骤4的打印过程。
3.打印左边界的延伸路径以及h左子树上所有的叶子节点，详见printLeftEdge函数
4.打印右边界的延伸路径以及h右子树上所有的叶子节点，详见printLeftEdge函数


```java
//标准1

    public List printBoundary1(TreeNode root){
        ArrayList<Integer> list = new ArrayList<>();
        if(root==null) return list;
        int height = getHeight(root);
        TreeNode[][] levelBound = new TreeNode[height+1][2];
        setLevelBound(root, 1, levelBound);
        //打印所有的左边界
        for(int i=1;i<=height;i++){
            list.add(levelBound[i][0].val);
        }

        //前序打印所有的不是边界的叶子
        printLeafNotBound(root, 1, levelBound, list);
        //倒序打印所有是右边界，但不是左边界的节点。如果如果该层只有一个节点，
        //那么它既是左边界又是右边界，之前肯定在打印左边界时打印过了，就不打印
        for(int i=height;i>0;i--){
            if(levelBound[i][0]!=levelBound[i][1]){
                list.add(levelBound[i][1].val);
            }
        }

        return list;
    }

    public void printLeafNotBound(TreeNode n, int l, TreeNode[][] levelBound, List<Integer> list){
        if(n==null) return;
        if(levelBound[l][0]!=n && levelBound[l][1]!=n && n.left==null && n.right==null){
            list.add(n.val);
        }
        printLeafNotBound(n.left, l+1, levelBound, list);
        printLeafNotBound(n.right, l+1, levelBound, list);
    }

    public int getHeight(TreeNode h){
        if(h==null){
            return 0;
        }
        return 1+Math.max(getHeight(h.left), getHeight(h.right));
    }

    public void setLevelBound(TreeNode h, int l, TreeNode[][] levelBound){
        if(h==null){
            return;
        }
        //如果该层的左边界已经被设置过了，就不用管，否则左节点就是h
        levelBound[l][0] = levelBound[l][0]==null?h:levelBound[l][0];
        //一直更新该层的右边界是h，一定会被更新为最右边的节点
        levelBound[l][1] = h;
        setLevelBound(h.left, l+1, levelBound);
        setLevelBound(h.right, l+1, levelBound);
    }




ArrayList<Integer> list = new ArrayList<>();
public List printBoundary2(TreeNode root){
    if(root==null) return list;
    if(root.left==null || root.right==null){
        list.add(root.val);
        printBoundary2(root.left==null?root.right:root.left);
    }else{
        printLeftEdge(node.left, true);
        printRightEdge(node.right, true);
    }
    return list;
}

public void printLeftEdge(TreeNode node, boolean added){
    if(node==null){
        return;
    }
    //对于左边的，先访问本节点，然后再访问它的左右孩子
    if(added || (node.left==null && node.right==null)){
        list.add(node)
    }
    printLeftEdge(node.left, added);
    printLeftEdge(node.right, added && node.left==null?true:false);
}

public void printRightEdge(TreeNode node, boolean added){
    if(node==null){
        return;
    }
    //对于右边的，先访问它的左右孩子，最后再访问本节点
    printLeftEdge(node.left, added);
    printLeftEdge(node.right, added && node.left==null?true:false);
    if(added || (node.left==null && node.right==null)){
        list.add(node)
    }
}
```

直观地打印二叉树，可以直观地展示树的形状，也便于画出真实的结构：c-p100
打印二叉树顺时针旋转90°的样子，如何清晰表示一个节点的父节点呢？如果一个节点打印结果的前缀和后缀都是H，说明这个节点是根节点，不存在父节点。如果一个打印结果前缀和后缀都有v，说明父节点在当前列所在的前一列，在该节点的下方，且是离该节点最近的节点。如果一个打印结果前缀和后缀都有^，说明父节点在当前列所在的前一列，在该节点的上方，且是离该节点最近的节点。

对于节点打印时所占的统一长度必须统一，例如如果一些节点的值很短，如1，2.有的节点值很长，如323123123，那么如果不统一长度的话，一定会格式对不齐，进而产生歧义。在java中，最长的整数是Integer.MIN_VALUE（-2147483648）长度为11，加上前后缀，长度为13，为了更好区分，把前面加上两个空格，后面加上两个空格。因此，长度为17的空间一定能存放任何一个32位整数。例如，对于v8v，要在前面补7个空格，在后面补7个空格，总长度为17
即：(       v8v       ),对于v66v，要在前面补6个空格，后面补7个空格：（      v66v       ）

打印的过程结合了先右子树，再根节点，最后左子树的递归遍历过程。如果递归到一个节点，先遍历它的右子树，右子树遍历结束后再回到这个节点。如果这个节点所在的层为l，则先打印l* 17个空格，不换行，然后开始制作该节点的打印内容，这个内容包括节点的值，及前后缀字符，及补充的空格。如果该节点是其父节点的右孩子，前后缀是v，因为它必然在它父节点的上面，如果是左孩子，前后缀是^，如果是头结点，前后缀为H。打印完这个内容后换行，最后进行左子树的遍历过程

```java

    public void printBinaryTree(TreeNode n, int l, boolean isRoot, boolean isLeft){
        if(n==null) return;
        //打印右子树
        printBinaryTree(n.right, l+1, false, false);

        //确定标志位
        char flag = isLeft?'^':'v';
        flag = isRoot?'H':flag;
        //高度是多少，则空出多少17个空格
        for(int i=0;i<l;i++){
            for(int j=0;j<17;j++){
                System.out.print(' ');
            }
        }
        //组合当前节点的内容
        StringBuilder str = new StringBuilder();
        str.append(flag).append(n.val).append(flag);
        int rest = 17-str.length();
        //补前面的0
        for(int i=0;i<rest/2;i++){
            str.insert(0, ' ');
        }
        //后半部分要补的空格是总空格除以2向上取整
        rest = (int)Math.ceil((double) rest/2);
        for(int i=0;i<rest;i++){
            str.append(' ');
        }
        //打印后换行
        System.out.println(str.toString());

        //打印左子树
        printBinaryTree(n.left, l+1, false, true);
    }

```
对于树：(实在不好画图，画本子上把)

        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.right = new TreeNode(4);
        node.left.right.left = new TreeNode(7);
        node.left.right.right = new TreeNode(8);
        node.left.right.right.right = new TreeNode(11);

        node.left.right.right.right.left = new TreeNode(13);
        node.left.right.right.right.right = new TreeNode(14);



        node.right.left = new TreeNode(5);
        node.right.right = new TreeNode(6);
        node.right.left.left = new TreeNode(9);
        node.right.left.right = new TreeNode(10);
        node.right.left.left.left = new TreeNode(12);
        node.right.left.left.left.left = new TreeNode(15);
        node.right.left.left.left.right = new TreeNode(16);


打印结果如下：

                                         v6v       
                        v3v       
                                                         v10v      
                                         ^5^       
                                                          ^9^       
                                                                                           v16v      
                                                                          ^12^      
                                                                                           ^15^      
       H1H       
                                                                                           v14v      
                                                                          v11v      
                                                                                           ^13^      
                                                          v8v       
                                         v4v       
                                                          ^7^       
                        ^2^       



二叉树的序列化与反序列化：c-p103

不用又保存前序又保存中序，只保存前序即可，但要保存非空节点的空节点孩子
前序：
序列化，把二叉树的前序序列放在一个String中，空节点用#表示,节点之间用,隔开
反序列化，先把序列放入队列中，用这个队列作为参数，按照前序遍历的方法，去构建树。因为序列化时是前序，此时也按照前序构建，并且保存了空孩子，不会引起歧义

```java
    public String serialize(TreeNode root){
        if(root==null){
            return "#,";
        }
        String res = root.val + ",";
        res += serialize(root.left);
        res += serialize(root.right);
        return res;
    }


    public TreeNode deserialize(String str){
        String[] strs = str.split(",");
        LinkedList<String> queue = new LinkedList<>();
        for(int i=0;i<strs.length;i++){
            queue.offer(strs[i]);
        }
        return reconPreOrder(queue);

    }

    public TreeNode reconPreOrder(Queue<String> queue){
        String value = queue.poll();
        if("#".equals(value)){
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(value));
        node.left = reconPreOrder(queue);
        node.right = reconPreOrder(queue);
        return node;
    }

```

层序遍历也类似，核心还是要保存非空节点的空子节点
序列化：先用层序遍历把节点序列化成字符串
反序列化：用层序遍历的方式构建节点。

```java
    public String serialize(TreeNode root){
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        StringBuilder str = new StringBuilder();
        while(!queue.isEmpty()){
            TreeNode p = queue.poll();
            if(p==null){
                str.append('#').append(',');
                continue;
            }else{
                str.append(p.val).append(',');
            }
            queue.offer(p.left);
            queue.offer(p.right);
        }
        return str.toString();

    }


    public TreeNode deserialize(String str){
        String[] strs = str.split(",");
        LinkedList<TreeNode> queue = new LinkedList<>();
        TreeNode root = buildNode(str[0]);
        queue.offer(root);
        int i=1;
        while(!queue.isEmpty()){
            TreeNode p = queue.poll();
            if(p==null){
                continue;
            }else{
                p.left = buildNode(str[i++]);
                p.right = buildNode(str[i++]);
            }
            queue.offer(p.left);
            queue.offer(p.right);
        }
        return root;
    }

    public TreeNode buildNode(String str){
        if("#".equals(str)){
            return null;
        }
        return new TreeNode(Integer.valueOf(str));
    }

```




遍历二叉树的神级方法：c-p107

之前的递归和非递归的的空间复杂度都是O(h),h是二叉树的高度。如果完全不用栈结构能完成三种遍历吗？
答案是可以，使用二叉树节点中大量指向的null指针。本题实际上就是Morries遍历

首先看普通的递归和非递归解法，其实都使用了栈结构，在处理完二叉树某个节点后可以回到上层取。为什么从下层回到上层如此之难？因为二叉树的结构如此，每个节点都有指向孩子节点的指针，而没有指向父节点的指针，所以从上到下易，从下到上难。

Morris遍历实际上就是避免使用栈结构，而是让下层到上层有指针，具体是通过底层节点指向null的空闲指针指回上层的某个节点，从而完成下层到上次的移动

首先看中序：
1.假设当前子树的头结点为h，让h的左子树中最右节点的right指针指向h，然后的左子树继续步骤1的处理过程，直到遇到某个节点没有左子树时记为node。
2.从node开始通过每个节点的right指针进行移动，并依次打印，假设移动到的节点为cur。对于每一个cur节点都判断cur节点的左子树中最右节点是否指向cur
    ①如果是，让cur节点的左子树中最右节点的right指针指向空，也就是把步骤1的调整后再逐渐调整回来，然后打印cur，继续通过cur的right指针移动到下一个节点，重复步骤2
    ②如果不是，以cur为头的子树重回步骤1执行。
3.步骤2最终会移动到null，整个过程结束

用一个二叉树手动模拟一下算法，会发现实现很简单
1.如果当前子树为空，返回
  否则假设当前子树的头结点为h，从h的左子树一直往右找，找到右孩子为空，或右孩子为h的节点，记为node
    ①如果node的右孩子为空，则让node的右孩子等于h,且以h的左子树为当前子树，返回步骤1
    ②如果node的右孩子等于h，则令node的右孩子为空，进入步骤2
2.打印h，并且以h的右子树为当前子树，返回步骤1


```java
public void morrisIn(TreeNode root){
    if(root==null)
        return;
    TreeNode cur1 = root;
    TreeNode cur2 = null;
    while(cur1!=null){
        cur2 = cur1.left;
        if(cur2!=null){
            //不断找到cur2最右边的节点，如果已经指定了
            while(cur2.right!=null && cur2.right!=cur1){
                cur2 = cur2.right;
            }
            //让cur1左子树，即以cur2为根的树最右边的节点为cur1
            //且cur1成为cur1的左子树
            //到了这里，要么cur2的右孩子是空（说明还没设置）
            //要么cur2的右孩子就是cur1（已经设置了）
            if(cur2.right==null){
                cur2.right = cur1;
                cur1 = cur1.left;
                continue;
            }else{
                //如果已经设置了
                cur2.right=null;
            }

        }
        System.out.print(cur1.val + " ");
        cur1 = cur1.right;
    }
    System.out.println();
}
```

Morris前序遍历的实现就是Morris中序遍历实现的简单改写，只是修改打印的时机，打印时机放在步骤1发生的时候，正在处理以h为头的子树，且是以h为头的子树首次进入调整过程，则直接打印h


```java
public void morrisPre(TreeNode root){
    if(root==null)
        return;
    TreeNode cur1 = root;
    TreeNode cur2 = null;
    while(cur1!=null){
        cur2 = cur1.left;
        if(cur2!=null){
            while(cur2.right!=null && cur2.right!=cur1){
                cur2 = cur2.right;
            }

            if(cur2.right==null){
                cur2.right = cur1;
                System.out.print(cur1.val + " ");
                cur1 = cur1.left;
                continue;
            }else{
                cur2.right=null;
            }
        }else{
            System.out.print(cur1.val + " ");
        }
        
        cur1 = cur1.right;
    }
    System.out.println();
}
```

Morris后序遍历也是Morris中序遍历的改写，但包含调整过程，简单来说，就是依次逆序打印所有节点的左子树的右边界。（最好看代码，这个描述太抽象）

可以看到Morris遍历的前中后序的总体结构是一样的，只是打印时机不同，而且后序加了个调整。

```java
public void morrisPost(TreeNode root){
    if(root==null)
        return;
    TreeNode cur1 = root;
    TreeNode cur2 = null;
    while(cur1!=null){
        cur2 = cur1.left;
        if(cur2!=null){
            while(cur2.right!=null && cur2.right!=cur1){
                cur2 = cur2.right;
            }

            if(cur2.right==null){
                cur2.right = cur1;
                cur1 = cur1.left;
                continue;
            }else{
                cur2.right=null;
                printRightEdge(cur1.left);
            }
        }
        cur1 = cur1.right;
    }
    printRightEdge(root);
    System.out.println();
}

//打印以root为根的子树的右边界
public void printRightEdge(TreeNode root){
    //打印倒序，则可以先翻转过来，然后打印完了再翻转过去
    TreeNode tail = reverseRightEdge(root);
    TreeNode cur = tail;
    while(cur!=null){
        System.out.print(cur.val + " ");
        cur = cur.right;
    }
    reverseRightEdge(tail);
}

//让 n1.right=n2, n2.right=n3  变成 n3.right=n2, n2.right=n1 就像是单链表的翻转一样。
public TreeNode reverseRightEdge(TreeNode from){
    TreeNode dummy = new TreeNode(0);
    dummy.right = null;
    TreeNode cur = from;
    while(cur!=null){
        TreeNode tmp = cur.right;
        cur.right = dummy.right;
        dummy.right = cur;
        cur = tmp;
    }
    return dummy.right;
}


```


在二叉树中找到累加和为指定值的最长路径长度：c-p115
树中可能出现负值
求累加和为sum的最长路径长度，路径是指从某个节点往下，每次最多选择一个孩子节点或不选所成的节点链。用递归方法求解，能解出，但时间复杂度太高（见437）
本解法是可以做到tO(N), sO(h)

具体：
1.二叉树头结点head和规定值sum已知，生成变量maxLen，记录累加和等于sum的最长路径长度。
2.生成哈希表sumMap，记录从head开始的一条路径上的累加和的出现情况。累加和从head的值开始累加，sumMap的key代表某个累加和，value代表这个累加和在路径中最早出现的层数
3.首先在sumMap中加入一个记录(0,0)（这一步很重要！！这是起始情况），它表示累加和0不用包括任何节点就可以得到。然后按照二叉树的先序遍历方式遍历节点，遍历到的当前节点记为cur，从head到cur父节点的累加和记为preSum，cur所在的层数记为level。将cur.value+preSum的值记为curSum，就是从head到cur的累加和，如果sumMap中已经包含了curSum的记录，说明curSum在上层中已经出现过，那么就不更新sumMap；否则把curSum放入。如果map中含有curSum-sum的key，说明从head到当前节点的路径上，有子路径的和是sum，该子路径的长是（当前层数-（curSum-sum所在的层数）），让它和当前maxlen中的大值赋给maxlen。
4.递归遍历当前节点的左右子树
5.如果curSum的值是当前层高，则从sumMap中删除curSum-curlevel，为了让上层的另一个分支不受当前分支的干扰.


其本质实际上是一种trackback，每次sumMap中只保存head到当前节点的路径上出现过的子路径的所有和值，并且相同的和下只保存head到更上面的节点的路径和，这是为了让下面满足条件的子路径计算时，能得到更大的子路径长，如：
路径1-2的和是10
路径1-2-3-4 的和也是 10
路径1-2-3-4-5-6的和是15， 要求的sum是5，所以更长的子路径是3-4-5-6，因此map保存的就应该是节点2的层数


```java
    public int getLongestSumPath(TreeNode root, int sum){
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,0);
        return preOrder(root, 1, 0, 0, sum ,map);
    }

    public int preOrder(TreeNode root, int level, int maxLen, int preSum, int sum, HashMap<Integer, Integer> map){
        if(root==null) return maxLen;
        int curSum = preSum + root.val;
        if(!map.containsKey(curSum)){
            map.put(curSum, level);
        }
        if(map.containsKey(curSum - sum)){
            maxLen = Math.max(level - map.get(curSum - sum), maxLen);
        }
        maxLen = Math.max(maxLen, preOrder(root.left, level+1, maxLen, curSum, sum, map));
        maxLen = Math.max(maxLen, preOrder(root.right, level+1, maxLen, curSum, sum, map));
        if(map.get(curSum)==level){
            map.remove(curSum);
        }
        return maxLen;
    }

```

找到二叉树中的最大搜索二叉子树：c-p117
给一棵二叉树，树中的节点值都不一样
搜索二叉子树的叶子必须是原二叉树的叶子，即不能从中间截出一块来，只能从下往上取
显然可以使用递归法方法，方法返回的是以本节点为根是否能构成一个二叉排序树。如果节点是null，可以视其为二叉排序树，返回true。对一个节点，如果它的左右子树都不是二叉排序树，那么它一定不是二叉排序树，返回false。若左子树是，且其最大值节点小于本节点值，则说明根值和左子树可构成搜索树；若右子树是，且其最小值节点大于本节点值，则说明根值和右子树可构成搜索树。将能构成的二叉排序树的中序序列返回给上层（作为参数），函数返回true。

```java

    TreeNode ansRoot = null;
    int maxNode = 0;

    public TreeNode getMaxSearchChildBinaryTree(TreeNode root){
        isBinaryTree(root, new ArrayList<Integer>());
        return ansRoot;
    }

    //list中存放以root为根能构成的搜索树的中序遍历序列，如果不能构成，则其为空
    public boolean isBinaryTree(TreeNode root, List<Integer> list){
        if(root==null) return true;
        ArrayList<Integer> leftList = new ArrayList<>();
        ArrayList<Integer> rightList = new ArrayList<>();
        boolean isLeft = isBinaryTree(root.left, leftList);
        boolean isRight = isBinaryTree(root.right, rightList);

        //左右子树都不是搜索树，则本树一定不是
        if(!isLeft && !isRight){
            return false;
        }
        //若左子树是，且其最大值节点小于本根值，则说明根值和左子树可构成搜索树
        if(isLeft && (leftList.size()==0 ||  leftList.get(leftList.size()-1)<root.val)){
            list.addAll(leftList);
        }
        list.add(root.val);
        //若右子树是，且其最小值节点大于本根值，则说明根值和右子树可构成搜索树
        if(isRight && (rightList.size()==0 ||  rightList.get(0)>root.val)){
            list.addAll(rightList);
        }


        int nodeCnt = list.size();
        if(maxNode<nodeCnt){
            ansRoot = root;
            maxNode = nodeCnt;
        }
        return true;
    }

```



找到二叉树中符合二叉排序树条件的最大拓扑结构：c-p119
给一棵二叉树，树中的节点值都不一样，这道题搜索二叉树就可以从中间截出来一块了（即子树的叶子可以不是原二叉树的叶子），此时返回的是符合二叉排序树的子结构的大小
修改一下上面的方法，

先看一种简单解法，对于每一个节点，都搜索以它为根的最大二叉搜索树拓扑结构，往下添加节点，必须要当前遍历到的节点符合上下限要求才能添加，直到无法再往下走就返回。整个左子树都要小于根值，整个右子树都要大于根值。 一开始的时候，上下限分别是MAX和MIN，代表没有上下限，往下传递时才会补充上下限
时间复杂度为O(N^2)

```java

    public int getMaxSearchChildNodes(TreeNode root){
        if(root==null) return 0;
        int ans = addNode(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        ans = Math.max(ans, getMaxSearchChildNodes(root.left));
        ans = Math.max(ans, getMaxSearchChildNodes(root.right));
        return ans;
    }

    public int addNode(TreeNode node, int lower, int upper){
        if(node==null) return 0;
        int val = node.val;
        int cnt = 0;
        if(val>lower && val<upper){
            cnt=1;
        }else{
            return 0;
        }
        cnt += addNode(node.left, lower, Math.min(upper, val));
        cnt += addNode(node.right, Math.max(val, lower), upper);
        return cnt;

    }
```

## 下面方法有点难以理解，暂不考虑
利用拓扑贡献记录：每个节点旁边有被括号括起来的两个值，称为节点对当前头结点的拓扑贡献记录
第一个值代表节点的左子树可以为当前头结点的拓扑贡献几个节点；第二个值代表节点的右子树可以为当前头结点的拓扑贡献几个节点；
              10(3,3)
      4(1,1)           14(1,1)
2(0,0)    5(0,0)  11(0,0)    15(0,0)
例如4(1,1) 代表4的左子树为节点10为头的拓扑结构贡献1个节点，4的右子树为节点10为头的拓扑结构贡献1个节点

如       13(0,1)
    20(0,0)   16(0,0)

13的左子树为13为头的拓扑结构贡献0个节点；13的右子树为13为头的拓扑结构贡献1个节点；

如果得到了h左右孩子为头的拓扑贡献记录，则可以快速得到以h为头的拓扑贡献记录





二叉树的按层打印与zigzag打印：c-p129
按层打印，非常简单，层序遍历即可，不同点是要标注level，也很简单，多加一个变量表示层数。用两个队列很方便，用一个队列的就加两个变量，一个代表本层剩下的节点数，一个代表下一层的节点数，

zigzag也很简单，两个队列和一个队列的方法都可以，用一个list存储节点，奇数层顺序输出，偶数层倒序输出即可。更好的方法是不使用list（因为list如果扩容的话，时间复杂度会上升），而使用双端队列，奇数层顺着出，偶数层倒着出

```java
//层序
    public void levelOrder(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        ArrayList<Integer> list = new ArrayList<>();
        int cnt = 1;
        int nextlevel = 0;
        int level=1;
        while(!queue.isEmpty()){
            TreeNode p = queue.poll();
            list.add(p.val);
            if(p.left!=null){
                queue.offer(p.left);
                nextlevel++;
            }
            if(p.right!=null){
                queue.offer(p.right);
                nextlevel++;
            }
            cnt--;
            if(cnt==0){
                cnt = nextlevel;
                nextlevel = 0;
                System.out.print("Level "+level+ " : ");
                for(int i=0;i<list.size();i++){
                    if(i==list.size()-1){
                        System.out.print(list.get(i));
                    }else{
                        System.out.print(list.get(i) + " ");
                    }
                }
                level++;
                list.clear();
                System.out.println();
            }

        }
    }

//zigzag
    public void zigzagOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        LinkedList<Integer> list = new LinkedList<>();
        int cnt = 1;
        int nextlevel = 0;
        int level = 1;
        while (!queue.isEmpty()) {
            TreeNode p = queue.poll();
            list.offer(p.val);
            if (p.left != null) {
                queue.offer(p.left);
                nextlevel++;
            }
            if (p.right != null) {
                queue.offer(p.right);
                nextlevel++;
            }
            cnt--;
            if (cnt == 0) {
                cnt = nextlevel;
                nextlevel = 0;
                System.out.print("Level " + level + " : ");
                while (!list.isEmpty()) {
                    int e = ((level&1)!=0)?list.pollFirst():list.pollLast();
                    if (list.isEmpty()) {
                        System.out.println(e);
                    } else {
                        System.out.print(e + " ");
                    }
                }
                level++;
                list.clear();
            }
        }
    }

```

调整搜索二叉排序树中两个错误的节点，并且在要完全交换两个节点的位置（而不只是交换值）：c-p134

这个题是题目99的改进版，要求交换两个节点，不光是交换值

二叉树的中序遍历是升序的，因此在中序遍历的过程中，一定会发生降序，中序遍历时节点发生降序，第一个错误的节点是第一次降序时较大的节点，第二个错误的节点是降序时较小的节点。可以用中序序列把节点全部放进list（99题），但没必要，首先要中序遍历一遍，找到两个错误的节点并保存，然后再中序遍历一遍，找到他俩的父节点，然后先交换彼此的父节点(即交换2个父节点的相应域)，再交换彼此的即可。（先交换父节点，再交换子节点，即使两个错误节点有父子关系，最终结果也是保证正确的）

需要注意的是，找第二个错误节点时，先用第一个错误节点的后继赋予它，然后接下来如果还有错误节点，则再次赋给它，这样做的目的是怕两个错误节点连在一起时，找不到第二个错误节点：

如： 1 2 3 6 5 4   遍历到5时，令error1=6（即5的pre），也令error2=5，接着遍历到4时，再令error=4

如 1 2 3 5 4 6
遍历到4时，令error1=5（即4的pre），也令error2=4，接着遍历就没有降序了。如果此时遍历到4时，只令error1=5。那么error2将找不到了



```java

    public void recoverTree(TreeNode root){
        TreeNode pre = null;
        TreeNode error1 = null;
        TreeNode error2 = null;
        Stack<TreeNode> stk = new Stack<>();
        TreeNode p = root;
        //第一次遍历，找到俩错误节点
        while(p!=null || !stk.isEmpty()){
            if(p!=null){
                stk.push(p);
                p = p.left;
            }else{
                p = stk.pop();
                if(pre!=null && pre.val>p.val){
                    if(error1==null){
                        error1=pre;
                    }
                    //注意，这里有点特殊，如果两个错误的顶点不挨着，则error2先赋第一次错误时的小值，
                    //再赋第二次错误时的小值，其值没问题
                    //如果两个顶点挨着，则error2先赋第一次错误时的小值，接下来不会再被赋值了，但error2实际就是这个值
                    error2 = p;

                }
                pre = p;
                p = p.right;
            }
        }


        p = root;
        pre = null;
        TreeNode error1Pre = null;
        TreeNode error2Pre = null;
        stk.clear();
        //第二次遍历，找到两个父节点，注意父节点不是遍历时的上一个节点！可别弄混了
        while(p!=null || !stk.isEmpty()){
            if(p!=null){
                stk.push(p);
                p = p.left;
            }else{
                p = stk.pop();
                if(p.left==error1 || p.right==error1){
                    error1Pre = p;
                }else if(p.left==error2 || p.right==error2){
                    error2Pre = p;
                }
                pre = p;
                p = p.right;
            }
        }

        TreeNode tmpleft = null;
        TreeNode tmpright = null;

        //交换到父节点合适的分支上
        if(error1Pre!=null){
            if(error1Pre.left==error1){
                error1Pre.left = error2;
            }else{
                error1Pre.right = error2;
            }
        }
        if(error2Pre!=null){
            if(error2Pre.left==error2){
                error2Pre.left = error1;
            }else{
                error2Pre.right = error1;
            }
        }
        //交换孩子节点
        tmpleft = error1.left;
        tmpright = error1.right;
        error1.left = error2.left;
        error1.right = error2.right;
        error2.left = tmpleft;
        error2.right = tmpright;
    }

```




二叉树t1是否包含t2树全部的拓扑结构：c-p140
依次遍历t1所有的节点，是否包含t2形状的拓扑

```java
public boolean isContain(TreeNode h1, TreeNode h2){
    if(h2==null) return true;
    else if(h1==null) return false;
    return isInclude(h1,h2) || isContain(h1.left, h2) || isContain(h1.right, h2);

}

public boolean isInclude(TreeNode h1, TreeNode h2){
    //只要h2是空，h1不是空也可
    if(h2==null){
        return true;
    }else if(h1==null){
        //h1为null， h2不为空
        return false;
    }
    return h1.val==h2.val && isInclude(h1.left, h2.left) && isInclude(h1.right, h2.right);
}


```

二叉树t1是否包含t2一样的子树：c-p141
依次遍历t1所有的节点，是否包含t2形状的子树，上一题是h2的节点为空时，不论如何都满足条件，这时h2为空h1也必须为空。
```java
public boolean isSubtree(TreeNode h1, TreeNode h2){
    if(h2==null && h1==null) return true;
    else if(h2==null || h1==null) return false;
    return isInclude(h1,h2) || isInclude(h1.left, h2) || isInclude(h1.right, h2);

}

public boolean isInclude(TreeNode h1, TreeNode h2){
    if(h2==null && h1==null) return true;
    else if(h2==null || h1==null) return false;

    return h1.val==h2.val && isInclude(h1.left, h2.left) && isInclude(h1.right, h2.right);
}


```

判断二叉树是否为二叉平衡树：c-p144
左右孩子的高度差不超过1

整体解法是后序遍历，先遍历node的左子树，node的左子树是否为平衡二叉树，如果发现不是二叉平衡树，直接返回；如果是，则再看右子树，程序是一样的。如果左右子树都是平衡树，就看l和r差的绝对值是否大于1，如果是则说明不是二叉平衡树，否则就是返回l和r较大的那一个。使用一个boolean的数组保持遍历过程中每个子树是否是平衡的，如果发现不平衡的，设置boolean数组为false，且直接返回，此时返回什么不重要。boolean数组其功能相当于一个全局变量

```java
public is isBalanced(TreeNode h){
    if(h==null) return true;
    boolean[] res = new boolean[1];
    res[0] = true;
    getHeight(h, 1, res);
    return res[0];
}

public int getHeight(TreeNode h, int level, boolean[] res){
    if(h==null) return level;
    int lh=0,rh=0;

    lh = getHeight(h.left, level+1, res);
    if(!res[0])
        return level;

    rh = getHeight(h.right, level+1, res);
    if(!res[0])
        return level;

    if(Math.abs(rh-lh)>1){
        res[0] = false;
        return level;
    }
    level = Math.max(rh, lh);
    return level;
}
```

给定一个整型数组，已知没有重复值，判断arr是否可能是节点值为整型的二叉搜索树后序遍历的结果，如果是，则重构该树：c-p145

判断：c-p145.1
最后一个元素是根，分离出左子树和右子树，左子树是从头到第一个大于根的元素。如果右子树中有小于根的，直接返回false。否则递归判断左子树和右子树是否符合。

构建：c-p145.2
构建更简单，因为确定是搜索树，所以只需要分离出左右子树即可，然后分别递归构建左右子树

```java
public boolean isPostOrder(int[] arr){
    if(arr==null || arr.length==0) return true;
    return isPostOrder(arr, 0, arr.length-1);
}

public boolean isPostOrder(int[] arr, int start, int end){
    if(start>=end) return true;
    int root = arr[end];
    int rightStart=start;
    for(;rightStart<end ;rightStart++){
        if(arr[rightStart]>root) break;
    }

    for(int i=rightStart;i<end;i++){
        if(arr[i]<root)
            return false;
    }

    return isPostOrder(int[] arr, start, rightStart-1) && isPostOrder(int[] arr, rightStart, end);

}


public TreeNode buildByPostOrder(int[] arr, int start, int end){
    if(start>end) return null;

    int rootVal = arr[end];
    TreeNode root = new TreeNode(rootVal);
    int rightStart=start;
    for(;rightStart<end ;rightStart++){
        if(arr[rightStart]>rootVal) break;
    }

    root.left = buildByPostOrder(int[] arr, start, rightStart-1)
    root.right = buildByPostOrder(int[] arr, rightStart, end);
    return root;

}
```

判断一棵二叉树是否为二叉搜索树和完全二叉树：c-p147

二叉搜索树(BST)：c-p147.1

由于二叉搜索树的中序一定是正序的，所以中序遍历即可，用一个单元素数组作为全局变量表示上次遍历的值，如果发现了逆序，说明不是，直接返回即可

```java
    public boolean isValidBST(TreeNode h){
        long[] pre = new long[]{Long.MIN_VALUE};
        return inOrder(h, pre);
    }

    public boolean inOrder(TreeNode h, long[] pre){
        if(h==null) return true;
        if(!inOrder(h.left, pre)){
            return false;
        }
        int rootVal = h.val;
        if(pre[0]>=rootVal){
            return false;
        }else{
            pre[0] = rootVal;
        }

        if(!inOrder(h.right, pre)){
            return false;
        }

        return true;
    }
```

判断是否是完全二叉树(CBT)：c-p147.2

层序遍历二叉树，如果当前节点没有左孩子但有右孩子，直接返回false
如果当前节点并不是左右孩子都有，则之后遍历到的节点必须都是叶子，否则返回false
如果遍历过程中不返回false，则最后返回true


```java
public boolean isCBT(TreeNode h){
    LinkedList<TreeNode> queue = new LinkedList<>();
    queue.offer(h);
    boolean leaf = false;
    while(!queue.isEmpty()){
        TreeNode p = queue.poll();
        if(leaf && (p.left!=null || p.right!=null)){
            return false;
        }

        if(p.left==null){
            if(p.right!=null) return false;
            leaf = true;
        }
        if(p.right==null){
            leaf=true;
        }
        if(p.left!=null)
            queue.offer(p.left);
        if(p.right!=null)
            queue.offer(p.right);
    }
    return true;


}

```

通过有序数组生成平衡搜索二叉树：c-p150
给一个有序数组，没有重复值，生成平衡二叉搜索树

每次都取 start-end中间的值作为根值，两边分别为左子树和右子树，递归生成
```java
public TreeNode generateBalancedBST(int[] arr){
    return generateBST(arr, 0, arr.length-1);
}

public TreeNode generateBalancedBST(int[] arr, int start, int end){
    if(start>end) return null;
    int mid = (start+end)>>1;
    int rootVal = arr[mid];
    TreeNode root = new TreeNode(rootVal);
    root.left = generateBST(arr, start, mid-1);
    root.right = generateBST(arr, mid+1, end);
    return root;
}
```

先序，中序，后序数组两两结合重构二叉树：c-p171

先序，后序分别和中序组合重构比较容易，核心就是根据先序或后序找出根节点的值，然后在中序中找到根节点的位置，借此可以区分出左子树和右子树，进而可以递归构建

用先序和后序遍历构建二叉树（节点值都不同），
    1、首先要分析出一般的二叉树即使有正确的先序和后续数组，大多数情况下也不能通过这两个数组构建原来的树，因为很多结构不同的树拥有一样的先序和后序，如，根节点是1，左孩子为2，右孩子为null，则先序是[1,2],后序是[2,1]；根节点是1，左孩子是null，右孩子是2，这样的结果也是先序是[1,2],后序是[2,1]。
    2、然后要分析如果一棵二叉树除了叶节点之外，其他所有节点都有左孩子和右孩子（即每个节点的度只为0或为2，因为先序和后序无法构造的原因就是如果一个节点的左孩子为空右子树为X，和其左子树为X右子树为空无法分辨，既然有子树的两边子树都不为空，就能分辨出了）

既然两边子树都不为空，则对于前序遍历来说，如果不是只有一个值，那么，根后面的那个一定是左孩子

```java
    public TreeNode prePosToTree(int[] pre, int[] pos){
        if(pre==null || pos==null) return null;
        return buildTree(pre, 0, pre.length-1, pos, 0, pos.length-1);
    }

    public TreeNode buildTree(int[] pre, int preStart, int preEnd, int[] pos, int posStart, int posEnd){
        if(preStart>preEnd) return null;
        TreeNode root = new TreeNode(pre[preStart]);
        if(preStart==preEnd) return root;
        int leftVal = pre[preStart+1];
        int posLeftIndex = posStart;
        for(int i=posStart;i<=posEnd;i++){
            if(pos[i]==leftVal){
                posLeftIndex = i;
                break;
            }
        }
        int leftCount = (posLeftIndex-posStart)+1;
        root.left = buildTree(pre, preStart+1, leftCount+preStart, pos, posStart, posStart+leftCount-1);
        root.right = buildTree(pre, leftCount+preStart+1, preEnd, pos, posStart+leftCount, posEnd);
        return root;

    }
```


通过前序和中序数组，不通过重建整棵树生成后序数组：c-p174

虽然不生成树，但核心思想还是一致的，用一个全局的list来保存后序数组，利用前序和中序先定位到根节点，然后递归地先把左子树和右子树加入到list中，最后再把根节点加入到list中

```java
    public int[] prePosToTree(int[] pre, int[] mid){
        if(pre==null || mid==null) return null;
        ArrayList<Integer> list = new ArrayList<>();
        buildTree(pre, 0, pre.length-1, mid, 0, mid.length-1, list);
        int[] ans = new int[pre.length];
        for (int i=0;i<pre.length;i++){
            ans[i] = list.get(i);
        }
        return ans;
    }

    public void buildTree(int[] pre, int preStart, int preEnd, int[] mid, int midStart, int midEnd, ArrayList<Integer> list){
        if(preStart>preEnd) return;
        int rootVal = pre[preStart];
        if(preStart==preEnd) {
            list.add(rootVal);
            return;
        }

        int rootMidIndex = midStart;
        for(int i=midStart;i<=midEnd;i++){
            if(mid[i]==rootVal){
                rootMidIndex = i;
                break;
            }
        }
        int leftCount = rootMidIndex-midStart;

        buildTree(pre, preStart+1, leftCount+preStart, mid, midStart, rootMidIndex-1, list);
        buildTree(pre, leftCount+preStart+1, preEnd, mid, rootMidIndex, midEnd, list);
        list.add(rootVal);
    }
```

给定一个整数N，如果N<1代表空树，否则代表中序遍历的结果为{1,2,3...N}，返回可能的二叉树结构有多少种：c-p175
也就是是生成的必须是二叉排序树

dp[i]代表从1到n能生成的二叉排序树的个数
dp[0]=1 设定该值，方便计算
dp[1]=1
dp[2]=2
dp[3]=dp[2]+dp[1]* dp[1] + dp[2]  (以1为根，以2为根，以3为根)
dp[4]=dp[3] + (dp[1]* dp[2]) + (dp[2] * dp[1]) + dp[3]
...
dp[n] = Σdp[i]* dp[n-1-i]  (i从0到n-1)


```java
public int numTrees(int n){
    if(n<=1) return 1;
    int[] dp = new int[n+1];
    dp[0]=1;
    dp[1]=1;
    dp[2]=2;
    for(int i=3;i<=n;i++){
        for(int j=0;j<i;j++){
            dp[i] += dp[j]*dp[i-1-j];
        }
    }
    return dp[n];
}
```

生成这些树，其实还就是每个值都当根节点，然后递归生成子树
```java
public List<TreeNode> generateTree(int n){
    if(n<=0)
        return new ArrayList<>();
    return generateTree(1, n);
}

public List<TreeNode> generateTree(int start, int end){
    ArrayList<TreeNode> list = new ArrayList<>();
    if(start>end){
        list.add(null);
        return list;
    }else if(start==end){
        list.add(new TreeNode(start));
        return list;
    }

    for(int i=start; i<=end;i++){
        ArrayList<TreeNode> leftList = generateTree(start, i-1);
        ArrayList<TreeNode> rightList = generateTree(i+1, end);
        for(int m=0;m<leftList.size();m++){
            for(int n=0;n<rightList.size();n++){
                TreeNode tmp = new TreeNode(i);
                tmp.left = leftList.get(m);
                tmp.right = rightList.get(n);
                list.add(tmp);
            }
        }
    }
    return list;
}

```

给定一棵完全二叉树的头结点head，返回这棵树的节点个数：c-p178
对于完全二叉树，如果右子树的高度比总高度h少1，说明最后的叶子一定在右子树上，那么左子树一定是高为h-1的满二叉树。右子树是一个普通的完全二叉树。
如果右子树的高度比总高度小2，那么说明最后的叶子一定在左子树上，那么右子树一定是高为h-2的满二叉树，左子树是一个普通的完全二叉树。

满二叉树节点数 = 2^h-1

而且对于完全二叉树，左子树的高度一定等于总高度-1.

复杂度是O(logN)

```java
    HashMap<TreeNode, Integer> map = new HashMap<>();
    public int countNodes(TreeNode h){
        int height = getHeight(h);
        if(height==1) return 1;
        else if(height==0) return 0;
        return 1 + ((getHeight(h.right)==height-1)?( (1<<(height-1))-1 + countNodes(h.right)):( (1<<(height-2))-1 + countNodes(h.left)));
    }

    public int getHeight(TreeNode h){
        if(map.containsKey(h)) return map.get(h);
        int res = h==null?0:1+getHeight(h.left);
        map.put(h,res);
        return res;
    }
```


求斐波那契数列的第N项：c-p181.1

f(1)=1;
f(2)=1;
f(n) = f(n-1)+f(n-2);

给定整数N，代表台阶数，一次可以跨2个台阶或1个，共有多少种走法：c-p181.2

dp[1]=1;
dp[2]=2;

dp[n]=dp[n-2]+dp[n-1]


假设弄成中成熟的母牛每年生1头小母牛，且永远不会死，第一年农场有1头成熟的母牛，第二年开始，母牛开始生小母牛，每只小母牛3年后成熟又可以生小母牛，给定整数N，求出N年后母牛的数量：c-p181.3

对于第N年，所有3年前的牛在今年都可以生一头母牛，并且牛总数要加上两年前新出生的牛和一年前新出生的牛
dp[1]=1;
dp[2]=2;
dp[3]=3
dp[4] = dp[4-3] * 2 + dp[3]-dp[2] + dp[2]-dp[1];
dp[n] = dp[n-3] * 2 + dp[n-2]-dp[n-3] + dp[n-1]-dp[n-2];

```java
    public int getCowCount(int n){
        if(n<=3){
            if(n==1) return 1;
            else if(n==2) return 2;
            else return 3;
        }
        int[] dp = new int[n+1];
        dp[1]=1;
        dp[2]=2;
        dp[3]=3;
        for(int i=4;i<=n;i++){
            dp[i] = dp[i-3]*2 + dp[i-2]-dp[i-3]+dp[i-1]-dp[i-2];
        }
        return dp[n];
    }
```


在二叉树中找到一个节点的后继节点：c-p151
有一种新的二叉树节点，多了一个指向父节点的parent域，头结点的parent指向null，现给一个树中的某个节点node，实现返回node中序遍历后继节点的函数。

中序遍历：左-根-右
如果node有右子树，则找到右子树最左边的节点即可
如果node没有右子树：
    若node是它父亲的左孩子，则后继就是它父亲
    若node是它父亲的右孩子，则往上找直到某个祖先节点是其父节点的左孩子，则后继就是这个祖先节点的父节点（祖祖先）

```java
public TreeNode getNext(TreeNode node){
    if(node==null) return null;
    if(node.right!=null){
        TreeNode p = node.right;
        while(p.left!=null){
            p = p.left;
        }
        return p;
    }else if(node.parent==null){
        return null;
    }else if(node.parent.left==node){
        return node.parent
    }else{
        TreeNode p = node.parent;
        while(p.parent!=null && p.parent.left==p){
            p = p.parent;
        }
        //这里不论是null或不是null都可
        return p.parent;
    }
}
```

给定一棵二叉树的头结点，以及这棵树中两个节点o1和o2，返回o1，o2的最近公共节点：c-p153

## 当用后序遍历到某个节点时，栈中的元素是该节点的祖先链
问题1.本问题
所以用非递归的后序遍历，用两次后序遍历分别存储o1，o2的祖先链，这里用LinkedList，因为它既能当栈又能当队列。当两个祖先链都获得后，按照尾端出队的方式出两个祖先链，最后一个相同的节点就是最近公共节点

问题2.如果查询两个节点的最近公共祖先操作十分频繁，想办法让单条查询的查询时间减少。
对于每个节点，把其祖先链存放在map中，即HashMap<TreeNode, LinkeList>，每当要查找时，先看map中有无该节点的祖先链，如果有的话就不用再遍历了。
把所有的节点存储在HashSet中，对树进行后续遍历，当遍历到一个节点在HashSet中时，就保存其祖先链，当一遍遍历完后，所有需要查询的节点的祖先链就都保存起来了。


```java

    public TreeNode findLastCommonAncestor(TreeNode h, TreeNode o1, TreeNode o2){
        LinkedList<TreeNode> list1 = getAncestorChain(h, o1);
        LinkedList<TreeNode> list2 = getAncestorChain(h, o2);

        TreeNode pre = null;
        while(!list1.isEmpty() && !list2.isEmpty()){
            TreeNode p1 = list1.pollLast();
            TreeNode p2 = list2.pollLast();
            if(p1!=p2){
                break;
            }
            pre = p1;
        }
        return pre;

    }

    public LinkedList<TreeNode> getAncestorChain(TreeNode h, TreeNode t){
        LinkedList<TreeNode> list = new LinkedList<>();
        TreeNode p = h;
        TreeNode pre = null;
        while(p!=null || !list.isEmpty()){
            while(p!=null){
                list.push(p);
                p = p.left;
            }
            TreeNode tmp = list.peek();
            if(tmp.right==null || tmp.right==pre){
                if(tmp == t){
                    return list;
                }
                p = list.pop();
                pre = p;
                p = null;
            }else{
                p = tmp.right;
            }
        }
        return list;
    }


```

Tarjan算法与并查集解决二叉树节点间最近公共祖先的批量查询问题：c-p159

（这个太复杂了，暂时不考虑）

树节点：
```java
class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int val){
        this.val = val;
    }
}
```
再定义Query类如下
```java
class Query{
    TreeNode o1;
    TreeNode o2;
    public Query(TreeNode o1, TreeNode o2){
        this.o1 = o1;
        this,o2 = o2;
    }
}
```
一个Query类的实例代表一条查询语句，表示想要查询o1节点和o2节点的最近公共祖先节点。
给定一棵二叉树的头结点head，并给定所有的查询语句，即一个Query类型的数组Query[] ques,要返回TreeNode类型的数组TreeNode[] ans, ans[i]代表ques[i]这条查询的答案，即ques[i].o1和ques[i].o2的最近公共祖先。
如果二叉树的节点数为N，查询语句条数是M，整个处理过程的时间复杂度要求达到O(N+M)

首先生成ques长度一样的ans数组，有3种情况可以直接得到答案：
1.如果o1等于o2，答案为o1
2.如果o1和o2有一个为null，则答案是不为空的那个
3.如果o1和o2都是null，则答案是null

生成两张哈希表queryMap和indexMap，queryMap类似于邻接表，key表示查询涉及的某个节点，value是一个链表类型，表示key与那些节点之间有查询任务。indexMap的key也表示查询涉及的某个节点，value也是链表类型，表示如果一次解决有关key节点的每个问题，该把答案放在ans的上面位置。



二叉树节点之间的最大距离：c-p169
从二叉树的一个节点出发，可以向上走或者向下走，但沿途的节点只能经过1次，当到达另一个节点时，路径上的节点数叫做A到B的距离。
对于每个节点，都把他当做拐点，计算 左子树的高度+右子树高度+1 对所有的节点，求出这个值的最大值
```java
public int getMaxDistance(TreeNode h){
    int[] res = new int[1];
    getHeight(h, res);
    return res[0];

}

public int getHeight(TreeNode h, int[] res){
    if(h==null) return 0;
    int left = getHeight(h.left, res);
    int right = getHeight(h.right, res);

    int cur = 1+left+right;
    if(res[0]<cur)
        res[0] = cur;

    return 1+Math.max(left, right);
}
```

矩阵的最小路径和：c-p187

给一个矩阵，从左上角开始只能向右或向下走，达到右下角位置的最小路径和

dp[i][j] = num[i][j]+Math.min(dp[i-1][j], dp[i][j-1]);

由于只和上面的和左面的有关，所以可以优化为一维数组
i之前的都是这一层的，i及i之后的都是上一层的

优化空间复杂度为O(col)

```java
public int getMinPathSum(int[][] m){
    if(m==null || m.length==0 || m[0].length==0) return 0;
    int row = m.length;
    int col = m[0].length;
    int[] dp = new int[col];
    for(int i=0;i<row;i++){
        for(int j=0;j<col;j++){
            if(i==0 && j==0){
                dp[j] = m[0][0];
            }else if(j==0){
                dp[j] = dp[j-1]+m[i][j];
            }else if(i==0){
                dp[j] == dp[j]+m[i][j];
            }else{
                dp[j] = m[i][j]+Math.min(dp[j], dp[j-1]);
            }
        }
    }
    return dp[col-1];
}
```

如果类似本题这种需要二维表的动态规划题目，最终目的是想求最优解的具体路径，往往需要完整的动态规划表，但如果只是想求得最优解的值，则可以使用空间压缩的方法。因为空间压缩的方法是滚动更新的，会覆盖之前求解的值，让求解轨迹变得不可回溯。


换钱的最少货币数（找零钱）：c-p191.1
给定数组arr，arr中所有值都为正数且不重复，每个值代表一面值的货币，每种货币可以使用任意张，再给一个整数aim代表要找的钱数，求组成aim的最少货币数

一定要注意，找零的这种题不能一上来就从大到小每个试，尽量让大货币数额多，这是不对的！！！❌

例如，有 1，5，6，8 四种货币，要找11块钱，如果先找8的，则结果是：8，1，1，1，但最少其实应该是 6，5

则做法是：如果有N张货币（arr大小为N），换钱数为aim，则生成动态规划表：dp[N][aim+1]
dp[i][j]表示，在任意使用arr[0...i]货币的情况下，组成j所需的最小张数

dp[i][j] = min {dp[i-1][j-k * arr[i]]+k} (k>=0)
可以推导得:
dp[i][j] = min{dp[i][j-arr[i]]+1, dp[i-1][j]}，如果j-arr[i]<0，或者dp[i][j-arr[i]]没有合理解,直接让dp[i][j]=dp[i-1][j]

任何纸币得到结果0只需要0张；

可以看到仍然可以使用路径压缩：
dp[j] = min{dp[j-arr[i]]+1, dp[j]}  


//更便于理解的方法：对于每个和sum，对它遍历每一个面额的钱，如果dp[sum-coins[i]]!=MAX，则dp[sum] = dp[sum-coins[i]]+1，一个sum遍历完所有的coins后，取过程中的最小值,如果对于所有的coins，sum-coins[i]都不合适，则dp[sum]取MAX


```java
//未路径压缩
    public int coinChange(int[] coins, int amount) {
        if(coins==null || coins.length==0 || amount < 0) return -1;
        int n = coins.length;
        int[][] dp = new int[n][amount+1];

        for(int j=1;j<=amount;j++){
            if(j%coins[0]==0){
                dp[0][j] = j/coins[0];
            }else{
                dp[0][j] = Integer.MAX_VALUE;
            }
        }

        for(int i=1;i<n;i++){
            for(int j=0;j<=amount;j++){
                if(j-coins[i]>=0 && dp[i][j-coins[i]]!=Integer.MAX_VALUE){
                    dp[i][j] = Math.min(dp[i-1][j], dp[i][j-coins[i]]+1);
                }else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[n-1][amount]==Integer.MAX_VALUE?-1:dp[n-1][amount];
    }

//路径压缩
    public int coinChange(int[] coins, int amount) {
        if(coins==null || coins.length==0 || amount < 0) return -1;
        int n = coins.length;
        int[] dp = new int[amount+1];
        dp[0] = 0;
        for(int j=1;j<=amount;j++){
            if(j-coins[0]>=0 && dp[j-coins[0]]!=Integer.MAX_VALUE){
                dp[j] = dp[j-coins[0]]+1;
            }else{
                dp[j] = Integer.MAX_VALUE;
            }
        }

        for(int i=1;i<n;i++){
            for(int j=1;j<=amount;j++){
                int tmp = Integer.MAX_VALUE;
                if(j-coins[i]>=0 && dp[j-coins[i]]!=Integer.MAX_VALUE){
                    dp[j] = Math.min(dp[j], dp[j-coins[i]]+1);
                }else{
                    dp[j] = Math.min(dp[j], tmp);
                }
            }
        }
        return dp[amount]==Integer.MAX_VALUE?-1:dp[amount];
    }

//更便于理解的方法：对于每个和sum，对它遍历每一个面额的钱，如果dp[sum-coins[i]]!=MAX，则dp[sum] = dp[sum-coins[i]]+1，一个sum遍历完所有的coins后，取过程中的最小值
    public int coinChange(int[] coins, int amount) {
        if(coins==null || coins.length==0 || amount < 0) return -1;
        int[] dp = new int[amount+1];
        dp[0] = 0;
        for(int sum=1;sum<=amount;sum++){
            dp[sum] = Integer.MAX_VALUE;
            for(int i=0;i<coins.length;i++){
                int tmp = dp[sum];
                if(sum-coins[i]>=0 && dp[sum-coins[i]]!=Integer.MAX_VALUE){
                    tmp = dp[sum-coins[i]] +1;
                }
                dp[sum] = Math.min(dp[sum], tmp);
            }
        }
        return dp[amount];
    }
```


给定数组arr，arr中所有的值都为正数，每个值仅代表该面值的一张钱，再给定一个整数aim代表要找的钱数，求组成aim的最少货币数，如果无法组成，返回-1：c-p191.2
如[5,2,3] aim=20：5，2，3元各有1张，无法组成20元，返回-1
[5,2,5,3] aim=10  5元的有两张，可以组成10元且用钱最少，返回2
[5,2,5,3] aim=15  所有的钱加起来才能组成15元，返回4
[5,2,5,3] aim=0  不用任何货币就能组成0，返回0

dp[i][j]是前i-1张货币组成j元所用钱的最少数
dp[i][0]=0;
dp[0][j]表示只使用这一张钱能达到的钱数，自然让 dp[0][coins[0]]=1, 其他dp[0][i]都为MAX(除了dp[0][0])
如果不使用arr[i]就能组成j，则dp[i][j]的值可能等于dp[i-1][j]
因为arr[i]只有一张不能重复使用，考虑dp[i-1][j-arr[i]]，它代表可以任意使用arr[0..i-1]货币的情况下，组成j-arr[i]的最小张数，因此dp[i][j]可能等于dp[i-1][j-arr[i]]+1，如果j-arr[i]<0,则直接让dp[i][j]=dp[i-1][j]

    //注意！这里路径压缩的方法有点不一样，因为dp[i][j]要用到dp[i-1][j-arr[i]]，也就是上一行的前面的数据，所以这里j的遍历要从后往前走，避免上一行的数据被过早覆盖

```java
    public int minCoins(int[] arr, int aim){
        if(arr==null || arr.length==0 || aim<0) return 0;
        int n = arr.length;
        int[][] dp = new int[n][aim+1];

        for(int i=0;i<n;i++){
            dp[i][0] = 0;
        }

        for(int j=1;j<=aim;j++){
            if(j==arr[0]){
                dp[0][j] = 1;
            }else
                dp[0][j] = Integer.MAX_VALUE;
        }

        for(int i=1;i<n;i++){
            for(int j=1;j<=aim;j++){
                if(j-arr[i]>=0 && dp[i-1][j-arr[i]]!=Integer.MAX_VALUE){
                    dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j-arr[i]]+1);
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[n-1][aim]==Integer.MAX_VALUE?-1:dp[n-1][aim];
    }


    //注意！这里路径压缩的方法有点不一样，因为dp[i][j]要用到dp[i-1][j-arr[i]]，也就是上一行的前面的数据，所以这里j的遍历要从后往前走，避免上一行的数据被过早覆盖
    public int minCoins(int[] arr, int aim){
        if(arr==null || arr.length==0 || aim<0) return 0;
        int n = arr.length;
        int[] dp = new int[aim+1];

        dp[0]=0;

        for(int j=1;j<=aim;j++){
            if(j==arr[0]){
                dp[j] = 1;
            }else
                dp[j] = Integer.MAX_VALUE;
        }

        for(int i=1;i<n;i++){
            for(int j=aim;j>0;j--){
                if(j-arr[i]>=0 && dp[j-arr[i]]!=Integer.MAX_VALUE){
                    dp[j] = Math.min(dp[j], dp[j-arr[i]]+1);
                }else{
                    dp[j] = dp[j];
                }
            }
        }
        return dp[aim]==Integer.MAX_VALUE?-1:dp[aim];
    }


```

换钱的方法数：c-p196

给定数组arr，arr中所有值都为正数且不重复，每个值代表 一种面额的货币，每种面值的货币可以使用任意张，再给定一个整数aim代表要找的钱数，求换钱有多少种方法

》方法一：
首先想到的就是用trackback求组合数，需要传入start。（暴力递归）
复杂度是O(aim^N)

```java
public int coins(int[] arr, int aim){
    int[] res = new int[1];
    coins(0, 0, arr, aim, res);
    return res[0];
}

public void coins(int start, int cur, int[] arr, int aim, int[] res){
    if(cur>aim) return;
    else if(cur==aim){
        res[0]++;
        return;
    }
    for(int i=start;i<arr.length;i++){
        coins(i, cur+arr[i], arr, aim, res);
    }
}
```

》方法二：记忆搜索（优化的trackback），在递归过程中，arr始终不变，变得只有start和cur，计算之所以大量，因为中间过程都没有记录下来，只要把start-cur作为k，此时能完成的结果数作为v记录到map中，就能使用之前遍历过的结果,为了更方便理解，把cur改成rest，表示剩下准备处理的数。以start-rest作为key，复用之前的结果
复杂度是O(N * aim^2)

```java
public int coins2(int[] arr, int aim){
    return coins(0, aim, arr, new HashMap<String, Integer>());
}

public int coins2(int start, int rest, int[] arr, HashMap<String, Integer> map){
    if(map.containsKey(start+"-"+rest)){
        return map.get(start+"-"+rest);
    }
    int ans = 0;
    if(rest<0) {
        ans = 0;
    }
    else if(rest==0){
        ans=1;
    }else{
        for(int i=start;i<arr.length;i++){
            ans += coins(i, rest-arr[i], arr, map);
        }
    }
    map.put(start+"-"+rest, ans);
    return ans;
}
```

》方法三：动态规划：生成行数为N，列数为aim+1的矩阵dp[][] dp[i][j]代表使用arr[0...i]的货币能构成总钱数为j的总方法数

dp[i][0]=1 组成0元的方法数均为1  //这个有点不好想，因为不使用任何货币也算一种方法，所以是1
dp[0][j]= j%arr[0]==0?j/arr[0]:0;  //j是arr[0]的倍数，则可组成，否则为0

dp[i][j]包含 不使用arr[i]，只使用arr[0..i]组成j的可能性：dp[i-1][j]，
如果使用arr[i]，则可能性为：dp[i-1][j-k * arr[i]] （一直到 j-k * arr[i]<0为止, k为0时包含了dp[i][j]）

所以dp[i][j] = Σdp[i-1][j-k * arr[i]] (j-k * arr[i]>=0)

如果要使用路径压缩优化空间，二层的遍历也要倒序遍历，因为本层的答案用的都是上一层的结果，用不到本层之前的结果。要注意。

动态规划比较吃空间，当空间吃不消时，可以改用记忆搜索


》优化动态规划：对于dp[i-1][j-k* arr[i]],当k为0时，为dp[i-1][j], 
而当k不为0时，有 dp[i-1][j-1* arr[i]] + dp[i-1][j-2* arr[i]]+... 其实就是dp[i][j-arr[i]]
因此又可以简化为：dp[i][j] = dp[i-1][j] + dp[i][j-arr[i]] 不过还是得保证j-arr[i]>=0，否则只有前面的项

```java
public int coins3(int[] arr, int aim){
    if(arr==null || arr.length==0 || aim<0) return 0;
    int n = arr.length;
    int[][] dp = new int[n][aim+1];
    for(int i=0;i<n;i++){
        dp[i][0] = 1;
    }

    for(int j=1;j<=aim;j++){
        if(j%arr[0]==0){
            dp[0][j] = 1;
        }else{
            dp[0][j]=0;
        }
    }

    for(int i=1;i<n;i++){
        for(int j=1;j<=aim;j++){
            for(int k=0;k*arr[i]<=j;k++){
                dp[i][j] += dp[i-1][j-k*arr[i]];
            }
        }
    }
    return dp[n-1][aim];
}



public int coins4(int[] arr, int aim){
    if(arr==null || arr.length==0 || aim<0) return 0;
    int n = arr.length;
    int[][] dp = new int[n][aim+1];
    for(int i=0;i<n;i++){
        dp[i][0] = 1;
    }

    for(int j=1;j<=aim;j++){
        if(j%arr[0]==0){
            dp[0][j] = 1;
        }else{
            dp[0][j]=0;
        }
    }

    for(int i=1;i<n;i++){
        for(int j=1;j<=aim;j++){
            dp[i][j] = j-arr[i]>=0?dp[i-1][j]+dp[i][j-arr[i]]:dp[i-1][j];
            
        }
    }
    return dp[n-1][aim];
}
```

# 很重要的是动态规划不要想一蹴而就，在大概规划好数组的意义后，先处理边界情况，一般是dp[i][0]和dp[0][j]的情况，都处理完了，再一步一步慢慢找递推关系。就像上面题一样


最长递增子序列：c-p202

给定数组arr，返回arr的最长递增子序列

一般求最长递增子序列长度的办法的动态规划法：O(n^2)
dp[i] 表示arr[0...i]之间且以arr[i]结尾的最长的递增子序列
dp[i]初始化为1，表示只有arr[i]一个

dp[i]=1;
for(int j=0;j< i;j++){
    if(arr[i]>arr[j])
        dp[i] = Math.max(dp[i],dp[j]+1);
}

如果要求具体序列的话，可以在dp的过程中加上一个表示上一个索引的数组pre[i]
，如pre[i]=x, 表示以arr[i]结尾的最长递增子序列中，arr[i]的上一个数字是arr[x];
令pre[i]默认为-1，表示没有上一个数字。最后安装pre数组还原出序列



```java
    public int[] getLIS(int[] arr){
        if(arr==null || arr.length==0) return null;
        int len = arr.length;
        int[] dp = new int[len];
        int[] pre = new int[len];


        int max = 0;
        int lastIndex = 0;
        for(int i=0;i<len;i++){
            dp[i] = 1;
            pre[i]=-1;
            for(int j=0;j<i;j++){
                if(arr[i]>arr[j]){
                    if(dp[j]+1>dp[i]){
                        dp[i] = dp[j]+1;
                        pre[i] = j;
                    }
                }
            }
            if(dp[i]>max){
                lastIndex = i;
                max = dp[i];
            }
        }
        int p = lastIndex;
        int[] ans = new int[max];
        int i=max-1;
        while(p!=-1){
            ans[i--] = arr[p];
            p = pre[p];
        }

        return ans;
    }
```

还原序列的方法2：除了上面用pre来记录上个节点，直接用dp也可以还原序列
遍历dp数组，找到最大值以及为止，然后从最大值位置开始从右往左遍历，如果对应某一位置i，既有arr[i]< arr[maxi], 又有dp[i]=dp[maxi]-1,说明其可以当做递增子序列的倒数第二个数，然后就可以继续往前走了，直到找到所有的数
```java
    public int[] generateLIS(int[] arr, int[] dp){
        int index = 0;
        int len = 0;
        for(int i=0;i<dp.length;i++){
            if(dp[i]>dp[index]) {
                index = i;
                len = dp[i];
            }

        }
        int[] ans = new int[len];
        ans[--len] = arr[index];
        for(int i=index;i>=0;i--){
            if(arr[i]<ans[len] && dp[i]==dp[index]-1){
                ans[--len] = arr[i];
                index = i;
            }
        }
        return ans;
    }
```



用二分查找来优化： c-p202.2
先生成一个长度为N的数组ends，初始时ends[0]=arr[0],其他位置上的值为0。生成整型变量right，初始时right=0。
含义为：遍历过程中，ends[0...right]为有效区，ends[right+1..N-1]为无效区。对有效区的位置b，如果有ends[b]=c，则表示遍历到目前位置，所有长度为b+1的递增序列中，最小的结尾数是c。

对于arr=[2,1,5,3,6,4,8,9,7]，初始时dp[0]=1，ends[0]=2,right=0, ends[0..0]为有效区，ends[0]=2的含义是，在遍历过arr[0]后，所有长度为1的递增序列中（此时只有2），最小的结尾数是2
遍历到arr[1]=1。ends有效区为ends[0..0]=[2]，在有效区中找到最左边的大于或等于arr[1]的数，发现是ends[0],表示以arr[1]结尾的最长递增子序列只有arr[1],令dp[1]=1,再令ends[0]=1,表示到目前为止，所有长度为1的递增序列中，最小的结尾数是1
遍历到arr[2]=5,ends有效区=ends[0..0]=[1]，在有效区中找到最左边大于或等于arr[2]的数。发现没有这样的数，表示以arr[2]结尾的最长递增序列长度=ends有效区长度+1,令dp[2]=2。ends整个有效区都没有比arr[2]更大的数，说明发现了比ends有效区长度更长的递增序列，于是把有效区扩大，ends有效区=ends[0..1]=[1,5]
遍历到arr[3]=3,ends[0..1]=[1,5],在有效区中用二分法找到最左边大于或等于arr[3]的数，发现是ends[1]，表示以arr[3]结尾的最长递增子序列长度为2，令dp[3]=2,然后令ends[1]=3,因为到目前为止，在所有长度为2的递增子序列中，最小的结尾数是3，不再是5
一直把所有的数都遍历完，ends有效区=ends[0...right]，而ends[0...right]就是最长递增子序列

```java
    public int[] getLIS(int[] arr){
        if(arr==null || arr.length==0) return null;
        int len = arr.length;
        int[] ends = new int[len];
        int[] dp = new int[len];
        dp[0] = 1;
        int right = 0;
        ends[0] = arr[0];
        int m=0,l=0,r=0;
        for(int i=1;i<len;i++){
            l=0;
            r=right;
            while(r>=l){
                m = (r+l)>>1;
                if(arr[i]>ends[m]){
                    l = m+1;
                }else{
                    r = m-1;
                }
            }
            right = Math.max(right,l);
            ends[l] = arr[i];
            dp[i] = l+1;
        }
        return generateLIS(arr, dp);
    }
```


汉诺塔问题：c-p206.1
给一个整数n，代表汉诺塔游戏从小到大放置的n个圆盘，假设开始时所有的圆盘都放在左边的柱子上，想按照规则把所有圆盘移到右边的柱子上，实现函数打印最优移动轨迹
假设有from柱子，mid柱子和to柱子，都在from的圆盘1到i完全移动到to，最优过程为：
步骤1为圆盘1到i-1从from移动到mid
步骤2为单独把圆盘i从from移动到to
步骤3为把圆盘1到i-1从mid移动到to，如果圆盘只有1个，直接把这个圆盘从from移动到to即可

这个看起来和 c-p14 不一样，左柱子上的可以直接到右柱子而不用经过中间

打印最优移动轨迹
```java
public void hanoi(int n){
    if(n>0){
        func(n, "left", "mid", "right");
    }
}

public void func(int n, String from, String mid, String to){
    if(n==1){
        System.out.println("move from " + from + " to " + to);
    }else{
        //把上面的1到n-1从左移动到中
        func(n-1. from, to, mid);
        //把n从左移动到右
        func(1, from mid, to);
        //把1到n-1从中移动到右
        from(n-1, mid, from, to);
    }
}
```

汉诺塔进阶：c-p206.2
给定一个整型数组arr，其中只含有1，2，3代表所有圆盘目前的状态，1代表左柱，2代表中柱，3代表右柱，arr[i]的值代表第i+1个圆盘的位置，比如arr[3,3,2,1]代表第1个圆盘在右柱上，第2个圆盘在右柱上，第3个圆盘在中柱上，第4个圆盘在左柱上。如果arr代表的是最优移动轨迹过程中出现的状态，返回arr这种状态是最优移动轨迹中的第几个状态。如果arr不是最优移动轨迹过程中出现的状态，则返回-1。

首先求都在from柱子上的圆盘1到i，如果都移动到to上的最少步骤数，假设为S(i),根据上面的步骤，S(i)=步骤1的步骤总数+1+步骤3的步骤总数=S(i-1)+1+S(i-1) S(1)=1，
可得到S(i)=2^(i-1)

对于数组arr来说，arr[N-1]表示最大圆盘N在哪个柱子上，情况有以下三种：
1.圆盘n在左柱子上，说明步骤1或者没有完成，或者已经完成，需要考察圆盘1到n-1的状况
2.圆盘n在右柱子上，说明步骤1已经完成，起码走完了2^(n-1)-1步，步骤2也已经完成，起码又走了1步，所以当前状况是最优步骤的2^(n-1)步，剩下的步骤怎么确定还得继续考察圆盘1到n-1的状况
3.圆盘n在中柱上，这是不可能的，最优步骤中不可能让圆盘n处在中柱上，直接返回-1。

所以整个过程可以总结为：对于圆盘1到i来说，如果目标从from到to，那么情况有3种：
1.圆盘i在from上，需要继续考察圆盘1到i-1的状况，圆盘1到i-1的目标为从from到mid
2.圆盘i在to上，说明起码走完了2^（i-1）步，剩下的步骤怎么确定还得继续考察圆盘1到i-1的状况，圆盘1到i-1的目标为从mid到to
3.圆盘i在mid上，直接返回-1

```java
public int step1(int[] arr){
    if(arr==null || arr.length==0){
        return -1;
    }else{
        return process(arr, arr.length-1, 1, 2, 3);
    }
}

//process是把i从from放到to的过程，arr当前的状态可能是这个过程中的任意一个过程，也可能不是
public int process(int[] arr, int i, int from, int mid, int to){
    if(i==-1){
        return 0;
    }
    if(arr[i]!=from && arr[i]!=to){
        return -1;
    }
    if(arr[i]==from){
        return process(arr, i-1, from, to, mid);
    }else{
        int rest = process(arr, i-1, mid, from, to);
        if(rest==-1)
            return -1;
        return (1<<i)+rest;
    }
}

```

如果不使用递归：
```java
public int step2(int[] arr){
    if(arr==null || arr.length==0){
        return -1;
    }
    int from = 1;
    int mid = 2;
    int to = 3;
    int i= arr.length-1;
    int res = 0;
    int tmp = 0;
    while(i>=0){
        if(arr[i]!=from && arr[i]!=to){
            return -1;
        }
        if(arr[i]==to){
            res+=1<<i;
            tmp = from;
            from = mid;
        }else{
            tmp = to;
            to = mid;
        }
        mid = tmp;
        i--;
    }
    return res;
}
```

最长公共子序列/公共最长子序列：c-p210
给定两个字符串str1和str2，返回两个字符串的最长公共子序列
如str1="1A2C3D4B56" str2="B1D23CA45B6A"
"123456" 或 "12C4B6" 都是最长公共子序列，返回哪个都行

dp[i][j]为 str1[0..i]与str2[0...j]的最长公共子序列长度

dp[0][0] == str1[0]==str2[0]?1:0
dp[0][j] == max(dp[0][j-1], str1[0]==str2[j]?1:0)
dp[i][0] == max(dp[i-1][0], str1[i]==str2[0]?1:0)

若str1[i]==str2[j]
    dp[i][j] = max(dp[i-1][j-1]+1, dp[i][j-1], dp[i-1][j])
若str1[i]!=str2[j]
    dp[i][j] = max(dp[i][j-1], dp[i-1][j])

要还原出序列，方法如下:
1.从矩阵右下角开始，有三种移动方式：向上，向左，向左上。假设移动过程中，i表示此时的行数，j表示此时的列数，同时用一个变量res表示最长公共子序列。
2.如果dp[i][j]大于dp[i-1][j]和dp[i][j-1],说明之前计算dp[i][j]时，移动选择了决策dp[i-1][j-1]+1,可以确定str1[i]等于str2[j]，且这个字符移动属于最长公共子序列，把这个字符放进res，然后向左上方移动
3.如果dp[i][j]等于dp[i-1][j]，说明之前在计算dp[i][j]的时候，dp[i-1][j-1]+1这个决策部署必须选择的决策，向上方移动即可
4.如果dp[i][j]等于dp[i][j-1],与步骤3同理，向左上方移动。
5.如果dp[i][j]同时等于dp[i][j-1]和dp[i][j-1]，向上还是向左无所谓，选择其中一个即可


```java
    public int[][] getLCS_DP(char[] str1, char[] str2){
        if(str1==null || str2==null) return null;
        int len1 = str1.length;
        int len2 = str2.length;
        int[][] dp = new int[len1][len2];
        dp[0][0] = str1[0]==str2[0]?1:0;
        for(int i=1;i<len1;i++){
            dp[i][0]=Math.max(dp[i-1][0], str1[i]==str2[0]?1:0);
        }
        for(int j=1;j<len2;j++){
            dp[0][j]=Math.max(dp[0][j-1], str1[0]==str2[j]?1:0);
        }

        for(int i=1;i<len1;i++){
            for(int j=1;j<len2;j++){
                if(str1[i]==str2[j]){
                    dp[i][j]=Math.max(dp[i-1][j-1]+1, dp[i][j-1]);
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j]);
                }else{
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }
            }
        }
        return dp;
    }

    public String getLCS(String str1, String str2){
        if(str1==null || str2==null || str1.length()==0 || str2.length()==0)
            return "";
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int m = s1.length-1;
        int n = s2.length-1;
        int[][] dp = getLCS_DP(s1, s2);
        char[] res = new char[dp[m][n]];
        int index = res.length-1;
        while(index>=0){
            if(n>0 && dp[m][n]==dp[m][n-1]){
                n--;
            }else if(m>0 && dp[m][n]==dp[m-1][n]){
                m--;
            }else{
                res[index--] = s1[m];
                m--;
                n--;
            }
        }
        return String.valueOf(res);
    }

```

# 子序列可以是不连续的，子串是连续的


最长公共子串：c-p213
给定两个字符串str1和str2，返回两个字符串最长公共子串
str1="1AB2345CD", str2="12345EF", 返回"2345"

dp[i][j]是str1以str1[i]结尾，str2以str2[j]结尾的最长公共子串长度

dp[0][0] = str1[0]==str2[0]?1:0
dp[0][j] == str1[0]==str2[j]?1:0
dp[i][0] == str1[i]==str2[0]?1:0

如果str1[i]!=str2[j]
    dp[i][j]=0
如果str1[i]==str2[j]
    dp[i][j]=dp[i-1][j-1]+1

复原子串：
遍历dp，记录其中最大值的行和列，比如dp[6][4]=4
则str[3..6]就是这个最长子序列


经典方法需要大小为MxN的矩阵，实际上可以减小到O(1)，因为计算dp[i][j]的时候，只需要dp[i-1][j-1]的值，按照斜线方向（从左上往右下的斜线），只需要常数级变量就可以计算出所有位置的值



```java
//方法1：
    public int[][] getLCS_DP(char[] s1, char[] s2){
        if(s1==null || s2==null || s1.length==0 || s2.length==0){
            return null;
        }
        int m = s1.length;
        int n = s2.length;
        int[][] dp = new int[m][n];
        dp[0][0] = s1[0]==s2[0]?1:0;
        for(int i=1;i<m;i++){
            dp[i][0]=s1[i]==s2[0]?1:0;
        }
        for(int j=1;j<n;j++){
            dp[0][j]=s1[0]==s2[j]?1:0;
        }

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                if(s1[i]==s2[j]){
                    dp[i][j]=dp[i-1][j-1]+1;
                }
            }
        }
        return dp;
    }

    public String getLCS(String str1, String str2){
        if(str1==null || str2==null || str1.length()==0 || str2.length()==0){
            return null;
        }
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int m = s1.length-1;
        int n = s2.length-1;
        int[][] dp = getLCS_DP(s1, s2);
        int row = 0;
        int col = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(dp[i][j]>dp[row][col]){
                    row=i;
                    col=j;
                }
            }
        }
        int len = dp[row][col];
        StringBuilder builder = new StringBuilder();
        while(len>0){
            builder.insert(0, s1[row--]);
            len--;
        }
        return builder.toString();
    }


//方法二：

    public String getLCS2(String str1, String str2){
        if(str1==null || str2==null || str1.length()==0 || str2.length()==0){
            return null;
        }
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int m = s1.length-1;
        int n = s2.length-1;

        int pre = 0;
        int i=0,j=0;
        int tmp = 0;
        int max = 0;
        int recordi = -1;
        //先看第一排每个元素的斜下方一溜
        while(tmp<n){
            i=0;
            j=tmp;
            while(i<m && j<n){
                if(s1[i]==s2[j]){
                    pre +=1;
                }else{
                    pre=0;
                }
                if(pre>max){
                    max=pre;
                    recordi = i;
                }
                i++;
                j++;
            }
            tmp++;
        }

        tmp=1;
        while(tmp<m){
            i=tmp;
            j=0;
            while(i<m && j<n){
                if(s1[i]==s2[j]){
                    pre +=1;
                }else{
                    pre=0;
                }
                if(pre>max){
                    max=pre;
                    recordi = i;
                }
                i++;
                j++;
            }
            tmp++;
        }
        if(max==0) return "";
        return str1.substring(recordi-max+1, recordi+1);
    }
```
    
最小编辑代价：c-p217
给定两个字符串str1和str2，再给定3个整数ic，dc，rc，分别代表插入、删除和替换一个字符的代价，返回将str1编辑成str2的最小代价
例如str1="abc", str2="adc" ic=5,dc=3,rc=2
从abc编辑成adc，把b替换成d是代价最小的，所以返回2
例如str1="abc", str2="adc" ic=5,dc=3,rc=100
先删除b，然后插入d是代价最小的，返回8

dp[i][j]是把str1[0..i-1]变成str2[0..j-1]的最小代价 （注意，这里因为要讨论空的问题，所以i对应str1[0..i-1]）

dp[0][0]=0  //空变成空，花费0

dp[0][j]=dp[0][j-1]+ic;
dp[i][0]=dp[i-1][0]+dc;

//要使用替换的话，先把str1[i-1]变成str2[j-1](代价为0或rc)，然后把str1[0..i-2]变成str2[0..j-2](代价dp[i-1][j-1])
若str1[i-1]==str2[j-1]：
    替换：dp[i][j]1=dp[i-1][j-1]  //由于str1[i]==str2[j]，所以最好一个字符不用替换
若str1[i]!=str2[j]:
    替换：dp[i][j]1=dp[i-1][j-1]+rc 

插入：dp[i][j]2=dp[i][j-1]+ic   //要使用插入的话，先把str1[0..i-1]变成str2[0...j-2],然后再插入str2[j-1](代价dp[i][j-1]+ic)
删除：dp[i][j]3=dp[i-1][j]+dc   //要使用删除的的话，先把str1[0..i-2]变成str2[0..j-1],然后再删除str1[i-1](代价dp[i-1][j]+dc)

dp[i][j] = min(dp[i][j]1, dp[i][j]2, dp[i][j]3);


```java
    public int smallestEditPrice(String str1, String str2, int ic, int dc, int rc){
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int m = s1.length;
        int n = s2.length;
        int[][] dp = new int[m+1][n+1];

        dp[0][0]=0;
        for(int i=1;i<=m;i++){
            dp[i][0]=dp[i-1][0]+dc;
        }
        for(int j=1;j<=n;j++){
            dp[0][j]=dp[0][j-1]+ic;
        }

        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(s1[i-1]==s2[j-1]){
                    dp[i][j]=dp[i-1][j-1];
                }else{
                    dp[i][j]=dp[i-1][j-1]+rc;
                }
                dp[i][j]=Math.min(dp[i][j], dp[i][j-1]+ic);
                dp[i][j]=Math.min(dp[i][j], dp[i-1][j]+dc);
                
            }
        }

        return dp[m][n];

    }
```

字符串的交错组成：c-p220
给定3个字符串str1，str2和aim，如果aim包含且仅包含来自str1和str2的所有字符，且在aim中属于str1的字符之间保持原来在str1中的顺序，属于str2的字符之间保持原来在str2中的顺序，那么称aim是str1和str2的交错组成。实现一个函数，判断aim是否是str1和str2交错组成。

如str1="AB", str2="12", 则"AB12", "A1B2", "A12B", "1A2B"和"1AB2"等都是str1和str2的交错组成

aim的长度一定是M+N，否则直接返回false，然后生成大小为(M+1)x(N+1)的布尔型矩阵dp，dp[i][j]的值代表aim[0..i+j-1]能否被str1[0..i-1]和str2[0..j-1]交错组成。

1.dp[0][0]=true, aim为空串时，当然可以被str1为空串和str2为空串交错组成
2.dp[i][0]表示aim[0..i-1]能否只被str1[0..i-1]交错组成，如果aim[0..i-1]等于str1[0..i-1]，则令dp[i][0]=true, 否则令dp[i][0]=false
3.dp[0][j]表示aim[0..j-1]能否只被str2[0..j-1]交错组成，如果aim[0..j-1]等于str2[0..j-1]，则令dp[0][j]=true, 否则令dp[0][j]=false
4.
    dp[i-1][j]代表aim[0...i+j-2]能否被str1[0..i-2]和str2[0..j-1]交错组成，如果可以，那么如果再有aim[i+j-1]与str1[i-1]相等，说明str1[i-1]可以作为交错组成aim[0...i+j-1]的最后一个字符，令dp[i][j]=true。
    dp[i][j-1]代表aim[0...i+j-2]能否被str1[0..i-1]和str2[0..j-2]交错组成，如果可以，那么如果再有aim[i+j-1]与str2[j-1]相等，说明str2[j-1]可以作为交错组成aim[0...i+j-1]的最后一个字符，令dp[i][j]=true。
    如果上述两种情况都不满足，则返回dp[i][j]=false

```java

    public boolean isCross(String str1, String str2, String aim){
        if(aim==null || aim.length()==0) return true;
        if(str1==null && str2==null) return false;
        if(str1==null) return str2.equals(aim);
        if(str2==null) return str1.equals(aim);

        if(str1.length()+str2.length()!=aim.length()) return false;

        char[] s1= str1.toCharArray();
        char[] s2 = str2.toCharArray();
        char[] a = aim.toCharArray();
        int m = s1.length;
        int n = s2.length;
        boolean[][] dp = new boolean[m+1][n+1];

        dp[0][0]=true;
        for(int i=1;i<=m;i++){
            dp[i][0] = aim.substring(0, i).equals(str1.substring(0,i));
        }
        for(int j=1;j<=n;j++){
            dp[0][j] = aim.substring(0, j).equals(str2.substring(0,j));
        }

        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                dp[i][j] = (dp[i-1][j] && a[i+j-1]==s1[i-1]) || (dp[i][j-1] && a[i+j-1]==s2[j-1]);

            }
        }
        return dp[m][n];
    }
```

龙与地下城游戏：c-p223
给一个二维数组，含义是一张地图，如果是负数，说明骑士要损失这么多的血量，如果是非负数，可以让骑士回复这么多血量，，骑士从左上角走到右下角，走到任何一个位置，血量都不能少于1。为了保证最终能见到公主，最少的初始血量是多少

从右下角倒推，dp[i][j]是进入map[i][j]要保证的最少血量，这个值必须大等于1
一个位置可以往下和往右走，只需保证其中一个的最低血量即可
比如dp[i][j], dp[i][j+1]代表要进入map[i][j+1]前的最低血量， dp[i+1][j]代表要进入map[i+1][j]前的最低血量。

dp[i][j] = min(dp[i][j+1], dp[i+1][j])-map[i][j]
如果上述情况得到dp[i][j]<1, 则要让dp[i][j]=1

注意，这里容易想错一点：如果map[i][j]>=0，要是直接让dp[i][j]=min(dp[i+1][j], dp[i][j+1])
就不对了，因为map[i][j]中有回血瓶，只要加了这个血瓶能让到达下一个状态即可，当前状态不一定要那么多血量，所以不能直接取min(dp[i+1][j], dp[i][j+1])。

初始情况：
    map[m][n]>=0 dp[m][n]=1
    map[m][n]<0 dp[m][n]=1+abs(map[m][n])

```java
    public int getInitHP(int[][] map){
        if(map==null || map.length==0 || map[0].length==0) return 1;
        int m = map.length;
        int n = map[0].length;

        int[][] dp = new int[m][n];
        dp[m-1][n-1] = map[m-1][n-1]>=0?1:(1+Math.abs(map[m-1][n-1]));

        for(int i=m-1;i>=0;i--){
            for(int j=n-1;j>=0;j--){
                if(i==m-1 && j==n-1) continue;
                if(i+1==m){
                    dp[i][j]=dp[i][j+1]-map[i][j];
                }
                else if(j+1==n){
                    dp[i][j]=dp[i+1][j]-map[i][j];
                }else{
                    int tmp = Math.min(dp[i+1][j], dp[i][j+1]);
                    dp[i][j]=tmp-map[i][j];
                }
                if(dp[i][j]<1) dp[i][j]=1;
            }
        }
        return dp[0][0];
    }
```


数字字符串转换为字母组合的种数：c-p225
给定一个字符串str，str全部由数字字符组成，如果str中某一个或某相邻两个字符组成的子串值在1-26之间，则这个子串可以转换为一个字符，规定“1”转换为A，“2”转换为B。。。“26”转换为Z，写一个函数，求str有多少种不同的返回种数。

如str="1111", 转换出的结果有AAAA,LAA,ALA,AAL,LL 返回5
令dp[i]为str[0...i]转成的最大个数

dp[0]=(str[0]>=1 && str[0]<=9) ? 1:0

如果dp[0]=0，则整体一定转换不成功

若str[i]==0
    若str[i-1, i]满足条件，dp[i]=dp[i-2]
否则：
    dp[i] = 0 //初始化
    若str[i]满足条件 ：dp[i] += dp[i-1]
    若str[i-1, i]满足条件，dp[i] += dp[i-2]

判断str是否满足条件的函数：
    
    //注意如果出现前导0，一定不满足
    public boolean isValid(String s){
        try{
            int t = 0;
            for(int i=0;i<s.length();i++){
                //如果出现前导0，则一定不成立
                if(t==0 && s.charAt(i)=='0') return false;
                t = t*10 + s.charAt(i) - '0';
            }
            return (t<=26 && t>=1);
        }catch(Exception e){
            return false;
        }
    }


```java
class Solution {
   public int numDecodings(String s) {
        if(s==null || s.length()==0) return 0;
        int len = s.length();
        int[] dp = new int[len];

        dp[0] = isValid(s.substring(0,1))?1:0;
        if(dp[0]==0) return 0;

        for(int i=1;i<len;i++){
            if(isValid(s.substring(i-1, i+1))){
                dp[i] += i-2>=0?dp[i-2]:1;
            }
            if(isValid(s.substring(i,i+1))){
                dp[i] += dp[i-1];
            }
        }
        return dp[len-1];
    }

    public boolean isValid(String s){
        try{
            int t = 0;
            for(int i=0;i<s.length();i++){
                //如果出现前导0，则一定不成立
                if(t==0 && s.charAt(i)=='0') return false;
                t = t*10 + s.charAt(i) - '0';
            }
            return (t<=26 && t>=1);
        }catch(Exception e){
            return false;
        }
    }
}

```

表达式得到期望结果的组成总数：c-p228
给定一个只由0(假)、1（真）、&（逻辑与）、|（逻辑或）、^（异或）五种字符组成的字符串express，再给定一个布尔值desired。返回express能有多少种组合方式，可以达到desired的结果。

例如express = 1^0|0|1 desired=false
只有1^((0|0)|1) 和 1^(0|(0|1))的组合可以得到false，返回2
express=1，desired=false
无组合可得到false，返回0

首先判断表达式是否合法，合法的表达式要求以下3点：
1.表达式的长度必须是奇数
2.表达式下标的偶数位置的字符一定是0或者1
3.表达式下标奇数位置的字符一定是&或|或^

遍历字符串，每遍历到一个逻辑运算符(&，|，^)，就会把原字符串分成两部分，，再计算这两部分各自要得到一个布尔值的种数
例如，逻辑运算符是&，目标是true，那就计算它两边各自能成为true的总数，然后乘起来。
用map保存每个字符串和desired对应的值（双map）,使用“字符串+desired”作为key，因为二者没有重复的地方，所以可以直接连起来


```java

    HashMap<String, Integer> map = new HashMap<>();
    public int getCompareCount(String s, boolean desired){
        if(map.containsKey(s+desired)) {
            return map.get(s+desired);
        }
        if(!isValid(s) || s==null || s.length()==0) return 0;

        if(s.length()==1){
            boolean tmp = s.charAt(0)=='1';
            if(tmp==desired) return 1;
            else return 0;
        }

        int res = 0;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c=='&' || c=='|' || c=='^'){
                int leftTrue = getCompareCount(s.substring(0, i), true);
                int leftFalse = getCompareCount(s.substring(0, i), false);
                int rightTrue = getCompareCount(s.substring(i+1), true);
                int rightFalse = getCompareCount(s.substring(i+1), false);

                if(c=='&'){
                    if(desired){
                        res += leftTrue * rightTrue;
                    }else{
                        res += leftTrue * rightFalse;
                        res += leftFalse * rightTrue;
                        res += leftFalse * rightFalse;
                    }
                }

                if(c=='|'){
                    if(desired){
                        res += leftTrue * rightTrue;
                        res += leftTrue * rightFalse;
                        res += leftFalse * rightTrue;
                    }else{
                        res += leftFalse * rightFalse;
                    }
                }

                if(c=='^'){
                    if(desired){
                        res += leftTrue * rightFalse;
                        res += leftFalse * rightTrue;
                    }else{
                        res += leftTrue * rightTrue;
                        res += leftFalse * rightFalse;
                    }
                }
            }
        }
        map.put(s+desired, res);
        return res;
    }

    public boolean isValid(String express){
        int len = express.length();
        if((len&1)==0) return false;
        for(int i=0;i<len;i++){
            char c = express.charAt(i);
            if((i&1)==0){
                if(c!='1' && c!='0') return false;
            }else{
                if(c!='&' && c!='|' && c!='^') return false;
            }
        }
        return true;
    }
```


排成一条线的纸牌博弈问题：c-p233
给定一个整型数组arr，代表数值不同的纸牌排成一条线，玩家A和玩家B依次拿走每张纸牌，规定玩家A先拿，玩家B后拿，但是每个玩家每次只能拿走最左或最右的纸牌，玩家A和玩家B都很聪明，请返回最后胜利者的分数

例如arr=[1,2,100,4]
开始时玩家A只能拿走1或4，若A拿走1，则排列变为[2,100,4],接下来B可以拿走2或4，然后继续轮到A，如果开始时A先拿走4，则排列变为[1,2,100]，接下来B可以拿走1或100，然后继续轮到A，A作为聪明的人不会先拿4，因为拿走4后B会拿走100.所以玩家A会先拿1，让排列变成[2,100,4]，接下来玩家B不管怎么选，100都会被玩家A拿走。玩家A会获胜，分数为101.所以返回101。

arr=[1,100,2],开始时玩家A不管先拿1还是2，B作为聪明人，都会把100拿走，B会获胜，分数为100，所以返回100

递归的方法。定义递归函数f(i,j),表示如果arr[i..j]这个排列上的纸牌被聪明人先拿，最终能得到什么分数。定义递归函数s(i,j)，表示如果arr[i..j]这个排列上的纸牌被聪明人后拿，最终能获得什么分数。

首先分析f(i..j)，具体过程如下：
1.如果i==j，即arr[i..j]上只剩一张纸牌，当然会被先拿纸牌的人拿走，所以返回arr[i]
2.如果i!=j, 当前拿牌的人有两种选择，要么拿走arr[i],要么拿走arr[j]。如果拿走arr[i],那么排列将剩下arr[i+1,j]。对当前的玩家来说，面对arr[i+1..j]排列的纸牌，他成了后拿的人，所以后续他能获得的分数为s(i+1,j)，如果先拿走arr[j]，那么排列剩下arr[i..j-1]。面对arr[i..j-1]，他成了后拿的人，为了后续他能获得的分数为s(i,j-1)，作为聪明人，必然会在两种决策中选最优的，所以返回max{arr[i]+s(i+1,j), arr[j]+s(i,j-1)}。

然后分析s(i,j)，具体过程如下：
1.如果j==i，即arr[i..j]只剩一张纸牌，后拿纸牌的人必然什么也得不到。返回0
2.如果i!=j。根据函数s的定义，玩家的对手会先拿牌，对手要么拿走arr[i]，要么拿走arr[j]。如果对手拿走arr[i]，那么排列将剩下arr[i+1..j]，然后轮到玩家先拿。如果对手拿走arr[j]，那么排列将剩下arr[i..j-1],然后轮到玩家先拿。对手也是聪明的人，所以必然会把最差的情况留给玩家。所以返回min{f(i+1,j), f(i,j-1)}

```java
    public int win1(int[] arr){
        if(arr==null || arr.length==0){
            return 0;
        }
        return Math.max(f(arr, 0, arr.length-1), s(arr, 0, arr.length-1));
    }

    public int f(int[] arr, int i, int j){
        if(i==j){
            return arr[i];
        }
        return Math.max(arr[i]+s(arr, i+1, j), arr[j]+s(arr, i, j-1));
    }

    public int s(int[] arr, int i, int j){
        if(i==j){
            return 0;
        }
        return Math.min(f(arr, i+1, j), f(arr, i, j-1));
    }

```

根据递归的方法，很明显可以使用动态规划来改进：
i依赖i+1，j依赖j-1，所以i要倒序，j要正序
f[i][j]=Math.max(arr[i]+s[i+1][j], arr[j]+s[i][j-1]);
s[i][j]=Math.min(f[i+1][j], f[i][j-1]);

```java
    public int win2(int[] arr){
        if(arr==null || arr.length==0){
            return 0;
        }
        int len = arr.length;
        int[][] f = new int[arr.length][arr.length];
        int[][] s = new int[arr.length][arr.length];
        for(int j=0;j<arr.length;j++){
            f[j][j] = arr[j];
            for(int i=j-1;i>=0;i--){
                f[i][j]=Math.max(arr[i]+s[i+1][j], arr[j]+s[i][j-1]);
                s[i][j]=Math.min(f[i+1][j], f[i][j-1]);
            }
        }
        return Math.max(f[0][len-1], s[0][len-1]);
    }
```

跳跃游戏：c-p235
给定数组arr，arr[i]=k代表可以从位置i向右跳1-k个距离，比如arr[2]=3,代表位置2可以跳到位置3，位置4，位置5.如果从位置0出发，返回最少跳几次能到arr最后的位置上

dp[i]为最少跳几次跳到索引i处
dp[i] = min(dp[j]+1)  且dp[j]>=i-j
这样的话复杂度是O(N^2)

O(N)的做法如下：
1.设dump，表示目前跳了多少步，
cur代表如果只能跳jump步，最远能够达到的位置。
next代表如果再多跳一步，最远到达的位置
2.从左到右遍历arr，假设遍历到位置i
    1.如果cur>=i，说明跳jump步可以到达位置i，此时什么都不用做
    2.如果cur< i, 说明只跳jump步不能到达位置i，需要多跳一步才行。此时令jump++，cur=next，表示多跳了一步，cur更新为跳jump+1步能够达到的位置，即next
    3.将next更新为math.max(next, i+arr[i])，表示下一次多跳一步能到达的最远位置
3.最终返回jump即可


```java
public int jump(int[] arr){
    if(arr==null || arr.length==0){
        return 0;
    }
    int jump = 0;
    int cur = 0;
    int next = 0;
    for(int i=0;i<arr.length;i++){
        if(cur<i){
            jump++;
            cur = next;
        }
        next = Math.max(next, i+arr[i]);
    }
    return jump;
}
```

数组中的连续最长序列：c-p236
给定无序数组arr，返回其中最长的连续序列的长度
如arr=[100,4,200,1,3,2],最长连续序列为[1,2,3,4]，返回4
先把数组全部放到一个set中，表示全集，
再创建一个set(visited)存放已经遍历过的数字。然后对数组进行遍历
遍历到arr[i]时，如果arr[i]-1在visited中，则跳过，说明arr[i]所在的最长的子序列一定已经被检查过了。否则依次看arr[i], arr[i]+1，arr[i]+2...是否在set中，直到不在set中，每找到一个数，就把它加入到visited中，

时间复杂度O(N), 空间复杂度O(N)
```java
public int longestSerialSequence(int[] arr){
    if(arr==null || arr.length==0) return 0;
    HashSet<Integer> set = new HashSet<>();
    for(int i:arr){
        set.add(i);
    }
    int res = 0;
    for(int i:arr){
        if(!set.contains(i-1)){ //如果i-1在，说明i要么在后面会被检查到，要么已经检查完了，取决于i-1在i的前还是后。总之cur就不用管了
            int cnt = 0;
            int tmp = i;
            while(set.contains(tmp)){
                cnt++;
                tmp++;
            }
            res = Math.max(cnt, res);
        }
    }
    return res;
}
```

N皇后问题：c-p238

N皇后问题是在NxN的棋盘上摆N个皇后，要求任何两个皇后不同行，不同列，也不在同一斜线，给定一个整数n，返回n皇后的摆法有多少种
例：n=1，返回1；n=2或3，怎么摆都不行，返回0；n=8，返回92

》基本方法：
递归+遍历的做法（本质是trackback），递归每一行，如果该行该位置能放置，则进入下一行
这样做法在n大时会非常非常慢，（在n=14时为10s）


```java
    public int nQueue(int n){
        if(n<=0) return 0;
        int[] res = new int[1];
        int[][] board = new int[n][n];
        trackBack(board, n, 0, res);
        return res[0];
    }


    public void trackBack(int[][] board, int n, int curRow, int[] res){
        if(curRow==n) {
            res[0]++;
            return;

        }
        for(int j=0;j<n;j++){
            if(posValid(n, curRow, j, board)){
                board[curRow][j]=1;
                trackBack(board, n, curRow+1, res);
                board[curRow][j]=0;
            }
        }
    }

    public boolean posValid(int n, int row, int col, int[][] board){
        int tmpx = row;
        int tmpy = col;
        //检查同一行是否有其他皇后
        for(int j=0;j<n;j++){
            if(board[row][j]==1) return false;
        }
        //检查同一列是否有其他皇后
        for(int i=0;i<n;i++){
            if(board[i][col]==1) return false;
        }
        //检查所在的斜线上是否有其他皇后
        tmpx = row;
        tmpy = col;
        while(tmpx>=0 && tmpy>=0){
            if(board[tmpx][tmpy]==1) return false;
            tmpx--;
            tmpy--;
        }
        tmpx = row;
        tmpy = col;
        while(tmpx<n && tmpy>=0){
            if(board[tmpx][tmpy]==1) return false;
            tmpx++;
            tmpy--;
        }
        tmpx = row;
        tmpy = col;
        while(tmpx>=0 && tmpy<n){
            if(board[tmpx][tmpy]==1) return false;
            tmpx--;
            tmpy++;
        }
        tmpx = row;
        tmpy = col;
        while(tmpx<n && tmpy<n){
            if(board[tmpx][tmpy]==1) return false;
            tmpx++;
            tmpy++;
        }
        return true;

    }


```

》更好的方法：（好像比较超自然）
变量upperLim表示当前行的那些位置是可以放皇后的，1代表可以放置，0代表不能放置。
8皇后问题中，初始时upperLim=00000000000000000000000011111111，32皇后问题中，初始时upperLim=11111111111111111111111111111111
colLim表示递归计算到上一行为止，哪些列已经放置了皇后，1代表已经放置，0代表没有放置。
leftDiaLim表示递归到上一行为止，因为受已经放置的所有皇后的左下方斜线的影响，导致当前行不能放置皇后，1代表不能放置，0代表可以放置。举个例子，如果在第0行第4列放置了皇后。计算到第1行时，第0行皇后的左下斜方影响的是第1行第3列，当计算到第二行是，第0行皇后的左下斜影响的是第二行第2列，leftDiaLim每次左移一位，就可以得到之前所有皇后的左下方斜线对当前行的影响。
rightDiaLim表示递归到上一行为止，因为受已经放置的所有皇后的右下方斜线的影响，导致当前行不能放置皇后，1代表不能放置，0代表可以放置。rightDiaLim每次右移一位，就可以得到之前所有皇后的右下方斜线对当前行的影响。

变量mostRightOne代表在pos中，最右边的1是什么位置，然后从右到左一次筛选出pos中可选择的位置进行递归尝试

```java
public int num2(int n){
    //本方法的位运算的载体是int变量，所以只能计算1-32皇后问题
    //项计算更多皇后问题，需使用含更多位的变量
    if(n<1 || n>32){
        return 0;
    }
    int upperLim = n ==32?-1:(1<<n)-1; //((1<<n)-1)从第0位到第n-1位都是1，其他位是0
    return process2(upperLim, 0,0,0);
}

public int process2(int upperLim, int colLim, int leftDiaLim, int rightDiaLim){
    if(colLim==upperLim){
        return 1;
    }
    int pos=0;
    int mostRightOne = 0;
    //(colLim | leftDiaLim | rightDiaLim)会把所有不能再放皇后的位标为1
    //取反后就是所有能放皇后的地方标为1，再与upplerLim做与，就是最终能放皇后的位置都是1的数
    int res = 0;
    pos = upperLim & (~(colLim | leftDiaLim | rightDiaLim));
    while(pos!=0){
        mostRightOne = pos & (~pos+1); //得到pos中最右边1保留其他位都变0的数
        pos = pos -mostRightOne; //相当于把最右边的1变为0，代表这个位置已经被放了皇后，接下来这一列就不能再放了
        res += process2(upperLim, colLim | mostRightOne,
            (leftDiaLim | mostRightOne)<<1,  //左下方不能再放了
            (rightDiaLim | mostRightOne)>>>1  //右下方不能再放了
            );

    }
    return res;
}
```

第五章：字符串问题

判断两个字符串是否互为变形词：c-p242
给定两个字符串str1和str2，如果str1和str2中出现的字符种类一样且每种字符出现的次数也一样，那么str1和str2互为变形词，请实现函数判断两个字符串是否互为变形词

》方法一：用map
用hashmap，key为字符，value为出现次数，遍历str1，把所有字符及次数都放入map中，然后遍历str2，每出现一个字符，若其在map中没有，直接返回false，若有，次数-1，当次数为0时从map中删除这个key，最后看map的size是否为0,

》方法二：用数组
用两个数组分别记录两个单词中每个字母的个数，然后比较两个数组对应位置的值是否相同

```java
public boolean isChanged(String s1, String s2){
    if(s1==null && s2==null){
        return true;
    }else if(s1==null || s2==null){
        return false;
    }
    if(s1.length()!=s2.length()) return false;
    HashMap<Character, Integer> map = new HashMap<>();
    for(int i=0;i<s1.length();i++){
        char c = s1.charAt(i);
        map.put(c, map.getOrDefault(c, 0)+1);
    }

    for(int i=0;i<s2.length();i++){
        char c = s2.charAt(i);
        if(!map.containsKey(c)) return false;
        else{
            map.put(c, map.get(c)-1);
            if(map.get(c)==0){
                map.remove(c);
            }
        }
    }
    return map.size()==0;
}
```

字符串中数字子串求和：c-p243

给定一个字符串str，求其中全部数字所代表的数字之和
1.忽略小数点符，如"A1.3" 其中包含两个数字1和3
2.如果紧贴数字子串的左侧出现字符"-",连续出现的数量为奇数时，数字视为负，连续出现的数量为偶数时，数字视为正，例如，"A-1BC--12"，其中包含数字为-1和12
例如：str="A1CD2E33" 返回36
str="A-1B--2C--D6E"

首先遍历的时候如果遇到数字，则持续走到没数字位置，把连着出现的数字作为一个数，然后往前找它的符号，直到不是"-", 如果-是奇数则取反，否则不变，把这个数加到结果上

```java

    public int getStrSum(String s){
        if(s==null || s.length()==0) return 0;
        int res = 0;
        char[] ca = s.toCharArray();
        for(int i=0;i<ca.length;i++){
            char c = ca[i];
            if(c<'0' || c>'9') continue;
            else{
                int start = i;
                int tmp =0;
                while(i<ca.length && ca[i]>='0' && ca[i]<='9'){
                    tmp = tmp *10 + ca[i]-'0';
                    i++;
                }
                //如果i直接进入下一轮循环，又会加一，相当于多加了，这里减去1个
                i--;

                boolean isNa = false;
                for(int j=start-1; j>=0 && ca[j]=='-'; j--){
                    isNa = !isNa;
                }
                tmp = isNa?-tmp:tmp;
                res += tmp;
            }
        }
        return res;
    }
```

去掉字符串中连续出现的k个0的子串：c-p245

给定一个字符串str和一个整数k，如果str中正好有连续的k个'0'字符出现时，把k个连续的'0'字符去掉，返回处理后的字符串

str='A00B' k=2 返回 AB
str='A0000B000' k=3 返回"A0000B"

遍历，设一个记录累计0的个数cnt，当是0时+1，不是0时检查这个cnt是否为k，若为k则把答案串的后k个字符删除掉。 做完上述操作后，cnt重置为0。
把字符加入答案集中。这样做可能会漏掉最后的部分，遍历完后再看一次cnt是否为k，是则删除答案串的后k个字符

时间复杂度O(n), 空间复杂度O(1) （为了方便用了toCharArray，其实可以不用）

```java
    public String removeSerialK(String s, int k){
        if(s==null || s.length()==0) return s;
        char[] array = s.toCharArray();
        int cnt = 0;
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<array.length;i++){
            if(array[i]=='0'){
                cnt++;
            }else{
                if(cnt==k){
                    builder.delete(builder.length()-k, builder.length());
                }
                cnt = 0;
            }
            builder.append(array[i]);
        }
        if(cnt==k){
            builder.delete(builder.length()-k, builder.length());
        }
        return builder.toString();
    }

```

判断两个字符是否互为旋转词：c-p247
判断一个字符串str，把字符串str前面任意部分挪到后面形成的字符串叫做str的旋转词，如str="12345",str的旋转词有"12345", "23451", "34512", "45123" 和"51234"。给定两个字符串a和b，请判断a和b是否互为旋转词

把a放入一个builder中，然后每次把开头的字符放到后面，每次放1个，共放len-2次，每次看builder.substring(i+1)与b是否相等,这里不要delete，因为删除会引起builder底层的数组进行重新组织，很费时间。

```java
    public boolean isRotatingWord(String a, String b){
        if(a==null || b==null ||a.length()==0 || b.length()==0){
            return false;
        }
        int len = a.length();
        StringBuilder builder = new StringBuilder(a);
        for(int i=0;i<len-1;i++){
            builder.append(builder.charAt(i));
            if(builder.substring(i+1).equals(b)) return true;
        }
        return false;
    }
```

替换字符串中连续出现的指定字符串：c-p251
给定三个字符串 str，from，to，把str中所有的from子串全部替换成to，对于连续出现from的部分要求只替换成一个to
如 str=123abc from=abc to=4567  返回1234567
str=123 from=abc to=456 返回123
str=123abcabc from=abc to=X 返回123X

遍历str，对于每一个位置i，看substring(i)是不是以from开头的，如果是，那么标记一个flag=true，并且把to加到答案中
如果flag已经为true，则直接跳过这么长的，继续看下一个，如果不是，则让flag=false，
如果不是，则把当前的这个字符加到答案中

```java
    public String replace(String str, String from, String to){
        if(str==null || str.length()<from.length()) return str;
        int fromLen = from.length();
        boolean flag = false;
        StringBuilder builder = new StringBuilder();
        
        for(int i=0;i<str.length();i++){
            if(!str.substring(i).startsWith(from)){
                flag = false;
                builder.append(str.charAt(i));

            }else{
                if(!flag){
                    builder.append(to);
                    flag=true;
                    i += fromLen-1;
                }else{
                    i += fromLen-1;
                }
            }
        }
        return builder.toString();
    }

```

字符串的统计字符串：c-p253.1
给定一个字符串，返回str的统计字符串，例如，aaabbadddffc的统计字符串为a3b2a1d3f2c1
遍历，维持一个遍历的数字cnt，和前一个字符pre，如果cur==pre，则继续cnt++，否则，输出cnt+pre
然后让cnt=1，pre=cur，这样最后的字符需要额外再加一遍

```java
    public String statisString(String s){
        if(s==null) return null;
        StringBuilder ans = new StringBuilder();
        int cnt = 1;
        char pre = s.charAt(0);
        for(int i=1;i<s.length();i++){
            char cur = s.charAt(i);
            if(cur!=pre){
                ans.append(pre).append("_").append(cnt);
                cnt = 1;
                pre = cur;
            }else{
                cnt++;
            }
        }
        ans.append(pre).append("_").append(cnt);
        return ans.toString();
    }
```

c-p253.2
给定一个字符串的统计字符串，再给定一个整数index，返回cstr所代表的原始字符串上的第index字符，例如 "a_1_b_100",所代表的原始字符串上第0个字符是'a',第50个字符是'b'

把cstr按"_ " 分割成字符串数组，则第偶数位一定是字符，奇数位一定是数字 （因为从0开始的）
i从1开始，i=i+2;
用一个cnt记录当前到多少个字符了，cnt += strs[i] 若index+1 <= cnt 则字符就是strs[i-1];

```java
    public char getChar(String cstr, int index){
        String[] strs = cstr.split("_");
        char c = '\0';
        int cnt=0;
        for(int i=1;i<strs.length;i+=2){
            int tmp = Integer.valueOf(strs[i]);
            cnt += tmp;
            if(index+1<=cnt){
                c = strs[i-1].charAt(0);
                break;
            }
        }
        return c;
    }
```


判断字符数组中是否所有字符都只出现过一次：c-p255
给定一个字符类型数组chas[]，判断chas中是否所有的字符都只出现过一次
如chas['a','b','c'] 返回true，chas['1','2','1']返回false

时间和空间O(N)的做法：用HashSet即可，很简单
空间O(1):将chas排序，排序后如果一样的就一定在一起，关键是要用空间复杂度O(1)的排序算法：
其实只有冒泡空间复杂度是O(1),堆排和快排由于是递归，空间复杂度是O(logn),但冒泡的时间效率低于快排和堆排



```java

    public boolean isUnique(char[] chas){
//        qSort(chas, 0, chas.length-1);
//        bubbleSort(chas);
        heapSort(chas);

        char pre = chas[0];
        for(int i=1;i<chas.length;i++){
            if(pre==chas[i]) return false;
            pre=chas[i];
        }
        return true;
    }

    public void heapSort(char[] chas){
        int n = chas.length;
        //一定要记得，堆排序建堆必须倒着遍历
        for(int i=n/2-1;i>=0;i--){
            heapify(chas, i, n);
        }

        for(int i=n-1;i>=0;i--){
            char tmp = chas[0];
            chas[0] = chas[i];
            chas[i]=tmp;
            heapify(chas, 0, i);
        }
    }

    public void heapify(char[] chas, int i, int n){
        if(i>=n) return;
        int left = i*2+1;
        int right = i*2+2;

        int tmp = i;
        if(left<n && chas[tmp]<chas[left]){
            tmp = left;
        }
        if(right<n && chas[tmp]<chas[right]){
            tmp = right;
        }
        if(tmp==i) return;
        else {
            char t = chas[i];
            chas[i] = chas[tmp];
            chas[tmp] = t;

            heapify(chas, tmp, n);
        }

    }


    public void bubbleSort(char[] chas){
        boolean change = false;
        for(int i=0;i<chas.length;i++){
            change = false;
            for(int j=chas.length-1;j>i;j--){
                if(chas[j-1]>chas[j]){
                    char tmp = chas[j];
                    chas[j]=chas[j-1];
                    chas[j-1]=tmp;
                    change=true;
                }
            }
            if(!change)
                return;
        }
    }

    public void qSort(char[] chas, int start, int end){
        if(start>=end) return;
        int index = partition(chas, start, end);
        qSort(chas, start, index-1);
        qSort(chas, index+1, end);
    }


    public int partition(char[] chas, int low, int high){
        char c = chas[low];
        while(low<high){
            while(chas[high]>=c && high>low){
                high--;
            }
            chas[low]=chas[high];
            while(chas[low]<=c && low<high){
                low++;
            }
            chas[high]=chas[low];
        }
        chas[low]=c;
        return low;
    }

```























********************** dxc 多线程题 **************************

2个线程交替打印1到10
```java

    void alternatePrint(){
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(0);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=1;i<=10;i+=2){
                    try {
                        s1.acquire();
                        System.out.println("t1:"+i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        s2.release();
                    }
                }
            }
        });
        t1.start();


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=2;i<=10;i+=2){
                    try {
                        s2.acquire();
                        System.out.println("t2:"+i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        s1.release();
                    }
                }
            }
        });
        t2.start();
    }
```




