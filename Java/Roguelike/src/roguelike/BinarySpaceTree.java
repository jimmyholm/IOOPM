package roguelike;

public class BinarySpaceTree {
	private BinarySpaceTree 	Left = null;
	private BinarySpaceTree 	Right = null;
	private Rectangle			Data = null;
	
	public BinarySpaceTree(Rectangle Data) {
		this.Data = Data;
	}
	
	public Rectangle getData() {
		return Data;
	}
	
	public BinarySpaceTree getLeft()
	{
		return Left;
	}
	
	public BinarySpaceTree getRight()
	{
		return Right;
	}
	
	public boolean isLeaf()
	{
		return (Left == null && Right == null);
	}
	
	public void getLeafStack(java.util.Stack<Rectangle> Stack) {
		if(isLeaf())
			Stack.push(Data);
		else
		{
			if(Left != null)
				Left.getLeafStack(Stack);
			if(Right != null)
				Right.getLeafStack(Stack);
		}
	}
	
	public void setLeft(Rectangle Data) {
		Left = new BinarySpaceTree (Data);
	}
	
	public void setRight(Rectangle Data) {
		Right = new BinarySpaceTree (Data);
	}
	
	public void addRect(Rectangle Data) {
		if(Left == null)
			Left = new BinarySpaceTree (Data);
		else if(Left.Data.Contains(Data))
			Left.addRect(Data);
		else if(Right == null)
			Right = new BinarySpaceTree(Data);
		else if(Right.Data.Contains(Data))
			Right.addRect(Data);
	}
	
	public void eraseLeaves() {
		if(isLeaf())
			return;
		if(Left != null)
			if(!Left.isLeaf())
				Left.eraseLeaves();
			else
				Left = null;
		if(Right != null)
			if(!Right.isLeaf())
				Right.eraseLeaves();
			else
				Right = null;
	}
}
