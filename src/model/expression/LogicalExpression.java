package model.expression;

import exception.InvalidOperatorException;
import exception.InvalidTypeException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Heap.HeapInterface;
import model.type.BoolType;
import model.type.TypeInterface;
import model.value.BoolValue;
import model.value.ValueInterface;

public class LogicalExpression implements ExpressionInterface{

    private ExpressionInterface firstExpression;
    private ExpressionInterface secondExpression;
    private int operator;

    public LogicalExpression(ExpressionInterface firstExpression, ExpressionInterface secondExpression, int operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws Exception {
        ValueInterface firstValue;
        ValueInterface secondValue;
        firstValue = this.firstExpression.evaluate(table, heap);
        secondValue = this.secondExpression.evaluate(table, heap);
        boolean firstBoolean = ((BoolValue)firstValue).getValue();
        boolean secondBoolean = ((BoolValue)secondValue).getValue();

        if(operator == 1){
            return new BoolValue(firstBoolean && secondBoolean);
        }
        if(operator == 2){
            return new BoolValue(firstBoolean || secondBoolean);
        }
        else {
            throw new InvalidOperatorException();
        }
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws InvalidTypeException {
        TypeInterface type1, type2;
        type1 = this.firstExpression.typeCheck(typeEnv);
        type2 = this.secondExpression.typeCheck(typeEnv);
        if(!type1.equals(new BoolType())){
            throw new InvalidTypeException("First expression is not a boolean!\n");
        }

        if(!type2.equals(new BoolType())){
            throw new InvalidTypeException("Second expression is not a boolean!\n");
        }

        return new BoolType();
    }
}
