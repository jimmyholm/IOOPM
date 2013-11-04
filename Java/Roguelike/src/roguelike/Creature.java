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
		if (stats.GetString("healthRegen") != "") {
			stats.Set("health", (Math.min(stats.GetInt("maxHealth"), (stats.GetInt("health")) + (stats.GetInt("healthRegen")))));//lägg till maxtak
			stats.Set("healthRegenLeft", stats.GetInt("healthRegenLeft")-1); // Ta bort 1 från "regen-timer"
		}
	} 
}

