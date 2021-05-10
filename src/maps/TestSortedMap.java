package maps;

public class TestSortedMap {
    // Test sorted map
    public static void main(String[] args) {
        SortedTableMap<Integer, String> sortedMap = new SortedTableMap<>();
        sortedMap.put(4, "Gilbert");
//        printMap(sortedMap);
        printSubMap(sortedMap, 0, 10); // prints only one (4)
        sortedMap.put(3, "Jorge");
        printSubMap(sortedMap, 0, 3); // prints none
//        printMap(sortedMap);
        sortedMap.put(6, "Moses");
//        printMap(sortedMap);
        printSubMap(sortedMap, 5, 10); // prints only one (key 6)
        printSubMap(sortedMap, 0, 40); // prints all (3 entries)
    }


    static void printMap(Map<Integer,String> map) {
        System.out.println("*** Printing ***");
        for(Entry<Integer,String> e: map.entrySet()) {
            System.out.println("Key: " + e.getKey() + " Value: " + e.getValue());
        }
        System.out.println("*** Finished ***");
    }

    static void printSubMap(SortedTableMap<Integer,String> map, int startKey, int endKey) {
        System.out.println("*** Printing ***");
        for(Entry<Integer,String> e: map.subMap(startKey, endKey)) {
            System.out.println("Key: " + e.getKey() + " Value: " + e.getValue());
        }
        System.out.println("*** Finished ***");
    }
}
