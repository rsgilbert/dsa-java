package priorityqueue;

import java.util.Comparator;

public class TestSortedPriorityQueue {
    public static void main(String[] args) {
        Comparator<String> stringLengthComparator = (s1, s2) -> {
            if(s1.length() < s2.length()) return -1;
            if(s1.length() == s2.length()) return 0;
            return 1;
        };

        PriorityQueue<String, String> sortedPQ = new SortedPriorityQueue<>(stringLengthComparator);
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
    }
}
