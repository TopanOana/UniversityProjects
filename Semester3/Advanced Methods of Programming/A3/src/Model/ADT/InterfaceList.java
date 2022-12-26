package Model.ADT;

import java.util.List;

public interface InterfaceList<T> {
    void add(T element);
    void remove(T element);

    String toString();

    List<T> getList();
}
