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
		int Health = DiceRoller.GetInstance().Roll("2d6+3");
		int Offense = DiceRoller.GetInstance().Roll("2d4");
		int Defense = DiceRoller.GetInstance().Roll("2d4");
		int Dex = DiceRoller.GetInstance().Roll("2d4");
		this.stats = new Stats();
		this.stats.Add("Name", "The Hero");
		this.stats.Add("maxHealth", Health);
		this.stats.Add("health", Health);
		this.stats.Add("offense", Offense);
		this.stats.Add("defense", Defense);
		this.stats.Add("dexterity", Dex);
		this.stats.Add("HealthRegen", 3);
		this.weapon = new Weapon();
		this.armor = new Armor();
		this.shield = new Shield();
		
	}
	public void Setup(int x, int y, Stats stats) {
		this.x = x;
		this.y = y;
	}
	public static Player GetInstance() {
		if(Instance == null)
			Instance = new Player();
		return Instance;
	}
	
	public void step(){
		super.Step();
	}
	
	public void Death() {
		MessageList.GetInstance().AddMessage("The hero falls; a glorious death in battle - but death nonetheless. Game Over.");
		Game.GetInstance().GameOver();
	}
	
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
