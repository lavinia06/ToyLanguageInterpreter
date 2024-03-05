package view;

import controller.ControllerInterface;

public class RunExampleCommand extends Command{
    private ControllerInterface controller;

    public RunExampleCommand(String key, String description, ControllerInterface controller){
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute(){
        try{
            controller.fullProgramExecution();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ControllerInterface getController(){
        return this.controller;
    }

    public String toString(){
        return this.getKey() + " " + this.getDescription();
    }
}


/*
            StackInterface<StatementInterface> executionStack = new MyStack<StatementInterface>();
            DictionaryInterface<String, ValueInterface> symTable = new MyDictionary<String, ValueInterface>();
            ListInterface<ValueInterface> output = new MyList<ValueInterface>();
            DictionaryInterface<StringValue, BufferedReader> fileTable = new MyDictionary<StringValue, BufferedReader>();
            ProgramState currentProgramState = new ProgramState(executionStack, symTable, output, fileTable, this.currentStatement);
            RepositoryInterface repo = new Repository(this.location);
            ControllerInterface controller = new Controller(repo);
            controller.addProgramState(currentProgramState);
 */