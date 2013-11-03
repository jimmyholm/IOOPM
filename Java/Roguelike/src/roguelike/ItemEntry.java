package roguelike;


public class ItemEntry {
	private Stats Stats;
	

	public ItemEntry(String[] Entries){
		Stats = new Stats();
		if (Entries == null || Entries.length == 0) return;
		for(int i = 0; i < Entries.length; i++) {
			int cut = Entries[i].indexOf('=');
			String ln = Entries[i];
			String Name = ln.substring(0, cut);
			String Value = ln.substring(cut+1, ln.length());
			Stats.Add(Name, Value);
		}
	}
	
	Stats GetStats() {
		return Stats;
	}
}
