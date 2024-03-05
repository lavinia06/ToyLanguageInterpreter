package repository;

import exception.RepositoryException;
import model.ProgramState;

import java.util.List;

public interface RepositoryInterface {
    //ProgramState getCurrentProgramState() throws Exception;
    void addProgramState(ProgramState newProgramState);
    void logPrgStateExec(ProgramState program) throws RepositoryException;
    void clearFile() throws Exception;
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> prg);
}
