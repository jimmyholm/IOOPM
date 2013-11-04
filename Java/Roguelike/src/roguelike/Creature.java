package roguelike;
import java.awt.Color;
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
	protected char character;
	protected Color color;

	public void Attack (Creature attacker, Creature defender){
		int attackerOffense = (attacker.stats.GetInt("offense")) + (attacker.weapon.GetWeaponOffense());
		int attackerDexterity = (attacker.stats.GetInt("dexterity")) + (attacker.weapon.GetWeaponDexterity());
		int defenderDefense = (defender.stats.GetInt("defense")) + (defender.armor.GetArmorDefense()) + defender.shield.GetShieldDefense();
		int defenderDexterity = (defender.stats.GetInt("dexterity")) + (defender.weapon.GetWeaponDexterity());
		
		if (DiceRoller.GetInstance().Roll("1d" + attackerDexterity) > DiceRoller.GetInstance().Roll("1d" + defenderDexterity)) {
			
		}
	}



	public void step() {
		if (stats.GetString("healthRegen") != "")

			stats.Set("health", (Math.min(stats.GetInt("maxHealth"), (stats.GetInt("health")) + (stats.GetInt("healthRegen")))));
	} 
	
	public boolean IsPlayer() {
		return false;
	}
	
	public char GetCharacter() {
		return character;
	}
	
	public Color GetColor() {
		return color;
	}
}

