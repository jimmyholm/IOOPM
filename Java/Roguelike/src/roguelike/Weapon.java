package roguelike;

public class Weapon {
	public enum WeaponType {SWORD, AXE, FORK};
	private WeaponType weaponType;
	private int offense;
	private int dexterity;

	public Weapon (){
		switch(DiceRoller.GetInstance().Roll("1d3")){
		case 1: 
			this.weaponType = WeaponType.SWORD;
			this.offense = DiceRoller.GetInstance().Roll("1d6");
			this.dexterity = DiceRoller.GetInstance().Roll("1d6");
			break;
		case 2:
			this.weaponType = WeaponType.AXE;
			this.offense = DiceRoller.GetInstance().Roll("1d10");
			this.dexterity = DiceRoller.GetInstance().Roll("1d3");
			break;
		case 3:
			this.weaponType = WeaponType.FORK;
			this.offense = DiceRoller.GetInstance().Roll("1d2");
			this.dexterity = DiceRoller.GetInstance().Roll("1d2");
			break;
	}
	}
	public String GetWeaponType () {return this.weaponType.toString();}
	public int GetWeaponOffense () {return this.offense;}
	public int GetWeaponDexterity () {return this.dexterity;}
}