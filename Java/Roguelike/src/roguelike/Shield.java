package roguelike;

public class Shield {
	private int defense;
	private int offense;
	
	public Shield () {
		this.defense = DiceRoller.GetInstance().Roll("1d6");
		this.offense = 0 - (DiceRoller.GetInstance().Roll("1d6"));
	}
	
	public int GetShieldDefense () {return this.defense;};
	public int GetShieldOffense () {return this.offense;};

}
