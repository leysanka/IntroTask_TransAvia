package com.epam.transavia.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestStreamCollection {


    public static void main(String[] args) {
        String[] s1 = {"aaa", "bbbv", "ggggg"};
        String[] s2 = {"aaa", "bbbv", "ccccc", "123456"};

        List<String> list = new ArrayList<>(Arrays.asList(s1));
        List<String> list1 = new ArrayList<>(Arrays.asList(s2));

        list.addAll(list1);


       List<String> s = new ArrayList<String>();
       List<String> a = new ArrayList<String>();
       a.addAll(list);

       list.stream().filter(e -> e.length() > 4).forEach(e -> {
           s.add(e);
           a.remove(e);
       });
       System.out.println(s);
       System.out.println(a);

       System.out.println(list.stream().anyMatch(e -> e.contains("2")? true: false));



    }

}
