package tests.maps;


import maps.Map;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProbeHashMap {
    Map<Integer, Integer> map;

    @Before
    public void createMap() {
        map = new maps.ProbeHashMap<>();
        map.put(1, 3);
        map.put(2, 10);
    }

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


}


