package model.ADT.Stack;

import java.util.ArrayDeque;

public interface StackInterface<TElem> {
    public TElem pop();
    public int size();
    public void clear();
    public void push(TElem newElem);
    public boolean isEmpty();
    public ArrayDeque<TElem> getContent();
}
