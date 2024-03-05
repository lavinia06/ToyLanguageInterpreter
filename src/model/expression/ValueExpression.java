package model.expression;

import exception.InvalidTypeException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Heap.HeapInterface;
import model.type.TypeInterface;
import model.value.ValueInterface;

public class ValueExpression implements ExpressionInterface{
    private ValueInterface value;

    public ValueExpression(ValueInterface value) {
        this.value = value;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws Exception {
        return value;
    }

    public String toString(){
        return value.toString();
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws InvalidTypeException {
        return this.value.getType();
    }

}
