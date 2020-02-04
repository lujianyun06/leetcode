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

min和max的大小是反的
min = 0, max = 7; 此时直到分割点在中间
mid = max+min/2 = 3  nums[3]=6, nums[0]=3, nums[6]=2
知道0-3是顺序了，如果目标落在其中，那就直接二分

可知3-7里面一段有两段顺序的。
mid = max+min/2 = (7+3)/2 = 5  nums[5]=0  nums[3]=6, nums[6]=2
知道5-6是顺序了，如果落在其中，就直接二分。

3-5里面有两段顺序的。
mid = (3+5)/2 = 4  nums[4]=7, nums[3]=6,nums[5]=0
可知3-4是顺序的，落在其中就二分

4-5是两段顺序的，如果两个索引就差1，则搜索结束没找到
如果差的多，那就继续像刚才那样

```java
public class Solution{
    public int search(int[] nums, int target) {
        int len = nums.length;
        int start = 0;
        int end = len-1;
        int ans = -1;
        int mid;
        while(start <= end){
            if(end - start <= 1){
                if(nums[end]==target) return end;
                else if(nums[start] == target) return start;
                break;
            }
            mid = (end+start)/2;
            if(nums[mid] == target){
                return mid;
            }
            //如果序列是有序的，则只会出现#1和#3的情况，即都是顺序。
            //如果前面是正序，且tar落入前面
            if(nums[mid] > nums[start] && target >= nums[start] && target <= nums[mid]){
                ans = binarySearch(start, mid-1, nums, target);
                return ans;
            }
            //如果前面是两段序，且tar落入前面
            else if(nums[mid] < nums[start] && (target >= nums[start] || target <= nums[mid])){
                end = mid;
                continue;
            }
            //如果后面是正序，且tar落入后面
            else if(nums[mid] < nums[end] && target >= nums[mid] && target <= nums[end]){
                ans = binarySearch(mid+1, end, nums, target);
                return ans;
            }
            //如果后面是两段序，且tar落入后面
            else if(nums[mid] > nums[end] && (target >= nums[mid] || target <= nums[end])){
                start = mid;
                continue;
            }
            //tar落不到任意一个区间中去
            else {
                return -1;
            }

        }
        return ans;

    }

    //二分查找只对有序序列
    public int binarySearch(int start, int end, int[] nums, int target){
        if(start > end) return -1;
        int mid = (end + start)/2;
        int ans = -1;
        if(nums[mid] == target) return mid;
        else if(nums[mid] > target){
            int tmp = binarySearch(start, mid-1, nums, target);
            if(tmp!=-1) ans = tmp;
        } else{
            int tmp = binarySearch(mid+1, end, nums, target);
            if(tmp!=-1) ans = tmp;
        }
        return ans;
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
        combination(target, current+nums[i], len, nums, ans, numbers); //要减的话这里就是current-nums[i]
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

顺时针旋转矩阵可分为两步：
1.将矩阵中关于主对角线对称的元素交换，即 num[i][j] swap num[j][i]
2.交换后的矩阵，每一行都做镜面交换：即：num[i][j] swap num[i][n-1-j]
即可完成旋转。

可以在坐标轴上，以原点为中心，画个图形试一试，要任意一个图形顺时针旋转90度
，只需作y=-x的轴对称图形，然后再做y轴的对称图形，即可得到。

在矩阵中，做y=-x对称 ：num[i][j] swap num[j][i]    ： 横纵坐标交叉相等
        做关于y轴对称：num[i][j] swap num[i][n-1-j]  ：横坐标相等，纵坐标和为n-1
        做关于x轴对称：num[i][j] swap num[n-1-i][j]   ：横坐标和为n-1，纵坐标相等
        做y=x对称：   num[i][j] swap num[n-1-j][n-1-i] ：横纵坐标交叉和为n-1

矩阵的原点都在左上角(0,0)

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
dp[i] = dp[i] or (dp[j]==true && i <= j+nums[j])  j=0到i-1

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
合并的时候，比较当前inter和解集中的最后一个inter
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
        int[] paths = new int[n];
        if(n<=2) return 1;
        paths[1] = 1;
        paths[2] = 2;
        for(int i = 3;i<=n;i++){
            paths[i] = paths[i-1]+paths[i-2];
        }
        return 
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
窗口中的字母也用一个HashMap m2维护，对应的也是字母和出现的次数，设置一个int require
它代表窗口中的有几种字母已经满足了T，
比如：T=AABC, S=ABCDBBDC
当窗口为ABC时，require=2，因为窗口中B满足，C满足，但A不满足（个数不够），
设require=0，count=T中的不同的字母个数，当require==count时，说明窗口包含了T所有的字母
当窗口增加一个字母时，m1中该字母对应的k值+1，如果此时该m1的键值大于等于m2的该键值，则require++，
相反当窗口减少一个字母时，m1中该字母对应的k值-1，如果此时m1的键值小于m2的该键值，则require--

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


        int require = 0;
        int l=0,r=0;
        HashMap<Character, Integer> m2 = new HashMap<>();

        //因为要返回的是字符串不是长度，所以用字符串
        String ans = "";
        int minWindowLen = Integer.MAX_VALUE;
        while(l<=r && r<s.length()){
            //移动右指针,需要注意的是，当r跳出时，r是在窗口外面，但此时r指向的元素却不在窗口中，这个虽然没多大关系，但要注意。
            for(; require<count && r<s.length();r++){
                char c = s.charAt(r);
                m2.put(c, m2.getOrDefault(c, 0)+1);
                if(m2.get(c).equals(m1.getOrDefault(c, 0)))
                    require++;
            }

            //移动左指针
            for(;require==count && l<r;l++){
                int curLen = r-l;
                if(minWindowLen > curLen){
                    minWindowLen = curLen;
                    ans = s.substring(l,r);
                }

                char c = s.charAt(l);
                m2.put(c, m2.get(c)-1);
                if(m2.get(c) < m1.getOrDefault(c, 0))
                    require--;
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

还是回退迭代

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


又是一道遍历匹配的题，但这个题是有顺序的，不用双Map法，可以直接用DFS，但要注意的是，在一个顺序中搜完一个元素后，要把它标记为
未搜索，因为它再一个顺序中不满足不代表在另一个顺序中也不满足。
这里的难点就在于标定已经看过的元素

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

因此，如果对于任何i坐标，我们知道他在右边和左边的最高（或相同高度）邻居，我们可以很容易地找到最大的矩形：

对于i，l是i左边离i第一个的比它低的矩形坐标，r是i右边第一个比它低的矩形坐标

int maxArea = 0;
for（int i = 0; i <height.length; i ++）{
    maxArea = Math.max（maxArea，height [i] *（lessFromRight [i]  -  lessFromLeft [i]  -  1））;
}
lessFromLeft[i] 表示i左边比height[i]的高(或等)的最近的坐标
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
而对于lessFromRight，要从后往前遍历来找

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

设cur_left是第i行第j各元素往左数最左边1的位置；
left[i][j]是第i行第j个元素所在的最高矩形的左边界位置
设cur_right是第i行第j各元素往右数第最后一个1的位置；
right[i][j]是第i行第j个元素所在的最高矩形的右边界位置

left[i][j] = max(left[i-1][j], cur_left)
right[i][j] = min(right[i-1][j], cur_right)


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


因此，问题是：两棵树何时相互镜像？

如果出现以下情况，两棵树是彼此的镜像反射：

1. 他们的两个根的值相同。
2. 每棵树的右子树是另一棵树的左子树的镜像反射。

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    boolean isMirror(TreeNode left, TreeNode right){
        if(left==null && right== null) return true;
        if(left==null || right == null) return false;
        return (left.val == right.val) && isMirror(left.right, right.left) && isMirror(left.left, right.right);
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

展平二叉树，按照前序遍历，而且是就地完成，即空间复杂度必须是O(1)
其实很简单，再加一个指针pre保存上一个遍历过的元素即可，借助栈，对每一个元素都是先入右再入左
遍历到一个元素，把它作为pre的右孩子，（pre原来的右孩子在栈中，不会丢失），再入栈它自己的右左孩子，
把pre置为它，再把它的左右孩子都置null


```java
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


考虑动态规划，dp[i]表示s的前i个字符是否能匹配

检查s[j...i], 如果dp[j-1]匹配，且字典中含有s[j...i]，则dp[i]匹配 对于每一个i，j要试从0到i，只要有一个满足
那dp[i]就匹配

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



