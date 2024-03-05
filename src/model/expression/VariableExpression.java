package model.expression;

import exception.InvalidTypeException;
import exception.UndefinedVariableException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Heap.HeapInterface;
import model.type.TypeInterface;
import model.value.ValueInterface;

public class VariableExpression implements ExpressionInterface{

    private String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws Exception {
        if (!table.containsKey(id)) {
            throw new UndefinedVariableException(id + "is not defined as a variable!");
        }
        return table.getValue(id);
    }

    public String toString(){
        return id;
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws InvalidTypeException {
        return typeEnv.getValue(this.id);
    }

}
