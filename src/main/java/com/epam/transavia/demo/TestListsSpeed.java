package com.epam.transavia.demo;

import java.util.*;

public class TestListsSpeed {

    private enum Operations {
        ADD, ADD_POSITION, GET, REMOVE, REMOVE_INDEX, SET
    }


    public static String[] createStringArray(int ofSize) {
        String[] s = new String[ofSize];
        int i = 0;
        while (i < ofSize) {
            s[i] = "hello";
            i++;
        }
        return s;
    }

    private static long calcSpeedOfListOperations(List list, Operations op) {
        long currTime = System.currentTimeMillis();
        switch (op) {
            case GET:
                list.get(list.size()/2);
                break;
            case ADD:
                list.add("insert");
                break;
            case ADD_POSITION:
                list.add(list.size()/2, "insert_middle");
                break;
            case REMOVE:
                list.remove("insert_middle");
                break;
            case REMOVE_INDEX:
                list.remove(list.size()/2);
                break;
            case SET:
                list.set(list.size()/2+5, "SETTer");
                break;
        }
        long afterTime = System.currentTimeMillis();
        return afterTime-currTime;
    }

    private static long calcSpeedOfGetListMid(List list) {
        long currTime = System.currentTimeMillis();
        list.get(list.size()/2);
        long afterTime = System.currentTimeMillis();
        return afterTime-currTime;
    }

    public static final void main(String[] args) {

        String[] strings = createStringArray(1000000);

        List<String> arrayList = new ArrayList<>(Arrays.asList(strings));
        ArrayList<String> arrList = new ArrayList<>(Arrays.asList(strings));
        List<String> linkedList = new LinkedList<>(Arrays.asList(strings));
        LinkedList<String> lnkList = new LinkedList<>(Arrays.asList(strings));


        /**
         * Test Lists Iteration speed
         * */
        ListIterator<String> LstIt = lnkList.listIterator(lnkList.size());
        ListIterator<String> ArrLstIt = arrList.listIterator(arrList.size());

        long iterStart = System.currentTimeMillis();
        for (lnkList.size(); LstIt.hasPrevious();) {
            LstIt.set(LstIt.previous().toUpperCase());
        }
        long iterFinish = System.currentTimeMillis();
        System.out.println((iterFinish-iterStart) + " LnkList: " + lnkList.subList(0,5));

        long iterArrStart = System.currentTimeMillis();
        for (arrList.size(); ArrLstIt.hasPrevious();) {
            ArrLstIt.set(ArrLstIt.previous().toUpperCase());
        }
        long iterArrFinish = System.currentTimeMillis();
        System.out.println((iterArrFinish-iterArrStart) + " ArrayList: " + arrList.subList(0,5));

/*
//        Collections.rotate(lnkList,3);
//        Collections.shuffle(lnkList);
//        System.out.print(lnkList);
//        Iterator<String> It = lnkList.iterator();
          while(LstIt.hasPrevious()){
            String el = LstIt.previous().concat("a");
            LstIt.set(el);
        }
        System.out.println(lnkList); */


        System.out.println("ArrayList Get " + calcSpeedOfListOperations(arrList, Operations.GET));
        System.out.println("LinkedList Get " + calcSpeedOfListOperations(lnkList, Operations.GET));

        System.out.println("ArrayList ADD " + calcSpeedOfListOperations(arrList, Operations.ADD));
        System.out.println("LinkedList ADD " + calcSpeedOfListOperations(lnkList, Operations.ADD));

        System.out.println("ArrayList ADD_Position " + calcSpeedOfListOperations(arrList, Operations.ADD_POSITION));
        System.out.println("LinkedList ADD_Position " + calcSpeedOfListOperations(lnkList, Operations.ADD_POSITION));

        System.out.println("ArrayList Remove " + calcSpeedOfListOperations(arrList, Operations.REMOVE));
        System.out.println("LinkedList Remove " + calcSpeedOfListOperations(lnkList, Operations.REMOVE));

        System.out.println("ArrayList Remove_Index " + calcSpeedOfListOperations(arrList, Operations.REMOVE_INDEX));
        System.out.println("LinkedList Remove_Index " + calcSpeedOfListOperations(lnkList, Operations.REMOVE_INDEX));

        System.out.println("ArrayList Set_Index " + calcSpeedOfListOperations(arrList, Operations.SET));
        System.out.println("LinkedList Set_Index " + calcSpeedOfListOperations(lnkList, Operations.SET));


    }

}
