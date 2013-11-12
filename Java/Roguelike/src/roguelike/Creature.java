package roguelike;
import java.awt.Color;
import java.util.List;

public abstract class Creature {
	//position
	protected long id;
	protected int x;
	protected int y;
	protected String description;
	protected Stats stats;
	protected Weapon weapon;
	protected Shield shield;
	protected Armor armor;
	protected List<Potion> potions;
	protected boolean key;
	protected char character;
	protected Color color;

	public Weapon GetWeapon() {
		return weapon;
	}
	
	public Shield GetShield() {
		return shield;
	}
	
	public Armor GetArmor() {
		return armor;
	}
	
	public int GetPotionCount() {
		return potions.size();
	}
	
	public boolean HasKey() {
		return key;
	}
	
	public void Attack (Creature attacker, Creature defender){
		if(attacker == null || defender == null)
			return;
		int attackerOffense = (attacker.stats.GetInt("offense")) + ((attacker.weapon != null) ? attacker.weapon.GetWeaponOffense() : 0);
		int attackerDexterity = (attacker.stats.GetInt("dexterity")) + ((attacker.weapon != null) ? attacker.weapon.GetWeaponDexterity() : 0);
		int defenderDefense = (defender.stats.GetInt("defense")) + ((defender.armor != null) ? defender.armor.GetArmorDefense() + ((defender.shield != null) ? defender.shield.GetShieldDefense() : 0) : 0);
		int defenderDexterity = (defender.stats.GetInt("dexterity")) + ((defender.weapon != null) ? defender.weapon.GetWeaponDexterity() : 0);
		
		if (DiceRoller.GetInstance().Roll("1d" + attackerDexterity) > DiceRoller.GetInstance().Roll("1d" + defenderDexterity)) {
			int Damage = Math.max(1,(DiceRoller.GetInstance().Roll("1d" + attackerOffense) - DiceRoller.GetInstance().Roll("1d" + defenderDefense)));
			defender.stats.Set("health", defender.stats.GetInt("health") - Damage);
			MessageList.GetInstance().AddMessage(attacker.GetStats().GetString("Name") + " attacks " + defender.GetStats().GetString("Name") + " and hits for " + Damage + " points of damage!");
			if (defender.stats.GetInt("health") <= 0) {defender.Death();}
		}
		else {
			MessageList.GetInstance().AddMessage(attacker.GetStats().GetString("Name") + " attacks " + defender.GetStats().GetString("Name") + " but misses!");
		}
	}

	public void GetPos(Integer X, Integer Y) {
		X = this.x;
		Y = this.y;
	}
	
	public int GetXPos() {
		return x;
	}
	
	public int GetYPos() {
		return y;
	}

	public void SetId (long id) {
		this.id = id;
	}
	
	public long GetId () {
		return this.id;
	}
	
	public boolean equals (Creature c2) {
		return c2.id == id;
		}
	
	@Override
	public boolean equals (Object O) {
		return (O instanceof Creature && (
				id == ((Creature)O).id &&
				 x == ((Creature)O).x &&
				 y == ((Creature)O).y &&
				 description == ((Creature)O).description &&
				 stats.equals(((Creature)O).stats)
				 ));
	}
	
	
	public abstract void Death ();

	public String GetDescription () {
		return this.description;
	}
	
	public Stats GetStats() {
		return stats;
	}

	public void Step() {
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

