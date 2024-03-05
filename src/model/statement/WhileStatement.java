package model.statement;

import exception.InvalidTypeException;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Heap.HeapInterface;
import model.ADT.Stack.StackInterface;
import model.ProgramState;
import model.expression.ExpressionInterface;
import model.type.BoolType;
import model.type.TypeInterface;
import model.value.BoolValue;
import model.value.ValueInterface;

public class WhileStatement implements  StatementInterface{

    private final ExpressionInterface whileCondition;
    private final StatementInterface whileBody;

    public WhileStatement(ExpressionInterface whileCondition, StatementInterface whileBody){
        this.whileCondition = whileCondition;
        this.whileBody = whileBody;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        StackInterface<StatementInterface> executionStack = state.getExecutionStack();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        ValueInterface evaluatedExpression = this.whileCondition.evaluate(symTable, heap);
        if(((BoolValue) evaluatedExpression).getValue()){
            ///First we push on the stack the current statement, in our case exactly the while statement
                executionStack.push(this);
                ///Afterwards we push to the stack the whileBody in order to execute it before the while statement executes again
                executionStack.push(this.whileBody);
        }

        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new WhileStatement(this.whileCondition, this.whileBody);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
       TypeInterface typeExpression = this.whileCondition.typeCheck(typeEnv);
       if(typeExpression.equals(new BoolType())){
           /// In this case we also use a copy of the typeEnv because we can have the case
           /// while(...)
           ///{ int a; }
           /// bool a;
           /// And here for example, if the condition of while is not true, we would still declare "a" as an int in the typeEnv through the typeChecking even though we shouldn't
           /// And when we will try to declare "a" as a BOOL it will crash, reason why we use a copy of the typeEnv when we check the whileBody with the typeChecker
           this.whileBody.typeCheck(typeEnv.copy());
           return typeEnv;
       }
       else throw new InvalidTypeException("WhileStatement: Conditional expression is not boolean!\n");
    }

    @Override
    public String toString(){
        return "while("+whileCondition.toString()+"){ "+whileBody.toString()+" }";
    }

}
