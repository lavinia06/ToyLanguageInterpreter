package model.statement;

import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Stack.MyStack;
import model.ADT.Stack.StackInterface;
import model.ProgramState;
import model.type.TypeInterface;
import model.value.ValueInterface;

public class ForkStatement implements StatementInterface{
    StatementInterface statement;

    public ForkStatement(StatementInterface statement){
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        StackInterface<StatementInterface> newStack = new MyStack<>();
        DictionaryInterface<String , ValueInterface> symTable = state.getSymbolTable();
        DictionaryInterface<String, ValueInterface> newDictionary = symTable.copy();

        return new ProgramState(newStack, newDictionary, state.getOutput(), state.getFileTable(),
                state.getHeap(), ProgramState.generateNewID(), this.statement);

    }

    @Override
    public StatementInterface deepCopy() {
        return new ForkStatement(this.statement);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
        /// When we make a fork, the child process will use everything we have in the memory until that moment, but when
        /// It changes something, it won't affect the parent process, because these two can communicate only through the HEAP
        this.statement.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public String toString(){
        return "fork( " + this.statement + " );";
    }

}
