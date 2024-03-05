package model.value;

import model.type.BoolType;
import model.type.TypeInterface;

public class BoolValue implements ValueInterface{

    public static final boolean DEFAULT_BOOL_VALUE = false;
    private boolean value;

    public BoolValue(){
        this.value = BoolValue.DEFAULT_BOOL_VALUE;
    }

    @Override
    public boolean equals(Object another){
        return (another instanceof BoolValue && ((BoolValue)another).getValue() == this.value);
    }

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue(){
        return value;
    }

    public String toString(){
        return String.valueOf(value);
    }

    @Override
    public TypeInterface getType() {
        return new BoolType();
    }
}
