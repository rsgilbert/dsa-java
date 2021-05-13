package util;

import maps.Entry;

import java.util.ArrayList;
import java.util.List;

public class Helpers<E> {

    public static List<Position<Integer>> toList(Iterable<Position<Integer>> iterable) {
        List<Position<Integer>> list = new ArrayList<>();
        for(Position<Integer> e: iterable) list.add(e);
        return list;
    }
}
