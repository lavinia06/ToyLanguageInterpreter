package model.ADT.Dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<TKey, TValue> implements DictionaryInterface<TKey, TValue> {

    private Map<TKey, TValue> dictionary;

    public MyDictionary(){this.dictionary = new HashMap<TKey, TValue>();}

    @Override
    public String toString(){
        String representation = "[ \n";
        Collection<TKey> allKeys = dictionary.keySet();
        for(TKey key: allKeys){
            representation += (key.toString() + ": " + dictionary.get(key) + ", \n");
        }
        representation += "]";
        return representation;
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    @Override
    public boolean containsKey(TKey tKey) {
        return dictionary.containsKey(tKey);
    }

    @Override
    public boolean containsValue(TValue value) {
        return dictionary.containsValue(value);
    }

    @Override
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    @Override
    public void update(TKey tKey, TValue newValue) {
        this.dictionary.replace(tKey, newValue);
    }

    @Override
    public void insert(TKey tKey, TValue newValue) {
        this.dictionary.put(tKey, newValue);
    }

    @Override
    public void clear() {
        this.dictionary.clear();
    }

    @Override
    public TValue getValue(TKey tKey) {
        return this.dictionary.get(tKey);
    }

    @Override
    public TValue remove(TKey tKey) {
        return this.dictionary.remove(tKey);
    }

    @Override
    public Collection<TValue> getAllValues() {
        return this.dictionary.values();
    }

    @Override
    public Collection<TKey> getAllKeys() {
        return this.dictionary.keySet();
    }

    @Override
    public Map<TKey, TValue> getContent() {
        return this.dictionary;
    }

    @Override
    public DictionaryInterface<TKey, TValue> copy() {
        DictionaryInterface<TKey, TValue> newDictionary = new MyDictionary<>();

        for(TKey key: this.dictionary.keySet()){
            newDictionary.insert(key, this.dictionary.get(key));
        }

        return newDictionary;
    }
}
