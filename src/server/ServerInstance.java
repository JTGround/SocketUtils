/*
 * ServerInstance.java
 *
 * Created on January 1, 2007, 8:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package server;


/**
 * The ServerInstance class is the main starting class of the server framework.
 * This class contains all security managers and functions, connection and other managers,
 * and the blocking tasks that listen for input. All exceptions are thrown up to this level.
 */
public interface ServerInstance {
               
    public void initialize();

    public void run();
    
    public String getInstanceName();
    
    public boolean isInitialized();
    
    public boolean isRunning();
    
    public boolean isConnectionListenerRunning();
        
}
