package util;

public class UnsynchronizedQueue<T> {

    private int topIndex = 0;
    private int bottomIndex = 0;
    private int capacity;
    private T[] commandArray;
    private boolean isFull = false;
    private boolean isEmpty = true;
    private int ct = 0;
    
    /** Queue starts at 0 index and proceeds to capacity
     * 
     * @param capacity
     */
    public UnsynchronizedQueue(int capacity) {
        commandArray = (T[]) new Object[capacity];
        this.capacity = capacity;
    }
    
    //draws from bottom of array
    public void offer(T command) {
        if(!isFull) {
            commandArray[topIndex] = command;            
            topIndex += 1; //resets counter for next object
            if(topIndex==capacity) topIndex = 0; //sets back to 0 if reach end of array            
            ct += 1;
            isEmpty = false;
            if(ct==capacity) isFull = true;
        }            
        else javax.swing.JOptionPane.showMessageDialog(null, "array full");
    }
    
    //returns and removes top value
    public T poll() {
        T command = null;
        if(!isEmpty) {
            command = commandArray[bottomIndex];
            bottomIndex += 1; //resets counter for next object
            if(bottomIndex==capacity) bottomIndex = 0;
            ct -= 1;
            isFull = false;
            if(ct==0) isEmpty = true;
        }
        else javax.swing.JOptionPane.showMessageDialog(null, "array empty");
        return command;
    }
    
    public T peek() {
        T command = null;
        if(!isEmpty) command = commandArray[bottomIndex];
        return command;
    }
    
    public int count() {
        return ct;
    }
    
    public boolean isFull() {
        return isFull;
    }
    
    public boolean isEmpty() {
        return isEmpty;
    }
}
