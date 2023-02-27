package Model.ADT;

import Model.Values.Value;

import java.util.ArrayDeque;
import java.util.Stack;

public class StackClass<T> implements InterfaceStack<T>{
    private ArrayDeque<T> stack;

    @Override
    public T top() {
        return stack.peekFirst();
    }

    public StackClass() {
        this.stack = new ArrayDeque<>();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public ArrayDeque<T> getContent() {
        return this.stack;
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    public StackClass<InterfaceDictionary<String, Value>> deepCopy(){
        StackClass<InterfaceDictionary<String, Value>> aux = new StackClass<>();
        StackClass<InterfaceDictionary<String, Value>> fin = new StackClass<>();

        while(!this.isEmpty()){
            aux.push((InterfaceDictionary<String, Value>) this.pop());
        }

        while(!aux.isEmpty()){
            fin.push(aux.top().deepCopy());
            this.push((T) aux.pop());
        }
        return fin;
    }
}
