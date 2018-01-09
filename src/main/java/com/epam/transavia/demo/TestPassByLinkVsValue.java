package com.epam.transavia.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestPassByLinkVsValue {

    private static int x =1;
    private static String s ="1";
    private  TestClass2 testClass2 = new TestClass2();

    public  TestClass2 getTestClass2() {
        return testClass2;
    }

    public static void modifyX(){
        x = 2;
    }

   /* public static void modifyX(int testx){
        testx = 2;
    }*/

    public static int modifyX(int testx){
       return testx = 2;
    }


    public static void modifyS(){
        s = "2";
    }

    public static void modifyS(String tests){
        tests = "2";
    }

    public  void modifyTC() {
        testClass2.setHello(345);
    }

    public static void modifyTC(TestClass2 tc) {
        tc.setHello(456); //will change passed object
        tc = new TestClass2(22); //will put a new object reference to copy link
       // tc.setHello(456); //will not change passed object
    }

    public static TestClass2 modifyReturnTC(TestClass2 tc) {
        tc.setHello(456);
        tc = new TestClass2(22);
        return tc;
    }

    public static void main(String[] args) {
        List<TestClass2> list = new ArrayList<TestClass2>();
        TestClass2 tc1 = new TestClass2(), tc2 = new TestClass2(), tc3 = new TestClass2();
        Collections.addAll(list, tc1, tc2, tc3);
        int z=1;

        System.out.println("Before: ");
        System.out.println("x " + x);
        System.out.println("s " + s);
       // System.out.println("hello " + testClass2.hello);

        /* Pass object -by ref*/
        TestPassByLinkVsValue testPassValue = new TestPassByLinkVsValue();
        System.out.println("Before modify TC: " + testPassValue.testClass2.getHello());
        testPassValue.modifyTC();
        System.out.println("After modify TC: " + testPassValue.testClass2.getHello());

        System.out.println("Before modify TC(Tc arg): " + tc2.getHello());
        modifyTC(tc2); //value still does not change
        System.out.println("After modify TC(Tc arg): " + tc2.getHello());
        modifyReturnTC(tc2); //value still does not change
       // tc2 = modifyReturnTC(tc2); //value will be changed
        System.out.println("After modifyReturnTc(Tc arg): " + tc2.getHello());


        System.out.println("List before: " + list.toString());
        List<TestClass2> list2 = new ArrayList<TestClass2>();
        list2.addAll(list);
        list2.add(tc1);

        //list.add(tc3);
        z=5;
        System.out.println("List after: " + list.toString());
        System.out.println("List2 after: " + list2.toString());

        x = modifyX(x);
        modifyS(s);
      //  modifyTC(testClass2);

        System.out.println("After: ");
        System.out.println("x " + x);
        System.out.println("s " + s);
     //   System.out.println("hello " + testClass2.hello);


    }


}
