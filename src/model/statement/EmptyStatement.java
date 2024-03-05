package model.statement;

import model.ADT.Dictionary.DictionaryInterface;
import model.ProgramState;
import model.type.TypeInterface;

public class EmptyStatement implements StatementInterface{

    public EmptyStatement(){

    }

    @Override
    public String toString(){
        return "Empty statement!";
    }

    @Override
    public StatementInterface deepCopy() {
        return new EmptyStatement();
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }
}
