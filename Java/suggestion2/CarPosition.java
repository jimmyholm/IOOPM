


// Haller i en bil och kanner till sina "grannar". 
public class CarPosition{
	
	private Car currentCar = null; // null om ingen bil finns pa positionen
	
	private Lane owner;
	
	private CarPosition forward;
	private CarPosition turn;
	
	public CarPosition(Lane a_Owner)
	{
		owner = a_Owner;
		forward = null;
		turn = null;
	}
	
	public boolean isEnd(CarPosition target)
	{
		return owner.matchEnd(target);
	}
	
	public boolean moveForward()
	{
		if(forward != null && forward.currentCar == null)
		{
			forward.currentCar = currentCar;
			currentCar = null;
			return true;
		}
		return false;
		// Flytta bilen fram till forward
	}
	
	public boolean turn()
	{
		if(turn != null && turn.currentCar == null)
		{
			turn.currentCar = currentCar;
			currentCar = null;
			return true;
		}
		return false;
		// Flytta bilen till turn
	}

	public void setTurn(CarPosition turn) {
		this.turn = turn;
	}

	public CarPosition getTurn() {
		return turn;
	}
	
	public void setForward(carPosition forward) {
		this.forward = forward;
	}
	
	public CarPosition getForward() {
		return forward;
	}
	
}
