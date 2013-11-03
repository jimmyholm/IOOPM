package roguelike;

public class Weapon {
	public enum WeaponType {SWORD, AXE, FORK};
	private WeaponType weaponType;
	private int offense;
	private int dexterity;

	public Weapon (){
		switch(DiceRoller.Roll("d3")){
		case 1: 
			this.weaponType = WeaponType.SWORD;
			this.offense = DiceRoller.Roll("d6");
			this.dexterity = DiceRoller.Roll("d6");
			break;
		case 2:
			this.weaponType = WeaponType.AXE;
			this.offense = DiceRoller.Roll("d10");
			this.dexterity = DiceRoller.Roll("d3");
			break;
		case 3:
			this.weaponType = WeaponType.FORK;
			this.offense = DiceRoller.Roll("d2");
			this.dexterity = DiceRoller.Roll("d2");
			break;
	}
	}
	public String GetWeaponType () {return this.weaponType.toString();}
	public int GetWeaponOffense () {return this.offense;}
	public int GetWeaponDexterity () {return this.dexterity;}
}