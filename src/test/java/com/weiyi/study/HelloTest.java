package com.weiyi.study;

import lombok.val;
import lombok.var;
import org.junit.Test;
public class HelloTest {
    int[] arr = {2,3,5,6,12,23,167,4,67,257,1098,23,101,104,54,204,543,93};
    int[] arr1 = {2,3,5,6,12,23,167,4,67,257,1098,23,101,104,54,204,543,93,14};
    int[] arr2 = {2,3,5,6,12,23,167,4,67,257,-14,23,101,0,54,204,543,93,14};
    int[] arr3 = {-1,0,1,2,-1,-4};
    @Test
    public void find_n(){
        int k=Hello.find_n(arr,-1);
        System.out.println(k);
    }
    @Test
    public void find_mid(){
        int k=Hello.find_mid(arr);
        System.out.println(k);
        int k1=Hello.find_mid(arr1);
        System.out.println(k1);
    }
    @Test
    public void threeSum(){
        val k=Hello.threeSum(arr3);
        System.out.println(k);

    }
    @Test
    public void trap(){
        int[] arr1 = {0,1,0,2,1,0,1,3,2,1,2,1};
        var k=Hello.trap(arr1);
        System.out.println(k);
        k=Hello.trap2(arr1);
        System.out.println(k);

    }
}
