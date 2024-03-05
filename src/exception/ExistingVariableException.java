package exception;

public class ExistingVariableException extends Exception{
    public ExistingVariableException(){super("Variable already existing!");}
    public ExistingVariableException(String message){super(message);}
}


