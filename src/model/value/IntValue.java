package model.value;

import model.type.IntType;
import model.type.TypeInterface;

public class IntValue implements ValueInterface{

    public static final int DEFAULT_INT_VALUE = 0;
    private int value;

    public IntValue(){
        this.value = IntValue.DEFAULT_INT_VALUE;
    }

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof IntValue && ((IntValue)another).getValue() == this.value);
    }

    public int getValue(){ return value;}

    public String toString(){
        return String.valueOf(value);
    }

    @Override
    public TypeInterface getType() {
        return new IntType();
    }
}
