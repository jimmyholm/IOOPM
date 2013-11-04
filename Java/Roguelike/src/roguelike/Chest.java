package roguelike;

import java.awt.Color;

public class Chest extends Item {
	Item item1;
	Item item2;
	Item item3;

	public Chest () {
		this.color = new Color(77, 77, 77, 255);
		this.character = '+';
		this.item1 = Item.Create();
		this.item2 = Item.Create();
		this.item3 = Item.Create();
	} 

}
