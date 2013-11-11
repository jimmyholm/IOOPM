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
	private int		ViewRadius = 4;
	private List<Creature> Creatures;
	private Rectangle Camera = new Rectangle(0, 0, 26, 26);
	private Random  Rnd;
	private List<Room> Rooms;
	private Tile LookTile = new Tile('X', false, false, new Color(255, 0, 255, 255), new Color(255, 0, 255, 255));
	private Player player;
	private enum KeyMode { MOVE, LOOK};
	private KeyMode Mode;
	private int LookX;
	private int LookY;
	
	public void Step(long ElapsedTime) {
	}
	
	public void CameraToMap(Integer x, Integer y) {
		x = Camera.Left() + x;
		y = Camera.Top() + y;
	}

	private void VisibilityLine(int x1, int y1, int x2, int y2) {
		/*
		 function line(x0, y0, x1, y1)
		   dx := abs(x1-x0)
		   dy := abs(y1-y0) 
		   if x0 < x1 then sx := 1 else sx := -1
		   if y0 < y1 then sy := 1 else sy := -1
		   err := dx-dy
		 
		   loop
		     plot(x0,y0)
		     if x0 = x1 and y0 = y1 exit loop
		     e2 := 2*err
		     if e2 > -dy then 
		       err := err - dy
		       x0 := x0 + sx
		     end if
		     if x0 = x1 and y0 = y1 then 
		       plot(x0,y0)
		       exit loop
		     end if
		     if e2 <  dx then 
		       err := err + dx
		       y0 := y0 + sy 
		     end if
		  end loop
 */
		int dx = Math.abs(x2-x1);
		int dy = Math.abs(y2-y1);
		int sx = (x1 < x2) ? 1 : -1;
		int sy = (y1 < y2) ? 1 : -1;
		int err = dx - dy;
		
		while(true) {
			Map[x1+y1*Width].SetVisible(true);
			Map[x1+y1*Width].SetDiscovered(true);
			int e2 = 2*err;
			if(e2 > dy*-1) {
				err -= dy;
				x1 += sx;
			}
			if(x1 >= 0 && x1 < Width && y1 >= 0 && y1 < Height) {
				if( (((sx > 0) ? x1 >= x2-1 : x1 <= x2+1) && ((sy > 0) ? y1 >= y2-1 : y1 <= y2+1)) || Map[x1+y1*Width].BlocksSight()) {
					
					Map[x1+y1*Width].SetVisible(true);
					Map[x1+y1*Width].SetDiscovered(true);
					break;
				}
				if(e2 < dx) {
					err += dx;
					y1 += sy;
				}
			}
		}
	}
	
	public void SetVisible() {
		int CX = Player.GetInstance().GetPlayerX();
		int CY = Player.GetInstance().GetPlayerY();
		int R = ViewRadius;
		//VisibilityLine(CX, CY, CX+R, CY+R);
		for(int i = 0; i < 360; i += 1) {
			double r = ((double)i * (Math.PI/180.0));
			int x = CX + (int)((double)R* Math.cos(r));
			int y = CY + (int)((double)R * Math.sin(r));
			VisibilityLine(CX, CY, x, y);
			/*Map[x+y*Width].SetVisible(true);
			Map[x+y*Width].SetDiscovered(true);*/
		}
	}
	
	public void PopulateRooms() {
		int CreatureCnt = Rnd.nextInt(MAXCREATURES-MINCREATURES) + MINCREATURES;
		for(int i = 0; i < CreatureCnt; i++) {
			int n = Rnd.nextInt(CurrentRooms-2) + 1;
			Room room = Rooms.get(n);
			Rectangle r = room.GetArea();
			int x = Rnd.nextInt(r.Right() - r.Left()) + r.Left();
			int y = Rnd.nextInt(r.Bottom() - r.Top()) + r.Top();
			Tile t = Map[x+y*Width];
			int m = Rnd.nextInt(3) + 1;
			Monster m2 = null;
			if(t.GetCreature() == null)
			{
				m2 = new Monster(m, x, y, this);
				t.SetCreature(m2);//new Monster(m, x, y, this));
				Creatures.add(m2);
			}
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
	
	public boolean IsVisible(int X, int Y) {
		int d = (int)(Math.pow(X - Player.GetInstance().GetPlayerX(), 2.0) + Math.pow(Y - Player.GetInstance().GetPlayerY(), 2.0));
		return d <= Math.pow(ViewRadius, 2.0);
	}
	
	public void AddHorizontalCorridor(int x1, int x2, int y) {
		for(int i = Math.min(x1,  x2); i < Math.max(x1, x2) + 1; i++) {
			//Map[i+y*Width].SetTile(' ');
			Map[i+y*Width].SetBlocksMovement(false);
			Map[i+y*Width].SetBlocksSight(false);
			Map[i+y*Width].SetColor(Color.black, new Color(25, 25, 25, 255));
		}
	}
	
	public void AddVerticalCorridor(int y1, int y2, int x) {
		for(int i = Math.min(y1,  y2); i < Math.max(y1, y2) + 1; i++) {
			//Map[x+i*Width].SetTile(' ');
			Map[x+i*Width].SetBlocksMovement(false);
			Map[x+i*Width].SetBlocksSight(false);
			Map[x+i*Width].SetColor(Color.black, new Color(25, 25, 25, 255));
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
					//Map[x+y*Width].SetTile(' ');
					Map[x+y*Width].SetBlocksMovement(false);
					Map[x+y*Width].SetBlocksSight(false);
					Map[x+y*Width].SetColor(Color.black, new Color(25, 25, 25, 255));
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
				Map[x+y*this.Width] = new Tile((char)219, true, true, new Color(255, 255, 255, 255), new Color(127, 127, 127, 255));
		Rooms = new ArrayList<Room>();
		Creatures = new ArrayList<Creature>();
		MakeRooms();
		PopulateRooms();
		Room R = Rooms.get(0);
		Rectangle R1  = R.GetArea();
		player = Player.GetInstance();
		player.Setup((Rnd.nextInt((R1.Right()-R1.Left())) + R1.Left()), (Rnd.nextInt((R1.Bottom()-R1.Top())) + R1.Top()), new Stats());
		Map[player.GetPlayerX() + player.GetPlayerY() * this.Width].SetCreature(player);
		CenterCamera(player.GetPlayerX(), player.GetPlayerY());
		Mode = KeyMode.MOVE;
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
				if(Mode == KeyMode.MOVE) {
					if(playerX < Width-1)
						++playerX;
				}
				else {
					if(LookX < Camera.Width() && LookX < Width) {
						++LookX;
						InfoPanel.GetInstance().SetDescription(Map[LookX+LookY*Width].GetDescription());
					}
				}
				break;
			case KeyEvent.VK_DOWN:
				if(Mode == KeyMode.MOVE) {
					if(playerY < Height-1)
						++playerY;
				}
				else {
					if(LookY < Camera.Height() && LookY < Width) {
						++LookY;
						InfoPanel.GetInstance().SetDescription(Map[LookX+LookY*Width].GetDescription());
					}
				}
				break;
			case KeyEvent.VK_LEFT:
				if(Mode == KeyMode.MOVE) {
					if(playerX > 0)
						--playerX;
				}
				else {
					if(LookX > 0 && LookX > Camera.Left()) {
						--LookX;
						InfoPanel.GetInstance().SetDescription(Map[LookX+LookY*Width].GetDescription());
					}
				}
					
				break;
			case KeyEvent.VK_UP:
				if(Mode == KeyMode.MOVE) {
					if(playerY > 0)
						--playerY;
				}
				else {
					if(LookY > 0 && LookY > Camera.Top()) {
						--LookY;
						InfoPanel.GetInstance().SetDescription(Map[LookX+LookY*Width].GetDescription());
					}
				}
				break;
			case KeyEvent.VK_K:
				if(Mode == KeyMode.MOVE)
				{
					Mode = KeyMode.LOOK;
					LookX = Camera.Right() - playerX;
					LookY = Camera.Bottom() - playerY;
				}
				break;
			case KeyEvent.VK_ESCAPE:
				if(Mode == KeyMode.LOOK)
					Mode = KeyMode.MOVE;
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
		}
		InfoPanel.GetInstance().Update();
		InfoPanel.GetInstance().repaint();
		repaint();
	}
	
	public Tile getTile(int x, int y)
	{
		return Map[x+y*Width];
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		SetVisible();
		int w = TileSet.GetInstance().GetTileWidth();
		int h = TileSet.GetInstance().GetTileHeight();
		for(int y = Camera.Top(); y < Camera.Bottom(); y++)
			for(int x = Camera.Left(); x < Camera.Right(); x++)
			{
				int X = x-Camera.Left();//Camera.Right() - x;
				int Y = y-Camera.Top();//Camera.Bottom() - y;
				if(Mode == KeyMode.LOOK && (X == LookX && Y == LookY))
					LookTile.Draw(g2, (x-Camera.Left())*w, (y-Camera.Top())*h);
				else {
					/*if(Map[x+y*Width].IsVisible(x, y)) {
						//Map[x+y*Width].SetVisible(true);
						Map[x+y*Width].SetDiscovered(true);
					}
					/*else {
						Map[x+y*Width].SetVisible(false);
					}*/
					Map[x+y*Width].Draw(g2, (x - Camera.Left())*w, (y - Camera.Top())*h);
					Map[x+y*Width].SetVisible(false);
				}
			}
		}
}
