package roguelike;

import java.awt.Color;
public class Item {
	//public static string 
	
	private Stats stat;
	protected char character;
	protected Color color;
	
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
}