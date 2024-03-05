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

public class IfStatement implements StatementInterface {
    private ExpressionInterface expression;
    private StatementInterface trueStatement;
    private StatementInterface falseStatement;

    public IfStatement(ExpressionInterface expression, StatementInterface trueStatement, StatementInterface falseStatement) {
        this.expression = expression;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }

    @Override
    public String toString() {
        return "IF(" + expression.toString() + ")THEN(" + trueStatement.toString() + ")ELSE(" + falseStatement.toString() + "))";
    }

    @Override
    public StatementInterface deepCopy() {
        return new IfStatement(expression, trueStatement, falseStatement);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws Exception {
        TypeInterface typeExpression = this.expression.typeCheck(typeEnv);
        if(typeExpression.equals(new BoolType())){
            /// We use a copy of the typeEnv when we check the type of the true statement and the false statement because something like this can happen:
            /// In the true statement we can have declared a variable "a" as an Int and in the false statement we can have a variable "a" declared as a Bool
            /// And if we use the initial typeChecker for both of them, in the true statement we will have the variable "a" declared already as an Int
            /// While in the false statement we will try to declare it again as a Bool Type and the program will crash
            this.trueStatement.typeCheck(typeEnv.copy());
            this.falseStatement.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else throw new InvalidTypeException("The condition of IF has not the type bool!\n");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        ValueInterface conditionalExpressionVal = expression.evaluate(symbolTable, heap);
        if (((BoolValue) conditionalExpressionVal).getValue()) {
            stack.push(trueStatement);
        } else {
            stack.push(falseStatement);
        }

        return null;
    }
}