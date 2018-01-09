package com.epam.transavia.demo;

import java.util.*;

public class TestMapsSpeed {


    private static List<String> values = new ArrayList<>(Arrays.asList(TestListsSpeed.createStringArray(100000)));
//    private static String[] valuesArr = Arrays.asList(TestListsSpeed.createStringArray(100000)).toArray(new String[0]);
    private static Set<String> keys = new HashSet<>(new TestSetsSpeed().createListCollection(100000));

    public HashMap<String, String> createMap() {
        HashMap<String, String> map = new HashMap<>();
        int i = values.size()-1;
        for(Iterator it = keys.iterator(); it.hasNext(); ) {
            map.put(it.next().toString(), values.get(i));
            i--;
        }
        return  map;
    }

    public TreeMap<String, String> createTreeMap() {
        TreeMap<String, String> map = new TreeMap<>();
        int i = values.size()-1;
        for(Iterator it = keys.iterator(); it.hasNext(); ) {
            map.put(it.next().toString(), values.get(i));
            i--;
        }
        return  map;
    }

    public LinkedHashMap<String, String> createLinkedMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        int i = values.size()-1;
        for(Iterator it = keys.iterator(); it.hasNext(); ) {
            map.put(it.next().toString(), values.get(i));
            i--;
        }
        return  map;
    }

    public static long putNewElementSpeed(Map map) {
        long start = System.currentTimeMillis();
        map.put("test", "test");
        long finish = System.currentTimeMillis();
        return finish - start;
    }

    public static long removeElementByKeySpeed(Map map) {
        long start = System.currentTimeMillis();
        map.remove("test");
        long finish = System.currentTimeMillis();
        return finish - start;
    }

    public static void main(String[] args) {

        long mapPutSt = System.currentTimeMillis();
        HashMap map = new TestMapsSpeed().createMap();
        long mapPutEnd = System.currentTimeMillis();
        System.out.println("HashMap: " + map.size() + " time: " + (mapPutEnd-mapPutSt));

        long treemapPutSt = System.currentTimeMillis();
        TreeMap treemap = new TestMapsSpeed().createTreeMap();
        long treemapPutEnd = System.currentTimeMillis();
        System.out.println("TreeMap: " + treemap.size() + " time: " + (treemapPutEnd-treemapPutSt));

        long linkmapPutSt = System.currentTimeMillis();
        LinkedHashMap linkmap = new TestMapsSpeed().createLinkedMap();
        long linkmapPutEnd = System.currentTimeMillis();
        System.out.println("LinkMap: " + linkmap.size() + " time: " + (linkmapPutEnd-linkmapPutSt));

        System.out.println(map.equals(treemap) && map.equals(linkmap));

        System.out.println("HasMap put: " + putNewElementSpeed(map));
        System.out.println("TreeMap put: " + putNewElementSpeed(treemap));
        System.out.println("LinkMap put: " + putNewElementSpeed(linkmap));

   /*     System.out.println(map);
        System.out.println(treemap);
        System.out.println(linkmap);*/

        System.out.println("HasMap remove: " + removeElementByKeySpeed(map));
        System.out.println("TreeMap remove: " + removeElementByKeySpeed(treemap));
        System.out.println("LinkMap remove: " + removeElementByKeySpeed(linkmap));


    }

}
