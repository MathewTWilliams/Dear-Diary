package v1;

import java.io.Serializable;
import java.util.HashMap;
/*
 * Interface to implement new trackers 
 * 
 * @author Julian Pino
 * @version 4/29/2020
 */
public interface Trackable extends Serializable{
	
	
	public abstract boolean trackStat(int stat);
	public abstract HashMap<String, Integer> getStat();
}
