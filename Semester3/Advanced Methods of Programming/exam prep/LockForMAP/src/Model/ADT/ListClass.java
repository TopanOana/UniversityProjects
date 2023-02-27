package Model.ADT;

import java.util.ArrayList;
import java.util.List;

public class ListClass<T> implements InterfaceList<T>{
    ArrayList<T> list;

    public ListClass() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        synchronized (this) {
            list.add(element);
        }
    }

    @Override
    public void remove(T element) {
        synchronized (this) {
            list.remove(element);
        }
    }

    @Override
    public String toString(){
        return list.toString();
    }

    @Override
    public List<T> getList() {
        return list;
    }
}
