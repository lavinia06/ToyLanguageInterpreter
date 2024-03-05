package model.statement.FilePack;

import exception.InvalidTypeException;
import exception.UndefinedVariableException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Heap.HeapInterface;
import model.ProgramState;
import model.expression.ExpressionInterface;
import model.statement.StatementInterface;
import model.type.IntType;
import model.type.StringType;
import model.type.TypeInterface;
import model.value.IntValue;
import model.value.StringValue;
import model.value.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements StatementInterface {

    private ExpressionInterface filePath;
    private String variableName;

    public ReadFileStatement(ExpressionInterface filePath, String variableName){
        this.filePath = filePath;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception{
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        DictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        if(!symTable.containsKey(this.variableName)){
            throw new UndefinedVariableException("The variable name is not defined in the symbol table!\n");
        }
        ValueInterface filePathValue = filePath.evaluate(symTable, heap);

        if(!fileTable.containsKey((StringValue) filePathValue)){
            throw new UndefinedVariableException("The file path value is not defined in file table!\n");
        }
        try {
            BufferedReader fileBuffer = fileTable.getValue((StringValue) filePathValue);
            String line = fileBuffer.readLine();
            if (line == null)
            {
                /// default value for int = 0
                symTable.update(this.variableName, new IntValue());
            }
            else
            {
                try {
                    symTable.update(this.variableName, new IntValue(Integer.parseInt(line)));
                } catch (Exception ignored) {
                    throw new Exception("Cannot read value because EOF has been reached!\n");
                }
            }
        }
        catch(IOException ex){
            throw new IOException("An error has occurred while reading!\n");
        }
        return null;

    }

    @Override
    public String toString(){
        return "readFile(" + this.filePath + ");\n";
    }

    @Override
    public StatementInterface deepCopy() {
        return new ReadFileStatement(filePath, variableName);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
        TypeInterface typeVariable = typeEnv.getValue(this.variableName);
        TypeInterface typeExpression = this.filePath.typeCheck(typeEnv);
        if(typeVariable.equals(new IntType())){
            if(typeExpression.equals(new StringType())){
                return typeEnv;
            }
            else
                throw new InvalidTypeException("ReadFileStatement: file path be a stringValue!\n");
        }
        else
            throw new InvalidTypeException("ReadFileStatement" + this.variableName + " is not an integer!\n");
    }
}
