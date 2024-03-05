package model.value;

import model.type.ReferenceType;
import model.type.TypeInterface;

public class ReferenceValue implements ValueInterface{

    private final int heapAddress;
    public static final int DEFAULT_HEAD_ADDRESS = 0;
    private final TypeInterface innerReferencedType;

    public ReferenceValue(TypeInterface innerReferencedType){
        this.heapAddress = DEFAULT_HEAD_ADDRESS;
        this.innerReferencedType = innerReferencedType;
    }

    public ReferenceValue(int heapAddress, TypeInterface innerReferencedType){
        this.heapAddress = heapAddress;
        this.innerReferencedType = innerReferencedType;
    }

    public int getHeapAddress(){
        return this.heapAddress;
    }

    public String toString(){
        return "("+this.heapAddress+","+innerReferencedType.toString()+")";
    }

    @Override
    public TypeInterface getType() {
        ///Returneaza mereu referenceType (returneaza tipul pointerului care e mereu referenceType)
        return new ReferenceType(this.innerReferencedType);
    }
}


