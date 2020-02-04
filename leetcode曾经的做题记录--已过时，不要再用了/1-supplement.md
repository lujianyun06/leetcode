35. Search Insert Position
Easy

Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You may assume no duplicates in the array.

Example 1:

Input: [1,3,5,6], 5
Output: 2
Example 2:

Input: [1,3,5,6], 2
Output: 1
Example 3:

Input: [1,3,5,6], 7
Output: 4
Example 4:

Input: [1,3,5,6], 0
Output: 0

给一个排好序的数组，返回给定值的位置，如果没有该值，给出如果插入它时的位置
如果有，返回位置；如果么有，返回第一个比它大的位置
```java
class Solution {
    public int searchInsert(int[] nums, int target) {

        for(int i=0;i<nums.length;i++){
        	if(nums[i]==target){
        		return i;
        	}else if(nums[i]>target){
        		return i;
        	}
        }
        //到这里说明都比它小
        return nums.length;
    }
}
```

37. Sudoku Solver
Hard

Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:

Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
Empty cells are indicated by the character '.'.


A sudoku puzzle...


...and its solution numbers marked in red.

Note:

The given board contain only digits 1-9 and the character '.'.
You may assume that the given Sudoku puzzle will have a single unique solution.
The given board size is always 9x9.

编写一个程序，通过填充空单元格来解决数独难题。

数独解决方案必须满足以下所有规则:

每个数字1-9必须在每一行中精确地出现一次。
每个数字1-9必须在每一列中精确地出现一次。
每个数字1-9必须在网格的9个3x3子框中精确地出现一次。
空单元格由字符'.'表示。

注意:

给定的板只包含数字1-9和字符'.'。
你可以假设给定的数独游戏将有一个唯一的解。
给定的板大小总是9x9。

解决数独问题

```java
class Solution {
    public void solveSudoku(char[][] board) {
        solve(board);
    }

    boolean solve(char[][] board){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]=='.'){
                    for(char c='1'; c<='9';c++){
                        if(isValid(board, i,j,c)){  //如果该位置可以放c
                            board[i][j]=c;  //放上去
                            if(solve(board)){
                                return true;
                            }else{
                                board[i][j]='.';  //回退
                            }
                        }
                    }
                    //尝试在这个位置上放置所有数字，都无效（都没有返回true），说明此次验证失败
                    return false;
                }
            }
        }
        //因为给的board一定有解，所以只有第一层的调用才能走到这里
        return true;
    }

    //检查字符c是否可以放在row-col处
    boolean isValid(char[][] board, int row, int col, char c){
        for(int i=0;i<9;i++){
            if(board[i][col]==c) return false; //检查该列
            if(board[row][i]==c) return false; //检查该行
            //检查3x3方格，注意这个方格不是以row-col为中心的，而是9x9中的3x3大方格
            //要在i从0到8这个过程中检查这9各方格
            //row-col所在的大方格的列是 col/3，所在大方格的行是 row/3
            //为了能遍历完，行上用i/3,列上用i%3,,如果都用i/3或i%3，那么9次只能遍历3个格子
            if(board[(row/3)*3+i/3][(col/3)*3+i%3]==c) return false;
        }
        return true;
    }
}
```

40. Combination Sum II
Medium

Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.

Each number in candidates may only be used once in the combination.

Note:

All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.
Example 1:

Input: candidates = [10,1,2,7,6,1,5], target = 8,
A solution set is:
[
  [1, 7],
  [1, 2, 5],
  [2, 6],
  [1, 1, 6]
]
Example 2:

Input: candidates = [2,5,2,1,2], target = 5,
A solution set is:
[
  [1,2,2],
  [5]
]

给定一组候选人编号(候选)和一个目标编号(目标)，找出候选人编号与目标编号之和的所有唯一组合。

候选项中的每个数字只能在组合中使用一次。

注意:

所有的数字(包括目标)都是正整数。
解集不能包含重复的组合。

先把数字排序，
用递归，一个一个加着试，每次只加比当前数字大的数字

```java
class Solution {
    //先用set，例如如果出现（[1,1,7]，8）  用List会出现两个[1,7]，用set就可以避免
    Set<List<Integer>> res;
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        res = new HashSet<>();
        Arrays.sort(candidates);
        trackBack(0, candidates, target, 0, new ArrayList<Integer>());
        return new ArrayList<>(res);

    }

    void trackBack(int start, int[] nums, int target, int preSum, List<Integer> list){
        if(preSum==target){
            List<Integer> tmp = new ArrayList<>(list);
            res.add(tmp);
            return;
        }

        for(int i=start;i<nums.length;i++){
            if(target-preSum<nums[i]) return; //比nums[i]大的数没必要再试了
            list.add(nums[i]);
            trackBack(i+1, nums, target, preSum+nums[i], list);
            list.remove(list.size()-1);
        }
    }
}
```

43. Multiply Strings
Medium

Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.

Example 1:

Input: num1 = "2", num2 = "3"
Output: "6"
Example 2:

Input: num1 = "123", num2 = "456"
Output: "56088"
Note:

The length of both num1 and num2 is < 110.
Both num1 and num2 contain only digits 0-9.
Both num1 and num2 do not contain any leading zero, except the number 0 itself.
You must not use any built-in BigInteger library or convert the inputs to integer directly.

给两个字符串表示的整数，求它们的乘积

num1和num2的长度都小于110。
数字1和数字2都只包含0-9。
除了数字0本身之外，num1和num2都不包含任何前导零。
您不能使用任何内置的BigInteger库或直接将输入转换为integer。


对于一个数a，表示为数组a[] 高位在前低位在后， 如 123 = [1,2,3]
与数b的乘积为num，起始可以令num为全0数组

那么 a[i] * b[j] = c， c有两位，高位为c[0],低位为c[1]
把c[0]加到num[i+j]，c[1]加到num[i+j+1]，当所有a中数字和b中数字乘完之后的结果就是最终的乘积

详情见 

https://leetcode.com/problems/multiply-strings/discuss/17605/Easiest-JAVA-Solution-with-Graph-Explanation，

其实就是把每一位的竖式乘法再分步

```java
class Solution {
    public String multiply(String num1, String num2) {
        StringBuilder builder = new StringBuilder();
        int len1 = num1.length();
        int len2 = num2.length();
        int[] num = new int[len1+len2]; //总长度不会超过len1+len2
        if(num1.equals("0") || num2.equals("0")) return "0";

        for(int i=0;i<len1;i++){
            for(int j=0;j<len2;j++){
                int a = num1.charAt(i)-'0';
                int b = num2.charAt(j)-'0';
                String s = String.valueOf(a * b);
                int c0=0;
                int c1=0;
                if(s.length()>1){
                    c0 = s.charAt(0)-'0';
                    c1 = s.charAt(1)-'0';
                }else {
                    c1 = s.charAt(0)-'0';
                }
                //把当前俩数字计算的乘积分位加到结果数组中
                num[i+j] += c0;
                num[i+j+1] += c1;

            }
        }

        int c = 0; //进位
        //从最低位开始加，别忘了可能产生进位
        for(int i=len1+len2-1;i>=0;i--){
            int tmp = c + num[i];
            c = tmp/10;
            builder.insert(0,tmp%10);
        }

        //如果第一个数字是0，删去，循环直到第一个数子不是0
        while (builder.indexOf("0")==0){
            builder.deleteCharAt(0);
        }

        //去掉前面的0,能到这里已经不存在只为0的情况了

        return builder.toString();
    }
}
```

45. Jump Game II
Hard

Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Your goal is to reach the last index in the minimum number of jumps.

Example:

Input: [2,3,1,1,4]
Output: 2
Explanation: The minimum number of jumps to reach the last index is 2.
    Jump 1 step from index 0 to 1, then 3 steps to the last index.
Note:

You can assume that you can always reach the last index.

给定一个非负整数数组，初始位置是数组的第一个索引。

数组中的每个元素表示该位置的最大跳转长度。

您的目标是在最少的跳跃次数中达到最后一个索引。

首先想到的是用动态规划
dp[i] 表示从0位置跳到i的最少次数
dp[i]的算法：
```java
dp[i] = Math.MAX_VALUE;
for(int j=0;j<i;j++){
    if(num[j] >= i-j){
        dp[i] = Math.min(dp[j]+1, dp[i]);
    }
}
```
dp[0]=0;
dp[1]=1;//最少一定是1
每次让j用遍历0到i，即时间复杂度为O(n^2)，会超时

这里用一种巧妙并且不太容易理解的贪心算法可以达到O(N)的时间复杂度，
只不过在算法中要记录当前一跳所能到达的最远距离、上一跳所能到达的最远距离，
和当前所使用跳数就可以了。另外需要注意的一点是：题意要求不一定非得跳到last index，越过去也算，这点需要特别强调。

https://blog.csdn.net/wusecaiyun/article/details/47041057

## 不是很懂，MARK，下次再看


```java
class Solution {
    //超时！！！！
    public int jump(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0]=0;
        for(int i=1;i<nums.length;i++){
            dp[i] = Math.MAX_VALUE;
            for(int j=0;j<i;j++){
                if(num[j]>= (i-j))
                    dp[i] = Math.min(dp[j]+1, dp[i]);
            }
        }
        return dp[dp.length-1];
    }

    //贪心算法
    //cur表示最远能覆盖到的地方，用红色表示。last表示已经覆盖的地方，用箭头表示。起始时，它们都指向第一个元素。
    public int jump(int[] nums) {
        int ret = 0;//当前跳数  
        int last = 0;//上一跳可达最远距离  
        int cur = 0;//当前一跳可达最远距  
        int n = nums.length;
        for (int i = 0; i < n; ++i) {  
            //无法向前继跳直接返回  
            if(i>cur){  //有可能无论怎么跳，都不能到达终点或者越过终点，比如[3,2,1,0,4]。 但是题目里面没有这种情况
                return -1;  
            }  
            //要不就不跳，要跳就跳到最远的地方
            //需要进行下次跳跃，则更新last和当执行的跳数ret，只有当前位置大于了上一次能到的最远距离，才进行跳跃
            if (i > last) {  
                last = cur;  
                ++ret;  
            }  
            //记录当前可达的最远点的位置索引  
            cur = Math.max(cur, i+nums[i]);  
        }  
  
        return ret;  
    }
}
```

47. Permutations II
Medium

Given a collection of numbers that might contain duplicates, return all possible unique permutations.

Example:

Input: [1,1,2]
Output:
[
  [1,1,2],
  [1,2,1],
  [2,1,1]
]

给定一组可能包含重复项的数字，返回所有可能的惟一排列。

还是用trackBack和Set,用一个标志数组表示当前数是否已经被算进去

```java
class Solution {
    Set<List<Integer>> res;
    public List<List<Integer>> permuteUnique(int[] nums) {
        res = new HashSet<>();
        trackBack(new boolean[nums.length], nums, 0, new ArrayList<>());
        return new ArrayList<>(res);

    }

    void trackBack(boolean[] flag, int[] nums, int count, List<Integer> list){
        if(count==nums.length){
            List<Integer> tmp = new ArrayList<>(list);
            res.add(tmp);
        }

        for(int i=0;i<nums.length;i++){
            if(flag[i]) continue;
            list.add(nums[i]);
            flag[i]=true;
            trackBack(flag, nums, count+1, list);
            list.remove(list.size()-1);
            flag[i]=false;
        }
    }
}
```

51. N-Queens
Hard

The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.

Given an integer n, return all distinct solutions to the n-queens puzzle.

Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space respectively.

Example:

Input: 4
Output: [
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above.

n-皇后难题是将n个皇后放在n×n的棋盘上，使没有两个皇后互相攻击。

给定一个整数n，返回n-queens谜题的所有不同解。

每个解决方案包含一个不同的董事会配置的n皇后的位置，其中的'Q'和'。“这两个字母分别表示女王和空格。

每个皇后周围的8个格子内不能有别的皇后,而且每一列，每一行，每一斜线只能有一个皇后

```java
class Solution {

    List<List<String>> res = new ArrayList<>();
    public List<List<String>> solveNQueens(int n) {
        //先用二维数组，好操作一点
        char[][] board = new char[n][n];
        for(int i=0;i<n;i++){
            Arrays.fill(board[i], '.');
        }
        trackBack(board, n, 0, 0);
        return res;
    }

    //显然一行只能有一个皇后
    void trackBack(char[][] board, int n, int row, int queenCount){
        if(queenCount>n) return;
        if(queenCount==n){
            addOneList(board, n);
            return;
        }

        for(int j=0;j<n;j++){
            if(isValid(row, j,board,n)){
                board[row][j]='Q';
                trackBack(board, n,row+1,queenCount+1);
                board[row][j]='.';
            }
        }
    }

    StringBuilder s = new StringBuilder();
    //把当前的二维数组转成List
    void addOneList(char[][] board, int n){
        List<String> tmp = new ArrayList<>(n);
        for(int i=0;i<n;i++){
            s.delete(0, s.length());
            for(int j=0;j<n;j++){
                s.append(board[i][j]);
            }
            tmp.add(s.toString());
        }
        res.add(tmp);
    }

    //放到外面减少内存使用
    int[][] direct = new int[][]{{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
    //该格子四周是否有皇后
    boolean isValid(int i, int j, char[][] board, int n){
        //判断四周是否有皇后
        for(int k=0;k<8;k++){
            int x = i+direct[k][0];
            int y = j+direct[k][1];
            if(x>=0 && x<n && y>=0 && y <n){
                if(board[x][y]=='Q')
                    return false;
            }
        }
        //由于上面的tackBack能保证一行只有一个皇后，所以这里只需要判断该列是否有别的皇后即可
        for(int k=0;k<n;k++){
            if(board[k][j]=='Q') return false;
        }

        //再算两条斜线上的,分别有右上，右下，左上，左下四种
        int[] rightTop = new int[2];
        rightTop[0]=i;rightTop[1]=j;
        int[] rightBottom = new int[2];
        rightBottom[0]=i;rightBottom[1]=j;
        int[] leftTop = new int[2];
        leftTop[0]=i;leftTop[1]=j;
        int[] leftBottom = new int[2];
        leftBottom[0]=i;leftBottom[1]=j;

        for(int k=0;k<n;k++){
            rightTop[0]--;
            rightTop[1]++;
            if(rightTop[0] >= 0 && rightTop[0] < n && rightTop[1] >= 0 && rightTop[1] < n && board[rightTop[0]][rightTop[1]]=='Q')
                return false;

            rightBottom[0]++;
            rightBottom[1]++;
            if(rightBottom[0] >= 0 && rightBottom[0] < n && rightBottom[1] >= 0 && rightBottom[1] < n && board[rightBottom[0]][rightBottom[1]]=='Q')
                return false;

            leftTop[0]--;
            leftTop[1]--;
            if(leftTop[0] >= 0 && leftTop[0] < n && leftTop[1] >= 0 && leftTop[1] < n && board[leftTop[0]][leftTop[1]]=='Q')
                return false;

            leftBottom[0]++;
            leftBottom[1]--;
            if(leftBottom[0] >= 0 && leftBottom[0] < n && leftBottom[1] >= 0 && leftBottom[1] < n && board[leftBottom[0]][leftBottom[1]]=='Q')
                return false;
        }

        return true;
    }
}
```

52. N-Queens II
Hard

The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.

Given an integer n, return the number of distinct solutions to the n-queens puzzle.

Example:

Input: 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown below.
[
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]

n皇后2，返回的不是结果集，而是个数，和上面的一脉相承
把结果集改成个数即可

```java
class Solution {

    int res = 0;
    public int totalNQueens(int n) {
        //先用二维数组，好操作一点
        char[][] board = new char[n][n];
        for(int i=0;i<n;i++){
            Arrays.fill(board[i], '.');
        }
        trackBack(board, n, 0, 0);
        return res;
    }

    //显然一行只能有一个皇后
    void trackBack(char[][] board, int n, int row, int queenCount){
        if(queenCount>n) return;
        if(queenCount==n){
            res++;
            return;
        }

        for(int j=0;j<n;j++){
            if(isValid(row, j,board,n)){
                board[row][j]='Q';
                trackBack(board, n,row+1,queenCount+1);
                board[row][j]='.';
            }
        }
    }

    //放到外面减少内存使用
    int[][] direct = new int[][]{{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
    //该格子四周是否有皇后
    boolean isValid(int i, int j, char[][] board, int n){
        //判断四周是否有皇后
        for(int k=0;k<8;k++){
            int x = i+direct[k][0];
            int y = j+direct[k][1];
            if(x>=0 && x<n && y>=0 && y <n){
                if(board[x][y]=='Q')
                    return false;
            }
        }
        //由于上面的tackBack能保证一行只有一个皇后，所以这里只需要判断该列是否有别的皇后即可
        for(int k=0;k<n;k++){
            if(board[k][j]=='Q') return false;
        }

        //再算两条斜线上的,分别有右上，右下，左上，左下四种
        int[] rightTop = new int[2];
        rightTop[0]=i;rightTop[1]=j;
        int[] rightBottom = new int[2];
        rightBottom[0]=i;rightBottom[1]=j;
        int[] leftTop = new int[2];
        leftTop[0]=i;leftTop[1]=j;
        int[] leftBottom = new int[2];
        leftBottom[0]=i;leftBottom[1]=j;

        for(int k=0;k<n;k++){
            rightTop[0]--;
            rightTop[1]++;
            if(rightTop[0] >= 0 && rightTop[0] < n && rightTop[1] >= 0 && rightTop[1] < n && board[rightTop[0]][rightTop[1]]=='Q')
                return false;

            rightBottom[0]++;
            rightBottom[1]++;
            if(rightBottom[0] >= 0 && rightBottom[0] < n && rightBottom[1] >= 0 && rightBottom[1] < n && board[rightBottom[0]][rightBottom[1]]=='Q')
                return false;

            leftTop[0]--;
            leftTop[1]--;
            if(leftTop[0] >= 0 && leftTop[0] < n && leftTop[1] >= 0 && leftTop[1] < n && board[leftTop[0]][leftTop[1]]=='Q')
                return false;

            leftBottom[0]++;
            leftBottom[1]--;
            if(leftBottom[0] >= 0 && leftBottom[0] < n && leftBottom[1] >= 0 && leftBottom[1] < n && board[leftBottom[0]][leftBottom[1]]=='Q')
                return false;
        }

        return true;
    }
}
```

57. Insert Interval
Hard

Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

You may assume that the intervals were initially sorted according to their start times.

Example 1:

Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
Example 2:

Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.

给定一组不重叠的区间，在区间中插入一个新的区间(如果需要合并则合并)。

您可以假设这些区间最初是根据它们的开始时间排序的。

遍历所有区间和新区间，如果当前遍历到的区间和新区间有重合，则将新区间更新为并集，然后继续这个过程，期间把没有重合的单另放出去
最终的结果就是新区间以及没有重合的区间，只需要排序并放入答案数组中即可

```java
class Solution {
   public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> unOverlapIntervals = new ArrayList<>(); //没有重合部分的区域
        //新区间
        int[] overlapInterval = new int[2];
        overlapInterval[0] = newInterval[0];
        overlapInterval[1] = newInterval[1];

        for(int[] interval: intervals){
            //每次都比较当前区间和一已经重叠过的区间
            if(isOverlap(interval, overlapInterval)){
                //更新overlapInterval
                overlapInterval[0] = Math.min(overlapInterval[0], interval[0]);
                overlapInterval[1] = Math.max(overlapInterval[1], interval[1]);
            }else{
                unOverlapIntervals.add(interval);
            }
        }
        List<int[]> newIntervals = new ArrayList<>(unOverlapIntervals);
        newIntervals.add(overlapInterval);
        newIntervals.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]!=o2[0]) return o1[0]-o2[0];
                else  return o1[1]-o2[1];
            }
        });
        int[][] res = new int[newIntervals.size()][2];
        for(int i=0;i<newIntervals.size();i++){
            res[i] = newIntervals.get(i);
        }
        return res;

    }

    boolean isOverlap(int[] interval1, int[] interval2){
        if(interval1[1] < interval2[0] || interval1[0] > interval2[1]) return false;
        return true;
    }
}
```

58. Length of Last Word
Easy

Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word in the string.

If the last word does not exist, return 0.

Note: A word is defined as a character sequence consists of non-space characters only.

Example:

Input: "Hello World"
Output: 5

给定一个字符串s由大写/小写字母和空空格字符' '组成，返回字符串中最后一个单词的长度。

如果最后一个单词不存在，返回0。

注意:单词被定义为由非空格字符组成的字符序列。

```java
class Solution {
    public int lengthOfLastWord(String s) {
        if(s.length()==0) return 0;
        String[] strs = s.split(" ");
        if(strs.length==0) return 0;
        return strs[strs.length-1].length();
    }
}
```

59. Spiral Matrix II
Medium

Given a positive integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.

Example:

Input: 3
Output:
[
 [ 1, 2, 3 ],
 [ 8, 9, 4 ],
 [ 7, 6, 5 ]
]

给一个数字n，产生一个矩阵，矩阵是螺旋式的
仍然是按照 右、下、左、上的顺序，一到边界就换方向，边界值缩小

```java
class Solution {
    //0,1,2,3分别代表右、下、左、上的顺序
   public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int curDir = 0;
        int rightBound = n-1, downBound = n-1, leftBound = 0, upBound = 1;
        int x=0, y=0;
        int i = 1;
        while(i<=n*n){
            while(curDir==0 && y<=rightBound){
                res[x][y] = i++;
                if(y==rightBound){
                    curDir = (curDir + 1) % 4;
                    rightBound--;
                    x++;
                    break;
                }
                y++;
            }
            while(curDir==1 && x<=downBound){
                res[x][y] = i++;
                if(x==downBound){
                    curDir = (curDir + 1) % 4;
                    downBound--;
                    y--;
                    break;
                }
                x++;
            }
            while(curDir==2 && y>=leftBound){
                res[x][y] = i++;
                if(y==leftBound){
                    curDir = (curDir + 1) % 4;
                    leftBound++;
                    x--;
                    break;
                }
                y--;
            }
            while(curDir==3 && x>=upBound){
                res[x][y] = i++;
                if(x==upBound){
                    curDir = (curDir + 1) % 4;
                    upBound++;
                    y++;
                    break;
                }
                x--;
            }
        }
        return res;
    }
}
```

60. Permutation Sequence
Medium

The set [1,2,3,...,n] contains a total of n! unique permutations.

By listing and labeling all of the permutations in order, we get the following sequence for n = 3:

"123"
"132"
"213"
"231"
"312"
"321"
Given n and k, return the kth permutation sequence.

Note:

Given n will be between 1 and 9 inclusive.
Given k will be between 1 and n! inclusive.
Example 1:

Input: n = 3, k = 3
Output: "213"
Example 2:

Input: n = 4, k = 9
Output: "2314"


集(1、2、3……，n]共包含n!独特的排列。将所有排列按顺序列出并标注，得到n = 3的序列如下:
"123"
"132"
"213"
"231"
"312"
"321"
给定n和k，返回第k个排列序列。

n从1到9，k从1到n!

以数字x开头个数有 (n-1)!个 再里面以数字y开头的个数有 (n-2)!个。。。

将9个数字依次放入一个list 
(k/(n-1)!)是该排列的第一个数字,令为i，在list中顺序选出第i个数字，选出后，从list中去掉该数字
让k = k%((n-1)) , n=n-1 重复上面的过程

```java
class Solution {
    HashMap<Integer, Integer> map;
    public String getPermutation(int n, int k) {
        StringBuilder builder = new StringBuilder();
        List<Integer> list = new ArrayList<>();
        for(int i=1;i<10;i++)
            list.add(i);

        int count = n;
        //为了顺序从0开始，k先-1
        k = k-1;
        while(count>0){
            int num = k/factorial(n-1);
            builder.append(list.get(num));
            //每加入一个数，就从list中删掉该数
            list.remove(num);
            k = k%factorial(n-1);
            n = n-1;
            count--;
        }
        return builder.toString();
    }

    //计算阶乘的函数
    public int factorial(int number) {
        if (number <= 1)
            return 1;
        else
            return number * factorial(number - 1);
    }
}    
```

106. Construct Binary Tree from Inorder and Postorder Traversal
Medium

Given inorder and postorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.

For example, given

inorder = [9,3,15,20,7]
postorder = [9,15,7,20,3]
Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7

给出中序和后序遍历的顺序，构造二叉树,二叉树中没有相同的值

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTree(inorder, 0, inorder.length-1, postorder, 0, postorder.length-1);
    }

    public TreeNode buildTree(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {

        if(inStart>inEnd || postStart>postEnd || (inStart<0||inStart>=inorder.length) ||
                (inEnd<0||inEnd>=inorder.length) || (postStart<0||postStart>=inorder.length) ||
                (postEnd<0||postEnd>=inorder.length))
            return null;

        TreeNode node = new TreeNode(postorder[postEnd]);
        int rootIn = 0;
        for(int i=0;i<inorder.length;i++){
            if(node.val==inorder[i]){
                rootIn = i;
                break;
            }
        }
        int leftChildCount = rootIn-inStart;
        int rightChildCount = inEnd-rootIn;
        node.left = buildTree(inorder, inStart, rootIn-1, postorder, postStart, postStart+leftChildCount-1);
        node.right = buildTree(inorder, rootIn+1, inEnd, postorder, postStart+leftChildCount, postEnd-1);
        return node;
    }
}
```

107. Binary Tree Level Order Traversal II
Easy

Given a binary tree, return the bottom-up level order traversal of its nodes' values. 
(ie, from left to right, level by level from leaf to root).

For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its bottom-up level order traversal as:
[
  [15,7],
  [9,20],
  [3]
]

给定一个二叉树，返回其节点值的自底向上顺序遍历。
(即从左到右，从叶到根，一层一层地)。

层序遍历，每一层的list都放入一个list，然后倒序这个list即可

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root==null)
            return res;
            LinkedList<TreeNode> queue1 = new LinkedList<>();
        LinkedList<TreeNode> queue2 = new LinkedList<>();

        queue1.offer(root);
        res.add(convertList(queue1));
        while(!queue1.isEmpty() || !queue2.isEmpty()){
            LinkedList<TreeNode> cur = queue1.isEmpty()?queue2:queue1;
            LinkedList<TreeNode> another = queue1.isEmpty()?queue1:queue2;
            while(!cur.isEmpty()){
                TreeNode node = cur.poll();
                if(node.left!=null) another.offer(node.left);
                if(node.right!=null) another.offer(node.right);
            }
            if (another.size()>0)
                res.add(convertList(another));
        }
        Collections.reverse(res);
        return res;
    }

    List<Integer> convertList(List<TreeNode> list){
        List<Integer> one = new ArrayList<>();
        for(TreeNode node : list){
            one.add(node.val);
        }
        return one;
    }
}
```

61. Rotate List
Medium

Given a linked list, rotate the list to the right by k places, where k is non-negative.

Example 1:

Input: 1->2->3->4->5->NULL, k = 2
Output: 4->5->1->2->3->NULL
Explanation:
rotate 1 steps to the right: 5->1->2->3->4->NULL
rotate 2 steps to the right: 4->5->1->2->3->NULL
Example 2:

Input: 0->1->2->NULL, k = 4
Output: 2->0->1->NULL
Explanation:
rotate 1 steps to the right: 2->0->1->NULL
rotate 2 steps to the right: 1->2->0->NULL
rotate 3 steps to the right: 0->1->2->NULL
rotate 4 steps to the right: 2->0->1->NULL

把链表中的后面k各节点提到前面来,如果全用数组做，会涉及到大量数组元素的拷贝，会很慢
用数组+链表的形式，把节点都放到数组中，在数组中操作它们的next的关系，因为是顺序移动，
不会涉及跨很多步的移动，所以这样做很方便

当k大于len时，对链表的操作就和之前的重复了，即对于上面k=4的情况，step1和step4的情况完全相同（因为len=3），
所以只需要取k=k%len计算

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {

    public ListNode rotateRight(ListNode head, int k) {
        if(head==null) return null;
        int len = 0;
        //计算出链表长度
        ListNode node = head;
        while(node!=null){
            len++;
            node = node.next;
        }
        if(len==1) return head;
        ListNode[] nodes = new ListNode[len];
        node=head;
        for(int i=0;i<len;i++){
            nodes[i]=node;
            node = node.next;
        }

        //取k=k%len,因为后面都是重复的
        k = k%len;
        if(k==0) return head;
        ListNode dummy = new ListNode(0);
        for(int i=0;i<len;i++){
            if(i>=len-k){
                if(i==len-1) nodes[i].next = head;
                else nodes[i].next = nodes[i+1];
                if(i==len-k){
                    dummy.next = nodes[i];
                }
            }
        }

        for(int i=0;i<len;i++){
            if(i==len-k-1){
                nodes[i].next=null;
            }
        }
        return dummy.next;
    }
}
```

63. Unique Paths II
Medium

A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

Now consider if some obstacles are added to the grids. How many unique paths would there be?

An obstacle and empty space is marked as 1 and 0 respectively in the grid.

Note: m and n will be at most 100.


Example 1:

Input:
[
  [0,0,0],
  [0,1,0],
  [0,0,0]
]
Output: 2
Explanation:
There is one obstacle in the middle of the 3x3 grid above.
There are two ways to reach the bottom-right corner:
1. Right -> Right -> Down -> Down
2. Down -> Down -> Right -> Right

机器人位于mxn网格的左上角(下图中标记为“Start”)。

机器人只能在任何时间点向下或向右移动。机器人正试图到达网格的右下角(下图中标记为“Finish”)。

现在考虑一下是否向网格中添加了一些障碍。有多少条唯一路径?

在网格中，障碍物和空地分别标记为1和0。

注意:m和n最多为100。

dp[i][j] 代表到达(i,j)的唯一路径的个数，每个位置只能由上方或左方移动而来
dp[i][j] = dp[i-1][j] + dp[i][j-1]
对于任何nums[i][j]=1，则dp[i][j-1]=0

```java
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int row = obstacleGrid.length;
        int col = obstacleGrid[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = 1;
        for(int i=0; i<row;i++){
            for(int j=0;j<col;j++){
                if(obstacleGrid[i][j]==1) dp[i][j]=0;
                else{
                    if(i-1>=0) dp[i][j] += dp[i-1][j];
                    if(j-1>=0) dp[i][j] += dp[i][j-1];
                }
            }
        }
        return dp[row-1][col-1];
    }
}
```

109. Convert Sorted List to Binary Search Tree
Medium

Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.

For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

Example:

Given the sorted linked list: [-10,-3,0,5,9],

One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

      0
     / \
   -3   9
   /   /
 -10  5


给定一个单链表，其中元素按升序排序，将其转换为高度平衡的BST。

对于该问题，高度平衡二叉树定义为每个节点的两个子树深度相差不超过1的二叉树。

考虑把单链表节点放到数组中，对数组操作要更简单

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    ListNode[] nodes;
    public TreeNode sortedListToBST(ListNode head) {
        ListNode node = head;
        int len =0;
        while(node!=null){
            node = node.next;
            len++;
        }
        nodes = new ListNode[len];
        node = head;
        for(int i=0;i<len;i++){
            nodes[i] = node;
            node = node.next;
        }

        return buildTree(0, nodes.length-1);

    }

    TreeNode buildTree(int start, int end){
        if(start > end) return null;
        //每次取中间的值作为根
        int mid = (start + end ) / 2;
        TreeNode root = new TreeNode(nodes[mid].val);
        root.left = buildTree(start, mid-1);
        root.right = buildTree(mid+1, end);
        return root;
    }
}

```

110. Balanced Binary Tree
Easy

Given a binary tree, determine if it is height-balanced.

For this problem, a height-balanced binary tree is defined as:

a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

Example 1:

Given the following tree [3,9,20,null,null,15,7]:

    3
   / \
  9  20
    /  \
   15   7
Return true.

Example 2:

Given the following tree [1,2,2,3,3,null,null,4,4]:

       1
      / \
     2   2
    / \
   3   3
  / \
 4   4
Return false.

给定一个二叉树，判断它是否高度平衡。

对于该问题，定义高度平衡二叉树为:

一种二叉树，其中每个节点的两个子树的深度相差不超过1。

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    //用hashMap保存某节点对应的树的高度
    HashMap<TreeNode, Integer> heightMap = new HashMap<>();
    public boolean isBalanced(TreeNode root) {
        if(root==null) return true;
        boolean b1 = Math.abs(getTreeHeight(root.left)-getTreeHeight(root.right))<2;
        boolean b2 = isBalanced(root.left) && isBalanced(root.right);
        return b1 && b2;
    }

    int getTreeHeight(TreeNode root){
        if(root==null) return 0;
        if(heightMap.containsKey(root)) return heightMap.get(root);
        int height = 1 + Math.max(getTreeHeight(root.left), getTreeHeight(root.right));
        heightMap.put(root, height);
        return height;
    }
}
```

111. Minimum Depth of Binary Tree
Easy

Given a binary tree, find its minimum depth.

The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.

Note: A leaf is a node with no children.

Example:

Given binary tree [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
return its minimum depth = 2.

给定一个二叉树，求它的最小深度。

最小深度是从根节点到最近叶子节点的最短路径上的节点数。

注意:叶子是没有子节点的节点。

使用记录层数的层序遍历（两个队列），当遍历到第一个叶子时，返回层数

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int minDepth(TreeNode root) {
        if(root==null) return 0;
        LinkedList<TreeNode> queue1 = new LinkedList<>();
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        queue1.add(root);
        int minLevel = 0;
        while(!queue1.isEmpty() || !queue2.isEmpty()){
            LinkedList<TreeNode> cur = queue1.isEmpty()?queue2:queue1;
            LinkedList<TreeNode> another = queue1.isEmpty()?queue1:queue2;
            minLevel++;
            while(!cur.isEmpty()){
                TreeNode node = cur.poll();
                if(node.left==null && node.right==null){
                    return minLevel;
                }else{
                    if(node.left!=null)another.offer(node.left);
                    if(node.right!=null)another.offer(node.right);
                }
            }

        }
        return 0;
    }
}
```

112. Path Sum
Easy

Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.

Note: A leaf is a node with no children.

Example:

Given the below binary tree and sum = 22,

      5
     / \
    4   8
   /   / \
  11  13  4
 /  \      \
7    2      1
return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.

给定一棵二叉树和一个和，确定该树是否有根到叶的路径，以便将路径上的所有值相加等于给定的和。

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    boolean res;
    public boolean hasPathSum(TreeNode root, int sum) {
        caculateSum(root, 0, sum);
        return res;
    }

    void caculateSum(TreeNode node, int preSum, int targetSum){
        if(node==null) return;
        //找到了就不用再找了
        if(res) return;

        int curSum = preSum + node.val;
        if(node.left==null && node.right==null && curSum==targetSum){
            res = true;
        }else{
            caculateSum(node.left, curSum, targetSum);
            caculateSum(node.right, curSum, targetSum);
        }
    }
}
```

65. Valid Number
Hard

Validate if a given string can be interpreted as a decimal number.

Some examples:
"0" => true
" 0.1 " => true
"abc" => false
"1 a" => false
"2e10" => true
" -90e3   " => true
" 1e" => false
"e3" => false
" 6e-1" => true
" 99e2.5 " => false
"53.5e93" => true
" --6 " => false
"-+3" => false
"95a54e53" => false

Note: It is intended for the problem statement to be ambiguous. You should gather all requirements up front before implementing one. However, here is a list of characters that can be in a valid decimal number:

Numbers 0-9
Exponent - "e"
Positive/negative sign - "+"/"-"
Decimal point - "."
Of course, the context of these characters also matters in the input.

Update (2015-02-10):
The signature of the C++ function had been updated. If you still see your function signature accepts a const char * argument, please click the reload button to reset your code definition.

验证给定字符串是否可以解释为十进制数。

注意:它的目的是使问题语句变得含糊不清。在实现一个需求之前，您应该预先收集所有的需求。然而，这里有一个字符列表，可以在一个有效的十进制数字:

数字0 - 9
指数——“e”
正负号-"+"/"-"
小数点- ".""
当然，这些字符的上下文在输入中也很重要。

一个数字中至多只能有一个e
一个数字中至多只能有一个小数点
自然数：只有数字
合理小数：至多有一个. ,且前后都是合理的自然数
如果有e，后面必须是合理的正负数，前面必须是合理的小数
合理的正负数：只有一个正负号再最前面，且后面是合理的小数

故判断优先级：如果有e，判断左边的数是否是合理的正负数，右边是否是合理的自然数
如果没e，判断是否是合理的正负数

正负数：看符合是否只有一个，如果只有一个，看后面是不是合理小数

```java
class Solution {

    char[] symbolDict = new char[]{'1','2','3','4','5','6','7','8','9', '0', '.', 'e', '+', '-'};
    Set<Character> symbolSet;
    HashMap<Character, Integer> symbolMap;
    public boolean isNumber(String s) {
        s = s.trim(); //去掉首位的空格
        symbolSet = new HashSet<>();
        for (char c: symbolDict){
            symbolSet.add(c);
        }
        symbolMap = new HashMap<>();
        for(int i=0;i<s.length();i++){
            Character c = s.charAt(i);
            //如果是非法符号，直接返回false
            if(!symbolSet.contains(c)) return false;
            symbolMap.put(c, symbolMap.getOrDefault(c, 0)+1);
        }
        //如果有两个以上e或两个以上小数点，直接返回false
        if(symbolMap.getOrDefault('e', 0)>=2 || symbolMap.getOrDefault('.', 0)>=2)
            return false;

        if(symbolMap.getOrDefault('e', 0)==1){
            //有e
            int eIndex = s.indexOf('e');
            try{
                return isSignedDecimal(s.substring(0, eIndex)) && isSignedInteger(s.substring(eIndex+1, s.length()));
            }catch(Exception e){
                return false;
            }
        }else{
            //没有e
            try{
                return isSignedDecimal(s);
            }catch(Exception e){
                return false;
            }
        }
    }

    //是不是合理的带符号小数, 整数也属于小数
    boolean isSignedDecimal(String s){
        if(isEmpty(s)) return false;
        if(s.charAt(0)=='+' || s.charAt(0)=='-'){
            try{
                return isUnsignedDecimal(s.substring(1, s.length()));
            }catch(Exception e){
                return false;
            }
        }else{
            return isUnsignedDecimal(s);
        }
    }

    //是不是合理的不带符号小数， 整数也属于小数, 按答案的讲法，3. 和 .3  都属于合理的小数，真实醉了
    boolean isUnsignedDecimal(String s){
        if(isEmpty(s)) return false;
        int dotIndex = s.indexOf('.');
        if(dotIndex>0){
            try{
                //小数点不是第一个，则点前面必须是数字，后面可以是空，可以是数字
                return isUnsignedInteger(s.substring(0, dotIndex)) &&
                        (isUnsignedInteger(s.substring(dotIndex+1, s.length())) || isEmpty(s.substring(dotIndex+1, s.length())));
            }catch(Exception e){
                return false;
            }
        }else if(dotIndex==0){ //如果第一个字符是点，则只需判断点后面的
            try{
                return isUnsignedInteger(s.substring(1, s.length()));
            }catch(Exception e){
                return false;
            }
        } else{
            return isUnsignedInteger(s);
        }
    }

    //是不是合理的不带符号整数
    boolean isUnsignedInteger(String s){
        if(isEmpty(s)) return false;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c<'0' || c>'9') return false;
        }
        return true;
    }

    //是不是合理的带符号整数
    boolean isSignedInteger(String s){
        if(isEmpty(s)) return false;
        if(s.charAt(0)=='+' || s.charAt(0)=='-'){
            try{
                return isUnsignedInteger(s.substring(1, s.length()));
            }catch(Exception e){
                return false;
            }
        }else{
            return isUnsignedInteger(s);
        }
    }

    boolean isEmpty(String s){
        return s==null || s.length()==0;
    }
}
```

77. Combinations
Medium

Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.

Example:

Input: n = 4, k = 2
Output:
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]

给定两个整数n和k，返回1…n。

又是回退递归

```java
class Solution {
    List<List<Integer>> res;
    public List<List<Integer>> combine(int n, int k) {
        res = new ArrayList<>();
        trackBack(1, new ArrayList<>(), n, k);
        return res;
    }

    void trackBack(int start, List<Integer> preList, int n, int k){
        if(preList.size()==k){
            List<Integer> cclone = new ArrayList(preList);
            res.add(cclone);
            return;
        }

        for(int i=start; i<=n; i++){
            preList.add(i);
            trackBack(i+1, preList, n, k);
            preList.remove(preList.size()-1);
        }
    }
}
```

67. Add Binary
Easy

Given two binary strings, return their sum (also a binary string).

The input strings are both non-empty and contains only characters 1 or 0.

Example 1:

Input: a = "11", b = "1"
Output: "100"
Example 2:

Input: a = "1010", b = "1011"
Output: "10101"

给定两个二进制字符串，返回它们的和(也是一个二进制字符串)。

输入字符串都是非空的，并且只包含字符1或0。

```java
class Solution {
    public String addBinary(String a, String b) {
        //给长度小的最高位补0
        if(a.length()>b.length()){
            int offset = a.length()-b.length();
            for(int i=0;i<offset;i++){
                b = "0" + b;
            }
        }else{
            int offset = b.length()-a.length();
            for(int i=0;i<offset;i++){
                a = "0" + a;
            }
        }

        StringBuilder builder = new StringBuilder();
        int c = 0; //进位符
        for(int i=a.length()-1;i>=0;i--){
            int aa = a.charAt(i)-'0';
            int bb = b.charAt(i)-'0';
            builder.insert(0, (aa^bb^c)+"");
            if(aa+bb+c>=2){
                c = 1;
            }else {
                c = 0;
            }
        }
        if(c==1){
            builder.insert(0, "1");
        }
        return builder.toString();
    }

}
```

68. Text Justification
Hard

Given an array of words and a width maxWidth, format the text such that each line has exactly maxWidth characters and is fully (left and right) justified.

You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.

Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.

For the last line of text, it should be left justified and no extra space is inserted between words.

Note:

A word is defined as a character sequence consisting of non-space characters only.
Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
The input array words contains at least one word.
Example 1:

Input:
words = ["This", "is", "an", "example", "of", "text", "justification."]
maxWidth = 16
Output:
[
   "This    is    an",
   "example  of text",
   "justification.  "
]


Example 2:

Input:
words = ["What","must","be","acknowledgment","shall","be"]
maxWidth = 16
Output:
[
  "What   must   be",
  "acknowledgment  ",
  "shall be        "
]
Explanation: Note that the last line is "shall be    " instead of "shall     be",
             because the last line must be left-justified instead of fully-justified.
             Note that the second line is also left-justified becase it contains only one word.
Example 3:

Input:
words = ["Science","is","what","we","understand","well","enough","to","explain",
         "to","a","computer.","Art","is","everything","else","we","do"]
maxWidth = 20
Output:
[
  "Science  is  what we",
  "understand      well",
  "enough to explain to",
  "a  computer.  Art is",
  "everything  else  we",
  "do                  "
]

给定一个单词数组和一个width maxWidth，对文本进行格式化，使每一行都具有精确的maxWidth字符，并且完全(左对齐和右对齐)。

你应该用贪婪的方式来包装你的话;也就是说，在每一行中尽可能多地填充单词。在必要时填充额外的空格，以便每行都有精确的maxWidth字符。

单词之间的额外空格应该尽可能均匀地分布。如果一行的空格数不能在单词之间平均分配，那么左边的空格将比右边的空格分配更多的空格。

对于最后一行文本，它应该左对齐，并且单词之间不应该插入额外的空格。

注意:

一个单词被定义为一个由非空格字符组成的字符序列。
每个单词的长度保证大于0且不超过maxWidth。
输入数组单词至少包含一个单词。

```java
class Solution {

    public List<String> fullJustify(String[] words, int maxWidth) {
        StringBuilder curLine = new StringBuilder();
        List<String> res = new ArrayList<>();
        int i=0;
        boolean firstWord = true;

        while(i<words.length){
            //如果当前串加上下一个单词和一个空格，满足宽度时，则增加
            //当前串的长度小于宽度
            if(curLine.length()<maxWidth){
                //如果是第一个字符，则只需要该字符长度小于宽度即可添加
                if(firstWord && words[i].length() <= maxWidth){
                    curLine.append(words[i]);
                    firstWord = false;
                    i++;
                    continue;
                }else if(!firstWord && words[i].length()+1+curLine.length() <= maxWidth){
                    //如果不是第一个字符
                    //则需要该字符长度+1小于宽度才可添加
                    curLine.append(" ");
                    curLine.append(words[i]);
                    i++;
                    continue;
                }else {
                    //当前串长度小于宽度，但无法再加下一个单词了,此时就需要补空格
                    String tmp = supplyBlankSpace(curLine.toString(), maxWidth);
                    curLine.delete(0, curLine.length());
                    curLine.append(tmp); //把该行更新为满足条件的串
                }
            }else {
                //(curLine.length()==maxWidth)
                res.add(curLine.toString());
                firstWord = true;
                curLine.delete(0, curLine.length());
            }
        }
        //添加最后一行

        //对最后一行进行特殊处理：
        //这行如果不是最后一行，则应该是 "shall       be"
        //如果是最后一行，这应该是      "shall be      "
        String tmp = handleLastLine(curLine.toString(), maxWidth);
        res.add(tmp);
        return res;
    }

    String handleLastLine(String src, int maxWidth){
        //这里面可能出现空
        String[] words = src.split(" ");
        int len = 0;
        for (int i=0;i<words.length;i++){
            if(words[i].length()>0)
                len++;
        }
        int j=0;
        String[] nwords = new String[len];
        for (int i=0;i<words.length;i++){
            if(words[i].length()>0){
                nwords[j++]=words[i];
            }
        }
        StringBuilder builder = new StringBuilder();

        for(int i=0;i<nwords.length;i++){
            if(i==nwords.length-1){
                builder.append(nwords[i]);
            }else {
                builder.append(nwords[i]);
                builder.append(' ');
            }
        }
        while (builder.length()<maxWidth){
            builder.append(' ');
        }
        return builder.toString();
    }

    //根据规则，补空格有如下规则：
    /*
    1. 先找有空格的地方，顺序从左到右去每个位置补空格，一遍不够再来一遍。。。
    2. 字符串中如果没有空格，则直接在字符串末尾补足maxwidth-n个空格, n是当前串的长度
    */
    public String supplyBlankSpace(String src, int maxWidth){
        StringBuilder res = new StringBuilder(src);
        if(!src.contains(" ")){ //没有空格,直接在末尾补充空格
            int offset = maxWidth - src.length();
            while(offset>0){
                res.append(" ");
                offset--;
            }
            return res.toString();
        }else{
            while(res.length() < maxWidth){
                for(int i=0;i<res.length() && res.length()<maxWidth;i++){
                    //为了保证两边的平衡
                    if(res.charAt(i)==' ' && res.charAt(i-1)!= ' '){
                        res.insert(i, " ");
                    }
                }
            }
        }
        return res.toString();
    }
}
```

71. Simplify Path
Medium

Given an absolute path for a file (Unix-style), simplify it. Or in other words, convert it to the canonical path.

In a UNIX-style file system, a period . refers to the current directory. 
Furthermore, a double period .. moves the directory up a level. For more information, see: Absolute path vs relative path in Linux/Unix

Note that the returned canonical path must always begin with a slash /, 
and there must be only a single slash / between two directory names. 
The last directory name (if it exists) must not end with a trailing /. 
Also, the canonical path must be the shortest string representing the absolute path.

Example 1:

Input: "/home/"
Output: "/home"
Explanation: Note that there is no trailing slash after the last directory name.
Example 2:

Input: "/../"
Output: "/"
Explanation: Going one level up from the root directory is a no-op, as the root level is the highest level you can go.
Example 3:

Input: "/home//foo/"
Output: "/home/foo"
Explanation: In the canonical path, multiple consecutive slashes are replaced by a single one.
Example 4:

Input: "/a/./b/../../c/"
Output: "/c"
Example 5:

Input: "/a/../../b/../c//.//"
Output: "/c"
Example 6:

Input: "/a//b////c/d//././/.."
Output: "/a/b/c"

给定一个文件的绝对路径(unix风格)，简化它。换句话说，将它转换为规范路径。

在unix风格的文件系统中，句号.引用当前目录。
此外，双倍.将目录向上移动一级。有关更多信息，请参见:Linux/Unix中的绝对路径与相对路径

注意，返回的规范路径必须始终以斜杠/开头，
而且两个目录名之间必须只有一个斜杠/。
最后一个目录名(如果存在)不能以/结尾。
此外，规范路径必须是表示绝对路径的最短字符串。

这里有个重要的点是，一旦返回一个目录的上级，这个目录就不要了

```java
class Solution {
    public String simplifyPath(String path) {
        ArrayList<String> pathList = new ArrayList<>();
        String[] paths = path.split("/");

        for(int i=0;i<paths.length;i++){
            if(paths[i].equals("")){
                continue;
            }

            if(paths[i].equals(".")){
                //不变
            }else if(paths[i].equals("..")){
                if(pathList.size()>0)
                    pathList.remove(pathList.size()-1);
            }else{
                pathList.add(paths[i]);
            }
        }

        //此时pathList中是 root->a->b->c， index=3
        StringBuilder res = new StringBuilder();
        if(pathList.size()==0){
            res.append("/");
            return res.toString();
        }else{
            for(int i=0;i<pathList.size();i++){
                res.append("/");
                res.append(pathList.get(i));
            }
            return res.toString();
        }
    }
}
```

74. Search a 2D Matrix
Medium

Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

Integers in each row are sorted from left to right.
The first integer of each row is greater than the last integer of the previous row.
Example 1:

Input:
matrix = [
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
target = 3
Output: true
Example 2:

Input:
matrix = [
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
target = 13
Output: false

编写一个搜索mxn矩阵值的有效算法。该矩阵具有如下性质:

每一行中的整数都是从左到右排序的。
每一行的第一个整数都大于前一行的最后一个整数。

这个题做过把？，从右上角开始找，只走下和左，

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix==null || matrix.length==0 || matrix[0].length==0) return false;
        int row = matrix.length;
        int col = matrix[0].length;
        int x = 0;
        int y = col-1;

        while((x<=row-1) && (y>=0)){
            if(matrix[x][y] > target){
                y--;
            }else if(matrix[x][y] < target){
                x++;
            }else{
                return true;
            }
        }
        return false;
    }
}
```

80. Remove Duplicates from Sorted Array II
Medium

Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most twice and return the new length.

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

Example 1:

Given nums = [1,1,1,2,2,3],

Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 respectively.

It doesn't matter what you leave beyond the returned length.
Example 2:

Given nums = [0,0,1,1,1,1,2,3,3],

Your function should return length = 7, with the first seven elements of nums being modified to 0, 0, 1, 1, 2, 3 and 3 respectively.

It doesn't matter what values are set beyond the returned length.
Clarification:

Confused why the returned value is an integer but your answer is an array?

Note that the input array is passed in by reference, which means modification to the input array will be known to the caller as well.

Internally you can think of this:

// nums is passed in by reference. (i.e., without making a copy)
int len = removeDuplicates(nums);

// any modification to nums in your function would be known by the caller.
// using the length returned by your function, it prints the first len elements.
for (int i = 0; i < len; i++) {
    print(nums[i]);
}

给定已排序的数组号，删除重复项，使重复项最多出现两次，并返回新的长度。

不要为另一个数组分配额外的空间，您必须使用O(1)额外内存修改输入数组。

由于数组已经是排好序的，而且最后只看前面满足条件的个数，所以直接让需要被替换的地方变成最大的数，最后再对数组排序即可

0,0,1,1,1,1,2,3,3-》0,0,1,1,3,3,2,3,3--》0,0,1,1,2,3,3,3,3

1,1,1,2,2,3-》1,1,3,2,2,3 -》1,1,2,3,3

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if(nums==null || nums.length==0) return 0;
        int curNum = -1;
        int curFirst = 0;
        int dupCount = 0;
        int max = nums[nums.length-1];
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=curNum){
                curNum=nums[i];
                curFirst = i;
            }
            if(nums[i]==curNum && curFirst+1<i){
                nums[i] = max;
                dupCount++;
            }
        }

        //所有需要变的地方全变成max了,此时只要再次排序即可
        Arrays.sort(nums);
        return nums.length - dupCount;
    }
}
```

81. Search in Rotated Sorted Array II
Medium

Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., [0,0,1,2,2,5,6] might become [2,5,6,0,0,1,2]).

You are given a target value to search. If found in the array return true, otherwise return false.

Example 1:

Input: nums = [2,5,6,0,0,1,2], target = 0
Output: true
Example 2:

Input: nums = [2,5,6,0,0,1,2], target = 3
Output: false
Follow up:

This is a follow up problem to Search in Rotated Sorted Array, where nums may contain duplicates.
Would this affect the run-time complexity? How and why?

假设一个按升序排序的数组在某个未知的主轴上旋转。

(即。，[0,0,1,2,2,5,6]可能变成[2,5,6,0,0,1,2])。

给定要搜索的目标值。如果在数组中找到，返回true，否则返回false。

对整个数组处理，看mid和low， high的大小关系，如果 mid>low，则前面是顺序，直接可以用二分，后面是一部分顺序一部分逆序,继续处理

相比于之前的二分，这个题不能简单那么做，因为有重复元素，边界条件很难判定



1)每次检查targe == nums[mid]，如果是，我们就找到它。
2)否则，检查前半部分是否是顺序(即nums[left]<=nums[mid])
如果是，请转到步骤3)，否则，请转到步骤4)
3)检查target是否在[left, mid-1]范围内(即nums[left]<=target < nums[mid])，如果是，则在前半部分进行搜索，即right = mid-1;否则，下半部分搜索 left = mid+1;
4)检查target是否在[mid+1, right]范围内(即nums[mid]< target <= nums[right])，如果是，则在后半部分进行搜索，即left = mid+1;否则前半部分进行搜索 right=mid-1;

```java
class Solution {
    boolean found = false;
    public boolean search(int[] nums, int target) {
        int left = 0, right =  nums.length-1, mid;
        
        while(left<=right){
            mid = (left + right) >> 1;
            if(nums[mid] == target) return true;

            // the only difference from the first one, trickly case, just update left and right
            //这是最关键的一步，这一步就区别了和Search in Rotated Sorted Array
            if( (nums[left] == nums[mid]) && (nums[right] == nums[mid]) ) {++left; --right;}

            else if(nums[left] <= nums[mid]){
                if( (nums[left]<=target) && (nums[mid] > target) ) right = mid-1;
                else left = mid + 1; 
            }
            else{
                if((nums[mid] < target) &&  (nums[right] >= target) ) left = mid+1;
                else right = mid-1;
            }
        }
        return false;
    }

}
```

82. Remove Duplicates from Sorted List II
Medium

Given a sorted linked list, delete all nodes that have duplicate numbers, 
leaving only distinct numbers from the original list.

Example 1:

Input: 1->2->3->3->4->4->5
Output: 1->2->5
Example 2:

Input: 1->1->1->2->3
Output: 2->3

给定一个排序链表，删除所有重复编号的节点，
只留下与原始列表不同的数字。

简单期间，引入一个头结点，同时保留一个节点的前驱，后继
如果节点node的后继等于它，则前驱不变，直到找到后继不是它的节点，删除中间的节点

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {

    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode node = head;
        ListNode pre = dummy;
        boolean isCurDup = false;
        while(node!=null){
            if(isCurDup){
                if(node.next!=null && node.next.val==node.val){
                    node = node.next;
                }else if(node.next!=null && node.next.val!=node.val){
                    deleteNodes(pre, node);
                    isCurDup = false;
                    node = node.next;
                }else{
                    //node.next==null
                    deleteNodes(pre, node);
                    isCurDup = false;
                    node = node.next;
                }
            }else{
                if(node.next!=null){
                    if(node.next.val==node.val){
                        isCurDup = true;
                        node = node.next;
                    }else{
                        pre = node;
                        node = node.next;
                    }
                }else{
                    pre = node;
                    node = node.next;
                }
            }
        }
        return dummy.next;
    }


    void deleteNodes(ListNode pre, ListNode curNode){
        //删除pre到curNode之间的节点（不包括pre，包括curNode）
        ListNode node = pre.next;
        while(node!=curNode){
            node = node.next;
            pre.next = node;
        }
        pre.next = node.next;
    }
}
```

83. Remove Duplicates from Sorted List
Easy

Given a sorted linked list, delete all duplicates such that each element appear only once.

Example 1:

Input: 1->1->2
Output: 1->2
Example 2:

Input: 1->1->2->3->3
Output: 1->2->3

如果当前节点值和前面的相同，则删除当前节点

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(Integer.MAX_VALUE);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode node = head;
        while(node!=null){
            if(node.val==pre.val){
                pre.next = node.next;
                node = node.next;
            }else{
                pre = node;
                node = node.next;
            }
        }
        return dummy.next;
    }
}
```

86. Partition List
Medium

Given a linked list and a value x, 
partition it such that all nodes less than x come before nodes greater than or equal to x.
You should preserve the original relative order of the nodes in each of the two partitions.

Example:

Input: head = 1->4->3->2->5->2, x = 3
Output: 1->2->2->4->3->5


给定一个链表和一个值x，
对其进行分区，使所有小于x的节点都位于大于或等于x的节点之前。
您应该保留两个分区中每个节点的原始相对顺序。

这里只对小于x的节点的位置进行了约束，没说对于大于x的节点如何做，那就是不动
把小于x的节点按顺序放入一个数组，其他的放入另一个数组，然后把这个两个数组连接起来

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode partition(ListNode head, int x) {
        ArrayList<ListNode> smaller = new ArrayList<>();
        ArrayList<ListNode> greater = new ArrayList<>(); //大于或等于x的节点
        ListNode node = head;
        while(node!=null){
            if(node.val<x){
                smaller.add(node);
            }else{
                greater.add(node);
            }
            node = node.next;
        }

        ListNode dummy = new ListNode(0);
        ListNode pre = dummy;
        for(int i=0;i<smaller.size();i++){
            pre.next = smaller.get(i);
            pre = pre.next;
        }

        for(int i=0;i<greater.size();i++){
            pre.next = greater.get(i);
            pre = pre.next;
        }
        pre.next = null;
        return dummy.next;
    }
}
```

87. Scramble String
Hard

Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.

Below is one possible representation of s1 = "great":

给定一个字符串s1，我们可以递归地将它划分为两个非空子字符串，从而将它表示为二叉树。

下面是s1 = "great"的一种可能表示:

    great
   /    \
  gr    eat
 / \    /  \
g   r  e   at
           / \
          a   t
To scramble the string, we may choose any non-leaf node and swap its two children.

For example, if we choose the node "gr" and swap its two children, it produces a scrambled string "rgeat".

要打乱字符串，我们可以选择任何非叶节点并交换它的两个子节点。

例如，如果我们选择节点“gr”并交换它的两个子节点，它将生成一个打乱的字符串“rgeat”。

    rgeat
   /    \
  rg    eat
 / \    /  \
r   g  e   at
           / \
          a   t
We say that "rgeat" is a scrambled string of "great".

Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae".

我们说“rgeat”是一串“great”的乱串。

同样，如果我们继续交换节点“eat”和“at”的子节点，它会生成一个打乱的字符串“rgtae”。

    rgtae
   /    \
  rg    tae
 / \    /  \
r   g  ta  e
       / \
      t   a
We say that "rgtae" is a scrambled string of "great".

Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.

我们说“rgtae”是一串“great”的乱串。

给定两个长度相同的字符串s1和s2，判断s2是否是s1的乱串。

Example 1:

Input: s1 = "great", s2 = "rgeat"
Output: true
Example 2:

Input: s1 = "abcde", s2 = "caebd"
Output: false


没看懂，抄的答案

```java
class Solution {
     public boolean isScramble(String s1, String s2) {
        if (s1.equals(s2)) return true; 
        
        //首先保证s1和s2中的字母及个数都一样
        int[] letters = new int[26];
        for (int i=0; i<s1.length(); i++) {
            letters[s1.charAt(i)-'a']++;
            letters[s2.charAt(i)-'a']--;
        }
        for (int i=0; i<26; i++) if (letters[i]!=0) return false;
        

        for (int i=1; i<s1.length(); i++) {
            //这个很关键，可以是s1的头对应s2的头，也可以是s1的头对应s2的尾，只要有一种情况满足，那总体就是满足的
            if (isScramble(s1.substring(0,i), s2.substring(0,i)) 
             && isScramble(s1.substring(i), s2.substring(i))) return true;
            if (isScramble(s1.substring(0,i), s2.substring(s2.length()-i)) 
             && isScramble(s1.substring(i), s2.substring(0,s2.length()-i))) return true;
        }
        return false;
    }
}
```

89. Gray Code
Medium

The gray code is a binary numeral system where two successive values differ in only one bit.

Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. A gray code sequence must begin with 0.

Example 1:

Input: 2
Output: [0,1,3,2]
Explanation:
00 - 0
01 - 1
11 - 3
10 - 2

For a given n, a gray code sequence may not be uniquely defined.
For example, [0,2,3,1] is also a valid gray code sequence.

00 - 0
10 - 2
11 - 3
01 - 1
Example 2:

Input: 0
Output: [0]
Explanation: We define the gray code sequence to begin with 0.
             A gray code sequence of n has size = 2^n, which for n = 0 the size is 20 = 1.
             Therefore, for n = 0 the gray code sequence is [0].

灰色代码是一个二进制数字系统，其中两个连续的值只相差一位。
给定一个非负整数n表示代码中的总比特数，打印灰色代码序列。灰色代码序列必须以0开头。

对于给定的n，灰色代码序列可能不是唯一定义的。
例如，[0,2,3,1]也是一个有效的灰色代码序列。

说明:我们定义了从0开始的灰色代码序列。
一个n的灰度编码序列大小为2n，当n = 0时，大小为20 = 1。
因此，对于n = 0，灰度编码序列为[0]。



```java
class Solution {

    public List<Integer> grayCode(int n) {
        ArrayList<Integer> res = new ArrayList<>();
        if (n==0){
            res.add(0);
            return res;
        }
        //不能用set，因为遍历set时不能保证遍历顺序和加入顺序一致，用array
        ArrayList<String> list = new ArrayList<>();
        StringBuilder zero = new StringBuilder();
        for(int i=0;i<n;i++){
            zero.append('0');
        }
        String cur = zero.toString();
        list.add(zero.toString());
        int total = (int) Math.pow(2, n);
        for(int i=0;i<total;i++){
            for(int j=0;j<n;j++){
                String t = flip(cur, j);
                if(!list.contains(t)){
                    list.add(t);
                    cur = t;
                    break;
                }
            }
        }

        for(String s: list){
            int t = Integer.parseInt(s, 2);
            res.add(t);
        }
        return res;
    }

    //翻转字符串中的某位
    String flip(String src, int index){
        StringBuilder res = new StringBuilder(src);
        if(res.charAt(index)=='1'){
            res.replace(index, index+1, "0");
        }else{
            res.replace(index, index+1, "1");
        }
        return res.toString();
    }

}
```

90. Subsets II
Medium

Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).

Note: The solution set must not contain duplicate subsets.

Example:

Input: [1,2,2]
Output:
[
  [2],
  [1],
  [1,2,2],
  [2,2],
  [1,2],
  []
]

给定一组可能包含重复数、数字的整数，返回所有可能的子集(幂集)。

注意:解决方案集不能包含重复的子集。

trackBack

```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        trackBack(0, nums, new ArrayList<>());
        return res;

    }

    void trackBack(int start, int[] nums, List<Integer> curList){
        List<Integer> tmp = new ArrayList<>(curList);
        Collections.sort(tmp);
        if(!res.contains(tmp)){
            res.add(tmp);
        }

        for(int i=start;i<nums.length;i++){
            curList.add(nums[i]);
            trackBack(i+1, nums, curList);
            curList.remove(curList.size()-1);
        }
    }
}
```

93. Restore IP Addresses
Medium

Given a string containing only digits, restore it by returning all possible valid IP address combinations.

Example:

Input: "25525511135"
Output: ["255.255.11.135", "255.255.111.35"]

给定一个只包含数字的字符串，通过返回所有可能的有效IP地址组合来还原它。

使用trackBack

```java
class Solution {

    List<String> res;
    public List<String> restoreIpAddresses(String s) {
        res = new ArrayList<>();
        trackBack(0, s, new ArrayList(), 0);
        return res;
    }


    void trackBack(int start, String s, List curList, int cnt){
        if(cnt>4){  //已经攒了4个以上数，肯定不对
            return;
        }

        if(cnt==4&& start!=s.length()){  //攒了4个数，但是没到头，不对
            return;
        }
        if(start==s.length() && cnt==4){  //攒了4个数，且到头了，是一个答案
            addOneAns(curList);
            return;
        }

        for(int i=start;i<3+start;i++){  //因为是0-255,所以每个数字最少1位最多3位
            if(i+1>s.length()) break; //当超过长度时就不要了
            String sub = s.substring(start, i+1);
            if(isValidNum(sub)){
                curList.add(Integer.valueOf(sub));
                trackBack(i+1, s, curList, cnt+1);
                curList.remove(curList.size()-1);
            }
        }
    }

    void addOneAns(List list){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<list.size();i++){
            builder.append(list.get(i));
            builder.append(".");
        }
        //去掉最后面的.
        builder.delete(builder.length()-1, builder.length());
        res.add(builder.toString());
    }

    boolean isValidNum(String s){
        try {
            //如果开头是0，但整个数字又不是0，则该数字不行，如010，会被解读为10，但实际上不能丢掉任何一位数
            //如果是00，这种也不合法，如果是0，那么只能是单纯一个0。
            if(s.startsWith("0") && Integer.valueOf(s)!=0) return false;
            if(s.startsWith("0") && Integer.valueOf(s)==0 && s.length()>1) return false;
            int c = Integer.valueOf(s);
            return !(c>255);
        }catch (Exception e){
            return false;
        }
    }
}
```

95. Unique Binary Search Trees II
Medium

Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.

Example:

Input: 3
Output:
[
  [1,null,3,2],
  [3,2,null,1],
  [3,1,null,null,2],
  [2,1,3],
  [1,null,2,null,3]
]
Explanation:
The above output corresponds to the 5 unique BST's shown below:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3

给定一个整数n，生成所有结构上唯一的BST(二叉搜索树)，存储值1…n。

下面这个做法要从大一点的角度去看，对于每个节点，获得它的左边的可能性的所有节点的组合和右边可能性的所有节点的组合
是一种很巧妙的递归做法

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {

    public List<TreeNode> generateTrees(int n) {
        if(n==0) return new ArrayList<>();
        return genTrees(1, n);
    }

    public List<TreeNode> genTrees(int start, int end) {

        List<TreeNode> list = new ArrayList<TreeNode>();

        if (start > end) {
            list.add(null);
            return list;
        }

        if (start == end) {
            list.add(new TreeNode(start));
            return list;
        }

        List<TreeNode> left, right;
        for (int i = start; i <= end; i++) {

            left = genTrees(start, i - 1);
            right = genTrees(i + 1, end);

            for (TreeNode lnode : left) {
                for (TreeNode rnode : right) {
                    TreeNode root = new TreeNode(i);
                    root.left = lnode;
                    root.right = rnode;
                    list.add(root);
                }
            }

        }

        return list;
    }
}
```

97. Interleaving String
Hard

Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.

Example 1:

Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
Example 2:

Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false

给定s1 s2 s3，找出s3是否由s1和s2交叉组成。

这里的交叉，应该是拿s2中的字母去随便插入s1的位置，而不是整个s2插入s1中

## 这道求交织相错的字符串和之前那道 Word Break 拆分词句 的题很类似，
## 就想我之前说的只要是遇到字符串的子序列或是匹配问题直接就上动态规划DynamicProgramming，
## 其他的都不要考虑，什么递归呀的都是浮云，千辛万苦的写了递归结果拿到OJ上妥妥Time Limit Exceeded，能把人气昏了，
## 所以还是直接就考虑DP解法省事些。一般来说字符串匹配问题都是更新一个二维dp数组，核心就在于找出递推公式。


  Ø d b b c a
Ø T F F F F F
a T F F F F F
a T T T T T F
b F T T F T F
c F F T T T T
c F F F T F T

首先，这道题的大前提是字符串s1和s2的长度和必须等于s3的长度，如果不等于，肯定返回false。那么当s1和s2是空串的时候，s3必然是空串，则返回true。所以直接给dp[0][0]赋值true，然后若s1和s2其中的一个为空串的话，那么另一个肯定和s3的长度相等，则按位比较，若相同且上一个位置为True，赋True，其余情况都赋False，这样的二维数组dp的边缘就初始化好了。下面只需要找出递推公式来更新整个数组即可，我们发现，在任意非边缘位置dp[i][j]时，它的左边或上边有可能为True或是False，两边都可以更新过来，只要有一条路通着，那么这个点就可以为True。那么我们得分别来看，如果左边的为True，那么我们去除当前对应的s2中的字符串s2[j - 1] 和 s3中对应的位置的字符相比（计算对应位置时还要考虑已匹配的s1中的字符），为s3[j - 1 + i], 如果相等，则赋True，反之赋False。 而上边为True的情况也类似，所以可以求出递推公式为：

即一个位置只能由它的左边或上边更新过来，如果是从左边更新过来，意味着增加了s2的一个字母；如果是从上边更新过来，意味着增加了s1的一个字母；

dp[i][j] = (dp[i - 1][j] && s1[i - 1] == s3[i - 1 + j]) || (dp[i][j - 1] && s2[j - 1] == s3[j - 1 + i]);

其中dp[i][j] 表示的是 s2 的前 i 个字符和 s1 的前 j 个字符是否匹配 s3 的前 i+j 个字符，根据以上分析，可写出代码如下：



```java
class Solution {
    public boolean isInterleave(String ss1, String ss2, String ss3) {
        int len1 = ss1.length();
        int len2 = ss2.length();
        int len3 = ss3.length();
        char[] s1 = ss1.toCharArray();
        char[] s2 = ss2.toCharArray();
        char[] s3 = ss3.toCharArray();
        if(len1+len2!=len3) return false;
        boolean[][] dp = new boolean[len1+1][len2+1];

        dp[0][0] = true;
        for(int i=1;i<=len1;i++){
            if(s1[i-1]==s3[i-1] && dp[i-1][0])
                dp[i][0] = true;
        }

        for(int i=1;i<=len2;i++){
            if(s2[i-1]==s3[i-1] && dp[0][i-1])
                dp[0][i] = true;
        }

        for(int i=1;i<=len1;i++){
            for(int j=1;j<=len2;j++){
                dp[i][j] = (dp[i][j-1] && s3[i+j-1]==s2[j-1]) || (dp[i-1][j] && s3[i+j-1]==s1[i-1]);
            }
        }

        return dp[len1][len2];

    }
}
```

99. Recover Binary Search Tree
Hard

Two elements of a binary search tree (BST) are swapped by mistake.

Recover the tree without changing its structure.

Example 1:

Input: [1,3,null,null,2]

   1
  /
 3
  \
   2

Output: [3,1,null,null,2]

   3
  /
 1
  \
   2
Example 2:

Input: [3,1,4,null,null,2]

  3
 / \
1   4
   /
  2

Output: [2,1,4,null,null,3]

  2
 / \
1   4
   /
  3
Follow up:

A solution using O(n) space is pretty straight forward.
Could you devise a constant space solution?

一个二叉搜索树(BST)的两个元素被错误地交换了。

在不改变树结构的情况下恢复树。

使用O(n)空间的解非常简单。(把节点全部存下来)

## 二叉搜索树的中序遍历是有序序列
先用中序遍历一遍，遍历出的结果必然只有两个位置不对：
第一个：321
第二个：1324
再中序遍历一遍树，记录位置不对的两个节点，然后交换它们的值即可

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    ArrayList<Integer> ori;
    int[] eles;
    public void recoverTree(TreeNode root) {
        ori = new ArrayList<>();
        eles = new int[2];
        //第一遍遍历不记录
        inOrder(root, null);
        ArrayList<Integer> order = new ArrayList<>(ori);
        Collections.sort(order);
        compareVals(ori, order);
        HashSet<TreeNode> set = new HashSet<>();
        //第二遍遍历记录
        inOrder(root, set);

        TreeNode tmp = null;
        for(TreeNode node: set){
            if(tmp==null){
                tmp = node;
            }else{
                int t = tmp.val;
                tmp.val = node.val;
                node.val = t;
            }
        }

    }

    void compareVals(ArrayList<Integer> list1, ArrayList<Integer> list2){
        int k = 0;
        for(int i=0;i<list1.size();i++){
            if(list1.get(i)!=list2.get(i)){
                eles[k++] = list1.get(i);
            }
        }
    }

    void inOrder(TreeNode root, HashSet set){
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode p = root;
        while(!stack.isEmpty() || p!=null){
            if(p!=null){
                stack.push(p);
                p = p.left;
            }else{
                p = stack.pop();
                ori.add(p.val);
                if(set!=null && eles!=null){
                    if(p.val==eles[0] || p.val==eles[1]){
                        set.add(p);
                    }
                }
                p = p.right;
            }
        }
    }
}
```

100. Same Tree
Easy

Given two binary trees, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical and the nodes have the same value.

Example 1:

Input:     1         1
          / \       / \
         2   3     2   3

        [1,2,3],   [1,2,3]

Output: true
Example 2:

Input:     1         1
          /           \
         2             2

        [1,2],     [1,null,2]

Output: false
Example 3:

Input:     1         1
          / \       / \
         2   1     1   2

        [1,2,1],   [1,1,2]

Output: false

很简单的递归

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null && q==null) return true;
        else if(p==null || q==null) return false;

        return p.val==q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```

###################### 前一百道做完了，撒花🌹🌹🌹🌹🌹🌹 ##################

113. Path Sum II
Medium

Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.

Note: A leaf is a node with no children.

Example:

Given the below binary tree and sum = 22,

      5
     / \
    4   8
   /   / \
  11  13  4
 /  \    / \
7    2  5   1
Return:

[
   [5,4,11,2],
   [5,8,4,5]
]

给定一个二叉树和一个和，找出所有根到叶的路径，其中每个路径的和等于给定的和。

注意:叶子是没有子节点的节点。

依然用回溯法，注意的是每对一个节点扫描完毕后，要把它移出list，因为list是共用的

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    ArrayList<List<Integer>> lists;
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        lists = new ArrayList<>();
        findPath(root, sum, 0, new ArrayList<Integer>());
        return lists;
    }

    void findPath(TreeNode root, int sum, int preSum, List<Integer> list){
        if(root==null) return;

        int cur = root.val;
        list.add(cur);
        if(root.left==null && root.right==null && cur+preSum==sum){
            ArrayList<Integer> ele = new ArrayList<>(list);
            lists.add(ele);
        }else{

            findPath(root.left, sum, preSum+cur, list);
            findPath(root.right, sum, preSum+cur, list);
        }

        list.remove(list.size()-1);
    }
}
```

115. Distinct Subsequences
Hard

Given a string S and a string T, count the number of distinct subsequences of S which equals T.

A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).

Example 1:

Input: S = "rabbbit", T = "rabbit"
Output: 3
Explanation:

As shown below, there are 3 ways you can generate "rabbit" from S.
(The caret symbol ^ means the chosen letters)

rabbbit
^^^^ ^^
rabbbit
^^ ^^^^
rabbbit
^^^ ^^^
Example 2:

Input: S = "babgbag", T = "bag"
Output: 5
Explanation:

As shown below, there are 5 ways you can generate "bag" from S.
(The caret symbol ^ means the chosen letters)

babgbag
^^ ^
babgbag
^^    ^
babgbag
^    ^^
babgbag
  ^  ^^
babgbag
    ^^^

给定一个字符串S和一个字符串T，计算S的不同子序列的个数，它们等于T。

一个字符串的子序列是一个新的字符串，它由原来的字符串组成，删除一些字符(可以是none)，而不影响其余字符的相对位置。(例如，“ACE”是“ABCDE”的子序列，而“AEC”不是)。

这种题基本上用一般的回溯就会超时，考虑dp

我们将构建一个数组mem，其中mem[i+1][j+1]表示S[0...j]包含T[0 . .i]的不同子序列的个数。因此，结果将是mem[T.length()][S.length()]。
第一行必须填入1。这是因为空字符串是任意字符串的子序列，但只有1次。

mem[i+1][j+1] 表示s的前j+1字符包含t的前i+1个字符的不同次序的个数

所以mem[0][j] = 1（对于任意j），这样不仅使更简单，而且如果T是一个空字符串，我们还返回正确的值。

除第一行外，每一行的第一列必须为0。这是因为空字符串不能包含非空字符串作为子字符串——数组的第一项:mem[0][0] = 1，
因为空字符串一次包含空字符串1一次。

  S 0123....j
T +----------+
  |1111111111|
0 |0         |
1 |0         |
2 |0         |
. |0         |
. |0         |
i |0         |

当S[j]!=T[i]的时候 mem[i+1][j+1] = mem[i+1][j] //因为加入的新字母没有用
当S[j]==T[i]的时候 mem[i+1][j+1] = mem[i+1][j] + mem[i][j]  
//因为加入的新字母有用,所以这时有两种情况：
    1. 不考虑新加入的字母，那么还是有 mem[i+1][j]种情况
    2. 考虑新加入的字母，已知 T[i] 和 S[j]相同，那么就看 前面S中[0 - j-1] 包含 T[0-i-1]有多少种可能性

## 对于字符匹配的问题，上面那种这种矩阵很好用

```java
class Solution {
    public int numDistinct(String S, String T) {
        // array creation
        int[][] mem = new int[T.length()+1][S.length()+1];

        // filling the first row: with 1s
        for(int j=0; j<=S.length(); j++) {
           mem[0][j] = 1;
        }

        for(int j=0;j<S.length();j++){
            for(int i=0;i<T.length();i++){
                if(S.charAt(j)==T.charAt(i)){
                    mem[i+1][j+1] = mem[i+1][j] + mem[i][j];
                }else{
                    mem[i+1][j+1] = mem[i+1][j];
                }
            }
        }
        return mem[T.length()][S.length()];
    }
}
```

117. Populating Next Right Pointers in Each Node II
Medium

Given a binary tree

struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.

Initially, all next pointers are set to NULL.

Example:

Input: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},"next":null,"right":{"$id":"4","left":null,"next":null,"right":null,"val":5},"val":2},"next":null,"right":{"$id":"5","left":null,"next":null,"right":{"$id":"6","left":null,"next":null,"right":null,"val":7},"val":3},"val":1}

Output: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":{"$id":"4","left":null,"next":{"$id":"5","left":null,"next":null,"right":null,"val":7},"right":null,"val":5},"right":null,"val":4},"next":{"$id":"6","left":null,"next":null,"right":{"$ref":"5"},"val":3},"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"6"},"val":1}

Explanation: Given the above binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B.
 
Note:

You may only use constant extra space.
Recursive approach is fine, implicit stack space does not count as extra space for this problem.

给定一个二叉树

填充每个next指针，指向它的右边的兄弟节点。如果没有next right节点，则应该将下一个指针设置为NULL。

最初，所有next指针都设置为NULL。

用两个队列的层序遍历即可

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val,Node _left,Node _right,Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/
class Solution {
    public Node connect(Node root) {
        if (root==null) return null;
        LinkedList<Node> queue1 = new LinkedList<>();
        LinkedList<Node> queue2 = new LinkedList<>();
        queue1.offer(root);
        Node node = null;
        while(!queue1.isEmpty() || !queue2.isEmpty()){
            LinkedList<Node> cur = queue1.isEmpty()?queue2:queue1;
            LinkedList<Node> another = queue1.isEmpty()?queue1:queue2;
            Node pre = null;
            while(!cur.isEmpty()){
                node = cur.poll();
                if(node.left!=null) another.offer(node.left);
                if(node.right!=null) another.offer(node.right);
                if(pre!=null)
                    pre.next = node;
                pre = node;
            }
        }
        return root;
    }
}
```

120. Triangle
Medium

Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.

For example, given the following triangle

[
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).

Note:

Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.

给定一个三角形，从上到下求最小路径和。每一步都可以移动到下面一行的相邻数字。

如果你能只使用O(n)额外的空间来做这件事，额外的好处是n是三角形的总行数。

dp[i][j] 是走到第i行第j列的最小和

if(j==0){
    dp[i][0] = dp[i-1][0] + num[i][0]
}
else if(j==最后一列){
    dp[i][j] = dp[i-1][j-1] + num[i][j]
}
else{
    dp[i][j] = Math.min(dp[i-1][j-1] + dp[i-1][j]);
}

```java
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int row = triangle.size();
        int col = triangle.get(row-1).size();
        int[][] dp = new int[row][col];

        int len1 = triangle.size();
        for(int i=0;i<len1;i++){
            int len2 = triangle.get(i).size();
            for(int j=0;j<len2;j++){
                int cur = triangle.get(i).get(j);
                if(j==0){
                    dp[i][j] = i==0?cur:cur+dp[i-1][j];
                }else if(j==len2-1){
                    if(i==0 && j==0){
                        dp[i][j] = cur;
                    }else{
                        dp[i][j] = dp[i-1][j-1] + cur;
                    }
                }else{
                    dp[i][j] = Math.min(dp[i-1][j-1] , dp[i-1][j]) + cur;
                }
            }
        }

        int lastLen = triangle.get(len1-1).size();
        int res = Integer.MAX_VALUE;
        for(int i=0;i<lastLen;i++){
            res = Math.min(dp[len1-1][i], res);
        }
        return res;
    }
}
```

123. Best Time to Buy and Sell Stock III
Hard

Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete at most two transactions.

Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).

Example 1:

Input: [3,3,5,0,0,3,1,4]
Output: 6
Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
             Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
Example 2:

Input: [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
             Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
             engaging multiple transactions at the same time. You must sell before buying again.
Example 3:

Input: [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.

假设你有一个数组，其中第i个元素是某只股票在第i天的价格。

设计一个算法来寻找最大的利润。您最多可以完成两个事务（最多两次买卖）。

注:你不可同时进行多项交易(即，你必须先把股票卖了再买。

使用动态规划

dp[k][i] 表示操作k步（买卖次数，买+卖算操作一次），截止到prices[i]能获得的最大利润

对于dp[k][i],如果不考虑prices[i],那就是对前面i-1操作k次获得的利润，即dp[k][i-1]
如果考虑prices[i], 有个难点是对于prices[j]，因为dp[k][i]是考虑进prices[i]的，所以有可能dp[k-1][j]中把prices[j]卖了
而dp[k][i]算的时候又有prices[i]-prices[j]，相当于把prices[j]又买了，但其实就相当于prices[j]做了个桥梁，结果没有变，而操作步骤只会少而不会多
因此就可以这么做

## 下面是最关键的递推公式：
dp[k][i] = max(dp[k][i-1], prices[i]-prices[j] + dp[k-1][j])  (j从0到i-1)
dp[0][i] 操作0步能获得的最大利润，一定是0
dp[k][0] 只对prices[0]操作 能获得的最大利润，一定也是0

这个DP的好处是不受限于k的值，不论k是几都可以做

```java
class Solution {
    public int maxProfit(int[] prices) {
        int op = 2;
        int len = prices.length;
        if (len <= 1) return 0;
        int[][] dp = new int[op + 1][len];

        for (int i = 0; i < len; i++) {
            dp[0][i] = 0;
        }
        for (int i = 0; i <= op; i++) {
            dp[i][0] = 0;
        }
        
        for (int k = 1; k <= op; k++) {
            for (int i = 1; i < len; i++) {
                for (int j = 0; j < i; j++) {
                    int tmp = Math.max(dp[k][i - 1], prices[i] - prices[j] + dp[k - 1][j]);
                    dp[k][i] = Math.max(tmp, dp[k][i]);
                    
                }
            }
        }

        int res = 0;
        for (int k = 1; k <= op; k++) {
            res = Math.max(dp[k][len - 1], res);
        }
        return res;
    }
}
``` 

126. Word Ladder II
Hard

Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:

Only one letter can be changed at a time
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
Note:

Return an empty list if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.
Example 1:

Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output:
[
  ["hit","hot","dot","dog","cog"],
  ["hit","hot","lot","log","cog"]
]
Example 2:

Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

Output: []

Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.

给定两个单词(开始词和结束词)和一个字典的单词列表，找到从开始词到结束词的所有最短转换序列，这样:

一次只能换一个字母
每个转换后的单词必须存在于单词列表中。注意beginWord不是一个转换后的单词。
注意:

如果没有这样的转换序列，返回一个空列表。
所有的单词都有相同的长度。
所有单词只包含小写字母。
您可以假定单词列表中没有重复。
你可以假设开始词和结束词都是非空的，而且它们是不一样的。


这个比Word Ladder复杂了一个维度，Word Ladder仅仅要求找到最小距离，这个需要把所有最小距离的路径全部显示出来

用bfs，找到dict中所有的词到起始词的最短路径，这一步是Word Ladder的一个加强版，因为要多一步记录距离

然后用dfs，其实也就是trackback，从beginword找到每一个距离值大一的词，并加入队列

这算是相当难的一道题了

```java
class Solution {

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        HashMap<String, Integer> distance = new HashMap<>();
        HashSet<String> dict = new HashSet<>(wordList);
        List<List<String>> res = new ArrayList<List<String>>();
        HashMap<String, ArrayList<String>> nodeNeighbors = new HashMap<String, ArrayList<String>>();// Neighbors for every node
        ArrayList<String> solution = new ArrayList<String>();
        dict.add(beginWord);
        bfs(beginWord, endWord, dict, distance, nodeNeighbors);
        dfs(beginWord, endWord, dict, nodeNeighbors, distance, solution, res);
        return res;

    }

    public void bfs(String beginWord, String endWord, HashSet<String> dict, HashMap<String, Integer> distance, HashMap<String, ArrayList<String>> nodeNeighbors){

        //每个词都有一个邻居数组
        for (String str : dict)
            nodeNeighbors.put(str, new ArrayList<String>());

        distance.put(beginWord, 0);

        LinkedList<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        boolean found = false;
        while(!queue.isEmpty()){


            int cnt = queue.size();
            //使用下面这个循环的原因是，假如第距离为4的词有3个，如果第一个和第二个都能到达end，如果不用下面这个
            //到达end就直接返回，就会导致第二个词的情况被省略掉了，所以，end之前所有距离的词一定都要遍历到
            for(int i=0;i<cnt;i++){
                String cur = queue.poll();
                int curDistance = distance.get(cur);
                ArrayList<String> neighbors = getNeighbors(cur, dict);

                for(String next: neighbors){
                    nodeNeighbors.get(cur).add(next);
                    //如果这个next已经出现过了，那就不用加了，之前加的距离肯定小于等于之后加的
                    if(!distance.containsKey(next)){
                        distance.put(next, curDistance+1);
                        if(endWord.equals(next)){
                            found = true;
                            break;
                        }else{
                            queue.offer(next);
                        }
                    }
                }
            }

            if(found) break;
        }
    }

    public void dfs(String cur, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance, ArrayList<String> solution, List<List<String>> res){
        solution.add(cur);
        if(cur.equals(end)){
            res.add(new ArrayList<>(solution));
        }else{
            for(String next: nodeNeighbors.get(cur)){
                //必须这个邻词的距离满足条件，如果是不满足+1的条件，那么这个序列肯定不是最短的
                if(distance.get(next)==distance.get(cur)+1){
                    dfs(next, end, dict, nodeNeighbors, distance, solution, res);
                }
            }
        }
        solution.remove(solution.size()-1);
    }

    public ArrayList<String> getNeighbors(String str, HashSet<String> dict){
        ArrayList<String> res = new ArrayList<>();
        char[] array = str.toCharArray();
        for(int i=0;i<array.length;i++){
            char old = array[i];
            for(char c = 'a'; c<='z';c++){
                array[i] = c;
                String s = new String(array);
                //不添加str自己
                if(s.equals(str)) continue;

                if(dict.contains(s))
                    res.add(s);
            }
            array[i] = old;
        }
        return res;
    }
}
```

129. Sum Root to Leaf Numbers
Medium

Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.

An example is the root-to-leaf path 1->2->3 which represents the number 123.

Find the total sum of all root-to-leaf numbers.

Note: A leaf is a node with no children.

Example:

Input: [1,2,3]
    1
   / \
  2   3
Output: 25
Explanation:
The root-to-leaf path 1->2 represents the number 12.
The root-to-leaf path 1->3 represents the number 13.
Therefore, sum = 12 + 13 = 25.
Example 2:

Input: [4,9,0,5,1]
    4
   / \
  9   0
 / \
5   1
Output: 1026
Explanation:
The root-to-leaf path 4->9->5 represents the number 495.
The root-to-leaf path 4->9->1 represents the number 491.
The root-to-leaf path 4->0 represents the number 40.
Therefore, sum = 495 + 491 + 40 = 1026.


给定一个只包含0-9位数字的二叉树，每个根到叶的路径都可以表示一个数字。
一个例子是根到叶的路径1->2->3，它表示数字123。
求所有根到叶的数的和。

直接递归即可

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    int res = 0;
    public int sumNumbers(TreeNode root) {
        if(root==null) return 0;
        addParent(root, 0);
        return res;
    }

    public void addParent(TreeNode cur, int preSum){
        if(cur.left==null && cur.right==null){
            res += cur.val + preSum;
        }

        if(cur.left!=null) addParent(cur.left, (cur.val+preSum)*10);
        if(cur.right!=null) addParent(cur.right, (cur.val+preSum)*10);
    }
}
```

132. Palindrome Partitioning II
Hard

Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

Example:

Input: "aab"
Output: 1
Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.


给定一个字符串s，分区s使得分区的每个子字符串都是回文。

返回s的回文分区所需的最小分割次数。

cut[i] 是截止到c[i]的最小分割次数
1. cut[i] = cut[j-1] + 1 (j<=i， 且c[j,i]是回文) 
2. 如果[j,i]是回文，那么[j+1, i-1]是回文，且c[j]==c[i]

pal[j][i] 表示[j...i]是回文
当i==j时其实不用太管，照常处理即可

```java
class Solution {
    public int minCut(String s) {
        char[] c = s.toCharArray();
        int n = c.length;
        int[] cut = new int[n];
        boolean[][] pal = new boolean[n][n];

        for(int i=0;i<n;i++){
            int min=i; //截止到c[i]，最多分割i次，即每个回文都是1个字母
            for(int j=0;j<=i;j++){
                //j+1>i-1 只能在i==j的时候出现，此时只有一个字母，一定是回文
                if(c[i]==c[j] && (j+1>i-1 || pal[j+1][i-1])){
                    pal[j][i] = true;
                    //此时就是把[j...i]当成一个整体，所以只看[0...j-1]，[0...j-1]的分割次数是cut[j-1],所以加上一个[j...i] 就是cut[j-1]+1
                    //当然是在所有的这些里面找最小的
                    min = j==0?0:Math.min(min, cut[j-1]+1);
                }
            }
            cut[i] = min;
        }
        return cut[n-1];
    }
}
```

133. Clone Graph
Medium

Given a reference of a node in a connected undirected graph, return a deep copy (clone) of the graph. Each node in the graph contains a val (int) and a list (List[Node]) of its neighbors.

Example:

Input:
{"$id":"1","neighbors":[{"$id":"2","neighbors":[{"$ref":"1"},{"$id":"3","neighbors":[{"$ref":"2"},{"$id":"4","neighbors":[{"$ref":"3"},{"$ref":"1"}],"val":4}],"val":3}],"val":2},{"$ref":"4"}],"val":1}

Explanation:
Node 1's value is 1, and it has two neighbors: Node 2 and 4.
Node 2's value is 2, and it has two neighbors: Node 1 and 3.
Node 3's value is 3, and it has two neighbors: Node 2 and 4.
Node 4's value is 4, and it has two neighbors: Node 1 and 3.
 

Note:

The number of nodes will be between 1 and 100.
The undirected graph is a simple graph, which means no repeated edges and no self-loops in the graph.
Since the graph is undirected, if node p has node q as neighbor, then node q must have node p as neighbor too.
You must return the copy of the given node as a reference to the cloned graph.

给定连接的无向图中节点的引用，返回图的深度副本(克隆)。图中的每个节点都包含一个val (int)及其邻居的列表(list [node])。

注意:

节点的数量将在1到100之间。
无向图是一个简单的图，这意味着图中没有重复的边和自循环。
由于图是无向的，如果节点p有节点q作为邻居，那么节点q也必须有节点p作为邻居。
您必须返回给定节点的副本作为对克隆图的引用。

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {}

    public Node(int _val,List<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
};
*/
class Solution {
    //把已经创建了新对应节点的节点保存起来，避免重复创建
    HashMap<Node, Node> old2newMap;
    public Node cloneGraph(Node node) {
        old2newMap = new HashMap<>();
        return cloneNode(node);
    }

    public Node cloneNode(Node node){
        Node newOne = new Node();
        newOne.val = node.val;
        old2newMap.put(node, newOne);
        newOne.neighbors = new ArrayList<>();
        for(Node neighbor: node.neighbors){
            if(old2newMap.containsKey(neighbor)){
                //如果有这个邻居对应的新节点，直接加入，否则就新建
                newOne.neighbors.add(old2newMap.get(neighbor));
            }else{
                newOne.neighbors.add(cloneNode(neighbor));
            }
        }
        return newOne;
    }
}
```

135. Candy
Hard

There are N children standing in a line. Each child is assigned a rating value.

You are giving candies to these children subjected to the following requirements:

Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
What is the minimum candies you must give?

Example 1:

Input: [1,0,2]
Output: 5
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
Example 2:

Input: [1,2,2]
Output: 4
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
             The third child gets 1 candy because it satisfies the above two conditions.

有N个孩子站在一排。每个孩子都有一个评分值。

你给予这些儿童糖果，必须符合下列规定:

每个孩子必须至少有一颗糖果。
得分高的孩子比他们的邻居得到更多的糖果。
你至少要给多少糖果?

```java
class Solution {

    //方法一： 能AC，但很慢 Runtime: 1087 ms, faster than 5.01% ACCEPT
    public int candy(int[] ratings) {
        int[] candy = new int[ratings.length];
        int len = ratings.length;
        //初始化一人一颗
        for (int i = 0; i < len; i++) {
            candy[i] = 1;
        }
        //双重循环(n^2)会超时,设置一个位，当该遍不再有任何变化时，说明到了最终结果，就不需要再遍历了
        boolean change = false;
        do{
            change = false;
            for (int j = 0; j < len; j++) {
                int cur = candy[j];
                if (j - 1 >= 0 && ratings[j] > ratings[j - 1])
                    cur = Math.max(cur, candy[j - 1] + 1);
                if (j + 1 < len && ratings[j] > ratings[j + 1])
                    cur = Math.max(cur, candy[j + 1] + 1);
                if (cur!=candy[j]) {
                    change = true;
                    candy[j] = cur;
                }
            }
        }while (change);

        int sum = 0;
        for (int t : candy) {
            sum += t;
        }
        return sum;
    }
}
```

137. Single Number II
Medium

Given a non-empty array of integers, every element appears three times except for one, which appears exactly once. Find that single one.

Note:

Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

Example 1:

Input: [2,2,3,2]
Output: 3
Example 2:

Input: [0,1,0,1,0,1,99]
Output: 99

给定一个非空整数数组，每个元素出现三次，只有一个元素出现一次。找到那一个。

注意:

您的算法应该具有线性运行时复杂度。你能在不使用额外内存的情况下实现它吗?

既要O(n)的时间复杂度，又要O(1)的空间复杂度

假设输入中没有single number，那么输入中的每个数字都重复出现了数字，也就是说，对这32位中的每一位i而言，所有的输入加起来之后，第i位一定是3的倍数。
现在增加了single number，那么对这32位中的每一位做相同的处理，也就是说，逐位把所有的输入加起来，并且看看第i位的和除以3的余数，这个余数就是single numer在第i位的取值。这样就得到了single number在第i位的取值。这等价于一个模拟的二进制，接着只需要把这个模拟的二进制转化为十进制输出即可。
为了完成上面的过程，需要使用一个数组 int a[ 32 ]来模拟位运算。
可以看出来，这个做法对于功力的要求非常高，需要看到数字的时候，眼前展现的是数字背后的二进制组成，要不然很难想到可以逐位去处理。

另外，这个做法可以扩展，如果有一堆输入，其中1个数字出现了1次，剩下的数字出现了K次，这样的问题全部可以使用这样的办法来做。

```java
class Solution {
    public int singleNumber(int[] nums) {
        int[] count = new int[32];
        int len = nums.length;
        for(int i=0;i<32;i++){
            for(int j=0;j<len;j++){
                //数字右移i位后再与1相与，结果即为第i位上的数字(当然是1或0)
                count[i] += ((nums[j]>>i)&1);
            }
        }

        int result = 0;
        for(int i=0;i<32;i++){
            //如果singlenum的第i位是1，那么算下来count[i]就是1，如果是0就是0
            //因为该位上的次数，要么是1次，要么是3次(余3变0)，要么是4次（余3就变成1了），不存在2次的情况
            count[i] = count[i]%3;
            //把count[i]放到result的第i位上，就用|的方式
            result |= count[i]<<i;
        }
        return result;
    }
}
```

143. Reorder List
Medium

Given a singly linked list L: L0→L1→…→Ln-1→Ln,
reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…

You may not modify the values in the list's nodes, only nodes itself may be changed.

Example 1:

Given 1->2->3->4, reorder it to 1->4->2->3.
Example 2:

Given 1->2->3->4->5, reorder it to 1->5->2->4->3.

给一个单链表 L0→L1→…→Ln-1→Ln,
重排序成  L0→Ln→L1→Ln-1→L2→Ln-2→…
您不能修改列表节点中的值，只能修改节点本身。

利用数组很方便
```java

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {

    public void reorderList(ListNode head) {
        int len = 0;
        if(head==null) return;
        ListNode node = head;
        ArrayList<ListNode> list = new ArrayList<>();
        while(node!=null){
            list.add(node);
            node = node.next;
            len++;
        }

        int tt1 = 0;
        int tt2 = len-1;
        ListNode t1 = list.get(tt1);
        ListNode t2 = list.get(tt2);
        //交替让t1和t2互相成为next，t1从前往后走，t2从后往前走
        boolean t1First = true;
        while(tt1<tt2){
            if(t1First){
                t1.next = t2;
                t1 = list.get(++tt1);
            }else{
                t2.next = t1;
                t2 = list.get(--tt2);
            }
            t1First = !t1First;
        }

        list.get(tt1).next = null;
    }
}

```

144. Binary Tree Preorder Traversal
Medium

Given a binary tree, return the preorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [1,2,3]
Follow up: Recursive solution is trivial, could you do it iteratively?

返回一颗二叉树的前序遍历，不用递归的做法

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    //前序
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        TreeNode node = null;
        Stack<TreeNode> stk = new Stack<>();
        stk.push(root);
        while (!stk.isEmpty()) {
            node = stk.pop();
            res.add(node.val);
            if (node.right != null) stk.push(node.right);
            if (node.left != null) stk.push(node.left);

        }
        return res;
    }

        //中序
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        TreeNode node = root;
        Stack<TreeNode> stk = new Stack<>();
        while (!stk.isEmpty() || node != null) {
            if (node != null) {
                stk.push(node);
                node = node.left;
            } else {
                node = stk.pop();
                res.add(node.val);
                node = node.right;
            }
        }
        return res;
    }
}
```
## 需要记住，前序只有一个循环，里面分别是check值，加右孩子，加左孩子
## 中序也只有一个循环，里面有if-else判断node是否为空，不空就继续挪到左孩子，空就check栈中值
## 后序是一个大循环里面有个小循环，有一个pre节点保存上一个节点。用tmp节点保存当前栈顶节点的右孩子

145. Binary Tree Postorder Traversal
Hard

959

47

Favorite

Share
Given a binary tree, return the postorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [3,2,1]
Follow up: Recursive solution is trivial, could you do it iteratively?

非递归的后续遍历

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> stk = new Stack<>();
        ArrayList<Integer> res = new ArrayList<>();
        TreeNode pre = null;
        TreeNode node = root;
        while(node!=null || !stk.isEmpty()){
            while(node!=null){
                stk.push(node);
                node = node.left;
            }
            TreeNode tmp = stk.peek().right;
            if(tmp==null || pre == tmp){  //走到这里该节点的左子树已经处理完毕，如果该节点的右子树为空或者右子树已经处理过，则输出该节点
                node = stk.pop();
                res.add(node.val);
                pre = node;   //记录上一个输出的节点
                node = null;
            }else{
                node = tmp;  //处理右子树
            }
        }
        return res;
    }
}
```

147. Insertion Sort List
Medium

Sort a linked list using insertion sort.

A graphical example of insertion sort. The partial sorted list (black) initially contains only the first element in the list.
With each iteration one element (red) is removed from the input data and inserted in-place into the sorted list
 
Algorithm of Insertion Sort:

Insertion sort iterates, consuming one input element each repetition, and growing a sorted output list.
At each iteration, insertion sort removes one element from the input data, finds the location it belongs within the sorted list, and inserts it there.
It repeats until no input elements remain.

Example 1:

Input: 4->2->1->3
Output: 1->2->3->4
Example 2:

Input: -1->5->3->4->0
Output: -1->0->3->4->5


使用插入排序对链表排序。

插入排序的图形示例。部分排序列表(黑色)最初只包含列表中的第一个元素。
每次迭代都从输入数据中删除一个元素(红色)，并插入到排序列表中

插入排序算法:

插入排序迭代，每次重复使用一个输入元素，并生成一个排序后的输出列表。
在每次迭代中，插入排序从输入数据中删除一个元素，找到它在已排序列表中的位置，并将其插入其中。
它不断重复，直到没有输入元素保留。

插入排序就必须每遍历到一个数时，就从头遍历到当前（在前面已排序列中找到其合适的位置）
这种题如果用数组就不用来回遍历了，但感觉链表的题用数组就失去了精髓（虽然有时候不得不用）

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {

    public ListNode insertionSortList(ListNode head) {
        if(head==null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode cur = head.next;
        int orderCount = 1;  //前面已经有序的节点个数
        while(cur!=null){
            //insert node
            ListNode node = dummy.next;
            ListNode pre = dummy;
            boolean foundPos = false;
            for(int i=0;i<orderCount;i++){
                //每次都看cur是否能放到node的前面
                if(node.val > cur.val){
                    ListNode tmp = cur.next;
                    pre.next = cur;
                    cur.next = node;
                    cur = tmp;
                    orderCount++;
                    foundPos = true;
                    break;
                }
                pre = node;
                node = node.next;
            }


            //找到有序的最后一个节点，如果foundPos为false，则pre已经是最后一个节点，不用再找了
            if(foundPos){
                node = dummy.next;
                pre = dummy;
                for(int i=0;i<orderCount && foundPos;i++){
                    pre = node;
                    node = node.next;
                }
            }

            //如果没有在已排序列中找到合适的位置，说明cur是最后一个元素，其本身就在合适的位置
            //此时，node是指向排序外的元素，pre是最后一个排好序的元素
            if(!foundPos){
                orderCount++;
                pre.next = cur;
                ListNode tmp = cur.next;
                cur.next = null;
                cur = tmp;
            }else{
                //找到了需要把有序节点的最后一个节点next置null
                pre.next = null;
            }
        }
        return dummy.next;
    }
}
```

153. Find Minimum in Rotated Sorted Array
Medium

Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

You may assume no duplicate exists in the array.

Example 1:

Input: [3,4,5,1,2] 
Output: 1
Example 2:

Input: [4,5,6,7,0,1,2]
Output: 0

假设一个按升序排序的数组在某个未知的主轴上旋转。
(即。,[0,1,2,4,5,6,7]可能成为(4、5、6、7 0,1,2))。
找出最小的元素。
您可以假设数组中不存在重复值。

分段用二分查找

原理见下面那道题的解释

```java
class Solution {
    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length-1;
        int mid = 0;
        while(high>low){
            mid = low + (high-low)/2;
            if(nums[mid]>nums[high])
                //小的在后半部分
                low = mid+1;
            else
                //小的在前半部分，但最大的数也在前半部分，因为后面是正序，前面就是两段序
                high = mid;
        }
        return nums[low];
    }
}
```

154. Find Minimum in Rotated Sorted Array II
Hard

Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

The array may contain duplicates.

Example 1:

Input: [1,3,5]
Output: 1
Example 2:

Input: [2,2,2,0,1]
Output: 0
Note:

This is a follow up problem to Find Minimum in Rotated Sorted Array.
Would allow duplicates affect the run-time complexity? How and why?

假设一个按升序排序的数组在某个未知的主轴上旋转。

找出最小的元素。

数组可能包含重复项。

比上面的题多了一个重复项，但整个思想就变了

# 这种二分查找一定要记住

## 其实只要判断high和mid指的数的大小关系就能确定前后的序了

因为：

》nums[mid]>nums[high]时，说明mid到high之间有两段序，最小值一定在之间

》nums[mid]< nums[high]时，说明mid到high之间是顺序，最小值一定在这个区间之外（或者就是mid）

上面两种情况对于不重复的情况已经够用了，下面是有数字重复的情况

》nums[mid] == nums[high]时，可知道mid到high之间的数字可能都是相同的，也可能是不同的，
例如： 2 2 2 2 1 2 2 2
不知道最小值在哪，可能在前面，也可能在后面，这种情况就可以直接让high-1,既可以缩小范围，又不会丢数（因为mid数和high数一样大，所以不会丢失）

下面的代码之间可以用到上一道题上
```java
class Solution {
    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length-1;
        int mid = 0;
        while(high>low){
            mid = low + (high-low)/2;
            if(nums[mid]>nums[high])
                //小的在后半部分
                low = mid+1;
            else if(nums[mid]<nums[high])
                //小的在前半部分，但最大的数也在前半部分，因为后面是正序，前面就是两段序
                high = mid;
            else
                //nums[mid]==nums[high] 
                //When num[mid] == num[high], we couldn't sure the position of minimum in mid's left or right, so just let upper 
                //bound reduce one.
                high--;
        }
        return nums[low];
    }
}
```

这段代码返回数组的最小值是正确的。但就“求最小值索引”而言，这是不对的。
考虑这个例子: 1 1 1 1 1 1 1 1 2 1 1
返回的最小索引是0，而实际上应该是9。
对于这个例子: 2 2 2 2 2 2 2 2 1 2 2
它将返回正确的索引，即8。

原因是，主索引将在hi——处传递。为了避免这种情况，我们可以加上以下判断:

```java

//把上面的else改造成下面这样
else {
    if (nums[hi - 1] > nums[hi]) {
        lo = hi;
        break;
    }
    hi--;
}
```

164. Maximum Gap
Hard

Given an unsorted array, find the maximum difference between the successive elements in its sorted form.

Return 0 if the array contains less than 2 elements.

Example 1:

Input: [3,6,9,1]
Output: 3
Explanation: The sorted form of the array is [1,3,6,9], either
             (3,6) or (6,9) has the maximum difference 3.
Example 2:

Input: [10]
Output: 0
Explanation: The array contains less than 2 elements, therefore return 0.
Note:

You may assume all elements in the array are non-negative integers and fit in the 32-bit signed integer range.
Try to solve it in linear time/space.


给定一个未排序的数组，找出其排序形式中连续元素之间的最大差值。

如果数组包含少于2个元素，则返回0。

您可以假设数组中的所有元素都是非负整数，并且符合32位带符号整数范围。
试着在线性时间/空间中求解。

用线性时间意味着不能排序，线性空间意味着不能存储全部元素

假设数组中有n个元素，最小和最大的数分别是min和max，那么相邻两个数之间的最大的跨度不会小于 tGap = ceil{(max-min)/(n-1)} 
这个很好理解，因为如果n个数是等差的话，那两个相邻数的差就是tGap，不是等差的话，则一定会有gap比tGap大（当然也会有gap比tGap小）
我们可以把这n个数安排到n-1个桶中，每个桶的范围为tGap，第k个桶包含数的范围是[min+（k-1） * tGap,  min+ k * tGap )  (k从0开始)

对于一个数x，它所属的桶的编号是 (x-min)/tGap   (单让min和max不属于任何一个桶)

因为除去min和max，有n-2个数字，而有n-1个桶，所以至少有一个桶是空的，对于每一个桶，
我们只需要存储其中最大的数和最小的数即可，存储完成后，
只需计算 相邻桶的 后桶最小数-前桶最大数，并找出其中最大的值即可

因为同一个桶中距离最大也就是tGap(桶中最大数-桶中最小数)，而由上面的论证可知数列中的最大差值最小也的是tGap，所以同一个桶中的不用算


```java
class Solution {
    public int maximumGap(int[] nums) {
        if(nums==null || nums.length<=1) return 0;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i:nums){
            max = Math.max(i, max);
            min = Math.min(i, min);
        }

        int tGap = (int)Math.ceil((double)(max-min)/(nums.length-1));
        //共n-1个桶，记录每个桶中最大元素和最小元素
        int[] minInEachBucket = new int[nums.length-1];
        int[] maxInEachBucket = new int[nums.length-1];
        Arrays.fill(minInEachBucket, Integer.MAX_VALUE);
        Arrays.fill(maxInEachBucket, Integer.MIN_VALUE);

        for(int i:nums){
            if(i==max || i==min) continue;
            int index = (i-min)/tGap;
            minInEachBucket[index] = Math.min(i, minInEachBucket[index]);
            maxInEachBucket[index] = Math.max(i, maxInEachBucket[index]);

        }

        //res不会小于tGap
        int res = tGap;
        int preMax = min;
        for(int i=0;i<nums.length-1;i++){
            if(minInEachBucket[i]==Integer.MAX_VALUE && maxInEachBucket[i]==Integer.MIN_VALUE)
                //空桶
                continue;
            //当前桶中最小的
            res = Math.max(res, minInEachBucket[i]-preMax);
            preMax = maxInEachBucket[i];
        }

        //单独处理最后一个，即最大值和最后一个桶中的最大值的差
        res = Math.max(res, max-preMax);

        return res;
    }
}
```

165. Compare Version Numbers
Medium

Compare two version numbers version1 and version2.
If version1 > version2 return 1; if version1 < version2 return -1;otherwise return 0.

You may assume that the version strings are non-empty and contain only digits and the . character.

The . character does not represent a decimal point and is used to separate number sequences.

For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level revision of the second first-level revision.

You may assume the default revision number for each level of a version number to be 0. For example, version number 3.4 has a revision number of 3 and 4 for its first and second level revision number. Its third and fourth level revision number are both 0.

Example 1:

Input: version1 = "0.1", version2 = "1.1"
Output: -1
Example 2:

Input: version1 = "1.0.1", version2 = "1"
Output: 1
Example 3:

Input: version1 = "7.5.2.4", version2 = "7.5.3"
Output: -1
Example 4:

Input: version1 = "1.01", version2 = "1.001"
Output: 0
Explanation: Ignoring leading zeroes, both “01” and “001" represent the same number “1”
Example 5:

Input: version1 = "1.0", version2 = "1.0.0"
Output: 0
Explanation: The first version number does not have a third level revision number, which means its third level revision number is default to "0"

Note:

Version strings are composed of numeric strings separated by dots . and this numeric strings may have leading zeroes.
Version strings do not start or end with dots, and they will not be two consecutive dots.


比较两个版本号version1和version2。
如果version1 > version2返回1;如果version1 < version2返回-1，否则返回0。

您可以假设版本字符串是非空的，并且只包含数字和.

.字符不表示小数点，用于分隔数字序列。

例如，2.5并不是“2.5版”或“到第三版的半路”，它是第二次一级修订的第5次二级修订。

您可以假定版本号的每个级别的默认修订号为0。例如，版本号3.4的第一级和第二级修订号的修订号分别为3和4。第三、四级修订号均为0。

版本字符串由点号分隔的数字字符串组成。这个数字字符串可能有前导零。
版本字符串不会以点开始或结束，它们也不会是两个连续的点。

直接从前往后比较即可

# 这里要注意的是，split里面是正则表达式，有的符号可以直接放，有的不行，（特殊情况有 * ^ : | . \）
# 像这道题，直接用split(".")是没有用的。必须要split("\\.") 才能正确分割

```java
class Solution {

    public int compareVersion(String version1, String version2) {
        String[] array1 = version1.split("\\.");
        String[] array2 = version2.split("\\.");
        int len1 = array1.length;
        int len2 = array2.length;
        int minLen = Math.min(array1.length, array2.length);
        int maxLen = Math.max(array1.length, array2.length);
        for(int i=0;i<minLen;i++){
            int t1 = Integer.valueOf(array1[i]);
            int t2 = Integer.valueOf(array2[i]);
            if(t1>t2){
                return 1;
            }else if(t1<t2){
                return -1;
            }else{
                continue;
            }
        }
        //能走到这里，说明前面全是相等的
        //如果长度再相等，那两个版本号相等
        if(len1==len2) return 0;
        //否则看长的那个后面是不是都是0
        String[] longArray = len1>len2?array1:array2;
        int rest = 0;
        for(int i=minLen;i<maxLen;i++){
            rest += Integer.valueOf(longArray[i]);
        }
        if(rest==0){
            //如果后面全是0，则两个版本号相等
            return 0;
        }else{
            //否则就是版本号长的大
            return len1>len2?1:-1;
        }
    }
}
```

167. Two Sum II - Input array is sorted
Easy

Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.

The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2.

Note:

Your returned answers (both index1 and index2) are not zero-based.
You may assume that each input would have exactly one solution and you may not use the same element twice.
Example:

Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore index1 = 1, index2 = 2.

给定一个已经按升序排序的整数数组，找到两个数字，使它们加起来等于一个特定的目标数字。

函数twoSum应该返回这两个数字的索引，使它们之和等于目标，其中index1必须小于index2。

```java
class Solution {

    public int[] twoSum(int[] numbers, int target) {

        int[] res = new int[2];
        for(int i=0;i<=numbers.length-1;i++){
            int cur = numbers[i];
            int suppIndex = binarySearch(numbers, 0, numbers.length-1, target-cur);
            if(suppIndex!=-1){
                //如果是索引相等，则看后面一个数是不是也是相等，等的话直接让suppIndex等于后面那个数的索引
                //这一步很重要，主要是解决某个数重复出现的情况
                if(suppIndex==i){
                    if(i+1<numbers.length && numbers[i+1]==numbers[i]){
                        suppIndex += 1;
                    }
                }
                res[0] = (i>suppIndex?suppIndex:i)+1;
                res[1] = (i<suppIndex?suppIndex:i)+1;
                break;
            }
        }
        return res;
    }

    //二分查找
    int binarySearch(int[] numbers, int low, int high, int target){
        while(low<=high){
            int mid = (low+high)>>1;
            if(numbers[mid]==target) return mid;
            else if(numbers[mid]>target){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        //没找到
        return -1;
    }
}
```

168. Excel Sheet Column Title
Easy

Given a positive integer, return its corresponding column title as appear in an Excel sheet.

For example:

    1 -> A
    2 -> B
    3 -> C
    ...
    26 -> Z
    27 -> AA
    28 -> AB 
    ...
Example 1:

Input: 1
Output: "A"
Example 2:

Input: 28
Output: "AB"
Example 3:

Input: 701
Output: "ZY"

给定一个正整数，返回其对应的列标题，如Excel表中所示。

看作十进制转26进制，就是看除以26的商数和余数，但这里没有0数，所以在整倍数时要修正

```java
class Solution {
    public String convertToTitle(int n) {
        final String[] symbol = {"Z", "A","B","C","D","E","F","G","H","I","J","K",
                "L","M","N","O","P","Q","R","S","T","U","V","W","X","Y"};
        StringBuilder builder = new StringBuilder();
        while(n>0){
            int cur = n%26;
            builder.insert(0, symbol[cur]);

            n /= 26;

            //这一步及其重要，其意义在于，如果当前n是26的倍数，再除以26的时候会多算一遍，所以要减去
            //如52对应 AZ，当52%26=0，而52/26=2 这个2其实应该是1，因为这里的进制没有0数（如0，10，20这种数），所以要减去进位时多算的一个
            //53对应 BA 53%26=1 53/26=2 由于不是26的倍数，所以就不用减1
            if(cur==0)
                n--;
        }
        return builder.toString();
    }
}
```

173. Binary Search Tree Iterator
Medium

Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.

Calling next() will return the next smallest number in the BST.


     7
   3  15
     9  20

Example:

BSTIterator iterator = new BSTIterator(root);
iterator.next();    // return 3
iterator.next();    // return 7
iterator.hasNext(); // return true
iterator.next();    // return 9
iterator.hasNext(); // return true
iterator.next();    // return 15
iterator.hasNext(); // return true
iterator.next();    // return 20
iterator.hasNext(); // return false
 
Note:

next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
You may assume that next() call will always be valid, that is, there will be at least a next smallest number in the BST when next() is called.


在二叉搜索树(BST)上实现迭代器。迭代器将使用BST的根节点初始化。

调用next()将返回BST中的下一个最小的数字。

注意:

next()和hasNext()应该在平均O(1)时间内运行，并使用O(h)内存，其中h是树的高度。
您可以假设next()调用总是有效的，也就是说，当调用next()时，BST中至少会有下一个最小的数。

可以一开始就把中序遍历保存起来，也能AC

下面这种方法更好,稍快

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class BSTIterator {
        int curIndex;
        Stack<TreeNode> stk;
        public BSTIterator(TreeNode root) {
            stk = new Stack<>();
            TreeNode cur = root;

            //相当于中序遍历的前一部分，有左则入左
            while(cur!=null){
                stk.push(cur);
                if(cur.left!=null){
                    cur = cur.left;
                }else{
                    break;
                }
            }
        }

        /** @return the next smallest number */
        public int next() {
            TreeNode node = stk.pop();
            TreeNode cur = node;
            //当某个节点输出时，它的左子树肯定已经输出过了，然后处理右子树，当然还是顺着左边处理
            if(cur.right!=null){
                cur = cur.right;
                while(cur!=null){
                    stk.push(cur);
                    if(cur.left!=null){
                        cur = cur.left;
                    }else{
                        break;
                    }
                }
            }
            return node.val;

        }

        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return !stk.isEmpty();
        }
    
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
```

174. Dungeon Game
Hard

The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon. The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially positioned in the top-left room and must fight his way through the dungeon to rescue the princess.

The knight has an initial health point represented by a positive integer. If at any point his health point drops to 0 or below, he dies immediately.

Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering these rooms; other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers).

In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.

Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.

For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.

-2 (K)  -3  3
-5  -10 1
10  30  -5 (P)
 

Note:

The knight's health has no upper bound.
Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.

恶魔抓住了公主(P)，把她囚禁在地牢的右下角。地牢由M x N个房间组成，以二维网格布局。我们英勇的骑士(K)最初被安置在左上方的房间里，他必须通过地牢来营救公主。

骑士的初始生命值点由一个正整数表示。如果他的健康值下降到0或更低，他会立即死亡。

有些房间由恶魔守卫，因此骑士在进入这些房间时失去生命值(负整数);其他房间要么是空的(0)，要么是包含增加骑士生命值的魔法球(正整数)。

为了尽快到达公主，骑士决定每一步只向右或向下移动。

编写一个函数来确定骑士的最低初始生命值，以便他能够拯救公主。

注意:

骑士的健康没有上限。
任何房间都可能包含威胁或能量提升，甚至是骑士进入的第一个房间和公主被囚禁的右下角房间。

思路：在走完最后一个房间的时候血量至少要剩下１，因此最后的状态可以当成是初始状态，由后往前依次决定在每一个位置至少要有多少血量, 这样一个位置的状态是由其下面一个和和左边一个的较小状态决定 ．因此一个基本的状态方程是: dp[i][j] = min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j]. 

但是还有一个条件就是在每一个状态必须血量都要大于１，因此我们还需要一个方程在保证在每一个状态都大于１，即：dp[i][j] = max(dp[i][j], 1);　也就是说虽然当前的血量到最后能够剩下１，但是现在已经低于１了，我们需要为其提升血量维持每一个状态至少都为１．

# 这是理解的关键：：
dp[i][j]是进入该房间之前能保证进入该房间后存活的最小血量(是一个准备进入但还没有进入的状态)


```java
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int row = dungeon.length;
        int col = dungeon[0].length;
        int[][] dp = new int[row][col];

        for(int i=row-1;i>=0;i--){
            for(int j=col-1;j>=0;j--){
                //如果最后一个格子是正数，那dp=1,如果是负数，则dp=1-dungeon，综合来看就是max(1, 1-dungeon[row-1][col-1]);
                if(i==row-1 && j==col-1) dp[i][j] = Math.max(1, 1-dungeon[row-1][col-1]);
                else if(i==row-1){
                    dp[i][j] = Math.max(dp[i][j+1]-dungeon[i][j], 1);
                }else if(j==col-1){
                    dp[i][j] = Math.max(dp[i+1][j]-dungeon[i][j], 1);
                }else{
                    dp[i][j] = Math.min(dp[i+1][j], dp[i][j+1])-dungeon[i][j];
                    dp[i][j] = Math.max(dp[i][j], 1);
                }
            }
        }
        return dp[0][0];
    }
}
```

175. Combine Two Tables
Easy

SQL Schema
Table: Person

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| PersonId    | int     |
| FirstName   | varchar |
| LastName    | varchar |
+-------------+---------+
PersonId is the primary key column for this table.
Table: Address

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| AddressId   | int     |
| PersonId    | int     |
| City        | varchar |
| State       | varchar |
+-------------+---------+
AddressId is the primary key column for this table.
 
Write a SQL query for a report that provides the following information for each person in the Person table, regardless if there is an address for each of those people:

FirstName, LastName, City, State

编写sql查询，查询的内容是每个在person表中的每行数据的 FirstName, LastName, City, State，不论该数据是否有address

显然是以左表为主体的查询，用左连接

```sql
select p.FirstName, p.LastName, a.City, a.State
from Person as p
left join Address as a
on p.PersonId = a.PersonId;
```

176. Second Highest Salary
Easy

SQL Schema
Write a SQL query to get the second highest salary from the Employee table.

+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
For example, given the above Employee table, the query should return 200 as the second highest salary. If there is no second highest salary, then the query should return null.

+---------------------+
| SecondHighestSalary |
+---------------------+
| 200                 |
+---------------------+

获得工资表中第二高的工资

使用max函数，并记得给结果字段添加别名
如果值不存在，使用max()将返回NULL。所以没有必要合并一个NULL。当然，如果第二个最大值保证存在，使用LIMIT 1,1将是最佳答案。

```sql
select max(Salary) as SecondHighestSalary
from Employee
where Salary < (select max(Salary) from Employee);
```

177. Nth Highest Salary
Medium

Write a SQL query to get the nth highest salary from the Employee table.

+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
For example, given the above Employee table, the nth highest salary where n = 2 is 200. If there is no nth highest salary, then the query should return null.

+------------------------+
| getNthHighestSalary(2) |
+------------------------+
| 200                    |
+------------------------+

获得第n高的工资

要注意的是limit的起始偏移是0，所以M=N-1

还要注意的是sql函数的写法

```sql
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
DECLARE M INT;
SET M=N-1;
  RETURN (
      # Write your MySQL query statement below.
      select distinct Salary from Employee order by Salary desc limit M, 1
  );
END
```

178. Rank Scores
Medium

SQL Schema
Write a SQL query to rank scores. If there is a tie between two scores, both should have the same ranking. Note that after a tie, the next ranking number should be the next consecutive integer value. In other words, there should be no "holes" between ranks.

+----+-------+
| Id | Score |
+----+-------+
| 1  | 3.50  |
| 2  | 3.65  |
| 3  | 4.00  |
| 4  | 3.85  |
| 5  | 4.00  |
| 6  | 3.65  |
+----+-------+
For example, given the above Scores table, your query should generate the following report (order by highest score):

+-------+------+
| Score | Rank |
+-------+------+
| 4.00  | 1    |
| 4.00  | 1    |
| 3.85  | 2    |
| 3.65  | 3    |
| 3.65  | 3    |
| 3.50  | 4    |
+-------+------+

编写一个SQL查询来对分数进行排序。如果两个分数之间存在平手，那么它们的排名应该是相同的。注意，在平手之后，下一个排名数字应该是下一个连续的整数值。换句话说，rank之间不应该有“漏洞”。

看不懂，学习了

```sql
SELECT
  Score,
  @rank := @rank + (@prev <> (@prev := Score)) Rank
FROM
  Scores,
  (SELECT @rank := 0, @prev := -1) init
ORDER BY Score desc
```

Write a SQL query to find all numbers that appear at least three times consecutively.

+----+-----+
| Id | Num |
+----+-----+
| 1  |  1  |
| 2  |  1  |
| 3  |  1  |
| 4  |  2  |
| 5  |  1  |
| 6  |  2  |
| 7  |  2  |
+----+-----+
For example, given the above Logs table, 1 is the only number that appears consecutively for at least three times.

+-----------------+
| ConsecutiveNums |
+-----------------+
| 1               |
+-----------------+

可以把logs看成三个表，直接多表联查

```sql
Select DISTINCT l1.Num as ConsecutiveNums 
from Logs as l1, Logs as l2, Logs as l3 
where l1.Id=l2.Id-1 and l2.Id=l3.Id-1 
and l1.Num=l2.Num and l2.Num=l3.Num;
```

181. Employees Earning More Than Their Managers
Easy

The Employee table holds all employees including their managers. Every employee has an Id, and there is also a column for the manager Id.

+----+-------+--------+-----------+
| Id | Name  | Salary | ManagerId |
+----+-------+--------+-----------+
| 1  | Joe   | 70000  | 3         |
| 2  | Henry | 80000  | 4         |
| 3  | Sam   | 60000  | NULL      |
| 4  | Max   | 90000  | NULL      |
+----+-------+--------+-----------+
Given the Employee table, write a SQL query that finds out employees who earn more than their managers. For the above table, Joe is the only employee who earns more than his manager.

+----------+
| Employee |
+----------+
| Joe      |
+----------+


182/5000
给定Employee表，编写一个SQL查询，查找收入高于其经理的员工。 对于上表，Joe是唯一一位收入超过其经理的员工。

# from 表名 as 别名，，这个别名就相当于表中的每一条数据了，可以用点操作符.来进行操作

```sql
select Name as Employee from Employee as e
where e.Salary > (select Salary from Employee where e.ManagerId=Id);
```

182. Duplicate Emails
Easy

SQL Schema
Write a SQL query to find all duplicate emails in a table named Person.

+----+---------+
| Id | Email   |
+----+---------+
| 1  | a@b.com |
| 2  | c@d.com |
| 3  | a@b.com |
+----+---------+
For example, your query should return the following for the above table:

+---------+
| Email   |
+---------+
| a@b.com |
+---------+
Note: All emails are in lowercase.

找出email出现次数大于1的所有

由sql语句的执行顺序：先选取表，再分组，对于例子，按照Email分成了两组，对每一组运行聚合函数，再用having筛选分组，筛选后选取Email

```sql
select Email  #5
from person   #1
group by Email  #2
having  #4
count(*) > 1;  #3
```

183. Customers Who Never Order
Easy

SQL Schema
Suppose that a website contains two tables, the Customers table and the Orders table. Write a SQL query to find all customers who never order anything.

Table: Customers.

+----+-------+
| Id | Name  |
+----+-------+
| 1  | Joe   |
| 2  | Henry |
| 3  | Sam   |
| 4  | Max   |
+----+-------+
Table: Orders.

+----+------------+
| Id | CustomerId |
+----+------------+
| 1  | 3          |
| 2  | 1          |
+----+------------+
Using the above tables as example, return the following:

+-----------+
| Customers |
+-----------+
| Henry     |
| Max       |
+-----------+


假设一个网站包含两个表，customer表和Orders表。编写一个SQL查询来查找所有从不订购任何东西的客户。

选取在表2中从来没有出现过的表一中的人

先选出表1中在表2中出现过的id号，再从表1中选出去掉这些id号剩下行的Name

注意一点：对于多个表联查，要select的字段可能在每个表中都有，为了不引起歧义（不知道要取哪个表中的数据），需要在字段使用：  表名.字段名

```sql
select Name as Customers
from Customers as cu
where cu.Id not in(
select c.Id
from Customers as c
right join Orders as o
on o.CustomerId=c.Id
);

```

184. Department Highest Salary
Medium

SQL Schema
The Employee table holds all employees. Every employee has an Id, a salary, and there is also a column for the department Id.

+----+-------+--------+--------------+
| Id | Name  | Salary | DepartmentId |
+----+-------+--------+--------------+
| 1  | Joe   | 70000  | 1            |
| 2  | Jim   | 90000  | 1            |
| 3  | Henry | 80000  | 2            |
| 4  | Sam   | 60000  | 2            |
| 5  | Max   | 90000  | 1            |
+----+-------+--------+--------------+
The Department table holds all departments of the company.

+----+----------+
| Id | Name     |
+----+----------+
| 1  | IT       |
| 2  | Sales    |
+----+----------+
Write a SQL query to find employees who have the highest salary in each of the departments. For the above tables, your SQL query should return the following rows (order of rows does not matter).

+------------+----------+--------+
| Department | Employee | Salary |
+------------+----------+--------+
| IT         | Max      | 90000  |
| IT         | Jim      | 90000  |
| Sales      | Henry    | 80000  |
+------------+----------+--------+
Explanation:

Max and Jim both have the highest salary in the IT department and Henry has the highest salary in the Sales department.

编写一个SQL查询，查找每个部门中工资最高的员工。对于上面的表，您的SQL查询应该返回以下行(行顺序无关紧要)。

解释:

尝试将查询分成三个部分。
第1部分)
select dep.Name as Department, emp.Name as Employee, emp.Salary
from Department dep, Employee emp
where emp.DepartmentId=dep.Id

这将返回所有行

第2部分
Select max(Salary) from Employee e2 where e2.DepartmentId=dep.Id

需要注意的重要一点是，e2是另一个实例(另一个表)，我们从其中选择了最高工资。我们对每个员工的部门都进行了调整，因此每个员工都在与自己的部门进行比较。注意，这是一种关联查询形式，因为dep是在外部循环中指定的。

第3部分
使用AND条件。AND条件很重要，因为它限制了外部和内部的查询

讲实话没看懂

```sql
select dep.Name as Department, emp.Name as Employee, emp.Salary
from Department dep, Employee emp
where emp.DepartmentId=dep.Id
and emp.Salary=(Select max(Salary) from Employee e2 where e2.DepartmentId=dep.Id);
```

185. Department Top Three Salaries
Hard

SQL Schema
The Employee table holds all employees. Every employee has an Id, and there is also a column for the department Id.

+----+-------+--------+--------------+
| Id | Name  | Salary | DepartmentId |
+----+-------+--------+--------------+
| 1  | Joe   | 85000  | 1            |
| 2  | Henry | 80000  | 2            |
| 3  | Sam   | 60000  | 2            |
| 4  | Max   | 90000  | 1            |
| 5  | Janet | 69000  | 1            |
| 6  | Randy | 85000  | 1            |
| 7  | Will  | 70000  | 1            |
+----+-------+--------+--------------+
The Department table holds all departments of the company.

+----+----------+
| Id | Name     |
+----+----------+
| 1  | IT       |
| 2  | Sales    |
+----+----------+
Write a SQL query to find employees who earn the top three salaries in each of the department. For the above tables, your SQL query should return the following rows (order of rows does not matter).

+------------+----------+--------+
| Department | Employee | Salary |
+------------+----------+--------+
| IT         | Max      | 90000  |
| IT         | Randy    | 85000  |
| IT         | Joe      | 85000  |
| IT         | Will     | 70000  |
| Sales      | Henry    | 80000  |
| Sales      | Sam      | 60000  |
+------------+----------+--------+
Explanation:

In IT department, Max earns the highest salary, both Randy and Joe earn the second highest salary, and Will earns the third highest salary. There are only two employees in the Sales department, Henry earns the highest salary while Sam earns the second highest salary.


编写一个SQL查询，查找每个部门中工资最高的三个员工。对于上面的表，您的SQL查询应该返回以下行(行顺序无关紧要)。


第一部分：
from Employee e1 
join Department d
on e1.DepartmentId = d.Id
e1和d组成的结果表，假设该结果表名为m，每一行都是e1的部门id等于d的id，然后再在该表和Employee联合 进行第二部分的筛选，


第二部分：
```sql
select count(distinct(e2.Salary)) 
                  from Employee e2 
                  where e2.Salary > e1.Salary 
                  and e1.DepartmentId = e2.DepartmentId
```

先分析如下sql语句
select *
from Employee e1, Employee e2
where e2.Salary > e1.Salary 
and e1.DepartmentId = e2.DepartmentId

比如原来的表是
+----+-------+--------+--------------+
| id | name  | salary | departmentid |
+----+-------+--------+--------------+
|  1 | Joe   | 70000  | 1            |
|  2 | Jim   | 90000  | 1            |
|  3 | Henry | 80000  | 2            |
|  4 | Sam   | 60000  | 2            |
|  5 | Max   | 90000  | 1            |
|  6 | Tom   | 40000  | 1            |
+----+-------+--------+--------------+
用上述语句得出的结果是：
+----+------+--------+--------------+----+-------+--------+--------------+
| id | name | salary | departmentid | id | name  | salary | departmentid |
+----+------+--------+--------------+----+-------+--------+--------------+
|  6 | Tom  | 40000  | 1            |  1 | Joe   | 70000  | 1            |
|  1 | Joe  | 70000  | 1            |  2 | Jim   | 90000  | 1            |
|  6 | Tom  | 40000  | 1            |  2 | Jim   | 90000  | 1            |
|  4 | Sam  | 60000  | 2            |  3 | Henry | 80000  | 2            |
|  1 | Joe  | 70000  | 1            |  5 | Max   | 90000  | 1            |
|  6 | Tom  | 40000  | 1            |  5 | Max   | 90000  | 1            |
+----+------+--------+--------------+----+-------+--------+--------------+

可以看到，拿e2的每一条数据去和e1的每一条数据按照where的条件比较，如果符合，则加入结果表，如果查询的是 * （结果集包含所有字段）， 则该条满足的数据（包括e1和e2）都会被加入结果表


join on 会先让要联查的表根据on条件和连接方式（左、右、内等）形成一张临时中间表，然后后面的where语句对这张中间表起作用

count函数会返回某字段的条数，distinct限定相同的值算一条（去重）

# 但这里的要比较的表已经部署Employee和Employee了，而是Employee和join on生成的中间表


select count(distinct(e2.Salary)) 
                  from Employee e2 
                  where e2.Salary > e1.Salary 
                  and e1.DepartmentId = e2.DepartmentId



想象e2先和中间表m组成的结果表，每一行中e2的salary都大于该行的m中的e1.salary且 m.e1.departmentid=e2.departmentid，再取count(distinct(e2.Salary)) 则取出该表中同一个部门中薪水非最低的薪水，然后去重再数个数

对于每一个e1，在e2中找到比e1的salary大且部门id相同的个数

where会对每一行元数据进行筛选，因此，where后面的select语句，也自然会对结果表中每一行进行筛选，对这道题来说，就是对表m的每一行都进行括号中的select语句的执行，如果该行执行下来结果小于3，则留下，否则就不在结果集中保留。

# where后面的语句会对每一行进行筛选，符合条件的行留下，不符合的剔除，这是一个很关键很关键的东西

```sql
select d.Name Department, e1.Name Employee, e1.Salary
from Employee e1 
join Department d
on e1.DepartmentId = d.Id
where 3 > (select count(distinct(e2.Salary)) 
                  from Employee e2 
                  where e2.Salary > e1.Salary 
                  and e1.DepartmentId = e2.DepartmentId
                  );



```

187. Repeated DNA Sequences
Medium

All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG". When studying DNA, it is sometimes useful to identify repeated sequences within the DNA.

Write a function to find all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule.

Example:

Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"

Output: ["AAAAACCCCC", "CCCCCAAAAA"]

所有的DNA都是由一系列核苷酸组成的，缩写为a、C、G和T，例如:“ACGAATTCCG”。在研究DNA时，有时识别DNA中的重复序列是有用的。

编写一个函数来查找一个DNA分子中不止一次出现的所有10个字母长的序列(子串)。

最简单的做法就是用窗口法，每次一个字母，一次取10个字母往后取，且放入set中，要已经出现过，则说明是重复的

...是自己想复杂了，窗口法的结果是
Success
Details 
Runtime: 16 ms, faster than 91.60% of Java online submissions for Repeated DNA Sequences.
Memory Usage: 47.1 MB, less than 70.07% of Java online submissions for Repeated DNA Sequences.

效果非常好，那么这题其实就是窗口法 -_ - ||

```java
class Solution{
    //窗口法
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList<>();
        if(s==null || s.length()<10) return res;
        HashSet<String> set = new HashSet<>();
        int len = s.length();
        for(int i=0;i<=len-10;i++){
            String sub = s.substring(i, i+10);
            if(!set.contains(sub)){
                set.add(sub);
            }else{
                if(!res.contains(sub))
                    res.add(sub);
            }
        }
        return res;
    }
}
```

188. Best Time to Buy and Sell Stock IV
Hard

Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete at most k transactions.

Note:
You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

Example 1:

Input: [2,4,1], k = 2
Output: 2
Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
Example 2:

Input: [3,2,6,5,0,3], k = 2
Output: 7
Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
             Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.


假设你有一个数组，其中第i个元素是某只股票在第i天的价格。

设计一个算法来寻找最大的利润。您最多可以完成k个事务。

注意:
您可能不会同时进行多个交易(即，您必须在再次购买之前出售股票)。

按照123题的做法，dp会提示Memory Limit Exceeded，当k=1000000000时，所需要的数组空间过大

用下面方法，在k足够大的时候，（k大于等于prices个数的一半，能保证所有的prices都可以交易时），顺序遍历，只要有价格差就做交易，这样看起来就像是在同一天进行买卖，其实不是
如[1,2,4], 可以变成：1买入，2卖出，2买入，4卖出，看上去是2操作了两次，但这种连续的，最终结果也只是4-1，只需要累加即可，这种情况只有在k足够大的情况才可以

如果k不是足够大，再用上面的经典做法，经典做法看本页的第123题

```java
class Solution{

    public int maxProfit(int op, int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int len = prices.length;
        //k足够大
        if(op>=len/2) return quickSolve(prices);

        //下面是k不足够大
        int[][] dp = new int[op + 1][len];
        for (int i = 0; i < len; i++) {
            dp[0][i] = 0;
        }
        for (int i = 0; i <= op; i++) {
            dp[i][0] = 0;
        }

        for (int k = 1; k <= op; k++) {
            for (int i = 1; i < len; i++) {
                for (int j = 0; j < i; j++) {
                    int tmp = Math.max(dp[k][i - 1], prices[i] - prices[j] + dp[k - 1][j]);
                    dp[k][i] = Math.max(tmp, dp[k][i]);
                }
            }
        }

        int res = 0;
        for (int i = 0; i <= op; i++) {
            res = Math.max(res, dp[i][len - 1]);
        }

        return res;
    }
    

    private int quickSolve(int[] prices) {
        int len = prices.length, profit = 0;
        for (int i = 1; i < len; i++)
            // as long as there is a price gap, we gain a profit.
            if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
        return profit;
    }
}
```

196. Delete Duplicate Emails
Easy

Write a SQL query to delete all duplicate email entries in a table named Person, keeping only unique emails based on its smallest Id.

+----+------------------+
| Id | Email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
| 3  | john@example.com |
+----+------------------+
Id is the primary key column for this table.
For example, after running your query, the above Person table should have the following rows:

+----+------------------+
| Id | Email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
+----+------------------+
Note:

Your output is the whole Person table after executing your sql. Use delete statement.

编写一个SQL查询来删除一个名为Person的表中所有重复的电子邮件条目，只保留基于其最小Id的惟一电子邮件。

下面是按步骤的解释：

DELETE p1
FROM Person p1, Person p2
WHERE p1.Email = p2.Email AND
p1.Id > p2.Id

EXPLANATION:

Take the table in the example
Id | Email

1 | john@example.com

2 | bob@example.com

3 | john@example.com

Join the table on itself by the Email and you'll get:
FROM Person p1, Person p2 WHERE p1.Email = p2.Email

p1.Id | p1.Email | p2.Id | p2.Email

1   | john@example.com  | 1 | john@example.com

3   | john@example.com | 1  | john@example.com

2   | bob@example.com   | 2 | bob@example.com

1   | john@example.com  | 3 | john@example.com

3   | john@example.com  | 3 | john@example.com

From this results filter the records that have p1.Id>p2.ID, in this case you'll get just one record:
AND p1.Id > p2.Id

p1.Id | p1.Email | p2.Id | p2.Email

3   | john@example.com  | 1 | john@example.com

这是我们需要删除的记录
DELETE p1

在这个多表语法中，只删除from子句之前列出的表中的匹配行，在本例中仅删除

p1.Id | p1.Email

3   | john@example.com

会被删除

```sql

delete p1
from Person as p1, Person as p2
where p2.Id<p1.Id and p2.Email = p1.Email;


create table person(
    id varchar(255) primary key not null,
    email varchar(255) default null
);

insert person (id, email) values (1, 'john@example.com');
insert person (id, email) values (2, 'bob@example.com');
insert person (id, email) values (3, 'john@example.com');
insert person (id, email) values (4, 'john@example.com');

```

197. Rising Temperature
Easy

SQL Schema
Given a Weather table, write a SQL query to find all dates' Ids with higher temperature compared to its previous (yesterday's) dates.

+---------+------------------+------------------+
| Id(INT) | RecordDate(DATE) | Temperature(INT) |
+---------+------------------+------------------+
|       1 |       2015-01-01 |               10 |
|       2 |       2015-01-02 |               25 |
|       3 |       2015-01-03 |               20 |
|       4 |       2015-01-04 |               30 |
+---------+------------------+------------------+
For example, return the following Ids for the above Weather table:

+----+
| Id |
+----+
|  2 |
|  4 |
+----+

给定一个天气表，编写一个SQL查询来查找所有与之前(昨天)日期相比温度更高的日期的id。

主要难点在于转换日期的函数TO_DAYS，其他条件很简单

TO_DAYS函数：返回从年份0开始的天数 


```sql
select distinct(w1.id)
from Weather as w1, Weather as w2
where TO_DAYS(w1.RecordDate)-TO_DAYS(w2.RecordDate)=1 and w1.Temperature > w2.Temperature;
```

199. Binary Tree Right Side View
Medium

Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

Example:

Input: [1,2,3,null,5,null,4]
Output: [1, 3, 4]
Explanation:

   1            <---
 /   \
2     3         <---
 \     \
  5     4       <---

给定一棵二叉树，想象自己站在树的右边，返回从上到下有序排列的节点的值。

用从右往左的双队列层序遍历可以做

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        LinkedList<TreeNode> queue1 = new LinkedList<>();
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        ArrayList<Integer> res = new ArrayList<>();

        if(root!=null) queue1.offer(root);
        while(!queue1.isEmpty() || !queue2.isEmpty()){
            LinkedList<TreeNode> cur = queue1.isEmpty()?queue2:queue1;
            LinkedList<TreeNode> another = queue2.isEmpty()?queue2:queue1;
            boolean isAdd = false;
            while(!cur.isEmpty()){
                TreeNode node = cur.poll();
                if(!isAdd){
                    res.add(node.val);
                    isAdd = true;
                }
                if(node.right!=null) another.offer(node.right);
                if(node.left!=null) another.offer(node.left);
            }
        }
        return res;
    }
}
```

~~~~~~~~~~~~ 前200道题结束，撒花✿✿ヽ(°▽°)ノ✿ 🌹🌹🌹🌹🌹🌹🌹🌹~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

~~~~~~~~~~~~~~~~~ 2019新开始ヾ(◍°∇°◍)ﾉﾞ~~~~~~~~~~~~~~~~~

205. Isomorphic Strings
Easy

Given two strings s and t, determine if they are isomorphic.

Two strings are isomorphic if the characters in s can be replaced to get t.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character but a character may map to itself.

Example 1:

Input: s = "egg", t = "add"
Output: true
Example 2:

Input: s = "foo", t = "bar"
Output: false
Example 3:

Input: s = "paper", t = "title"
Output: true
Note:
You may assume both s and t have the same length.

给定两个字符串s和t，判断它们是否同构。

如果可以将s中的字符替换为t，则两个字符串是同构的。

必须用另一个字符替换出现的所有字符，同时保留字符的顺序。没有两个字符可以映射到同一个字符，但是一个字符可以映射到它自己。

用map保存映射关系，如果遍历到一个字母已经映射过但不是它将要映射的字母，返回false,要注意的是两边都是一一映射，不能只保存一个映射，而应该两边都保存

## 同构字符串：两个字符串的字符之间一一映射

```java
class Solution {
    public boolean isIsomorphic(String s, String t) {
        if(s==null && t==null) return true;
        if(s==null || t==null) return false;
        if(s.length() != t.length()) return false;

        HashMap<Character, Character> s2tmap = new HashMap<>();
        HashMap<Character, Character> t2smap = new HashMap<>();
        for(int i=0;i<s.length();i++){
            char ss = s.charAt(i);
            char tt = t.charAt(i);
            if(!s2tmap.containsKey(ss) && !t2smap.containsKey(tt) ){
                s2tmap.put(ss, tt);
                t2smap.put(tt, ss);
            }
            else if((s2tmap.containsKey(ss) && !s2tmap.get(ss).equals(tt))
                    || (t2smap.containsKey(tt) && !t2smap.get(tt).equals(ss)))
                return false;
        }

        return true;
    }
}
```

209. Minimum Size Subarray Sum
Medium

Given an array of n positive integers and a positive integer s, find the minimal length of a contiguous subarray of which the sum ≥ s. If there isn't one, return 0 instead.

Example: 

Input: s = 7, nums = [2,3,1,2,4,3]
Output: 2
Explanation: the subarray [4,3] has the minimal length under the problem constraint.
Follow up:
If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log n). 

给定一个由n个正整数的数组和一个正整数s，找出其和≥s的相邻子数组的最小长度。如果没有，则返回0。

n^2的方法很好找，就是两个遍历，每次选一个数字作为当次循环的开头，加入其后面的数字

使用双指针法，即窗口法，窗口中值小于s时，窗口往后延伸，大于等于s时，窗口往前缩小

```java
class Solution{
    public int minSubArrayLen(int s, int[] nums) {
        int i=0, j=0, min = Integer.MAX_VALUE;
        int sum=0;

        while(j<nums.length){
            sum += nums[j++];

            while(sum>=s){
                min = Math.min(min, j-i);
                sum -= nums[i++];
            }
        }

        return min==Integer.MAX_VALUE?0:min;
    }
}
```

211. Add and Search Word - Data structure design
Medium

Design a data structure that supports the following two operations:

void addWord(word)
bool search(word)
search(word) can search a literal word or a regular expression string containing only letters a-z or .. A . means it can represent any one letter.

Example:

addWord("bad")
addWord("dad")
addWord("mad")
search("pad") -> false
search("bad") -> true
search(".ad") -> true
search("b..") -> true

设计一个支持以下两项操作的数据结构:

空白addWord(单词)
bool搜索(单词)
search(word)可以搜索只包含字母a-z或..A  .意思是它可以代表任何一个字母。

```java

    class WordDictionary {
        Trie root;
        /** Initialize your data structure here. */
        public WordDictionary() {
            root = new Trie();
        }

        /** Adds a word into the data structure. */
        public void addWord(String word) {
            root.insert(word);
        }

        /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
        public boolean search(String word) {
            return root.hasWord(word);
        }


        //使用字典树，既能节约时间，也能节约空间
        class Trie{
            Trie[] nodes= new Trie[26];
            boolean isWord = false;

            Trie(){

            }

            void insert(String word){
                if(word==null) return;
                Trie t = this;
                for(int i=0;i<word.length();i++){
                    int num = word.charAt(i)-'a';
                    if(t.nodes[num]==null) t.nodes[num] = new Trie();
                    t = t.nodes[num];
                    if(i==word.length()-1) t.isWord = true;
                }
            }

            boolean hasPrefix(String prefix){
                Trie t = this;
                for(int i=0;i<prefix.length();i++){
                    int num = prefix.charAt(i)-'a';
                    if(t.nodes[num]==null) return false;
                    t = t.nodes[num];
                }
                return true;
            }

            boolean hasWord(String word){
                if(word==null) return false;
                else if(word.length()==0) return true;
                return dfs(this, 0, word);
            }

            //其实这也就是trackback，只不过因为word中只有.出现时才会出现分支，所以没有大循环
            boolean dfs(Trie parent, int start, String word){

                if(parent==null) return false;
                //抵达字符末尾
                if(start==word.length()){
                    return parent.isWord;
                }

                char c = word.charAt(start);
                if(c=='.'){
                    boolean ans = false;
                    for(int j=0;j<26;j++){
                        ans |= dfs(parent.nodes[j], start+1, word);
                    }
                    return ans;
                }else{
                    return dfs(parent.nodes[c-'a'], start+1,word);
                }

            }
        }
    }

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
```

213. House Robber II
Medium

You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.

Example 1:

Input: [2,3,2]
Output: 3
Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
             because they are adjacent houses.
Example 2:

Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.

你是一个计划沿街抢劫房屋的职业强盗。每所房子都有一定数量的钱。这个地方所有的房子都排成一圈。这意味着第一个房子是最后一个房子的邻居。与此同时，相邻的房屋都连接了安全系统，如果相邻的房屋在同一晚被闯入，系统会自动报警。

给出一个非负整数列表，表示每户人家的钱数，确定你今晚不报警就能抢劫的最大钱数。

由于不是按序排的，不能简单的取奇数偶数下标的方法

比如对于[1, 2, 3, 4, 5, 1, 2, 3, 4, 5]来说，只取奇数下标或只取偶数下标都不对，正确的是取 3，5，3，5  即 隔多少个也是不确定的。

```java
class Solution {
    public int rob(int[] nums) {
        if(nums==null || nums.length==0) return 0;
        
        
    }
}
```




















