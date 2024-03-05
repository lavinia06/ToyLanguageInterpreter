package model.statement;


import model.ADT.Dictionary.DictionaryInterface;
import model.ProgramState;
import model.type.TypeInterface;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws Exception;
    String toString();
    StatementInterface deepCopy();
    DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception;
}
