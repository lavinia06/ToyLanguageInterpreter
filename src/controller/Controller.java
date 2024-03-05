package controller;

import exception.RepositoryException;
import model.ADT.Heap.HeapInterface;
import model.ProgramState;
import model.value.ReferenceValue;
import model.value.ValueInterface;
import repository.RepositoryInterface;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements ControllerInterface{

    private RepositoryInterface repository;
    private ExecutorService executor;

    public Controller(RepositoryInterface repository){
        this.repository = repository;
    }

    private List<Integer> getAddressesFromSymTable(Collection<ValueInterface> symTableValues){
        ///We get a list with all the addreses of the referenceValues from the symbol table
        return  symTableValues.stream()
                .filter(v->v instanceof ReferenceValue)
                .map(v->{ReferenceValue v1 = (ReferenceValue) v; return v1.getHeapAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getAddressesFromHeap(Map<Integer, ValueInterface> heap){
        ///We get a list with all the addreses of the referanceValues from the heap
        return heap.values().stream()
                .filter(v->v instanceof ReferenceValue)
                .map(v->{ReferenceValue v1 = (ReferenceValue) v; return v1.getHeapAddress();})
                .collect(Collectors.toList());
    }

    private Map<Integer, ValueInterface> safeGarbageCollector(List<Integer> symTableAddresses,
                                                              List<Integer> heapReferencedAddressed,
                                                              ProgramState currProgram){
        ///We get the heap and we transform it into a Set in order to apply stream on it
        ///We filter the heap in the following way. We keep all the elements which have their addresss in the symTable or in the heap
        ///Otherwise we don't include them

        HeapInterface<Integer,ValueInterface> heap = currProgram.getHeap();
        return heap.getContent().entrySet().stream()
                .filter(elem-> symTableAddresses.contains(elem.getKey()) || heapReferencedAddressed.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void oneStepExecution(){
        // We fix the number of threads
        executor = Executors.newFixedThreadPool(2);
        // We remove the completed programs
        List<ProgramState> programs = removeCompletedProgram(this.repository.getProgramList());
        if(programs.size() > 0) {
            Collection<ValueInterface> addresses = programs.stream().
                    flatMap(program -> program.getSymbolTable().getContent().values().stream())
                    .collect(Collectors.toList());

            //Collection<ValueInterface> heapAddresses = programs.get(0).getHeap().getContent().values();

            //We apply the safe garbage collector on our heap
            programs.get(0).getHeap().setContent(this.safeGarbageCollector(this.getAddressesFromSymTable(addresses),
                    this.getAddressesFromHeap(programs.get(0).getHeap().getContent()), programs.get(0)));

            try {
                this.oneStepForAllPrograms(programs);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            programs = removeCompletedProgram(this.repository.getProgramList());
        }
        executor.shutdown();
        this.repository.setProgramList(programs);
    }

    @Override
    public void fullProgramExecution() throws Exception {

        // We fix the number of threads
        executor = Executors.newFixedThreadPool(2);
        // We remove the completed programs
        List<ProgramState> programs = removeCompletedProgram(this.repository.getProgramList());
        while(programs.size() > 0){
            Collection<ValueInterface> addresses = programs.stream().
                    flatMap(program -> program.getSymbolTable().getContent().values().stream())
                    .collect(Collectors.toList());

            //Collection<ValueInterface> heapAddresses = programs.get(0).getHeap().getContent().values();

            //We apply the safe garbage collector on our heap
            programs.get(0).getHeap().setContent(this.safeGarbageCollector(this.getAddressesFromSymTable(addresses),
                    this.getAddressesFromHeap(programs.get(0).getHeap().getContent()), programs.get(0)));

            this.oneStepForAllPrograms(programs);
            programs = removeCompletedProgram(this.repository.getProgramList());
        }
        executor.shutdown();
        this.repository.setProgramList(programs);
    }

    @Override
    public void addProgramState(ProgramState newProgramState) {
        repository.addProgramState(newProgramState);
    }

    @Override
    public List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public void oneStepForAllPrograms(List<ProgramState> programStatesList) throws InterruptedException {
        //First we save the initial programStates to a file
        programStatesList.forEach(p -> {

            try {
                this.repository.logPrgStateExec(p);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }

        });

        /// We create a list with the oneStepExecution function for each programState
        /// Mainly we prepare the list of callables
        List<Callable<ProgramState>> callList = programStatesList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStepExecution))
                .collect(Collectors.toList());

        /// It executes the oneStepExecution function for each programState from the list above
        /// And it takes the return values and place them in a list
        /// Mainly it starts the execution of the callables
        /// It returns the list of new ProgramStates (namely threads)
        List<ProgramState> newProgramsList = executor.invokeAll(callList).stream()
                .map(future ->
                {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException ex) {
                        System.out.println(ex.toString());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        /// We add the new created threads to the list of the existing threads
        programStatesList.addAll(newProgramsList);

        /// We save again all the program states, including the old and the new ones to a file
        programStatesList.forEach(p -> {
            try {
                this.repository.logPrgStateExec(p);
            } catch (RepositoryException e) {
                e.printStackTrace();
                ;
            }
        });

        this.repository.setProgramList(programStatesList);
    }

    @Override
    public RepositoryInterface getRepo() {
        return this.repository;
    }
}
//
//    @Override
//    public ProgramState oneStepExecution(ProgramState state) throws Exception {
//        StackInterface<StatementInterface> stack = state.getExecutionStack();
//        if(stack.isEmpty()) throw new EmptyADTException("ProgramState stack is empty!");
//        StatementInterface currentStatement = stack.pop();
//        return currentStatement.execute(state);
//    }
//
//    private Map<Integer, ValueInterface> unsafeGarbageCollector(List<Integer> symTableAddresses, Map<Integer, ValueInterface> heap){
//        return heap.entrySet().stream()
//                .filter(e->symTableAddresses.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }
