package roguelike;
import java.awt.Color;
import java.util.ArrayList;

public class Player extends Creature{
	private String playerName;
private char character;
	
	public Player (int x, int y, Stats stats)
	{
		this.description = "You are holy worrior";
		this.color = new Color(0, 0, 255, 255);
		this.character = '@';
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
	
	public void Death() {}
	
	public String GetPlayerName () {return this.playerName;}
	public void SetPlayerName (String newName) {this.playerName = newName;} 
	public int GetPlayerX () {return this.x;}
	public int GetPlayerY () {return this.y;}
	public void SetPlayerX (int x) {this.x = x;}
	public void SetPlayerY (int y) {this.y = y;}
	
	@Override
	public boolean IsPlayer() {
		return true;
	}
	
}
