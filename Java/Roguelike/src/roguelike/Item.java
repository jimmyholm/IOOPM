package roguelike;

import java.awt.Color;
public class Item {
	//public static string 
	
	protected Stats stat;
	protected char character;
	protected Color color;
	protected String description;
	public Item() {
		this.stat = null;
	}
	public Item (Stats stat, int value){
		this.stat = stat;	
	}
	
	public void Create (Stats Entry){	
		
	}
	
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