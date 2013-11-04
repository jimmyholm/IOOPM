package roguelike;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
public class Tile {
	private char Tile;
	private boolean BlockMovement;
	private boolean BlockSight;
	private boolean Discovered;
	private Creature Creature;
	private Item Item; 
	private Color	Color;
	
	public Tile(char Tile, boolean BlockMovement, boolean BlockSight, Color Color) {
		this.Tile = Tile;
		this.BlockMovement = BlockMovement;
		this.BlockSight = BlockSight;
		this.Color = Color;
		Creature = null;
		Item = null;
	}
	
	public char GetTile() {
		return Tile;
	}
	
	public void SetTile(char Tile) {
		this.Tile = Tile;
	}
	
	public void SetColor(Color Color) {
		this.Color = Color;
	}
	
	public boolean CanMove() {
		return (!BlockMovement && Creature != null);
	}
	public boolean CanAttack(boolean isPlayer) {
		return (!BlockMovement && ((isPlayer && Creature != null) || (!isPlayer && Creature.IsPlayer() == true)));
	}
	
	public Item GetItem() {
	 	return Item;
	}
	
	public Creature GetCreature() {
	    return Creature;
	}
	
	public Tile Move(Tile nextTile) {
		if(nextTile.CanMove())
			nextTile.Creature = Creature;
			Creature = null;
			return nextTile;
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
	
	public void SetDiscovered(boolean Discovered) {
		this.Discovered = Discovered;
	}
	
	public void Draw(Graphics2D g2, int x, int y) {
		char c = (Creature != null) ? Creature.GetCharacter() : (Item != null) ? Item.GetCharacter() : Tile;
		Rectangle R = TileSet.GetInstance().GetCharacter(c);
		if(R == new Rectangle(0, 0, 0, 0))
			return;
		int w = TileSet.GetInstance().GetTileWidth();
		int h = TileSet.GetInstance().GetTileHeight();
		BufferedImage Render = new BufferedImage(w, h, TileSet.GetInstance().GetTileImage().getType());
		Graphics2D g2t = Render.createGraphics();
		Color c2 = (Creature != null) ? Creature.GetColor() : (Item != null) ? Item.GetColor() : Color;
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
		if(Creature!= null)
	}
}
