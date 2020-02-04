//和为s的连续整数序列
```java
public ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
    ArrayList<ArrayList<Integer>> res = new ArrayList<>();
    if(sum<3){
        return res;
    }
    int small = 1;
    int big = 2;
    int tempRes = small+big;//当前序列的和
    ArrayList<Integer> tempList = new ArrayList<>();//缓存当前序列
    tempList.add(small);
    tempList.add(big);
    while(small <= sum/2){//至少两个数的和为sum，所以small不能超过sum的一半
        if(tempRes<=sum){
            if(tempRes==sum){
                res.add(new ArrayList<>(tempList));
            }
            big++;
            tempRes += big;
            tempList.add(big);
        }
        else if(tempRes>sum){
            tempRes-=small;
            tempList.remove((Object)small);
            small++;
        }
    }
    return res;
}
```