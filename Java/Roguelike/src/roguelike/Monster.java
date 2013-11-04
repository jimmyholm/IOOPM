package roguelike;

import java.util.ArrayList;

public class Monster extends Creature {
	public enum MonsterType {GOBLIN, ZOMBIE, TROLL};
	private MonsterType monsterType;
	private Dungeon dungeon;

	public Monster (int monsterNumber, int x, int y, Dungeon dungeon) {
		this.dungeon = dungeon;
		switch(monsterNumber){
		case 1:
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
		case 2:
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
		case 3:
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
		}
	}

	public double PlayerDistance(Player player) {
		int xDiff = (this.x - player.GetPlayerX());
		int yDiff = (this.y - player.GetPlayerY());	
		return (java.lang.Math.sqrt((xDiff * xDiff) + (yDiff * yDiff)));
	}
	
	public double PlayerDistance(int x, int y, Player player) {
		int xDiff = (x - player.GetPlayerX());
		int yDiff = (y - player.GetPlayerY());	
		return (java.lang.Math.sqrt((xDiff * xDiff) + (yDiff * yDiff)));
	}
	

	public boolean PlayerDetect(Player player) {
		if (PlayerDistance(dungeon.GetPlayer()) < 4){
			return true;
		}
		return false;
	}




	public void Step() {
		if (PlayerDetect(dungeon.GetPlayer())){
			if (PlayerDistance(dungeon.GetPlayer()) < 2){
//				AttackPlayer();
				}
			else
				MoveToPlayer();
		}
		else
			MoveRoam();
	}

private void MoveToPlayer () {
	double northDistance = PlayerDistance(this.x, this.y + 1, dungeon.GetPlayer());
	double southDistance = PlayerDistance(this.x, this.y - 1, dungeon.GetPlayer());
	double westDistance = PlayerDistance(this.x -1, this.y, dungeon.GetPlayer());
	double eastDistance = PlayerDistance(this.x +1, this.y, dungeon.GetPlayer());
	if (northDistance < PlayerDistance(dungeon.GetPlayer()) || dungeon.CanMove(this.x, this.y +1)) {this.y = this.y + 1;}
	else
	if (southDistance < PlayerDistance(dungeon.GetPlayer()) || dungeon.CanMove(this.x, this.y -1)) {this.y = this.y - 1;}
	else
	if (westDistance < PlayerDistance(dungeon.GetPlayer()) || dungeon.CanMove(this.x -1, this.y)) {this.x = this.x - 1;}
	else
	if (eastDistance < PlayerDistance(dungeon.GetPlayer()) || dungeon.CanMove(this.x +1, this.y)) {this.x = this.x + 1;};
	}

private void MoveRoam () {

	boolean foundMove = false;
while (!foundMove){
int randomDirection = DiceRoller.GetInstance().Roll("1d4");
switch (randomDirection){
case 1:
	if (dungeon.CanMove(this.x, this.y + 1)) {this.y = this.y + 1; foundMove = true;} 
	break;
case 2:
	if (dungeon.CanMove(this.x, this.y - 1)) {this.y = this.y - 1; foundMove = true;}
	break;
case 3:
	if (dungeon.CanMove(this.x + 1, this.y)) {this.y = this.x + 1; foundMove = true;}
	break;
case 4:
	if (dungeon.CanMove(this.x - 1, this.y)) {this.y = this.x - 1; foundMove = true;}
	break;
}

}

}

}
















