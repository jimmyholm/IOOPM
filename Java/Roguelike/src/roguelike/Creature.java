package roguelike;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Creature {
	//position
	protected int x;
	protected int y;

	protected Stats stats;
	protected Weapon weapon;
	protected Shield shield;
	protected Armor armor;
	protected ArrayList<Potion> potions;
	protected boolean key;
	public void step() {
		if (stats.GetString("healthRegen") != "")stats.Set("health", ((stats.GetInt("health")) + (stats.GetInt("healthRegen"))));}; //lägg till maxtak
}

