package roguelike;
import java.util.*;
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
	private int		MINDIM 	 = 4;
	private int		MAXDIM	 = 8;
	private int		MINROOMS = 10;
	private int		MAXROOMS = 20;
	private int		MAXTRIES = 1000;
	private int		CurrentRooms = 0;
	private int		GoalRooms = 0;
	private Random  Rnd;
	private List<Rectangle> Rooms;
	//private List<Rectangle> Corridors;
	
	public void AddHorizontalCorridor(int x1, int x2, int y) {
		for(int i = Math.min(x1,  x2); i < Math.max(x1, x2) + 1; i++) {
			if(Map[i+y*Width] == ColorizeString(" ", Color.White, false, Color.Black, false))
				return;
			Map[i+y*Width] = ColorizeString(" ", Color.White, false, Color.Black, false);
		}
	}
	
	public void AddVerticalCorridor(int y1, int y2, int x) {
		for(int i = Math.min(y1,  y2); i < Math.max(y1, y2) + 1; i++) {
			if(Map[x+i*Width] == ColorizeString(" ", Color.White, false, Color.Black, false))
				return;
			Map[x+i*Width] = ColorizeString(" ", Color.White, false, Color.Black, false);
		}
	}
	
	
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
	
	public void MakeRooms() {
		boolean Add;
		Rectangle R;
		int Tries = 0;
		while(CurrentRooms != GoalRooms && Tries < MAXTRIES) {
			Add = true;
			int h = Rnd.nextInt(MAXDIM - MINDIM)+MINDIM;
			int w = (int)((float)h*1.5);//Rnd.nextInt(MAXDIM - MINDIM)+MINDIM;
			int x = Rnd.nextInt(Width-w-1)+1;
			int y = Rnd.nextInt(Height-h-1)+1;
			R = new Rectangle(x, y, x+w, y+h);
			for(ListIterator<Rectangle> it = Rooms.listIterator(); it.hasNext() && Add;) {
				Rectangle R2 = it.next();
				if(R.Touching(R2))
				{
					Add = false;
					Tries++;
				}
			}
			if(Add) {
				Tries = 0;
				CurrentRooms++;
				Rooms.add(R);
				if(Rooms.size() > 1) {
					ListIterator<Rectangle> it = Rooms.listIterator(Rooms.size()-2);
					Rectangle R2 = it.next();
					if(Rnd.nextFloat() >= 0.5) {
						AddHorizontalCorridor(R2.XCenter(), R.XCenter(), R2.YCenter());
						AddVerticalCorridor  (R2.YCenter(), R.YCenter(),  R.XCenter());
					}
					else {
						AddVerticalCorridor  (R2.YCenter(), R.YCenter(), R2.XCenter());
						AddHorizontalCorridor(R2.XCenter(), R.XCenter(),  R.YCenter());
					}
				}
			}
		}
		for(ListIterator<Rectangle> it = Rooms.listIterator(); it.hasNext();) {
			R = it.next();
			for(int x = R.Left(); x < R.Right(); x++)
				for(int y = R.Top(); y < R.Bottom(); y++)
					Map[x+y*Width] = ColorizeString(" ", Color.Black, false, Color.Black, false);
		}
	}
	
	public Dungeon(int Width, int Height, long Seed, int MinRooms, int MaxRooms, int MinDim, int MaxDim) {
		this.Width 	= Width; 
		this.Height = Height;
		Rnd = new Random(Seed);
		MAXROOMS = MaxRooms;
		MINROOMS = MinRooms;
		MINDIM   = MinDim;
		MAXDIM   = MaxDim;
		GoalRooms = Rnd.nextInt(MAXROOMS-MINROOMS) + MINROOMS; 
		Map = new String[this.Width*this.Height];
		Rooms = new ArrayList<Rectangle>();
		//Corridors = new ArrayList<Rectangle>();
		for(int x = 0; x < this.Width; x++)
			for(int y = 0; y < this.Height; y++)
				Map[x+y*this.Width] = ColorizeString(" ", Color.White, true, Color.White, false);
		MakeRooms();
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
		S += CurrentRooms + "/" + GoalRooms;
		return S;
	}
}
