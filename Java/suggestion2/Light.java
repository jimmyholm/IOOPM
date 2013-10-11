public class Light {
    private int period;
    private int time;  // Intern klocka: 0, 1, ... period-1, 0, 1 ...
    private int green; // Signalen gron nar time<green 

    public Light(int period, int green) {
    	this.period = period;
			this.green  = green;
			this.time   = 0;
    	}

    public void    step() { 
		  if(++time >= period)
				time = 0;
    }

    public boolean isGreen()   {
    	return (time < green) ? true : false;
    	// Returnerar true om time<green, annars false
    }

    public String  toString()  {
			return "\u001b[37;1m(" + ((isGreen()) ? "\u001b[32mG" : "\u001b[31;1mR") +"\u001b[37m)\u001b[0m";
    	//return "Light(Period: " + period + ", time: " + time + ", isGreen: " + isGreen() + ")";
    	}
	
}
