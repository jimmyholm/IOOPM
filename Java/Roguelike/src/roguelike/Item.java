package roguelike;

import java.awt.Color;
public class Item {
	//public static string 
	
	protected char character;
	protected Color color;
	protected String description;
	protected long id;
	public Item() {
	
	}
	
	
	public static Item Create (){
		int dropSwitch = DiceRoller.GetInstance().Roll("1d10");
		switch (dropSwitch){
		case 1: return new Weapon();
		case 2: return new Shield();
		case 3: return new Armor();
		default: return new Potion();
		
		}
		
	}
	
	public void SetId (long id) {
		this.id = id;
	} 
	
	public long GetId() {
		return this.id;
	}
	
	public boolean equals (Item i2) {return i2.id == id;}
	
	public char GetCharacter() {
		return character;
	}
	
	public Color GetColor() {
		return color;
	}
	
	public String GetDescription() {
		return description;
	}
}