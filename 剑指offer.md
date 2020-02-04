看到：2周目看完


t1 p39
```java
class Solution{
    List<Integer> getDup(int[] nums){

        ArrayList<Integer> array = new ArrayList<>();
        if(nums==null) return array;
        for(int i=0;i<nums.length;i++){

            while(nums[i]!=i){
                //比如 nums[0]=2 则应该把这个2放到下标为2的位置处，然后把原来下标为2的数放到0处，继续比较，直到下标与数字相等
                if(nums[i]==nums[nums[i]]){
                	//如果这个重复的数在答案中没有，则添加，否则直接退出
                    if(!array.contains(nums[i])) array.add(nums[i]);
                    break;
                }
                int tmp = nums[nums[i]];
                nums[nums[i]] = nums[i];
                nums[i] = tmp;
            }
        }
        return array;
    }
}
```

值为x的数应该要放在下标为x的位置，但若此时下标不为x，但下标为x的地方已经有一个值为x的数，说明有重复

t2 p41


该方法的核心是，数字范围为1-n，而数组大小为n+1时。当 l-m的范围内的数字个数大于 m-l+1，则说明该范围的数字有重复的
这种算法不保证找出所有重复数字，但不一定是哪个数字

```java
class Solution{
    int getOneDup(int[] nums){
    	if(nums==null || nums.length==0) return -1;
        int len = nums.length;

        int low = 1;
        int high = len-1; //len-1为n

        while(low <= high){
            int mid = (low+high)>>1;
            int count = countRange(low, mid, nums);
            if(low==high){
                if(count>1) return low;
                else break;
            }

            /*
            对于这里的二分查找，有必要解释为什么low=mid+1而不是low=mid
            因为计算机都是向下取整的，当low和high差1时，mid==low，如果目标不在low-mid之中，则要low增大，而如果增大至mid会没变化，导致死循环
            如果目标在low-mid中，要减小high，此时因为差1，所以high可以顺利减小
            */
            if(count > (mid-low+1)){
                high = mid;
            } else{

                low = mid+1;
            }
        }
        //没有重复的则返回-1；
        return -1;
    }

    int countRange(int low, int high, int[] nums){
        int sum = 0;
        for(int i: nums){
            if(i>=low && i<=high) sum++;
        }
        return sum;
    }
}
```

二维数组中的查找

面试题4 p44

二维数组，从上到下和从左到右都是顺序，查找某个值

```java
class Solution{
    public boolean Find(int target, int [][] num) {
        if(num==null || num[0].length==0) return false;
        int row = num.length;
        int col = num[0].length;

        int x = 0;
        int y = col-1;
        while(num[x][y]!=target){

            if(num[x][y]==target){
                return true;
            }else if(num[x][y]>target){
                y--;
            }else{
                x++;
            }

            if(x>=row || y<0){
                return false;
            }
        }

        return true;
    }
}
```

字符串

字面量创造的字符串（不用new），就在字符串常量区，用new的就在堆

```java
String s = new String("abc");
String s1 = new String("abc");

String s2 = "abc";
String s3 = "abc";

System.out.println(s==s1);  //false，在堆中创建不同的对象
System.out.println(s==s2);  //false，堆和常量区两个不同的对象
System.out.println(s2==s3); //true，都指向了字符串常量区的同一块区域

```


面试题5  替换空格 p51

```java
public class Solution {
    public String replaceSpace(StringBuffer str) {
        StringBuilder builder = new StringBuilder(str);
        return builder.toString().replace(" ", "%20");
    }
}
```
这个题对于java来说，换成替换数组中的值更合适，把一个值替换成多个值。如 [1,2,3,4,2,5] 把2替换为7，8  结果为 [1,7,8,3,4,7,8,5]

    还是要新开一个数组，先遍历一遍得到要替换的个数，可知新开的数组大小，然后遍历即可，当遍历到原数组的2时，新数组放入7，8；遍历到其他数时，拷贝该值到新数组，复杂度是tO(n)+sO(n)


合并两个有序数组
p55 有两个排序的数组a1和a2，a1的末尾有足够多的空间容纳a2，实现一个函数，把a2所有数字插入a1中，并且所有数字是排序的

空间复杂度低的办法：把a2放进a1后面，然后对a1进行排序（快排，冒泡都可）
时间复杂度低的办法：把a1拷贝一份出来为a1c，用并归排序的办法，设置两个指针分别指向a1c和a2，并归进a1中
时间和空间复杂度都低的办法：从尾到头比较a1与a2的数字，并把较大的数字复制进a1的合适位置（尾部往前,索引从len1+len2-1开始，逐渐减小）

```java
public class Solution {
    public void combine(int[] num1, int[] num2, int len1, int len2) {
        if(num1==null || num2==null || num1.length<len1+len2)return;
        int p1 = len1-1;
        int p2 = len2-1;

        int pp = len1+len2-1;
        while(p1>=0 || p2>=0){
            if(p1<0 || p2<0){
                if(p1<0){
                    num1[pp--] = num2[p2--];
                }else{
                    num1[pp--] = num1[p1--];
                }
            }else if(num1[p1]>num2[p2]){
                num1[pp--] = num1[p1--];
            }else{
                num1[pp--] = num2[p2--];
            }
        }
    }
}
```

链表

面试题6 p58
给一个链表的头结点，从尾到头打印链表, 由于使用O(n)空间复杂度的办法很容易，用栈，用数组保存数字都可以，所以这里直接用
O(1)的就地翻转链表

```java
public class Solution {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> ans = new ArrayList<>();
        ListNode t = reverse(listNode);
        ListNode cur = head;
        while(cur!=null){
            ans.add(cur.val);
            cur = cur.next;
            len++;
        }
        return ans;
    }

    //对于链表的翻转，一定要单独处理长度为0(null)，1，2时的情景，（因为一般做法中有3个节点）
    private ListNode reverse(ListNode head){
        if(head==null) return null;
        int len = 0;
        int last = null;
        ListNode cur = head;
        while(cur!=null){
            if(cur.next == null) last = cur;
            cur = cur.next;
            len++;
        }
        if(len==1) return head;
        if(len==2){
            listNode tmp = head.next;
            head.next = null;
            tmp.next = head;
            return tmp;
        }

        ListNode n1 = head;
        ListNode n2 = head.next;
        ListNode n3 = head.next.next;

        while(n3!=last){
            n2.next = n1;
            n1 = n2;
            n2 = n3;
            n3 = n3.next;
        }
        n3.next = n2;
        n2.next = n1;
        head.next = null;
        return n3;
    }
}
```

树

二叉树的前中后序遍历：

## 需要记住，前序只有一个循环，里面分别是check值，加右孩子，加左孩子
## 中序也只有一个循环，里面有if-else判断node是否为空，不空就继续挪到左孩子，空就check栈中值
## 后序是一个大循环里面有个小循环+if-else判断，有一个pre节点保存上一个节点。用tmp节点保存当前栈顶节点的右孩子
## 层序的话，如果只需要一个序列，则用一个队列，如果需要表明层级，则需要两个队列

```java
class Solution {
    //中序遍历
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


    //双队列层序遍历
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

面试题7 重建二叉树：p62

当然这个里面不能有值相同的节点

```java
public class Solution {
    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        if(pre==null || in==null || pre.length==0 || in.length==0) return null;

        return reConstructBinaryTree(pre, 0, pre.length-1, in, 0, in.length-1);
    }

    private TreeNode reConstructBinaryTree(int [] pre,int preStart, int preEnd, int [] in, int inStart, int inEnd){
        if(pre==null || in==null || pre.length==0 || in.length==0 || preStart>preEnd || inStart>inEnd) return null;
        TreeNode root = new TreeNode(pre[0]);
        int rootInIndex = 0;
        for(int i=inStart;i<=inEnd;i++){
            if(in[i]==root.val){
                rootInIndex = i;
                break;
            }
        }
        int leftChildCount = rootInIndex - inStart;  //左子树上所有节点个数
        int rightChildCount = inEnd - rootInIndex;//右子树所有节点个数
        root.left = reConstructBinaryTree(pre, preStart+1, preStart+leftChildCount, in, inStart, rootInIndex-1);
        root.right = reConstructBinaryTree(pre, preEnd-rightChildCount+1, preEnd, in, rootInIndex+1, inEnd);
        return root;

    } 

}
```

面试题8 二叉树的中序遍历的下一个节点 j8-p65

其实直接用中序遍历也可

```java
public class Solution {

    public TreeNode findNextNode(TreeNode root, TreeNode cur) {
        //右子树不为空，直接从右子树里找
        if(cur.right!=null){
            TreeNode tmp = cur.right;
            while(tmp.left!=null){
                tmp = tmp.left;
            }
            return tmp;
        }
        //否则
        else if(cur.parent!=null && cur.parent.left==cur)
            return cur.parent;
        else{
            TreeNode tmp = cur;
            while(tmp!=null){
                if(tmp.parent!=null && tmp.parent.left==tmp) {
                    tmp = tmp.next;
                    break;
                }
                tmp = tmp.parent;
            }
            return tmp; //如果最终没有，那么也返回的是null
        }
    }



}
```

面试题9 用两个栈实现队列  p68

用两个栈，一个用来入队，一个用来出队，入队时，压栈stk1；出队时，如果stk2不空，则从stk2弹栈，否则先把stk1中的元素倒入stk2中在从stk2弹栈

```java
import java.util.Stack;

public class Solution {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();
    
    public void push(int node) {
        stack1.push(node);
        
    }
    
    public int pop() {
        if(!stack2.isEmpty()) return stack2.pop();

        while(!stack1.isEmpty()){
            stack2.push(stack1.pop());
        }
        return stack2.pop();
    }
}
```

p71，用两个队列实现一个栈

队列q1和队列q2，压栈时，往不空的队列中入队，（若都空，则默认往q1中入队）。弹栈时，出队不空的队列中的元素，每出队一个元素，用tmp暂存，如果此时队列还不为空，则把它放入另一个队列，继续出队，直到出队一个元素后队空，则返回该元素，且该元素不入另一个队列

```java
public class Solution {
    LinkedList<Integer> queue1 = new LinkedList<Integer>();
    LinkedList<Integer> queue2 = new LinkedList<Integer>();
    
    public void push(int node) {
        if(queue2.isEmpty()){
            queue1.offer(node);
        }else{
            queue2.offer(node);
        }       
    }
    
    public int pop() {
        LinkedList<Integer> cur = !queue1.isEmpty()?queue1:queue2;
        LinkedList<Integer> another = queue1.isEmpty()?queue1:queue2;
        int ans = 0;
        while(!cur.isEmpty()){
            int tmp = cur.poll();
            if(cur.isEmpty()){
                ans = tmp;
                break;
            }
            another.offer(tmp);
        }
        return ans;
    }
}
```

# 算法和数据操作

## 应该重点掌握二分查找，归并排序和快速排序，做到能随时正确，完整地写出代码

## 如果面试题要求在二维数组（可能具体表现为迷宫或者棋盘等）上搜索路径，那么可以尝试回溯法，通常适合使用回退递归，当不能用递归时，考虑用栈来模拟递归的过程

## 如果是球某个问题的最优解，且该问题可以分为多个子问题，则可以尝试动态规划，在用自上而下的递归思路分析dp问题时，会发现子问题直接存在重叠的更小子问题，为了避免重复计算，用自下而上的循环代码来实现，也就是把子问题的最优解先算出来并用数组（一般是一维或二维数组）保存下来，接来下基于子问题的解计算大问题的解

## 如果分解子问题存在某个特殊的选择，若使用这个特殊选择将一定能得到最优解，那么可能意味着该题可能适用于贪婪算法

## 位运算对于数字操作非常重要



面试题10 斐波那契数列 j10-p74

典型的递归或者动态规划

动态规划
```java
public class Solution {
    public int Fibonacci(int n) {
        int[] dp = new int[n+1];

        dp[0] = 0;
        dp[1] = 1;
        for(int i=2;i<n+1;i++){
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[n];
    }
}
```

青蛙跳台阶问题：也很典型 dp[i]是到达第i层台阶的办法，dp[i]=dp[i]+dp[i-2]:  leetcode 70

》跳台阶
一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
```java
public class Solution {
    public int JumpFloor(int n) {
        if(n<=0) return 0;
        else if(n==1) return 1;
        else if(n==2) return 2;
        int[] paths = new int[n+1];
        paths[1] = 1;
        paths[2] = 2;
        for(int i=3;i<n+1;i++){
            paths[i] = (paths[i-1]+paths[i-2]);
        }
        return paths[n];
    }
}
```

》变态跳台阶
一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级,求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。

倒数第二个台阶是第x级台阶
paths[n]= Σpaths[x]+1  (x从1到n-1，这个单独的1是直接跳到目标台阶)

1，2，4，8，16...

```java
import 
public class Solution {
    public int JumpFloorII(int n) {
        return (int)Math.pow(2, n-1);
    }
}
```




我们可以用2x1的小矩形横着或者竖着去覆盖更大的矩形，请问用8个2x1的小矩形无重复的覆盖2x8的大矩形总共有多少种方法？ j-p79

先把2x8的覆盖方法记为f(8)，用最后一个2x1的小矩形去覆盖大矩形的最左边有两种选择：竖着放或者横着放。当竖着放时，右边还剩2x7的区域一定是满的，这种情形下的覆盖方法记为f(7)，接下来考虑横放的情况，当2x1的小矩形横着放在左上角时，左下角必须也横着放一个2x1的小矩形，而在右边还剩2x6的区域一定是满的，这种情形下覆盖方法记为f(6)，因此f(8)=f(6)+f(7)。此时可以看出，仍然是斐波那契数列


查找和排序

必须能信手拈来写出完整的二分查找代码

tips：如果面试题要求在排序的数组（或者部分排序的数组）中查找一个数字或者统计某个数字出现的次数，都可以尝试二分查找


面试题11 旋转数组的最小数字 j11-p82

把一个排序的数组最开始的若干个元素搬到数组末尾，找出这个数组中的最小值

把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。

也就是循环移位，当然仍用二分查找，low-mid-high中，一段是有序，一段是两段序，因为挪动最少也是1个元素，所以最小值一定在两段序中


```java
public class Solution {
    public int minNumberInRotateArray(int [] array) {
        if(array==null || array.length==0) return 0;
        if(array.length == 1) return array[0];
        int low = 0;
        int high = array.length-1;
        int mid =(low+high)/2;

        //没有挪动过
        if(array[low]<array[high]) return array[low];


        //只要挪动过，最小值一定不在数组边界
        while(low<high){
            mid =(low+high)/2;
            if(array[mid]>array[high]){ //后面是两段序
                low = mid+1;
            } 
            else if(array[mid]<array[high]){ //前面是两段序
                high = mid;
            }else{ //array[mid==array[high] 这种情况不能挪动low，只能往前挪high
                high = high-1;
            }
        }
        return array[low];
    }
}
```

回溯法

非常适合由多个步骤组成的问题，并且每个步骤都有多个选项。当我们在某一步选择了其中一个选项时，就进入下一步，然后又面临新的选项。我们就这样重复选择，直至到达最终的状态

面试题12：矩阵中的路径 j12-p89，和leetcode79一样,只不过79是

可以直接用DFS，将目标单词中字母顺序与遍历序列中的字母匹配，符合就继续匹配该字母周围的字母与单词的下一个字母。否则就退出。这里最好用递归的dfs（trackback），当遍历完一个位置时，将其标记为已看，然后继续dfs它的周围8个方向的位置，当把它周围的元素后续全部遍历完后，要将其标记为未看，因为它在一个顺序中不满足不代表在另一个顺序中也不满足。
    这里的难点就在于标定已经看过的元素，4个方向的dfs递归，以及遍历过后标为未查看


面试题13：机器人的运动范围  j13-p92

地上有一个m行n列的方格，一个机器人从坐标(0,0)的格子开始移动，它每次可以向上下左右移动一格，但不能进入行坐标和列坐标的数位之和大于k的格子，如当k=18时，
它能进入(35,37)，因为3+3+5+7=18，但不能进入(35,38),因为3+5+3+8=19，请问机器人能到达多少个格子

用一个boolean二维数组保存某个位置是否已到达过，用回退递归去到达位置，当递归到一个位置时，如果该位置还未到达过，且该位置坐标有效，则到达，计数+1，标定该位置已到达过，然后回溯它周围的四个位置

```java
class Solution{

    int K = 0;
    int ans = 0;
    public int movingCount(int k, int m, int n){
        //到达一个位置后，将其设为true
        boolean[][] arrived = new boolean[m][n];
        K = k;
        trackBack(m,n,0,0,arrived);
        return ans;
    }

    void trackBack(int m, int n, int curX, int curY, boolean[][] arrived){
        if(curX<0 || curX>=m || curY<0 || curY>=n)
            return;

        //如果该位置已经到达过了，就不用再管它了
        //如果该位置的坐标不满足条件，也直接不管了
        if(isPosValid(curX, curY) && !arrived[curX][curY]){
            arrived[curX][curY] = true;
            ans++;
            trackBack(m,n,curX-1, curY,arrived);
            trackBack(m,n,curX+1, curY,arrived);
            trackBack(m,n,curX, curY-1,arrived);
            trackBack(m,n,curX, curY+1,arrived);
        }
    }

    //判断俩数的数位之和是否小等于K
    boolean isPosValid(int x, int y){
        int sumx=0, sumy=0;
        while(x>0){
            sumx+=x%10;
            x=x/10;
        }
        while(y>0){
            sumy+=y%10;
            y=y/10;
        }
        return !(sumx+sumy>K);
    }
}
```


面试题14 剪绳子 j14-p96

至少要剪1刀

//tackback：数字一大及其慢，不能用

//动态规划，对长为n的绳子来说，剪一刀就变成了长为 i 和 n-i 的两根绳子(只需考虑第一根绳子长度)

设dp[i]是剪长度为i的绳子的最大乘积，j是剪一刀后第一段绳子的长度，则i-j是第二段绳子长度，这两部分都可以剪或不剪，因此有4种可能的组合
dp[i] = max(dp[j] * dp[i-j], j* (i-j), dp[j]* (i-j), j* dp[i-j]);
但对于剪后的两段绳子 j和 i-j来说，都有可能存在不剪的情况，如对于i=5来说，剪一刀即可，即2和3，但对2和3不能再剪
(书上的做法是，长度为3以上的都要剪成长度为1or2or3的小段,证明见书p98)
```java
class Solution{
    public int cutRope(int n){
        int[] dp = new int[n+1];
        dp[1] = 1;
        dp[2] = 1;
        for(int i=3;i<n+1;i++){
            for(int j=1;j<=i/2;j++){
                //第一段和第二段都继续剪
                dp[i] = Math.max(dp[j] * dp[i-j], dp[i]);
                //第一段和第二段都不剪
                dp[i] = Math.max(dp[i], j*(i-j));
                //第一段剪第二段不减
                dp[i] = Math.max(dp[i], dp[j]*(i-j));
                //第一段不剪第二段剪
                dp[i] = Math.max(dp[i], j*dp[i-j]);
            }
        }
        return dp[n];
    }
}
```

//贪婪算法：当n>=5时，尽可能多剪长度为3的绳子，当剩下的绳子长度为4时，把绳子剪成两段长度为2的绳子，这种思路参考代码如下：
```java
class Solution{
    public int cutRope3(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 1;
        if (n == 3) return 2;

        int ans = 1;
        int rest = n;
        while (rest >= 5) {
            ans *= 3;
            rest -= 3;
        }
        if (rest == 4) ans *= 4;
        else if (rest <= 3 && rest > 0) {
            ans *= rest;

        }
        return ans;
    }
}
```

p99
用A表示1列，B表示2列.。。。Z表示26列，AA表示27列，AB表示28列.。。。以次类推，编写一个函数，输入用字母表示的列号编码，输出它是第几列

```java
public int alpha2Number(String s){
    int sum = 0;
    for(int i=0;i<s.length();i++){
        int cur = s.charAt(i)-'A'+1;
        sum *=26;
        sum += cur;
    }
    return sum;
}
```

面试题15 二进制中1的个数 j15-p100

当然可以直接先把数字转成2进制字符串，然后算其中1的个数

好做法：数字不断无符号右移1位，然后和1做与，如果得1，则证明当前最后一位是1，累加，直到右移到该数字为0
       还可以不动数字，动1，如数字先和1做与，然后1左移1位和数字做与，然后再左移一位。。

更好的做法：把数字减去1，那么其二进制最右端的1会变成0，然后这个1再往右的所有0会全部变成1
          然后再喝原整数做与运算，会把该整数最右边的1变成0，那么一个整数的二进制中有多少个1，就可以进行多少次这样的操作
```java
public class Solution {
    public int NumberOf1(int n){
        int sum = 0;
        while(n!=0){
            sum += n&1;
            n = n>>>1;
        }
        return sum;
    }
}
```

》用一条语句判断一个整数是不是2的整数次方。 ：j-p103
    一个整数如果是2的整数次方，那么它的二进制表示中有且只有一位是1，，而其他位都是0，把这个整数减去1后再和它自己做与运算，这个整数中唯一的1就会变成0

》输入两个整数m和n，计算需要改变m的二进制表示中的多少位才能得到n： j-p103
    两个数求异或，则他们二进制中一样的位就会变成0，不一样的就会变成1，因此可以先求这两个数的异或，然后统计异或结果中1的位数       


## 把一个整数减去1之后再和原来的整数做位与运算，得到的结果相当于把整数的二进制表示中最右边的1变成0，很多二进制问题都可以用这种思路解决

面试题16 数值的整数次方  j16-p110

实现函数 double Power(double base, int exponent)，不得用库函数，不用考虑大数

考虑自顶向下的动态规划
//为了高效，奇偶判断也不用 %2==1or0，而使用 与1做与运算，若1为奇若0为偶

a^n  如果n是偶数，则 a^n = (a^(n/2) * a^(n/2))   //为了高效，n/2最好换成n>>1
     如果n是奇数，则 a^n = (a * a^(n/2) * a^(n/2))

终止条件是 n==0时，a^n=1

而且要用HashMap保存中间结果防止重复计算
还要注意有可能指数可能是负数，在先把它变成正数来进行DP，直到最后时取个倒数即可，（负数只有可能在最终结果，因为处理后，子问题中的n都是正数）

要考虑边界条件，基数是0且指数是负数的情况下，取倒数就出问题，所以直接在基数为0的情况下返回0（0的0次方在数学上没有意义，但要考虑这种边界）

```java
class Solution{
    HashMap<Integer, Double> map = new HashMap<>();
    public double Power(double base, int n){
        if(map.containsKey(n)){
            return map.get(n);
        }
        if(base==0) return 0;
        //记录符号
        int symbol = n<0?-1:1;
        //变成正数
        n = Math.abs(n);
        if(n==0) return 1;
        double tmp = 0;
        if((n&1)==0){
            tmp = Power(base, n>>1) * Power(base, n>>1);
        }else{
            tmp = base * Power(base, n>>1) * Power(base, n>>1);
        }
        map.put(n, tmp);
        if(symbol<0){
            tmp = 1/tmp;
        }
        return tmp;
    }

}
```

面试题17，打印从1到最大的n位数  j17-p114

输入数字n，按顺序打印出从1到最大的n位十进制数，如输入3，则打印1、2、3一直到最大的3位数999

要注意的是，可能n会非常大，要考虑大数的问题，用long long都不够，因为n可能取到的是最大整型

显然用字符串比较合理，在字符串上模拟加法就是关键，加法的进位至多只能是1，且是每次都加1，针对字符串中每一位都是一个数字，从低位到高位加(所以要反着遍历字符串)，处理好进位就问题不大

要考虑边界值，当n<=0时直接返回
如果循环条件每次都要遍历一遍字符串得出长度，效率不高。好办法是设置一个全局变量，在+1的时候从0开始顺便累加字符串长度，即累加该变量（什么意思没看懂？）
循环条件是该变量不大于n

```java
class Solution{

    int maxLen = 0;
    public void printN(int n){
        //最大的一定的
        if(n<=0) return;
        String s = "1";
        while(maxLen<=n){
            System.out.println(s);
            s = increment(s);
        }
    }

    String increment(String str){
        StringBuilder builder = new StringBuilder();
        //进位, 因为要加1，就相当于最低位的再低一位上来一个进位
        int carry = 1;

        maxLen = 0;
        for(int i=str.length()-1; i>=0; i--){
            int cur = str.charAt(i)-'0';
            if(carry==1){
                cur = cur+carry;
                if(cur>9){
                    cur = cur%10;
                    carry = 1;
                }else{
                    carry=0;
                }
            }
            maxLen++;
            builder.insert(0, ""+cur);
        }

        if(carry==1){
            maxLen ++;
            builder.insert(0, 1+"");
        }
        return builder.toString();
    }
}
```

p119 
两个大数相加
大数问题都使用字符串很方便，核心还是逐个遍历两个字符串，从低位开始往高位加，但这里要注意，由于两数不一定一样长，所以要先让两个字符串一样长，
短的在高位补0

两数相减，等于大绝对值-小绝对值，要注意符号和绝对值大的数一致，两数相减的话直接就用绝对值减，要注意做单数位的减法，要先借位（借10），减完如果还不上，就标记借位，如果还的上（差大等于10），则不标记借位
```java
class Solution{

    String twoSum(String s1, String s2) {
        if(s1.charAt(0)!='-' && s2.charAt(0)!='-'){  //两个正数

            return addTwoPositive(s1,s2);
        }else if(s1.charAt(0)=='-' && s2.charAt(0)=='-'){  //两个负数
            return "-" + addTwoPositive(s1.substring(1), s2.substring(1));
        }else{
            boolean s1N = false;
            if(s1.charAt(0)=='-'){
                s1N = true;
                s1 =s1.substring(1);
            }else
                s2 = s2.substring(1);

            int cmp = compare(s1,s2);
            if(cmp==1){
                String res = subTwo(s1, s2);
                return s1N?"-"+res:res;
            }else if(cmp==-1){
                String res = subTwo(s2, s1);
                return s1N?res:"-"+res;
            }else{
                return "0";
            }
        }
    }

    //1 s1>s2,  0 s1==s2   -1 s1<s2
    int compare(String s1, String s2){
        int len1 = s1.length();
        int len2 = s2.length();
        if(len1>len2) return 1;
        else if(len1<len2) return -1;
        else{
            //从高位开始比
            for(int i=0;i<len1;i++){
                int num1 = s1.charAt(i) - '0';
                int num2 = s2.charAt(i) - '0';
                if(num1>num2) return 1;
                else if(num1<num2) return -1;
            }
            return 0;
        }

    }

    String subTwo(String s1, String s2){
        int len1 = s1.length();
        int len2 = s2.length();
        StringBuilder sb1 = new StringBuilder(s1);
        StringBuilder sb2 = new StringBuilder(s2);

        if (len1 > len2) {
            for (int i = 0; i < len1 - len2; i++) {
                sb2.insert(0, "0");
            }
        }

        int len = len1 > len2 ? len1 : len2;
        StringBuilder sb = new StringBuilder();
        int borrow = 0;
        for (int i = len - 1; i >= 0; i--) {
            int num1 = sb1.charAt(i) - '0';
            int num2 = sb2.charAt(i) - '0';
            //先借10
            int cur = 10 + num1 - num2 - borrow;
            //如果不够还，再往上借
            if (cur < 10) {
                cur = cur % 10;
                borrow = 1;
            } else { //够还就不借了
                cur = cur % 10;
                borrow = 0;
            }
            sb.insert(0, cur + "");
        }
        return sb.toString();
    }

    String addTwoPositive(String s1, String s2){
        int len1 = s1.length();
        int len2 = s2.length();
        StringBuilder sb1 = new StringBuilder(s1);
        StringBuilder sb2 = new StringBuilder(s2);

        if (len1 > len2) {
            for (int i = 0; i < len1 - len2; i++) {
                sb2.insert(0, "0");
            }
        } else {
            for (int i = 0; i < len2 - len1; i++) {
                sb1.insert(0, "0");
            }
        }

        int len = len1 > len2 ? len1 : len2;
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for (int i = len - 1; i >= 0; i--) {
            int num1 = sb1.charAt(i) - '0';
            int num2 = sb2.charAt(i) - '0';
            int cur = num1 + num2 + carry;
            if (cur >= 10) {
                cur = cur % 10;
                carry = 1;
            } else {
                carry = 0;
            }
            sb.insert(0, cur + "");
        }
        if (carry == 1) {
            sb.insert(0, 1 + "");
        }
        return sb.toString();
    }
}    
```

面试题18  j18-p119

在O(1)的时间内删除某个节点

有点投机取巧的意味，比如要删除node，
直接把node.next的值设置给node，然后node.next = node.next.next 
其实是删除了node.next，但从值上看，删除了node

如果node是头结点，则新链表的头结点就是node的next。
如果node是尾节点，只能从头遍历，将node前面的节点的next置null了(只有这一种情况不是O(1)，但其余情况都是O(1))

j18-p122
在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5

先遍历链表，把有重复的值都放到一个HashSet中，再从头到尾遍历，如果出现set中有的值，删去该节点

如果要求空间复杂度O(1)的话，则保留一个pre，如果node.val==node.next.val,则循环将node=node.next直到其值变了为止

```java
/*
 public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}
*/
import java.util.*;
public class Solution {
    public ListNode deleteDuplication(ListNode pHead){
        if(pHead==null) return null;
        ListNode dummy = new ListNode(0);
        dummy.next = pHead;


        ListNode pre = dummy;
        ListNode node = dummy.next;
        while(node!=null){
            if(node.next!=null && node.val==node.next.val){
                int sameVal = node.val;
                while(node!=null && node.val==sameVal){
                    node = node.next;
                }
                pre.next = node;
            }else{
                pre = node;
                node = node.next;
            }

        }
        return dummy.next;
    }
}
```

面试题19：正则表达式匹配：j19-p124

题目描述
请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配



step1：
先匹配原串和匹配串的第一个字符

//如果不匹配：
    1.如果匹配串第二个是*，则继续匹配，原串=原串，匹配串=匹配串第3个字符开始的子串（跳过第1、2个字符）。返回step1
    2.如果匹配串第二个不是*，则失败
//如果匹配：
    1.如果匹配串第二个是*
        1.原串=原串第2个字符开始的子串（跳过第1个字符），匹配串=匹配串，返回step1 (即要这个匹配的字符，后面的继续匹配)
        2.原串=原串，匹配串=匹配串第3个字符开始的子串（跳过第1、2个字符），返回step1 （即看如果不要这个匹配的字符，即*前的字符取0个。后面的是否匹配） 
        如果1和2中有任意一种可匹配成功，则成功
    2.如果匹配串第二个不是*，原串和匹配串都跳过第一个字符，继续匹配，返回step1



```java
public class Solution {

    public boolean match(char[] str, char[] pattern){
        return match(new String(str), new String(pattern));
    }

    public boolean match(String s, String p){
        if (p.isEmpty()) return s.isEmpty();

        boolean firstMatch = false;
        if(s.length()>0 && (s.charAt(0)==p.charAt(0) || p.charAt(0)=='.'))
            firstMatch = true;

        if(firstMatch){
            if(p.length()>=2 && p.charAt(1)=='*'){
                return match(s.substring(1), p) || match(s, p.substring(2));
            }else{
                return match(s.substring(1), p.substring(1));
            }

        }else{
            if(p.length()>=2 && p.charAt(1)=='*'){
                return match(s, p.substring(2));
            }else{
                return false;
            }
        }
    }
}
```

面试题20 表示数值的字符串 j20-p127， 65

题目描述
请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。

表示数值的字符串遵循模式 A[.B]][e|EC]或者.B[e|EC],
其中A为数值的整数部分，B紧跟着小数点为数值的小数部分，C紧跟着‘e’或者E为数值的指数部分。另外，在小数里可能没有数值的整数部分，如.123=0.123
因此A不是必须的，如果一个数没有整数部分，那么它的小数部分不能为空
上述A和C都是可能以‘+’或者‘-’开头的0到9的字符串，B也是0到9的数位串，但前面不能有正负号

取字符串中的.号和e或E号，.的位置为pi，e或E的位置为ei

写两个函数作为辅助：判断是否为合法带符号整数（如果带符号，后面就得是合法自然数；否则整体就得是合法自然数），判断是否为合法自然数（字符串只含数字即可）

分以下几种情况：
    pi<0, ei<0 :不带小数点不带e，直接判断是否是合法带符号整数即可
    pi>=0, ei<0 :带小数点不带e
        如果小数点在第一位，判断后面是否是合法自然数
        否则 小数点前面的是否是合法带符号数 && 小数点后面的是否是合法自然数
    pi<0, ei>=0 :不带小数带e e前面是合法带符号整数 && e后面是合法带符号整数 
    pi>=0 ei>=0 :带小数点带e
        小数点一定得在e前面，否则非法
        如果小数点在第一位：(pi,ei)合法自然数数 && （ei, last]合法带符号整数
        否则 [0,pi)合法带符号整数 && (pi,ei)合法自然数数 && （ei, last]合法带符号整数



```java
public class Solution {

    public boolean isNumeric(char[] str) {
        return isNumeric(String.valueOf(str));
    }

    public boolean isNumeric(String s) {
        int pointIndex = s.indexOf('.');

        int eIndex = s.indexOf('e');
        int EIndex = s.indexOf('E');
        //有两个e一定非法
        if(eIndex>=0 && EIndex>=0) return false;
        else
            eIndex = eIndex>=0?eIndex:EIndex;

        if(pointIndex<0 && eIndex>=0){
            //不带小数点带e
            return isSignedNumeric(s.substring(0, eIndex)) && isSignedNumeric(s.substring(eIndex+1));

        }else if(pointIndex>=0 && eIndex<0){
            //带小数点不带e
            //如果第一个就是小数点
            if(pointIndex==0)
                return isNatureNumeric(s.substring(1));
            else{ //如果第一个不是小数点
                return isSignedNumeric(s.substring(0, pointIndex)) && isNatureNumeric(s.substring(pointIndex+1));
            }

        }else if(pointIndex>=0 && eIndex>=0){
            //带小数点带e
            //小数点一定得在e前面
            if(pointIndex>eIndex) return false;
            //如果第一个就是小数点
            if(pointIndex==0)
                return isNatureNumeric(s.substring(1, eIndex)) && isSignedNumeric(s.substring(eIndex+1));
            else{ //如果第一个不是小数点
                return isSignedNumeric(s.substring(0, pointIndex)) && isNatureNumeric(s.substring(pointIndex+1, eIndex)) && isSignedNumeric(s.substring(eIndex+1));
            }

        }else {
            //不带小数点不带e
            return isSignedNumeric(s);
        }
    }

    //是不是合法的带符号整数
    public boolean isSignedNumeric(String s){
        if(s.length()<1) return false;
        if(s.charAt(0)=='+' || s.charAt(0)=='-'){
            return isNatureNumeric(s.substring(1));
        }else{
            return isNatureNumeric(s);
        }
    }

    //是不是合法的非负整数,只含有数字
    public boolean isNatureNumeric(String s){
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c < '0' || c>'9')
                return false;
        }
        return true;
    }
}
```

面试题21 调整数组顺序使奇数位于偶数前面  j21-p129

题目描述
输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。

先遍历，得出奇数多少个，把偶数存起来。
再遍历数组，遍历到奇数时，放入一个自增的下标中，遍历完后，再把保存的偶数放到后面
sO(n)+tO(n)
## 记得奇数二进制最低位为1，偶数二进制最低位为0

# 需要注意位运算优先级很低，要记得加括号！！

```java
import java.util.*;
public class Solution {
    public void reOrderArray(int [] array) {
        int oddCount = 0;
        ArrayList<Integer> evenList = new ArrayList<>();
        for(int i=0;i<array.length;i++){
            if((array[i]&1)==1){
                oddCount++;
            }else{
                evenList.add(array[i]);
            }
        }

        int pOdd = 0;
        for(int i=0;i<array.length;i++){
            if((array[i]&1)==1){
                array[pOdd++]=array[i];
            }
        }
        int pEven = pOdd;
        for(int i=0;i<evenList.size();i++){
            array[pEven++] = evenList.get(i);
        }
    }
}
```

面试题22  j22-p134
题目描述
输入一个链表，输出该链表中倒数第k个结点。

思想很简单，就是双指针法，p1出发k步后p2才出发，p1到达末尾时，p2到达倒数第k个

重点在于代码的鲁棒性
1.头结点判空，若为null直接返回null
2.k值的合法性，输入小于等于0的值返回null
3.记录步数的count在什么地方增加，结果才能合理

```java
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
    public ListNode FindKthToTail(ListNode head,int k) {
        if(head==null) return null;
        if(k<=0) return null;
        ListNode p1 = head;
        ListNode p2 = null;
        int count = 1;
        while(p1!=null){
            p1 = p1.next;
            if(count>=k){
                if(count==k)
                    p2=head;
                else
                    p2=p2.next;
            }
            count++;
        }
        return p2;
    }
}
```

j138
求链表的中间节点，如果链表中节点总数位奇数，则返回中间节点；如果节点总数是偶数，则返回中间两个节点的任意一个。
    定义两个指针，同时从头结点出发，一个指针一次走1步，另一个一次走两步，当走的快的到达末尾时，走得慢的正好在链表中间


## 当用一个指针遍历链表不能解决问题时，可以尝试两个指针遍历，可以让一个走的速度快一些，或者先让它在链表上走若干步

面试题23：链表中环的入口：j23-p139
题目描述
给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。

龟兔赛跑算法
p1每次跑1步，p2每次跑2步
若能相遇，则证明有环，而当一个节点为null时，证明无环
当有环时，将p2放回链表头，此时p1和p2同时开始每次走1步，直到相遇，相遇点为环的入口

设链表头到环入口处长度为l，环长度为c，环入口到两个指针第一次相遇的点距离为d

p1走的距离为 d1=l+m* c + d  m为非负整数
p2走的距离为 d2=l+ n * c +d  n为非负整数

d2=2d1  即：2l + 2m * c +2d = l +n * c + d
移项得 l+d = (n-2m) * c  即从链表头到相遇点的距离是环长的整数倍
l=(n-2m)* c-d
此时让p2放回链表头，再走l，到达环入口；而同时p1也走l长度，则p1从一开始算走过的长度为 
l+m* c + d + l=l+m* c + d +(n-2m) * c -d  =  l + (n-m) * c  即相当于p1从一开始走到环入口处，然后绕了若干圈又回到环入口处，因此p1p2会在环入口处相遇

```java
/*
 public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}
*/
public class Solution {

    public ListNode EntryNodeOfLoop(ListNode pHead){
        ListNode p1 = pHead;
        ListNode p2 = pHead;
        //p1走一步，p2走两步
        do{
            p1=p1.next;
            p2=p2.next;
            if(p2!=null) p2 = p2.next;
            if(p1==p2) break;
        }while(p1!=null && p2!=null);
        if(p2==null) return null;
        p2 = pHead;
        while(p2!=p1){
            p2=p2.next;
            p1=p1.next;
        }
        return p1;
    }
}
```

》如何计算上述链表中环的长度
    上述可以根据龟兔赛跑算法，知道一快一慢两个指针相遇时一定在环内，标记这个节点，让慢指针继续走，直到下一次到达这个节点时，就可直到环的长度


面试题24，翻转链表  ： j24-p142
输入一个链表，反转链表后，输出新链表的表头。

    》空间复杂度O(1)
    链表长度=2时直接交换头尾
    链表长度>=3时，用以下方式：其实就是每次只修改n2的next指向n1，即后一个节点的next指向前一个节点，n3是辅助节点，防止断链
        {
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
            head.next=null //这一步很重要，一定记得!!!
        }

```java
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {

    public ListNode ReverseList(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode last = null;
        ListNode cur = head;
        if(head==null) return null;
        else if(head.next==null) return head;
        else if(head.next.next==null){
            ListNode tmp = head.next;
            tmp.next = head;
            return tmp;
        }

        while(cur!=null){
            if(cur.next==null)
                last = cur;
            cur=cur.next;
        }

        //能到这里，至少有3个节点
        ListNode p1 = head;
        ListNode p2 = head.next;
        ListNode p3 = head.next.next;
        while(p3!=last){
            p2.next = p1;
            p1 = p2;
            p2=p3;
            p3=p3.next;
        }
        p2.next=p1;
        p3.next=p2;
        head.next=null;
        return p3;
    }
}
```

面试题25：合并两个排序的链表 j25-p145

题目描述
输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。

用两个链表指针，每次都比较，将小的值接在新链表的后面，并且更新该指针指向下一个节点。
所以一开始要有个假头
也算是双指针法,一直到其中一个指针为null，则把剩下的那个链表直接全接在新链表的后面即可

```java
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
    public ListNode Merge(ListNode list1,ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode p1 = list1;
        ListNode p2 = list2;
        ListNode p = dummy;
        while(p1!=null && p2!=null){
            if(p1.val<=p2.val){
                p.next = p1;
                p1=p1.next;
                p = p.next;
            }else{
                p.next = p2;
                p2=p2.next;
                p = p.next;
            }
        }

        //到这里必至少有一个为null
        if(p1!=null){
            p.next=p1;
        }
        else if(p2!=null){
            p.next = p2;
        }
        return dummy.next;
    }
}
```


面试题26：树的子结构：j26-p148


题目描述
输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）

设A的根为a
按照题意，B可以是A中任意一段，即B的叶子节点，可以是A中的非叶子节点，相当于就不要A中这个节点下面的部分了
但B的非叶子节点在A中必须是非叶子节点，且子树也相同（即A中包含B）

如果树a和树b相似，充要条件是b的子树(如果有的话)和a的子树相似且 b.val==a.val  
//这里如果b有子树，则a必须有子树与其相同，如果b没有子树，则只需要满足两节点值相同，a有没有子树不重要，如上面的

递归遍历以查看a中以每一个节点为根的子树与树b是否相似，如果有一个则为true，一个都没有则为false

```java
/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        if(root1==null || root2==null) return false;
        return isSame(root1, root2) || HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
    }

    public boolean isSame(TreeNode root1,TreeNode root2){
        if(root1==null && root2==null) return true;
        else if(root1==null || root2==null) return false;

        boolean cond1 = root1.val == root2.val;
        boolean cond2 = true;
        boolean cond3 = true;
        //root2的左孩子不空，就需要看两者左孩子。若root2左孩子为空就不需要看，因为root1的左孩子空不空都行。右孩子同理
        if(root2.left!=null){
            cond2 = isSame(root1.left, root2.left);
        }

        if(root2.right!=null){
            cond3 = isSame(root1.right, root2.right);
        }
        return cond1 && cond2 && cond3;

    }
}
```

## 判断两个double或者float数相等时不能用 == ，因为在计算机内表示小数时都有误差。
判断两个小数是否相等，只能判断它们的差的绝对值是不是在一个很小的范围内，如果两个数差很小，就可以认为它们相等
```java

boolean Equal(double a, double b){
    if((a-b>-0.0000001) && (a-b<0.0000001))
        return true;
    else
        return false;
}

```

# 一定要有意识地提高鲁棒性，采取防御性编程处理无效的输入

面试题27：二叉树的镜像 j27-p157

题目描述
操作给定的二叉树，将其变换为源二叉树的镜像。
输入描述:
二叉树的镜像定义：源二叉树 
            8
           /  \
          6   10
         / \  / \
        5  7 9 11
        镜像二叉树
            8
           /  \
          10   6
         / \  / \
        11 9 7  5


遍历树的同时交换节点的左右子节点，最方便当然就是递归遍历，前中后序都可

```java
/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    public void Mirror(TreeNode root) {
        if(root==null) return;
        TreeNode tmp = root.right;
        root.right = root.left;
        root.left = tmp;
        Mirror(root.left);
        Mirror(root.right);
    }
}
```


面试题28 ：j28-p159
请实现一个函数，用来判断一颗二叉树是不是对称的。
注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
空树是对称的

一棵树对称，充要条件是左子树和右子树是镜像的

判断镜像可以直接传入两个root，也可以传入root.left和root.right(但就要判断root是否为null)


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
/*
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
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

j29 顺时针打印矩阵，即打印矩阵的螺旋序 ：j29-p161
题目描述
输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.


还是四个方向，先往右，右到头了向下，要注意的是要遍历的个数每一圈都在减少，直到最后一个遍历完了。
设置左右上下的边界值，一旦到达边界值，下右边界值就-1，上左边界就+1
如：example2，一开始从左往右走，右边界为3，当到达第四列时（值为3），则往下走，右边界-1
往下走到下边界2处，下边界-1，再往右走。。。
注意的是，可能不是方阵, 
每遍历到一个数时，先加入到结果集中
在一个方向上运动，如果没到头，该方向上索引继续；如果到头了，更改方向，另一方向上的索引再更新
循环终止的条件是访问过的节点数等于节点总数

```java
import java.util.ArrayList;
public class Solution {

    public ArrayList<Integer> printMatrix(int [][] matrix) {
        int dir = 0;  //0右，1下，2左，3上
        ArrayList<Integer> ans = new ArrayList<>();
        if(matrix==null || matrix[0]==null) return ans;
        int row = matrix.length;
        int col = matrix[0].length;

        int limitTop = 1;
        int limitBottom = row-1;
        int limitLeft = 0;
        int limitRight = col -1;
        int total = row * col;
        int cur = 0;
        int i=0,j=0;
        while(cur<total){
            ans.add(matrix[i][j]);
            if(dir==0){
                if(j==limitRight){
                    dir=1;
                    limitRight--;
                    i++;
                }else
                    j++;
            }else if(dir==1){
                if(i==limitBottom){
                    dir=2;
                    limitBottom--;
                    j--;
                }else
                    i++;
            }else if(dir==2){
                if(j==limitLeft){
                    dir=3;
                    limitLeft++;
                    i--;
                }else
                    j--;
            }else{
                if(i==limitTop){
                    dir=0;
                    limitTop++;
                    j++;
                }else
                    i--;
            }
            cur++;
        }
        return ans;
    }
}
```

和画图一样，可以借助举例模拟的方法来思考分析复杂问题。当一眼看不出问题中隐藏的规律时，可以试着用一两个具体的例子模拟操作的过程，说不定就能从例子找到抽象的规律

面试题30 包含min函数的栈  j30-p165

定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。

用两个栈，一个主栈数据栈，一个辅助栈
把每次的最小元素（之前的最小元素和新压入栈的元素两者的较小值）都保存起来放到另一个辅助栈中

假设入栈3，4，2，1

首先往空的数据栈中压入数字3，显然现在3是最小值，也把这个数压入辅助栈。然后往数据栈压入数字4，比较4和辅助栈的栈顶元素，发现4大于3，因此仍然往辅助栈中压入3。第三步往数据栈中压入2，2小于辅助栈栈顶元素3，因此把2压入辅助栈。同样，压入数字1时，也要把1压入辅助栈

辅助栈栈顶永远是当前栈中元素的最小值。
弹栈时，不仅弹主栈，也弹辅助栈。
求最小值时，直接返回辅助栈的栈顶元素即可（不弹）

# 记得把这个更新到leetcode155 和总结中！

```java
import java.util.Stack;

public class Solution {
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

    public int min() {
        return helperStk.peek();
    }
}
```

j31栈的压入、弹出序列    j31-p168

题目描述
输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）

直观想法就是模拟这个过程，可以这么做：
用一个指针x保持当前要弹出的数，所以这个数是从第二个序列下标为0开始往后走的

如果当前要弹出的数和栈顶元素不等，则入栈，直到相等，则弹栈，然后x往后走一位，继续判断要弹出的数和栈顶元素是否相等，等则弹栈。 直到不等后，继续入栈。
当数字全部压入，且要弹出的数和栈顶元素不等时，则为false。如果数字全部压入，且全部弹出了，则为true


```java
import java.util.*;

public class Solution {

    public boolean IsPopOrder(int [] pushA,int [] popA) {
        int len = pushA.length;
        int popIndex = 0;
        int pushIndex = 0;
        Stack<Integer> stk = new Stack<>();
        while(popIndex!=len){
            while(!stk.isEmpty() && popA[popIndex]==stk.peek()){
                popIndex++;
                stk.pop();
            }
            if(pushIndex==len){
                break;
            }
            stk.push(pushA[pushIndex++]);
        }

        return stk.isEmpty();

    }
}
```

j32 从上到下打印二叉树  j32-p171

从上往下打印出二叉树的每个节点，同层节点从左至右打印。

也就是层序遍历，由于不用管层数，用一个队列即可层序遍历即可

```java
import java.util.*;
/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {

    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        if(root==null) return ans;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            ans.add(node.val);
            if(node.left!=null){
                queue.offer(node.left);
            }
            if(node.right!=null){
                queue.offer(node.right);
            }
        }
        return ans;
    }
}
```

如何广度优先遍历一副有向图？同样也是基于队列实现，树是图的一种退化形式，层序遍历二叉树从本质上来说就是广度优先遍历二叉树

## 深度优先是用栈来完成的，配合标志数组记录已经遍历过的位置
## 广度优先是用队列来做的，也要配合标志数组


分行从上到下打印二叉树，就要用到双队列版的层序遍历了

```java
    public List<ArrayList<Integer>> levelTraversal2(TreeNode root) {
        LinkedList<TreeNode> queue1 = new LinkedList<>();
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        if(root!=null) queue1.offer(root);
        while(!queue1.isEmpty() || !queue2.isEmpty()){
            LinkedList<TreeNode> cur = queue1.isEmpty()?queue2:queue1;
            LinkedList<TreeNode> another = queue2.isEmpty()?queue2:queue1;
            ArrayList<Integer> tmp = new ArrayList<>();

            while(!cur.isEmpty()){
                TreeNode node = cur.poll();
                tmp.add(node.val);
                if(node.left!=null) another.offer(node.left);
                if(node.right!=null) another.offer(node.right);
            }
            res.add(tmp);
        }
        return res;
    }
```

//用一个队列且能分行的打印的层序遍历，需要两个变量：一个变量表示当前层还没有打印的节点数，另一个变量表示下一层的节点数目  p174

```java
    public List<ArrayList<Integer>> levelTraversal2(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        int toBeAdded = 1;
        int nextLevel = 0;
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(root==null) return res;
        ArrayList<Integer> tmp = new ArrayList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            tmp.add(node.val);
            toBeAdded--;
            if(node.left!=null) {
                queue.offer(node.left);
                nextLevel++;
            }
            if(node.right!=null) {
                queue.offer(node.right);
                nextLevel++;
            }

            if(toBeAdded==0){
                res.add(tmp);
                toBeAdded = nextLevel;
                nextLevel = 0;
                tmp = new ArrayList<>();
            }
        }
        return res;
    }
```


之字形分行打印二叉树  p176

和上面的一样，只是记录一个当前打印的行是偶数行还是奇数行的变量就行，偶数行把当前行的序列翻转后加入结果集

```java
import java.util.*;

/*
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    public ArrayList<ArrayList<Integer> > Print(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        int toBeAdded = 1;
        int nextLevel = 0;
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(root==null) return res;
        ArrayList<Integer> tmp = new ArrayList<>();
        queue.offer(root);
        boolean isReverse = false;
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            tmp.add(node.val);
            toBeAdded--;
            if(node.left!=null) {
                queue.offer(node.left);
                nextLevel++;
            }
            if(node.right!=null) {
                queue.offer(node.right);
                nextLevel++;
            }


            if(toBeAdded==0){
                if(isReverse)
                    Collections.reverse(tmp);
                res.add(tmp);
                isReverse = !isReverse;
                toBeAdded = nextLevel;
                nextLevel = 0;
                tmp = new ArrayList<>();
            }
        }
        return res;
    }

}
```

面试题33 二叉搜索树的后序遍历序列 j33-p179

题目描述
输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。

二叉排序树的中序遍历是 升序序列，左中右。 后序遍历 是 左右中

后序遍历中，最后一个数字就是根，数组中的数可以分为两部分，一部分是左子树，值都比根小，一部分是右子树，值都比根大，再用同样的办法确定与数组每一部分对应的结构，其实就是一个递归的过程

先遍历数组，让i成为右子树的首值，即从左往右第一个大于根值的，然后遍历右子树，如果发现有值小于根的，返回false

如果左子树不为空，则左子树得正确，没有则默认正确
如果右子树不为空，则右子树得正确，没有则默认正确
返回 上述两结果的与值

```java
import java.util.Arrays;

public class Solution {

    public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence==null || sequence.length==0){
            return false;
        }
        
        int root = sequence[sequence.length-1];

        //先让i成为右子树的开始值
        int i =0;
        for(;i<sequence.length-1;i++){
            if(sequence[i]>root){
                break;
            }
        }
        //此时i是右子树开始的地方

        //如果右子树中有比根小的数，返回false
        for(int j=i;j<sequence.length-1;j++){
            if(sequence[j]<root){
                return false;
            }
        }

        //能到这里，说明左右子树和根的大小关系正确
        //如果有左子树，则左子树得正确，没有则默认正确
        boolean left=true;
        if(i>0){
            int[] l = Arrays.copyOfRange(sequence, 0, i);
            left = VerifySquenceOfBST(l);
        }
        //如果有右子树，则右子树得正确，没有则默认正确
        boolean right = true;
        if(i<sequence.length-1){
            int[] r = Arrays.copyOfRange(sequence, i, sequence.length-1);
            right = VerifySquenceOfBST(r);
        }
        return left && right;
    }
}
```

输入一个整数数组，判断该数组是不是某二叉搜索树的前序遍历结果，这和前面问题的后序遍历很类似，只是在前序遍历得到的序列中，第一个数字是根节点的值



面试题34 二叉树中和为某一值的路径：j34-p182

题目描述
输入一颗二叉树的根节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。(注意: 在返回值的list中，数组长度大的数组靠前)

使用trackback

```java
import java.util.ArrayList;
/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        if(root!=null)
            trackBack(root, 0, target, new ArrayList<>(), ans);
        return ans;
    }

    public void trackBack(TreeNode root, int cur, int target, ArrayList<Integer> list, ArrayList<ArrayList<Integer>> ans){
        cur += root.val;
        list.add(root.val);
        if(root.left==null && root.right==null && cur==target){
            ans.add(new ArrayList<>(list));
            list.remove(list.size()-1);
            return;
        }
        if(root.left!=null){
            trackBack(root.left, cur, target, list, ans);
        }
        if(root.right!=null){
            trackBack(root.right, cur, target, list, ans);
        }
        list.remove(list.size()-1);
    }

}
```

分解让复杂问题简单化：分治法

面试题35 复杂链表的复制：j35-p187

题目描述
输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）

把链表节点存进一个数组，根据数组的索引关系可以界定特殊指针指向的位置，把复制的链表也放进数组，根据前一个数组中元素特殊指向的索引来让该数组中元素的特殊指针指向相应的位置

也可以用HashMap保存旧节点和新节点的对应关系<Node,Node>，在设置random域时，就能根据旧节点得到其对应的新节点。

用O(1)的空间复杂度和O(n)的时间复杂度

step1：根据原始链表的每个节点N创建对应的N‘，这一次把N’链接在N的后面
step2：设置复制出来的节点的random，假设原始链表上的N的random指向节点S，那么其对应复制出来的N‘是N的next指向的节点，同样S’也是S的next指向的节点
step3：把这个长链表拆分成两个链表：把奇数位置的节点用next连起来就是原始链表，把偶数位置用next连起来就是复制出来的链表


```java
import java.util.ArrayList;
/*
public class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}
*/
public class Solution {

    //sO(n) + tO(n) 
    public RandomListNode Clone(RandomListNode pHead){
        if(pHead==null){
            return null;
        }
        int len = 0;
        RandomListNode node = pHead;
        ArrayList<RandomListNode> oriArray = new ArrayList<>();
        //加入数组
        while(node!=null){
            len++;
            oriArray.add(node);
            node = node.next;
        }

        ArrayList<RandomListNode> newArray = new ArrayList<>();
        //把新节点创建好
        for(int i=0;i<len;i++){
            newArray.add(new RandomListNode(oriArray.get(i).label));
        }

        for(int i=0;i<len;i++){
            RandomListNode cur = newArray.get(i);
            //添加next域
            if(i+1<len){
                cur.next = newArray.get(i+1);
            }

            //添加random域，原节点的random不是null才添加
            if(oriArray.get(i).random!=null){
                RandomListNode oriRandom = oriArray.get(i).random;
                //看oriRandom在原列表中是第几个元素
                int index = oriArray.indexOf(oriRandom);
                cur.random = newArray.get(index);
            }
        }

        return newArray.get(0);
    }



    //sO(1)+tO(n)
    public RandomListNode Clone(RandomListNode pHead){
        if(pHead==null){
            return null;
        }
        RandomListNode node = pHead;


        //把新节点创建好,并且连接在对应节点的后面
        while(node!=null){
            RandomListNode clone = new RandomListNode(node.label);
            clone.random=null;
            clone.next = node.next;

            node.next = clone;
            node = clone.next;
        }

        node = pHead;
        //设置新节点的random域
        while(node!=null){
            RandomListNode clone = node.next;
            if(node.random!=null)
                clone.random = node.random.next;
            node = clone.next;
        }

        //重链两个链表
        node = pHead;
        RandomListNode newHead = pHead.next;
        while(node!=null){
            RandomListNode clone = node.next;
            node.next = clone.next;
            if(clone.next!=null)
                clone.next = clone.next.next;
            node = node.next;
        }
        return newHead;
    }

}
```

面试题36 二叉搜索树与双向链表：j36-p191

输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。

二叉搜索树中序遍历就是排序的顺序,利用中序遍历，每次都保存上一个遍历过的节点，将上一个遍历过的节点的right指向当前节点，当前节点的left指向上一个节点。
（由于当前节点的left已经遍历完了，上一个遍历过的节点的right一定也遍历完了(或者就是当前节点，否则不可能遍历到当前节点)，所以不会出现断链的问题）并且要保留第一个访问的节点。
可以有递归和非递归两种方法，递归由于涉及到方法之间的跳转，所以需要一个实例域来保存上一个遍历过的节点。最终返回第一个访问的节点即可

```java

/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    public TreeNode Convert(TreeNode pRootOfTree) {
        inOrder(pRootOfTree);
        return head;
    }

    //递归方法
    TreeNode head=null;
    TreeNode preVisitor=null;
    public void inOrder(TreeNode root){
        if(root==null) return;
        inOrder(root.left);
        if(preVisitor!=null){
            preVisitor.right=root;
            root.left = preVisitor;
        }else{
            //证明是第一个节点
            head = root;
        }
        preVisitor = root;
        inOrder(root.right);
    }

    //非递归方法
    TreeNode head=null;
    public void inOrder(TreeNode root){
        TreeNode preVisitor=null;
        Stack<TreeNode> stk = new Stack<>();
        TreeNode p = root;
        while(p!=null || !stk.isEmpty()){
            if(p!=null){
                stk.push(p);
                p=p.left;
            }else{
                p = stk.pop();
                if(preVisitor!=null){
                    preVisitor.right = p;
                    p.left = preVisitor;
                }else{
                    head = p;
                }
                preVisitor = p;
                p=p.right;
            }
        }
    }

}
```

面试题37 序列化二叉树：j37-p194

题目描述
请实现两个函数，分别用来序列化和反序列化二叉树

二叉树的序列化是指：把一棵二叉树按照某种遍历方式的结果以某种格式保存为字符串，从而使得内存中建立起来的二叉树可以持久保存。序列化可以基于先序、中序、后序、层序的二叉树遍历方式来进行修改，序列化的结果是一个字符串，序列化时通过 某种符号表示空节点（#），以 ！ 表示一个结点值的结束（value!）。

二叉树的反序列化是指：根据某种遍历顺序得到的序列化字符串结果str，重构二叉树。

如果使用中序和前序：
序列化：把中序和前序序列放入字符串，中间用#隔开
反序列化，从字符串得到中序和前序，并用其构建一棵二叉树返回

也可以用层序序列来完成该任务。

下面两种方法都有，都可以AC


```java
/*
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {

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


//使用层序遍历
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
```

面试题38.字符串的排列  j38-p197

题目描述
输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。

典型的回退递归，trackback，要加一个flag表征已经加入过的字符,当答案字符串中长度等于原串长度就找到了一个答案。其他的没什么不同之处，有点区别的是该答案集中要求不能有重复的，而且答案排列要按序排列，
因此，先把所有可能的答案加入到HashSet中(去重)，然后用该set初始化一个ArrayList，再把该list排序即可

```java
import java.util.*;
public class Solution {

    public ArrayList<String> Permutation(String str) {
        ArrayList<String> ans = new ArrayList<>();
        if(str==null || str.length()==0) return ans;
        HashSet<String> set = new HashSet<>();
        trackBack(str, new boolean[str.length()], set, 0, new StringBuilder());
        ans = new ArrayList<>(set);
        Collections.sort(ans);
        return ans;
    }

    public void trackBack(String str, boolean[] flag, HashSet<String> ans, int curTotal, StringBuilder builder){
        if(curTotal==str.length()){
            ans.add(builder.toString());
            return;
        }
        for(int i=0;i<str.length();i++){
            if(flag[i]==false){
                flag[i]=true;
                builder.append(str.charAt(i));
                trackBack(str, flag, ans, curTotal+1, builder);
                flag[i]=false;
                builder.deleteCharAt(builder.length()-1);
            }

        }
    }
}
```

上面是求字符的所有排列，如果求字符的所有组合： j38-p199
如abc，它们的组合有：a、b、c、ab、ac、bc、abc

也使用回退递归，更新每次遍历的起点start，当遍历到某个字符时直接把当前遍历到的路径加入答案集

```java
class Solution{

    public ArrayList<String> Combination(String str) {
        ArrayList<String> ans = new ArrayList<>();
        if(str==null || str.length()==0) return ans;
        HashSet<String> set = new HashSet<>();
        trackBack(0, str, set, new StringBuilder());
        ans = new ArrayList<>(set);
        //重写排序算法，短的位于长的前面，要是一样长才看字典序
        Collections.sort(ans, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(o1.length()!=o2.length())
                    return o1.length()-o2.length();
                else{
                    return o1.compareTo(o2);
                }
            }
        });
        return ans;
    }

    public void trackBack(int start, String str, HashSet<String> ans, StringBuilder builder){

        for(int i=start;i<str.length();i++){
            builder.append(str.charAt(i));
            ans.add(builder.toString());
            trackBack(i+1, str, ans, builder);
            builder.deleteCharAt(builder.length()-1);
        }
    }

}
```

输入一个含有8个数字的数组，判断有没有可能把这8个数字分别放到正方形的8个顶点上，使得正方体三组相对的面上的4个顶点的和都相等：j38-p200

    这相当于先得到a1,a2,a3,a4,a5,a6,a7,a8这8个数字的所有排列，然后判断有没有某个排列符合题目给定的条件，即同时满足下面三个等式
    a1+a2+a3+a4=a5+a6+a7+a8, a1+a3+a5+a7=a2+a4+a6+a8, a1+a2+a5+a6=a3+a4+a7+a8
    把所有的排列的出来，对于任意一个排列x, a1相当于x[0]，把上面的ai变成 x[i-1] 求上述三个等式在该排列下是否都成立
    若成立，则是一种答案。


八皇后问题：j38-p200  ：n皇后问题：51



```java
class Solution {

    List<List<String>> res = new ArrayList<>();
    public List<List<String>> solveNQueens() {
        //先用二维数组，好操作一点
        char[][] board = new char[8][8];
        for(int i=0;i<8;i++){
            Arrays.fill(board[i], '.');
        }
        trackBack(board, 8, 0, 0);
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

举一反三：

## 如果面试题是按照一定要求摆放若干个数字，则可以先求出这些数字所有的排列，然后一一判断每个排列是不是满足题目给定的要求

## 在面试时遇到难题，画图、举例（举几个例子模拟运行的过程）、分解（DP、分治法）这三种方法能够帮助我们解决复杂的问题


面试题39. 数组中出现次数超过一半的数字  j39-p205

数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。

如果某个数字出现次数超过一半，意味着它可以和其他数字都做抵消最后还剩下它，所以可以用此思想，当计数器为0时，将上一个数替换为当前数，继续遍历，若计数器不为0，当出现一个数时，如果上一个数也是它，计数器+1，若上一个数不是它，计数器-1（相当于之前的数抵消了当前数），直到最后剩下的那个数就是最终结果(如果有的话)，最后还要遍历一遍数组，如果出现次数确实超过了一半(同时记录该数的出现次数和非该数的出现次数，若前者更大，说明确实超过了一半)，就返回该数，否则返回0。

```java
public class Solution {
    public int MoreThanHalfNum_Solution(int [] array) {
        if(array==null || array.length==0) return 0;
        if(array.length==1) return array[0];
        int pre = array[0];
        int cnt = 1;
        for(int i=1;i<array.length;i++){
            int cur = array[i];
            if(cnt==0){
                pre = cur;
                cnt=1;
                continue;
            }
            
            if(pre==cur){
                cnt++;
            }else{
                cnt--;
            }

        }
        int count = 0;
        int nCount = 0;
        for(int i=0;i<array.length;i++){
            int cur = array[i];
            if(cur==pre) count++;
            else nCount++;
        }

        if(count>nCount)
            return pre;
        else
            return 0;
    }
}
```

另一种思想：如果把这个数组排序，那么排序之后位于数组中间的数字一定就是那个出现次数超过一半的数字。也就是说，这个数字就是统计学上的中位数，即长度为n的数组中第n/2大的数字，下面是复杂度为O(n)的算法：
    在随机快速排序的算法中，先在数组中随机选择一个数字，然后调整数组中数字的顺序，使得比选中的数字小的数字都排在它的左边，比选中的数字大的数都排在它的右边。如果这个选中的数字的下标刚好是n/2，那么这个数字就是数组中的中位数，如果它的下标大于n/2，那么中位数应该位于它的左边，我们可以借助在它的左边部分的数组中查找；如果它的下标小于n/2，那么中位数应该位于它的右边，我们可以借助在它的右边部分的数组中查找；这是一个典型的递归过程。  其中的关键代码是快速排序


面试题40 最小的k个数   j40-p209

题目描述
输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。

可以基于快排中的Partition函数来解决这个问题。如果基于数组中的第k个数字调整，使得比第k个数字小的所有数字都位于数组的左边，比第k个数字大的所有数字都位于数字右边，这样调整后，位于数组中左边的k个数字就是最小的k个数字（这k个数字不一定是排过序的） 但该方法的弊端是必须要修改原数组（对其进行快排）

Partition后，枢轴索引若小于k，则继续Partition枢轴右边的数组；若枢轴索引大于k，则Partition左边的数组

```java
public class Solution {
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> ans = new ArrayList();
        if(input==null || input.length==0 || k<=0 || k>input.length) return ans;
        int low = 0;
        int high = input.length-1;
        int index = Partition(low, high, input);
        while(index!=k-1){
            if(index<k-1){
                high = index-1;
                index = Partition(low, high, input);
            }else if(index>k-1){
                low = index+1;
                index = Partition(low, high, input);                
            }
        }
        for(int i=0;i<k;i++){
            ans.add(input[i]);
        }
        return ans;
    }

    //找到
    public int Partition(int low, int high, int[] input){
        int pivot = input[low];
        int left = low;
        int right = high;
        while(left<right){
            while(input[right]>=pivot && left<right){
                right--;
            }
            input[left] = input[right];
            while(input[left]<=pivot && left<right){
                left++;
            }
            input[right] = input[left];
        }
        input[left] = pivot;
        return left;
    }
}
```

方法二：先创建一个大小为k的容器来存储最小的k个数字，然后从输入的n个整数中读入一个数,如果容器中的数字少于k个，则直接把这次读入的数据放入容器，若容器已满，则不能再插入新值，找到容器中的最大值，若待插入的数字比当前最大值小，则将该数字替换该最大值，否则不变。
可以使用PriorityQueue来做，让queue中的数字始终以降序排列，即队头是最大的数字，当遇到一个数且队列容量为k时，比较队头的数字和该数字，若该数更小，出队，然后把该数入队即可

```java
import java.util.*;
public class Solution {
   public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (input == null || input.length == 0 || k <= 0 || k > input.length) return ans;
        PriorityQueue<Integer> queue = new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        for(int i=0;i<input.length;i++){
            int n = input[i];
            if(queue.size()<k){
                queue.offer(n);
            }else{
                int t = queue.peek();
                if(t>n){
                    queue.poll();
                    queue.offer(n);
                }
            }
        }
        ans = new ArrayList<>(queue);
        return ans;
    }

}
```

面试题41.数据流中的中位数：j40-p214

如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取数据的中位数。

如果数组没有排序，则可以用Partition函数找出数组中的中位数，更好的办法是类似于leetcode 295中的方法，利用两个优先级队列，max中始终保存着当前数字小的一半，min中始终保存着当前数字中大的一半，若二者尺寸相同，则max出队和min出队就是最中间的两个元素；若不一样，只能是max比min多一，所以max出队就是中间的元素

先把一个数字加入max，然后从max中poll出的一定是max中最大的数（规定了优先级），
然后加入min，如果此时总数是偶数，则max和min的个数一样
如果是奇数，则从min中poll出min中最小的数，放入max中。
这样能保证，如果总数是偶数，则两个队头的元素就是数组中最中间的两个数
如果总数是奇数，max队头的元素就是最中间的那个数

```java
import java.util.*;
public class Solution {
    PriorityQueue<Integer> min = new PriorityQueue<>();
    PriorityQueue<Integer> max = new PriorityQueue(Collections.reverseOrder());

    public void Insert(Integer num) {
        max.offer(num);
        min.offer(max.poll());
        if(min.size()>max.size()){
            max.offer(min.poll());
        }
    }

    public Double GetMedian() {
        if(max.size()==min.size()) return (max.peek()+min.peek())/2.0;
        else{
            return Double.valueOf(max.peek());
        }
    }

}
```


面试题42.连续子数组的最大和：j42-p218

输入一个整型数组，有正数也有负数，数组中的一个或连续多个整数组成一个子数组，求所有子数组的和的最大值，要求时间复杂度是O(n)

遍历数组，初始化一个maxSoFar=nums[0];
        当遍历到nums[i]时, 如果nums[i] 大于 maxSoFar+nums[i]，说明前面累加的和还会抵消掉nums[i]，那么从nums[i]重新开始累计，令maxSoFar = nums[i]
        如果nums[i] 小等于 maxSoFar+nums[i]，说明可以起到累加的效果，令maxSoFar = nums[i]+maxSoFar
        综合上面两种情况，即为 maxSoFar = Math.max(nums[i]+maxSoFar, nums[i]);
        每次更新完后，更新maxTotal = Math.max(maxSoFar, maxTotal);
        这种方法的关键在于及时止损，如果x前面的累加和加上x还能比x小，那么直接不要前面的了，从x重新开始
    maxTotal记录已经出现过的最大值

动态规划：
dp[i]是截止加到nums[i]时连续子数组的最大值
当dp[i-1]>0时
    dp[i]=dp[i-1]+nums[i]
当dp[i-1]<=0时
    dp[i]=nums[i]


核心思想是，与其让前面的和把该数抵消得更小，还不如从该数开始算

```java
public class Solution {
    public int FindGreatestSumOfSubArray(int[] array) {
        int maxSoFar = array[0];
        int maxTotal = array[0];
        for(int i=1;i<array.length;i++){
            int n = array[i];
            if(n+maxSoFar>n){
                maxSoFar = maxSoFar+n;
            }else{
                maxSoFar = n;
            }
            maxTotal = Math.max(maxTotal,maxSoFar);
        }
        return maxTotal;
    }
}
```

面试题43.1-n整数中1出现的次数  j43-p221

求出1~ 13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？为此他特别数了一下1~13中包含1的数字有1、10、11、12、13因此共出现6次,但是对于后面问题他就没辙了。ACMer希望你们帮帮他,并把问题更加普遍化,可以很快的求出任意非负整数区间中1出现的次数（从1 到 n 中1出现的次数）。

对每个数字
先把21345分成：1345-21345 （即[次高位，最低位] 到 [最高位，最低位]）
    先看最高位：
        如果最高位是1，比如11234，那么最高位的1只有 10000~11234 即最高位后面的数字+1 （1234+1）
        如果最高位不是1，则可以次高位到最低位可以在0~9中选择，共有 10^(len-1)种选择
    再看后面的几位：
        由于最高位是2，所以又可以分成 1345-11345，11346-21345 （如果最高位是3就再加个21345-31345，总之就是按最高位从0到n，共有 最高位 个组）
        每一段除了从次高位到最低位，选择其中1位是1，剩下三位可以在0到9这10个数字中任意选择？？？（但是显然1221就不在这个范围内啊！）


使用leetcode 233中的方法

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

    1)如果我们不看那些特殊的行（以1X、11X等开头），我们知道每10个数字中有一个1在个位，每100个数字中有10个1在十位，每1000个数字中有100个1在百位，以次类推
    让我们从个位开始并计算有多少个1在这个位置，设k=1，根据上面所说，每10个数字1会出现1次在个位上，所以我们有多少10个数字？
    答案为 (n/k)/10
    现在计算十位，设k=10，每100个数字中有10个1在十位，所以我们有多少100个数字
    答案是 (n/k)/10，所以在十位上的1的个数为 (n/k)/10* k
    设 r=n/k  现在我们有了公式计算k位上的1的个数： r/10* k

    2).我们现在解决特殊行
    使用模运算，举10，11，12为例，假如n是10，则个位中1的个数是 (n/1)/10* 1=1,正确，但计算十位上时，(n/10)/10 * 10=0,错误，因为有1个1在十位上。
    从10到19，我们总是有1在十位上，设 m=n%k,在这个特殊位置上的1的个数是m+1，显然特殊行都是1打头的行
    即：  r/10 * k+(r%10==1?m+1:0)

    3）如果对于20，21，22呢？
    对于20，使用上述公式会得到十位上的1的个数是0，但实际上是10（10到19）,如何修正？我们知道一旦数字大于2，我们应该在十位上的1的个数中加上10，一个聪明的办法是在r上加上8，公式如下：
    (r+8)/10* k + (r%10==1?m+1:0)，循环的条件是n>=k

    举例：加入对于225来说
    个位上1的个数：k=1时，r=225  (225+8)/10=23, 
    十位上1的个数为 k=10时，r=22   (22+8)/10 * 10=30：21X ，11X，1X
    百位上1的个数为 k=100时，r=2， （2+8）/10 * 100 = 100   ：1XX
    共153

```java
public class Solution {
    public int NumberOf1Between1AndN_Solution(int n) {
        int k=1;
        int count = 0;
        while(n>=k){
            int r = n/k;
            int m= n%k;
            count += (r+8)/10*k+(r%10==1?m+1:0);
            k *= 10;
        }
        return count;
    }
}
```


# 下面方法存疑，不要使用

```java
public class Solution {
    public int NumberOf1Between1AndN_Solution(int n) {
        if(n<=0)
            return 0;
        String s = String.valueOf(n);
        numberOf1(s);

    }

    int numberOf1(String s){
        int first = s.charAt(0)-'0';
        int len = s.length();
        //要是个位数，直接返回1
        if(len==1 && first==0)
            return 0;
        if(len==1 && first>0)
            return 1;

        //假设s是21345
        //numFirstDigit是数字 10000~19999的第一位中1的数目
        //如果最高位是1，比如11234，那么最高位的1只有 10000~11234 即最高位后面的数字+1 （1234+1）
        //如果最高位不是1，则可以次高位到最低位可以在0~9中选择，共有 10^(len-1)种选择
        int numFirstDigit = 0;
        if(first>1){
            numFirstDigit = PowerBase10(len-1);
        }else{
            numFirstDigit = Integer.valueOf(s.substring(1))+1;
        }

        //numOtherDigits是1346~21345除第一位之外的数位中1的数目
        int numOtherDigits = first * (len-1) * PowerBase10(len-2);

        //
        numRecusive = numberOf1(s.substring(1));

        return numFirstDigit +numOtherDigits + numRecusive;

    }

    //返回10^n 
    int PowerBase10(int n){
        int result = 1;
        for(int i=0;i<n;i++){
            result *= 10;
        }
        return result;
    }
}
```


面试题44. 数字序列中某一位的数字  j44-p225

数字以0123456789101112131415的格式序列化到一个字符序列中。在这个序列中，第5位（从0开始计数）是5，第13位是1，第19位是4，等等，写出一个函数，求任意第n位对应的数字

1位的数字共10个，2的位数字共(10到99)9 * 10=90个,共90* 2位，3位的数字共（100到999）9 * 10 * 10共900个，共900 * 3位，4位的9000个。。。
若n小于10，直接返回n，否则，n所在的位置的这个数的位数如下算法：（2位的数字，10<=位置<190(10+90* 2), 3位的数字，190<=位置<2890(10+90* 2 + 900 * 3)）
        int k=90;
        int w = 2;
        int count = 10+k* w;
        int pre = 10;
        while(n>=count){
            k * = 10;
            w++;
            pre = count;
            count += k* w;
        }
得到的w就是n所在的位置的这个数的位数，count是截止到这么大位数的最大位置，pre是之前的位数的最大位置
i = n-pre 可得到n在从w位数开始算起是第几个，假如是3位数，即从100开始算起，是第几位，如：100101102 第8位是2
假如就按3位数距离，再看i是从100开始第几个数字中的，i/3表示是第从第一个3位数即100开始第几个数中的(100算第0个数), i%3表示是这个数字中的第几个数
因此i所在数就是：t = 10^(w-1)+(i/w)  (w位的数字，第0个数是10^(w-1)， 所以第x个数就是在后面加上x)
然后在t在找到第i%3个数即可：
s = String.valueOf(t);
int ans = s.charAt(i%w)-'0';

```java
class Solution{
    int getNumber(int n){
        if(n<10)
            return n;

        int k=90;
        int w = 2;
        int count = 10+k*w;
        int pre = 10;
        while(n>=count){
            k *= 10;
            w++;
            pre = count;
            count += k*w;
        }
        //此时w就代表n所在的数字是几位数
        int innerN = n-pre; //n在从w位数开始算起是第几个，假如是3位数，即从100开始算起，是第几位，如：100101102 第8位是2
        //再看innerN是从100开始第几个数字中的，即innerN/3 表示是第从第一个3位数即100开始第几个数中的(100算第0个数)，然后innerN%3表示是第几个数
        int a = innerN/w;
        int b = innerN%w;
        int target=(int)Math.pow(10,w-1)+a;
        String s = String.valueOf(target);
        int ans = s.charAt(b)-'0';
        return ans;
    }

}
```

面试题45.把数组排成最小的数：j45-p227

输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。

最简单的办法就是求出所有排列，然后求其中最小的。

更好的办法是对数组进行排序 例如对于两个数字 m和n，若mn< nm 则m应该排在n的前面，把数组中的数存入list，根据重写Comparator很容易实现对list的重排序，
根据上述规则重排序后，将其按顺序组合成字符串即为答案

在定义了新的排序规则后，如何证明该规则是有效的？要证明
1.自反性，2.对称性，3.传递性， 证明过程见 j-p229

```java
import java.util.*;

public class Solution {

    public String PrintMinNumber(int [] numbers) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i:numbers){
            list.add(i);
        }
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                StringBuilder s1 = new StringBuilder(o1+""+o2);
                StringBuilder s2 = new StringBuilder(o2+""+o1);
                return Integer.valueOf(s1.toString())-Integer.valueOf(s2.toString());
            }
        });

        StringBuilder builder = new StringBuilder();
        for(int i:list){
            builder.append(i);
        }
        return builder.toString();

    }
}
```

面试题46.把数字翻译成字符串 j46-p231

给定一个数字，按照如下规则把它翻译成字符串：0翻译成a，1翻译成b。。。。 11翻译成l，25翻译成z，一个数字可能有多种翻译
例如，12258有5种不同的翻译，分别是 bccfi,bwfi,bczi,mcfi,mzi 实现一个函数，用来计算一个数字有多少只不同的翻译方法

显然用带start的trackback即可(深度优先遍历),每次遍历，因为一个字母最大只有两位数，所以遍历sub(start, start+1) 和 sub(start, start+2)即可，若
s[start]是0，则不需要遍历后者（不用看以0开头的两位数）

```java
public class Solution{

    public int translate(int n){
        trackBack(0, String.valueOf(n), new StringBuilder());
        System.out.println(ans);
        return ans.size();
    }

    ArrayList<String> ans = new ArrayList<>();
    void trackBack(int start, String str, StringBuilder builder){
        if(start==str.length()){
            ans.add(builder.toString());
            return;
        }
        //最大就两位数
        for(int i=start; i<start+2 && i<str.length();i++){

            //截取当前要判别的数字
            String s = str.substring(start, i+1);
            int n = Integer.valueOf(s);
            if(n>=0 && n<=25){
                char c = (char) (n+'a');
                builder.append(c);
                trackBack(i+1, str, builder);
                builder.delete(builder.length()-1, builder.length());
            }
            //如果是当前数字是0，则不用判断以它打头的两位数，只有一位数
            if(str.charAt(i)=='0')
                i++;
        }
    }
}
```

面试题47.礼物的最大价值：j47-p233

在一个m x n的棋盘的每一格都放有礼物，每个礼物都有一个价值，可以从棋盘的左上角开始拿礼物，每次往右或者朝下移动一格，直到到达棋盘的右下角，
给定一个棋盘及其上面的礼物，请计算最多能拿到多少钱的礼物

很简单的动态规划，dp[i][j]是到达位置[i][j]后能有的最大价值

dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]) + gift[i][j]

```java
public class Solution{
    public int maxValue(int[][] values){
        if(values==null || values.length==0 || values[0].length==0) return 0;
        int row = values.length;
        int col = values[0].length;
        int[][] dp = new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(i==0 && j==0){
                    dp[0][0] = values[0][0];
                }else if(i==0){
                    dp[i][j] = dp[i][j-1] + values[i][j];
                }else if(j==0){
                    dp[i][j] = dp[i-1][j] + values[i][j];
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]) + values[i][j];
                }
            }
        }
        return dp[row-1][col-1];
    }
}
```

可以对空间进行优化，因为每次dp[i][j]只依赖dp[i-1][j]和dp[i][j-1],因此i-2及以上的格子都没有必要保存
对于dp[],当遍历到num[i][j]时，dp[0]到dp[j-1]是本行的数据，dp[j]到dp[col]是上一行的数据，这样用一维数组就可以完成工作了。
需要注意的是，就算dp优化成了一维数组，遍历num仍然二重循环。
```java
public class Solution{
    public int maxValue(int[][] values){
        if(values==null || values.length==0 || values[0].length==0) return 0;
        int row = values.length;
        int col = values[0].length;
        int[] dp = new int[col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                int left = 0;
                int up = 0;
                if(i>0){
                    up = dp[j];
                }
                if(j>0){
                    left = dp[j-1];
                }
                dp[j] = Math.max(left, up)+values[i][j];
            }
        }
        return dp[col-1];
    }
}
```

面试题48.最长不含重复字符的子字符串 ：j48-p236

请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度，假设字符串中只包含a-z的字符，例如，在字符串arabcacfr中，最长不含重复字符的子字符串是acfr，长度为4

使用窗口法，前后两个指针front和tail，tail往前移动，每遍历一个字符，对应的数组位置+1，当该位置字母对应的数字为2时，tail不动了，front向前移动，去掉的字母的对应的计数-1，直到该位置的数字变成1。

count[26]是当前窗口内各个字母的个数。
这有几个需要注意的点：用1个boolean b控制front和tail哪个走。若字符串长度为0或者1时，直接返回答案。否则先把str[0]，加算进去，front=0,tail=1。front一轮更新后在窗口内，往前走时，也是先从窗口中剔除，对应的count-1，若重复的字母就是当前字符，b=!b,front再++；tail一轮更新后在窗口外，往前走时，先把此时tail的字符加入窗口，对应的count+1, 若该count>1，则b=!b  否则 更新最长子字符串

```java
public class Solution{
    public int longestSubstringWithoutDup(String str){
        if(str==null|| str.length()==0) return 0;
        else if(str.length()==1) return 1;

        int[] count = new int[26];
        int front = 0;
        int tail = 1;
        boolean frontMove = false;
        count[str.charAt(front)-'a']++;
        int dup = 0;
        int maxLen = 1;
        while(front<=tail && tail < str.length()){
            if(frontMove){
                char c = str.charAt(front);
                int index = c-'a';
                count[index]--;
                if(index==dup){
                    frontMove = false;
                }
                front++;

            }else{
                char c = str.charAt(tail);
                int index = c-'a';
                count[index]++;
                if(count[index]>1){
                    frontMove=true;
                    dup = index;
                }else{
                    maxLen = Math.max(maxLen, tail-front+1);
                }
                tail++;
            }
        }
        return maxLen;
    }
}
```

方法二：动态规划

dp[i]表示以第i个字符结尾的不包含重复子字符串的最长长度，如果第i个字符之前没有出现过，则dp[i]=dp[i-1]+1
如果第i个字符之前出现过，设当前位置距离上一次出现过的位置为d
    若d<=dp[i-1],则说明第i个字符上次出现在dp[i-1]所对应的最长子字符串中，dp[i]=d;
    若d>dp[i-1]，则说明第i个字符上次出现在dp[i-1]所对应的最长子字符串之前，则dp[i]=dp[i-1]+1
用数组pos[26]来记录当前字符上一次出现的位置,一开始全部设置为-1，表示没出现过。

```java
public class Solution{

    public int longestSubstringWithoutDup(String str){
        if(str==null|| str.length()==0) return 0;
        else if(str.length()==1) return 1;
        int len = str.length();
        int[] pos = new int[26];
        int[] dp = new int[len];
        dp[0] = 1;
        for(int i=0;i<26;i++){
            pos[i]=-1;
        }

        int maxLen = 0;
        pos[str.charAt(0)-'a']++;
        for(int i=1;i<len;i++){
            int c = str.charAt(i)-'a';
            if(pos[c]<0){
                pos[c]=i;
                dp[i] = dp[i-1]+1;

            }else{
                int preIndex = pos[c];
                if((i-preIndex)<=dp[i-1])
                    dp[i] = i-preIndex;
                else
                    dp[i] = dp[i-1]+1;
                pos[c]=i;
            }
            maxLen = Math.max(maxLen, dp[i]);

        }
        return maxLen;
    }
}
```

以空间换时间

面试题49.丑数 j49-p240

把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含质因子7。 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。

逐个判断每个数是不是丑数，一定会超时
    
假设我们现在已经有了一个丑数的有序数组，如果要找到下一个丑数，则可以将数组中的每一个数乘以2，并将其中第一个大于当前丑数的的结果记为M2，同样将当前有序数组每一个数都乘以3，第一个大于当前丑数的的结果记为M3，同样方式得到乘以5的第一个大于当前丑数的结果记为M5。可以下一个丑数必然是min(M2, M3, M5)。


```java
public class Solution {
    public int GetUglyNumber_Solution(int index) {
        if(index<=0) return 0;
        int[] ulgys = new int[index];
        ulgys[0]=1;
        int pre = 1;
        for(int i=1;i<index;i++){
            int m2=0,m3=0,m5=0;
            for(int j=0;j<i;j++){
                if(ulgys[j]*2>pre){
                    m2 = ulgys[j]*2;
                    break;
                }
            }
            for(int j=0;j<i;j++){
                if(ulgys[j]*3>pre){
                    m3 = ulgys[j]*3;
                    break;
                }
            }
            for(int j=0;j<i;j++){
                if(ulgys[j]*5>pre){
                    m5 = ulgys[j]*5;
                    break;
                }
            }
            pre = Math.min(m2,m3);
            pre = Math.min(pre,m5);
            ulgys[i]=pre;
        }
        return ulgys[index-1];
    }
}
```


面试题50：第一个只出现一次的字符  j50-p243
题目描述
在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.

方法一：
用一个数组保留每个字母出现的次数，用一个hashmap映射每个字母第一次出现的位置，只需遍历一遍字符串就可以构建出来，再依次遍历每个字母，当出现次数为1，且位置小于当前最小位置时，更新该最小位置(只对出现全小写字母或者全大写字母管用)

方法二：遍历串，将hashmap存入每个字符出现的次数，然后再遍历串，让该字符出现次数为1时，返回该位置（针对任意大小写的字符）

```java
import java.util.*;
public class Solution {
    public int FirstNotRepeatingChar(String str) {
        HashMap<Character, Integer> cntMap = new HashMap<>();
        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);
            cntMap.put(c, cntMap.getOrDefault(c, 0)+1);
        }

        int firstPos = -1;
        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);
            if(cntMap.get(c)==1){
                firstPos = i;
                break;
            }
        }

        return firstPos;
    }
}
```

如果字符统计的是汉字，要如何改进？
首先统计字符串中出现了多少汉字，然后给每个汉字编号，仍然用上面hash表的形式


定义一个函数，输入两个字符串，从第一个字符串中删除在第二个字符串中出现过的所有字符：j-p246-1
    将第二个字符串中所有字符及出现次数全部存在hashmap中，再遍历第一个字符串，当出现字符在map中对应的次数大于0时，结果集不加它，且map中对应的次数-1


定义一个函数，删除字符串中所有重复出现的字符，如google变成gole  j-p246-2
    将字符串中所有字符及出现次数全部存在hashmap中，并且再设置一个map记录当前结果集中该字符出现的次数，再遍历一遍原串，当其在原串出现次数大于1且在当前结果集中出现次数等于1时，不加入结果集；否则加入结果集



字符流中只出现一次的字符：j-p247

请实现一个函数，用来找出字符流中第一个只出现一次的字符，例如，当从字符流中只读出前两个字符“go”的时候，第一个只出现一次的字符是“g”，当从该字符流中读出前6个字符“google”时，第一个只出现一次的字符是“l”

把读入的字符及其对应的次数放入hashmap中，并且按序放入一个队列中，每当读入一个字符时，查看队头的字符对应的次数是否为1，如果是，队列不变，否则，出队直到队列头的字符次数为1

```java
import java.util.*;
public class Solution {
    LinkedList<Character> queue = new LinkedList<>();
    HashMap<Character, Integer> count = new HashMap<>();

    void insert(char c){
        count.put(c, count.getOrDefault(c, 0)+1);
        queue.offer(c);
    }

    char getFirstAppearingOnce(){
        char defaultAns = '\0';
        while(!queue.isEmpty()){
            char peek = queue.peek();
            if(count.get(peek)==1)
                return peek;
            queue.poll();
        }
        return defaultAns;
    }
}
```

面试题51.数组中的逆序对：j51-p249
在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007

输入描述:
题目保证输入的数组中没有的相同的数字

数据范围：

    对于%50的数据,size<=10^4

    对于%75的数据,size<=10^5

    对于%100的数据,size<=2*10^5

示例1
输入
复制
1,2,3,4,5,6,7,0
输出
复制
7

显然不能用顺序扫描的思想，一定会超时。

用分解然后归并的思想：
把数组分成2部分，part1和part2，先得到part1和part2各自的逆序数i1和i2，设置i表示part1和part2之间的逆序数，然后对part1和part2各自排序，设置两个指针p1,p2分别从后往前遍历part1和part2
当part1[p1]>part2[p2]时，则肯定比part2[0]到part2[p2]都大，一共有p2+1个数，i += （p2+1），p1往前挪；否则part1[p1]< part2[p2] p2往前挪
直到有一个到头了。然后把数组排序，供上层使用，返回i+i1+i2, 即本层的逆序数等于两个子部分各自的逆序数及两部分之间的逆序数

```java
import java.util.*;

public class Solution {

    public int InversePairs(int [] array) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0;i<array.length;i++){
            list.add(array[i]);
        }
        return getInversePair(list);
    }

    int getInversePair(ArrayList<Integer> list){
        int len = list.size();
        if(len==0 || len==1) return 0;
        ArrayList<Integer> part1 = new ArrayList<>();
        ArrayList<Integer> part2 = new ArrayList<>();

        //数组前一半
        for(int i=0;i<len/2;i++){
            part1.add(list.get(i));
        }

        //数组后一半
        for(int i=len/2;i<len;i++){
            part2.add(list.get(i));
        }

        int inverseCount = 0;
        //递归找前后半中的逆序数
        int i1 = getInversePair(part1);
        int i2 = getInversePair(part2);

        //合并子数组的过程中找两个部分之间的逆序数
        int p1 =part1.size()-1;
        int p2 = part2.size()-1;
        while(p1>=0 && p2>=0){
            if(part1.get(p1)>part2.get(p2)){
                //若part[p1]比part2[p2]大，则肯定比part[0]到part[p2]都大，一共有p2+1个数
                inverseCount += (p2+1);
                p1--;
            }else{ //没有相同的数字，则肯定就是小于
                p2--;
            }
        }

        //计算完逆序数后排好序供上层使用
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        //本层的逆序数等于两个子部分各自的逆序数及两部分之间的逆序数
        return inverseCount+i1+i2;

    }
}
```

牛客网上的要求返回对1000000007取模的结果，则要求在整个过程中都要对其取模

```java
import java.util.*;

public class Solution {

    public int InversePairs(int [] array) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0;i<array.length;i++){
            list.add(array[i]);
        }
        return getInversePair(list)%1000000007;
    }

    int getInversePair(ArrayList<Integer> list){
        int len = list.size();
        if(len==0 || len==1) return 0;
        ArrayList<Integer> part1 = new ArrayList<>();
        ArrayList<Integer> part2 = new ArrayList<>();

        //数组前一半
        for(int i=0;i<len/2;i++){
            part1.add(list.get(i));
        }

        //数组后一半
        for(int i=len/2;i<len;i++){
            part2.add(list.get(i));
        }

        int inverseCount = 0;
        //递归找前后半中的逆序数
        int i1 = getInversePair(part1)%1000000007;
        int i2 = getInversePair(part2)%1000000007;

        //合并子数组的过程中找两个部分之间的逆序数
        int p1 =part1.size()-1;
        int p2 = part2.size()-1;
        while(p1>=0 && p2>=0){
            if(part1.get(p1)>part2.get(p2)){
                //若part[p1]比part2[p2]大，则肯定比part[0]到part[p2]都大，一共有p2+1个数
                inverseCount += (p2+1);
                if(inverseCount>=1000000007){
                    inverseCount %= 1000000007;
                }
                p1--;
            }else{ //没有相同的数字，则肯定就是小于
                p2--;
            }
        }

        //计算完逆序数后排好序供上层使用
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        //本层的逆序数等于两个子部分各自的逆序数及两部分之间的逆序数
        return (inverseCount+i1+i2)%1000000007;

    }
}
```

## 对于 x和y，若二者符号相同时，取模就是取余 mod == %；二者符号不同时，x % y 取余运算，结果符号和x符号一样；x mod y ，取模运算，结果和y符号一样

下面都讨论符号一样的情况，取模就是取余

## 若计算一个结果的取模，若该结果是多个数字累加形成的，则可以对所有累加的部分全部取模后再累加，再将最终累加的结果取模，结果和先累加再取模是一样的。
即 c = （a1+a2+a3+...+an）
c % x = (a1%x +a2%x +... an%x) % x


面试题52.两个链表的第一个公共节点 j52-p253

输入两个链表，找出它们的第一个公共结点。

p1和p2，分别计算两个链表的长度，然后保证p1和p2从到链表尾距离相同的地方开始走，直到二者相同

```java
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
 public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if(pHead1==null || pHead2==null) return null;
        int len1 = 0;
        int len2 = 0;
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        while(p1!=null){
            len1++;
            p1=p1.next;
        }

        while(p2!=null){
            len2++;
            p2=p2.next;
        }

        if(len1>len2){
            p1 = pHead1;
            p2=pHead2;
            while(len1!=len2){
                p1=p1.next;
                len1--;
            }
        }else{
            p2 = pHead2;
            p1=pHead1;
            while(len1!=len2){
                p2=p2.next;
                len2--;
            }
        }

        while(p1!=p2 ){
            p1=p1.next;
            p2=p2.next;
        }
        return p1;
    }

}
```

降低时间复杂度的做法：
    1、改用更加高效的算法，如用动态规划
    2、用空间换时间：创建一个缓存保存中间结果从而避免重复计算

方法二：两个指针p1，p2分别从链表A的开头和链表B的开头往后走，当p1到达末尾时，再从B开始走，当p2到达末尾时，再从A开始走，当二者重合时即为第一个公共节点。

这里要注意！在循环中不能将p1设置为p1.next后再判断是否为空然后跳转。 应该是要么设置为next，要么跳转，二者都为一个操作，切记不要连着做
```java
public class Solution {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) return null;
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        while (p1 != p2) {
            if (p1 == null) {
                p1 = pHead2;
            } else
                p1 = p1.next;
            
            if (p2 == null) {
                p2 = pHead1;
            } else
                p2 = p2.next;

        }
        return p1;
    }
}
```
    


题目53、数字在排序数组中出现的次数  j53-p263
题目描述
统计一个数字在排序数组中出现的次数。

先二分查找，找到后左右扩展边界


```java
public class Solution {

    public int GetNumberOfK(int[] array, int k) {
        int low = 0;
        int high = array.length - 1;
        int mid = 0;
        boolean found = false;
        while (low <= high) {
            mid = (low + high) >> 1;
            if (array[mid] == k) {
                found = true;
                break;
            } else if (array[mid] > k) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if (found) {
            int count = 1;
            int left = mid - 1;
            int right = mid + 1;
            while ((left >= 0 && array[left] == k) || (right < array.length && array[right] == k)) {
                if (left >= 0 && array[left] == k) {
                    left--;
                    count++;
                }
                if (right < array.length && array[right] == k) {
                    right++;
                    count++;
                }
            }
            return count;
        } else {
            return 0;
        }
    }

}
```

0到n-1中缺失的数字  j-p266
一个长度为n-1的递增数组中所有数字是唯一的，并且每个数字都在0到n-1的范围之内，在0到n-1内的n个数字有且只有一个不在该数组中，找到这个数字

把和算出来，和0到n-1的和一减就是没有的那个数
等差数列通项公式： Sn = n(a1+an)/2
但该方法没有利用递增数列这一条件

因为0到n-1这些数字再数组中是排过序的，因此数组中开始的一些数字和它们的下标相同，也就是说，0在下标为0的位置，1在下标为1的位置，以此类推，如果不在数组中的那个数字记为m，那么所有比m小的数字的下标都与他们的值相同，因此转换成在排序数组中找出第一个值和下标不相等的元素

仍可利用二分查找,区别代码如下：


```java
public class Solution{
    int getMissingNumber(int[] array){
        int low = 0;
        int high = array.length - 1;
        int mid = -1;
        while (low <= high) {
            mid = (low + high) >> 1;
            if(array[mid] != mid){ //若索引和值不等时，若该索引为0，或前一个数等于其索引，则返回该索引即为答案，否则high=mid-1
                if(mid==0 || array[mid-1]==mid-1){
                    return mid;
                }else{
                    high = mid-1;
                }
            }else{ //索引值
                low = mid+1;
            }
        }
        return mid;        
    }
}
```


数组中数值和下标相等的元素 j-p267
假设一个单调递增的数组里每个元素都是整数且是唯一的，实现一个函数，找出数组中任意一个数值等于其下标的元素
如{-3,-1,1,3,5} 数字3与其下标相等
遍历的复杂度是O(n)

由于数组是单调递增的（索引也是单调递增的），因此可以用二分查找算法来优化，如果某一步抵达数组中第i个数字，假设该数字正好是i，则找到了
如果数字小于其索引i,则说明还在后面，更新low
如果数字大于其索引i，说明在前面，更新high

```java
public class Solution{
    int getIndexedNumber(int[] array){
        int low = 0;
        int high = array.length - 1;
        int mid = -1;
        while (low <= high) {
            mid = (low + high) >> 1;
            if(array[mid] == mid){ //若索引和值不等时，若该索引为0，或前一个数等于其索引，则返回该索引即为答案，否则high=mid-1
                return mid;
            }else if(array[mid]>mid){
                high = mid-1;
            }else{ //索引值
                low = mid+1;
            }
        }
        return mid;        
    }
}
```

面试题54：j54-p269
给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）    中，按结点数值大小顺序第三小结点的值为4。

二叉搜索树的中序遍历就是递增序列，直接中序遍历直到第k个元素即可

```java
/*
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    TreeNode KthNode(TreeNode pRoot, int k){
        if(pRoot==null) return null;
        TreeNode p = null;
        Stack<TreeNode> stk = new Stack<>();
        p = pRoot;
        while(p!=null || !stk.isEmpty()){
            if(p!=null){
                stk.push(p);
                p = p.left;
            }else{
                p = stk.pop();
                k--;
                if(k==0){
                    return p;
                }
                p = p.right;
            }
        }
        return null;
    }
}
```

面试题55.二叉树的深度：j55-p271

输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。

很简单的递归

```java
public class Solution {
    public int TreeDepth(TreeNode root) {
        if(root==null) return 0;
        return 1+Math.max(TreeDepth(root.left), TreeDepth(root.right));
    }
}
```



题目描述：j-p273
输入一棵二叉树，判断该二叉树是否是平衡二叉树。

    先将以每个节点为根的树的高度保存在hashMap中：树的高度=max(左子树高度，右子树高度)+1
    然后对于任何一棵树，它为平衡二叉树 等价于下面两个条件同时满足
    1.它的左子树和右子树高度差不超过1
    2.它的左子树和右子树都是二叉平衡树

```java
import java.util.*;
public class Solution {
    HashMap<TreeNode, Integer> heightMap = new HashMap<>();
    public boolean IsBalanced_Solution(TreeNode root) {
        if(root==null) return true;
        return Math.abs(TreeDepth(root.right)-TreeDepth(root.left))<=1 &&
                IsBalanced_Solution(root.left) && IsBalanced_Solution(root.right);
    }

    public int TreeDepth(TreeNode root) {
        if(heightMap.containsKey(root)) return heightMap.get(root);

        if(root==null) return 0;

        int h =  1+Math.max(TreeDepth(root.left), TreeDepth(root.right));
        heightMap.put(root, h);
        return h;
    }
}
```

方法二：如果用后序遍历去遍历二叉树的每个节点，那么在遍历到一个节点之前我们就已经遍历了它的左右子树，只要在遍历每个节点的时候记录它的深度，就可以一边遍历一边判断每个节点是不是平衡的(在没有指针的java，仍然需要使用map保存每个节点对应的深度，其实和上面的就一样了)


题目56.数组中只出现一次的数字：j56-p275
题目描述
一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。

将这些所有的数字都异或，由于相同的数字异或后为0，所以最终得到的结果就是这两个数的异或结果，从这个结果中随便取是1的一位

例如，结果是 11000100，取从右往左数第三位，将其他所有位都置为0，得 00000100，说明上述两数该位上不一样

将所有数字与其做与运算，将所有与上该数不是0的数字全部异或，得到数字a；将所有与上该数是0的数字全部异或，得到数字b
a和b即为要求的数
    
## 任何数和0(全0)异或都是它自身；任何数和全1异或就相当于各个位取反

```java
//num1,num2分别为长度为1的数组。传出参数
//将num1[0],num2[0]设置为返回结果
public class Solution {

    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        int XOR = 0;
        for(int i=0;i<array.length;i++){
            XOR ^= array[i];
        }

        int tmp = XOR;
        //寻找XOR的第一个1
        int pos = 0;
        String s = Integer.toBinaryString(XOR);
        boolean foundFirst1 = false;
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='1'){
                if(foundFirst1){
                    builder.append('0');
                }else{
                    builder.append('1');
                    foundFirst1=true;
                }
            }else{
                builder.append('0');
            }
        }

        int x = Integer.valueOf(builder.toString());

        for(int i=0;i<array.length;i++){

            if((array[i]&x)==0){
                num1[0] ^= array[i];
            }else{
                num2[0] ^= array[i];
            }
        }
    }
}
```

题目：j-p278
数组中唯一出现一次的数字
数组中除了一个数字只出现了一次，其他数字都出现了3次，请找出那个出现3次的数字，用sO(1)

异或不能解决这个问题，因为三个相同的数字异或的结果还是该数字，如果一个数字出现3次，那么它的二进制位表示的每一位也出现3次，如果把所有出现3次的数字的二进制的每一位都分别加起来（10进制的加），那么每一位的和都能被3整除，如果某一位的和能被3整除，那么这一位在那个只出现一次的数字的二进制中是0，否则就是1

```java
public class Solution{
    public int findNumberAppearingOnce(int[] nums){
        int[] bitSum = new int[32]; //记录每个数字每一位的和

        for(int i=0;i<nums.length;i++){
            int tmp = nums[i];
            for(int j=0;j<32;j++){
                //与上1就得到了tmp最右端的那一位是0还是1
                bitSum[j] += tmp & 1;
                //向右移位
                tmp = tmp>>1;
            }
        }

        int target = 0;
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<32;i++){
            //如果该位不是3的倍数，则加上该位对应的2的幂次对应的数
            if(bitSum[i]%3!=0){
                target += Math.pow(2, i);
            }
        }
        return target;
    }
}
```

面试题57.和为s的数字：j57-p280

题目描述
输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
输出描述:
对应每个测试案例，输出两个数，小的先输出。

》方法一：
    遍历每个数，每遍历到一个数，在它后面的数字中进行二分查找，找到其补数(两者和为s，然后看它们的乘积是否是最小的，最后保留乘积最小的那一对)

》方法二：
    双指针法，p1从头向尾走，p2从尾向头走，设 sum = array[p1]+array[p2]
        若sum>s,p2--; 若sum< s, p1++, 若sum==s, 记录一下，然后p1++，最后要找二者乘积最小的。
方法一：
```java
import java.util.ArrayList;
public class Solution {

    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        int len = array.length;
        int minProduct = Integer.MAX_VALUE;
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=0;i<array.length;i++){
            int n = array[i];
            int support = sum-n;
            int index = binarySearch(array, i+1, len-1, support);
            if(index!=-1){
                if(n * support<minProduct){
                    ans.clear();
                    ans.add(n);
                    ans.add(support);
                    minProduct = n * support;
                }
            }
        }
        return ans;
    }

    int binarySearch(int[] array, int low, int high, int target){
        int mid = (low+high)>>1;
        while(low<=high){
            mid = (low+high)>>1;
            if(array[mid]==target) return mid;
            else if(array[mid]>target){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return -1;
    }
}
```

方法二：
```java
import java.util.ArrayList;
public class Solution {
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int s) {
        int len = array.length;
        int minProduct = Integer.MAX_VALUE;
        int p1=0, p2=len-1;
        ArrayList<Integer> ans = new ArrayList<>();
        while(p1<p2){
            int sum = array[p1]+array[p2];
            if(sum==s){
                if(array[p1]*array[p2]<minProduct){
                    ans.clear();
                    ans.add(array[p1]);
                    ans.add(array[p2]);
                    minProduct = array[p1]*array[p2];
                }
                p1++;
            }else if(sum>s){
                p2--;
            }else{
                p1++;
            }
        }
        return ans;
    }
}
```

和为s的连续正数序列  j-p282
输入一个正数s,打印出所有和为s的连续正数序列（至少含两个数），例如，输入15，由于1+2+3+4+5 = 4+5+6+7 = 7+8=15,
所以打印出3个连续序列： 1~ 5， 4~ 7， 7~ 8

用双指针窗口法，p1,p2分别为前后指针  sum为窗口内所有元素之和，p1< p2,且 array[p1] < 1+(s/2)
若sum< s，p2++ sum+=array[p2]； 若sum>s，p1++  sum-= array[p1]；若sum==s，将p1到p2全部加入一个列表，并把该列表加入结果集，且p2++，sum+=p2

```java
import java.util.ArrayList;
public class Solution {

    public ArrayList<ArrayList<Integer> > FindContinuousSequence(int s) {
        int p1=1;
        int p2=2;
        ArrayList<ArrayList<Integer> > ans = new ArrayList<>();
        int halfPlus = (s>>1) + 1;
        int sum = 3;
        while(p1<halfPlus && p1<p2){
            if(sum==s){
                ans.add(addOneAns(p1,p2));
                p2++;
                sum += p2;
            }else if(sum>s){
                sum -= p1;
                p1++;
            }else{
                p2++;
                sum += p2;
            }
        }
        return ans;
    }

    ArrayList<Integer> addOneAns(int p1, int p2){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=p1;i<=p2;i++){
            list.add(i);
        }
        return list;
    }
}
```

题目58.翻转字符串：j58-p284

把“student. a am I”翻转为：“I am a student.”
很简单，用split按空格分割，然后倒序组成一个新的字符串，但要注意全空格的情况，这样split的字符串数组是空的，则直接返回原字符串即可

```java
//方法一
public class Solution {
    public String ReverseSentence(String str) {
        String[] strs = str.split(" ");
        if(strs.length==0){ //说明全是空格
            return str;
        }
        StringBuilder builder = new StringBuilder();
        for(int i=strs.length-1;i>=0;i--){
            builder.append(i!=0?(strs[i] + " "):(strs[i]));
        }
        return builder.toString();
    }
}

```

题目。左旋转字符串 j-p286
对于一个给定的字符序列S，
请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”

对于原来位置为x的字符，循环左移后的位置为(x-3+len)%len，如果要求空间复杂度为O(n),则可另开一个字符串保存
如果要求空间复杂度是O(1),则需要两层循环，第一层循环遍历每个数字，第二层循环放置每个到新位置的字符，并把该位置上的原字符保存，并放到下一个位置上
不能光有里面的循环，因为可能会造成内圈导致无法把所有的字符都安置完。

》方法二：
    循环左移k位，先将字符串分成两部分：[0, k-1], [k, len-1]
    分别翻转两部分：[k-1, 0], [len-1, k]
    然后再翻转整个字符串 [k...len-1,0...k-1]

    对于循环右移，循环右移k位相当于循环左移len-k位，做法就能统一起来了 


```java
public class Solution {
    //方法一：
    public String LeftRotateString(String str,int n) {
        int finished = 0; //已经放置完的数字
        StringBuilder builder = new StringBuilder(str);
        int len = str.length();
        for(int i=0;i<str.length();i++){
            if(finished==len) break;
            char c = builder.charAt(i);
            int nextPos = (i-n+len)%len;
            int firstNextPos = nextPos;
            char toBePlaced = c;
            do{
                char tmp = builder.charAt(nextPos);
                builder.replace(nextPos, nextPos+1, toBePlaced+"");
                toBePlaced = tmp;
                nextPos = (nextPos-n+len)%len;
                finished++;
            }while(firstNextPos!=nextPos && finished!=len);
        }
        return builder.toString();
    }



    //方法二：
    public String LeftRotateString(String str,int n) {
        if(str==null || str.length()==0) return str;
        StringBuilder leftPart = new StringBuilder(str.substring(0, n));
        StringBuilder rightPart = new StringBuilder(str.substring(n));

        leftPart.reverse();
        rightPart.reverse();
        StringBuilder ans = new StringBuilder();
        ans.append(leftPart).append(rightPart);
        return ans.reverse().toString();

    }
}

```

题目59.队列的最大值

题目1.滑动窗口的最大值 j59-p288

给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个： {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。

把原数组按照窗口大小顺序分成若干个组，最后一个组不一定满n个，如上述，窗口为3:
2,3,4 | 2,6,2 | 5，1

顺序遍历每个数字，将截止到该数字为止，该数字所在的组中最大的数 依次放入一个新数组：
left：2 3 4   2 6 6   5 5

倒序遍历每个数字，将截止到该数字为止，该数字所在的组中最大的数 依次放入一个新数组：
right：4 4 4   6 6 2    5 1

对于位置i，在当前窗口中的每个位置i处滑动最大值 = max(right[i], left[i+n-1])

（这种方法很难记忆，最好还是用下面双端队列的方法）
```java
public class Solution {
    public ArrayList<Integer> maxInWindows(int [] num, int size){
        ArrayList<Integer> ans = new ArrayList<>();
        if(num==null || num.length==0 || size<=0) return ans;
        int[] left = new int[num.length];
        for(int i=0;i<num.length;i+=size){
            int max = Integer.MIN_VALUE;
            for(int j=i;j<size+i && j<num.length;j++){
                max = Math.max(max, num[j]);
                left[j] = max;
            }
        }

        int[] right = new int[num.length];
        int pre = (num.length-1)/size;
        int max = 0;
        for(int i=num.length-1;i>=0;i--){
            if(i/size==pre){
                max = Math.max(max, num[i]);
                right[i] = max;
            }else{
                pre = i/size;
                right[i] = num[i];
                max = num[i];
            }
        }

        for(int i=0;i<num.length-size+1;i++){
            ans.add(Math.max(left[i+size-1], right[i]));
        }
        return ans;
    }
}
```

另一种做法，使用窗口法,借助双端队列 deque

双端队列中放入的是索引
若待加入的数字大于队头索引对应的数字，则清空队列加入该索引
若待加入的数字小于队头索引对应的数，则和队尾索引对应的数的比较：
    若大于队尾对应的数，从队尾出队直到队尾对应的数大于该数，向队尾插入该数的索引
    若小于队尾对应的数，向队尾插入该数的索引

若待加入的数字的索引和队头索引差大等于过size，则出队直到索引差小于size

出队头，将其对应的数字加入答案集（该步从第size个数，下标为size-1时再开始做）


对于
{2,3,4,2,6,2,5,1}
遍历到的数字     队列中的索引(对应的数字)（队尾-----队头）
2                   0(2)
3                   1(3)
4                   2(4)
2                   3(2)  2(4)
6                   4(6)
2                   5(2) 4(6)
5                   6(5) 4(6)
1                   6(5)


双端队列就使用LinkedList

```java
import java.util.*;
public class Solution {

    public ArrayList<Integer> maxInWindows(int [] num, int size){
        ArrayList<Integer> ans = new ArrayList<>();
        if(num==null || num.length<size || size==0) return ans;
        LinkedList<Integer> deque = new LinkedList<>();

        for(int i=0;i<num.length;i++){
            int n = num[i];
            if(deque.isEmpty()){
                deque.offerLast(i);
            } else if(n > num[deque.peekFirst()]){ //大于队头
                deque.clear();
                deque.offerLast(i);
            }else if(n>num[deque.peekLast()]){ //大于队尾
                while(!deque.isEmpty() && n>num[deque.peekLast()]){
                    deque.pollLast();
                }
                deque.offerLast(i);
            }else{ //小于队尾
                deque.offerLast(i);
            }

            //从size-1，即第size个数才开始算，此时窗口才够大
            if(i>=size-1){
                //直到队头的索引和i差值小于size
                while (i - deque.peekFirst() >= size){
                    deque.pollFirst();
                }
                ans.add(num[deque.peekFirst()]);
            }
        }


        return ans;
    }
}
```

队列的最大值：j-p292

定义一个队列并实现函数max，得到其中的最大值，要求函数max、offer，poll的时间复杂度都是O（1），由于max和poll不同，所以不能用PriorityQueue
还是用上述j59-p288的思想，把滑动窗口看成是一个队列，队列的大小就是窗口的宽度，

里面的数据需要单独开一个类InternalData，既有数据又有索引
        class InternalData{
            int index;
            int data;

            InternalData(int index, int data){
                this.index = index;
                this.data = data;
            }
        }

一个双端队列dequeue，类型InternalData，用来索引当前最大值
一个队列，InternalData，是正经的标准队列
一个计数器curIndex， 表示当前已经是加入的第i个数（只增不减）


offer(e): 先用数字和当前索引curIndex初始化一个元素
将该元素加入到queue中
双端队列中放入的是索引
若待加入的元素大于队头元素对应的数字，则清空队列加入该元素
若待加入的元素小于队头元素对应的数，则和队尾元素对应的数的比较：
    若大于队尾对应的数，从队尾出队直到队尾对应的数大于该数，向队尾插入该元素
    若小于队尾对应的数，向队尾插入该元素

poll：出队头，返回其对应的数字

max：
若当前curIndex和队头索引差大于过size，则出deque队直到队头元素索引差小等于size，peek出deque队头对应的数字


```java

    public class MinQueue{

        class InternalData{
            int index;
            int data;

            InternalData(int index, int data){
                this.index = index;
                this.data = data;
            }
        }

        private LinkedList<InternalData> deque = new LinkedList<>();
        private LinkedList<InternalData> queue = new LinkedList<>();
        private int curIndex = 0;
        void offer(int e){
            InternalData n = new InternalData(curIndex, e);
            queue.offer(n);
            if(deque.isEmpty()){
                deque.offerLast(n);
            } else if(e > deque.peekFirst().data){ //大于队头
                deque.clear();
                deque.offerLast(n);
            }else if(e>deque.peekLast().data){ //大于队尾
                while(!deque.isEmpty() && e>deque.peekLast().data){
                    deque.pollLast();
                }
                deque.offerLast(n);
            }else{ //小于队尾
                deque.offerLast(n);
            }
            curIndex++;
        }

        int poll() throws Exception {
            if(queue.isEmpty())
                throw new Exception("queue empty!");

            return queue.poll().data;
        }

        int max(){
            if(queue.isEmpty())
                throw new Exception("queue empty!");
            int window = queue.size();
            while (curIndex - deque.peekFirst().index > window){
                deque.pollFirst();
            }
            return deque.peekFirst().data;

        }

    }

```

建模的第一步是选择合理的数据结构来表述问题，第二步是分析模型中的内在规律，并用编程语言表述这种规律



面试题60.n个骰子的点数：j60-p294
把n个骰子扔到地上，所有骰子朝上的一面的点数和为s，输入n，打印出s所有可能的值出现的概率

本质是一个概率论问题， 点数和最小为 n* 1； 点数和最大为 n * 6； 二者的概率都是 1/(6^n)

方法一：
骰子所有点数的排列数为6^n，如果基于递归求骰子点数，时间效率会很低
定义一个长度为6n-n+1的数组，将和为s点数出现的次数保存到数组的第s-n个元素中。

方法二：基于循环求骰子点数，时间性能好
考虑用两个数组来存储骰子点数的每个总数出现的次数。在第一轮循环中，第一个数组中的第n个数字表示骰子和为n出现的次数。下一轮循环中，加上一个新的骰子，
此时和为n的骰子出现的次数应该等于上一轮循环中骰子点数和为n-1,n-2,n-3,n-4,n-5,n-6的次数的总和，所以我们把另一个数组的第n个数字设为前一个数组对应的第n-1,
n-2,n-3,n-4,n-5,n-6个数字之和

        int[] sum1 = new int[6* n+ 1];
        int[] sum2 = new int[6* n+ 1];
        int cnt = 0;
        //如果是1个骰子，直接计算
        for(int i=1;i<=6;i++){
            sum1[i]=1;
            cnt++;
        }
        boolean isEven = true;
        for(int dieCnt = 2; dieCnt<=n;dieCnt++){ //加入的骰子个数，从第二个开始算
            int[] cur = isEven?sum2:sum1;
            int[] another = cur==sum1?sum2:sum1;
            cnt = 0;
            for(int i=1 * dieCnt;i<=dieCnt * 6; i++){ //和的上限是本次的骰子数 * 6； 下限是骰子数 * 1
                for(int j=1;j<=6;j++){
                    if(i-j<=0) break;
                    cur[i] += another[i-j];
                }
                cnt += cur[i];
            }
            isEven=!isEven;
            Arrays.fill(another, 0); //一定要有这个，把待填充的数组清空，否则会影响到下一次的填充
        }


```java
public class Solution{
    ArrayList dieScore(int n){
        int max = 6* n;
        int[] sum1 = new int[6* n+ 1];
        int[] sum2 = new int[6* n+ 1];

        int cnt = 0;
        //如果是1个骰子，直接计算
        for(int i=1;i<=6;i++){
            sum1[i]=1;
            cnt++;
        }
        boolean isEven = true;
        for(int dieCnt = 2; dieCnt<=n;dieCnt++){ //加入的骰子个数，从第二个开始算
            int[] cur = isEven?sum2:sum1;
            int[] another = cur==sum1?sum2:sum1;
            cnt = 0;
            for(int i=1 * dieCnt;i<=dieCnt * 6; i++){ //和的上限是本次的骰子数 * 6； 下限是骰子数 * 1
                for(int j=1;j<=6;j++){
                    if(i-j<=0) break;
                    cur[i] += another[i-j];
                }
                cnt += cur[i];
            }
            isEven=!isEven;
            Arrays.fill(another, 0); //一定要有这个，把待填充的数组清空，否则会影响到下一次的填充
        }
        int[] sum;
        if(n%2==0){ //如果是偶数，使用sum2
            sum = sum2;
        }else{
            sum = sum1;
        }
        ArrayList<Float> ans = new ArrayList<>();
        for(int i=1*n;i<=6*n;i++){
            ans.add(((float)sum[i])/cnt);
        }
        return ans;
    }

}
```

面试题61.扑克牌中的顺子：j61-p298
从扑克牌中随机抽5张牌，判断是不是一个顺子，即这五张牌是不是连续的，A为1，J为11，Q为12，K为13，大小王可以看成任意数字
可以先把大小王视为0，然后对抽出的5张牌排序，，然后可以用0去补满数组中的空缺，如果相邻的两个数字相隔若干个数字，只要我们有足够的0补满这两个数字间的空缺，那么这个数组实际上还是连续的。 
于是需要做3件事：首先把数组排序；其次统计数组中0的个数；最后统计排序之后相邻数字之间的空缺总数。如果空缺总数小于等于0的个数，那么数组就是连续的，否则不连续

```java
public class Solution {
    public boolean isContinuous(int [] numbers) {
        if(numbers==null || numbers.length==0) return false;
        Arrays.sort(numbers);
        int joker = 0;
        for(int i=0;i<numbers.length;i++){
            if(numbers[i]==0) joker++;
            else if(i>0 && numbers[i-1]!= 0 && numbers[i]-numbers[i-1]!=1){
                if(numbers[i]==numbers[i-1]) return false; //有两个一样的非0牌，一定不是顺子
                int offset = numbers[i]-numbers[i-1]-1;  //需要补上的数字
                if (offset>joker) {
                    return false; //不够补
                }else{
                    joker -= offset; //剩余大小王的个数
                }
            }
        }
        return true;
    }
}
```

面试题62.圆圈中最后剩下的数字：j62-p300
0，1.。。。n-1 这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈中删除第m个数字，求出这个圆圈剩下的最后一个数字

》方法一
有点类似哈夫曼环，可以构建一个循环链表，然后去删即可，由于要维持链表，所以要保留一个pre节点来保存前一个节点，当cur等于pre时，说明环中只有一个节点了
边界情况：n为0或m为0时，没有这样的数字，返回-1

》方法二：
数学方法，具体见 j-p302
f(n,m) = 0                  n=1
         (f(n-1, m)+m)%n    n>1
//代码如下：
last = 0;
for(int i=2;i<=n;i++){
    last = (last+m)%i;
}
return last;




```java
public class Solution {

    class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public int LastRemaining_Solution(int n, int m) {
        if(n==0 || m==0) return -1;
        
        ListNode dummy = new ListNode(-1);
        ListNode pre = dummy;
        for (int i = 0; i < n; i++) {
            ListNode node = new ListNode(i);
            pre.next = node;
            pre = node;
        }
        pre.next = dummy.next; //一个圈，dummy不在环中

        ListNode cur = dummy.next;
        pre = dummy;
        while(cur!=pre){
            //找到第m个节点
            for(int i=0;i<m-1;i++){
                pre = cur;
                cur = cur.next;
            }
            //删除cur指向的节点
            System.out.println("delete node:" + cur.val);
            pre.next = cur.next;
            cur = cur.next;

        }
        return cur.val;

    }
}
```

面试题63.股票的最大利润：j63-p304
假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能的最大利润是多少，
例如，一支股票在某些时间节点的价格为{9,11,8,5,7,12,16,14}。如果我们能在价格为5的时候买入并在价格为16的时候卖出，能收获的最大利润为11

遍历数组，保持一个数字，代表当前遍历过的最小值，若当前数比最小值大，则计算出差，并与最大利润比较，更大则替换；若当前数比最小值小，则替换当前最小值

```java
public class Solution{
    int maxDiff(int[] nums){
        int min = Integer.MAX_VALUE;
        int maxProfit = 0;
        for(int i=0;i<nums.length;i++){
            if(min>nums[i]){
                min = nums[i];
            }else{
                maxProfit = Math.max(maxProfit, nums[i]-min);
            }
        }
        return maxProfit;
    }
}
```

题目64.求1+2+3+...+n：j64-p307
求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。

java创建数组的时候只会分配内存而不会调用数组对象的构造函数，只有当真正new对象的时候才会调用构造器（而C++不同，只要创建数组就会真正去构造对象填充数组，因此只需构造n个对象，利用静态变量记录个数即可）

除了逻辑跳转，java中0不是false，剩下能实现跳转的，就是异常，只要在抵达边界条件时构造出一个异常，转入异常处理的代码，就实现了跳转

```java
public class Solution {
    public int Sum_Solution(int n) {
        return sum(n);
    }

    int sum(int n){
        try{
            int i = 1/n;
            return n+sum(n-1);
        }catch(Exception e){
            return 0;
        }
    }
}
```

题目65.不用加减乘除做加法：j65-p310
写一个函数，求两个整数之和，要求在函数体内不得使用+、-、* 、/四则运算符号。

见371
把两个数看成2进制，

对于二进制，不考虑进位的加运算和异或运算是等价的。

```java
public class Solution {
    public int Add(int num1,int num2) {
        while(num1!=0){
            int c = (num1&num2)<<1;
            int sum = num1^num2;
            num1 = c;
            num2 = sum;
        }
        return num2;
    }
}
```

##不使用新的变量，交换两个变量的值：
基于加减法：
    a = a+b;
    b = a-b;
    a = a-b;

基于位运算：
    a = a^b;
    b = a^b;
    a = a^b;



j66.构建乘积数组：j66-p312
给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。不能使用除法。

定义C[i] = A[0]xA[1]...xA[i-1]
D[i] = A[i+1]xA[i+2]...xA[n-1]

C[i] = C[i-1]xA[i-1]
D[i] = D[i+1]xA[i+1]

C[0]=1;
D[n-1] = 1;

B[i] = C[i]xD[i]

//由于D[i]只与D[i+1]和A[i+1]有关，因此在算出C数组的前提下，只需要一个数就能完成滚动数组

        for(int i=1;i<n;i++){
            C[i] = C[i-1]*A[i-1];
        }

        //由于D[i]只与D[i+1]和A[i+1]有关，因此在算出C数组的前提下，只需要一个数就能完成滚动数组
        int temp = 1;
        int[] B = new int[n];
        B[n-1] = C[n-1];
        for(int i=n-2;i>=0;i--){
            temp = temp * A[i+1];
            B[i] = temp * C[i];
        }
        return B;

```java
import java.util.ArrayList;
public class Solution {
    public int[] multiply(int[] A) {
        int n = A.length;
        int[] C = new int[n];
        C[0] = 1;

        for(int i=1;i<n;i++){
            C[i] = C[i-1]*A[i-1];
        }

        //由于D[i]只与D[i+1]和A[i+1]有关，因此在算出C数组的前提下，只需要一个数就能完成滚动数组
        int temp = 1;
        int[] B = new int[n];
        B[n-1] = C[n-1];
        for(int i=n-2;i>=0;i--){
            temp = temp * A[i+1];
            B[i] = temp * C[i];
        }
        return B;
    }
}
```

j67.把字符串转换成整数：j67-p318
题目描述
将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0
输入描述:
输入一个字符串,包括数字字母符号,可以为空
输出描述:
如果是合法的数值表达则返回该数字，否则返回0
示例1
输入
复制
+2147483647
    1a33
输出
复制
2147483647
    0

该题主考的就是边界条件
1.字符串为空或长度为0时，返回0；
2.如果首字符是+或-，遍历从第1个开始，否则从第0个开始
int sum
遍历：
    如果当前字符不在 '0'到'9'之间，返回0
    boolean min_ready = false; //设置是否已到达边界
    int pre = 0;
    如果sum==Integer.MIN_VALUE/10
        min_ready=true
        pre = sum
    sum = sum * 10 - (c-'0'); //用减法，因为负数范围大于正数
    //抵达最小值，并且新和比旧和大（因为一直是减法，所以新和应该小于旧和）,则溢出了
    if(min_ready && sum >= pre){
        //溢出
        return 0;
    }

如果是负数，返回sum
如果是正数，如果0-sum小于0，则返回0（正数溢出）
          否则返回sum


```java
public class Solution {

    public int StrToInt(String str) {
        if(str==null || str.length()==0) return 0;
        boolean isNav = false;
        if(str.charAt(0)=='-'){
            isNav = true;
        }
        int len = str.length();

        int sum = 0; //先一开始假定是负数，负数比正数的范围大
        int startPos = 0;
        if(str.charAt(0)=='-' || str.charAt(0)=='+'){
            startPos = 1;
        }
        for(int i=startPos;i<len;i++){
            char c = str.charAt(i);
            if(c<'0' || c>'9') return 0;
            boolean min_ready = false;
            int pre = 0;
            //已经快抵达最小值边界了
            if(sum==Integer.MIN_VALUE/10){
                min_ready = true;
                pre = sum;
            }
            sum = sum * 10 - (c-'0');

            //抵达最小值，并且新和比旧和大（因为一直是减法，所以新和应该小于旧和）,则溢出了
            if(min_ready && sum >= pre){
                //溢出
                return 0;
            }
        }
        if(isNav) return sum;
        else{
            int ans = 0-sum;
            if(ans<0) return 0; //如果换号后仍为负数，则是溢出
            else return ans;
        }

    }
}
```


# 寻找树中两个节点的公共最低祖先：j68-p326
如果是普通二叉树，后序遍历到该节点时，栈中元素就是该节点的祖先链（注意还要把自己也算进去）
如果是二叉树，且是二叉搜索树，可以很容易的找出根到两个节点分别的路径，然后从根的那一端开始找，就能找到
对于二叉树进行后续遍历，当遍历到一个节点时，此时栈中的序列就是该节点的祖先链
如果是一棵有指向父节点的指针的树，则转变成求两个链表的第一个公共节点
如果只是一棵普通的树，则可以分别对两个节点进行深度优先搜索，并且保留两个深搜过程中使用的栈，两个栈中就包括了两个节点从根开始的路径，剩下的就很好操作了



j69.数组中重复的数字：j69
题目描述
在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道有几个数字是重复的。也不知道每个数字重复几次。请找出数组中任意一个重复的数字。 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2。

所有的数字都是0到n-1的范围内，所以它们都可以放到自己对应的位置上，如果某个数字对应的位置已经是这个数了，则是一个重复的数(注意里面得是双重循环)

```java
public class Solution {
    // Parameters:
    //    numbers:     an array of integers
    //    length:      the length of array numbers
    //    duplication: (Output) the duplicated number in the array number,length of duplication array is 1,so using duplication[0] = ? in implementation;
    //                  Here duplication like pointor in C/C++, duplication[0] equal *duplication in C/C++
    //    这里要特别注意~返回任意重复的一个，赋值duplication[0]
    // Return value:       true if the input is valid, and there are some duplications in the array number
    //                     otherwise false

    //最终返回的答案放到duplication[0]中
    public boolean duplicate(int numbers[],int length,int [] duplication) {
        if(numbers==null || numbers.length==0) return false;
        int len = numbers.length;
        for(int i=0;i<len;i++){

            while(numbers[numbers[i]]!=numbers[i]){
                int tmp = numbers[numbers[i]];
                numbers[numbers[i]] = numbers[i];
                numbers[i] = tmp;
            }
            //numbers[i]不在其该在的位置上，但是其位置上已经有个该数字了
            if(i!=numbers[i] && numbers[numbers[i]]==numbers[i]){
                duplication[0] = numbers[i];
                return true;
            }
        }
        return false;
    }
}
```

j70
请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。 
例如 
a b c e 
s f c s 
a d e e 

矩阵中包含一条字符串"bcced"的路径，但是矩阵中不包含"abcb"路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。

仍然是trackback，要注意恢复格子遍历状态，防止一个格子只能出现在一个路径中

```java
public class Solution {

    boolean ans = false;
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str){
        if(matrix==null || str==null || str.length==0 || rows<=0 || cols<=0) return false;
        char[][] m = new char[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                m[i][j] = matrix[i*cols+j];
            }
        }
        boolean[][] flag = new boolean[rows][cols];

        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(ans) return ans;
                if(m[i][j]==str[0]){
                    trackBack(m, rows, cols, i,j,str,flag, 0);
                }
            }
        }
        return ans;


    }

    public void trackBack(char[][] m, int rows, int cols, int curX, int curY, char[] str, boolean[][] flag, int i){
        if(ans) return;
        if(i==str.length){
            ans = true;
            return;
        }
        if(curX<0 || curX>=rows || curY<0 || curY >=cols)
            return;

        if(str[i]!=m[curX][curY] || flag[curX][curY]){
            return;
        }
        flag[curX][curY] = true;

        trackBack(m, rows, cols, curX-1, curY, str, flag, i+1);
        trackBack(m, rows, cols, curX+1, curY, str, flag, i+1);
        trackBack(m, rows, cols, curX, curY-1, str, flag, i+1);
        trackBack(m, rows, cols, curX, curY+1, str, flag, i+1);
        flag[curX][curY] = false;
    }
}
```














