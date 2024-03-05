package model.expression;

import exception.InvalidTypeException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Heap.HeapInterface;
import model.type.TypeInterface;
import model.value.ValueInterface;

public interface ExpressionInterface {
     ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table,
                             HeapInterface<Integer, ValueInterface> heap) throws Exception;
     String toString();
     TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws InvalidTypeException;
}
