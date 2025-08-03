package com.weiyi.study;

import java.util.*;

public class Hello {

    public static void main(String... args) {
        int[] arr = {2, 3, 5, 6, 12, 23, 167, 4, 67, 257, 1098, 23, 101, 104, 54, 204, 543, 93};
        // merge_sort(arr,0,arr.length);
        quick_sort(arr);
        Utils.showArrayItems(arr);
    }

    //T(n)=(T(nlogn))
    public static void merge_sort(int[] arr, int begin, int end) {
        int len = end - begin;
        if (len <= 1) return;
//        if(len==2){
//            try_swap(arr,begin,end-1);return;}
        int mid = (end + begin) / 2;
        merge_sort(arr, begin, mid);
        merge_sort(arr, mid, end);
        sort_ordered(arr, begin, mid, end);
    }

    public static void try_swap(int[] arr, int i1, int i2) {
        if (arr[i1] <= arr[i2]) return;
        swap(arr, i1, i2);
    }

    public static void swap(int[] arr, int i1, int i2) {
        if (i1 == i2) return;
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

    //T(n)
    public static void sort_ordered(int[] arr, int begin, int mid, int end) {
        int len = end - begin;
        int[] tmp = new int[len];
        int k1 = begin, k2 = mid, i = 0;
        while (k1 < mid && k2 < end) {
            if (arr[k1] < arr[k2]) {
                tmp[i] = arr[k1];
                k1++;
            } else {
                tmp[i] = arr[k2];
                k2++;
            }
            i++;
        }
        if (k1 < mid) copy(arr, k1, mid, tmp, i);//use k1 remains
        else copy(arr, k2, end, tmp, i);//use k2 remains

        copy(tmp, 0, len, arr, begin);//copy tmp to origin arr.
    }

    public static void copy(int[] arr1, int begin, int end, int[] arr2, int begin2) {
        int i2 = begin2;
        for (int i = begin; i < end; i++)
            arr2[i2++] = arr1[i];//copy arr1 to arr2.
    }

    public static void quick_sort(int[] arr) {
        quick_sort(arr, 0, arr.length);
    }

    public static void quick_sort(int[] arr, int begin, int end) {
        int len = end - begin;
        if (len <= 1) return;
        //int mid=Q_partition(arr,begin,end);
        int mid = Q_partition2(arr, begin, end);
        //int mid = (end + begin)/2;
        quick_sort(arr, begin, mid);
        quick_sort(arr, mid, end);
    }

    //need return mid value index.
    private static int Q_partition(int[] arr, int begin, int end) {
        int len = end - begin;
        int mid_value = arr[end - 1];//randomly pick last one as mid-value.
        int[] tmp = new int[len];
        int j1 = 0;//from begin to end.
        int j2 = len - 1;// from end to begin.
        for (int i = begin; i < end - 1; i++) {
            int v = arr[i];
            if (v <= mid_value) {
                tmp[j1] = v;
                j1++;
            } else {
                tmp[j2] = v;
                j2--;
            }
        }
        tmp[j1] = mid_value;
        copy(tmp, 0, len, arr, begin);//copy tmp to origin arr.
        return begin + j1;
    }

    //need return mid value index.
    private static int Q_partition2(int[] arr, int begin, int end) {
        int i = begin;
        int j = end - 1;
        int left_check = -1;//-1 means check left, 1 means check right.
        //let's take last one as pivot-value.
        while (i < j) {
            while (i < j && arr[i] <= arr[j]) i++; //left to right.
            swap(arr, i, j);
            while (i < j && arr[i] <= arr[j]) j--; //right to left.
            swap(arr, i, j);
        }

        return i;
    }

    private static int find_n(int[] arr, int begin, int end, int n) {
        int i = Q_partition2(arr, begin, end);
        if (i == n) return arr[i];
        if (i == 0 && n < i) return arr[0];
        if (i == arr.length - 1 && n > i) return arr[arr.length - 1];
        if (n < i) return find_n(arr, begin, i, n);
        return find_n(arr, i + 1, end, n);
    }

    public static int find_n(int[] arr, int n) {
        return find_n(arr, 0, arr.length, n);
    }

    public static int find_mid(int[] arr) {
        int len = arr.length;
        if (len % 2 != 0) return find_n(arr, 0, len, len / 2);//odd length.
        //even length.
        int a = find_n(arr, 0, arr.length, len / 2);
        int b = find_n(arr, 0, arr.length, len / 2 - 1);
        System.out.printf("(%d+%d)/2=", a, b);
        return (a + b) / 2;
    }

    private static int moveP(int[] nums, int p, int q) {
        do p++;
        while (p < q && nums[p] == nums[p - 1]);
        return p;
    }

    private static int moveQ(int[] nums, int p, int q) {
        do q--;
        while (p < q && nums[q] == nums[q + 1]);
        return q;
    }

    private static int moveI(int[] nums, int i) {
        do i++;
        while (i < nums.length - 2 && nums[i] == nums[i - 1]);
        return i;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        int i = 0;
        while (i < (nums.length - 2)) {
            if (nums[i] + nums[i + 1] + nums[i + 2] > 0) break;
            int a = nums[i];
            int p = i + 1;
            int q = nums.length - 1;
            while (p < q) {
                int a2 = a + nums[p];
                if (a2 > 0) break;
                int b = a2 + nums[q];
                if (b == 0) {
                    result.add(List.of(a, nums[p], nums[q]));
                    p = moveP(nums, p, q);
                } else if (b < 0) {
                    p = moveP(nums, p, q);
                } else {
                    q = moveQ(nums, p, q);
                }
            }
            i = moveI(nums, i);
        }
        return result;
    }

    public int[] twoSum(int[] numbers, int target) {
        //numbers are sorted.
        for (int i = 0; i < numbers.length - 1; i++) {
            int j = i + 1;
            while (j < numbers.length) {
                int k = numbers[i] + numbers[j];
                if (k == target) return new int[]{i + 1, j + 1}; //Question said it's only one answer!!
                else if (k < target) j++;
                else break;
            }
        }
        return new int[0];
    }

    public int[] twoSum2(int[] numbers, int target) {
        //numbers are sorted.
        int i = 0, j = numbers.length - 1;
        while (i < j) {
            int v = numbers[i] + numbers[j];
            if (v == target) return new int[]{i + 1, j + 1};
            if (v > target) j--;
            else i++;
        }
        return new int[0];
    }

    public int maxArea(int[] height) {
        int max = 0, i = 0, j = height.length - 1;
        while (i < j) {
            int area = Math.min(height[i], height[j]) * (j - i);
            if (area > max) max = area;
            if (height[i] < height[j]) i++;
            else j--;
        }
        return max;
    }

    //42. trapping-rain-water
    public static int trap(int[] height) {
        int len = height.length, max = 0;
        int[] pre = new int[len], suf = new int[len];
        pre[0] = 0;
        for (int i = 1; i < len; i++) {
            pre[i] = Math.max(height[i - 1], pre[i - 1]);//pre[i-1] is max height
        }
        Utils.showArrayItems(pre);
        suf[len - 1] = 0;
        for (int j = len - 2; j >= 0; j--) {
            suf[j] = Math.max(height[j + 1], suf[j + 1]);//suf[j+1] is max height.
        }
        Utils.showArrayItems(suf);
        for (int i = 1; i < len - 1; i++) {
            int h = Math.min(pre[i], suf[i]);
            int h1 = Math.max(h - height[i], 0);
            max += h1;
        }
        return max;
    }

    //42. trapping-rain-water
    public static int trap2(int[] height) {
        int len = height.length, max = 0;
        int i = 1, j = len - 2, left = 0, right = 0;
        while (i <= j) {
            left = Math.max(height[i - 1], left);
            right = Math.max(height[j + 1], right);
            int water = 0;
            if (left < right) {
                water = Math.max(left - height[i], 0);
                i++;
            } else {
                water = Math.max(right - height[j], 0);
                j--;
            }
            max += water;
        }


        return max;
    }

    int sum(int[] nums, int i, int j) {
        int sum = 0;
        for (int k = i; k <= j; k++) {
            sum += nums[k];
        }
        return sum;
    }

    //209. minimum-size-subarray-sum
    public int minSubArrayLen(int target, int[] nums) {
        int i = 0, j = 0, sum = nums[0], minLen = 0;
        while (i <= j && j < nums.length) {
            int len = j - i + 1;
            if (minLen != 0 && len >= minLen) {
                sum -= nums[i];
                i++;
                continue;
            }
            if (sum >= target) {
                if (len == 1) return len;
                if (minLen == 0)
                    minLen = len;
                else
                    minLen = Math.min(minLen, len);
                sum -= nums[i];
                i++;
            } else {
                if (j + 1 < nums.length)
                    sum += nums[j + 1];
                j++;
            }
        }
        return minLen;
    }

    public int minSubArrayLen2(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int ans = nums.length + 1;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target && left <= right) {
                int len = right - left + 1;
                if (len == 1) return len;
                ans = Math.min(ans, len);
                sum -= nums[left];
                left++;
            }
        }

        if (ans != nums.length + 1) {
            return ans;
        }
        return 0;
    }

    //713. subarray-product-less-than-k
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int multi = 1, l = 0, ans = 0;
        for (int r = 0; r < nums.length; r++) {
            multi *= nums[r];
            while (multi >= k && l <= r) {
                multi = multi / nums[l];
                l++;
            }
            if (l <= r) ans += (r - l + 1);
        }
        return ans;
    }

    private Map<Character, Integer> initMap(char[] s) {
        Map<Character, Integer> cnt = new HashMap<>();
        for (char c : s) {
            cnt.put(c, 0);
        }
        return cnt;
    }

    //3. longest-substring-without-repeating-characters
    public int lengthOfLongestSubstring(String s) {
        int ans = 0, l = 0;
        Map<Character, Integer> cnt = initMap(s.toCharArray());
        for (int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            cnt.put(c, cnt.get(c) + 1);
            while (cnt.get(c) > 1 && l <= r) {
                char c1 = s.charAt(l);
                cnt.put(c1, cnt.get(c1) - 1);//left c count minus 1.
                l++;
            }
            if (l <= r) ans = Math.max(r - l + 1, ans);
        }
        return ans;
    }

    //34. find-first-and-last-position-of-element-in-sorted-array
    public int[] searchRange(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        int[] ans = {-1, -1};
        while (l <= r) {
            int mid = (l + r) / 2;
            int v = nums[mid];
            if (v >= target) r = mid - 1;// num in r's right is bigger equal than target.
            else l = mid + 1;
        }
        if (r == nums.length - 1 || nums[l] != target) return ans;//if r not move, then all data smaller than target.
        ans[0] = l;

        r = nums.length - 1;//reuse last left index.
        while (l <= r) {
            int mid = (l + r) / 2;
            int v = nums[mid];
            if (v <= target) l = mid + 1;// num in l's left is smaller equal than target.
            else r = mid - 1;
        }
        ans[1] = r;
        return ans;
    }

    //162. find-peak-element
    public int findPeakElement(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] < nums[mid + 1]) l = mid + 1;
            else r = mid;
        }
        return l;
    }

    //153. find-minimum-in-rotated-sorted-array
    public int findMin(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int pivot = nums[r];
            int mid = (l + r) / 2;
            if (nums[mid] > pivot) l = mid + 1;
            else r = mid;
        }
        return nums[l];
    }

    //33. search-in-rotated-sorted-array
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1, v1 = nums[0];
        while (l < r) {
            int mid = (l + r) / 2;
            int v_mid = nums[mid];
            if ((target >= v1 && v_mid >= v1) || (target < v1 && v_mid < v1)) {
                if (v_mid < target) l = mid + 1;
                else r = mid;
            } else if (target >= v1) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        if (nums[l] == target) return l;
        return -1;
    }

    //206. reverse-linked-list
    public static ListNode reverseList(ListNode head) {
        ListNode pre = null, cur = head;
        while (cur != null) {
            ListNode nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        return pre;
    }

    public MyListNode reverseList1(ListNode head, int len) {
        ListNode pre = null, cur = head, tailNxt = null;
        while (cur != null && len != 0) {
            tailNxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tailNxt;
            len--;
        }
        return new MyListNode(pre, tailNxt);
    }

    //92. reverse-linked-list-ii
    public ListNode reverseBetween(ListNode head, int left, int right) {
        int pos = 1;
        ListNode cur = head, pre = null, ans = head;
        while (pos <= right && cur != null) {
            if (pos == left) {
                MyListNode myList = reverseList1(cur, right - left + 1);
                if (pre == null) ans = myList.head;
                else pre.next = myList.head;
                cur.next = myList.tailNext;
                break;//got answer.
            } else {
                pre = cur;
                cur = cur.next;
                pos++;

            }
        }
        return ans;
    }

    //25. reverse-nodes-in-k-group
    public ListNode reverseKGroup(ListNode head, int k) {
        int len = 0;
        ListNode cur = head;
        while (cur != null) {
            len++;
            cur = cur.next;
        }
        int pos = 1;
        cur = head;
        ListNode pre = null, ans = head;
        while (pos + k - 1 <= len && cur != null) {
            MyListNode myList = reverseList1(cur, k);
            if (pre == null) ans = myList.head;
            else pre.next = myList.head;
            cur.next = myList.tailNext;
            //got answer
            pre = cur;
            cur = cur.next;
            pos += k;
        }
        return ans;
    }

    //876. middle-of-the-linked-list
    public ListNode middleNode(ListNode head) {
        ListNode cur1=head,cur2=head;
        while (cur2!=null&&cur2.next!=null){
            cur2=cur2.next.next;
            cur1=cur1.next;
        }
        return cur1;
    }

    //141. linked-list-cycle
    public boolean hasCycle(ListNode head) {
        if(head==null)return false;
        ListNode cur1 = head, cur2 = head.next;
        while (cur2 != null) {
            if(cur2==cur1)return true;
            cur2 = cur2.next;
            if (cur2 == null) break;
            cur2 = cur2.next;
            cur1 = cur1.next;

        }
        return false;
    }

    public ListNode meetNode(ListNode head) {
        ListNode cur1 = head, cur2 = head;
        while (cur2 != null && cur2.next!=null) {
            cur2 = cur2.next.next;
            cur1 = cur1.next;
            if(cur1==cur2)return cur1;
        }
        return null;
    }
    //142. linked-list-cycle-ii
    public ListNode detectCycle(ListNode head) {
        ListNode meet = meetNode(head);
        if (meet==null)return null;
        ListNode cur1=head, cur2=meet;
        while (true){
            if(cur1==cur2)return cur1;
            cur1=cur1.next;
            cur2=cur2.next;
        }

    }
    //143. reorder-list
    public void reorderList(ListNode head) {
        ListNode middle = middleNode(head);
        ListNode head2 = reverseList(middle);
        ListNode cur1=head, cur2=head2;
        while (cur1!=null&&cur2!=null&&cur2.next!=null){
            ListNode nxt1=cur1.next, nxt2=cur2.next;
            cur1.next=cur2;
            cur2.next=nxt1;
            //
            cur1=nxt1;
            cur2=nxt2;
        }
    }
    //237. delete-node-in-a-linked-list
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
    //19. remove-nth-node-from-end-of-list
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy=new ListNode(0,head);
        ListNode left=dummy, right=dummy;
        int step =0;
        while (step<n){ // move right n step
            right=right.next;
            step++;
        }

        while (right.next!=null){ // move left & right together till right to end.
            right=right.next;
            left=left.next;
        }
        //delete left+1.
        if(left==dummy)return head.next;
        left.next=left.next.next;
        return head;
    }
    //83. remove-duplicates-from-sorted-list
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null)return head;
        ListNode cur = head, nxt=head.next;
        while (nxt!=null){
            if(nxt.val!=cur.val){
                cur.next=nxt;
                cur=nxt;
            }
            nxt=nxt.next;
        }
        cur.next=nxt;
        return head;
    }
    //82. remove-duplicates-from-sorted-list-ii
    public ListNode deleteDuplicatesII(ListNode head) {
        ListNode dummy = new ListNode(-101,head);
        ListNode cur = dummy;
        while (cur.next != null && cur.next.next != null){
            if(cur.next.val==cur.next.next.val){
                cur.next= findNextNoDupNode(cur.next);//change current's next.
            }else cur=cur.next;//change current.

        }
        return dummy.next;
    }
    public ListNode findNextNoDupNode(ListNode node){
        int v=node.val;
        while (node!=null){
            if(node.val!=v)break;
            node=node.next;
        }

        return node;
    }
    //104. maximum-depth-of-binary-tree
    public int maxDepth(TreeNode root) {
        if(root==null)return 0;
        int left = maxDepth(root.left);
        int right =maxDepth(root.right);
        return 1+Math.max(left,right);
    }
    //100. same-tree
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null&&q==null)return true;
        if(p==null||q==null)return false;
        if(p.val!=q.val)return false;
        //when reach here, mean p.val = q.val. check child tree.
        return isSameTree(p.left,q.left)&& isSameTree(p.right,q.right);

    }
    //101. symmetric-tree
    public boolean isSymmetric(TreeNode root) {
        if(root==null)return true;
        return isSymmetric(root.left,root.right);
    }
    public boolean isSymmetric(TreeNode p, TreeNode q) {
        if(p==null&&q==null)return true;
        if(p==null||q==null)return false;
        if(p.val!=q.val)return false;
        //when reach here, mean p.val = q.val. check child tree.
        return isSymmetric(p.left,q.right)&& isSymmetric(p.right,q.left);

    }
    //110. balanced-binary-tree
    public boolean isBalanced(TreeNode root) {
        int height = treeHeight(root);
        return height!=-1;
    }
    public int treeHeight(TreeNode root){
        if(root==null)return 0;
        int left = treeHeight(root.left);
        if(left==-1)return -1;//if subtree is no balanced tree, then whole tree in no balanced tree.
        int right =treeHeight(root.right);
        if(right==-1)return -1;
        if(Math.abs(left-right)>1)return -1;//return -1 means no balanced tree.
        return 1+Math.max(left,right); // return real height means balanced tree.
    }
    //199. binary-tree-right-side-view
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        walkRight(root,0,ans);
        return ans;
    }

    public void walkRight(TreeNode root, int height, List<Integer> ans){
        if(root==null)return ;
        height++;
        if(height>ans.size())ans.add(root.val);
        walkRight(root.right,height,ans);
        walkRight(root.left,height,ans);
    }
    //98. validate-binary-search-tree
    public boolean isValidBST(TreeNode root) {
        return InorderTraversal(root,new long[] {(long)Integer.MIN_VALUE-1});
    }
    public boolean InorderTraversal(TreeNode node, long[] pivot){
        if(node==null)return true;
        if(!InorderTraversal(node.left,pivot))return false;
        if(node.val<=pivot[0])return false;
        pivot[0]=node.val;
        if(!InorderTraversal(node.right,pivot))return false;
        return true;
    }
    // use pre-order traversal
    public boolean isValidBST_1(TreeNode root) {

        return preOrderTraversal(root,(long)Integer.MIN_VALUE-1, (long) Integer.MAX_VALUE+1);
    }
    public boolean preOrderTraversal(TreeNode node, long left, long right){
        if(node==null) return true;
        boolean ans = left<node.val && node.val < right;
        ans = ans && preOrderTraversal(node.left,left, node.val);
        ans = ans && preOrderTraversal(node.right, node.val,right);
        return ans;
    }
    //236. lowest-common-ancestor-of-a-binary-tree
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null)return null;
        if(root==p || root== q) return root;
        TreeNode left=lowestCommonAncestor(root.left,p,q);
        TreeNode right=lowestCommonAncestor(root.right,p,q);
        if(left!=null&&right!=null)return root;
        if(left!=null)return left;
        return right;
    }
    //235. lowest-common-ancestor-of-a-binary-search-tree
    public TreeNode lowestCommonAncestor_1(TreeNode root, TreeNode p, TreeNode q) {
        int v = root.val;
        if((p.val<=v && v<=q.val) ||(q.val<=v && v<=p.val)) return root;
        TreeNode left=null, right=null;
        if(p.val<v) left=lowestCommonAncestor_1(root.left,p,q);
        else right=lowestCommonAncestor_1(root.right,p,q);
        if(left!=null)return left;
        return right;
    }
}