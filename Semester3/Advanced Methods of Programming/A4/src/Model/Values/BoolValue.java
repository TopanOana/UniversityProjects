package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value{
    boolean value;

    @Override
    public Value deepCopy() {
        return new BoolValue(value);
    }

    public BoolValue(){ this.value=false;}
    public BoolValue(boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value) ;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }
}
