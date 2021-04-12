package com.datastructures.hashmap;

import java.util.ArrayList;

import com.datastructures.utils.Pair;

public class HashMap<K,V> {
    
    private int CAPACITY = 10;
    private final float LOAD_FACTOR;
    private ArrayList<Bucket<K,V>> storage;
    private int size;
    private float bucketUsed = 0f;

    public HashMap() {
        this.storage = initializeStorageWithCapacity(CAPACITY);
        LOAD_FACTOR = 0.75f;
    }

    public HashMap(int loadFactor) {
        this.storage = initializeStorageWithCapacity(CAPACITY);
        this.LOAD_FACTOR = loadFactor;
    }

    public HashMap(int initialCapacity, int loadFactor) {
        this.CAPACITY = initialCapacity;
        this.storage = initializeStorageWithCapacity(this.CAPACITY);
        this.LOAD_FACTOR = loadFactor;
    }

    private ArrayList<Bucket<K,V>> initializeStorageWithCapacity(int capacity) { 
        ArrayList<Bucket<K,V>> newStorage = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) newStorage.add(new Bucket<K,V>());
        return newStorage;
    }

    private int calculateHash(K key) {
        return key.hashCode();
    }

    private int calculateIndex(K key) {
        // if key is null, then its located in the first bucket.
        if (key == null) return 0;

        int keyHash = calculateHash(key);
        
        // apply shift mask to resolve negative hash
        return (keyHash & 0x7fffffff) % CAPACITY;
    }

    private void updateCapacity(int newCapacity) {
        this.CAPACITY = newCapacity;
    }

    private void validateLoadFactorAndResize() {
        if (bucketUsed / storage.size() > LOAD_FACTOR) {
            updateCapacity(this.CAPACITY * 2);
            ArrayList<Bucket<K,V>> newStorage = initializeStorageWithCapacity(this.CAPACITY);
            for (Bucket<K,V> bucket : storage) {
                bucket.nodes.forEach(n -> {
                    int newIndex = calculateIndex(n.originalKey);
                    newStorage.get(newIndex).nodes.add(n);
                });
            }
        }
    }

    

    /**
     * Insert kvp if key cannot be found in HashMap. If key exists, then value will be updated.
     */
    public void put(K key, V value) {

        // Calculate hash from key and use hash to calculate which bucket to put the kvp in.
        // If key is null, index is 0.
        int index = calculateIndex(key);
        // Non null here as buckets are always initialized when storage is initialized/resized.
        Bucket<K,V> bucket = storage.get(index);
        boolean isUpdated = bucket.upsertNodeWithKVP(key, value);
        
        // If put triggers an insert and not an update, increase size counter by 1.
        if (!isUpdated) size += 1;

        // Checks if inserting new kvp exceeds the load factor.
        // If load factor is exceeded, increase capacity and restructure nodes.
        validateLoadFactorAndResize();
    }

    /**
     * Returns true if key exists in HashMap, else return false.
     */
    public boolean containsKey(K key) {
        int index = calculateIndex(key);
        return storage.get(index)
                .containsKey(key);
    }

    /**
     * Returns true if at least one value exists in HashMap, else return false.
     */
    public boolean containsValue(V value) {
        for (Bucket<K,V> bucket : storage) {
            if (bucket.containsValue(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns number of kvp in HashMap.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if HashMap is empty, else return false.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Removes key and associated value from HashMap.
     * Returns removed value if key exists, else return null.
     */
    public V remove(K key) {
        int index = calculateIndex(key);
        Bucket<K,V> bucket = storage.get(index);
        Pair<Boolean,V> pairedValue = bucket.removeValue(key);

        // if value is not null, then decrement HashMap size by one.
        if (pairedValue.getKey()) this.size -= 1;
        return pairedValue.getValue();
    }

    public V get(K key) {
        int index = calculateIndex(key);
        Bucket<K,V> bucket = storage.get(index);
        Node<K,V> node = bucket.getNodeByKey(key);
        return node == null ? null : node.value;
    }

}