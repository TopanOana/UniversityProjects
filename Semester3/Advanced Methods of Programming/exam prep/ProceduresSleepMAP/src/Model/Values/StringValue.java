package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value{
    String value;

    public String getValue() {
        return value;
    }

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return value.equals(obj.toString());
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
