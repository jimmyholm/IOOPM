package roguelike;
import java.awt.Color;
import java.util.ArrayList;

public class Monster extends Creature {
	public enum MonsterType {GOBLIN, ZOMBIE, TROLL};
	private MonsterType monsterType;
	private Dungeon dungeon;

	public Monster (int monsterNumber, int x, int y, Dungeon dungeon) {
		this.dungeon = dungeon;
		int H = 0;
		switch(monsterNumber){
		case 1:
			this.color = new Color(0 , 255, 0, 255);
			this.character = 'g';
			this.description = "This is an ugly little green goblin";
			this.monsterType = MonsterType.GOBLIN;
			this.stats = new Stats();
			this.armor = new Armor();
			this.weapon = new Weapon();
			this.shield = new Shield();
			this.key = false;
			this.potions = new ArrayList<Potion>();
			this.x = x;
			this.y = y;
			stats.Add("Name", "Goblin");
			stats.Add("offense", 1);
			stats.Add("defense", 1);
			H = DiceRoller.GetInstance().Roll("2d4");
			stats.Add("health", H);
			stats.Add("maxHealth", H);
			stats.Add("dexterity", 2);
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
			stats.Add("Name", "Zombie");
			stats.Add("offense", 3);
			stats.Add("defense", 2);
			H = DiceRoller.GetInstance().Roll("3d6");
			stats.Add("health", H);
			stats.Add("maxHealth", H);
			stats.Add("dexterity", 7);
			break;
		case 3:
			this.color = new Color(12 , 123, 40, 255);
			this.character = 'T';
			this.description = "This is a big and stupid troll";
			this.monsterType = MonsterType.TROLL;
			this.stats = new Stats();
			this.armor = null;
			this.weapon = null;
			this.shield = null;
			this.key = false;
			this.potions = new ArrayList<Potion>();
			this.x = x;
			this.y = y;
			stats.Add("Name", "Troll");;
			stats.Add("offense", 2);
			stats.Add("defense", 2);
			H = DiceRoller.GetInstance().Roll("3d8");
			stats.Add("health", H);
			stats.Add("maxHealth", H);
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
	public int PlayerDistance(int x, int y, Player player) {
		int xDiff = Math.abs(x - player.GetPlayerX());
		int yDiff = Math.abs(y - player.GetPlayerY());	
		return xDiff+yDiff;
	}
	

	public boolean PlayerDetect(Player player) {
		if (PlayerDistance (x, y, Player.GetInstance()) < 4) {	
		return true;
		}
		return false;
	}

	public void Death () {
		MessageList.GetInstance().AddMessage(stats.GetString("Name") + " dies a horrible bloody death; good job, Hero!");
		Game.GetInstance().GetDungeon().KillCreature(this);
	}

//

	public void Step() {
		super.Step();
		if (PlayerDetect(Player.GetInstance())){
			if (PlayerDistance(Player.GetInstance()) <= 1) {
				Attack(this, Player.GetInstance());
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
			default:
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
















