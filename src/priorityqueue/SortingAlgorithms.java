package priorityqueue;

import lists.position.PositionalList;

public class SortingAlgorithms {
    /**
     * Sorts sequence in ascending order using initially empty priority queue P to produce the order
     * @param S sequence
     * @param P priority queue
     * @param <E> element
     */
    public static <E> void pqSort(PositionalList<E> S, PriorityQueue<E,?> P) {
        int n = S.size();
        for (int j = 0; j < n; j++) {
            E element = S.remove(S.first());
            P.insert(element, null);
        }
        for(int j = 0; j < n; j++) {
            S.addLast(P.removeMin().getKey());
        }
    }
}
