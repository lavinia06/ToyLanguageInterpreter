package model.ADT.Heap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyHeap<TKey, TValue> implements HeapInterface<TKey, TValue> {

    private Map<TKey, TValue> heap;
    int firstAvailablePosition = 1;

    public MyHeap(){
        this.heap = new HashMap<>();
    }

    @Override
    public Collection<TValue> values() {
        return this.heap.values();
    }

    public void setContent(Map<TKey, TValue> newHeap){
        this.heap.clear();
        this.heap.putAll(newHeap);
    }

    @Override
    public void setFirstAvailablePosition(){
        // normally I would search for available positions that have previously been occupied and are now free due to the GC
        // but for now I'll just add them one after the other
        this.firstAvailablePosition = this.firstAvailablePosition + 1;

    }

    @Override
    public String toString(){
        String heapVals="";
        for(TKey key:heap.keySet())
            heapVals=heapVals.concat("["+key+"->"+heap.get(key).toString()+"] ");
        return heapVals;
    }

    @Override
    public int getFirstAvailablePosition(){
        int positionCopy = this.firstAvailablePosition;
        this.setFirstAvailablePosition();
        return positionCopy;
    }

    @Override
    public int size() {
        return this.heap.size();
    }

    @Override
    public boolean containsKey(TKey tKey) {
        return this.heap.containsKey(tKey);
    }

    @Override
    public boolean containsValue(TValue tValue) {
        return this.heap.containsValue(tValue);
    }

    @Override
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    @Override
    public void update(TKey tKey, TValue newValue) {
        this.heap.replace(tKey,newValue);
    }

    @Override
    public void insert(TKey tKey, TValue newValue) {
        this.heap.put(tKey, newValue);
    }

    @Override
    public void clear() {
        this.heap.clear();
    }

    @Override
    public TValue getValue(TKey tKey) {
        return this.heap.get(tKey);
    }

    @Override
    public TValue remove(TKey tKey) {
        return this.remove(tKey);
    }

    @Override
    public Collection<TValue> getAllValues() {
        return this.heap.values();
    }

    @Override
    public Collection<TKey> getAllKeys() {
        return this.heap.keySet();
    }

    @Override
    public Map<TKey, TValue> getContent() {
        return this.heap;
    }

}
