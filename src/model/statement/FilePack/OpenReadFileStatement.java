package model.statement.FilePack;

import exception.ExistingVariableException;
import exception.InvalidTypeException;
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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements StatementInterface {

    private ExpressionInterface filePath;

    public OpenReadFileStatement(ExpressionInterface filePath){
        this.filePath = filePath;
    }

    public ProgramState execute(ProgramState state) throws Exception{

        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        DictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        ValueInterface filePathValue = this.filePath.evaluate(symTable, heap);

        if(fileTable.containsKey((StringValue) filePathValue)){
            throw new ExistingVariableException("The filepath is already a key in FileTable!\n");
        }

        try {
            BufferedReader fileBuffer = new BufferedReader(new FileReader(((StringValue) filePathValue).getValue()));
            fileTable.insert((StringValue) filePathValue, fileBuffer);
        }
        catch (FileNotFoundException ex){
            throw new Exception(ex.getMessage());
        }
        return null;
    }


    public String toString(){
        return "openRead(" + this.filePath + ");\n";
    }

    @Override
    public StatementInterface deepCopy() {
        return new OpenReadFileStatement(filePath);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
        TypeInterface typeExpression = this.filePath.typeCheck(typeEnv);
        if(!typeExpression.equals(new StringType())){
            throw new InvalidTypeException("OpenReadFileStatement: file path should be a stringValue!\n");
        }
        return typeEnv;
    }

}
