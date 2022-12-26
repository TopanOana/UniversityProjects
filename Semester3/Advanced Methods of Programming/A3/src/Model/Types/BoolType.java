package Model.Types;

import Model.Values.BoolValue;
import Model.Values.Value;

public class BoolType implements Type{
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof BoolType);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public String toString() {
        return "bool";
    }
}
