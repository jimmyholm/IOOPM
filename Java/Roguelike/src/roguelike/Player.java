package roguelike;

import java.util.ArrayList;

public class Player extends Creature{
	private String playerName;

	
	public Player (int x, int y, Stats stats)
	{
		this.x = x;
		this.y = y;
		this.stats = stats;
		this.key = false;
		this.weapon = null;
		this.armor = null;
		this.shield = null;
		this.potions = new ArrayList<Potion>();
	}
	
	public void step(){

	}
	
	public String GetPlayerName () {return this.playerName;};
	public void SetPlayerName (String newName) {this.playerName = newName;}; 
	public int GetPlayerX () {return this.x;};
	public int GetPlayerY () {return this.y;};
	
	
}
