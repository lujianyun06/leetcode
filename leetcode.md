1. Two Sum
Share
Given an array of integers, return indices of the two numbers such that they add up to a specific target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

Example:

Given nums = [2, 7, 11, 15], target = 9,

Because nums[0] + nums[1] = 2 + 7 = 9,
return [0, 1].

```java
//相比于双重遍历，这个复杂度是O(n)，只需要遍历一遍即可完成
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int size = nums.length;
        
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<size;i++){
            int num = target-nums[i];
            if(map.containsKey(num)){
                return new int[]{map.get(num), i};
            }
            map.put(nums[i], i);
        }
        return null;
     
    }
}
```
* * *

2. Add Two Numbers
Medium

You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example:

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
Explanation: 342 + 465 = 807.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
1->2->3
//加法进位只能进1！！！
//不需要知道最后的和是多少，只需要把每次加后的结果+进位知道即可，即不需要知道10的次数
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        //添加头结点，更好操作
        ListNode head1 = new ListNode(0);
        ListNode head2 = new ListNode(0);
        head1.next = l1;
        head2.next = l2;
        l1 = head1;
        l2 = head2; 


        ListNode newHead = new ListNode(0);
        ListNode node = newHead;
        int c = 0; //进位
        do {
            l1 = getNextNode(l1);
            l2 = getNextNode(l2);

            double temp = l1.val + l2.val + c;
            if (temp >= 10) {
                c = 1;
            }else{
                c = 0;
            }

            int newNodeVal = (int)temp % 10; //本次新链表节点

            node.next = new ListNode(newNodeVal);
            node = node.next;

        } while(!isNoNext(l1, l2));  //注意这里是next至少有一个不空时才继续

        //最终还有进位
        if (c==1) { 
            node.next = new ListNode(1);
        }

        newHead = newHead.next;
        return newHead;
    }


    boolean isNoNext(ListNode l1, ListNode l2){
        return (l1.next==null && l2.next==null)?true:false;
    }


    public ListNode getNextNode(ListNode node){
        if (node.next==null) {
            return new ListNode(0);
        }
        return node.next;
    }
}
```

## 两数做加法进位只能进1！！！

* * *


3. Longest Substring Without Repeating Characters
Medium

Given a string, find the length of the longest substring without repeating characters.

Example 1:

Input: "abcabcbb"
Output: 3 
Explanation: The answer is "abc", with the length of 3. 
Example 2:

Input: "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
Example 3:

Input: "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3. 
             Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
没有重复字母的子字符串
往后推移发现子串，如果有重复的字母，则从第一个重复字母的下一个重新开始计算
如：pwwkew
p，pw，（pww从第一个w的后一个字母重新开始）：w，wk，wke，（wkew，从第一个w的后一个字母重新开始）：kew
abcabcbb
a，ab，abc，bca，cab，b，b
取第一个最长的wke
```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        String subs = "";
        int longestLenght = 0;
        
        int len = s.length();
        for (int i=0; i<len;i++ ) {
            char c = s.charAt(i);

            int index = subs.indexOf(c);
            if(index > -1){  //包含该字母,则将子串更新为从该字母后的位置开始

                if (index == subs.length()-1) {  //该字母是最后一个
                    subs = "";
                }else{
                    subs = subs.substring(index + 1);
                }

            }

            subs += String.valueOf(c); //增加新值
            int tmpLen = subs.length();

            if (tmpLen > longestLenght) { //如果新子串大于当前最大子串，则更新最大子串
                longestLenght = tmpLen;
            }

        }
        return longestLenght;
    }
}
/*****************/
String.valueOf(97) 不是a，而是“97”，必须先把数字转成字符，即 String.valueOf((char)97);
核心在于 string.indexOf(c) 返回某个字符第一次在字符串中出现的位置，如果没有则返回-1;
```

4. Median of Two Sorted Arrays
Hard

There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

You may assume nums1 and nums2 cannot be both empty.

Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0
Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5

>难度在于怎么把时间复杂度控制在log(m+n),一般只要出了log的复杂度，那就是二分查找这种类型的。

把A和B分别分成两个集合（A长m，B长n），A1,A2,B1,B2；,A1中的元素都小于A2中的元素；B1中的元素都小于B2中的元素，要把它们合起来，变成A1+B1 和A2+B2,前面集合中的元素必须都小等于后面集合的元素。（即A[i-1]<B[j] 且 A[i]>B[j-1]）
分成两个大集合
           left_part          |        right_part
    A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
    B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
如果A1中元素有i个，则A2中有m-i个，同理，B1中有j个，则B2中有n-j个
要取A+B的中位数，则：
#1。如果A+B的长度为奇数，则要使A1+B1的长度 比 A2+B2的多1，中位数就是多出来的那个数。
#2。如果A+B长度是偶数，则要使A1+B1的长度 == A2+B2的长度，中位数=（前面集合中的最大数+后面集合中的最小数）/2
i和j需满足的条件是：i+j=m-i+n-j（或m-i+n-j+1（当n+m为奇数））则 j=（m+n）/2-i 或（m+n+1）/2 -i （为了方便起见，由于若m+n是偶数时，(int)(m+n+1)/2 == (int)(m+n) /2）
所以统一写为 j=(m+n+1)/2-i。

i的范围是0~m时（i是要进行搜寻的变量，所以以他的范围为标准来界定j的范围），则为了让j满足 0~n，则需要使 n>=m,因为：
m≤n,i<m⟹j=(m+n+1)/2−i>(m+n+1)/2−m≥(2m+1)/2−m≥0
m≤n,i>0⟹j=(m+n+1)/2−i<(m+n+1)/2<=(2n+1)/2−m<=n
如果n<<m，而i要是取个m-1，j=(m+n+1)/2-(m-1)=(n-m+3)/2如果m远大于n，则这个值可能是负值，那就出错了。（所以要选取短的那个数组做A）

所以要做的事情就变成了在A中搜一个下标i，使得搜出的i满足上述条件。
搜i的方法要使用二分查找，只有二分查找能达到log的时间复杂度
一开始限定i的范围为 imin=0，imax=m，则i= （imin+imax）/2
搜i的过程中可能会遇到以下情况：
* A[i-1]<B[j] 且 A[i]>B[j-1] 符合条件，退出搜索
* A[i-1]>B[j]（A[i]>B[j-1] 一定成立）说明i太靠后了，修改imax的限定范围为  i-1,  则      i=（imin+i-1）/2，继续搜索。
* A[i]<B[j-1]（A[i-1]<B[j] 一定成立）说明i太靠前了，修改imin的限定范围为  i+1  ,则i=（i+1+imax）/2，继续搜索。

当遇到边界情况
    当i=0时，说明A中所有元素都在右边，B不可能都在右边
    当j=0时， 说明B中所有元素都在右边，A不可能都在右边
    当i=m时，说明A中元素都在左边，B不可能都在左边
    当j=n时，说明B中元素都在左边 ，A不可能都在左边


如果借鉴求流中中位数的方法，使用两个优先级队列可以很方便地求出中位数，但由于优先级队列的插入复杂度是logn,(优先级队列的底层实现是堆)，
所以总的复杂度会成为(m+n)log(m+n),因此仍然不满足时间条件


```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int m, n;
        int[] a, b; //让a短b长, 且m<=n
        if (len1 < len2) {
            a = nums1;
            b = nums2;
            m = len1;
            n = len2;
        } else {
            a = nums2;
            b = nums1;
            m = len2;
            n = len1;
        }
        boolean even = (m + n) % 2 == 0; //偶数长度
        int imin = 0;
        int imax = m;
        int i;
        int j = 0;
        while (imax >= imin) {
            i = (imin + imax) / 2;
            j = (m + n + 1) / 2 - i;  //确保两边长度一样，或只多1,由这个式子可知，不可能i和j同时是同一边的边界。
            //j取0时，i一定为m；但反过来不一定成立
            //j取n时，i一定是0；但反过来不一定成立
            if (i > imin && a[i - 1] > b[j]) {
                imax = i-1;
                continue;
            } else if (i < imax && a[i] < b[j - 1]) {
                imin = i+1;
                continue;
            } else {  //满足条件时，分别有7种可能性，其中还有几种边界条件，画图的话很容易看出来，但提取共同特征，就如下：
                /*
               A --|--
               B --|--

                A--|
                   |-- B

                A  |--
                 --|  B

                A --|
                B --|--

                 A   |--
                 B --|--

                 A --|--
                 B   |--

                 A --|--
                 B --|
                 */

                int leftMax = 0;
                if (i == 0) leftMax = b[j - 1];
                else if (j == 0) leftMax = a[i - 1];
                else {
                    leftMax = Math.max(a[i - 1], b[j - 1]);
                }
                if (!even) return (double) leftMax;

                int rightMin = 0;
                if (i == m) rightMin = b[j];
                else if (j == n) rightMin = a[i];
                else {
                    rightMin = Math.min(a[i], b[j]);
                }
                return (leftMax + rightMin) / 2.0;

            }

        }
        return 0.0;
    }
}
```


5. Longest Palindromic Substring
Medium

Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

Example 1:

Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.
Example 2:

Input: "cbbd"
Output: "bb"

以每个字母为中心点，
如果是奇数长度，回文，向两边拓展，左右分别是 left=i-1和right=i+1,   left--, right++
如果是偶数回文，左右分别是 i, i+1,回文一般用这种方法比较好判断

```java
/*
1+2+3.。+1000 = 1000 * 1000 /2 = 
: b ba bab baba babad；a ab aba abad；b ba bad; a ad;  d
》耗时解法，暴力枚举，这种方法一旦字符串长就会超时了
*/
class Solution {

    public String longestPalindrome(String s) {
        for(int i=0;i<s.length();i++){
            checkPalindrome(s, i, i+1, false, null);
            checkPalindrome(s, i-1, i+1, true, s.indexOf(i));
        }
        return ans;
    }

    int max = 0;
    String ans = "";
    checkPalindrome(String s, int left, int right, boolean even, char mid){
        String p = even?""+mid:"";
        while(left>=0 && right <= s.length() && s.indexOf(left)==s.indexOf(right)){
            p = s.indexOf(left) + p + s.indexOf(right);
            right++;
            left--;
        }
        if(p.length() > max) ans = p;
    }
}
```


6. ZigZag Conversion
Medium

The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)

P   A   H   N
A P L S I I G
Y   I   R
And then read line by line: "PAHNAPLSIIGYIR"

Write the code that will take a string and make this conversion given a number of rows:

string convert(string s, int numRows);
Example 1:

Input: s = "PAYPALISHIRING", numRows = 3
Output: "PAHNAPLSIIGYIR"
Example 2:

Input: s = "PAYPALISHIRING", numRows = 4
Output: "PINALSIGYAHRPI"
Explanation:

P     I    N
A   L S  I G
Y A   H R
P     I
字母走势都是从头到底，再从底到头这样，看成是电梯，那么只需要建n个字符串，每个字符串代表一层，每当到一层时，把该层的字母加入该层的字符串，最后把所有字符串连起来就可以了


```java
class Solution {
    public String convert(String s, int numRows) {
        if (numRows==1) return s;

        //每一行都用一个stringbuilder来记录，这是关键所在
        StringBuilder[] listArray = new StringBuilder[numRows];
        for (int i=0;i<numRows;i++){
            listArray[i] = new StringBuilder();
        }

        int len = s.length();
        int direction = 0; //0代表下行，1代表上行,直接不要管之字，就按电梯来看
        for (int i=0, index=0; i< len; i++) {

            char c = s.charAt(i);
            if (direction == 0) {
                boolean end = index>=(numRows-1); //是否走到尽头
                if (end) {
                    direction = 1;
                }
                listArray[index].append(c);
                index = end ? (index-1):(index+1);
            } else{
                boolean top = index<=0;
                if (top) {
                    direction = 0;
                }
                listArray[index].append(c);
                index = top? index+1:index-1;
            }
        }
        StringBuilder result = new StringBuilder();
        for(int i=0;i<numRows;i++){
            result.append(listArray[i].toString());
        }
        return result.toString();
    }
}
```

7. Reverse Integer

Given a 32-bit signed integer, reverse digits of an integer.

Example 1:

Input: 123
Output: 321
Example 2:

Input: -123
Output: -321
Example 3:

Input: 120
Output: 21
Note:
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−2^31,  2^31 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.

这里算是有点投机取巧了，其实C++也可以这么干，先把数转成字符串存起来，然后翻转，把翻转后的字符串改成精度更高的类型，判断翻转后是不是溢出，溢出就返回0，否则返回原值，用字符串转数字也不用考虑首0了，有点投机取巧的意思，如果判断首0的话，对翻转后的字符串从前往后检查，找到第一个不是0的值，把后面的复制到另一个字符串中，再一个字符一个字符转成数字。
如果纯数字考虑的话，就是取余得出每一位数字，然后翻转，然后再判断是否溢出
```java
class Solution {
    public int reverse(int x) {

        boolean isN = x <= 0;
        String s = String.valueOf(Math.abs((long)x));
        StringBuilder builder = new StringBuilder(s);
        builder.reverse();
        s = builder.toString();
        long result = Long.valueOf(s);
        if (Math.abs(result) > Integer.MAX_VALUE && !isN){
            return 0;
        } else if(Math.abs(result) > ((long) Integer.MAX_VALUE+1) && isN){
            return 0;
        } else {
            int symbol = isN?-1:1;
            return (int) (result * symbol);
        }
    }
}

/可以用异常处理来处理溢出的异常，如果有异常直接返回0，否则返回没有异常的值
class Solution {
    public int reverse(int x) {

        boolean isN = x <= 0;
        String s = String.valueOf(Math.abs((long)x));
        StringBuilder builder = new StringBuilder(s);
        builder.reverse();
        s = builder.toString();
        try {
            int result = Integer.valueOf(s);
            if (isN) result = -result;
            return result;
        }catch (Exception e){
            return 0;
        }
    }
}
```

8. String to Integer (atoi)

Implement atoi which converts a string to an integer.

The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.

The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.

If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.

If no valid conversion could be performed, a zero value is returned.

Note:

Only the space character ' ' is considered as whitespace character.
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. If the numerical value is out of the range of representable values, INT_MAX (231 − 1) or INT_MIN (−231) is returned.
Example 1:

Input: "42"
Output: 42
Example 2:

Input: "   -42"
Output: -42
Explanation: The first non-whitespace character is '-', which is the minus sign.
             Then take as many numerical digits as possible, which gets 42.
Example 3:

Input: "4193 with words"
Output: 4193
Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.
Example 4:

Input: "words and 987"
Output: 0
Explanation: The first non-whitespace character is 'w', which is not a numerical 
             digit or a +/- sign. Therefore no valid conversion could be performed.
Example 5:

Input: "-91283472332"
Output: -2147483648
Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
             Thefore INT_MIN (−231) is returned.

字符串转整数

```java
class Solution {
    public int myAtoi(String str) {
        //去掉空格
        str = str.trim();
        if(str.length()==0) return 0;
        char first = str.charAt(0);
        //第一个字符如果不是数字或者符合，说明不合理
        LinkedList<Integer> queue = new LinkedList<>();
        if ((first <= '9' && first >= '0') || first == '+' || first == '-') {
            int flag = 1;
            for (int i = 1; i < str.length(); i++) {
                if (str.charAt(i) > '9' || str.charAt(i) < '0') break;
                else {
                    queue.add(str.charAt(i) - '0');
                }
            }
            //如果能到这里，说明有合法的数字
            int initNum = -1;
            if (first <= '9' && first >= '0') initNum = first - '0';
            else {
                flag = first == '-' ? -1 : 1;
            }
            //如果第一个不是数字，且从第二个开始不是数字
            if (initNum < 0 && queue.size() == 0) return 0;
            int ans = getNum(queue, initNum < 0 ? 0 : initNum, flag);
            return ans;

        } else {
            return 0;
        }
    }

    int getNum(LinkedList<Integer> queue, int initNum, int flag) {
        int ans = initNum * flag;
        int tmp = ans;
        while (!queue.isEmpty()) {
            tmp = ans * 10 + queue.poll() * flag;
            //这里是判断溢出的关键所在，当队列里还有数字，但当前数字已经超过上限的1/10时，说明一定会溢出
            //若当前数字等于上限的1/10时，则要判断队列中的数字，也就是即将检查结果是否会等于或小于上限
            if(flag > 0 &&
                    ((tmp > Integer.MAX_VALUE / 10 && !queue.isEmpty()) ||
                    (tmp == Integer.MAX_VALUE / 10 && !queue.isEmpty() && queue.peek()>7))){
                ans = Integer.MAX_VALUE;
                break;
            }
            else if(flag < 0 &&
                    ((tmp < Integer.MIN_VALUE / 10 && !queue.isEmpty()) ||
                            (tmp == Integer.MIN_VALUE / 10 && !queue.isEmpty() && queue.peek()>8))){
                ans = Integer.MIN_VALUE;
                break;
            }
            ans = tmp;
        }
        return ans;
    }


}
```



9. Palindrome Number

Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.

Example 1:

Input: 121
Output: true
Example 2:

Input: -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
Example 3:

Input: 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
Follow up:

正着和反着一样就是回文

Coud you solve it without converting the integer to a string?
```java
class Solution {

    public boolean isPalindrome(int x) {
        final int origin = x;
        if(x<0) return false; //负数一定不是回文
        ArrayList<Integer> array = new ArrayList<>(); //保存每个位的数字
        do{
            int tmp = x % 10;
            array.add(tmp);
            x = x / 10;
        }while(x>0);
        //再把这个数字翻转成int
        int reversed = array.get(0);
        int len = array.size();
        for(int i=1;i<len;i++){
            reversed = 10*reversed + array.get(i);
        }
        return reversed==origin;
    }

}
```

10. Regular Expression Matching
Hard

Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
'.' Matches any single character.
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).

Note:

s could be empty and contains only lowercase letters a-z.
p could be empty and contains only lowercase letters a-z, and characters like . or *.
Example 1:
Input:
s = "aa"
p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:
Input:
s = "aa"
p = "a*"
Output: true
Explanation: '*' means zero or more of the precedeng element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
Example 3:
Input:
s = "ab"
p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
Example 4:

Input:
s = "aab"
p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore it matches "aab".
Example 5:

Input:
s = "mississippi"
p = "mis*is*p*."  
Output: false

递归来判断
当第一个字符匹配且 且第二个字符是*时，要么就从*后p和原t匹配（第一个字符没重复了），要么p和t从第二个字符后匹配（第一个字符还有重复）

当第一个字符不匹配时，如果第二个字符为*，则p从*后与原t匹配。
如aab和c*a*b
c与a不匹配，比较 aab与 a*b; a与a匹配，比较 (ab与 a*b) || (aab与b) 

首字母不匹配时，必须是因为匹配串后面有*，可以使得匹配串可以跳过这个字母和原串重新进行匹配，否则不匹配。
首字母匹配时，匹配串后面如果有*，则既可以跳过首字母和*和原串重新进行匹配，也可以让原串减去首字母和匹配串再进行匹配,两者只要有一个成立即可（当然不可能两个都成立）


```java
class Solution {
    //下面方法的那种版本更易理解
    public boolean isMatch(String text, String pattern) {
        if (pattern.isEmpty()) return text.isEmpty();
        //首字母是否匹配
        boolean first_match = (!text.isEmpty() &&
                               (pattern.charAt(0) == text.charAt(0) || pattern.charAt(0) == '.'));

        //匹配串长度大于2，第二个字符是*
        //如果首字母匹配，则原串往后挪一位，匹配串不变再比较，这样的结果是最终*号就会被消除
        //或者直接忽略 x* 的情况，再比较剩下的
        if (pattern.length() >= 2 && pattern.charAt(1) == '*'){
            return (isMatch(text, pattern.substring(2)) ||
                    (first_match && isMatch(text.substring(1), pattern)));
        } else { //匹配串长度只为1或者第二个字符不是'*'，则只能逐字母继续比较后面的匹配串和原串
            return first_match && isMatch(text.substring(1), pattern.substring(1));
        }
    }


    //上面的是这个的简化版，比这个快，但看这个更容易理解
     public boolean isMatch(String text, String pattern) {
        if (pattern.isEmpty()) return text.isEmpty();
        //首字母是否匹配
        boolean first_match = (!text.isEmpty() &&
                (pattern.charAt(0) == text.charAt(0) || pattern.charAt(0) == '.'));

        //如果首字母不匹配，匹配串且第二个字符是*，则直接跳过 first*,从下一个开始匹配
        if (!first_match) {
            if(pattern.length() >= 2 && pattern.charAt(1) == '*') return isMatch(text, pattern.substring(2));
            else return false;

        } else {
            //如果首字母匹配，则：
            //如果第二个字符是*，
            //1. 则匹配串可以匹配更多该字母，则可原串往后挪一位，匹配串不变进行匹配：如： aab  a*b:  a*既与第1个a匹配，又与第2个a匹配
            //2. 原串后面再没有 aaa a*a  下面的两种情况都不能省略一种，例如，对于aaa a*a 
            //如果只考虑case 1：即原串挪，匹配串不挪 就变成了：aaa a*a, aa a*a, a a*a  不匹配
            //如果只考虑case 2：原串不挪，匹配串挪，那肯定是不对的，原串永远不挪了，进行不下去了

            //如果第二个字符串不是*，那么原串往后挪1位，匹配串往后挪一位，继续匹配
            if(pattern.length() >= 2 && pattern.charAt(1) == '*'){
                boolean case1 = isMatch(text.substring(1), pattern);
                boolean case2 = isMatch(text, pattern.substring(2));
                return case1 || case2;
            } else{
                return isMatch(text.substring(1), pattern.substring(1));
            }

        }
    }
}
```
*


11. Container With Most Water
Medium

Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.

Note: You may not slant the container and n is at least 2.

给定n个非负整数a1，a2，...，an，其中每个表示坐标（i，ai）处的点。 绘制n条垂直线，使得线i的两个端点位于（i，ai）和（i，0）。 找到两条线，它们与x轴一起形成一个容器，这样容器就含有最多的水。

一个数组中 x = 两数最小值 * 两数间隔;  就是找出x最大的值

```java
//暴力遍历法，当然是能成功的，但是效率不高
class Solution {
    public int maxArea(int[] height) {
        int len = height.length;
        int maxArea = 0;

        for(int i=0;i<len;i++){
            for(int j=i+1;j<len;j++){
                int area = Math.min(height[i], height[j]) * (j-i);
                maxArea = maxArea>area?maxArea:area;
            }
        }
        return maxArea;
    }
}

/*
双指针法
这种方法背后的直觉是，直线之间形成的面积总是受到较短直线高度的限制。直线越远，得到的面积就越大。

我们取两个指针，一个在数组的开头，一个在数组的末尾，组成了行长度。然后，我们维护一个变量maxareamaxarea来存储到目前为止获得的最大面积。在每一步中，我们找出它们之间形成的区域，更新maxareamaxarea，并将指针指向较短的线的另一端移动一步。

当长度减小时，要使得面积大，只能是高度增加，所以应该保留高的，让矮的前进，例如
高的和矮的后面都是一样的，那么挪高不挪低的面积一定小于挪低不挪高

原理是:在长度减小的情况下，尽量保持可用的高度最高，这样面积就能尽可能地大
*/
class Solution {
    public int maxArea(int[] height) {
        int len = height.length;
        int maxArea = 0;
        for(int i=0,j=len-1;i<j && i < len && j >= 0;){
            boolean jMove = false;  //
            boolean iMove = false;
            if(height[j] > height[i]) iMove = true;
            else jMove = true;
            int area = Math.min(height[j],height[i]) * (j-i);
            maxArea = maxArea>area?maxArea:area;
            if (iMove) i++;
            else j--;
        }
        return maxArea;
    }
}
```

12. Integer to Roman
Medium

Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9. 
X can be placed before L (50) and C (100) to make 40 and 90. 
C can be placed before D (500) and M (1000) to make 400 and 900.
Given an integer, convert it to a roman numeral. Input is guaranteed to be within the range from 1 to 3999.

Example 1:

Input: 3
Output: "III"
Example 2:

Input: 4
Output: "IV"
Example 3:

Input: 9
Output: "IX"
Example 4:

Input: 58
Output: "LVIII"
Explanation: L = 50, V = 5, III = 3.
Example 5:

Input: 1994
Output: "MCMXCIV"
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.

罗马数字
这个题看着复杂其实很简单，只要把对应位的数字表示出来，然后从高位到低位排列出来就行了，和阿拉伯数字是一样的，
只需要把各位可能出现的符号先存起来然后直接按照对应的数字取就行了
```java
//范围只是1~3999
class Solution {

    public String intToRoman(int num) {
        String[] KILO = new String[]{"", "M", "MM","MMM"};
        String[] HUND = new String[]{"", "C", "CC","CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] DECA = new String[]{"", "X", "XX","XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] UNIT = new String[]{"", "I", "II","III", "IV", "V", "VI", "VII", "VIII", "IX"};
        int K=1000; int H = 100; int D = 10; int U = 1;
        int kp = num / K;
        num = num % K;
        int hp = num / H;
        num = num % H;
        int dp = num / D;
        int up = num % D;
        return KILO[kp] + HUND[hp] + DECA[dp] + UNIT[up];
    }
}
```

13. Roman to Integer

Medium

Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9. 
X can be placed before L (50) and C (100) to make 40 and 90. 
C can be placed before D (500) and M (1000) to make 400 and 900.
Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.

Example 1:

Input: "III"
Output: 3
Example 2:

Input: "IV"
Output: 4
Example 3:

Input: "IX"
Output: 9
Example 4:

Input: "LVIII"
Output: 58
Explanation: L = 50, V= 5, III = 3.
Example 5:

Input: "MCMXCIV"
Output: 1994
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.

罗马子转阿拉伯字，这个就更简单了，

```java
class Solution {
    public int romanToInt(String s) {

        String[][] unit = new String[][]{{"", "M", "MM","MMM"},
                {"", "C", "CC","CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
                {"", "X", "XX","XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
                {"", "I", "II","III", "IV", "V", "VI", "VII", "VIII", "IX"}};
        int[] c = new int[]{1000,100,10,1};

        int result = 0;
        int startPoint=0;
        for(int i=0;i<4;i++){
            int len = unit[i].length;
            for(int j=len-1;j>0;j--){
                int unitLen = unit[i][j].length();
                try{    //这里用捕获异常处理如果子串长度不合适了，说明不匹配，直接跳到下一个匹配串
                    if(s.substring(startPoint, startPoint+unitLen).equals(unit[i][j])){
                        result += j * c[i];
                        startPoint += unitLen; //说明该位上匹配完了，该到下一位上匹配了
                        break;
                    }
                } catch (Exception e){
                    continue;
                }

            }
        }
        return result;
    }
}
```

14. Longest Common Prefix

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string "".

Example 1:

Input: ["flower","flow","flight"]
Output: "fl"
Example 2:

Input: ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
Note:

All given inputs are in lowercase letters a-z.
找公共最长前缀

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        int num = strs.length;
        if (num==0) return "";
        String pattern = strs[0];
        //把匹配串换成其中最短的串
        for(int i=1;i<num;i++){
            if (strs[i].length() < pattern.length()) pattern = strs[i];
        }

        StringBuilder prefix = new StringBuilder();

        int minLen = pattern.length();
        //匹配串的逐字母与每个串的对应位置的字母匹配，只要不匹配截止
        for(int i=0;i<minLen;i++){
            boolean isMatch = true;
            char ch = pattern.charAt(i);
            for(int j=0;j<num;j++){
                isMatch = isMatch && (ch == strs[j].charAt(i));
                if (!isMatch) break;
            }
            if(isMatch) prefix.append(ch);
            else break;
        }
        return prefix.toString();

    }
}
```

15. 3Sum
Medium

Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note:

The solution set must not contain duplicate triplets.

Example:

Given array nums = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]

牢记多指针法，双指针，三指针之类的，多指针法对于一些题可以将时间复杂度降低一个量级，所以一定要考虑它。

先对数组排序，然后对每个数字作为中心向两边，一个左指针一个右指针，向两边走（这个和找回文的有点相似）
或者是从两边开始往中间靠拢（下面是靠拢的方法）

```java
class Solution {
   public List<List<Integer>> threeSum(int[] nums) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i : nums) {
            array.add(i);
        }
        Collections.sort(array);
        ArrayList<List<Integer>> ans = new ArrayList<>();

        int len = nums.length;
        if (len < 3) return ans;
        for(int i=0;i<len-1;i++){
            int left = i+1;
            int right = len-1;
            if(i > 0 && array.get(i).equals(array.get(i - 1))) continue;

            while(right > left){
                int sum = array.get(left) + array.get(i) + array.get(right);
                if(sum==0) {
                    ans.add(Arrays.asList(array.get(i) , array.get(left) , array.get(right)));
                    //处理重复的数字，就是直接跳过它
                    while(left+1 < len && array.get(left).equals(array.get(left + 1))) left++;
                    while(right-1 >= 0 && array.get(right).equals(array.get(right - 1))) right--;
                    left++;
                    right--;
                } else if(sum >0){
                    right--;
                } else if (sum < 0){
                    left++;
                }
            }

        }
        return ans;
    }



}
```

16. 3Sum Closest
Medium

Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.

Example:

Given array nums = [-1, 2, 1, -4], and target = 1.

The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).

和上面的类似，关键是用双指针法
```java
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i : nums) {
            array.add(i);
        }
        Collections.sort(array);
        int len = nums.length;
        if (len < 3) return 0;

        //一定要用真实值初始化这个变量，否则会出错
        int closetTarget = array.get(0) + array.get(1) + array.get(2);
        for (int i = 0; i < len - 1; i++) {
            boolean findTarget = false;
            int left = i + 1;
            int right = len - 1;
            if (i > 0 && array.get(i).equals(array.get(i - 1))) continue;

            while (right > left) {
                int sum = array.get(left) + array.get(i) + array.get(right);
                int sub = sum - target;
                if (Math.abs(sub) < Math.abs(closetTarget - target)) closetTarget = sum;

                if (sub == 0) {
                    //如果等的话，就说明target就是答案，也就不用再找了
                    findTarget = true;
                    break;
                } else if (sub > 0) {
                    right--;
                } else if (sub < 0) {
                    left++;
                }
            }
            if(findTarget) break;
        }
        return closetTarget;
    }

    //从中间往两边走
        public int threeSumClosest(int[] nums, int target) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i : nums) {
            array.add(i);
        }
        Collections.sort(array);
        int len = nums.length;
        if (len < 3) return 0;

        //一定要用真实值初始化这个变量，否则会出错
        int closetTarget = array.get(0) + array.get(1) + array.get(2);
        for (int i = 1; i < len - 1; i++) {
            boolean findTarget = false;
            int left = i - 1;
            int right = i+1;

            while (right < nums.length && left >= 0) {
                int sum = array.get(left) + array.get(i) + array.get(right);
                int sub = sum - target;
                if (Math.abs(sub) < Math.abs(closetTarget - target)) closetTarget = sum;

                if (sub == 0) {
                    findTarget = true;
                    break;
                } else if (sub > 0) {
                    left--;
                } else if (sub < 0) {
                    right++;
                }
            }
            if(findTarget) break;

        }
        return closetTarget;
    }

}
```

17. Letter Combinations of a Phone Number
Medium

Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.

A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

Example:

Input: "23"
Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].

第一个击败100%的人，记录一下，开心

原理很简单，就是用递归，一层一层往下递归，把每个字符的映射先保存起来，就很好弄。

这个题最容易理解的方法是回退递归

```java
class Solution {
    int len  = 0;
    public List<String> letterCombinations(String digits) {
        len = digits.length();
        if (len==0) return new ArrayList<String>();
        return append(digits, 0);
    }

    public ArrayList<String> append(String digits, int index){
        int num = (int)(digits.charAt(index)-'0');
        ArrayList<String> array = new ArrayList<>();
        if(index == len-1){
            return new ArrayList<String>(Arrays.asList(maps[num]));
        }

        ArrayList<String> child = append(digits, index+1);
        int childLen = child.size();

        for(int i=0;i<maps[num].length;i++){
            for (int j=0; j<childLen; j++) {
                array.add(maps[num][i] + child.get(j));
            }
        }
        return array;


    }

    String[][] maps = new String[][]{
            {},{},
            {"a", "b", "c"},
            {"d", "e", "f"},
            {"g", "h", "i"},
            {"j", "k", "l"},
            {"m", "n", "o"},
            {"p", "q", "r", "s"},
            {"t", "u", "v"},
            {"w", "x", "y", "z"},
    };
}
```

18. 4Sum
Medium

Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.

Note:

The solution set must not contain duplicate quadruplets.

Example:

Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.

A solution set is:
[
  [-1,  0, 0, 1],
  [-2, -1, 1, 2],
  [-2,  0, 0, 2]
]

数组可以直接用Arrays.sort排序；List可以用Collections.sort排序

这个题有两个难点：一是求和，二是里面边界条件判断错综复杂，少一个就会出错。

思想：求4个和，则遍历数组，让求当前数后面的数组中的3个和
求3个和，则遍历数组，让求当前数后面的数组中的1个和当前数的和。其实也是递归的思想，只不过递归较复杂。

这种多数求指定和的思想很重要，不仅是求目标值，也可以求与目标值最近的和，
上面3个数求和的双指针法到这里就不起作用了，当计算多重运算的时候，如求4数之和，5数之和等等，要思考递归。

## 这个题的下面这种做法很值得借鉴，思路明确，容易复用代码

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        ArrayList<List<Integer>> ans = new ArrayList<>();
        int len = nums.length;
        if (len < 4) return ans;
        Arrays.sort(nums);
        if (nums[len - 1] * 4 < target || nums[0] * 4 > target) //边界数字都无法满足条件
            return ans;


        for (int i = 0; i < len; i++) {
            int cur = nums[i];
            if ((i - 1) >= 0 && nums[i] == nums[i - 1])
                continue; //该数字和前数字一样
            if ((i + 3) <= (len - 1) && nums[i + 3] == nums[i] && 4 * cur == target) //连着4个一样的数字，其和为目标值
                ans.add(Arrays.asList(cur, cur, cur, cur));
            if (4 * cur > target) //4倍的该数字比目标大，往后的数字会更大，所以就直接跳出了
                break;
            if (4 * cur < target)
                doThreeSum(nums, target - cur, i + 1, len, cur, ans);
        }

        return ans;
    }

    //因为是顺序遍历的，所以当前数字前面的数字就不用管了，要是出现早就出现了
    public void doThreeSum(int[] nums, int target, int low, int high, int z1, List<List<Integer>> ans) {
//        ArrayList<List<Integer>> ans = new ArrayList<>();
        int len = high - low;
        if(len < 2) return;
        if (nums[high - 1] * 3 < target || nums[0] * 3 > target) //边界数字都无法满足条件
            return;


        for (int i = low; i < high; i++) {
            int cur = nums[i];
            if ((i - 1) >= low && nums[i] == nums[i - 1])
                continue; //该数字和前数字一样
            if ((i + 2) <= (high - 1) && nums[i + 2] == nums[i] && 3*cur==target) //连着3个一样的数字，其和为目标值
                ans.add(Arrays.asList(z1, cur, cur, cur));
            if (3 * cur > target) //3倍的该数字比目标大，往后的数字会更大，所以就直接跳出了
                break;
            if (3 * cur < target)
                doTwoSum(nums, target - cur, i + 1, high, z1, cur, ans);
        }

    }

    /*
    @low inclusive
    @high exclusive
    */
    public void doTwoSum(int[] nums, int target, int low, int high, int z1, int z2, List<List<Integer>> ans) {
        int len = high - low;
        if (len < 2) return;

        for (int i = low; i < high; i++) {

            int cur = nums[i];
            //出现了和前面相同的重复数字，那么这个数字的情况已经被前面的数字所处理过了，直接跳过
            if (i > low && nums[i - 1] == nums[i]) continue; 

            for (int left = i + 1; left < high;left++) {
                //left大于i+1时还是重复，就说明left为i+1时是重复数字，所以后面的重复数字可以直接跳过去了
                if(left > i+1 && nums[left] == nums[left-1]) continue;
                int sub = target - nums[i] - nums[left];
                if (sub > 0) continue;
                else if (sub < 0) break;
                else { //sub=0;
                    ans.add(Arrays.asList(z1, z2, nums[i], nums[left]));
                    continue;
                }
            }
        }

    }
}
```

19. Remove Nth Node From End of List
Medium

Given a linked list, remove the n-th node from the end of list and return its head.

Example:

Given linked list: 1->2->3->4->5, and n = 2.

After removing the second node from the end, the linked list becomes 1->2->3->5.

```java
class Solution {
   public ListNode removeNthFromEnd(ListNode head, int n) {
         //明显是用双指针法，步长为n-1，当后面的指针到达末尾时，前面的指针就是倒数第n个
        //但由于要remove，所以要保留前面的节点
        if(head == null) return null;

        ListNode p1,pre,p2;
        p1 = head;
        p2 = head;
        pre =head;
        int count = 1; //这个应该要是1，因为最小也得是倒数第1个
        boolean p1Move = false;
        while(p2.next != null){
            if(count == n){
                p2 = p2.next;
                pre = p1;
                p1 = p1.next;
            } else{
                p2 = p2.next;
                count++;
            }
        
        }
      // System.out.println(p1.val + " " + pre.val);
        //如果p1是头，那么就相当于删除头，直接返回头的next即可
        if(p1 == head) return p1.next;
        else{
            pre.next = p1.next;
            return head;
        }
    }
}
```

22. Generate Parentheses
Medium

Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

For example, given n = 3, a solution set is:

[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]

在做括号匹配时，从左往右扫描，累积已经出现过的左右括号，当扫描到右括号时，如果此时左括号个数大于右括号的个数(不算此时的这个)，则此时是有效匹配，否则是无效的。 

所以扫描一个括号序列是否匹配（括号匹配）可以如下做：
初始化一个计数器count为0，扫描到左括号时，count++，扫描到右括号时，count--，扫描时如果出现count<0，则跳出，为无效匹配；扫描完后count不为0则无效匹配，为0则是有效匹配

```java
class Solution {
    public List<String> generateParenthesis(int n) {
        ArrayList<String> list = new ArrayList<>();
        String cur = "";
        addBracket(list, cur, 0, 0,n);
        return list;
    }

    public void addBracket(List<String> list, String pre, int left, int right, int n){
//        System.out.println(pre);
        if(left==n && right==n){
            list.add(pre);
            return;
        }
        if(left<n){
            String cur = pre + "(";
            addBracket(list, cur, left+1, right, n);
        }
        //这里直接在加右括号的时候就用上面的条件判断，如果已经是无效串了，那就直接断了这条路
        if(right<n && left>right){ 
            String cur = pre + ")";
            addBracket(list, cur, left, right+1, n);
        }
    }
}
```

23. Merge k Sorted Lists
Hard

Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

Example:

Input:
[
  1->4->5,
  1->3->4,
  2->6
]
Output: 1->1->2->3->4->4->5->6

把所有链表里的数据都加到同一个数组里，然后排序，再重新构造一个链表不行，这个题肯定是不能改变这个链表节点本身
考虑使用优先级队列，把所有节点加入优先级队列，然后顺序就是排好的，而且节点本身也没有被改变

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
    public ListNode mergeKLists(List<ListNode> lists) {
        if (lists==null||lists.size()==0) return null;
        
        PriorityQueue<ListNode> queue= new PriorityQueue<ListNode>(lists.size(),new Comparator<ListNode>(){
            @Override
            public int compare(ListNode o1,ListNode o2){
                if (o1.val<o2.val)
                    return -1;
                else if (o1.val==o2.val)
                    return 0;
                else 
                    return 1;
            }
        });
        
        ListNode dummy = new ListNode(0);
        ListNode tail=dummy;
        
        for (ListNode node:lists)
            if (node!=null)
                queue.add(node);
            
        while (!queue.isEmpty()){
            tail.next=queue.poll();
            tail=tail.next;
            
            if (tail.next!=null)
                queue.add(tail.next);
        }
        return dummy.next;
    }
}
```

24. Swap Nodes in Pairs
Medium

Given a linked list, swap every two adjacent nodes and return its head.

You may not modify the values in the list's nodes, only nodes itself may be changed.

Example:

Given 1->2->3->4, you should return the list as 2->1->4->3.

添加一个虚头，保留3个指针，一个pre，一个n1,一个n2，关系是 pre->n1->n2

交换：
n1->next=n2->next
n2->next = n1
pre->next = n2

pre->n2->n1

交换完之后，都要往后移一位，且还要满足上面的关系：

            pre = n1;
            //如果后面没东西了捕获异常退出
            try{
                n2 = n1.next.next;
                n1 = n1.next;
            }catch(Exception e){
                break;
            }

当n2为null时，循环结束


```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        if(head==null) return null;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode n1 = head;
        ListNode n2 = head.next;
        while(n2!=null){
            n1.next = n2.next;
            n2.next = n1;
            pre.next = n2;

            pre = n1;
            //如果后面没东西了捕获异常退出
            try{
                n2 = n1.next.next;
                n1 = n1.next;
            }catch(Exception e){
                break;
            }

        }
        return dummy.next;
    }
}
```

25. Reverse Nodes in k-Group
Hard

Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.

Example:

Given this linked list: 1->2->3->4->5

For k = 2, you should return: 2->1->4->3->5

For k = 3, you should return: 3->2->1->4->5

Note:

Only constant extra memory is allowed.
You may not alter the values in the list's nodes, only nodes itself may be changed.

翻转链表，不过这个是分段翻转，那就分段处理即可


```java
class Solution {
   public ListNode reverseKGroup(ListNode head, int k) {
        //k为1时不变
        if(k==1) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode node = head;
        ListNode groupDummy = dummy;
        ListNode groupNext = null;

        while(node != null){
            int i=1;
            for(;i<k && node != null ;i++){
                node = node.next;
            }
            if(i==k && node != null){ //说明该段有满足条件的k个节点
                //这是该段后面的第一个节点
                groupNext = node.next;
                //得到翻转后该段第一个节点和最后一个节点
                ListNode[] nodeList = reverse(groupDummy.next, node, k);

                groupDummy.next = nodeList[0];
                nodeList[1].next = groupNext;

                //下一个段的哑结点即为这个段的尾节点
                //下个段的起始节点即为这个段的groupNext；
                groupDummy = nodeList[1];
                node = groupNext;

            }
        }
        return dummy.next;

    }


    //翻转这个链表，返回这个链表的头节点和尾节点,并且不能用栈，因为只用常数级空间
    ListNode[] reverse(ListNode head, ListNode last,int k){
        if(k==2){
            //直接让last的next指向head即可
            last.next = head;
        }else if(k >= 3){
            ListNode n1 = head;
            ListNode n2 = head.next;
            ListNode n3 = head.next.next;

            while(n3!=last){
                n2.next = n1;

                n1 = n2;
                n2 = n3;
                n3 = n3.next;
            }

            //此时n1,n2,n3分别是这个链表的最后3个节点,手动把它们设置好
            n3.next = n2;
            n2.next = n1;
        }
        ListNode[] ans = new ListNode[2];
        ans[0] = last;
        ans[1] = head;
        return ans;
    }
}
```



26. Remove Duplicates from Sorted Array
Easy

Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the new length.

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

Example 1:

Given nums = [1,1,2],

Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.

It doesn't matter what you leave beyond the returned length.
Example 2:

Given nums = [0,0,1,1,1,2,2,3,3,4],

Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.

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

        用3个值：pre代表上一次出现的值，mod代表当前准备替换的坐标，i是当前遍历到的位置
        i用于循环，当num[i]与pre不等时，说明出现了新值，更新将num[mod]=num[i],更新mod，更新pre为num[i]
                  当num[i]与pre等时，说明还是旧值，不变

还有关键点在于pre和mod的初始值。

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int count = 0;
        int len = nums.length;
        if(len == 0) return 0;
        if(len == 1) return 1;

        int pre = nums[0];
        int modIndex = 1;
        for(int i=1;i<len;i++){
            if(pre != nums[i]){
                count++;
                pre = nums[i];
                nums[modIndex] = nums[i];
                modIndex++;
                continue;
            }
            pre = nums[i];
        }
        return count+1;
    }
}
```

27. Remove Element
Easy

Given an array nums and a value val, remove all instances of that value in-place and return the new length.

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

The order of elements can be changed. It doesn't matter what you leave beyond the new length.

Example 1:

Given nums = [3,2,2,3], val = 3,

Your function should return length = 2, with the first two elements of nums being 2.

It doesn't matter what you leave beyond the returned length.
Example 2:

Given nums = [0,1,2,2,3,0,4,2], val = 2,

Your function should return length = 5, with the first five elements of nums containing 0, 1, 3, 0, and 4.

Note that the order of those five elements can be arbitrary.

It doesn't matter what values are set beyond the returned length.
Clarification:

Confused why the returned value is an integer but your answer is an array?

Note that the input array is passed in by reference, which means modification to the input array will be known to the caller as well.

Internally you can think of this:

// nums is passed in by reference. (i.e., without making a copy)
int len = removeElement(nums, val);

// any modification to nums in your function would be known by the caller.
// using the length returned by your function, it prints the first len elements.
for (int i = 0; i < len; i++) {
    print(nums[i]);
}

找到第一个为val的数，下标为m，遍历此后的每个数，当不为val时，将下标为m的数替换，m++

```java

class Solution {
    public int removeElement(int[] nums, int val) {
        int count = 0;
        int len = nums.length;
        if(len == 0) return 0;

        int m = 0;
        int n = 0;

        while(m<len && nums[m]!=val){
            m++;
        }

        for(int i=0;i<len;i++){
            if(nums[i]!=val) count++;
        }

        for(int i=m;i<len;i++){
            if(nums[i]!=val){
                nums[m++] = nums[i];
            }
        }
        return count;
    }
}
```

28. Implement strStr()
Easy

Implement strStr().

Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

Example 1:

Input: haystack = "hello", needle = "ll"
Output: 2
Example 2:

Input: haystack = "aaaaa", needle = "bba"
Output: -1
Clarification:

What should we return when needle is an empty string? This is a great question to ask during an interview.

For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().

算出源字符串和匹配串的长度，从原串的第一个比对到第 元串长度-子串长度+1的位置，一旦匹配就直接返回，很简单

```java
class Solution {
    public int strStr(String haystack, String needle) {
        int hayLen = haystack.length();
        int subLen = needle.length();

        int index = -1;
        if(subLen==0) return 0; //needle = ""
        if(subLen > hayLen) return -1; 
        
        for(int i=0;i<= hayLen-subLen;i++){
            if(haystack.substring(i, i+subLen).equals(needle)){
                index = i;
                break;
            }
                
        }
        return index;
    }
}
```

29. Divide Two Integers
Medium

Given two integers dividend and divisor, divide two integers without using multiplication, division and mod operator.

Return the quotient after dividing dividend by divisor.

The integer division should truncate toward zero.

Example 1:

Input: dividend = 10, divisor = 3
Output: 3
Example 2:

Input: dividend = 7, divisor = -3
Output: -2
Note:

Both dividend and divisor will be 32-bit signed integers.
The divisor will never be 0.
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−2^31,  2^31 − 1]. For the purpose of this problem, assume that your function returns 231 − 1 when the division result overflows.

笨办法：
```java
class Solution {
    public int divide(int dividend, int divisor) {
        //乘法的本质是多次加法，所以除法的本质是多次减法
        //溢出：负-正，正-负，0-最大负，
        if(dividend==0) return 0;

        int n1Flag = dividend >0?1:-1;
        int n2Flag = divisor >0?1:-1;
        //由于负数范围比正数大，所以把被除数和除数都先转成负数
        dividend = dividend<0?dividend:-dividend;
        divisor = divisor<0?divisor:-divisor;
        //这种情况下，两个数要么是0，要么是负数，而且当前都是合法的int
        //负-负不会溢出，负-0不会溢出，0-负可能溢出
        int tmp = dividend;
        int result = 0;

        //如果俩正数一直相减，减直到差是负数，说明除到头了。
        //俩负数就是一直相减，减到差是正数，说明除到头了
        while(tmp-divisor <= 0){
            if(tmp==0) //说明已经整除了
                break;
            tmp -= divisor;
            result--;
        }
        int flag = n1Flag==n2Flag?1:-1;
        if(result == Integer.MIN_VALUE && flag==1)
            return Integer.MAX_VALUE;

        return flag==-1?result:-result;

    }
}
```

更好的方法是尝试在每次迭代中完成更多工作。 想法是计算最大的k，
使得2^k * y <= x，然后从x减去2^k * y并且将2^k加到商上。

例如 - 如果x =（1011）2 且y =（10）2，则k = 2，因为2 * 2^2 <= 11
且2 * 2^3> 11.我们从（1011）2中减去2 * 2^2=（1000）2得到 （11）2，
向商添加2^k = 2^2 =（100）2，并通过将x更新为（11）2继续。

使用2^k * y的优点是可以使用移位非常有效地计算，并且在每次迭代中x减半。 
如果需要n位来表示x / y，则存在O（n）次迭代。 如果通过迭代k计算最大k使得
2^k * y <= x，并且由于每次迭代具有时间复杂度O（n），这导致O（n2）算法。

# 一遇到以2为底的乘除法就要想到移位运算，尽可能尝试移位
## 2^k = 1 << k;
## y * 2^k = y << k;
## y / 2^k = y >> k;

这样可能不够直观，但思考一个问题：除法的本质就是算x中有多少个y.
一般的做法，是从x中去掉一个y，再去掉一个y。。。一直去到不能再去为止。
这里的做法是，通过移位，知道x中至少有 2^k个y，但没有 2^(k+1)个(但可能有 2^k + b 个)，那么先去掉2^k个y
，得到的结果为x1，再通过移位知道其中有2^m个y，但没有 2^(m+1)个，那么再从x1中去掉2^m个y，得到的结果是x2.。。。
最后直到结果为 xn时，里面已经没有y了，就作罢，除法答案就是上面的 2^k + 2 ^m + ....


```java
public class Solution{
    public int divide(int dividend, int divisor) {
        //只有这一种溢出的情况。就是负数的最值除以-1
        if(dividend == Integer.MIN_VALUE && divisor==-1)
            return Integer.MAX_VALUE;

        long x = Math.abs((long)dividend);
        long y = Math.abs((long)divisor);
        if(dividend==0) return 0;
        int sign = (dividend < 0) ^ (divisor < 0 )? -1 : 1;

        int result = 0;

        for(;x>=y;){
            int k=1;
            while(y << k <= x){
                k++;
            }
            //跳出循环时， y<<k > x
            k--; //为了恢复使得y<<k小于等于x的最后一个k
            x -= y<<k;
            result += 1<<k;
        }
        return sign==1?result:-result;
    }
}
```

30. Substring with Concatenation of All Words
Hard

You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.

Example 1:

Input:
  s = "barfoothefoobarman",
  words = ["foo","bar"]
Output: [0,9]
Explanation: Substrings starting at index 0 and 9 are "barfoor" and "foobar" respectively.
The output order does not matter, returning [9,0] is fine too.
Example 2:

Input:
  s = "wordgoodgoodgoodbestword",
  words = ["word","good","best","word"]
Output: []

给你一个字符串，s，和一列单词，单词，它们都是相同长度的。
在s中找到所有子字符串的起始索引，该索引是单词中每个单词的一次连接，并且不包含任何中间字符。


该题的关键在于words中每个词的长度都是相等的。
```java
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        final Map<String, Integer> counts = new HashMap<>(); //words中每个词对应的个数
        for (final String word : words) {
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }



        final List<Integer> indexes = new ArrayList<>(); //答案
        final int n = s.length();
        int num = words.length;  //单词的个数
        if(n==0 || num==0) return indexes; //s长度为0或words里面没东西，直接返回0；

        int len = words[0].length(); //单词的长度（每个单词长度一样）
        for (int i = 0;   num * len - 1 < n-i ; i++) { 
        //这里必须i要一个一个加，而不是len个len个加。
        //如果i后面的字符串长度不够words中所有的长度，则停止
            final Map<String, Integer> seen = new HashMap<>();
            int j = 0;
            while (j < num) {
                final String word = s.substring(i + j * len, i + (j + 1) * len); //单词都是用len个字母len个字母地取
                if (counts.containsKey(word)) { 
                //如果包含这个单词，并且出现的次数小于等于words中该单词数，那就继续，如果大于则多了，不符合，
                //从下一个字母开始重来
                    seen.put(word, seen.getOrDefault(word, 0) + 1);
                    if (seen.get(word) > counts.getOrDefault(word, 0)) {
                        break;
                    }
                } else { //如果不包含这个词，退出重来
                    break;
                }
                j++;
            }
            if (j == num) { //全部单词都能找到且个数合适，则该子串合适
                indexes.add(i);
            }
        }
        return indexes;
    }
}
```

31. Next Permutation
Medium

Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.

If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).

The replacement must be in-place and use only constant extra memory.

Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.

1,2,3 → 1,3,2
3,2,1 → 1,2,3
1,1,5 → 1,5,1

从后往前，找到第一个减小的数字x,再从x往后找，找到最后一个大于x的数字y，交换x和y，
然后翻转新的x后面的序列为顺序，即为下一个字典序（因为交换掉以后，x后面是逆序的，大值的权值高，此时x已经变大了，
后面的序列的值应该是最小的）。

如 158476531

从后往前找到第一个下降的数为4，从4的后面往后找最后一个大于4的数字，找到5
交换4，5： 158 5 76 4 31
让交换的5后面的序列翻转成顺序：158513467


因为要找下一个字典序，就找的是比他大一点点的一种数字组合，如 1,3,2，下一个就是2,3,1，为什么要从后往前找，因为先动
的肯定是权值小的位。x与权值最小的比他大的数字交换，能保证带来的增大幅度是最小的。

这种做法对于求下一个字典序，上一个字典序的题都是通用的。，不论其元素是数字，字母，字典是不是特定的。

# 记住这种方法

如果要求上一个字典序，则从后往前找第一个上升的，然后从该数开始找后面最后一个比它小的，交换，然后让后面的都是逆序
151237，找到 5，他后面最后一个比它小的是3
交换为：131257，让3后面为逆序：137521，则151237的上一个字典序是137521

## 就地翻转数组的代码也要记住

```java
class Solution {
    public void nextPermutation(int[] nums) {
        int len = nums.length;
        boolean swap = false;
        for(int i=len-2;i>=0;i--){
            if(swap) break;
            if(nums[i] < nums[i+1]){
                swap = true;
                for(int j=i+1;j<len;j++){
                    if(j==len-1 && nums[j]>nums[i]){
                        swap(nums, i, j);
                        reverse(nums, i+1, len);//翻转后面的序列
                        break;
                    }
                    if(j<len-1 && nums[j]>nums[i] && nums[j+1]<=nums[i]){//这里的小于等于非常关键
                        swap(nums, i, j);
                        reverse(nums, i+1, len); ////翻转后面的序列
                        break;
                    }

                }
            }
        }

        if(!swap){
            reverse(nums, 0, len);
        }
    }

    public void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    //start inclusive
    //end exclusive
    public void reverse(int[] nums, int start, int end){
        //就地翻转
        int len = end - start;
        int sum = end-1+start; //要交换的每两个值的下标都满足和为sum
        for(int i=start;i<len/2 + start;i++){
            int tmp = nums[i];
            nums[i] = nums[sum-i];
            nums[sum-i] = tmp;
        }
    }
}
```

32. Longest Valid Parentheses
Hard

Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

Example 1:

Input: "(()"
Output: 2
Explanation: The longest valid parentheses substring is "()"
Example 2:

Input: ")()())"
Output: 4
Explanation: The longest valid parentheses substring is "()()"

使用栈：
先入栈-1
遇到'('时，入栈索引
遇到')'时，先弹栈，如果弹栈后栈为空，入栈其索引，
            计算此时的 t=索引-栈顶元素， max = Math.max(t, max);
          

这样做会使得每次遇到 ')'时，弹栈，然后此时的栈顶元素是当前能连起来的括号集合的最左端的前一个位置（放-1的原因）。
如
()()() : 每次计算t的时候，栈顶元素都是-1
    0 1 2 3 4 5 6 7
    ( ) ( ) ) ) ( )
栈顶: -1  -1 4 5   5 
t     2   4 0 0   2
    0 1 2   

       0 1 2 3 4 5 6 7 8 9
       ( ( ) ( ) ) ( ) ) )
栈顶:      0   0 -1  -1 8 9
t          2   4 6   8 0  0


## 这种在栈(队列)里放索引的方法很多地方都用得到

```java
public class Solution{
     public int longestValidParentheses(String s) {
        int maxLen = 0;
        Stack<Integer> stack = new Stack<>();
        int len = s.length();
        stack.push(-1);
        for(int i =0;i<len;i++){
            char c = s.charAt(i);
            if(c=='(')
                stack.push(i);
            else{
                stack.pop(); //先弹栈
                if(stack.empty()){
                    stack.push(i);
                } else{ //如果为空也可以计算这个，但因为stack中只有刚入栈的i,i-stack.peek
                    //必然是0，所以就不用算了
                    maxLen = Math.max(i-stack.peek(), maxLen); //stack.peek为取栈顶元素，但不弹栈
                }
            }
        }
        return maxLen;
    }
}
```

33. Search in Rotated Sorted Array
Medium

Share
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).

You are given a target value to search. If found in the array return its index, otherwise return -1.

You may assume no duplicate exists in the array.

Your algorithm's runtime complexity must be in the order of O(log n).

Example 1:

Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
Example 2:

Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1

logN的复杂度就一定是二分查找的方法，


有序序列相当于经过了若干次循环移位，找出mid，把序列分成两个部分，
一部分一定是有序的，而另一部分是两段有序的

3,4,5,6,7,0,1,2

只考虑两种情况： 
    1. num[mid]小于num[high] 说明mid-high是正序
    2. num[mid]大于等于num[high] 说明low-mid是正序

        在每种情况里面再分target是否在正序区间，如果是，则将另一个指针挪到这个区间，如果否，则将正序区间的指针挪到外面

```java
public class Solution{
        public int search(int[] num, int target) {
        int len = num.length;
        int low = 0;
        int high = len-1;
        int mid;
        while(low <= high){
            mid= (low+high)/2;
            if(num[mid]==target) return mid;

            if(num[mid]<num[high]) {//说明mid~high是正序{
                if(num[mid]<target&& target<=num[high]) //落在正序范围内
                    low = mid+1;
                else{
                    high = mid-1;
                }
            }
            else{ //mid>=high  //说明low~mid是正序
                if(num[low]<=target && target<num[mid])
                    high = mid-1;
                else
                    low = mid+1;
            }
        }
        return -1;
    }
}
```

34. Find First and Last Position of Element in Sorted Array
Medium

Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.

Your algorithm's runtime complexity must be in the order of O(log n).

If the target is not found in the array, return [-1, -1].

Example 1:

Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
Example 2:

Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]

时间复杂度是O(log n)，说明还得用二分查找

用二分找出边界上的target。

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] scope = new int[]{-1,-1};
        binarySearch(0, nums.length-1, nums, target, scope);
        return scope;
    }

    //只找target的两端边界。
    public void binarySearch(int start, int end, int[] nums, int target, int[] scope){
        if(start > end) return;
        int mid = (end + start)/2;

        //所有target全部落在 start~mid-1之间
        if(nums[mid] > target){
            binarySearch(start, mid-1, nums, target, scope);
        }
        else if(nums[mid] < target){
            binarySearch(mid+1, end, nums, target, scope);
        }
        //target正好在中间部分，
        else if(nums[mid] == target){
            boolean l=false,r = false; //表示左右边界是否已找到
            if(mid-1 < 0 || (mid - 1 >= 0 && nums[mid-1]<target)){
                scope[0] = mid;
                l = true;
            }
            if(mid+1 >= nums.length || (mid + 1 < nums.length && nums[mid+1]>target)){
                scope[1] = mid;
                r = true;
            }
            //这里必须放心让end = mid-1，而不必怕mid是边界，因为上面已经判断了mid是否是边界了呀！
            //而且左右没找到的情况，则左右都要找
            if(!l)
                binarySearch(start, mid-1, nums, target, scope);
            if(!r)
                binarySearch(mid+1, end, nums, target, scope);

        }
    }
}
```

39. Combination Sum
Medium

Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.

The same repeated number may be chosen from candidates unlimited number of times.

Note:

All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.
Example 1:

Input: candidates = [2,3,6,7], target = 7,
A solution set is:
[
  [7],
  [2,2,3]
]
Example 2:

Input: candidates = [2,3,5], target = 8,
A solution set is:
[
  [2,2,2,2],
  [2,3,3],
  [3,5]
]

先把数组排好序，找到tar在数组中的位置在何处，那能合成它的只能是比它小的数

实际上就是遍历每种可能性，如果数字合适，加入解集，否则继续查找接下来的。

这种回退递归的方法是非常常用的，（包括深搜也可以用到）一定要记住.

回退递归 ~~ 栈

关键地方有两点：
## 1.递归时候的，当前值之前的值就不再考虑了
## 2.测完一个情况，删掉上一个的保存值

如对2,3,5 tar=8进行搜：

2->2,2->2,2,2->2,2,2,2(满足，回退)
->去掉上一个2：2,2,2->2,2,2,3(大了，回退)
->去掉上一个3：2,2,2->2,2,2,5(大了，回退)
--->2,2,3->2,2,3,3(这里3后面不再接2了，大了，回退)
->2,2,3,5(大了，回退)->2,2,5(大了，回退).......

整个工作过程很像是一个栈，其实就是深度优先搜索，深搜的工作情景就是一个栈


```java
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> numbers = new ArrayList<Integer>();
        trackBack(candidates,0,result,numbers,0,target);
        return result;
    }

    public void trackBack(int[] nums, int start, List<List<Integer>> result, List<Integer> numbers,
                          int sum, int target){
        if(sum > target){ //和大于目标
            return;
        }

        if(sum==target){
            List<Integer> tmp = new ArrayList<>(numbers);
            result.add(tmp);
        }

        for(int i=start; i< nums.length;i++){
            numbers.add(nums[i]);
            //这里有关键一点是，当找到第i个数后，i前面的就不在搜索了，这就避免了重复，也就是传递i给递归中的start
            trackBack(nums, i, result, numbers, sum+nums[i], target);
            numbers.remove(numbers.size()-1);
        }
    }

}
```

42. Trapping Rain Water
Hard

Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.


The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped. Thanks Marcos for contributing this image!

Example:

Input: [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6

有一种更快的方法，利用栈，利用两个凹槽之间的高度差和它们之间最高的中间块来计算它们之间的容积
这个方法走一遍调试，观察变量其实不难理解的。难得在于实现和边界条件的规定
栈里面存的是索引（位置号）

      |
|     |
|   | |
| | | |
| | | |
0 1 2 3
按照单步来看，每两根矩形之间必须有比它们低的矩形才能蓄水，
它们的蓄水量，除去前面算过的的，都只和他们之间的最短的那个长度和它们中间的最长矩形的长度差，以及他们相隔的距离有关
如0和2，之间有1，则0和2之间的面积 = (min(height[0], height[2])-height[1]) * (2-0-1)
如0和3，之间有1，2，但2与0之间的面积已经算过了，所以当成它们之间填充满了2的长度：
它们之间的面积（除去0与2之间的面积）= (min(height[0], height[3])-height[2]) * (3-0-1)

    该题关键理解点在于，遍历高度，
    check: 对于某高度来说，每次都看它是否能和栈顶的下一个元素索引的高度构成矩形（即该高度是否大于栈顶索引对应的高度）
        若能，则矩形高度= min（该元素-栈顶，栈顶下一个元素），宽度=当前元素索引-栈顶下一个元素索引，总面积+= 高度*宽度 弹出栈顶索引，由于栈顶索引变了，继续check，直到上述的不能，或者栈顶的下一个元素为空
        这么看下来栈中的连续三个元素不会保留”高矮高“的情况，当出现这种情况时，一定会被处理了，即矮的一定会被弹出（这句话是核心）
        过了check的循环后，入栈当前元素索引
        若不能，则只会入栈当前元素索引，不会对它计算，因为不能以它为右边构造矩形

        例如对于 4 2 3 5的高度来说
        遍历到3时，取出2的索引，宽度是4到3之间的宽度，高度是 min(3-2,4-2), 再放入3的索引
        遍历到5时，取出3的索引，宽度是4到5之间的宽度，高度是 min(5-3,4-3), 再放入5的索引


循环遍历高度
    循环（若栈不为空，且当前高度大于栈顶高度）
        top=弹栈（栈顶矩形索引）
        若栈为空，break
        宽度 = 当前索引-当前栈顶索引-1
        高度 = min(当前栈顶对应高度，当前高度)-top对应的高度
        面积 += 宽度 * 高度
    压栈当前索引


          |
    | 1 1 |
    | 0 | |
    | | | |
    | | | |
    0 1 2 3 如上图：第一次加面积的时候加入了0的部分，第二次加面积加入了1的部分

```java
public class Solution{
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int cur = 0;
        int len = height.length;
        int ans = 0;
        while(cur<len){
            //当前的比前一个高，则前一个退栈，并以它为基点算当前的和再前一个的差值
            //在本子上画个草图演算一下就容易理解了，分别画一下当前的比前一个高，低的情况
            while(!stack.empty() && height[cur] > height[stack.peek()]){
                int top=stack.pop();
                //弹出后前面再没有了，则这俩矩形之间不能蓄水，前面那个矩形就不要了
                if(stack.empty()){ 
                    break;
                }
                int width = cur - stack.peek() - 1;
                int height1 = Math.min(height[stack.peek()], height[cur]) - height[top];
                ans += width * height1;
            }
            stack.push(cur++);
        }
        return ans;
    }
}
```

46. Permutations
Medium

Given a collection of distinct integers, return all possible permutations.

Example:

Input: [1,2,3]
Output:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]

因为这个允许元素以重复次序出现即排列，所有每回的循环都以0开始，而上面那个是组合，所以为了跳过因为次序导致的不同情况，
循环开始要以当前元素的下标开始，即前面的就不管了。

这个题不能简单地用上面39题的回退递归和深搜，’要额外借助一个标志数组，
将出现过的标为1，跳过已经出现过的元素，每次循环都从0开始

这种元素不重复出现，问排列可能性的题首先就考虑回退递归，如果不能重复出现，则借助一个标志数组。


```java
class Solution {
   public List<List<Integer>> permute(int[] nums) {
        ArrayList<List<Integer>> ans = new ArrayList<>();
        int len = nums.length;
        boolean[] flag = new boolean[len];
        ArrayList<Integer> numbers = new ArrayList<>();
        trackBack(flag, len, nums, ans, numbers);
        return ans;

    }

    public void trackBack(boolean[] flag, int len, int[] nums, List<List<Integer>> ans, List<Integer> numbers){

        if(numbers.size() == len){
            List<Integer> curAns = new ArrayList<>(numbers);
            ans.add(curAns);
            return;
        }

        for(int i=0;i<len;i++){
            if(flag[i]) continue;
            numbers.add(nums[i]);
            flag[i] = true;
            trackBack(flag, len, nums, ans, numbers);
            flag[i] = false;
            numbers.remove(numbers.size()-1);

        }
    }
}
```

正好在这里记录一下排列和组合的写法：

//任一数组元素的组合都只有一种, 这里只记录不同元素不重复，同一个元素出现后可重复出现情况下的写法
这里是元素叠加成目标，还可能出现初始值减各元素成目标，其实就是换个符号

```java

public void combination(int target, int current, int start, int len, int[] nums, List<List<Integer>> ans, List<Integer> numbers){
    if(current > target) return;  //要减的话这里就是 <

    //数字够了
    if(target==current){
        List<Integer> tmp = new ArrayList<>(numbers);
        ans.add(tmp);
        return;
    }

    for(int i=start;i < len;i++){
        numbers.add(nums[i]);
        combination(target, current+nums[i], i, len, nums, ans, numbers); //要减的话这里就是current-nums[i]
        numbers.remove(numbers.size()-1);
    }
}

//排列，不同的次序算不同种
//排列必须每次都从第一个元素开始遍历，但要剔除已经在numbers中的数字，所以用一个标志数组保存当前数是否已经出现过
public void arrangement(int len ,  int[] nums, boolean[] flags, List<List<Integer>> ans, List<Integer> numbers){
    if(numbers.size()==len){
        List<Integer> tmp = new ArrayList<>(numbers);
        ans.add(tmp);
        return;
    }

    for(int i=0;i < len;i++){
        if(flags[i]) continue; //该数字已经出现过了。
        numbers.add(nums[i]);
        flags[i] = true;
        arrangement(i+1, len, nums, ans, numbers);
        numbers.remove(numbers.size()-1);
        flags[i] = false;

    }
}


```

48. Rotate Image
Medium

You are given an n x n 2D matrix representing an image.

Rotate the image by 90 degrees (clockwise).

Note:

You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.

Example 1:

Given input matrix = 
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],

rotate the input matrix in-place such that it becomes:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]

Example 2:

Given input matrix =
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
], 

rotate the input matrix in-place such that it becomes:
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
]


顺时针旋转矩阵

    注意，作为图像的坐标轴都是以矩阵中心为原点，而矩阵中的元素坐标都是以左上角为0，0点

    顺时针旋转矩阵90度可分为两步：
    1.将矩阵中关于主对角线对称的元素交换(关于y=-x做轴对称)，即 num[i][j] swap num[j][i]
    2.交换后的矩阵，每一行都做镜面交换：即：num[i][j] swap num[i][n-1-j]
    即可完成旋转。

    在矩阵中，做y=-x对称 ：num[i][j] swap num[j][i]    ： 横纵坐标交叉相等
        做关于y轴对称：num[i][j] swap num[i][n-1-j]  ：横坐标相等，纵坐标和为n-1
        做关于x轴对称：num[i][j] swap num[n-1-i][j]   ：横坐标和为n-1，纵坐标相等
        做y=x对称：   num[i][j] swap num[n-1-j][n-1-i] ：横纵坐标交叉和为n-1

```java
class Solution {
    public void rotate(int[][] matrix) {
        //第0行交换0个，第1行交换1个，第2行交换2个。。。
        int n = matrix[0].length;
        for(int row=0;row<n;row++){
            for(int col = 0;col<row;col++){
                swap(matrix, row, col);
            }
        }

        for(int row=0;row<n;row++){
            int lim = n/2;
            for(int col=0;col<lim;col++){
                swap2(matrix,row, col, n);
            }
        }
    }

    void swap(int[][] matrix, int i, int j){
        int temp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = temp;
    }

    void swap2(int[][] matrix, int i, int j, int n){
        int temp = matrix[i][j];
        matrix[i][j] = matrix[i][n-1-j];
        matrix[i][n-1-j] = temp;
    }
}
```

49. Group Anagrams
Medium

Given an array of strings, group anagrams together.

Example:

Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
Output:
[
  ["ate","eat","tea"],
  ["nat","tan"],
  ["bat"]
]
Note:

All inputs will be in lowercase.
The order of your output does not matter.

检查某个单词时，把它字母重新组合成字典序，设置一个HashMap专门保存该字典序的List,以字典序为key,
再设一个数组专门保存出现过的key,以便最后从map中获取列表


把任意一个串转成字典序这个函数很重要

这种不同排列归类的题，都可以借鉴类似转成字典序的这种方式

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        int len = strs.length;
        HashMap<String, List<String>> map = new HashMap<>();
        ArrayList<String> keys = new ArrayList<>(); //保存键
        for(int i=0;i<len;i++){
            String s = word2Lexico(strs[i]);
            if(map.containsKey(s)){
                map.get(s).add(strs[i]);
            }else{
                keys.add(s);
                ArrayList<String> strings = new ArrayList<>();
                strings.add(strs[i]);
                map.put(s, strings);
            }
        }
        ArrayList<List<String>> ans = new ArrayList<>();
        for(int i=0;i<keys.size();i++){
            ans.add(map.get(keys.get(i)));
        }
        return ans;
    }

    //把单词转成字典序
    public String word2Lexico(String s){
        int[] number = new int[26];
        int len = s.length();
        for(int i=0;i<len;i++){
            int index = (int)(s.charAt(i) - 'a');
            number[index]++;
        }
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<26;i++){
            if(number[i]==0) continue;
            for(int j=0;j<number[i];j++){
                builder.append((char)(i+'a'));
            }
        }
        return builder.toString();
    }
}
```


53. Maximum Subarray
Easy

Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

Example:

Input: [-2,1,-3,4,-1,2,1,-5,4],
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.
Follow up:

If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.

这道题还是有点难想的，为了O(n)复杂度

找到和最大的子数组

遍历数组，初始化一个maxSoFar=nums[0];

    当遍历到nums[i]时, 如果nums[i] 大于 maxSoFar+nums[i]，说明前面累加的和还会抵消掉nums[i]，那么从nums[i]重新开始
累计，令maxSoFar = nums[i]
    如果nums[i] 小等于 maxSoFar+nums[i]，说明可以起到累加的效果，令maxSoFar = nums[i]+maxSoFar
    综合为 maxSoFar = Math.max(nums[i]+maxSoFar, nums[i]);
    每次更新完后，更新maxTotal = Math.max(maxSoFar, maxTotal);
    这种方法的关键在于及时止损，如果x前面的累加和加上x还能比x小，那么直接不要前面的了，从x重新开始

maxTotal记录已经出现过的最大值

这个题还是不简单的。

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int maxSoFar = nums[0], maxTotal = nums[0];

        for(int i=1;i<nums.length;i++){
            maxSoFar = Math.max(nums[i]+maxSoFar, nums[i]);
            maxTotal = Math.max(maxSoFar, maxTotal);
        }
        return maxTotal;    
    }
}
```


55. Jump Game
Medium

Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Determine if you are able to reach the last index.

Example 1:

Input: [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
Example 2:

Input: [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum
             jump length is 0, which makes it impossible to reach the last index.


使用动态规划
boolean dp[i] 为是否能跳到索引为i的位置上

dp[0] = true;
dp[i] = (dp[j]==true && i <= j+nums[j])  j=0到i-1，只要有一个能到，那就是能到

看dp[len-1]是否为true

```java
class Solution {
    public boolean canJump(int[] nums) {
        int len = nums.length;
        if(len == 0) return false;
        boolean[] dp = new boolean[len];
        dp[0] = true;
        for(int i=1;i<len;i++){
            for(int j=i-1;j>=0;j--){
                dp[i] =  dp[j] && (i <= j+nums[j]);
                if(dp[i]) //如果有一个能到，那就是能到
                    break;
            }
        }
        return dp[len-1];
    }
}
```



56. Merge Intervals
Medium

Given a collection of intervals, merge all overlapping intervals.

Example 1:

Input: [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
Example 2:

Input: [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.

不能直接顺着去合并，会漏掉情况，比如 {[2,3],[4,6],[7,9],[1,10]}
顺着去合并，只能成为{[1,10],[4,6],[7,9]}，如果多次检查的话，会导致复杂度特别高

事实上，把interval按照start的排序，然后就可以顺着去合并了
如上面的，排完序是{[1,10],[2,3],[4,6],[7,9]}
首先将第一个inter加入解集
然后遍历所有inter进行合并，每遍历到一个inter，比较当前inter和解集中的最后一个inter
如果两个interval的start一样，则该inter合并到解集中的inter，
如果两个start不一样，但解集中的的start大于这个的end，则该inter合并到解集中的inter，
否则，这个inter和解集中的inter无交集，把这个inter加入解集，从下一个inter再重新开始比较

快排的复杂度是 NlgN,遍历的复杂度是N，所以总体复杂度是 NlgN

```java
/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
class Solution {
    public List<Interval> merge(List<Interval> intervals) {
        int len = intervals.size();
        if(len==0) return new ArrayList<>();
        ArrayList<Interval> ans = new ArrayList<>();
        qsort(0, len-1, intervals);
        ans.add(new Interval(intervals.get(0).start, intervals.get(0).end));
        //每次都是拿ans中的最后一个interval和当前遍历到的interval比较
        for (int i = 1; i < len; i++) {
            if (intervals.get(i).start == ans.get(ans.size()-1).start)
                ans.get(ans.size()-1).end = Math.max(intervals.get(i).end, ans.get(ans.size()-1).end);
            else{
                //因为是按start排的序，所以当前遍历到的start一定大于ans中最后一个的start，所以就不用比start
                if(intervals.get(i).start <= ans.get(ans.size()-1).end)
                    ans.get(ans.size()-1).end = Math.max(intervals.get(i).end, ans.get(ans.size()-1).end);
                else
                    ans.add(new Interval(intervals.get(i).start, intervals.get(i).end));
            }
        }
        return ans;
    }

    public void qsort(int left, int right, List<Interval> intervals) {

        int l = left;
        int r = right;
        if (l >= r)
            return;

        Interval pivot = new Interval(intervals.get(l).start, intervals.get(l).end);

        while (l != r) {
            for(;r>l && intervals.get(r).start>pivot.start;r--);
            if(l==r) break;
            intervals.set(l++, intervals.get(r));
            for(; r>l && intervals.get(l).start<pivot.start;l++);
            if(l==r) break;
            intervals.set(r--, intervals.get(l));
        }
        if(l==r)
            intervals.set(r, pivot);
        qsort(left, r-1, intervals);
        qsort(l+1, right, intervals);
    }
}
```

62. Unique Paths
Medium

A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

How many possible unique paths are there?


Above is a 7 x 3 grid. How many possible unique paths are there?

Note: m and n will be at most 100.

Example 1:

Input: m = 3, n = 2
Output: 3
Explanation:
From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
1. Right -> Right -> Down
2. Right -> Down -> Right
3. Down -> Right -> Right
Example 2:

Input: m = 7, n = 3
Output: 28

算一个左上角的机器人到达右下角的可能走法数，机器人只能右走和下走
很明显是深度优先遍历了,但遗憾的是，深搜太慢了，会超时

从左上角到右下角，下走的步数是确定的(n-1)，右走的步数也是确定的(m-1)，
问题就转化成: n-1个0和m-1个1，能组合成多少不同的序列？
再抽象为，m-1个球要放到n个盒子(n-1个数能留出n个空位)，有多少种放法？但需要注意的是，球都是一样的，而盒子不一样
( (m-1)+n-1)
(    n-1   )
以上的组合数
使用组合数会用到阶乘，难以计算且容易出错，
答案为：

用以下动态规划思想：
示例 - 

[[1,1,1]，[1,2,3]，[1,3,6]]

在密切观察时，从（0,0）开始，达到（0,1）和（0,2）的方法是1种。
达到（1,0）和（2,0）的方法是1种
达到（1,1）的方法是2.这是因为它可以通过（0,1）和（1,0）到达。
达到（1,2）的方法是3，因为它可以通过（0,1） - >（0,2），（0,1） - >（1,1）和（1,0） - >到达（1,1）。

因此，到达该目的地的方式是两个单元的总和
cell1  - 到达其上方的单元格的方法
cell2  - 到达左侧单元格的方法

如果使用此规则构建表，您将在矩阵的最后一个单元格中获得正确的答案。

```java
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] matrix = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(i==0 || j==0){
                    matrix[i][j]=1;
                } else{
                    matrix[i][j] = matrix[i][j-1] + matrix[i-1][j];
                }
            }
        }
        return matrix[m-1][n-1];
    }
}
```

64. Minimum Path Sum
Medium

Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

Example:

Input:
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
Output: 7
Explanation: Because the path 1→3→1→1→1 minimizes the sum.

还是要明确一点，到达一个单元格的方法只能是从它的上面或者左边过来
用和上面类似的方法，
不过这次用矩阵保存的变成能到达本单元格的最小耗费
每个单元格的耗费 = Math.min (它左边单元格的最小耗费， 它上边单元格的最小耗费) + 它自己的耗费

```java
public class Solution{
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] cost = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(i==0){
                    int preCost = j>0?cost[i][j-1]:0;
                    cost[i][j] += preCost + grid[i][j];
                }else if(j==0){
                    int preCost = i>0?cost[i-1][j]:0;
                    cost[i][j] += preCost + grid[i][j];
                } else{
                    cost[i][j]= Math.min(cost[i][j-1], cost[i-1][j])+grid[i][j];
                }
            }
        }
        return cost[m-1][n-1];
    }
}
```

70. Climbing Stairs
Easy

You are climbing a stair case. It takes n steps to reach to the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Note: Given n will be a positive integer.

Example 1:

Input: 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
Example 2:

Input: 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step

到达一阶只能从它的上一个或者上两个而来，如果paths[n]是到达第n层的方法
则 paths[n] = paths[n-1]+paths[n-2];

```java
class Solution {
    public int climbStairs(int n) {
        int[] paths = new int[n+1];
        if(n<=2) return 1;
        paths[1] = 1;
        paths[2] = 2;
        for(int i = 3;i<=n;i++){
            paths[i] = paths[i-1]+paths[i-2];
        }
        return paths[n];
    }
}
```

62,64,70都有一个明显的特征：到达某个状态一定由前面的状态而来。而且当前状态的值和到达它的前面状态的值是息息相关的
这就是基本的动态规划的特征。而且动态规划一般都是求最优解，但最优解问题不一定用动态规划

某一步的最优解是由它的子问题的最优解组成的。 某一步的状态必须由多种（至少2种）可达，所以在列状态转移方程的时候，
要看所写的状态是不是由别的多种状态可达，如果不是的话那方程就列的有问题

最终状态以及结果一定是数组的最后一项（不管是几维数组），如果结果是数组中间的某项，那就是方程列的不对


72. Edit Distance
Hard

Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.

You have the following 3 operations permitted on a word:

Insert a character
Delete a character
Replace a character
Example 1:

Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')
Example 2:

Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')


动态规划：

Let following be the function definition :-

f(i, j) := minimum cost (or steps) required to convert first i characters of word1 to first j characters of word2
表示word1的第0位到第i位变成word2的第0位到第j位的最小花费

Case 1: word1[i] == word2[j], i.e. the ith the jth character matches.

f(i, j) = f(i - 1, j - 1) //因为 word1[i]->word2[j]的转换没有花费，所以就等于前面的花费

Case 2: word1[i] != word2[j], then we must either insert, delete or replace, whichever is cheaper

f(i, j) = 1 + min { f(i, j - 1), f(i - 1, j), f(i - 1, j - 1) }

f(i, j - 1) represents insert operation  

》当要用插入操作使得word1前i字母变成word2的前j字母，那就是word1的前i字母就是word2的前j-1字母，然后再在i后插入一个word2[j]

f(i - 1, j) represents delete operation

》当要用删除操作使得word1的前i字母变成word2的前j字母，那就是word1的前i-1字母就是word2的前j字母，然后再把word[i]删了就行

f(i - 1, j - 1) represents replace operation
Here, we consider any operation from word1 to word2. It means, when we say insert operation, we insert a new character after word1 that matches the jth character of word2. So, now have to match i characters of word1 to j - 1 characters of word2. Same goes for other 2 operations as well.

》当要用替换操作使得word1前i字母变成word2的前j字母，那就是word1的前i-1字母就是word2的前j-1字母，然后再把word1[i]替换成word2[j]

Note that the problem is symmetric. The insert operation in one direction (i.e. from word1 to word2) is same as delete operation in other. So, we could choose any direction.

Above equations become the recursive definitions for DP.

Base Case:

f(0, k) = f(k, 0) = k
word1的前0个字母要变成word2前k个字母，直接增插入k个
word1的前k个字母要变成word2前0个字母，直接删除k个


Below is the direct bottom-up translation of this recurrent relation. It is only important to take care of 0-based index with actual code :-

本题是典型的适合使用动态规划的题目。在斯坦福的公开课(中文，英文)上，有对这个问题的详细说明，所以接下来就继续使用斯坦福公开课的例子了。

如果要计算单词"INTENTION"和单词"EXECUTION"之间的编辑距离，那么该怎么计算呢？

首先，把这个问题简单化。把上面两个单词简化为长度为1的两个单词I和E。

如果要“I”变化为"E"，可以把"I"替换为"E"
如果要“I”变化为空串" "，可以把"I"删除，从而形成""
如果要空串“ ”变化为"E"，可以把"E"插入，从而形成E

上面三种变化分别表示替换，删除，插入这三种基本操作。

接下来，定义一个表达式D(i,j)。它表示从第1个字单词的第0位至第i位形成的子串和第2个单词的第0位至第j位形成的子串的编辑距离。

显然，可以计算出动态规划的初始表达式，如下:

D(i,0) = i

D(0,j) = j

然后，考虑动态规划的状态转移方程式，如下:

                                   D(i-1, j) + 1
D(i,j)=min                  ( D(i, j-1) + 1 )
                                   D(i-1, j-1) +2( if  X(i) != Y(j) ) ; D(i-1,j-1) ( if  X(i) == Y(j) )

上面的状态转移方程的含义是，D(i,j)的值，要么是D(i-1, j)的操作完成之后删除一个字符(第1个单词的第i个字符)，要么是D(i, j-1)的操作完成之后增加一个字符(第2个单词的第j个字符)，要么是D(i-1, j-1)的操作完成自后替换一个字符(如果第1个单词的第i个字符和第2个单词的第j个字符不等)，或者是D(i-1, j-1)的操作完成自后什么也不做(如果第1个单词的第i个字符和第2个单词的第j个字符相等)。其中，课件定义删除，插入，替换的操作步数分别为一步，一步，两步。

以第一个单词"INTENTION"和第二个单词"EXECUTION"为例，看下面的图

接下来，代码实现。注意在leetcode中，把插入，删除，替换全部视为一步操作。


```java
public class Solution{
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        //f[i][j]代表word1的前i个字母变成word2的前j个字母的最小花费，需要注意的是，单词的第i个字母的序号是i-1
        //所以要保证有f[m][n]，所以长度必须是[m+1][n+1]。f[m][n]代表word1的前m个字母变成word2的前n个字母的
        //最小花费，即是整个转变完成的花费，即题目所求
        int[][] f = new int[m+1][n+1];
        for(int i=0;i<m+1;i++){
            f[i][0] = i;
        }
        for(int i=0;i<n+1;i++){
            f[0][i] = i;
        }

        for(int i=1; i<m+1;i++){
            for(int j=1;j<n+1;j++){
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    f[i][j] = f[i-1][j-1];
                }
                else{
                    int min = Math.min(f[i][j-1], f[i-1][j]);
                    min = Math.min(min, f[i-1][j-1]);
                    f[i][j] = min+1;
                }
            }
        }
        return f[m][n];
    }
}
//动态规划太tm神奇了
```


75. Sort Colors
Medium

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


双指针定位，单指针扫描(一共3个指针，因为如果用双指针的话，当p1和p2都遇到1的时候就卡住了，必须有其他人来打破僵局)，p1和p2分别从前后往中间靠，p1到达第一个不为0的地方，p2到达第1个不为2的地方
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

76. Minimum Window Substring
Hard

Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

Example:

Input: S = "ADOBECODEBANC", T = "ABC"
Output: "BANC"
Note:

If there is no such window in S that covers all characters in T, return the empty string "".
If there is such window, you are guaranteed that there will always be only one unique minimum window in S.

问题要求我们从字符串S返回最小窗口，该字符串具有字符串T的所有字符。如果它包含来自T的所有字符，那么让我们称之为窗口。

我们可以使用简单的滑动窗口方法来解决这个问题。

在任何基于滑动窗口的问题中，我们有两个指针。一个right指针，其工作是扩展当前窗口，然后我们有left指针，
其工作是收缩一个给定的窗口。在任何时间点，这些指针中只有一个移动而另一个保持固定。

解决方案非常直观。我们通过移动右指针继续扩展窗口。当窗口具有所有所需字符时，我们收缩（如果可能）并保存最小窗口，直到现在。

答案是最小的理想窗口。

例如。 S =“ABAACBAB”T =“ABC”。然后我们的答案窗口是“ACB”，下面显示的是可能需要的窗口之一。


算法

我们从两个指针开始，left和right最初指向字符串SS的第一个元素。

我们使用right指针展开窗口，直到我们得到一个理想的窗口，即包含T的所有字符的窗口。

一旦我们有一个包含所有字符的窗口，我们就可以将左指针一个接一个地向前移动。如果窗口仍然是理想的窗口，我们继续更新最小窗口大小。

如果窗口不再是包含所有所需字符，我们重复步骤2。

还有一个难点是如何判断窗口的字符串包含T，使用一个HashMap m1保存T中出现过的字母和对应的频次
窗口中的字母也用一个HashMap m2维护，对应的也是字母和出现的次数，设置一个int satisfied
它代表窗口中的有几种字母已经满足了T，
比如：T=AABC, S=ABCDBBDC
当窗口为ABC时，satisfied=2，因为窗口中B满足，C满足，但A不满足（个数不够），
设satisfied=0，count=T中的不同的字母个数，当satisfied==count时，说明窗口包含了T所有的字母
当窗口增加一个字母时，m2中该字母对应的k值+1，如果此时该m1的键值等于m2的该键值，则satisfied++(大于不加)，
相反当窗口减少一个字母时，m2中该字母对应的k值-1，如果此时m2的键值小于m1的该键值，则satisfied--

这样判断的好处在于，当增减一个字母时，不用重新遍历整个窗口去匹配T，而是利用之前的信息。

还有个好处也在于map的getOrDefault函数，用来读取次数最合适不过，因为如果有就返回，没有就设为0并返回，语义容易理解，没有的时候可以用递减到0
而不用直接删除这个key

## 一定要掌握这种字符串的匹配方式，即通过两个HashMap分别存目标串中每个元素的次数和当前窗口内的元素的次数，
## require保存已经满足的条件的个数

# 这里吧这种方法叫双Map法，（或者双HashMap法），用在判断一个序列里是否包含另一个序列，且元素是无序的。


```java
class Solution {
    public String minWindow(String s, String t) {
        if(s.equals("") || t.equals("")) return "";
        HashMap<Character, Integer> m1 = new HashMap<>();
        int count = 0;
        for(int i=0;i<t.length();i++){
            char c = t.charAt(i);
            if(!m1.containsKey(c)) count++;
            m1.put(c, m1.getOrDefault(c, 0)+1);
        }


        int satisfied = 0;
        int l=0,r=0;
        HashMap<Character, Integer> m2 = new HashMap<>();

        //因为要返回的是字符串不是长度，所以用字符串
        String ans = "";
        int minWindowLen = Integer.MAX_VALUE;
        while(l<=r && r<s.length()){
            //移动右指针,需要注意的是，当r跳出时，r是在窗口外面，但此时r指向的元素不在窗口中，这个虽然没多大关系，但要注意。
            for(; satisfied<count && r<s.length();r++){
                char c = s.charAt(r);
                m2.put(c, m2.getOrDefault(c, 0)+1);
                if(m2.get(c).equals(m1.getOrDefault(c, 0)))
                    satisfied++;
            }

            //移动左指针
            for(;satisfied==count && l<r;l++){
                int curLen = r-l;
                if(minWindowLen > curLen){
                    minWindowLen = curLen;
                    ans = s.substring(l,r);
                }

                char c = s.charAt(l);
                m2.put(c, m2.get(c)-1);
                if(m2.get(c) < m1.getOrDefault(c, 0))
                    satisfied--;
            }
        }
        return minWindowLen==Integer.MAX_VALUE?"":ans;
    }

}
```


78. Subsets
Medium

Given a set of distinct integers, nums, return all possible subsets (the power set).

Note: The solution set must not contain duplicate subsets.

Example:

Input: nums = [1,2,3]
Output:
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]

给出一个集合所有可能的子集

还是回退递归

```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        ArrayList<List<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> cur = new ArrayList<>();
        trackBack(0, ans, cur, nums);
        return ans;
    }

    void trackBack(int start, List<List<Integer>> ans, List<Integer> cur, int[] nums){

        ArrayList<Integer> tmp = new ArrayList<>(cur);
        ans.add(tmp);

        for(int i=start;i<nums.length;i++){
            cur.add(nums[i]);
            trackBack(i+1, ans, cur, nums);  //这里一定要注意新的start是i+1
            cur.remove(cur.size()-1);
        }
    }
}
```
## 所以要记得，类似这种求一个数组内元素各种排列的题都可以用回退迭代完成。



79. Word Search
Medium

Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.

Example:

board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

Given word = "ABCCED", return true.
Given word = "SEE", return true.
Given word = "ABCB", return false.


又是一道遍历匹配的题，但这个题是有顺序的，不用双Map法，可以直接用DFS，将目标单词中字母顺序与遍历序列中的字母匹配，符合就继续匹配该字母周围的字母与单词的下一个字母。否则就退出这里最好用递归的dfs，当遍历完一个位置时，将其标记为已看，然后继续dfs它的周围8个方向的位置，当把它周围的元素后续全部遍历完后，要将其标记为未看，因为它在一个顺序中不满足不代表在另一个顺序中也不满足。
这里的难点就在于标定已经看过的元素，4个方向的dfs递归，以及遍历过后标为未查看

```java
class Solution {

    public boolean exist(char[][] board, String word) {
        boolean ans = false;

        int row = board.length;
        int col = board[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == word.charAt(0)) {
                    int require = 0;
                    //还需要一个数组来保存当前遍历已经探索过的位置
                    boolean[][] watched = new boolean[row][col];
                    ans = DFS(word,0, i, j, board, watched);
                    if (ans) return true;
                }
            }
        }
        return false;
    }

    //index 该去检查word的第index个元素了
    boolean DFS(String word, int index
                ,int i, int j, char[][] board, boolean[][] watched) {
        int row = board.length;
        int col = board[0].length;
        if (i >= row || j >= col || i < 0 || j < 0) return false;
        if (watched[i][j]) return false; //表示当前这条路走不通

        char c = board[i][j];
        if(c!=word.charAt(index)) {
            watched[i][j] = false;
            return false;
        }
        else {
            if(index == word.length()-1) return true;
            watched[i][j] = true;
            boolean ans = DFS(word, index+1, i - 1, j, board, watched)
                    || DFS(word, index+1,  i, j - 1, board, watched)
                    || DFS(word, index+1,  i + 1, j, board, watched)
                    || DFS(word, index+1, i, j + 1, board, watched);
            if (ans) return true;

        }
        //这里有非常重要的一点，当某个元素的子元素都遍历完后，要把这个元素标为未查看，
        //因为它在当前的顺序中不满足，不代表它咋其他顺序中也不满足。
        watched[i][j] = false;
        return false;
    }
}
```

84. Largest Rectangle in Histogram
Hard

Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.

Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].

The largest rectangle is shown in the shaded area, which has area = 10 unit.

Example:

Input: [2,1,5,6,2,3]
Output: 10

图片贴不上来，意思就是给一个数组代表一排矩形，矩形的宽都是1，让求能构成的最大面积

边是第i个矩形和第j个矩形的最大面积= i~j之间所有矩形中~最矮的高度 * (j-i+1)，  注意这里规定了两边必须是i和j
用一个二维数组记录第i条和第j条之间的最矮高度：shortest[i][j]
显然shortest[i][j] = Math.min(shortest[i][j-1], num[j])
shortest[i][i] = num[i],     i <= j
但这种做法会超内存

***********************************

对于任何条形i，最大矩形的宽度为r-l-1，其中r是条形i的右边的一个坐标，r低于i，i+1到r-1的条形都不低于i，l是条形i坐标左边的一个坐标 l+1到i-1的条形都不低于i，l低于i

对于i，l是i左边离i第一个的比它低的矩形坐标，r是i右边第一个比它低的矩形坐标

int maxArea = 0;
for（int i = 0; i <height.length; i ++）{
    maxArea = Math.max（maxArea，height [i] *（lessFromRight [i]  -  lessFromLeft [i]  -  1））;
}
lessFromLeft[i] 表示i左边最近的比height[i]的矮的坐标
主要技巧是如何有效地计算lessFromRight和lessFromLeft数组。平凡的解决方案是使用O（n ^ 2）解决方案，并且对于每个i元素，首先在第二个内部循环中找到他的左/右heighbour，只是向后或向前迭代：

for（int i = 1; i <height.length; i ++）{
    int p = i  -  1;
    while（p> = 0 && height [p]> = height [i]）{
        P--;
    }
    lessFromLeft [i] = p;
}
唯一的行更改将此算法从O（n ^ 2）转移到O（n）复杂度：我们不需要重新扫描左边的每个项 - 我们可以重用先前计算的结果并快速“跳转”索引：

while（p> = 0 && height [p]> = height [i]）{
      p = lessFromLeft [p];
}
但

这个思想有点像滑动窗口，但它的窗口主体是每个条形边的左右滑动，而不是从头到尾的遍历数组
还有一个技巧在于p的搜索，比如寻找左边界，当p到达x时（x在i前面）还不满足，说明h[x]肯定比h[i]大，
那就p直接跳转到lessFromLeft[x]的地方（第一个比h[x]低的位置）再去比较h[p]和h[i]的高度，
而对于lessFromRight，要从后往前来找

最后遍历一遍i，计算 height [i] *（lessFromRight [i]  -  lessFromLeft [i]  -  1）的最大值



```java
class Solution {
     public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        if(len==0) return 0;
        int maxArea = 0;
        int[] lessFromLeft = new int[len];
        int[] lessFromRight = new int[len];
        lessFromLeft[0] = -1;
        lessFromRight[len-1] = len;

        for(int i=1;i<len;i++){
            int p = i-1;
            while(p>=0 && heights[p]>=heights[i]){
                p=lessFromLeft[p];
            }
            lessFromLeft[i] = p;
        }

        for(int i=len-2;i>=0;i--){
            int p=i+1;
            while(p<=len-1 && heights[p]>=heights[i]){
                p=lessFromRight[p];
            }
            lessFromRight[i]=p;
        }

        for(int i=0;i<len;i++){
            maxArea = Math.max(maxArea, heights [i] *(lessFromRight [i]-lessFromLeft [i]  -  1));
        }
        return maxArea;
    }
}
```


85. Maximal Rectangle
Hard

Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

Example:

Input:
[
  ["1","0","1","0","0"],
  ["1","0","1","1","1"],
  ["1","1","1","1","1"],
  ["1","0","0","1","0"]
]
Output: 6

用动态规划，DP解决方案从第一行开始逐行进行。 令第i行和第j列的最大矩形区域由[right（i，j） -  left（i，j）+ 1] * height（i，j）计算。

# 这个算的是这样一个矩形的面积：以元素i所在的行为底边行，且元素i在该矩形之中，且它是元素i当前所在的矩形之中最高的。

这里不要用下面的方法：
扫描到元素num[i-1][j]，找以它为左上角/右下角的矩形，诸如此类的以该元素为角点找矩形做法，
因为如果作为角点，宽和高的选择不好协调, 这样可能会漏情况，如：

100000
111111 

如果优先高度的话，那么maxarea=5（但其实应该等于6），这里因为[1][0]的元素优先到了高度为2，宽度为1的矩形；错过了高为1，宽为6的矩形

10
10
11 

如果优先宽度的话，maxarea=2（但其实应该等于3），理由类似

## 出现这种情况的原因是把当前遍历到的元素视为了角点，而如果不把元素视作角点，
## 而仅仅是是找它所在的最高的矩形，那么它漏掉的情况会由扫描到其他元素的情况补上，

如，继续用高度优先
100000
111111
对[0][0],area=1，对[1][0]，area=2(因为是高度优先)，对[1][1]，area=6（因为[1][1]不作为角点，它所在的最高矩形高度为1，宽度为6）
不作为角点的话，就需要找当前元素连续的最左边的1（其位置为curL）和连续的最右边的1（其位置为curR）
但既然是高度优先，那么如果当前元素上方的元素是1，则当前元素用于计算矩形宽度的有效的L = max(preL, curL)，R=min(preR,curR) (用范围最小的，才能保证覆盖)

设cur_left是第i行第j个元素往左数最左边1的位置；
left[i][j]是第i行第j个元素所在的最高矩形的左边界位置，计算的时候只根据元素[i][j]和元素[i-1][j]最左边最近的‘1’定。只需考虑他俩即可。
设cur_right是第i行第j各元素往右数第最后一个1的位置；
right[i][j]是第i行第j个元素所在的最高矩形的右边界位置，计算的时候只根据元素[i][j]和元素[i-1][j]最右边最近的‘1’定。同上
height[i][j]为第i行第j个元素所在最高矩形的高度，一定是以1为宽，往上走能达到的最高高度


if num[i][j]=='1' left[i][j] = max(left[i-1][j], cur_left)
right[i][j] = min(right[i-1][j], cur_right)
height[i][j] = height[i-1][j]+1

第i行第j个元素所在的最高矩形面积为 (right[i][j]-left[i][j] + 1) * height[i][j]


```java
class Solution {
    public int maximalRectangle(char[][] matrix) {
        int row = matrix.length;
        if (row==0) return 0;
        int col = matrix[0].length;
        if (col==0) return 0;
        //由于left和height每次只会用同一列上一行的，所以只用一维数组来保存即可，遍历到每一行用完上一行的数据后直接覆盖
        int[] left = new int[col];
        int[] height = new int[col];
        int[] right = new int[col]; //right的起始值必须都为col-1;
        fill_n(right, col, col-1);
        int maxArea = 0;
        for(int i=0;i<row;i++){
            int cur_left = 0, cur_right=col-1;
            for(int j=0;j<col;j++){
                if(matrix[i][j]=='1') height[j] = height[j]+1;
                else height[j]=0;
            }

            /*当前元素若为1，如果上面的元素是0，则left[j]=cur_left
                         如果上面的元素是1，则left[j]=Math.max(left[j], cur_left)；
                         由于0对应的left[j]=0，所以总的可以写成 left[j]=Math.max(left[j], cur_left)
              当前元素若为0，更新cur_left，将left[j]置为0(方便下方的元素使用)
            */
            for(int j=0;j<col;j++){
                if(matrix[i][j]=='1') left[j] = Math.max(left[j], cur_left);
                else {
                    cur_left=j+1;
                    left[j]=0;
                }
            }

            /*
            设置right时从右往左遍历
            */
            for(int j=col-1;j>=0;j--){
                if(matrix[i][j]=='1') right[j] = Math.min(right[j], cur_right);
                else {
                    cur_right=j-1;
                    right[j]=col-1;
                }
            }
            for(int j=0;j<col;j++){
                maxArea = Math.max((right[j]-left[j]+1) * height[j], maxArea) ;
            }

        }
        return maxArea;
    }

    void fill_n(int[] nums, int len, int ele){
        for(int i=0;i<len;i++){
            nums[i] = ele;
        }
    }
}
```

94. Binary Tree Inorder Traversal
Medium

Given a binary tree, return the inorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [1,3,2]
Follow up: Recursive solution is trivial, could you do it iteratively?

二叉树的中序遍历,递归就很好用，但上面要求用遍历而不是递归

# 非递归遍历的做法：
## 先序遍历用1个栈 Stack<E>
## 中序遍历用1个栈   Stack<E>  
## 后序遍历用1个栈   Stack<E>
## 层序遍历用1个队列 LinkedList<E>，如果要求每个元素的层号，则用两个队列，交替放奇数层和偶数层  LinkedList<E> * 2


用两个优先级不同栈，优先级高的栈(lstk)保存左儿子还未被检查过的节点，
优先级低的栈(rstk)保存左儿子已经检查过的节点

如果lstk不空，则先将lstk的栈顶node弹出，检查其左儿子，若左儿子存在，则将左儿子放入lstk
然后将node加入rstk

如果lstk为空，且rstk不空，则先将rstk的栈顶node弹出，输出其值，若node存在右儿子，则将右儿子放入lstk

如果lstk为空，且rstk为空，退出循环

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

    //中序遍历一个栈的写法
    public List<Integer> inorderTraversal(TreeNode root) {
        if(root==null) return new ArrayList<>();
        Stack<TreeNode> stk = new Stack<>();
        ArrayList<Integer> ans = new ArrayList<>();
        //中序遍历需要借助一个指针p
        TreeNode p = root;
        while(p!=null || !stk.empty()){
            if(p!=null){
                stk.push(p);
                p = p.left;
            }else{
                p = stk.pop();
                ans.add(p.val);
                p = p.right;
            }
        }
        return ans;
    }


    //前序遍历
    public List<Integer> preTraversal(TreeNode root) {
        if(root==null) return new ArrayList<>();
        //遍历的话,必须要辅助以数据结构，此时用两个栈
        Stack<TreeNode> stk = new Stack<>();
        ArrayList<Integer> ans = new ArrayList<>();
        stk.push(root);
        while(!stk.empty()){
            TreeNode node = stk.pop();
            ans.add(node.val);
            if(node.right!=null) stk.push(node.right);
            if(node.left!=null) stk.push(node.left);
        }
        return ans;
    }

    //后序遍历
    public List<Integer> postTraversal(TreeNode root) {
        if(root==null) return new ArrayList<>();
        Stack<TreeNode> stk = new Stack<>();
        ArrayList<Integer> ans = new ArrayList<>();
        TreeNode p = root;
        TreeNode pre = null;  //pre记录上一个已经输出的结点
        while(p!= null || !stk.empty()){
            while(p!=null){
                stk.push(p);
                p = p.left;
            }
            TreeNode tmp = stk.peek().right;  //在出栈之前，先判断栈顶元素的右孩子结点
            ////走到这里该节点的左子树已经处理完毕，如果该节点的右子树为空或者右子树已经处理过，则输出该节点
            if(tmp==null || tmp==pre){  //当前节点无右子树或右子树已经输出    
                p = stk.pop();
                ans.add(p.val); 
                pre = p;    //记录上一个已输出结点
                p = null;
            }else{
                p = tmp;  //处理右子树
            }
        }
        return ans;
    }

}
```






96. Unique Binary Search Trees
Medium

Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?

Example:

Input: 3
Output: 5
Explanation:
Given n = 3, there are a total of 5 unique BST's:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3

找二叉排序树的个数

这个题不能用回退递归，理由是回退递归只能构造出单支树，2，1，3的话，构造不出
  2                    
 / \          
1   3   
而只能构造出： 

2  
 \  
  3 
 /  
1
而这个显然不是二叉排序树，所以这种做法是错的。  （对于2节点来说，它的右子树出现了比它还小的数1）

考虑对于任意一个元素做为根节点时，可能二叉排序树的个数 = 可能的左子树个数 * 可能的右子树个数。
对于任意二叉排序树，它的左右子树都是二叉排序树，则对于左子树中每个元素作为根节点时，
可能的左子树个数 = 可能的左子树的左子树个数 * 可能的左子树的右子树个数

这样会不断地减少子树中的节点数，直到1


```java
class Solution {
//容易理解的做法
    public int numTrees(int n) {
        return getTreeCount(1, n);
    }

    int getTreeCount(int start, int end){
        //如果start==end,说明这个树只有1个节点，则能构成的个数为1；
        //如果start>end,说明没有节点，空树也是一种情况，所以个数为1
        if(start==end || start > end) return 1;

        int count =0;
        for(int i=start; i<=end;i++){
            int lchildCount = getTreeCount(start, i-1);
            int rchildCount = getTreeCount(i+1, end);
            count += lchildCount * rchildCount;
        }
        return count;
    }
}
```
但这样会很慢，做了很多重复的工作，显然这个是不断划分子问题，且子问题可以独立求解，所以可以用动态规划
当节点数一定时，构成的二叉排序树的个数是一定的（因为树中的元素一定可以有序排列的，而且其中没有重复的元素）

dp[n] = Σ dp[i] * dp[n-i]  for(i:0-->n) 
        i为左子树中节点个数，n-i为右子树中节点个数

n个节点构成的二叉搜索树个数 =  Σ 左子树可能的个数 * 右子树可能的个数

dp[i]表示i个元素能构成的二叉搜索树的个数，注意不是以i为根的二叉搜索树个数！
其中包括以1为根，以2为根。。。。以i为根的二叉搜索树的个数总和
```java
dp[1]=1;
dp[0]=1;  //空树
dp[2] = dp[0]*dp[1] + dp[1] * dp[0];
dp[3] = dp[0]*dp[2] + dp[1] * dp[1] + dp[2] * dp[0];
        根节点为1        根节点为2         根节点为3
...

dp[n] = 0;
for(int i=0;i<n;i++){
    dp[n] += dp[i] * dp[n-i-1];
}

//注意到对于n为奇数时，除了中间一项，左右两边都是对称的，而偶数时左右两边完全是对称的
//所以上面代码可以更精简，省去一些重复计算：
for(int j=0;j<=n/2;j++){
    if(j == n/2) {
        if(n%2==1)dp[n] += dp[j]*dp[j];
    }
    else dp[n] += dp[j] * dp[n-j-1] * 2;
}
```
完整代码：

```java
class Solution {
    //Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Binary Search Trees.
    public int numTrees(int n) {
        int dp[] = new int[n+1];
        dp[0]=1;
        dp[1]=1;

        for(int i=2;i<=n;i++){
            for(int j=0;j<=i/2;j++){
                if(j == i/2) {
                    if(i%2==1)dp[i] += dp[j]*dp[j];
                }
                else dp[i] += dp[j] * dp[i-j-1] * 2;
            }
        }
        return dp[n];
    }
}
```


98. Validate Binary Search Tree
Medium

Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
Example 1:

Input:
    2
   / \
  1   3
Output: true
Example 2:

    5
   / \
  1   4
     / \
    3   6
Output: false
Explanation: The input is: [5,1,4,null,null,3,6]. The root node's value
             is 5 but its right child's value is 4.

一个二叉搜索树合理，是：左子树上的所有值必须都小于根值，右子树上所有值都大于根值

每个节点向它的左右子树分别传递要小于的值和要大于的值

如果root为根，root的左子树要小于 root,大于MIN，root的右子树要小于MAX,大于root
root的左节点l： l的左子树要小于l,大于MIN，l的右子树要小于root，大于l

对于节点i，它向左子树传递的上界是它自己，下界是它的下界；它向右子树传递的上界是它的上界，下界是它自己.

当节点i不满足上下界时，断定不合理，如果满足再向它的子树传递

由于节点值可能是Integer的最大最小值，为了安全使用上下界，上下界的范围设置为Long类型

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        return checkTree(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    boolean checkTree(TreeNode cur, long lower, long upper){
        if(cur==null) return true;
        boolean ans = cur.val > lower && cur.val < upper;
        return ans && checkTree(cur.left,lower, cur.val) && checkTree(cur.right, cur.val, upper);
    }

}
```

101. Symmetric Tree
Easy

Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).

For example, this binary tree [1,2,2,3,4,4,3] is symmetric:

    1
   / \
  2   2
 / \ / \
3  4 4  3
But the following [1,2,2,null,3,null,3] is not:
    1
   / \
  2   2
   \   \
   3    3
Note:
Bonus points if you could solve it both recursively and iteratively.

对称的树
如果左子树是右子树的镜像反射，则树是对称的。
空树是对称的

因此，问题是：两棵树何时相互镜像？

如果出现以下情况，两棵树是彼此的镜像反射：
1. 他们的两个根的值相同。
2. 每棵树的右子树是另一棵树的左子树的镜像反射。
3. 两棵空树一定是彼此的镜像反射
4. 一空一非空一定不是彼此的镜像反射

方法二：
定义一种遍历算法：先遍历父，再遍历右子节点，最后遍历左子节点 （父右左）
如果这种遍历算法和前序遍历（父左右）的序列一样（！！但前提是两种遍历必须要把null也考虑进来！！），则树是对称的，否则不对称

```java
class Solution {
    boolean isSymmetrical(TreeNode pRoot){
        if(pRoot==null) return true;
        return isMirror(pRoot.left, pRoot.right);
    }

    boolean isMirror(TreeNode root1, TreeNode root2){
        if(root1==null && root2==null) return true;
        else if(root1==null || root2==null) return false;
        return (root1.val==root2.val) && isMirror(root1.left, root2.right) && isMirror(root1.right, root2.left);
    }
}
```

102. Binary Tree Level Order Traversal
Medium

Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).

For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its level order traversal as:
[
  [3],
  [9,20],
  [15,7]
]

二叉树的层序遍历，感觉应该这个是easy，上面对称树是medium
直接用两个队列来做
如果涉及到划分出层，或者要知道层高，就需要用两个队列；
如果只是单纯的层序遍历，遍历结果只是一个数组，就只需要一个队列

java中的Linkedlist，既可以当做链表，也可以当做队列，也可以当做栈。

list.addFirst、list.addLast,list.push（往表头添加）、list.add（往表尾添加）
list.poll(获得表头元素然后删除它)、list.get(获得第i个元素)、list.pop(返回表头元素然后删除它)
list.peek(获得表头元素，但不删除)，poll和pop的功效是一样的

作为队列（Queue：FIFO）：只用 add和poll、peek
作为栈（Stack：FILO）：只用push和pop、peek
作为队列，就可以具体看队列的方式使用api了

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
    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root==null) return new ArrayList<>();
        ArrayList<List<Integer>> ans = new ArrayList<>();
        LinkedList<TreeNode> q1 = new LinkedList<>();
        LinkedList<TreeNode> q2 = new LinkedList<>();
        q1.add(root);
        while(!q1.isEmpty() || !q2.isEmpty()){
            List<Integer> list = new ArrayList<>();
            while(!q1.isEmpty()){
                TreeNode node = q1.poll();
                list.add(node.val);
                if(node.left!=null) q2.add(node.left);
                if(node.right!=null) q2.add(node.right);

            }
            if(!list.isEmpty()) ans.add(list);
            list = new ArrayList<>();
            while(!q2.isEmpty()){
                TreeNode node = q2.poll();
                list.add(node.val);
                if(node.left!=null) q1.add(node.left);
                if(node.right!=null) q1.add(node.right);
            }
            if(!list.isEmpty()) ans.add(list);
        }
        return ans;
    }
}
```

104. Maximum Depth of Binary Tree
Easy

Given a binary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

Note: A leaf is a node with no children.

Example:

Given binary tree [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
return its depth = 3.

计算二叉树的层数
还是层序遍历，这次用于记录层数：
这种做法很慢

考虑更简单有效的做法

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
    //慢办法
    public int maxDepth(TreeNode root) {
        if(root==null) return 0;
        ArrayList<List<Integer>> ans = new ArrayList<>();
        LinkedList<TreeNode> q1 = new LinkedList<>();
        LinkedList<TreeNode> q2 = new LinkedList<>();
        q1.add(root);
        int level = 0;
        while(!q1.isEmpty() || !q2.isEmpty()){
            if(!q1.isEmpty()){
                while(!q1.isEmpty()){
                    TreeNode node = q1.poll();
                    if(node.left!=null) q2.add(node.left);
                    if(node.right!=null) q2.add(node.right);

                }
                level++;
            }
            else {
                while(!q2.isEmpty()){
                    TreeNode node = q2.poll();
                    if(node.left!=null) q1.add(node.left);
                    if(node.right!=null) q1.add(node.right);

                }
                level++;
            }
        }
        return level;
    }


    //快办法
    public int maxDepth(TreeNode root) {
        if(root==null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}
```

105. Construct Binary Tree from Preorder and Inorder Traversal
Medium

Given preorder and inorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.

For example, given

preorder = [3,9,20,15,7]
inorder = [9,3,15,20,7]
Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7

构建一个树：从前序中得到根的值，在中序中根据根找到左子树的范围，从而确定其中的元素个数为l，
前序中，根后的l个数就是左子树中的元素，这样就可以确定左子树的前序序列和中序序列了，再以相同的方式构建左子树

```java
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, 0, preorder.length-1, inorde, 0, inorde.length-1);
    }


    public TreeNode buildTree(int[] preorder, int preFirst, int preLast, int[] inorde, int inFirst, int inLast){
        if(preFirst > preLast || inFirst > inLast) return null;
        int rootVal = preorder[preFirst];
        TreeNode node = new TreeNode(rootVal);
        int inRootLoc = inFirst; 
        //找到根节点在中序序列中的位置
        //注意这么按值找的前提是没有重复元素
        for(; inRootLoc<=inLast; inRootLoc++){ 
            if(inorder[inRootLoc]==rootVal) break;
        }
        //左子树的元素个数
        int leftCount = inRootLoc - inFirst;
        //右子树的元素个数
        int rightCount = inLast - inRootLoc;
        //构建左子树
        node.left = buildTree(preorder, preFirst+1, preFirst+leftCount, inorder, inFirst, inRootLoc-1);
        //构建右子树
        node.right = buildTree(preorder, preFirst+leftCount+1, preLast, inorder, inRootLoc+1, inLast);
        return node;
    }
}
```

114. Flatten Binary Tree to Linked List
Medium

Given a binary tree, flatten it to a linked list in-place.

For example, given the following tree:

    1
   / \
  2   5
 / \   \
3   4   6
The flattened tree should look like:

1
 \
  2
   \
    3
     \
      4
       \
        5
         \
          6

》方法一：
展平二叉树，按照前序遍历，而且是就地完成，即空间复杂度必须是O(1)
其实很简单，再加一个指针pre保存上一个遍历过的元素即可，借助栈，对每一个元素都是先入右再入左
遍历到一个元素，把它作为pre的右孩子，（pre原来的右孩子在栈中，不会丢失），再入栈它自己的右左孩子，
把pre置为它，再把它的左右孩子都置null（但这种做法空间复杂度是O(n)）

》方法二：
递归展平树，对于以root为根的树，先将其左子树展平，再将其右子树展平，然后将右子树接到左子树的最后一个节点上，然后将左子树移到右子树的位置，左子树置空
    辅助递归函数：
        左右子树都为空，返回root
        左子树为空，返回右子树展平后的最后一个节点
        右子树为空，返回左子树展平后的最后一个节点
        都不为空，将右子树的根接到左子树的最后一个节点上，，然后将左子树移到右子树的位置，左子树置空，然后返回（没换位置时的）右子树展平的最后一个节点


```java
//方法一：
class Solution {
     public void flatten(TreeNode root) {
        if(root==null) return;
        TreeNode p = root;
        Stack<TreeNode> stk = new Stack<>();
        stk.push(p);
        TreeNode pre = null;
        while(!stk.empty()){
            p = stk.pop();
            if(pre!=null){
                pre.left=null;
                pre.right=p;
            }
            pre = p;
            if(p.right!=null) stk.push(p.right);
            if(p.left!=null) stk.push(p.left);

        }
        p.left=null;
        p.right=null;
    }
}
```

```java
//方法二：
class Solution {
    public void flatten(TreeNode root) {
        sort(root);
    }

    //返回以root为根的树展平后的最后一个节点
    public TreeNode sort(TreeNode root){
        if(root==null) return null;
        if(root.left==null && root.right==null) return root;
        //走到这，左右子树必有一个不为空
        TreeNode leftLast = sort(root.left);
        if(leftLast==null){
            //左子树为空，直接将右子树展平
            return sort(root.right);
        }else{
            //左子树不为空，将根的右子树接到左子树展平的最后一个节点（简称ll）上
            //若右子树为空，返回ll
            //右子树不为空，返回右子树展平后的最后一个节点
            leftLast.right=root.right;
            root.right = root.left;
            root.left = null;
            TreeNode rightLast = sort(leftLast.right);
            if(rightLast==null) return leftLast;
            else return rightLast;

        }
    }
}
```






121. Best Time to Buy and Sell Stock
Easy

Say you have an array for which the ith element is the price of a given stock on day i.

If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.

Note that you cannot sell a stock before you buy one.

Example 1:

Input: [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
             Not 7-1 = 6, as selling price needs to be larger than buying price.
Example 2:

Input: [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.

这里只能完成一笔交易
双遍历是很容易，但不好，复杂度是O(n^2)

把数组中的元素连成折线图，感兴趣的点是给定图中的峰和谷。 我们需要找到最小山谷之后的最大峰值。 我们可以维持两个变量 - 分别对应于最小谷值和最大利润（销售价格和最低价格之间的最大差异）的minprice和maxprofit。

像这种计算时必须有一个数在另一个数的后面，就考虑使用空间换时间，多设几个变量保存最值。

对于任何一个数，以它为底的最大差值，一定是它后面的最大的数减它，而且这个最大数和它之间不能有比它小的数


```java
class Solution {
    public int maxProfit(int[] prices) {
        if(prices.length==0) return 0;
        int maxProfit = 0;
        int minPrice = Integer.MAX_VALUE;
        for(int i=0;i<prices.length;i++){
            if(prices[i] < minPrice) minPrice = prices[i];
            else{
                maxProfit = Math.max(maxProfit, prices[i]-minPrice);
            }
        }
        return maxProfit;
    }
}
```

124. Binary Tree Maximum Path Sum
Hard

Given a non-empty binary tree, find the maximum path sum.

For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The path must contain at least one node and does not need to go through the root.

Example 1:

Input: [1,2,3]

       1
      / \
     2   3

Output: 6
Example 2:

Input: [-10,9,20,null,null,15,7]

   -10
   / \
  9  20
    /  \
   15   7

Output: 42

实现一个函数，这个函数会计算以当前节点为最上节点的的路径最长长度

显然只有路径最上面的节点可以是折点

```java
class Solution {
    public int maxPathSum(TreeNode root) {
        getCurrentRootMaxPath(root);
        return max;
    }
    int max = Integer.MIN_VALUE;

    public int getCurrentRootMaxPath(TreeNode root){
        if(root == null) return 0;
        //分别算得以左右儿子为最上节点的路径最长长度,如果某个子树最长长度是负值，那么这个分支就不要了
        int left = Math.max(0, getCurrentRootMaxPath(root.left));
        int right = Math.max(0, getCurrentRootMaxPath(root.right));
        //root.val + left + right 是 当root为路径最上面的节点时的路径最长长度
        max = Math.max(max, root.val + left + right);
        //如果root不是最上面的节点，则返回左分支和右分支里面数值大的一条，作为更上层节点的子分支
        return Math.max(left, right) + root.val;
    }
}
```

128. Longest Consecutive Sequence
Hard

Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

Your algorithm should run in O(n) complexity.

Example:

Input: [100, 4, 200, 1, 3, 2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.

关键在于是未排序的，而且时间复杂度要求O(n),任何一个排序算法无法达到该时间复杂度。如果是用快排的话，可以达到 nlog(n)

不用排序，用HashSet可以达到O(n)的效果，把数组中的所有元素装入HashSet中，然后再遍历数组，当便利到一个时，检查它后面的，以及后面的后面的在不在Set中，
如果在，则以它开头的连续数字长度就一直加

如果遍历的时候一个数字前面的数在set里，那么它一定被检查过了，则continue

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set1 = new HashSet<>();
        for(int i=0;i<nums.length;i++)
            set1.add(nums[i]);

        int maxSequence = 0;
        for(int i=0;i<nums.length;i++){
            int cur = nums[i];
            if(!set1.contains(cur-1)){ //说明它以及被检查过了
                int sequenceLen = 0;
                while(set1.contains(cur)){
                    sequenceLen += 1;
                    cur = cur+1;
                }
                maxSequence = Math.max(maxSequence, sequenceLen);
            }            

        }

        return maxSequence;
    }
}
```


136. Single Number

Given a non-empty array of integers, every element appears twice except for one. Find that single one.

Note:

Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

Example 1:

Input: [2,2,1]
Output: 1
Example 2:

Input: [4,1,2,1,2]
Output: 4

先遍历一遍使用HashMap保存键和出现的次数,然后在遍历一遍，出现次数为1的即为答案，保证是O(n),空间复杂度是O(n)

最好的办法是使用异或

a^a = 0
0^b = b

把所有数字都异或一遍，最后的结果就是只出现过一次的那个数,时间复杂度O(n),空间复杂度O(1)

```java
class Solution {
    public int singleNumber(int[] nums) {
        int sum = 0;
        for(int i=0;i<nums.length;i++){
            sum ^= nums[i];
        }
        return sum;
    }
}
```

139. Word Break
Medium

Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.

Note:

The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.
Example 1:

Input: s = "leetcode", wordDict = ["leet", "code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".
Example 2:

Input: s = "applepenapple", wordDict = ["apple", "pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
             Note that you are allowed to reuse a dictionary word.
Example 3:

Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
Output: false

可以用递归的方法 isChildPattern(int start, int end, String s) 里面匹配是否有以start开头的单词匹配，如果有，则调用isChildPattern去匹配剩下的串，但这种递归会超时


考虑动态规划，dp[i]表示s的前i个字符是否能匹配（匹配指能拆成字典中的单词组合）

双重循环i，j。检查s[j...i], 如果dp[j-1]匹配，且字典中含有s[j...i]，则dp[i]匹配 对于每一个i，j要试从0到i，
只要有一个满足字典中含有s[j...i],且dp[j-1]匹配或j为0，那dp[i]就匹配，最后返回dp[s.len-1]

```java
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()];
        for(int i=0;i<s.length();i++){
            for(int j=0;j<=i;j++){
                String sub = s.substring(j,i+1);
                if(wordDict.contains(sub) && (j==0 || dp[j-1])){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()-1];
    }
}
```

141. Linked List Cycle
Easy

Given a linked list, determine if it has a cycle in it.

To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.

 
Example 1:

Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where tail connects to the second node.
3->2->0->-4
   ↑------|  

Example 2:

Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where tail connects to the first node.


Example 3:

Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.

Follow up:

Can you solve it using O(1) (i.e. constant) memory?

给出一个链表，判断里面有没有环


如果不用O(1)的空间复杂度，则很简单，直接将遍历过的每个节点保存到set里，每遍历到一个节点就看看它在不在set里，在就有环
用O(1)的复杂度，只用两个指针，一个指针一次走两步，一个指针一次走一步，当两个指针能重合，说明有环（龟兔赛跑算法）

```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        if(head==null) return false;
        ListNode p1 = head;
        ListNode p2 = head;

        //一旦发现p1的下个指针为null或者p2下下个指针为null，说明无环
        do{
            if(p1.next!=null){
                p1=p1.next;
            }else{
                return false;
            }

            if(p2.next!=null && p2.next.next!=null){
                p2 = p2.next.next;
            }else{
                return false;
            }
        }while(p1!=p2);

        return true;       
    }
}
```

# 纪念完成Liked 100 Question 的一半！！！

******************************************************************************************



142. Linked List Cycle II
Medium

Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.

Note: Do not modify the linked list.

Example 1:

Input: head = [3,2,0,-4], pos = 1
Output: tail connects to node index 1
Explanation: There is a cycle in the linked list, where tail connects to the second node.


Example 2:

Input: head = [1,2], pos = 0
Output: tail connects to node index 0
Explanation: There is a cycle in the linked list, where tail connects to the first node.


Example 3:

Input: head = [1], pos = -1
Output: no cycle
Explanation: There is no cycle in the linked list.

Follow up:
Can you solve it without using extra space?

基本的想法是使用HashMap，节点当key，索引当value，遍历过一个就放一个，当p出现过时，则说明有环，且p就是环的开头
这个方法没问题，也能AC，但使用了额外的空间

龟兔赛跑算法（Floyd判圈法）

首先，判定环的存在：
fast一次走2步，slow一次走1步
a. 有环的话，快指针和慢指针必然相遇，通过指针相等退出循环；
b. 无环时，快指针将访问非法域，导致异常 

判定有环后，利用上一步的信息（相遇的点），判定入口：
a. 设起点q到环的入口点r的距离为H步，入口r到第一次见面点m的为d步。一圈为C步，设第一次相遇时，Fast相对于r点转了m圈，Slow转了n圈
b. 则第一次相遇时，Slow运动了(H+d+nC)步，Fast运动了(H + mC + d), 由于Fast指针是慢指针Slow的两倍速度, 从而有距离公式：

                      2(H+d+nC) = H + mC + d,
经过简单的移位运算，有：

                       H+d = (m-2n) * C
即：  H = (m-2n) * C - d
H+d = (m-2n) * C

H+d是环长的整数倍

（fast是slow速度的2倍）
这表明，当我们让Slow重新从q点处、Fast继续从相遇见的m点处，都以速度1移动时，当slow到达r点时，走了H步，此时fast从一开始链表起点算，走过的路程是
H + mC + d + H， 而H+d是整数倍的C，则fast走过的路程可以写成 kC + H，相当于fast从起点走到r点（H步），然后在环里绕了k圈又回到了r点，故此时slow和
fast重合，且重合的点是环的入口点m，而且这是slow重新从q出发以后第一次和fast相遇（slow在环外，fast在环内）

》如何计算上述链表中环的长度
    上述可以根据龟兔赛跑算法，知道一快一慢两个指针相遇时一定在环内，标记这个节点，让慢指针继续走，直到下一次到达这个节点时，就可直到环的长度


```java
public class Solution {
    //使用额外空间
    public ListNode detectCycle(ListNode head) {
        HashMap<ListNode, Integer> map = new HashMap<>();
        ListNode p =head;
        for(int i=0;p!=null;i++){
            if(map.containsKey(p)) return p;
            map.put(p, i);
            p = p.next;
        }
        return null;
    }

    //快速方法
     public ListNode detectCycle(ListNode head) {
        if(head==null || head.next==null || head.next.next==null) return null;
        ListNode slow =head;
        ListNode fast = head;

        do{
            if(slow.next!=null) slow = slow.next;
            else return null;

            if(fast.next!=null && fast.next.next!=null) fast = fast.next.next;
            else return null;

        }while(slow!=fast);
        //here slow = fast;
        slow = head;
        while(slow!=fast){
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

}
```

146. LRU Cache
Hard

Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LRUCache cache = new LRUCache( 2 /* capacity */ );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.put(4, 4);    // evicts key 1
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4

最近使用缓存，当超过额度时，会把最远使用的覆盖，可以用一个LinkedList list，一个HashMap map实现
队列按优先级放置key，队头的优先级最低

set时：
若size不满：map.set;list.add
若size满，list.poll，map.remove，然后重新set

get时
若没有：返回-1
若有：将该元素从list中删除，然后再添加进去（为了改变优先级），然后从mapget后返回

```java
    class LRUCache {

        final int size;
        final HashMap<Integer, Integer> map;
        final LinkedList<Integer> list;

        public LRUCache(int capacity) {
            size = capacity;
            map = new HashMap<>();
            list = new LinkedList<>();
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            } else { //使用了值，更新优先级
                int index = list.indexOf(key);
                list.remove(index);
                list.add(key);
                return map.get(key);
            }
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {  //如果是更新值，也算是使用了，更新优先级
                int index = list.indexOf(key);
                list.remove(index);
                list.add(key);
                map.put(key, value);
            } else {
                if (map.size() < size) { //如果容量还够
                    list.add(key);
                    map.put(key, value);
                } else {
                    int oldKey = list.poll();  //如果容量不够，则删除最旧的元素，再次尝试插入新元素
                    map.remove(oldKey);
                    put(key, value);
                }
            }
        }
    }


    //更简单的实现，直接用LinkedHashMap
    class LRUCache extends LinkedHashMap<Integer, Integer> {
        int maxSize;

        LRUCache(int capacity){
            super(capacity, 0.75f, true);
            maxSize = capacity;
        }
        
        Integer get(Integer key){
            return super.get(key)==null?-1:super.get(key);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return this.size() > maxSize;
        }
    }
```

148. Sort List
Medium

Sort a linked list in O(n log n) time using constant space complexity.

Example 1:

Input: 4->2->1->3
Output: 1->2->3->4
Example 2:

Input: -1->5->3->4->0
Output: -1->0->3->4->5

排序算法能达到O(nlgn)的有归并排序，堆排序，快速排序，其空间复杂度分别为O(n),O(1),O(1), 但这里不是双向链表，所以应该不能用快排
，考虑并归排序，不足之处在于并归的空间复杂度不是常数级别的，把链表分成两个部分，，每一部分的排序又把其分成两个部分，最后综合成一个链表

这里把将两个链表排序的思想用在了一个链表的排序上


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
    public ListNode sortList(ListNode head) {
        head = mergeSort(head);
        return head;
    }

    //@return 头结点
    public ListNode mergeSort(ListNode head){
        //空或者只有一个节点则不用排序，直接返回
        if(head==null || head.next==null) return head;
        //构造两个指针，一个快一个慢，fast一次走两步，slow一次走1步，当fast走到链表尾时，slow正好就在链表中间
        //这个长度为奇偶的时候各不一样，但只要fast走不动时，slow就一定在中间
        ListNode fast = head;
        ListNode slow = head;
        while(slow!=null && fast!=null){
            if(fast.next!=null && fast.next.next!=null){
                //要是满足这个条件，slow.next绝对不是null
                fast = fast.next.next;
                slow = slow.next;
            } else
                break;
        }

        //分别是要并归的两个序列头结点，这里吧slow.next=null是为了让左右两个序列彻底分开，以便于归并
        //这里左序列长度可能比右序列长1（序列长为奇数），或者相等（偶数）
        ListNode right = slow.next;
        slow.next = null;
        ListNode left = head;

        //将左右两个序列排好序
        left = mergeSort(left);
        right = mergeSort(right);

        //假头结点便于操作
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        //归并两个链表
        while(right!=null || left!=null){
            if(left==null){
                cur.next = right;
                break;
            } else if(right==null){
                cur.next = left;
                break;
            }

            if(right.val < left.val){
                cur.next = right;
                right = right.next;
            } else{
                cur.next = left;
                left = left.next;
            }
            cur = cur.next;
        }
        return dummy.next;
    }
}
```

152. Maximum Product Subarray
Medium

Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which has the largest product.

Example 1:

Input: [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.
Example 2:

Input: [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.

给定整数数组nums，找到具有最大乘积的数组（包含至少一个数字）内的连续子数组。
如果用n^2的遍历，肯定能做出来，但效率不高

用max[i]表示 第i个数与它前面的数能构成的最大乘积
用min[i]表示 第i个数与它前面的数能构成的最小乘积

max[i] = Math.max(max[i-1]*nums[i], min[i-1]*nums[i], nums[i])

min[i] = Math.min(max[i-1]*nums[i], min[i-1]*nums[i], nums[i])

保存最小乘积是为了避免由于负负得正而漏情况


```java
class Solution {
    public int maxProduct(int[] nums) {
        int[] max = new int[nums.length];
        int[] min = new int[nums.length];
        max[0] = nums[0];
        min[0] = nums[0];

        for(int i=1;i<nums.length;i++){
            int tmp = Math.max(max[i-1]*nums[i], min[i-1]*nums[i]);
            max[i] = Math.max(tmp, nums[i]);

            tmp = Math.min(max[i-1]*nums[i], min[i-1]*nums[i]);
            min[i] = Math.min(tmp, nums[i]);
        }

        int maxV = Integer.MIN_VALUE;
        for(int i=0;i<nums.length;i++){
            maxV = Math.max(maxV, max[i]);
        }
        return maxV;
    }
}
```

155. Min Stack
Easy

Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
getMin() -- Retrieve the minimum element in the stack.
Example:
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> Returns -3.
minStack.pop();
minStack.top();      --> Returns 0.
minStack.getMin();   --> Returns -2.

定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。

用两个栈，一个主栈数据栈，一个辅助栈
把每次的最小元素（之前的最小元素和新压入栈的元素两者的较小值）都保存起来放到另一个辅助栈中

假设入栈3，4，2，1

首先往空的数据栈中压入数字3，显然现在3是最小值，也把这个数压入辅助栈。然后往数据栈压入数字4，比较4和辅助栈的栈顶元素，发现4大于3，因此仍然往辅助栈中压入3。第三步往数据栈中压入2，2小于辅助栈栈顶元素3，因此把2压入辅助栈。同样，压入数字1时，也要把1压入辅助栈

辅助栈栈顶永远是当前栈中元素的最小值。
弹栈时，不仅弹主栈，也弹辅助栈。
求最小值时，直接返回辅助栈的栈顶元素即可（不弹）

```java
    class MinStack {
    Stack<Integer> mainStk = new Stack<>();
    Stack<Integer> helperStk = new Stack<>();

    public void push(int node) {
        mainStk.push(node);
        if(helperStk.isEmpty() || helperStk.peek()>node){
            helperStk.push(node);
        }else{
            int min = helperStk.peek();
            helperStk.push(min);
        }
    }

    public void pop() {
        if(!mainStk.isEmpty()){
            mainStk.pop();
            helperStk.pop();
        }
    }

    public int top() {
        return mainStk.peek();
    }

    public int getMin() {
        return helperStk.peek();
    }
    }
```


160. Intersection of Two Linked Lists
Easy

Write a program to find the node at which the intersection of two singly linked lists begins.

For example, the following two linked lists:


begin to intersect at node c1.

Example 1:

Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
Output: Reference of the node with value = 8
Input Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,0,1,8,4,5]. There are 2 nodes before the intersected node in A; There are 3 nodes before the intersected node in B.
 
Example 2:

Input: intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
Output: Reference of the node with value = 2
Input Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [0,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the intersected node in A; There are 1 node before the intersected node in B.
 
Example 3:

Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
Output: null
Input Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two lists do not intersect, intersectVal must be 0, while skipA and skipB can be arbitrary values.
Explanation: The two lists do not intersect, so return null.
 
Notes:

If the two linked lists have no intersection at all, return null.
The linked lists must retain their original structure after the function returns.
You may assume there are no cycles anywhere in the entire linked structure.
Your code should preferably run in O(n) time and use only O(1) memory.

就是找两个链表的第一个公共节点，两个链表有公共节点，则一定是Y型的样子
A从头到公共节点距离为ta，B从头到公共节点距离为tb，公共节点到尾距离为l
方法一：
保持两个指针pA和pB分别在A和B的头部初始化。 然后让它们遍历列表，一次遍历一个节点。
当pA到达列表的末尾时，然后将其重定向到B的头部（是的，B，这是正确的。）; 类似地，当pB到达列表的末尾时，将其重定向到A的头部。
如果pA在任何时候遇到pB，则pA即pB是交叉点节点。此时pA走了：ta+l+tb,pB走了tb+l+ta

方法二：
设长的链表长度为x，短的为y，指针p1，p2分别指向x，y的头结点，一次一步，当p2到达链表末尾时，走过的路程是y，p1走过的路程也是y，剩余x-y
此时把p2定到长链表的头部，当p1走到末尾时，p2走过的路程是x-y，再把p1定到短链表的开头，那么此时正好x和y距离链表尾是相同的距离，再让它们一步一走直到相遇，即是第一个公共节点



```java
public class Solution {
   public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
       if(p1==null || p2==null) return null;
        while(p1.next!=null && p2.next!=null){
            p1=p1.next;
            p2=p2.next;
        }
        //headA短
        if(p1.next==null){
            p1 = headB;
            while(p2.next!=null){
                p1=p1.next;
                p2=p2.next;
            }
            p2 = headA;
            while(p1!=p2){
                p1=p1.next;
                p2=p2.next;
            }
            if(p1==p2) return p1;
            else return null;
        } else{ //head2短
            p2 = headA;
            while(p1.next!=null){
                p1=p1.next;
                p2=p2.next;
            }
            p1 = headB;
            while(p1!=p2){
                p1=p1.next;
                p2=p2.next;
            }
            if(p1==p2) return p1;
            else return null;
        }
    }
}
```

169. Majority Element
Easy

Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

You may assume that the array is non-empty and the majority element always exist in the array.

Example 1:

Input: [3,2,3]
Output: 3
Example 2:

Input: [2,2,1,1,1,2,2]
Output: 2

一个可以想到的简单办法是排序，然后计数，这样复杂度是O(NlogN) + O(N) (排序+遍历)

更简单的办法是，维护一个计数和一个数x，若遍历到的数为x，计数+1，否则减一，计数为0时，更换数x为遍历到的数，这样，最后x的数就是主数
思想是，主数出现过超过一半的次数，则它可以和每个不是主数的数字抵消，最后还剩下至少1个

```java
class Solution {
    public int majorityElement(int[] nums) {
        int major = -54353;
        int count = 0;
        for(int i=0;i<nums.length;i++){
            int cur = nums[i];
            if(major==cur){
                count++;
            }else{
                if(count==0){
                    major = cur;
                    count=1;
                }else{
                    count--;
                }
            }
        }
        return major;
    }
}
```

198. House Robber
Easy  

Share
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.

Example 1:

Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.
Example 2:

Input: [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
             Total amount you can rob = 2 + 9 + 1 = 12.

这个题一点都不easy

给你一个数组，让取出和最大的数字组合，而且不能取相邻的数字，看起来直接算一遍奇数位置和偶数位置的和，谁大取谁即可,
但这么做不行，因为只要不邻着就行，隔一个或以上都可以，如 [2,1,1,2]，最大的数是2+2=4

用动态规划，dp[i]表示截止到num[i]能达到的最大的和

要么是抢nums[i],要么是不抢，如果抢，那就是dp[i-2]+nums[i]; 如果不抢，那就是dp[i]

dp[i] = max(dp[i-1], dp[i-2]+nums[i]);

dp[0]=nums[0];
dp[1]=nums[1];

上面动态规划不是最好的方法，还可以优化

因为一个状态只与它前两个状态有关，所以可以变成常数空间复杂度

对于遍历到的下标为i的数x来说，include表示截止x，包含x的最大值，exclude表示不包含x的最大值
则对下一个数字y（下标为i+1）来说，include（截止到y，包含y的最大值）=exclude+num[i+1]  (截止到x不包含x的最大值+y)
                  exclude（截止到y，不包含y的最大值）=Math.max ( 前一个include(截止到x包含x的最大值) ,前一个exclude(截止到x不包含x的最大值)


```java
class Solution {

    //一般的动态规划
    public int rob(int[] nums) {

        if(nums.length==0) return 0;
        else if(nums.length==1) return nums[0];
        else if(nums.length==2) return nums[0]>nums[1]?nums[0]:nums[1];

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i-2]+nums[i], dp[i-1]);
        }
        return dp[nums.length-1];
    }



    //最好的方法
    public int rob(int[] num) {

        if(nums.length==0) return 0;
        else if(nums.length==1) return nums[0];
        else if(nums.length==2) return nums[0]>nums[1]?nums[0]:nums[1];
        int lo = 0;
        int hi = num.length-1;

        int include = 0, exclude = 0;

        for (int j = lo; j <= hi; j++) {
            int i = include, e = exclude;
            include = e + num[j];
            exclude = Math.max(e, i);
        }
        return Math.max(include, exclude);
    }
}
```

200. Number of Islands
Medium

Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:

Input:
11110
11010
11000
00000

Output: 1
Example 2:

Input:
11000
11000
00100
00011

Output: 3

计算块的，用广度优先遍历，遍历过的位置标记，如果是1则加入队列，如果是0则跳过
需要注意的是，对于一个块来说，斜方的不算和它同一块的

```java
class Solution {
    boolean[][] flag;
    public int numIslands(char[][] grid) {
        
        int row = grid.length;
        if(row==0) return 0;
        int col = grid[0].length;
        if(col==0) return 0;
        flag = new boolean[grid.length][grid[0].length];
        int count = 0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(flag[i][j]) continue;
                if(grid[i][j] == '0'){
                    flag[i][j] = true;
                    continue;
                }else{
                    count += 1;
                    flag[i][j] = true;
                    BFS(grid, i, j, row, col);
                }
            }
        }
        return count;
    }

    void BFS(char[][] grid, int i, int j, int row, int col){
        LinkedList<int[]> queue = new LinkedList<>();
        int[] cur = new int[]{i,j};
        queue.add(cur);
        while(!queue.isEmpty()){
            cur = queue.poll();
            int curI = cur[0];
            int curJ = cur[1];
            int top = curI-1;
            int bottom = curI+1;
            int left = curJ-1;
            int right = curJ+1;
            if(top >= 0 && grid[top][curJ]=='1' && !flag[top][curJ]){
                flag[top][curJ] = true;
                queue.add(new int[]{top, curJ});
            }

            if(right < col && grid[curI][right]=='1' && !flag[curI][right]){
                flag[curI][right] = true;
                queue.add(new int[]{curI, right});
            }
            if(bottom < row && grid[bottom][curJ]=='1' && !flag[bottom][curJ]){
                flag[bottom][curJ] = true;
                queue.add(new int[]{bottom, curJ});
            }
            if(left >= 0 && grid[curI][left]=='1' && !flag[curI][left]){
                flag[curI][left] = true;
                queue.add(new int[]{curI, left});
            }
        }
    }
}
```

207. Course Schedule
Medium

There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

Example 1:

Input: 2, [[1,0]] 
Output: true
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0. So it is possible.
Example 2:

Input: 2, [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.
Note:

The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.

这里也用广度优先遍历，每遍历到一个节点时，把它上级节点的出度-1，每次只对出度为0的节点进行扫描，如果最终扫描完的节点数等于全部节点数，则说明是可以的.

这里用的是出度，一般情况下拓扑排序用入度，不过其实都是一样的。

这个题的本质是拓扑排序，解法也是典型的拓扑排序的解法，也就是不断去除前驱


```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList[] preRequisites = new ArrayList[numCourses]; //保存每个节点的前驱节点列表
        int[] outDegree = new int[numCourses]; //出度，边是从前驱课程指向后继课程

        //初始化
        for(int i=0;i<numCourses;i++){
            preRequisites[i] = new ArrayList<>();
        }


        for(int i = 0; i< prerequisites.length;i++){
            int from = prerequisites[i][1];
            int to = prerequisites[i][0];
            preRequisites[to].add(from);
            outDegree[from]++;
        }
        int count = 0;
        LinkedList<Integer> queue = new LinkedList<>();

        for(int i=0;i<numCourses;i++){
            if(outDegree[i]==0){
                queue.add(i);
                count++;
            }
        }

        while(!queue.isEmpty()){
            int cur = queue.poll();
            for(int i=0;i<preRequisites[cur].size();i++){
                int pre = (int)preRequisites[cur].get(i);
                outDegree[pre]--;
                //加入出度降为0的节点
                if(outDegree[pre]==0){
                    queue.add(pre);
                    count++;
                }
            }
        }
        return count==numCourses;
    }
}
```

208. Implement Trie (Prefix Tree)
Medium

Implement a trie with insert, search, and startsWith methods.

Example:

Trie trie = new Trie();

trie.insert("apple");
trie.search("apple");   // returns true
trie.search("app");     // returns false
trie.startsWith("app"); // returns true
trie.insert("app");   
trie.search("app");     // returns true
Note:

You may assume that all inputs are consist of lowercase letters a-z.
All inputs are guaranteed to be non-empty strings.

前缀树

Trie（我们发音为“try”）或前缀树是树数据结构，用于检索字符串数据集中的键。这种非常有效的数据结构有各种各样的应用

trie：字典树（特里结构、单词查找树）

虽然哈希表在查找键值对时具有O（1）时间复杂度，但在以下操作中效率不高：

1. 查找具有公共前缀的所有密钥。
2. 按字典顺序枚举字符串数据集。

trie优于散列表的另一个原因是，随着散列表的大小增加，存在大量的散列冲突，并且搜索时间复杂度可能恶化为O（n），其中n是插入的键的数量。 当存储具有相同前缀的许多键时，Trie可以使用比Hash Table更少的空间。 在这种情况下，使用trie仅具有O（m）时间复杂度，其中m是密钥长度。 在平衡树中搜索密钥会花费O（m \ log n）O（mlogn）时间复杂度。

Trie节点结构
Trie是一棵有根的树。 其节点具有以下字段：

》到其子节点的R链接的最大值，其中每个链接对应于数据集字母表中的一个RR字符值。 在本文中，我们假设RR为26，即小写拉丁字母的数量。
》布尔字段，指定节点是对应于键的末尾，还是仅仅是键前缀。

https://leetcode.com/problems/implement-trie-prefix-tree/solution/

https://blog.csdn.net/johnny901114/article/details/80711441

极大地复用了空间，只要任意两个前缀相同的单词，都共享它们相同的前缀的节点。

这种前缀树的数据结构是非常好用的，一定要掌握

下面是需要绝对掌握的版本：
```java
    class Trie{
        Trie[] nodes= new Trie[26];
        boolean isWord = false;

        Trie(){

        }

        void insert(String word){
            Trie t = this;
            for(int i=0;i<word.length();i++){
                int num = word.charAt(i)-'a';
                if(t.nodes[num]==null) t.nodes[num] = new Trie();
                t = t.nodes[num];
                if(i==word.length()-1) t.isWord = true;
            }
        }

        boolean startsWith(String prefix){
            Trie t = this;
            for(int i=0;i<prefix.length();i++){
                int num = prefix.charAt(i)-'a';
                if(t.nodes[num]==null) return false;
                t = t.nodes[num];
            }
            return true;
        }

        boolean search(String word){
            Trie t = this;
            for(int i=0;i<word.length();i++){
                int num = word.charAt(i)-'a';
                if(t.nodes[num]==null) return false;
                t = t.nodes[num];
            }

            return t.isWord;
        }

        //符合特殊要求的字典树：211（涉及到了模式匹配）
    }
```


下面是树和节点分开的版本，理解即可
树中节点的数据结构：

```java
class TrieNode {

    // R links to node children   每个节点有26个子节点的位置
    private TrieNode[] links;

    private final int R = 26;

    private boolean isEnd;

    public TrieNode() {
        links = new TrieNode[R];
    }

    public boolean containsKey(char ch) {
        return links[ch -'a'] != null;
    }
    public TrieNode get(char ch) {
        return links[ch -'a'];
    }
    public void put(char ch, TrieNode node) {
        links[ch -'a'] = node;
    }
    public void setEnd() {
        isEnd = true;
    }
    public boolean isEnd() {
        return isEnd;
    }
}
```

树的数据结构：

```java
class Trie {
    private TrieNode root;

    public Trie() {
        //根节点本身不代表任何字母，只有处于link数组中，有数组编号的节点才代表对应的字母
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (!node.containsKey(currentChar)) {
                node.put(currentChar, new TrieNode());
            }
            node = node.get(currentChar);
        }
        node.setEnd();
    }

    // search a prefix or whole key in trie and
    // returns the node where search ends
    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
           char curLetter = word.charAt(i);
           if (node.containsKey(curLetter)) {
               node = node.get(curLetter);
           } else {
               return null;
           }
        }
        return node;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
       TrieNode node = searchPrefix(word);
       return node != null && node.isEnd();
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }
}
```

215. Kth Largest Element in an Array
Medium

Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

Example 1:

Input: [3,2,1,5,6,4] and k = 2
Output: 5
Example 2:

Input: [3,2,3,1,2,4,5,5,6] and k = 4
Output: 4
Note: 
You may assume k is always valid, 1 ≤ k ≤ array's length.

一般遇到这种第k大的，就可以考虑堆排序了，或者快排也行

无法忍受的是，中国的网上大部分流传的堆排序是错误的，
在这里找到对的：
https://www.geeksforgeeks.org/heap-sort/

```java
// Java program for implementation of Heap Sort 
class Solution {
    public int findKthLargest(int[] nums, int k) {
        sort(nums);
        int index = nums.length-1-(k-1);
        System.out.println(Arrays.toString(nums));
        return nums[index];
    }


    public void sort(int arr[])
    {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    void heapify(int arr[], int n, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2*i + 1; // left = 2*i + 1
        int r = 2*i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i)
        {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
}


```


221. Maximal Square
Medium

1113

29

Favorite

Share
Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

Example:

Input: 

1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

Output: 4

以dp[i][j] 记录以第i行第j列的元素为右下角的最大正方形的边长

如果
leftOne[i][j] 表示nums[i][j]从右往左数有几个连着的1（算自己）
topOne[i][j] 表示nums[i][j]从下往上数有几个连着的1（算自己）

当nums[i][j]=0,则leftOne[i][j]=0; topOne[i][j]=0;
当nums[i][j]=1，则leftOne[i][j]=leftOne[i][j-1]+1; topOne[i][j]=topOne[i-1][j]+1;

如果nums[i][j]=0,则dp[i][j]=0;
如果nums[i][j]=1
```java
int pre = dp[i-1][j-1];
//左连1须大等于左上角的边长，右连1须大等于左上角的边长，且左上角边长大等于1，才不会进入循环，否则就进入表示左上角边长太大，需要减
//这个过程代入个例子想一想很简单的。
while(!(leftOne[i][j-1] >= pre && topOne[i-1][j] >= pre) && pre>=1){
    pre--;
}
dp[i][j] = pre+1;
```



1 1 1 1 1 1 1
1 1 1 1 1 1 1
1 1 1 1 1 1 1
0 0 1 1 0 0 1

当遍历到[3][3]时，它斜上方元素是[2][2],以[2][2]为右下角的正方形最大边长是3
但显然以[3][3]为右下角的正方形无法包括它，所以将[2][2]为右下角的正方形边长-1再试，还是无法包括它
再-1再试，此时[2][2]为右下角的正方形边长是1，则以[3][3]为右下角的正方形可以包括它了，边长是1+1=2

```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        if(matrix.length==0 || matrix[0].length==0)return 0;
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] leftOne = new int[row][col];
        int[][] topOne = new int[row][col];
        int[][] dp = new int[row][col];

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(matrix[i][j]=='1'){
                    if(j==0){
                        leftOne[i][j]=1;
                    }else{
                        leftOne[i][j]=leftOne[i][j-1]+1;
                    }
                    if(i==0){
                        topOne[i][j]=1;
                    }else{
                        topOne[i][j]=topOne[i-1][j]+1;
                    }
                }
            }
        }

        int max = 0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(matrix[i][j]=='1'){
                    if(i==0 || j==0){
                        dp[i][j]=1;
                    }else{
                        int pre = dp[i-1][j-1];
                        while(!(leftOne[i][j-1] >= pre && topOne[i-1][j] >= pre) && pre>=1){
                            pre--;
                        }
                        dp[i][j] = pre+1;
                    }
                }
                max = Math.max(dp[i][j], max);
            }
        }
        return max*max;
    }
}
```

226. Invert Binary Tree
Easy

Invert a binary tree.

Example:

Input:

     4
   /   \
  2     7
 / \   / \
1   3 6   9
Output:

     4
   /   \
  7     2
 / \   / \
9   6 3   1
Trivia:
This problem was inspired by this original tweet by Max Howell:

Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary tree on a whiteboard so f*** off.

翻转二叉树

不断地交换左右孩子

```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        invert(root);
        return root;
    }

    public void invert(TreeNode root){
        if(root==null) return;
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invert(root.left);
        invert(root.right);
    }
}
```
234. Palindrome Linked List
Easy

Given a singly linked list, determine if it is a palindrome.

Example 1:

Input: 1->2
Output: false
Example 2:

Input: 1->2->2->1
Output: true
Follow up:
Could you do it in O(n) time and O(1) space?

回文链表，如果没有下面O(1)空间复杂度的要求则很简单，直接把它放进数组里，只要两个下标和是length-1的元素相等，则是回文
先遍历一遍知道长度，然后再便历一遍把指针p放在第一个元素上，逐个把前半段元素放入栈，直到到后半段，则让前半段元素出栈
此时出栈的顺序是倒过来的，就可以和后半段元素一一比对了

如果再有一个序列倒过来和另一个序列相同的题，可以考虑栈

反转链表法，将链表后半段原地翻转，再将前半段、后半段依次比较，判断是否相等，时间复杂度O（n），空间复杂度为O（1）满足题目要求。

```java
class Solution {
    //栈做法
    public boolean isPalindrome(ListNode head) {
        ListNode p = head;
        Stack<Integer> preHalf = new Stack<>();
        int length = 0;
        while (p != null) {
            length++;
            p = p.next;
        }

        p = head;
        int preCount = 0;
        while (preCount < length / 2) {
            preHalf.push(p.val);
            p = p.next;
            preCount++;
        }
        //奇数个元素则跳过最中间的
        if (length % 2 == 1) p = p.next;

        while (!preHalf.empty()) {
            if (preHalf.pop() != p.val) return false;
            p = p.next;
        }
        return true;
    }
}
```


236. Lowest Common Ancestor of a Binary Tree
Medium

Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]


 

Example 1:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.
Example 2:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.


给定一棵树，两个节点，找到这两个节点的最低公共祖先节点（LCA），需要注意的是，一个节点算是它自己的祖先

》方法一：
维护一个本节点与其祖先的HashMap<TreeNode, TreeNode> map，k是本节点，v是其祖先节点，先用层序遍历把这个map给做好
过程中把q和p的层高找到,先让低的追溯祖先让二者层高相同，然后两个一起追溯，直到节点相同，相同的就是公共祖先
时间复杂度O(n),空间复杂度O(n)

》方法二：
对于二叉树中的节点，当以后序遍历遍历到一个节点时，此时栈中的元素就是该节点的祖先链，因此考虑用后续遍历得到两个节点的祖先链，然后再从头开始算出最远的相同节点
即为最低公共祖先（需要注意的是，一个节点的祖先链包括自己，所以对普通的后续遍历需要小改造一下）

```java
//方法一：
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) return null;
        LinkedList<TreeNode> queue1 = new LinkedList<>();
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        HashMap<TreeNode, TreeNode> map = new HashMap<>();
        queue1.add(root);
        int pLevel = 0;
        int qLevel = 0;
        TreeNode tmp;
        int curLevel = 1;
        LinkedList<TreeNode> curQueue;
        LinkedList<TreeNode> anotherQueue; 
        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            curQueue = queue1.isEmpty()?queue2:queue1;
            anotherQueue = queue1.isEmpty()?queue1:queue2;
            while (!curQueue.isEmpty()) {
                tmp = curQueue.poll();
                if (tmp == p) {
                    pLevel = curLevel;
                }
                if (tmp == q) {
                    qLevel = curLevel;
                }
                if (tmp.left != null) {
                    map.put(tmp.left, tmp);
                    anotherQueue.add(tmp.left);
                }
                if (tmp.right != null) {
                    map.put(tmp.right, tmp);
                    anotherQueue.add(tmp.right);
                }
            }
            curLevel++;
        }
        TreeNode p1 = p;
        TreeNode p2 = q;
        while (pLevel != qLevel) {
            if (pLevel > qLevel) {
                pLevel--;
                p1 = map.get(p1);
            } else {
                qLevel--;
                p2 = map.get(p2);
            }
        }

        while (p1 != p2) {
            p1 = map.get(p1);
            p2 = map.get(p2);
        }
        return p1;
    }
}
```

```java
//方法二：
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

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode t1, TreeNode t2){
        if (root == null || t1 == null || t2 == null) return null;

        TreeNode p = root;
        TreeNode pre = null;
        Stack<TreeNode> stk = new Stack<>();
        boolean findOne = false;
        ArrayList<TreeNode> l1 = new ArrayList<>();
        ArrayList<TreeNode> l2 = new ArrayList<>();

        //后续遍历
        while (p!=null || !stk.isEmpty()){
            while (p!=null){
                stk.push(p);
                p=p.left;
            }
            TreeNode tmp = stk.peek().right;
            if(tmp==null || pre==tmp){
                //visit
                p = stk.pop();
                if(p==t1 || p==t2){
                    stk.push(p); //因为节点本身也算自己的祖先，所以这里要先添进去，保证祖先链有自己
                    if(p==t1) l1 = new ArrayList<TreeNode>(stk);
                    else l2 = new ArrayList<TreeNode>(stk);
                    if(findOne==true){
                        break;
                    }
                    findOne = true;
                    stk.pop();
                }
                pre = p;
                p = null;
            }else {
                p = tmp;
            }
        }


        int i=0;
        int minLen = Math.min(l1.size(), l2.size());
        for(;i<minLen;i++){
            TreeNode a1 = l1.get(i);
            TreeNode a2 = l2.get(i);
            if(a1!=a2){
                break;
            }
        }
        return l1.get(i-1);
    }

}
```




238. Product of Array Except Self
Medium

Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].

Example:

Input:  [1,2,3,4]
Output: [24,12,8,6]
Note: Please solve it without division and in O(n).

Follow up:
Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose of space complexity analysis.)

给一个数组a，输出另一个数组b, b[i]=Πa[x] / a[i]

这里的关键在于碰到0怎么办,如果多于1个0，那么答案数组肯定全是0，如果只有一个0，则除了0位置对应的元素是其他元素乘积，其他位置都是0
如果没有0，则每个位置对应所有乘积/该位置元素

更简单的办法见下面，只需要两遍循环，第一遍循环正序，让b[i] = a[0] * a[1] ... * a[i-1]

第二遍循环倒序，让 b[i] = a[0] * a[1] ... * a[i-1] * a[i+1] * ... * a[length-1]

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        long totalProduct = 1; //记录所有非0数的乘积
        int zeroCount = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=0){
                totalProduct *= nums[i];
            }else{
                zeroCount++;
            }
        }
        int[] ans = new int[nums.length];

        for(int i=0;i<nums.length;i++){
            if(nums[i]==0 && zeroCount > 1) ans[i]=0;
            else if(nums[i]==0 && zeroCount == 1) ans[i] = (int) totalProduct;
            else if(nums[i]!=0 && zeroCount > 0) ans[i] = 0;
            else if(nums[i]!=0 && zeroCount==0) ans[i] = (int) (totalProduct / nums[i]);
        }
        return ans;
    }


    //更容易的办法：

    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        //第一遍循环，result[i]等于nums[i]前面数的乘积
        for (int i = 0, tmp = 1; i < nums.length; i++) {
            result[i] = tmp;
            tmp *= nums[i];
        }
        //第二遍循环，result[i]再乘以nums[i]后面数的乘积
        for (int i = nums.length - 1, tmp = 1; i >= 0; i--) {
            result[i] *= tmp;
            tmp *= nums[i];
        }
        return result;
    }
}

``` 

239. Sliding Window Maximum
Hard

Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.

Example:

Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
Output: [3,3,5,5,6,7] 
Explanation: 

Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
Note: 
You may assume k is always valid, 1 ≤ k ≤ input array's size for non-empty array.

Follow up:
Could you solve it in linear time?

给定一个数组，有一个大小为k的滑动窗口，它从数组的最左边移动到最右边。你只能在窗口看到k个数字。每次滑动窗口右移一个位置，算一个新的窗口。返回每个窗口中的最大数值组成的数组。

例如：A = [2,1,3,4,6,3,8,9,10,12,56]，w = 4

将数组分区为大小为w = 4的块。 最后一个块可能少于w。
2,1,3,4 | 6,3,8,9 | 10,12,56 |

从头到尾遍历列表并计算max_so_far。 在每个块边界（w个元素）之后重置最大值。
left_max [] = 2,2,3,4 | 6,6,8,9 | 10,12,56

类似地，通过从末端到开始遍历来计算未来的最大值。
right_max [] = 4,4,4,4 | 9,9,9,9 | 56,56,56

现在，在当前窗口中的每个位置i处滑动最大值，sliding-max（i）= max {right_max（i），left_max（i + w-1）}
sliding_max = 4,6,6,8,9,10,12,56

left_max[i]表示以nums[i]为右边界，左边界为nums[i]所在块的左边界  这个区间中最大的数
right_max[i]表示以nums[i]为左边界，右边界为nums[i]所在块的右边界 这个区间中最大的数。

则对于任意ans[i]，则是nums[i]为左边界，nums[i+w-1]为右边界的区间中的最大数

比如对 4,1,2,3 | 6,3,8,9 | 10,12,56 |
ans[2]对应的区间是： 2 3 | 6 3，left[5]=6 , right[2]= 4  故ans[2]=6

对于该区间，计算的是，2<-3中最大的值，与6->3中最大的值，即为6
对于区间 1,2,3 | 6 计算的是 1<-2<-3中最大的值与6 即为6

对于不跨区间的  4,1,2,3 | ，即ans[0]  left[3] = 4, right[0]=4 

一定要学会这种方法解决类似的问题：滑动窗口中的最值，核心在于划分区域+左右遍历

```java

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums.length==0) return new int[]{};
        int[] left_max = new int[nums.length];
        int[] right_max = new int[nums.length];
        int[] ans = new int[nums.length-k+1];

        for (int i = 0; i < nums.length; i++) {
            if (i % k == 0) left_max[i] = nums[i];
            else {
                left_max[i] = Math.max(left_max[i - 1], nums[i]);
            }
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            if (i % k == (k - 1) || i == nums.length - 1) right_max[i] = nums[i];
            else {
                right_max[i] = Math.max(right_max[i + 1], nums[i]);
            }
        }

        for (int i = 0; i < ans.length; i++) {
            ans[i] = Math.max(left_max[k + i - 1], right_max[i]);

        }
        return ans;
    }
}

```

240. Search a 2D Matrix II
Medium

Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

Integers in each row are sorted in ascending from left to right.
Integers in each column are sorted in ascending from top to bottom.
Example:

Consider the following matrix:

[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
Given target = 5, return true.

Given target = 20, return false.

二维数组中的搜索，每一行是顺序序列，每一列也是顺序序列

先在第一行上二分搜索，找出范围i1，再在最后一行上搜索，找出范围i2，比如对于5，
在第一行的二分搜索中，能确定它（要是存在的话）一定在第0-1列，
在最后一行的二分搜索中，能确定它一定在0-4列，取交集，则在0-1列，然后每一列再用2分搜索

时间复杂度是logN * log N


上面的办法太繁琐复杂，直接用下面的：
从第一行的最右边开始逐个找，如果小了则往下移，如果大了往左移
也可以从第一列最下边开始逐个找，如果小了右移，大了上移,
能这么做的核心在于，不走回头路，不会出现下移，左移，但然后需要右移的情况

先左移，左移一位就相当于放弃了该列（因为在第一行移，如果小于该列最小的数，那么一定小于该列所有的数），
下移一位就相当于放弃该行，因为右边的列已经被放弃掉了，而左边的该行的数又太小，所以只能下移

右移和上移也是相同的道理


```java
class Solution {
    //方法一，双重二分
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix.length ==0 || matrix[0].length ==0) return false;
        int row = matrix.length;
        int col = matrix[0].length;
        if(matrix[0][0] > target || matrix[row-1][col-1] < target) return false;
        int firstRight = 0; //第一排的右边界
        int lastLeft = 0;  //最后一排的左边界

        int low = 0;
        int high=col-1;
        int mid;
        while(high>low){
            mid = (low+high)/2;
            if(matrix[0][mid]>target) high=mid-1;
            else if(matrix[0][mid]<target) low=mid+1;
            else return true;
        }
        //此时的有效范围是0-low
        if(matrix[0][low]==target) return true;
        firstRight = matrix[0][low]>target?low-1:low;

        low = 0;
        high=col-1;
        while(high>low){
            mid = (low+high)/2;
            if(matrix[row-1][mid]>target) high=mid-1;
            else if(matrix[row-1][mid]<target) low=mid+1;
            else return true;
        }
        if(matrix[row-1][low]==target) return true;
        lastLeft = matrix[row-1][low]<target?low+1:low;

        int left = Math.max(lastLeft, 0);
        int right = Math.min(firstRight, col-1);

        //此时对 matrix[0][i] ~ matrix[row-1][i] 进行二分搜索， left<=i<=right
        boolean ans = false;
        for(int i=left; i<=right; i++){
            ans = binarySearch(i, matrix, row, col, target);
            if(ans) return true;
        }
        return false;
    }

    public boolean binarySearch(int curCol, int[][] matrix, int row, int col, int target){
        int low = 0;
        int high=row-1;
        int mid;
        while(high>low){
            mid = (low+high)/2;
            if(matrix[mid][curCol]>target) high=mid-1;
            else if(matrix[mid][curCol]<target) low=mid+1;
            else return true;
        }
        return matrix[low][curCol]==target;
    }


    //方法二
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix.length ==0 || matrix[0].length ==0) return false;

        int m=matrix.length, n=matrix[0].length, i=0, j=n-1;
        while (i<m && j>=0) {
            if (matrix[i][j]==target) return true;
            else if (matrix[i][j]<target) i++;
            else j--;
        }
        return false;
    }

    //方法三
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix.length ==0 || matrix[0].length ==0) return false;

        int m=matrix.length, n=matrix[0].length, i=m-1, j=0;
        while (i>=0 && j<n) {
            if (matrix[i][j]==target) return true;
            else if (matrix[i][j]<target) j++;
            else i--;
        }
        return false;
    }

}
```

279. Perfect Squares
Medium

Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.

Example 1:

Input: n = 12
Output: 3 
Explanation: 12 = 4 + 4 + 4.
Example 2:

Input: n = 13
Output: 2
Explanation: 13 = 4 + 9.


给定一个数，它最少能被几个完全平方数的和表示出来

对于数x来说，其完全平方和的表达式若为f(x),如f(12)=4+4+4
对于每一个i，循环j从1到j^2==i, f(i)=j * j + f(i - j * j)，因此，若dp[i-j^2]是i-j^2中的最少的完全平方和个数，则dp[i] = min(dp[i-j^2]+1)

```java
class Solution {
    public int numSquares(int n) {
        int[] minPerfect = new int[n+1];
        for(int i=1;i<n+1;i++){
            minPerfect[i] = Integer.MAX_VALUE;
        }
        //minPerfect[0] = 0

        for(int i=1;i<n+1;i++){
            for(int j=1; j*j <= i;j++){
                minPerfect[i] = Math.min(minPerfect[i], minPerfect[i-j*j]+1);
            }
        }
        return minPerfect[n];
    }
}
```

283. Move Zeroes
Easy

Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.

Example:

Input: [0,1,0,3,12]
Output: [1,3,12,0,0]
Note:

You must do this in-place without making a copy of the array.
Minimize the total number of operations.

noZeroPos从0开始，遍历数组，当遇到不是0的数，放入noZeroPos的位置，noZeroPos++，遇到0则记录0出现的个数
最后再把有多少个0放到数组最后即可

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int noZeroPos = 0;
        int zeroCount = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=0){
                nums[noZeroPos++] = nums[i];
            }else{
                zeroCount++;
            }
        }
        for(int i=0; i<zeroCount;i++){
            nums[nums.length-1-i]=0;
        }
    }
}
```

287. Find the Duplicate Number
Medium

Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

Example 1:

Input: [1,3,4,2,2]
Output: 2
Example 2:

Input: [3,3,3,4,2]
Output: 3
Note:

You must not modify the array (assume the array is read only).
You must use only constant, O(1) extra space.
Your runtime complexity should be less than O(n^2).
There is only one duplicate number in the array, but it could be repeated more than once.

给一个n+1个数的数组，每个数大小都在1到n之间，其中只有一个重复的数，找到这个重复的数

注意的是，只有一个数会重复，但它重复多少遍却不一定
条件是不能动这个数组，常数空间复杂度，时间复杂度小于O(n^2)（也就无法排序了，而且也不能双重遍历了）

把nums看成一个链表节点，索引i表示该节点位置，其值表示它的next节点的位置（画个图很直观）

这个链表一定是有环的，因为每个节点的next一定是一个存在的节点(因为值是1到n，而节点位置编号范围是0-n)。
而且也不可能完全是一个环，因为位置为0的节点一定没有前驱（没有节点的next是0）。因此整个图的抽象一定是若干个链表（可能是1个，可能是多个）汇聚成一个环
，而且只可能有一个环，因为只有一个元素重复，代表如果有不同节点的next相同，那么这个next只有一种取值。

重复的元素一定是环的起点位置，因为环内有一个节点的next是它，环外也有一个节点的next是它。因此找到这个环的起点即可，则用龟兔赛跑算法（Floyd判圈法）
来找圈的起点.

由于位置为0的节点一定没有前驱，所以0可以当做一个链表的头来作为算法的开始。🐢和🐰都在位置为0的节点处开始

```java
class Solution {
    public int findDuplicate(int[] nums) {
        int fast = 0;
        int slow = 0;
        do{
            slow = nums[slow];
            fast = nums[nums[fast]];
        }while(fast!=slow);
        //龟兔重合
        //龟返回起点0，兔继续走，每次都走1步，再次重合时就是环的起点
        slow = 0;
        while(slow!=fast){
            slow=nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
```

297. Serialize and Deserialize Binary Tree
Hard

Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

Example: 

You may serialize the following tree:

    1
   / \
  2   3
     / \
    4   5

as "[1,2,3,null,null,4,5]"
Clarification: The above format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.

Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.

序列化和反序列化一棵二叉树，将二叉树结构序列化成一个字符串，将一个字符串反序列化成一棵二叉树

这里不要二叉树转成中序序列和前序序列的合集字符串，然后将这个合集再转回二叉树

因为节点的值可以是重复的，而且可以是负数，使用层序遍历来构建字符串

//如果使用中序和前序，序列化后的字符会和答案不同，但按理来说字符的格式应该是自己定的，使用中序和前序见下面，但通不过（剑指上可以通过）
其实就是因为没有存null，把null一存也是对的


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
   public class Codec {
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return null;
            TreeNode p;
            ArrayList<String> list = new ArrayList<>();
            LinkedList<TreeNode> queue = new LinkedList<>();
            //先确定层数

            //按照如果是null，也添加，但null没有儿子节点
            queue.add(root);
            while (!queue.isEmpty()) {
                p = queue.poll();
                if (p == null) {
                    list.add("null");
                } else {
                    list.add(""+p.val);
                    queue.add(p.left);
                    queue.add(p.right);

                }
            }
            //去掉后面多余的null
            int i=list.size()-1;
            //得到最后一个不为null的元素序号
            while (list.get(i).equals("null")){
                i--;
            }
            StringBuilder res = new StringBuilder();
            for(int j=0;j<=i;j++){
                res.append(list.get(j));
                res.append(",");
            }
            res.deleteCharAt(res.length()-1);
            return res.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            //直接用字符串不好处理，把字符串转成数组，data中的元素个数为：
            if (data == null || data.length() == 0) return null;
            String[] list = data.split(",");
            TreeNode[] nodeList = new TreeNode[list.length];
            for (int i = 0; i < list.length; i++) {
                nodeList[i] = createNode(list[i]);
            }

            //构建二叉树，维护一个父亲节点索引，父亲节点不能是null，当前节点不是null时，则必须等到它的左右儿子都添加后，该父亲节点索引+1
            int parent = 0;
            boolean isLeft = true;
            for (int i = 1; i < nodeList.length; i++) {
                if(isLeft){
                    nodeList[parent].left = nodeList[i];
                    //添加完左孩子该添加右孩子
                    isLeft = false;
                }else {
                    nodeList[parent++].right = nodeList[i];
                    //添加完右孩子该添加下一个节点的左孩子
                    isLeft = true;
                }
                //父亲节点始终不为null
                while(parent < i && nodeList[parent]==null) parent++;
            }
            return nodeList[0];
        }

        TreeNode createNode(String val) {
            if (val.equals("null")) return null;
            else
                return new TreeNode(Integer.valueOf(val));
        }


}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));



//使用中序和前序
class Solution{
    String Serialize(TreeNode root) {
        if(root==null) return "";
        StringBuilder ans = new StringBuilder();
        ArrayList<Integer> inOrder = new ArrayList<>();
        ArrayList<Integer> preOrder = new ArrayList<>();

        Stack<TreeNode> stk = new Stack<>();
        TreeNode p = root;
        //先中序
        while(p!=null || !stk.isEmpty()){
            if(p!=null){
                stk.push(p);
                p = p.left;
            }else{
                p = stk.pop();
                inOrder.add(p.val);
                p = p.right;
            }
        }
        //再先序
        stk.clear();
        stk.push(root);
        while(!stk.isEmpty()){
            p = stk.pop();
            preOrder.add(p.val);
            if(p.right!=null) stk.push(p.right);
            if(p.left!=null) stk.push(p.left);
        }

        for(int i=0;i<inOrder.size();i++){
            ans.append("").append(inOrder.get(i)).append(" ");
        }
        ans.append("#"+" ");
        for(int i=0;i<preOrder.size();i++){
            ans.append("").append(preOrder.get(i)).append(" ");
        }
        //把最后的空格去掉
        ans.deleteCharAt(ans.length()-1);
        return ans.toString();
    }

    TreeNode Deserialize(String str) {
        if(str.length()==0) return null;
        String[] strs = str.split(" ");
        //除掉#号，再除以2，就是树的节点数
        int len = (strs.length-1)>>1;

        //中序序列
        int[] inOrder = new int[len];
        for(int i =0;i<len;i++){
            inOrder[i] = Integer.valueOf(strs[i]);
        }

        //先序序列
        int[] preOrder = new int[len];
        for(int i =0;i<len;i++){
            preOrder[i] = Integer.valueOf(strs[i+len+1]);
        }
        return buildTree(preOrder, 0, preOrder.length-1, inOrder, 0, inOrder.length-1);

    }

    TreeNode buildTree(int[] preOrder, int preStart, int preEnd, int[] inOrder, int inStart, int inEnd){
//        if(preEnd>=preOrder.length || preStart>= preOrder.length || preStart>preEnd ||
//                inEnd>=inOrder.length || inStart>= inOrder.length || inStart>inEnd){
//            return null;
//        }
        if(preStart>preEnd || inStart>inEnd){
            return null;
        }
        int rootVal = preOrder[preStart];
        int inRootIndex = 0;
        for(int i=inStart;i<=inEnd;i++){
            if(rootVal==inOrder[i]){
                inRootIndex = i;
                break;
            }
        }
        int leftCount = inRootIndex - inStart;
        int rightCount = inEnd-inRootIndex;
        TreeNode node = new TreeNode(rootVal);
        node.left = buildTree(preOrder, preStart+1, preStart+leftCount, inOrder, inStart, inRootIndex-1);
        node.right  = buildTree(preOrder, preStart+leftCount+1, preEnd, inOrder, inRootIndex+1, inEnd);
        return node;
    }
}
```

300. Longest Increasing Subsequence
Medium

Given an unsorted array of integers, find the length of longest increasing subsequence.

Example:

Input: [10,9,2,5,3,7,101,18]
Output: 4 
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
Note:

There may be more than one LIS combination, it is only necessary for you to return the length.
Your algorithm should run in O(n2) complexity.
Follow up: Could you improve it to O(n log n) time complexity?

找出最长的递增子序列（这里的子序列不用挨着）

LIS问题

有序序列{a1,a2,...} 求其最长递增子序列的长度，用dp[i]代表递增子序列以ai结束时的长度，dp[1]=1，
注意到以ax结尾的递增子序列，除了长度为1之外，其他情况，ax都是紧跟在一个由ai(i< x)组成的递增序列之后。要求以ax结尾的最长递增子序列长度
则要依次比较ax与其之前所有的ai(i< x)，若ai小于ax，则说明ax可以跟在ai结尾的递增子序列后形成一个新的递增子序列
特殊的，当没有ai(i< x)小于ax，那么以ax结尾的递增子序列最长长度为1，即dp[x] = max {1, dp[i]+1|ai< ax && i < x|}

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if(nums==null || nums.length==0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
        }
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
```

301. Remove Invalid Parentheses

Hard

Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.

Note: The input string may contain letters other than the parentheses ( and ).

Example 1:

Input: "()())()"
Output: ["()()()", "(())()"]
Example 2:

Input: "(a)())()"
Output: ["(a)()()", "(a())()"]
Example 3:

Input: ")("
Output: [""]

删除无用的括号，字符串中除了括号还有别的字符，要保留，且有效匹配形式不止一种

回退递归，对一个括号的策略只有要或者不要，这样可以使用回退递归（BackTracking）来将所有情况列出，
对于不是括号的字符，直接加入并继续递归，当递归到最后时，对最后的字符串进行正确性判别，如果左括号不等于右括号则无效，
如果相等，则再次进行一遍有效性判别（查看括号序列是否合法：leetcode22）

```java
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        set = new HashSet<String>();
        String s1 = "";
        trackBack(0,0,0,s,s1);
        List<String> ans = new ArrayList<>(set);
        if(ans.size()==0) ans.add("");
        return ans;

    }
    HashSet<String> set;
    int maxLen = 0;

    public void trackBack(int index, int leftCount, int rightCount, String s, String cur){
        if(index==s.length()){
            if(leftCount!=rightCount)
                return;
            else{
                if(isValid(cur)) {
                    int len = cur.length();
                    if (len == maxLen)
                        set.add(cur);
                    else if (len > maxLen) {
                        set.clear();
                        set.add(cur);
                        maxLen = len;
                    }
                }
                return;
            }
        }

        char c = s.charAt(index);
        //如果是括号
        if(c=='(' || c==')') {
            //加
            if (c == '(') {
                trackBack(index + 1, leftCount + 1, rightCount, s, cur+c);
            } else {
                trackBack(index + 1, leftCount, rightCount + 1, s, cur+c);
            }
            //不加
            trackBack(index + 1, leftCount, rightCount, s, cur);
        }
        //不是括号则一定要加
        else{
            trackBack(index+1, leftCount, rightCount, s, cur+c);
        }
    }

    public boolean isValid(String cur){
        int count = 0;
        for(int i=0;i<cur.length();i++){
            char c = cur.charAt(i);
            //左括号+1，后括号-1,如果出现了小于0，说明右括号在前了，直接返回false
            if(c=='(') count++;
            else if(c==')') count--;
            if(count < 0) return false;
        }
        return count==0;

    }
}
```


309. Best Time to Buy and Sell Stock with Cooldown
Medium

Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:

You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
Example:

Input: [1,2,3,0,2]
Output: 3 
Explanation: transactions = [buy, sell, cooldown, buy, sell]

买卖股票，卖了后的一天不能立即买，要冻结一天，求能获得的最大收益

还是可以考虑一下回退递归，对于一个股票，可以有买，卖，啥也不干的做法，记录上一个买的价格，如果当前价格高于买入的价格并且已经买了，才能卖
回退递归太慢了，不能接受。

https://www.cnblogs.com/jdneo/p/5228004.html

这道题可以用动态规划的思路解决。但是一开始想的时候总是抽象不出状态转移方程来，之后看到了一种用状态机的思路，
觉得很清晰，特此拿来分享，先看如下状态转移图：

S0 代表没有买入的等待状态，s0可以由s2和s0得来
S1 代表买入后等待卖出的状态，s1可以由s1和s0得来
S2 代表卖出后的状态（冻结状态）（s2只能由s1得来，且s2只能转到s0）且冻结状态只有一天

S2与S0的区别是：因为题目要求卖出后必须cooldown一轮，所以卖出进入S2后，必须再进入S0这个等待买入的状态，这一状态转换代表cooldown一轮

这里我们把状态分成了三个，根据每个状态的指向，我们可以得出下面的状态转移方程：

（这里要明确一点，第i天的状态只能由第i-1天的状态推得，而与更前面的无关，因为日子是一天天过的）


s0[i] = max(s0[i-1], s2[i-1])
s1[i] = max(s1[i-1], s0[i-1] - price[i])
s2[i] = s1[i-1] + price[i]
这样就清晰了很多。
sx[i]表示第i天保持x状态的最大收益

s0[0]=0, s2[0]=0, s1[0]=-price[0](买入prices[0]，收益是-price[0])

最终的结果状态可能是s0，也可能是s2，但不会是s1
即结果为max(s0[n-1], s2[n-1])

# 动态规划的题，画状态图会清晰很多！！！！！
## 动态规划的题，状态转移方程画不出的时候画状态图会清晰很多！！！！！



```java
class Solution {
    public int maxProfit(int[] prices) {
        if(prices.length==0) return 0;
        int len = prices.length;
        int[] s0 = new int[len];
        int[] s1 = new int[len];
        int[] s2 = new int[len];
        s0[0]=0;
        s1[0]=-prices[0];
        s2[0]=0;
        for(int i=1;i<prices.length;i++){
            s0[i] = Math.max(s0[i-1], s2[i-1]);
            s1[i] = Math.max(s1[i-1], s0[i-1]-prices[i]);
            s2[i] = s1[i-1]+prices[i];
        }
        return Math.max(s0[len-1], s2[len-1]);
    }

}
```


312. Burst Balloons
Hard

Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins. Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.

Find the maximum coins you can collect by bursting the balloons wisely.

Note:

You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
Example:

Input: [3,1,5,8]
Output: 167 
Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
             coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167

对于气球0。。。。n-1
考虑扎区间[start, end]
如果最后扎第i个，那么
在结束之前，已经扎掉了[start, i-1] 和 [i+1, end] 的所有气球
现在这个分治递归区间里,只存在三个元素即start-1,i,end+1,因为在[start-1,i-1],[i+1,end+1]这两个区间已经提前被扎爆了，
而且[start, i-1] 这个区域的取值与[i+1,end]无关，因为num[i]是最后取走的，此时左边的数列永远不会与右边的数相邻。

如果整个数组最后扎破的是num[i]，那么i的左右邻居都是1。且对于[0,i-1]这个区间，最后一个扎破的气球，其左邻居一定是1，右邻居一定是num[i];
对于[i+1,n-1]这个区间，最后一个扎破的气球，其左邻居一定是num[i]，右邻居一定是1; 然后再划分，

发现当考虑一个区间如[0,i-1]时，
如果它是包含它的大区间(称为父区间，如[0,n-1])的左半边，则该区间最后扎的气球的左邻居就是父区间的左邻居，右邻居就是父区间中最后一个扎破的气球
如果它是包含它的大区间的右半边，则该区间最后扎的气球的左邻居就是父区间中最后一个扎破的气球，右邻居就是父区间的右邻居

因此就有递归的形式了：
maxCoin(start:0, end: n-1, leftBound, rightBound) 
= Max(maxCoin(0,i-1, leftBound, nums[i]) + leftBound*nums[i]*rightBound + maxCoin(0,i-1, nums[i],rightBound))
用dp[i][j]来描述区间[i,j]能取得的最大值

dp不需要重复计算，如果算过一遍了就可以重复使用，例如，对于dp[i][j]，当考虑的区间是它时，由于上面的算法，那么nums[i-1]和nums[j+1]一定在它的父区间内考虑的，尽管题目是任意扎，看起来dp[i][j]两边的邻居可能会不同，但由于上述算法的思想，考虑一个区间时，它的真实邻居一定是还未被扎的，所以对任意区间它的邻居是一定的，因此可以重复使用


```java
class Solution {
    public int maxCoins(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];
        return maxCoins(0,nums.length-1, 1,1,nums, dp);
    }


    public int maxCoins(int start, int end, int leftNeighbor, int rightNeighbor, int[] nums, int[][] dp){
        if(start > end){
            return 0;
        }
        if(dp[start][end]!=0) return dp[start][end];
        for(int i=start;i<=end;i++){
            int val = maxCoins(start, i-1, leftNeighbor, nums[i], nums, dp)+
                    leftNeighbor * nums[i] * rightNeighbor
                    +maxCoins(i+1, end, nums[i], rightNeighbor, nums, dp);
            dp[start][end] = Math.max(val, dp[start][end]);
        }
        return dp[start][end];
    }
}
```

322. Coin Change
Medium

You are given coins of different denominations and a total amount of money amount. Write a function to compute the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

Example 1:

Input: coins = [1, 2, 5], amount = 11
Output: 3 
Explanation: 11 = 5 + 5 + 1
Example 2:

Input: coins = [2], amount = 3
Output: -1
Note:
You may assume that you have an infinite number of each kind of coin.

不同的面值组合成一个和t，能的话输出最少纸币数，不能则输出-1

这个题不能从大到小依次试，因为最大的可能不用（如果面值是从1到n连续的，则可以这么做）

考虑对于和的表示，其中最大的值是i（可能不是面值中最大的值）

如果用回退递归，可以解决问题，但是复杂度太高，太慢了

动态规划：

》自顶向下的动态规划：
F(s)表示总额为s时用的最少的张数
F(S)=min F(S−coin[i])+1  i=0...n−1。 S-ci<0时直接跳过

F(S) = 0 , when S = 0
F(S)=−1,   when n=0

S从t开始


》自底向上的动态规划

dp[i] = min(dp[i−coin[j]]+1)   j=0…n−1 

https://leetcode.com/problems/coin-change/solution/

通过这道题，好好研究审视一下两种动态规划思想：

自顶向下；（一般都伴随着递归）
自底向上，
另外再加一个分治法

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        return topDownDP(coins, amount, new int[amount]);
    }

    //自顶向下的动态规划
    int topDownDP(int[] coins, int amount, int[] dp){
        if(amount==0) return 0;
        if(amount < 0) return -1;
        //避免重复计算,使用之前的计算结果
        if(dp[amount-1]!=0) return dp[amount-1];

        int min = Integer.MAX_VALUE;
        for(int i=0;i<coins.length;i++){
            int res = topDownDP(coins, amount-coins[i], dp);
            if(res >= 0 && res < min){
                min = res + 1;
            }
        }
        dp[amount-1] = min==Integer.MAX_VALUE?-1:min;
        return dp[amount-1];
    }

    //自底向上的动态规划
    int bottomUpDP(int[] coins, int amount){
        int[] dp = new int[amount+1];
        dp[0] = 0;
        //这里一定要从1开始
        for(int i=1;i<=amount;i++){ 
            int min = Integer.MAX_VALUE;
            for(int j=0;j<coins.length;j++){
                if(i-coins[j] >= 0 && dp[i-coins[j]] >= 0)
                    min = Math.min(dp[i-coins[j]]+1, min);
            }
            dp[i] = min==Integer.MAX_VALUE?-1:min;
        }
        return dp[amount];
    }

}
```

337. House Robber III
Medium

The thief has found himself a new place for his thievery again. There is only one entrance to this area, called the "root." Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that "all houses in this place forms a binary tree". It will automatically contact the police if two directly-linked houses were broken into on the same night.

Determine the maximum amount of money the thief can rob tonight without alerting the police.

Example 1:

Input: [3,2,3,null,3,null,1]

     3
    / \
   2   3
    \   \ 
     3   1

Output: 7 
Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
Example 2:

Input: [3,4,5,1,3,null,1]

     3
    / \
   4   5
  / \   \ 
 1   3   1

Output: 9
Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.

两个相邻的房子不能被同时偷

     3
    / \
   4   50
  / \   \ 
 10   3   1

output=10+3+50=63 所以不是按照层序来看的。

乍一看，问题表现出“最优子结构”的特征：如果我们想从当前的二叉树（根植于根）中抢夺最大金额，我们当然希望我们可以对其左右子树做同样的事情。

因此，沿着这条线，让我们定义函数rob（root），它将返回我们可以为根植于根的二叉树抢夺的最大金额;
现在的关键是从解决方案到其子问题构建原始问题的解决方案，即如何从rob（root.left），rob（root.right）等中获取rob（root）。

显然，上面的分析提出了递归解决方案。对于递归，总是值得找出以下两个属性：

终止条件：我们何时知道rob（root）的答案而不进行任何计算？当然，当树是空的时候----我们没有什么可以抢劫的，所以钱数是零。

递归关系：即如何从rob（root.left），rob（root.right）等中获取rob（root）。从树根的角度来看，最后只有两个场景：
root是抢劫还是不抢劫。
如果是，由于“我们不能抢夺任何两个直接连接的房屋”的约束，可用的下一级子树将是四个“孙子子树”（root.left.left，root.left.right） ，root.right.left，root.right.right）。
但是，如果root没有被抢夺，那么下一级别的可用子树就是两个“儿子子树”（root.left，root.right）。我们只需要选择产生更大金额的方案。

```java
public int rob(TreeNode root) {
    if (root == null) return 0;
    
    int val = 0;
    
    if (root.left != null) {
        val += rob(root.left.left) + rob(root.left.right);
    }
    
    if (root.right != null) {
        val += rob(root.right.left) + rob(root.right.right);
    }
    
    return Math.max(val + root.val, rob(root.left) + rob(root.right));
}
```
显然上面的方法对于同一个树根会重复计算多次（当它是孙子子树根或儿子子树根时），因此用map保存已经计算过的节点，避免重复计算



```java

//对于一个根，函数要么取有它的值，要么取没它的值，

class Solution {
    public int rob(TreeNode root) {
        return rob(root, new HashMap<>());
    }

    public int rob(TreeNode root, HashMap<TreeNode, Integer> map) {
        if (root == null) return 0;
        if(map.containsKey(root)) return map.get(root);
        int val = 0;
    
        if (root.left != null) {
            val += rob(root.left.left, map) + rob(root.left.right, map);
        }
    
        if (root.right != null) {
            val += rob(root.right.left, map) + rob(root.right.right, map);
        }
        int max = Math.max(val + root.val, rob(root.left, map) + rob(root.right, map));
        map.put(root, max);
        return max;
    }

}

```

338. Counting Bits
Medium

Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the number of 1's in their binary representation and return them as an array.

Example 1:

Input: 2
Output: [0,1,1]
Example 2:

Input: 5
Output: [0,1,1,2,1,2]
Follow up:

It is very easy to come up with a solution with run time O(n*sizeof(integer)). 
But can you do it in linear time O(n) /possibly in a single pass?
Space complexity should be O(n).
Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any other language.




给定非负整数num。对于0≤i≤num范围内的每个数字i，计算其二进制表示中的1的数量并将它们作为数组返回。
简单的做法就是对于每个数转成2进制，算其中1的个数
整数转2进制：Integer.toBinaryString(num);（如果要自己实现的话，可以每次都右移一位和1做与操作）


对于任何一个数，只要它乘以2，就相当于它整体左移一位，右边补0，所以对于任何偶数x，1的个数=（x/2）的1的个数
对于奇数，它 = 比它小一的偶数 + 1，而比它小一的偶数最低位一定是0，所以对于任何奇数x，1的个数 = (x-1)的1的个数 + 1

又由这道题可知，偶数的二进制最低位一定是0，奇数二进制最低位一定是1(奇数等于比它小的偶数+1)

```java
class Solution {
    //一般做法
    public int[] countBits(int num) {
        int[] ans = new int[num+1];
        for(int i=0;i<=num;i++){
            ans[i] = getBits(i);
        }
        return ans;
    }

    int getBits(int num){
        String s = Integer.toBinaryString(num);
        int ans = 0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='1') ans++;
        }
        return ans;
    }

    //好做法
    public int[] countBits(int num) {
        int[] ans = new int[num+1];
        for(int i=0;i<=num;i++){
            ans[i] = ans[i >> 1] + (i & 1);  //如果是偶数，i&1为0，如果是奇数，i&1为1
        }
        return ans;
    }
}
```

347. Top K Frequent Elements
Medium

Given a non-empty array of integers, return the k most frequent elements.

Example 1:

Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
Example 2:

Input: nums = [1], k = 1
Output: [1]
Note:

You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
Your algorithm's time complexity must be better than O(n log n), where n is the array's size.

关键是在于HashMap的对键排序

```java
class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            map.put(nums[i], map.getOrDefault(nums[i], 0)+1);
        }

        Set<Map.Entry<Integer, Integer>> set = map.entrySet();
        List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(set);
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        });
        List<Integer> ans = new ArrayList<>();
        for(int i=0;i<k;i++){
            ans.add(list.get(i).getKey());
        }
        return ans;
    }
}
```

394. Decode String
Medium

Given an encoded string, return it's decoded string.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.

Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].

Examples:

s = "3[a]2[bc]", return "aaabcbc".
s = "3[a2[c]]", return "accaccacc".
s = "2[abc]3[cd]ef", return "abcabccdcdcdef".


维持一个倍数栈和字符串栈，

如果是数字，加入数字栈，这里要注意对10以上的数字，要循环直到不是数字
如果是字母，则直接附加到字符串栈顶
如果是[,栈顶加一个""，作为括号内外的分隔符，再下面的字符串就要往它上附加了，
如果是]，数字栈顶出栈为x，字符串栈顶的出栈，做x倍数，然后加到字符栈顶

```java
class Solution {
    public String decodeString(String s) {
        Stack<Integer> timeStk = new Stack<>();
        Stack<String> strStk = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                //获得这个数
                int n = Integer.valueOf(c - '0');
                //对于此时进入的数，一定要先入栈
                timeStk.push(n);
                i++;
                //如果是10以上的数，则做叠加，直到不是数字位置
                while (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    n = Integer.valueOf(s.charAt(i) - '0');
                    int top = timeStk.pop() * 10 + n;
                    timeStk.push(top);
                    i++;
                }
                i--; //因为此时不是数字，且循环还会+1，所以-1一下
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                String tmp = "";
                if(!strStk.empty()) tmp = strStk.pop();
                tmp += c;
                strStk.push(tmp);
            } else if(c == '['){
                strStk.push("");
            }
            else if (c == ']') {
                int time = timeStk.pop();
                String str = strStk.pop();
                String tmp = "";
                for (int j = 0; j < time; j++) {
                    tmp += str;
                }
                //把tmp加到栈顶元素里
                if(!strStk.empty()){
                    String tmp2 = strStk.pop();
                    tmp2 += tmp;
                    strStk.push(tmp2);
                }else
                    strStk.push(tmp);
            }
        }
        StringBuilder ans = new StringBuilder();
        while (!strStk.empty()) {
            ans.insert(0, strStk.pop());
        }
        return ans.toString();
    }

}
```

406. Queue Reconstruction by Height
Medium

Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k), where h is the height of the person and k is the number of people in front of this person who have a height greater than or equal to h. Write an algorithm to reconstruct the queue.

Note:
The number of people is less than 1,100.


Example

Input:
[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]

Output:
[[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]

假设你有一列随机的人在排队。每个人由一对整数(h, k)描述，其中h是这个人的高度，k是这个人前面的高度大于或等于h的人数。编写一个算法来重构队列。


找到其中最高的值，排列出来  [7,0]  [7,1]
次高的值，先按照k从小到大排：[6,1],然后从上面队列的第一个开始数，数k个，插入：[7,0] [6,1] [7,1]
继续：[5,0], [5,2]，插入：[5,0] [7,0] [5,2] [6,1] [7,1]
继续：[4,4],插入：[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]

简化方法是，把原数组排序，按照h优先，h从大到小，k从小到大的顺序：要重写一下sort方法h不同时，按降序，h相同时，按照k升序
[[7,0],[7,1],[6,1],[5,0],[5,2],[4,4]]然后按照k当做位置，从前到后直接逐个往List里面插即可

```java
class Solution {
    public int[][] reconstructQueue(int[][] people) {
        ArrayList<int[]> list = new ArrayList<>();
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]!=o2[0]){
                    return o2[0]-o1[0]; //高度不同时，降序
                }else{
                    return o1[1]-o2[1]; //高度相同时，升序
                }
            }
        });
        for(int i=0;i<people.length;i++){
            int[] elem = people[i];
            int index = elem[1];
            list.add(index, elem);
        }

        int[][] ans = new int[people.length][];
        for(int i=0;i<people.length;i++){
            ans[i] = list.get(i);
        }
        return ans;
    }
}
```

416. Partition Equal Subset Sum
Medium

Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

Note:

Each of the array element will not exceed 100.
The array size will not exceed 200.
 

Example 1:

Input: [1, 5, 11, 5]

Output: true

Explanation: The array can be partitioned as [1, 5, 5] and [11].
 

Example 2:

Input: [1, 2, 3, 5]

Output: false

Explanation: The array cannot be partitioned into equal sum subsets.

数组是否能被分成两个集合，和相等，则每个集合中的和都是总数的一半
先算所有数字之和，如果是奇数，则肯定不行；如果是偶数
先把数组排序，如[1,5,5,11]
判断最后一个数，如果小等于和的一半，则成立，如果大于和的一半，则不成立，而且这个不能用回退递归，太慢了

这个问题基本上是让我们找出集合中是否有多个能够求和到特定值的数字（在这个问题中，值是sum / 2）。

实际上，这是一个0/1背包问题，对于每个数字，我们可以选择与否。 让我们假设dp[i][j]表示是否可以从前i个数中得到特定的和j。 如果我们可以从0-i中选择这样一系列数字，其总和为j，则dp [i] [j]为真，否则为假。

基本情况：dp [0][0]为真; （零数由0和0组成）

第i个数字是nums[i-1]
转换函数：对于每个数字，如果我们选择不使用它，dp [i] [j] = dp [i-1] [j]，这意味着如果第一个i-1元素已经使它成为j，dp [i][j]也会把它变成j（我们可以忽略不要nums[i-1]）。 如果我们选择使用nums[i-1]。 dp [i][j] = dp [i-1][j-nums [i-1]]，表示j由当前值nums [i-1]组成，剩余部分由其他先前数字组成。 因此，转移函数是dp [i][j] = dp [i-1][j] || dp [i-1] [j-nums[i-1]] (第i个数是nums[i-1])


```java
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int i=0;i<nums.length;i++){
            sum += nums[i];
        }
        if(sum%2==1) return false;

        int setSum = sum /2;
        int len = nums.length;

        boolean[][] dp = new boolean[len+1][setSum+1];
        //前i个数一定能使得和为0，都不取即可
        for(int i=0;i<len+1;i++){
            dp[i][0] = true;
        }
        //前0个数不能组成任何值大于0的和
        for(int j=1;j<setSum+1;j++){
            dp[0][j] = false;
        }
        for(int i=1;i<len+1;i++){
            for(int j=1;j<=setSum;j++){
                if(j<nums[i-1])
                    dp[i][j] = dp[i-1][j];
                else
                    dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]];
            }
        }

        for(int i=1;i<len+1;i++){
            if(dp[i][setSum]) return true;
        }
        return false;
    }
}
```

437. Path Sum III
Easy

You are given a binary tree in which each node contains an integer value.

Find the number of paths that sum to a given value.

The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).

The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.

Example:

root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1

Return 3. The paths that sum to 8 are:

1.  5 -> 3
2.  5 -> 2 -> 1
3. -3 -> 11

对于每一个节点，当它为一个路径末节点的时候且路径和为目标值，计数+1，

关键在于往下传的时候减去它自己的值。
由于有可能从任意节点开始，所以对每个节点都要调用pathSum

对于某个节点，则用递归来找到以它为根的路径

```java
class Solution {
    public int pathSum(TreeNode root, int sum) {
        if(root == null)
            return 0;
        //这样会做到让每个节点都能成为最顶上的节点，去计算
        return findPath(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
    }

    public int findPath(TreeNode root, int sum){
        if(root==null) return 0;
        int res = 0;
        if(root.val==sum)
            res++;
        res += findPath(root.left, sum-root.val);
        res += findPath(root.right, sum-root.val);
        return res;
    }
}
```

438. Find All Anagrams in a String
Easy

Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.

Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.

The order of output does not matter.

Example 1:

Input:
s: "cbaebabacd" p: "abc"

Output:
[0, 6]

Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".
Example 2:

Input:
s: "abab" p: "ab"

Output:
[0, 1, 2]

Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".

先把p中的每个字母及其出现次数放入数组对应的位置pCount[26]中，并统计其长度为len，
建立一个头指针start，尾指针end，并有一个标志flag，代表当前 [start, end]是否满足条件

新建一个数组 sCount = int[26]
一开始start和end都是在0处，先让end移动到len-1处，每移动一位，flag对应位置+1，当end-1移动到len-1时，
比较sCount和pCount，如果每一位都相等，则flag=true

从这里开始，end和start同时往后移，移动后 sCount[s.charAt(start-1)-'a']--,sCount[s.charAt(end)-'a']++
若flag=false，则再次对比sCount和pCount，如果每一位都相等，则flag=true，否则为false
若flag=true，则比较s.charAt(start-1)和s.charAt(end)，若等则flag=true，否则为false

其实还是滑动窗口的思想，窗口大小一定(p的长度)，每次都看窗口内元素是否满足条件

不要用每次 s.substring和p去比，可以做但很慢，可以利用的点是，当s[i...j]和p匹配时，如果s[i]和s[j+1]匹配，则s[i+1...j+1]和p匹配
即不用再去全量比较了

```java
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        if(s.length()==0 || p.length()==0 || s.length() < p.length()) return ans;
        int[] pCount = new int[26];
        int[] sCount = new int[26];
        for(int i=0;i<p.length();i++){
            pCount[p.charAt(i)-'a']++;
        }
        int start=0,end=0;
        while(end<p.length()){
            sCount[s.charAt(end)-'a']++;
            end++;
        }
        end--;
        boolean flag = false;

        do{
            if(!flag){
                flag = compareArray(sCount, pCount);
            }else{
                flag = s.charAt(start-1)==s.charAt(end);
            }
            if(flag) ans.add(start);
            start++;
            end++;
            if(end>=s.length()) break;
            else{
                sCount[s.charAt(start-1)-'a']--;
                sCount[s.charAt(end)-'a']++;
            }
        }while(end<s.length());
        return ans;

    }

    boolean compareArray(int[] a, int[] b){
        if(a.length!=b.length) return false;
        for(int i=0;i<a.length;i++){
            if(a[i]!=b[i])return false;
        }
        return true;
    }
}
```

448. Find All Numbers Disappeared in an Array
Easy

Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.

Find all the elements of [1, n] inclusive that do not appear in this array.

Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.

Example:

Input:
[4,3,2,7,8,2,3,1]

Output:
[5,6]

给定一个整数数组，其中1≤a[i]≤n (n =数组大小)，有些元素出现两次，有些元素出现一次。

找到[1,n]中不出现在该数组中的所有元素。

你能在没有额外空间的情况下在O(n)运行时完成吗?您可以假设返回的列表不算作额外的空间。


这个题要是不考虑O(1)的空间复杂度就很简单，用HashMap即可，不用O(n)的时间复杂度，排序即可，困难在于如何把这两种需求结合起来

方法一：
基本思想是我们遍历整个数组，当见过一个数时，就把它作为索引(由于是1开始，所以要-1)的数标记为负数
如对于4，代表第四个数，索引为4-1=3：nums[4-1] = -nums[4-1]：-7，一圈标记下来，为：[-4,-3,-2,-7,8,2,-3,-1]
通过这种方式，我们看到的出现过的索引所代表的数都将标记为负数。 
在第二次迭代中，如果某个值未标记为负数，则表示我们之前从未见过该索引，因此只需将其添加到返回列表中即可。

数组长度为n，数组中的数字小于等于n，大于等于1，当遍历到了nums[i]=x时，令nums[x]为负数，表示x这个数出现过
当设负一遍后，所有出现过的值作为索引的对应数字都是负数，意为只要有i，那么nums[i]就是负数
那么没有i的话，nums[i]就是正数

方法二：
    遍历数组，如果某个数字没有放在其应该在的位置（如4应该放在第四个位置，即nums[3]应该=4），交换其与本来在这个位置上的数字
    i=0时，nums[nums[i]-1] = nums[i]：nums[2]=3
    则下标递增去，考虑下一个数字。一直到遍历完数组，变成了[1,2,3,4,3,2,7,8]，即每个位置一定是其对应数字，如果不是，则说明这个数字没有，记录

```java
class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        for(int i=0;i<nums.length;i++){
            int val = nums[Math.abs(nums[i])-1];
            if(val > 0){
                nums[Math.abs(nums[i])-1] = -nums[Math.abs(nums[i])-1];
            }
        }

        for (int i=0; i<nums.length; i++) {
            if(nums[i]>0){
                ans.add(i+1);
            }
        }
        return ans;
    }


    //方法二：
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ans = new ArrayList<>();

        int i = 0;
        while (i<nums.length){
            if(nums[i]!=nums[nums[i]-1]){
                int tmp = nums[nums[i]-1];
                nums[nums[i]-1] = nums[i];
                nums[i] = tmp;
            }else{
                i++;
            }
        }


        for (int j=0; j<nums.length; j++) {
            if(nums[j]!=j+1){
                ans.add(j+1);
            }
        }
        return ans;
    }
}
```

461. Hamming Distance
Easy

The Hamming distance between two integers is the number of positions at which the corresponding bits are different.

Given two integers x and y, calculate the Hamming distance.

Note:
0 ≤ x, y < 231.

Example:

Input: x = 1, y = 4

Output: 2

Explanation:
1   (0 0 0 1)
4   (0 1 0 0)
       ↑   ↑

The above arrows point to positions where the corresponding bits are different.


求两数的汉明距离,直接把两数异或，可以得到一个数，它们的汉明距离就是这个数中1的个数

1^4 = 5 : 0101

```java
class Solution {
    public int hammingDistance(int x, int y) {
        int tmp = x^y;
        int dis = 0;
        while(tmp!=0){
            dis += tmp&1;  //tmp&1 可以得到tmp的二进制最低位是几
            tmp = tmp >> 1;  //tmp /= 2， 位运算更快
        }
        return dis;
    }
}
```

494. Target Sum
Medium

You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.

Find out how many ways to assign symbols to make sum of integers equal to target S.

Example 1:
Input: nums is [1, 1, 1, 1, 1], S is 3. 
Output: 5
Explanation: 

-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3

There are 5 ways to assign symbols to make the sum of nums be target 3.

Note:
The length of the given array is positive and will not exceed 20.
The sum of elements in the given array will not exceed 1000.
Your output answer is guaranteed to be fitted in a 32-bit integer.


直接用回退递归,可以过，但是速度太慢了，运行时间是指数级别的

原始问题陈述相当于：
找到需要为正的nums子集，其余为负数，使得总和等于target

设P为正子集，N为负子集
例如：
给定nums = [1,2,3,4,5]和target = 3，那么一个可能的解决方案是+ 1-2 + 3-4 + 5 = 3
这里正子集是P = [1,3,5]，负子集是N = [2,4]

然后让我们看看如何将其转换为子集求和问题：
                  sum(P) - sum(N) = target
sum(P) + sum(N) + sum(P) - sum(N) = target + sum(P) + sum(N)
                       2 * sum(P) = target + sum(nums)

因此，原始问题已转换为子集求和问题，如下所示：
找到n的子集P，使得sum（P）=（target + sum（nums））/ 2

显然如果 target + sum（nums）是奇数就没有适合的解    
如果是偶数，

可以转换成0-1背包问题，从数组中找到若干个数，使得和为sum（P）
dp[i][j]是前i个数（可以1个都不包括），和为j的可能性数


if(j>=nums[i-1])
    dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i-1]];
else
    dp[i][j] = dp[i-1][j];

dp[0][0]=1;
dp[0][j]=0;   sum>=j>=1



```java
class Solution {
    public int findTargetSumWays(int[] nums, int s) {
        int sum = 0;
        for(int num:nums){
            sum += num;
        }

        //奇数与1位1，偶数与1为0
        int target = (sum + s);
        if((target&1)==1 || s>sum )return 0;
        return getSumCount(nums, target>>1);
    }

    int getSumCount(int[] nums, int target){
        int[][] dp = new int[nums.length+1][target+1];
        dp[0][0]=1;
        for(int j=1;j<target+1;j++){
            dp[0][j] = 0;
        }

        for(int i=1;i<nums.length+1;i++){
            for(int j=0;j<target+1;j++){
                if(j>=nums[i-1])
                    dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i-1]];
                else
                    dp[i][j] = dp[i-1][j];
            }
        }
        return dp[nums.length][target];
    }
}
```

538. Convert BST to Greater Tree
Easy

Given a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed to the original key plus sum of all keys greater than the original key in BST.

Example:

Input: The root of a Binary Search Tree like this:
              5
            /   \
           2     13

Output: The root of a Greater Tree like this:
             18
            /   \
          20     13

把一个二叉搜索树转成大树，原来二叉树中所有的节点都加上比它值大的节点的值
getSum用于求以root为根节点的树的所有节点和
Helper返回一个节点新树中root位置要放的节点，该节点的值等于它的右树和+它自己的值+它的右上部分所有节点的和

```java
class Solution {

    HashMap<TreeNode, Integer> map;
    public TreeNode convertBST(TreeNode root) {
        map = new HashMap<>();
        return Helper(root, 0);
    }

    TreeNode Helper(TreeNode root, int preSum){
        if(root==null) return null;
        TreeNode newRoot;
        newRoot = new TreeNode(root.val + getSum(root.right) + preSum);
        newRoot.left = Helper(root.left, newRoot.val);
        newRoot.right = Helper(root.right,preSum);
        return newRoot;
    }

    //获得root为根的树所有值的和
    int getSum(TreeNode root){
        if(root==null) return 0;
        if(map.containsKey(root)) return map.get(root);
        int ans = root.val + getSum(root.left) + getSum(root.right);
        map.put(root, ans);
        return ans;
    }
}
```

543. Diameter of Binary Tree
Easy

Given a binary tree, you need to compute the length of the diameter of the tree. 
The diameter of a binary tree is the length of the longest path between any two nodes in a tree. 
This path may or may not pass through the root.

Example:
Given a binary tree 
          1
         / \
        2   3
       / \     
      4   5    
Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].

Note: The length of path between two nodes is represented by the number of edges between them.

二叉树的直径，是二叉树中两个相距最远的点之间的距离，如上图的4和3（或5和3），距离为3，

getLongestPath(root),获得以root为根的树的根到叶子的最大距离=max（左子树的最大距离，右子树最大距离） + 1

显然getLongestPath(root) = 1+ Math.max(getLongestPath(root.left), getLongestPath(root.right));

那么对于以root为根的树，它的直径就是对所有的节点取  getLongestPath(node.left) + getLongestPath(node.right);，其中的最大值就是答案

```java
class Solution {
    HashMap<TreeNode, Integer> map;
    int max;
    public int diameterOfBinaryTree(TreeNode root) {
        map = new HashMap<>();
        max = 0;
        Helper(root);
        return max;
    }

    void Helper(TreeNode root){
        if(root==null) return;
        int path = 0;
        if(root.left!=null)
            path += (1+getLongestPath(root.left)); //+1是连接根节点和子树的那条树枝
        if(root.right!=null)
            path += (1+getLongestPath(root.right));
        max = Math.max(path, max);
        Helper(root.left);
        Helper(root.right);
    }

    //以root为根的树的最长路径（即根到最低的叶子的路径）
    int getLongestPath(TreeNode root){
        if(root!=null && map.containsKey(root)) return map.get(root);
        int ans = 0;
        if(root==null) ans = 0;
        else if(root.left==null && root.right==null) ans = 0;
        else{
            ans = 1 + Math.max(getLongestPath(root.left), getLongestPath(root.right));
        }
        map.put(root, ans);
        return ans;
    }
}
```

560. Subarray Sum Equals K
Medium

Given an array of integers and an integer k, 
you need to find the total number of continuous subarrays whose sum equals to k.

Example 1:
Input:nums = [1,1,1], k = 2
Output: 2
Note:
    The length of the array is in range [1, 20,000].
    The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].


找出数组中和为k的连续子数组，返回有多少个这样的子数组

方法一
可以对与每一个数作为开头，算它与后面的连续数组之和，这样会很慢，但能过

方法二：
上面主要耗时间的步骤是计算sum[i，j]并判断是否等于k,但如果我们知道sum[0,i-1]和sum[0,j]，其实也就知道了sum[i,j]，
所以当sum[i,j]==k时，sum[0,i-1] = sum[0,j] - k，不需要知道i是多少，只需要只有没有，有几个即可，
由于可能会出现负数，所以sum[0,i-1]这个数可能出现不止一次，其实用图像来想象这个过程会很容易
用HashMap来保存出现过的sum[0,j]的次数



```java
class Solution {
    //方法一
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            int sum=0;
            for (int end = start; end < nums.length; end++) {
                sum+=nums[end];
                if (sum == k)
                    count++;
            }
        }
        return count;
    }

    //方法二：
    /*
    */
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> preSum = new HashMap<>();
        int result = 0;
        int sum=0;
        preSum.put(0,1);
        for(int i=0;i<nums.length;i++){
            sum += nums[i];
            if(preSum.containsKey(sum-k)){
                result += preSum.get(sum-k);
            }
            preSum.put(sum, preSum.getOrDefault(sum, 0)+1);
        }
        return result;
    }
}
```

572. Subtree of Another Tree
Easy

Given two non-empty binary trees s and t, 
check whether tree t has exactly the same structure and node values with a subtree of s. 
A subtree of s is a tree consists of a node in s and all of this node's descendants. 
The tree s could also be considered as a subtree of itself.

Example 1:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
Given tree t:
   4 
  / \
 1   2
Return true, because t has the same structure and node values with a subtree of s.
Example 2:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
    /
   0
Given tree t:
   4
  / \
 1   2
Return false.

先算出t的高度，然后找出s中高度与t相同的子树的所有节点，然后判断它们两个树是否相等
以p节点为根的树的高度=1+Max(左子树高度，右子树高度)

如，对于example2：t的高度为2，s中高度为2的节点为2，比较s中以2为根的子树的与t是否相同


```java
//方法一
class Solution {
    Set<TreeNode> set;
    public boolean isSubtree(TreeNode s, TreeNode t) {
        set = new HashSet<>();
        //先算出目标树的高度是多少
        int targetHeight = getTreeHeight(t, 0, false);
        //再记录s中高度与目标树高度相同的节点
        getTreeHeight(s, targetHeight, true);
        //逐个比较记录的节点为根的树与目标树是否相同
        Iterator<TreeNode> it = set.iterator();
        while(it.hasNext()){
            TreeNode node = it.next();
            if(compareTree(node, t)) return true;
        }
        return false;

    }

    int getTreeHeight(TreeNode root, int targetHeight, boolean record){
        if(root==null) return 0;
        int height = 1 + Math.max(getTreeHeight(root.left, targetHeight, record), getTreeHeight(root.right, targetHeight, record));
        if(height == targetHeight && record){
            set.add(root);
        }
        return height;
    }
    
    //比较两棵树是否相同
    boolean compareTree(TreeNode t1, TreeNode t2){
        if(t1==null && t2==null) return true;
        else if(t1==null || t2 == null) return false;
        return t1.val==t2.val && compareTree(t1.left, t2.left) && compareTree(t1.right , t2.right);
    }
}


//方法2：
//直接判断s中每个节点为根的树与t是否相同
class Solution{
    //比较两棵树是否相同
    boolean compareTree(TreeNode t1, TreeNode t2){
        if(t1==null && t2==null) return true;
        else if(t1==null || t2 == null) return false;
        return t1.val==t2.val && compareTree(t1.left, t2.left) && compareTree(t1.right , t2.right);
    }

    public boolean isSubtree(TreeNode s, TreeNode t) {
        boolean res = false;
        res = res || compareTree(s, t)
        if(s!=null)
            res = res || isSubtree(s.left, t) || isSubtree(s.right, t);
        return res;
    }
}
```



581. Shortest Unsorted Continuous Subarray
Easy

Given an integer array, you need to find one continuous subarray that if you only sort this subarray in ascending order, 
then the whole array will be sorted in ascending order, too.

You need to find the shortest such subarray and output its length.

Example 1:
Input: [2, 6, 4, 8, 10, 9, 15]
Output: 5
Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
Note:
Then length of the input array is in range [1, 10,000].
The input array may contain duplicates, so ascending order here means <=.

给定一个整数数组，你需要找到一个连续子数组，如果你只按升序排列这个子数组，
然后整个数组也将按升序排序。

您需要找到最短的子数组并输出它的长度。

直接将序列弄一个排好的，然后用两个指针一个从前往后一个从后往前，当遇到不一样的数字时指针停止走动（因为要求的子数组必须是连续的），最后算两个指针的差即可

```java
class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int[] sorted = new int[nums.length];
        for(int i=0;i<nums.length;i++){
            sorted[i] = nums[i];
        }
        Arrays.sort(sorted);
        int i =0;
        int j = sorted.length-1;
        for(;i<sorted.length;i++){
            if(nums[i]!=sorted[i]) break;
        }

        for(;j>=0;j--){
            if(nums[j]!=sorted[j]) break;
        }
        if(i>j) return 0;
        else return j-i+1;
    }
}
```

617. Merge Two Binary Trees
Easy

Given two binary trees and imagine that when you put one of them to cover the other, 
some nodes of the two trees are overlapped while the others are not.

You need to merge them into a new binary tree. The merge rule is that if two nodes overlap, then sum node values up as the new value of the merged node. Otherwise, the NOT null node will be used as the node of new tree.

Example 1:

Input: 
    Tree 1                     Tree 2                  
          1                         2                             
         / \                       / \                            
        3   2                     1   3                        
       /                           \   \                      
      5                             4   7                  
Output: 
Merged tree:
         3
        / \
       4   5
      / \   \ 
     5   4   7

合并二叉树，如果有重合的节点，则加和它们的值，
对于两棵树合并，合并它们的根节点，然后在分别合并左子树和右子树

```java
class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        TreeNode ans = merge(t1,t2);
        return ans;
    }

    TreeNode merge(TreeNode t1, TreeNode t2){
        if(t1==null && t2==null) return null;
        TreeNode node = new TreeNode(getVal(t1) + getVal(t2));
        //到这里能保证t1,t2不全为null，但不能保证其中有null
        node.left = merge(t1==null?null:t1.left, t2==null?null:t2.left);
        node.right = merge(t1==null?null:t1.right, t2==null?null:t2.right);
        return node;
    }

    int getVal(TreeNode node){
        return node==null?0:node.val;
    }
}
```


621. Task Scheduler
Medium

Given a char array representing tasks CPU need to do.
 It contains capital letters A to Z where different letters represent different tasks. 
 Tasks could be done without original order. Each task could be done in one interval. 
 For each interval, CPU could finish one task or just be idle.

However, there is a non-negative cooling interval n that means between two same tasks, 
there must be at least n intervals that CPU are doing different tasks or just be idle.

You need to return the least number of intervals the CPU will take to finish all the given tasks.

 

Example:

Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
 

Note:

The number of tasks is in the range [1, 10000].
The integer n is in the range [0, 100].

给定一个char数组表示CPU需要执行的任务。 它包含大写字母A到Z，其中不同的字母代表不同的任务。 
任务可以不按照原始的顺序完成。 
每项任务都可以在一个时间槽内完成。 对于每个时间槽，CPU可以完成一个任务或只是空闲。

但是，有一个非负的冷却间隔n意味着在两个相同的任务之间，必须至少有n个时间槽，CPU正在执行不同的任务或只是空闲。

您需要返回CPU将完成所有给定任务所需的最少时间槽数。

也就是说，上面A和A之间必须有两个以上时间槽

加入对[A,A,A,A,B,C,C,C]
将任务排序，
A,A,A,A
C,C,C
B

每次取出当前任务数最多的1个任务t

然后每次取n个除了最大的所有的不重复的任务，插入到t的后面，
然后每次都是取出，插入，当不够取时，插入idle, 直到最后一个最大插入
要注意的是，最多元素每次插完就会改变，如下：

每次都取出当前任务数最多的1个，不够就用interval补齐（除了最后）
取完一组就对任务数组重新排序，（这一步极其重要！）
取出一个任务，就减1

对[A,A,A,A,B,C,C,C] n=2：

A C B --->   A C B A C idle  ---> A C B A C IDLE A C IDLE  --->  A C B A C IDLE A C IDLE A


```java
class Solution {
    public int leastInterval(char[] tasks, int n) {

        int[] taskCount = new int[26]; //每个数组代表该字母对应的任务数
        for (int i = 0; i < tasks.length; i++) {
            taskCount[tasks[i] - 'A']++;
        }

        //工作序列，以-1代表idle
        ArrayList<Character> ans = new ArrayList<>();

        Arrays.sort(taskCount);

        int time = 0;
        while (taskCount[25] > 0) {
            taskCount[25]--;
            time++;

            int interval = 0;
            //把剩下的任务种数，每个加一个进去
            for (int i = 24; i >= 0; i--) {
                if (interval >= n) break; //一次最多取n个
                if (taskCount[i] > 0) {
                    taskCount[i]--;
                    interval++;
                    time++;
                }
            }

            Arrays.sort(taskCount);
            //如果到最后，剩下的元素不够了，不用补空隙
            while (interval < n && taskCount[25] > 0) {
                interval++;
                time++;
            }
        }
        return time;
    }
}
```

647. Palindromic Substrings
Medium

Given a string, your task is to count how many palindromic substrings in this string.

The substrings with different start indexes or end indexes are counted as different substrings even they consist of same characters.

Example 1:

Input: "abc"
Output: 3
Explanation: Three palindromic strings: "a", "b", "c".
 

Example 2:

Input: "aaa"
Output: 6
Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".


找出字符串中回文子串的个数, 要查询的子串个数用两层循环来标定,这样做会比较慢（但能过）


方法二：每次针对一个字符，分别找以它为中心的回文字符，要分奇数和偶数两种情况

```java
class Solution {
    //复杂度：O(n * n * n) //判断回文其实还是遍历
    public int countSubstrings(String s) {
        int ans=0;
        for(int i=0;i<s.length();i++){
            for(int j=i+1;j<s.length+1;j++){
                if(isPalindrome(s.substring(i, j)))
                    ans++;
            }
        }
        return ans;
    }

    boolean isPalindrome(String str){
        StringBuilder builder = new String Builder(str);
        builder.reverse();
        return builder.toString().equals(str);
    }


    //方法二：
    //复杂度： O（n*n)
    int count = 0;
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) return 0;
        
        for (int i = 0; i < s.length(); i++) { // i is the mid point
            extendPalindrome(s, i, i); // odd length;
            extendPalindrome(s, i, i + 1); // even length
        }
        
        return count;
    }
    
    private void extendPalindrome(String s, int left, int right) {
        while (left >=0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++; left--; right++;
        }
    }
}
```

739. Daily Temperatures
Medium

Given a list of daily temperatures T, return a list such that, 
for each day in the input, tells you how many days you would have to wait until a warmer temperature. 
If there is no future day for which this is possible, put 0 instead.

For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76, 73], 
your output should be [1, 1, 4, 2, 1, 1, 0, 0].

Note: The length of temperatures will be in the range [1, 30000]. 
Each temperature will be an integer in the range [30, 100].

给一个数组，就是对于每一个值，找到它后面的第几个数是比它大的，并返回这个数字的数组

二重循环很简单，但肯定不这么弄

还是用栈来做，当遍历到一个值时，如果它比栈顶元素所索引的元素大，那栈顶元素所索引的答案就是该值-栈顶元素所索引
然后栈顶元素出栈，持续比较该值和栈顶元素大小，直到栈顶元素不比该值小，然后该值入栈，继续遍历
最终还在栈中的元素，说明它后面没有比它大的数了，所以对应的值是0

## 又一个这里入栈索引的典型

```java
class Solution {
    public int[] dailyTemperatures(int[] nums) {
        int[] ans = new int[nums.length];
        Stack<Integer> stk = new Stack<>();
        for(int i=0;i<ans.length;i++){
            if(stk.empty()){
                stk.push(i);
            }else{
                while(!stk.empty() && nums[stk.peek()] < nums[i]){
                    int top = stk.pop();
                    ans[top] = i-top;
                }
                stk.push(i);
            }
        }

        //留在里面的，说明它后面没有比它大的值
        while(!stk.empty()){
            ans[stk.pop()] = 0;
        }
        return ans;
    }
}
```

# 至此完结 #

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

对于每一个值(除了.)，尝试添加上述三个值，如果上述三个信息中有任何一个被添加过了，则说明一定是不符合的。直到把所有数字都执行完发现都可以添加，则一定是符合的

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

用两个队列来做,分别存放本次和下次的读取顺序，
记录一个count，一个item，每次出队一个数，更新item
如果当前队头的数字等于item，次数+1，直到不等
另一个队列放入count和item

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
对于长度为5的数组，只要发现1到5范围内的数x，就让它挪到x-1的位置（交换，不是替换，比如发现1就让它换到index为0的位置），其他的数字都不管，因为如果缺失这个范围的某个数，
那答案就是这个数，如果不缺失，那么答案就是最大数+1

如果出现重复的数，那么不做改变，因为前一个该数已经放在正确的位置上了。

    [3,4,-1,1]->[-1,4,3,1]->[-1,1,3,4]->[1,-1,3,4]，从头开始遍历，第一个不等于索引+1的数是2，所以答案是2


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


又一种正则匹配
“* ”代表任意一串的字符（包括空字符）
“?” 代表任意单个字符
匹配串s只有小写字母
模式串p只有小写字母和* 和?，看str和pattern是否匹配

使用4个计数，s和p作为指针，初始化为0，lastStar为上一个“* ”的在pattern中位置，初始化-1；match代表最新的一个“* ”可以在s中开始匹配的地方
遍历str串，
    如果pattern[p]==str[s] 或pattern[p]=='?'：s++，p++

    如果pattern[p]=='* ' ：s不动，p++，记录lastStar=p，match=s

    如果 lastStar!=-1 说明pattern[p]此时和str[s]不匹配，且pattern[p]不是* （才能走到这来），但前面有* ，则* 要匹配的串再加上一个字符，再匹配s和pattern中* 后面的。
    ：p=lastStar+1，match++，s=match

    否则（str[s]和pattern[p]不匹配，且前面没出现过* ，则pattern[p]也不是*），一定不匹配

遍历完str
此时如果p及后面都是* ，则还是可以匹配的（因为* 可以代表空串），但如果pattern[p]及后面还有其他字符，则不匹配


    举个例子
（cdf  *cde）
一开始就是* 则p++， match=0，lastStar=0
一直到f和e这，不匹配，要回头，p=lastStar+1，即c处，match=1，s=1处，即相当于* 此时和c匹配了，继续匹配df和cde
d和c不匹配，要回头，p=lastStar+1 即c处，match=2，s=2处，相当于* 此时和cd匹配了，继续匹配f和cde
f和c不匹配,要回头，p=lastStar+1 即c处，match=3，s=3处，相当于* 此时和cdf匹配了，继续匹配“”和cde
s到str的头了，跳出
此时如果p及后面都是* ，则还是可以匹配的（因为* 可以代表空串），但如果pattern[p]及后面还有其他字符，则不匹配

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

给出一个矩阵的螺旋序，即从左上角开始一直顺时针往里卷

设置左右上下的边界值，一旦到达边界值，下右边界值就-1，上左边界就+1
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

还是很重要的一点：加法的进位最多只能是1

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

计算x^2 = n的解，令f(x)=x^2-n，相当于求解f(x)=0的解，可以画个图看
   首先取x0，如果x0不是解，做一个经过(x0,f(x0))这个点的切线，与x轴的交点为x1。
   同样的道理，如果x1不是解，做一个经过(x1,f(x1))这个点的切线，与x轴的交点为x2。
   以此类推。
   以这样的方式得到的xi会无限趋近于f(x)=0的解。
   判断xi是否是f(x)=0的解有两种方法：
   一是直接计算f(xi)的值判断是否为0，二是判断前后两个解xi和xi-1是否无限接近（判断两数的整数部分是否相同，相同就算是满足接近的要求了）。

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

双指针定位，单指针扫描，
循环：
    p1和p2分别从前后往中间靠，p1到达第一个不为0的地方，p2到达第1个不为2的地方
    p从前往后走，遇到0则和p1的值交换，遇到2与p2交换，p遇到1继续走不管，
    循环继续：p1继续往后走到不为0的地方，，p2走到不为2的地方。。。
    循环停止的条件是p1超过p2 或 p超过p2

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

容易想到的方法是先把nums2全放到nums1的后面，然后直接对nums1快排

更好的方法是：    时间和空间复杂度都低的办法：从尾到头比较a1与a2的数字，并把较大的数字复制进a1的合适位置（尾部往前,索引从len1+len2-1开始，逐渐减小）



```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //把nums2拷贝到nums1中
        int pp = m+n-1;
        int p1 = m-1;
        int p2 = n-1;
        while(p1>=0 && p2>=0){
            if(nums1[p1]>=nums2[p2]){
                nums1[pp--] = nums1[p1--];
            }else{
                nums1[pp--] = nums2[p2--];
            }
        }
        while(p1>=0){
            nums1[pp--]=nums1[p1--];
        }
        while(p2>=0){
            nums1[pp--]=nums2[p2--];
        }
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

如果使用递归
当扫描到一个数字，有两种办法，一种是只取它，一种是取它和它后面的数字组合（前提是不超过26）
但这种做法会超时

考虑动态规划
dp[i]是以 第i个数字结尾的字符串能转换的可能数
dp[0]=1
dp[1]=1

（str[i-1]是第i个字符）
当str[i-1]是0时，dp[i] = dp[i-2] //此时s[i-2, i-1]必是合法的数
当str[i-1]不是0时
    若s[i-2, i-1]是合法的数(即str[i-2]不是0， 且s[i-2, i-1]<=26)，则当s[i-2,i-1]作为一个数时，可能性数为dp[i-2];当str[i-1]单独作为一个数时，可能性为dp[i-1]
                         dp[i] = dp[i-2] + dp[i-1]
    若s[i-2, i-1]不是合法的数，则只能str[i-1]单独作为一个数
                         dp[i] = dp[i-1]

这样dp的长度就得为len+1, 最终结果为dp[len]




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

对一个二叉树进行之字形的层序遍历，其实就是奇数层从左往右遍历，偶数层从右往左遍历
当遇到奇数层时，直接把节点从队列中取出并放入list，并将孩子节点加入队列
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

》方法一：
让每个节点的next域都填为它同层的右边的节点，一层中最右边的节点next域为null
直接用层序遍历即可

》方法二：
用如下方法，逐层构造：
    TreeNode pre = root;
    TreeNode cur = null;
    while(pre.left!=null) {
        cur = pre;
        //将当前cur所在的一层的节点都逐个赋给cur，并将下一层的next关系构造完成
        while(cur!=null) {
            cur.left.next = cur.right;
            if(cur.next!=null) cur.right.next = cur.next.left;
            cur = cur.next;
        }
        pre = pre.left;
    }


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

》方法二：
```java
class Solution {
    public Node connect(Node root) {
        if(root==null) return null;
        Node pre = root;
        Node cur = null;
        while(pre.left!=null) {
            cur = pre;
            //将当前cur所在的一层的节点都逐个赋给cur，并将下一层的next关系构造完成
            while(cur!=null) {
                cur.left.next = cur.right;
                if(cur.next!=null) cur.right.next = cur.next.left;
                cur = cur.next;
            }
            pre = pre.left;
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

对每个词进行广度优先遍历，遍历当前已到达的词能到达的所有词，即逐个替换每个字母

把所有词都放入一个词典set中，再有一个表示一个当前到达词的reachSet，遍历reachSet中的每一个词，将它能转换成的所有词的形式都扫描一遍，如果这个能转换的词在字典中，则把它加入一个临时toAddSet，同时把它从字典中删掉，当当前到达词都遍历完后，把当前到达词的reachSet换成这个临时toAddSet，继续遍历reachSet...

当当前到达词中有endword时，说明已经转换成功

当临时toAddSet中没有词时，说明字典中的词已经全部转过了，无法达到目标，则返回0



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
                //对于一次遍历，一定要全部都遍历完了，再做处理，不能半途遇到边界就退出
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
比如
检查[0]是回文，
    再检查[1]
        再检查[2]
    再检查[1,2]

检查[0,1]
    检查[2]

再检查[0,1,2]

...整个过程如此


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

举个例子：
    cost  2   1    5    2
    gas：1--》3--》2--》4-->1

从1开始走，1-》3，remain=-1  令remain=0，debt=-1
重新从当前位置开始走： 3-》2  remain=2  debt=-1
继续  2-》4  remain=-1  debt = debt-1 = -2，令remain=0
重新从当前位置开始走：4-》1  remain=2  此时debt=-2, remian=-debt，
此时遍历完了所有位置，且remain+debt>=0。说明从现在的当前的起始点开始走（注意不是当前点，是当前的起始点），可以把之前欠的都补上，所以当前的起始点就是所求点
为了更明确过程，继续这个过程：
继续，1-》3  remain=1
继续，3->2   remain=3
继续  2-》4  remain=0，刚好走完




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
把生成的链表也放入一个list，按照原链表的random序号去指新链表节点的random


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
    //sO(n)+tO(n)
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


    //sO(1)+tO(n)
    public Node copyRandomList(Node pHead){
        if(pHead==null){
            return null;
        }
        Node node = pHead;


        //把新节点创建好,并且连接在对应节点的后面
        while(node!=null){
            Node clone = new Node();
            clone.val=node.val;
            clone.random=null;
            clone.next = node.next;

            node.next = clone;
            node = clone.next;
        }

        node = pHead;
        //设置新节点的random域
        while(node!=null){
            Node clone = node.next;
            if(node.random!=null)
                clone.random = node.random.next;
            node = clone.next;
        }

        //重链两个链表
        node = pHead;
        Node newHead = pHead.next;
        while(node!=null){
            Node clone = node.next;
            node.next = clone.next;
            if(clone.next!=null)
                clone.next = clone.next.next;
            node = node.next;
        }
        return newHead;
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

即给一个字符串，给一个字典，要求把字符串分割成字典中单词的组合，求出所有的组合方式（相对顺序不能变）

用回退递归会导致超时，回退递归由于是跨着来遍历的，没办法用map保存已经检查过的字符串对于的结果（事实上也没有这个步骤
，所以会超时）

以字符串为主体进行dfs，这里节省时间的核心是用map来保存已经检查过的字符串对应的结果，过程如下代码：

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
            //如果当前串以word开头
            if(!s.startsWith(word)) continue;
            String sub = s.substring(word.length(), s.length());
            //找到余下部分的结果列表，并把本单词加到余下部分每个结果的开头，作为本层结果的开头
            //如果subList为空，则res也加不进去word
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
所以左侧上下都除以上下的最大公约数后为 y/x，右侧上下都除以上下的最大公约数后也必为y/x
这样以这个x,y作为key存储在这条直线上的点数

用Map<Integer,Map<Integer,Integer>> map = new HashMap<Integer,Map<Integer,Integer>>();记录x-y对应下的
（这个Map套Map十分关键, 适用于有两个关键字存值的情景，比如这里的关键字就是一个斜率的分子分母，二者共同确定一个斜率）
result
for(i){
    map.clear()
    max=0;
    overlap=0
    for(j){
        xx = points[j].x-points[i].x;
        yy = points[j].y-points[i].y;
        if(xx==0&&yy==0){
            //points[i]与points[j]重合，不论哪条直线，两者一定在同一直线(因为就是一个点)
            overlap++;
            continue;
        }
        gcd = getGCD(xx,yy)找到xx和yy的最大公约数
        x = xx/gcd, y=yy/gcd
        将 map.get(x).get(y)的值加1，意味着该斜率下，又有一个点与points[i]在一条直线上
        max = Max(max, map.get(x).get(y)); //记录当前与points[i]在同一条直线上的最多的点数
    }
    result = Max(result, max+overlap+1) //对于每个点points[i]来说，都要计算与其在同一直线上的最多点数+自己本身+与自己重合的点，其中的最大值即为所求
}

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

    
找到峰值元素，峰值元素是指比它两边的元素都大，如果用线性复杂度很简单，但这里要用对数复杂度
对数就得用二分查找的思想，查看mid是否是峰值，
如果不是，则分别递归查看它的两边的数组，这里和普通二分有区别，因为两边都要找，但如果先找的一边有峰值，就可以直接返回，后面的那一边就不用找了
如果mid不是峰值，且左右两边都没有峰值找到，则返回-1.

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
        //如果一边没有峰值，则会返回-1，最上层的二分不会走到这里，但下层的查找则可能走到这里
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

还有一个重要的是在考虑这个问题时要考虑所有的边界情况，包括:负整数、可能的溢出等。

在进行除法时，使用HashMap存储余数及其相关索引，以便每当出现相同的余数时，我们知道存在重复小数部分。

如对于2/3：商0余2，整数部分为0，开始计算小数部分，设为s，2* 10=20  20/3 = 6，s插入6，20%3=2  2* 10=20 20已经出现过了，其索引为0，所以把括号插入到s中索引为0的地方，即(6 ,再在s末尾添加上 ）因此结果是0.(6)

## 整数和小数：
对于被除数n和除数d，其整数部分是： n/d
其小数部分如果用一个str存储
//处理负数的符号,用异或处理，如果两个数同号，则为正，否则为负
res.append(((numerator>0)^(denominator>0))?"-":"");
map保存出现过的被除数及其出现的位置，一旦再次出现，说明进入了循环,获取到这个第一次出现循环的位置，然后插入括号，跳出循环
n = n%d; //这一步保证剩下的都是小数部分，因为是取余，能除成整数的已经没有了
i=0
while(n!=0){
    n * = 10;
    if(map.containsKey(n)){
        str.insert(map.get(n), "(");
        str.insert(")")
        break;
    }
    map.put(n, i)
    str += (n/d);
    n %= d;
    i++;

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
        //保存出现过的被除数，一旦再次出现，说明有循环,map保存出现过的被除数及其位置索引
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
要求n!末尾有多少个0，
对于任意一个数m，它可以被写成：m=2^x * 5^y * (...其他部分)

0只能是m中的2和5相乘得到的，而在1到m（m任意）这个范围内，2的个数要远多于5的个数，
所以这里只需计算从1到n!这个范围内有多少个5就可以了。即上面y的个数


考虑n!的质数因子。后缀0总是由质因子2和质因子5相乘得来的，如果我们可以计数2和5的个数，问题就解决了。
//考虑例子：n = 5时，5!的质因子中(2 * 2 * 2 * 3 * 5)包含一个5和三个2。因而后缀0的个数是1。
//n = 11时，11!的质因子中((2 ^ 8) * (3 ^ 4) * (5 ^ 2) * 7)包含两个5和八个2。于是后缀0的个数就是2。
//我们很容易观察到质因子中2的个数总是大于等于5的个数,因此只要计数5的个数即可。
//那么怎样计算n!的质因子中所有5的个数呢？一个简单的方法是计算floor(n / 5)。例如，7!有一个5，10!有两个5。
//除此之外，还有一件事情要考虑。诸如25，125之类的数字有不止一个5。
//例如n=25, n!=25*24*23*...*15...*10...*5...*1=(5*5)*24*23*...*(5*3)*...(5*2)*...(5*1)*...*1，其中25可看成5*5,多了一个5，应该加上
//处理这个问题也很简单，首先对n/5，移除所有的单个5，然后/25，移除额外的5，以此类推。下面是归纳出的计算后缀0的公式。
//n!后缀0的个数 = n!质因子中5的个数= floor(n / 5) + floor(n / 25) + floor(n / 125) + ....
即如下代码：第一遍是求n/5 第二遍是求n/5/5即n/25...
        int result = 0;
        while(n>0){
            n /= 5;
            result += n;
        }


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

给出一个非负整数的数组，组合它们使其成为能组成的最大数字
这里直接用排序的工具Comparator，重写对于数组的排序算法，对于两个数a和b
如果ab大于ba，则a排在b的前面，直接将nums进行这样的排序，可得答案
需要特殊处理的是，如果数组中全是0，则直接返回0

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

把一个数组循环右移k位
如果使用O(1)的空间复杂度
使用2层循环，并使用一个计数器记录已经被放到合适位置上的数字个数
外循环：for(i: nums.length) 如果计数器==数组长度，表示所有数字都被放置完毕，跳出循环。curIndex=i;  oriIndex=curIndex
内循环：设 nextIndex = (curIndex+k)%nums.len  交换 nums[curIndex]和nums[nextIndex] ，此时nums[curIndex]到达了合适的位置，接着往后
      放nums[nextIndex]，更新curIndex=nextIndex 继续循环,每成功交换一个数计数器+1（表示有一个数已经被放到了合适的位置上）
      循环条件是curIndex!=oriIndex （即已经绕了一圈回来了）


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
    Integer.toBinaryString(int n)用于把一个整数转换成其二进制表示的字符串，但会删去前面的0：比如 0001，会直接转成 1
    stringBuilder.reverse()  翻转字符串(builder)
    因为新的整数必须为32位，但由于上面转换的时候删去了头上的0，所以此时应该要在后面补，要提前补好再转换
    如，若整数为6位：n: 00110  转成二进制时会把前面的0去掉，转换后是 11，因此需要在后面补全0，即 1100
    然后再把1100转成整数即可

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

    好做法：数字不断无符号右移1位，然后和1做与，如果得1，则证明当前最后一位是1，累加，直到右移到该数字为0
           还可以不动数字，动1，如数字先和1做与，然后1左移1位和数字做与，然后再左移一位。。

```java
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count =0;
        while(n!=0){
            count = (n&1)==1?count+1:count;
            //无符号右移
            n = n>>>1;
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

使用素数判别法：使用一个数组来标志，当遍历到一个数x是素数时，将 2x，3x...n全部标记为非素数，遍历直到n

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

给出一系列图中的有向边，求出一个拓扑排序

给出的数组相当于是边的数组，这里仍然是求有向图的拓扑排序，用入度来做
把每个节点的前驱节点都保存到各自的一个数组中
每当遍历到一个元素时，它的后继元素入度-1，每次都遍历入度为0的节点

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

把字典保存在trie p中，二重循环遍历面板中每一个字母，对其进行dfs
每当遍历到一个位置时，p也随着到相应的位置，当二者不匹配时，不符合。把遍历过的位置先置为一个特殊字符，比如'#'，防止下次又遍历到它。再深入它的邻居位置，当所有邻居位置都扫描过后，再把它置回。
当遍历到一个单词时，p也能到相应的位置，说明匹配，加入结果集，最后结果集要去重（所以可用set）


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

中缀-》后缀：这里栈是个暂时存放的地方，最终后缀表达式是输出的东西
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

》解：
从左往右扫描，当遇到矩形边时，假设其横坐标为x，找到该x上最高的那一个点，如果最高点和前面的高度一样，则不记录，否则记录
为了不漏掉高度为1的点

把l和r都存入一个数组中，并且将其排序，结果为list。即list中为所有矩形的左右边界
遍历list，若当前为x，求出x对应的最高高度hh（遍历所有的建筑物，满足x>=l 且 x< r的最大h, 如果没有建筑物满足当前x，则x对应的最高高度为0）
把[x,hh]加入结果集

遍历结果集，如果连续多个同高，则只保留最前面的一个。如 [3,10], [6,10], [11, 10] 只保留[3,10]


```java
class Solution {

    public List<List<Integer>> getSkyline(int[][] buildings) {
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
        //这样可能还是会添加一些同高的数据，在后面做清除
        for(int x: list){
            int height = findMaxHeightWithX(buildings, x);
            ArrayList<Integer> ans = new ArrayList<>();
            ans.add(x);
            ans.add(height);
            tmp.add(ans);
        }

        //如果后面的和前面的同高，去掉后面的
        int preH = 0;
        ArrayList<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<tmp.size();i++){
            if(tmp.get(i).get(1)==preH){
                continue;
            }
            res.add(tmp.get(i));
            preH = tmp.get(i).get(1);
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
但每个队列只存储数列的一半，max中始终保存着当前数字小的一半，min中始终保存着当前数字中大的一半，若二者尺寸相同，则max出队和min出队就是最中间的两个元素；若不一样，只能是max比min多一，所以max出队就是中间的元素

先把一个数字加入max，然后从max中poll出的一定是max中最大的数（规定了优先级），
然后加入min，如果此时总数是偶数，则max和min的个数一样
如果是奇数，则从min中poll出min中最小的数，放入max中

这样能保证，如果总数是偶数，则两个队头的元素就是数组中最中间的两个数
如果总数是奇数，max队头的元素就是最中间的那个数

## 记住这种找中间值的办法（两个优先级相反的优先级队列）：先往大里放，再往小里放，小得多的话再往大里放

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

把数组改造成一个二叉搜索树，其节点有4个域：
val：本身的值
leftSum：该节点的左子树中的节点值
smaller：插入该节点时树中比它小的节点总数
dup：该值在邻旁的重复数，

比如对于 [3, 2, 2, 6, 1]，从后往前向树中插入节点

对于重复的节点，一开始并不知道重复不重复，当遍历到与要插入的节点值相等的节点，则在dup域上+1


对bst中的任意一个节点，比它小的节点有几种可能：
    1.它的左子树上的所有节点；
    2.它的祖先链上（包括它自己）的所有作为右子节点其父节点的左子树上所有节点

节点有一个记录在插入自己时，树中比自己小的节点个数域s，一个记录自己左子树节点数的值l
因此，s只在插入节点的时候赋值，插入后续节点时就不动了，而l要一直跟踪变化

因此需要累积一个每节点左子树上的节点数

在插入一个节点时，每当右拐，则在该节点m域上增加当前位置节点的l域+当前位置节点的重复次数
每当左拐，则在当前位置节点的l域上+1

统计当node插入后的s域即可，但要格外注意的是，当node的值存在时，说明前面已经出现过该值，别忘记加上该值的左子树的节点个数
（如果之前x就插入了，在第一次出现后第二次出现前，该节点的左子树可能增加了若干值，所以要加上）


```java
class Solution {


    class TreeNode {
        int leftChildren; //左子树上的元素个数
        int smaller;
        int val;
        int dup=1;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val, int leftChildren){
            this.val = val;
            this.leftChildren = leftChildren;
        }

    }


    public List<Integer> countSmaller(int[] nums) {
        int len = nums.length;
        Integer[] ans = new Integer[len];
        if(len==0) return Arrays.asList(ans);

        TreeNode root = new TreeNode(nums[len-1], 0);;
        ans[len-1]=0;


        //每次node都是根节点
        for(int i=len-2;i>=0;i--){
            TreeNode inserted = new TreeNode(nums[i], 0);
            insert(i, null, root, inserted, true, ans);
        }
        return Arrays.asList(ans);

    }

    public void insert(int i, TreeNode pre, TreeNode cur, TreeNode node, boolean curIsLeft, Integer[] ans){
        if(cur==null){
            if(curIsLeft)
                pre.left = node;
            else
                pre.right = node;
            ans[i] = node.smaller;
            return;
        }

        if(cur.val==node.val){
            cur.dup++;
            //别忘了除了smaller，还有增加其左子树上的个数
            node.smaller += cur.leftChildren;
            ans[i] = node.smaller;

        }else if(cur.val<node.val){
            //往右拐，则比node小的数要加上当前节点左子树节点数和当前节点的重复次数
            node.smaller += cur.leftChildren + cur.dup;
            insert(i, cur, cur.right, node, false, ans);
        }else{
            //往左拐，当前节点左子树个数+1
            cur.leftChildren++;
            insert(i, cur, cur.left, node, true,ans);
        }
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

要保证的是，奇数位的数字一定大于它两边的偶数位的（从0开始算） 
先排序，排完序然后从左往右奇数索引位置放大于中位数的数, 然后从右往左在偶数索引位置放小于中位数的数, 剩下的位置都放中位数. 其时间复杂度为O(nlog(n)), 空间复杂度为O(n).

[1, 5, 2, 1, 6, 4]-》[2, 6, 1, 5, 1, 4] （可能有多种结果，只求出其中一个结果）

        //小数字从右往左放，从右往左依次增大，能保证如果需要中间数，那它一定在最左端（对于小数字而言，整体的中间数就是其中最大的）
        //大数字也从左往右放，从右往左依次增大，能保证如果需要中间数，那它一定在最右端（对于大数字而言，整体的中间数就是其中最小的）
        //这里的话，就算两头是中间数，也不会出现相邻的相同了
        //小数字放偶数位
        //如果末尾索引是偶数，就从末尾开始填，如果是奇数，就从末尾的前一个开始填

```java
class Solution {
  public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        int[] copy = new int[nums.length];
        System.arraycopy(nums, 0, copy, 0, nums.length);

        //小数字从右往左放，从右往左依次增大，能保证如果需要中间数，那它一定在最左端（对于小数字而言，整体的中间数就是其中最大的）
        //大数字也从左往右放，从右往左依次增大，能保证如果需要中间数，那它一定在最右端（对于大数字而言，整体的中间数就是其中最小的）
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

用odd，even分别代表奇偶节点，初始化odd为第一个节点，even为第二个节点。并且保留当前这两个节点的引用。
循环迭代，每次令odd的next为它的next的next，even也一样。然后再让odd = odd.next, even=even.next;

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
这里有个核心地方在于，保存已经遍历过的位置作为出发点的最大路径(不保存再利用的话会超时)

因此这里的关键在于，从一个cell出发，它的最长路径值是一定的， 不用管它的上一个cell是谁，也不需要用标志数组保存已经遍历过的节点(不存在走回头路的可能，因为回头的cell比当前cell小，走不过去的，而不像一般的dfs，还要保存已经遍历过的标志)

对一个位置使用dfs去探究它的四个邻居，当邻居值大于它时，递归dfs这个邻居，并且保存其中的能到达的最长路径，将其作为v，保存到缓存数组中
最后求出所有位置的缓存数组中的最大值


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
[[1,1],2,[1,1]]-》[1,1,2,1,1]； [1,[4,[6]]]-》[1,4,6]
每个元素要么是整数，要么是列表——其元素也可以是整数或其他列表。

其底层是一个链表队列LinkedList：l

next()保证如果有 hasNext为true，则返回l.pop.getInteger()

hashNext负责：
while：
1.如果l下一个是整数，则直接返回true
2.如果l下一个仍是list，则把这个list的元素逐个添加进l，然后继续上面的while

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
再遍历另一个，一旦出现一次，若该数字在map中的v>0,则v-1，并把该数字保存到结果集中

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

我们平时计算时是将对应位相加和进位同时计算，其实可以保留下进位，只计算对应位相加，保留进位的位置（值）。接下来，将进位向左移动一位，将上一步的结果与移位后的进位值进行对应位相加，直到没有进位结束。
（过程具体可见：
https://www.cnblogs.com/dyzhao-blog/p/5662891.html
）

为了好做，则把使用数字的二进制，用以下操作：
对于二进制数的而言，对应位相加就可以使用异或（xor）操作，计算进位就可以使用与（and）操作，在下一步进行对应位相加前，对进位数使用移位操作（<<）。

而且由于整数是补码形式存在的，所以正数负数无所谓，加出来还是补码

非常神奇且有意思的做法，但是不知道原理


假如算13+22
13二进制：1101
22：10110

 1101
10110

进位数：作与操作：100
和数：异或操作：11011

进位数左移一位：1000，与上一步和数再求进位数和和数

进位数： 1000
和数：  10011
       10000

进位数左移一位：10000，继续：

进位数： 10000
和数：  11

进位数左移一位 100000 继续：

进位数： 0
和数： 100011  

进位数为0，停止，此时和数为最终结果 100011二进制转为十进制 为 35


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
简单的做法，每一行都维持一个头指针，每次取头指针中最小的值，然后该头指针后移，再次比较，直到比较第k次为答案

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

数据结构中有以下域：
一个整数list，保存加入的数字
一个hashmap，保存该数字的及其对应的位置
一个random对象

insert(a)：如果map中有这个key，返回false，，添加到map中，k为a，v为list当前大小，然后把它加入到list中
remove(a)：这是关键一步：通过map找到a在list中的位置，如果该数不是最后一个，则把最后一个数放到该位置上，这样该数就会没有，最后一个数就会多一个，然后再删除最后一个数（这是效率高的删除方式，但前提是对位置不敏感），并且从map中删掉k为a的记录
getRandom：r = random.getInt(list.size()) 以list的大小为上界，求出一个随机值，然后返回list.get(r)


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
        //下面的删除方法能做到O(1)的删除，前提是对位置不敏感
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

计算4个数组A, B, C, D中各取一个数得到和为0的取法总数
直接用O(n^4)做会超时

好做法是先算A和B的各个和值，保存起来，再去算C和D的和值，也就是“中途相遇法”

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
遍历即可，if先判断是否等，等则返回，else if判断是否大，大则返回，循环结束没返回说明都比给定值小，返回数组长度即可
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

三重循环+递归

solve(){
    for i:行
        for j:列
            如果该位置是待填充的位置
                for c: 1-9
                    if(isvalid()){
                        将c填入该位置
                        if(solve())
                            return true;
                        else
                            将该位置恢复到未填充的状态
                    }
                //尝试在这个位置上放置所有数字，都无效（都没有返回true），说明此次验证失败   
                return false

    //全都填满了，但没有返回false，说明成功
    return true;    
}

//检查字符c是否可以放在row-col处
isvalid(){
    for(int i=0;i<9;i++){
        if(board[i][col]==c) return false; //检查该列
        if(board[row][i]==c) return false; //检查该行
        //检查3x3方格，注意这个方格不是以row-col为中心的，而是9x9中的3x3大方格
        //要在i从0到8这个过程中检查这9各方格
        //row-col所在的大方格的列是 col/3，所在大方格的行是 row/3
        //为了能遍历完，行上用i/3,列上用i%3,,如果都用i/3或i%3，那么9次只能遍历3个格子
        if(board[(row/3)* 3+i/3][(col/3)* 3+i%3]==c) return false;
    }
    return true; 
}



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

给定一组候选数字编号和一个目标数字，找出候选数字之和等于目标数字的所有唯一组合。

候选项中的每个数字只能在每个组合中使用一次。

注意:

所有的数字(包括目标)都是正整数。
解集不能包含重复的组合。

先把数字排序，然后使用标准的trackback
用递归，一个一个加着试，每次只加比当前数字大的数字，当剩余的目标值小于当前准备加的数字，则后面的数字都不用加了

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

num为乘积数组，是int类型的
那么 a[i] * b[j] = c， c有两位，高位为c[0],低位为c[1]
把c[0]加到num[i+j]，c[1]加到num[i+j+1]，当所有a中数字和b中数字乘完之后
从低位到高位依次遍历num，当某一位大于10时，保留个位数为该位的值，然后往前进一
这样的结果就是最终的乘积

其实就是把每一位的竖式乘法再分步


详情见 

https://leetcode.com/problems/multiply-strings/discuss/17605/Easiest-JAVA-Solution-with-Graph-Explanation，


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

给定一个非负整数数组，初始位置是数组的第一个位置。

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

》第一种贪心算法：
以目的地为导向，从前往后找能第一个到达目的地的位置，找到后，将该位置标记为新的目的地，然后重复这个过程直到目的地为第一个位置
（重点掌握这种贪心）

》第二种贪心
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
    public static int jump(int[] A) {

        int jmp = 0;
        int dest = A.length - 1;        // destination index

        while(dest != 0){       // 不断向前移动dest
            for(int i = 0; i < dest; i++){
                if(i + A[i] >= dest){       // 说明从i位置能1步到达dest的位置
                    dest = i;       // 更新dest位置，下一步就是计算要几步能调到当前i的位置
                    jmp++;
                    break;      // 没必要再继续找，因为越早找到的i肯定越靠前，说明这一跳的距离越远
                }
            }
        }
        return jmp;
    }



    //贪心算法2，这种贪心没看懂
    //cur表示最远能覆盖到的地方，用红色表示。last表示已经覆盖的地方，用箭头表示。起始时，它们都指向第一个元素。
    public int jump(int[] nums) {
        int ret = 0;//当前跳数  
        int last = 0;//上一跳可达最远位置  
        int cur = 0;//当前一跳可达最远位置
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

标准trackback，还是用trackBack和Set,用一个标志数组表示当前数是否已经被算进去，当已经被算进去的数等于总数，则添加进结果集

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

先把二维数组填充满.

    trackback(char[][] board, int n, int row, int queenCount){
        //皇后数不能超过n
        if(queenCount>n) return;
        //皇后数为n时，即为一个解
        if(queenCount==n){
            addOneList(board, n);
            return;
        }
        每一行只能有一个皇后，在该行(row)中如果该位置(row,j)可以放置皇后，则再到下一行(row+1)放置皇后。
        由于在该行可能不止该列能放置皇后，所以在trackback，回退去检查该行的其他位置
        遍历
        for(int j=0;j<n;j++){
            if(isValid(row, j,board,n)){
                board[row][j]='Q';
                trackBack(board, n,row+1,queenCount+1);
                board[row][j]='.';
            }
        }
    }
    //判断当前位置(i,j)放置皇后是否合法
    boolean isValid(int i, int j, char[][] board, int n){
        先检查该位置四周8个位置是否有其他皇后，有则返回false
        再检查该列上是否有别的皇后 //由于上面的tackBack能保证一行只有一个皇后，所以这里只需要判断该列是否有别的皇后即可
        再检查两条斜线上的,分别有右上，右下，左上，左下四种情况
        如果上述检查都通过，则返回true;
    }





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

n皇后2，返回的不是结果集，而是结果个数，和上面的一脉相承
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

遍历所有区间和新区间，如果当前遍历到的区间和新区间有重合，则将新区间更新为并集，然后继续这个过程，期间把没有重合的单独保存在结果集
最终的结果就是新区间以及没有重合的区间，只需要将新区间放入答案集中，然后排序（需要重写Comparator）即可

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

        List<Integer> list = new ArrayList<>();
        for(int i=1;i<10;i++)
            list.add(i);
        int count = n;
        //为了顺序从0开始，k先-1
        k = k-1;
        while(count>0){
            int num = k/factorial(n-1); //factorial是计算阶乘的函数
            builder.append(list.get(num));
            //每加入一个数，就从list中删掉该数
            list.remove(num);
            k = k%factorial(n-1);
            n = n-1;
            count--;
        }

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

把链表中的后面k个节点提到前面来

相当于把倒数的k个节点插入到链表头，用双指针法定位到倒数第k个节点，然后把该节点前面的next置空，末尾节点的next置为原头结点
若k大于链表长度，则 k=k%len
注意要对处理后k=0的情况做特殊处理，直接返回原头结点即可


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

    //sO(1)+tO(n)
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null) return null;
        int len = 0;
        //计算出链表长度
        ListNode node = head;
        ListNode tail = head;
        while(node!=null){
            len++;
            node = node.next;
        }
        if(len==1) return head;
        //不论大不大都可以先这么处理
        k=k%len;
        //处理过后k==0，则返回原链表
        if(k==0) return head;

        //找到倒数第k个节点，及其前驱
        ListNode kNode = head;
        ListNode kPre = head;
        int step = 0;
        while(tail.next!=null){
            tail = tail.next;
            step++;
            if(step>=k){
                kPre = kNode;
                kNode = kNode.next;
            }
        }
        tail.next = head;
        kPre.next = null;
        return kNode;
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
对于任何nums[i][j]=1，则dp[i][j]=0  //因为此处是障碍

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
每次取数组的中间值为根，把数组分成左子树和右子树，然后再递归构建
    TreeNode buildTree(int start, int end){
        if(start > end) return null;
        //每次取中间的值作为根
        int mid = (start + end ) / 2;
        TreeNode root = new TreeNode(nodes[mid].val);
        root.left = buildTree(start, mid-1);
        root.right = buildTree(mid+1, end);
        return root;
    }

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

给定一个二叉树，判断它是否为高度平衡二叉树。

对于该问题，定义高度平衡二叉树为:

一种二叉树，其中每个节点的两个子树的深度相差不超过1。

先将以每个节点为根的树的高度保存在hashMap中：树的高度=max(左子树高度，右子树高度)+1
然后对于任何一棵树，它为平衡二叉树 等价于下面两个条件同时满足
1.它的左子树和右子树高度差不超过1
2.它的左子树和右子树都是二叉平衡树


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
    》方法一：
    使用记录层数的层序遍历（两个队列），当遍历到第一个叶子时，返回层数
    》方法二：
    使用递归：
        当节点为null时（只有最顶层的节点为null才会走到这，因为下面的逻辑约束不会有其他的null节点进入该递归）返回0
        当节点是叶子时(左右孩子都为空)，返回1
        当节点不是叶子时（左右孩子有一个不为空），返回其左右子树中的最小深度+1
     注意，不能直接递归左右两个孩子，而要判断它们是否为空，不为空后在判断，否则当root左孩子为2层，右孩子为null时，最小深度会变成1（因为右边是0），而应该是3（右边根本就不算）

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


    //方法二：
    public int minDepth(TreeNode root){
        //只有最顶层才可能返回这个0，其他情况下不会出现root为null
        if(root==null) return 0;
        //保证只有叶子节点才会返回定数字
        if(root.left==null && root.right==null) return 1;
        else{
            //对于有孩子的节点，一定不是叶子，求其左子树和右子树中的最小深度
            int l = Integer.MAX_VALUE;
            int r = Integer.MAX_VALUE;
            if(root.left!=null){
                l = minDepth(root.left);
            }
            if(root.right!=null){
                r = minDepth(root.right);
            }
            return Math.min(l,r)+1;
        }
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

很简单的递归，找到就直接返回true

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

给定两个整数n和k，返回1…n中任意k个数的不同的组合。

简单的回退递归，传入的参数有start的（前面的不要）。

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
两个字符串先变成一样长（根据更长的标准来），然后从低位到高位一位一位加，记录每一位的进位，将每一位的计算结果插入到结果字符的首位，注意处理最终可能出现的进位

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

给定一个单词数组和一个width maxWidth，对文本进行格式化，使每一行都具有精确的maxWidth个字符，并且完全(左对齐和右对齐)。

你应该用贪婪的方式来包装你的话;也就是说，在每一行中尽可能多地填充单词。在必要时填充额外的空格，以便每行都有精确的maxWidth字符。

单词之间的额外空格应该尽可能均匀地分布。如果一行的空格数不能在单词之间平均分配，那么左边的空格将比右边的空格分配更多的空格。

对于最后一行文本，它应该左对齐，并且单词之间不应该插入额外的空格。

注意:

一个单词被定义为一个由非空格字符组成的字符序列。
每个单词的长度保证大于0且不超过maxWidth。
输入数组单词至少包含一个单词。

遍历字符串数组：

若当前行的长度小于宽度
    如果是该行第一个串，则只需要该串长度小于宽度即可添加
    如果如果不是第一个字符串，则需要该字符长度+1小于宽度才可添加
    若当前行长度小于宽度，但当前串无法加到该行中了，则对该行进行补空格对其至宽度

若当前行长度等于宽度
    结果集增加当前行，重置当前行为空。

对最后一行进行特殊处理，与上面的不全空格的方法不同，空格要尽可能多的放在最后面

补全空格：    
    1. 先找有空格的地方，顺序从左到右去每个位置补空格，一遍不够再来一遍。。。
    2. 字符串中如果没有空格，则直接在字符串末尾补足maxwidth-n个空格, n是当前串的长度


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
                //如果是该行第一个串，则只需要该串长度小于宽度即可添加
                if(firstWord && words[i].length() <= maxWidth){
                    curLine.append(words[i]);
                    firstWord = false;
                    i++;
                    continue;
                }else if(!firstWord && words[i].length()+1+curLine.length() <= maxWidth){
                    //如果不是第一个字符串
                    //则需要该字符串长度+1小于宽度才可添加
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
用栈来做，当遇到/时，将其后面第一个不为/的字符串（压栈），如果后面是"."，则不动，如果后面是“..”则当前栈弹栈
最后的路径是栈中元素倒过来

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

遍历数组，记录上一个出现过两次的元素，当再出现时，直接将其替换成最大的数（每当替换一个数，计数器+1，最终新的数组长度=原数组长度-计数器）

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
2）//区别于上述的最关键一步：若left，mid，right的值相同，则同时缩小左右范围
    if( (nums[left] == nums[mid]) && (nums[right] == nums[mid]) ) {++left; --right;}
3)否则，检查前半部分是否是顺序(即nums[left]<=nums[mid])
如果是，请转到步骤3)，否则，请转到步骤4)
4)检查target是否在[left, mid-1]范围内(即nums[left]<=target < nums[mid])，如果是，则在前半部分进行搜索，即right = mid-1;否则，下半部分搜索 left = mid+1;
5)检查target是否在[mid+1, right]范围内(即nums[mid]< target <= nums[right])，如果是，则在后半部分进行搜索，即left = mid+1;否则前半部分进行搜索 right=mid-1;

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
                if( (nums[left]<=target) && (nums[mid] >= target) ) right = mid-1;
                else left = mid + 1; 
            }
            else{
                if((nums[mid] <= target) &&  (nums[right] >= target) ) left = mid+1;
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
如果节点node的后继等于它，则前驱不变，往后遍历node，直到node的值与其后继的值不同，连接前驱的next为node，也即删除中间的节点

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

给定一个已排序的链表，删除所有重复项，使每个元素只出现一次。

保留前驱
如果当前节点值和前驱的相同，则删除当前节点

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

》方法一：sO(n)
这里只对小于x的节点的位置进行了约束，没说对于大于x的节点如何做，那就是不动
把小于x的节点按顺序放入一个数组，其他的放入另一个数组，然后把这个两个数组连接起来

》方法二：sO(1)
更好的办法是，设置两个链表头: sHead和 tHead，遍历链表，当遇到比target小的数时，接入sHead，更新指针s指向sHead链表的最后一个节点
当遇到比target大或等的数时，接入tHead，更新指针t指向tHead链表的最后一个节点，最后把sHead-tHead把两个链表连起来即可

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
    //方法一
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


    //方法二
    public ListNode partition(ListNode head, int x) {
        ListNode sDummy = new ListNode(0);
        ListNode tDummy = new ListNode(0);
        ListNode s = sDummy;
        ListNode t = tDummy;

        ListNode node = head;
        while(node!=null){
            if(node.val==x || node.val>x){
                t.next = node;
                t = node;
                node = node.next;
                t.next = null;
            }else{
                s.next = node;
                s = node;
                node = node.next;
                s.next = null;
            }
        }

        s.next = tDummy.next;
        return sDummy.next;
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


打乱的字符串，给定一个字符串s1，我们可以递归地将它划分为两个非空子字符串，从而将它表示为二叉树。
下面是s1 = "great"的一种可能表示:

    great
   /    \
  gr    eat
 / \    /  \
g   r  e   at
           / \
          a   t

要打乱字符串，我们可以选择任何非叶节点并交换它的两个子节点。

例如，如果我们选择节点“gr”并交换它的两个子节点，它将生成一个打乱的字符串“rgeat”。

    rgeat
   /    \
  rg    eat
 / \    /  \
r   g  e   at
           / \
          a   t
我们说“rgeat”是一串“great”的乱串。
如果我们继续交换节点“eat”和“at”的子节点，它会生成一个打乱的字符串“rgtae”。
我们说“rgtae”是一串“great”的乱串。

判断s2是否是s1的乱串
如果s1和s2相同，则是。
首先要判断s1和s2中出现的字母及个数是不是完全一样，不一样则不是

还是要用递归判断：
遍历s1，位置索引为i
    如果s1[0,i)是s2[0,i)的乱串，且s1[i,len)是s2[i,len)的乱串，则s1是s2的乱串
    如果s1[0,i)是s2[len-i,len)的乱串，且s1[i,len)是s2[0,len-i)的乱串，则s1是s2的乱串
//这个很关键，可以是s1的头对应s2的头，也可以是s1的头对应s2的尾，只要有一种情况满足，那总体就是满足的
遍历完s1若都不满足，则返回false


总体的思路就是分割子问题，持续把问题范围变小再解决


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

灰色代码总数是2^n个，先从全0开始。对任意一个序列，遍历其每一位，把该位取反，然后查看结果集中是否有该序列，若没有则加入结果集，把该新序列更新为要遍历的序列，否则继续遍历该序列。直到结果集中的总数为2^n个，所有序列都已求得



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

带start的trackBack

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

使用带start的trackBack，因为是ip地址，所以每个数字是0-255，最少一位最多3位。

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
求出所有这种组合即可
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

s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"

  Ø d b b c a
Ø T F F F F F
a T F F F F F
a T T T T T F
b F T T F T F
c F F T T T T
c F F F T F T

首先，这道题的大前提是字符串s1和s2的长度和必须等于s3的长度，如果不等于，肯定返回false。那么当s1和s2是空串的时候，s3必然是空串，则返回true。所以直接给dp[0][0]赋值true，然后若s1和s2其中的一个为空串的话，那么另一个肯定和s3的长度相等，则按位比较，若相同且上一个位置为True，赋True，其余情况都赋False，这样的二维数组dp的边缘就初始化好了。下面只需要找出递推公式来更新整个数组即可，我们发现，在任意非边缘位置dp[i][j]时，它的左边或上边有可能为True或是False，两边都可以更新过来，只要有一条路通着，那么这个点就可以为True。那么我们得分别来看，如果左边的为True，那么我们去除当前对应的s2中的字符串s2[j - 1] 和 s3中对应的位置的字符相比（计算对应位置时还要考虑已匹配的s1中的字符），为s3[j - 1 + i], 如果相等，则赋True，反之赋False。 而上边为True的情况也类似，所以可以求出递推公式为：

例如对于第二行第二列(a,d)，如果从上面更新过来，说明此时的字符串是da，如果从左面更新过来，说明此时字符串是ad，只要有一种情况能成立，此位置就是T，否则就是F
使用这样boolean类型字符表盘，屏蔽了当前字符串具体是什么，只需知道能不能成为它即可。

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
先用中序遍历一遍，遍历出的结果与排序结果逐位置对比，必然只有两个位置不对：
第一个：321，正常序：123  ：1和3不对
第二个：1324  正常序：1234： 2和3不对
记录这两个值
再中序遍历一遍树，当遍历到位置不对的两个节点时，替换其值为另一个即可（如果要替换节点，还要前序遍历一遍找到它们两个各自的父节点并保存下来，交换各自的父节点和子节点）

注意这道题是只交换两个节点值，不交换节点，所以算简单题

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

给定两棵二叉树，写一个函数来检查它们是否相同。

很简单的递归，对于2个节点来说，它们的值相等，且左右子树都相同才算一样

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

我们将构建一个数组mem，其中mem[i+1][j+1]表示S[0...j]（前j+1个字符）包含T[0 . .i]（前i+1个字符）的不同子序列的个数。因此，结果将是mem[T.length()][S.length()]。
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

然后求最后一行所有的dp中的最小值即可

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

用bfs，找到dict中所有的词到起始词的最短路径，这一步是Word Ladder的一个加强版，因为要多一步记录距离，但核心步骤不变，仍然是遍历-发现在字典中-放入set-从字典中删除-继续遍历，但同时要用map记录距离，并且用一个list记录字典中每个词能转换的邻居，如，A和B可以换一个字母后互相得到，且AB都在字典中，则AB就是邻居，但要注意的是，只有距离start近的节点加距离远的节点，反过来不行，例如，A距离startword近，而B远一步，neighbor(A)中有B，但neighbor(B)中没有A，这是为了更好地为dfs服务

然后用dfs，其实也就是trackback，从beginwor开始找到每一个距离值大一的词，并加入队列。直到当前词为endword，则找到一个答案序列

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
                    //注意的是，nodeNeighbors只有距离start近的节点加距离远的节点，反过来不行

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

直接递归即可，每往抵达一个节点，记录数=pre* 10 + node.val，然后再把记录数往下一个节点传递

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


从起始点开始，以深度优先的形式，克隆每个节点，
遍历该节点的每一个相邻节点，若相邻节点对应新节点存在，则直接将其与该节点连接起来，否则，递归克隆该相邻节点
很关键的一点是要用一个map保存新节点和旧节点的对应关系


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

初始化给没人一颗
循环，设置一个布尔位，当此次循环数组中没发生任何变化就不再循环
    循环遍历数组的每一个位置，
        当该位置的分数高于左边的分，l= max(左边的+1，该位置原本的糖果)
        当该位置的分数高于右边的分，r= max(右边的+1，该位置原本的糖果)
        该位置上的糖果 = max(l,r),当该位置原本的糖果与之前不一样多时，布尔位标注为变化发生

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
例如，[5，5，5，1]  5的二进制：101  每一个数字转成二进制后，叠加逐个位，但叠加的和是以10进制的形式的，如 3个5逐位叠加后是 303， 1的二进制是001，加上后是304  3%3=0，0%3=0，4%3=1  故唯一的那个数的二进制表示是001

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

利用数组很方便，先把链表节点顺序放进数组，一个指针p1从前往后走，一个指针p2从后往前走，让两个操作：（p1.next=p2, p1++）和（p2.next=p1,p2--）交替进行
直到p1大于p2为止
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
## 需要记住，前序只有一个循环，里面分别是访问值，加右孩子，加左孩子
## 中序也只有一个循环，里面有if-else判断node是否为空，不空就继续挪到左孩子，空就访问栈中值，然后挪到右孩子
## 后序是一个大循环里面有个小循环+if-else判断，有一个pre节点保存上一个节点。用tmp节点保存当前栈顶节点的右孩子
## 层序的话，如果只需要一个序列，则用一个队列，如果需要表明层级，则需要两个队列

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

插入排序就必须每遍历到一个数时，就从头遍历到当前（在前面已排序列中找到其合适的位置），要注意排完序后要把最后一个节点的next置null
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
最后返回low即为最小值的索引


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

用线性时间意味着不能排序，线性空间意味着能存储全部元素

假设数组中有n个元素，最小和最大的数分别是min和max，那么相邻两个数之间的最大的跨度不会小于 tGap = ceil{(max-min)/(n-1)} （ceil是向上取整）
这个很好理解，因为如果n个数是等差的话，那两个相邻数的差就是tGap，不是等差的话，则一定会有gap比tGap等或大（当然也会有gap比tGap小）
我们可以把这n个数安排到n-1个桶中，每个桶的范围为tGap，第k个桶包含数的范围是[min+（k-1） * tGap,  min+ k * tGap )  (k从1开始)

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
》方法一：
直接从前往后比较即可，把每个版本号按“.”分割成数组，以其中短的长度为基准从前往后（从大版本到小版本）比较。如果这些都相同，则再将长的版本号剩下的部分提取出来
如果这些是0，则两版本一样大，若不是0，则是长版本的大

》方法二：
以长的为主导，从前往后比较，每次比较当前位对应的数字，如果短的不够了，直接补上0，如果在当前位能分出大小，则直接返回，当前位相等则继续比较下一位，全部比较完还没分出大小则二者相等

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


    //方法二：
    public int compareVersion(String version1, String version2) {
        String[] array1 = version1.split("\\.");
        String[] array2 = version2.split("\\.");
        int maxLen = Math.max(array1.length, array2.length);
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        for(int i=0;i<maxLen;i++){
            StringBuilder s1 = new StringBuilder();
            StringBuilder s2 = new StringBuilder();
            if(array1.length>i){
                s1.append(Integer.valueOf(array1[i])); //消除前导零
            }else {
                s1.append('0');
            }
            if(array2.length>i){
                s2.append(Integer.valueOf(array2[i]));
            }else {
                s2.append('0');
            }
            int v1 = Integer.valueOf(s1.toString());
            int v2 = Integer.valueOf(s2.toString());
            if(v1==v2)
                continue;
            else {
                return Integer.compare(v1,v2);
            }


        }
        return 0;

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

遍历数组，每遍历到一个数字cur时，用二分搜索去查找与其和为目标值的另一个数
如果当前遍历到的数cur的索引和查找后的索引相同，则看cur后面一个数是不是与cur相等，等的话按序将cur的索引，及cur的索引+1放入答案集，这一步很重要，主要是解决某个数重复出现的情况

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

》方法一
可以一开始就把中序遍历保存起来，也能AC

》方法二：
使用栈，构建迭代器时，就把树的从根一直靠左入栈直到最小节点进去
next则是一个中序遍历的部分，先获得栈顶元素，输出该元素前，处理其右子树，对右子树顺着左边依次入栈，直到一个左节点不为空
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
hasNext则是判断栈是否为空


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

思路：在走完最后一个房间的时候血量至少要剩下１，因此最后的状态可以当成是初始状态，由后往前依次决定在每一个位置至少要有多少血量, 这样一个位置的状态是由其下面一个和和右边一个的较小状态决定 ．因此一个基本的状态方程是: dp[i][j] = min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j]. 
（因为只需保证一条路能走通即可，所以取min）
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

使用双指针法，即窗口法，窗口中值小于s时，窗口往后延伸，大于等于s时，窗口往前缩小，每当遇到一个和满足的子数组，就和最小长度对比，若更小就更新最小长度

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

对于House Robber来说，与该题的唯一区别是 头尾不相邻
由于第一个数和最后一个数是相邻的，所以它们不可能同时出现

现在的问题是如何抢劫一排圆形房屋。像更简单的问题一样解决起来有点复杂。这是因为在更简单（198）的问题中，是否抢劫num [lo]完全是我们的选择。但是，它现在受到num [hi]是否被抢夺的限制。

但是，因为我们已经有一个很好的解决方案来解决这个问题。我们不想扔掉它。然后，它变成了如何将这个问题简化为更简单的问题。实际上，从逻辑上延伸，如果房子i没有被抢劫，那么你可以自由选择是否抢劫房子i + 1，你可以通过假设房子i没有被抢劫来打破这个圈子。

例如，如果1未被抢夺，则1  - > 2  - > 3  - > 1变为2  - > 3。

由于每个房子都被抢劫或者没有抢劫，并且至少有一半的房屋没有被抢劫，解决方案只是连续房屋的两个案例中的较大者，即房屋i没有抢劫，打破圈子，解决它，或房子i + 1没有抢劫。因此，以下解决方案。为了简化编码，我选择了i = n和i + 1 = 0。但是，您可以选择连续两个。

即对 num[0...len-2] 和 num[1...len-1] 用上面198的算法，取结果中的最大值


198的算法：
    遍历数组
    对于遍历到的下标为i的数x来说，include表示截止x，包含x的最大值，exclude表示不包含x的最大值
    则对下一个数字y（下标为i+1）来说，include（截止到y，包含y的最大值）=exclude+num[i+1]  (截止到x不包含x的最大值+y)
                  exclude（截止到y，不包含y的最大值）=Math.max ( 前一个include(截止到x包含x的最大值) ,前一个exclude(截止到x不包含x的最大值)

```java
class Solution {
    public int rob(int[] nums) {
        if(nums==null || nums.length==0) return 0;
        if(nums.length==1) return nums[0];
        return Math.max(rob(nums, 0, nums.length-2), rob(nums,1,nums.length-1));

    }

    public int rob(int[] nums, int low, int high){
        if(nums==null || nums.length==0 || low>high) return 0;
        int include = 0;
        int exclude = 0;
        for(int j=low;j<=high;j++){
            int i=include, e = exclude;
            include = e + nums[j];
            exclude = Math.max(e, i);
        }
        return Math.max(exclude, include);
    }
}
```

214. Shortest Palindrome
Hard

Given a string s, you are allowed to convert it to a palindrome by adding characters in front of it. Find and return the shortest palindrome you can find by performing this transformation.

Example 1:

Input: "aacecaaa"
Output: "aaacecaaa"
Example 2:

Input: "abcd"
Output: "dcbabcd"

给定一个字符串s，您可以通过在其前面添加字符将其转换为回文。通过执行此转换找到并返回您能找到的最短回文。

循环i从0到len-1，把s从后往前依次去掉i个字符看是否是回文，若是，i就是最少要添加的字符

```java
class Solution {

    public String shortestPalindrome(String s) {
        if(s==null || s.length()==1 || s.length()==0) return s;
        int len =s.length();
        int minAdd = len-1;
        //把s从后往前依次去掉i个字符看是否是回文，若是，则i就是最少要添加的字符
        for(int i=0;i<len-1;i++){
            String tmp = s.substring(0, len-i);
            if(isPalindrome(tmp)){
                minAdd = i;
                break;
            }
        }
        //把需要插入的字符插入新字符串中，要插入的字符从s中取出后要翻转后再插入
        String toAdd = new StringBuilder(s.substring(len-minAdd, len)).reverse().toString();
        return toAdd + s;
    }

    //判断是否是回文
    private boolean isPalindrome(String s){
        StringBuilder builder = new StringBuilder(s);
        return s.equals(builder.reverse().toString());
    }
}
```

216. Combination Sum III
Medium

Find all possible combinations of k numbers that add up to a number n, given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.

Note:

All numbers will be positive integers.
The solution set must not contain duplicate combinations.
Example 1:

Input: k = 3, n = 7
Output: [[1,2,4]]
Example 2:

Input: k = 3, n = 9
Output: [[1,2,6], [1,3,5], [2,3,4]]

找出k个数字的所有可能组合，它们加起来等于一个数字n，假设只能使用1到9之间的数字，并且每个组合应该是一组唯一的数字。
    k<9,所以用很简单的带start的trackback，然后找到符合条件的组合即可

```java
class Solution {
    List<List<Integer>> ans;
    public List<List<Integer>> combinationSum3(int k, int n) {
        ans = new ArrayList<>();
        trackBack(1, k, n, new ArrayList<>());
        return ans;
    }

    void trackBack(int start, int k, int rest, List<Integer> list){
        if(rest==0 && list.size()==k){
            ans.add(new ArrayList<>(list));
            return;
        }
        if(list.size()>=k && rest>0) return;
        if(start>rest) return;

        for(int i=start; i<=9;i++){
            if(i>rest) break;
            list.add(i);
            trackBack(i+1, k, rest-i, list);
            list.remove(new Integer(i));
        }
    }
}
```

219. Contains Duplicate II
Easy

Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array such that nums[i] = nums[j] and the absolute difference between i and j is at most k.

Example 1:

Input: nums = [1,2,3,1], k = 3
Output: true
Example 2:

Input: nums = [1,0,1,1], k = 1
Output: true
Example 3:

Input: nums = [1,2,3,1,2,3], k = 2
Output: false

给定一个整数数组和一个整数k，找出数组中是否有两个不同的索引i和j，使得nums[i] = nums[j]，并且i和j之间的绝对差不超过k。

    使用HashMap<Integer, List>的形式，key是数组中出现过的数字，list存该数字的所有索引，当遍历到一个数时，取出该数对应的上一个索引，
    看差值是否满足，如果上一个都不满足，那么前面的就没必要再比了

```java
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(!map.containsKey(nums[i])){//之前没有出现过该数字
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(nums[i], list);
            }else{
                List list = map.get(nums[i]);
                int offset = i - (Integer) list.get(list.size()-1);
                if(offset<=k) return true;
                list.add(i);
            }
        }
        return false;
    }
}
```

220. Contains Duplicate III
Medium

Given an array of integers, find out whether there are two distinct indices i and j in the array such that the absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.

Example 1:

Input: nums = [1,2,3,1], k = 3, t = 0
Output: true
Example 2:

Input: nums = [1,0,1,1], k = 1, t = 2
Output: true
Example 3:

Input: nums = [1,5,9,1,5,9], k = 2, t = 3
Output: false

给定一个整数数组，找出数组中是否有两个不同的索引i和j，使得nums[i]和nums[j]之间的绝对值差最大为t, i和j之间的绝对值差最大为k。

//笨办法就是双重循环，如果下标绝对值符合后再看数值是否符合，会超时

网上找了没有一个中文解释能把这题说明白的。

使用TreeSet数据结构，可以借助TreeSet中有用的函数

TreeSet.floor(x) 表示TreeSet中小于或等于x的最大元素
TreeSet.ceiling(x) 表示TreeSet中大于或等于x的最小元素

对于数组中任意一个数nums[i], 与其绝对值差小于等于为t的区间为 [nums[i]-t，nums[i]+t],  数轴表示如下

        I__________I__________I
    nums[i]-t   nums[i]      nums[i]+t

使用TreeSet的floor和ceiling函数，可以得到是否有数字在上述区间内
即：f = floor(nums[i] + t) 且 f>=nums[i]，说明f在[ nums[i], nums[i]+t]内
   c = ceiling(nums[i] - t) 且 c<=nums[i]，说明c在[ nums[i]-t, nums[i]]内

如果存在f或者c，然后要保证的是 它们的索引和i差距小于k，则只需维持TreeSet中始终只保留窗口大小为k的元素即可（在访问nums[i]时，TreeSet中始终只保留nums[i-k]到nums[i-1] ），int±t可能会溢出，所以TreeSet用Long保存

这样，只要存在f或者c，就存在满足条件的值，因为TreeSet中元素的坐标都满足和i的约束关系
若不存在，则把nums[i]加入TreeSet，删去nums[i-k]（因为下一个要访问的是nums[i+1]，nums[i-k]与nums[i+1]的坐标约束不满足）

核心在于，本来要考虑i和j两个随机索引，现在只用考虑一个索引i和其前面的索引了，而其前面的索引由于TreeSet的关系，不用复杂的遍历

不用担心有两个相同的数字会删去，当相同的数字会奏效时，已经满足条件了，就不会走后面的删除的逻辑了


```java
public class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return false;
        }
        //由于nums±t可能出现溢出的情况，所以全变成long来进行运算
        final TreeSet<Long> values = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
        
            final Long floor = values.floor((long) nums[i] + t); //set中是否有满足 小于或等于 nums[i]+t 的数字
            final Long ceil = values.ceiling((long) nums[i] - t); //set中是否有满足 大于或等于 nums[i]-t 的数字

            //如果有这样的数字，且TreeSet中元素坐标都与i满足约束条件，那么就有满足题意的元素

            if ((floor != null && floor >= nums[i])
                    || (ceil != null && ceil <= nums[i])) {
                return true;
            }

            values.add((long) nums[i]);
            //保证TreeSet中的坐标和i的差都小于等于k
            if (i >= k) {
                values.remove((long)nums[i - k]);
            }
        }

        return false;
    }
}
```


222. Count Complete Tree Nodes
Medium

Given a complete binary tree, count the number of nodes.

Note:

Definition of a complete binary tree from Wikipedia:
In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.

Example:

Input: 
    1
   / \
  2   3
 / \  /
4  5 6

Output: 6


给定一个完全二叉树，计算节点数。

维基百科中完整二叉树的定义：
在完全二叉树中，除了可能的最后一个level之外，每个level都被完全填充，并且最后一级中的所有节点都尽可能地靠左。 它可以具有1到2h节点，包括最后一级h。

直接层序遍历当然可以，复杂度是O(n), 而且只需要一个队列即可，但这样就没有用到“完全二叉树”这个条件

只需向左走即可找到树的高度。 让单个节点树的高度为1.找到整个树的高度h。 如果整个树是空的，即高度为0。

然后检查右子树的高度是否只比整个树的高度小1，这意味着左右子树具有相同的高度。

如果是，则最后一个树行上的最后一个节点在右子树中，而左子树是一个高度为h-1的完整树。 
    因此，该树的总结点数 = 左子树的2 ^ (h-1)-1个节点加上1个根节点加上递归的右子树中的节点数。
如果否，则最后一个树行上的最后一个节点在左子树中，右子树是一个高度为h-2的完整树。 
    因此，该树的总结点数 = 右子树的2 ^（h-2）-1个节点加上1个根节点加上递归的左子树中的节点数。
由于我在每个递归步骤中将树减半，因此我有O（log（n））步。 找高度成本为O（log（n））。 所以整体O（log（n）^ 2）。

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
    int getHeight(TreeNode root){
        return root==null?0:1+getHeight(root.left);
    }

    public int countNodes(TreeNode root){
        int h = getHeight(root);
        if(h==0) return 0;
        int t = getHeight(root.right)==h-1? ((1<<(h-1)))+countNodes(root.right)
                : ((1<<(h-2)))+countNodes(root.left);
        return t;
    }
}
```

223. Rectangle Area
Medium

Find the total area covered by two rectilinear rectangles in a 2D plane.

Each rectangle is defined by its bottom left corner and top right corner as shown in the figure.

Rectangle Area

Example:

Input: A = -3, B = 0, C = 3, D = 4, E = 0, F = -1, G = 9, H = 2
Output: 45
Note:

Assume that the total area is never beyond the maximum possible value of int.


求二维平面上两个矩形所覆盖的总面积。

每个矩形由其左下角和右上角定义

假设总面积永远不会超过int的最大值。

两个矩形左边界的大值和右边界的小值构成重合区域的左右边界，上边界的小值和下边界的大值构成上下边界
这样可以得到两个矩形重合的面积，总面积=两个矩形面积-重合面积

```java
class Solution {
    public int computeArea(int left1, int bottom1, int right1, int top1, int left2, int bottom2, int right2, int top2) {

        int left = Math.max(left1, left2);
        int right = Math.min(right1, right2);
        int bottom = Math.max(bottom1, bottom2);
        int top = Math.min(top1, top2);
        int rect1 = (right1-left1) * (top1-bottom1);
        int rect2 = (right2-left2) * (top2-bottom2);

        /*如果没重合面积,要么根据竖直方向，要么根据横向
            可以在坐标轴上画出相应区域比较好理解
        */
        if(top1<bottom2 || top2<bottom1 || left1>right2 || left2>right1) {
            return rect1 + rect2;
        }else
            return rect1+rect2-(right-left) * (top-bottom);
    }
}
```

225. Implement Stack using Queues
Easy

Implement the following operations of a stack using queues.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
empty() -- Return whether the stack is empty.
Example:

MyStack stack = new MyStack();

stack.push(1);
stack.push(2);  
stack.top();   // returns 2
stack.pop();   // returns 2
stack.empty(); // returns false
Notes:

You must use only standard operations of a queue -- which means only push to back, peek/pop from front, size, and is empty operations are valid.
Depending on your language, queue may not be supported natively. You may simulate a queue by using a list or deque (double-ended queue), as long as you use only standard operations of a queue.
You may assume that all operations are valid (for example, no pop or top operations will be called on an empty stack).

用队列实现栈

```java
class MyStack {
        LinkedList<Integer> q1;
        LinkedList<Integer> q2;

        /** Initialize your data structure here. */
        public MyStack() {
            q1 = new LinkedList<>();
            q2 = new LinkedList<>();
        }

        /** Push element x onto stack. */
        public void push(int x) {
            q1.offer(x);
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            int ans = 0;
            while(!q1.isEmpty()){
                int x = q1.pop();
                if(!q1.isEmpty()){
                    q2.offer(x);
                }else{
                    ans = x;
                }
            }

            //之后吧元素还回q1
            while(!q2.isEmpty()){
                int x = q2.pop();
                q1.offer(x);
            }
            return ans;
        }

        /** Get the top element. */
        public int top() {
            int ans = 0;
            while(!q1.isEmpty()){
                int x = q1.pop();
                if(!q1.isEmpty()){
                    q2.offer(x);
                }else{
                    q2.offer(x);
                    ans = x;
                }
            }

            //之后吧元素还回q1
            while(!q2.isEmpty()){
                int x = q2.pop();
                q1.offer(x);
            }
            return ans;
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return q1.isEmpty();
        }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
```


228. Summary Ranges
Medium

Given a sorted integer array without duplicates, return the summary of its ranges.

Example 1:

Input:  [0,1,2,4,5,7]
Output: ["0->2","4->5","7"]
Explanation: 0,1,2 form a continuous range; 4,5 form a continuous range.
Example 2:

Input:  [0,2,3,4,6,8,9]
Output: ["0","2->4","6","8->9"]
Explanation: 2,3,4 form a continuous range; 8,9 form a continuous range.

给定一个没有重复的排序整数数组，返回其范围的摘要。
定义一个表示当前范围的int[2]，[0]表示下界，[1]表示上界
遍历数组，
    如果该数=当前上界+1，则更新上界为该数，继续
    如果不是，则将当前上下界加入答案集中（上下界相同时，不用->；不同时，用->），然后把上下界都更新为该数，继续


```java
class Solution {

    public List<String> summaryRanges(int[] nums) {
        ArrayList<String> list = new ArrayList<>();
        if(nums==null || nums.length==0) return list;
        else if(nums.length==1) {
            list.add(String .valueOf(nums[0]));
            return  list;
        }
        int[] range = new int[2];
        range[0] = nums[0];
        range[1] = nums[0];
        for(int i=1;i<nums.length;i++){
            //该数仍可连在当前区间内
            if(nums[i]==range[1]+1){
                range[1]=nums[i];
                
            }else{//该数连不起来了
                if(range[0]==range[1]){
                    list.add(String .valueOf(range[0]));
                }else{
                    list.add(String .valueOf(range[0]) + "->" + String .valueOf(range[1]));
                }
                range[0] = nums[i];
                range[1] = nums[i];
            }
        }
        if(range[0]==range[1]){
            list.add(String .valueOf(range[0]));
        }else{
            list.add(String .valueOf(range[0]) + "->" + String .valueOf(range[1]));
        }
        return list;
    }
}
```

229. Majority Element II
Medium

Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.

Note: The algorithm should run in linear time and in O(1) space.

Example 1:

Input: [3,2,3]
Output: [3]
Example 2:

Input: [1,1,1,3,3,2,2,2]
Output: [1,2]


给定一个整数数组的大小为n,找到所有出现n / 3次以上的元素。要使用O(n)的时间和O(1)的空间复杂度

不能排序，不能另存元素。可以证明，出现次数超过n/3的元素至多只有2个，(反证：假设有3个，则他们3个的总数就超过n了。)

如果查找其中个数超过一半的某元素，可以直接用简单的摩尔投票法，如下
```java
    for (int num : nums) {
        if (count == 0) {
            majority = num;
            count++;
        } else {
            if (majority == num) {
                count++;
            } else {
                count--;
            }
        }
    }
```
当要找超过三分之一的元素时，我们使用投票法的核心是找出两个候选众数进行投票，需要两遍遍历，第一遍历找出两个候选众数，第二遍遍历重新投票验证这两个候选众数是否为众数即可，选候选众数方法和前面那篇Majority Element求众数一样，由于之前那题题目中限定了一定会有众数存在，故而省略了验证候选众数的步骤，这道题却没有这种限定，即满足要求的众数可能不存在，所以要有验证。

```java
class Solution{
    public List<Integer> majorityElement(int[] nums) {
        if (nums == null || nums.length == 0)
            return new ArrayList<Integer>();
        List<Integer> result = new ArrayList<Integer>();
        int number1 = nums[0], number2 = nums[0], count1 = 0, count2 = 0, len = nums.length;

        //用if-else确保不会对同一个数计算重复
        //注意的是，若某数出现了n/3次以上，经过遍历后一定在number1或number2中；但反过来number1和number2不一定是出现次数超过三分之一（此时根本就没有出现超过三分之一的数字）
        for(int i=0;i<len;i++){
            if(nums[i]==number1){
                count1++;
            }else if(nums[i]==number2){
                count2++;
            }else if(count1==0){
                number1 = nums[i];
                count1=1;
            }else if(count2==0){
                number2 = nums[i];
                count2=1;                
            }else{
                count1--;
                count2--;
            }
        }
        //验证number1和number2是否真的出现了n/3次以上,用if-else，就算两者相同，最后也只会计算一个
        count1=0;count2=0;
        for(int i =0;i<len;i++){
            if(nums[i]==number1) count1++;
            else if(nums[i]==number2) count2++;
        }
        if(count1>len/3) result.add(number1);
        if(count2>len/3) result.add(number2);
        return result;
    }
}
```

231. Power of Two
Easy

Given an integer, write a function to determine if it is a power of two.

Example 1:

Input: 1
Output: true 
Explanation: 20 = 1
Example 2:

Input: 16
Output: true
Explanation: 24 = 16
Example 3:

Input: 218
Output: false

给定一个数，判断它是否是2的次方

2的次方有且只有1个1，对于某个数x，x-1后二进制会使最右边的1变成0，再往右的0全变成1，与x做与运算，结果是x二进制中最右边的1变成0，其余位均不变
如：10100(2)， 减一后变成 10011(2)，做与后变成 10000
因为2的次方有且只有1个1，所以这么做完后，如果变成0，说明里面只有一个1；即是2的次方数，否则不是
要注意的是非正数一定不是次方数

```java
class Solution {
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) return false;
        return ((n - 1) & (n)) == 0;
    }
}
```

233. Number of Digit One
Hard

Given an integer n, count the total number of digit 1 appearing in all non-negative integers less than or equal to n.

Example:

Input: 13
Output: 6 
Explanation: Digit 1 occurred in the following numbers: 1, 10, 11, 12, 13.

0, 1, 2, 3 ... 9 (1)

10, 11, 12, 13 ... 19 (1) + 10

20, 21, 22, 23 ... 29 (1)

...

90, 91, 92, 93 ... 99 (1)

100, 101, 102, 103 ... 109 (10 + 1)

110, 111, 112, 113 ... 119 (10 + 1) + 10

120, 121, 122, 123 ... 129 (10 + 1)

...

190, 191, 192, 193 ... 199 (10 + 1)

给定一个整数n，计算所有小于或等于n的非负整数中出现的数字1的个数。

 1)如果我们不看那些特殊的行（以10、110等开头），我们知道每10个数字中有一个1在个位，每100个数字中有10个1在十位，每1000个数字中有100个1在百位，以次类推
 让我们从个位开始并计算有多少个1在这个位置，设k=1，根据上面所说，每10个数字1会出现1次在十位上，所以我们有多少10个数字？
答案为 (n/k)/10
现在计算十位，设k=10，每100个数字中有10个1在十位，所以我们有多少100个数字
答案是 (n/k)/10，所以在十位上的1的个数为 (n/k)/10* k
设 r=n/k 现在我们有了公式计算k位上的1的个数： r/10* k

2).我们现在解决特殊行
使用模运算，举10，11，12为例，假如n是10，则个位中1的个数是 (n/1)/10* 1=1,正确，但计算十位上时，(n/10)/10 * 10=0,错误，因为有1个1在十位上。
从10到19，我们总是有1在十位上，设 m=n%k,在这个特殊位置上的1的个数是m+1，显然特殊行都是1打头的行
即：  r/10 * k+(r%10==1?m+1:0)  (r%10==1代表的是若n的最高位就是第k位，且这个最高位是1，那么要额外计算以1开头的这些数，例如，对180，180%100=80,且(180/100)%10==1,因此180中以1开头的3位数共80个，而前面r/10 = 180/100/10 = 0, 因为不够1000个数字)

（如何判断是不是特殊行，有多少个特殊行？？？）

3）如果对于20，21，22呢？
对于20，使用上述公式会得到十位上的1的个数是0，但实际上是10（10到19）,如何修正？我们知道一旦数字大于2，我们应该在十位上的1的个数中加上10，一个聪明的办法是在r上加上8，公式如下：
(r+8)/10* k + (r%10==1?m+1:0)

加入对于225来说

个位上1的个数：k=1时，r=225  (225+8)/10=23, 
十位上1的个数为 k=10时，r=22   (22+8)/10 * 10=30：21X ，11X，1X
百位上1的个数为 k=100时，r=2， （2+8）/10 * 100 = 100   ：1XX
共153

```java
class Solution {
    public int countDigitOne(int n) {
        long k=1;
        long ans = 0;
        while(n>=k){
            long r = n/k;
            long m = n%k;
            ans += (r+8)/10*k + (r%10==1?m+1:0);
            k*=10;
        }
        return (int)ans;

    }
}
```

235. Lowest Common Ancestor of a Binary Search Tree
Easy

Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Given binary search tree:  root = [6,2,8,0,4,7,9,null,null,3,5]



Example 1:

Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
Explanation: The LCA of nodes 2 and 8 is 6.
Example 2:

Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
Output: 2
Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
 

Note:

All of the nodes' values will be unique.
p and q are different and both values will exist in the BST.

给定二叉搜索树(BST)，查找BST中两个给定节点的最低公共祖先(LCA)。

根据Wikipedia对LCA的定义:“在两个节点p和q之间定义的最低公共祖先是T中同时具有p和q作为后代的最低节点(我们允许一个节点作为其自身的后代)。”

如果是普通二叉树的两个节点，则

给定一棵树，两个节点，找到这两个节点的最低公共祖先节点（LCA），需要注意的是，一个节点算是它自己的祖先

维护一个本节点与其祖先的HashMap<TreeNode, TreeNode> map，k是本节点，v是其祖先节点，先用层序遍历把这个map给做好
过程中把q和p的层高找到,先让低的追溯祖先让二者层高相同，然后两个一起追溯，直到节点相同，相同的就是公共祖先

时间复杂度O(n),空间复杂度O(n)

而对于二叉搜索树，就变得更简单了，因为是二叉搜索树，很方便能找到对应节点直接用队列qa记录根到节点A的节点链，qb记录根到节点B的节点链，然后出队到两个队头节点不同时，上一个出队的节点就是最低公共祖先
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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        LinkedList<TreeNode> q1 = new LinkedList<>();
        LinkedList<TreeNode> q2 = new LinkedList<>();
        if(root==null || p==null || q==null) return null;

        getLinkedListFromNodePath(q1, root, p);
        getLinkedListFromNodePath(q2, root, q);

        TreeNode lca = root;
        while(!q1.isEmpty() && !q2.isEmpty()){
            TreeNode p1 = q1.poll();
            TreeNode p2 = q2.poll();
            if(p1==p2){
                lca = p1;
            }else{
                break;
            }
        }
        return lca;   

    }

    public void getLinkedListFromNodePath(LinkedList<TreeNode> list, TreeNode root, TreeNode target){
        list.offer(root);
        if(root==target){
            return;
        }else if(root.val<target.val){
            getLinkedListFromNodePath(list, root.right, target);
        }else{
            getLinkedListFromNodePath(list, root.left, target);
        }
    }
}
```


241. Different Ways to Add Parentheses
Medium

Given a string of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators. The valid operators are +, - and *.

Example 1:

Input: "2-1-1"
Output: [0, 2]
Explanation: 
((2-1)-1) = 0 
(2-(1-1)) = 2
Example 2:

Input: "2*3-4*5"
Output: [-34, -14, -10, -10, 10]
Explanation: 
(2*(3-(4*5))) = -34 
((2*3)-(4*5)) = -14 
((2*(3-4))*5) = -10 
(2*((3-4)*5)) = -10 
(((2*3)-4)*5) = 10


给定一串数字和运算符，返回所有可能的结果，这些结果来自计算所有可能的方法来分组数字和运算符。有效的操作符是+、-和 * 。

非常典型的大问题由相同类型的小问题组成，典型的分治法
用分治法的思想来解决
当遍历到一个符号时，递归找到该符号两边能成为的数字，再将它们组合
当输入的字符串中没有符号时，则它是一个数字，直接加入到答案集中返回即可



```java
class Solution {
    public List<Integer> diffWaysToCompute(String input) {
        ArrayList<Integer> ans = new ArrayList<>();
        int operatorCnt = 0;
        for(int i=0;i<input.length();i++){
            char c = input.charAt(i);
            if(c=='-' || c=='+' || c=='*'){
                operatorCnt++;
                List<Integer> part1 = diffWaysToCompute(input.substring(0, i));
                List<Integer> part2 = diffWaysToCompute(input.substring(i+1, input.length()));
                for(int i1: part1){
                    for(int i2: part2){
                        if(c=='-'){
                            ans.add(i1-i2);
                        }else if(c=='+'){
                            ans.add(i1+i2);
                        }else{
                            ans.add(i1*i2);
                        }
                    }
                }
            }
        }
        //没有符号，说明是一个数字
        if(operatorCnt==0){
            ans.add(Integer.valueOf(input));
        }
        return ans;
    }
}
```

257. Binary Tree Paths
Easy

Given a binary tree, return all root-to-leaf paths.

Note: A leaf is a node with no children.

Example:

Input:

   1
 /   \
2     3
 \
  5

Output: ["1->2->5", "1->3"]

Explanation: All root-to-leaf paths are: 1->2->5, 1->3

记录根到叶子的所有路径
利用递归，每遍历到一个节点，则把它和从上级传下来的路径字符串组合成一个新的字符串作为本级路径串，当该节点为叶子时，把本级路径串加入答案集
如果不是叶子，则递归遍历该节点的左右孩子，并把本级路径串作为参数传递下去

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

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> ans = new ArrayList<>();
        if(root==null) return ans;
        recordPath(ans, root, "");
        return ans;
    }

    public void recordPath(List<String> ans, TreeNode cur, String pathString){

        String s = pathString;
        if(pathString.length()==0){
            s += cur.val;
        }else{
            s += "->"+ cur.val;
        }

        if(cur.left==null&&cur.right==null){
            ans.add(s);
            return;
        }

        if(cur.left!=null)recordPath(ans, cur.left, s);
        if(cur.right!=null)recordPath(ans, cur.right, s);
    }
}
```

258. Add Digits
Easy

Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

Example:

Input: 38
Output: 2 
Explanation: The process is like: 3 + 8 = 11, 1 + 1 = 2. 
             Since 2 has only one digit, return it.

给定一个非负整数，重复添加它的所有位上的数字，直到结果只有一个数字。
用双层循环，逐个把它们的各位加到一个整数，然后再把该整数转成字符串继续该操作，直到该字符串长度只有1，时间复杂度O(n^2)

该问题其实是数字根问题：

数字根就是这样一种数字：重复添加它的所有位上的数字，直到结果只有一个数字。
对于以b为基底(十进制情况b = 10)的整数n，其数字根为:

    dr(n) = 0 若n=0 （如果是0，则数字根为0）
    dr(n) = (b-1)如果n != 0且 n % (b-1) == 0（如果是9的倍数，则数字根为9）
    dr(n) = n mod (b-1)如果n % (b-1) != 0 （如果不是9的倍数，则数字根为该数除以9的余数）
或

（上述三个公式综合为1个）dr(n) = 1 + (n - 1) % 9

注意，当n = 0时，因为(n -1) % 9 = -1，所以返回值为0(正确)。

由公式可知，该问题的结果具有内在的周期性，周期为(b-1)。

小数输出顺序(b = 10):

~输入:0 1 2 3 4…
输出:0 1 2 3 4 5 6 6 7 8 9 1 2 3 4 5 6 8 9 1 2 3

因此，我们可以编写以下代码，其时间和空间复杂度都是O(1)。

    int addDigits(int num) {
        return 1 + (num - 1) % 9;
    }


性质说明

任何数加9的数字根还是它本身
小学学加法的时候我们都明白，一个数字加9，就是把十位加1，个位减1。因此十位加个位的和是不变的；如果有进位，即十位上是9，那么进位之后十位会变成0，百位会加1，道理和一个一位数加9是一样的。

9乘任何数字的数字根都是9
同样是小学时学乘法时，我们在计算一位数乘九的时候，把十只手指头排开，乘几便弯下第几只手指头，前后的手指个数便是那个结果。它的数字根永远是10-1=9。多位数的话，拆分每一位数字即可。

数字根的三则运算
（1）       两数之和的数字根等于这两个数的数字根的和的数字根

对于两个一位数来说，很容易理解。因为一位数的数字根就是它本身。对于多位数来说，由性质1，把每个数字mod 9，就又变成了两个一位数。

（2）       两数之积的数字根等于这两个数的数字根的积的数字根

可以把每个数字拆成许多9相加的形式，最后各剩余一个 (a mod 9)， 由

(a1+a2+...)*(b1+b2+...)=a1*(b1+b2+...)+a2*(b1+b2+...)+...+an*bm

从a1到a[n-1]都是9，由性质2，原来两式的数字根就是（an*bm）的数字根。而由性质1，可知an,bm又是两数本身的数字根。

（3）       一个数字的n次幂的数字根等于这个数字的数字根的n次幂的和数字根

（4）       a的数根b = ( a - 1) % 9 + 1: 考虑到9的数根



```java
class Solution {
    //时间复杂度为O(n^2)
    public int addDigits(int num) {
        String s = String.valueOf(num);
        int ans = 0;
        while(s.lenght()>1){
            ans = 0;
            for(int i=0;i<s.length();i++){
                ans += Integer.valueOf(s.substring(i,i+1));
            }
            s = String.valueOf(ans);
        }
        return ans;
    }

    //时间复杂度O(1)
    public int addDigits(int num) {
        return 1+(num-1)%9;
    }
}
```

260. Single Number III
Medium

Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once.

Example:

Input:  [1,2,1,3,2,5]
Output: [3,5]
Note:

The order of the result is not important. So in the above example, [5, 3] is also correct.
Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?


给定一个数字数字数组，其中恰好有两个元素只出现一次，而所有其他元素正好出现两次。找到只出现一次的两个元素。

结果的顺序并不重要。所以在上面的例子中，[5,3]也是正确的。
您的算法应该在线性运行时复杂度中运行。你能只用恒定的空间复杂度来实现它吗?

方法一：
如果用sO(n)很简单，用hashmap保存每个值出现的次数，再遍历一遍找到出现次数为1的两个数即可

方法二：
    假设数组中两个不同的数字为 A 和 B；
    通过遍历整个数组并求整个数组所有数字之间的 XOR，根据 XOR 的特性可以得到最终的结果为 AXORB = A XOR B；

    通过某种特定的方式，我们可以通过 AXORB 得到在数字 A 和数字 B 的二进制下某一位不相同的位；因为A 和 B 是不相同的，所以他们的二进制数字有且至少有一位是不相同的。我们将这一位设置为 1，并将所有的其他位设置为 0，我们假设我们得到的这个数字为 bitFlag；
    （与上：与运算， XOR：异或运算）
    那么现在，我们很容易知道，数字 A 和 数字 B 中必然有一个数字与上 bitFlag 为 0；因为bitFlag 标志了数字 A 和数字 B 中的某一位不同，那么在数字 A 和 B 中的这一位必然是一个为 0，另一个为 1；而我们在 bitFlag 中将其他位都设置为 0，那么该位为 0 的数字与上 bitFlag 就等于 0，而该位为 1 的数字与上 bitFlag 就等于 bitFlag

    现在问题就简单了，我们只需要在循环一次数组，将与上 bitFlag 为 0 的数字全部彼此进行 XOR 运算，与上 bitFlag 不为 0 的数全部彼此进行 XOR 运算。那么最后我们得到的这两个数字就是 A 和 B。

    ##保留一个数字二进制位的最右边的1，其余位都设为0的简单方法： a & (~ (a - 1)));  //原理看不太懂

```java
class Solution {
    //sO(n)
    public int[] singleNumber(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i: nums){
            map.put(i, map.getOrDefault(i, 0)+1);
        }

        int[] ans = new int[2];
        int index = 0;
        for(int i: nums){
            if(map.get(i)==1)
                ans[index++]=i;
        }
        return ans;
    }

    //sO(1)
    public int[] singleNumber(int[] nums) {
        int AXORB = 0;
        for (int num : nums) {
            AXORB ^= num; 
        }
        // pick one bit as flag
        int bitFlag = (AXORB & (~ (AXORB - 1)));
        int[] res = new int[2];
        for (int num : nums) {
            if ((num & bitFlag) == 0) {
                res[0] ^= num;
            } else {
                res[1] ^= num;
            }
        }
        return res;
    }

}
```

263. Ugly Number
Easy

Write a program to check whether a given number is an ugly number.

Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.

Example 1:

Input: 6
Output: true
Explanation: 6 = 2 × 3
Example 2:

Input: 8
Output: true
Explanation: 8 = 2 × 2 × 2
Example 3:

Input: 14
Output: false 
Explanation: 14 is not ugly since it includes another prime factor 7.
Note:

1 is typically treated as an ugly number.
Input is within the 32-bit signed integer range: [−2^31,  2^31 − 1].

编写一个程序来检查给定的数字是否是一个丑数。

丑数是正数，它的质因数只有2,3,5。

1始终被视为丑数

用最简单的办法，逐个除以2、3、5，若最后结果是1，则是丑数，否则不是

```java
class Solution {
    public boolean isUgly(int num) {
        if(num==0) return false;
        while(num%2==0){
            num = num>>1;
        }

        while(num%3==0){
            num /= 3;
        }

        while(num%5==0){
            num/=5;
        }
        return num==1;
    }
}
```

264. Ugly Number II
Medium

Write a program to find the n-th ugly number.

Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. 

Example:

Input: n = 10
Output: 12
Explanation: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.

找到第n个丑数，丑数是正数，它的质因数只有2,3,5。1始终被视为丑数
如果直接遍历判断每个数是否是丑数会超时

假设我们现在已经有了一个丑数的有序数组，如果要找到下一个丑数，则可以将数组中的每一个数乘以2，并将其中第一个大于当前丑数的的结果记为M2，同样将当前有序数组每一个数都乘以3，第一个大于当前丑数的的结果记为M3，同样方式得到乘以5的第一个大于当前丑数的结果记为M5。可以下一个丑数必然是min(M2, M3, M5)。

```java
class Solution {
    public int nthUglyNumber(int n) {
        int[] ugly = new int[n];
        ugly[0]=1;
        for(int i=1;i<n;i++){
            int pre = ugly[i-1];
            int m2=0,m3=0,m5=0;
            for(int j=0;j<i;j++){
                if(ugly[j]*2>pre) {
                    m2 = ugly[j] * 2;
                    break;
                }
            }
            for(int j=0;j<i;j++){
                if(ugly[j]*3>pre) {
                    m3 = ugly[j] * 3;
                    break;
                }
            }
            for(int j=0;j<i;j++){
                if(ugly[j]*5>pre) {
                    m5 = ugly[j] * 5;
                    break;
                }
            }
            ugly[i] = Math.min(m2,m3);
            ugly[i] = Math.min(ugly[i],m5);
        }
        return ugly[n-1];
    }
}
```

273. Integer to English Words
Hard

Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 - 1.

Example 1:

Input: 123
Output: "One Hundred Twenty Three"
Example 2:

Input: 12345
Output: "Twelve Thousand Three Hundred Forty Five"
Example 3:

Input: 1234567
Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
Example 4:

Input: 1234567891
Output: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"

将非负整数转换为其英文单词表示形式。给定的输入保证小于2^31 - 1。

英文数字都是3个3个一组来看的

2^31-1: 2 147 483 647  ：最大的就是billion，  billion，million，thousand，把它们记为 “分位号”
把数字分成三个三个一组，例如对于abc，每个组的读法都是： a hundred (b==1 则连着后面的c一起念，b==0，则不念，b>1则念成相应的 xxx ty) c + 相应分位号
需要注意的有以下几点：
1.要是输入0直接返回Zero
2.在一组数字（3位）中，遇到0就直接看下一位
3.如果一组数字全是0，则该组不输出任何字符串


```java
class Solution {
class Solution {

    String[] split_symbol = {"", "Thousand ", "Million ", "Billion "};
    String hundred = "Hundred";
    String[] base = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    String[] decimal = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    String[] decades = {"","","Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

    public String numberToWords(int num) {
        if(num==0) return "Zero";
        StringBuilder ans = new StringBuilder();
        String ori = String.valueOf(num);
        int cnt = 0;

        ori = new StringBuilder(ori).reverse().toString();
        for(int i=0;i<ori.length();i=i+3){
            int subLen = 1;
            if(i+2<ori.length()) subLen=3;
            else if(i+1<ori.length()) subLen=2;

            String sub = ori.substring(i, i+subLen);
            StringBuilder cur = new StringBuilder();
            for(int j=sub.length()-1;j>=0;j--){
                int n = Integer.valueOf(sub.substring(j,j+1));
                if(n==0) continue;
                if(j==2){ //高位
                    cur.append(base[n]+" "+ hundred+" ");
                }else if(j==1){ //中间位
                    if(n==1){
                        int tmp = Integer.valueOf(sub.substring(j-1,j));
                        cur.append(decimal[tmp]+" ");
                        j--; //这里减1，即不会再进入j=0的情况了。
                    }else{
                        cur.append(decades[n]+" ");
                    }
                }else{ //低位
                    cur.append(base[n]+" ");
                }
            }
            if(cur.length()!=0)
                cur.append(split_symbol[cnt]);
            cnt++;
            ans.insert(0, cur);
        }
        ans.deleteCharAt(ans.length()-1);
        return ans.toString();
    }


}
```

274. H-Index
Medium

Given an array of citations (each citation is a non-negative integer) of a researcher, write a function to compute the researcher's h-index.

According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at least h citations each, and the other N − h papers have no more than h citations each."

Example:

Input: citations = [3,0,6,1,5]
Output: 3 
Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each of them had 
             received 3, 0, 6, 1, 5 citations respectively. 
             Since the researcher has 3 papers with at least 3 citations each and the remaining 
             two with no more than 3 citations each, her h-index is 3.
Note: If there are several possible values for h, the maximum one is taken as the h-index.

h索引
给定一个研究员的文献的被引用数组(每个数都是非负整数)，编写一个函数来计算研究员的h指数。

根据Wikipedia上h-index的定义：“如果科学家的N篇论文中的h篇每篇至少被h引用，而其他n － h篇每篇不超过h篇引用，则科学家对h索引进行索引。”

[3,0,6,1,5]表示研究人员总共有5篇论文，每篇都有
              分别收到了3、0、6、1、5个引用。
              由于研究人员有3篇论文，每篇论文至少被引3篇，其余
              两次引用均不超过3次，她的h指数为3。

总共只有n片论文，因此h最大也不能超过n，在数组中，有h个数不小于h，其余n-h个数不大于h
假设n是论文总数，如果我们有n + 1个存储桶，即从0到n，即count[n+1]
遍历数组，遇到一个数x时，若x小于n，count[x]++, 否则count[n]++，即大于n的数字，最多只能对h贡献n
然后从后往前遍历数组count,并且累加count[i]到total上， 当total大等于i时，说明比i大或等的数超过（或等于）了i个，这就是h索引。从后往前遍历的原因是我们要找最大的h索引

```java
class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] count = new int[n+1];
        for(int i=0;i<n;i++){
            int cur = citations[i];
            if(cur<n){
                count[cur]++;
            }else{
                count[n]++;
            }
        }

        int total = 0;
        int ans = 0;
        for(int i=n;i>=0;i--){
            total += count[i];
            if(total>=i){
                ans = i;
                break;
            }
        }
        return ans;
    }
}
```

284. Peeking Iterator
Medium

Given an Iterator class interface with methods: next() and hasNext(), design and implement a PeekingIterator that support the peek() operation -- it essentially peek() at the element that will be returned by the next call to next().

Example:

Assume that the iterator is initialized to the beginning of the list: [1,2,3].

Call next() gets you 1, the first element in the list.
Now you call peek() and it returns 2, the next element. Calling next() after that still return 2. 
You call next() the final time and it returns 3, the last element. 
Calling hasNext() after that should return false.
Follow up: How would you extend your design to be generic and work with all types, not just integer?

给定一个具有next()和hasNext()方法的迭代器类接口，设计并实现一个支持peek()操作的PeekingIterator——它本质上是对元素的peek()，该元素将在下一次调用next()时返回。

假设迭代器根据一个列表初始化:[1,2,3]。

调用next()将得到列表中的第一个元素1。
现在调用peek()，它返回2，即下一个元素。在此之后调用next()仍然返回2。
最后一次调用next()，它返回3，即最后一个元素。
在此之后调用hasNext()应该返回false。
后续工作:如何将您的设计扩展为通用的，并能处理所有类型，而不仅仅是整数?

先把迭代器所有元素存进一个队列，之后用队列的peek，poll，isEmpty来代替该数据结构的peek，next，hasNext即可


```java
    class PeekingIterator implements Iterator<Integer> {
        LinkedList<Integer> innerQueue;
        public PeekingIterator(Iterator<Integer> iterator) {
            // initialize any member here.
            innerQueue = new LinkedList<>();
            while (iterator.hasNext()){
                innerQueue.offer(iterator.next());
            }
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            return innerQueue.peek();
        }

        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            return innerQueue.poll();
        }

        @Override
        public boolean hasNext() {
            return !innerQueue.isEmpty();
        }
    }
```

290. Word Pattern
Easy

Given a pattern and a string str, find if str follows the same pattern.

Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.

Example 1:

Input: pattern = "abba", str = "dog cat cat dog"
Output: true
Example 2:

Input:pattern = "abba", str = "dog cat cat fish"
Output: false
Example 3:

Input: pattern = "aaaa", str = "dog cat cat dog"
Output: false
Example 4:

Input: pattern = "abba", str = "dog dog dog dog"
Output: false
Notes:
You may assume pattern contains only lowercase letters, and str contains lowercase letters that may be separated by a single space.

给定一个模式和一个字符串str，查找str是否遵循相同的模式。

这里的follow表示完全匹配，即模式中的字母和str中的非空单词之间存在双射。

用map保存模式和对应的词的关系即可,但还是要注意是双向匹配，即a对应x，则b不能再对应x，

所以要用两个map，char-string 和 string-char

边遍历边加，如果当前有个map中string和char对应关系不对，则是false

还有要注意的一点是，必须先判断str中的词数和pattern中的字符数是否相等，若不等返回false，否则会误判

```java
class Solution {
    public boolean wordPattern(String pattern, String str) {
        HashMap<Character, String> map = new HashMap<>();
        HashMap<String, Character> map2 = new HashMap<>();
        String[] strs = str.split(" ");
        if(strs.length!=pattern.length()) return false;

        for(int i=0;i<pattern.length();i++){
            char c = pattern.charAt(i);
            String s = strs[i];
            if(!map.containsKey(c) && !map2.containsKey(s)){
                map.put(c, strs[i]);
                map2.put(s, c);
            }else{
                if((map.containsKey(c) && !map.get(c).equals(s)) || (map2.containsKey(s) && !map2.get(s).equals(c)))
                    return false;
            }
        }
        return true;
    }
}
```

292. Nim Game
Easy

You are playing the following Nim Game with your friend: There is a heap of stones on the table, each time one of you take turns to remove 1 to 3 stones. The one who removes the last stone will be the winner. You will take the first turn to remove the stones.

Both of you are very clever and have optimal strategies for the game. Write a function to determine whether you can win the game given the number of stones in the heap.

Example:

Input: 4
Output: false 
Explanation: If there are 4 stones in the heap, then you will never win the game;
             No matter 1, 2, or 3 stones you remove, the last stone will always be 
             removed by your friend.

你和你的朋友在玩下面的尼姆游戏:桌子上有一堆石头，每次你们轮流移走1到3块石头。谁把最后一块石头搬走谁就是胜利者。你将在第一个回合移走石头。

你们俩都很聪明，在游戏中都有最佳的策略。写一个函数来确定你是否可以赢得游戏给定的石头堆的数量。

输入:4
输出:false
说明:如果堆里有4块石头，你就永远赢不了游戏;无论你移走1块、2块还是3块石头，最后的那些石头永远可以被你的朋友拿走了。
不能用trackback，其关键在于，两人都很聪明，所以每一步都是要以赢的想法去做的，有些操作不会做，而trackback会导致所有的操作都做，就会产生错误


定理：若n是4的倍数，第一个得到的数字的人将输掉，否则他/她将获胜。
    若n是4的倍数，那么第二个人始终只拿到4的倍数为止的数字即可获胜：如第一个人拿1，2；则第二个人拿3，4. 第一个人拿1，则第二个人拿2，3，4
    若n不是4的倍数，则第一个人始终只要保证剩下的牌数是4的倍数，即可获胜

证明：
    1. 基本情况：当n = 4时，如问题提示所提示，无论第一个玩家是哪个号码，第二个玩家都将始终能够选择剩余的号码。
    2. 对于1 * 4 < n <2 * 4（n = 5、6、7）
        第一个玩家可以将初始号码减少为4，这会将死亡号码4留给第二个玩家。即数字5、6、7是任何首先获得该数字的玩家的获胜数字。
    3. 现在到下一个周期的开始，n = 8，无论第一个玩家选择哪个号码，它总是将获胜号码（5、6、7）留给第二个玩家。因此，8％4 == 0，再次是死亡数字。
    4. 在第二种情况之后，对于（2 * 4 = 8）和（3 * 4 = 12）之间的数字，即9、10、11，再次成为第一位玩家的获胜数字，因为第一位玩家总是可以减少该数字进入死亡数字8。

根据上述定理和证明，解决方案再简单不过了：

```java
class Solution {
    public boolean canWinNim(int n) {
        return n%4!=0;
    }
}
```

299. Bulls and Cows
Easy

You are playing the following Bulls and Cows game with your friend: You write down a number and ask your friend to guess what the number is. Each time your friend makes a guess, you provide a hint that indicates how many digits in said guess match your secret number exactly in both digit and position (called "bulls") and how many digits match the secret number but locate in the wrong position (called "cows"). Your friend will use successive guesses and hints to eventually derive the secret number.

Write a function to return a hint according to the secret number and friend's guess, use A to indicate the bulls and B to indicate the cows. 

Please note that both secret number and friend's guess may contain duplicate digits.

Example 1:

Input: secret = "1807", guess = "7810"

Output: "1A3B"

Explanation: 1 bull and 3 cows. The bull is 8, the cows are 0, 1 and 7.
Example 2:

Input: secret = "1123", guess = "0111"

Output: "1A1B"

Explanation: The 1st 1 in friend's guess is a bull, the 2nd or 3rd 1 is a cow.
Note: You may assume that the secret number and your friend's guess only contain digits, and their lengths are always equal.

您正在和您的朋友一起玩以下Bulls and Cows游戏：您写下一个数字，并请您的朋友猜出数字是多少。 每次您的朋友进行猜测时，您都会提供一个提示，指示该猜测中有多少位数字与您的秘密号码在数字和位置上都完全匹配（称为“ bulls”），有多少位数字与秘密号码匹配但位于错误的位置 （称为“cows”）。 您的朋友将使用连续的猜测和提示来最终得出秘密号码。

根据密码和朋友的猜测编写一个函数以返回提示，用A表示bulls，用B表示cows。

请注意，密码和朋友的猜测都可能包含重复的数字。


Input: secret = "1807", guess = "7810"

Output: "1A3B"

Explanation: 1 bull and 3 cows. The bull is 8, the cows are 0, 1 and 7.
Example 2:

Input: secret = "1123", guess = "0111"

Output: "1A1B"

先顺序遍历两个串，把相同数字对应的个数都弄出来，放入一个数组a[10]中，然后再把secret中每个数字的个数放入s[10]中，guess个数放入g[10]中
A就是遍历a中的所有数字之和， 再同时遍历s和g，countB +=  min(s[i]-a[i], g[i]-a[i])。  该题的切入点就是：cows就是secret中有，guess中有，但是位置不对的数字

```java
class Solution {
    public String getHint(String secret, String guess) {
        int[] a = new int[10];
        int[] s = new int[10];
        int[] g = new int[10];
        int sLen = secret.length();
        int gLen = guess.length();
        int minLen = Math.min(sLen, gLen);


        for(int i=0;i<minLen;i++){
            if(secret.charAt(i)==guess.charAt(i)){
                a[secret.charAt(i)-'0']++;
            }
            s[secret.charAt(i)-'0']++;
            g[guess.charAt(i)-'0']++;
        }
        int A=0,B=0;
        for(int i=0;i<10;i++){
            A += a[i];
            B += Math.min(s[i]-a[i], g[i]-a[i]);
        }
        return A + "A" + B + "B";
    }
}
```

303. Range Sum Query - Immutable
Easy

Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

Example:
Given nums = [-2, 0, 3, -5, 2, -1]

sumRange(0, 2) -> 1
sumRange(2, 5) -> -1
sumRange(0, 5) -> -3
Note:
You may assume that the array does not change.
There are many calls to sumRange function.

给定一个整数数组nums，找到索引i和j之间的元素之和（i≤j）（含）。

您可以假设数组没有更改。
有许多对sumRange函数的调用。

由于调用有很多，所以不能每次都算

设sum[i] 为 从第0个开始，截止到第i个（包含）的和

sumRange(i,j) = i==0?sum[j]:(sum[j]-sum[i-1])

```java
class NumArray {
    int[] sum;
    public NumArray(int[] nums) {
        sum = new int[nums.length];
        for(int i=0;i<nums.length;i++){
            sum[i] += i==0?nums[i]:(sum[i-1]+nums[i]);
        }
    }

    public int sumRange(int i, int j) {
        return i==0?sum[j]:(sum[j]-sum[i-1]);
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */
```

304. Range Sum Query 2D - Immutable
Medium

Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

Range Sum Query 2D
The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.

Example:
Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
sumRegion(1, 1, 2, 2) -> 11
sumRegion(1, 2, 2, 4) -> 12
Note:
You may assume that the matrix does not change.
There are many calls to sumRegion function.
You may assume that row1 ≤ row2 and col1 ≤ col2.

给定一个2D矩阵matrix，求给定的左上角(row1, col1)和右下角(row2, col2)所定义的矩形内元素的和。

范围和查询2D
上面的矩形(带有红色边框)由(row1, col1) =(2,1)和(row2, col2) =(4,3)定义，其中包含sum = 8。

你可以假设矩阵不变。
对sumRegion函数的调用有很多。
您可以假设row1≤row2, col1≤col2。

由于调用很多，所以不能每次调用都去遍历计算

》方法一：
设dp[i][j]是第i行，前j个元素的和,从第0个开始算

dp[i][j] = dp[i][j-1]+nums[i][j]

sumRegion(row1, col1, row2, col2) = for(int i=row1,i<=row2;i++) sum += dp[i][col2]-dp[i][col1-1]


》方法二：
    设dp[i][j]是(0,0)为左上角，(i,j)为右下角的矩形中的和
    则有递推公式：dp[i][j] = dp[i-1][j]+dp[i][j-1]-dp[i-1][j-1]+nums[i][j]
    i=0时，dp[i][j] = dp[i][j-1]+nums[i][j]
    j=0时，dp[i][j] = dp[i-1][j]+nums[i][j]
    dp[0][0] = nums[0][0]
    计算结果:
            int sum = dp[row2][col2];
            if(row1>0)
                sum -= dp[row1-1][col2]; //去掉上侧的一块
            if(col1>0)
                sum -= dp[row2][col1-1];  //去掉左侧的一块
            if(row1>0 && col1>0)
                sum += dp[row1-1][col1-1]; //加上被多去掉的一块，即左上角那一块
            return sum;



```java
//方法一：
    class NumMatrix {

        int[][] dp;
        public NumMatrix(int[][] matrix) {
            if(matrix==null || matrix.length==0 || matrix[0].length==0) return;
            int row = matrix.length;
            int col = matrix[0].length;
            dp = new int[row][col];
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    if(j==0) dp[i][j]=matrix[i][j];
                    else{
                        dp[i][j] = dp[i][j-1]+matrix[i][j];
                    }
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            if(dp==null || !(row2>=row1 && col2>=col1)) return 0;
            int sum = 0;
            for(int i=row1;i<=row2;i++){
                if(col1==0){
                    sum += dp[i][col2];
                }else{
                    sum += dp[i][col2]-dp[i][col1-1];
                }
            }
            return sum;
        }
    }

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */


//方法二：

    class NumMatrix {
        int[][] dp;
        int[][] nums;
        public NumMatrix(int[][] matrix) {
            if(matrix==null || matrix.length==0 || matrix[0].length==0) return;
            int row = matrix.length;
            int col = matrix[0].length;
            dp = new int[row][col];
            nums = matrix;

            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    if(i==0 && j==0) dp[0][0] = matrix[0][0];
                    else if(i==0) dp[i][j] = dp[i][j-1]+matrix[i][j];
                    else if(j==0) dp[i][j] = dp[i-1][j]+matrix[i][j];
                    else{
                        dp[i][j] = dp[i-1][j]+dp[i][j-1]-dp[i-1][j-1]+matrix[i][j];
                    }
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            if(dp==null || !(row2>=row1 && col2>=col1)) return 0;
            int sum = dp[row2][col2];
            if(row1>0)
                sum -= dp[row1-1][col2];
            if(col1>0)
                sum -= dp[row2][col1-1];
            if(row1>0 && col1>0)
                sum += dp[row1-1][col1-1];
            return sum;
        }
    }



```






306. Additive Number
Medium

Additive number is a string whose digits can form additive sequence.

A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent number in the sequence must be the sum of the preceding two.

Given a string containing only digits '0'-'9', write a function to determine if it's an additive number.

Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.

Example 1:

Input: "112358"
Output: true
Explanation: The digits can form an additive sequence: 1, 1, 2, 3, 5, 8. 
             1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
Example 2:

Input: "199100199"
Output: true
Explanation: The additive sequence is: 1, 99, 100, 199. 
             1 + 99 = 100, 99 + 100 = 199
 

Constraints:

num consists only of digits '0'-'9'.
1 <= num.length <= 35
Follow up:
How would you handle overflow for very large input integers?


加法数是一个数字可以构成加法序列的字符串。
一个有效的加法序列应该包含至少三个数字。除了前两个数字外，序列中的每个后续数字都必须是前两个数字的和。
给定一个只包含数字“0”-“9”的字符串，写一个函数来确定它是否是一个加法数字。这里序列必须是连着的直到最后字符串结束
如上面，1，99，100，199 ，  1+99=100，99+100=199

注意:加性序列中的数字不能有前导零，因此序列1、2、03或1、02、3无效。


这里使用简单的递归思想，如果x1，x2的和等于下一个数字为x3，则则再判断x2和x3的和，持续往下走

```java
class Solution {

    public boolean isAdditiveNumber(String num) {
        int len = num.length();
        for(int i=1;i<=len / 2;i++){  //i不超过总长度除以2，由于奇数除以2向下取整，所以这里必须用小等于号
            //i是第一个数字的长度
            //取[0,i), 第0个数字为0且有效的情况，只能是第一个数字就是0，否则都无效
            if (num.charAt(0) == '0' && i > 1) return false; //若有前导0
            BigInteger x1 = new BigInteger(num.substring(0, i)); //取(0,i)

            //显然两个加数的长度都不能大于和的最大长度（两个加数的长度分别为i和j，它们两和的长度一定不小于它们，且字符串剩下的可用长度是len-（i+j））
            //这个就是它们和的长度的上限
            for(int j=1;Math.max(i,j)<=len-(j+i);j++){
                //j是第二个数字的长度
                if(num.charAt(i) == '0' && j > 1) //第二个数若有前导0，不要直接返回，而是跳出此次循环，让第一个数增长一位（后面加上0），这样第二个数就没有前导0了
                    break;
                BigInteger x2 = new BigInteger(num.substring(i, i+j));
                if(isValid(x1, x2, i+j, num)) return true;
            }
        }
        return false;
    }

    boolean isValid(BigInteger x1, BigInteger x2, int startPos, String num){
        if(startPos==num.length()) return true;
        BigInteger sum = x1.add(x2);
        return num.startsWith(sum.toString(), startPos) && isValid(x2, sum, startPos+sum.toString().length(), num);
    }
}
```

307. Range Sum Query - Mutable
Medium

Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

The update(i, val) function modifies nums by updating the element at index i to val.

Example:

Given nums = [1, 3, 5]

sumRange(0, 2) -> 9
update(1, 2)
sumRange(0, 2) -> 8
Note:

The array is only modifiable by the update function.
You may assume the number of calls to update and sumRange function is distributed evenly.

给定一个整数数组编号，求索引i和j (i≤j)之间元素的和,(两边都是闭区间)
update(i, val)函数通过将索引i处的元素更新为val来修改nums。
是303的改进版，这次可以修改其中的值了，update和sumRange的调用是均匀分布的。
主体思想还是sumRange(i,j) = i==0?sum[j]:(sum[j]-sum[i-1])
但当有update(x,i)的时候，计算offset =  x-num[i], 然后更新所有的 sum[j]  (j从i到len-1)

```java
class NumArray {

        int[] sum;
        int[] nums;
        public NumArray(int[] nums) {
            this.nums = nums;
            sum = new int[nums.length];
            for(int i=0;i<sum.length;i++){
                sum[i] = i==0?nums[i]:sum[i-1]+nums[i];
            }
        }

        public void update(int i, int val) {
            int offset = val- nums[i];
            nums[i] = val;
            for(int j=i;j<sum.length;j++){
                sum[j] += offset;
            }
        }

        public int sumRange(int i, int j) {
            return i==0?sum[j]:(sum[j]-sum[i-1]);
        }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */
```

310. Minimum Height Trees
Medium

For an undirected graph with tree characteristics, we can choose any node as the root. The result graph is then a rooted tree. Among all possible rooted trees, those with minimum height are called minimum height trees (MHTs). Given such a graph, write a function to find all the MHTs and return a list of their root labels.

Format
The graph contains n nodes which are labeled from 0 to n - 1. You will be given the number n and a list of undirected edges (each edge is a pair of labels).

You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

Example 1 :

Input: n = 4, edges = [[1, 0], [1, 2], [1, 3]]

        0
        |
        1
       / \
      2   3 

Output: [1]
Example 2 :

Input: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]

     0  1  2
      \ | /
        3
        |
        4
        |
        5 

Output: [3, 4]
Note:

According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”
The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.

最小高度树
对于具有树特征的无向图，我们可以选择任何节点作为根。 结果图就是一棵有根树。 在所有可能的有根树中，具有最小高度的树被称为最小高度树（MHT）。 给定这样一个图，编写一个函数来查找所有MHT并返回其根标签列表。（这里不是二叉树，只需是树即可）
该图包含从0到n-1标记的n个节点。将为您提供数字n和一个无向边的列表（每个边是一对标签）。
您可以假定边缘中不会出现重复的边缘。 由于所有边缘都是无方向的，因此[0，1]与[1，0]相同，因此不会一起出现在边缘中。

归结到图算法中，其实是求图中每个节点到图中其他节点的最大距离中的最小值。

》超时方法
    广度优先遍历每一个节点，计算每个节点到图中其他节点的最大值，在这些最大值中挑出最小的，这个起始点就是一个MHT的根
    这里的广度优先遍历要记录每次遍历的层级，采取这样的方法：
    while(队列不空){
        for(i<当前层的节点数){
            if(当前层的节点的邻节点有 没有被访问过的，将这个邻节点加入队列，且下一层节点数++
        }
        当前层节点数 = 下一层节点数
    }
    使用这种普通广搜方法会超时，需要考虑更特殊的解法

》AC方法：
    我们的问题是要我们找到最小高度树并返回其根标签。首先，我们可以考虑一个简单的案例-路径图。
    对于n个节点的路径图(本质是链表)，发现最小高度树是微不足道的。只需将中间点指定为根即可。
    尽管琐碎，让我们设计一个算法来找到它们。
    假设我们不知道n，也没有节点的随机访问权。我们必须遍历。很容易得到两个指针的想法。两端各有一个，并以相同的速度移动。当它们相遇或相距一步之遥时（取决于n的奇偶性），我们就有了想要的根。

    这给我们提供了许多有用的想法来破解我们的实际问题。
    用拓扑排序的思想，每次都删除度为1的节点，最终剩下的一个或两个节点就是这个根
    而且要注意的是如何保留一个节点所有的邻节点，只需要用ArrayList<HashSet<Integer>> list:  list.get(i)中包含了i节点的邻节点
    时间复杂度和空间复杂度均为O（n）。

    ## 这里有一个重要的思想是，当一个节点度不为1时，删除了一轮度为1的节点后，它的度变成了1，唯一的可能性是它是上一轮度为1的节点的邻节点
    在树中，就是一个节点从非叶子节点变为叶子节点，唯一的可能性是它是上一次叶子节点的邻节点


```java
class Solution {

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        ArrayList<HashSet<Integer>> list = new ArrayList<>();
        for(int i=0;i<n;i++){
            list.add(new HashSet<>());
        }

        //构建邻节点图
        for(int i=0;i<edges.length;i++){
            list.get(edges[i][0]).add(edges[i][1]);
            list.get(edges[i][1]).add(edges[i][0]);
        }

        ArrayList<Integer> leaves = new ArrayList<>();
        boolean[] flag = new boolean[n];
        int x = n; //表示剩下没删除的节点数
        //加入当前所有的叶子
        for(int i=0;i<n;i++){
            if(list.get(i).size()<=1){
                leaves.add(i);
                flag[i] = true;
            }
        }

        //删除旧叶子，并添加新叶子，直到删的就剩2个或1个
        while(x>2){
            ArrayList<Integer> newLeaves = new ArrayList<>();

            for(int leaf:leaves){ //因为每一个都是叶子，所以它们只有唯一的邻节点
                //一个节点从非叶子节点变为叶子节点，唯一的可能性是它是上一次叶子节点的邻节点
                Iterator iter = list.get(leaf).iterator();
                int neighbor = (int) iter.next();
                //每个叶子的邻居都删除叶子
                list.get(neighbor).remove(leaf);
                x--;
                //若该邻居变成了叶子，加入新的叶子节点,已经在叶子集中的节点不用再加
                if(list.get(neighbor).size()<=1 && !newLeaves.contains(neighbor))
                    newLeaves.add(neighbor);
            }
            leaves = newLeaves;
        }
        return leaves;
    }
}
```

313. Super Ugly Number
Medium

Write a program to find the nth super ugly number.

Super ugly numbers are positive numbers whose all prime factors are in the given prime list primes of size k.

Example:

Input: n = 12, primes = [2,7,13,19]
Output: 32 
Explanation: [1,2,4,7,8,13,14,16,19,26,28,32] is the sequence of the first 12 
             super ugly numbers given primes = [2,7,13,19] of size 4.
Note:

1 is a super ugly number for any given primes.
The given numbers in primes are in ascending order.
0 < k ≤ 100, 0 < n ≤ 10^6, 0 < primes[i] < 1000.
The nth super ugly number is guaranteed to fit in a 32-bit signed integer.

编写一个程序来找出第n个超级丑数。

超级丑数是正数，它的所有质因数都在给定的质数列表中，质数列表的大小为k。

用求第n个丑数的思想：在当前已有的丑数中依次乘以每个因子，取出所有第一个比当前最大丑数大的数，并取其最小值。但不能直接套用，否则会超时，需要改进一下，
若当前丑数为x，需要保存每个质因子乘以某丑数后比x更大的第一个丑数的索引，相比上面的，就是以空间换时间


```java
class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] ugly = new int[n];
        int[] index = new int[primes.length];
        ugly[0]=1;
        Arrays.fill(index,0);
        int minLarger = Integer.MAX_VALUE;
        for(int i=1;i<n;i++){
            for(int j=0;j<primes.length;j++){
                ugly[i] = Math.min(primes[j] * ugly[index[j]]);
            }
            //对于每个质因数，找到与其相乘后第一个比ugly[i]大的那个丑数的索引，由于index[j]存的是与primes[j]相乘后第一个比ugly[i-1]大的丑数的索引，
            //而ugly[i]大于ugly[i-1]，所以现在要找的索引只可能大等于index[j]。只需要在它的基础上递增试验即可,所以index[j]的值是只增不减的
            for(int j=0;j<primes.length;j++){
                while(ugly[i] >= primes[j] * ugly[index[j]]) index[j]++;
            }
        }
        return ugly[n-1];
    }
}
```

316. Remove Duplicate Letters
Hard

Given a string which contains only lowercase letters, remove duplicate letters so that every letter appears once and only once. You must make sure your result is the smallest in lexicographical order among all possible results.

Example 1:

Input: "bcabc"
Output: "abc"
Example 2:

Input: "cbacdcbc"
Output: "acdb"

给定一个只包含小写字母的字符串，删除重复的字母，使每个字母只出现一次。您必须确保在所有可能的结果中，您的结果按字典顺序是最小的。
例如：
Input: "cbacdcbc"
Output: "acdb"

对于当前串中的的最小字母，我们一定选最左边的这个字母进入答案，例如，串中有多个a，一定是最左边的a能保证后面跟的串有可能最小

遍历串，记录当前串中每个字母出现的个数
遍历串，记录当前最小且最左的字母的位置pos，作为答案串开始的位置，当出现一个字母只出现这一次时，跳出循环，因为pos不能比这个字母的位置更右，否则该字母就进不到答案串里了,设pos对应的字符是x
返回 x + sub.      sub = 递归（ s.substing(pos+1)且消除了其中所有的x的字符串 )


```java
class Solution {
    public String removeDuplicateLetters(String s) {
        int[] cnt = new int[26];
        for(int i=0;i<s.length();i++)
            cnt[s.charAt(i)-'a']++;
        int startPos = 0;
        for(int i=0;i<s.length();i++){
            //startPos记录最小且最左的字母位置
            if(s.charAt(i)<s.charAt(startPos))
                startPos = i;
            //如果某个字母只在此处出现一次,则答案子串中一定得包含它，因此startPos不能比它更大
            if(--cnt[s.charAt(i)-'a']==0)
                break;
        }

        //返回startPos对应的x开头的字符，且把后面的部分继续递归，而且把后续部分中的x除掉
        return s.length()==0?"":s.charAt(startPos)+removeDuplicateLetters(s.substring(startPos+1).replaceAll(s.charAt(startPos)+"", ""));

    }

}
```

318. Maximum Product of Word Lengths
Medium

Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where the two words do not share common letters. You may assume that each word will contain only lower case letters. If no such two words exist, return 0.

Example 1:

Input: ["abcw","baz","foo","bar","xtfn","abcdef"]
Output: 16 
Explanation: The two words can be "abcw", "xtfn".
Example 2:

Input: ["a","ab","abc","d","cd","bcd","abcd"]
Output: 4 
Explanation: The two words can be "ab", "cd".
Example 3:

Input: ["a","aa","aaa","aaaa"]
Output: 0 
Explanation: No such pair of words.

给定一个字符串数组单词，找出长度(word[i]) * 长度(word[j])的最大值，
且这两个单词不共享公共字母。您可以假设每个单词只包含小写字母。如果不存在这两个单词，则返回0。

关键在于如何快速判断一个单词中的字母是否在另一个单词中，如果使用set，可以但是不够好。因为小写字母只有26个，考虑对每个单词使用一个整数（31位，不考虑符号位），若有字母x，则让该整数二进制的第x-'a'位为1，否则为0，若要判断两个单词是否有同样的字母，只需让两个单词对应的这个整数做与操作，如果不为0则说明有相同的字母

```java
class Solution {

    public int maxProduct(String[] words) {
        if(words==null || words.length==0) return 0;
        int len = words.length;
        int[] values = new int[len];
        for(int i=0;i<len;i++){
            for(int j=0;j<words[i].length();j++){
                values[i] |= 1<<(words[i].charAt(j)-'a');
            }
        }

        int ans = 0;
        for(int i=0;i<words.length;i++){
            for(int j=i+1;j<words.length;j++){
                if((values[i]&values[j])==0){
                    ans = Math.max(ans, words[i].length() * words[j].length());
                }
            }
        }
        return ans;
    }

}
```

319. Bulb Switcher
Medium

There are n bulbs that are initially off. You first turn on all the bulbs. Then, you turn off every second bulb. On the third round, you toggle every third bulb (turning on if it's off or turning off if it's on). For the i-th round, you toggle every i bulb. For the n-th round, you only toggle the last bulb. Find how many bulbs are on after n rounds.

Example:

Input: 3
Output: 1 
Explanation: 
At first, the three bulbs are [off, off, off].
After first round, the three bulbs are [on, on, on].
After second round, the three bulbs are [on, off, on].
After third round, the three bulbs are [on, off, off]. 

So you should return 1, because there is only one bulb is on.


一开始有n个灯泡是关着的。第一轮，你先把所有的灯泡都打开。第二轮，将灯泡分为两个为一组，关掉每组的第2个灯泡。在第三轮中，每三个灯泡为一组，你要切换每组的第三个灯泡(如果是关着的就打开，如果是开着的就关闭)。在第i轮，让灯泡每i个为一组，切换每组的第i个灯泡。在第n轮，你只切换最后一个灯泡。求n轮之后有多少灯泡是亮着的。

如果input为1，则返回0，若input为2，返回n

这道题可以延伸一些知识：

# 约数个数定理：
对于一个大于1正整数n可以分解质因数 n=p1^a1 * p2^a2 ... * pk^ak
则n的正约数个数就是 f(n) = (a1+1)* (a2+1)... * (ak+1)
其中a1,a2...ak 是p1，p2...pk的指数

但实际上并不用这个知识，用它会超时。

灯泡相当于一开始全是灭的，当它切换奇数次时，它就是亮的。
灯泡i在第d轮切换状态，当且仅当d可以整除i时，因此当且仅当i有奇数个因数时，灯泡i才会亮

因数是成对出现的，比如i=12时，有1和12，2和6，3和4。除非i是平方数，比如36，有1和36，2和18，3和12，4和9，以及一个单独的6。
因此当且仅当i是平方数时，灯泡i才会亮着。所以只需要计算1到n之间的平方数的个数即可
让r = int(sqrt(n)), 即r是1到n之间的最大的平方根，而1是最小的平方根。1到n之间的平方数只能是：1^2, 2^2 .... r^2，共有r个数
因此该题的答案就是sqrt(n)

```java
class Solution {
    public int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }
}
```

######## 300 题完结 ✿✿ヽ(°▽°)ノ✿########

321. Create Maximum Number
Hard

Given two arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number of length k <= m + n from digits of the two. The relative order of the digits from the same array must be preserved. Return an array of the k digits.

Note: You should try to optimize your time and space complexity.

Example 1:

Input:
nums1 = [3, 4, 6, 5]
nums2 = [9, 1, 2, 5, 8, 3]
k = 5
Output:
[9, 8, 6, 5, 3]
Example 2:

Input:
nums1 = [6, 7]
nums2 = [6, 0, 4]
k = 5
Output:
[6, 7, 6, 0, 4]
Example 3:

Input:
nums1 = [3, 9]
nums2 = [8, 9]
k = 3
Output:
[9, 8, 9]

给定两个长度为m和n的数组，只包含数字0到9，每个数组都代表一个数。 从两个数组中创建最大长度k <= m + n的新数组（代表一个数，从第0位到第k位代表数位从高到低）。 该数组中的数字必须保留同一数组中数字的相对顺序。 返回这个k个数字的数组。

注意:你应该尽量优化你的时间和空间复杂度。

该题其实是很麻烦的一道题，最终也只能达到O((m+n)^3)的时间复杂度。

首先是对两个数组各取若干个数字，若nums1取i个，则nums2取k-i个，且取的数字必须是顺序能组成的最大数，
采用回溯法，当遍历到一个比前一个取的数字更大的数字时，回溯已经取的数字，目的是想要把大的数字尽可能往前移，且还要满足剩下的数字够取

把nums1和nums2中的尽可能大的数字取出后，使用归并排序让他们组成的数字尽可能大，
先是普通的归并，当两个指针指向的数字不一样时，优先将大数字放入结果，两数字一样大时，要额外处理，则先取后面有更大值的那一个

比较所有这样取得的序列（由i值不同取得不同的序列），其中最大的就是答案。

下面得到是如何得到一个数组中尽可能大的子数组，且保持数字相对顺序不变的方法：
    public int[] maxArray(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[k];

        //j是当前ans中的元素数
        for(int i=0,j=0;i<n;i++){

            //如果当前数nums[i]大于ans[j-1]，则继续往前找，直到剩下的数字(n-i)+ans中的数字个数(j)少于等于k，或者ans[j-1]大于当前数，则停止寻找
            while (n-i+j>k && j>0 && nums[i]>ans[j-1]){
                j--;
            }

            if(j<k)
                ans[j++] = nums[i];
        }
        return ans;
    }



```java
class Solution {

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length;
        int len2 = nums2.length;


        int[] ans = new int[k];
        for(int i=0;i<=k && i<=len1;i++){
            if(k-i>len2) continue;
            int[] a1 = maxArray(nums1, i);
            int[] a2 = maxArray(nums2, k-i);
            int[] candidate = merge(a1,a2);
            if(isGreater(candidate, ans, k)) ans = candidate;
        }
        return ans;
    }

    boolean isGreater(int[] newOne, int[] oldOne, int k){
        for(int i=0;i<k;i++){
            if(newOne[i]>oldOne[i]) return true;
            else if(newOne[i]<oldOne[i]) return false;
        }
        return false;
    }


    int[] merge(int[] num1, int[] num2){
        if(num1==null) return num2;
        else if(num2==null) return num1;
        //不是简单的归并，而是要拼成最大的序
        int len1 = num1.length;
        int len2 = num2.length;

        int p1=0,p2=0;
        int[] ans = new int[len1+len2];
        int r = 0;
        //先是普通的归并，当两个指针指向的数字不一样时，优先将大数字放入结果
        while(p1<len1 && p2<len2){
            if(num1[p1]>num2[p2]){
                ans[r++] = num1[p1++];
            }else if(num1[p1]<num2[p2]){
                ans[r++] = num2[p2++];
            }else{
                //两数字一样大时，要额外处理，则先取后面有更大值的那一个
                int i=p1,j=p2;
                while(i<len1&&j<len2&&num1[i]==num2[j]){
                    i++;
                    j++;
                }
                //如果其中一个比到底了也没比出来,说明后面都是一样大的，优先放没到底的那个，因为没到底的后面还可能有更大的数字
                //例如 0 和 0 6 ，先放第二个0，则会变成：060，如果先放第一个0，则只能变成 006，显然第一种情况数字更大
                if(i==len1 || j==len2){
                    if(i==len1) ans[r++]=num2[p2++];
                    else ans[r++]=num1[p1++];
                }else{
                    //如果比出了大小，则取后面先有更大值的那一个，目的是让后面的大值尽早出现在前面
                    if(num1[i]>num2[j]) ans[r++]=num1[p1++];
                    else ans[r++]=num2[p2++];
                }
            }
        }
        if(p1==len1 && p2!=len2){
            while(p2<len2){
                ans[r++]=num2[p2++];
            }
        }else if(p1!=len1 && p2==len2){
            while(p1<len1){
                ans[r++]=num1[p1++];
            }
        }
        return ans;
    }

    public int[] maxArray(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[k];

        //j是当前ans中的元素数
        for(int i=0,j=0;i<n;i++){

            //如果当前数nums[i]大于ans[j-1]，则继续往前找，直到剩下的数字(n-i)+ans中的数字个数(j)少于等于k，或者ans[j-1]大于当前数，则停止寻找
            while (n-i+j>k && j>0 && nums[i]>ans[j-1]){
                j--;
            }

            if(j<k)
                ans[j++] = nums[i];
        }
        return ans;
    }
}
```

327. Count of Range Sum
Hard

Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.

Note:
A naive algorithm of O(n^2) is trivial. You MUST do better than that.

Example:

Input: nums = [-2,5,-1], lower = -2, upper = 2,
Output: 3 
Explanation: The three ranges are : [0,0], [2,2], [0,2] and their respective sums are: -2, -1, 2.


给一个数组，询问多少个区间和在某个[lower,upper]之内。
求出前缀和sums[i] 。
归根结底是要计算sums[j]-sums[i] （j>i）满足条件的个数,对sums使用归并排序，在每次归并完左右两个子区间后，当前区间两部分分别都已经排序完毕，然后将这两部分归并
此时只需计算前半部分sums当做sums[i]，后半部分sums当做sums[j]时，满足条件的个数
lower<=sums[j]-sums[i]<=upper  --->   sums[j]-upper<=sums[i]<=sums[j]-lower
对于sums[i]，找到第一个满足条件的j，令为l，以及l后第一个不满足条件的i，令为r
因为sums[j]和sums[i]是单调递增的，因此，对于i+1对应的l和r只会比i对应的l和r更大
l和r是单调不减的。，因此该循环的总复杂度是O(n)

这里有两个技巧，
    1.为了让sum[i]满足条件时自身也能算入，且nums都能被sum的表达式表示，令sums长度为n+1，
      且让sums[0]=0，sums[i+1]=sums[i]+nums[i]。这样任何sums[i]-sums[0]=sums[i]如果满足条件就会被算入了；任何nums[i]
    2.归并的时候要满足待归并块大于2个，当待归并块只有一个时（即low==high），不进行操作


```java
class Solution {

    public int countRangeSum(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n+1];
        for (int i = 0; i < n; ++i) {
            sums[i+1] = sums[i] + nums[i];

        }

        return countWithMergeSort(0,n,sums,lower,upper);
    }

    //还有一个关键在于，只排2个以上数，当只有数字自己时，不记录。而使得sums[0]为0，就是为了让sums[i]-0=sums[i]，如果自己本身也满足条件，则计入答案
    int countWithMergeSort(int low, int high,  long[] sums,int lower, int upper){
        if(low>=high)
            return 0;

        int mid = (low+high)/2;

        //排完序后，low到mid是有序的，mid+1到high是有序的。
        int count = countWithMergeSort(low, mid, sums, lower, upper)+
                countWithMergeSort(mid+1, high,sums,lower, upper);

        //归根结底是要计算sums[j]-sums[i] （j>i）满足条件的个数,此时只需计算前半部分sums当做sums[i]，后半部分sums当做sums[j]时，
        // 满足条件的个数
        //  lower<=sums[j]-sums[i]<=upper  --->   sums[j]-upper<=sums[i]<=sums[j]-lower
        //对于sums[i]，找到第一个满足条件的j，令为l，以及l后第一个不满足条件的i，令为r
        //因为sums[j]和sums[i]是单调递增的，因此，对于i+1对应的l和r只会比i对应的l和r更大
        //l和r是单调不减的。，因此该循环的总复杂度是O(n)
        int l = mid+1, r = mid+1;
        for (int i=low;i<=mid;i++){
            while(l<=high && sums[l] - sums[i]<lower)
                l++;
            while (r<=high && sums[r]- sums[i]<=upper)
                r++;
            count += r-l;
        }

        //把 两个有序序列归并排序到cache中
        long[] cache = new long[high-low+1];
        int p1=low, p2=mid+1;
        int i=0;
        while(p1<mid+1 && p2<=high){
            if(sums[p1]<sums[p2]){
                cache[i++] = sums[p1++];
            }else{
                cache[i++] = sums[p2++];
            }
        }
        //只剩一个序列，把它都加到后面,下面两个循环至多只会进入一个
        while (p1<mid+1){
            cache[i++] = sums[p1++];
        }
        while (p2<=high){
            cache[i++] = sums[p2++];
        }
        System.arraycopy(cache, 0, sums, low, high-low+1);
        return count;

    }

}
```

330. Patching Array
Hard

Given a sorted positive integer array nums and an integer n, add/patch elements to the array such that any number in range [1, n] inclusive can be formed by the sum of some elements in the array. Return the minimum number of patches required.

Example 1:

Input: nums = [1,3], n = 6
Output: 1 
Explanation:
Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
So we only need 1 patch.
Example 2:

Input: nums = [1,5,10], n = 20
Output: 2
Explanation: The two patches can be [2, 4].
Example 3:

Input: nums = [1,2,2], n = 5
Output: 0

给定一个已排序的正整数数组编号和一个整数n，向数组中添加元素，使范围[1,n]内的任何数字都可以由数组中某些元素的和组成。返回所需的最小补丁数量。


假设输入为nums = [1、2、4、13、43]和n =100。我们需要确保范围为[1,100]的所有和都是可能的。

使用给定的数字1、2和4，我们已经可以建立从0到7的所有和，即范围[0,8）。 但是我们无法建立和8，下一个给定的数字（13）太大。 因此，我们将8插入数组。 然后，我们可以在[0,16）中建立所有和。

我们需要在数组中插入16吗？ 不需要！ 我们已经可以建立和3，将给定的13加到我们得出的总和为16。我们还可以将13加到其他总和上，将范围扩展到[0,29）。

等等。 给定的43太大，无法求和29，因此我们必须在数组中插入29。 这将我们的范围扩展到[0,58）。 但随后43变得有用，并将我们的范围扩展到[0,101）。 至此，我们完成了。

如果当前第一个达不到的和大等于下一个待加入的数字，则直接把下一个数字加入集，更新下一个达不到的和；
如果当前第一个达不到的和小于下一个待加入的数字，则把这个和插入集中，更新下一个达不到的和为2倍的该和

当和为[1...i）时都能满足，第一个达不到的数是i，若数组中下一个数字是x>i，显然不能加入x，此时加入i，则第一个达不到的和成了2i，2i前面的数都能达到，
因为1到i-1都能达到，i也能达到，所以下一个达不到的是2i；若2i>=x, 把x加入集中，则此时第一个达不到的和成了2i+x，
为什么2i到2i+x之间的数能到达？此时显然能到达的数有：1，2，... 2i, x。那么显然1+x,2+x,....2i+x-1都能达到，且1，2,...2i都能到达，二者取并集，所以
1，2，.... 2i+x-1都能到达

但这个结果为啥是要加的最小个数，不知道怎么证明

```java
class Solution {
    public int minPatches(int[] nums, int n) {
        int len = nums.length;
        long missing = 1; //第一个到达不了的数，必须用long，否则出错
        int added = 0;
        int i=0;
        while(missing<=n){
            if(i<len && missing>=nums[i]){ //若到达不了的数比下一个数大或等，则直接加入下一个数就能更新上限
                missing += nums[i++];
            }else{ //若到达不了的数比下一个数小，则需要补充这个到达不了的数
                missing += missing;
                added++;
            }
        }
        return added;
    }
}
```



331. Verify Preorder Serialization of a Binary Tree
Medium

One way to serialize a binary tree is to use pre-order traversal. When we encounter a non-null node, we record the node's value. If it is a null node, we record using a sentinel value such as #.

     _9_
    /   \
   3     2
  / \   / \
 4   1  #  6
/ \ / \   / \
# # # #   # #
For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where # represents a null node.

Given a string of comma separated values, verify whether it is a correct preorder traversal serialization of a binary tree. Find an algorithm without reconstructing the tree.

Each comma separated value in the string must be either an integer or a character '#' representing null pointer.

You may assume that the input format is always valid, for example it could never contain two consecutive commas such as "1,,3".

Example 1:

Input: "9,3,4,#,#,1,#,#,2,#,6,#,#"
Output: true
Example 2:

Input: "1,#"
Output: false
Example 3:

Input: "9,#,#,1"
Output: false

方法一：
序列化二叉树的一种方法是使用前序遍历。当遇到非空节点时，我们记录该节点的值。如果它是一个空节点，我们使用一个标记值来记录，比如#。
例如，上面的二叉树可以序列化为字符串“9、3、4、#、#、1、#、#、2、#、6、#、#”，其中#表示空节点。
给定一串逗号分隔的值，验证它是否是正确的二叉树的前序遍历序列化。在不重构树的情况下找到算法。
字符串中每个逗号分隔的值必须是整数或字符'#'表示空指针。
您可以假设输入格式总是有效的，例如它不可能包含两个连续的逗号，比如“1，，3”。

设置一个数据结构，保存每个节点值，并表示它的左右孩子是否已找到
每遍历到一个节点，先看栈顶元素的左右孩子是否都找到，都找到了就让其弹栈，循环直到栈空或栈顶元素有孩子没找到
若当前节点是数字，让栈顶元素的为false的域设为true，然后把这个数字创建一个节点加入栈中。这里要注意，设置一个布尔位标识当前节点是否是树的根节点。当栈为空时，当前节点必须是根节点。若不是，说明后面的元素都是多余的，则返回false。
若当前节点是#，让栈顶元素的为false的域设为true，若当前栈为空则不满足，#的节点不入栈。
遍历完后，弹出所有栈中的元素，若栈中有左右孩子还没找到的节点，返回false。


方法二：借鉴c-p103的方法（更好）
直接把原序列放入队列中，然后进行前序遍历的二叉树的构建，每到要构建节点则出队一个元素，当构建到一个节点时队列中的元素不够了，则说明不符合，直接跳出返回。 因为构建时返回的类型是节点类型，所以要设置一个全局的变量flag，当元素不够时，设置其为false。并且在构建完最后要判断一下队列是否为空，如果不为空说明没用完节点。 最后返回 flag && 队列是否为空


```java
//方法一，很麻烦
class Solution {

    class Node{
        int val;
        boolean foundLeft;
        boolean foundRight;
        public Node(int v){
            val = v;
        }
    }

    public boolean isValidSerialization(String preorder) {
        //只有一个空节点
        if(preorder.equals("#")) return true;
        String[] order = preorder.split(",");
        //是否是第一个节点
        boolean isFirst = true;
        Stack<Node> stk = new Stack<>();
        for(int i=0;i<order.length;i++){
            char c = order[i].charAt(0);
            while(!stk.isEmpty() && stk.peek().foundLeft && stk.peek().foundRight){
                stk.pop();
            }
            if(c=='#'){
                //如果栈中没有元素，则肯定不符合
                if(stk.isEmpty()) return false;
                Node top = stk.peek();
                //找到了一个孩子
                if(!top.foundLeft) top.foundLeft = true;
                else top.foundRight = true;
            }else{
                int v = Integer.valueOf(c-'0');
                Node node = new Node(v);
                //如果前面有节点左或右孩子没找到，则该节点就是其中一个
                if(!stk.isEmpty()){
                    Node top = stk.peek();
                    if(!top.foundLeft) top.foundLeft = true;
                    else top.foundRight = true;
                }else{
                    //如果栈空，则必须是第一个节点，否则不满足条件
                    if(isFirst) isFirst = false;
                    else return false;
                }

                //将该节点加入栈中
                stk.push(node);
            }
        }

        //弹出所有左右孩子都找到的节点，如果符合条件栈中节点应该都是
        while(!stk.isEmpty() && stk.peek().foundLeft && stk.peek().foundRight){
            stk.pop();
        }

        //若栈还不为空，则不满足。
        return stk.isEmpty();
    }
}


//方法二：
class Solution {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        TreeNode(int x) {
            val = x;
        }
    }

    boolean canBuild = true;
    public boolean isValidSerialization(String preorder) {
        if(preorder==null || preorder.length()==0) return false;
        String[] strs = preorder.split(",");
        Queue<String> queue = new LinkedList<>();
        for(int i=0;i<strs.length;i++){
            queue.offer(strs[i]);
        }
        nodeBuild(queue);
        return canBuild && queue.isEmpty();


    }

    public TreeNode nodeBuild(Queue<String> queue){
        if(queue.isEmpty()){
            canBuild = false;
            return null;
        }
        String cur  = queue.poll();
        if(cur.equals("#")){
            return null;
        }
        TreeNode n = new TreeNode(Integer.valueOf(cur));
        n.left = nodeBuild(queue);
        n.right = nodeBuild(queue);
        return n;
    }
}

```

332. Reconstruct Itinerary
Medium

Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.

Note:

If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
All airports are represented by three capital letters (IATA code).
You may assume all tickets form at least one valid itinerary.
Example 1:

Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
Output: ["JFK", "MUC", "LHR", "SFO", "SJC"]
Example 2:

Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"].
             But it is larger in lexical order.


给定以成对的出发地和到达机场为代表的机票清单，请按顺序重构行程。 所有的票都属于一个从JFK出发的人。 因此，行程必须从JFK开始。

如果有多个有效路线，则当以单个字符串形式读取时，应返回词汇顺序最小的路线。 例如，行程[“ JFK”，“ LGA”]的词序比[[JFK]，“ LGB”]的词序小。所有机场都用三个大写字母（IATA代码）表示。

遍历出所有可能性，将所有可能性序列排序，取出其中最小的，这种方法超时

一开始考虑过用Map<String, PriorityQueue>的方式，但想的是从前往后走，
一旦遇到像 A->B, A->C，C->A这种情况，正常的顺序应该是A->C->A->B，但从A只能搜出来B，就卡壳了。
但其实是逆向思考的，从A开始如果能往下走则一直往下走，直到走不动，就把这个值加入答案中，然后再回溯。答案是从后往前添加的。

例如 A->B, A->C，C->A，从A开始

map中：A：B,C;   C：A
从A开始遍历其map，先取出的是B，
B的map为空，所以把B加入答案中,此时答案是 B
A的map剩下C，继续遍历C的map
    C的map剩下A，继续遍历A的map
        A的map为空，把A插入答案中，注意这里用头插法，所以此时答案是 AB
    C的map为空，把C插入答案中，所以此时答案是 CAB
A的map为空，把A插入答案中，所以此时答案是 ACAB

## 这种方法用来找有向图的边遍历路径，即该路径包含了有向图的所有边


```java
class Solution {
    List<String> ans = new ArrayList<>();
    Map<String, PriorityQueue<String>> map = new HashMap<>();
    public List<String> findItinerary(List<List<String>> tickets) {
        for(List<String> ticket: tickets){
            //如果给定的key对应的value不存在，则插入后面方法计算出的值，并返回该值
            //如果存在，则直接返回key对应的value
            //相当于
            /*
            if(!containsKey(ticket.get(0))){
                map.put(ticket.get(0), new PriorityQueue<>().add(ticket.get(0));
            }else{
                map.get(ticket.get(0)).add(ticket.get(0);
            }
            */
            map.computeIfAbsent(ticket.get(0), k->new PriorityQueue<>()).add(ticket.get(1));
        }
        visit("JFK");
        return ans;
    }

    public void visit(String startPos){
        while(map.get(startPos)!=null && map.get(startPos).size()>0){
            visit(map.get(startPos).poll());
        }
        ans.add(0, startPos);
    }

}
```

335. Self Crossing

You are given an array x of n positive numbers. You start at point (0,0) and moves x[0] metres to the north, then x[1] metres to the west, x[2] metres to the south, x[3] metres to the east and so on. In other words, after each move your direction changes counter-clockwise.

Write a one-pass algorithm with O(1) extra space to determine, if your path crosses itself, or not.

Example 1:

┌───┐
│   │
└───┼──>
    │

Input: [2,1,1,2]
Output: true
Example 2:

┌──────┐
│      │
│
│
└────────────>

Input: [1,2,3,4]
Output: false 
Example 3:

┌───┐
│   │
└───┼>

Input: [1,1,1,1]
Output: true 


你得到一个n个正数的x数组。从点(0,0)开始，向北移动x[0]米，向西移动x[1]米，向南移动x[2]米，向东移动x[3]米，以此类推。换句话说，每移动一次，你的方向就会逆时针改变。

编写一个带有O(1)额外空间的单遍算法来确定路径是否与自身相交。

可以每次以步长1往前走坐标，每过一个坐标，把它加入到set中，当遇到已经访问过的坐标，就是相交了，这样的空间复杂度是O(n)（居然内存使用超过了100%...），时间复杂度是O(n)，可AC

```java
class Solution {

    public boolean isSelfCrossing(int[] x) {
        int curX = 0;
        int curY = 0;
        HashSet<List<Integer>> visited = new HashSet<>();
        List<Integer> init = new ArrayList<>();
        init.add(curX);
        init.add(curY);
        visited.add(init);
        for(int i=0;i<x.length;i++){
            for(int j=x[i];j>0;j--){
                List<Integer> l = new ArrayList<>();
                if(i%4==0){
                    curY++;
                    l.add(curX);
                    l.add(curY);
                    if(!visited.add(l)) return true;
                }else if(i%4==1){
                    curX--;
                    l.add(curX);
                    l.add(curY);
                    if(!visited.add(l)) return true;
                }else if(i%4==2){
                    curY--;
                    l.add(curX);
                    l.add(curY);
                    if(!visited.add(l)) return true;
                }else{
                    curX++;
                    l.add(curX);
                    l.add(curY);
                    if(!visited.add(l)) return true;
                }
            }
        }
        return false;
    }
}


```

342. Power of Four
Easy

Given an integer (signed 32 bits), write a function to check whether it is a power of 4.

Example 1:

Input: 16
Output: true
Example 2:

Input: 5
Output: false
Follow up: Could you solve it without loops/recursion?

给定一个整数(有符号的32位)，写一个函数来检查它是否是4的幂。
能否不使用递归或循环判断？

递归或循环很简单，一直在保证余数为0的情况下除以4若最后能等于1则为4的幂
负数一定不是幂次

```java
class Solution {
    public boolean isPowerOfFour(int num) {
        if(num<=0) return false;
        if(num==1) return true;
        return num%4==0 && isPowerOfFour(num/4);
    }
}
```

数学方法：
很容易发现4的幂有这3个共同的特征。首先,大于0。其次，二进制符号中只有一个1位，所以我们用x&(x-1)来删除最低的1位，如果它变成0，就证明只有一个1位。第三，唯一的“1”位应该位于奇数位置，例如16。二进制是00010000。因此，我们可以使用'0x55555555'来检查'1'位是否在正确的位置。有了这个想法，我们可以很容易地把它写出来!

0x5的二进制： 0101 即只在奇数位有1，偶数位为0，将0x55555555与该数做与，若该数的1在奇数位，则结果不是0。

```java
class Solution {
    public boolean isPowerOfFour(int num) {
        return num>0 && ((num&(num-1))==0) && ((num & 0x55555555)!=0);  
    }
}
```

343. Integer Break
Medium

Given a positive integer n, break it into the sum of at least two positive integers and maximize the product of those integers. Return the maximum product you can get.

Example 1:

Input: 2
Output: 1
Explanation: 2 = 1 + 1, 1 × 1 = 1.
Example 2:

Input: 10
Output: 36
Explanation: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36.
Note: You may assume that n is not less than 2 and not larger than 58.

给定一个正整数n，把它分解成至少两个正整数的和，然后最大化这些整数的乘积。返回您可以得到的最大乘积。
n不会小于2不会大于58

将满足条件的数称为dp
得出每一个2到n数字的这个值，对每个数字i，trackback分解出所有可能的形式并求出其中最大的值，，并记录在dp[i]中供后续的数字调用

例如对于5来说，先将其分解为1+4，对于4，之前已经记录过了，其分解的加数的最大乘积为4，因此对于1+4的形式，4就不用再往下分解了，此时的乘积就为4；记录该数
再分解为2+3，对于3页之前记录过了，其分解的加数的最大乘积为2，此时就要比较，是用该数的分解加数的最大乘积还是用它自己，显然2* 3大于2* 2，记录此时的乘积为6，比之前的4大，因此dp[5]更新为6
再分解为3+2，后面的工作都是类似的。核心思想就是，对于分解后的加数，因为之前就计算过它自己的加数最大乘积，因此不用重复分解，直接用之前的结果即可

典型的划分子问题

```java
class Solution {

    int[] dp;
    public int integerBreak(int n) {
        dp = new int[n + 1];
        dp[2] = 1;
        //从3开始，2是一个特殊的值，单独放出来
        for (int i = 3; i <= n; i++) {
            trackBack(i, i, 1, 1);
        }
        return dp[n];
    }

    void trackBack(int n, int remain, int curProduct, int cnt) {
        //当分解到剩下1或2时，如果是2，直接在乘积上乘以2，因为dp[2]=1,故2>dp[2]。如果是1则直接就乘以1即可
        if (remain <= 2) {
            if (remain == 2) {
                curProduct *= 2;
            } else if (remain == 1) {
                curProduct *= 1;
            }
            dp[n] = Math.max(curProduct, dp[n]);
            return;
        }

        //直接使用该数字之前的结果和该数字作比较，取大者作为因数，这个数就不用再分解了。
        if (dp[remain]>0 && cnt > 1) {
            dp[n] = Math.max(dp[n], curProduct * Math.max(remain, dp[remain]));
            return;
        }

        //遍历每种可能性，其实这个循环只有在最外层会被调用，每分解一个成一个加数，因为该加数的结果之前就保存过，就不会再分解了。
        for (int i = 1; i < remain; i++) {
            trackBack(n, remain - i, curProduct * i, cnt + 1);
        }
    }
}
```



























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


















********************** 多线程题 **************************

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











































































































































































































































































































































































































































































































































