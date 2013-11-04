package roguelike;
import java.util.*;
import java.awt.event.KeyEvent;
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
	private int		CurrentRooms = 0;
	private int		MAXTRIES = 1000;
	private int		MAXCREATURES = MAXROOMS *2;
	private int		MINCREATURES = CurrentRooms-2;
	private int		GoalRooms = 0;
	private List<Creature> Creatures;
	private Rectangle Camera = new Rectangle(0, 0, 26, 26);
	private Random  Rnd;
	private List<Room> Rooms;
	private Tile PlayerTile = new Tile((char)2, false, false, new Color(255, 0, 0, 255));
	private Player player;
	private enum KeyMode { MOVE, LOOK};
	private KeyMode Mode;
	private int lookX;
	private int lookY;
	public void Step(long ElapsedTime) {
		
	}
	
	public void CameraToMap(Integer x, Integer y) {
		x = Camera.Left() + x;
		y = Camera.Top() + y;
	}
	
	public void PopulateRooms() {
		int CreatureCnt = Rnd.nextInt(MAXROOMS-MINROOMS) + MINROOMS;
		for(int i = 0; i < CreatureCnt; i++) {
			int n = Rnd.nextInt(CurrentRooms-2) + 1;
			Room room = Rooms.get(n);
			Rectangle r = room.GetArea();
			int x = Rnd.nextInt(r.Right() - r.Left()) + r.Left();
			int y = Rnd.nextInt(r.Bottom() - r.Top()) + r.Top();
			Tile t = Map[x+y*Width];
			int m = Rnd.nextInt(3) + 1;
			System.out.println(m);
			if(t.GetCreature() == null)
				t.SetCreature(new Monster(m, x, y, this));
		}
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
	



	public Dungeon(int Width, int Height, int MinRooms, int MaxRooms, int MinDim, int MaxDim) {

		this.Width 	= Width; 
		this.Height = Height;
		Rnd = Game.GetRandomizer();
		MAXROOMS = MaxRooms;
		MINROOMS = MinRooms;
		MINDIM   = MinDim;
		MAXDIM   = MaxDim;
		GoalRooms = Rnd.nextInt(MAXROOMS-MINROOMS) + MINROOMS; 
		Map = new Tile[this.Width*this.Height];
		
		for(int x = 0; x < this.Width; x++)
			for(int y = 0; y < this.Height; y++)
				Map[x+y*this.Width] = new Tile((char)219, true, true, new Color(255, 255, 255, 255));
		Rooms = new ArrayList<Room>();
		MakeRooms();
		PopulateRooms();
		Room R = Rooms.get(0);
		Rectangle R1  = R.GetArea();
		player = Player.GetInstance();
		player.Setup((Rnd.nextInt((R1.Right()-R1.Left())) + R1.Left()), (Rnd.nextInt((R1.Bottom()-R1.Top())) + R1.Top()), new Stats());
		Map[player.GetPlayerX() + player.GetPlayerY() * this.Width].SetCreature(player);
		CenterCamera(player.GetPlayerX(), player.GetPlayerY());
	}
	
	public boolean CanMove (int x, int y) {
	if ((x < 0) || x >= this.Width) {return false;}
	if ((y < 0) || y >= this.Height) {return false;}
	if (Map[(x + y * this.Width) ].CanMove())
		return true;
	return false;
	
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
		return S;
	}
	
	public void KeyDown(int Key)
	{
		int playerX = player.GetPlayerX();
		int playerY = player.GetPlayerY();
		
		switch(Key) {
			case KeyEvent.VK_RIGHT:
				if(playerX < Width-1)
					++playerX;
				break;
			case KeyEvent.VK_DOWN:
				if(playerY < Height-1)
					++playerY;
				break;
			case KeyEvent.VK_LEFT:
				if(playerX > 0)
					--playerX;
				break;
			case KeyEvent.VK_UP:
				if(playerY > 0)
				--playerY;
				break;
			default:
				break;
		}
		if (Map[player.GetPlayerX() + player.GetPlayerY() * this.Width].Move(Map[playerX+playerY*Width])) {
			player.SetPlayerX(playerX);
			player.SetPlayerY(playerY);
			CenterCamera(player.GetPlayerX(), player.GetPlayerY());
			MessageList.GetInstance().AddMessage(Camera.toString());
			
			for(ListIterator<Creature> it = Creatures.listIterator(); it.hasNext();)
			{
				it.next().Step();
			}
			repaint();
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
					Map[x+y*Width].Draw(g2, (x - Camera.Left())*w, (y - Camera.Top())*h);
			}
		}
}
