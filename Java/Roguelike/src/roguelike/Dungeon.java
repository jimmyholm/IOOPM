package roguelike;

public class Dungeon {
	private char 	Map[];
	private int		Width;
	private int 	Height;
	private int		MAXDEPTH = 4;
	private int		MINDIM 	 = 4;
	private java.util.Stack<Rectangle> Rects;
	private void DivideMap(Rectangle R, int Depth, boolean Vertical) {
		/*if(Depth >= MAXDEPTH || R.Width() < MINDIM || R.Height() < MINDIM)
			return;*/
		Rectangle R1, R2;
		int Cut = 0;
		if(Vertical) {
			Cut = (int)(Math.random() * (R.Height() - 2*MINDIM) + MINDIM);
			R1 = new Rectangle(R.Left(), R.Top(), R.Width(), Cut-1);
			R2 = new Rectangle(R.Left(), R.Top()+Cut, R.Width(), R.Height() - Cut);
		} else {
			Cut = (int)(Math.random() * (R.Width() - 2*MINDIM) + MINDIM);
			R1 = new Rectangle(R.Left(), R.Top(), Cut-1, R.Height());
			R2 = new Rectangle(R.Left() + Cut, R.Top(), R.Width() - Cut, R.Height());
		}
		if(Depth >= MAXDEPTH || (R1.Width() <= MINDIM || R1.Height() <= MINDIM) || (R2.Width() <= MINDIM || R2.Height() <= MINDIM)) {
			Rects.push(R1);
			Rects.push(R2);
			return;
		} else {
			DivideMap(R1, Depth + 1, (Vertical) ? false : true);
			DivideMap(R2, Depth + 1, (Vertical) ? false : true);
		}
			
	}
	
	public Dungeon(int Width, int Height) {
		this.Width 	= Width; 
		this.Height = Height;
		Rectangle R = new Rectangle(2, 2, Width-4, Height-4);
		Rects = new java.util.Stack<Rectangle>();
		Map = new char[this.Width*this.Height];
		for(int x = 0; x < this.Width; x++)
			for(int y = 0; y < this.Height; y++)
				Map[x+y*this.Width] = '#';
		DivideMap(R, 0, false);
		while(!Rects.empty())
		{
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
