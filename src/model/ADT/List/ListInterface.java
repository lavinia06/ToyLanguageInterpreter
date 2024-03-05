package model.ADT.List;

import java.util.List;

public interface ListInterface<TElem> {
    public void add(TElem newElem);
    public void insert(int position, TElem newElem);
    public void clear();
    public void setElement(int position, TElem newElem);
    public TElem getElement(int position);
    public TElem pop() throws Exception;
    public TElem remove(int position);
    public TElem getLast() throws Exception;
    public int size();
    public boolean isEmpty();
    public boolean remove(TElem elem);
    public List<TElem> getContent();
}
