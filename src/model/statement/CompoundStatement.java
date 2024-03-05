package model.statement;

import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Stack.StackInterface;
import model.ProgramState;
import model.type.TypeInterface;

public class CompoundStatement implements StatementInterface{
    private StatementInterface firstStatement;
    private StatementInterface secondStatement;

    public CompoundStatement(StatementInterface firstStatement, StatementInterface secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public String toString(){
        return "("+firstStatement.toString()+";"+secondStatement.toString()+")";
    }

    @Override
    public StatementInterface deepCopy() {
        return new CompoundStatement(firstStatement, secondStatement);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
        return this.secondStatement.typeCheck(this.firstStatement.typeCheck(typeEnv));
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        return null;
    }
}
