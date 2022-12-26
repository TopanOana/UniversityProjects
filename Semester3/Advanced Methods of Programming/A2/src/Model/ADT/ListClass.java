package Model.ADT;

import java.util.ArrayList;

public class ListClass<T> implements InterfaceList<T>{
    ArrayList<T> list;

    public ListClass() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public void remove(T element) {
        list.remove(element);
    }

    @Override
    public String toString(){
        return list.toString();
    }
}
