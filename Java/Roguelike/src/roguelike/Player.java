package roguelike;
import java.awt.Color;
import java.util.ArrayList;

public class Player extends Creature{
	private String playerName;
	private static Player Instance = null;
	
	private Player ()
	{
		this.description = "This is me; a handsome, holy warrior, glorybent on destroying evil!";
		this.color = new Color(0, 0, 255, 255);
		this.character = '@';
		this.key = false;
		this.weapon = null;
		this.armor = null;
		this.shield = null;
		this.potions = new ArrayList<Potion>();
	}
	public void Setup(int x, int y, Stats stats) {
		this.x = x;
		this.y = y;
		this.stats = stats;
	}
	public static Player GetInstance() {
		if(Instance == null)
			Instance = new Player();
		return Instance;
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
