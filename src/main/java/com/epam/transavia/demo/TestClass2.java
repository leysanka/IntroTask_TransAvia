package com.epam.transavia.demo;

import java.util.*;
import java.util.stream.Collectors;

public class  TestClass2 extends TestJava{

    private   Integer hello = 123;

    public final static int[] immutableArray = {1,3,7};
    public static final List<Integer> immutableList = //Collections.unmodifiableList(Arrays.asList(1,2,3)); /** To make immutable list*/
            new ArrayList<>(Arrays.asList(1,2,4));
    public static TestClass2 testCl = new TestClass2();

    private transient Integer hello2;

    public TestClass2(Integer hello) {
        this.hello = hello;
    }

    public TestClass2() {
    }

    public  Integer getHello() {
        return hello;
    }

    public  void setHello(Integer hello) {
       this.hello = hello;
    }

    public Collection<String> objUnion() {

        Collection<String> collection = new ArrayList<String>() {{
            add((String) "foo");
            add((String) "bar");
        }};

        return collection;
    }

    public void testRun(){
         new Thread(() -> System.out.println("Hello!")).start();
    }
    public String M1(Object a) {
        return a.toString();
    }



 /*   public String M1(Object a) {
        return a.toString();
    }*/

/*
    public <Object>Collection<Object> objUnion(Collection<Object> a, Collection<Object> b) {

        Collection<Object> collection = new ArrayList<Object>() {{
            add((Object) "foo");
            add((Object) "bar");
        }};;
        return collection;
    }
*/


    public <Object>List<Object> objUnion(Collection<Object> a, Collection<Object> b) {

        List<Object> collection = new ArrayList<Object>() {{
            add((Object) "foo");
            add((Object) "bar");
        }};
        return collection;
    }

    @Override
    public int test() {
        test="123";
        return test.length();
    }


    /** Overload */
    public int test(int a) {
        test="123";
       return test.length();
    }

    public static void main(String[] args) {
      /*  System.out.println(hello);
        TestClass2.hello = 456;
        System.out.println(hello);*/

        TestClass2 testClass2 = new TestClass2();
        testClass2.setHello(5);
        System.out.println(testClass2.getHello());

        TestClass2 testClass3 = new TestClass2();
       System.out.println(testClass3.getHello());
       System.out.println(testClass3.hello2 = 1);
       System.out.println(testClass3.hello2);
       System.out.println(testClass3.test() + " length");

       testClass2.testRun();


        for (int i =0; i < immutableArray.length; i++) {
          immutableArray[i] = ++immutableArray[i];
        }
        System.out.println("immutableArray" + Arrays.stream(immutableArray).boxed().collect(Collectors.toList()));

        for (int i =0; i < immutableList.size(); i++) {
            immutableList.set(i, immutableList.get(i)+1 );
        }
        System.out.println("immutableList" + immutableList);

    }
}

