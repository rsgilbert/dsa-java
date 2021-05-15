package tests.maps;


import util.Entry;
import maps.SortedMap;
import maps.SortedTableMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class SortedTableMapTest {
    SortedMap<Integer, Integer> map;

    @Before
    public void createMap() {
        map = new SortedTableMap<>();
        map.put(1, 3);
        map.put(2, 10);
    }

    // -- sorted map specific --
    @Test
    public void firstEntry() {
        assertThat(map.firstEntry().getKey().equals(1));
        map.put(0, 5);
        assertThat(map.firstEntry().getKey()).isEqualTo(0);
        assertThat(map.firstEntry().getValue()).isEqualTo(5);
    }

    @Test
    public void lastEntry() {
        assertThat(map.lastEntry().getKey()).isEqualTo(2);
        map.put(-4, 50);
        map.put(5, 0);
        map.put(6, 3);
        map.put(4, 8);
        assertThat(map.lastEntry().getValue()).isEqualTo(3);
    }

    @Test
    public void ceilingEntry() {
        assertThat(map.ceilingEntry(1).getKey()).isEqualTo(1);
        assertThat(map.ceilingEntry(2).getKey()).isEqualTo(2);
        assertThat(map.ceilingEntry(100)).isNull();
        map.put(8, 4);
        map.put(5, 3);
        assertThat(map.ceilingEntry(6).getKey()).isEqualTo(8);
    }

    @Test
    public void floorEntry() {
        assertThat(map.floorEntry(1).getKey()).isEqualTo(1);
        assertThat(map.floorEntry(2).getKey()).isEqualTo(2);
        assertThat(map.floorEntry(0)).isNull();
        assertThat(map.floorEntry(2000).getKey()).isEqualTo(2);
    }

    @Test
    public void lowerEntry() {
        assertThat(map.lowerEntry(2).getKey()).isEqualTo(1);
        assertThat(map.lowerEntry(1)).isNull();
        assertThat(map.lowerEntry(5).getKey()).isEqualTo(2);
        assertThat(map.lowerEntry(-5)).isNull();
    }

    @Test
    public void higherEntry() {
        assertThat(map.higherEntry(1).getKey()).isEqualTo(2);
        assertThat(map.higherEntry(2)).isNull();
        assertThat(map.higherEntry(-199).getKey()).isEqualTo(1);
    }

    @Test
    public void subMap() {
        Iterator<Entry<Integer, Integer>> it = map.subMap(0, 3).iterator();
        int k1 = it.next().getKey();
        int k2 = it.next().getKey();
        assertThat(it.hasNext()).isFalse();
        assertThat(k1).isEqualTo(1);
        assertThat(k2).isEqualTo(2);
        map.put(5, 10);
        map.put(8, 20);
        map.put(12, 30);
        map.put(15, 3);
        map.put(16, 5);
        map.subMap(7, 12);
        List<Entry<Integer,Integer>> list = toList(map.subMap(7, 15));
        List<Integer> keyList = list.stream()
                .map(Entry::getKey)
                .collect(Collectors.toList());
        assertThat(keyList).contains(8, 12);
        assertThat(keyList).doesNotContain(15);
        assertThat(keyList).hasSize(2);
        assertThat(keyList.get(0)).isEqualTo(8);
    }

    @Test
    public void entrySet() {
        List<Entry<Integer,Integer>> list = toList(map.entrySet());
        assertThat(list).hasSize(2);
        assertThat(list.get(0).getKey()).isEqualTo(1);
    }

    // -- end of sorted map specific --

    // -- map specific --
    @Test
    public void get() {
        assertThat(map.get(1)).isEqualTo(3);
        assertThat(map.get(2)).isNotEqualTo(5);
        assertThat(map.get(3)).isNull();
    }

    @Test
    public void size() {
        assertThat(map.size()).isEqualTo(2);
        map.put(1, 100);
        assertThat(map.size()).isEqualTo(2);
        map.put(5, 50);
        assertThat(map.size()).isEqualTo(3);
        map.remove(5);
        assertThat(map.size()).isEqualTo(2);
        map.remove(2);
        assertThat(map.size()).isEqualTo(1);
        map.remove(10);
        assertThat(map.size()).isEqualTo(1);
    }

    @Test
    public void put() {
        map.put(1, 50);
        assertThat(map.get(1)).isEqualTo(50);
        map.put(200, 5);
        assertThat(map.get(200)).isEqualTo(5);
    }

    @Test
    public void remove() {
        assertThat(map.remove(100)).isNull();
        int s1 = map.size();
        assertThat(map.remove(1)).isEqualTo(3);
        assertThat(map.size()).isEqualTo(s1 - 1);
    }
    // -- end of map specific --


    public List<Entry<Integer, Integer>> toList(Iterable<Entry<Integer,Integer>> iterable) {
        List<Entry<Integer,Integer>> list = new ArrayList<>();
        for(Entry<Integer, Integer> e: iterable) list.add(e);
        return list;
    }

}


