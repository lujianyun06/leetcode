36. Valid Sudoku
Medium

Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:

Each row must contain the digits 1-9 without repetition.
Each column must contain the digits 1-9 without repetition.
Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.

A partially filled sudoku which is valid.

The Sudoku board could be partially filled, where empty cells are filled with the character '.'.

Example 1:

Input:
[
  ["5","3",".",".","7",".",".",".","."],
  ["6",".",".","1","9","5",".",".","."],
  [".","9","8",".",".",".",".","6","."],
  ["8",".",".",".","6",".",".",".","3"],
  ["4",".",".","8",".","3",".",".","1"],
  ["7",".",".",".","2",".",".",".","6"],
  [".","6",".",".",".",".","2","8","."],
  [".",".",".","4","1","9",".",".","5"],
  [".",".",".",".","8",".",".","7","9"]
]
Output: true
Example 2:

Input:
[
  ["8","3",".",".","7",".",".",".","."],
  ["6",".",".","1","9","5",".",".","."],
  [".","9","8",".",".",".",".","6","."],
  ["8",".",".",".","6",".",".",".","3"],
  ["4",".",".","8",".","3",".",".","1"],
  ["7",".",".",".","2",".",".",".","6"],
  [".","6",".",".",".",".","2","8","."],
  [".",".",".","4","1","9",".",".","5"],
  [".",".",".",".","8",".",".","7","9"]
]
Output: false
Explanation: Same as Example 1, except with the 5 in the top left corner being 
    modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
Note:

A Sudoku board (partially filled) could be valid but is not necessarily solvable.
Only the filled cells need to be validated according to the mentioned rules.
The given board contain only digits 1-9 and the character '.'.
The given board size is always 9x9.


确定9x9数独板是否有效。只需要根据以下规则验证已填充的单元格：
  1.每行必须包含数字1-9而不重复。 
  2.每列必须包含数字1-9而不重复。 
  3.网格的9个3x3子框中的每一个必须包含数字1-9而不重复。

数独板可以部分填充，其中空单元格填充字符'.'

注意：

数独板（部分填充）可能有效，但不一定是可解决的。
只需要根据上述规则验证已填充的单元格。
给定的板只包含数字1-9和字符'。'。
给定的板尺寸始终为9x9。


使用HashSet，对于任意一个数nums[i][j]=k

set中对于一个数字，保存以下信息：
k在第i行，被记为"(k)i"
k在第j列，被记为"j(k)"
k所在的块的序号为 i/3, j/3  记为："i/3(k)j/3"

对于每一个值(除了.)，如果上述三个信息中有任何一个被添加过了，则说明一定是不符合的，否则一定是符合的.

对于HashSet.add，如果添加一个已经存在的值，则会返回false，否则返回true

```java
class Solution {
    public boolean isValidSudoku(char[][] board) {
        HashSet<String> set = new HashSet<>();
        for(int i=0;i<9;i++){
          for(int j=0;j<9;j++){
            if(board[i][j]!='.'){
              String b = "(" + board[i][j] + ")";
              if(!set.add(b + i) || !set.add(j + b) || !set.add(i/3 + b + j/3))
                return false;
            }
          }
        }
        return true;
    }
}

```


38. Count and Say
Easy

The count-and-say sequence is the sequence of integers with the first five terms as following:

1.     1
2.     11
3.     21
4.     1211
5.     111221
1 is read off as "one 1" or 11.
11 is read off as "two 1s" or 21.
21 is read off as "one 2, then one 1" or 1211.

Given an integer n where 1 ≤ n ≤ 30, generate the nth term of the count-and-say sequence.

Note: Each term of the sequence of integers will be represented as a string.

 

Example 1:

Input: 1
Output: "1"
Example 2:

Input: 4
Output: "1211"

读与计数，对于1，读取为，1个1：11， 
1：读取为 1个1：11
11：读取为：2个1：21
21：读取为：1个2，1个1： 1211
1211：读取为：1个1，1个2，2个1： 111221

用两个队列来做,分别存放本次和下次的读取顺序

```java
class Solution {
    public String countAndSay(int n) {
        int count = n-1;
        LinkedList<Integer> queue1 = new LinkedList<>();
        LinkedList<Integer> queue2 = new LinkedList<>();
        queue1.add(1);

        while(count > 0){
            LinkedList<Integer> cur = queue1.isEmpty()?queue2:queue1;
            LinkedList<Integer> another = cur==queue1?queue2:queue1;
            helper(cur, another);
            count--;
        }
        LinkedList<Integer> cur = queue1.isEmpty()?queue2:queue1;
        StringBuilder builder = new StringBuilder();
        while(!cur.isEmpty()){
            builder.append(cur.poll());
        }
        return builder.toString();
    }


    void helper(LinkedList<Integer> cur, LinkedList<Integer> another){
        while(!cur.isEmpty()){
            int item = cur.poll();
            int count = 1;
            while(!cur.isEmpty() && cur.peek()==item){
                cur.poll();
                count++;
            }
            another.add(count);
            another.add(item);
        }
    }
}
```

41. First Missing Positive
Hard

Given an unsorted integer array, find the smallest missing positive integer.

Example 1:

Input: [1,2,0]
Output: 3
Example 2:

Input: [3,4,-1,1]
Output: 2
Example 3:

Input: [7,8,9,11,12]
Output: 1
Note:


Your algorithm should run in O(n) time and uses constant extra space.


给定一个未排序的整数数组，找出丢失的最小正整数。要求是用O(n)的时间复杂度和O(1)的空间复杂度

这个做法是只管数组长度这么长范围内的从1开始的整数，即：
对于长度为5的数组，只要发现1到5范围内的数x，就让它挪到x-1的位置（交换，不是替换），其他的数字都不管，因为如果缺失这个范围的某个数，
那答案就是这个数，如果不缺失，那么答案就是最大数+1

如果出现重复的数，那么不做改变，因为前一个该数已经放在正确的位置上了。

```java
class Solution {
    public int firstMissingPositive(int[] nums) {
        int i=0;
        for(;i<nums.length;){
            //例如把5这个数换到下标为4的位置,把1这个数换到下标为0的位置,但是换完后i应该不变，不然此时换过来的数可能被无视
            //(这一点很重要！！！)
            //而且如果这个位置已经放了合适的数，就i+1；如果要交换的两个数相同，则也i+1
            if(nums[i] > 0 && nums[i] < nums.length && nums[i] != i+1 && nums[i] != nums[nums[i]-1]){
                swap(nums, i, nums[i]-1);
            }else
                i++;
        }

        i=0;
        while(i<nums.length && nums[i]==i+1) i++;
        return i+1;
    }

    void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
```

44. Wildcard Matching
Hard

Given an input string (s) and a pattern (p), implement wildcard（通配符） pattern matching with support for '?' and '*'.

'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).
The matching should cover the entire input string (not partial).

Note:

s could be empty and contains only lowercase letters a-z.
p could be empty and contains only lowercase letters a-z, and characters like ? or *.
Example 1:

Input:
s = "aa"
p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input:
s = "aa"
p = "*"
Output: true
Explanation: '*' matches any sequence.
Example 3:

Input:
s = "cb"
p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
Example 4:

Input:
s = "adceb"
p = "*a*b"
Output: true
Explanation: The first '*' matches the empty sequence, while the second '*' matches the substring "dce".
Example 5:

Input:
s = "acdcb"
p = "a*c?b"
Output: false


具体过程看下面的注释，这个算法算是匹配里面很好的算法了

```java
class Solution {
     public boolean isMatch(String str, String pattern) {
        //先遍历str串,与pattern匹配
        int s = 0, p = 0, lastStar = -1, match = 0;
        //match代表*可以开始匹配的地方
        while(s < str.length()){
            //如果字符匹配
            if(p < pattern.length() && (str.charAt(s) == pattern.charAt(p) ||
                                        pattern.charAt(p)== '?')){
                s++;
                p++;
            }
            //如果是*，s不动，p往前走一步，且记录该*的位置，这里先是假定*谁都不匹配
            else if(p < pattern.length() && pattern.charAt(p)== '*'){
                lastStar = p;
                match = s;
                p++;
            }
            //如果遇到不匹配的，而且前面出现过*，则p返回*，s返回match，让*匹配一个字母（match++），继续匹配
            //如果再不匹配，再返回*，让*匹配两个字母（因为之前已经匹配过一个了，此时再+1即可），
            //再继续匹配。。。再不匹配就再回来匹配3个。。。。
            else if(lastStar!=-1){
                p = lastStar+1;
                match++;
                s = match;
            }

            //如果前面没有*号，且字符不匹配，则一定不匹配
            else return false;
        }
        //如果到这里，str就都匹配完了，如果pattern后面都是*则匹配，否则不匹配

        while(p < pattern.length() && pattern.charAt(p)=='*')
            p++;

        return p==pattern.length();

    }

}
```

50. Pow(x, n)
Medium

Implement pow(x, n), which calculates x raised to the power n (x^n).

Example 1:

Input: 2.00000, 10
Output: 1024.00000
Example 2:

Input: 2.10000, 3
Output: 9.26100
Example 3:

Input: 2.00000, -2
Output: 0.25000
Explanation: 2^-2 = 1/2^2 = 1/4 = 0.25
Note:

-100.0 < x < 100.0
n is a 32-bit signed integer, within the range [−2^31, 2^31 − 1]

如果用一个一个的普通的乘法，则会超时，

可以尽可能地削减（合并）情况

x^n  ==  (x * x) ^ (n/2)

```java
class Solution {
    public double myPow(double x, int n) {
        if(n==0) return 1;
        boolean minN = false;
        if(n<0){
            x = 1/x;
            if(n == Integer.MIN_VALUE){ //如果是最大负数，则需要做一些处理
                n = n+1; //把它变成正数能表示的范围
                minN = true;
            }
            n = -n;
        }

        //原本需要做n次乘法，现在只需要做 n/2+1次，（加的1是x*x）
        //可以把复杂度降下来
        double ans = (n%2==0)?myPow(x*x, n/2):x*myPow(x*x, n/2);
        if(!minN) return ans;
        else return ans * x;
    }
}
```

54. Spiral Matrix
Medium

Given a matrix of m x n elements (m rows, n columns), 
return all elements of the matrix in spiral order.

Example 1:

Input:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
Output: [1,2,3,6,9,8,7,4,5]
Example 2:

Input:
[
  [1, 2, 3, 4],
  [5, 6, 7, 8],
  [9,10,11,12]
]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]

给出一个矩阵的螺旋序，即从左上角开始一直往里卷

设置左右上下的边界值，一旦到达边界值，边界值就-1
如：example2，一开始从左往右走，右边界为3，当到达第四列时（值为3），则往下走，右边界-1
往下走到下边界2处，下边界-1，再往右走。。。

```java
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        if(matrix.length == 0 || matrix[0].length == 0) return new ArrayList<>();
        int left = 0, top=1,right = matrix[0].length-1, bottom = matrix.length-1; //比较不同的是，top一开始就是1，意味着第一次往上走的时候就不能走到第一列
        int direction = 3;  //移动的方向 上0， 下1  左2   右3
        List<Integer> ans = new ArrayList<>();

        int x = 0;  //横坐标
        int y = 0;  //纵坐标
        while(true){
            if(direction==3 && y<=right){
                ans.add(matrix[x][y]);
                if(y==right){
                    direction = 1;
                    right--;
                    x++;
                }else{
                    y++;
                }
            }
            else if(direction==1 && x<=bottom){
                ans.add(matrix[x][y]);
                if(x==bottom){
                    direction = 2;
                    bottom--;
                    y--;
                }else{
                    x++;
                }
            }
            else if(direction==2 && y>=left){
                ans.add(matrix[x][y]);
                if(y==left){
                    direction = 0;
                    left++;
                    x--;
                }else{
                    y--;
                }
            }
            else if(direction==0 && x>=top){
                ans.add(matrix[x][y]);
                if(x==top){
                    direction = 3;
                    top++;
                    y++;
                }else{
                    x--;
                }
            }else{
                break;
            }
        }
        return ans;
    }
}
```


66. Plus One
Easy

Given a non-empty array of digits representing a non-negative integer, plus one to the integer.

The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.

You may assume the integer does not contain any leading zero, except the number 0 itself.

Example 1:

Input: [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Example 2:

Input: [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.

用数组模拟一个数然后算+1

显然主要的难点在于解决进位

从后往前遍历加，要加1相当于一开始就从后面有一个进位

```java
class Solution {
    public int[] plusOne(int[] digits) {
        int c = 1;
        int[] ans = new int[digits.length];
        for(int i=ans.length-1;i>=0;i--){
            int tmp = digits[i]+c;
            int cur = tmp % 10;
            c = tmp / 10;
            ans[i] = cur;
        }
        //最高位没有进位
        if(c==0){
            return ans;
        }else{
            int[] ans2 = new int[ans.length+1];
            System.arraycopy(ans, 0, ans2, 1, ans.length);
            ans2[0] = c;
            return ans2;
        }
    }
}
```

69. Sqrt(x)
Easy

Implement int sqrt(int x).

Compute and return the square root of x, where x is guaranteed to be a non-negative integer.

Since the return type is an integer, 
the decimal digits are truncated and only the integer part of the result is returned.

Example 1:

Input: 4
Output: 2
Example 2:

Input: 8
Output: 2
Explanation: The square root of 8 is 2.82842..., and since 
             the decimal part is truncated, 2 is returned.

计算平方根，有两种办法：

一是二分搜索，二是牛顿迭代法。

详细介绍：
https://www.cnblogs.com/qlky/p/7735145.html

1. 二分搜索
对于一个非负数n，它的平方根不会大于（n/2+1）（谢谢@linzhi-cs提醒）。在[0, n/2+1]这个范围内可以进行二分搜索，求出n的平方根。
///计算过程可能会溢出，所以都用long表示

2. 牛顿迭代法

计算x^2 = n的解，令f(x)=x^2-n，相当于求解f(x)=0的解，如左图所示。

   首先取x0，如果x0不是解，做一个经过(x0,f(x0))这个点的切线，与x轴的交点为x1。

   同样的道理，如果x1不是解，做一个经过(x1,f(x1))这个点的切线，与x轴的交点为x2。

   以此类推。

   以这样的方式得到的xi会无限趋近于f(x)=0的解。

   判断xi是否是f(x)=0的解有两种方法：

   一是直接计算f(xi)的值判断是否为0，二是判断前后两个解xi和xi-1是否无限接近。

```java
class Solution {
    //二分搜索
    public int mySqrt(int x) {
        long low = 0;
        long high = x/2 + 1;
        //注意，这里与普通的二分查找有点不太一样，low等于high的时候还要再来一次，而且由于是向下取整，
        //当low和high一样时，再循环一次high就比low要小了，所以最后返回high
        while(low <= high){
            long mid = (low + high) / 2;
            long square = mid * mid;
            if(square == x) return (int)mid;
            else if(square > x){
                high = mid-1;
            }
            else{
                low = mid + 1;
            }
        }
        return (int)high;
    }

    //牛顿迭代法
    int mySqrt(int x) {
        if (x == 0) return 0;
        //这里一定要用double，用float会精度不够出问题
        double last = 0;
        double res = 1;
        while (res != last) //当两个值的整数一样时，此时就认为是无限接近了
        {
            last = res;
            res = (res + x / res) / 2;
        }
        return (int)res;
    }
}
```

73. Set Matrix Zeroes
Medium

Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.

Example 1:

Input: 
[
  [1,1,1],
  [1,0,1],
  [1,1,1]
]
Output: 
[
  [1,0,1],
  [0,0,0],
  [1,0,1]
]
Example 2:

Input: 
[
  [0,1,2,0],
  [3,4,5,2],
  [1,3,1,5]
]
Output: 
[
  [0,0,0,0],
  [0,4,5,0],
  [0,3,1,0]
]
Follow up:

A straight forward solution using O(mn) space is probably a bad idea.
A simple improvement uses O(m + n) space, but still not the best solution.
Could you devise a constant space solution?

保留两个set，一个存为0的行标，一个存为0的列标，先遍历，把两个set都放满，然后再二重遍历去放0
当然这种做法空间复杂度不是O(1)

```java
class Solution {
    public void setZeroes(int[][] matrix) {
        Set<Integer> rowSet = new HashSet<>();
        Set<Integer> colSet = new HashSet<>();
        if(matrix.length==0 || matrix[0].length==0) return;

        for(int i = 0; i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j]==0){
                    rowSet.add(i);
                    colSet.add(j);
                }
            }
        }

        for(int i = 0; i<matrix.length;i++){
            boolean isRowMatch = rowSet.contains(i);
            for(int j=0;j<matrix[0].length;j++){
                if(isRowMatch)
                    matrix[i][j]=0;
                else if(colSet.contains(j))
                    matrix[i][j]=0;
            }
        }
    }
}
```

75. Sort Colors
Medium

1538

146

Favorite

Share
Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white and blue.

Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

Note: You are not suppose to use the library's sort function for this problem.

Example:

Input: [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
Follow up:

A rather straight forward solution is a two-pass algorithm using counting sort.
First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.
Could you come up with a one-pass algorithm using only constant space?

双指针定位，单指针扫描，p1和p2分别从前后往中间靠，p1到达第一个不为0的地方，p2到达第1个不为2的地方
p从前往后走，遇到0则和p1的值交换，p1继续往后走到不为0的地方，p再遇到2与p2交换，p2走到不为2的地方
遇到1继续走不管，停止的条件是p1超过p2 或 p超过p2

```java
class Solution {
    public void sortColors(int[] nums) {
        int p1=0,p2=nums.length-1,p=0;

        while(p1 < p2 && p < p2){
            while(nums[p1]==0 && p1 < p2){
                p1++;
            }

            while(nums[p2]==2 && p1 < p2){
                p2--;
            }
            //p1前面的就不管了，肯定都是0
            //
            p = p1;
            //如果p遇到1，则不构成交换条件，继续往后走
            while(p < nums.length && nums[p]==1){
                p++;
            }

             //上面因为p1先更新，p2后更新，所以到这里还是要判断一下p1不能超过p2
            //而且p不能超过p2，因为p2后面的肯定都是2，也不用管，只需要管p1，p2之间的
            //p1==p2时，说明他俩之间没有元素了，就是他俩都不需要交换了，相差一个位置是还是有交换的可能
            if(p1 >= p2 || p > p2){
                break;
            }
            if(nums[p]==0){
                int tmp = nums[p1];
                nums[p1] = nums[p];
                nums[p] = tmp;
            }else if(nums[p]==2){
                int tmp = nums[p2];
                nums[p2] = nums[p];
                nums[p] = tmp;
            }
        }
    }
}
```

88. Merge Sorted Array
Easy

Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

Note:

The number of elements initialized in nums1 and nums2 are m and n respectively.
You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
Example:

Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6]

先把nums2全放到nums1的后面，然后直接对nums1快排

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //把nums2拷贝到nums1中
        System.arraycopy(nums2, 0, nums1, m,n);
        qSort(nums1, 0, m+n-1);
    }

    void qSort(int[] nums, int start, int end){
        if(start < end){
            int mid = partition(nums, start, end);
            qSort(nums, start, mid-1);
            qSort(nums, mid+1, end);
        }
    }

    int partition(int[] nums, int low, int high){
        int pivot = nums[low];
        int left = low;
        int right = high;
        while(left < right){
            while(nums[right] >= pivot && left < right){
                right--;
            }

            nums[left] = nums[right];
            while(nums[left] <= pivot && left < right){
                left++;
            }
            nums[right] = nums[left];

        }
        nums[left] = pivot;
        return left;
    }
}
```

91. Decode Ways
Medium

A message containing letters from A-Z is being encoded to numbers using the following mapping:

'A' -> 1
'B' -> 2
...
'Z' -> 26
Given a non-empty string containing only digits, determine the total number of ways to decode it.

Example 1:

Input: "12"
Output: 2
Explanation: It could be decoded as "AB" (1 2) or "L" (12).
Example 2:

Input: "226"
Output: 3
Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).

又到了递归的使用
当扫描到一个数字，有两种办法，一种是只取它，一种是取它和它后面的数字组合（前提是不超过26）
但这种做法会超时

考虑动态规划
dp[i]是以 第i个数字结尾的可能数
dp[0]=1
dp[1]=1

当dp[i]是0时，dp[i] = dp[i-2] //此时s(i-1, i)必是合法的数
当dp[i]不是0时
    若s(i-1, i)是合法的数(即第i-1个数不是0， 且s(i-1, i)<=26)，则第i个和第i-1个作为一个数时，可能性为dp[i-2],第i个作为一个数时，可能性为dp[i-1]
                         dp[i] = dp[i-2] + dp[i-1]
    若s(i-1, i)不是合法的数，则第i个只作为一个数
                         dp[i] = dp[i-1]


```java
class Solution {
    public int numDecodings(String s) {
        int len = s.length();
        if(len==0 || Integer.valueOf(s.substring(0,1))==0) return 0;
        int[] dp = new int[len+1];

        //加入一个0，让后面的好算一点
        dp[0] = 1;
        dp[1] = 1;

        //i是dp的索引，index是s的索引， i对应index， index=i-1
        for(int i=2;i<len+1;i++){
            int index = i-1;
            if(Integer.valueOf(s.substring(index,index+1))==0){
                if(Integer.valueOf(s.substring(index-1,index))==0 ||
                        Integer.valueOf(s.substring(index-1,index)) > 2){ //0前面又是0，或者比2大的数，则非法,返回0
                    return 0;
                }
                dp[i] = dp[i-2];
            }else{
                if(Integer.valueOf(s.substring(index-1,index+1)) <= 26 && 
                    Integer.valueOf(s.substring(index-1,index))!=0){
                    dp[i] = dp[i-2] + dp[i-1];
                }else{
                    dp[i] = dp[i-1];
                }
            }
        }
        return dp[len];
    }

}
```

103. Binary Tree Zigzag Level Order Traversal
Medium

Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).

For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its zigzag level order traversal as:
[
  [3],
  [20,9],
  [15,7]
]

之字形的层序遍历，当遇到奇数层时，直接把节点从队列中取出并放入list，并将孩子节点加入队列
当遇到偶数层时，先把节点从队列放入一个栈，将孩子加入队列，然后从栈中取出并放入list

```java
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if(root==null) return ans;
        LinkedList<TreeNode> queue1 = new LinkedList<>();
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        Stack<Integer> stk = new Stack<>();
        queue1.add(root);
        boolean oddLevel=true; //判断奇偶数层直接用一个布尔，不用数字了
        while(!queue1.isEmpty() || !queue2.isEmpty()){
            LinkedList<TreeNode> cur = queue1.isEmpty()?queue2:queue1;
            LinkedList<TreeNode> another = queue1.isEmpty()?queue1:queue2;
            List<Integer> tmp = new ArrayList<>();
            stk.clear();
            while(!cur.isEmpty()){
                TreeNode node = cur.poll();
                if(node.left!=null) another.add(node.left);
                if(node.right!=null) another.add(node.right);
                if(oddLevel){
                    tmp.add(node.val);
                }else{
                    stk.push(node.val);
                }
            }
            while(!stk.empty()){
                Integer i = stk.pop();
                tmp.add(i);
            }
            ans.add(tmp);
            oddLevel = !oddLevel;
        }
        return ans;
    }
}


```

108. Convert Sorted Array to Binary Search Tree
Easy

Given an array where elements are sorted in ascending order, convert it to a height balanced BST.

For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

Example:

Given the sorted array: [-10,-3,0,5,9],

One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

      0
     / \
   -3   9
   /   /
 -10  5


对于把一个有序序列排成二叉搜索树，比乱序序列要好排，找到中间的元素，以它为根，左边的是左子树，右边的是右子树
这样直接就是平衡树。

```java
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(0,nums.length-1, nums);
    }

    TreeNode buildTree(int start, int end, int[] nums){
        if(start > end || start < 0 || end > nums.length-1) return null;
        else if(start == end) return new TreeNode(nums[start]);
        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = buildTree(start, mid-1, nums);
        node.right = buildTree(mid+1, end, nums);
        return node;
    }
}
```

116. Populating Next Right Pointers in Each Node
Medium

You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:

struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.

Initially, all next pointers are set to NULL.

Example:

Input: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},"next":null,"right":{"$id":"4","left":null,"next":null,"right":null,"val":5},"val":2},"next":null,"right":{"$id":"5","left":{"$id":"6","left":null,"next":null,"right":null,"val":6},"next":null,"right":{"$id":"7","left":null,"next":null,"right":null,"val":7},"val":3},"val":1}

Output: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":{"$id":"4","left":null,"next":{"$id":"5","left":null,"next":{"$id":"6","left":null,"next":null,"right":null,"val":7},"right":null,"val":6},"right":null,"val":5},"right":null,"val":4},"next":{"$id":"7","left":{"$ref":"5"},"next":null,"right":{"$ref":"6"},"val":3},"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"7"},"val":1}

Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B.
 

Note:

You may only use constant extra space.
Recursive approach is fine, implicit stack space does not count as extra space for this problem.


给一个完全二叉树，所有叶子在同一层，所有父亲节点都有两个孩子节点

让每个节点的next域都填为它同层的右边的节点，一层中最右边的节点next域为null
直接用层序遍历即可

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
        if(root==null) return null;
        LinkedList<Node> queue1 = new LinkedList<>();
        LinkedList<Node> queue2 = new LinkedList<>();
        queue1.add(root);
        while(!queue1.isEmpty() || !queue2.isEmpty()){
            LinkedList<Node> cur = queue1.isEmpty()?queue2:queue1;
            LinkedList<Node> another = queue1==cur?queue2:queue1;
            while(!cur.isEmpty()){
                Node node = cur.poll();
                node.next = cur.isEmpty()?null:cur.peek();
                //完全树，只要左孩子存在，右孩子一定存在
                if(node.left!=null){
                    another.add(node.left);
                    another.add(node.right);
                }
            }
        }
        return root;
    }
}
```

118. Pascal's Triangle
Easy

Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.


In Pascal's triangle, each number is the sum of the two numbers directly above it.

Example:

Input: 5
Output:
[
     [1],
    [1,1],
   [1,2,1],
  [1,3,3,1],
 [1,4,6,4,1]
]

杨辉三角，给定层数，获得一个杨辉三角
对于每一行的第i个元素，等于上一行的第i-1个元素和第i个元素之和（除了第一个元素和最后一个元素都是1）

```java
class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        for(int i=0;i<numRows;i++){
            List<Integer> row = new ArrayList<>();
            for(int j=0;j<=i;j++){
                if(j==0) row.add(1);
                else if(j==i && j!=0) row.add(1);
                else {
                    int tmp = ans.get(i-1).get(j-1)+ans.get(i-1).get(j);
                    row.add(tmp);
                }
            }
            ans.add(row);
        }
        return ans;
    }
}
```

122. Best Time to Buy and Sell Stock II
Easy

Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times).

Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).

Example 1:

Input: [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
             Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
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

Best Time to Buy and Sell Stock I是只能交易一次，这里是可以交易多次

还是想象成折线图，则最大利润是在谷底买入，在谷后的第一个峰卖出，然后再在峰后的第一个谷买入，在这个谷后的第一个峰卖出。。。

```java
class Solution {
    public int maxProfit(int[] prices) {
        if(prices.length <= 1) return 0;
        //两边都下降或左边下降右边相等的，为峰，两边都上升的或左边相等右边上升的，为谷，
        //第一个数作为谷才有意义，最后一个数作为峰才有意义
        ArrayList<Integer> valleys = new ArrayList<>();
        ArrayList<Integer> peeks = new ArrayList<>();
        for(int i=0;i<prices.length;i++){
            if(i==0){
                if(prices[i+1] > prices[i]) valleys.add(prices[i]);
            }else if(i==prices.length-1){
                if(prices[i-1] < prices[i]) peeks.add(prices[i]);
            }else{
                if(prices[i-1] < prices[i] &&
                        (prices[i+1] < prices[i] || prices[i+1] == prices[i])){
                    peeks.add(prices[i]);
                }
                else if(prices[i+1] > prices[i] &&
                        (prices[i-1] > prices[i] || prices[i-1] == prices[i])){
                    valleys.add(prices[i]);
                }
            }
        }
        //第一个数不当峰，最后一个数不当谷
        //此时只需第一个峰-第一个谷，第二个峰-第二个谷。。。。直到有一方不存在，把所有的差加起来就是结果
        //取两个数组长度的小值
        int totalProfit = 0;
        int len = valleys.size()>peeks.size()?peeks.size():valleys.size();
        for(int i=0;i<len;i++){
            totalProfit += peeks.get(i)-valleys.get(i);
        }
        return totalProfit;
    }

}
```

125. Valid Palindrome
Easy

Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.

Note: For the purpose of this problem, we define empty string as valid palindrome.

Example 1:

Input: "A man, a plan, a canal: Panama"
Output: true
Example 2:

Input: "race a car"
Output: false

判断字符串是否是回文，只考虑字母，忽略大小写

```java
class Solution {
    public boolean isPalindrome(String s) {
        StringBuilder builder = new StringBuilder();
        s = s.toLowerCase();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')){
                builder.append(c);
            }
        }
        String s1 = builder.toString();
        return s1.equals(builder.reverse().toString());
    }
}
```

127. Word Ladder
Medium

Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:

Only one letter can be changed at a time.
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
Note:

Return 0 if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.
Example 1:

Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output: 5

Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.
Example 2:

Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

Output: 0

Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.

词语转换，把beginword转成endword的最小花费，只能转成词典中的词语，且只能相互转换，一次只能转一个字母



把起始词，以及词典都放入一个新数组，词典中有n个词
起始词是第0个
持有一个标记数组f和两个记录队列a1,a2
从新词典中的第0个词开始，放入a1，
取a1至不为空，依次遍历它能转成哪些词，能转的标记，加入a2
取a2至不为空，依次遍历它们能转成哪些词，如果标记过的则再不选，能转的标记，加入a1
。。。
每遍历一个队列，count+=1
当能转的词有一个是endword，返回counter)
直到a1和a2都空时，说明转不了了，返回0

****************上面这个做法比较慢：

对每个词进行广度优先遍历

把所有词都放入一个词典set中，再有一个表示一个当前到达词的set，遍历set中的每一个词，将它能转换成的所有词的形式都扫描一遍，如果这个能转换的词在字典中，则把它加入一个临时set，把它从字典中删掉，当当前到达词都遍历完后，把当前到达词的
set换成这个临时set，继续遍历

当当前到达词中有endword时，说明已经转换成功

当临时set中没有词时，说明字典中的词已经全部转过了，无法达到目标，则返回0



```java
class Solution {

     public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordDict = new HashSet<>(wordList);
        //当前到达过的词，
        Set<String> reached = new HashSet<String>();
        reached.add(beginWord);
        int distance = 1;
        while (!reached.contains(endWord)) {
            Set<String> toAdd = new HashSet<String>();
            for (String each : reached) {
                //遍历当前已到达的词能到达的所有词
                for (int i = 0; i < each.length(); i++) {
                    char[] chars = each.toCharArray();
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        chars[i] = ch;
                        String word = new String(chars);
                        //如果这个词被包含在词典中，则加入待添加词，为了避免一个词被重复添加
                        //一旦一个词被添加过，就把它从词典中删除。
                        if (wordDict.contains(word)) {
                            toAdd.add(word);
                            wordDict.remove(word);
                        }
                    }
                }
            }
            distance++;
            if (toAdd.size() == 0) return 0;
            reached = toAdd;
        }
        return distance;
    }



    //比较慢的做法
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        LinkedList<String> a1 = new LinkedList<>();
        LinkedList<String> a2 = new LinkedList<>();
        if(!wordList.contains(endWord)) return 0;
        wordList.add(0, beginWord);

        int len = wordList.size();
        boolean[] flag = new boolean[len];

        a1.add(wordList.get(0));
        flag[0] = true;
        int count=1;

        while(!a1.isEmpty() || !a2.isEmpty()){
            LinkedList<String> cur = a1.isEmpty()?a2:a1;
            LinkedList<String> another = cur==a1?a2:a1;

            while(!cur.isEmpty()){
                String word = cur.poll();
                if(word.equals(endWord)){
                    return count;
                }

                for(int i=0;i<len;i++){
                    //没有标记过的词才看
                    String tmp = wordList.get(i);
                    if(!flag[i]){
                        if(canTransform(word, tmp)){
                            flag[i]=true;
                            another.add(wordList.get(i));
                            //如果词典其中出现和最后一个词一样的，那认为已经转换好了
                        }
                    }
                }
            }
            count++;
        }
        return 0;
    }

    boolean canTransform(String s1, String s2){
        int cnt = 0;
        for(int i=0; i< s1.length();i++){
            if(s1.charAt(i) != s2.charAt(i))
                cnt++;
        }
        //只有差一步才能转
        return cnt==1;
    }

}


```

130. Surrounded Regions
Medium

Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.

A region is captured by flipping all 'O's into 'X's in that surrounded region.

Example:

X X X X
X O O X
X X O X
X O X X
After running your function, the board should be:

X X X X
X X X X
X X X X
X O X X
Explanation:

Surrounded regions shouldn’t be on the border, which means that any 'O' on the border of the board are not flipped to 'X'. Any 'O' that is not on the border and it is not connected to an 'O' on the border will be flipped to 'X'. Two cells are connected if they are adjacent cells connected horizontally or vertically.

给定一个包含“X”和“O”(字母O)的2D板，捕获由“X”包围的所有区域。
一个区域是通过在被包围的区域内将所有的O翻转成X来捕获的。

把被'X'包裹的'O'块全转成'X'，有点像秦时明月里面那个特殊的围棋玩法

对于不在边界上的一个'O'，对其广度优先遍历，当遇到相邻的'O'时入队，并做标记，当遇到'O'时在边界上时，说明这个块没有被包裹
当没有边界上的'O'，且最终队列为空时，说明被全部包裹了，则对它再次广搜（或者记录搜过的行列号），这次把它们全部都变成X


```java
class Solution {
    public void solve(char[][] board) {
        if (board.length == 0 || board[0].length == 0) return;
        boolean[][] flag = new boolean[board.length][board[0].length];

        //不检查边界
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[0].length - 1; j++) {
                if (board[i][j] == 'O' && flag[i][j] == false) {
                    bfs(board, i, j, flag);
                }
            }
        }
    }

    void bfs(char[][] board, int row, int col, boolean[][] flag) {
        LinkedList<String> queue = new LinkedList<>();
        Set<String> set = new HashSet<>();
        queue.add(row + "-" + col);
        set.add(row + "-" + col);
        flag[row][col] = true;

        boolean includeBorder = false;
        while (!queue.isEmpty()) {
            String coordinate = queue.poll();
            int i = Integer.valueOf(coordinate.split("-")[0]);
            int j = Integer.valueOf(coordinate.split("-")[1]);
            if (i == 0 || i == board.length - 1 || j == 0 || j == board[0].length - 1) {
                //这个块包含边界元素，不能直接返回，做个标记直接返回会使得有的元素可能变成"孤岛"
                //即一个O周围的O因为遍历都被标记为true，到它这里只有它一个，会被错误地判断为X
//                return;
                includeBorder = true;
            }

            //按上下左右的顺序：
            //因为如果遇到边界元素就直接在上面返回了，所以到这里的肯定不是边界元素，所以一定是有四周的，直接添加就行
            //上
            if (i - 1 >= 0) {
                if (board[i - 1][j] == 'O' && !flag[i - 1][j]) {
                    queue.add((i - 1) + "-" + j);
                    set.add((i - 1) + "-" + j);
                }
                flag[i - 1][j] = true;
            }

            //下
            if (i + 1 < board.length) {
                if (board[i + 1][j] == 'O' && !flag[i + 1][j]) {
                    queue.add((i + 1) + "-" + j);
                    set.add((i + 1) + "-" + j);
                }
                flag[i + 1][j] = true;
            }

            //左
            if (j - 1 >= 0) {
                if (board[i][j - 1] == 'O' && !flag[i][j - 1]) {
                    queue.add((i) + "-" + (j - 1));
                    set.add((i) + "-" + (j - 1));
                }
                flag[i][j - 1] = true;
            }

            //右
            if (j + 1 < board[0].length) {
                if (board[i][j + 1] == 'O' && !flag[i][j + 1]) {
                    queue.add((i) + "-" + (j + 1));
                    set.add((i) + "-" + (j + 1));
                }
                flag[i][j + 1] = true;
            }
        }

        //如果这个块中有边界元素，就返回
        if (includeBorder) return;

        //能到这里，说明一定是被包裹的块，如果有边界块就已经返回了,set中包含了所有在这个块内的坐标
        for (String coordinate : set) {
            int i = Integer.valueOf(coordinate.split("-")[0]);
            int j = Integer.valueOf(coordinate.split("-")[1]);
            board[i][j] = 'X';
        }

    }
}
```

## 深度优先是用栈来完成的，配合标志数组记录已经遍历过的位置
## 广度优先是用队列来做的，也要配合标志数组

（因为有时候叫搜索，有时间叫遍历，索性就不加后面的词了）


131. Palindrome Partitioning
Medium

Given a string s, partition s such that every substring of the partition is a palindrome.

Return all possible palindrome partitioning of s.

Example:

Input: "aab"
Output:
[
  ["aa","b"],
  ["a","a","b"]
]

如果输入是“aab”，检查[0,0]“a”是否是回文。 然后检查[0,1]“aa”，然后检查[0,2]“aab”。
在检查[0,0]时，字符串的其余部分为“ab”，使用ab作为输入来进行递归调用。


```java
public class Solution {

    List<String> curLst;
    List<List<String>> ans;
    public List<List<String>> partition(String s) {
        ans = new List<List<String>>();
        curLst = new List<String>();
        trackBack(s, 0);
        return ans;
    }

    void trackBack(String s, int start){
        if(start >= s.length){
            ans.add(curLst.clone());
            return;
        }

        for(int i=start;i<s.length;i++){
            //查看[start, i]这个字符串是否是回文
            if(isPalindrome(start, i, s)){
                String cur;
                if(start==i){
                    cur = ""+s.charAt(i);
                }else{
                    cur = s.substring(start,i+1);
                }
                curLst.add(cur);
                trackBack(s, i+1);
                curLst.remove(curLst.size()-1);
            }
        }
    }

    boolean isPalindrome(int l, int r, String s){
        if(r==l) return true;
        while(l<=r){
            if(s.charAt(l)==s.charAt(r)){
                l++;
                r--;
            }else{
                return false;
            }
        }
        return true;
    }
}
```

134. Gas Station
Medium

There are N gas stations along a circular route, where the amount of gas at station i is gas[i].

You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.

Return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.

Note:

If there exists a solution, it is guaranteed to be unique.
Both input arrays are non-empty and have the same length.
Each element in the input arrays is a non-negative integer.
Example 1:

Input: 
gas  = [1,2,3,4,5]
cost = [3,4,5,1,2]

Output: 3

Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.
Example 2:

Input: 
gas  = [2,3,4]
cost = [3,4,3]

Output: -1

Explanation:
You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
Therefore, you can't travel around the circuit once no matter where you start.

环形路上的加油站
tank = 油箱

从gas[i]走到gas[i+1]需要消耗 cost[i]的汽油，问从哪个gas开始走，顺时针绕一圈能回到gas[i]
一开始油箱是空的，油箱是无限大的。

先用简单粗暴的遍历
可以通过，但耗时较久


*****************
快的方法：
基于一个数学定理：

## 如果一个数组的总和非负，那么一定可以找到一个起始位置，从他开始绕数组一圈，累加和不管到哪一步一直都是非负的


有了这个定理，判断到底是否存在这样的解非常容易，只需要把全部的油耗情况计算出来看看是否大于等于0即可。


如果我们从第i个气站出发，第一个不能到达的气站Index为j，那么从第i+1..j-1个气站出发也不能到达第j个气站。

证明如下：

以从第i+1气站（加油站）出发为例，从第i气站出发到达第i+1气站后剩余的气>=0,再从i+1出发，仍然到达不了第j气站。从第i+1气站出发的话开始剩余的气为0，比上述的情况要糟糕，所以也到达不了第j气站。i+2..j-1个气站出发同理，第i气站到达出发点时剩余的气>=0。

所以我们可以实现气站的跳跃，从0气站出发，一旦发现到达j+1气站后剩余的气<0(tank+gas[j]-cost[j]<0),
即不能到达j+1气站，就尝试从j+1气站重新出发。
因为题目保证具有唯一解，所以只需要记录最后的出发点k即可。
k前面的点肯定不行，k后面的点如果是解，那k可以到达后面的点，那么k也是解，与具有唯一解矛盾，所以该出发点为唯一解。

如果油的总量不小于消耗的总量，则一定存在一个解（由上面的数学定理）

假设t[i] = gas[i]-cost[i] t[i]表示从i到i+1的 纯剩余油量（假设这么叫）

如果纯剩余油量之和大于等于0，说明有解

证明如下：（其实也是上面数学定理的证明）
```
所有的前提是 Σ gas[i] > Σ cost[i]
如果只有一个气站，显然正确
如果有两个气站a和b，如果gas[a]<cost[a] 那么gas[b]一定大于cost[b],所以从b出发即可
如果有三个加油站a，b和c，其中gas（a）<cost（a），即我们不能直接从a到b，那么：
如果gas（b）<cost（b），即我们不能直接从b到c，那么gas（c）>cost（c），所以我们可以从c开始并前往a;因为gas（b）<cost（b），gas（c）+gas（a）必须大于cost（c）+cost（a），所以我们可以继续从a到b旅行。

关键点：可以认为这是因为c'处有一个气站 gas（c'）=gas（c）+gas（a），而c'到b的成本是 cost（c'）=cost（c） +cost（a），问题减少到两个站的问题。这反过来成为上面两个站的问题。

或者如果gas（b）> =cost（b），我们可以直接从b到c。与上述情况类似，这个问题可以减少到两个站b'和a的问题，其中gas（b'）=gas（b）+gas（c）和cost（b'）=cost（b）+cost（C）。由于gas（a）<cost（a），gas（b'）必须大于cost（b'），所以它也得到了解决。
对于更多站点的问题，我们可以以类似的方式减少它们。实际上，如上面针对三个站的示例所示，两个站的问题也可以减少到一个站的初始问题
```

```java
class Solution {
    //简单办法
    public int canCompleteCircuit(int[] gas, int[] cost) {
        for(int i=0;i<gas.length;i++){
            int tank = 0;
            int curPos = i;
            tank += gas[i];
            int reached = 0;  //经过的站的个数
            while(reached < gas.length){
                //不够到达下个加油站
                if(tank < cost[curPos]){
                    break;
                }
                //够到达
                tank = tank - cost[curPos];
                curPos = (curPos+1)%gas.length;
                tank += gas[curPos];
                reached++;
            }
            if(reached==gas.length) return i;
        }
        return -1;
    }

    //更好的办法
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int start = 0; // 起始位置
        int remain = 0; // 当前剩余燃料
        int debt = 0; // 前面没能走完的路上欠的债

        for (int i = 0; i < gas.size(); i++) {
            remain += gas[i] - cost[i];
            if (remain < 0) {
                debt += remain;
                start = i + 1;
                remain = 0;
            }
        }

        //剩余燃料-欠的燃料 = 总的剩余燃料
        return remain + debt >= 0 ? start : -1;
    }

}
```

138. Copy List with Random Pointer
Medium

1502

416

Favorite

Share
A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.

Return a deep copy of the list.

 
Example 1:

Input:
{"$id":"1","next":{"$id":"2","next":null,"random":{"$ref":"2"},"val":2},"random":{"$ref":"2"},"val":1}

Explanation:
Node 1's value is 1, both of its next and random pointer points to Node 2.
Node 2's value is 2, its next pointer points to null and its random pointer points to itself.

一个特殊的链表，链表节点的next指向下一个节点，random指向这个链表中任一一个节点或者空，
要求深拷贝这个链表

难点在于怎么让新的链表也维持random域的关系

先完全不管random域，拷贝出一个只有next域的链表
把源链表放入一个list，这样可以知道它的random域指向的节点序号
把生成的链表也放入list，按照原链表的random序号去指新链表节点的random


```java
/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};
*/
class Solution {
    public Node copyRandomList(Node head) {
        if(head==null) return null;
        Node cur = head;
        Node newPre = null;
        ArrayList<Node> originList = new ArrayList<>();
        ArrayList<Node> newList = new ArrayList<>();

        while(cur!=null){
            originList.add(cur);
            Node copy = new Node();
            copy.val=cur.val;
            newList.add(copy);
            if(newPre!=null){
                newPre.next = copy;
            }
            newPre = copy;
            cur = cur.next;
        }


        for(int i = 0; i<newList.size();i++){
            Node t = originList.get(i).random;
            //如果random不为空才去找这个random的索引
            if(t!=null){
                int randomIndex = originList.indexOf(t);
                newList.get(i).random = newList.get(randomIndex);
            }
        }
        return newList.get(0);
    }
}
```

140. Word Break II
Hard

Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, 
add spaces in s to construct a sentence where each word is a valid dictionary word. 
Return all such possible sentences.

Note:

The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.
Example 1:

Input:
s = "catsanddog"
wordDict = ["cat", "cats", "and", "sand", "dog"]
Output:
[
  "cats and dog",
  "cat sand dog"
]
Example 2:

Input:
s = "pineapplepenapple"
wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
Output:
[
  "pine apple pen apple",
  "pineapple pen apple",
  "pine applepen apple"
]
Explanation: Note that you are allowed to reuse a dictionary word.
Example 3:

Input:
s = "catsandog"
wordDict = ["cats", "dog", "sand", "and", "cat"]
Output:
[]

用回退递归会导致超时，回退递归由于是跨着来遍历的，没办法用map保存已经检查过的字符串对于的结果（事实上也没有这个步骤
，所以会超时）

以字符串为主体进行dfs，这里节省时间的核心是用map来保存已经检查过的字符串对应的结果，
如果没有map，则会超时

```java
class Solution {

    public List<String> wordBreak(String s, List<String> wordDict) {
        return DFS(s, wordDict, new HashMap<String, List<String>>());
    }

    List<String> DFS(String s, List<String> wordDict, HashMap<String, List<String>> map){
        //map是用来复用结果的，如果被检查的字符串已经遍历过了，直接使用结果
        //这个map也是节省时间的核心
        if(map.containsKey(s)){
            return map.get(s);
        }

        //本层的结果
        ArrayList<String> res = new ArrayList<>();
        if(s.length()==0){
            res.add("");
            return res;
        }

        for(String word: wordDict){
            //如果当前串以word开头，当然对于一个s，只能有一次成功
            if(!s.startsWith(word)) continue;
            String sub = s.substring(word.length(), s.length());
            //找到余下部分的结果列表，并把本单词加到余下部分每个结果的开头，作为本层结果的开头
            List<String> subList = DFS(sub, wordDict, map);
            for(String per: subList){
                res.add(word + (per.isEmpty()?"":" ") + per);
            }
        }

        //将本次的结果存入map以便于复用
        map.put(s, res);
        return res;

    }
}
```

150. Evaluate Reverse Polish Notation
Medium

Evaluate the value of an arithmetic expression in Reverse Polish Notation.

Valid operators are +, -, *, /. Each operand may be an integer or another expression.

Note:

Division between two integers should truncate toward zero.
The given RPN expression is always valid. That means the expression would always evaluate to a result and there won't be any divide by zero operation.
Example 1:

Input: ["2", "1", "+", "3", "*"]
Output: 9
Explanation: ((2 + 1) * 3) = 9
Example 2:

Input: ["4", "13", "5", "/", "+"]
Output: 6
Explanation: (4 + (13 / 5)) = 6
Example 3:

Input: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
Output: 22
Explanation: 
  ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
= ((10 * (6 / (12 * -11))) + 17) + 5
= ((10 * (6 / -132)) + 17) + 5
= ((10 * 0) + 17) + 5
= (0 + 17) + 5
= 17 + 5
= 22

后缀表达式，直接用栈即可

```java
class Solution {

    public int evalRPN(String[] tokens) {
        Stack<String> stk = new Stack<>();
        for(int i=0;i<tokens.length;i++){
            String token = tokens[i];
            if(token.equals("+")){
                int op2 = Integer.valueOf(stk.pop());
                int op1 = Integer.valueOf(stk.pop());
                stk.push(String.valueOf(op1+op2));
            }else if(token.equals("-")){
                int op2 = Integer.valueOf(stk.pop());
                int op1 = Integer.valueOf(stk.pop());
                stk.push(String.valueOf(op1-op2));
            }else if(token.equals("*")){
                int op2 = Integer.valueOf(stk.pop());
                int op1 = Integer.valueOf(stk.pop());
                stk.push(String.valueOf(op1*op2));
            }else if(token.equals("/")){
                int op2 = Integer.valueOf(stk.pop());
                int op1 = Integer.valueOf(stk.pop());
                stk.push(String.valueOf(op1/op2));
            }else{
                stk.push(token);
            }
        }
        if(!stk.empty()){
            return Integer.valueOf(stk.pop());
        }
        return 0;
    }
}
```

149. Max Points on a Line
Hard

Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.

Example 1:

Input: [[1,1],[2,2],[3,3]]
Output: 3
Explanation:
^
|
|        o
|     o
|  o  
+------------->
0  1  2  3  4
Example 2:

Input: [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
Output: 4
Explanation:
^
|
|  o
|     o        o
|        o
|  o        o
+------------------->
0  1  2  3  4  5  6
NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.

找到同一条直线上点的最多的个数

对于一个点p0，如果p1，p2与它在同一直线上，则y1-y0/x1-x0  == y2-y0/x2-x0
所以左侧上下都除以上下的最大公约数为 y/x，右侧上下都除以上下的最大公约数也必为y/x
这样以这个x,y作为k存储在这条直线上的点数


```java
/*
     *  A line is determined by two factors,say y=ax+b
     *  
     *  If two points(x1,y1) (x2,y2) are on the same line(Of course). 

     *  Consider the gap between two points.

     *  We have (y2-y1)=a(x2-x1),a=(y2-y1)/(x2-x1) a is a rational, b is canceled since b is a constant

     *  If a third point (x3,y3) are on the same line. So we must have y3=ax3+b

     *  Thus,(y3-y1)/(x3-x1)=(y2-y1)/(x2-x1)=a

     *  Since a is a rational, there exists y0 and x0, y0/x0=(y3-y1)/(x3-x1)=(y2-y1)/(x2-x1)=a

     *  So we can use y0&x0 to track a line;
     */
    
    public class Solution{
        public int maxPoints(int[][] points) {
            if (points==null) return 0;
            if (points.length<=2) return points.length;
            
            //记录：x-y-个数
            Map<Integer,Map<Integer,Integer>> map = new HashMap<Integer,Map<Integer,Integer>>();
            int result=0;
            for (int i=0;i<points.length;i++){ 
                map.clear();
                int overlap=0,max=0;
                for (int j=i+1;j<points.length;j++){
                    //对于每一个顶点i，遍历它后面的所有顶点，计算横坐标和纵坐标的差值x，y
                    int x=points[j][0]-points[i][0];   //j.x-i.x
                    int y=points[j][1]-points[i][1];   //j.y-i.y
                    if (x==0&&y==0){
                        //和顶点i重复的点
                        overlap++;
                        continue;
                    }

                    //找到x，y的最大公约数
                    //如果在p1,p2,p3在同一条直线上，那么p1与p2的xy
                    //和p1与p3的xy的最大公约数是相同的
                    /*这个最大公约数是关键所在*/
                    int gcd=generateGCD(x,y);
                    if (gcd!=0){
                        x/=gcd;
                        y/=gcd;
                    }
                    
                    //对于每个x-y的组合，对应一个值，就是这种斜率下与点i在同一条直线上的点的个数
                    if (map.containsKey(x)){
                        if (map.get(x).containsKey(y)){
                            map.get(x).put(y, map.get(x).get(y)+1);
                        }else{
                            map.get(x).put(y, 1);
                        }                       
                    }else{
                        Map<Integer,Integer> m = new HashMap<Integer,Integer>();
                        m.put(y, 1);
                        map.put(x, m);
                    }
                    //找到点i所在所有直线中最大的点数（会少1，没有算点i本身）
                    max=Math.max(max, map.get(x).get(y));
                }
                //与i重复的点也是算（重复几个就算几次），再加上i本身
                result=Math.max(result, max+overlap+1);
            }
            return result;
            
            
        }
        private int generateGCD(int a,int b){
    
            if (b==0) return a;
            else return generateGCD(b,a%b);
            
        }
    }
```

162. Find Peak Element
Medium

A peak element is an element that is greater than its neighbors.

Given an input array nums, where nums[i] ≠ nums[i+1], find a peak element and return its index.

The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.

You may imagine that nums[-1] = nums[n] = -∞.

Example 1:

Input: nums = [1,2,3,1]
Output: 2
Explanation: 3 is a peak element and your function should return the index number 2.
Example 2:

Input: nums = [1,2,1,3,5,6,4]
Output: 1 or 5 
Explanation: Your function can return either index number 1 where the peak element is 2, 
             or index number 5 where the peak element is 6.
Note:

Your solution should be in logarithmic complexity.

peak元素是比它的邻居更大的元素。
给定一个输入数组nums，其中nums[i]≠nums[i+1]，找到一个peak元素并返回其索引。
数组可能包含多个峰值，在这种情况下，将索引返回到任何一个峰值都可以。

找到峰值元素，峰值元素是指比它两边的元素都大，如果用线性复杂度很简单，但这里要用对数复杂度
对数就得用二分的思想，查看mid是否是峰值，如果不是，则分别查看它的两边

```java
class Solution {
    public int findPeakElement(int[] nums) {
        if (nums.length <= 1) return 0;
        return binarySearch(0, nums.length - 1, nums);
    }

    int binarySearch(int low, int high, int[] nums) {
        //-1做一个边界条件避免越界
        if (low > high) return -1;
        int mid = (low + high) / 2;

        //如果mid是峰值，有三种情况，两种是mid为边界，一种是在中间
        if (mid == 0) {
            if (nums[0] > nums[1]) return mid;
        } else if (mid == nums.length - 1) {
            if (nums[mid] > nums[mid - 1]) return mid;
        } else if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
            return mid;
        }
        //mid不是峰值，则找它两边数组的峰值
        int left = binarySearch(low, mid - 1, nums);
        if (left != -1) return left;
        int right = binarySearch(mid + 1, high, nums);
        if(right!=-1) return right;

        //如果峰值不在左边则一定在右边，不可能mid不是峰值，而且还两边都没有峰值，因为题设说了一定有至少一个峰值
        // 如果不做约束，这情况可能出现没有峰值的情况，如[1,1,1,1]
        //按照题设，应该不会转到这个地方来，这里为了完备所以就这么写了
        return -1;
    }
}
```

166. Fraction to Recurring Decimal
Medium

Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.

If the fractional part is repeating, enclose the repeating part in parentheses.

Example 1:

Input: numerator = 1, denominator = 2
Output: "0.5"
Example 2:

Input: numerator = 2, denominator = 1
Output: "2"
Example 3:

Input: numerator = 2, denominator = 3
Output: "0.(6)"

给定两个表示分数的分子和分母的整数，以字符串格式返回分数。如果小数部分是重复的，则将重复部分用括号括起来。

这个题的难点在于怎么判断重复的部分

重要的是在考虑这个问题时要考虑所有的边界情况，包括:负整数、可能的溢出等。

在进行除法时，使用HashMap存储余数及其相关索引，以便每当出现相同的余数时，我们知道存在重复小数部分。

如对于2/3：商0余2，2* 10=20  20%3=2   20/3 = 6

## 整数和小数：
对于被除数n和除数d，其整数部分是： n/d
其小数部分如果用一个str存储
n = n%d;
while(n!=0){
    n * = 10;
    str += (n/d);
    n %= d;
}
在小数部分中，当n在之前出现过时，说明进入了循环,则在第一次n出现时的 n/d之前加（，在此时str中加），退出循环，即
能实现循环部分括起来


```java
class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        StringBuilder res = new StringBuilder();
        if(numerator==0) return "0";
        //处理负数的符号,用异或处理，如果两个数同号，则为正，否则为负
        res.append(((numerator>0)^(denominator>0))?"-":"");
        //因为有可能是最小负值，所以全转成long处理
        long n = Math.abs((long)numerator);
        long d = Math.abs((long)denominator);
        //整数部分：

        res.append(String.valueOf(n/d));

        //小数部分
        n %= d;
        StringBuilder sb2 = new StringBuilder();
        //保存出现过的被除数，一旦再次出现，说明有循环
        HashMap<Long, Integer> map = new HashMap<>();
        int i =0;
        if(n!=0){
            res.append(".");
        }
        while(n!=0){
            n *= 10; //这一步也很关键！
            if(map.containsKey(n)){
                //在n对应的结果的前面加上（表示从这里开始循环
                sb2.insert(map.get(n), "(");
                sb2.append(")");
                break;
            }
            sb2.append((n/d));
            map.put(n, i);
            n %= d;
            i++;
        }
        res.append(sb2.toString());
        return res.toString();
    }
}
```

171. Excel Sheet Column Number
Easy

Given a column title as appear in an Excel sheet, return its corresponding column number.

For example:

    A -> 1
    B -> 2
    C -> 3
    ...
    Z -> 26
    AA -> 27
    AB -> 28 
    ...
Example 1:

Input: "A"
Output: 1
Example 2:

Input: "AB"
Output: 28
Example 3:

Input: "ZY"
Output: 701

可以视为：26进制转10进制，其实很简单，对于ZY来说= Y  + Z * 26 = 25 + 26* 26

```java
class Solution {
    public int titleToNumber(String s) {
        int res = 0;
        int c = 0;
        for(int i=s.length()-1;i>=0;i--){
            //获得当前字母的数字
            int cur = (s.charAt(i)-'A' + 1);
            res += cur * Math.pow(26, c);
            c++;
        }
        return res;
    }
}
```

172. Factorial Trailing Zeroes
Easy

Given an integer n, return the number of trailing zeroes in n!.

Example 1:

Input: 3
Output: 0
Explanation: 3! = 6, no trailing zero.
Example 2:

Input: 5
Output: 1
Explanation: 5! = 120, one trailing zero.
Note: Your solution should be in logarithmic time complexity.

阶乘中的0
这里我们要求n!末尾有多少个0，
对于任意一个数m，它可以被写成：m=2^x * 5^y * (...其他部分)

0只能是m中的2和5相乘得到的，而在1到m（m任意）这个范围内，2的个数要远多于5的个数，
所以这里只需计算从1到n!这个范围内有多少个5就可以了。即上面y的个数

n！可以表示为：1* ... 5* ... 10* ... 15* ... (i* 5)* ... n

考虑n!的质数因子。后缀0总是由质因子2和质因子5相乘得来的，如果我们可以计数2和5的个数，问题就解决了。
//考虑例子：n = 5时，5!的质因子中(2 * 2 * 2 * 3 * 5)包含一个5和三个2。因而后缀0的个数是1。
//n = 11时，11!的质因子中((2 ^ 8) * (3 ^ 4) * (5 ^ 2) * 7)包含两个5和八个2。于是后缀0的个数就是2。
//我们很容易观察到质因子中2的个数总是大于等于5的个数,因此只要计数5的个数即可。
//那么怎样计算n!的质因子中所有5的个数呢？一个简单的方法是计算floor(n / 5)。例如，7!有一个5，10!有两个5。
//除此之外，还有一件事情要考虑。诸如25，125之类的数字有不止一个5。
//例如n=25, n!=25*24*23*...*15...*10...*5...*1=(5*5)*24*23*...*(5*3)*...(5*2)*...(5*1)*...*1，其中25可看成5*5,多了一个5，应该加上
//处理这个问题也很简单，首先对n/5，移除所有的单个5，然后/25，移除额外的5，以此类推。下面是归纳出的计算后缀0的公式。
//n!后缀0的个数 = n!质因子中5的个数= floor(n / 5) + floor(n / 25) + floor(n / 125) + ....


```java
class Solution {
    public int trailingZeroes(int n) {
        int result = 0;
        while(n>0){
            n /= 5;
            result += n;
        }
        return result;
    }
}
```

179. Largest Number
Medium

Given a list of non negative integers, arrange them such that they form the largest number.

Example 1:

Input: [10,2]
Output: "210"
Example 2:

Input: [3,30,34,5,9]
Output: "9534330"

这里直接用排序的工具Comparator，重写对于数组的排序算法，对于两个数a和b
如果ab大于ba，则a排在b的前面，直接将nums进行这样的排序，可得答案

```java
class Solution {

    public String largestNumber(int[] nums) {
        ArrayList<String> strs = new ArrayList<>();
        int zeroCount = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]==0) zeroCount += 1;
            strs.add(String.valueOf(nums[i]));
        }
        //如果全是0，直接返回0
        if(zeroCount==nums.length) return "0";
        sortArray(strs);
        StringBuilder res = new StringBuilder();
        for (int i=strs.size()-1; i>=0;i--){
            res.append(strs.get(i));
        }
        return res.toString();
    }

    void sortArray(ArrayList<String> list) {
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                StringBuilder s1 = new StringBuilder(o1 + o2);
                StringBuilder s2 = new StringBuilder(o2 + o1);
                //避免溢出，用long
                long l1 = Long.valueOf(s1.toString());
                long l2 = Long.valueOf(s2.toString());
                return (int) (l1 - l2);
            }
        });
    }
}
```

189. Rotate Array
Easy

Given an array, rotate the array to the right by k steps, where k is non-negative.

Example 1:

Input: [1,2,3,4,5,6,7] and k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
Example 2:

Input: [-1,-100,3,99] and k = 2
Output: [3,99,-1,-100]
Explanation: 
rotate 1 steps to the right: [99,-1,-100,3]
rotate 2 steps to the right: [3,99,-1,-100]
Note:

Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
Could you do it in-place with O(1) extra space?

相当于循环右移

```java
class Solution {
    public void rotate(int[] nums, int k) {
        int totalCount = 0;
        ////如果单用do...while的话，可能会形成一个小闭环，导致不是所有的元素都被正确移动过了，
        //所以再用一个大循环，如果移动过的元素不等于元素总数，则还要继续下一个坐标移动
        for(int i=0;i<nums.length;i++){
            if(totalCount==nums.length) break;
            int curIndex = i;
            int originIndex = curIndex;
            int nextValue = nums[curIndex];
            do{
                int nextIndex = (curIndex+k)%nums.length;
                int tmp = nums[nextIndex];
                nums[nextIndex] = nextValue;
                curIndex = nextIndex;
                nextValue = tmp;
                totalCount++;
            }while(curIndex!=originIndex);
        }
    }
}
```

190. Reverse Bits
Easy

Reverse bits of a given 32 bits unsigned integer.

Example 1:

Input: 00000010100101000001111010011100
Output: 00111001011110000010100101000000
Explanation: The input binary string 00000010100101000001111010011100 represents the unsigned integer 43261596, so return 964176192 which its binary representation is 00111001011110000010100101000000.
Example 2:

Input: 11111111111111111111111111111101
Output: 10111111111111111111111111111111
Explanation: The input binary string 11111111111111111111111111111101 represents the unsigned integer 4294967293, so return 3221225471 which its binary representation is 10101111110010110010011101101001.
 

Note:

Note that in some languages such as Java, there is no unsigned integer type. In this case, both input and output will be given as signed integer type and should not affect your implementation, as the internal binary representation of the integer is the same whether it is signed or unsigned.
In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 2 above the input represents the signed integer -3 and the output represents the signed integer -1073741825.
 

Follow up:

If this function is called many times, how would you optimize it?

把一个整数的二进制翻转，变成一个新的整数

```java
public class Solution {
    public int reverseBits(int n) {
        String originBits = Integer.toBinaryString(n);
        StringBuilder builder = new StringBuilder(originBits);
        builder.reverse();
        //因为要32位，所以要在不够的后面补0,但是直接用parese会在前面补，所以这里直接补
        int len = builder.length();
        for(int i=0;i<32-len;i++){
            builder.append(0);
        }
        //防止溢出，要先用long
        return (int) Long.parseLong(builder.toString(), 2);
    }
}
```

191. Number of 1 Bits
Easy

Write a function that takes an unsigned integer and return the number of '1' bits it has (also known as the Hamming weight).

Example 1:

Input: 00000000000000000000000000001011
Output: 3
Explanation: The input binary string 00000000000000000000000000001011 has a total of three '1' bits.
Example 2:

Input: 00000000000000000000000010000000
Output: 1
Explanation: The input binary string 00000000000000000000000010000000 has a total of one '1' bit.
Example 3:

Input: 11111111111111111111111111111101
Output: 31
Explanation: The input binary string 11111111111111111111111111111101 has a total of thirty one '1' bits.
 
Note:

Note that in some languages such as Java, there is no unsigned integer type. In this case, the input will be given as signed integer type and should not affect your implementation, as the internal binary representation of the integer is the same whether it is signed or unsigned.
In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 3 above the input represents the signed integer -3.

计算输入整数的二进制中1的个数

```java
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        String s = Integer.toBinaryString(n);
        int count =0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='1') count++;
        }
        return count;
    }
}
```

204. Count Primes

Count the number of prime numbers less than a non-negative number, n.

Example:

Input: 10
Output: 4
Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7

计算小于n的素数的个数

使用素数判别法

```java
class Solution {
    public int countPrimes(int n) {
        boolean[] flag = new boolean[n]; //true为非素数
        int count = 0;
        for(int i=2;i<n;i++){
            if(flag[i]) continue;
            count++;
            for(int j=i*2; j < n;j+=i){  //书上这里j=i*i，按道理没错，但是有时会超出范围，这里从i*2开始,将所有i的倍数判别为非素数
                flag[j] = true;
            }
        }
        return count;
    }
}
```

210. Course Schedule II
Medium

There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.

There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.

Example 1:

Input: 2, [[1,0]] 
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished   
             course 0. So the correct course order is [0,1] .
Example 2:

Input: 4, [[1,0],[2,0],[3,1],[3,2]]
Output: [0,1,2,3] or [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both     
             courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. 
             So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
Note:

The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.

给出的数组相当于是边的数组，这里仍然是求有向图的拓扑排序，用入度来做
每当遍历到一个元素时，它的后继元素入度-1

```java
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        ArrayList<Integer>[] postList = new ArrayList[numCourses]; //每个list保存一个节点的后继节点
        for(int i=0;i<numCourses;i++){
            postList[i] = new ArrayList<>();
        }
        for(int i=0;i<prerequisites.length;i++){
            int from = prerequisites[i][1];
            int to = prerequisites[i][0];
            inDegree[to]++;
            postList[from].add(to);
        }

        //因为最先求出的是入度为0的，所以用队列
        ArrayList<Integer> resList = new ArrayList<>();
        boolean[] flag = new boolean[numCourses];
        LinkedList<Integer> queue = new LinkedList<>();
        for(int i=0;i<numCourses;i++){
            if(inDegree[i]==0){
                queue.add(i);
                flag[i] = true;
            }

        }

        while(!queue.isEmpty()){
            int cur = queue.poll();
            resList.add(cur);
            //对cur的后继元素进行遍历，其入度-1，当入度为0且之前没有添加过时，添加进队列
            for(int i=0;i<postList[cur].size();i++){
                int post = postList[cur].get(i);
                inDegree[post]--;
                if(flag[post]) continue;
                if(inDegree[post]==0){
                    queue.add(post);
                    flag[post] = true;
                }
            }
        }

        
        //如果没有符合要求的，返回大小为0的数组，而不是null
        if(resList.size()==numCourses){
            int[] res = new int[numCourses];
            for(int i=0;i<numCourses;i++){
                res[i] = resList.get(i);
            }
            return res;
        }
        return new int[0];
    }
}
```

212. Word Search II
Hard

Given a 2D board and a list of words from the dictionary, find all words in the board.

Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

Example:

Input: 
board = [
  ['o','a','a','n'],
  ['e','t','a','e'],
  ['i','h','k','r'],
  ['i','f','l','v']
]
words = ["oath","pea","eat","rain"]

Output: ["eat","oath"]
 

Note:

All inputs are consist of lowercase letters a-z.
The values of words are distinct.

给出一个字典，在版面中找到其中出现过的词，词由相邻的字母组成，一个位置上的字母不能在一个词中出现多次
首先想到的就是深度优先遍历,对一个位置进行深搜时，要用一个flag避免搜到已经搜到过的字母,
使用普通递归的深搜会超时

考虑使用trie结构，并用word，表示它是当前单词的最后一个字母
只有一个trie根

这种搜索的题，可以考虑使用trie，配合使用深度优先遍历，可以剪很多枝（避免很多没用的遍历）
trie：字典树（特里结构、单词查找树）

```java
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        TrieNode root = buildTrie(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, res);
            }
        }
        return res;
    }

    public void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
        char c = board[i][j];
        if (c == '#' || p.next[c - 'a'] == null) return;
        p = p.next[c - 'a'];
        if (p.word != null) {   // found one
            res.add(p.word);
            p.word = null;     // de-duplicate，找到一个词后把它置null，下次便不会再找到它
        }

        board[i][j] = '#'; //在遍历完一个位置后，把它置为#,下次就不会遍历到他
        if (i > 0) dfs(board, i - 1, j, p, res);
        if (j > 0) dfs(board, i, j - 1, p, res);
        if (i < board.length - 1) dfs(board, i + 1, j, p, res);
        if (j < board[0].length - 1) dfs(board, i, j + 1, p, res);
        board[i][j] = c;  //遍历完它相邻的所有位置，再把它置回去
    }

    public TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String w : words) {
            TrieNode p = root;
            for (char c : w.toCharArray()) {
                int i = c - 'a';
                if (p.next[i] == null) p.next[i] = new TrieNode();
                p = p.next[i];
            }
            p.word = w;
        }
        return root;
    }

    class TrieNode {
        TrieNode[] next = new TrieNode[26];
        String word;
    }
}
```

217. Contains Duplicate
Easy

Given an array of integers, find if the array contains any duplicates.

Your function should return true if any value appears at least twice in the array, and it should return false if every element is distinct.

Example 1:

Input: [1,2,3,1]
Output: true
Example 2:

Input: [1,2,3,4]
Output: false
Example 3:

Input: [1,1,1,3,3,4,3,2,4,2]
Output: true

查看一个数组中是否含有重复的数
直接用set即可

```java
class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<nums.length;i++){
            if(set.contains(nums[i])){
                return true;
            }
            set.add(nums[i]);
        }
        return false;
    }
}
```

227. Basic Calculator II
Medium

Implement a basic calculator to evaluate a simple expression string.

The expression string contains only non-negative integers, +, -, *, / operators and empty spaces . The integer division should truncate toward zero.

Example 1:

Input: "3+2*2"
Output: 7
Example 2:

Input: " 3/2 "
Output: 1
Example 3:

Input: " 3+5 / 2 "
Output: 5
Note:

You may assume that the given expression is always valid.
Do not use the eval built-in library function.

给定中缀表达式，求表达式的值，
显然需要把中缀表达式转换成后缀表达式，再使用栈去解决

中缀-》后缀：
```
如果是数字则直接输出，如果是操作符，放入栈中：
-》如果是左括号，直接放入栈中
-》如果是右括号，则将栈元素弹出，将弹出的操作符输出到遇到左括号为止（左括号只弹出不输出）
-》如果是其他的操作符（加减乘除），从栈中弹出元素直到遇到发现更低优先级的元素(或者栈为空)为止。弹出完这些元素后，（元素弹出后输出）才将遇到的操作符压入到栈中。有一点需要注意，只有在遇到" ) "的情况下我们才弹出" ( "，其他情况我们都不会弹出" ( "。
-》如果我们读到了输入的末尾，则将栈中所有元素依次弹出。
```

后缀表达式：直接用栈来计算值

```java
class Solution {
    public int calculate(String s) {
        //去除所有空格
        s = s.replace(" ", "");
        String[] post = mid2Post(s);
        return post2Int(post);
    }

    //中缀表达式转后缀表达式
     String[] mid2Post(String s){
        ArrayList<String> res = new ArrayList<>();
        Stack<Character> stk = new Stack<>();
        boolean preIsNum = false;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            //如果是数字则直接输出,可以处理大于1位的非负整数
            if(c>= '0' && c <= '9') {
                if (preIsNum) {
                    res.set(res.size() - 1, res.get(res.size() - 1) + c);
                } else {
                    res.add("" + c);
                }
                preIsNum = true;
            }else{
                if(stk.isEmpty()) stk.push(c);
                else{
                    //对于乘除，只有下面是加减或空的时候才满足下面的优先级低，否则就一直弹栈
                    if((c=='*') || (c=='/')){
                        while(!(stk.isEmpty() || stk.peek()=='+' || stk.peek()=='-')){
                            res.add(""+stk.pop());
                        }
                    }else{
                        //对于加减，只有下面是空的时候才满足下面的优先级低
                        while(!stk.isEmpty()){
                            res.add(""+stk.pop());
                        }
                    }
                    stk.push(c);
                }
                preIsNum = false;
            }
        }
        while(!stk.isEmpty()){
            res.add(""+stk.pop());
        }

        String[] ss = new String[res.size()];
        return res.toArray(ss);
    }


    //后缀表达式转值
    int post2Int(String[] s){
        Stack<String> stk = new Stack<>();
        for(int i=0;i<s.length;i++){
            String token = s[i];
            if(token.equals("+")){
                int op2 = Integer.valueOf(stk.pop());
                int op1 = Integer.valueOf(stk.pop());
                stk.push(String.valueOf(op1+op2));
            }else if(token.equals("-")){
                int op2 = Integer.valueOf(stk.pop());
                int op1 = Integer.valueOf(stk.pop());
                stk.push(String.valueOf(op1-op2));
            }else if(token.equals("*")){
                int op2 = Integer.valueOf(stk.pop());
                int op1 = Integer.valueOf(stk.pop());
                stk.push(String.valueOf(op1*op2));
            }else if(token.equals("/")){
                int op2 = Integer.valueOf(stk.pop());
                int op1 = Integer.valueOf(stk.pop());
                stk.push(String.valueOf(op1/op2));
            }else{
                stk.push(token);
            }
        }
        if(!stk.empty()){
            return Integer.valueOf(stk.pop());
        }
        return 0;
    }

}
```

218. The Skyline Problem
Hard

A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo (Figure A), write a program to output the skyline formed by these buildings collectively (Figure B).

Buildings  Skyline Contour
The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and Ri are the x coordinates of the left and right edge of the ith building, respectively, and Hi is its height. It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.

For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .

The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. Note that the last key point, where the rightmost building ends, is merely used to mark the termination of the skyline, and always has zero height. Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.

For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].

Notes:

The number of buildings in any input list is guaranteed to be in the range [0, 10000].
The input list is already sorted in ascending order by the left x position Li.
The output list must be sorted by the x position.
There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]

图片沾不上来

https://leetcode.com/problems/the-skyline-problem/

城市的天际线是城市中所有建筑从远处看形成轮廓的外部轮廓。现在，假设您得到了城市景观照片(图a)中所示的所有建筑物的位置和高度，编写一个程序来输出这些建筑物共同形成的天际线(图B)。

每个建筑的几何信息由一个整数三联体[Li, Ri, Hi]表示，其中Li和Ri分别为第i个建筑左右边缘的x坐标，Hi为其高度。保证0≤Li, Ri≤INT_MAX, 0 < Hi≤INT_MAX, Ri - Li > 0。你可以假设所有的建筑都是完美的矩形，在高度为0的平面上。

例如，图A中所有建筑物的尺寸记录为:[[2 9 10]，[3 7 15]，[5 12 12]，[15 20 10]，[19 24 8]]。

输出为[[x1,y1]， [x2, y2]， [x3, y3]，…这是天际线的独特定义。关键点是水平线段的左端点。请注意，最后一个关键点，也就是最右边的建筑结束的地方，只是用来标记天际线的结束，并且总是没有高度。此外，任何两座相邻建筑之间的地面都应被视为天际线的一部分。

例如，图B中的skyline应该表示为:[[2 10]，[3 15]，[7 12]，[12 0]，[15 10]，[20 8]，[24,0]]。

注:

任何输入列表中的建筑物数量都保证在[0,10000]范围内。
输入列表已经按左x位置Li的升序排序。
输出列表必须按x位置排序。
输出天际线中不能有等高的连续水平线。例如,[…[2 3]，[4 5]，[7 5]，[11 5]，[12 7]…是不可接受的;在最终的输出中，高度5的三行应该合并为一行，如下所示:[…[2 3]，[4 5]，[12 7]，…]


从左往右扫描，当遇到矩形边时，假设其横坐标为x，找到该x上最高的那一个点，如果最高点和前面的高度一样，则不记录，否则记录
为了不漏掉高度为1的点

```java
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        int[] maxHeight = new int[10005];
        Set<Integer> set = new HashSet<>();
        //遍历全部边界
        for(int[] build: buildings){
            int l = build[0];
            int r = build[1];
            int h = build[2];
            set.add(l);
            set.add(r);
        }
        ArrayList<Integer> list = new ArrayList<>(set);
        //把边界全部排序
        Collections.sort(list);

        ArrayList<List<Integer>> tmp = new ArrayList<>();
        //这样可能还是会添加一些同高的数据
        int preHeight = 0;
        for(int x: list){
            int height = findMaxHeightWithX(buildings, x);
            if(height!=preHeight){
                //addPoint;
                ArrayList<Integer> ans = new ArrayList<>();
                ans.add(x);
                ans.add(height);
                tmp.add(ans);
            }else if(findMaxHeightWithX(buildings, x+1)==0){
                //如果x+1的最高高度是0，而且x的height等于前一个height而且height不为0,说明x是边界,则添加
                ArrayList<Integer> ans = new ArrayList<>();
                ans.add(x);
                ans.add(0);
                tmp.add(ans);
            }
            preHeight = height;
        }

        //如果后面的和前面的同高，去掉前面的
        //比如[3,10,10]，[4,9,5]，当判断9的时候，由于10处的高是0，所以会带着9处的高也变成0，且不与前面的一样
        //会导致[9,0]也添加进tmp，这里清除一下
        ArrayList<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<tmp.size();i++){
            if(i+1<tmp.size() && tmp.get(i).get(1).equals(tmp.get(i+1).get(1))){
                continue;
            }
            res.add(tmp.get(i));
        }
        return res;
    }

    //找到横坐标为x对应的最高高度
    int findMaxHeightWithX(int[][] buildings, int x){
        int maxHeight = 0;
        for(int[] build: buildings){
            int l = build[0];
            int r = build[1];
            int h = build[2];
            //对于矩形边界来说，左算右不算，比如保证[3,8,10]和[4,10.5]这两个矩形在x=8处的最高高度是5
            if(x >= l && x < r && h> maxHeight){
                maxHeight = h;
            }
        }
        return maxHeight;
    }

}
```

230. Kth Smallest Element in a BST
Medium

Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

Note: 
You may assume k is always valid, 1 ≤ k ≤ BST's total elements.

Example 1:

Input: root = [3,1,4,null,2], k = 1
   3
  / \
 1   4
  \
   2
Output: 1
Example 2:

Input: root = [5,3,6,2,4,null,null,1], k = 3
       5
      / \
     3   6
    / \
   2   4
  /
 1
Output: 3
Follow up:
What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? How would you optimize the kthSmallest routine?

找出二叉搜索树第k小的节点
二叉搜索树的中序遍历就是递增序列，所以要中序遍历的第k个值
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
    public int kthSmallest(TreeNode root, int k) {
        int count =0;
        Stack<TreeNode> stk = new Stack<>();
        TreeNode p = root;
        while(p!=null || !stk.isEmpty()){
            if(p!=null){
                stk.push(p);
                p = p.left;
            }else{
                p = stk.pop();
                //check p
                if(++count==k){
                    return p.val;
                }
                p = p.right;
            }
        }
        //保证的默认值
        return 0;
    }
}
```

237. Delete Node in a Linked List
Easy

Write a function to delete a node (except the tail) in a singly linked list, given only access to that node.

Given linked list -- head = [4,5,1,9], which looks like following:

Example 1:

Input: head = [4,5,1,9], node = 5
Output: [4,1,9]
Explanation: You are given the second node with value 5, the linked list should become 4 -> 1 -> 9 after calling your function.
Example 2:

Input: head = [4,5,1,9], node = 1
Output: [4,5,9]
Explanation: You are given the third node with value 1, the linked list should become 4 -> 5 -> 9 after calling your function.
 

Note:

The linked list will have at least two elements.  链表至少有2个节点
All of the nodes' values will be unique.   链表中值唯一
The given node will not be the tail and it will always be a valid node of the linked list.
Do not return anything from your function.

编写一个函数来删除单链表中的一个节点(尾巴除外)，该函数只允许访问该节点。

注意的是只能访问该节点！也就是说前面的都是透明的

而这个题的解法，就是把node的值改成node.next的值，然后删除node.next
显然这不是删除了节点，题目有点违反直觉，但在实际中是很有用的

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
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
```

242. Valid Anagram
Easy

Given two strings s and t , write a function to determine if t is an anagram of s.

Example 1:

Input: s = "anagram", t = "nagaram"
Output: true
Example 2:

Input: s = "rat", t = "car"
Output: false
Note:
You may assume the string contains only lowercase alphabets.

Follow up:
What if the inputs contain unicode characters? How would you adapt your solution to such case?

单词s是否是单词t的改变字母顺序的结果

用两个数组分别记录两个单词中每个字母的个数，然后比较两个数组对应位置的值是否相同

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        int[] sCount = new int[26];
        int[] tCount = new int[26];
        if(s.length()!=t.length()) return false;
        if(s.equals(t)) return true;

        for(int i=0;i<s.length();i++){
            sCount[s.charAt(i)-'a']++;
            tCount[t.charAt(i)-'a']++;
        }

        for(int i=0;i<sCount.length;i++){
            if(sCount[i]!=tCount[i]) return false;
        }
        return true;
    }
}
```

268. Missing Number
Easy

Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.

Example 1:

Input: [3,0,1]
Output: 2
Example 2:

Input: [9,6,4,2,3,5,7,0,1]
Output: 8
Note:
Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?


给一个长度为n的数组，里面的元素是从0取到n，（数组长度为n，元素个数为n+1）,找到缺少的那个数

算出0到n的和，再算出数组中的和，一减就是差的那个数

```java
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = (0+n)*(n+1)/2;
        int arraySum = 0;
        for(int i;i<n;i++){
            arraySum += nums[i];
        }
        return sum - arraySum;
    }
}
```

289. Game of Life
Medium

According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):

Any live cell with fewer than two live neighbors dies, as if caused by under-population.
Any live cell with two or three live neighbors lives on to the next generation.
Any live cell with more than three live neighbors dies, as if by over-population..
Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
Write a function to compute the next state (after one update) of the board given its current state. The next state is created by applying the above rules simultaneously to every cell in the current state, where births and deaths occur simultaneously.

Example:

Input: 
[
  [0,1,0],
  [0,0,1],
  [1,1,1],
  [0,0,0]
]
Output: 
[
  [0,0,0],
  [1,0,1],
  [0,1,1],
  [0,1,0]
]
Follow up:

Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot update some cells first and then use their updated values to update other cells.
In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array. How would you address these problems?

给一个m* n的平面中的细胞，每个细胞有1（生）或0（死）两种状态，每个格子可以影响到它周围的8个格子（上下左右，斜方），
按照如下规则：
任何活的细胞如果少于两个活的邻居，就会死亡，好像是由于人口不足造成的。
任何有两个或三个邻居的活细胞都能活到下一代。
任何有三个以上邻居的活细胞都会死亡，就好像是由于人口过剩。
任何有三个活邻居的死细胞都会变成活细胞，就像通过繁殖一样。
编写一个函数来计算给定当前状态的下一个状态(在一次更新之后)。下一个状态是通过将上述规则同时应用于当前状态中的每个细胞而创建的，此时出生和死亡同时发生。

你能就地解决吗?请记住，板子需要同时更新:您不能先更新某些细胞，然后使用它们的更新值来更新其他细胞。
在这个问题中，我们使用2D数组来表示黑板。从理论上讲，板是无限的，当有源区域侵犯阵列边界时，会产生问题。你将如何解决这些问题?

如果不用就地解决很好办，直接用另一个二维数组存下一个状态即可

为了能就地解决，让board既保存原状态，也保存下一个状态，由于状态是非0既1，只用一位就可以表示
而board的每个元素elem是int
因此让elem的最低位表示旧状态，次低位表示新状态，
当新状态更新完后，只需要右移一位即可替换

这种思想可以借鉴，当要存储的每项数据只有很少的几位，而实际类型却有很多位时，可以考虑复用存储单元

```java
class Solution {
    public void gameOfLife(int[][] board) {
        int row = board.length;
        int col = board[0].length;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                //如果下一个状态是死，则不需要更改（因此次高位本来就是0），如果是活，则需要
                //把次低位改成1，而且不能破坏最低位，因为别的cell还会用
                if(checkNextStatus(i,j,board)){
                    //和 10（2）做或操作，可以使第二位变成1，第一位不变
                    board[i][j] = board[i][j] | 2;
                }
            }
        }

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                board[i][j] = board[i][j]>>1;
            }
        }
    }

    boolean checkNextStatus(int i, int j, int[][] board){
        //边界之外就当成死细胞，好处理
        int row = board.length;
        int col = board[0].length;
        int[] neighbors = new int[8];
        //初始状态置为-1；
        Arrays.fill(neighbors, -1);
        //保存从左上方开始围绕当前cell一圈的邻居的状态,注意要&1获得当前状态
        if(i-1>=0 && j-1>=0) neighbors[0] = board[i-1][j-1]&1;
        if(i-1>=0) neighbors[1] = board[i-1][j]&1;
        if(i-1>=0 && j+1<col) neighbors[2] = board[i-1][j+1]&1;
        if(j+1 < col) neighbors[3] = board[i][j+1]&1;
        if(i+1 < row && j+1 < col) neighbors[4] = board[i+1][j+1]&1;
        if(i+1 < row) neighbors[5] = board[i+1][j]&1;
        if(i+1 < row && j-1 >= 0) neighbors[6] = board[i+1][j-1]&1;
        if(j-1>=0) neighbors[7] = board[i][j-1]&1;

        //获得最低位，最低位就是当前状态
        int cur = board[i][j]&1;
        int deadNeighbor = 0;
        int liveNeighbor = 0;
        for(int k=0;k<8;k++){
            if(neighbors[k]==1) liveNeighbor++;
            else if(neighbors[k]==0) deadNeighbor++;
        }

        if(cur==1 && liveNeighbor<2) return false;
        if(cur==1 && (liveNeighbor==2 || liveNeighbor==3)) return true;
        if(cur==1 && liveNeighbor>3) return false;
        if(cur==0 && liveNeighbor==3) return true;
        return false;
    }
}
```

295. Find Median from Data Stream
Hard

Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.

For example,
[2,3,4], the median is 3

[2,3], the median is (2 + 3) / 2 = 2.5

Design a data structure that supports the following two operations:

void addNum(int num) - Add a integer number from the data stream to the data structure.
double findMedian() - Return the median of all elements so far.
 

Example:

addNum(1)
addNum(2)
findMedian() -> 1.5
addNum(3) 
findMedian() -> 2
 

Follow up:

If all integer numbers from the stream are between 0 and 100, how would you optimize it?
If 99% of all integer numbers from the stream are between 0 and 100, how would you optimize it?

median是一个数组序列中的中间值，数组长度是偶数就是两个中间的值之和
设计一个数据结构完成：
addNum，添加一个值到结构中
findMedian，找到结构中所有值的中间值

使用ArrayList，排序是容易想到的办法，但效率很低，虽然能过

效率高的话，考虑使用优先级队列
两个优先级队列max,min，一个从大到小排序，一个从小到大排序
但每个队列只存储数列的一半，但随着数加入顺序的不同，不一定是前-后 的一半，或是奇-偶的一半

先把一个数字加入max，然后从max中poll出的一定是max中最大的数（规定了优先级），
然后加入min，如果此时总数是偶数，则max和min的个数一样
如果是奇数，则从min中poll出min中最小的数，放入max中

这样能保证，如果总数是偶数，则两个队头的元素就是数组中最中间的两个数
如果总数是奇数，max队头的元素就是最中间的那个数

## 记住这种找中间值的办法（两个优先级相反的优先级队列）

考虑使用

```java
//方法一：使用array，很容易想到，能过但效率很低
class MedianFinder {
    ArrayList<Integer> array;
    /** initialize your data structure here. */
    public MedianFinder() {
        array = new ArrayList<>();
    }
    
    public void addNum(int num) {
        array.add(num);
    }
    
    public double findMedian() {
        array.sort();

        int len = array.size();
        if(len==0) return 0;
        if(len%2==0){
            int mid1 = len/2;
            int mid2 = mid1-1;
            return ((double) array.get(mid1)+array.get(mid2))/2;
        }else{
            int mid = len/2;
            return (double)array.get(mid);
        }
    }   
}

//方法二：使用两个优先级队列：
class MedianFinder {
    // max queue is always larger or equal to min queue

    PriorityQueue<Integer> smallHalf = new PriorityQueue();
    PriorityQueue<Integer> greatHalf = new PriorityQueue(1000, Collections.reverseOrder());
    // Adds a number into the data structure.
    public void addNum(int num) {
        //这样做可以保证要么两个队列元素一样多，要么greatHalf比smallHalf多一个
        greatHalf.offer(num);
        smallHalf.offer(greatHalf.poll());
        if (greatHalf.size() < smallHalf.size()){
            greatHalf.offer(smallHalf.poll());
        }
    }

    // Returns the median of current data stream
    public double findMedian() {
        if (greatHalf.size() == smallHalf.size()) return (greatHalf.peek() + smallHalf.peek()) /  2.0;
        else return greatHalf.peek();
    }
};


/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
```

315. Count of Smaller Numbers After Self
Hard

You are given an integer array nums and you have to return a new counts array. The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].

Example:

Input: [5,2,6,1]
Output: [2,1,1,0] 
Explanation:
To the right of 5 there are 2 smaller elements (2 and 1).
To the right of 2 there is only 1 smaller element (1).
To the right of 6 there is 1 smaller element (1).
To the right of 1 there is 0 smaller element.

给一个数组nums，返回一个数组counts, counts[i]是nums[i]右边比它小的元素的个数
如果回回都要遍历会超时

把数组改造成一个二叉搜索树，其节点有3个域：
val：本身的值
leftSum：该节点的左子树中的节点值
dup：该值在邻旁的重复数，

比如对于 [3, 2, 2, 6, 1]，从后往前向树中插入节点

                1(0, 1) 
                     \
                     6(3, 1)
                     /
                   2(0, 2) （2在这个位置重复了两次）
                       \
                        3(0, 1)
每个元素对应的答案就是它插入过程中，转向右子树（要插入的节点转向右子树，说明要插入的节点比该节点大）
比较费解的方法，尤其是插入的过程，还需要好好看看


```java
class Solution {
        class TreeNode {
            int leftSum; //代表元素左边比它小的元素个数
            int val;
            int dup=1;
            TreeNode left;
            TreeNode right;
            public TreeNode(int val, int leftSum){
                this.val = val;
                this.leftSum = leftSum;
            }

        }
        public List<Integer> countSmaller(int[] nums) {
            int len = nums.length;
            Integer[] ans = new Integer[len];
            TreeNode node = null;
            //每次node都是根节点
            for(int i=len-1;i>=0;i--){
                node = insert(nums[i], ans, i, node, 0);
            }
            return Arrays.asList(ans);

        }

        //preSum是比在node所代表元素右边，且比它小的元素
        //新加入一个元素t时，其他所有元素都在它的右边（因为它是最新的），但它往右拐弯时，让它拐弯的元素为m，
        //m左边比m更小的元素仍是t的右边，它们仍算在t的leftSum
        public TreeNode insert(int val, Integer[] ans, int i, TreeNode node, int preSum){
            if(node==null){
                node= new TreeNode(val, 0);
                ans[i] = preSum;
            }else if(val==node.val){
                node.dup++;
                ans[i] = preSum + node.leftSum;
            }else if(val > node.val){
                node.right = insert(val, ans, i, node.right, preSum+node.leftSum+node.dup);
            }else if(val < node.val){
                node.left = insert(val, ans, i, node.left, preSum);
                node.leftSum++;
            }
            return node;
        }
}
```

324. Wiggle Sort II
Medium

Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....

Example 1:

Input: nums = [1, 5, 1, 1, 6, 4]
Output: One possible answer is [1, 4, 1, 5, 1, 6].
Example 2:

Input: nums = [1, 3, 2, 2, 3, 1]
Output: One possible answer is [2, 3, 1, 3, 1, 2].
Note:
You may assume all input has valid answer.

Follow Up:
Can you do it in O(n) time and/or in-place with O(1) extra space?

摆动排序

要保证的是，奇数位的数字一定它两边的偶数位的
先排序，排完序然后从左往右奇数索引位置放大于中位数的数, 然后从右往左在偶数索引位置放小于中位数的数, 剩下的位置都放中位数. 其时间复杂度为O(nlog(n)), 空间复杂度为O(n).

```java
class Solution {
  public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        int[] copy = new int[nums.length];
        System.arraycopy(nums, 0, copy, 0, nums.length);

        //小数字从右往左放，从右往左依次增大，能保证如果需要中间数，那它一定在最左端
        //大数字也从右往左放，从右往左依次增大，能保证如果需要中间数，那它一定在最右端
        //这里的话，就算两头是中间数，也不会出现相邻的相同了

        //小数字放偶数位
        //如果末尾索引是偶数，就从末尾开始填，如果是奇数，就从末尾的前一个开始填
        int i = ((nums.length-1)%2==0)?nums.length-1:nums.length-2;
        int j = 0;
        while (i>=0){
            nums[i] = copy[j++];
            i -=2;
        }


        //大数字放奇数位
        i = 1;
        j = nums.length-1;
        while (i<nums.length){
            nums[i] = copy[j--];
            i += 2;
        }
    }
}
```

326. Power of Three
Easy

Given an integer, write a function to determine if it is a power of three.

Example 1:

Input: 27
Output: true
Example 2:

Input: 0
Output: false
Example 3:

Input: 9
Output: true
Example 4:

Input: 45
Output: false
Follow up:
Could you do it without using any loop / recursion?

判断一个数是否是3的次方数
注意的是，1是任何数的次方数（0次方）

```java
class Solution{
    //方法一：
    public boolean isPowerOfThree(int n) {
        HashSet<Integer> set = new HashSet<>(Arrays.asList(1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049, 177147, 531441, 1594323, 4782969, 14348907, 43046721, 129140163, 387420489, 1162261467));
        return set.contains(n);
    }

    //方法二：
    public boolean isPowerOfThree(int n) {
        return n > 0 && (n==1 || n/3==0 && isPowerOfThree(n/3));
    }

    //方法三：
    public boolean isPowerOfThree(int n) {
        //3^19 = 1162261467, 3^20比INT最大值还大
        return n>1 && 1162261467%n==0;
    }    
}
```

328. Odd Even Linked List
Medium

Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are talking about the node number and not the value in the nodes.

You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.

Example 1:

Input: 1->2->3->4->5->NULL
Output: 1->3->5->2->4->NULL
Example 2:

Input: 2->1->3->5->6->4->7->NULL
Output: 2->3->6->7->1->5->4->NULL
Note:

The relative order inside both the even and odd groups should remain as it was in the input.
The first node is considered odd, the second node even and so on ...

奇偶链表，给一个单链表，把奇数序号的节点放在前面，偶数序号的放在奇数序号的后面，应该使用O(1)
的空间复杂度和O(n)的时间复杂度。
关键是常数空间复杂度

用odd，even分别代表奇偶节点，循环迭代，每次令odd的next为它的next的next，even也一样
然后再让odd = odd.next, even=even.next;

这里是不设置值，而是利用next节点，在链表内部形成两个链表，最后再把它们连起来


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
    public ListNode oddEvenList(ListNode head) {
        if(head==null || head.next==null) return head;
        ListNode oddHead = head;
        ListNode evenHead = head.next;
        ListNode odd=oddHead;
        ListNode even = evenHead;
        //奇数要么和偶数一样多，要么比偶数多1
        //最终odd是最后一个奇，even是最后一个偶
        while((odd.next!=null && odd.next.next!=null) || (even.next!=null && even.next.next!=null)){
            if(odd.next!=null && odd.next.next!=null){
                odd.next = odd.next.next;
                odd = odd.next;
            }

            if(even.next!=null && even.next.next!=null){
                even.next = even.next.next;
                even = even.next;
            }
        }
        odd.next = evenHead;
        even.next = null;
        return head;
    }
}
```

329. Longest Increasing Path in a Matrix
Hard

Given an integer matrix, find the length of the longest increasing path.

From each cell, you can either move to four directions: left, right, up or down. You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).

Example 1:

Input: nums = 
[
  [9,9,4],
  [6,6,8],
  [2,1,1]
] 
Output: 4 
Explanation: The longest increasing path is [1, 2, 6, 9].
Example 2:

Input: nums = 
[
  [3,4,5],
  [3,2,6],
  [2,2,1]
] 
Output: 4 
Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.

给定一个整数矩阵，求出最长增长路径的长度。

从每个单元格中，您可以向四个方向移动:左、右、上或下。你不能斜向移动或移出边界(即不允许环绕)。

可以试一试回退递归，回退递归其实和dfs是一样的，
这里有个核心地方在于，保存已经遍历过的位置作为出发点的最大路径

因此这里的关键在于，从一个cell出发，它的最长路径值是一定的， 不用管它的上一个cell是谁，(不存在走回头路的可能，因为回头的cell比当前cell小，走不过去的，而不像一般的dfs，还要保存已经遍历过的标志)

使用dfs

```java
class Solution {
    int[][] dirs = new int[][]{{-1,0}, {1,0}, {0, -1}, {0,1}};
    public int longestIncreasingPath(int[][] matrix) {
        if(matrix.length==0 || matrix[0].length==0) return 0;
        int max = 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] cache= new int[m][n];
        for(int i=0;i < m; i++){
            for(int j=0;j<n;j++){
                int len = dfs(i, j,m,n, matrix, cache);
                max = Math.max(max, len);
            }
        }
        return max;
    }

    int dfs(int row, int col, int m, int n, int[][] matrix, int[][] cache){
        if(cache[row][col]!=0) return cache[row][col];

        int tmpMax = 1; //最少也是1个，只有当前cell
        for(int[] dir: dirs){
            int x = dir[0]+row;
            int y = dir[1]+col;
            if(x<0 || x >= m || y <0 || y >= n || matrix[x][y]<=matrix[row][col])
                continue;
            int len = 1+dfs(x,y,m,n,matrix, cache);
            tmpMax = Math.max(tmpMax, len);
        }

        cache[row][col] = tmpMax;
        return tmpMax;
    }

}
```

334. Increasing Triplet Subsequence
Medium

Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.

Formally the function should:

Return true if there exists i, j, k 
such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
Note: Your algorithm should run in O(n) time complexity and O(1) space complexity.

Example 1:

Input: [1,2,3,4,5]
Output: true
Example 2:

Input: [5,4,3,2,1]
Output: false

查看数组内有没有从左往右递增的三个数，这三个数可以不挨着
只遍历一遍，而且空间复杂度O（1）

以下是一个巧妙的算法

small永远是遍历过的数中的最小值
mid永远是比small大，且排在small后面，到当前数中的最小值
再遇到比small和mid大的数时，则说明满足条件

if语句能保证small，mid的次序是对的

这个题还可以拓展，如果问四个连续增加数，则再加一个，五个就再加1个。。。

```java
class Solution {
    public boolean increasingTriplet(int[] nums) {
        int small = Integer.MAX_VALUE;
        int mid = Integer.MAX_VALUE;
        for(int num: nums){
            if(num <= small){
                small = num;
            }
            else if(num <= mid){
                mid = num;
            }else{
                return true;
            }
        }
        return false;
    }
}
```

341. Flatten Nested List Iterator
Medium

Given a nested list of integers, implement an iterator to flatten it.

Each element is either an integer, or a list -- whose elements may also be integers or other lists.

Example 1:

Input: [[1,1],2,[1,1]]
Output: [1,1,2,1,1]
Explanation: By calling next repeatedly until hasNext returns false, 
             the order of elements returned by next should be: [1,1,2,1,1].
Example 2:

Input: [1,[4,[6]]]
Output: [1,4,6]
Explanation: By calling next repeatedly until hasNext returns false, 
             the order of elements returned by next should be: [1,4,6].

给定一个嵌套的整数列表，实现一个迭代器将其变平。
每个元素要么是整数，要么是列表——其元素也可以是整数或其他列表。

这里有一个地方一定要注意，逻辑一定要放在hasNext而不是next中
假如传入[[]] 如果逻辑在hashNext中，则能判断出这个的hasNext为false，继而不会调用next，最终结果是链表：[]
如果逻辑都在next中，此时hashNext只判断nestedList的长度，长度为1（唯一的元素是[]），再调用next，而后返回结果null，
最终结果是[null],就会导致错误

```java
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {
    LinkedList<NestedInteger> nestedList;
    public NestedIterator(List<NestedInteger> nestedList) {
        this.nestedList = new LinkedList<>(nestedList);
    }

    @Override
    public Integer next() {
        if(hasNext()) return nestedList.pop().getInteger();
        return null;
    }

    @Override
    public boolean hasNext() {
        while(nestedList.size()>0){
            if(nestedList.peek().isInteger()){
                return true;
            }else{
                List<NestedInteger> tmp = nestedList.pop().getList();
                //向栈中添加，顺序是从tmp后往前
                for(int i=tmp.size()-1;i>=0;i--){
                    nestedList.push(tmp.get(i));
                }
            }
        }
        return false;
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
```


344. Reverse String
Easy

Write a function that reverses a string. The input string is given as an array of characters char[].

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

You may assume all the characters consist of printable ascii characters.

Example 1:

Input: ["h","e","l","l","o"]
Output: ["o","l","l","e","h"]
Example 2:

Input: ["H","a","n","n","a","h"]
Output: ["h","a","n","n","a","H"]

翻转字符数组顺序，用O(1)的空间复杂度
所有的翻转都可以：

s[i] swap s[len-i-1]

```java
class Solution {
    public void reverseString(char[] s) {
        int len = s.length;
        for(int i=0;i<len/2;i++){
            int j=len-i-1;
            int tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
        }
    }
}
```


350. Intersection of Two Arrays II
Easy

Given two arrays, write a function to compute their intersection.

Example 1:

Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2,2]
Example 2:

Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [4,9]
Note:

Each element in the result should appear as many times as it shows in both arrays.
The result can be in any order.
Follow up:

What if the given array is already sorted? How would you optimize your algorithm?
What if nums1's size is small compared to nums2's size? Which algorithm is better?
What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?


给定两个数组，写一个函数来计算它们的交集。

使用hashMap，先遍历一个数组，把出现的数字和次数放入
再遍历另一个，一旦出现一次，数字次数-1

```java
class Solution {
   public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> resList = new ArrayList<>();
        for(int i: nums1){
            map.put(i, map.getOrDefault(i, 0)+1);
        }

        for(int i: nums2){
            if(map.getOrDefault(i,0)>0){
                resList.add(i);
                map.put(i, map.get(i)-1);
            }
        }

        int[] res = new int[resList.size()];
        for(int i = 0;i<resList.size();i++){
            res[i] = resList.get(i);
        }
        return res;
    }
}
```

371. Sum of Two Integers
Easy

Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.

Example 1:

Input: a = 1, b = 2
Output: 3
Example 2:

Input: a = -2, b = 3
Output: 1

计算两个数的和，但是不能用+和-

思路：两个数的加法分为两步，对应位相加和进位。

举个简单的例子：997+24

我们平时计算时是将对应位相加和进位同时计算，其实可以保留下进位，只计算对应位相加，保留进位的位置（值）。接下来，将进位向左移动一位，将上一步的结果与移位后的进位值进行对应位相加，直到没有进位结束。
（过程具体可见：
https://www.cnblogs.com/dyzhao-blog/p/5662891.html
）
 
对于二进制数的而言，对应位相加就可以使用异或（xor）操作，计算进位就可以使用与（and）操作，在下一步进行对应位相加前，对进位数使用移位操作（<<）。

而且由于整数是补码形式存在的，所以正数负数无所谓，加出来还是补码

非常神奇且有意思的做法，但是不知道原理

```java
class Solution {
    public int getSum(int a, int b) {
        while(b!=0){
            int c = a^b;
            b = (a&b)<<1;
            a=c;
        }
        return a;
    }
}
```

378. Kth Smallest Element in a Sorted Matrix
Medium

Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element in the matrix.

Note that it is the kth smallest element in the sorted order, not the kth distinct element.

Example:

matrix = [
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
],
k = 8,

return 13.
Note: 
You may assume k is always valid, 1 ≤ k ≤ n^2.

给一个n x n的矩阵，每一行每一列都是排好序的，找到矩阵中第k小的元素
简单的做法，每一行都维持一个头指针，每次取头指针中最小的值，然后该头指针后移，再次比较

```java
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        //head中每个元素的索引就是它所在的行号，其保存的值就是列号，一开始都从第0列开始
        int[] heads = new int[matrix.length];
        int n = matrix.length;
        //每次都更新这个值，当k为0时就是要求的第k个最小值
        int kthMin = 0;
        while(k>0){

            //当前次最小值的横纵坐标
            int[] minCoordinate = new int[2];
            int min = Integer.MAX_VALUE;
            for(int i=0;i<n;i++){
                if(heads[i]==n) continue; //该行head已到达最右端
                int tmp = matrix[i][heads[i]];
                if(tmp < min){
                    min = matrix[i][heads[i]];
                    minCoordinate[0] = i;
                    minCoordinate[1] = heads[i];
                }
            }
            int curX = minCoordinate[0];
            int curY = minCoordinate[1];

            heads[curX]++;
            kthMin = matrix[curX][curY];
            k--;
        }
        return kthMin;

    }
}
```


380. Insert Delete GetRandom O(1)
Medium

Design a data structure that supports all following operations in average O(1) time.

insert(val): Inserts an item val to the set if not already present.
remove(val): Removes an item val from the set if present.
getRandom: Returns a random element from current set of elements. Each element must have the same probability of being returned.
Example:

// Init an empty set.
RandomizedSet randomSet = new RandomizedSet();

// Inserts 1 to the set. Returns true as 1 was inserted successfully.
randomSet.insert(1);

// Returns false as 2 does not exist in the set.
randomSet.remove(2);

// Inserts 2 to the set, returns true. Set now contains [1,2].
randomSet.insert(2);

// getRandom should return either 1 or 2 randomly.
randomSet.getRandom();

// Removes 1 from the set, returns true. Set now contains [2].
randomSet.remove(1);

// 2 was already in the set, so return false.
randomSet.insert(2);

// Since 2 is the only number in the set, getRandom always return 2.
randomSet.getRandom();

设计一个数据结构，可以以O(1)的时间复杂度完成下列工作：

insert(val):将一个项val插入到集合中(如果还没有)。
remove(val):如果存在，则从集合中删除一个项目val。
getRandom:从当前元素集中返回一个随机元素。每个元素必须具有相同的返回概率。

```java
public class RandomizedSet {
    ArrayList<Integer> nums;
    HashMap<Integer, Integer> locs;
    java.util.Random rand = new java.util.Random();
    /** Initialize your data structure here. */
    public RandomizedSet() {
        nums = new ArrayList<Integer>();
        locs = new HashMap<Integer, Integer>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        boolean contain = locs.containsKey(val);
        if ( contain ) return false;
        locs.put( val, nums.size());
        nums.add(val);
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        boolean contain = locs.containsKey(val);
        if ( ! contain ) return false;
        int loc = locs.get(val);
        if (loc < nums.size() - 1 ) { // not the last one than swap the last one with this val
            //如果该数不是最后一个，则把最后一个数放到该位置上，这样该数就会没有，最后一个数就会多一个
            int lastone = nums.get(nums.size() - 1 );
            nums.set( loc , lastone );
            locs.put(lastone, loc);
        }
        //然后再删除最后一个数（这是效率高的删除方式，但前提是对位置不敏感）
        locs.remove(val);
        nums.remove(nums.size() - 1);
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        return nums.get( rand.nextInt(nums.size()) );
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
```

384. Shuffle an Array
Medium

Shuffle a set of numbers without duplicates.

Example:

// Init an array with set 1, 2, and 3.
int[] nums = {1,2,3};
Solution solution = new Solution(nums);

// Shuffle the array [1,2,3] and return its result. Any permutation of [1,2,3] must equally likely to be returned.
solution.shuffle();

// Resets the array back to its original configuration [1,2,3].
solution.reset();

// Returns the random shuffling of array [1,2,3].
solution.shuffle();

将一个数组洗牌，并返回一个新数组，任何结果返回的概率要一样大
如：把[1,2,3]洗牌成[2,1,3]
reset会把数组恢复成刚进入的样子

如何洗牌？

循环遍历该数组，在每次遍历中产生一个0 ~ length - 1的数，该数代表本次循环要随机交换的位置。
将本次循环当前位置的数和随机位置的数进行交换。


```java
class Solution {
    int[] origin;
    int[] copy;
    java.util.Random rand = new java.util.Random();
    public Solution(int[] nums) {
        origin = new int[nums.length];
        copy = new int[nums.length];
        System.arraycopy(nums, 0,origin,0,nums.length);
        copy = origin.clone();
    }
    
    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return origin;
    }
    
    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        for(int i=0;i<origin.length;i++){
            int randomIndex = rand.nextInt(origin.length);
            int tmp = copy[i];
            copy[i] = copy[randomIndex];
            copy[randomIndex] = tmp;
        }
        return copy;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
```

387. First Unique Character in a String
Easy

Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.

Examples:

s = "leetcode"
return 0.

s = "loveleetcode",
return 2.
Note: You may assume the string contain only lowercase letters.

保存一个数组，表示每个字母出现过的次数，
先遍历字符串保存出现过的次数
再遍历第二遍，当次数为1时即为要找的字母

```java
class Solution {
    public int firstUniqChar(String s) {
        int[] count = new int[26];
        for(int i=0;i<s.length();i++){
            count[s.charAt(i)-'a']++;
        }

        for(int i=0;i<s.length();i++){
            if(count[s.charAt(i)-'a']==1) return i;
        }
        return -1;
    }
}
```

395. Longest Substring with At Least K Repeating Characters
Medium

Find the length of the longest substring T of a given string (consists of lowercase letters only) such that every character in T appears no less than k times.

Example 1:

Input:
s = "aaabb", k = 3

Output:
3

The longest substring is "aaa", as 'a' is repeated 3 times.
Example 2:

Input:
s = "ababbc", k = 2

Output:
5

The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.

找出给定字符串的最长子串的长度，这个子串中每个字母都必须重复至少k次,这里的子串整个是一个整体

思路是分治法。

要找s[i,j]的最大子串，先统计频数，然后遍历一遍频数，找出第一个频数小于k且大于0的字符，然后找出这个字符的位置，接下来的分析很重要，这个字符一定不能出现在任何的子串中，因为i,j是整个的子串，在ij里面频数都没有达到k，那么在ij的任何子串中，这个字符也不可能达到频数k。所以不能有这个字符，那么就在这个位置做一个分治，返回前半部分和后半部分的最大值。

## 分治法的很好体现

```java
class Solution {
    public int longestSubstring(String s, int k) {
        return longestSubstring(0, s.length()-1, s, k);
    }

    int longestSubstring(int start, int end, String s, int k){
        if(start>end) return 0;
        int[] count = new int[26];
        for(int i=start;i<=end;i++){
            count[s.charAt(i)-'a']++;
        }

        for(int i = 0; i < 26; i++){
            if(count[i] > 0 && count[i] < k){
                int pos = s.indexOf((char)(i + 'a'), start);
                return Math.max(longestSubstring(start, pos - 1, s, k), longestSubstring(pos + 1, end,s,k));
            }
        }
        //能走到这说明这个s中的字母重复次数都满足条件
        return end-start+1;
    }
}
```

412. Fizz Buzz
Easy

Write a program that outputs the string representation of numbers from 1 to n.

But for multiples of three it should output “Fizz” instead of the number and for the multiples of five output “Buzz”. For numbers which are multiples of both three and five output “FizzBuzz”.

Example:

n = 15,

Return:
[
    "1",
    "2",
    "Fizz",
    "4",
    "Buzz",
    "Fizz",
    "7",
    "8",
    "Fizz",
    "Buzz",
    "11",
    "Fizz",
    "13",
    "14",
    "FizzBuzz"
]

编写一个程序，输出从1到n的数字的字符串表示形式。

但对于3的倍数，它应该输出“Fizz”而不是数字;对于5的倍数，它应该输出“Buzz”。对于同时是3和5的倍数的数字，输出“FizzBuzz”。

```java
class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> res = new ArrayList<>();
        for(int i=1;i<=n;i++){
            if(i%15==0){
                res.add("FizzBuzz");
            }else if(i%5==0){
                res.add("Buzz");
       
            }else if(i%3==0){
                res.add("Fizz");
            }else {
                res.add(""+i);
            }
        }
        return res;
    }
}
```

454. 4Sum II
Medium

Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l) there are such that A[i] + B[j] + C[k] + D[l] is zero.

To make problem a bit easier, all A, B, C, D have same length of N where 0 ≤ N ≤ 500. All integers are in the range of -2^28 to 2^28 - 1 and the result is guaranteed to be at most 2^31 - 1.

Example:

Input:
A = [ 1, 2]
B = [-2,-1]
C = [-1, 2]
D = [ 0, 2]

Output:
2

Explanation:
The two tuples are:
1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0

计算4个数组中各取一个数得到和为0的取法总数
直接用O(n^4)做会超时

好做法是先算A和B的各个和值，保存起来，再去算C和D的和值

需要注意的是，重复的组合也算，比如A中有两个1，那么计算次数的时候1算两次的
（那个加不同符号和为指定数的题：top100liked-494）

```java
//下面是最简单的做法：
class Solution {
    HashMap<Integer, Integer> map1 = new HashMap<>(); 
    HashMap<Integer, Integer> map2 = new HashMap<>(); 

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        for(int i=0;i<C.length;i++){
            for(int j=0;j<D.length;j++){
                int sum = C[i]+ D[j];
                map1.put(sum, map1.getOrDefault(sum, 0)+1);
            }
        }

        int res = 0;
        for(int i=0;i<A.length;i++){
            for(int j=0;j<B.length;j++){
                int sum = A[i]+ B[j];
                res += map1.getOrDefault(-1 * sum, 0);
            }
        }
        return res;        
    }
}
```
## 完结撒花  ჰჰჰ❛‿❛ჴჴჴ  2019-6-3







































































































































































































































































































































































































































































































































































































































































































































































































































