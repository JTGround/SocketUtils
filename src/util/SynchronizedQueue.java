/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;
import java.util.Vector;

public class SynchronizedQueue<T> {

    private Vector<T> list = new Vector<T>();
    
    synchronized public void offer(T obj) {
        list.addElement(obj);
        notifyAll();
    }
    
    synchronized public T take() {
        while(true) {
            if(list.size()>0) {
                T o = list.elementAt(0);
                list.removeElementAt(0);
                return o;
            }
            else {
                try { 
                    wait();
                }
                catch(InterruptedException iex) {System.out.println(iex.getMessage());}
            }
        }
    }
}
