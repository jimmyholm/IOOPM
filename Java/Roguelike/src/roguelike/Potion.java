package roguelike;

public class Potion extends Item{

	public enum Size {SMALL, MEDIUM, LARGE};
	private Size size;

	public Potion (Size size){
		this.size = size; 
	}	

	public Potion () {
		switch(DiceRoller.GetInstance().Roll("1d3")){
		case 1: this.size = Size.SMALL; break;
		case 2: this.size = Size.MEDIUM; break;
		case 3: this.size = Size.LARGE; break;
		}
	}

	public int GetHealingPower (){
		switch (size){
		case SMALL: return 10;
		case MEDIUM: return 20;
		case LARGE: return 30;
		default: return 0;
		}
	}

}
