package model.statement.FilePack;

import exception.InvalidTypeException;
import exception.UndefinedVariableException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Heap.HeapInterface;
import model.ProgramState;
import model.expression.ExpressionInterface;
import model.statement.StatementInterface;
import model.type.StringType;
import model.type.TypeInterface;
import model.value.StringValue;
import model.value.ValueInterface;

import java.io.BufferedReader;

public class CloseReadFileStatement implements StatementInterface {

    private final ExpressionInterface filePath;

    public CloseReadFileStatement(ExpressionInterface filePath){
        this.filePath = filePath;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception{

        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        DictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        ValueInterface filePathValue = filePath.evaluate(symTable, heap);

        if(!fileTable.containsKey((StringValue) filePathValue)){
            throw new UndefinedVariableException("The file path: " + filePathValue + "is not defined in the file table!\n");
        }

        BufferedReader fileBuffer = fileTable.getValue((StringValue) filePathValue);
        fileBuffer.close();
        fileTable.remove((StringValue) filePathValue);

        return null;
    }
    public String toString(){
        return "closeRead(" + this.filePath + ");\n";
    }

    @Override
    public StatementInterface deepCopy() {
        return new CloseReadFileStatement(filePath);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
        TypeInterface typeExpression = this.filePath.typeCheck(typeEnv);
        if(!typeExpression.equals(new StringType())){
            throw new InvalidTypeException("CloseReadFileStatement: file path should be a stringValue!");
        }
        return typeEnv;
    }

}
