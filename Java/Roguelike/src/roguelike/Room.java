package roguelike;

public class Room {
	private Rectangle Area;
	private int ItemCount;
	private int CreatureCount;
	private int MaxItemCount;
	private int MaxCreatureCount;
	private boolean HasChest = false;
	
	public Room(Rectangle Area) {
		this.Area = Area;
		ItemCount = 0;
		CreatureCount = 0;
		MaxItemCount = Area.Width() * Area.Height();
		MaxCreatureCount = Area.Width() * Area.Height() - 4;
	}
	
	public Rectangle GetArea() {
		return Area;
	}
	
	public int GetItemCount() {
		return ItemCount;
	}
	
	public boolean CanHaveItem() {
		return ItemCount < MaxItemCount;
	}
	
	public int GetCreatureCount() {
		return CreatureCount;
	}
	
	public boolean CanHaveCreature() {
		return CreatureCount < MaxCreatureCount;
	}
	
	public boolean CanHaveChest() {
		return !HasChest;
	}
	public void AddItem() {
		ItemCount++;
	}
	
	public void AddCreature() {
		CreatureCount++;
	}
	
	public void AddChest() {
		HasChest = true;
	}
}
