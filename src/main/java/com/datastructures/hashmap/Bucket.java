package com.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Iterator;

import com.datastructures.utils.Pair;

public class Bucket<K,V> {
    
    final ArrayList<Node<K,V>> nodes;

    public Bucket() {
        this.nodes = new ArrayList<>();
    }

    boolean deleteNodeWithKey(K key) {
        if (key == null) return false;
        return nodes.removeIf(n -> n.originalKey.equals(key));
    }

    /**
     * Insert new node if no key is found in bucket, else update value of existing key.
     * Returns true if updated, else return false.
     */
    boolean upsertNodeWithKVP(K key, V value) {
        boolean isUpdated = false;
        
        // Update node with specified value when key exists.
        Node<K,V> node = getNodeByKey(key);
        if (node != null) {
            node.value = value;
            isUpdated = true;
        }

        // Insert new node with specified KVP when key is not found.
        if (!isUpdated) nodes.add(new Node<K,V>(key, value));
        return isUpdated;
    }

    /**
     * Returns null when key does not exist in HashMap.
     */
    Node<K,V> getNodeByKey(K key) {
        for (Node<K,V> node : nodes) {
            if (key == null & node.originalKey == null) {
                return node;
            } else if (node.originalKey != null && node.originalKey.equals(key)) {
                return node;
            }
        }
        return null;
    }

    boolean containsKey(K key) {
        Node<K,V> node = getNodeByKey(key);
        return node != null;
    }

    boolean containsValue(V value) {
        for (Node<K,V> node : nodes) {
            if (value == null && node.value == null) {
                return true;
            } else if (node.value != null && node.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    Pair<Boolean, V> removeValue(K key) {
        Iterator<Node<K,V>> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            Node<K,V> node = iterator.next();
            if (node.originalKey.equals(key)) {
                iterator.remove();
                return new Pair<Boolean, V>(Boolean.TRUE, node.value);
            }
        }

        // Use Pair object to store boolean value since need to 
        // differentiate a null value from an existing key from a null value from an nonexistant key.
        return new Pair<Boolean, V>(Boolean.FALSE, null);
    }
}
