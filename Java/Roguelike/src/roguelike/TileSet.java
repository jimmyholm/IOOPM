package roguelike;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileSet {
	private BufferedImage TileImage;
	private int TileWidth, TileHeight;
	private int ColCount, RowCount;
	private static TileSet Instance = null;
	private TileSet() {
		TileImage = null;
		TileWidth = 0;
		TileHeight = 0;
		ColCount = 0;
		RowCount = 0;
		
	}
	public static TileSet GetInstance() {
		if(Instance == null)
			Instance = new TileSet();
		return Instance;
	}
	public void Load(String File, int TileWidth, int TileHeight, 
				   int ColCount, int RowCount) {
		try {
			TileImage = javax.imageio.ImageIO.read(new File(File));
		} catch(IOException e) { System.out.println(e); }
		this.TileWidth = TileWidth;
		this.TileHeight = TileHeight;
		this.ColCount = ColCount;
		this.RowCount = RowCount;
	}
	
	public BufferedImage GetTileImage() {
		return TileImage;
	}
	
	public Rectangle GetCharacter(char C) {
		if(C < 0 || C > ColCount*RowCount)
			return new Rectangle(0, 0, 0, 0);
		int l = C % ColCount * TileWidth;
		int t = C / ColCount * TileHeight;
		int r = l + TileWidth;
		int b = t + TileHeight;
		return new Rectangle(l, t, r, b);
	}
	
	public int GetTileWidth() {
		return TileWidth;
	}
	
	public int GetTileHeight() {
		return TileHeight;
	}
}
