public class Lane {

    public static class OverflowException extends RuntimeException {
        // Undantag som kastas nar det inte gick att lagga 
        // in en ny bil pa vagen
    }

    protected CarPosition[] theLane;

    public Lane(int n) {
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
	// Stega fram alla fordon (utom det pa plats 0) ett steg 
        // (om det gar). (Fordonet pa plats 0 tas bort utifran 
	// mm h a metoden nedan.)
    }

    public Car getFirst() {
    	return null;
	// Returnera och tag bort bilen som star forst
    }

    public Car firstCar() {
    	return null;
	// Returnera bilen som star forst utan att ta bort den
    }


    public boolean lastFree() {
    	return false;
	// Returnera true om sista platsen ledig, annars false
    }

    public void putLast(Car c) throws OverflowException {
	// Stall en bil pa sista platsen pa vagen
	// (om det gar).
    }

    public String toString() {
    	return "";
    	//...
    	}

}
