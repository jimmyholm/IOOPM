package roguelike;

public class Armor {
	int defense;
	
	public Armor () {
		this.defense = DiceRoller.GetInstance().Roll("1d10");
	}
public int GetArmorDefense () {return this.defense;}
public void SetArmorDefense (int defense) {this.defense = defense;}
}
