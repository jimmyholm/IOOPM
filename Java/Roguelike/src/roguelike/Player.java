package roguelike;

public class Player extends Creature{

	
	public Player (int x, int y, boolean key, Stats stat, Item[] items, Item[] potions)
	{
		this.x = x;
		this.y = y;
		this.key = key;
		this.stats = stats;
		this.items = items;
		this.potions = potions;
	}
	
	public void step(){}
	
	public void addItem (Item item){
		
	}
}
