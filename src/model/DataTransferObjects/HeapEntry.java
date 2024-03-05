package model.DataTransferObjects;

import javafx.beans.property.SimpleStringProperty;
import model.value.ValueInterface;

public class HeapEntry {

    private SimpleStringProperty heapAddress;
    private SimpleStringProperty heapValue;

    public HeapEntry(Integer heapAddress, ValueInterface heapValue){
        this.heapAddress = new SimpleStringProperty(Integer.toString(heapAddress));
        this.heapValue = new SimpleStringProperty(heapValue.toString());
    }

    public SimpleStringProperty heapAddressProperty(){
        return this.heapAddress;
    }

    public SimpleStringProperty heapValueProperty(){
        return this.heapValue;
    }

    public String getHeapAddress(){
        return this.heapAddress.get();
    }

    public String getHeapValue(){
        return this.heapValue.get();
    }

    public void setHeapAddress(Integer newHeapAddress){
        this.heapAddress.set(String.valueOf(newHeapAddress));
    }

    public void setHeapValue(ValueInterface newHeapValue){
        this.heapValue.set(newHeapValue.toString());
    }

}
