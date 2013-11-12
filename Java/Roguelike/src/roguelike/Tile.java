package roguelike;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
public class Tile {
	private char Tile;
	private boolean BlockMovement;
	private boolean BlockSight;
	private boolean Visible;
	private boolean Discovered;
	private Creature Creature;
	private Item Item; 
	private Color	VisColor;
	private Color   FogColor;
	
	public Tile(char Tile, boolean BlockMovement, boolean BlockSight, Color VisColor, Color FogColor) {
		this.Tile = Tile;
		this.BlockMovement = BlockMovement;
		this.BlockSight = BlockSight;
		this.VisColor = VisColor;
		this.FogColor = FogColor;
		Creature = null;
		Item = null;
		Discovered = false;
		Visible = false;
	}
	
	public char GetTile() {
		return Tile;
	}
	
	public void SetTile(char Tile) {
		this.Tile = Tile;
	}
	
	public void SetColor(Color VisColor, Color FogColor) {
		this.VisColor = VisColor;
		this.FogColor = FogColor;
	}
	
	public boolean CanMove() {
		return (!BlockMovement && Creature == null);
	}
	
	public boolean CanAttack(boolean isPlayer) {
		return (!BlockMovement && ((isPlayer && Creature != null) || (!isPlayer && Creature.IsPlayer() == true)));
	}
	
	public boolean CanHaveItem() {
		return (!BlockMovement && Item == null);
	}
	
	public Item GetItem() {
	 	return Item;
	}
	
	public Creature GetCreature() {
	    return Creature;
	}
	
	public void SetCreature(Creature Creature) {
		this.Creature = Creature; 
	}
	
	public void SetItem(Item Item) {
		this.Item = Item;
	}
	
	public void SetVisible(boolean Visible) {
		this.Visible = Visible;
	}
	
	public boolean Move(Tile nextTile) {
		if(nextTile.CanMove())
		{
			nextTile.Creature = Creature;
			Creature = null;
			return true;
		}
		return false;
	}
	
	public boolean BlocksMovement() {
		return BlockMovement;
	}
	
	public void SetBlocksMovement(boolean Block) {
		BlockMovement = Block;
	}
	
	public boolean BlocksSight() {
		return BlockSight;
	}
	
	public void SetBlocksSight(boolean Block) {
		BlockSight = Block;
	}
	
	public boolean IsDiscovered() {
		return Discovered;
	}
	
	public boolean IsVisible() {
		return Visible;
	}
	
	public void SetDiscovered(boolean Discovered) {
		this.Discovered = Discovered;
	}
	
	public void Draw(Graphics2D g2, int x, int y) {
		char c = (!Discovered) ? (char)178 : (Creature != null && Visible) ? Creature.GetCharacter() : (Item != null) ? Item.GetCharacter() : Tile;
		Rectangle R = TileSet.GetInstance().GetCharacter(c);
		if(R == new Rectangle(0, 0, 0, 0))
			return;
		int w = TileSet.GetInstance().GetTileWidth();
		int h = TileSet.GetInstance().GetTileHeight();
		BufferedImage Render = new BufferedImage(w, h, TileSet.GetInstance().GetTileImage().getType());
		Graphics2D g2t = Render.createGraphics();
		Color c2 = (!Discovered) ? new Color(50, 50, 50, 255) : (!Visible) ? FogColor : (Creature != null) ? Creature.GetColor() : (Item != null) ? Item.GetColor() : VisColor;
		int Red = c2.getRed();
		int Green = c2.getGreen();
		int Blue = c2.getBlue();
		g2t.drawImage(TileSet.GetInstance().GetTileImage(), 0, 0, w, h, R.Left(), R.Top(), R.Right(), R.Bottom(), null);
		for(int X = 0; X < w; X++)
			for(int Y = 0; Y < h; Y++ ) {
				int Alpha = Render.getRGB(X, Y);
				Alpha = Alpha >>> 24;
				float scale = (float)Alpha / 255.0f;
				int R2 = (int)((float)Red * scale);
				int G2 = (int)((float)Green * scale);
				int B2 = (int)((float)Blue * scale);
				Color Tint = new Color(R2, G2, B2, Alpha);
				Render.setRGB(X, Y, Tint.getRGB());
			}
		g2t.dispose();
		g2.drawImage(Render, x, y, x+w, y+h, 0, 0, w, h, null);//R.Left(), R.Top(), R.Right(), R.Bottom(), null);
	}
	public String GetDescription() {
		if(BlockMovement && BlockSight) {
			return "This is a wall, very solid!";
		}
		if(Creature != null)
			return Creature.GetDescription();
		else if(Item != null)
			return Item.GetDescription();
		return "This is an empty piece of floor.";
	}
}
