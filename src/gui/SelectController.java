package gui;

import controller.Controller;
import controller.ControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Dictionary.MyDictionary;
import model.ADT.Heap.MyHeap;
import model.ADT.List.MyList;
import model.ADT.Stack.MyStack;
import model.ProgramState;
import model.expression.*;
import model.statement.*;
import model.statement.FilePack.CloseReadFileStatement;
import model.statement.FilePack.OpenReadFileStatement;
import model.statement.FilePack.ReadFileStatement;
import model.statement.HeapPack.HeapAllocationStatement;
import model.statement.HeapPack.HeapWritingStatement;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.ValueInterface;
import repository.Repository;
import repository.RepositoryInterface;
import view.ExitCommand;
import view.RunExampleCommand;

import java.io.BufferedReader;

public class SelectController {

    @FXML
    private ListView<RunExampleCommand> examplesListView;

    public ListView<RunExampleCommand> getExamplesListView(){
        return this.examplesListView;
    }

    @FXML
    public void initialize() throws Exception {

        String FOLDER_PATH = "C:\\Users\\night\\Desktop\\Facultate An 2\\Semestrul 1\\Advanced Programming Methods\\MAPInterpreter\\A7";


        /// EXAMPLE 1
        /// int v; v=2;Print(v)
        StatementInterface declare_v = new VariableDeclarationStatement("v", new IntType());
        StatementInterface assign_v = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        StatementInterface print_v = new PrintStatement(new VariableExpression("v"));

        StatementInterface programExample1 = new CompoundStatement(declare_v, new CompoundStatement(assign_v, print_v));

        DictionaryInterface<String, TypeInterface> typeEnvironment1 = new MyDictionary<String, TypeInterface>();
        programExample1.typeCheck(typeEnvironment1);

        ProgramState currentProgramState1 = new ProgramState(new MyStack<StatementInterface>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample1);
        RepositoryInterface repo1 = new Repository(FOLDER_PATH + "\\log1.in");
        ControllerInterface controller1 = new Controller(repo1);

        controller1.addProgramState(currentProgramState1);



        /// EXAMPLE 2
        /// int a;int b; a=2+3*5;b=a+1;Print(b)
        StatementInterface declare_a = new VariableDeclarationStatement("a", new IntType());
        StatementInterface declare_b = new VariableDeclarationStatement("b", new IntType());
        //StatementInterface declare_b = new VariableDeclarationStatement("b", new StringType());
        ExpressionInterface multiply_a = new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), '*');
        ExpressionInterface add_a = new ArithmeticExpression(multiply_a, new ValueExpression(new IntValue(2)), '+');
        StatementInterface assign_a = new AssignmentStatement("a", add_a);
        ExpressionInterface add_b = new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), '+');
        StatementInterface assign_b = new AssignmentStatement("b", add_b);
        StatementInterface print_b = new PrintStatement(new VariableExpression("b"));

        StatementInterface programExample2 = new CompoundStatement(declare_a, new CompoundStatement(declare_b,
                new CompoundStatement(assign_a, new CompoundStatement(assign_b, print_b))));

        DictionaryInterface<String, TypeInterface> typeEnvironment2 = new MyDictionary<String, TypeInterface>();
        programExample2.typeCheck(typeEnvironment2);
//        try {
//            programExample2.typeCheck(typeEnvironment2);
//        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        ProgramState currentProgramState2 = new ProgramState(new MyStack<StatementInterface>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample2);
        RepositoryInterface repo2 = new Repository(FOLDER_PATH + "\\log2.in");
        ControllerInterface controller2 = new Controller(repo2);

        controller2.addProgramState(currentProgramState2);





        /// EXAMPLE 3
        /// bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        StatementInterface declare_a3 = new VariableDeclarationStatement("a", new BoolType());
        StatementInterface declare_v3 = new VariableDeclarationStatement("v", new IntType());
        StatementInterface assign_a3 = new AssignmentStatement("a", new ValueExpression(new BoolValue(true)));
        StatementInterface assign_v_1 = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        StatementInterface assign_v_2 = new AssignmentStatement("v", new ValueExpression(new IntValue(3)));
        StatementInterface if_statement3 = new IfStatement(new VariableExpression("a"), assign_v_1, assign_v_2);
        StatementInterface print_v3 = new PrintStatement(new VariableExpression("v"));

        StatementInterface programExample3 = new CompoundStatement(declare_a3, new CompoundStatement(declare_v3, new CompoundStatement(assign_a3,
                new CompoundStatement(if_statement3, print_v3))));

        DictionaryInterface<String, TypeInterface> typeEnvironment3 = new MyDictionary<String, TypeInterface>();
        programExample3.typeCheck(typeEnvironment3);

        ProgramState currentProgramState3 = new ProgramState(new MyStack<StatementInterface>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample3);
        RepositoryInterface repo3 = new Repository(FOLDER_PATH + "\\log3.in");
        ControllerInterface controller3 = new Controller(repo3);

        controller3.addProgramState(currentProgramState3);





        /// EXAMPLE 4
        StatementInterface stringDeclaration = new VariableDeclarationStatement("varf", new StringType());
        StatementInterface assignment = new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in")));
        StatementInterface open = new OpenReadFileStatement(new VariableExpression("varf"));
        StatementInterface intDeclaration = new VariableDeclarationStatement("varc", new IntType());
        StatementInterface readFile = new ReadFileStatement(new VariableExpression("varf"), "varc");
        StatementInterface print = new PrintStatement(new VariableExpression("varc"));
        StatementInterface close = new CloseReadFileStatement(new VariableExpression("varf"));

        StatementInterface programExample4 = new CompoundStatement(stringDeclaration, new CompoundStatement(assignment, new CompoundStatement(open,
                new CompoundStatement(intDeclaration, new CompoundStatement(readFile, new CompoundStatement(print,
                        new CompoundStatement(readFile, new CompoundStatement(print, close))))))));

        DictionaryInterface<String, TypeInterface> typeEnvironment4 = new MyDictionary<String, TypeInterface>();
        programExample4.typeCheck(typeEnvironment4);

        ProgramState currentProgramState4 = new ProgramState(new MyStack<StatementInterface>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample4);
        RepositoryInterface repo4 = new Repository(FOLDER_PATH + "\\log4.in");
        ControllerInterface controller4 = new Controller(repo4);

        controller4.addProgramState(currentProgramState4);





        /// EXAMPLE 5
        ///int a; a = 25; int b; b = 30; IF (a > b) THEN print(a) ELSE print(b)

        StatementInterface declare_a5 = new VariableDeclarationStatement("a", new IntType());
        StatementInterface assign_a5 = new AssignmentStatement("a", new ValueExpression(new IntValue(25)));
        StatementInterface declare_b5 = new VariableDeclarationStatement("b", new IntType());
        StatementInterface assign_b5 = new AssignmentStatement("b", new ValueExpression(new IntValue(30)));
        ExpressionInterface relationalExpression5 = new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"), "<");
        StatementInterface print_a5 = new PrintStatement(new VariableExpression("a"));
        StatementInterface print_b5 = new PrintStatement(new VariableExpression("b"));
        StatementInterface if_statement5 = new IfStatement(relationalExpression5, print_a5, print_b5);

        StatementInterface programExample5 = new CompoundStatement(declare_a5, new CompoundStatement(assign_a5,
                new CompoundStatement(declare_b5, new CompoundStatement(assign_b5, if_statement5))));

        DictionaryInterface<String, TypeInterface> typeEnvironment5 = new MyDictionary<String, TypeInterface>();
        programExample5.typeCheck(typeEnvironment5);

        ProgramState currentProgramState5 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample5);
        RepositoryInterface repo5 = new Repository(FOLDER_PATH + "\\log5.in");
        ControllerInterface controller5 = new Controller(repo5);

        controller5.addProgramState(currentProgramState5);





        /// EXAMPLE 6
        ///Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)

        StatementInterface declare_v6 = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        StatementInterface alloc_v6 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        StatementInterface declare_a6 = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        StatementInterface alloc_a6 = new HeapAllocationStatement("a", new VariableExpression("v"));
        StatementInterface print_v6 = new PrintStatement(new VariableExpression("v"));
        StatementInterface print_a6 = new PrintStatement(new VariableExpression("a"));

        StatementInterface programExample6 = new CompoundStatement(declare_v6, new CompoundStatement(alloc_v6,
                new CompoundStatement(declare_a6, new CompoundStatement(alloc_a6, new CompoundStatement(print_v6, print_a6)))));

        DictionaryInterface<String, TypeInterface> typeEnvironment6 = new MyDictionary<String, TypeInterface>();
        programExample6.typeCheck(typeEnvironment6);

        ProgramState currentProgramState6 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample6);
        RepositoryInterface repo6 = new Repository(FOLDER_PATH + "\\log6.in");
        ControllerInterface controller6 = new Controller(repo6);

        controller6.addProgramState(currentProgramState6);




        ///EXAMPLE 7
        ///Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)

        StatementInterface declare_v7 = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        StatementInterface alloc_v7 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        StatementInterface declare_a7 = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        StatementInterface alloc_a7 = new HeapAllocationStatement("a", new VariableExpression("v"));
        ExpressionInterface read_v7 = new HeapReadingExpression(new VariableExpression("v"));
        StatementInterface print_v7 = new PrintStatement(read_v7);
        ExpressionInterface read_a7 = new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")));
        ExpressionInterface add7 = new ArithmeticExpression(read_a7, new ValueExpression(new IntValue(5)), '+');
        StatementInterface print_a7 = new PrintStatement(add7);

        StatementInterface programExample7 = new CompoundStatement(declare_v7, new CompoundStatement(alloc_v7, new CompoundStatement(declare_a7,
                new CompoundStatement(alloc_a7, new CompoundStatement(print_v7, print_a7)))));

        DictionaryInterface<String, TypeInterface> typeEnvironment7 = new MyDictionary<String, TypeInterface>();
        programExample7.typeCheck(typeEnvironment7);

        ProgramState currentProgramState7 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample7);
        RepositoryInterface repo7 = new Repository(FOLDER_PATH + "\\log7.in");
        ControllerInterface controller7 = new Controller(repo7);

        controller7.addProgramState(currentProgramState7);





        ///EXAMPLE 8
        ///Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);

        StatementInterface declare_v8 = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        StatementInterface alloc_v8 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        ExpressionInterface read_v8 = new HeapReadingExpression(new VariableExpression("v"));
        StatementInterface print_v8 = new PrintStatement(read_v8);
        StatementInterface write_v8 = new HeapWritingStatement("v", new ValueExpression(new IntValue(30)));
        ExpressionInterface read_v8_2 = new HeapReadingExpression(new VariableExpression("v"));
        ExpressionInterface add8 = new ArithmeticExpression(read_v8_2, new ValueExpression(new IntValue(5)), '+');
        StatementInterface print_v8_2 = new PrintStatement(add8);

        StatementInterface programExample8 = new CompoundStatement(declare_v8, new CompoundStatement(alloc_v8, new CompoundStatement(print_v8,
                new CompoundStatement(write_v8, print_v8_2))));

        DictionaryInterface<String, TypeInterface> typeEnvironment8 = new MyDictionary<String, TypeInterface>();
        programExample8.typeCheck(typeEnvironment8);

        ProgramState currentProgramState8 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample8);
        RepositoryInterface repo8 = new Repository(FOLDER_PATH + "\\log8.in");
        ControllerInterface controller8 = new Controller(repo8);

        controller8.addProgramState(currentProgramState8);





        ///EXAMPLE 9
        ///Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        StatementInterface declare_v9 = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        StatementInterface alloc_v9_1 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        StatementInterface declare_a9 = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        StatementInterface alloc_a9 = new HeapAllocationStatement("a", new VariableExpression("v"));
        StatementInterface alloc_v9_2 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(30)));
        ExpressionInterface read_a_1 = new HeapReadingExpression(new VariableExpression("a"));
        ExpressionInterface read_a_2 = new HeapReadingExpression(read_a_1);
        StatementInterface print_a9 = new PrintStatement(read_a_2);

        StatementInterface programExample9 = new CompoundStatement(declare_v9, new CompoundStatement(alloc_v9_1, new CompoundStatement(declare_a9,
                new CompoundStatement(alloc_a9, new CompoundStatement(alloc_v9_2, print_a9)))));

        DictionaryInterface<String, TypeInterface> typeEnvironment9 = new MyDictionary<String, TypeInterface>();
        programExample9.typeCheck(typeEnvironment9);

        ProgramState currentProgramState9 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample9);
        RepositoryInterface repo9 = new Repository(FOLDER_PATH + "\\log9.in");
        ControllerInterface controller9 = new Controller(repo9);

        controller9.addProgramState(currentProgramState9);





        ///EXAMPLE 10
        ///int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        StatementInterface declare_v10 = new VariableDeclarationStatement("v", new IntType());
        StatementInterface assign_v10_1 = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
        ExpressionInterface rel_expr10 = new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">");
        StatementInterface print_v10_1 = new PrintStatement(new VariableExpression("v"));
        ExpressionInterface arithmetic_v10 = new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '-');
        StatementInterface assign_v10_2 = new AssignmentStatement("v", arithmetic_v10);
        StatementInterface compoundStatement_v10 = new CompoundStatement(print_v10_1, assign_v10_2);
        StatementInterface whileStatement_v10 = new WhileStatement(rel_expr10, compoundStatement_v10);
        StatementInterface print_v10_2 = new PrintStatement(new VariableExpression("v"));

        StatementInterface programExample10 = new CompoundStatement(declare_v10, new CompoundStatement(assign_v10_1, new CompoundStatement(whileStatement_v10, print_v10_2)));

        DictionaryInterface<String, TypeInterface> typeEnvironment10 = new MyDictionary<String, TypeInterface>();
        programExample10.typeCheck(typeEnvironment10);

        ProgramState currentProgramState10 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample10);
        RepositoryInterface repo10 = new Repository(FOLDER_PATH + "\\log10.in");
        ControllerInterface controller10 = new Controller(repo10);

        controller10.addProgramState(currentProgramState10);





        ///EXAMPLE 11
        /// int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
        StatementInterface declare_v11 = new VariableDeclarationStatement("v", new IntType());
        StatementInterface declare_a11 = new VariableDeclarationStatement("a", new ReferenceType(new IntType()));
        StatementInterface assign_v11_1 = new AssignmentStatement("v", new ValueExpression(new IntValue(10)));
        StatementInterface alloc_v11 = new HeapAllocationStatement("a", new ValueExpression(new IntValue(22)));
        StatementInterface write_a11 = new HeapWritingStatement("a", new ValueExpression(new IntValue(30)));
        StatementInterface assign_v11_2 = new AssignmentStatement("v", new ValueExpression(new IntValue(32)));
        StatementInterface print_v11_1 = new PrintStatement(new VariableExpression("v"));
        ExpressionInterface read_v11 = new HeapReadingExpression(new VariableExpression("a"));
        StatementInterface print_v11_2 = new PrintStatement(read_v11);
        StatementInterface fork_11 = new ForkStatement(new CompoundStatement(write_a11, new CompoundStatement(assign_v11_2, new CompoundStatement(print_v11_1, print_v11_2))));

        StatementInterface programExample11 = new CompoundStatement(declare_v11, new CompoundStatement(declare_a11, new CompoundStatement(assign_v11_1,
                new CompoundStatement(alloc_v11, new CompoundStatement(fork_11, new CompoundStatement(print_v11_1, print_v11_2))))));

        DictionaryInterface<String, TypeInterface> typeEnvironment11 = new MyDictionary<String, TypeInterface>();
        programExample11.typeCheck(typeEnvironment11);

        ProgramState currentProgramState11 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample11);
        RepositoryInterface repo11 = new Repository(FOLDER_PATH + "\\log11.in");
        ControllerInterface controller11 = new Controller(repo11);

        controller11.addProgramState(currentProgramState11);





        ///EXAMPLE 12
        ///int v; Ref int  a; v=10; new(a, 22); Fork( wH(a, 30); v=32; print(v); print(rH(a)); (Fork( v=16; print(v)); (print(v); print(rH(a)))
        StatementInterface declare_v12 = new VariableDeclarationStatement("v", new IntType());
        StatementInterface declare_a12 = new VariableDeclarationStatement("a", new ReferenceType(new IntType()));
        StatementInterface assign_v12_1 = new AssignmentStatement("v", new ValueExpression(new IntValue(10)));
        StatementInterface alloc_a12 = new HeapAllocationStatement("a", new ValueExpression(new IntValue(22)));
        StatementInterface write_a12 = new HeapWritingStatement("a", new ValueExpression(new IntValue(30)));
        StatementInterface assign_v12_2 = new AssignmentStatement("v", new ValueExpression(new IntValue(32)));
        StatementInterface print_v12_1 = new PrintStatement(new VariableExpression("v"));
        ExpressionInterface read_v12 = new HeapReadingExpression(new VariableExpression("a"));
        StatementInterface print_v12_2 = new PrintStatement(read_v12);
        StatementInterface fork_12_1 = new ForkStatement(new CompoundStatement(write_a12, new CompoundStatement(assign_v12_2, new CompoundStatement(print_v12_1, print_v12_2))));
        StatementInterface assign_v12_3 = new AssignmentStatement("v", new ValueExpression(new IntValue(16)));
        StatementInterface fork_12_2 = new ForkStatement(new CompoundStatement(assign_v12_3, print_v12_1));

        StatementInterface programExample12 = new CompoundStatement(declare_v12, new CompoundStatement(declare_a12,
                new CompoundStatement(assign_v12_1, new CompoundStatement(alloc_a12, new CompoundStatement(fork_12_1,
                        new CompoundStatement(fork_12_2, new CompoundStatement(print_v12_1, print_v12_2)))))));

        DictionaryInterface<String, TypeInterface> typeEnvironment12 = new MyDictionary<String, TypeInterface>();
        programExample12.typeCheck(typeEnvironment12);

        ProgramState currentProgramState12= new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample12);
        RepositoryInterface repo12 = new Repository(FOLDER_PATH + "\\log12.in");
        ControllerInterface controller12 = new Controller(repo12);

        controller12.addProgramState(currentProgramState12);





        ///EXAMPLE 13
        ///int v; Ref int a; v=10; new(a, 22); Fork(wH(a, 30); v=32; print(v); Fork(wH(a, 100); v=50; print(v)); print(rH(a)); print(v); print(rH(a)))
        StatementInterface declare_v13 = new VariableDeclarationStatement("v", new IntType());
        StatementInterface declare_a13 = new VariableDeclarationStatement("a", new ReferenceType(new IntType()));
        StatementInterface assign_v13_1 = new AssignmentStatement("v", new ValueExpression(new IntValue(10)));
        StatementInterface alloc_a13 = new HeapAllocationStatement("a", new ValueExpression(new IntValue(22)));
        StatementInterface write_a13_1 = new HeapWritingStatement("a", new ValueExpression(new IntValue(30)));
        StatementInterface assign_v13_2 = new AssignmentStatement("v", new ValueExpression(new IntValue(32)));
        StatementInterface print_v13_1 = new PrintStatement(new VariableExpression("v"));
        StatementInterface write_a13_2 = new HeapWritingStatement("a", new ValueExpression(new IntValue(100)));
        StatementInterface assign_v13_3 = new AssignmentStatement("v", new ValueExpression(new IntValue(50)));
        ExpressionInterface read_a13 = new HeapReadingExpression(new VariableExpression("a"));
        StatementInterface print_v13_2 = new PrintStatement(read_a13);
        StatementInterface fork_13_1 = new ForkStatement(new CompoundStatement(write_a13_2, new CompoundStatement(assign_v13_3, print_v13_1)));
        StatementInterface fork_13_2 = new ForkStatement(new CompoundStatement(write_a13_1, new CompoundStatement(assign_v13_2,  new CompoundStatement(print_v13_1,
                new CompoundStatement(fork_13_1, print_v13_2)))));

        StatementInterface programExample13 = new CompoundStatement(declare_v13, new CompoundStatement(declare_a13,
                new CompoundStatement(assign_v13_1, new CompoundStatement(alloc_a13, new CompoundStatement(fork_13_2,
                        new CompoundStatement(print_v13_1, print_v13_2))))));

        DictionaryInterface<String, TypeInterface> typeEnvironment13 = new MyDictionary<String, TypeInterface>();
        programExample13.typeCheck(typeEnvironment13);

        ProgramState currentProgramState13= new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ProgramState.generateNewID(), programExample13);
        RepositoryInterface repo13 = new Repository(FOLDER_PATH + "\\log13.in");
        ControllerInterface controller13 = new Controller(repo13);

        controller13.addProgramState(currentProgramState13);

        // When we do this for the list, is somehow like deciding what value to place, like the form of the String
        // (not sure I got it right)
        this.examplesListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<RunExampleCommand>() {
            @Override
            public String toString(RunExampleCommand runExampleCommand) {
                return runExampleCommand.toString();
            }

            @Override
            public RunExampleCommand fromString(String s) {
                return null;
            }
        }));

        this.examplesListView.getItems().add(new RunExampleCommand("1", "int v; v=2; Print(v)", controller1));
        this.examplesListView.getItems().add(new RunExampleCommand("2", "int a; int b; a=2+3*5; b=a+1; Print(b)", controller2));
        this.examplesListView.getItems().add(new RunExampleCommand("3", "bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)", controller3));
        this.examplesListView.getItems().add(new RunExampleCommand("4", "string varf; " +
                " varf=\"test.in\"; " +
                " openRFile(varf); " +
                " int varc; " +
                " readFile(varf,varc) ;print(varc); " +
                " readFile(varf,varc); print(varc) " +
                " closeRFile(varf) ", controller4));
        this.examplesListView.getItems().add(new RunExampleCommand("5", "int a; a = 25; int b; b = 30; IF (a < b) THEN print(a) ELSE print(b)", controller5));
        this.examplesListView.getItems().add(new RunExampleCommand("6", "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)", controller6));
        this.examplesListView.getItems().add(new RunExampleCommand("7", "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)", controller7));
        this.examplesListView.getItems().add(new RunExampleCommand("8", "Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);", controller8));
        this.examplesListView.getItems().add(new RunExampleCommand("9", "Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))", controller9));
        this.examplesListView.getItems().add(new RunExampleCommand("10", "int v; v=4; (while (v>0) print(v);v=v-1);print(v)", controller10));
        this.examplesListView.getItems().add(new RunExampleCommand("11", "int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))", controller11));
        this.examplesListView.getItems().add(new RunExampleCommand("12", "int v; Ref int  a; v=10; new(a, 22); Fork( wH(a, 30); v=32; print(v);print(rH(a)); (Fork( v=16; print(v));(print(v);print(rH(a));", controller12));
        this.examplesListView.getItems().add(new RunExampleCommand("13", "int v; Ref int a; v=10; new(a, 22); Fork(wH(a, 30); v=32; print(v); Fork(wH(a, 100); v=50; print(v)); print(rH(a)); print(v); print(rH(a)))", controller13));

        this.examplesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
