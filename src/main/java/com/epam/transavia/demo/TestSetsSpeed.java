package com.epam.transavia.demo;

import java.util.*;

public class TestSetsSpeed {

    public Collection<String> createListCollection(int ofSize) {
        Collection<String> stringCollection = new ArrayList<>();
        String etalon = "abcdefghijklmnopqrstuvwxyz1234567890";
        while(ofSize > 0) {
            StringBuilder tmp = new StringBuilder();
            Random rnd = new Random();
            Iterator<Integer> it = rnd.ints(0, etalon.length()-1).iterator();
            char[] chars = new char[5];
            for (int i = 0; i < 5; i++) {
                chars[i] = etalon.charAt(it.next());
            }
            tmp.append(chars);
            ofSize--;
            stringCollection.add(tmp.toString());
        }
        return stringCollection;
    }

    public static long speedOfSetOperations(Set set, Operations op) {
        long currTime = System.currentTimeMillis();
        switch (op) {
            case ADDALL:
                set.addAll(new TestSetsSpeed().createListCollection(1000000));
                break;
            case ADD:
                set.add("Test");
                break;
            case CONTAINS:
                set.contains("Test");
                break;
            case REMOVE:
                set.remove("Test");
                break;
            case ITERATE:
                Iterator it = set.iterator();
                int i=0;
                while(it.hasNext()){
                    it.next();
//                    i++;
                }
//                System.out.println("Iterates set i times: " + i);
                break;
        }

        long afterTime = System.currentTimeMillis();
        return afterTime-currTime;
    }

    private enum Operations {
        ADD,  CONTAINS, REMOVE, ITERATE, ADDALL
    }

    public static void main(String[] args) {
//        Collection<String> collection = new TestSetsSpeed().createListCollection(100000);

        HashSet<String> hashSet = new HashSet<>();
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
//        TreeSet<String> treeSet = new TreeSet<String>((o1, o2) -> (o1.length()> o2.length())? -1:1 );
        TreeSet<String> treeSet = new TreeSet<>();

//        System.out.println(hashSet);
//        System.out.println(hashSet.size());
//        System.out.println(linkedHashSet);
//        System.out.println(linkedHashSet.size());
//        System.out.println(treeSet);
//        System.out.println(treeSet.size());


        /*Equals check - order does not matter*/
        System.out.println("All sets are equal: " + (hashSet.equals(linkedHashSet) && hashSet.equals(treeSet)));

        /*Speed test*/
        System.out.println("HashSet AddAll: " + speedOfSetOperations(hashSet, Operations.ADDALL));
        System.out.println("LinkeHashSet AddAll: " + speedOfSetOperations(linkedHashSet, Operations.ADDALL));
        System.out.println("TreeSet AddAll: " + speedOfSetOperations(treeSet, Operations.ADDALL));

        System.out.println("HashSet Add: " + speedOfSetOperations(hashSet, Operations.ADD));
        System.out.println("LinkeHashSet Add: " + speedOfSetOperations(linkedHashSet, Operations.ADD));
        System.out.println("TreeSet Add: " + speedOfSetOperations(treeSet, Operations.ADD));

        System.out.println("HashSet CONTAINS: " + speedOfSetOperations(hashSet, Operations.CONTAINS));
        System.out.println("LinkeHashSet CONTAINS: " + speedOfSetOperations(linkedHashSet, Operations.CONTAINS));
        System.out.println("TreeSet CONTAINS: " + speedOfSetOperations(treeSet, Operations.CONTAINS));

       // System.out.println(treeSet);
        System.out.println(treeSet.first() + " and " + treeSet.size() + " and " + treeSet.last());

        System.out.println("HashSet REMOVE: " + speedOfSetOperations(hashSet, Operations.REMOVE));
        System.out.println("LinkeHashSet REMOVE: " + speedOfSetOperations(linkedHashSet, Operations.REMOVE));
        System.out.println("TreeSet REMOVE: " + speedOfSetOperations(treeSet, Operations.REMOVE));


        System.out.println("HashSet ITERATE: " + speedOfSetOperations(hashSet, Operations.ITERATE));
        System.out.println("LinkeHashSet ITERATE: " + speedOfSetOperations(linkedHashSet, Operations.ITERATE));
        System.out.println("TreeSet ITERATE: " + speedOfSetOperations(treeSet, Operations.ITERATE));

        long time =System.currentTimeMillis();
        /*Test foreach*/
        for (String str: hashSet ) {
            int count=0;
            if (str.startsWith("b")){
                //System.out.println(str);
                str = null;
            }
        }

       /*Test stream*/
      /*  final int[] count = {0};
        hashSet.stream().filter(o -> o.startsWith("b")).forEach(o-> count[0]++);*/

        long endTime = System.currentTimeMillis();
        System.out.println(endTime-time);
//        System.out.println(count[0]);
     //   System.out.println(treeSet);


    }








}
