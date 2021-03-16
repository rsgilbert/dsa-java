package priorityqueue;

public class TestUnsortedPriorityQueue {
    public static void main(String[] args) {
        UnsortedPriorityQueue<Integer, String> unsortedPQ = new UnsortedPriorityQueue<>();
        unsortedPQ.insert(2, "Great");
        System.out.println(unsortedPQ.min().getValue());
        unsortedPQ.insert(5, "Marvelous");
        System.out.println(unsortedPQ.min().getValue());
        unsortedPQ.insert(3, "Nice");
        System.out.println(unsortedPQ.min().getValue());
        unsortedPQ.removeMin();
        System.out.println(unsortedPQ.min().getValue());
        unsortedPQ.removeMin();
        System.out.println(unsortedPQ.size());
        System.out.println(unsortedPQ.min().getValue());



    }
}
