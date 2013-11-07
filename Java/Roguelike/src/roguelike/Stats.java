package roguelike;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
public class Stats {

	private HashMap<String, String> Data;
	
	public Stats() {
		Data = new HashMap<String, String>();
	}
	
	public int GetInt(String Name) {
		if(Data.containsKey(Name))
			return Integer.valueOf(Data.get(Name));
		return Integer.MIN_VALUE;
	}
	
	public float GetFloat(String Name) {
		if(Data.containsKey(Name))
			return Float.valueOf(Data.get(Name));
		return Float.MIN_VALUE;
	}
	
	public String GetString(String Name) {
		if(Data.containsKey(Name))
			return Data.get(Name);
		return "";
	}
	
	public void Set(String Name, Integer Value) {
		if(Data.containsKey(Name))
			Data.put(Name, Value.toString());
	}
	
	public void Set(String Name, Float Value) {
		if(Data.containsKey(Name))
			Data.put(Name, Value.toString());
	}
	
	public void Set(String Name, String Value) {
		if(Data.containsKey(Name))
			Data.put(Name, Value);
	}
	
	public void Add(String Name, Integer Value) {
		if(!Data.containsKey(Name))
			Data.put(Name,  Value.toString());
	}
	
	public void Add(String Name, Float Value) {
		if(!Data.containsKey(Name))
			Data.put(Name,  Value.toString());
	}
	
	public void Add(String Name, String Value) {
		if(!Data.containsKey(Name))
			Data.put(Name,  Value);
	}
	
	public boolean equals (Stats s) {
		if(Data.entrySet().size() != s.Data.entrySet().size())
			return false;
		Iterator<Entry<String, String>> it1 = Data.entrySet().iterator();
		for(; it1.hasNext();) {
			Entry<String, String> e1 = it1.next();
			if(!s.Data.containsKey(e1.getKey()))
				return false;
			if(s.Data.get(e1.getKey()) != e1.getValue())
				return false;
		}
		return true;
	}
}
