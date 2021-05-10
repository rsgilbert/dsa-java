package maps;

import java.util.SortedMap;

/**
 * Maintains a database of maximal (cost, performance) pairs
 */
public class CostPerformanceDatabase {
    SortedTableMap<Integer, Integer> map = new SortedTableMap<>();

    /**
     * Constructs an initially empty database
     */
    public CostPerformanceDatabase() {}

    /**
     * Returns the (cost, performance) entry with largest cost not exceeding c
     * (or null if no entry exist with cost c or less)
     *
     * Running time: O(log n)
     *
     * @param cost cost to use as query to determine entry with best performance given cost
     * @return entry with best performance whose cost does not exceed @param cost
     */
    public Entry<Integer, Integer> best(int cost) {
        return map.floorEntry(cost);
    }

    /**
     * Adds a new entry with given cost and performance
     *
     * Running time (worst case): O(n), bottleneck is on the map.put call
     *
     * @param c cost
     * @param p performance
     */
    public void add(int c, int p) {
        Entry<Integer, Integer> floor = map.floorEntry(c);
        // If performance of floor is as good as p, ignore
        if(floor != null && floor.getValue() >= p) // Although based on our SortedTableMap implementation, floor can never be null
            return;
        map.put(c, p); // bottleneck, performs in O(n) for new entry
        // Remove any entries dominated by the new entry
        Entry<Integer, Integer> entry = map.higherEntry(c);
        while(entry != null && entry.getValue() <= p) {
            map.remove(entry.getKey());
            entry = map.higherEntry(c);
        }
    }
}
