package com.weiyi.study;

import lombok.val;
import lombok.var;

public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    static ListNode create(int[] arr){
        ListNode nxt=null, head=null;
        for (int i=arr.length-1;i>=0;i--){
            head = new ListNode(arr[i],nxt);
            nxt=head;
        }
        return head;
    }
    static void show(ListNode head){
        var cur = head;
        val sb = new StringBuilder("[");
        while (cur!=null){
            sb.append(cur.val);
            sb.append(',');
            cur=cur.next;
        }
        val last=sb.length()-1;
        if(sb.charAt(last)==',')sb.deleteCharAt(last);
        sb.append(']');
        System.out.println(sb);
    }
}
