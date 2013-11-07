package roguelike;
import java.awt.Color;
import java.util.ArrayList;

public class Monster extends Creature {
	public enum MonsterType {GOBLIN, ZOMBIE, TROLL};
	private MonsterType monsterType;
	private Dungeon dungeon;

	public Monster (int monsterNumber, int x, int y, Dungeon dungeon) {
		this.dungeon = dungeon;
		switch(monsterNumber){
		case 1:
			this.color = new Color(0 , 255, 0, 255);
			this.character = 'g';
			this.description = "This is an ugly little green goblin";
			this.monsterType = MonsterType.GOBLIN;
			this.stats = new Stats();
			this.armor = null;
			this.weapon = null;
			this.shield = null;
			this.key = false;
			this.potions = new ArrayList<Potion>();
			this.x = x;
			this.y = y;
			stats.Add("offense", 2);
			stats.Add("defense", 3);
			stats.Add("health", 30);
			stats.Add("maxHealth", 30);
			stats.Add("dexterity", 8);
			break;
		case 2:
			this.color = new Color(124 , 30, 49, 255);
			this.character = 'z';
			this.description = "This zombie wants BRAIIINS";
			this.monsterType = MonsterType.ZOMBIE;
			this.stats = new Stats();
			this.armor = null;
			this.weapon = null;
			this.shield = null;
			this.key = false;
			this.potions = new ArrayList<Potion>();
			this.x = x;
			this.y = y;
			stats.Add("offense", 3);
			stats.Add("defense", 2);
			stats.Add("health", 45);
			stats.Add("maxHealth", 45);
			stats.Add("dexterity", 7);
			break;
		case 3:
			this.color = new Color(12 , 123, 40, 255);
			this.character = 'T';
			this.description = "This is a stupid troll";
			this.monsterType = MonsterType.TROLL;
			this.stats = new Stats();
			this.armor = null;
			this.weapon = null;
			this.shield = null;
			this.key = false;
			this.potions = new ArrayList<Potion>();
			this.x = x;
			this.y = y;
			stats.Add("offense", 2);
			stats.Add("defense", 2);
			stats.Add("health", 200);
			stats.Add("maxHealth", 200);
			stats.Add("dexterity", 5);
			stats.Add("healthRegen", 5);
			break;
		}
	}

	public int PlayerDistance(Player player) {
		int xDiff = Math.abs(this.x - player.GetPlayerX());
		int yDiff = Math.abs(this.y - player.GetPlayerY());
		return xDiff+yDiff;
		//return (java.lang.Math.sqrt((xDiff * xDiff) + (yDiff * yDiff)));
	}
	
	public int PlayerHorizDistance(Player player) {
		return Math.abs(player.GetPlayerX() - x);
	}
	
	public int PlayerHorizDistance(int x, Player player) {
		return Math.abs(player.GetPlayerX() - x);
	}
	
	public int PlayerVertDistance(Player player) {
		return Math.abs(player.GetPlayerY() - y);
	}
	public int PlayerVertDistance(int y, Player player) {
		return Math.abs(player.GetPlayerY() - y);
	}
	public int PlayerDistance(int x, int y, Player player) {
		int xDiff = Math.abs(x - player.GetPlayerX());
		int yDiff = Math.abs(y - player.GetPlayerY());	
		return xDiff+yDiff;
		//return (java.lang.Math.sqrt((xDiff * xDiff) + (yDiff * yDiff)));
	}
	

	public boolean PlayerDetect(Player player) {
		if (PlayerHorizDistance(Player.GetInstance()) < 4 || PlayerVertDistance(Player.GetInstance()) < 4){
			return true;
		}
		return false;
	}

	public void Death () {}

//

	public void Step() {
		super.Step();
		if (PlayerDetect(Player.GetInstance())){
			if (PlayerDistance(Player.GetInstance()) == 1) { //(PlayerHorizDistance(Player.GetInstance()) < 2 || PlayerVertDistance(Player.GetInstance()) < 2){
//				AttackPlayer();
				}
			else
				MoveToPlayer();
		}
		else
			MoveRoam();
	}

private void MoveToPlayer () {
	int northDistance = PlayerDistance(this.x, this.y - 1, Player.GetInstance());
	int southDistance = PlayerDistance(this.x, this.y + 1, Player.GetInstance());
	int westDistance = PlayerDistance(this.x -1,this.y, Player.GetInstance());
	int eastDistance = PlayerDistance(this.x +1, this.y, Player.GetInstance());
	Tile T = dungeon.getTile(x, y);
	     if (northDistance < PlayerDistance(Player.GetInstance())  && T.Move(dungeon.getTile(this.x, this.y -1))) {this.y -= 1;}
	else if (southDistance < PlayerDistance(Player.GetInstance())  && T.Move(dungeon.getTile(this.x, this.y +1))) {this.y += 1;}
	else if (westDistance  < PlayerDistance(Player.GetInstance()) && T.Move(dungeon.getTile(this.x -1, this.y))) {this.x -= 1;}
	else if (eastDistance  < PlayerDistance(Player.GetInstance()) && T.Move(dungeon.getTile(this.x +1, this.y))) {this.x += 1;}
	else
		System.out.println("error");
	}

@Override
public boolean equals (Object o) {
	return (o instanceof Monster &&
			this.monsterType.equals(((Monster)o).monsterType) &&
			this.dungeon.equals(((Monster)o).dungeon));	
}


private void MoveRoam () {
	boolean foundMove = false;
	int Tries = 0;
	while (!foundMove && Tries < 20){
		int randomDirection = DiceRoller.GetInstance().Roll("1d4");
		Tile T = dungeon.getTile(x, y);
		switch (randomDirection){
			case 1:
				if (T.Move(dungeon.getTile(x, y+1))) { //CanMove(this.x, this.y + 1))  {
						this.y += 1; 
						foundMove = true;
					} 
				break;
			case 2:
				if (T.Move(dungeon.getTile(x, y-1))) {//dungeon.CanMove(this.x, this.y - 1)) {
					this.y -= 1; 
					foundMove = true;
				}
				break;
			case 3:
				if (T.Move(dungeon.getTile(x+1, y))) {//dungeon.CanMove(this.x + 1, this.y)) {
					this.x += 1; 
					foundMove = true;
				}
				break;
			case 4:
				if (T.Move(dungeon.getTile(x-1, y))) {//if (dungeon.CanMove(this.x - 1, this.y)) {
					this.x -= 1; 
					foundMove = true;
				}
				break;
		}
		Tries++;
	}
}

}
















