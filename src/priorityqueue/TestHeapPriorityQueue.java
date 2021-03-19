package priorityqueue;

import java.util.Comparator;

public class TestHeapPriorityQueue {
    public static void main(String[] args) {
        Comparator<String> stringLengthComparator = (s1, s2) -> {
            if(s1.length() < s2.length()) return -1;
            if(s1.length() == s2.length()) return 0;
            return 1;
        };

        PriorityQueue<String, String> sortedPQ = new HeapPriorityQueue<>(stringLengthComparator);
        System.out.println("____Test for Heap Priority Queue__________");
        sortedPQ.insert("Ambrose", "Ambrose Gerald");
        sortedPQ.insert("Ken", "Ken Mukisa");
        System.out.println(sortedPQ.min().getValue()); // Ken
        Entry<String, String> rem = sortedPQ.removeMin();
        if(rem != null) {
            System.out.println(rem.getValue()); // Ken
        }
        sortedPQ.insert("Bruno", "Bruno Ssenyonjo");
        sortedPQ.insert("Queen", "Queen of Buganda");
        sortedPQ.insert("Jeferson", "Jeff Mayaga");
        System.out.println(sortedPQ.min().getValue()); // Bruno
        System.out.println(sortedPQ.size()); // 4
        sortedPQ.removeMin();
        System.out.println(sortedPQ.min().getValue()); // Queen
        System.out.println(sortedPQ.removeMin().getValue()); // Queen
        sortedPQ.insert("Cleopatratera", "Cleopatra the Female Pharaoh");
        System.out.println(sortedPQ.removeMin().getValue()); // Ambrose
        System.out.println(sortedPQ.removeMin().getValue()); // Jeff
        System.out.println(sortedPQ.min().getValue()); // Cleopatra
        System.out.println(sortedPQ.size()); // 1
        System.out.println(sortedPQ.removeMin().getValue()); // cleopatra
        System.out.println(sortedPQ.isEmpty()); // true

    }
}
