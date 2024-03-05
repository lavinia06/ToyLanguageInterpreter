package exception;

public class EmptyADTException extends Exception{
    public EmptyADTException(){super("The ADT is empty!");}
    public EmptyADTException(String message){super(message);}
}
