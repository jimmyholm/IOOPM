package roguelike;

import java.awt.Color;

public class Weapon extends Item{
	public enum WeaponType {SWORD, AXE, FORK};
	private WeaponType weaponType;
	private int offense;
	private int dexterity;

	public Weapon (){
		this.color = new Color(128, 128, 128, 255);
		switch(DiceRoller.GetInstance().Roll("1d3")){
			case 1: 
				this.weaponType = WeaponType.SWORD;
				this.description = "This is a sword!";
				this.offense = DiceRoller.GetInstance().Roll("1d6");
				this.dexterity = DiceRoller.GetInstance().Roll("1d6");
				this.character = (char)45;
				break;
			case 2:
				this.weaponType = WeaponType.AXE;
				this.description = "This is an axe.";
				this.character = (char)13;
				this.offense = DiceRoller.GetInstance().Roll("1d10");
				this.dexterity = DiceRoller.GetInstance().Roll("1d3");
				break;
			case 3:
				this.weaponType = WeaponType.FORK;
				this.description = "This is a trident.";
				this.character = (char)148;
				this.offense = DiceRoller.GetInstance().Roll("1d2");
				this.dexterity = DiceRoller.GetInstance().Roll("1d2");
				break;
		}
	}
	public String GetWeaponType () {return this.weaponType.toString();}
	public int GetWeaponOffense () {return this.offense;}
	public int GetWeaponDexterity () {return this.dexterity;}
}