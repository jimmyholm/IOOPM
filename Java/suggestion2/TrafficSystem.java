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
	private Statistics Stats;
	private static TrafficSystem Instance = null;
  private TrafficSystem() {
		readParameters();
		r0 = new Lane(totalLaneLength - sideLaneLength);
		r1 = new Lane(sideLaneLength);
		r2 = new Lane(sideLaneLength);
		s1 = new Light(lightPeriod, greenFPeriod);
		s2 = new Light(lightPeriod, greenTPeriod);
		D1 = new CarPosition(r1);
		D2 = new CarPosition(r2);
		Stats = new Statistics();
	}
	
	public static TrafficSystem getInstance() {
		if(TrafficSystem.Instance == null)
			TrafficSystem.Instance = new TrafficSystem();
		return TrafficSystem.Instance;
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

	public void step(int stepCnt) {
		// Stega systemet ett tidssteg m h a komponenternas step-metoder
		// Skapa bilar, lagg in och ta ur pa de olika Lane-kompenenterna
		s1.step();
		s2.step();
		if(s1.isGreen())
			if(r1.firstCar() != null)
			{
				Car c = r1.getFirst();
				Stats.currentCarCount--;
				Stats.currentCarCountForward--;
				int time = (stepCnt - c.getbornTime());
				if(time > Stats.longestTimeForward)
					Stats.longestTimeForward = time;
				Stats.totalTimeForward += time;
				Stats.averageTimeForward = (float)Stats.totalTimeForward / (float)Stats.totalCarCountForward;
			}
		if(s2.isGreen())
			if(r2.firstCar() != null)
			{
				Car c = r2.getFirst();
				Stats.currentCarCount--;		
				Stats.currentCarCountTurn--;
				int time = stepCnt- c.getbornTime();
				if(time > Stats.longestTimeTurn)
					Stats.longestTimeTurn = time;
				Stats.totalTimeTurn += time;
				Stats.averageTimeTurn = (float)Stats.totalTimeTurn / (float)Stats.totalCarCountTurn;
			}
		
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
				r0.putLast(new Car(stepCnt, D, null));
				Stats.currentCarCount++;
				if(Stats.currentCarCount > Stats.mostSimultaneousCars)
					Stats.mostSimultaneousCars = Stats.currentCarCount;
				Stats.totalCarCount++;
				if(D == D1)
				{
					Stats.currentCarCountForward++;
					Stats.totalCarCountForward++;
				}
				else
				{
					Stats.currentCarCountTurn++;
					Stats.totalCarCountTurn++;
				}
			}
		}
		
	}

	public void printStatistics() {
		// Skriv statistiken samlad sa har langt
		System.out.println("\t\t\t\tStatistics\n--------------------------------------------------------------------------------");
		System.out.println("\t\t\t\tTraffic\n--------------------------------------------------------------------------------");
		System.out.println("Current cars:\t\t\t\t" + Stats.currentCarCount + "\nTotal Throughput:\t\t\t" + Stats.totalCarCount);
		System.out.println("\u001b[37;1mCurrent Cars Heading Forward:\t\t" + Stats.currentCarCountForward + "\nTotal Number of Cars Headed Forward:\t" + Stats.totalCarCountForward+"\u001b[0m");
		System.out.println("\u001b[31;1mCurrent Cars Turning:\t\t\t" + Stats.currentCarCountTurn + "\nTotal Number of Cars Turning:\t\t" + Stats.totalCarCountTurn+"\u001b[0m");
		System.out.println("Most Simultaneous Cars in simulation:\t" + Stats.mostSimultaneousCars);
		System.out.println("\t\t\t\tTiming\n--------------------------------------------------------------------------------\nMinimum number of timesteps from A to Destination: " + totalLaneLength + " timesteps.");
		System.out.println("\u001b[37;1mLongest Time Going Forward:\t" + Stats.longestTimeForward + " timesteps.\nAverage Time Going Forward:\t" + Stats.averageTimeForward + " timesteps\u001b[0m");
		System.out.println("\u001b[31;1mLongest Time Turning:\t\t" + Stats.longestTimeForward + " timesteps.\nAverage Time Turning:\t\t" + Stats.averageTimeTurn + " timesteps\u001b[0m");
	}

	public void print() {
		// Skriv ut en grafisk representation av kosituationen
		// med hjalp av klassernas toString-metoder
		System.out.println("\t\t\t\tSimulation\n--------------------------------------------------------------------------------");
		System.out.println("Legend: \u001b[37;1m" + (char)(171) + "\u001b[0m = going forward. \u001b[31;1m" +(char)(171) + "\u001b[0m = turning.");
		System.out.print(" C  ");
		for(int i = 0; i < sideLaneLength; i++) 
			System.out.print(" ");
		System.out.print("B");
		for(int i = 0; i < totalLaneLength-sideLaneLength; i++)
			System.out.print(" ");
		System.out.println("A");
		System.out.println(s1 + "\u001b[37;1m<\u001b[0m" + r1 + "\u001b[37;1m<\u001b[0m" + r0 + "\u001b[37;1m<\u001b[0m");
		System.out.println(s2 + "\u001b[37;1m<\u001b[0m" + r2 + "\u001b[37;1m<\u001b[0m");
		// Print some statistics:
		
		printStatistics();
		//
	}

	public CarPosition getForwardDestination() {
		return D1;
	}

	public CarPosition getTurnDestination() {
		return D2;
	}
}
