package com.epam.transavia.demo;

import java.util.*;
import java.util.stream.Collectors;


public abstract class TestJava {
    public String test;

    public abstract int test();

    public static void main(String[] args) {

       /* TestClass2 testClass2 = new TestClass2();
        TestClass2.setHello(6);
        System.out.println(TestClass2.hello);*/

       // List<Integer> list = Arrays.asList(1, 5, 7, 9);
     /*   List<Integer> list = new ArrayList<>();
        Collections.addAll(list,1, 2, 3 );

        Iterator it = list.iterator();
        if (it.hasNext()) {
            it.next();
            it.remove();
        }
        System.out.println(list.toString());*/

        /*list.forEach(item -> System.out.println(item));
        int array[] = {1, 3, 5};

        System.out.println(Array.getLength(array));
        for (int item :
                array) {
            System.out.println("Array it:" + item);
        }

        ArrayList arraylistobject = new ArrayList<>();
        arraylistobject.add(1);
        arraylistobject.add("123");
        arraylistobject.add(123.35);
        System.out.println("ArraylistObject" + arraylistobject.toString());
        arraylistobject.forEach( item -> System.out.println(item));
*/

   /*     LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addAll(list);
        System.out.println(linkedList.getFirst());
        System.out.println(linkedList.getLast());
        System.out.println(linkedList.offerFirst(4));
        System.out.println(linkedList.getFirst());
        System.out.println("Peak" + linkedList.removeFirst());
        System.out.println(linkedList.getFirst());
        System.out.println(linkedList.getLast());
        linkedList.spliterator().forEachRemaining(o-> System.out.print(o));*/

        /*Iterable<Integer> listIter = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        listIter.forEach(o-> System.out.println(o));
        Collection<Integer> listColl = new ArrayList<>(Arrays.asList(1, 2, 3, 4));*/

      /*  List<String> listStr = Arrays.asList("a", "b", "c", "c");
        Set<String> uniqueSet = new HashSet<>();
        uniqueSet.addAll(listStr);
        uniqueSet.add("a");
        uniqueSet.forEach(o-> System.out.println(o));
*/
      /*  int bitmask = 0x000F;
        int val = 0x2222;
        // prints "2"
        System.out.println(val & bitmask);*/

/*        String a = "quadratic", b = "complexity";
        boolean hasSame = false;
        outer:
        for (int i = 0; i < a.length(); ++i) {
            for (int j = 0; j < b.length(); ++j) {
                if (a.charAt(i) == b.charAt(j)) {
                    hasSame = true;
                    break outer;
                }
            }
        }
        System.out.println("Same " + hasSame);*/

 /*       int a =1;
        double b =2;
        long с = a+b;

        long d =1;
        int e =2;
        double f = d+e;

        char j =1;
        char h =2;
        char i = (j+h);
*/

      //  System.out.println(7/-4 + "  Остаток");

        int[] a = {1,2,3};
        int[] b = {6,9,3};

        List<Integer> aI = Arrays.stream(a).boxed().collect(Collectors.toList());
        List<Integer> bI = Arrays.stream(b).boxed().collect(Collectors.toList());
        aI.addAll(bI);
        //aI.retainAll(bI);
        aI.sort(Comparator.naturalOrder());

        System.out.println("AI" + aI);


        Collection<String> strColl = new ArrayList<>(Arrays.asList("111", "444", "111", null));
        System.out.println(strColl);
        strColl.removeAll(Collections.singleton(null));
        /*Convert list to String array*/
        String[] s = strColl.toArray(new String[0]);
        /*Convert list to Integer array*/
        Integer[] i = strColl.stream().mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
        int[] ints = strColl.stream().mapToInt(Integer::parseInt).toArray();


        System.out.println(strColl);
        /*Printing arrays via stream: String[], Integer[], int[] */
        System.out.println(Arrays.stream(s).collect(Collectors.toList()));
        System.out.println(Arrays.stream(i).collect(Collectors.toList()));
        System.out.println(Arrays.stream(ints).boxed().collect(Collectors.toList()));


/*        HashSet hashSetAB = new HashSet(aI);
        System.out.println("HashSetAB" + hashSetAB.toString());
        System.out.println("TreeSet" + new TreeSet<>(aI));

        Collection<String> listStr1 = Arrays.asList("a", "b", "c", "c");
        Collection<String> listStr2 = Arrays.asList("a", "b", "c", "c");
        Collection<Integer> listStr3 = Arrays.asList(1,2,3);
        Collection<String> listStrUnion = symmetricUnion(listStr1, listStr2);
        HashSet set = new HashSet<>(listStrUnion);
        System.out.println("Set" + set.toString());*/
       // Collection<Object> listDiffrUnion = objUnion(listStr1, listStr3);
       /* System.out.println("Union" + listStrUnion.toString());

        System.out.println("Intersection" + symmetricIntersection(listStr1, listStr2));*/
       // System.out.println(listDiffrUnion.toString());


/*        String s;

        Map map = new HashMap();
        map.put(set,listStr1);
        map.put(set.toArray()[1],listStr1);
        map.put(set.toArray()[2],listStr2);
        map.entrySet().forEach(o -> System.out.println(o));


        HashSet<Map.Entry<String,String>> hashSet = new HashSet<>();
        hashSet.add(new AbstractMap.SimpleEntry("a", "b"));
        hashSet.add(new AbstractMap.SimpleEntry("a", "d"));
        hashSet.add(new AbstractMap.SimpleEntry("c", "d"));
        System.out.println("HashSet" + hashSet);*/


 /*       Map fromHashSet = new HashMap(hashSet.size());
        for (Map.Entry entry :
                hashSet) {
            fromHashSet.put(entry.getKey(), entry.getValue());
        }
        System.out.println("HashMapFromHashSet " + fromHashSet.entrySet());
        System.out.println(fromHashSet.keySet());
        System.out.println(fromHashSet.values());
        */
      /*  IdentityHashMap fromHashSet = new IdentityHashMap(hashSet.size());
        for (Map.Entry entry :
                hashSet) {
            fromHashSet.put(entry.getKey(), entry.getValue());
        }
        System.out.println("HashMapFromHashSet " + fromHashSet.entrySet());
        System.out.println(fromHashSet.keySet());
        System.out.println(fromHashSet.values());*/

        //int a = tryFinallyTest(list);
       /* System.out.println("This is a: " + tryFinallyTest(list));*/
    }

    public static  <T> Collection<T> symmetricIntersection(Collection<T> a, Collection<T> b) {
        Collection<T> intersection = new ArrayList<>(a);
        intersection.retainAll(b);
        return intersection;
    }

    public <Object> Collection<Object> objUnion(Collection<Object> a, Collection<Object> b) {
        Collection<Object> union = new ArrayList<>(a);
        union.addAll(b);
        return union;
    }

  public static  <T> Collection<T> symmetricUnion(Collection<T> a, Collection<T> b) {
        Collection<T> union = new ArrayList<>(a);
        union.addAll(b);
        return union;
    }

    public Object M1(Object a) {
        return a;
    }

 /*   private static int tryFinallyTest(List<Integer> list) {
        try {
            for (int item : list) {
                switch (item) {
                    case 1: {
                        System.out.println("1");
                        return 1;
                    }
                    case 9: {
                        System.out.println("9");
                        return 9;
                    }
                }
            }
        } finally {
            System.out.println("100");
            return 100;
        }
    }*/

}
