package gui;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Dictionary.MyDictionary;
import model.ADT.Heap.HeapInterface;
import model.ADT.List.ListInterface;
import model.ADT.Stack.StackInterface;
import model.DataTransferObjects.HeapEntry;
import model.DataTransferObjects.SymbolTableEntry;
import model.ProgramState;
import model.statement.StatementInterface;
import model.value.IntValue;
import model.value.StringValue;
import model.value.ValueInterface;
import view.RunExampleCommand;

import javax.management.loading.PrivateClassLoader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {

    private SelectController selectController;
    private RunExampleCommand selectedExample;
    private ProgramState selectedProgram;


    // Here somehow we specify what type of values we are going to store on each row in the table
    // So we are giving it the HeapEntry which is a class that in fact creates somehow a pair
    @FXML
    private TableView<HeapEntry> heapTableView;
    @FXML
    private TableColumn<HeapEntry, String> addressColumn;
    @FXML
    private TableColumn<HeapEntry, String> valueHeapColumn;


    @FXML
    private TableView<SymbolTableEntry> symbolTableView;
    @FXML
    private TableColumn<SymbolTableEntry, String> variableNameColumn;
    @FXML
    private TableColumn<SymbolTableEntry,String> valueSymColumn;

    @FXML
    private ListView<ValueInterface> outListView;

    @FXML
    private ListView<StringValue> fileTableListView;
    @FXML
    private ListView<ProgramState> programStatesListView;

    @FXML
    private ListView<StatementInterface> executionStackListView;

    @FXML
    private TextField nrOfProgramStatesTextField;

    @FXML
    private Button runOneStepButton;

    public void setSelectController(SelectController newSelectController){
        // Set the selector controller
        this.selectController = newSelectController;
        // Create a listener for the list of examples. If an example is selected and it is changed, the necessary changes
        // will be made in the tables
        // So more exactly, it looks whether the current selected example is changed or not
        this.selectController.getExamplesListView().getSelectionModel().selectedItemProperty().addListener((a,b,ex)->this.showDataForSelectedExample(ex));

    }

    @FXML
    private void initialize(){
        // We don't allow the number of program states to be changeable
        this.nrOfProgramStatesTextField.setEditable(false);

        this.addressColumn.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("heapAddress"));
        this.valueHeapColumn.setCellValueFactory(new PropertyValueFactory<HeapEntry, String >("heapValue"));

        this.variableNameColumn.setCellValueFactory(new PropertyValueFactory<SymbolTableEntry, String>("variableName"));
        this.valueSymColumn.setCellValueFactory(new PropertyValueFactory<SymbolTableEntry, String>("value"));

        // When we do this for the list, is somehow like deciding what value to place, like the form of the String
        // (not sure I got it right)
        this.outListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<ValueInterface>() {
            @Override
            public String toString(ValueInterface valueInterface) {
                return valueInterface.toString();
            }

            @Override
            public ValueInterface fromString(String s) {
                return null;
            }
        }));

        this.fileTableListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<StringValue>() {
            @Override
            public String toString(StringValue stringValue) {
                return stringValue.toString();
            }

            @Override
            public StringValue fromString(String s) {
                return null;
            }
        }));

        this.programStatesListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<ProgramState>() {
            @Override
            public String toString(ProgramState programState) {
                return Integer.toString(programState.getId());
            }

            @Override
            public ProgramState fromString(String s) {
                return null;
            }
        }));

        this.executionStackListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<StatementInterface>() {
            @Override
            public String toString(StatementInterface statementInterface) {
                return statementInterface.toString();
            }

            @Override
            public StatementInterface fromString(String s) {
                return null;
            }
        }));

        // Like that we are able to select only one item from the list
        this.programStatesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // We create a listener on the programStatesListView, which in our case will look if there are changes regarding
        // the current program state, more exactly, if there is a current program state selected and it is changed,
        // the necessary changes in the tables/lists will be done as well
        this.programStatesListView.getSelectionModel().selectedItemProperty().addListener((a,b,state)-> {
            if(state != null)
                showDataForSelectedProgramState(state);

        });

        // We activate the button and connect it to the runOneStep function
        this.runOneStepButton.setOnAction(actionEvent -> runOneStep(this.selectController.getExamplesListView().getSelectionModel().getSelectedItems().get(0)));

    }

    private void showDataForSelectedExample(RunExampleCommand example){
        // We clear all the tables and lists and prepare them for new data
        this.heapTableView.getItems().clear();
        this.outListView.getItems().clear();
        this.fileTableListView.getItems().clear();
        this.programStatesListView.getItems().clear();
        this.symbolTableView.getItems().clear();
        this.executionStackListView.getItems().clear();

        // We get the list with the existing programStates
        List<ProgramState> programStates = example.getController().getRepo().getProgramList();

        // If the size is still different from 0 we can update the current programState
        if(programStates.size() != 0)
            this.selectedProgram = programStates.get(0);

        // We get the heap, fileTable and output based on the programState 0 because they are shared and have the same
        // values for each program state
        HeapInterface<Integer, ValueInterface> sharedHeap = this.selectedProgram.getHeap();
        DictionaryInterface<StringValue, BufferedReader> fileTable = this.selectedProgram.getFileTable();
        ListInterface<ValueInterface> output = this.selectedProgram.getOutput();

        // We update their content with the new content
        sharedHeap.getContent().forEach((address, value)->this.heapTableView.getItems().add(new HeapEntry(address, value)));
        fileTable.getContent().forEach((fileName, filePath)->this.fileTableListView.getItems().add(fileName));
        output.getContent().forEach((value)->this.outListView.getItems().add(value));

        programStates.forEach((programState)->this.programStatesListView.getItems().add(programState));

        // We update the number of program states
        this.nrOfProgramStatesTextField.setText(Integer.toString(programStates.size()));

    }

    private void showDataForSelectedProgramState(ProgramState program){
        //We clear the execution stack and the symbol stack
        this.symbolTableView.getItems().clear();
        this.executionStackListView.getItems().clear();

        StackInterface<StatementInterface> executionStack = program.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = program.getSymbolTable();

        //We are going to repopulate them back with the "new values" if it's the case
        executionStack.getContent().forEach((statement)->this.executionStackListView.getItems().add(statement));
        symbolTable.getContent().forEach((name, value)->this.symbolTableView.getItems().add(new SymbolTableEntry(name, value)));
    }

    private void runOneStep(RunExampleCommand ex){
        try{
            ex.getController().oneStepExecution();
        }
        catch (Exception e){

        }
        showDataForSelectedExample(ex);
    }

}


//    @FXML
//    private Label heapTableLabel;
//    @FXML
//    private Label outLabel;
//    @FXML
//    private Label fileTableLabel;
//    @FXML
//    private Label programStatesLabel;
//    @FXML
//    private Label symbolTableLabel;
//    @FXML
//    private Label executionStackLabel;
//    @FXML
//    private Label nrOfProgramStatesLabel;