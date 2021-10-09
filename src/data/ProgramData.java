/*
 * ProgramData.java
 *
 * Created on March 29, 2006, 12:23 AM
 *
 */

package data;
import java.util.Hashtable;
import server.ServerInstanceProperties;

import java.io.*;

import javax.swing.JOptionPane;


public class ProgramData {
    
    public ProgramData() {
    }
    
    /**
     * Method for reading Server Instance List from file.
     * @return
     */
    public Hashtable<String, ServerInstanceProperties> loadInstanceList() {
        Hashtable<String, ServerInstanceProperties> instanceList = null;
        File f = new File("instancelist.dat");
        if(f.exists()) {
            try {
                ObjectInputStream connectionStream = new ObjectInputStream(new FileInputStream(f));
                instanceList = (Hashtable<String, ServerInstanceProperties>)connectionStream.readObject();
                connectionStream.close();
            }
            catch(FileNotFoundException fnfex) { JOptionPane.showMessageDialog(null,"File that contains server instance list was not found." + fnfex.getMessage()); }
            catch(IOException ioex) { JOptionPane.showMessageDialog(null,"IO Exception. " + ioex.getMessage()); }
            catch(ClassNotFoundException cnfex) { JOptionPane.showMessageDialog(null,"Class not found." + cnfex.getMessage()); }
        }
        else instanceList = new Hashtable<String, ServerInstanceProperties>();
        return instanceList;
    }
    
    /**
     * Method for writing Server Instance List to file.
     * @param instanceList
     */
    public void writeInstanceList(Hashtable<String, ServerInstanceProperties> instanceList) {
        String filename = "instancelist.dat";
        try {
            ObjectOutputStream outstream = new ObjectOutputStream(new FileOutputStream(filename));
        outstream.writeObject(instanceList);
        outstream.flush();
        outstream.close();
        }
        catch(FileNotFoundException fnfex) {JOptionPane.showMessageDialog(null,fnfex.getMessage()); }
        catch(IOException ioex) { JOptionPane.showMessageDialog(null,ioex.getMessage()); }
        
        
    }
}
