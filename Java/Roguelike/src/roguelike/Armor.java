package roguelike;

import java.awt.Color;

public class Armor extends Item{
	int defense;
	
	public Armor () {
		this.character = (char)14;
		this.color = new Color(255, 128, 128, 128);
		this.defense = DiceRoller.GetInstance().Roll("1d10");
	}
public int GetArmorDefense () {return this.defense;}
public void SetArmorDefense (int defense) {this.defense = defense;}
}
