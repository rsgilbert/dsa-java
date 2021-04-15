package priorityqueue;

import java.util.Comparator;

public class TestHeapPriorityQueue {
    public static void main(String[] args) {
        Comparator<String> stringLengthComparator = (s1, s2) -> {
            if(s1.length() < s2.length()) return -1;
            if(s1.length() == s2.length()) return 0;
            return 1;
        };

        PriorityQueue<String, String> pq = new HeapPriorityQueue<>(stringLengthComparator);
        System.out.println("____Test for Heap Priority Queue__________");
        pq.insert("Ambrose", "Ambrose Gerald");
        pq.insert("Ken", "Ken Mukisa");
        System.out.println(pq.min().getValue()); // Ken
        Entry<String, String> rem = pq.removeMin();
        if(rem != null) {
            System.out.println(rem.getValue()); // Ken
        }
        pq.insert("Bruno", "Bruno Ssenyonjo");
        pq.insert("Queen", "Queen of Buganda");
        pq.insert("Jeferson", "Jeff Mayaga");
        System.out.println(pq.min().getValue()); // Bruno
        System.out.println(pq.size()); // 4
        pq.removeMin();
        System.out.println(pq.min().getValue()); // Queen
        System.out.println(pq.removeMin().getValue()); // Queen
        pq.insert("Cleopatratera", "Cleopatra the Female Pharaoh");
        System.out.println(pq.removeMin().getValue()); // Ambrose
        System.out.println(pq.removeMin().getValue()); // Jeff
        System.out.println(pq.min().getValue()); // Cleopatra
        System.out.println(pq.size()); // 1
        System.out.println(pq.removeMin().getValue()); // cleopatra
        System.out.println(pq.isEmpty()); // true

        // -------- end of testing heap priority queue ---------------
        // -------- test bottom up construction, uses natural ordering ----------------------
        pq = new HeapPriorityQueue<>(new String[] { "jack", "dj", "paulina"}, new String[] {"Jack Ma", "DJ Shiri", "Paulina Vei"});
        System.out.println("********* Testing bottom up construction *********");
        System.out.println(pq.removeMin().getValue()); // dj
        System.out.println(pq.removeMin().getValue()); // jack
        System.out.println(pq.min().getValue()); // paulina
        pq.insert("Ambrose", "Ambrose Gerald");
        System.out.println(pq.min().getValue()); // ambrose
        pq.insert("Ken", "Ken Mukisa");
        System.out.println(pq.removeMin().getValue()); // Ambrose
        pq.insert("Bruno", "Bruno Ssenyonjo");
        pq.insert("Queen", "Queen of Buganda");
        pq.insert("Jeferson", "Jeff Mayaga");
        System.out.println(pq.min().getValue()); // Bruno
        System.out.println(pq.size()); // 5
        pq.removeMin();
        System.out.println(pq.min().getValue()); // jeff
        System.out.println(pq.removeMin().getValue()); // jeff
        pq.insert("Cleopatratera", "Cleopatra the Female Pharaoh");
        System.out.println(pq.removeMin().getValue()); // cleopatra
        System.out.println(pq.removeMin().getValue()); // ken
        System.out.println(pq.min().getValue()); // queen
        System.out.println(pq.size()); // 2
        System.out.println(pq.removeMin().getValue()); // queen
        System.out.println(pq.isEmpty()); // false

    }
}
