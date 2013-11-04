package roguelike;
import java.util.ArrayList;

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
		if (stats.GetString("healthRegen") != "")
			stats.Set("health", (Math.min(stats.GetInt("maxHealth"), (stats.GetInt("health")) + (stats.GetInt("healthRegen")))));//lägg till maxtak
	} 
}

