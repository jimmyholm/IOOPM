package roguelike;

public class Shield {
	private int defense;
	private int offense;
	
	public Shield () {
		this.defense = DiceRoller.Roll("d6");
		this.offense = 0 - (DiceRoller.Roll("d6"));
	}
	
	public int GetShieldDefense () {return this.defense;};
	public int GetShieldOffense () {return this.offense;};

}
