```java
    第四题
    //m为打了多少枪，n为几种颜色
    //复杂度=O(mn)
    //核心思想是，当某一枪称为最少枪数范围内的最后一枪时，击中其他颜色的气球的枪位置一定离它最近，
    // 所以不断覆盖某一种气球的最新位置，并计算如果该次是最小次数的最后一枪，对应的最小次数是几

public class Solution{
    public test(){
        Scanner cin = new Scanner(System.in);
        int m,n;
        m = cin.nextInt();
        n = cin.nextInt();
        int[] shot = new int[m];
        for(int i=0;i<m;i++){
            shot[i] = cin.nextInt();
        }
        int ans = minDistance(m,n,shot);
        System.out.println(ans);

    }

    public int minDistance(int m, int n, int[] shot) {
        int[] locWithLastShot = new int[n+1]; //颜色和下标一一对应，locWithLastShot[i]记录颜色i出现过的最近的位置
        for(int i=0;i<n+1;i++){
            locWithLastShot[i] = -1;
        }

        int minDis = Integer.MAX_VALUE;
        for(int i=0;i<m;i++){
            int color = shot[i];
            locWithLastShot[color] = i;

            int maxLoc = -1212; //随便取个比-1小的负值
            int minLoc = Integer.MAX_VALUE;
            int validCount = 0; //确保每种气球都有
            for(int j=1;j<n+1;j++){
                if(locWithLastShot[j]==-1){
                    break;
                }else {
                    validCount++;
                    maxLoc = Math.max(maxLoc, locWithLastShot[j]);
                    minLoc = Math.min(minLoc, locWithLastShot[j]);
                }
            }
            if(validCount==n){
                minDis = Math.min(minDis, maxLoc-minLoc+1);
            }

        }
        return minDis==Integer.MAX_VALUE?-1:minDis;
    }

}



```