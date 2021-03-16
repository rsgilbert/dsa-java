package lists.position;

import java.util.Iterator;

public class TestLinkedPositionalList {
    public static void main(String[] args) {
        LinkedPositionalList<String> list = new LinkedPositionalList<>();
        Position<String> johnPos = list.addFirst("John");
        list.addFirst("Ben");
        list.addLast("Paul");
        System.out.println(list.first().getElement());
        System.out.println(list.isEmpty());
        System.out.println(list.size());
        System.out.println(list.before(johnPos).getElement());
        System.out.println(list.after(johnPos).getElement());
        System.out.println(list.last().getElement());

        // Iterate ove elements
        System.out.println("----- element iterator --------");
        for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
            String s = it.next();
            System.out.println(s);
        }

        // Iterate over positions
        System.out.println("--------- positions iterable ---------");
        for(Position<String> pos: list.positions()) {
            System.out.println(pos.getElement());
        }
    }
}
