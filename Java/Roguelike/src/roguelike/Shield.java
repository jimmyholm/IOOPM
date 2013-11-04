package roguelike;

import java.awt.Color;

public class Shield extends Item {
	private int defense;
	private int offense;
	
	public Shield () {
		this.character = (char)15;
		this.description = "This is a shield.";
		this.color = new Color(128, 128, 128, 255);
		this.defense = DiceRoller.GetInstance().Roll("1d6");
		this.offense = 0 - (DiceRoller.GetInstance().Roll("1d6"));
	}
	
	public int GetShieldDefense () {return this.defense;};
	public int GetShieldOffense () {return this.offense;};

}
