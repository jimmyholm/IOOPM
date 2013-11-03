package roguelike;
import java.util.*;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class Dungeon extends JPanel{
	private static final long serialVersionUID = 5158570127101695897L;
	
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
	private Rectangle Camera = new Rectangle(0, 0, 26, 26);
	private Random  Rnd;
	private List<Room> Rooms;
	private Tile PlayerTile = new Tile((char)2, false, false, new Color(255, 0, 0, 255));
	private Player player;
	public void Step(long ElapsedTime) {
		
	}
	
	public void AddHorizontalCorridor(int x1, int x2, int y) {
		for(int i = Math.min(x1,  x2); i < Math.max(x1, x2) + 1; i++) {
			Map[i+y*Width].SetTile(' ');
			Map[i+y*Width].SetBlocksMovement(false);
			Map[i+y*Width].SetBlocksSight(false);
			Map[i+y*Width].SetColor(Color.black);
		}
	}
	
	public void CenterCamera(int x, int y) {
		if((x - Camera.Width()/2) < 0)
			x = Camera.Width()/2;
		if((x + Camera.Width() / 2) >= Width)
			x = Width-1 - Camera.Width() / 2;
		if((y - Camera.Height() / 2) < 0)
			y = Camera.Height() / 2;
		if((y + Camera.Height() / 2) >= Height)
			y = Height-1 - Camera.Height() / 2;
		int CW = Camera.Width()/2;
		int CH = Camera.Height()/2;
		int l = x - CW;
		int r = x + CW;
		int t = y - CH;
		int b  =y + CH;
		Camera = new Rectangle(l, t, r, b);
	}
	
	public void AddVerticalCorridor(int y1, int y2, int x) {
		for(int i = Math.min(y1,  y2); i < Math.max(y1, y2) + 1; i++) {
			Map[x+i*Width].SetTile(' ');
			Map[x+i*Width].SetBlocksMovement(false);
			Map[x+i*Width].SetBlocksSight(false);
			Map[x+i*Width].SetColor(Color.black);
		}
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
					Map[x+y*Width].SetColor(Color.black);
				}
		}
	}
	
	public Dungeon(int Width, int Height, long Seed, int MinRooms, int MaxRooms, int MinDim, int MaxDim) {
		this.player = new Player(0, 0, new Stats());
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
				Map[x+y*this.Width] = new Tile((char)219, true, true, new Color(255, 255, 255, 255));
		MakeRooms();
		Room R = Rooms.get(0);
		Rectangle R1  = R.GetArea();
		playerX = Rnd.nextInt((R1.Right()-R1.Left())) + R1.Left();
		playerY = Rnd.nextInt((R1.Bottom()-R1.Top())) + R1.Top();
		CenterCamera(playerX, playerY);
	}
	public Player GetPlayer () {return this.player;}
	public String toString()
	{
		String S = "";
		for(int y = 0; y < this.Height; y++)
		{
			for(int x = 0; x < this.Width; x++)
				S += Map[x+y*this.Width].GetTile();
			S += "\n";
		}
		return S;
	}
	
	public void KeyDown(int Key)
	{
		switch(Key) {
			case 0:
				if(playerX < Width-1)
					playerX++;
				break;
			case 1:
				if(playerY < Height-1)
					playerY++;
				break;
			case 2:
				if(playerX > 0)
					playerX++;
				break;
			default:
				if(playerY > 0)
					playerY--;
				break;
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int w = TileSet.GetInstance().GetTileWidth();
		int h = TileSet.GetInstance().GetTileHeight();
		for(int y = Camera.Top(); y < Camera.Bottom(); y++)
			for(int x = Camera.Left(); x < Camera.Right(); x++)
			{
				if(x == playerX && y == playerY)
					PlayerTile.Draw(g2, (x - Camera.Left())*w, (y - Camera.Top())*h);
				else
					Map[x+y*this.Width].Draw(g2, (x - Camera.Left())*w, (y - Camera.Top())*h);
			}
		}
}
