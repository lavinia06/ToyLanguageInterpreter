package controller;

import model.ProgramState;
import repository.RepositoryInterface;

import java.util.List;

public interface ControllerInterface {
    //ProgramState oneStepExecution(ProgramState state) throws Exception;
    void fullProgramExecution() throws Exception;
    void addProgramState(ProgramState newProgramState);
    List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramList);
    void oneStepForAllPrograms(List<ProgramState> programStatesList) throws InterruptedException;
    void oneStepExecution();
    RepositoryInterface getRepo();
}
