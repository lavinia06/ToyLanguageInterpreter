package model.expression;

import exception.InvalidTypeException;
import exception.UndefinedVariableException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Heap.HeapInterface;
import model.type.ReferenceType;
import model.type.TypeInterface;
import model.value.ReferenceValue;
import model.value.ValueInterface;

public class HeapReadingExpression implements ExpressionInterface{

    private ExpressionInterface expression;

    public HeapReadingExpression(ExpressionInterface expression){
        this.expression = expression;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws Exception {
        ValueInterface value = expression.evaluate(table, heap);

        int address = ((ReferenceValue) value).getHeapAddress();
        if(!heap.containsKey(address)){
            throw new UndefinedVariableException("The given key address: " + address + " is not defined in the heap\n");
        }

        return heap.getValue(address);
    }

    @Override
    public String toString(){
        return "readHeap("+expression.toString()+")";
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws InvalidTypeException {
        TypeInterface type = this.expression.typeCheck(typeEnv);
        if(type instanceof ReferenceType){
            ReferenceType refType = (ReferenceType) type;
            return refType.getInner();
        }
        else
            throw new InvalidTypeException("the rH argument is not a reference type");
    }

}
