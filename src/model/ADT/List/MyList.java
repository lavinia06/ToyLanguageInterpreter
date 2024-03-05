package model.ADT.List;

import exception.EmptyADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<TElem> implements ListInterface<TElem> {

    private List<TElem> list;

    public MyList(){this.list = new ArrayList<TElem>();}

    @Override
    public String toString(){
        String representation = "";
        for(TElem elem: list){
            representation += (elem.toString() + "\n");
        }

        return representation;
    }

    @Override
    public void add(TElem newElem) {
        list.add(newElem);
    }

    @Override
    public void insert(int position, TElem newElem) {
        list.add(position, newElem);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void setElement(int position, TElem newElem) {
        list.set(position, newElem);
    }

    @Override
    public TElem getElement(int position) {
        return list.get(position);
    }

    @Override
    public TElem pop() throws Exception {
        int size = this.list.size();
        if(size == 0){
            throw new EmptyADTException("Empty list!\n");
        }
        return this.list.remove(size-1);
    }

    @Override
    public TElem remove(int position) {
        return list.remove(position);
    }

    @Override
    public TElem getLast() throws Exception {
        int size = this.list.size();
        if(size == 0){
            throw new EmptyADTException("Empty list!\n");
        }
        return this.list.get(size-1);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean remove(TElem tElem) {
        return this.list.remove(tElem);
    }

    @Override
    public List<TElem> getContent(){
        return this.list;
    }
}
