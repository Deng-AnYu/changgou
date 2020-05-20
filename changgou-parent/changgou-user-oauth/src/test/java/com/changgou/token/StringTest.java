package com.changgou.token;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-05-19 21:45
 * @description:
 */
public class StringTest {
    @Test
    public void test(){
        String [] arr={"zaa","cds","bfds","ffds","afdsfds"};
        List<String> list = Arrays.asList(arr);
        Collections.sort(list,String.CASE_INSENSITIVE_ORDER);
        System.out.println(list);

    }
    @Test
    public void test2(){
        String [] arr={"zaa","cds","bfds","ffds","afdsfds"};
        Arrays.sort(arr);
        System.out.println(arr);
    }
}
