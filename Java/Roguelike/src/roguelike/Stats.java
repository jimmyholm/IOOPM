package roguelike;
import java.util.HashMap;
public class Stats {

	private HashMap<String, Integer> Data;
	
	public Stats() {
		Data = new HashMap<String, Integer>();
	}
	
	public int Get(String Name) {
		if(Data.containsKey(Name))
			return Data.get(Name);
		return -1;
	}
	
	public void Set(String Name, int Value) {
		if(Data.containsKey(Name))
			Data.put(Name, Value);
	}
	
	public void Add(String Name, int Value) {
		if(!Data.containsKey(Name))
			Data.put(Name,  Value);
	}
}
