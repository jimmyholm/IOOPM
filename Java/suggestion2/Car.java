public class Car {

    private int bornTime;
    private CarPosition destination; 

    private CarPosition currentPosition;
    
    public void step()
    {  
    	// Uppdatera bilen ett tidssteg
    }

    // konstruktor och get- oct set-metoder
    //...

    public void setcurrentPosition (CarPosition currentPosition) { this.currentPosition = currentPosition; }

    public int getbornTime () { return this.bornTime; }
    public CarPosition getdestination () { return this.destination; }
    public CarPosition getcurrentPosition () { return this.currentPosition; }

    public Car(int bornTime, CarPosition destination, CarPosition currentPosition){
        this.bornTime = bornTime;
        this.destination = destination;
        this.currentPosition = currentPosition;
    }
    
    public String toString(){
    	return "Car(bornTime = " 
            + this.bornTime 
            + ", destination = " 
            + this.destination 
            + ", currentPosition = " 
            + this.currentPosition;
    }
    
}
