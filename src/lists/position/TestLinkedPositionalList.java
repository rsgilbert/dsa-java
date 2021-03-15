package lists.position;

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
    }
}
