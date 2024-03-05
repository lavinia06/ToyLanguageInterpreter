package model.expression;

import exception.DivisionByZeroException;
import exception.InvalidOperatorException;
import exception.InvalidTypeException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Heap.HeapInterface;
import model.type.IntType;
import model.type.TypeInterface;
import model.value.IntValue;
import model.value.ValueInterface;

public class ArithmeticExpression implements ExpressionInterface {

    private final ExpressionInterface firstExpression;
    private final ExpressionInterface secondExpression;
    //    private int operator;
    private final char operator;

//    public ArithmeticExpression(ExpressionInterface firstExpression, ExpressionInterface secondExpression, int operator) {
//        this.firstExpression = firstExpression;
//        this.secondExpression = secondExpression;
//        this.operator = operator;
//    }

    public ArithmeticExpression(ExpressionInterface firstExpression, ExpressionInterface secondExpression, char operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return firstExpression.toString() + operator + secondExpression.toString();
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws InvalidTypeException {
        TypeInterface type1;
        TypeInterface type2;
        type1 = this.firstExpression.typeCheck(typeEnv);
        type2 = this.secondExpression.typeCheck(typeEnv);

        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
                throw new InvalidTypeException("Invalid type for the second expression!\n");
        } else
            throw new InvalidTypeException("Invalid type for the first expression!\n");
    }

    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws Exception {
        ValueInterface firstValue, secondValue;
        firstValue = firstExpression.evaluate(table, heap);
        secondValue = secondExpression.evaluate(table, heap);
        int firstInt = ((IntValue) firstValue).getValue();
        int secondInt = ((IntValue) secondValue).getValue();
        if (operator == '+') {
            return new IntValue(firstInt + secondInt);
        }
        if (operator == '-') {
            return new IntValue(firstInt - secondInt);
        }
        if (operator == '*') {
            return new IntValue(firstInt * secondInt);
        }
        if (operator == '/') {
            if (secondInt == 0)
                throw new DivisionByZeroException("Division by zero!");
            else
                return new IntValue(firstInt / secondInt);
        } else {
            throw new InvalidOperatorException();
        }
    }
}
