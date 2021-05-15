package maps;

import util.Entry;

public class TestCostPerformanceDatabase {
    /**
     * Test the implementation of CostPerformanceDatabase class that is
     * used to obtain maxima sets
     *
     **/
    public static void main(String[] args) {
        CostPerformanceDatabase db = new CostPerformanceDatabase();
        db.add(3, 10);
        printEntry(db.best(5)); // 3 10
        db.add(4, 3); // skip
        db.add(6, 5); // skip
        db.add(8, 15); // dont skip
        printEntry(db.best(10)); // 8 15
        printEntry(db.best(6)); // 3 10
        db.add(14, 100); // dont skip
        db.add(1, 2); // dont skip
        db.add(2,0);  // skip
        printEntry(db.best(20)); // 14 100
        printEntry(db.best(2)); // 1 2

    }

    public static void printEntry(Entry<Integer,Integer> entry) {
        System.out.println(entry.getKey() + " " + entry.getValue());
    }
}
