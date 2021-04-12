package com.datastructures.hashmap;

public class Node<K,V> {
    
    final K originalKey;
    V value;

    public Node(K originalKey, V value) {
        this.originalKey = originalKey;
        this.value = value;
    }


}