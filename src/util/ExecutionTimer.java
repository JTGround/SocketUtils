/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;
import java.util.Hashtable;

public class ExecutionTimer {

    private long startTime,stopTime,elapsedTime;
	private Hashtable<String,Long> markTable = new Hashtable<String,Long>();
	private boolean isActive = false;
	
	public ExecutionTimer() {		
	}
	
	public void start() {
		startTimer();
	}
	
	private void startTimer() {
		if(!isActive) {
			startTime = System.currentTimeMillis();
			elapsedTime = 0;
			isActive = true;
		}		
		else {
			startTime = System.currentTimeMillis();
		}
	}

	public void stop() {
		stopTime = System.currentTimeMillis();
		elapsedTime = elapsedTime + stopTime - startTime;
	}
	
	public void mark(String markName,boolean resetTimer) {
		long curtime = System.currentTimeMillis();
		markTable.put(markName, elapsedTime + curtime-startTime);
		if(resetTimer) {
			resetTimer();
			startTimer();
		}
		
	}

	public void reset() {
		resetTimer();
	}
	
	private void resetTimer() {
		startTime = 0;
		stopTime = 0;
		elapsedTime = 0;
		isActive = false;
	}
	
	public long getMillis() {
		long curtime = System.currentTimeMillis();
		return elapsedTime + curtime - startTime;
	}
	
	public long getMarkedTime(String markName) {
		return markTable.get(markName);
	}

}
