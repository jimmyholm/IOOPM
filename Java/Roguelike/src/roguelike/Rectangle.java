package roguelike;

public class Rectangle {
	private int x;
	private int y;
	private int Width;
	private int Height;
	
	public Rectangle(int x,int y, int Width, int Height) {
		this.x = x;
		this.y = y;
		this.Width = Width;
		this.Height = Height;
	}
	
	public int Left() {
		return x;
	}
	
	public int Top() {
		return y;
	}
	public int Right() {
		return x + Width;
	}
	
	public int Bottom() {
		return y + Height;
	}
	
	public int Width() {
		return Width;
	}
	
	public int Height() {
		return Height;
	}
	
	public int XCenter() {
		return x + (Width/2);
	}
	
	public int YCenter() {
		return y + (Height/2);
	}
	
	public void TL(Integer x, Integer y)	{
		x = this.x;
		y = this.y;
	}
	
	public void BR(Integer x, Integer y) {
		x = this.x + Width;
		y = this.y + Height;
	}
	
	public void Center(Integer x, Integer y) {
		x = XCenter();
		y = YCenter();
	}
}
