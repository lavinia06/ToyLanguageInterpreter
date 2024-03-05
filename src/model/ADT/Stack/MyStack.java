package model.ADT.Stack;

import java.util.ArrayDeque;

public class MyStack<TElem> implements StackInterface<TElem> {

    private ArrayDeque<TElem> stack;

    public MyStack(){
        stack = new ArrayDeque<>();
    }

    @Override
    public String toString(){
        String representation = "";
        for(TElem current: stack){
            representation += current.toString() + "\n";
        }
//        representation = stack.toString();
        return representation;
    }

    @Override
    public TElem pop() {
        return stack.pop();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public void push(TElem newElem) {
        stack.push(newElem);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public ArrayDeque<TElem> getContent(){
        return this.stack;
    }
}
