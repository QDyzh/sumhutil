package com.test.node;

import org.apache.poi.util.StringUtil;
import top.sumhzehn.util.StrUtil;

/**
 * @author SumhZehn
 * 2021/10/15 15:22
 **/
public class ListNode {
    public int val;
    public ListNode next = null;

    public static ListNode init(int[] arr) {
        ListNode node = null;
        for (int i = 0; i < arr.length; i++) {
            if(node == null) {
                node = new ListNode(arr[i]);
            } else {
                node = new ListNode(arr[i], node);
            }
        }
        return node;
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        String res = "";
        ListNode t = this;
         do{
            res += t.val;
            t = t.next;
        }while (t != null);
        return StrUtil.isEmpty(res) ? "--" : res;
    }
}
