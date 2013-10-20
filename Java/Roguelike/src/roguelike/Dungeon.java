package roguelike;

public class Dungeon {
	private char 	Map[];
	private int		Width;
	private int 	Height;
	private int		MAXDEPTH = 8;
	private int		MINDIM 	 = 4;
	private java.util.Random Random;
	private java.util.Stack<Rectangle> Rects;
	private BinarySpaceTree BSP;
	
	private void DivideMap(Rectangle R, int Depth, boolean Vertical) {
		if(Depth >= MAXDEPTH || (R.Width() < MINDIM || R.Height() < MINDIM))
		{
			return;
		}
		Vertical = (Random.nextFloat() > 0.5) ? true : false;
		Rectangle R1, R2;
		int Cut = 0;
		if(Vertical) {
			Cut = (int)((Random.nextFloat()*(R.Height()-MINDIM)) + R.Top() + MINDIM);
			R1 = new Rectangle(R.Left()+1, R.Top()+1, R.Right(), Cut-1);
			R2 = new Rectangle(R.Left()+1, Cut, R.Right(), R.Bottom());
		} else {
			Cut = (int)((Random.nextFloat()*(R.Width()-MINDIM)) + R.Left() + MINDIM);
			R1 = new Rectangle(R.Left()+1, R.Top()+1, Cut-1, R.Bottom());
			R2 = new Rectangle(Cut, R.Top()+1, R.Right(), R.Bottom());
		}
		if(R1.Width() > MINDIM && R1.Height() > MINDIM)
		{
			BSP.addRect(R1);
			DivideMap(R1, Depth + 1, (Vertical) ? false : true);
		}
		if(R2.Width() > MINDIM && R2.Height() > MINDIM)
		{
			BSP.addRect(R2);
			DivideMap(R2, Depth + 1, (Vertical) ? false : true);
		}
	}
	
	public void MakeCorridors() {
		Rects.clear();
		BSP.getLeafStack(Rects);
		while(Rects.size() > 1) {
			Rectangle R2 = Rects.pop();
			Rectangle R1 = Rects.pop();
			int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
			R1.Center(x1, y1);
			R2.Center(x2, y2);
			if(R1.FacingVertical(R2)) {
				
				int y = 0;
				do {
					y = (int)(Random.nextFloat() * (R1.Bottom() - R1.Top())) + R1.Top();
				} while(false);//!R2.Contains(x2, y));
				for(int i = x1; i < x2; i++)
					Map[i + y*Width] = '.';
			}
			else if(R1.FacingHorizontal(R2)) {
				int x = 0;
				do {
					x = (int)(Random.nextFloat() * (R1.Right() - R1.Left())) + R1.Left();
				} while(false);//!R2.Contains(x, y2));
				for(int i = y1; i < y2; i++)
					Map[x + i*Width] = '.';
			}
			else {
				if(x2 > x1 || y2 > y1) {
					R1.Center(x2, y2);
					R2.Center(x1, y1);
				}
			}
		}
	}
	
	public Dungeon(int Width, int Height, long Seed) {
		this.Width 	= Width; 
		this.Height = Height;
		Random = new java.util.Random(Seed);
		Rectangle R = new Rectangle(0, 0, Width-1, Height-1);
		Rects = new java.util.Stack<Rectangle>();
		BSP = new BinarySpaceTree (R);
		Map = new char[this.Width*this.Height];
		for(int x = 0; x < this.Width; x++)
			for(int y = 0; y < this.Height; y++)
				Map[x+y*this.Width] = '#';
		DivideMap(R, 0, false);
		BSP.getLeafStack(Rects);
		while(!Rects.empty()) {
			R = Rects.pop();
			/*int X, Y, W, H;
			X = (int)(Math.random() * (R.XCenter() - MINDIM - R.Left()) + R.Left() + MINDIM);
			Y = (int)(Math.random() * (R.YCenter() - MINDIM - R.Top()) + R.Top() + MINDIM);
			W = (int)(Math.random() * (R.Width()/2 - MINDIM) + MINDIM);
			H = (int)(Math.random() * (R.Height()/2 - MINDIM) + MINDIM);
			//Rectangle Room = new Rectangle(X, Y, W, H);*/
			for(int x = R.Left(); x < R.Right(); x++)
				for(int y = R.Top(); y < R.Bottom(); y++)
				{
					Map[x+y*this.Width] = ' ';
				}
		}
		MakeCorridors();
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
