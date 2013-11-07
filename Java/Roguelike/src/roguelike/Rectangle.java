package roguelike;

public class Rectangle {
	private int Left;
	private int Top;
	private int Right;
	private int Bottom;
	
	public Rectangle(int Left, int Top, int Right, int Bottom) {
		this.Left = Left;
		this.Top = Top;
		this.Right = Right;
		this.Bottom = Bottom;
	}

	public int Left() {
		return Left;
	}
	
	public int Top() {
		return Top;
	}
	public int Right() {
		return Right;
	}
	
	public int Bottom() {
		return Bottom;
	}
	
	public int Width() {
		return Right-Left;
	}
	
	public int Height() {
		return Bottom-Top;
	}
	
	public int XCenter() {
		return Left + (Width()/2);
	}
	
	public int YCenter() {
		return Top + (Height()/2);
	}
	
	public void TL(Integer x, Integer y)	{
		x = this.Left;
		y = this.Top;
	}
	
	public void BR(Integer x, Integer y) {
		x = this.Right;
		y = this.Bottom;
	}
	
	public void Center(Integer x, Integer y) {
		x = XCenter();
		y = YCenter();
	}
	
	public boolean Contains(int x, int y) {
		return (x >= Left && x < Right) && (y >= Top && y < Bottom);
	}
	
	public boolean Intersects(Rectangle R) {
		return 	(
				 (R.Left <= Right  && R.Right  > Left) && 
				 (R.Top  <= Bottom && R.Bottom > Top )
				);
	}
	public boolean Touching(Rectangle R) {
		return 	(
				 (R.Left-1 <= Right  && R.Right   + 1 > Left) && 
				 (R.Top-1  <= Bottom && R.Bottom + 1 > Top )
				);
	}
	
	public String toString() {
		return "Rect(L = " + Left + ", T = " + Top + ", R = " + Right + ", B = " + Bottom + ")";
	}
	
	public boolean equals(Rectangle R) {
		return (Left == R.Left && Right == R.Right && Top == R.Top && Bottom == R.Bottom);
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof Rectangle &&
				Left == ((Rectangle)o).Left &&
				Right == ((Rectangle)o).Right &&
				Top == ((Rectangle)o).Top &&
				Bottom == ((Rectangle)o).Bottom);
	}
}
