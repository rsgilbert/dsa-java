package priorityqueue;

import java.util.Comparator;

/**
 * Default comparator for elements with natural ordering
 * @param <E>
 */
public class DefaultComparator<E> implements Comparator<E> {
    public int compare(E a, E b) throws ClassCastException {
        return ((Comparable<E>) a).compareTo(b);
    }
}
