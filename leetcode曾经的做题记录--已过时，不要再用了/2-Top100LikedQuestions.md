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

时间复杂度为O(n)（两个序列合并）* O(lgn) （排列子序列）

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

一直记录最小值min，每当pop一个值，如果pop的值大于min，min不变，否则遍历所有值，找到最小值（用一个栈和一个map，一栈作为主栈，map记录当前所有元素及其个数，当出栈时，如果出栈的是最小值而且再没有这个最小值了，再遍历map，找出现存的最小值

```java
class MinStack {
        int minValue;
        Stack<Integer> mainStk;
        HashMap<Integer, Integer> map;
        /** initialize your data structure here. */
        public MinStack() {
            minValue = Integer.MAX_VALUE;
            mainStk = new Stack<>();
            map = new HashMap<>();
        }

        public void push(int x) {
            minValue = minValue > x?x:minValue;
            mainStk.push(x);
            map.put(x, map.getOrDefault(x, 0)+1);
        }

        public void pop() {
            int top = mainStk.pop();
            //每当有元素出栈且该元素为最小值时，重新更新最小值
            map.put(top, map.get(top)-1);
            if(top == minValue && map.get(top)==0) {
                minValue = Integer.MAX_VALUE;
                Set<Map.Entry<Integer, Integer>> set = map.entrySet();
                for(Map.Entry<Integer, Integer> ele: set){
                    if(ele.getKey() < minValue && ele.getValue()>0){
                        minValue = ele.getKey();
                    }
                }
            }
        }

        public int top() {
            return mainStk.peek();
        }

        public int getMin() {
            return minValue;
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
此时把p2定到长链表的头部，当p1走到末尾时，p2走过的路程是x-y，再把p1定到短链表的开头，那么此时正好x和y距离链表尾是相同的距离，再让它们一步一走直到相遇
即是第一个公共节点



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

用动态规划，dp[i]表示算上nums[i]能达到的最大的和
pre[i]表示第i个数前面的能达到的最大的和

dp[i] = max(pre[i-1]+nums[i], dp[i-2]+nums[i]);
pre[i] = max(dp[i-2], dp[i-1])  //按道理应该是遍历dp从0到i-1，但因为没有负数，最大的一定是这两个其中之一

pre[0]= 0;
pre[1]=nums[0];

dp[0]=nums[0];
dp[1]=nums[1];



```java
class Solution {
    public int rob(int[] nums) {
        if(nums.length==0) return 0;
        else if(nums.length==1) return nums[0];
        else if(nums.length==2) return nums[0]>nums[1]?nums[0]:nums[1];

        int[] pre = new int[nums.length];
        int[] dp = new int[nums.length];
        pre[0]= 0;
        pre[1]=nums[0];

        dp[0]=nums[0];
        dp[1]=nums[1];
        for(int i=0;i<nums.length;i++){
            dp[i] = Math.max(pre[i-1]+nums[i], dp[i-2]+nums[i]);
            pre[i] = Math.max(dp[i-2], dp[i-1])
        }

        int max = 0;
        for(int i=0;i<nums.length;i++){
            max = Math.max(max, dp[i]);
        }
        return max;
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

如果nums[i][j]=0,则dp[i][j]=0;
如果nums[i][j]=1
则：dp[i][j] = 1 或 dp[i-1][j-1]+1
如果
leftOne[i][j] 表示nums[i][j]从右往左数有几个连着的1（算自己）
topOne[i][j] 表示nums[i][j]从下往上数有几个连着的1（算自己）

当nums[i][j]=0,则leftOne[i][j]=0; topOne[i][j]=0;
当nums[i][j]=1，则leftOne[i][j]=leftOne[i][j-1]+1; topOne[i][j]=topOne[i-1][j]+1;


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
                        //这里的逻辑看上面的解释
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

```java
class Solution {
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

维护一个本节点与其祖先的HashMap<TreeNode, TreeNode> map，k是本节点，v是其祖先节点，先用层序遍历把这个map给做好
过程中把q和p的层高找到,追溯祖先让层高相同，然后两个一起追溯，直到节点相同，相同的就是公共祖先

时间复杂度O(n),空间复杂度O(n)

```java
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
        //第一遍循环，result[i]再乘以nums[i]后面数的乘积
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

dp[i] = min(dp[i-j^2]+1) //j^2 <= i

其实这个才是动态规划的要义所在，即，当i的表示中有 j*j时且此时是表示个数最少，
那么余下的数就是 i-j*j,再算余下的数最少表示的个数

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

当遇到不是0的数，放入noZeroPos的位置，noZeroPos++，遇到0则记录0出现的个数
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
，而且只可能有一个环，因为只有一个元素重复，代表如果有不同节点的next相同，那么next只有一种取值。

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

这里理解成将二叉树转成中序序列和前序序列的合集字符串，然后将这个合集再转回二叉树

节点的值可以是重复的，而且可以是负数，使用层序遍历来构建字符串

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

            //构建二叉树，维护一个父亲节点，父亲节点不能是null，当前节点不是null时，则必须等到它的左右儿子都添加后，
            int parent = 0;
            boolean isLeft = true;
            for (int i = 1; i < nodeList.length; i++) {

                if(isLeft){
                    nodeList[parent].left = nodeList[i];
                    isLeft = false;
                }else {
                    nodeList[parent++].right = nodeList[i];
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

删除无用的括号

回退递归，对一个括号的策略只有要或者不要，这样可以使用回退递归（BackTracking）来将所有情况列出，
对于不是括号的字符，直接加入并继续递归，当递归到最后时，对最后的字符串进行正确性判别，如果左括号不等于右括号则无效，
如果相等，则再次进行一遍有效性判别（括号匹配）

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

买卖股票，卖了后的一天不能立即买，要冻结一天

还是可以考虑一下回退递归，对于一个股票，可以有买，卖，啥也不干的做法，记录上一个买的价格，如果当前价格高于买入的价格并且已经买了，才能卖
回退递归太慢了，不能接受。

https://www.cnblogs.com/jdneo/p/5228004.html

这道题可以用动态规划的思路解决。但是一开始想的时候总是抽象不出状态转移方程来，之后看到了一种用状态机的思路，
觉得很清晰，特此拿来分享，先看如下状态转移图：

S0 代表没有买入的等待状态，s0可以由s2和s0得来
S1 代表买入后等待卖出的状态，s1可以由s1和s0得来
S2 代表卖出后的状态（冻结状态）（s2只能由s1得来，且s2只能转到s0）

S2与S0的区别是：因为题目要求卖出后必须cooldown一轮，所以卖出进入S2后，必须再进入S0这个等待买入的状态，这一状态转换代表cooldown一轮

这里我们把状态分成了三个，根据每个状态的指向，我们可以得出下面的状态转移方程：

s0[i] = max(s0[i-1], s2[i-1])
s1[i] = max(s1[i-1], s0[i-1] - price[i])
s2[i] = s1[i-1] + price[i]
这样就清晰了很多。
sx[i]表示第i天保持x状态的最大收益

最终的结果状态可能是s0，也可能是s2，但不会是s1

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
现在这个分治递归区间里,只存在三个元素即start-1,i,end+1,因为在[start-1,i-1],[i+1,end+1]这两个区间已经提前被扎爆了
这个就是整个这个解法的最关键的部分,我们不需要考虑扎爆的先后顺序,不然如果普通的分治法,这个问题会变得极为复杂而且难以证明正确性
所以说整个区间可以被分解成如下部分
start - 1, maxCoin(start, i - 1), i, maxCoins(i + 1, end), end + 1

```java
class Solution {
    public int maxCoins(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];
        return maxCoins(0,nums.length-1, nums, dp);
    }

    public int maxCoins(int start, int end, int[] nums, int[][] dp){
        if(start > end){
            return 0;
        }

        //如果已经算出一个区间的值，那么不用再重复计算了，因为不管算多少次，对于一个区间，计算该区间时，它前面的和后面的一定还没扎
        if(dp[start][end]!=0) return dp[start][end];


        int max = 0;
        for(int i=start, i<=end;i++){
            int val = maxCoins(start, i-1. nums, dp)+
                      get(start-1, nums) * get(i, nums) * get(end+1, nums)
                      +maxCoins(i+1, end, dp);
            max = Math.max(val, max);
        }
        dp[start][end] = max;
        return max;
    }

    int get(int i, int[] nums){
        if(i<0 || i >= nums.length) return 1;
        return nums[i];
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

不同的面值组合成一个和，能的话输出最少纸币数，不能输出-1

这个题不能从大到小依次试，因为最大的可能不用（如果面值是从1到n，则可以这么做）

考虑对于和的表示，其中最大的值是i（可能不是面值中最大的值）

如果用回退递归，可以解决问题，但是复杂度太高，太慢了

动态规划：

自顶向下的动态规划：
F(s)表示总额为s时用的最少的张数
F(s) = F(s-c)+1  c是最大面额

F(S)=min F(S−ci)+1  i=0...n−1
subject to S−ci≥0

F(S) = 0 , when S = 0
F(S)=−1,   when n=0

自底向上的动态规划
F(i)是 最小的 F(i−cj)+1   j=0…n−1 
由于是自底向下，当i−cj大于i时直接跳过

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




给定非负整数num。对于0≤i≤n范围内的每个数字i，计算其二进制表示中的1的数量并将它们作为数组返回。
简单的做法就是对于每个数转成2进制，算其中1的个数
整数转2进制：Integer.toBinaryString(num);（如果要自己实现的话，可以每次都右移一位和1做与操作）


对于任何一个数，只要它乘以2，就相当于它整体左移一位，右边补0，所以对于任何偶数x，1的个数=（x/2）的1的个数
对于奇数，它 = 比它小一的偶数 + 1，而比它小一的偶数最低位一定是0，所以对于任何奇数x，1的个数 = (x-1)的1的个数 + 1


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

如果是数字，加入数字栈
如果是字母，直接加到栈顶
如果是[,栈顶加一个""
如果是]，栈顶的出栈，做倍数，然后加到栈顶

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

把原数组排序，按照h优先，h从大到小，k从小到大的顺序：
[[7,0],[7,1],[6,1],[5,0],[5,2],[4,4]]然后按照k当做位置，直接往List里面插即可

其实每次插入直接插到原始队列的第k个位置即可

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
判断最后一个数，如果等于和的一半，则成立，如果大于和的一半，则不成立，而且这个不能用回退递归，太慢了


1，2，3，3，4, 5：

这个问题基本上是让我们找出集合中是否有多个能够求和到特定值的数字（在这个问题中，值是sum / 2）。

实际上，这是一个0/1背包问题，对于每个数字，我们可以选择与否。 让我们假设dp[i][j]表示是否可以从前i个数中得到特定的和j。 如果我们可以从0-i中选择这样一系列数字，其总和为j，则dp [i] [j]为真，否则为假。

基本情况：dp [0][0]为真; （零数由0和0组成）

转换函数：对于每个数字，如果我们选择不使用它，dp [i] [j] = dp [i-1] [j]，这意味着如果第一个i-1元素已经使它成为j，dp [i][j]也会把它变成j（我们可以忽略不要nums[i-1]）。 如果我们选择使用nums[i]。 dp [i][j] = dp [i-1][j-nums [i-1]]，表示j由当前值nums [i-1]组成，剩余部分由其他先前数字组成。 因此，转移函数是dp [i][j] = dp [i-1][j] || dp [i-1] [j-nums[i-1]] (第i个数是nums[i-1])


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

关键在于往下传的时候减去它自己的值

用DFS

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



这个题要是不考虑O(1)的空间复杂度就很简单，用HashMap即可，不用O(n)的时间复杂度，排序即可，困难在于如何把这两种需求结合起来


基本思想是我们遍历整个数组，当见过一个数时，就把它作为索引(由于是1开始，所以要-1)的数标记为负数
如对于4，代表第四个数，索引为4-1=3：nums[4-1] = -nums[4-1]：-7，一圈标记下来，为：[-4,-3,-2,-7,8,2,-3,-1]
通过这种方式，我们看到的出现过的索引所代表的数都将标记为负数。 
在第二次迭代中，如果某个值未标记为负数，则表示我们之前从未见过该索引，因此只需将其添加到返回列表中即可。

数组长度为n，数组中的数字小于等于n，大于等于1，当遍历到了nums[i]=x时，令nums[x]为负数，表示x这个数出现过
当设负一遍后，所有出现过的值作为索引的对应数字都是负数，意为只要有i，那么nums[i]就是负数
那么没有i的话，nums[i]就是正数

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

那么对于以root为根的树，它的直径就是对所有的节点取  getLongestPath(root.left) + getLongestPath(root.right);，其中的最大值就是答案

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
用HashMap来保存出现过的sum[0,j]



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


直接将序列弄一个排好的，然后用两个指针一个从前往后一个从后往前，当遇到不一样的数字时指针停止走动，最后算两个指针的差即可

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
每项任务都可以在一个时间间隔内完成。 对于每个间隔，CPU可以完成一个任务或只是空闲。

但是，有一个非负的冷却间隔n意味着在两个相同的任务之间，必须至少有n个间隔，CPU正在执行不同的任务或只是空闲。

您需要返回CPU将完成所有给定任务所需的最少间隔数。

也就是说，上面A和A之间必须有两个以上间隔

加入对[A,A,A,A,B,C,C,C]
将任务排序，
A,A,A,A
C,C,C
B

每次取出当前任务数最多的1个任务t

每次取n个除了最大的所有的不重复的任务，插入到t的后面，
然后每次都是取出，插入，当不够取时，插入idle, 直到最后一个最大插入
要注意的是，最多元素每次插完就会改变，如下：

每次都取出当前任务数最多的1个，不够就用interval补齐（除了最后）
取完一组就对任务数组重新排序，
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


找出回文子串的个数, 要查询的子串个数用两层循环来标定,这样做会比较慢（但能过）


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

就是对于每一个值，它后面的第几个数是比它大的

二重循环很简单，但肯定不这么弄

还是用栈来做，当遍历到一个值时，如果它比栈顶元素所索引的元素大，那栈顶元素所索引的答案就是该值-栈顶元素所索引
然后出栈，直到栈顶元素不比该值小，然后该值入栈，继续遍历

## 这里核心是入栈的是索引

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






























