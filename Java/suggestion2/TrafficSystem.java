public class TrafficSystem {
	// Definierar de vagar och signaler som ingar i det 
	// system som skall studeras.
	// Samlar statistik
	
	// Attribut som beskriver bestandsdelarna i systemet
	private Lane  r0;
	private Lane  r1;
	private Lane  r2;
	private Light s1;
	private Light s2;
	
	// Diverse attribut for simuleringsparametrar (ankomstintensiteter,
	// destinationer...)
	private float arrivalIntensity; // Ankomstintensitet (% chans per step)
	private float turnIntensity;    // Chans att ankommande bil ska svanga.
	private CarPosition D1;         // Forward
	private CarPosition D2;         // Turn
	private int totalLaneLength;
	private int sideLaneLength;
	private int lightPeriod;
	private int greenFPeriod;
	private int greenTPeriod;
	// Diverse attribut for statistiksamling
	private int totalThroughput = 0;
	private int currentNumCars = 0;

	public TrafficSystem() {
		readParameters();
		r0 = new Lane(totalLaneLength - sideLaneLength);
		r1 = new Lane(sideLaneLength);
		r2 = new Lane(sideLaneLength);
		s1 = new Light(lightPeriod, greenFPeriod);
		s2 = new Light(lightPeriod, greenTPeriod);
		D1 = new CarPosition(r1);
		D2 = new CarPosition(r2);
	}

	public void readParameters() {
		// Laser in parametrar for simuleringen
		// Metoden kan lasa fran terminalfonster, dialogrutor
		// eller fran en parameterfil. Det sista alternativet
		// ar att foredra vid uttestning av programmet eftersom
		// man inte da behover mata in vardena vid varje korning.
		// Standardklassen Properties ar anvandbar for detta. 
		java.util.Properties P = new java.util.Properties();
		try
		{
			P.load(new java.io.FileInputStream("./properties.txt"));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		arrivalIntensity = Float.valueOf(P.getProperty("ArrivalIntensity"));
		turnIntensity = Float.valueOf(P.getProperty("TurnIntensity"));
		totalLaneLength = Integer.parseInt(P.getProperty("TotalLength"));
		sideLaneLength = Integer.parseInt(P.getProperty("SideLaneLength"));
		lightPeriod = Integer.parseInt(P.getProperty("LightPeriod"));
		greenFPeriod = Integer.parseInt(P.getProperty("ForwardGreenPeriod"));
		greenTPeriod = Integer.parseInt(P.getProperty("TurnGreenPeriod"));
	}

	public void step() {
		// Stega systemet ett tidssteg m h a komponenternas step-metoder
		// Skapa bilar, lagg in och ta ur pa de olika Lane-kompenenterna
		s1.step();
		s2.step();
		if(s1.isGreen())
			if(r1.getFirst() != null)
				currentNumCars--;
		if(s2.isGreen())
			if(r2.getFirst() != null)
				currentNumCars--;		
		
		r2.step();
		r1.step();
		if(r0.firstCar() != null)
			if(r0.firstCar().getdestination() == D1 && r1.lastFree())
			{
				r1.putLast(r0.getFirst());
			}
			else if(r0.firstCar().getdestination() == D2 && r2.lastFree())
			{
				r2.putLast(r0.getFirst());
			}
		r0.step();
		if(Math.random() <= arrivalIntensity)
		{
			if(r0.lastFree())
			{
				CarPosition D = (Math.random() <= turnIntensity) ? D2 : D1;
				r0.putLast(new Car((int)System.currentTimeMillis(), D, null));
				currentNumCars++;
				totalThroughput++;
			}
		}
		
	}

	public void printStatistics() {
		// Skriv statistiken samlad sa har langt
	}

	public void print() {
		// Skriv ut en grafisk representation av kosituationen
		// med hjalp av klassernas toString-metoder
		System.out.print(" C  ");
		for(int i = 0; i < sideLaneLength; i++) 
			System.out.print(" ");
		System.out.print("B");
		for(int i = 0; i < totalLaneLength-sideLaneLength; i++)
			System.out.print(" ");
		System.out.println("A");
		System.out.println(s1 + "<" + r1 + "<" + r0 + "<");
		System.out.println(s2 + "<" + r2 + "<");
		// Print some statistics:
		System.out.println("Current cars: " + currentNumCars + "\tTotal Throughput: " + totalThroughput);
	}
}
