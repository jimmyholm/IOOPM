public class Lane {

	public static class OverflowException extends RuntimeException {
		// Undantag som kastas nar det inte gick att lagga 
		// in en ny bil pa vagen
	}

	protected CarPosition[] theLane;

	public Lane(int n) {
		theLane = new CarPosition[n];
		for (int i = 0; i < n; i++)
		{
			theLane[i] = new CarPosition(this);
			if(i > 0)
				theLane[i].setForward(theLane[i-1]);
		}
		// Konstruerar ett Lane-objekt med plats for n fordon
    // Samt lanker ihop varje CarPosition med forward for den framfor
	}
    
	public boolean matchEnd(CarPosition target)
    {
    	if(theLane[0] == target)
    		return true;
    	else
    		return false;
    }
    
	public int getLength(){
		return theLane.length;
	}
    
	public void setParallel(Lane sideLane)
    {
    	int i = 0;
    	
    	while(i < sideLane.getLength() && i < theLane.length)
    	{
    		theLane[i].setTurn(sideLane.theLane[i]);
    		i++;
    	}
    }

	public void step() {
		for (int i = 1; i < theLane.length; i++){
			if(theLane[i].getCurrentCar() != null){
				theLane[i].getCurrentCar().step();
			}
		}
		// Stega fram alla fordon (utom det pa plats 0) ett steg 
		// (om det gar). (Fordonet pa plats 0 tas bort utifran 
		// mm h a metoden nedan.)
	}

	public Car getFirst() {
		Car returnCar = theLane[0].getCurrentCar();
		theLane[0].setCurrentCar(null);
		return returnCar;
		// Returnera och tag bort bilen som star forst
	}

	public Car firstCar() {
		Car returnCar = theLane[0].getCurrentCar();
		return returnCar;
		// Returnera bilen som star forst utan att ta bort den
	}


	public boolean lastFree() {
		Car returnBool = theLane[theLane.length -1].getCurrentCar();
		return (returnBool == null);
		// Returnera true om sista platsen ledig, annars false
	}

	public void putLast(Car c) throws OverflowException {
		if (lastFree())
		{
			c.setcurrentPosition(theLane[theLane.length - 1]);
			theLane[theLane.length -1].setCurrentCar(c);
		}
		else
			throw new OverflowException();
		// Stall en bil pa sista platsen pa vagen
		// (om det gar).
	}

	public String toString() {
		String ret = "";
		for(int i = 0; i < theLane.length; i++)
		{
			if(theLane[i].getCurrentCar() == null)
			{
				ret += "-";
			}
			else
			{
				ret += (char)(171);//"C";
			}
		}
		return ret;
	}

}
