package roguelike;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class EntryRepo {
	private List<ItemEntry> 	Items;
	//List<CreatureEntry> Creatures;
	private static EntryRepo Instance = null;
	
	private EntryRepo() {
		Items = new ArrayList<ItemEntry>();
		//Creatures = new ArrayList<Creatureentry>();
	}
	
	public static EntryRepo GetInstance() {
		if(Instance == null)
			Instance = new EntryRepo();
		return Instance;
	}
	
	public List<String> GetItemTypes() {
		List<String> ret = new ArrayList<String>();
		for(ListIterator<ItemEntry> It = Items.listIterator(); It.hasNext();) {
			String s = It.next().GetStats().GetString("Type");
			if(!ret.contains(s));
				ret.add(s);
		}
		return ret;
	}
	
	public int CountItemType(String Type) {
		int ret = 0;
		for(ListIterator<ItemEntry> It = Items.listIterator(); It.hasNext();) {
			String s = It.next().GetStats().GetString("Type");
			if(s.equals(Type))
				ret++;
		}
		return ret;
	}
	
	public ItemEntry GetRandomOfType(String Type) {
		ArrayList<ItemEntry> Entries = new ArrayList<ItemEntry>();
		for(ListIterator<ItemEntry> It = Items.listIterator(); It.hasNext();) {
			ItemEntry n = It.next();
			if(n.GetStats().GetString("Type").equals(Type))
				Entries.add(n);
		}
		int r = Game.GetRandomizer().nextInt(Entries.size());
		return Entries.get(r);
	}
	
	public void Load(String File) {
		FileInputStream fs = null;
		BufferedReader read = null;
		try {
			fs = new FileInputStream(File);
			read = new BufferedReader(new InputStreamReader(fs));
			String line = read.readLine();
			while(line != null) {
				if(line.equals("[ITEM]"))
				{
					ArrayList<String> Stats = new ArrayList<String>();
					line = read.readLine();
					while(!line.equals("[/ITEM]")) {
						if(line.charAt(0) == '[') {
							throw new IOException("Malformed resource file.");
						}
						else if(line.charAt(0) != '#') 
							Stats.add(line);
						line = read.readLine();
					}
					String[] st = new String[Stats.size()];
					Stats.toArray(st);
					Items.add(new ItemEntry(st));
				}
				if(line.equals("[CREATURE]"))
				{
					ArrayList<String> Stats = new ArrayList<String>();
					line = read.readLine();
					while(line.equals("[/CREATURE]")) {
						if(line.charAt(0) == '[') {
							throw new IOException("Malformed resource file.");
						}
						else if(line.charAt(0) != '#') 
							Stats.add(line);
						line = read.readLine();
					}
					//Creatures.add(new CreatureEntry((String[])Stats.toArray()));
				}
				line = read.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			try {
				read.close();
				fs.close();
			} catch(IOException e) {
				System.out.println(e);
			}
		}
	}
}
