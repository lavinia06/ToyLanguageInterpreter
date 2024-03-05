package model.statement.HeapPack;

import exception.InvalidTypeException;
import exception.UndefinedVariableException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Heap.HeapInterface;
import model.ProgramState;
import model.expression.ExpressionInterface;
import model.statement.StatementInterface;
import model.type.ReferenceType;
import model.type.TypeInterface;
import model.value.ReferenceValue;
import model.value.ValueInterface;

public class HeapWritingStatement implements StatementInterface {

    String heapAddress;
    ExpressionInterface expression;

    public HeapWritingStatement(String variableName, ExpressionInterface expression){
        this.heapAddress = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();
        if(!symTable.containsKey(this.heapAddress)){
            throw new UndefinedVariableException("The key address: " + this.heapAddress + " is not defined in the symbolTable!\n");
        }

        ValueInterface value = symTable.getValue(this.heapAddress);
        ReferenceValue refValue = (ReferenceValue) value;
        int address = refValue.getHeapAddress();

        if(!heap.containsKey(address)){
            throw new UndefinedVariableException("The given key address: " + address + " is not defined in the heap\n");
        }

        ValueInterface expressionValue = this.expression.evaluate(symTable, heap);

        heap.update(address, expressionValue);

        return null;
    }

    @Override
    public String toString(){
        String representation = "";
        representation += ("writeHeap(" + this.heapAddress + ", " + this.expression.toString() + ");\n");
        return representation;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapWritingStatement(this.heapAddress, this.expression);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
        TypeInterface typeVariable = typeEnv.getValue(this.heapAddress);
        TypeInterface typeExpression = this.expression.typeCheck(typeEnv);
        if(typeVariable.equals(new ReferenceType(typeExpression))){
            return typeEnv;
        }
        else
            throw new InvalidTypeException("HeapWritingStatement: Expression cannot be evaluated to : " + typeExpression);
    }
}
