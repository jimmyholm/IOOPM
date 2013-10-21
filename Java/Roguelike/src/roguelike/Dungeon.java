package roguelike;

public class Dungeon {
	public static class Color{
		public static int Black = 0;
		public static int Red = 1;
		public static int Green = 2;
		public static int Yellow = 3;
		public static int Blue = 4;
		public static int Magenta = 5;
		public static int Cyan = 6;
		public static int White = 7;
	}
	
	private String 	Map[];
	private int		Width;
	private int 	Height;
	/*private int		MINDIM 	 = 8;
	 *private int		MINROOMS = 10;
	 *private int		MAXROOMS = 20;
	private java.util.Random Random;*/
	public String ColorizeString(String s, int FG, boolean FGBright, int BG, boolean BGBright)
	{
		String ret = "";
		if(FG != -1) {
			ret +="\u001b[3" + FG + "";
			if(FGBright)
				ret += ";1";
			ret +="m";
		}
		if(BG != -1){
			ret += "\u001b[4" + BG + "";
			if(BGBright)
				ret += ";1";
			ret += "m";
		}
		ret += s + "\u001b[0m"; 
		return ret;
	}
	
	public void MakeCorridors() {
	}
	
	public void MakeRooms() {
		
	}
	
	public Dungeon(int Width, int Height, long Seed) {
		this.Width 	= Width; 
		this.Height = Height;
		//Random = new java.util.Random(Seed);
		Map = new String[this.Width*this.Height];
		for(int x = 0; x < this.Width; x++)
			for(int y = 0; y < this.Height; y++)
				Map[x+y*this.Width] = ColorizeString("#", Color.White, true, Color.White, false);
	}
	
	public String toString()
	{
		String S = "";
		for(int y = 0; y < this.Height; y++)
		{
			for(int x = 0; x < this.Width; x++)
				S += Map[x+y*this.Width];
			S += "\n";
		}
		return S;
	}
}
