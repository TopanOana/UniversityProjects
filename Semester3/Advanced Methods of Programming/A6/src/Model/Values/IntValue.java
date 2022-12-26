package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value{
    int value;

    @Override
    public Value deepCopy() {
        return new IntValue(value);
    }

    public IntValue(){this.value=0;}

    public IntValue(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value) ;
    }

    @Override
    public Type getType() {
        return new IntType();
    }
}
