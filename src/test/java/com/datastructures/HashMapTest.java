package com.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.datastructures.hashmap.HashMap;

import org.junit.jupiter.api.Test;

public class HashMapTest {

    @Test
    public void standardContainsTest() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Test", 100);
        map.put("HashMap", 123);
        assertTrue(map.containsKey("Test"));
        assertTrue(map.containsKey("HashMap"));
        assertTrue(map.containsValue(100));
        assertTrue(map.containsValue(123));
        assertFalse(map.containsKey("test"));
        assertFalse(map.containsKey("hashMap"));
        assertFalse(map.containsValue(101));
    }

    @Test
    public void standardGetTest() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Test", 100);
        map.put("HashMap", 123);
        assertEquals(map.get("Test"), 100);
        assertEquals(map.get("HashMap"), 123);
        assertNotEquals(map.get("Test"), 101);
        assertNotEquals(map.get("HashMap"), 412);
        assertNotEquals(map.get("test"), 100);
    }

    @Test
    public void nullTest() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(null, 2000);
        map.put("Test", null);
        assertTrue(map.containsKey(null));
        assertTrue(map.containsValue(null));
        assertEquals(map.get(null), 2000);
        assertEquals(map.get("Test"), null);
    }

    @Test
    public void updateTest() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Test", 100);
        assertEquals(map.get("Test"), 100);
        map.put("Test", 2300);
        assertEquals(map.get("Test"), 2300);
        assertNotEquals(map.get("Test"), 100);
        map.put("Test", null);
        assertEquals(map.get("Test"), null);
        assertNotEquals(map.get("Test"), 2300);
        map.put(null, 123);
        assertEquals(map.get(null), 123);
        map.put(null, 321);
        assertEquals(map.get(null), 321);
        assertNotEquals(map.get(null), 123);
    }

}
