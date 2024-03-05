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

public class HeapAllocationStatement implements StatementInterface {

    private final ExpressionInterface expression;
    private final String variableName;

    public HeapAllocationStatement(String variableName, ExpressionInterface expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        if(!symTable.containsKey(this.variableName)){
            throw new UndefinedVariableException(this.variableName + " is not defined in the symbol table!\n");
        }

        ValueInterface expValue = this.expression.evaluate(symTable, heap);
        ReferenceValue refVal = ((ReferenceValue) symTable.getValue(this.variableName));
        TypeInterface innerType = ((ReferenceType) refVal.getType()).getInner();

        int copyAddress = heap.getFirstAvailablePosition();
        heap.insert(copyAddress,expValue);
        symTable.update(this.variableName, new ReferenceValue(copyAddress, innerType));

        return null;
    }

    @Override
    public String toString(){
        String representation = "";
        representation += ("new(" + this.variableName + ", " + this.expression.toString() + ");\n");
        return representation;
    }

    @Override
    public StatementInterface deepCopy()
    {
        return new HeapAllocationStatement(this.variableName, this.expression);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
        TypeInterface typeVariable = typeEnv.getValue(this.variableName);
        TypeInterface typeExpression = expression.typeCheck(typeEnv);
        if(typeVariable.equals(new ReferenceType(typeExpression))){
            return typeEnv;
        }
        else
            throw new InvalidTypeException("HeapAllocationStatement: right hand side and left hand side have different types!\n");
    }
}
