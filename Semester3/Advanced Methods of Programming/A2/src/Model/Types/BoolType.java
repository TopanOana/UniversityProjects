package Model.Types;

public class BoolType implements Type{
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof BoolType);
    }

    @Override
    public String toString() {
        return "bool";
    }
}
