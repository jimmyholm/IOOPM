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
	
	private Tile	Map[];
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
	private List<Room> Rooms;
	
	public void AddHorizontalCorridor(int x1, int x2, int y) {
		for(int i = Math.min(x1,  x2); i < Math.max(x1, x2) + 1; i++) {
			Map[i+y*Width].SetTile(' ');
			Map[i+y*Width].SetBlocksMovement(false);
			Map[i+y*Width].SetBlocksSight(false);
		}
	}
	
	public void AddVerticalCorridor(int y1, int y2, int x) {
		for(int i = Math.min(y1,  y2); i < Math.max(y1, y2) + 1; i++) {
			Map[x+i*Width].SetTile(' ');
			Map[x+i*Width].SetBlocksMovement(false);
			Map[x+i*Width].SetBlocksSight(false);
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
			int w = (int)((float)h * 1.5);
			int x = Rnd.nextInt(Width-w-1)+1;
			int y = Rnd.nextInt(Height-h-1)+1;
			R = new Rectangle(x, y, x+w, y+h);
			for(ListIterator<Room> it = Rooms.listIterator(); it.hasNext() && Add;) {
				Rectangle R2 = it.next().GetArea();
				if(R.Touching(R2)) {
					Add = false;
					Tries++;
				}
			}
			if(Add) {
				Tries = 0;
				CurrentRooms++;
				Rooms.add(new Room(R));
				if(Rooms.size() > 1) {
					ListIterator<Room> it = Rooms.listIterator(Rooms.size()-2);
					Rectangle R2 = it.next().GetArea();
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
		for(ListIterator<Room> it = Rooms.listIterator(); it.hasNext();) {
			R = it.next().GetArea();
			for(int x = R.Left(); x < R.Right(); x++)
				for(int y = R.Top(); y < R.Bottom(); y++){
					Map[x+y*Width].SetTile(' ');
					Map[x+y*Width].SetBlocksMovement(false);
					Map[x+y*Width].SetBlocksSight(false);
				}
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
		Map = new Tile[this.Width*this.Height];
		Rooms = new ArrayList<Room>();
		for(int x = 0; x < this.Width; x++)
			for(int y = 0; y < this.Height; y++)
				Map[x+y*this.Width] = new Tile('#', true, true);
		MakeRooms();
	}
	
	public String toString()
	{
		String S = "";
		for(int y = 0; y < this.Height; y++)
		{
			for(int x = 0; x < this.Width; x++)
				S += Map[x+y*this.Width].GetTile();
			S += "\n";
		}
		S += CurrentRooms + "/" + GoalRooms;
		return S;
	}
}
