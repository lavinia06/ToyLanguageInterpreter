package model.statement;

import exception.ExistingVariableException;
import exception.InvalidTypeException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ProgramState;
import model.type.*;
import model.value.*;

public class VariableDeclarationStatement implements StatementInterface{

    private String name;
    private TypeInterface type;

    public VariableDeclarationStatement(String name, TypeInterface type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString(){
        return type.toString() + " " + name;
    }

    @Override
    public StatementInterface deepCopy() {
        return new VariableDeclarationStatement(name, type);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
        typeEnv.insert(this.name, this.type);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        if(symbolTable.containsKey(name)){
            throw new ExistingVariableException("Variable " + name + "is already declared!");
        }
        else if(type.equals(new IntType())){
            symbolTable.insert(name, new IntValue());
        }
        else if(type.equals(new BoolType())){
            symbolTable.insert(name, new BoolValue());
        }
        else if(type.equals(new StringType())){
            symbolTable.insert(name, new StringValue());
        }
        else if(type instanceof ReferenceType refType){
            symbolTable.insert(name, new ReferenceValue(refType.getInner()));
        }
        // I'm not sure if this part will ever be reached, because the compiler doesn't allow for anything but a TypeInterface
        // to be added, but just in case...
        else
        {
            throw new InvalidTypeException("Invalid type!");
        }
        return null;
    }
}
