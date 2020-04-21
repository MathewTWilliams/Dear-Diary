package v1;

import java.io.Serializable;
import java.util.HashMap;

public interface Trackable extends Serializable{
	
	
	public abstract boolean trackStat(int stat);
	public abstract HashMap<String, Integer> getStat();
}
