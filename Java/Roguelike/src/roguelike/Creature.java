package roguelike;

public abstract class Creature {
	//position
	protected int x;
	protected int y;

	protected boolean key;
	protected Stats stats;
	protected Item[] items;
	protected Item[] potions;
	public abstract void step();
}

